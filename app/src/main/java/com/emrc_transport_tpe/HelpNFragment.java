package com.emrc_transport_tpe;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;



import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HelpNFragment extends Fragment {

    private View view;

    public static ArrayList<View> viewlist = new ArrayList<View>();
    private ArrayList<String> treatment_list_string = new ArrayList<String>();
    private ArrayList<Boolean> treatment_list_click = new ArrayList<Boolean>();
    private ListView emrgnlist;
    private ImageView ok;
    private TextView emrgn_title;



    public HelpNFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_help_y, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        findViewById();
        setCount();

    }

    public void load() {
        String helpn = MainActivity.helpn;
        if (!helpn.equals("null")) {
            ArrayList<String> tmp = new ArrayList<String>();
            int tag1 = 0;
            while (tag1 < helpn.length()) {
                String msg = getLine(helpn, tag1, '#');
                tag1 += msg.length() + 1;
                tmp.add(msg);
                // MainActivity.toast(msg, this);
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
                CheckBox cb4 = (CheckBox) viewlist.get(num).findViewById(R.id.cb_style4);
                EditText ed5 = (EditText) viewlist.get(num).findViewById(R.id.ed_style_5);
                cb.setChecked(true);
                cb4.setChecked(true);
                switch (type) {
                    case 0:
                        // no message
                        break;
                    case 3:
                        break;
                    case 4:
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
        emrgnlist = (ListView) getView().findViewById(R.id.emrgnlist);
        emrgnlist.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
    }

    @SuppressLint("NewApi")
    private void setCount() {
        treatment_list_string.clear();
        treatment_list_click.clear();
        viewlist.clear();



        //treatment_list_string.add("6/"+getString(R.string.s_EMRGN_000));
        treatment_list_string.add("0/"+getString(R.string.s_EMRGN_001));
        treatment_list_string.add("0/"+getString(R.string.s_EMRGN_002));
        treatment_list_string.add("0/"+getString(R.string.s_EMRGN_003));
        treatment_list_string.add("0/"+getString(R.string.s_EMRGN_004));
        treatment_list_string.add("0/"+getString(R.string.s_EMRGN_005));
        treatment_list_string.add("0/"+getString(R.string.s_EMRGN_006));
        treatment_list_string.add("3/"+getString(R.string.s_EMRGN_007));
        treatment_list_string.add("3/"+getString(R.string.s_EMRGN_008));
        treatment_list_string.add("3/"+getString(R.string.s_EMRGN_009));
        treatment_list_string.add("3/"+getString(R.string.s_EMRGN_010));
        treatment_list_string.add("0/"+getString(R.string.s_EMRGN_011));
        treatment_list_string.add("0/"+getString(R.string.s_EMRGN_012));
        treatment_list_string.add("0/"+getString(R.string.s_EMRGN_013));
        treatment_list_string.add("0/"+getString(R.string.s_EMRGN_014));
        treatment_list_string.add("0/"+getString(R.string.s_EMRGN_015));
        treatment_list_string.add("0/"+getString(R.string.s_EMRGN_016));
        treatment_list_string.add("0/"+getString(R.string.s_EMRGN_017));
        treatment_list_string.add("0/"+getString(R.string.s_EMRGN_018));
        treatment_list_string.add("4/"+getString(R.string.s_medical_10));




        for (int i = 0; i < treatment_list_string.size(); i++) {
            treatment_list_click.add(false);
            setMyAdapter(i);
        }
        emrgnlist.setAdapter(new MyListAdapter(viewlist));
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
        final CheckBox cb_style4 = (CheckBox) vi.findViewById(R.id.cb_style4);
        final EditText ed_style = (EditText) vi.findViewById(R.id.ed_style);
        final EditText ed_style_1 = (EditText) vi.findViewById(R.id.ed_style_1);
        final EditText ed_style_5 = (EditText) vi.findViewById(R.id.ed_style_5);
        TextView tv_style = (TextView) vi.findViewById(R.id.tv_style);
        TextView tv_style_1 = (TextView) vi.findViewById(R.id.tv_style_1);
        TextView tv_style_1_2 = (TextView) vi.findViewById(R.id.tv_style_1_2);
//        TextView tv_style_2 = (TextView) vi.findViewById(R.id.tv_style_2);
//        TextView tv_style_3 = (TextView) vi.findViewById(R.id.tv_style_3);
        cb_style.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                treatment_list_click.set(position, isChecked);
            }
        });
        cb_style4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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

                if(cb_style.getText().equals(getString(R.string.s_EMRGN_006))
                        ||cb_style.getText().equals(getString(R.string.s_EMRGN_020))
                        ||cb_style.getText().equals(getString(R.string.s_EMRGN_026))){
                    cb_style.setVisibility(View.GONE);
                    ed_style.setVisibility(View.GONE);
                    tv_style.setText(msg.substring(2).substring(0, msg.substring(2).indexOf('|')));
                }else {
                    ed_style.setVisibility(View.GONE);
                    tv_style.setVisibility(View.GONE);
                }
                row_1.setVisibility(View.GONE);
                row_2.setVisibility(View.GONE);
                row_3.setVisibility(View.GONE);
                row_4.setVisibility(View.GONE);
                row_5.setVisibility(View.GONE);
                break;
            case 1:
                String m0 = msg.substring(2).substring(0, msg.substring(2).indexOf('|'));
                String m1 = msg.substring(3 + m0.length()).substring(0, msg.substring(3 + m0.length()).indexOf('|'));
                cb_style.setText(m0);
                ed_style.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void afterTextChanged(Editable s) {
                    }

                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        CheckBox cb = (CheckBox) viewlist.get(position).findViewById(R.id.cb_style);
                        if (ed_style.getText().toString().length() > 0) {
                            cb.setChecked(true);
                        } else {
                            cb.setChecked(false);
                        }
                    }
                });
                tv_style.setText(m1);

                row_1.setVisibility(View.GONE);
                row_2.setVisibility(View.GONE);
                row_3.setVisibility(View.GONE);
                row_4.setVisibility(View.GONE);
                row_5.setVisibility(View.GONE);
                break;
            case 2:
                cb_style.setText(msg.substring(2).substring(0, msg.substring(2).indexOf('|')));
                if(cb_style.getText().equals(getString(R.string.s_EMRGN_031))){
                    tv_style.setText("εΊ¦");
                    tv_style_1.setText("            ");
                    tv_style_1_2.setText("%");
                }
                ed_style.setFilters(new InputFilter[] { new InputFilter.LengthFilter(3) });
                ed_style.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);
                ed_style.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void afterTextChanged(Editable s) {
                    }

                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        CheckBox cb = (CheckBox) viewlist.get(position).findViewById(R.id.cb_style);
                        if (ed_style.getText().toString().length() > 0) {
                            cb.setChecked(true);
                        } else {
                            cb.setChecked(false);
                        }
                    }
                });
                ed_style_1.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void afterTextChanged(Editable s) {
                    }

                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        CheckBox cb = (CheckBox) viewlist.get(position).findViewById(R.id.cb_style);
                        if (ed_style_1.getText().toString().length() > 0) {
                            cb.setChecked(true);
                        } else {
                            cb.setChecked(false);
                        }
                    }
                });

                row_2.setVisibility(View.GONE);
                row_3.setVisibility(View.GONE);
                row_4.setVisibility(View.GONE);
                row_5.setVisibility(View.GONE);
                break;
            case 3:
                cb_style4.setText(msg.substring(2).substring(0, msg.substring(2).indexOf('|')));


                row_0.setVisibility(View.GONE);
                row_1.setVisibility(View.GONE);
                row_2.setVisibility(View.GONE);
                row_3.setVisibility(View.GONE);
                row_5.setVisibility(View.GONE);
                break;


            case 4: //2019-08-19
                String m2 = msg.substring(2).substring(0, msg.substring(2).indexOf('|'));
