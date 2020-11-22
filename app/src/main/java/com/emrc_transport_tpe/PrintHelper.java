package com.emrc_transport_tpe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.print.pdf.PrintedPdfDocument;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by An on 2018/12/19.
 */

public class PrintHelper {

    private static final String LOG_TAG = "PrintHelper";
    private static final int MAX_PRINT_SIZE = 3500;
    static final boolean PRINT_ACTIVITY_RESPECTS_ORIENTATION;
    static final boolean IS_MIN_MARGINS_HANDLING_CORRECT;
    public static final int SCALE_MODE_FIT = 1;
    public static final int SCALE_MODE_FILL = 2;
    @SuppressLint({"InlinedApi"})
    public static final int COLOR_MODE_MONOCHROME = 1;
    @SuppressLint({"InlinedApi"})
    public static final int COLOR_MODE_COLOR = 2;
    public static final int ORIENTATION_LANDSCAPE = 1;
    public static final int ORIENTATION_PORTRAIT = 2;
    final Context mContext;
    BitmapFactory.Options mDecodeOptions = null;
    final Object mLock = new Object();
    int mScaleMode = 2;
    int mColorMode = 2;
    int mOrientation = 1;

    public static boolean systemSupportsPrint() {
        return Build.VERSION.SDK_INT >= 19;
    }

    public PrintHelper(Context context) {
        this.mContext = context;
    }

    public void setScaleMode(int scaleMode) {
        this.mScaleMode = scaleMode;
    }

    public int getScaleMode() {
        return this.mScaleMode;
    }

    public void setColorMode(int colorMode) {
        this.mColorMode = colorMode;
    }

    public int getColorMode() {
        return this.mColorMode;
    }

    public void setOrientation(int orientation) {
        this.mOrientation = orientation;
    }

    public int getOrientation() {
        return Build.VERSION.SDK_INT >= 19 && this.mOrientation == 0?1:this.mOrientation;
    }

    public void printBitmap(String jobName, Bitmap bitmap) {
        this.printBitmap(jobName, (Bitmap)bitmap, (PrintHelper.OnPrintFinishCallback)null);
    }

    public void printBitmap(String jobName, Bitmap bitmap, PrintHelper.OnPrintFinishCallback callback) {
        if(Build.VERSION.SDK_INT >= 19 && bitmap != null) {
            @SuppressLint("WrongConstant") PrintManager printManager = (PrintManager)this.mContext.getSystemService("print");
            PrintAttributes.MediaSize mediaSize;
            if(isPortrait(bitmap)) {
                mediaSize = PrintAttributes.MediaSize.UNKNOWN_PORTRAIT;
            } else {
                mediaSize = PrintAttributes.MediaSize.UNKNOWN_LANDSCAPE;
            }

            @SuppressLint("WrongConstant") PrintAttributes attr = (new PrintAttributes.Builder()).setMediaSize(mediaSize).setColorMode(this.mColorMode).build();
            printManager.print(jobName, new PrintHelper.PrintBitmapAdapter(jobName, this.mScaleMode, bitmap, callback), attr);
        }
    }

    public void printBitmap(String jobName, Uri imageFile) throws FileNotFoundException {
        this.printBitmap(jobName, (Uri)imageFile, (PrintHelper.OnPrintFinishCallback)null);
    }

    @SuppressLint("WrongConstant")
    public void printBitmap(String jobName, Uri imageFile, PrintHelper.OnPrintFinishCallback callback) throws FileNotFoundException {
        if(Build.VERSION.SDK_INT >= 19) {
            PrintDocumentAdapter printDocumentAdapter = new PrintHelper.PrintUriAdapter(jobName, imageFile, callback, this.mScaleMode);
            PrintManager printManager = (PrintManager)this.mContext.getSystemService("print");
            PrintAttributes.Builder builder = new PrintAttributes.Builder();
            builder.setColorMode(this.mColorMode);
            if(this.mOrientation != 1 && this.mOrientation != 0) {
                if(this.mOrientation == 2) {
                    builder.setMediaSize(PrintAttributes.MediaSize.UNKNOWN_PORTRAIT);
                }
            } else {
                builder.setMediaSize(PrintAttributes.MediaSize.UNKNOWN_LANDSCAPE);
            }

            PrintAttributes attr = builder.build();
            printManager.print(jobName, printDocumentAdapter, attr);
        }
    }

    static boolean isPortrait(Bitmap bitmap) {
        return bitmap.getWidth() <= bitmap.getHeight();
    }

