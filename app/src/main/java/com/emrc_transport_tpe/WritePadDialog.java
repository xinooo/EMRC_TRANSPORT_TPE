package com.emrc_transport_tpe;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;



/**
 * Created by An on 2018/7/6.
 */

public class WritePadDialog extends Dialog implements View.OnClickListener{

    Context context;
    WindowManager.LayoutParams p ;
    DialogListener dialogListener;
    public static Button btnOk,btnClear,btnCancel;
    private static FrameLayout frameLayout;

    public WritePadDialog(Context context, DialogListener dialogListener) {
        super(context);
        this.context = context;
        this.dialogListener = dialogListener;
    }

    PaintView mView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.write_pad);

        p = getWindow().getAttributes();  //获取对话框当前的参数值
//        p.height = 880;//(int) (d.getHeight() * 0.4);   //高度设置为屏幕的0.4
//        p.width = 1320;//(int) (d.getWidth() * 0.6);    //宽度设置为屏幕的0.6

        p.height = 800;//(int) (d.getHeight() * 0.4);   //高度设置为屏幕的0.4
        p.width = 1200;//(int) (d.getWidth() * 0.6);    //宽度设置为屏幕的0.6
        getWindow().setAttributes(p);     //设置生效


        mView = new PaintView(context);
        frameLayout = (FrameLayout) findViewById(R.id.tablet_view);
        btnOk = (Button) findViewById(R.id.tablet_ok);
        btnClear = (Button) findViewById(R.id.tablet_clear);
        btnCancel = (Button) findViewById(R.id.tablet_cancel);
        frameLayout.addView(mView);
        mView.requestFocus();

        btnOk.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == btnOk){
            try {
                dialogListener.refreshActivity(mView.getCachebBitmap());
                WritePadDialog.this.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(v == btnClear){
            mView.clear();
        }
        if(v == btnCancel){
            cancel();
        }
    }

    class PaintView extends View {
        private Paint paint;
        private Canvas cacheCanvas;
        private Bitmap cachebBitmap;
        private Path path;

        public Bitmap getCachebBitmap() {
            return cachebBitmap;
        }

        public PaintView(Context context) {
            super(context);
            init();
        }

        private void init(){
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStrokeWidth(12);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setColor(Color.BLUE);
            path = new Path();
            cachebBitmap = Bitmap.createBitmap(p.width, (int)(p.height*0.8), Bitmap.Config.ARGB_8888);
            cacheCanvas = new Canvas(cachebBitmap);
            cacheCanvas.drawColor(Color.WHITE);
        }
        public void clear() {
            if (cacheCanvas != null) {
                cachebBitmap = Bitmap.createBitmap(p.width, (int)(p.height*0.8), Bitmap.Config.ARGB_8888);
                cacheCanvas = new Canvas(cachebBitmap);
                invalidate();
            }
        }



        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawBitmap(cachebBitmap, 0, 0, null);
            canvas.drawPath(path, paint);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            cachebBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            cacheCanvas = new Canvas(cachebBitmap);
        }

        private float cur_x, cur_y;
        private static final float TOUCH_TOLERANCE = 4;

        @Override
        public boolean onTouchEvent(MotionEvent event) {

            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    cur_x = x;
                    cur_y = y;
                    path.reset();
                    path.moveTo(cur_x, cur_y);
                    break;
                case MotionEvent.ACTION_MOVE:
                    float dx = Math.abs(x - cur_x);
                    float dy = Math.abs(y - cur_y);
                    if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                        path.quadTo(cur_x, cur_y, (x + cur_x) / 2, (y + cur_y) / 2);
                        cur_x = x;
                        cur_y = y;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    cacheCanvas.drawPath(path, paint);
                    path.reset();
                    break;
            }
            invalidate();
            return true;
        }
    }
}
