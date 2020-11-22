package com.emrc_transport_tpe;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;


import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MedicalFragment extends Fragment {
    public static ArrayList<View> viewlist = new ArrayList<View>();
    private ArrayList<String> treatment_list_string = new ArrayList<String>();
    public ArrayList<Boolean> treatment_list_click = new ArrayList<Boolean>();
    private ListView mediacallist;


    public MedicalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_medical, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        findViewById();
        setCount();
    }

    public void load() {
        String medical = MainActivity.medical;
        if (!medical.equals("null")) {
            ArrayList<String> tmp = new ArrayList<String>();
            int tag1 = 0;
            while (tag1 < medical.length()) {
                String msg = getLine(medical, tag1, '#');
                tag1 += msg.length() + 1;
                tmp.add(msg);
            }
            for (int at = 0; at < tmp.size(); at++) {
                int tag2 = 0;
                int type = Integer.parseInt(tmp.get(at).substring(0, 1));
                tag2++;
                String sum = getLine(tmp.get(at), tag2, '/');
                int num = Integer.parseInt(sum);
                tag2 += sum.length() + 1;
                String ts = tmp.get(at).substring(tag2);
                CheckBox cb = (CheckBox) viewlist.get(num).findViewById(R.id.cb_style);
                CheckBox cb5 = (CheckBox) viewlist.get(num).findViewById(R.id.cb_style_5);
                EditText ed5 = (EditText) viewlist.get(num).findViewById(R.id.ed_style_5);
                cb.setChecked(true);
                cb5.setChecked(true);
                switch (type) {
                    case 0:
                        // no message
                        break;
                    case 1:
                        // 1 message
                        ed5.setText(ts + "");
                        break;

                }
            }
        }
    }
    private String getLine(String msg, int run, char key) {
        return msg.substring(run).substring(0, msg.substring(run).indexOf(key));
    }

    private void findViewById() {
        mediacallist = (ListView) getView().findViewById(R.id.mediacallist);
        mediacallist.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
    }

    @SuppressLint("NewApi")
    private void setCount() {
        treatment_list_string.clear();
        treatment_list_click.clear();
        viewlist.clear();



        //treatment_list_string.add("6/"+getString(R.string.s_medical_00));
        treatment_list_string.add("0/"+getString(R.string.s_medical_01));
        treatment_list_string.add("0/"+getString(R.string.s_medical_02));
        treatment_list_string.add("0/"+getString(R.string.s_medical_03));
        treatment_list_string.add("0/"+getString(R.string.s_medical_04));
        treatment_list_string.add("0/"+getString(R.string.s_medical_05));
        treatment_list_string.add("0/"+getString(R.string.s_medical_06));
        treatment_list_string.add("0/"+getString(R.string.s_medical_07));
        treatment_list_string.add("0/"+getString(R.string.s_medical_08));
        treatment_list_string.add("0/"+getString(R.string.s_medical_09));
        treatment_list_string.add("1/"+getString(R.string.s_medical_10));




        for (int i = 0; i < treatment_list_string.size(); i++) {
            treatment_list_click.add(false);
            setMyAdapter(i);
        }
        mediacallist.setAdapter(new MyListAdapter(viewlist));
    }

    @SuppressLint({"InflateParams", "NewApi"})
    private void setMyAdapter(final int position) {
        View vi = LayoutInflater.from(getActivity()).inflate(R.layout.style_checkbox2, null);
        TableRow row_0 = (TableRow) vi.findViewById(R.id.row_0);
        TableRow row_1 = (TableRow) vi.findViewById(R.id.row_1);
        TableRow row_2 = (TableRow) vi.findViewById(R.id.row_2);
        TableRow row_3 = (TableRow) vi.findViewById(R.id.row_3);
        TableRow row_4 = (TableRow) vi.findViewById(R.id.row_4);
        TableRow row_5 = (TableRow) vi.findViewById(R.id.row_5);
        final CheckBox cb_style = (CheckBox) vi.findViewById(R.id.cb_style);
        final CheckBox cb_style_5 = (CheckBox) vi.findViewById(R.id.cb_style_5);
//        final CheckBox cb_style_2 = (CheckBox) vi.findViewById(R.id.cb_style_2);
//        final CheckBox cb_style_3 = (CheckBox) vi.findViewById(R.id.cb_style_3);
//        final CheckBox cb_style4 = (CheckBox) vi.findViewById(R.id.cb_style4);
        final EditText ed_style = (EditText) vi.findViewById(R.id.ed_style);
        final EditText ed_style_5 = (EditText) vi.findViewById(R.id.ed_style_5);
//        final EditText ed_style_1 = (EditText) vi.findViewById(R.id.ed_style_1);
        TextView tv_style = (TextView) vi.findViewById(R.id.tv_style);
//        TextView tv_style_1 = (TextView) vi.findViewById(R.id.tv_style_1);
//        TextView tv_style_1_2 = (TextView) vi.findViewById(R.id.tv_style_1_2);
//        TextView tv_style_2 = (TextView) vi.findViewById(R.id.tv_style_2);
//        TextView tv_style_3 = (TextView) vi.findViewById(R.id.tv_style_3);

        cb_style.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                treatment_list_click.set(position, isChecked);
            }
        });

        cb_style_5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                treatment_list_click.set(position, isChecked);
            }
        });
        String msg = treatment_list_string.get(position).toString() + '|';
        int type = Integer.parseInt(msg.substring(0, 1));
        switch (type) {
            case 0:
                cb_style.setText(msg.substring(2).substring(0, msg.substring(2).indexOf('|')));

                ed_style.setVisibility(View.GONE);
                tv_style.setVisibility(View.GONE);
                row_1.setVisibility(View.GONE);
                row_2.setVisibility(View.GONE);
                row_3.setVisibility(View.GONE);
                row_4.setVisibility(View.GONE);
                row_5.setVisibility(View.GONE);
                break;
            case 1:
                String m0 = msg.substring(2).substring(0, msg.substring(2).indexOf('|'));
//                String m1 = msg.substring(3 + m0.length()).substring(0, msg.substring(3 + m0.length()).indexOf('|'));
                cb_style_5.setText(m0);
                ed_style_5.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void afterTextChanged(Editable s) {
                    }

                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        CheckBox cb = (CheckBox) viewlist.get(position).findViewById(R.id.cb_style_5);
                        if (ed_style_5.getText().toString().length() > 0) {
                            cb.setChecked(true);
                        } else {
                            cb.setChecked(false);
                        }
                    }
                });