    private static PrintAttributes.Builder copyAttributes(PrintAttributes other) {
        PrintAttributes.Builder b = (new PrintAttributes.Builder()).setMediaSize(other.getMediaSize()).setResolution(other.getResolution()).setMinMargins(other.getMinMargins());
        if(other.getColorMode() != 0) {
            b.setColorMode(other.getColorMode());
        }

        if(Build.VERSION.SDK_INT >= 23 && other.getDuplexMode() != 0) {
            b.setDuplexMode(other.getDuplexMode());
        }

        return b;
    }

    static Matrix getMatrix(int imageWidth, int imageHeight, RectF content, int fittingMode) {
        Matrix matrix = new Matrix();
        float scale = content.width() / (float)imageWidth;
        if(fittingMode == 2) {
            scale = Math.max(scale, content.height() / (float)imageHeight);
        } else {
            scale = Math.min(scale, content.height() / (float)imageHeight);
        }

        matrix.postScale(scale, scale);
        float translateX = (content.width() - (float)imageWidth * scale) / 2.0F;
        float translateY = (content.height() - (float)imageHeight * scale) / 2.0F;
        matrix.postTranslate(translateX, translateY);
        return matrix;
    }

    @SuppressLint("StaticFieldLeak")
    void writeBitmap(final PrintAttributes attributes, final int fittingMode, final Bitmap bitmap, final ParcelFileDescriptor fileDescriptor, final CancellationSignal cancellationSignal, final PrintDocumentAdapter.WriteResultCallback writeResultCallback) {
        final PrintAttributes pdfAttributes;
        if(IS_MIN_MARGINS_HANDLING_CORRECT) {
            pdfAttributes = attributes;
        } else {
            pdfAttributes = copyAttributes(attributes).setMinMargins(new PrintAttributes.Margins(0, 0, 0, 0)).build();
        }

        new AsyncTask<Void, Void, PdfDocument.Page>() {
            protected PdfDocument.Page doInBackground(Void... params) {
                if(cancellationSignal.isCanceled()) {
                    return null;
                } else {
                    PrintedPdfDocument pdfDocument = new PrintedPdfDocument(PrintHelper.this.mContext, pdfAttributes);
                    Bitmap maybeGrayscale = PrintHelper.convertBitmapForColorMode(bitmap, pdfAttributes.getColorMode());
                    if(cancellationSignal.isCanceled()) {
                        return null;
                    } else {
                        PdfDocument.Page dummyPage = null;
                        try {
                            PdfDocument.Page page = pdfDocument.startPage(1);
                            RectF contentRect;
                            if(PrintHelper.IS_MIN_MARGINS_HANDLING_CORRECT) {
                                contentRect = new RectF(page.getInfo().getContentRect());
                            } else {
                                PrintedPdfDocument dummyDocument = new PrintedPdfDocument(PrintHelper.this.mContext, attributes);
                                dummyPage = dummyDocument.startPage(1);
                                contentRect = new RectF(dummyPage.getInfo().getContentRect());
                                dummyDocument.finishPage(dummyPage);
                                dummyDocument.close();
                            }

                            Matrix matrix = PrintHelper.getMatrix(maybeGrayscale.getWidth(), maybeGrayscale.getHeight(), contentRect, fittingMode);
                            if(!PrintHelper.IS_MIN_MARGINS_HANDLING_CORRECT) {
                                matrix.postTranslate(contentRect.left, contentRect.top);
                                page.getCanvas().clipRect(contentRect);
                            }

                            page.getCanvas().drawBitmap(maybeGrayscale, matrix, (Paint)null);
                            pdfDocument.finishPage(page);
                            if(cancellationSignal.isCanceled()) {
                                dummyPage = null;
                                return dummyPage;
                            }

                            pdfDocument.writeTo(new FileOutputStream(fileDescriptor.getFileDescriptor()));
                            dummyPage = null;
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            pdfDocument.close();
                            if(fileDescriptor != null) {
                                try {
                                    fileDescriptor.close();
                                } catch (IOException var16) {
                                    ;
                                }
                            }

                            if(maybeGrayscale != bitmap) {
                                maybeGrayscale.recycle();
                            }

                        }

                        return dummyPage;
                    }
                }
            }

            protected void onPostExecute(PdfDocument.Page throwable) {
                if(cancellationSignal.isCanceled()) {
                    writeResultCallback.onWriteCancelled();
                } else if(throwable == null) {
                    writeResultCallback.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});
                } else {
                    writeResultCallback.onWriteFailed((CharSequence)null);
                }

            }
        }.execute(new Void[0]);
    }

    Bitmap loadConstrainedBitmap(Uri uri) throws FileNotFoundException {
        if(uri != null && this.mContext != null) {
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inJustDecodeBounds = true;
            this.loadBitmap(uri, opt);
            int w = opt.outWidth;
            int h = opt.outHeight;
            if(w > 0 && h > 0) {
                int imageSide = Math.max(w, h);

                int sampleSize;
                for(sampleSize = 1; imageSide > 3500; sampleSize <<= 1) {
                    imageSide >>>= 1;
                }

                if(sampleSize > 0 && 0 < Math.min(w, h) / sampleSize) {
                    Object var8 = this.mLock;
                    BitmapFactory.Options decodeOptions;
                    synchronized(this.mLock) {
                        this.mDecodeOptions = new BitmapFactory.Options();
                        this.mDecodeOptions.inMutable = true;
                        this.mDecodeOptions.inSampleSize = sampleSize;
                        decodeOptions = this.mDecodeOptions;
                    }

                    boolean var18 = false;

                    Bitmap var23;
                    try {
                        var18 = true;
                        var23 = this.loadBitmap(uri, decodeOptions);
                        var18 = false;
                    } finally {
                        if(var18) {
                            Object var12 = this.mLock;
                            synchronized(this.mLock) {
                                this.mDecodeOptions = null;
                            }
                        }
                    }

                    Object var9 = this.mLock;
                    synchronized(this.mLock) {
                        this.mDecodeOptions = null;
                        return var23;
                    }
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            throw new IllegalArgumentException("bad argument to getScaledBitmap");
        }
    }

    private Bitmap loadBitmap(Uri uri, BitmapFactory.Options o) throws FileNotFoundException {
        if(uri != null && this.mContext != null) {
            InputStream is = null;

            Bitmap var4;
            try {
                is = this.mContext.getContentResolver().openInputStream(uri);
                var4 = BitmapFactory.decodeStream(is, (Rect)null, o);
            } finally {
                if(is != null) {
                    try {
                        is.close();
                    } catch (IOException var11) {
                        Log.w("PrintHelper", "close fail ", var11);
                    }
                }

            }

            return var4;
        } else {
            throw new IllegalArgumentException("bad argument to loadBitmap");
        }
    }

    static Bitmap convertBitmapForColorMode(Bitmap original, int colorMode) {
        if(colorMode != 1) {
            return original;
        } else {
            Bitmap grayscale = Bitmap.createBitmap(original.getWidth(), original.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(grayscale);
            Paint p = new Paint();
            ColorMatrix cm = new ColorMatrix();
            cm.setSaturation(0.0F);
            ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
            p.setColorFilter(f);
            c.drawBitmap(original, 0.0F, 0.0F, p);
            c.setBitmap((Bitmap)null);
            return grayscale;
        }
    }

    static {
        PRINT_ACTIVITY_RESPECTS_ORIENTATION = Build.VERSION.SDK_INT < 20 || Build.VERSION.SDK_INT > 23;
        IS_MIN_MARGINS_HANDLING_CORRECT = Build.VERSION.SDK_INT != 23;
    }


    private class PrintUriAdapter extends PrintDocumentAdapter {
        final String mJobName;
        final Uri mImageFile;
        final PrintHelper.OnPrintFinishCallback mCallback;
        final int mFittingMode;
        PrintAttributes mAttributes;
        AsyncTask<Uri, Boolean, Bitmap> mLoadBitmap;
        Bitmap mBitmap;

        PrintUriAdapter(String jobName, Uri imageFile, PrintHelper.OnPrintFinishCallback callback, int fittingMode) {
            this.mJobName = jobName;
            this.mImageFile = imageFile;
            this.mCallback = callback;
            this.mFittingMode = fittingMode;
            this.mBitmap = null;
        }

        @SuppressLint("StaticFieldLeak")
        public void onLayout(final PrintAttributes oldPrintAttributes, final PrintAttributes newPrintAttributes, final CancellationSignal cancellationSignal, final LayoutResultCallback layoutResultCallback, Bundle bundle) {
            synchronized(this) {
                this.mAttributes = newPrintAttributes;
            }

            if(cancellationSignal.isCanceled()) {
                layoutResultCallback.onLayoutCancelled();
            } else if(this.mBitmap != null) {
                @SuppressLint("WrongConstant") PrintDocumentInfo info = (new android.print.PrintDocumentInfo.Builder(this.mJobName)).setContentType(1).setPageCount(1).build();
                boolean changed = !newPrintAttributes.equals(oldPrintAttributes);
                layoutResultCallback.onLayoutFinished(info, changed);
            } else {
                this.mLoadBitmap = (new AsyncTask<Uri, Boolean, Bitmap>() {
                    protected void onPreExecute() {
                        cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() {
                            public void onCancel() {
                                PrintUriAdapter.this.cancelLoad();
                                cancel(false);
                            }
                        });
                    }

                    protected Bitmap doInBackground(Uri... uris) {
                        try {
                            return PrintHelper.this.loadConstrainedBitmap(PrintUriAdapter.this.mImageFile);
                        } catch (FileNotFoundException var3) {
                            return null;
                        }
                    }

                    protected void onPostExecute(Bitmap bitmap) {
                        super.onPostExecute(bitmap);
                        if(bitmap != null && (!PrintHelper.PRINT_ACTIVITY_RESPECTS_ORIENTATION || PrintHelper.this.mOrientation == 0)) {
                            PrintAttributes.MediaSize mediaSize;
                            synchronized(this) {
                                mediaSize = PrintUriAdapter.this.mAttributes.getMediaSize();
                            }

                            if(mediaSize != null && mediaSize.isPortrait() != PrintHelper.isPortrait(bitmap)) {
                                Matrix rotation = new Matrix();
                                rotation.postRotate(90.0F);
                                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), rotation, true);
                            }
                        }

                        PrintUriAdapter.this.mBitmap = bitmap;
                        if(bitmap != null) {
                            @SuppressLint("WrongConstant") PrintDocumentInfo info = (new android.print.PrintDocumentInfo.Builder(PrintUriAdapter.this.mJobName)).setContentType(1).setPageCount(1).build();
                            boolean changed = !newPrintAttributes.equals(oldPrintAttributes);
                            layoutResultCallback.onLayoutFinished(info, changed);
                        } else {
                            layoutResultCallback.onLayoutFailed((CharSequence)null);
                        }

                        PrintUriAdapter.this.mLoadBitmap = null;
                    }

                    protected void onCancelled(Bitmap result) {
                        layoutResultCallback.onLayoutCancelled();
                        PrintUriAdapter.this.mLoadBitmap = null;
                    }
                }).execute(new Uri[0]);
            }
        }

        void cancelLoad() {
            Object var1 = PrintHelper.this.mLock;
            synchronized(PrintHelper.this.mLock) {
                if(PrintHelper.this.mDecodeOptions != null) {
                    if(Build.VERSION.SDK_INT < 24) {
                        PrintHelper.this.mDecodeOptions.requestCancelDecode();
                    }

                    PrintHelper.this.mDecodeOptions = null;
                }

            }
        }

        public void onFinish() {
            super.onFinish();
            this.cancelLoad();
            if(this.mLoadBitmap != null) {
                this.mLoadBitmap.cancel(true);
            }

            if(this.mCallback != null) {
                this.mCallback.onFinish();
            }

            if(this.mBitmap != null) {
                this.mBitmap.recycle();
                this.mBitmap = null;
            }

        }

        public void onWrite(PageRange[] pageRanges, ParcelFileDescriptor fileDescriptor, CancellationSignal cancellationSignal, WriteResultCallback writeResultCallback) {
            PrintHelper.this.writeBitmap(this.mAttributes, this.mFittingMode, this.mBitmap, fileDescriptor, cancellationSignal, writeResultCallback);
        }
    }


    private class PrintBitmapAdapter extends PrintDocumentAdapter {
        private final String mJobName;
        private final int mFittingMode;
        private final Bitmap mBitmap;
        private final PrintHelper.OnPrintFinishCallback mCallback;
        private PrintAttributes mAttributes;

        PrintBitmapAdapter(String jobName, int fittingMode, Bitmap bitmap, PrintHelper.OnPrintFinishCallback callback) {
            this.mJobName = jobName;
            this.mFittingMode = fittingMode;
            this.mBitmap = bitmap;
            this.mCallback = callback;
        }

        public void onLayout(PrintAttributes oldPrintAttributes, PrintAttributes newPrintAttributes, CancellationSignal cancellationSignal, LayoutResultCallback layoutResultCallback, Bundle bundle) {
            this.mAttributes = newPrintAttributes;
            @SuppressLint("WrongConstant") PrintDocumentInfo info = (new android.print.PrintDocumentInfo.Builder(this.mJobName)).setContentType(1).setPageCount(1).build();
            boolean changed = !newPrintAttributes.equals(oldPrintAttributes);
            layoutResultCallback.onLayoutFinished(info, changed);
        }

        public void onWrite(PageRange[] pageRanges, ParcelFileDescriptor fileDescriptor, CancellationSignal cancellationSignal, WriteResultCallback writeResultCallback) {
            PrintHelper.this.writeBitmap(this.mAttributes, this.mFittingMode, this.mBitmap, fileDescriptor, cancellationSignal, writeResultCallback);
        }

        public void onFinish() {
            if(this.mCallback != null) {
                this.mCallback.onFinish();
            }

        }
    }

    public interface OnPrintFinishCallback {
        void onFinish();
    }
}
