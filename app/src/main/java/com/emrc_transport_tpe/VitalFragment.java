package com.emrc_transport_tpe;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;



import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class VitalFragment extends Fragment implements View.OnClickListener{

    private int count = 5, gcs_i0 = 0, gcs_i1 = 0, gcs_i2 = 0, oth_i0 = 0, oth_i1 = 0, oth_i2 = 0;
    private int e_i0 = 0, e_i1 = 0, e_i2 = 0, v_i0 = 0, v_i1 = 0, v_i2 = 0, m_i0 = 0, m_i1 = 0, m_i2 = 0;
    private String[] s={"意識","清","聲","痛","否"};
    private ArrayList<String> gcs_list = new ArrayList<String>();
    private ArrayList<String> other_list = new ArrayList<String>();
    private ArrayList<View> view_list = new ArrayList<View>();
    private ArrayAdapter<String> gcs_adapter_0, gcs_adapter_1, gcs_adapter_2;
    private ArrayAdapter<String> other_adapter_0, other_adapter_1, other_adapter_2;
    private AlertDialog Dialog;
    private Button gcs_0,gcs_1, gcs_2,e_0, e_1, e_2, v_0, v_1, v_2, m_0, m_1, m_2;
    private EditText pulse_0, pulse_1, pulse_2, rr_0, rr_1, rr_2;
    private EditText bp_0, bp_1, bp_2, spo2_0, spo2_1, spo2_2;
    private Spinner   oth_0, oth_1, oth_2;
    private TextView tmp, vita_no_top, vita_no_down;
    private TextView time_0, time_1, time_2;
    private TextWatcher textWatcher_0, textWatcher_1, textWatcher_2;



    public VitalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vita, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        findViewById();
        addList();
        reset();
        load();
    }
     private void findViewById(){
         time_0 = (TextView) getView().findViewById(R.id.vita_item_0_time);  //時間
         time_1 = (TextView) getView().findViewById(R.id.vita_item_1_time);
         time_2 = (TextView) getView().findViewById(R.id.vita_item_2_time);
         bp_0 = (EditText) getView().findViewById(R.id.vita_item_0_bp);		//血壓
         bp_1 = (EditText) getView().findViewById(R.id.vita_item_1_bp);
         bp_2 = (EditText) getView().findViewById(R.id.vita_item_2_bp);
         pulse_0 = (EditText) getView().findViewById(R.id.vita_item_0_pulse);		//心跳
         pulse_1 = (EditText) getView().findViewById(R.id.vita_item_1_pulse);
         pulse_2 = (EditText) getView().findViewById(R.id.vita_item_2_pulse);
         rr_0 = (EditText) getView().findViewById(R.id.vita_item_0_respiration);	//呼吸
         rr_1 = (EditText) getView().findViewById(R.id.vita_item_1_respiration);
         rr_2 = (EditText) getView().findViewById(R.id.vita_item_2_respiration);
         gcs_0 = (Button) getView().findViewById(R.id.vita_item_0_gcs);		//意識
         gcs_1 = (Button) getView().findViewById(R.id.vita_item_1_gcs);
         gcs_2 = (Button) getView().findViewById(R.id.vita_item_2_gcs);
         e_0 = (Button) getView().findViewById(R.id.vita_item_0_e);		//昏迷指數E
         v_0 = (Button) getView().findViewById(R.id.vita_item_0_v);		//昏迷指數V
         m_0 = (Button) getView().findViewById(R.id.vita_item_0_m);		//昏迷指數M
         e_1 = (Button) getView().findViewById(R.id.vita_item_1_e);
         v_1 = (Button) getView().findViewById(R.id.vita_item_1_v);
         m_1 = (Button) getView().findViewById(R.id.vita_item_1_m);
         e_2 = (Button) getView().findViewById(R.id.vita_item_2_e);
         v_2 = (Button) getView().findViewById(R.id.vita_item_2_v);
         m_2 = (Button) getView().findViewById(R.id.vita_item_2_m);
     }

    private void load() {
        if (MainActivity.vita_data.size() > 0) {
            String tmp;
            time_0.setText("" + MainActivity.vita_data.get(0));
            bp_0.setText("" + MainActivity.vita_data.get(1));
            pulse_0.setText("" + MainActivity.vita_data.get(2));
            rr_0.setText("" + MainActivity.vita_data.get(3));
            //spo2_0.setText("" + MainActivity.vita_data.get(4));
            tmp = MainActivity.vita_data.get(5);
            if (tmp.length() > 4) {
                gcs_i0 = Integer.parseInt(tmp.substring(0, 1));
                //oth_i0 = Integer.parseInt(tmp.substring(1, 2));
                e_i0 = Integer.parseInt(tmp.substring(2, 3));
                v_i0 = Integer.parseInt(tmp.substring(3, 4));
                m_i0 = Integer.parseInt(tmp.substring(4, 5));
//                gcs_0.setSelection(gcs_i0, true);
                // if (!user) {
                //oth_0.setSelection(oth_i0, true);

                setEVMtoBtn(e_0, e_i0, 0);
                setEVMtoBtn(v_0, v_i0, 1);
                setEVMtoBtn(m_0, m_i0, 2);
                gcs_0.setText(s[gcs_i0]);
                if (gcs_i0 != 0) {
                    gcs_0.setTextColor(Color.RED);
                }
                // }
            }
            time_1.setText("" + MainActivity.vita_data.get(6));
            bp_1.setText("" + MainActivity.vita_data.get(7));
            pulse_1.setText("" + MainActivity.vita_data.get(8));
            rr_1.setText("" + MainActivity.vita_data.get(9));
            //spo2_1.setText("" + MainActivity.vita_data.get(10));
            tmp = MainActivity.vita_data.get(11);

            if (tmp.length() > 4) {
                gcs_i1 = Integer.parseInt(tmp.substring(0, 1));
                //oth_i1 = Integer.parseInt(tmp.substring(1, 2));
                e_i1 = Integer.parseInt(tmp.substring(2, 3));
                v_i1 = Integer.parseInt(tmp.substring(3, 4));
                m_i1 = Integer.parseInt(tmp.substring(4, 5));
//                gcs_1.setSelection(gcs_i1, true);
                // if (!user) {
                //oth_1.setSelection(oth_i1, true);

                setEVMtoBtn(e_1, e_i1, 0);
                setEVMtoBtn(v_1, v_i1, 1);
                setEVMtoBtn(m_1, m_i1, 2);
                // }
                gcs_1.setText(s[gcs_i1]);
                if (gcs_i1 != 0) {
                    gcs_1.setTextColor(Color.RED);
                }
            }
            time_2.setText("" + MainActivity.vita_data.get(12));
            bp_2.setText("" + MainActivity.vita_data.get(13));
            pulse_2.setText("" + MainActivity.vita_data.get(14));
            rr_2.setText("" + MainActivity.vita_data.get(15));
            //spo2_2.setText("" + MainActivity.vita_data.get(16));
            tmp = MainActivity.vita_data.get(17);
            if (tmp.length() > 4) {
                gcs_i2 = Integer.parseInt(tmp.substring(0, 1));
                //oth_i2 = Integer.parseInt(tmp.substring(1, 2));
                e_i2 = Integer.parseInt(tmp.substring(2, 3));
                v_i2 = Integer.parseInt(tmp.substring(3, 4));
                m_i2 = Integer.parseInt(tmp.substring(4, 5));
//                gcs_2.setSelection(gcs_i2, true);
                // if (!user) {
                //oth_2.setSelection(oth_i2, true);

                setEVMtoBtn(e_2, e_i2, 0);
                setEVMtoBtn(v_2, v_i2, 1);
                setEVMtoBtn(m_2, m_i2, 2);
                // }
                gcs_2.setText(s[gcs_i2]);
                if (gcs_i2 != 0) {
                    gcs_2.setTextColor(Color.RED);
                }
            }
            MainActivity.vita_data.clear();
        }

    }

    private void setEVMtoBtn(Button btn, int x, int y) {
        String tmp = "";
        switch (y) {
            case 0:
                tmp = "E";
                break;
            case 1:
                tmp = "V";
                break;
            case 2:
                tmp = "M";
                break;
        }
        if (x != 0) {
            btn.setText(tmp + x);
            btn.setTextColor(Color.RED);
        }
    }

    @SuppressLint("NewApi")
    private void addList() {
        gcs_list.add(getString(R.string.s_List_8));
        gcs_list.add(getString(R.string.s_List_9));
        gcs_list.add(getString(R.string.s_List_10));
        gcs_list.add(getString(R.string.s_List_11));
        gcs_list.add(getString(R.string.s_List_12));
//        other_list.add(getString(R.string.s_List_13));
//        other_list.add(getString(R.string.s_List_14));
//        other_list.add(getString(R.string.s_List_15));
//        other_list.add(getString(R.string.s_List_16));
//        other_list.add(getString(R.string.s_List_17));
    }

    @SuppressLint({"SimpleDateFormat", "NewApi"})
    private void reset() {
        InitTextWatcher();
        time_0.setOnClickListener(this);
        time_1.setOnClickListener(this);
        time_2.setOnClickListener(this);
//        gcs_0.setOnItemSelectedListener(this);
//        // TODO
//        gcs_adapter_0 = new ArrayAdapter<String>(VitalFragment.this.getContext(), R.layout.style_spinner_15, gcs_list);
//        gcs_adapter_0.setDropDownViewResource(R.layout.style_spinner_15);
//        gcs_0.setAdapter(gcs_adapter_0);
//        gcs_1.setOnItemSelectedListener(this);
//        gcs_adapter_1 = new ArrayAdapter<String>(VitalFragment.this.getContext(), R.layout.style_spinner_15, gcs_list);
//        gcs_adapter_1.setDropDownViewResource(R.layout.style_spinner_15);
//        gcs_1.setAdapter(gcs_adapter_1);
//        gcs_2.setOnItemSelectedListener(this);
//        gcs_adapter_2 = new ArrayAdapter<String>(VitalFragment.this.getContext(), R.layout.style_spinner_15, gcs_list);
//        gcs_adapter_2.setDropDownViewResource(R.layout.style_spinner_15);
//        gcs_2.setAdapter(gcs_adapter_2);
//        int result = R.layout.style_spinner_15;
//        if (MainActivity.EN) {
//            result = R.layout.style_spinner_15;
//        }
        gcs_0.setOnClickListener(this);
        gcs_1.setOnClickListener(this);
        gcs_2.setOnClickListener(this);
        e_0.setOnClickListener(this);
        v_0.setOnClickListener(this);
        m_0.setOnClickListener(this);
        e_1.setOnClickListener(this);
        v_1.setOnClickListener(this);
        m_1.setOnClickListener(this);
        e_2.setOnClickListener(this);
        v_2.setOnClickListener(this);
        m_2.setOnClickListener(this);
        // }
        bp_0.addTextChangedListener(textWatcher_0);
        bp_1.addTextChangedListener(textWatcher_1);
        bp_2.addTextChangedListener(textWatcher_2);
        pulse_0.addTextChangedListener(textWatcher_0);
        pulse_1.addTextChangedListener(textWatcher_1);
        pulse_2.addTextChangedListener(textWatcher_2);
        rr_0.addTextChangedListener(textWatcher_0);
        rr_1.addTextChangedListener(textWatcher_1);
        rr_2.addTextChangedListener(textWatcher_2);
    }

    private void InitTextWatcher() {
        // �إߤ�r��ť
        textWatcher_0 = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                setEdtext(bp_0, pulse_0, rr_0, time_0);
            }

        };
        textWatcher_1 = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                setEdtext(bp_1, pulse_1, rr_1, time_1);
            }

        };
        textWatcher_2 = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                setEdtext(bp_2, pulse_2, rr_2, time_2);
            }

        };
    }

    private void setEdtext(EditText bp, EditText pulse, EditText respiration, TextView time) {
        // maxValue(bp, pulse, respiration);
        setBlack(bp, pulse, respiration, time);
        setRY(bp, pulse, respiration);
        // red(bp, pulse, respiration);
    }

    @SuppressLint("ResourceAsColor")
    private void setBlack(EditText BPs, EditText PPs, EditText RRs, TextView time) {
        int BP = BPs.getText().toString().length(), PP = PPs.getText().toString().length(),
                RR = RRs.getText().toString().length();
        if (BP < 4) {
            setColor(BPs, R.color.black, R.color.white);
            setTime(time);
        }

        if (PP == 1) {
            setColor(PPs, R.color.black, R.color.white);
            setTime(time);
        }

        if (RR == 1) {
            setColor(RRs, R.color.black, R.color.white);
            setTime(time);
        }

        if (BP == 0 && PP == 0 && RR == 0) {
            time.setText("");
        }

    }

    @SuppressLint("ResourceAsColor")
    private void setRY(EditText BPs, EditText PPs, EditText RRs) {
        // delayed:�M�I(��)
        if (BPs.getText().toString().length() >= 4) {
            boolean r = true;
            String mbp = BPs.getText().toString();
            if (mbp.indexOf('.') == -1) {
                int mssbp = Integer.parseInt(mbp.substring(0, 2));
                int msdbp = Integer.parseInt(mbp.substring(2));

                if (mssbp > 50) {
                } else if (mssbp < 50) {
                    mssbp = Integer.parseInt(mbp.substring(0, 3));
                    msdbp = Integer.parseInt(mbp.substring(3));
                    if (mbp.substring(3).length() == 1) {
                        r = !r;
                        setColor(BPs, R.color.black, R.color.white);
                    }
                } else {
                    r = !r;
                    setColor(BPs, R.color.black, R.color.white);
                }
                if (r) {
                    // SBP:���Y��,DBP:�αi��
                    int SBP = mssbp;
                    int DBP = msdbp;
                    if (SBP >= 80 && DBP >= 40) {
                        setColor(BPs, R.color.black, R.color.gold);
                        if (count > 2) {
                            count = 2;
                        }
                    } else {
                        setColor(BPs, R.color.red, R.color.white);
                        count = 1;
                    }
                }
            }
        }

        if (PPs.getText().toString().length() > 1)

        {
            String PP = PPs.getText().toString();
            int Pulse = Integer.parseInt(PP);
            if (Pulse <= 140 && Pulse >= 50) {
                // 50 <= Pulse <= 140
                setColor(PPs, R.color.black, R.color.gold);
                if (count > 2) {
                    count = 2;
                }
            } else {
                setColor(PPs, R.color.red, R.color.white);
                count = 1;
            }
        }

        if (RRs.getText().toString().length() > 1) {
            String RR = RRs.getText().toString();
            int Respiration = Integer.parseInt(RR);
            if (Respiration <= 30 && Respiration >= 10) {
                // 10 <= Respiration <= 30
                setColor(RRs, R.color.black, R.color.gold);
                if (count > 2) {
                    count = 2;
                }
            } else {
                setColor(RRs, R.color.red, R.color.white);
                count = 1;
            }
        }
    }

    @SuppressWarnings("deprecation")
    private void setColor(EditText ED, int text, int background) {
        ColorStateList color = VitalFragment.this.getResources().getColorStateList(text);
        ED.setTextColor(color);
        Drawable drawable = VitalFragment.this.getResources().getDrawable(background);
        ED.setBackground(drawable);
    }

    private void setTime(TextView time) {
        Calendar calendar = Calendar.getInstance();
        if (time.length() < 1) {
            String HOUR = "" + calendar.get(Calendar.HOUR_OF_DAY);
            String MIN = "" + calendar.get(Calendar.MINUTE);
            if (HOUR.length() < 2) {
                HOUR = "0" + HOUR;
            }
            if (MIN.length() < 2) {
                MIN = "0" + MIN;
            }
            time.setText(HOUR + ":" + MIN);
        }
    }

    @SuppressLint("NewApi")
    private void myDialog() {
        Calendar c = Calendar.getInstance();
        new TimePickerDialog(VitalFragment.this.getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String hour = hourOfDay + "", min = minute + "";
                if (hourOfDay < 10) {
                    hour = "0" + hourOfDay;
                }
                if (minute < 10) {
                    min = "0" + minute;
                }
                tmp.setText(hour + ":" + min);
            }
        },c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE),true).show();
    }

    private void setEVM(TextView t, int x, Button e, Button v, Button m, Button g) {
        String type = t.getText().toString().substring(0, 1);
        switch (type) {
            case "E":
                showEVMDialog(x, 1,e);
                break;
            case "V":
                showEVMDialog(x, 2,v);
                break;
            case "M":
                showEVMDialog(x, 3,m);
                break;
            default:
                showEVMDialog(x, 4,g);
                break;
        }
    }



    @SuppressLint("NewApi")
    private void showEVMDialog(final int x, final int y, Button b) {
        ArrayList<String> value = new ArrayList<String>();
        switch (y) {
            case 1:
                // title = getString(R.string.gcs_List_00);
                value.add(getString(R.string.gcs_List_00));
                value.add(getString(R.string.gcs_List_01));
                value.add(getString(R.string.gcs_List_02));
                value.add(getString(R.string.gcs_List_03));
                value.add(getString(R.string.gcs_List_04));
                break;
            case 2:
                value.add(getString(R.string.gcs_List_10));
                value.add(getString(R.string.gcs_List_11));
                value.add(getString(R.string.gcs_List_12));
                value.add(getString(R.string.gcs_List_13));
                value.add(getString(R.string.gcs_List_14));
                value.add(getString(R.string.gcs_List_15));
                break;
            case 3:
                value.add(getString(R.string.gcs_List_20));
                value.add(getString(R.string.gcs_List_21));
                value.add(getString(R.string.gcs_List_22));
                value.add(getString(R.string.gcs_List_23));
                value.add(getString(R.string.gcs_List_24));
                value.add(getString(R.string.gcs_List_25));
                value.add(getString(R.string.gcs_List_26));
                break;
            case 4:
                value.add(getString(R.string.s_List_8));
                value.add(getString(R.string.s_List_9));
                value.add(getString(R.string.s_List_10));
                value.add(getString(R.string.s_List_11));
                value.add(getString(R.string.s_List_12));
                break;
        }
        // Create LinearLayout Dynamically
        LinearLayout layout = new LinearLayout(VitalFragment.this.getContext());

        // Setup Layout Attributes
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(params);
        layout.setOrientation(LinearLayout.VERTICAL);

        // Create a TextView to add to layout
        AlertDialog.Builder builder = new AlertDialog.Builder(VitalFragment.this.getContext());
        for (int i = 0; i < value.size(); i++) {
            layout.addView(new mTextview(VitalFragment.this.getContext(), value.get(i), b, i,x, y, value.size()));
        }
        builder.setView(layout);
        Dialog = builder.create();
        Dialog.show();
    }

    @SuppressLint("NewApi")
    private class mTextview extends TextView {
        public mTextview(Context context, final String str, final Button vi, final int i, final int x,final int y, final int z) {
            super(context);
            this.setText(str);
            this.setTextSize(25);
            this.setTextColor(Color.BLACK);
            this.setGravity(Gravity.CENTER);
            this.setPadding(20, 20, 20, 20);
            this.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (i != 0) {
                        switch (y) {
                            case 1:
                                vi.setText("E" + (z - i));
                                if(x==1){
                                    e_i0 = z-i;
                                }
                                if(x==2){
                                    e_i1 = z-i;
                                }
                                if(x==3){
                                    e_i2 = z-i;
                                }
                                break;
                            case 2:
                                vi.setText("V" + (z - i));
                                if(x==1){
                                    v_i0 = z-i;
                                }
                                if(x==2){
                                    v_i1 = z-i;
                                }
                                if(x==3){
                                    v_i2 = z-i;
                                }
                                break;
                            case 3:
                                vi.setText("M" + (z - i));
                                if(x==1){
                                    m_i0 = z-i;
                                }
                                if(x==2){
                                    m_i1 = z-i;
                                }
                                if(x==3){
                                    m_i2 = z-i;
                                }
                                break;
                            case 4:
                                vi.setText(s[i]);
                                if(x==1){
                                    gcs_i0 = i;
                                }
                                if(x==2){
                                    gcs_i1 = i;
                                }
                                if(x==3){
                                    gcs_i2 = i;
                                }
                                break;

                        }
                        vi.setTextColor(Color.RED);
                    } else {
                        switch (y) {
                            case 1:
                                vi.setText("EYE");
                                if(x==1){
                                    e_i0 = 0;
                                }
                                if(x==2){
                                    e_i1 = 0;
                                }
                                if(x==3){
                                    e_i2 = 0;
                                }
                                break;
                            case 2:
                                if(x==1){
                                    v_i0 = 0;
                                }
                                if(x==2){
                                    v_i1 = 0;
                                }
                                if(x==3){
                                    v_i2 = 0;
                                }
                                vi.setText("VERBAL");
                                break;
                            case 3:
                                if(x==1){
                                    m_i0 = 0;
                                }
                                if(x==2){
                                    m_i1 = 0;
                                }
                                if(x==3){
                                    m_i2 = 0;
                                }
                                vi.setText("MOTOR");
                                break;
                            case 4:
                                vi.setText(s[0]);
                                if(x==1){
                                    gcs_i0 = 0;
                                }
                                if(x==2){
                                    gcs_i1 = 0;
                                }
                                if(x==3){
                                    gcs_i2 = 0;
                                }
                                break;
                        }
                        vi.setTextColor(Color.BLACK);
                    }
                    Dialog.cancel();
                }
            });
        }
    }



    @Override
    public void onClick(View v) {
        if (v == time_0 || v == time_1 || v == time_2) {
            tmp = (TextView) v;
            if (tmp.getText().toString().length() < 1) {
                Calendar calendar = Calendar.getInstance();
                String HOUR = "" + calendar.get(Calendar.HOUR_OF_DAY);
                String MIN = "" + calendar.get(Calendar.MINUTE);
                if (HOUR.length() < 2) {
                    HOUR = "0" + HOUR;
                }
                if (MIN.length() < 2) {
                    MIN = "0" + MIN;
                }
                tmp.setText(HOUR + ":" + MIN);
            } else {
                myDialog();
            }
        }

        if (v == e_0 || v == v_0 || v == m_0 || v == gcs_0) {
            setEVM((TextView) v, 1, e_0, v_0, m_0,gcs_0);
        }
        if (v == e_1 || v == v_1 || v == m_1 || v == gcs_1) {
            setEVM((TextView) v, 2, e_1, v_1, m_1,gcs_1);
        }
        if (v == e_2 || v == v_2 || v == m_2 || v == gcs_2) {
            setEVM((TextView) v, 3, e_2, v_2, m_2,gcs_2);
        }
    }

