package com.emrc_transport_tpe;


import android.annotation.SuppressLint;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.*;


import com.zxing.activity.CaptureActivity;

@SuppressLint("NewApi")
public class InformationActivity extends Activity implements OnClickListener, OnCheckedChangeListener {
	// private Uri image_uri;
	public static ImageView info_photo = null;
	private boolean img = false;
	private int gender;
	private RadioGroup info_gender;
	private RadioButton maleBtn, femalBtn;
	private EditText info_name, info_age, info_identity, info_other;
	private TextView info_no_top, info_no_down;
	private ImageView ok, bt_identity;
	private CheckBox info_cb;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_0_info);
		findViewById();
		load();
		info_other.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
		info_other.setGravity(Gravity.TOP);
		info_other.setSingleLine(false);
		info_other.setHorizontallyScrolling(false);
	}

	protected void onStart() {
		super.onStart();
	}

	protected void onPause() {
		super.onPause();
	}

	protected void onResume() {
		super.onResume();
	}

	protected void onDestroy() {
		super.onDestroy();
	}

	private void findViewById() {
		info_cb = (CheckBox) findViewById(R.id.info_cb);
		info_name = (EditText) findViewById(R.id.info_name);
		info_age = (EditText) findViewById(R.id.info_age);
		info_other = (EditText) findViewById(R.id.info_other);
		info_identity = (EditText) findViewById(R.id.info_identity);
		info_photo = (ImageView) findViewById(R.id.info_photo);
		bt_identity = (ImageView) findViewById(R.id.bt_identity);
		info_gender = (RadioGroup) findViewById(R.id.info_gender);
		info_no_top = (TextView) findViewById(R.id.info_no_top);
		info_no_down = (TextView) findViewById(R.id.info_no_down);
		maleBtn = (RadioButton) findViewById(R.id.maleBtn);
		femalBtn = (RadioButton) findViewById(R.id.femalBtn);
		ok = (ImageView) findViewById(R.id.info_check);
		info_cb.setOnClickListener(this);
		bt_identity.setOnClickListener(this);
		info_photo.setOnClickListener(this);
		ok.setOnClickListener(this);
		info_gender.setOnCheckedChangeListener(this);
		if (MainActivity.EN) {
			TextView info_name_t = (TextView) findViewById(R.id.info_name_t);
			TextView info_age_t = (TextView) findViewById(R.id.info_age_t);
			TextView info_gender_t = (TextView) findViewById(R.id.info_gender_t);
			TextView info_identity_t = (TextView) findViewById(R.id.info_identity_t);
			TextView info_other_t = (TextView) findViewById(R.id.info_other_t);
			info_cb.setTextSize(15);
			info_name_t.setTextSize(20);
			info_age_t.setTextSize(20);
			info_gender_t.setTextSize(20);
			info_identity_t.setTextSize(20);
			info_other_t.setTextSize(20);
			info_other.setTextSize(20);
		}
	}

	private void load() {
		if (MainActivity.info_photo != null) {
			info_photo.setBackgroundResource(R.drawable.item_background);
			info_photo.setImageBitmap(MainActivity.info_photo);
			img = true;
		}
		gender = MainActivity.gender;
		switch (gender) {
		case 0:
			maleBtn.setChecked(false);
			femalBtn.setChecked(true);
			break;
		case 1:
			maleBtn.setChecked(true);
			femalBtn.setChecked(false);
			break;
		case 2:
			info_cb.setChecked(true);
			break;
		}
		if (MainActivity.info_data.size() > 0) {
			info_name.setText(MainActivity.info_data.get(0));
			info_age.setText(MainActivity.info_data.get(1));
			info_other.setText(MainActivity.info_data.get(2));
			if (info_other.getText().toString().length() > 10) {
				info_other.setTextSize(20);
			}
			MainActivity.info_data.clear();
		}
		if ((MainActivity.identity + "").length() > 1) {
			info_identity.setText(MainActivity.identity);
		}
		String number = MainActivity.number;
		if (number.length() > 2) {
			info_no_top.setText(number);
			info_no_down.setText(number);
		}
	}

	private Bitmap drawableToBitmap(Drawable drawable) {
		// 取 drawable 的長寬
		int w = drawable.getIntrinsicWidth();
		int h = drawable.getIntrinsicHeight();
		// 取 drawable 的顏色格式
		Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565;
		// 建立對應 bitmap
		Bitmap bitmap = Bitmap.createBitmap(w, h, config);
		// 建立對應 bitmap 的畫布
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, w, h);
		// 把 drawable 內容畫到畫布中
		drawable.draw(canvas);
		return bitmap;
	}

	private void save() {
		int un = 0;
		String name = info_name.getText().toString();
		String age = info_age.getText().toString();
		String other = info_other.getText().toString();
		String identity = info_identity.getText().toString();
		MainActivity.identity = identity + "";
		if (info_cb.isChecked()) {
			gender = 2;
			un++;
		}
		MainActivity.gender = gender;
		if (img) {
			MainActivity.info_photo = drawableToBitmap(info_photo.getDrawable());
		}
		MainActivity.info_data.add(name);
		MainActivity.info_data.add(age);
		MainActivity.info_data.add(other);
		if (name.length() == 0) {
			name = getString(R.string.info_unknow);
			un++;
		}
		if (age.length() == 0) {
			MainActivity.info_age = -1+"";
		}else{
			MainActivity.info_age = age;
		}
		switch (gender) {
		case 0:
			name += "," + getString(R.string.info_femal);
			break;
		case 1:
			name += "," + getString(R.string.info_male);
			break;
		default:
			break;
		}
		if (un != 2) {
			MainActivity.menu_item.set(0, getString(R.string.s_Menu_1) + "\n<" + name + ">");
		}
		MainActivity.menu_upload(this);

		// MainActivity.menu_item.set(0, "基本資料");
		// MainActivity.menu_upload(this);

	}

	private void Dialog(boolean view) {
		if (view) {
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			dialog.setNeutralButton(getString(R.string.s_ts_46), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface arg0, int arg1) {
					startActivity(new Intent(InformationActivity.this, InformationViewActivity.class));
				}
			});
			dialog.setNegativeButton(getString(R.string.s_ts_48), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface arg0, int arg1) {
					Intent intent = new Intent(InformationActivity.this, CameraTwoActivity.class);
					startActivityForResult(intent, 1);
					// Ensure that there's a camera activity to handle the
					// intent
				}
			});
			dialog.show();
		} else {
			Intent intent = new Intent(InformationActivity.this, CameraTwoActivity.class);
			startActivityForResult(intent, 1);
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == 2) {
				// ZXing掃描身分證回傳的內容
				info_identity.setText(data.getStringExtra("result"));
				// 取得Focusable
				info_identity.setFocusable(true);

			} else {
				info_photo.setBackgroundResource(R.drawable.item_background);
				Bitmap bmp = MainActivity.tmp_bmp;
				if (MainActivity.link) {
					MainActivity.out("b0");
					Tools.sandFile(bmp);
				} else {
					Toast.makeText(this, getString(R.string.s_ts_38), Toast.LENGTH_SHORT).show();
				}
				info_photo.setImageBitmap(bmp);
				img = true;
			}
		}
	}

	public void onClick(View v) {
		if (v == info_cb) {
			maleBtn.setChecked(false);
			femalBtn.setChecked(false);
			info_cb.setChecked(true);
		}
		if (v == info_photo) {
			Dialog(img);
		}
		if (v == ok) {
			save();
			finish();
		}
		if (v == bt_identity) {
			Intent intent = new Intent(this, CaptureActivity.class);
			startActivityForResult(intent, 2);
		}
	}

	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.femalBtn:
			gender = 0;
			info_cb.setChecked(false);
			break;
		case R.id.maleBtn:
			gender = 1;
			info_cb.setChecked(false);
			break;
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			save();
			finish();
		}
		return false;
	}
}
