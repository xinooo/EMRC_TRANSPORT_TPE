package com.emrc_transport_tpe;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class InjuredFragment extends Fragment implements View.OnClickListener, View.OnTouchListener, ViewPager.OnPageChangeListener {

    private boolean F = false, B = false;
    private static ArrayList<View> view_list_1;
    private int downX = 0, downY = 0, pg = 0;
    private RelativeLayout inju_log;
    public ViewPager pager1;
    private List<String> title_list;
    private ImageView front_view, back_view, undo,F_check,B_check;
    private TextView inju_un,inju_F_un,inju_B_un;
    public static ArrayList<Double> front_list = MainActivity.inju_front;
    public static ArrayList<Double> back_list = MainActivity.inju_back;
    private ArrayList<Double> front_list2 = new ArrayList<Double>();
    private ArrayList<Double> back_list2 = new ArrayList<Double>();
    private DisplayMetrics metrics = new DisplayMetrics();



    public InjuredFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_injured, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        findViewById();
        addTab();
        addImage();
        view_0();
        view_1();
        load();

}

    public void view_0(){
        F_check = (ImageView)view_list_1.get(0).findViewById(R.id.F_check);
        inju_F_un = (TextView) view_list_1.get(0).findViewById(R.id.inju_F_un);
        F_check.setOnClickListener(this);
        inju_F_un.setOnClickListener(this);
    }
    public void view_1(){
        B_check = (ImageView)view_list_1.get(1).findViewById(R.id.B_check);
        inju_B_un = (TextView) view_list_1.get(1).findViewById(R.id.inju_B_un);
        B_check.setOnClickListener(this);
        inju_B_un.setOnClickListener(this);
    }

    public void load() {
        for(int i=0;i<front_list.size();i+=2){
            front_list.set(i,front_list.get(i)*322/768);
            front_list.set(i+1,front_list.get(i+1)*447/952);
        }
        for(int i=0;i<back_list.size();i+=2){
            back_list.set(i,back_list.get(i)*322/768);
            back_list.set(i+1,back_list.get(i+1)*447/952);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                print(front_list, front_view, 0);
                print(back_list, back_view, 2);
                Loading(false);
            }
        }, 500);

        MainActivity.main6_body_load();
    }

    private void findViewById() {
        undo = (ImageView) getView().findViewById(R.id.fs_rotate);
        pager1 = (ViewPager) getView().findViewById(R.id.inju_pager_1);
        inju_log = (RelativeLayout) getView().findViewById(R.id.inju_log);
        inju_un = (TextView) getView().findViewById(R.id.inju_un);
    }
    @SuppressLint("InflateParams")
    private void addTab() {
        @SuppressWarnings({"static-access","NewApi"})
        LayoutInflater mInflater = getActivity().getLayoutInflater().from(InjuredFragment.this.getContext());
        View v1 = mInflater.inflate(R.layout.item_1_inju_front_view, null);
        View v2 = mInflater.inflate(R.layout.item_1_inju_back_view, null);


        view_list_1 = new ArrayList<View>();
        view_list_1.add(v1);
        view_list_1.add(v2);

//		title_list = new ArrayList<String>();
//		title_list.add(getString(R.string.s_ts_43)); // body front
//		title_list.add(getString(R.string.s_ts_44)); // and back :)
        pager1.setOnPageChangeListener(this);
        pager1.setAdapter(new MyPagerAdapter_MAIN(view_list_1));
        pager1.setCurrentItem(0);

        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
    }

    private void addImage() {
        front_view = (ImageView) view_list_1.get(0).findViewById(R.id.front_canvas);
        back_view = (ImageView) view_list_1.get(1).findViewById(R.id.back_canvas);
        // OnClick-------
        undo.setOnClickListener(this);
        // OnTouch-------
        back_view.setOnTouchListener(this);
        front_view.setOnTouchListener(this);
    }


    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int arg0) {
        undo.setVisibility(View.GONE);
        switch (arg0) {
            case 0:
                pg = 0;
                if (F) {
                    undo.setVisibility(View.VISIBLE);
                }
                break;
            case 1:
                pg = 1;
                if (B) {
                    undo.setVisibility(View.VISIBLE);
                }
                break;
        }

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onClick(View v) {
        if (v == undo) {
            Loading(true);
            ArrayList<Double> tmp = new ArrayList<Double>();
            if (pg == 0) {
                for (int i = 0; i < front_list.size() - 2; i++) {
                    tmp.add(front_list.get(i));
                }
                front_list.clear();
                for (int i = 0; i < tmp.size(); i++) {
                    front_list.add(tmp.get(i));
                }
                print(front_list, front_view, 0);
                Loading(false);
                if (front_list.size() == 0) {
                    undo.setVisibility(View.GONE);
                    F = false;
                }
            } else {
                for (int i = 0; i < back_list.size() - 2; i++) {
                    tmp.add(back_list.get(i));
                }
                back_list.clear();
                for (int i = 0; i < tmp.size(); i++) {
                    back_list.add(tmp.get(i));
                }
                print(back_list, back_view, 1);
                Loading(false);
                if (back_list.size() == 0) {
                    undo.setVisibility(View.GONE);
                    B = false;
                }
            }
        }

        if (v == F_check) {
//            save();
//            load();
            pager1.setCurrentItem(1);
        }

        if (v == B_check) {
//            save();
//            load();
            pager1.setCurrentItem(0);
        }
    }
    private void Loading(boolean v) {
        if (v) {
            inju_log.setVisibility(View.VISIBLE);
        } else {
            inju_log.setVisibility(View.GONE);
        }
    }

    @SuppressWarnings("deprecation")
    private void print(ArrayList<Double> llf, ImageView v, int in) {
        int mDaySize = metrics.widthPixels / 100;// 字體大小
        int mCircle = mDaySize;// 大圓
        Bitmap bitmap = Bitmap.createBitmap((int) v.getWidth(), (int) v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.setBackgroundDrawable(new BitmapDrawable(bitmap));

        Paint mPaint = new Paint();
        for (int i = 0; i < llf.size(); i += 2) {
            mPaint.setColor(Color.parseColor("#ff4c4c")); // 大圓:紅
            mPaint.setStyle(Paint.Style.FILL);
            int mWidth = MainActivity.metrics.widthPixels; // 螢幕寬
            int mHeight = MainActivity.metrics.heightPixels; // 螢幕長
            int x = (int) (llf.get(i) * mWidth);
            int y = (int) (llf.get(i + 1) * mHeight);
            canvas.drawCircle(x, y, mCircle, mPaint);
        }
        if (llf.size() > 0) {
            switch (in) {
                case 0:
                    undo.setVisibility(View.VISIBLE);
                    F = true;
                    break;
                case 1:
                    undo.setVisibility(View.VISIBLE);
                    B = true;
                    break;
                case 2:
                    B = true;
                    break;
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 開始位置
                downX = (int) event.getX();
                downY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                // 移動位置
                break;
            case MotionEvent.ACTION_UP:
                // 離開位置
                int upX = (int) event.getX();
                int upY = (int) event.getY();
                // 點擊事件
                if (Math.abs(upX - downX) < 10 && Math.abs(upY - downY) < 10) {
                    getPoint(upX, upY, (ImageView) v);
                }
                break;
            default:
                break;
        }
        return true;
    }

    private void getPoint(int x, int y, ImageView v) {

        if (v == front_view) {
            getView(x, y, front_view, front_list, true);
        }
        if (v == back_view) {
            getView(x, y, back_view, back_list, false);
        }
    }

    @SuppressLint({"UseValueOf", "NewApi"})
    private void getView(int x, int y, ImageView v, ArrayList<Double> list, boolean f) {
        boolean view = true;
        int mWidth = MainActivity.metrics.widthPixels; // 螢幕寬
        int mHeight = MainActivity.metrics.heightPixels; // 螢幕長
        for (int i = 0; i < list.size(); i += 2) {
            double getX = list.get(i) * mWidth;
            double getY = list.get(i + 1) * mHeight;
            if (Math.abs(getX - x) < 50 && Math.abs(getY - y) < 50) {
                startActivity(new Intent(InjuredFragment.this.getContext(), PhotoActivity.class));
                view = false;
            }
        }
        if (view) {
            if (list.size() < 20) {
                list.add(getPercent(x, mWidth));
                list.add(getPercent(y, mHeight));
                if (f) {
                    print(list, v, 0);
                } else {
                    print(list, v, 1);
                }
            }else {
                Toast.makeText(InjuredFragment.this.getContext(), getString(R.string.s_ts_45), Toast.LENGTH_SHORT).show();
            }
        }

        // Toast.makeText(this, "X:" + getPercent(x, mWidth) + " Y:" +
        // getPercent(y, mHeight), Toast.LENGTH_SHORT).show();
    }

    private double getPercent(Integer num, Integer totalPeople) {
        String percent;
        Double p3 = 0.0;
        if (totalPeople == 0) {
            p3 = 0.0;
        } else {
            p3 = num * 0.01 / totalPeople;
        }
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits(2);// 控制保留小数点后几位，2：表示保留2位小数点
        percent = nf.format(p3);
        percent = percent.substring(0, percent.length() - 1);
        return Double.parseDouble(percent);
    }

    @SuppressLint("NewApi")
    public void save() {
        String sv = "";
        ArrayList<String> tmp_string = new ArrayList<String>();
        if (front_list.size() > 0 || back_list.size() > 0) {
            if (front_list.size() > 0) {
                for(int j=0;j<front_list.size();j+=2){
                    front_list.set(j,front_list.get(j)*768/322);
                    front_list.set(j+1,front_list.get(j+1)*952/447);
                }
                for (int i = 0; i < front_list.size(); i += 2) {
                    double x = front_list.get(i), y = front_list.get(i + 1);
                    //double x = front_list.get(i)/320*763, y = front_list.get(i + 1)/445*948;
                    sv = Tools.getInjury(InjuredFragment.this.getContext(), x, y, 0);
                    if (sv.length() > 1) {
                        tmp_string.add(sv);
                    }
                }
            }
            if (back_list.size() > 0) {
                for(int j=0;j<back_list.size();j+=2){
                    back_list.set(j,back_list.get(j)*768/322);
                    back_list.set(j+1,back_list.get(j+1)*952/447);
                }
                for (int i = 0; i < back_list.size(); i += 2) {
                    double x = back_list.get(i), y = back_list.get(i + 1);
                    sv = Tools.getInjury(InjuredFragment.this.getContext(), x, y, 1);
                    if (sv.length() > 1) {
                        tmp_string.add(sv);
                    }
                }
            }
            if (tmp_string.size() > 0) {
                String t = "";
                boolean one = true;
                for (int i = 0; i < tmp_string.size(); i++) {
                    if (tmp_string.get(i).length() > 0) {
                        if (one) {
                            one = false;
                            // s = tmp_string.get(i);
                            t = tmp_string.get(i);
                        } else {
                            // s += "\n" + tmp_string.get(i);
                            t += " " + tmp_string.get(i);
                        }
                    }
                }

                if (MainActivity.info_data.size() > 0) {
                    String name, age, other = "";
                    name = MainActivity.info_data.get(0);
                    age = MainActivity.info_data.get(1);
                    other = MainActivity.info_data.get(2);
                    MainActivity.info_data.clear();
                    if (other.length() > 0) {
                        other += "\n(" + getString(R.string.s_Menu_3) + ":" + t + ")";
                    } else {
                        other += "(" + getString(R.string.s_Menu_3) + ":" + t + ")";
                    }
                    MainActivity.info_data.add(name);
                    MainActivity.info_data.add(age);
                    MainActivity.info_data.add(other);
                } else {
                    String other = "(" + getString(R.string.s_Menu_3) + ":" + t + ")";
                    MainActivity.info_data.add("");
                    MainActivity.info_data.add("");
                    MainActivity.info_data.add(other);
                }

//                showDialog(t);
                MainActivity.menu_item.set(2, getString(R.string.s_Menu_3) + "\n<" + t + ">");
//                MainActivity.main_6_other.setText("(" +getString(R.string.s_Menu_3) +"："+  t + ")");
            } else {
                //finish();

            }
        } else {
            MainActivity.menu_item.set(2, getString(R.string.s_Menu_3));
            MainActivity.menu_upload(InjuredFragment.this.getContext());
            //finish();
        }
    }

//    @SuppressLint("NewApi")
//    private void showDialog(String f) {
//        final String s = f;
//        MainActivity.menu_item.set(2, getString(R.string.s_Menu_3) + "\n<" + f + ">");
//        MainActivity.menu_upload(InjuredFragment.this.getContext());
//        new AlertDialog.Builder(InjuredFragment.this.getContext()).setTitle(getString(R.string.s_ts_42))
//                .setPositiveButton(getString(R.string.s_ts_12), new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface arg0, int arg1) {
//                        //finish();
//                    }
//                }).setNegativeButton(getString(R.string.s_ts_13), new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface arg0, int arg1) {
//                if (MainActivity.info_data.size() > 0) {
//                    String name, age, other = "";
//                    name = MainActivity.info_data.get(0);
//                    age = MainActivity.info_data.get(1);
//                    other = MainActivity.info_data.get(2);
//                    MainActivity.info_data.clear();
//                    if (other.length() > 1) {
//                        other += "\n"+getString(R.string.s_Menu_3) + "(" + s + ").";
//                    } else {
//                        other += getString(R.string.s_Menu_3) + "(" + s + ").";
//                    }
//                    MainActivity.info_data.add(name);
//                    MainActivity.info_data.add(age);
//                    MainActivity.info_data.add(other);
//                } else {
//                    String other = "";
//                    other += getString(R.string.s_Menu_3) + "(" + s + ").";
//                    MainActivity.info_data.add("");
//                    MainActivity.info_data.add("");
//                    MainActivity.info_data.add(other);
//                }
//                //finish();
//            }
//        }).show();
//    }

    public void big(){
        if (front_list.size() > 0) {
            for(int j=0;j<front_list.size();j+=2){
                front_list.set(j,front_list.get(j)*768/322);
                front_list.set(j+1,front_list.get(j+1)*952/447);
            }
        }
        if (back_list.size() > 0) {
            for(int j=0;j<back_list.size();j+=2){
                back_list.set(j,back_list.get(j)*768/322);
                back_list.set(j+1,back_list.get(j+1)*952/447);
            }
        }
    }


}