//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        switch (parent.getId()) {
//            case R.id.vita_item_0_gcs:
//                gcs_i0 = position;
//                if(gcs_i0 != 0){
//                    setTime(time_0);
//                }
//                break;
//            case R.id.vita_item_1_gcs:
//                gcs_i1 = position;
//                if(gcs_i1 != 0){
//                    setTime(time_1);
//                }
//                break;
//            case R.id.vita_item_2_gcs:
//                gcs_i2 = position;
//                if(gcs_i2 != 0){
//                    setTime(time_2);
//                }
//                break;
//            default:
//                break;
//        }
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }

    @SuppressLint("NewApi")
    public void save() {
        MainActivity.vita_data.clear();
        ArrayList<String> list = new ArrayList<String>();
        list.add(time_0.getText().toString());
        list.add(bp_0.getText().toString());
        list.add(pulse_0.getText().toString());
        list.add(rr_0.getText().toString());
//        list.add(spo2_0.getText().toString());
        list.add("");
        list.add(gcs_i0 + "" + oth_i0 + "" + e_i0 + "" + v_i0 + "" + m_i0);
        list.add(time_1.getText().toString());
        list.add(bp_1.getText().toString());
        list.add(pulse_1.getText().toString());
        list.add(rr_1.getText().toString());
//        list.add(spo2_1.getText().toString());
        list.add("");
        list.add(gcs_i1 + "" + oth_i1 + "" + e_i1 + "" + v_i1 + "" + m_i1);
        list.add(time_2.getText().toString());
        list.add(bp_2.getText().toString());
        list.add(pulse_2.getText().toString());
        list.add(rr_2.getText().toString());
//        list.add(spo2_2.getText().toString());
        list.add("");
        list.add(gcs_i2 + "" + oth_i2 + "" + e_i2 + "" + v_i2 + "" + m_i2);
        String title = "";
        for (int j = 0; j < list.size(); j++) {
            MainActivity.vita_data.add(list.get(j));
            if (j == 0 && list.get(j).length() > 1) {
                title = list.get(j);
            }
            if (j == 6 && list.get(j).length() > 1) {
                title = list.get(j);
            }
            if (j == 12 && list.get(j).length() > 1) {
                title = list.get(j);
            }
        }
        if (title.length() > 0) {
            MainActivity.menu_item.set(3, getString(R.string.s_Menu_4) + "\n<" + title + ">");
        } else {
            MainActivity.menu_item.set(3, getString(R.string.s_Menu_4));
        }
        MainActivity.menu_upload(VitalFragment.this.getContext());
        list.clear();

        ChangeLevel();
    }

    @SuppressLint("NewApi")
    public void ChangeLevel(){
        if (MainActivity.leve_count > count && count != 5) {
             final String intArray[] = { getString(R.string.s_List_18),
                    getString(R.string.s_List_19), getString(R.string.s_List_20),
                    getString(R.string.s_List_21) };
            new AlertDialog.Builder(VitalFragment.this.getContext()).setTitle(getString(R.string.s_ts_49) + intArray[count] + " ?")
                    .setPositiveButton(getString(R.string.s_ts_12), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                        }
                    })
                    .setNegativeButton(getString(R.string.s_ts_13), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            MainActivity.leve_count = count;
                            MainActivity.emrc_count = 0;
                            MainActivity.setButtonColor(count);
                            MainActivity.menu_upload(VitalFragment.this.getContext());
                        }
                    }).show();
        }
    }
}