//                tv_style.setText(m1);
                row_0.setVisibility(View.GONE);
                row_1.setVisibility(View.GONE);
                row_2.setVisibility(View.GONE);
                row_3.setVisibility(View.GONE);
                row_4.setVisibility(View.GONE);
                break;
        }
        viewlist.add(vi);
    }

    public void save() {
        MainActivity.medical = "";
        ArrayList<String> tmp_title = new ArrayList<String>();
        ArrayList<String> tmp_string = new ArrayList<String>();
        for (int i = 0; i < treatment_list_click.size(); i++) {
            String medical = "";
            if (treatment_list_click.get(i)) {
                int type = Integer.parseInt(treatment_list_string.get(i).substring(0, 1));
                medical += type + "" + i + "/";
                switch (type){
                    case 0:
                        medical += '#';
                        break;
                    case 1:
                        medical += getViewString_0(i)+"#";;
                        break;
                }
                MainActivity.medical += medical;
                switch (i) {
                    case 0:
                        tmp_string.add(getString(R.string.s_medical_01));
                        break;
                    case 1:
                        tmp_string.add(getString(R.string.s_medical_02));
                        break;
                    case 2:
                        tmp_string.add(getString(R.string.s_medical_03));
                        break;
                    case 3:
                        tmp_string.add(getString(R.string.s_medical_04));
                        break;
                    case 4:
                        tmp_string.add(getString(R.string.s_medical_05));
                        break;
                    case 5:
                        tmp_string.add(getString(R.string.s_medical_06));
                        break;
                    case 6:
                        tmp_string.add(getString(R.string.s_medical_07));
                        break;
                    case 7:
                        tmp_string.add(getString(R.string.s_medical_08));
                        break;
                    case 8:
                        tmp_string.add(getString(R.string.s_medical_09));
                        break;
                    case 9:
                        tmp_string.add(getString(R.string.s_medical_101)+getViewString_0(i));
                        break;
                }
            }
        }
        getListString(tmp_title, tmp_string);
        tmp_string.clear();
        if(MainActivity.link){
            MainActivity.out("MED/"+MainActivity.medical+"|");
        }
    }

    private void getListString(ArrayList<String> T, ArrayList<String> S) {
        if (S.size() > 0) {
            boolean data = false;
            String HelpN, Mediacal="", HelpY;
            if (MainActivity.main5_data.size() > 0) {
                HelpN = MainActivity.main5_data.get(0);
                HelpY = MainActivity.main5_data.get(1);
                MainActivity.main5_data.clear();
                MainActivity.main5_data.add(HelpN);
                MainActivity.main5_data.add(HelpY);
                MainActivity.main5_data.add("");
                data = true;
            } else {
                Mediacal = "";
                MainActivity.main5_data.add("");
                MainActivity.main5_data.add("");
                MainActivity.main5_data.add("");
            }
            for (int i = 0; i < S.size(); i++) {
                if (i == 0) {
                    if (data) {
                        Mediacal += S.get(i) + "\n";
                    } else {
                        Mediacal += S.get(i) + "\n";
                    }
                } else {
                    Mediacal += S.get(i) + "\n";
                }
            }
//            other += ")";
            MainActivity.main5_data.set(2,Mediacal);
        }else{
            if (MainActivity.main5_data.size() > 0){
                MainActivity.main5_data.set(2,"");
            }else {
                MainActivity.main5_data.add("");
                MainActivity.main5_data.add("");
                MainActivity.main5_data.add("");
            }
            MainActivity.Mediacal_TextView.setText(getString(R.string.s_ts_64));
        }
    }

    private String getViewString_0(int i) {
        EditText ed_style = (EditText) viewlist.get(i).findViewById(R.id.ed_style_5);
        return ed_style.getText().toString();
    }

}