//                String m1 = msg.substring(3 + m0.length()).substring(0, msg.substring(3 + m0.length()).indexOf('|'));
                cb_style_5.setText(m2);
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
            case 6:
                // title
                row_0.setVisibility(View.GONE);
                tv_style_1.setVisibility(View.GONE);
                ed_style_1.setVisibility(View.GONE);
                tv_style_1_2.setText(msg.substring(2).substring(0, msg.substring(2).indexOf('|')));
                tv_style_1_2.setTextSize(30);

                tv_style_1_2.setTextColor(Color.DKGRAY);
                row_2.setVisibility(View.GONE);
                row_3.setVisibility(View.GONE);
                row_4.setVisibility(View.GONE);
                row_5.setVisibility(View.GONE);
                break;
        }
        viewlist.add(vi);
    }

    public void save() {
        MainActivity.helpn = "";
        ArrayList<String> tmp_title = new ArrayList<String>();
        ArrayList<String> tmp_string = new ArrayList<String>();
        for (int i = 0; i < treatment_list_click.size(); i++) {
            String helpn = "";
            if (treatment_list_click.get(i)) {
                int type = Integer.parseInt(treatment_list_string.get(i).substring(0, 1));
                helpn += type + "" + i + "/";
                switch (type){
                    case 0:
                        helpn += "#";
                        break;
                    case 3:
                        helpn += "#";
                        break;
                    case 4:
                        helpn += getViewString_0(i)+"#";
                        break;
                }
                MainActivity.helpn += helpn;
                switch (i) {
                    case 0:
                        tmp_string.add(getString(R.string.s_EMRGN_001));
                        break;
                    case 1:
                        tmp_string.add(getString(R.string.s_EMRGN_002));
                        break;
                    case 2:
                        tmp_string.add(getString(R.string.s_EMRGN_003));
                        break;
                    case 3:
                        tmp_string.add(getString(R.string.s_EMRGN_004));
                        break;
                    case 4:
                        tmp_string.add(getString(R.string.s_EMRGN_005));
                        break;
                    case 5:
                        tmp_string.add(getString(R.string.s_EMRGN_006));
                        break;
                    case 6:
                        tmp_string.add(getString(R.string.s_EMRGN_006)+"("+getString(R.string.s_EMRGN_007)+")");
                        break;
                    case 7:
                        tmp_string.add(getString(R.string.s_EMRGN_006)+"("+getString(R.string.s_EMRGN_008)+")");
                        break;
                    case 8:
                        tmp_string.add(getString(R.string.s_EMRGN_006)+"("+getString(R.string.s_EMRGN_009)+")");
                        break;
                    case 9:
                        tmp_string.add(getString(R.string.s_EMRGN_006)+"("+getString(R.string.s_EMRGN_010)+")");
                        break;
                    case 10:
                        tmp_string.add(getString(R.string.s_EMRGN_011));
                        break;
                    case 11:
                        tmp_string.add(getString(R.string.s_EMRGN_012));
                        break;
                    case 12:
                        tmp_string.add(getString(R.string.s_EMRGN_013));
                        break;
                    case 13:
                        tmp_string.add(getString(R.string.s_EMRGN_014));
                        break;
                    case 14:
                        tmp_string.add(getString(R.string.s_EMRGN_015));
                        break;
                    case 15:
                        tmp_string.add(getString(R.string.s_EMRGN_016));
                        break;
                    case 16:
                        tmp_string.add(getString(R.string.s_EMRGN_017));
                        break;
                    case 17:
                        tmp_string.add(getString(R.string.s_EMRGN_018));
                        break;
                    case 18:
                        tmp_string.add(getString(R.string.s_medical_101)+getViewString_0(i));
                        break;
                }
            }
        }
        getListString(tmp_title, tmp_string);
        tmp_string.clear();
        if(MainActivity.link){
            MainActivity.out("HPN/"+MainActivity.helpn+"|");
        }
    }

    private void getListString(ArrayList<String> T, ArrayList<String> S) {
        if (S.size() > 0) {
            boolean data = false;
            String HelpN="", Mediacal, HelpY;
            if (MainActivity.main5_data.size() > 0) {
                HelpY = MainActivity.main5_data.get(1);
                Mediacal = MainActivity.main5_data.get(2);
                MainActivity.main5_data.clear();
                MainActivity.main5_data.add("");
                MainActivity.main5_data.add(HelpY);
                MainActivity.main5_data.add(Mediacal);
                data = true;
            } else {
                HelpN = "";
                MainActivity.main5_data.add("");
                MainActivity.main5_data.add("");
                MainActivity.main5_data.add("");
            }
            for (int i = 0; i < S.size(); i++) {
                if (i == 0) {
                    if (data) {
                        HelpN += S.get(i) + "\n";
                    } else {
                        HelpN += S.get(i) + "\n";
                    }
                } else {
                    HelpN += S.get(i) + "\n";
                }
            }
//            other += ")";
            MainActivity.main5_data.set(0,HelpN);
        }else{
            if (MainActivity.main5_data.size() > 0){
                MainActivity.main5_data.set(0,"");
            }else {
                MainActivity.main5_data.add("");
                MainActivity.main5_data.add("");
                MainActivity.main5_data.add("");
            }
            MainActivity.HelpN_TextView.setText(getString(R.string.s_ts_63));
        }
    }

    private String getViewString_0(int i) {
        EditText ed_style = (EditText) viewlist.get(i).findViewById(R.id.ed_style_5);
        return ed_style.getText().toString();
    }
}
