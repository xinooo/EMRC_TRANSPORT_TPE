package com.emrc_transport_tpe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Collections;

public class MyListAdapter2 extends BaseAdapter {

	private Context context;
	private boolean db;
	public static ArrayList<String> aListView = new ArrayList<String>();

	MyListAdapter2(Context context, ArrayList<String> list,boolean b) {
		this.context = context;
		this.db = b;
		aListView.clear();
		for (int i = 0; i < list.size(); i++) {
			aListView.add(list.get(i));
		}
		if(db == false){
			Collections.reverse(aListView);
		}
	}

	public int getCount() {
		return aListView.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	private View setView(int position, View convertView) {
		String msg = aListView.get(position);
		if(position==0&&db){
			int resource = R.layout.style_textview4;
			if (MainActivity.EN) {
				resource = R.layout.style_textview6;
			}
			convertView = LayoutInflater.from(context).inflate(resource, null);
			TextView h_red = (TextView) convertView.findViewById(R.id.tv_h_red);
			TextView h_yellow = (TextView) convertView.findViewById(R.id.tv_h_yellow);
			TextView h_green = (TextView) convertView.findViewById(R.id.tv_h_green);
			TextView h_black = (TextView) convertView.findViewById(R.id.tv_h_black);
			TextView s_red = (TextView) convertView.findViewById(R.id.tv_s_red);
			TextView s_yellow = (TextView) convertView.findViewById(R.id.tv_s_yellow);
			TextView s_green = (TextView) convertView.findViewById(R.id.tv_s_green);
			TextView s_black = (TextView) convertView.findViewById(R.id.tv_s_black);
			// 17030600001/1/五身份證/5/ 其他處置 (保暖).|║0=n/║0=n/║n/║2/32/10/A1267767/
			int r = 0;
			String hr = getLine(msg, r, '/');
			r += hr.length() + 1;
			String hy = getLine(msg, r, '/');
			r += hy.length() + 1;
			String hg = getLine(msg, r, '/');
			r += hg.length() + 1;
			String hb = getLine(msg, r, '/');
			r += hb.length() + 1;
			String sr = getLine(msg, r, '/');
			r += sr.length() + 1;
			String sy = getLine(msg, r, '/');
			r += sy.length() + 1;
			String sg = getLine(msg, r, '/');
			r += sg.length() + 1;
			String sb = getLine(msg, r, '/');

			h_red.setText(hr);
			h_yellow.setText(hy);
			h_green.setText(hg);
			h_black.setText(hb);
			s_red.setText(sr);
			s_yellow.setText(sy);
			s_green.setText(sg);
			s_black.setText(sb);

		}else{
			convertView = LayoutInflater.from(context).inflate( R.layout.style_textview3, null);
			ImageView v = (ImageView) convertView.findViewById(R.id.t3_image);
			RelativeLayout t_bg = (RelativeLayout) convertView.findViewById(R.id.t3_relativeLayout);
			TextView t_name = (TextView) convertView.findViewById(R.id.t3_name);
			TextView t_hosp = (TextView) convertView.findViewById(R.id.t3_hospital);
			TextView t_other = (TextView) convertView.findViewById(R.id.t3_other);
			int r = 0;
			String number = getLine(msg, r, '/');
			r += number.length() + 3;
			String name = getLine(msg, r, '/');
			r += name.length() + 1;
			String age = getLine(msg, r, '/');
			r += age.length() + 1;
			String other = getLine(msg, r, '║');
			r += other.length() + 2;
			String inju_front = getLine(msg, r, '║');
			r += inju_front.length() + 1;
			String inju_back = getLine(msg, r, '║');
			r += inju_back.length() + 1;
			String vita_date = getLine(msg, r, '║');
			r += vita_date.length() + 1;
			int leve_count = Integer.parseInt(getLine(msg, r, '/'));
			r += 2;
			int emrc_count = Integer.parseInt(getLine(msg, r, '/'));
			r += (emrc_count + "").length() + 1;
			String hosp = getLine(msg, r, '/');
			if (hosp.length() != 0) {
				try {
					if (Integer.parseInt(hosp) == 0) {
						hosp = context.getResources().getString(R.string.s_List_38);
						setFlickerAnimation(v);
					} else {
						hosp = MainActivity.Treatment_unit.get(Integer.parseInt(hosp)).unit;
						v.setVisibility(View.GONE);
					}
				} catch (Exception e) {
					hosp = MainActivity.Treatment_unit.get(getTrainsportSelection(hosp)).unit;
					v.setVisibility(View.GONE);
				}
			} else {
				hosp = context.getResources().getString(R.string.s_List_38);
				setFlickerAnimation(v);
			}
			if (name.length() == 0) {
				t_name.setText(number);
			} else {
				t_name.setText(name);
			}
			t_hosp.setText(hosp);
			if (db) {
				// 去除標號
				other = getLine(other, 0, '|');
			}
			other += MainActivity.getEMRC(context, emrc_count, leve_count);
			t_other.setText(other);
			switch (leve_count + "") {
				case "0":
					t_name.setTextColor(Color.WHITE);
					t_hosp.setTextColor(Color.WHITE);
					t_other.setTextColor(Color.WHITE);
					t_bg.setBackgroundColor(Color.BLACK);
					break;
				case "1":
					t_name.setTextColor(Color.WHITE);
					t_hosp.setTextColor(Color.WHITE);
					t_other.setTextColor(Color.WHITE);
					t_bg.setBackgroundColor(Color.RED);
					break;
				case "2":
					t_bg.setBackgroundColor(Color.YELLOW);
					break;
				case "3":
					t_bg.setBackgroundColor(Color.GREEN);
					break;
				default:
					t_name.setTextColor(Color.BLACK);
					t_hosp.setTextColor(Color.BLACK);
					t_other.setTextColor(Color.BLACK);
					t_bg.setBackgroundColor(Color.WHITE);
					break;
			}
		}
		return convertView;
	}


	@SuppressLint("InflateParams")
	public View getView(int position, View convertView, ViewGroup parent) {
		return setView(position, convertView);
	}

	//圖片閃爍
	public  void setFlickerAnimation(ImageView iv_chat_head) {
		Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
		animation.setDuration(500); // duration - half a second
		animation.setRepeatCount(Animation.INFINITE); // Repeat animation infinitely
		animation.setRepeatMode(Animation.REVERSE); //
		iv_chat_head.setAnimation(animation);
	}

	public static String getLine(String msg, int run, char key) {
		return msg.substring(run).substring(0, msg.substring(run).indexOf(key));
	}

	public static int getTrainsportSelection(String area) {
		int s = 0;
		for (int i = 0; i < MainActivity.Treatment_unit.size(); i++) {
			if (MainActivity.Treatment_unit.get(i).area.equals(area)) {
				s = i;
				break;
			}
		}
		return s;
	}
}