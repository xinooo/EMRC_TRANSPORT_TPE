package com.emrc_transport_tpe;

import java.io.*;
import java.net.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.text.*;
import java.util.*;

import android.*;
import android.annotation.*;
import android.app.*;
import android.content.*;
import android.content.IntentFilter.*;
import android.content.pm.*;
import android.graphics.*;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.*;
import android.location.*;
import android.net.*;
import android.nfc.*;
import android.nfc.tech.*;
import android.os.*;
import android.preference.PreferenceManager;
import android.provider.*;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.*;
import android.text.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.widget.AdapterView.*;
import android.widget.*;
import android.app.Fragment;


import com.emrc_transport.Adler32;
import com.emrc_transport.CheckedDataInput;
import com.emrc_transport.Checksum;
import com.google.zxing.common.StringUtils;
import com.zxing.activity.CaptureActivity;
import com.zxing.camera.CameraManager;


import javax.crypto.Cipher;
import javax.crypto.spec.*;
import javax.net.ssl.*;

@SuppressWarnings("deprecation")
@SuppressLint("HandlerLeak")
public class MainActivity extends Activity implements OnClickListener, OnItemClickListener, OnItemLongClickListener,
		OnItemSelectedListener, OnTouchListener, LocationListener, OnPageChangeListener {
	
	public static String ip = "120.119.155.80";
//	public static String ip = "192.168.1.189";
	public static int port = 1195;
	private int id_Y = 3019, id_M = 11, id_D = 20;
	
	private int downX = 0, downY = 0;
	private boolean write = false, nfc = false, gps = false, car = false;
	private boolean home = false, sktRun = false, dblist = false;
	private double Lngitude, Latitude;
	private static final String TAG = "nfcproject";
	private static Spinner spinner;
	private static TextView medi_unit, medi_qunt, print_No, menu_No;
//	private static ViewPager pager1;
	private static ViewPager pager1;
	private AlertDialog alertDialog;
	private Button main_log, mediCar_btn0, mediCar_btn1, mediCar_btn2, mediCar_btn3;
	private Button mediCar_btn4, mediCar_btn5, mediCar_btn6, mediCar_btn7, mediCar_btn8;
	private Button mediCar_btn9, mediCar_btn10, mediCar_btn11;
	private EditText mediCar_unit, mediCar_brand;
	private HomeListen mHomeListen = null;
	private Handler SMSHandler = new Handler();
	private Handler GPSHandler = new Handler();
	private IntentFilter[] gNdefExchangeFilters, gWriteTagFilters;
	private ImageView main_left, main_right, medi_clear, medi_swap, menu_ok,menu_re, bg_emrc;
	// private TableRow btn_USER, btn_MASTER, btn_CAR;
	private LocationManager lms;
	private NfcAdapter nfcAdapter;
	private PendingIntent gNfcPendingIntent;
	private ProgressDialog mProgressDialog;
	private String bestProvider;
	private TextView print_item_0, print_item_1, print_item_2, print_item_3;
	private TextView print_item_4, print_item_5, print_item_6, mediCar_static;
	private TextWatcher textWatcher;
	// private ArrayList<Boolean> Car_trends = new ArrayList<Boolean>(); // 已回報
	private static final String KEY_STORE_CLIENT_PATH = "kclient.bks"; // 客户端要给服务器端认证的证书.
	private static final String KEY_STORE_TRUST_PATH = "tclient.bks"; // 客户端验证服务器端的证书库
	private static final String KEY_STORE_PASSWORD = "123456"; // 客户端证书密码
	public static int gender = 2, leve_count = 5, phot_count = 0,dev_count = 20;
	public static int emrc_count = 0, status_count = 0, textSize = 0,inc_code = 0,spinner_inc_code=0;
	public static boolean net = false, link = false, user = false, pin = false, f = true, EN = false,dev = false;
	public static String mmsg, handmsg, number = "", car_brand = "", identity = "", hosp_count = "",info_age="-1";
	public static Socket skt;
	public static Bitmap info_photo = null, tmp_bmp = null;
	private static ListView menu_list, medi_list;

	public static DisplayMetrics metrics = new DisplayMetrics();
	public static ArrayList<String> menu_item = new ArrayList<String>();

	public static ArrayList<String> medi_item = new ArrayList<String>();
	private static ArrayList<String> lead_item = new ArrayList<String>();
	private static ArrayList<View> main_view = new ArrayList<View>();
	private static ArrayList<View> menu_view = new ArrayList<View>();
	private static ArrayList<View> medi_view = new ArrayList<View>();
	private static ArrayList<View> lead_view = new ArrayList<View>();

	public static ArrayList<Double> inju_front = new ArrayList<Double>();
	public static ArrayList<Double> inju_back = new ArrayList<Double>();
	public static ArrayList<Double> inju_target = new ArrayList<Double>();
	public static ArrayList<String> info_data = new ArrayList<String>();
	public static ArrayList<String> vita_data = new ArrayList<String>();
	public static ArrayList<String> emrgn_data = new ArrayList<String>();
	public static ArrayList<Bitmap> phot_bitmap = new ArrayList<Bitmap>();
	public static ArrayList<Bitmap> sign_bitmap = new ArrayList<Bitmap>();

	public static ArrayList<hospital> Treatment_unit = new ArrayList<hospital>();



	private RelativeLayout main_load;
	public static String emrgn="",helpy="null",helpn="null",medical="null",ambu_reason_txt = "",emrgn_txt="";
	private ImageView sign0, sign1, sign5, sign6, sign7, sign2, sign3, sign4, allergy0,check;
	private Paint paint;//聲明畫筆
	private Bitmap mSignBitmap;
	private static int decide_help=0,decide_emrgn=0,ambu_reason1 = 0,ambu_reason2 = 0;
	private ImageView main_6_ok,main_6_up;
	private ImageView main_5_ok,main_5_up;
	private ImageView main5_photo;
	private ImageView body_front;
	private ImageView body_back;
	public static ImageView main5_info_photo = null;
	private static ImageView body_front_view;
	private static ImageView body_back_view;
	private static Spinner spinner_main5_gender, spinner_hos3,spinner_hos,spinner_hos2,incident_spinner;
	public static EditText main_6_other,ambu_guide;
	private static EditText accident_car1,accident_car2,accident_car3,accident_car4,allergy1,allergy2,allergy3;
	private LinearLayout HelpN_LinearLayout,HelpY_LinearLayout,Mediacal_LinearLayout,LinearLayout_inju,LinearLayout_emrgn;
	private static TextView main5_level,help_No,TextView_emrgn,emrc_t0,emrc_t1,emrc_t2,emrc_t3,emrc_t4,emrc_t5,emrc_date,main5_Car_unit;
	public static TextView HelpN_TextView,HelpY_TextView,Mediacal_TextView,photo_num;
	public static ArrayList<String> emrc_time = new ArrayList<String>();
	private EditText main5_name, main5_age, main5_identity,ambu_property,ambu_complaint,ambu_place1,ambu_place2;
	public static String ambu_date = "";
	private boolean img = false,allergy_check=false;
	private int db_allergy0=0,db_ambu_complaint0=0,db_ambu_complaint1=0,db_ambu_complaint2=0,db_ambu_complaint3=0,db_ambu_accepted=0;
	private String db_allergy1="",db_allergy2="",db_allergy3="",db_accident_car1="",db_accident_car2="",db_accident_car3="",db_accident_car4="";
	private String db_ambu_complaint="",db_ambu_place1="",db_ambu_place2="",db_ambu_property="",db_ambu_guide="";
	private Uri image_uri;
	private CheckBox ambu_complaint0,ambu_complaint1,ambu_complaint2,ambu_complaint3,ambu_accepted1,ambu_accepted2,ambu_accepted3;
	private static ArrayList<String> Reason_unit = new ArrayList<String>();
	private static ArrayList<String> Gender_unit = new ArrayList<String>();
	private static ArrayList<String> Hos_unit = new ArrayList<String>();
	public static ArrayList<Double> inju_body_front = new ArrayList<Double>();
	public static ArrayList<Double> inju_body_back = new ArrayList<Double>();
	public static ArrayList<String> main5_data = new ArrayList<String>();
	int[] cars_tring ={R.string.s_Menu_7, R.string.s_Menu_8,R.string.s_Menu_9,R.string.s_Menu_10,
					R.string.s_Menu_11,R.string.s_Menu_12,R.string.s_Menu_13,R.string.s_Menu_14};
	public static ArrayList<String> incident_unit = new ArrayList<String>();
	public static ArrayList<String> incident_code = new ArrayList<String>();



	public static String QRcode="",qr_msg="",h="";
	private boolean readNFC = false;
	private boolean Car_btn0 = true,Car_btn1 = true,Car_btn2 = true,Car_btn3 = true,Car_btn4 = true,Car_btn5 = true,Car_btn6 = true,Car_btn7 = true;
	private int MH=0;

	SharedPreferences sharedPreferences;

	FragmentManager fragmentManager;
	FragmentTransaction beginTransaction;
	HelpYFragment fragmentY = new HelpYFragment();
	HelpNFragment fragmentN = new HelpNFragment();
	EmrgnFragment fragmentE = new EmrgnFragment();
	MedicalFragment fragmentM = new MedicalFragment();
	InjuredFragment fragmentI = new InjuredFragment();
	VitalFragment fragmentV = new VitalFragment();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		metrics = getResources().getDisplayMetrics();
		findViewById();
		checkToday(0, 0, 0, false);
		addTab();
		for(int i=0;i<8;i++){
			emrc_time.add(getString(R.string.vita_time_hint));
		}
//		view_0();
		view_1();
		view_2();
		view_3();
		view_5();
		view_6();
		setTARGET();
//		setNFC();
		nfc = true;
		setNET();
		setHOME();
		load_USER_DATA();
		// ==================================
		// 直接開啟救護車模式
		pager_upload();
		// for (int cc = 0; cc < 6; cc++) {
		// Car_trends.add(false);
		// }
		PPHandler.obtainMessage().sendToTarget();
		// ==================================
		setPermission();

		pager1.setCurrentItem(2);
	}

	protected void onStart() {
		super.onStart();
	}

	protected void onStop() {
		super.onStop();
	}

	protected void onPause() {
		super.onPause();
		if (home) {
			mHomeListen.stop();
		}
//		if (nfc) {
//			// 由於NfcAdapter啟動前景模式將相對花費更多的電力，要記得關閉。
//			nfcAdapter.disableForegroundNdefPush(this);
//		}
		if (gps) {
			lms.removeUpdates(this); // 離開頁面時停止更新
		}
	}

	@SuppressLint("MissingPermission")
	protected void onResume() {
		super.onResume();
		if (home) {
			mHomeListen.start();
		}
		if (nfc) {
			// TODO 處理由Android系統送出應用程式處理的intent filter內容
			if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
				NdefMessage[] messages = getNdefMessages(getIntent());
				String data = Decrypt(new String(messages[0].getRecords()[0].getPayload()));
				// 往下送出該intent給其他的處理對象
				setIntent(new Intent());
				String msg = "";
				if (checkData(data)) {
					msg = Decrypt(data);
					// toast("De", this);
				} else {
					msg = data;
					// toast(msg, this);
//					toast(getString(R.string.s_ts_57), this);
				}
				if (msg.length() > 0) {
					final String finalMsg = msg;
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							getNFCString(finalMsg);
							load_data();
						}
					}, 1000);

					// pager_upload(this);
					// pager.setCurrentItem(1);
					// USHandler.obtainMessage().sendToTarget();
				} else {
					toast(getString(R.string.s_ts_0), this);
				}
				// toast("onResume", this);
			}
			// 啟動前景模式支持Nfc intent處理
//			enableNdefExchangeMode();
		}
		if (gps) {
			lms.requestLocationUpdates(bestProvider, 60000, 1, this);
			// 服務提供者、更新頻率60000毫秒=1分鐘、最短距離、地點改變時呼叫物件
		}
	}

	protected void onDestroy() {
		super.onDestroy();
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			save();

			new AlertDialog.Builder(this).setTitle(getString(R.string.s_ts_61))
					.setPositiveButton(getString(R.string.s_ts_12), new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {

						}
					}).setNegativeButton(getString(R.string.s_ts_13), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface arg0, int arg1) {
					android.os.Process.killProcess(android.os.Process.myPid());
				}
			}).show();
			return true;
		default:
			break;
		}
		return false;
	}
	public static int getTrainsportSelection(String area) {
		int s = 0;
		for (int i = 0; i < Treatment_unit.size(); i++) {
			if (Treatment_unit.get(i).area.equals(area)) {
				s = i;
				break;
			}
		}
		return s;
	}

	private void setHOME() {
		home = true;
		mHomeListen = new HomeListen(this);
		mHomeListen.setOnHomeBtnPressListener(new HomeListen.OnHomeBtnPressLitener() {
			public void onHomeBtnPress() {
				// toast(MainActivity.this, "按下Home按键！");
				save();
				android.os.Process.killProcess(android.os.Process.myPid());

			}

			public void onHomeBtnLongPress() {
				// toast(MainActivity.this, "长按Home按键！");
				save();
				android.os.Process.killProcess(android.os.Process.myPid());

			}
		});
	}

	@TargetApi(23)
	private void setPermission() {
		if (Build.VERSION.SDK_INT >= 23) {
			int PHONE = checkSelfPermission(Manifest.permission.CALL_PHONE);
			int LOCATION = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
			int STORAGE = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);

			if (permission(PHONE) || permission(LOCATION) || permission(STORAGE)) {
				showMessageOKCancel("親愛的用戶您好:\n由於Android 6.0 以上的版本在權限上有些更動，我們需要您授權以下的權限，感謝。",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								showPermission();
							}
						});
			}else {
				// 掃QRcode
//				if(!readNFC){
//					startActivityForResult(new Intent(MainActivity.this, CaptureActivity.class), 2);
//				}
			}
		}
	}

	private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
		new AlertDialog.Builder(this).setMessage(message).setPositiveButton("OK", okListener).setCancelable(false)
				.create().show();
	}

	@TargetApi(23)
	@SuppressLint("NewApi")
	private void showPermission() {
		// We don't have permission so prompt the user
		List<String> permissions = new ArrayList<String>();
		permissions.add(Manifest.permission.CALL_PHONE);
		permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
		permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
		permissions.add(Manifest.permission.CAMERA);
		requestPermissions(permissions.toArray(new String[permissions.size()]), 0);
	}

	@TargetApi(23)
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
			// 許可授權
			// 掃QRcode
//			if(!readNFC){
//				startActivityForResult(new Intent(MainActivity.this, CaptureActivity.class), 2);
//			}
		} else {
			// 沒有權限
			toast(getString(R.string.s_ts_58), this);
			showPermission();
			// new Timer(true).schedule(new TimerTask() {
			// public void run() {
			// finish();
			// }
			// }, 3000);

		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

			super.onActivityResult(requestCode, resultCode, data);
			if (resultCode == RESULT_OK) {
				if(requestCode == 0){
					showPermission();
				}
				if (requestCode == 1) {
					main5_info_photo.setBackgroundResource(R.drawable.item_background);
					Bitmap bmp = MainActivity.tmp_bmp;
					if (MainActivity.link) {
						MainActivity.out("b0");
						Tools.sandFile(bmp);
					} else {
						Toast.makeText(this, getString(R.string.s_ts_38), Toast.LENGTH_SHORT).show();
					}
					main5_info_photo.setImageBitmap(bmp);
					img = true;
				}
				if (requestCode == 2) {
					// ZXing掃描身分證回傳的內容
//					QRcode = QRcode_Decrypt(data.getStringExtra("result"));
					QRcode = data.getStringExtra("result");
					if(link){
						out("QRC/"+QRcode+"|");
					}
				}
			}
	}

	private boolean permission(int mp) {
		return mp != PackageManager.PERMISSION_GRANTED;
	}

	protected void onNewIntent(Intent intent) {
		// TODO 覆寫該Intent用於補捉如果有新的Intent進入時，可以觸發的事件任務。
		if (!write && NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
			NdefMessage[] messages = getNdefMessages(intent);
			String data = Decrypt(new String(messages[0].getRecords()[0].getPayload()));
			String msg = "";
			if (checkData(data)) {
				msg = Decrypt(data);
			} else {
				msg = data;
//				toast(getString(R.string.s_ts_57), this);
			}
			if (msg.length() > 0) {
				readNFC = true;
				Log.e("123",msg);
				getNFCString(msg);
				load_data();
			} else {
				toast(getString(R.string.s_ts_0), this);
			}
		}

		// 監測到有指定ACTION進入，代表要寫入資料至Tag中。
		if (write && NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
			Tag detectedTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
			writeTag(getNoteAsNdef(), detectedTag);
		}
	}

	private NdefMessage[] getNdefMessages(Intent intent) {
		// Parse the intent
		NdefMessage[] msgs = null;
		String action = intent.getAction();
		if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action) || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
			Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
			if (rawMsgs != null) {
				msgs = new NdefMessage[rawMsgs.length];
				for (int i = 0; i < rawMsgs.length; i++) {
					msgs[i] = (NdefMessage) rawMsgs[i];
				}
			} else {
				// Unknown tag type
				byte[] empty = new byte[] {};
				NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN, empty, empty, empty);
				NdefMessage msg = new NdefMessage(new NdefRecord[] { record });
				msgs = new NdefMessage[] { msg };
			}
		} else {
			Log.d(TAG, "Unknown intent.");
			finish();
		}
		return msgs;
	}

	private String getText(EditText ET) {
		return ET.getText().toString();
	}

	private void save() {
		String File = "", Date = "";
		if (user) {
			File = "DATA.txt";
			if (medi_item.size() > 0) {
				Date = medi_item.size() + "=";
				for (int i = 0; i < medi_item.size(); i++) {
					Date += medi_item.get(i);
				}
			}
		} else {
			File = "USER.txt";
			Date = getText(mediCar_unit) + "|" + getText(mediCar_brand) + "|";
			getFile(File, Date);
			File = "MEDI.txt";
			if (medi_item.size() > 0) {
				Date = medi_item.size() + "=";
				for (int i = 0; i < medi_item.size(); i++) {
					Date += medi_item.get(i);
				}
			} else {
				Date = "";
			}
			/*
			 * MODE_PRIVATE：為預設操作模式，代表該檔是私有資料，只能被應用本身訪問，在該模式下，寫入的內容會覆蓋原檔的內容。
			 * MODE_APPEND：模式會檢查檔是否存在，存在就往檔追加內容，否則就創建新檔。
			 * MODE_WORLD_READABLE和Context.
			 * MODE_WORLD_WRITEABLE用來控制其他應用是否有許可權讀寫該檔。
			 * MODE_WORLD_READABLE：表示當前檔可以被其他應用讀取。
			 * MODE_WORLD_WRITEABLE：表示當前檔可以被其他應用寫入。 如果希望檔被其他應用讀和寫，可以傳入：
			 * Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE
			 */
		}
		getFile(File, Date);
		if (Treatment_unit.size() > 0) {
			File = "HOSP.txt";
			Date = Treatment_unit.size() + "=";
			for (int i = 0; i < Treatment_unit.size(); i++) {
				Date += Treatment_unit.get(i).unit+","+Treatment_unit.get(i).level+","+Treatment_unit.get(i).location+","+Treatment_unit.get(i).area+"|";
			}
		}
		getFile(File, Date);
	}

	private void getFile(String File, String Date) {
		try {
			FileOutputStream outStream = this.openFileOutput(File, MODE_PRIVATE);
			outStream.write(Date.getBytes());
			outStream.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}

	private String getData(String filename) {
		String DATA = "";
		FileInputStream fin = null;
		ByteArrayOutputStream bos = null;
		try {
			fin = this.openFileInput(filename);
			bos = new ByteArrayOutputStream();
			int size;
			size = fin.available();

			byte[] data = new byte[size];
			fin.read(data);
			bos.write(data);
			DATA = bos.toString();
			bos.close();
			fin.close();
		} catch (IOException e) {
		}
		return DATA;
	}

	private void load_USER_DATA() {
		String File = "USER.txt";
		String Date = "", DateTmp;
		int tmp = 0;
		ArrayList<EditText> datelist = new ArrayList<EditText>();
		datelist.add(mediCar_unit);
		datelist.add(mediCar_brand);
			Date = getData(File);
			if (Date.length() > 0) {
				for (int i = 0; i < 2; i++) {
					DateTmp = Date.substring(tmp).substring(0, Date.substring(tmp).indexOf('|'));
					if (!DateTmp.equals("null")) {
						datelist.get(i).setText(DateTmp);
					}
					tmp = tmp + DateTmp.length() + 1;
				}
			}
		String unit = mediCar_unit.getText().toString() + "";
		String brand = mediCar_brand.getText().toString() + "";
		if (unit.length() > 1 && brand.length() > 1) {
			pager1.setCurrentItem(0);
		} else {
			pager1.setCurrentItem(2);
		}
	}

	private void load_MEDI_DATA() {
		String File;
		if (user) {
			File = "DATA.txt";
		} else {
			File = "MEDI.txt";
		}
		String Date = "", DateTmp;
		int tmp = 0, run = 0;

			Date = getData(File);
			if (Date.length() > 0) {
				run = Integer.parseInt(Date.substring(tmp).substring(0, Date.substring(tmp).indexOf('=')));
				tmp += ((run + "").length() + 1);
				for (int i = 0; i < run; i++) {
					DateTmp = Date.substring(tmp + i).substring(0, Date.substring(tmp + i).indexOf('|'));
					medi_item.add(DateTmp + '|');
					tmp += DateTmp.length();
				}
				user_upload();
			}

	}

//	private void load_HOSP_DATA() {
//		String File = "HOSP.txt";
//
//		String Date = "", DateTmp;
//
//		int tmp = 0, run = 0;
//
//		Date = getData(File);
//		if (Date.length() > 0) {
//			run = Integer.parseInt(Date.substring(tmp).substring(0, Date.substring(tmp).indexOf('=')));
//			tmp += ((run + "").length() + 1);
//
//			for (int i = 0; i < run; i++) {
//				DateTmp = Date.substring(tmp + i).substring(0, Date.substring(tmp + i).indexOf('║'));
//				Object[] array = DateTmp.split("|");
//				Treatment_unit.add(new hospital((String)array[0],(Integer)array[1],(String)array[2],(String)array[3]));
//				tmp += DateTmp.length();
//			}
//		}
//
//	}

	private void load_HOSP_DATA() {
		String File = "HOSP.txt";
		String Date = "", DateTmp;
		int tmp = 0, run = 0;

		try {
			FileInputStream fin = this.openFileInput(File);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int size = fin.available();
			byte[] data = new byte[size];
			fin.read(data);
			bos.write(data);
			Date = bos.toString();
			bos.close();
			fin.close();
			if (Date.length() > 0) {
				run = Integer.parseInt(Date.substring(tmp).substring(0, Date.substring(tmp).indexOf('=')));
				tmp += ((run + "").length() + 1);
				for (int i = 0; i < run; i++) {
					DateTmp = Date.substring(tmp + i).substring(0, Date.substring(tmp + i).indexOf('|'));
					tmp += DateTmp.length();
					if (i == 0) {
						Treatment_unit.add(new hospital(getString(R.string.s_H), 0, "", ""));
					} else {
						Object[] strs = DateTmp.split(",");
						Treatment_unit.add(new hospital((String) strs[0], Integer.parseInt((String) strs[1]),
								(String) strs[2] + "," + (String) strs[3], (String) strs[4]));
					}
				}
			}
			// toast(Date, this);
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}

	private void load_data(){

//		new Handler().postDelayed(new Runnable() {
//			@Override
//			public void run() {
//				if (MainActivity.info_photo != null) {
//					main5_info_photo.setBackgroundResource(R.drawable.item_background);
//					main5_info_photo.setImageBitmap(MainActivity.info_photo);
//					img = true;
//				}
//			}
//		}, 5000);
		main5_info_photo.setImageResource(R.drawable.fs_image);
		main5_info_photo.setBackgroundResource(R.drawable.item_background_sky);

		setButtonColor(leve_count);
		if(info_data.size()>0){
			main5_name.setText(info_data.get(0));
			main5_identity.setText(identity);
			main5_age.setText(info_data.get(1));
		}
		switch (gender) {
			case 0:
				//女
				spinner_main5_gender.setSelection(2);
				break;
			case 1:
				//男
				spinner_main5_gender.setSelection(1);
				break;
			case 2:
				//不詳
				spinner_main5_gender.setSelection(0);
				break;
		}
	}

	private void findViewById() {
		main_left = (ImageView) findViewById(R.id.main_left);
		main_right = (ImageView) findViewById(R.id.main_right);
		pager1 = (ViewPager) findViewById(R.id.main_pager1);
		main_log = (Button) findViewById(R.id.main_log);
		main_load = (RelativeLayout) findViewById(R.id.main_load);

		main_log.setOnClickListener(this);
		main_load.setVisibility(View.GONE);
		main_left.getBackground().setAlpha(50);
		main_right.getBackground().setAlpha(50);
	}

	private void date(){
		emrc_date.setText(ambu_date);
		emrc_t0.setText(emrc_time.get(0));
		emrc_t1.setText(emrc_time.get(1));
		emrc_t2.setText(emrc_time.get(2));
		emrc_t3.setText(emrc_time.get(3));
		emrc_t4.setText(emrc_time.get(4));
		emrc_t5.setText(emrc_time.get(5));
	}

	private void checkToday(int y, int m, int d, boolean check) {
		int cy, cm, cd;
		Calendar calendar = Calendar.getInstance();
		if (check) {
			if (calendar.get(Calendar.YEAR) != y || calendar.get(Calendar.MONTH) != m - 1
					|| calendar.get(Calendar.DATE) != d) {
				main_log.setVisibility(View.VISIBLE);
				main_log.setText(getString(R.string.s_ts_1));
				toast(getString(R.string.s_ts_1), this);
			}
		} else {
			boolean pass = false;
			y = id_Y;
			m = id_M;
			d = id_D;
			cy = calendar.get(Calendar.YEAR);
			cm = calendar.get(Calendar.MONTH)+1;
			cd = calendar.get(Calendar.DATE);
			if(cm<10 && cd<10){
				ambu_date = cy+"-0"+cm+"-0"+cd;
			}
			if(cm<10 && cd>10){
				ambu_date = cy+"-0"+cm+"-"+cd;
			}
			if(cm>10 && cd<10){
				ambu_date = cy+"-"+cm+"-0"+cd;
			}
			if(cm>10 && cd>10){
				ambu_date = cy+"-"+cm+"-"+cd;
			}
			if (y > cy) {
				pass = true;
			} else {
				if (y == cy) {
					cm++;
					if (m > cm) {
						pass = true;
					} else {
						if (m == cm) {
							if (d > cd) {
								pass = true;
							} else {
								if (d == cd) {
									pass = true;
								}
							}
						}
					}
				}
			}
			if (pass) {
				main_log.setVisibility(View.GONE);
//				toast(getString(R.string.s_ts_2) + m + getString(R.string.s_ts_4) + d + getString(R.string.s_ts_5),
//						this);
			} else {
				main_log.setVisibility(View.VISIBLE);
//				toast(getString(R.string.s_ts_3) + m + getString(R.string.s_ts_4) + d + getString(R.string.s_ts_5),
//						this);
				main_log.setText(getString(R.string.s_ts_3) + m + getString(R.string.s_ts_4) + d + getString(R.string.s_ts_5));
			}
		}
	}

	@SuppressWarnings("static-access")
	@SuppressLint("InflateParams")
	private void addTab() {
		main_view.clear();
		LayoutInflater mInflater = getLayoutInflater().from(this);
//		View v0 = mInflater.inflate(R.layout.main_0_view, null); // START
		View v1 = mInflater.inflate(R.layout.main_1_view, null); // 傷患資訊
		View v2 = mInflater.inflate(R.layout.main_2_view, null); // 歷史紀錄
		View v3 = mInflater.inflate(R.layout.main_3_view, null); // 駕駛模式

		 View v5 = mInflater.inflate(R.layout.main_5_view, null);
		 View v6 = mInflater.inflate(R.layout.main_6_view, null);
		// 初始畫面
//		main_view.add(v0);
		main_view.add(v1);
		main_view.add(v2);
		main_view.add(v3);

		 main_view.add(v5);
		 main_view.add(v6);

		// 設置標題
		// main_title_list.add("START檢傷");
		// main_title_list.add("檢傷卡");
		// main_title_list.add("即時車輛");
		// main_title_list.add("救護車設定");

		// 建立配適器
		pager1.setOnPageChangeListener(this);
		getDeviceWidth();
	}

	private void getDeviceWidth() {
		// 9吋 3904 * 3072
		// 5吋 2368 * 1440
		metrics = getResources().getDisplayMetrics();
		int mWidth = (int) (metrics.widthPixels * metrics.density); // 螢幕寬
		textSize = mWidth / 48;
		if (textSize > 60) {
			textSize /= 1.6;
		}
	}

	private void pager_upload() {
		pager1.setAdapter(new MyPagerAdapter_MAIN(main_view));
	}

	// 快速檢傷
//	private void view_0() {
//		bg_emrc = (ImageView) main_view.get(0).findViewById(R.id.print_background);
//		print_No = (TextView) main_view.get(0).findViewById(R.id.main_no_print);
//		print_item_0 = (TextView) main_view.get(0).findViewById(R.id.print_tv_0_green);
//		print_item_1 = (TextView) main_view.get(0).findViewById(R.id.print_tv_1_black);
//		print_item_2 = (TextView) main_view.get(0).findViewById(R.id.print_tv_2_red);
//		print_item_3 = (TextView) main_view.get(0).findViewById(R.id.print_tv_3_red);
//		print_item_4 = (TextView) main_view.get(0).findViewById(R.id.print_tv_4_red);
//		print_item_5 = (TextView) main_view.get(0).findViewById(R.id.print_tv_5_red);
//		print_item_6 = (TextView) main_view.get(0).findViewById(R.id.print_tv_6_gold);
//
//		print_item_0.setOnClickListener(this);
//		print_item_1.setOnClickListener(this);
//		print_item_2.setOnClickListener(this);
//		print_item_3.setOnClickListener(this);
//		print_item_4.setOnClickListener(this);
//		print_item_5.setOnClickListener(this);
//		print_item_6.setOnClickListener(this);
//		if (!this.getResources().getConfiguration().locale.getCountry().equals("TW")) {
//			bg_emrc.setImageResource(R.drawable.bg_emrc);
//
//			EN = true;
//		}
//	}

	// 檢傷卡
	@SuppressLint("InflateParams")
	private void view_1() {
		menu_No = (TextView) main_view.get(0).findViewById(R.id.main_no_menu);
		menu_list = (ListView) main_view.get(0).findViewById(R.id.menulist);
		menu_ok = (ImageView) main_view.get(0).findViewById(R.id.menu_ok);
		menu_re = (ImageView) main_view.get(0).findViewById(R.id.menu_re);
		spinner = (Spinner) main_view.get(0).findViewById(R.id.menu_spinner);
		menu_item.add(getString(R.string.s_Menu_1)); // information
		menu_item.add(getString(R.string.s_Menu_2)); // emergency
		menu_item.add(getString(R.string.s_Menu_3)); // injured
		menu_item.add(getString(R.string.s_Menu_4)); // vital signs
		menu_item.add(getString(R.string.s_Menu_5)); // level
		menu_item.add(getString(R.string.s_Menu_6)); // photo evidence

		load_HOSP_DATA();
//		Treatment_unit.add(new hospital(getString(R.string.s_H), 0, "", "")); // 收治單位
//		Treatment_unit.add(new hospital(getString(R.string.s_H_Tainan_1), 1, "23.002291,120.218917", "R01")); // 1.成大醫院
//		Treatment_unit.add(new hospital(getString(R.string.s_H_Tainan_2), 1, "23.021116,120.221472", "R02")); // 2.奇美醫院
//		Treatment_unit.add(new hospital(getString(R.string.s_H_Tainan_3), 2, "23.289625,120.325462", "R03")); // 3.柳營奇美
//		Treatment_unit.add(new hospital(getString(R.string.s_H_Tainan_4), 3, "23.181638,120.183853", "R04")); // 4.佳里奇美
//		Treatment_unit.add(new hospital(getString(R.string.s_H_Tainan_5), 2, "22.968939,120.226432", "R05")); // 5.台南市醫
//		Treatment_unit.add(new hospital(getString(R.string.s_H_Tainan_6), 2, "22.997351,120.209366", "R06")); // 6.衛部臺南
//		Treatment_unit.add(new hospital(getString(R.string.s_H_Tainan_7), 2, "22.997983,120.239994", "R07")); // 7.台南榮總
//		Treatment_unit.add(new hospital(getString(R.string.s_H_Tainan_8), 2, "22.989220,120.212705", "R08")); // 8.台南新樓
//		Treatment_unit.add(new hospital(getString(R.string.s_H_Tainan_9), 2, "23.180699,120.232423", "R09")); // 9.麻豆新樓
//		Treatment_unit.add(new hospital(getString(R.string.s_H_Tainan_10), 2, "22.994627,120.198932", "R10")); // 10.郭綜合醫院
//		Treatment_unit.add(new hospital(getString(R.string.s_H_Tainan_11), 3, "23.064470,120.223766", "R11")); // 11.安南醫院
//		Treatment_unit.add(new hospital(getString(R.string.s_H_Tainan_12), 3, "23.063487,120.335736", "R12")); // 12.衛部新化
//		Treatment_unit.add(new hospital(getString(R.string.s_H_Tainan_13), 3, "23.308968,120.313599", "R13")); // 13.衛部新營
//
//		Treatment_unit.add(new hospital(getString(R.string.s_H_Changhua_1), 1, "24.065078,120.537304", "N01")); // 14.秀傳醫院
//		Treatment_unit.add(new hospital(getString(R.string.s_H_Changhua_2), 1, "24.0709512,120.5446393", "N02")); // 15.彰化基督教醫院
//		Treatment_unit.add(new hospital(getString(R.string.s_H_Changhua_3), 2, "24.078987,120.412039", "N03")); // 16.彰濱秀傳醫院
//		Treatment_unit.add(new hospital(getString(R.string.s_H_Changhua_4), 2, "23.893694,120.363822", "N04")); // 17.二林基督教醫院
//		Treatment_unit.add(new hospital(getString(R.string.s_H_Changhua_5), 2, "24.060152,120.438532", "N05")); // 18.鹿港基督教醫院
//		Treatment_unit.add(new hospital(getString(R.string.s_H_Changhua_6), 2, "23.949322,120.527273", "N06")); // 19.衛福部彰化醫院
//		Treatment_unit.add(new hospital(getString(R.string.s_H_Changhua_7), 3, "23.871781,120.515663", "N07")); // 20.卓醫院
//		Treatment_unit.add(new hospital(getString(R.string.s_H_Changhua_8), 3, "23.858099,120.587909", "N08")); // 21.仁和醫院
//		Treatment_unit.add(new hospital(getString(R.string.s_H_Changhua_9), 3, "23.953454,120.575007", "N09")); // 22.員榮醫院
//		Treatment_unit.add(new hospital(getString(R.string.s_H_Changhua_10), 3, "24.110637,120.492225", "N10")); // 23.道周醫院
//		Treatment_unit.add(new hospital(getString(R.string.s_H_Changhua_11), 3, "23.961575,120.480077", "N11")); // 24.道安醫院
//
//		Treatment_unit.add(new hospital(getString(R.string.s_H_Yunlin_1), 1, "23.697808,120.525822", "P01")); // 25.臺大醫院雲林分院
//		Treatment_unit.add(new hospital(getString(R.string.s_H_Yunlin_2), 2, "23.708148,120.438055", "P02")); // 26.若瑟醫院
//		Treatment_unit.add(new hospital(getString(R.string.s_H_Yunlin_3), 2, "23.589272,120.307979", "P03")); // 27.中醫大北港醫院
//		Treatment_unit.add(new hospital(getString(R.string.s_H_Yunlin_4), 2, "23.780811,120.440984", "P04")); // 28.雲林基督教醫院
//		Treatment_unit.add(new hospital(getString(R.string.s_H_Yunlin_5), 2, "23.702108,120.545211", "P05")); // 29.大醫院斗六分院
//		Treatment_unit.add(new hospital(getString(R.string.s_H_Yunlin_6), 2, "23.795332,120.218874", "P06")); // 30.雲林長庚醫院
//
//		Treatment_unit.add(new hospital(getString(R.string.s_H_Taipei_1), 1, "25.027127,121.563626", "A01")); // 31.臺北醫學大學附設醫院
//		Treatment_unit.add(new hospital(getString(R.string.s_H_Taipei_2), 1, "25.037011,121.554348", "A02")); // 32.國泰綜合醫院
//		Treatment_unit.add(new hospital(getString(R.string.s_H_Taipei_3), 1, "25.058233,121.521736", "A03")); // 33.馬偕紀念醫院
//		Treatment_unit.add(new hospital(getString(R.string.s_H_Taipei_4), 1, "24.999455,121.557631", "A04")); // 34.萬芳醫院
//		Treatment_unit.add(new hospital(getString(R.string.s_H_Taipei_5), 1, "25.041601,121.519887", "A05")); // 35.台大醫學院附設醫院
//		Treatment_unit.add(new hospital(getString(R.string.s_H_Taipei_6), 1, "25.118969,121.521265", "A06")); // 36.臺北榮民總醫院
//		Treatment_unit.add(new hospital(getString(R.string.s_H_Taipei_7), 1, "25.070836,121.594757", "A07")); // 37.三軍總醫院內湖院區
//		Treatment_unit.add(new hospital(getString(R.string.s_H_Taipei_8), 1, "25.096200,121.519667", "A08")); // 38.新光吳火獅紀念醫院
//		Treatment_unit.add(new hospital(getString(R.string.s_H_Taipei_9), 2, "25.115624,121.523085", "A09")); // 39.振興醫院
//		Treatment_unit.add(new hospital(getString(R.string.s_H_Taipei_10), 2, "25.055894,121.549756", "A10")); // 40.台北長庚紀念醫院
//		Treatment_unit.add(new hospital(getString(R.string.s_H_Taipei_11), 2, "25.048117,121.547519", "A11")); // 41.臺安醫院
//		Treatment_unit.add(new hospital(getString(R.string.s_H_Taipei_12), 2, "25.054689,121.555917", "A12")); // 44.三軍總醫院松山分院
//		Treatment_unit.add(new hospital(getString(R.string.s_H_Taipei_13), 2, "25.037680,121.545118", "A13")); // 42.聯合醫院仁愛院區
//		Treatment_unit.add(new hospital(getString(R.string.s_H_Taipei_14), 2, "25.051478,121.508457", "A14")); // 43.聯合醫院中興院區
//		Treatment_unit.add(new hospital(getString(R.string.s_H_Taipei_15), 2, "25.046370,121.586535", "A15")); // 45.聯合醫院忠孝院區
//		Treatment_unit.add(new hospital(getString(R.string.s_H_Taipei_16), 2, "25.104626,121.531430", "A16")); // 46.聯合醫院陽明院區
//		Treatment_unit.add(new hospital(getString(R.string.s_H_Taipei_17), 2, "25.035139,121.507173", "A17")); // 47.聯合醫院和平院區
//		Treatment_unit.add(new hospital(getString(R.string.s_H_Taipei_18), 3, "25.075960,121.608561", "A18")); // 48.康寧醫院
//		Treatment_unit.add(new hospital(getString(R.string.s_H_Taipei_19), 3, "25.049755,121.557489", "A19")); // 49.博仁綜合醫院
//		Treatment_unit.add(new hospital(getString(R.string.s_H_Taipei_20), 3, "25.027688,121.494565", "A20")); // 50.西園醫院

		menu_upload(this);
		menu_list.setOnItemClickListener(this);
		menu_ok.setOnClickListener(this);
		menu_re.setOnClickListener(this);
		if(Treatment_unit.size()==0){
			Treatment_unit.add(new hospital(getString(R.string.s_H), 0, "", "")); // 收治單位
		}
		spinner.setOnItemSelectedListener(this);
		spinner.setAdapter(new MySpinnerAdapter_MAIN(this, Treatment_unit));
	}

	@SuppressLint("InflateParams")
	public static void menu_upload(Context con) {
			menu_view.clear();
		for (int i = 0; i < menu_item.size(); i++) {
			setMyMENUAdapter(i, con);
		}
		menu_list.setAdapter(new MyListAdapter(menu_view));
	}

	@SuppressLint("InflateParams")
	private static void setMyMENUAdapter(int f, Context con) {
		View vi = LayoutInflater.from(con).inflate(R.layout.style_textview, null);
		TextView textview = (TextView) vi.findViewById(R.id.textview);
		String msg = menu_item.get(f);
		textview.setText(msg);
		setViewColor(textview, leve_count);
		menu_view.add(vi);
	}

	@SuppressLint("InflateParams")
	private void setRYGB() {
		String mmsg = lead_item.get(0).toString();
		int resource = R.layout.style_textview4;
		if (EN) {
			resource = R.layout.style_textview6;
		}
		View vi = LayoutInflater.from(this).inflate(resource, null);
		TextView h_red = (TextView) vi.findViewById(R.id.tv_h_red);
		TextView h_yellow = (TextView) vi.findViewById(R.id.tv_h_yellow);
		TextView h_green = (TextView) vi.findViewById(R.id.tv_h_green);
		TextView h_black = (TextView) vi.findViewById(R.id.tv_h_black);
		TextView s_red = (TextView) vi.findViewById(R.id.tv_s_red);
		TextView s_yellow = (TextView) vi.findViewById(R.id.tv_s_yellow);
		TextView s_green = (TextView) vi.findViewById(R.id.tv_s_green);
		TextView s_black = (TextView) vi.findViewById(R.id.tv_s_black);
		// 17030600001/1/五身份證/5/ 其他處置 (保暖).|║0=n/║0=n/║n/║2/32/10/A1267767/
		int r = 0;
		String hr = getLine(mmsg, r, '/');
		r += hr.length() + 1;
		String hy = getLine(mmsg, r, '/');
		r += hy.length() + 1;
		String hg = getLine(mmsg, r, '/');
		r += hg.length() + 1;
		String hb = getLine(mmsg, r, '/');
		r += hb.length() + 1;
		String sr = getLine(mmsg, r, '/');
		r += sr.length() + 1;
		String sy = getLine(mmsg, r, '/');
		r += sy.length() + 1;
		String sg = getLine(mmsg, r, '/');
		r += sg.length() + 1;
		String sb = getLine(mmsg, r, '/');

		h_red.setText(hr);
		h_yellow.setText(hy);
		h_green.setText(hg);
		h_black.setText(hb);
		s_red.setText(sr);
		s_yellow.setText(sy);
		s_green.setText(sg);
		s_black.setText(sb);
		lead_view.add(vi);
	}

//	@SuppressLint("InflateParams")
//	private void setMyAdapter(int f, boolean db, ArrayList<String> ls, ArrayList<View> lv) {
//		// db 17030600001/1/五身份證/5/ 其他處置 (保暖).|║0=n/║0=n/║n/║2/32/10/A1267767/
//		// !db 17030600001/1/五身份證/5/ 其他處置 (保暖).║0=n/║0=n/║n/║2/32/10/A1267767/
//		final int i = f;
//		View vi = LayoutInflater.from(this).inflate(R.layout.style_textview3, null);
//		RelativeLayout t_bg = (RelativeLayout) vi.findViewById(R.id.t3_relativeLayout);
//		TextView t_name = (TextView) vi.findViewById(R.id.t3_name);
//		TextView t_hosp = (TextView) vi.findViewById(R.id.t3_hospital);
//		TextView t_other = (TextView) vi.findViewById(R.id.t3_other);
//		ImageView t_image = (ImageView) vi.findViewById(R.id.t3_image);
//
//
//		String mmsg = ls.get(i);
//		int r = 0;
//		String number = getLine(mmsg, r, '/');
//		r += number.length() + 3;
//		String name = getLine(mmsg, r, '/');
//		r += name.length() + 1;
//		String age = getLine(mmsg, r, '/');
//		r += age.length() + 1;
//		String other = getLine(mmsg, r, '║');
//		r += other.length() + 2;
//		String inju_front = getLine(mmsg, r, '║');
//		r += inju_front.length() + 1;
//		String inju_back = getLine(mmsg, r, '║');
//		r += inju_back.length() + 1;
//		String vita_date = getLine(mmsg, r, '║');
//		r += vita_date.length() + 1;
//		int leve_count = Integer.parseInt(getLine(mmsg, r, '/'));
//		r += 2;
//		int emrc_count = Integer.parseInt(getLine(mmsg, r, '/'));
//		r += (emrc_count + "").length() + 1;
//		String hosp_count = getLine(mmsg, r, '/');
//		String hospp;
//		if (Integer.parseInt(hosp_count) != 0) {
//			hospp = Treatment_unit.get(Integer.parseInt(hosp_count));
//		} else {
//			hospp = getString(R.string.s_List_38);
//		}
//		if (name.length() == 0) {
//			t_name.setText(number);
//		} else {
//			t_name.setText(name);
//		}
//		t_hosp.setText(hospp);
//		if (db) {
//			// 去除標號
//			other = getLine(other, 0, '|');
//		}
//		other += getEMRC(MainActivity.this, emrc_count, leve_count);
//		t_other.setText(other);
//		switch (leve_count + "") {
//		case "0":
//			t_name.setTextColor(Color.WHITE);
//			t_hosp.setTextColor(Color.WHITE);
//			t_other.setTextColor(Color.WHITE);
//			t_bg.setBackgroundColor(Color.BLACK);
//			break;
//		case "1":
//			t_name.setTextColor(Color.WHITE);
//			t_hosp.setTextColor(Color.WHITE);
//			t_other.setTextColor(Color.WHITE);
//			t_bg.setBackgroundColor(Color.RED);
//			break;
//		case "2":
//			t_bg.setBackgroundColor(Color.YELLOW);
//			break;
//		case "3":
//			t_bg.setBackgroundColor(Color.GREEN);
//			break;
//		default:
//			t_name.setTextColor(Color.BLACK);
//			t_hosp.setTextColor(Color.BLACK);
//			t_other.setTextColor(Color.BLACK);
//			t_bg.setBackgroundColor(Color.WHITE);
//			break;
//		}
//		lv.add(vi);
//
//	}

	// 傷患紀錄與清單
	private void view_2() {
		medi_unit = (TextView) main_view.get(1).findViewById(R.id.medi_unit);
		medi_qunt = (TextView) main_view.get(1).findViewById(R.id.medi_qunt);
		medi_list = (ListView) main_view.get(1).findViewById(R.id.medi_list);
		medi_swap = (ImageView) main_view.get(1).findViewById(R.id.medi_swap);
		medi_clear = (ImageView) main_view.get(1).findViewById(R.id.medi_clear);
		incident_spinner = (Spinner) main_view.get(1).findViewById(R.id.incident_spinner);
		medi_unit.setText(getString(R.string.s_List_1));

		load_MEDI_DATA(); // load medi_item
		medi_clear.setOnClickListener(this);
//		medi_clear.setVisibility(View.INVISIBLE);
		medi_list.setOnItemClickListener(this);
		medi_list.setOnItemLongClickListener(this);
		medi_swap.setOnClickListener(this);
		incident_spinner.setOnItemSelectedListener(this);
		medi_swap.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.fs_ambulance));
		user_upload();
	}

	private void lead_upload() {
		try {
			lead_view.clear();
			//setRYGB();
//			for (int i = 1; i < lead_item.size(); i++) {
//				setMyAdapter(i, true, lead_item, lead_view);
//			}
			medi_list.setAdapter(new MyListAdapter2(this,lead_item,true));
			medi_qunt.setText(lead_item.size() - 1 + getString(R.string.s_List_5));

		} catch (Exception e) {
			Log.e("SSL.ERROR", e.toString());
		}
	}

	private void user_upload() {
		dblist = false;
		try {
			medi_view.clear();
//			for (int i = 0; i < medi_item.size(); i++) {
//				setMyAdapter(i, false, medi_item, medi_view);
//			}
			medi_list.setAdapter(new MyListAdapter2(this,medi_item,false));
			medi_qunt.setText(medi_item.size() + getString(R.string.s_List_5));
		} catch (Exception e) {
			Log.e("SSL.ERROR", e.toString());
		}
	}

	// 救護車
	private void view_3() {
		TextView mediCar_un = (TextView) main_view.get(2).findViewById(R.id.mediCar_un);
		TextView mediCar_lp = (TextView) main_view.get(2).findViewById(R.id.mediCar_lp);
		mediCar_static = (TextView) main_view.get(2).findViewById(R.id.mediCar_static);
		mediCar_unit = (EditText) main_view.get(2).findViewById(R.id.mediCar_unit);
		mediCar_brand = (EditText) main_view.get(2).findViewById(R.id.mediCar_license);
		mediCar_btn0 = (Button) main_view.get(2).findViewById(R.id.mediCar_btn0); // 出勤
		mediCar_btn1 = (Button) main_view.get(2).findViewById(R.id.mediCar_btn1); // 到場
		mediCar_btn2 = (Button) main_view.get(2).findViewById(R.id.mediCar_btn2); // 離場
		mediCar_btn3 = (Button) main_view.get(2).findViewById(R.id.mediCar_btn3); // 到院
		mediCar_btn4 = (Button) main_view.get(2).findViewById(R.id.mediCar_btn4); // 離院
		mediCar_btn5 = (Button) main_view.get(2).findViewById(R.id.mediCar_btn5); // 返回
		mediCar_btn6 = (Button) main_view.get(2).findViewById(R.id.mediCar_btn6); // 取消
		mediCar_btn7 = (Button) main_view.get(2).findViewById(R.id.mediCar_btn7); // 拒絕
		mediCar_btn8 = (Button) main_view.get(2).findViewById(R.id.mediCar_btn8); // 導航
		mediCar_btn9 = (Button) main_view.get(2).findViewById(R.id.mediCar_btn9); // 錄影
		mediCar_btn10 = (Button) main_view.get(2).findViewById(R.id.mediCar_btn10); // 110
		mediCar_btn11 = (Button) main_view.get(2).findViewById(R.id.mediCar_btn11); // 119
		mediCar_static.setText(getString(R.string.s_Menu_20));
		mediCar_btn0.setOnClickListener(this); // 出勤通知
		mediCar_btn1.setOnClickListener(this); // 到達現場
		mediCar_btn2.setOnClickListener(this); // 離開現場
		mediCar_btn3.setOnClickListener(this); // 送達醫院
		mediCar_btn4.setOnClickListener(this); // 離開醫院
		mediCar_btn5.setOnClickListener(this); // 返隊待命
		mediCar_btn6.setOnClickListener(this); // 導航到場
		mediCar_btn7.setOnClickListener(this); // 導航到院
		mediCar_btn8.setOnClickListener(this); // 撥號
		mediCar_btn9.setOnClickListener(this);
		mediCar_btn10.setOnClickListener(this);
		mediCar_btn11.setOnClickListener(this);
		if (EN) {
			mediCar_un.setTextSize(25);
			mediCar_lp.setTextSize(25);
			mediCar_unit.setTextSize(25);
			mediCar_brand.setTextSize(25);
			mediCar_btn0.setTextSize(20);
			mediCar_btn1.setTextSize(20);
			mediCar_btn2.setTextSize(20);
			mediCar_btn3.setTextSize(20);
			mediCar_btn4.setTextSize(20);
			mediCar_btn5.setTextSize(20);
			mediCar_btn6.setTextSize(20);
			mediCar_btn7.setTextSize(20);
			mediCar_btn8.setTextSize(20);
			mediCar_btn9.setTextSize(20);
			mediCar_btn10.setTextSize(20);
			mediCar_btn11.setTextSize(20);
		}
//		setTextWatcher();
	}

	private void view_5(){
		help_No = (TextView) main_view.get(3).findViewById(R.id.emrgn_no_help);
		main_5_ok = (ImageView) main_view.get(3).findViewById(R.id.main_5_ok);
		main_5_up = (ImageView) main_view.get(3).findViewById(R.id.main_5_up);
		main5_photo = (ImageView) main_view.get(3).findViewById(R.id.main5_photo);
		emrc_t0 = (TextView)main_view.get(3).findViewById(R.id.time0);
		emrc_t1 = (TextView)main_view.get(3).findViewById(R.id.time1);
		emrc_t2 = (TextView)main_view.get(3).findViewById(R.id.time2);
		emrc_t3 = (TextView)main_view.get(3).findViewById(R.id.time3);
		emrc_t4 = (TextView)main_view.get(3).findViewById(R.id.time4);
		emrc_t5 = (TextView)main_view.get(3).findViewById(R.id.time5);
		emrc_date = (TextView)main_view.get(3).findViewById(R.id.emrc_date);
		main5_name = (EditText)main_view.get(3).findViewById(R.id.main5_name);
		main5_age = (EditText)main_view.get(3).findViewById(R.id.main5_age);
		main5_identity = (EditText)main_view.get(3).findViewById(R.id.main5_identity);
		ambu_property = (EditText)main_view.get(3).findViewById(R.id.ambu_property);
		ambu_complaint = (EditText)main_view.get(3).findViewById(R.id.ambu_complaint);
		ambu_complaint0 = (CheckBox) main_view.get(3).findViewById(R.id.ambu_complaint0);
		ambu_complaint1 = (CheckBox) main_view.get(3).findViewById(R.id.ambu_complaint1);
		ambu_complaint2 = (CheckBox) main_view.get(3).findViewById(R.id.ambu_complaint2);
		ambu_complaint3 = (CheckBox) main_view.get(3).findViewById(R.id.ambu_complaint3);
		ambu_accepted1 = (CheckBox) main_view.get(3).findViewById(R.id.ambu_accepted1);
		ambu_accepted2 = (CheckBox) main_view.get(3).findViewById(R.id.ambu_accepted2);
		ambu_accepted3 = (CheckBox) main_view.get(3).findViewById(R.id.ambu_accepted3);
		accident_car1 = (EditText)main_view.get(3).findViewById(R.id.accident_car1);
		accident_car2 = (EditText)main_view.get(3).findViewById(R.id.accident_car2);
		accident_car3 = (EditText)main_view.get(3).findViewById(R.id.accident_car3);
		accident_car4 = (EditText)main_view.get(3).findViewById(R.id.accident_car4);
		allergy1 = (EditText)main_view.get(3).findViewById(R.id.allergy1);
		allergy2 = (EditText)main_view.get(3).findViewById(R.id.allergy2);
		allergy3 = (EditText)main_view.get(3).findViewById(R.id.allergy3);
		ambu_place1 = (EditText)main_view.get(3).findViewById(R.id.ambu_place1);
		ambu_place2 = (EditText)main_view.get(3).findViewById(R.id.ambu_place2);
		spinner_main5_gender = (Spinner) main_view.get(3).findViewById(R.id.spinner_main5_gender);
		main5_Car_unit = (TextView) main_view.get(3).findViewById(R.id.main5_Car_unit);
		spinner_hos3 = (Spinner) main_view.get(3).findViewById(R.id.spinner_hos3);
		spinner_hos = (Spinner) main_view.get(3).findViewById(R.id.spinner_hos);
		spinner_hos2 = (Spinner) main_view.get(3).findViewById(R.id.spinner_hos2);
		main5_level = (TextView)main_view.get(3).findViewById(R.id.main5_level);
		photo_num = (TextView)main_view.get(3).findViewById(R.id.photo_num);
		main5_info_photo = (ImageView) main_view.get(3).findViewById(R.id.main5_info_photo);
		sign0 = (ImageView) main_view.get(3).findViewById(R.id.sign0);
		allergy0 = (ImageView) main_view.get(3).findViewById(R.id.allergy0);
		check = (ImageView) main_view.get(3).findViewById(R.id.check);
		HelpN_TextView = (TextView)main_view.get(3).findViewById(R.id.HelpN_TextView);
		HelpY_TextView = (TextView)main_view.get(3).findViewById(R.id.HelpY_TextView);
		Mediacal_TextView = (TextView)main_view.get(3).findViewById(R.id.Mediacal_TextView);
		HelpN_LinearLayout = (LinearLayout)main_view.get(3).findViewById(R.id.HelpN_LinearLayout);
		HelpY_LinearLayout = (LinearLayout)main_view.get(3).findViewById(R.id.HelpY_LinearLayout);
		Mediacal_LinearLayout = (LinearLayout)main_view.get(3).findViewById(R.id.Mediacal_LinearLayout);
		Gender_unit.add(getString(R.string.info_unknow));
		Gender_unit.add(getString(R.string.info_male));
		Gender_unit.add(getString(R.string.info_femal));
		Reason_unit.add(getString(R.string.s_List_43));
		Reason_unit.add(getString(R.string.s_List_38));
		Reason_unit.add(getString(R.string.s_List_39));
		Reason_unit.add(getString(R.string.s_List_40));
		Reason_unit.add(getString(R.string.s_List_41));
		Reason_unit.add(getString(R.string.s_List_42));
		Reason_unit.add(getString(R.string.s_List_44));
		Reason_unit.add("");
		Hos_unit.add(getString(R.string.s_hos_01));
		Hos_unit.add(getString(R.string.s_hos_02));
		Hos_unit.add(getString(R.string.s_hos_03));
		Hos_unit.add(getString(R.string.s_hos_04));
		emrc_t0.setOnClickListener(this);
		emrc_t1.setOnClickListener(this);
		emrc_t2.setOnClickListener(this);
		emrc_t3.setOnClickListener(this);
		emrc_t4.setOnClickListener(this);
		emrc_t5.setOnClickListener(this);
		ambu_accepted1.setOnClickListener(this);
		ambu_accepted2.setOnClickListener(this);
		ambu_accepted3.setOnClickListener(this);
		main5_info_photo.setOnClickListener(this);
		main_5_ok.setOnClickListener(this);
		main_5_up.setOnClickListener(this);
		main5_photo.setOnClickListener(this);
		main5_level.setOnClickListener(this);
		sign0.setOnClickListener(this);
		HelpN_TextView.setOnClickListener(this);
		HelpY_TextView.setOnClickListener(this);
		Mediacal_TextView.setOnClickListener(this);
		allergy0.setOnClickListener(this);
		check.setOnClickListener(this);
		spinner_main5_gender.setOnItemSelectedListener(this);
		spinner_hos3.setOnItemSelectedListener(this);
		spinner_hos2.setOnItemSelectedListener(this);
		spinner_hos.setOnItemSelectedListener(this);
		spinner_main5_gender.setAdapter(new ArrayAdapter<String>(this, R.layout.style_spinner, Gender_unit));
		spinner_hos3.setAdapter(new ArrayAdapter<String>(this, R.layout.style_spinner, Reason_unit));
		spinner_hos.setAdapter(new MySpinnerAdapter_MAIN(this, Treatment_unit));
		spinner_hos2.setAdapter(new ArrayAdapter<String>(this, R.layout.style_spinner, Hos_unit));

		setTextWatcher();
	}

	private void view_6(){
		main_6_ok = (ImageView) main_view.get(4).findViewById(R.id.main_6_ok);
		main_6_up = (ImageView) main_view.get(4).findViewById(R.id.main_6_up);
		main_6_other = (EditText) main_view.get(4).findViewById(R.id.main_6_other);
		ambu_guide = (EditText) main_view.get(4).findViewById(R.id.ambu_guide);
		LinearLayout_inju = (LinearLayout)main_view.get(4).findViewById(R.id.LinearLayout_inju);
		LinearLayout_emrgn = (LinearLayout)main_view.get(4).findViewById(R.id.LinearLayout_emrgn);
		TextView_emrgn = (TextView) main_view.get(4).findViewById(R.id.TextView_emrgn);
		sign1 = (ImageView) main_view.get(4).findViewById(R.id.sign1);
		sign2 = (ImageView) main_view.get(4).findViewById(R.id.sign2);
		sign3 = (ImageView) main_view.get(4).findViewById(R.id.sign3);
		sign4 = (ImageView) main_view.get(4).findViewById(R.id.sign4);
		sign5 = (ImageView) main_view.get(4).findViewById(R.id.sign5);
		sign6 = (ImageView) main_view.get(4).findViewById(R.id.sign6);
		sign7 = (ImageView) main_view.get(4).findViewById(R.id.sign7);
		body_front = (ImageView) main_view.get(4).findViewById(R.id.body_front);
		body_back = (ImageView) main_view.get(4).findViewById(R.id.body_back);
		body_front_view = (ImageView) main_view.get(4).findViewById(R.id.body_front_view);
		body_back_view = (ImageView) main_view.get(4).findViewById(R.id.body_back_view);
		paint = new Paint();
		paint.setStrokeWidth(10);
		paint.setColor(Color.BLUE);
		paint.setAntiAlias(true);//鋸齒不顯示
		paint.setStrokeJoin(Paint.Join.ROUND);
		sign1.setOnClickListener(this);
		sign5.setOnClickListener(this);
		sign6.setOnClickListener(this);
		sign7.setOnClickListener(this);
		sign2.setOnClickListener(this);
		sign3.setOnClickListener(this);
		sign4.setOnClickListener(this);
		body_front.setOnClickListener(this);
		body_back.setOnClickListener(this);
		main_6_ok.setOnClickListener(this);
		main_6_up.setOnClickListener(this);

		main_6_other.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
		main_6_other.setGravity(Gravity.TOP);
		main_6_other.setSingleLine(false);
		main_6_other.setHorizontallyScrolling(false);

		TextView_emrgn.setOnClickListener(this);

		//main_6_other_load();

	}

	public void main_6_other_load(){
		if (info_data.size() > 0) {
//			TextView_emrgn.setText(info_data.get(2));
			TextView_emrgn.setText(emrgn_txt);
			MainActivity.main_6_other.setText(MainActivity.info_data.get(2));
//			MainActivity.info_data.clear();
		}
	}

	public void main_5_other_load(){
		if (main5_data.size() > 0) {
			if(main5_data.get(0).length() >0){
				HelpN_TextView.setText(main5_data.get(0));
			}
			if(main5_data.get(1).length() >0){
				HelpY_TextView.setText(main5_data.get(1));
			}
			if(main5_data.get(2).length() >0){
				Mediacal_TextView.setText(main5_data.get(2));
			}
		}
	}

	public static void main6_body_load(){
		inju_body_front.clear();
		for(int i=0;i<InjuredFragment.front_list.size();i+=2){
			inju_body_front.add(InjuredFragment.front_list.get(i)*266/322);
			inju_body_front.add(InjuredFragment.front_list.get(i+1)*358/447);
		}
		inju_body_back.clear();
		for(int i=0;i<InjuredFragment.back_list.size();i+=2){
			inju_body_back.add(InjuredFragment.back_list.get(i)*266/322);
			inju_body_back.add(InjuredFragment.back_list.get(i+1)*358/447);
		}

		print(inju_body_front, body_front_view, 0);
		print(inju_body_back, body_back_view, 2);
	}



	public void onPageScrollStateChanged(int arg0) {

	}

	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	public void onPageSelected(int arg0) {
		if (nfc) {
			if (user) {
				switch (arg0) {
				case 0:
					main_left.setVisibility(View.GONE);
					main_right.setVisibility(View.VISIBLE);
					break;
				case 1:
					main_left.setVisibility(View.VISIBLE);
					main_right.setVisibility(View.VISIBLE);
					break;
				case 2:
					main_left.setVisibility(View.VISIBLE);
					main_right.setVisibility(View.GONE);
					break;
				default:
					break;
				}
			} else {
				switch (arg0) {
				case 0:
					main_left.setVisibility(View.GONE);
					main_right.setVisibility(View.VISIBLE);
					break;
				case 1:
					main_left.setVisibility(View.VISIBLE);
					main_right.setVisibility(View.VISIBLE);
					medi_swap.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.fs_ambulance));
					medi_unit.setText(getString(R.string.s_List_1));
					user_upload();
					break;
				case 2:
					main_left.setVisibility(View.VISIBLE);
					main_right.setVisibility(View.GONE);
					break;
				case 3:
					main_left.setVisibility(View.VISIBLE);
					main_right.setVisibility(View.VISIBLE);
					pager5();
					break;
				case 4:
					main_left.setVisibility(View.VISIBLE);
					main_right.setVisibility(View.GONE);
					pager6();
					break;
				case 5:
					main_left.setVisibility(View.VISIBLE);
					main_right.setVisibility(View.GONE);

					break;
				default:
					break;
				}
			}
		} else {
			main_left.setVisibility(View.GONE);
			main_right.setVisibility(View.GONE);
		}
	}

	private void pager5(){
		date();
		HelpN_LinearLayout.setVisibility(View.VISIBLE);
		HelpY_LinearLayout.setVisibility(View.VISIBLE);
		Mediacal_LinearLayout.setVisibility(View.VISIBLE);
		spinner_hos.setSelection(getTrainsportSelection(hosp_count));


		if(decide_help ==0){

			fragmentManager = getFragmentManager();
			beginTransaction = fragmentManager.beginTransaction();
			beginTransaction.replace(R.id.HelpY_LinearLayout, fragmentY);
			beginTransaction.replace(R.id.HelpN_LinearLayout, fragmentN);
			beginTransaction.replace(R.id.Mediacal_LinearLayout, fragmentM);
			beginTransaction.addToBackStack(null);
			beginTransaction.commit();
			decide_help =1;
		}
		if (decide_emrgn==1){
			fragmentI.big();
			fragmentI =new InjuredFragment();
			decide_emrgn = 0;
		}
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				fragmentM.load();
				fragmentN.load();
				fragmentY.load();
			}
		}, 50);
	}

	private void pager6(){
		if(decide_emrgn == 0){
			LinearLayout_inju.setVisibility(View.VISIBLE);
			LinearLayout_emrgn.setVisibility(View.VISIBLE);
			fragmentManager = getFragmentManager();
			beginTransaction = fragmentManager.beginTransaction();
			beginTransaction.replace(R.id.LinearLayout_inju, fragmentI);
			beginTransaction.replace(R.id.LinearLayout_emrgn, fragmentE);
			beginTransaction.replace(R.id.LinearLayout_vita,fragmentV);
			beginTransaction.addToBackStack(null);
			beginTransaction.commit();
			decide_emrgn =1;
		}
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				LinearLayout_inju.setVisibility(View.GONE);
			}
		}, 50);
		main_6_other_load();
	}

	private void setTextWatcher() {
		// 建立文字監聽
		textWatcher = new TextWatcher() {
			public void afterTextChanged(Editable s) {
			}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String num = "";
				if (leve_count != 5) {
					num += number;
				}
				String unit = mediCar_unit.getText().toString() + "";
				String brand = mediCar_brand.getText().toString() + "";
				main5_Car_unit.setText(mediCar_unit.getText().toString());
				if(ambu_property.getText().toString().length()>15){
					ambu_property.setTextSize(10);
				}else if(ambu_property.getText().toString().length()>11){
					ambu_property.setTextSize(15);
				}else{
					ambu_property.setTextSize(20);
				}
				if (link && unit.length() > 0 && brand.length() > 0) {
					if (!car) {
						car = true;
						RSMSHandler.obtainMessage().sendToTarget(); // 開啟自動更新
						mediCar_static.setText(getString(R.string.s_ts_6));
						toast(getString(R.string.s_ts_6), MainActivity.this);
					}
					out("CAR/" + unit + "/" + brand + "/" + status_count + "/" + num + "/|");
					Log.e("CAR / ", "01");
					car_brand = brand;
					save();
				} else {
					if (car) {
						car_brand = "";
						out("CAR///8//|");
						Log.e("CAR / ", "02");
					}
					if (gps) {
						gps = false;
					}
					status_count = 8;
					mediCar_static.setText(getString(R.string.s_Menu_20));
					car = false;
				}
			}
		};
		mediCar_unit.addTextChangedListener(textWatcher);
		mediCar_brand.addTextChangedListener(textWatcher);
		ambu_property.addTextChangedListener(textWatcher);
	}

	private void setTARGET() {
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		inju_target.add(0.49);// 頭.0
		inju_target.add(0.2);
		inju_target.add(0.49);// 胸部.2
		inju_target.add(0.33);
		inju_target.add(0.49);// 腹部.4
		inju_target.add(0.4245);
		inju_target.add(0.575);// 右大腿.8
		inju_target.add(0.55);
		inju_target.add(0.4);// 左大腿.6
		inju_target.add(0.55);
		inju_target.add(0.6);// 右小腿.12
		inju_target.add(0.7);
		inju_target.add(0.375);// 左小腿.10
		inju_target.add(0.7);
		inju_target.add(0.68);// 右上臂.16
		inju_target.add(0.33);
		inju_target.add(0.31);// 左上臂.14
		inju_target.add(0.33);
		inju_target.add(0.76);// 右前臂.20
		inju_target.add(0.38);
		inju_target.add(0.22);// 左前臂.18
		inju_target.add(0.38);
		inju_target.add(0.89);// 右手.24
		inju_target.add(0.44);
		inju_target.add(0.1);// 左手.22
		inju_target.add(0.43);
	}

	private void setNFC() {
		// 取得該設備預設的無線感應裝置
		nfcAdapter = NfcAdapter.getDefaultAdapter(this);
		if (nfcAdapter == null) {
			toast(getString(R.string.s_ts_9), this);
			Loding(true);
		} else {
			nfc = true;
			// 註冊讓該Activity負責處理所有接收到的NFC Intents。
			gNfcPendingIntent = PendingIntent.getActivity(this, 0,
					// 指定該Activity為應用程式中的最上層Activity
					new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
			// 建立要處理的Intent Filter負責處理來自Tag或p2p交換的資料。
			IntentFilter ndefDetected = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
			try {
				ndefDetected.addDataType("text/plain");
			} catch (MalformedMimeTypeException e) {
			}
			gNdefExchangeFilters = new IntentFilter[] { ndefDetected };
		}
	}

	private void addNFC() {
		if (link) {
			out("NFC/" + setNFCString());
			toast(getString(R.string.s_ts_10), this);
		}
//		if (nfc) {
//			// 先停止接收任何的Intent，準備寫入資料至tag；
//			disableNdefExchangeMode();
//			// 啟動寫入Tag模式，監測是否有Tag進入
//			enableTagWriteMode();
//			// Create LinearLayout Dynamically
//			LinearLayout layout = new LinearLayout(this);
//			// Setup Layout Attributes
//			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
//					ViewGroup.LayoutParams.WRAP_CONTENT);
//			layout.setLayoutParams(params);
//			layout.setOrientation(LinearLayout.VERTICAL);
//			layout.addView(new mTextview(this, getString(R.string.s_ts_40)));
//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			builder.setView(layout).setOnCancelListener(new DialogInterface.OnCancelListener() {
//				public void onCancel(DialogInterface dialog) {
//					// 在取消模式下，先關閉監偵有Tag準備寫入的模式，再啟動等待資料交換的模式。
//					// 停止寫入Tag模式，代表已有Tag進入
//					disableTagWriteMode();
//					// 啟動資料交換
//					enableNdefExchangeMode();
//				}
//			});
//			// 顯示對話框，告知將Tag或手機靠近本機的NFC感應區
//			alertDialog = builder.create();
//			alertDialog.show();
//		}
		if (car) {
			updateCar();
		}
	}




	private class mTextview extends TextView {
		public mTextview(Context context, final String str) {
			super(context);
			this.setText(str);
			this.setTextSize(35);
			this.setTextColor(Color.BLACK);
			this.setGravity(Gravity.CENTER);
			this.setPadding(20, 20, 20, 20);
		}
	}

	private void checkItem(String msg) {
		String nb = getLine(msg, 0, '/');
		boolean run = false;
		// 有流水號比對方法
		if (nb.length() != 0) {
			for (int i = 0; i < medi_item.size(); i++) {
				String mmsg = medi_item.get(i);
				// 流水號
				if (nb.equals(getLine(mmsg, 0, '/'))) {
					// 流水號相同取代資料
					medi_item.remove(i);
					medi_item.add(msg);
					run = true;
					break;
				}
			}
			if (!run) {
				medi_item.add(msg);
			}
		} else {
			// 沒有流水號比對方法
			medi_item.add(msg);
		}
	}

	private void updateCar() {
		String num = "";
		if (leve_count != 5) {
			num += number;
		}
		String unit = mediCar_unit.getText().toString() + "";
		String brand = mediCar_brand.getText().toString() + "";
		out("CAR/" + unit + "/" + brand + "/" + status_count + "/" + num + "/|");
		Log.e("CAR / ", "03");
	}

	private String getPercent(Integer num, Integer totalPeople) {
		String ms = "";
		if (totalPeople != 0) {
			ms = "" + (num + 0.0) / (totalPeople + 0.0);
		} else {
			return "n/";
		}
		ms = ms.substring(0, 4);
		return ms;
		// NumberFormat nf = NumberFormat.getPercentInstance();
		// nf.setMinimumFractionDigits(2);// 控制保留小数点后几位，2：表示保留2位小数点
		// percent = nf.format(p3);
		// percent = percent.substring(0, percent.length() - 1);
	}

	@SuppressLint("ClickableViewAccessibility")
	public boolean onTouch(View v, MotionEvent event) {
//		switch (event.getAction()) {
//		case MotionEvent.ACTION_DOWN:
//			// 開始位置
//			downX = (int) event.getX();
//			downY = (int) event.getY();
//			break;
//		case MotionEvent.ACTION_MOVE:
//			// 移動位置
//			break;
//		case MotionEvent.ACTION_UP:
//			// 離開位置
//			metrics = getResources().getDisplayMetrics();
//			int mWidth = metrics.widthPixels; // 螢幕寬
//			int mHeight = metrics.heightPixels; // 螢幕長
//			// Toast.makeText(this, "W:" + mWidth + " H:" + mHeight,
//			// Toast.LENGTH_SHORT).show();
//			int upX = (int) event.getX();
//			int upY = (int) event.getY();
//			// 點擊事件
//			if (Math.abs(upX - downX) < 10 && Math.abs(upY - downY) < 10) {
//				Toast.makeText(this, "X:" + getPercent(upX, mWidth) + " Y:" + getPercent(upY, mHeight),
//						Toast.LENGTH_SHORT).show();
//			}
//			break;
//		default:
//			break;
//		}
		return true;
	}

	private void Dialog(boolean view) {
		if (view) {
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			dialog.setNeutralButton(getString(R.string.s_ts_46), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface arg0, int arg1) {
					startActivity(new Intent(MainActivity.this, InformationViewActivity.class));
				}
			});
			dialog.setNegativeButton(getString(R.string.s_ts_48), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface arg0, int arg1) {
					Intent intent = new Intent(MainActivity.this, CameraTwoActivity.class);
					startActivityForResult(intent, 1);
					// Ensure that there's a camera activity to handle the
					// intent
				}
			});
			dialog.show();
		} else {
			Intent intent = new Intent(MainActivity.this, CameraTwoActivity.class);
			startActivityForResult(intent, 1);
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
	private void upup(){
		if(h.equals("")){
			MH = 4;
		}
		else {
			MH = -1;
		}
		h=hosp_count;
	}

	private void setMain_6_ok(){
		if(link){
			out("GUI/"+ambu_guide.getText().toString()+"|");
		}
//						MainActivity.info_data.clear();
		fragmentE.save();
		fragmentI.save();
		fragmentI.load();

		LinearLayout_inju.setVisibility(View.GONE);
		LinearLayout_emrgn.setVisibility(View.GONE);
		main_6_other_load();

		main6_body_load();
	}

	private void setMain_5_ok(){
		date();
		fragmentN.save();
		fragmentY.save();
		fragmentM.save();
		HelpN_LinearLayout.setVisibility(View.GONE);
		HelpY_LinearLayout.setVisibility(View.GONE);
		Mediacal_LinearLayout.setVisibility(View.GONE);
		main_5_other_load();

		if (img) {
			MainActivity.info_photo = drawableToBitmap(main5_info_photo.getDrawable());
		}
		main5_out();
		Toast.makeText(this, getString(R.string.s_ts_62), Toast.LENGTH_LONG).show();
	}

	private Bitmap getScreenShot(){
		//藉由View來Cache全螢幕畫面後放入Bitmap
		View mView = getWindow().getDecorView();
		mView.setDrawingCacheEnabled(true);
		mView.buildDrawingCache();
		Bitmap mFullBitmap = mView.getDrawingCache();

		//取得系統狀態列高度
		Rect mRect = new Rect();
		getWindow().getDecorView().getWindowVisibleDisplayFrame(mRect);
		int mStatusBarHeight = mRect.top;

		//取得手機螢幕長寬尺寸
		int mPhoneWidth = getWindowManager().getDefaultDisplay().getWidth();
		int mPhoneHeight = getWindowManager().getDefaultDisplay().getHeight();

		//將狀態列的部分移除並建立新的Bitmap
		Bitmap mBitmap = Bitmap.createBitmap(mFullBitmap, 0, mStatusBarHeight, mPhoneWidth, mPhoneHeight - mStatusBarHeight);
		//將Cache的畫面清除
		mView.destroyDrawingCache();

		return mBitmap;
	}

	private void saveScreenshot(Bitmap mBitmap){
		String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pictures/EMRC/";
		Calendar now = new GregorianCalendar();
		SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
		String fileName = simpleDate.format(now.getTime());

		try {
			File file = new File(dir + fileName + ".jpg");
			FileOutputStream out = new FileOutputStream(file);
			mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.flush();
			out.close();
			//保存图片后发送广播通知更新数据库
			Uri uri = Uri.fromFile(file);
			sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}




	@SuppressLint({"InflateParams", "NewApi"})
	public void onClick(View v) {

		if (v == main5_info_photo) {
			Dialog(img);
		}
		if (v == menu_ok) {
			// 保存NFC至檢傷紀錄
			// toast(setNFCString(), this);
			if(leve_count == 5){
				toast("未設置傷患等級!", this);
			}
			else if(hosp_count.equals("")){
				toast("請選擇醫院",this);
			}else {
				readNFC=false;
				upup();
				checkItem(setNFCString());
				user_upload();
				addNFC();
			}
		}

		if(v==main_6_ok){
			fragmentV.save();

			if(leve_count == 5){
				toast("未設置傷患等級!", this);
			}
			else if(hosp_count.equals("")){
				toast("請選擇醫院",this);
			}else {
				readNFC=false;
				upup();
//				checkItem(setNFCString());
//				user_upload();
//				addNFC();
				out("UPU/"+MH+"/"+number+"|");
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						setMain_6_ok();
					}
				}, 500);
			}
		}

		if(v==main_5_ok){
			String other;
			if (info_data.size() > 0) {
				other = info_data.get(2);
				info_data.clear();
				info_data.add(main5_name.getText().toString());
				info_data.add(main5_age.getText().toString());
				info_data.add(other);
			} else {
				other = "";
				info_data.add(main5_name.getText().toString());
				info_data.add(main5_age.getText().toString());
				info_data.add(other);
			}
			if (main5_age.getText().toString().length() == 0) {
				MainActivity.info_age = -1+"";
			}else{
				MainActivity.info_age = main5_age.getText().toString();
			}
			identity = main5_identity.getText().toString();

			if(leve_count == 5){
				toast("未設置傷患等級!", this);
			}
			else if(hosp_count.equals("")){
				toast("請選擇醫院",this);
			}else {
				readNFC=false;
				upup();
				checkItem(setNFCString());
				user_upload();
				addNFC();
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						setMain_5_ok();
					}
				}, 500);
			}


		}

		if (v == menu_re) {
			user_upload();
			getReset(true);
			toast(getResources().getString(R.string.s_ts_60), this);
			pager1.setCurrentItem(0);
			//掃QRcode
			startActivityForResult(new Intent(MainActivity.this, CaptureActivity.class), 2);
		}

		if(v==main_5_up){
			//上傳截圖
			if (MainActivity.link) {
				MainActivity.out("b7");
				Tools.sandFile(getScreenShot());
			}
			saveScreenshot(getScreenShot());
			toast("已截圖",this);

//			//列印
//			PrintHelper photoPrinter = new PrintHelper(MainActivity.this);
//			photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
//			Bitmap bitmap = getScreenShot();
//			photoPrinter.printBitmap("main_5 print", bitmap);
		}
		if(v==main_6_up){
			//上傳截圖
			if (MainActivity.link) {
				MainActivity.out("b8");
				Tools.sandFile(getScreenShot());
			}
//			saveScreenshot(getScreenShot());
			toast("已截圖",this);

			//列印
//			PrintHelper photoPrinter = new PrintHelper(MainActivity.this);
//			photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
//			Bitmap bitmap = getScreenShot();
//			photoPrinter.printBitmap("main_6 print", bitmap);

		}

		if(v == ambu_accepted1){
			if (ambu_accepted1.isChecked()){
				ambu_accepted2.setChecked(false);
				ambu_accepted3.setChecked(false);
			}
		}
		if(v == ambu_accepted2){
			if (ambu_accepted2.isChecked()){
				ambu_accepted1.setChecked(false);
				ambu_accepted3.setChecked(false);
			}
		}
		if(v == ambu_accepted3){
			if (ambu_accepted3.isChecked()){
				ambu_accepted1.setChecked(false);
				ambu_accepted2.setChecked(false);
			}
		}
		if(v == HelpN_TextView){
			HelpN_LinearLayout.setVisibility(View.VISIBLE);
		}
		if(v == HelpY_TextView){
			HelpY_LinearLayout.setVisibility(View.VISIBLE);
		}
		if(v == Mediacal_TextView ){
			Mediacal_LinearLayout.setVisibility(View.VISIBLE);
		}

		if(v == allergy0){
			allergy0.setVisibility(View.GONE);
			allergy_check=false;
		}
		if(v == check){
			allergy0.setVisibility(View.VISIBLE);
			allergy_check=true;
		}

		if(v == TextView_emrgn){
			LinearLayout_emrgn.setVisibility(View.VISIBLE);
		}
		if(v == body_front  || v == body_back ){
			LinearLayout_inju.setVisibility(View.VISIBLE);
		}

		if(v == main5_level){
			startActivity(new Intent(this, LevelActivity.class));
		}
		if(v == main5_photo){
			startActivity(new Intent(this, PhotoActivity.class));
		}
		if(v == sign0){ //保管人簽名
			WritePadDialog writeTabletDialog = new WritePadDialog(
					MainActivity.this, new DialogListener() {
				@Override
				public void refreshActivity(Object object) {
					mSignBitmap = (Bitmap) object;
					sign0.setImageBitmap(mSignBitmap);
					if (MainActivity.link) {
						MainActivity.out("S0");
						Tools.sandSign(Tools.drawableToBitmap(sign0.getDrawable()));
					} else {
						Toast.makeText(MainActivity.this, getString(R.string.s_ts_38), Toast.LENGTH_SHORT).show();
					}
				}
			});
			writeTabletDialog.show();
//			pager1.setSlidingEnable(false); //ViewPager禁止滑動
		}
		if(v == sign1){ //指導簽名
			WritePadDialog writeTabletDialog = new WritePadDialog(
					MainActivity.this, new DialogListener() {
				@Override
				public void refreshActivity(Object object) {
					mSignBitmap = (Bitmap) object;
					sign1.setImageBitmap(mSignBitmap);
					if (MainActivity.link) {
						MainActivity.out("S1");
						Tools.sandSign(mSignBitmap);
					} else {
						Toast.makeText(MainActivity.this, getString(R.string.s_ts_38), Toast.LENGTH_SHORT).show();
					}
				}
			});
			writeTabletDialog.show();
//			pager1.setSlidingEnable(false); //ViewPager禁止滑動
		}
		if(v == sign2){ //救護人員1
			WritePadDialog writeTabletDialog = new WritePadDialog(
					MainActivity.this, new DialogListener() {
				@Override
				public void refreshActivity(Object object) {
					mSignBitmap = (Bitmap) object;
					sign2.setImageBitmap(mSignBitmap);
					if (MainActivity.link) {
						MainActivity.out("S2");
						Tools.sandSign(mSignBitmap);
					} else {
						Toast.makeText(MainActivity.this, getString(R.string.s_ts_38), Toast.LENGTH_SHORT).show();
					}
				}
			});
			writeTabletDialog.show();
//			pager1.setSlidingEnable(false); //ViewPager禁止滑動
		}
		if(v == sign3){ //救護人員2
			WritePadDialog writeTabletDialog = new WritePadDialog(
					MainActivity.this, new DialogListener() {
				@Override
				public void refreshActivity(Object object) {
					mSignBitmap = (Bitmap) object;
					sign3.setImageBitmap(mSignBitmap);
					if (MainActivity.link) {
						MainActivity.out("S3");
						Tools.sandSign(mSignBitmap);
					} else {
						Toast.makeText(MainActivity.this, getString(R.string.s_ts_38), Toast.LENGTH_SHORT).show();
					}
				}
			});
			writeTabletDialog.show();
//			pager1.setSlidingEnable(false); //ViewPager禁止滑動
		}
		if(v == sign4){ //救護人員3
			WritePadDialog writeTabletDialog = new WritePadDialog(
					MainActivity.this, new DialogListener() {
				@Override
				public void refreshActivity(Object object) {
					mSignBitmap = (Bitmap) object;
					sign4.setImageBitmap(mSignBitmap);
					if (MainActivity.link) {
						MainActivity.out("S4");
						Tools.sandSign(mSignBitmap);
					} else {
						Toast.makeText(MainActivity.this, getString(R.string.s_ts_38), Toast.LENGTH_SHORT).show();
					}
				}
			});
			writeTabletDialog.show();
//			pager1.setSlidingEnable(false); //ViewPager禁止滑動
		}
		if(v == sign5){ //醫護人員
			WritePadDialog writeTabletDialog = new WritePadDialog(
					MainActivity.this, new DialogListener() {
				@Override
				public void refreshActivity(Object object) {
					mSignBitmap = (Bitmap) object;
					sign5.setImageBitmap(drawStringonBitmap(mSignBitmap));
					if (MainActivity.link) {
						MainActivity.out("S5");
						Tools.sandSign(Tools.drawableToBitmap(sign5.getDrawable()));
					} else {
						Toast.makeText(MainActivity.this, getString(R.string.s_ts_38), Toast.LENGTH_SHORT).show();
					}
				}
			});
			writeTabletDialog.show();
//			pager1.setSlidingEnable(false); //ViewPager禁止滑動
		}
		if(v == sign6){ //拒絕送醫簽名
			WritePadDialog2 writeTabletDialog = new WritePadDialog2(
					MainActivity.this, new DialogListener() {
				@Override
				public void refreshActivity(Object object) {
					mSignBitmap = (Bitmap) object;
					sign6.setImageBitmap(mSignBitmap);
					if (MainActivity.link) {
						MainActivity.out("S6");
						Tools.sandSign(mSignBitmap);
					} else {
						Toast.makeText(MainActivity.this, getString(R.string.s_ts_38), Toast.LENGTH_SHORT).show();
					}
				}
			});
			writeTabletDialog.show();
//			pager1.setSlidingEnable(false); //ViewPager禁止滑動
		}
		if(v == sign7){ //病患簽名
			WritePadDialog writeTabletDialog = new WritePadDialog(
					MainActivity.this, new DialogListener() {
				@Override
				public void refreshActivity(Object object) {
					mSignBitmap = (Bitmap) object;
					sign7.setImageBitmap(drawStringonBitmap(mSignBitmap));
					if (MainActivity.link) {
						MainActivity.out("S7");
						Tools.sandSign(Tools.drawableToBitmap(sign7.getDrawable()));
					} else {
						Toast.makeText(MainActivity.this, getString(R.string.s_ts_38), Toast.LENGTH_SHORT).show();
					}
				}
			});
			writeTabletDialog.show();
//			pager1.setSlidingEnable(false); //ViewPager禁止滑動
		}

		 if (v == medi_clear) {
			 new AlertDialog.Builder(this).setTitle(getString(R.string.s_ts_11))
		 			.setPositiveButton(getString(R.string.s_ts_12), new	 DialogInterface.OnClickListener() {
						 public void onClick(DialogInterface arg0, int arg1) {
						 }
					}).setNegativeButton(getString(R.string.s_ts_13), new DialogInterface.OnClickListener() {
						 public void onClick(DialogInterface arg0, int arg1) {
							 medi_item.clear();
							 getFile("DATA.txt", "");
							 medi_clear.setVisibility(View.INVISIBLE);
							 user_upload();
						 }
			 		}).show();
		 }
		if (v == medi_swap) {
			if (link) {
				if (!dblist) {
//					// Patients List
//					medi_swap.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.fs_database));
//					medi_unit.setText(getString(R.string.s_List_2));
//					out("c2");
//					if (lead_item.size() != 0) {
//						lead_upload();
//					} else {
//						medi_qunt.setText("");
//					}
//					dblist = true;

					if(spinner_inc_code!=0){
						medi_swap.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.fs_database));
						medi_unit.setText(getString(R.string.s_List_2));
						medi_clear.setVisibility(View.INVISIBLE);
						out("cc2/"+spinner_inc_code);
//						inc_txt.setVisibility(View.VISIBLE);
						if (lead_item.size() > 0) {
							lead_upload();
						} else {
							medi_qunt.setText("");
						}
						dblist = true;
					}
					else{
//						toast("Please select an event",this);
						toast("請選擇事件",this);
					}
				} else {
					// Transport Record
					medi_swap.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.fs_ambulance));
					medi_unit.setText(getString(R.string.s_List_1));
					if (medi_item.size() > 0) {
						medi_clear.setVisibility(View.VISIBLE);
					} else {
						medi_clear.setVisibility(View.INVISIBLE);
					}
					user_upload();
				}
			} else {
				toast(getString(R.string.s_ts_50), this);
			}
		}


		// if (v == print_item_0) {
		// setEMRC(3);
		// emrc_count = 0;
		// uploadview(MainActivity.this);
		// }
		// if (v == print_item_1) {
		// setEMRC(0);
		// emrc_count = 0;
		// }
		// if (v == print_item_2) {
		// setEMRC(1);
		// emrc_count = 1;
		// }
		// if (v == print_item_3) {
		// setEMRC(1);
		// emrc_count = 2;
		// }
		// if (v == print_item_4) {
		// setEMRC(1);
		// emrc_count = 512;
		// }
		// if (v == print_item_5) {
		// setEMRC(1);
		// emrc_count = 8;
		// }
		// if (v == print_item_6) {
		// setEMRC(2);
		// emrc_count = 0;
		// }
		if (v == mediCar_btn0) { // 出勤
			selectCar(0, mediCar_btn0);
		}
		if (v == mediCar_btn1) { // 到場
			selectCar(1, mediCar_btn1);
		}
		if (v == mediCar_btn2) { // 離場
			selectCar(2, mediCar_btn2);
		}
		if (v == mediCar_btn3) { // 到院
			selectCar(3, mediCar_btn3);
		}
		if (v == mediCar_btn4) { // 離院
			selectCar(4, mediCar_btn4);
		}
		if (v == mediCar_btn5) { // 待命
			selectCar(5, mediCar_btn5);
			new AlertDialog.Builder(MainActivity.this).setTitle(getString(R.string.s_ts_29))
					.setPositiveButton(getString(R.string.s_ts_12), new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {

						}
					}).setNegativeButton(getString(R.string.s_ts_13), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface arg0, int arg1) {
					user_upload();
					getReset(true);
					pager1.setCurrentItem(1);
				}
			}).show();
		}
		if (v == mediCar_btn6) { // 取消
			selectCar(6, mediCar_btn6);
			new AlertDialog.Builder(MainActivity.this).setTitle(getString(R.string.s_ts_29))
					.setPositiveButton(getString(R.string.s_ts_12), new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {

						}
					}).setNegativeButton(getString(R.string.s_ts_13), new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {
							user_upload();
							getReset(true);
							pager1.setCurrentItem(1);
						}
					}).show();
		}
		if (v == mediCar_btn7) { // 拒絕
			selectCar(7, mediCar_btn7);
			new AlertDialog.Builder(MainActivity.this).setTitle(getString(R.string.s_ts_29))
					.setPositiveButton(getString(R.string.s_ts_12), new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {

						}
					}).setNegativeButton(getString(R.string.s_ts_13), new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {
							user_upload();
							getReset(true);
							pager1.setCurrentItem(1);
						}
					}).show();
		}
		if (v == mediCar_btn8) { // 導航
			if (!hosp_count.equals("")) {
				LocationActivity(1); // 導航到院
			} else {
				if (leve_count != 5) {
					// 有檢傷但未選擇醫院時
					new AlertDialog.Builder(MainActivity.this).setTitle(getString(R.string.s_ts_56))
							.setPositiveButton(getString(R.string.s_ts_12), new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface arg0, int arg1) {

								}
							}).setNegativeButton(getString(R.string.s_ts_13), new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface arg0, int arg1) {
									HSHandler.obtainMessage().sendToTarget();
								}
							}).show();
				} else {
					LocationActivity(0); // 開啟導航
				}
			}
		}
		if (v == mediCar_btn9) { // 錄影
			startActivity(new Intent(android.provider.MediaStore.INTENT_ACTION_VIDEO_CAMERA));
		}
		if (v == mediCar_btn10) { // 119
			startActivity(new Intent().setAction("android.intent.action.CALL").setData(Uri.parse("tel:119")));
		}
		if (v == mediCar_btn11) { // 110
			startActivity(new Intent().setAction("android.intent.action.CALL").setData(Uri.parse("tel:110")));
		}

		if (v == main_log) {
			dev_count--;
			if (dev_count == 10)
				toast("您只需完成剩餘的三個步驟，即可成為開發人員。", this);
			if (dev_count == 5)
				toast("您只需完成剩餘的二個步驟，即可成為開發人員。", this);
			if (dev_count == 1)
				toast("您只需完成剩餘的一個步驟，即可成為開發人員。", this);
			if (dev_count == 0) {
				toast("您現在已成為開發人員。", this);
				main_log.setVisibility(View.GONE);
				dev = true;
			}
		}

		if(v == emrc_t0){
//			myDialog(0);
			settime(0);
		}
		if(v == emrc_t1){
//			myDialog(1);
			settime(1);
		}
		if(v == emrc_t2){
//			myDialog(2);
			settime(2);
		}
		if(v == emrc_t3){
//			myDialog(3);
			settime(3);
		}
		if(v == emrc_t4){
//			myDialog(4);
			settime(4);
		}
		if(v == emrc_t5){
//			myDialog(5);
			settime(5);
		}
	}

	public static Bitmap drawStringonBitmap(Bitmap src) {
		Calendar c = Calendar.getInstance();
		int YEAR = c.get(Calendar.YEAR);
		int MONTH = c.get(Calendar.MONTH)+1;
		int DAY = c.get(Calendar.DAY_OF_MONTH);

		String cm,cd;
		if(MONTH<10){
			cm = "0"+MONTH;
		}else {
			cm = ""+MONTH;
		}
		if(DAY<10){
			cd = "0"+DAY;
		}else {
			cd = ""+DAY;
		}

		Bitmap result = Bitmap.createBitmap(1320, (int)(880*0.8), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(result);
		canvas.drawBitmap(src, 0, 0, null);
		Paint paint = new Paint();
		paint.setAlpha(50);
		paint.setColor(Color.BLUE);
		paint.setTextSize(140);
		paint.setAntiAlias(true);
		canvas.drawText(YEAR+"/"+cm+"/"+cd, 500, 650, paint);

		return result;
	}



	@SuppressLint("SetTextI18n")
	private void selectCar(int i, Button btn) {
		Calendar c = Calendar.getInstance();
		String YEAR = "" + c.get(Calendar.YEAR);
		int MONTH = c.get(Calendar.MONTH)+1;
		String DAY = "" + c.get(Calendar.DAY_OF_MONTH);
		String HOUR = "" + c.get(Calendar.HOUR_OF_DAY);
		String MIN = "" + c.get(Calendar.MINUTE);
		String SEC = "" + c.get(Calendar.SECOND);
		String mon = "";
		if (HOUR.length() < 2) {
			HOUR = "0" + HOUR;
		}
		if (MIN.length() < 2) {
			MIN = "0" + MIN;
		}
		if (SEC.length() < 2) {
			SEC = "0" + SEC;
		}
		if (MONTH < 10) {
			mon = "0" + MONTH;
		}else {
			mon = ""+MONTH;
		}
		if (DAY.length() < 2) {
			DAY = "0" + DAY;
		}
		emrc_time.set(i,HOUR+":"+MIN+":"+SEC);
		// 駕駛是否登入
		if (car) {
			status_count = i;
//			mediCar_static.setText("已"+btn.getText().toString());
			mediCar_static.setText(""+getString(cars_tring[i]));
			// 是否出勤,到場,離場,到院,離院
			if (i < 5) {
				// 是否未開啟GPS上傳功能
				if (!gps) {
					// 檢傷等級不可為空白
					if (leve_count != 5) {
						UIHandler.obtainMessage().sendToTarget();
					}
				}
			} else {
				// 待命,拒絕,取消
				gps = false;
			}
//			new Handler().postDelayed(new Runnable() {
//				@Override
//				public void run() {
//					updateCar();
//				}
//			}, 2000);
//			toast(getString(R.string.s_ts_15), this);
		}
		switch (i){
			case 0://出勤
				mediCar_btn0.setText(getString(cars_tring[i])+"\n"+HOUR+":"+MIN+":"+SEC);
				if(link && Car_btn0){
					out("CT1/"+ YEAR+"-"+mon+"-"+DAY+" "+HOUR+":"+MIN+":"+SEC+"/"+number+"/"+car_brand+"|");
					Car_btn0 = false;
				}
				break;
			case 1://到場
				mediCar_btn1.setText(getString(cars_tring[i])+"\n"+HOUR+":"+MIN+":"+SEC);
				if(link && Car_btn1){
					out("CT2/"+ YEAR+"-"+mon+"-"+DAY+" "+HOUR+":"+MIN+":"+SEC+"/"+number+"/"+car_brand+"|");
					Car_btn1 = false;
				}
				break;
			case 2:// 離場
				mediCar_btn2.setText(getString(cars_tring[i])+"\n"+HOUR+":"+MIN+":"+SEC);
				if(link && Car_btn2){
					out("CT3/"+ YEAR+"-"+mon+"-"+DAY+" "+HOUR+":"+MIN+":"+SEC+"/"+number+"/"+car_brand+"|");
					Car_btn2 = false;
				}
				break;
			case 3:// 到院
				mediCar_btn3.setText(getString(cars_tring[i])+"\n"+HOUR+":"+MIN+":"+SEC);
				if(link && Car_btn3){
					out("CT4/"+ YEAR+"-"+mon+"-"+DAY+" "+HOUR+":"+MIN+":"+SEC+"/"+number+"/"+car_brand+"|");
					Car_btn3 = false;
				}
				break;
			case 4:// 離院
				mediCar_btn4.setText(getString(cars_tring[i])+"\n"+HOUR+":"+MIN+":"+SEC);
				if(link && Car_btn4){
					out("CT5/"+ YEAR+"-"+mon+"-"+DAY+" "+HOUR+":"+MIN+":"+SEC+"/"+number+"/"+car_brand+"|");
					Car_btn4 = false;
				}
				break;
			case 5:// 待命
				mediCar_btn5.setText(getString(cars_tring[i])+"\n"+HOUR+":"+MIN+":"+SEC);
				if(link && Car_btn5){
					out("CT6/"+ YEAR+"-"+mon+"-"+DAY+" "+HOUR+":"+MIN+":"+SEC+"/"+number+"/"+car_brand+"|");
					Car_btn5 = false;
				}
				break;
			case 6:// 取消
				mediCar_btn6.setText(getString(cars_tring[i])+"\n"+HOUR+":"+MIN+":"+SEC);
				if(link && Car_btn6){
					out("CT7/"+ YEAR+"-"+mon+"-"+DAY+" "+HOUR+":"+MIN+":"+SEC+"/"+number+"/"+car_brand+"|");
					Car_btn6 = false;
				}
				break;
			case 7:// 拒絕
				mediCar_btn7.setText(getString(cars_tring[i])+"\n"+HOUR+":"+MIN+":"+SEC);
				if(link && Car_btn7){
					out("CT8/"+ YEAR+"-"+mon+"-"+DAY+" "+HOUR+":"+MIN+":"+SEC+"/"+number+"/"+car_brand+"|");
					Car_btn7 = false;
				}
				break;
		}
	}

	@SuppressLint("NewApi")
	private void myDialog(final int i) {
		final Calendar c = Calendar.getInstance();
		new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				String hour = hourOfDay + "";
				String min = minute + "";
				int second;
				if (hourOfDay < 10) {
					hour = "0" + hourOfDay;
				}
				if (minute < 10) {
					min = "0" + minute;
				}
				second = c.get(Calendar.SECOND);
				emrc_time.set(i,hour+":"+min+":"+second);
				date();
				switch (i){
					case 0:
						if(link){
							out("CAT/0/"+emrc_date.getText().toString()+" "+hour+":"+min+":"+second+"/"+number+"|");
						}
						break;
					case 1:
						if(link){
							out("CAT/1/"+emrc_date.getText().toString()+" "+hour+":"+min+":"+second+"/"+number+"|");
						}
						break;
					case 2:
						if(link){
							out("CAT/2/"+emrc_date.getText().toString()+" "+hour+":"+min+":"+second+"/"+number+"|");
						}
						break;
					case 3:
						if(link){
							out("CAT/3/"+emrc_date.getText().toString()+" "+hour+":"+min+":"+second+"/"+number+"|");
						}
						break;
					case 4:
						if(link){
							out("CAT/4/"+emrc_date.getText().toString()+" "+hour+":"+min+":"+second+"/"+number+"|");
						}
						break;
					case 5:
						if(link){
							out("CAT/5/"+emrc_date.getText().toString()+" "+hour+":"+min+":"+second+"/"+number+"|");
						}
						break;
				}
			}
		},c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE),true).show();
	}
	private void settime(final int i){
		View view = View.inflate(MainActivity.this, R.layout.time_dialog, null);
		final NumberPicker numberPickerHour = (NumberPicker) view.findViewById(R.id.numpicker_hours);
		numberPickerHour.setMaxValue(23);
		numberPickerHour.setValue(sharedPreferences.getInt("Hours", 0));
		final NumberPicker numberPickerMinutes = (NumberPicker) view.findViewById(R.id.numpicker_minutes);
		numberPickerMinutes.setMaxValue(59);
		numberPickerMinutes.setValue(sharedPreferences.getInt("Minutes", 0));
		final NumberPicker numberPickerSeconds = (NumberPicker) view.findViewById(R.id.numpicker_seconds);
		numberPickerSeconds.setMaxValue(59);
		numberPickerSeconds.setValue(sharedPreferences.getInt("Seconds", 0));
		Button cancel = (Button) view.findViewById(R.id.cancel);
		Button ok = (Button) view.findViewById(R.id.ok);
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		builder.setView(view);
		final AlertDialog alertDialog = builder.create();

		cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				alertDialog.dismiss();
			}
		});
		ok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String hour="",min = "",sec="";
				hour = numberPickerHour.getValue()+"";
				min = numberPickerMinutes.getValue()+"";
				sec = numberPickerSeconds.getValue()+"";
				if(numberPickerHour.getValue() <10){
					hour = "0"+numberPickerHour.getValue();
				}
				if(numberPickerMinutes.getValue() <10){
					min = "0"+numberPickerMinutes.getValue();
				}
				if(numberPickerSeconds.getValue() <10){
					sec = "0"+numberPickerSeconds.getValue();
				}
				emrc_time.set(i,hour+":"+min+":"+sec);
				date();
				switch (i){
					case 0:
						if(link){
							out("CAT/0/"+emrc_date.getText().toString()+" "+hour+":"+min+":"+sec+"/"+number+"|");
						}
						break;
					case 1:
						if(link){
							out("CAT/1/"+emrc_date.getText().toString()+" "+hour+":"+min+":"+sec+"/"+number+"|");
						}
						break;
					case 2:
						if(link){
							out("CAT/2/"+emrc_date.getText().toString()+" "+hour+":"+min+":"+sec+"/"+number+"|");
						}
						break;
					case 3:
						if(link){
							out("CAT/3/"+emrc_date.getText().toString()+" "+hour+":"+min+":"+sec+"/"+number+"|");
						}
						break;
					case 4:
						if(link){
							out("CAT/4/"+emrc_date.getText().toString()+" "+hour+":"+min+":"+sec+"/"+number+"|");
						}
						break;
					case 5:
						if(link){
							out("CAT/5/"+emrc_date.getText().toString()+" "+hour+":"+min+":"+sec+"/"+number+"|");
						}
						break;
				}
				SharedPreferences.Editor editor = sharedPreferences.edit();
				editor.putInt("Hours", numberPickerHour.getValue());
				editor.putInt("Minutes", numberPickerMinutes.getValue());
				editor.putInt("Seconds", numberPickerSeconds.getValue());
				editor.apply();
				alertDialog.dismiss();
			}
		});
		alertDialog.show();
	}

	public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
		// parent = 事件發生的母體 spinner_items
		// position = 被選擇的項目index = parent.getSelectedItemPosition()
		// id = row id，通常給資料庫使用
		if (parent == spinner){
			if (position != 0) {
				String Treatment = parent.getSelectedItem().toString();
				if (!user) {
					Toast.makeText(this, getString(R.string.s_ts_18) + Treatment, Toast.LENGTH_SHORT).show();
				}
			}
			hosp_count = Treatment_unit.get(position).area;
		}

		if(parent == spinner_hos){
			if (position != 0) {
				String Treatment = parent.getSelectedItem().toString();
				if (!user) {
					Toast.makeText(this, getString(R.string.s_ts_18) + Treatment, Toast.LENGTH_SHORT).show();
				}
			}
			hosp_count = Treatment_unit.get(position).area;
		}
		if(parent == spinner_hos2){
			ambu_reason1 = position;
		}
		if (parent == spinner_hos3){
			if(position == 6){
				final AlertDialog.Builder editDialog = new AlertDialog.Builder(MainActivity.this);
				editDialog.setTitle("空跑原因");
				final EditText editText = new EditText(MainActivity.this);
				editDialog.setView(editText);
				editDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						Reason_unit.set(7,editText.getText().toString());
						ambu_reason2 = 7;
						ambu_reason_txt = editText.getText().toString();
						spinner_hos3.setAdapter(new ArrayAdapter<String>(MainActivity.this, R.layout.style_spinner, Reason_unit));
						spinner_hos3.setSelection(7);
					}
				});
				editDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
					}
				});
				editDialog.show();
			}
			if(position != 6 && position != 7){
				ambu_reason2 = position;
				ambu_reason_txt = "";
			}
		}
		if (parent == spinner_main5_gender){
			gender = 2-position;
		}

		if (parent == incident_spinner && position !=0){
			spinner_inc_code = Integer.parseInt(incident_code.get(position));
			out("cc2/"+spinner_inc_code);
			// Patients Record
//			inc_txt.setVisibility(View.GONE);
		}
		if (parent == incident_spinner && position ==0){
			spinner_inc_code = 0;
			Log.e("incident_spinner = ",spinner_inc_code+"");
		}
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		Toast.makeText(this, getString(R.string.s_ts_19), Toast.LENGTH_LONG).show();
	}

	// private void setEMRC(int c) {
	// leve_count = c;
	// // 傷患等極大於輕傷
	// if (c != 3) {
	// startActivity(new Intent(MainActivity.this, EMRCActivity.class));
	// }
	// }

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (parent == menu_list) {
			start(position);
		}
		if (parent == medi_list) {
			String pms;
			if (dblist && position != 0) {
				pms = lead_item.get(position);
			} else {
				//倒序
//				for (int i = medi_item.size()-1; i >=0 ; i--) {
//					aListView.add(medi_item.get(i));
//				}
//				for (int i = 0; i < MyListAdapter2.aListView.size(); i++) {
//					aListView.add(MyListAdapter2.aListView.get(i));
//				}
				pms = MyListAdapter2.aListView.get(position);
			}
			final String mmsg = pms;
			new AlertDialog.Builder(this).setTitle(getString(R.string.s_ts_20))
					.setPositiveButton(getString(R.string.s_ts_12), new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {
						}
					}).setNegativeButton(getString(R.string.s_ts_13), new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {
							getNFCString(mmsg);
							checkItem(setNFCString());
							load_data();
//							main_6_other_load();
//							fragmentE = new EmrgnFragment();
//							fragmentV = new VitalFragment();
//							fragmentY = new HelpYFragment();
//							decide_help =0;
						}
					}).show();
		}
	}

	public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
		if (!dblist) {
			new AlertDialog.Builder(this).setTitle(getString(R.string.s_ts_11))
					.setPositiveButton(getString(R.string.s_ts_12), new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {
						}
					}).setNegativeButton(getString(R.string.s_ts_13), new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {
							medi_item.remove(medi_item.size()-1-position);
							user_upload();
							if (user) {
								if (medi_item.size() == 0) {
									getFile("DATA.txt", "");
								}
							}
						}
					}).show();
		}
		return true;
	}

	private void disableNdefExchangeMode() {
		nfcAdapter.disableForegroundNdefPush(this);
		nfcAdapter.disableForegroundDispatch(this);
	}

	private void enableTagWriteMode() {
		write = true;
		IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
		gWriteTagFilters = new IntentFilter[] { tagDetected };
		nfcAdapter.enableForegroundDispatch(this, gNfcPendingIntent, gWriteTagFilters, null);
	}

	private void disableTagWriteMode() {
		write = false;
		nfcAdapter.disableForegroundDispatch(this);
	}

	private void enableNdefExchangeMode() {
		// 讓NfcAdatper啟動前景Push資料至Tag或應用程式。
		nfcAdapter.enableForegroundNdefPush(this, getNoteAsNdef());

		// 讓NfcAdapter啟動能夠在前景模式下進行intent filter的dispatch。
		nfcAdapter.enableForegroundDispatch(this, gNfcPendingIntent, gNdefExchangeFilters, null);
	}

	private NdefMessage getNoteAsNdef() {
		// 啟動Ndef交換資料模式。
		byte[] textBytes = Encrypt(setNFCString()).getBytes();
		NdefRecord textRecord = new NdefRecord(NdefRecord.TNF_MIME_MEDIA, "text/plain".getBytes(), new byte[] {},
				textBytes);
		return new NdefMessage(new NdefRecord[] { textRecord });
	}

	private void getNFCString(String tag) {
		if (tag.length() > 0 || tag.indexOf('|') != -1) {
			getReset(false);
			int tmp = 0;
			String msg = tag;
			String numbers = msg.substring(tmp).substring(0, msg.substring(tmp).indexOf('/'));
			tmp += numbers.length() + 1;
			number = numbers;
			int genders = Integer.parseInt(msg.substring(tmp).substring(0, msg.substring(tmp).indexOf('/')));
			tmp += 2;
			gender = genders;
			String info = msg.substring(tmp).substring(0, msg.substring(tmp).indexOf('║'));
			tmp += info.length() + 1;
			Tools.getListString(info, info_data, 3);
			String inju_f = msg.substring(tmp).substring(0, msg.substring(tmp).indexOf('║'));
			tmp += inju_f.length() + 1;
			Tools.getListString(inju_f, inju_front, 0);
			String inju_b = msg.substring(tmp).substring(0, msg.substring(tmp).indexOf('║'));
			tmp += inju_b.length() + 1;
			Tools.getListString(inju_b, inju_back, 0);
			String vita = msg.substring(tmp).substring(0, msg.substring(tmp).indexOf('║'));
			tmp += vita.length() + 1;
			Tools.getListString(vita, vita_data, 18);
			int leve = Integer.parseInt(msg.substring(tmp).substring(0, msg.substring(tmp).indexOf('/')));
			tmp += 2;
			leve_count = leve;
			int emrc = Integer.parseInt(msg.substring(tmp).substring(0, msg.substring(tmp).indexOf('/')));
			tmp += (emrc + "").length() + 1;
			emrc_count = emrc;
			String hosp =msg.substring(tmp).substring(0, msg.substring(tmp).indexOf('/'));
			tmp += (hosp + "").length() + 1;
			try {
				hosp_count = Treatment_unit.get(Integer.parseInt(hosp)).area;
			} catch (Exception e) {
				hosp_count = hosp;
			}
			h=hosp_count;
			if (msg.substring(tmp).indexOf('/') > 0) {
				String iden = msg.substring(tmp).substring(0, msg.substring(tmp).indexOf('/'));
				identity = iden;
				tmp += (iden + "").length();
			}
			tmp ++;
			if (msg.substring(tmp).indexOf('/') > 0) {
				String inc = msg.substring(tmp).substring(0, msg.substring(tmp).indexOf('/'));// 事件
				tmp += (inc + "").length();
				inc_code = Integer.parseInt(inc);
				Log.e("inc_code = ",inc_code+"");
			}
			tmp++;
			if (msg.substring(tmp).indexOf('/') > 0) {
				String age = msg.substring(tmp).substring(0, msg.substring(tmp).indexOf('/'));// 年齡
				tmp += (age + "").length();
				info_age = age;
			}
			tmp++;
			if (msg.substring(tmp).indexOf('/') > 0) {
				String mh = msg.substring(tmp).substring(0, msg.substring(tmp).indexOf('/'));// MH
				tmp += (mh + "").length();
				MH = Integer.parseInt(mh);
				Log.e("MH = ", mh+"");
			}
			tmp++;
			if (msg.substring(tmp).indexOf('/') > 0) {
				String QRC = msg.substring(tmp).substring(0, msg.substring(tmp).indexOf('/'));// QRcode
				tmp += (QRC + "").length();
				QRcode = QRC;
				Log.e("MH = ", QRC+"");
			}
			tmp++;
			if (msg.substring(tmp).indexOf('║') > 0) {
				String ien = msg.substring(tmp).substring(0, msg.substring(tmp).indexOf('║'));
				emrgn = ien;
			}
			uploadview(MainActivity.this);
			if (link) {
				if (numbers.length() < 2) {
					// 無流水號
					out("a0");
					// 上傳資料
					UPHandler.obtainMessage().sendToTarget();
				} else {
					// 搜尋照片
					out(number);
				}
			}
			if (user) {
				pager1.setCurrentItem(1);
			} else {
				pager1.setCurrentItem(0);
				if (link) {
					out("c0");
					if (hosp_count.equals("")) {
						out("c3");
					}
				}
			}
		} else {
			toast(getString(R.string.s_ts_0), this);
		}
		// user_name = null;
	}

	public static void uploadview(Context con) {
		if (number.length() > 2) {
//			print_No.setText(number + "");
			menu_No.setText(number + "");
			help_No.setText(number + "");
		} else {
//			print_No.setText("Not obtained.");
			menu_No.setText("Not obtained.");
			help_No.setText("Not obtained.");
		}
		if (info_data.size() > 2) {
			String name = info_data.get(0);
			if (name.length() == 0) {
				name = con.getResources().getString(R.string.info_unknow);
			}
			switch (gender) {
			case 0:
				name += "," + con.getResources().getString(R.string.info_femal);
				break;
			case 1:
				name += "," + con.getResources().getString(R.string.info_male);
				break;
			default:
				break;
			}
			menu_item.set(0, con.getResources().getString(R.string.s_Menu_1) + "\n<" + name + ">");
		} else {
			menu_item.set(0, con.getResources().getString(R.string.s_Menu_1));
		}
		if (inju_front.size() > 0 || inju_back.size() > 0) {
			String title = "";
			if (inju_front.size() > 0) {
				for (int i = 0; i < inju_front.size(); i += 2) {
					Double x = inju_front.get(i), y = inju_front.get(i + 1);
					title += Tools.getInjury(con, x, y, 0);
				}
			}
			if (inju_back.size() > 0) {
				for (int i = 0; i < inju_back.size(); i += 2) {
					Double x = inju_back.get(i), y = inju_back.get(i + 1);
					title += Tools.getInjury(con, x, y, 1);
				}
			}
			if (title.length() > 1) {
				menu_item.set(2, con.getResources().getString(R.string.s_Menu_3) + "\n<" + title + ">");
			} else {
				menu_item.set(2, con.getResources().getString(R.string.s_Menu_3));
			}
		} else {
			menu_item.set(2, con.getResources().getString(R.string.s_Menu_3));
		}
		if (vita_data.size() > 0) {
			for (int i = 0; i < 3; i++) {
				String j = vita_data.get(i * 6);
				if (j.length() > 1) {
					menu_item.set(3, con.getResources().getString(R.string.s_Menu_4) + "\n<" + j + ">");
				}
			}
		} else {
			menu_item.set(3, con.getResources().getString(R.string.s_Menu_4));
		}
		if (leve_count == 5) {
			// String intArray[] = { "死亡", "極危險", "危險", "輕傷" };
			// menu_item.set(4, "檢傷分類 <" + intArray[leve_count] + ">");
			// } else {
		}
		if (phot_count > 0) {
			int cc = phot_count, set = 0;
			while (cc != 0) {
				if (cc - 32 >= 0) {
					cc -= 32;
					set++;
				} else if (cc - 16 >= 0) {
					cc -= 16;
					set++;
				} else if (cc - 8 >= 0) {
					cc -= 8;
					set++;
				} else if (cc - 4 >= 0) {
					cc -= 4;
					set++;
				} else if (cc - 2 >= 0) {
					cc -= 2;
					set++;
				} else {
					cc--;
					set++;
				}
			}
			menu_item.set(5,
					con.getResources().getString(R.string.s_Menu_6) + "\n<"
							+ con.getResources().getString(R.string.s_List_3) + set
							+ con.getResources().getString(R.string.s_List_4) + ">");
			photo_num.setText(""+set);
		} else {
			menu_item.set(5, con.getResources().getString(R.string.s_Menu_6));
			photo_num.setText("0");
		}
		if (emrc_count != 0) {
			if (menu_item.size() == 6) {
				menu_item.add(getEMRC(con, emrc_count, leve_count));
			} else {
				menu_item.set(6, getEMRC(con, emrc_count, leve_count));
			}
		} else {
			if (menu_item.size() == 7) {
				menu_item.remove(6);
			}
		}
		spinner.setSelection(getTrainsportSelection(hosp_count), false);

		menu_upload(con);
		if (user) {
			pager1.setCurrentItem(1);
		} else {
			pager1.setCurrentItem(0);
		}
		// if (c < 6) {
		// menu_list.setSelection(c);
		// }
	}

	public static String getEMRC(Context c, int count, int leve) {
		String msg = "";
		ArrayList<String> list_string = new ArrayList<String>();
		switch (leve) {
		case 0:
			list_string.add(c.getResources().getString(R.string.s_EMRC_00));
			list_string.add(c.getResources().getString(R.string.s_EMRC_01));
			list_string.add(c.getResources().getString(R.string.s_EMRC_02));
			list_string.add(c.getResources().getString(R.string.s_EMRC_03));
			list_string.add(c.getResources().getString(R.string.s_EMRC_04));
			break;
		case 1:
			list_string.add(c.getResources().getString(R.string.s_EMRC_10));
			list_string.add(c.getResources().getString(R.string.s_EMRC_11));
			list_string.add(c.getResources().getString(R.string.s_EMRC_12));
			list_string.add(c.getResources().getString(R.string.s_EMRC_13));
			list_string.add(c.getResources().getString(R.string.s_EMRC_14));
			list_string.add(c.getResources().getString(R.string.s_EMRC_15));
			list_string.add(c.getResources().getString(R.string.s_EMRC_16));
			list_string.add(c.getResources().getString(R.string.s_EMRC_17));
			list_string.add(c.getResources().getString(R.string.s_EMRC_18));
			list_string.add(c.getResources().getString(R.string.s_EMRC_19));
			break;
		case 2:
			list_string.add(c.getResources().getString(R.string.s_EMRC_20));
			list_string.add(c.getResources().getString(R.string.s_EMRC_21));
			list_string.add(c.getResources().getString(R.string.s_EMRC_22));
			list_string.add(c.getResources().getString(R.string.s_EMRC_23));
			list_string.add(c.getResources().getString(R.string.s_EMRC_24));
			list_string.add(c.getResources().getString(R.string.s_EMRC_25));
			list_string.add(c.getResources().getString(R.string.s_EMRC_26));
			break;
		}
		int getcheck = count;
		for (int i = 9; i >= 0; i--) {
			if (getcheck >= (1 << i)) {
				getcheck -= (1 << i);
				if (msg.length() < 1) {
					msg += (list_string.get(i));
				} else {
					msg += ("\n" + list_string.get(i));
				}
			}
		}
		return msg;
	}

	private void main5_out(){
		String accident_car="";
		String allergy="";
		String complaint="";
		String accepted = "";
		String ambu_place = "";
		//事故車輛
		if(accident_car1.getText().length()>0){
			accident_car += accident_car1.getText().toString()+"/";
		}else {
			accident_car += "null/";
		}
		if(accident_car2.getText().length()>0){
			accident_car += accident_car2.getText().toString()+"/";
		}else {
			accident_car += "null/";
		}
		if(accident_car3.getText().length()>0){
			accident_car += accident_car3.getText().toString()+"/";
		}else {
			accident_car += "null/";
		}
		if(accident_car4.getText().length()>0){
			accident_car += accident_car4.getText().toString();
		}else {
			accident_car += "null";
		}
		//過敏藥物
		if(allergy1.getText().length()>0){
			allergy += allergy1.getText().toString()+"/";
		}else {
			allergy += "null/";
		}
		//過敏食物
		if(allergy2.getText().length()>0){
			allergy += allergy2.getText().toString()+"/";
		}else {
			allergy += "null/";
		}
		//過敏其他
		if(allergy3.getText().length()>0){
			allergy += allergy3.getText().toString();
		}else {
			allergy += "null";
		}
		if(allergy_check){
			allergy = "1/"+allergy;
		}else {
			allergy = "0/"+allergy;
		}
		if(ambu_complaint0.isChecked()){
			complaint += "1/";
		}
		else {
			complaint += "0/";
		}
		if(ambu_complaint1.isChecked()){
			complaint += "1/";
		}
		else {
			complaint += "0/";
		}
		if(ambu_complaint2.isChecked()){
			complaint += "1/";
		}
		else {
			complaint += "0/";
		}
		if(ambu_complaint3.isChecked()){
			complaint += "1/";
		}
		else {
			complaint += "0/";
		}
		if(ambu_complaint.getText().length()>0){
			complaint += ambu_complaint.getText();
		}
		else {
			complaint += "null";
		}
		if(ambu_accepted1.isChecked()){
			accepted += "1";
		}
		if(ambu_accepted2.isChecked()){
			accepted += "2";
		}
		if(ambu_accepted3.isChecked()){
			accepted += "3";
		}
		ambu_place = ambu_place1.getText()+"/"+ambu_place2.getText();


		if(link){
			out("ACC/"+accident_car+"|");
			out("RES/"+ambu_reason1+"/"+ambu_reason2+"/"+ambu_reason_txt+"|");
			out("COM/"+complaint+"|");
			out("acc/"+accepted+"/"+ambu_place+"/"+ambu_property.getText().toString()+"|");
			out("ALL/"+allergy+"|");
		}
	}

	private static void setViewColor(TextView view, int count) {
		switch (count) {
		case 0:
			view.setTextColor(Color.WHITE);
			menu_list.setBackgroundColor(Color.BLACK);
			break;
		case 1:
			view.setTextColor(Color.WHITE);
			menu_list.setBackgroundColor(Color.RED);
			break;
		case 2:
			view.setTextColor(Color.BLACK);
			menu_list.setBackgroundColor(Color.YELLOW);
			break;
		case 3:
			view.setTextColor(Color.BLACK);
			menu_list.setBackgroundColor(Color.GREEN);
			break;
		default:
			view.setTextColor(Color.BLACK);
			menu_list.setBackgroundColor(Color.WHITE);
			break;
		}
	}

	public static void setButtonColor(int count) {
		switch (count) {
			case 0:
				main5_level.setBackgroundColor(Color.BLACK);
				break;
			case 1:
				main5_level.setBackgroundColor(Color.RED);
				break;
			case 2:
				main5_level.setBackgroundColor(Color.YELLOW);
				break;
			case 3:
				main5_level.setBackgroundColor(Color.GREEN);
				break;
			default:
				main5_level.setBackgroundColor(Color.WHITE);
				break;
		}
	}

	private void start(int item) {
		switch (item) {
		case 0:
			startActivity(new Intent(this, InformationActivity.class));
			break;
		case 1:
			startActivity(new Intent(this, EmrgnActivity.class));
			break;
		case 2:
			startActivity(new Intent(this, InjuredActivity.class));
			break;
		case 3:
			startActivity(new Intent(this, VitalActivity.class));
			break;
		case 4:
			startActivity(new Intent(this, LevelActivity.class));
			break;
		case 5:
			startActivity(new Intent(this, PhotoActivity.class));
			break;
		case 6:
			startActivity(new Intent(this, EMRCActivity.class));
			break;
		}
	}

	private void getReset(boolean a0) {
		number = "";
		gender = 2;
		identity = "";
		emrgn = "";
		leve_count = 5;
		phot_count = 0;
		emrc_count = 0;
		hosp_count = "";
		info_photo = null;
		info_data.clear();
		inju_front.clear();
		inju_back.clear();
		vita_data.clear();
		phot_bitmap.clear();
		sign_bitmap.clear();
		ambu_place2.setText("");
		ambu_place1.setText("");
		ambu_property.setText("");
		ambu_guide.setText("");
		helpy = "null";
		helpn = "null";
		medical = "null";
		QRcode = "";
		ambu_complaint0.setChecked(false);
		ambu_complaint1.setChecked(false);
		ambu_complaint2.setChecked(false);
		ambu_complaint3.setChecked(false);
		ambu_accepted1.setChecked(false);
		ambu_accepted2.setChecked(false);
		ambu_accepted3.setChecked(false);

		Car_btn0 = true;
		Car_btn1 = true;
		Car_btn2 = true;
		Car_btn3 = true;
		Car_btn4 = true;
		Car_btn5 = true;
		Car_btn6 = true;
		Car_btn7 = true;

		for(int i=0;i<=7;i++){
			sign_bitmap.add(null);
		}
		for(int i=0;i<8;i++){
			emrc_time.set(i,getString(R.string.vita_time_hint));
		}
		spinner_hos2.setSelection(0);
		spinner_hos3.setSelection(0);

		fragmentE = new EmrgnFragment();
		fragmentV = new VitalFragment();
		fragmentY = new HelpYFragment();
		fragmentN = new HelpNFragment();
		fragmentM = new MedicalFragment();

		decide_help =0;
		mediCar_btn0.setText(getString(cars_tring[0]));
		mediCar_btn1.setText(getString(cars_tring[1]));
		mediCar_btn2.setText(getString(cars_tring[2]));
		mediCar_btn3.setText(getString(cars_tring[3]));
		mediCar_btn4.setText(getString(cars_tring[4]));
		mediCar_btn5.setText(getString(cars_tring[5]));
		mediCar_btn6.setText(getString(cars_tring[6]));
		mediCar_btn7.setText(getString(cars_tring[7]));
		date();
		sign0.setImageBitmap(sign_bitmap.get(0));
		sign1.setImageBitmap(sign_bitmap.get(1));
		sign2.setImageBitmap(sign_bitmap.get(2));
		sign3.setImageBitmap(sign_bitmap.get(3));
		sign4.setImageBitmap(sign_bitmap.get(4));
		sign5.setImageBitmap(sign_bitmap.get(5));
		sign6.setImageBitmap(sign_bitmap.get(6));
		sign7.setImageBitmap(sign_bitmap.get(7));



		if (a0) {
			menu_item.clear();
			menu_item.add(getString(R.string.s_Menu_1)); // information
			menu_item.add(getString(R.string.s_Menu_2)); // emergency
			menu_item.add(getString(R.string.s_Menu_3)); // injured
			menu_item.add(getString(R.string.s_Menu_4)); // vital signs
			menu_item.add(getString(R.string.s_Menu_5)); // level
			menu_item.add(getString(R.string.s_Menu_6)); // photo
															// evidence
			menu_upload(this);
			spinner.setSelection(getTrainsportSelection(hosp_count), true);
			if (net && link) {
				out("a0");
			} else {
//				print_No.setText(getString(R.string.id_000004));
				menu_No.setText(getString(R.string.id_000004));
				help_No.setText(getString(R.string.id_000004));

			}
		}
	}

	@SuppressLint("TrulyRandom")
	private String Encrypt(String sSrc) {
		String s = "";
		try {
			byte[] raw = "AIzaSyCpvaLBjUPz".getBytes();
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES");// "算法/模式/补码方式"
			IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			s = Base64.encodeToString(cipher.doFinal(sSrc.getBytes()), android.util.Base64.NO_WRAP);
		} catch (Exception e) {
		}
		return s;
	}

	private boolean checkData(String sSrc) {
		if (sSrc.indexOf('|') != -1) {
			// Normal data
			return false;
		} else {
			// Encrypted data
			return true;
		}
	}

	//解密
	private String Decrypt(String sSrc) {
		String s = "";
		try {
			byte[] raw = "AIzaSyCpvaLBjUPz".getBytes();
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			s = new String(cipher.doFinal(Base64.decode(sSrc, android.util.Base64.NO_WRAP)));
		} catch (Exception e) {
		}
		return s;
	}

	//QRcode_解密
	private String QRcode_Decrypt(String sSrc) {
		String s = "";
		try {
			byte[] raw = "AIzaSyCpvaLBjUPz".getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			s = new String(cipher.doFinal(Base64.decode(sSrc, android.util.Base64.NO_WRAP)));
		} catch (Exception e) {
		}
		return s;
	}

	private String setNFCString() {
		return number + '/' + gender + '/' + Tools.getList(info_data, true) + '║' + inju_front.size() + '='
				+ Tools.getList(inju_front, false) + '║' + inju_back.size() + '=' + Tools.getList(inju_back, false)
				+ '║' + Tools.getList(vita_data, false) + '║' + leve_count + '/' + emrc_count + '/' + hosp_count + '/'
				+ identity + "/" + inc_code + "/"+ info_age + "/" + MH + "/" + QRcode + "/" + emrgn + "║|";
//		return number + '/' + gender + '/' + Tools.getList(info_data, true) + '║' + inju_front.size() + '='
//				+ Tools.getList(inju_front, false) + '║' + inju_back.size() + '=' + Tools.getList(inju_back, false)
//				+ '║' + Tools.getList(vita_data, false) + '║' + leve_count + '/' + emrc_count + '/' + "14" + '/'
//				+ identity + "/" + emrgn + "║|";
	}

	private boolean writeTag(NdefMessage message, Tag tag) {

		int size = message.toByteArray().length;
		try {
			Ndef ndef = Ndef.get(tag);
			if (ndef != null) {
				ndef.connect();

				if (!ndef.isWritable()) {
					// 標籤是只讀的
					toast(getString(R.string.s_ts_21), this);
					nfcClose(false);
					return false;
				}
				if (ndef.getMaxSize() < size) {
					// 標籤容量ndef.getMaxSize() bytes,訊息是size bytes
					toast(getString(R.string.s_ts_22), this);
					toast(getString(R.string.s_ts_23) + ndef.getMaxSize() + " bytes, " + getString(R.string.s_ts_24)
							+ size + " bytes.", this);
					nfcClose(false);
					return false;
				}

				ndef.writeNdefMessage(message);
				// 將消息寫入預格式化的標籤
				toast(getString(R.string.s_ts_25), this);
				nfcClose(true);
				return true;
			} else {
				NdefFormatable format = NdefFormatable.get(tag);
				if (format != null) {
					try {
						format.connect();
						format.format(message);
						// 格式化標籤和寫入資料
						toast(getString(R.string.s_ts_25), this);
						nfcClose(true);
						return true;
					} catch (IOException e) {
						// 無法格式化標籤
						toast(getString(R.string.s_ts_26), this);
						nfcClose(false);
						return false;
					}
				} else {
					// 標籤不支持NDEF
					toast(getString(R.string.s_ts_27), this);
					nfcClose(false);
					return false;
				}
			}
		} catch (Exception e) {
			// 無法寫入標籤
			toast(getString(R.string.s_ts_26), this);
			nfcClose(false);
			return false;
		}
	}

	private void nfcClose(boolean tag) {
		alertDialog.cancel();
		if (!tag) {
			toast(getString(R.string.s_ts_28), this);
		} else {
			if (user) {
				// 連線後,寫入成功
				new AlertDialog.Builder(MainActivity.this).setTitle(getString(R.string.s_ts_29))
						.setPositiveButton(getString(R.string.s_ts_12), new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {

							}
						}).setNegativeButton(getString(R.string.s_ts_13), new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
								user_upload();
								getReset(true);
							}
						}).show();
			}

		}
	}

	public static void toast(String text, Context con) {
		Toast.makeText(con, text + "", Toast.LENGTH_SHORT).show();
	}

	private void setNET() {
		// 取得通訊服務
		ConnectivityManager connectivity = (ConnectivityManager) getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			// 是否有連線
			Boolean IsConnected = false;
			// 依序檢查各種網路連線方式
			for (NetworkInfo network : connectivity.getAllNetworkInfo()) {
				if (network.getState() == NetworkInfo.State.CONNECTED) {
					IsConnected = true;
					// 顯示網路連線種類
					Toast.makeText(getApplicationContext(),
							getString(R.string.s_ts_30) + network.getTypeName() + " "+getString(R.string.s_ts_31),
							Toast.LENGTH_SHORT).show();
					net = true;
					ConnectAndSend();

				}
			}
			if (!IsConnected) {
				Toast.makeText(getApplicationContext(), getString(R.string.s_ts_32), Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(getApplicationContext(), getString(R.string.s_ts_32), Toast.LENGTH_SHORT).show();
		}
	}

	private void ConnectAndSend() {
		new Thread(new Runnable() {
			public void run() {
				try {
					// TODO TCP SSLSocket
					// 使用TLS協議
					SSLContext context = SSLContext.getInstance("TLS");

					// 服务器端需要验证的客户端证书 p12
					KeyStore keyManagers = KeyStore.getInstance("BKS");
					keyManagers.load(getResources().getAssets().open(KEY_STORE_CLIENT_PATH),
							KEY_STORE_PASSWORD.toCharArray());

					// 客户端信任的服务器端证书 bks
					KeyStore trustManagers = KeyStore.getInstance("BKS");
					trustManagers.load(getResources().getAssets().open(KEY_STORE_TRUST_PATH),
							KEY_STORE_PASSWORD.toCharArray());

					// 获得X509密钥库管理实例
					KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("X509");
					keyManagerFactory.init(keyManagers, KEY_STORE_PASSWORD.toCharArray());
					TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("X509");
					trustManagerFactory.init(trustManagers);

					// 初始化SSLContext
					context.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);

					// 获取SSLSocket
					skt = context.getSocketFactory().createSocket(ip, port);
					// OutputStream out = skt.getOutputStream();
					// out.write("Connection established.\n".getBytes());
					readData();


				} catch (UnrecoverableKeyException | CertificateException | NoSuchAlgorithmException | KeyStoreException
						| KeyManagementException | IOException e) {
					Log.e("SSL.ERROR", e.toString());
				}
			}
		}).start();
	}

	private void Loding(Boolean bl) {
//		Button p = (Button) main_view.get(0).findViewById(R.id.print_log);
		Button m = (Button) main_view.get(0).findViewById(R.id.menu_log);
		if (nfc) {
			if (bl) {
//				p.setVisibility(View.VISIBLE);
				m.setVisibility(View.VISIBLE);
				main_left.setVisibility(View.GONE);
				main_right.setVisibility(View.GONE);
			} else {
//				p.setVisibility(View.GONE);
				m.setVisibility(View.GONE);
			}
		} else {
//			p.setVisibility(View.VISIBLE);
			m.setVisibility(View.VISIBLE);
		}
	}

//	private void DateInput(Boolean i) {
//		try {
//			DataInputStream dis = new DataInputStream(skt.getInputStream());
//			int size = dis.readInt();
//			byte[] data = new byte[size];
//			int len = 0;
//			while (len < size) {
//				len += dis.read(data, len, size - len);
//			}
//			ByteArrayOutputStream outPut = new ByteArrayOutputStream();
//			Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
//			bitmap.compress(CompressFormat.JPEG, 100, outPut);
//			if (i) {
//				info_photo = Tools.compBitmap(bitmap);
//			} else {
//				phot_bitmap.add(Tools.compBitmap(bitmap));
//			}
//		} catch (IOException e) {
//		}
//	}


	private void DateInput(Boolean i) {
		Adler32 inChecker = new Adler32();
		CheckedDataInput in = null;
		try {
			DataInputStream dis = new DataInputStream(skt.getInputStream());
			int size = dis.readInt();
			in = new CheckedDataInput(new DataInputStream(skt.getInputStream()), (Checksum) inChecker);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] data = new byte[size];
			in.readFully(data);
			out.write(data);
			Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
			bitmap.compress(CompressFormat.JPEG, 100, out);
			if (i) {
				info_photo = bitmap;
			} else {
				phot_bitmap.add(bitmap);
			}
		} catch (IOException e) {
		}finally {
			if (MainActivity.info_photo != null) {
					main5_info_photo.setBackgroundResource(R.drawable.item_background);
					main5_info_photo.setImageBitmap(MainActivity.info_photo);
					img = true;
				}
		}
	}

	//載入簽名
	private void SignInput(int i) {
		Adler32 inChecker = new Adler32();
		CheckedDataInput in = null;
		try {
			DataInputStream dis = new DataInputStream(skt.getInputStream());
			int size = dis.readInt();
			in = new CheckedDataInput(new DataInputStream(skt.getInputStream()), (Checksum) inChecker);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] data = new byte[size];
			in.readFully(data);
			out.write(data);
			Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
			bitmap.compress(CompressFormat.JPEG, 100, out);
			switch (i){
				case 0:
					sign_bitmap.set(0,bitmap);
					break;
				case 1:
					sign_bitmap.set(1,bitmap);
					break;
				case 2:
					sign_bitmap.set(2,bitmap);
					break;
				case 3:
					sign_bitmap.set(3,bitmap);
					break;
				case 4:
					sign_bitmap.set(4,bitmap);
					break;
				case 5:
					sign_bitmap.set(5,bitmap);
					break;
				case 6:
					sign_bitmap.set(6,bitmap);
					break;
				case 7:
					sign_bitmap.set(7,bitmap);
					break;
			}
		} catch (IOException e) {
		}
	}

	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case 0:
			mProgressDialog = new ProgressDialog(this);
			mProgressDialog.setTitle(getString(R.string.s_ts_33)); // 设置标题
			mProgressDialog.setMessage(getString(R.string.s_ts_34) + ".."); // 设置body信息
			mProgressDialog.setMax(1); // 进度条最大值是100
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL); // 设置进度条样式是横向的
			return mProgressDialog;
		}
		return super.onCreateDialog(id);
	}

	public static void out(String msg) {
		if (net) {
			new MainSocketOutput(msg).start();
		}
	}

	public static String getLine(String msg, int run, char key) {
		return msg.substring(run).substring(0, msg.substring(run).indexOf(key));
	}

	public void onLocationChanged(Location location) { // 當地點改變時
		getLocation(location);
	}

	public void onProviderDisabled(String arg0) { // 當GPS或網路定位功能關閉時
		Toast.makeText(getBaseContext(), "Gps turned off ", Toast.LENGTH_LONG).show();
	}

	public void onProviderEnabled(String arg0) { // 當GPS或網路定位功能開啟
		Toast.makeText(getBaseContext(), "Gps turned on ", Toast.LENGTH_LONG).show();
	}

	public void onStatusChanged(String provider, int status, Bundle extras) { // 定位狀態改變
		// status=OUT_OF_SERVICE 供應商停止服務
		// status=TEMPORARILY_UNAVAILABLE 供應商暫停服務
	}

	@SuppressLint("MissingPermission")
	private void locationServiceInitial() {
		lms = (LocationManager) getSystemService(Context.LOCATION_SERVICE); // 取得系統定位服務
		Criteria criteria = new Criteria(); // 資訊提供者選取標準
		bestProvider = lms.getBestProvider(criteria, true); // 選擇精準度最高的提供者
		// 服務提供者、更新頻率60000毫秒=1分鐘、最短距離、地點改變時呼叫物件
		getLocation(lms.getLastKnownLocation(bestProvider));
	}

	private void getLocation(Location location) { // 將定位資訊顯示在畫面中
		if (location != null && gps) {
			Lngitude = (location.getLongitude()); // 取得經度
			Latitude = (location.getLatitude()); // 取得緯度
		}
	}

	private void outSMS() {
		out("SMS/" + car_brand + "/|");
	}

	private void outLocation() {
		boolean nbr = false;
		String La, Lo;
		La = String.valueOf(Latitude);
		Lo = String.valueOf(Lngitude);
		// 座標精準度確認
		if (La.length() > 4 && Lo.length() > 4 && medi_item.size() > 0) {
			for (int i = 0; i < medi_item.size(); i++) {
				String nbs = getLine(medi_item.get(i), 0, '/');
				out("GPS/" + nbs + "/" + La + "/" + Lo + "/|");
				if (nbs.equals(number)) {
					nbr = true;
				}
			}
		}
		if (La.length() > 4 && Lo.length() > 4 && !nbr) {
			out("GPS/" + number + "/" + La + "/" + Lo + "/|");
		}
	}

	private double gpsDistance(double lat_a, double lng_a, double lat_b, double lng_b) {
		double radLat1 = (lat_a * Math.PI / 180.0);
		double radLat2 = (lat_b * Math.PI / 180.0);
		double a = radLat1 - radLat2;
		double b = (lng_a - lng_b) * Math.PI / 180.0;
		double s = 2 * Math.asin(Math.sqrt(
				Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * 6378137.0;
		s = Math.round(s * 10000) / 10000;
		return s;
	}

	@SuppressWarnings("unused")
	private String DistanceText(double distance) {
		if (distance < 1000) {
			return String.valueOf((int) distance) + "m";
		} else {
			return new DecimalFormat("#.00").format(distance / 1000) + "km";
		}
	}

	private void LocationProvider() {
		// 取得系統定位服務
		LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
		if (service.isProviderEnabled(LocationManager.GPS_PROVIDER)
				|| service.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			// 如果GPS或網路定位開啟，呼叫locationServiceInitial()更新位置
			gps = true; // 確認開啟定位服務
			locationServiceInitial();
		} else {
			Toast.makeText(MainActivity.this, getString(R.string.s_ts_35), Toast.LENGTH_LONG).show();
			startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
		}
	}

	private void LocationActivity(int mode) {
		String language = "tw";
		if (EN) {
			language = "en";
		}
		String Url = "https://maps.google.com/maps?f=d&hl=" + language;
		if (mode == 1) {
			if (!hosp_count.equals("")) {
				// 啟動 Google地圖導航至指定醫院
				toast(getString(R.string.s_ts_36), MainActivity.this);
				String sLocation = Treatment_unit.get(getTrainsportSelection(hosp_count)).location;
				Url += "&saddr=&daddr=" + sLocation;
			}
		}
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Url));
		intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
		startActivity(intent);
	}

	private void readData() {
		// TODO readData
		// new Thread(new Runnable() {
		// public void run() {
		try {
			BufferedReader buf = new BufferedReader(new InputStreamReader(skt.getInputStream(), "UTF-8"));
			if (number.length() < 2) {
				out("a0");
			} else {
				out(number);
			}
			out("CHP/" + Treatment_unit.size());
			out("INC/");
			sktRun = true;
			// setServiec();
			String Msg;
			while ((Msg = buf.readLine()) != null && sktRun) {
				 Log.e("SSL.msg", Msg);
				link = true;
				if (Msg.length() == 2) {
					switch (Msg) {
					case "a1":
						// 客戶端交握
						break;
					case "s1":
						out("s1");
						break;
					case "b0":
						DateInput(true);
						break;
					case "b1":
						DateInput(false);
						phot_count++;
						break;
					case "b2":
						DateInput(false);
						phot_count += 2;
						break;
					case "b3":
						DateInput(false);
						phot_count += 4;
						break;
					case "b4":
						DateInput(false);
						phot_count += 8;
						break;
					case "b5":
						DateInput(false);
						phot_count += 16;
						break;
					case "b6":
						DateInput(false);
						phot_count += 32;
						break;
					case "b7":
						PhotoHandler.obtainMessage().sendToTarget();
						break;
					case "c2":
						if (dblist) {
							out("c2");
						}
						break;
					case "S0":
						// 照片
						SignInput(0);
						break;
					case "S1":
						// 照片
						SignInput(1);
						break;
					case "S2":
						// 照片
						SignInput(2);
						break;
					case "S3":
						// 照片
						SignInput(3);
						break;
					case "S4":
						// 照片
						SignInput(4);
						break;
					case "S5":
						// 照片
						SignInput(5);
						break;
					case "S6":
						// 照片
						SignInput(6);
						break;
					case "S7":
						// 照片
						SignInput(7);
						break;
					case "ST":
						reasonHandler.obtainMessage().sendToTarget();
						break;
					case "FI":
						finishHandler.obtainMessage().sendToTarget();
						break;
					default:
						break;
					}
				} else {
					// Msg.length() >= 3
					String tag = getLine(Msg, 0, '/');
					// 取得標籤
					switch (tag) {
					case "NO":
						String msgs = Msg.substring(3, Msg.indexOf('|'));
						number = msgs;
						NOHandler.obtainMessage().sendToTarget();
						break;
					case "DC":
						String DC = Msg.substring(3);
						if (!DC.equals("END")) {
							if (DC.length() > 10) {
								checkItem(DC);
							}
						} else {
							CPHandler.obtainMessage().sendToTarget();
						}
						break;
					case "C2":
						String C2 = Msg.substring(3);
						if (!C2.equals("END")) {
							String NUM = getLine(C2, 0, '/');
							if (NUM.length() < 5) {
								lead_item.clear();
							}
							lead_item.add(C2);
						} else {
							C2Handler.obtainMessage().sendToTarget();
						}
						break;
					case "C3":
						// 從後端取得傷患後自動導航
						// String C3 = Msg.substring(3);
						// if (!"0".equals(C3)) {
						// hosp_count = Integer.parseInt(C3);
						// if (hosp_count != 0) {
						// MSHandler.obtainMessage().sendToTarget();
						// }
						// } else {
						// HSHandler.obtainMessage().sendToTarget();
						// }
						break;
					case "C4":
						// 載入醫院
						String C4 = Msg.substring(3);
						if (!C4.equals("END")) {
							if (C4.equals("CL")) {
								Treatment_unit.clear();
								Treatment_unit.add(new hospital(getString(R.string.s_H), 0, "", "")); // 收治單位
							} else {
								Object[] strs = C4.split(",");
								Treatment_unit.add(new hospital((String) strs[0], Integer.parseInt((String) strs[1]),
										(String) strs[2] + "," + (String) strs[3], (String) strs[4]));
							}
						} else {
							C4Handler.obtainMessage().sendToTarget();
						}
						break;
					case "C5":
						qr_msg = Msg.substring(3);
						C5Handler.obtainMessage().sendToTarget();
						break;
					case "C6":
						String C6 = Msg.substring(3);
						if (!C6.equals("END")) {
							if (C6.equals("CL")) {
								incident_unit.clear();
								incident_unit.add("選擇事件");
								incident_code.add("0");
							} else {
								String[] strs = C6.split(",");
								incident_unit.add(strs[1]);
								incident_code.add(strs[0]);
							}
						} else {
							C6Handler.obtainMessage().sendToTarget();
						}
						break;
					case "M5":
						int tmp =3;
						String helpN_data = Msg.substring(tmp).substring(0, Msg.substring(tmp).indexOf(','));			//非創傷
						tmp += helpN_data.length()+1;
						helpn = helpN_data;
						String helpY_data = Msg.substring(tmp).substring(0, Msg.substring(tmp).indexOf(','));			//創傷
						tmp += helpY_data.length()+1;
						helpy = helpY_data;
						String medical_data = Msg.substring(tmp).substring(0, Msg.substring(tmp).indexOf('|'));			//過去病史
						medical = medical_data;
						tmp += medical_data.length()+1;
						String reason1 = Msg.substring(tmp).substring(0, Msg.substring(tmp).indexOf(','));				//送往醫院原因
						ambu_reason1 = Integer.valueOf(reason1);
						tmp += reason1.length() +1 ;
						String reason2 = Msg.substring(tmp).substring(0, Msg.substring(tmp).indexOf('/'));				//空跑原因
						ambu_reason2 = Integer.valueOf(reason2);
						tmp += reason2.length() +1 ;
						String reason_txt = Msg.substring(tmp).substring(0, Msg.substring(tmp).indexOf('|'));			//空跑原因_其他
						ambu_reason_txt = reason_txt;
						tmp += reason_txt.length() +1 ;
						String ambu_allergy0_data = Msg.substring(tmp).substring(0, Msg.substring(tmp).indexOf('/'));	//過敏病史_不曾
						tmp += ambu_allergy0_data.length() +1 ;
						db_allergy0 = Integer.valueOf(ambu_allergy0_data);
						String ambu_allergy1_data = Msg.substring(tmp).substring(0, Msg.substring(tmp).indexOf('/'));	//過敏病史_藥物
						tmp += ambu_allergy1_data.length() +1 ;
						db_allergy1 = ambu_allergy1_data;
						String ambu_allergy2_data = Msg.substring(tmp).substring(0, Msg.substring(tmp).indexOf('/'));	//過敏病史_食物
						tmp += ambu_allergy2_data.length() +1 ;
						db_allergy2 = ambu_allergy2_data;
						String ambu_allergy3_data = Msg.substring(tmp).substring(0, Msg.substring(tmp).indexOf('|'));	//過敏病史_其他
						tmp += ambu_allergy3_data.length() +1 ;
						db_allergy3 = ambu_allergy3_data;
						String ambu_accident1_data = Msg.substring(tmp).substring(0, Msg.substring(tmp).indexOf('/'));	//事故車輛1
						tmp += ambu_accident1_data.length() +1 ;
						db_accident_car1 = ambu_accident1_data;
						String ambu_accident2_data = Msg.substring(tmp).substring(0, Msg.substring(tmp).indexOf('/'));	//事故車輛2
						tmp += ambu_accident2_data.length() +1 ;
						db_accident_car2 = ambu_accident2_data;
						String ambu_accident3_data = Msg.substring(tmp).substring(0, Msg.substring(tmp).indexOf('/'));	//事故車輛3
						tmp += ambu_accident3_data.length() +1 ;
						db_accident_car3 = ambu_accident3_data;
						String ambu_accident4_data = Msg.substring(tmp).substring(0, Msg.substring(tmp).indexOf('|'));	//事故車輛4
						tmp += ambu_accident4_data.length() +1 ;
						db_accident_car4 = ambu_accident4_data;
						String ambu_complaint0_data = Msg.substring(tmp).substring(0, Msg.substring(tmp).indexOf('/'));	//病患主訴1
						tmp += ambu_complaint0_data.length() +1 ;
						db_ambu_complaint0 = Integer.valueOf(ambu_complaint0_data);
						String ambu_complaint1_data = Msg.substring(tmp).substring(0, Msg.substring(tmp).indexOf('/'));	//病患主訴2
						tmp += ambu_complaint1_data.length() +1 ;
						db_ambu_complaint1 = Integer.valueOf(ambu_complaint1_data);
						String ambu_complaint2_data = Msg.substring(tmp).substring(0, Msg.substring(tmp).indexOf('/'));	//病患主訴3
						tmp += ambu_complaint2_data.length() +1 ;
						db_ambu_complaint2 = Integer.valueOf(ambu_complaint2_data);
						String ambu_complaint3_data = Msg.substring(tmp).substring(0, Msg.substring(tmp).indexOf('/'));	//病患主訴4
						tmp += ambu_complaint3_data.length() +1 ;
						db_ambu_complaint3 = Integer.valueOf(ambu_complaint3_data);
						String ambu_complaint_data = Msg.substring(tmp).substring(0, Msg.substring(tmp).indexOf('|'));	//病患主訴_補述說明
						tmp += ambu_complaint_data.length() +1 ;
						db_ambu_complaint = ambu_complaint_data;
						String ambu_accepted_data = Msg.substring(tmp).substring(0, Msg.substring(tmp).indexOf('|'));	//受案單位
						tmp += ambu_accepted_data.length() +1 ;
						db_ambu_accepted = Integer.valueOf(ambu_accepted_data);
						String ambu_place1_data = Msg.substring(tmp).substring(0, Msg.substring(tmp).indexOf('|'));		//發生地點
						tmp += ambu_place1_data.length() +1 ;
						db_ambu_place1 = ambu_place1_data;
						String ambu_place2_data = Msg.substring(tmp).substring(0, Msg.substring(tmp).indexOf('|'));		//病人住址
						tmp += ambu_place2_data.length() +1 ;
						db_ambu_place2 = ambu_place2_data;
						String ambu_property_data = Msg.substring(tmp).substring(0, Msg.substring(tmp).indexOf('|'));	//財務明細
						tmp += ambu_property_data.length() +1 ;
						db_ambu_property = ambu_property_data;
						String ambu_guide_data = Msg.substring(tmp).substring(0, Msg.substring(tmp).indexOf('|'));		//線上指導
//						tmp += ambu_guide_data.length() +1 ;
						db_ambu_guide = ambu_guide_data;
						break;
					case "CT":
						int tmp1=3;
						String ambu_date_data = Msg.substring(tmp1).substring(0, Msg.substring(tmp1).indexOf(','));
						tmp1 += ambu_date_data.length()+1;
						ambu_date = ambu_date_data.substring(0,10);
						String CT1 = Msg.substring(tmp1).substring(0, Msg.substring(tmp1).indexOf(','));
						tmp1 += CT1.length()+1;
						if(CT1.length()<5){
							emrc_time.set(0,getString(R.string.vita_time_hint));
						}
						else {
							emrc_time.set(0,CT1.substring(11,19));
						}
						String CT2 = Msg.substring(tmp1).substring(0, Msg.substring(tmp1).indexOf(','));
						tmp1 += CT2.length()+1;
						if(CT2.length()<5){
							emrc_time.set(1,getString(R.string.vita_time_hint));
						}
						else {
							emrc_time.set(1,CT2.substring(11,19));
						}
						String CT3 = Msg.substring(tmp1).substring(0, Msg.substring(tmp1).indexOf(','));
						tmp1 += CT3.length()+1;
						if(CT3.length()<5){
							emrc_time.set(2,getString(R.string.vita_time_hint));
						}
						else {
							emrc_time.set(2,CT3.substring(11,19));
						}
						String CT4 = Msg.substring(tmp1).substring(0, Msg.substring(tmp1).indexOf(','));
						tmp1 += CT4.length()+1;
						if(CT4.length()<5){
							emrc_time.set(3,getString(R.string.vita_time_hint));
						}
						else {
							emrc_time.set(3,CT4.substring(11,19));
						}
						String CT5 = Msg.substring(tmp1).substring(0, Msg.substring(tmp1).indexOf(','));
						tmp1 += CT5.length()+1;
						if(CT5.length()<5){
							emrc_time.set(4,getString(R.string.vita_time_hint));
						}
						else {
							emrc_time.set(4,CT5.substring(11,19));
						}
						String CT6 = Msg.substring(tmp1).substring(0, Msg.substring(tmp1).indexOf(','));
						tmp1 += CT6.length()+1;
						if(CT6.length()<5){
							emrc_time.set(5,getString(R.string.vita_time_hint));
						}
						else {
							emrc_time.set(5,CT6.substring(11,19));
						}
						String CT7 = Msg.substring(tmp1).substring(0, Msg.substring(tmp1).indexOf(','));
						tmp1 += CT7.length()+1;
						if(CT7.length()<5){
							emrc_time.set(6,getString(R.string.vita_time_hint));
						}
						else {
							emrc_time.set(6,CT7.substring(11,19));
						}
						String CT8 = Msg.substring(tmp1).substring(0, Msg.substring(tmp1).indexOf('|'));
						if(CT8.length()<5){
							emrc_time.set(7,getString(R.string.vita_time_hint));
						}
						else {
							emrc_time.set(7,CT8.substring(11,19));
						}
						break;
					case "NG":
						mmsg = Msg.substring(3);
						break;
					default:
						break;
					}
				}
			}
		} catch (Exception e) {
		}
		// }
		// }).start();
	}

	@SuppressWarnings("deprecation")
	private static void print(ArrayList<Double> llf, ImageView v, int in) {
		int mDaySize = metrics.widthPixels / 150;// 字體大小
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
	}

	private Runnable sendSMS = new Runnable() {
		public void run() {
			if (car) {
				outSMS();
				SMSHandler.postDelayed(this, 10000);// 每10秒呼叫自己執行一次
			}
		}
	};

	private Handler RSMSHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			SMSHandler.removeCallbacks(sendSMS);// 如有運作則停止
			SMSHandler.postDelayed(sendSMS, 2000);// 設定2秒後啟動
		};
	};

	private Runnable sendGPS = new Runnable() {
		public void run() {
			if (gps) {
				outLocation();
				GPSHandler.postDelayed(this, 60000);// 每60秒呼叫自己執行一次
			}
		}
	};

	private Runnable GPSLine = new Runnable() {
		public void run() {
			if (gps) {
				String La, Ln;
				La = String.valueOf(Latitude);
				Ln = String.valueOf(Lngitude);
				// 座標精準度確認
				if (La.length() > 4 && Ln.length() > 4) {
					double at = Double.parseDouble(La);
					double ng = Double.parseDouble(Ln);
					ArrayList<String> aist = new ArrayList<String>();
					ArrayList<Double> dist = new ArrayList<Double>();
					for (int h = 1; h < Treatment_unit.size(); h++) {
						String slat = getLine(Treatment_unit.get(h).location, 0, ',');
						String slng = Treatment_unit.get(h).location.substring(slat.length() + 1);
						double lat = Double.parseDouble(slat);
						double lng = Double.parseDouble(slng);
						double distance = gpsDistance(at, ng, lat, lng);
						aist.add(distance + "");
						dist.add(distance);
					}
					Collections.sort(dist); // 小到大自動排序
					int sdm = 0;
					String dm = dist.get(0) + "";
					for (int i = 0; i < aist.size(); i++) {
						if (aist.get(i).equals(dm)) { // 取得序列位置
							sdm = i;
							break;
						}
					}
					hosp_count = Treatment_unit.get(sdm + 1).area;
					if (!hosp_count.equals("")) { // 救護車取得傷患資訊時
						MSHandler.obtainMessage().sendToTarget();
					}
				} else {
					toast("GPS定位失敗..無法自動搜尋", MainActivity.this);
				}
			}
		}
	};

	private Handler HSHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			LocationProvider();
			toast("正在搜尋醫院..", MainActivity.this);
			GPSHandler.postDelayed(GPSLine, 3000);// 設定3秒後啟動
		};
	};

	private Handler MSHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			spinner.setSelection(getTrainsportSelection(hosp_count), false);
			LocationActivity(1); // 開啟導航
		};
	};

	private Handler UIHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			LocationProvider();
			GPSHandler.removeCallbacks(sendGPS);// 如有運作則停止
			GPSHandler.postDelayed(sendGPS, 10000);// 設定10秒後啟動
		};
	};

	private Handler UPHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			new Timer(true).schedule(new TimerTask() {
				public void run() {
					out("NFC/" + setNFCString());
				}
			}, 2000);
		};
	};

	private Handler PPHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			/*
			new Timer(true).schedule(new TimerTask() {
				public void run() {
					if (link) {
						String unit = mediCar_unit.getText().toString() + "";
						String brand = mediCar_brand.getText().toString() + "";
						if (unit.length() > 1 && brand.length() > 1) {
							if (!car) {
								car = true;
								RSMSHandler.obtainMessage().sendToTarget(); // 開啟自動更新
								mediCar_static.setText(getString(R.string.s_ts_6));
							}
							out("CAR/" + unit + "/" + brand + "/5//|");
							Log.e("CAR / ", "04");
							//1101
							status_count = 5;
							car_brand = brand;
							save();
						}
					}
				}
			}, 3000);
			*/
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					if (link) {
						String unit = mediCar_unit.getText().toString() + "";
						String brand = mediCar_brand.getText().toString() + "";
						if (unit.length() > 1 && brand.length() > 1) {
							if (!car) {
								car = true;
								RSMSHandler.obtainMessage().sendToTarget(); // 開啟自動更新
								mediCar_static.setText(getString(R.string.s_ts_6));
							}
							out("CAR/" + unit + "/" + brand + "/5//|");
							Log.e("CAR / ", "04");
							//1101
							status_count = 5;
							car_brand = brand;
							save();
						}
					}
				}
			}, 3000);
		};
	};

	private Handler C2Handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (dblist) {
				// 檢視傷患資訊時刷新
				lead_upload();
			}
		};
	};

	private Handler C4Handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			spinner.setAdapter(new MySpinnerAdapter_MAIN(MainActivity.this, Treatment_unit));
			toast("OK", MainActivity.this);
		};
	};
	private Handler C5Handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			getNFCString(qr_msg);
//			Log.e("CCCCC", qr_msg);
			checkItem(setNFCString());
			load_data();
		};
	};

	private Handler C6Handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			incident_spinner.setAdapter(new ArrayAdapter<String>(MainActivity.this, R.layout.style_spinner, incident_unit));
		};
	};

	private Handler CPHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			user_upload();
			pager1.setCurrentItem(1);
		};
	};

	private Handler NOHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String msgs = (number + "");
//			print_No.setText(number);
			menu_No.setText(number);
			help_No.setText(number);
			if (msgs.length() > 6) {
				String y = 20 + msgs.substring(0, 2);
				String m = msgs.substring(2, 4);
				String d = msgs.substring(4, 6);
				int Y = Integer.parseInt(y);
				int M = Integer.parseInt(m);
				int D = Integer.parseInt(d);
				checkToday(Y, M, D, true);
			}
		};
	};

	private Handler reasonHandler = new Handler(){
		public void handleMessage(android.os.Message msg){
			main_load.setVisibility(View.VISIBLE);
//			spinner_hos.setSelection(getTrainsportSelection(hosp_count));
			spinner_hos2.setSelection(ambu_reason1);
			Reason_unit.set(7,ambu_reason_txt);
			if(ambu_reason2 == 7){
				spinner_hos3.setAdapter(new ArrayAdapter<String>(MainActivity.this, R.layout.style_spinner, Reason_unit));
			}
			spinner_hos3.setSelection(ambu_reason2);
			if(db_allergy0 == 0){
				allergy0.setVisibility(View.GONE);
			}
			else {
				allergy0.setVisibility(View.VISIBLE);
			}
			allergy1.setText(db_allergy1);
			allergy2.setText(db_allergy2);
			allergy3.setText(db_allergy3);
			accident_car1.setText(db_accident_car1);
			accident_car2.setText(db_accident_car2);
			accident_car3.setText(db_accident_car3);
			accident_car4.setText(db_accident_car4);
			if(db_ambu_complaint0 == 1){
				ambu_complaint0.setChecked(true);
			}
			if(db_ambu_complaint1 == 1){
				ambu_complaint1.setChecked(true);
			}
			if(db_ambu_complaint2 == 1){
				ambu_complaint2.setChecked(true);
			}
			if(db_ambu_complaint3 == 1){
				ambu_complaint3.setChecked(true);
			}
			ambu_complaint.setText(db_ambu_complaint);
			if(db_ambu_accepted == 1){
				ambu_accepted1.setChecked(true);
			}
			if(db_ambu_accepted == 2){
				ambu_accepted2.setChecked(true);
			}
			if(db_ambu_accepted == 3){
				ambu_accepted3.setChecked(true);
			}
			ambu_place1.setText(db_ambu_place1);
			ambu_place2.setText(db_ambu_place2);
			ambu_property.setText(db_ambu_property);
			ambu_guide.setText(db_ambu_guide);

		};
	};


	private Handler finishHandler = new Handler(){
		public void handleMessage(android.os.Message msg){
			main_load.setVisibility(View.GONE);
		};
	};

	private Handler PhotoHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {


			if (phot_count > 0) {
				int cc = phot_count, set = 0;
				while (cc != 0) {
					if (cc - 32 >= 0) {
						cc -= 32;
						set++;
					} else if (cc - 16 >= 0) {
						cc -= 16;
						set++;
					} else if (cc - 8 >= 0) {
						cc -= 8;
						set++;
					} else if (cc - 4 >= 0) {
						cc -= 4;
						set++;
					} else if (cc - 2 >= 0) {
						cc -= 2;
						set++;
					} else {
						cc--;
						set++;
					}
				}
				menu_item.set(5, getString(R.string.s_Menu_6) + "<" + getString(R.string.s_List_3) + set
						+ getString(R.string.s_List_4) + ">");
				photo_num.setText(""+set);
				menu_upload(MainActivity.this);
			} else {
				menu_item.set(5, getString(R.string.s_Menu_6));
				menu_upload(MainActivity.this);
			}
			for(int i=0;i<sign_bitmap.size();i++){
			if(sign_bitmap.get(i)!=null){
					switch (i){
						case 0:
							sign0.setImageBitmap(sign_bitmap.get(0));
							break;
						case 1:
							sign1.setImageBitmap(sign_bitmap.get(1));
							break;
						case 2:
							sign2.setImageBitmap(sign_bitmap.get(2));
							break;
						case 3:
							sign3.setImageBitmap(sign_bitmap.get(3));
							break;
						case 4:
							sign4.setImageBitmap(sign_bitmap.get(4));
							break;
						case 5:
							sign5.setImageBitmap(sign_bitmap.get(5));
							break;
						case 6:
							sign6.setImageBitmap(sign_bitmap.get(6));
							break;
						case 7:
							sign7.setImageBitmap(sign_bitmap.get(7));
							break;
					}
				}
			}
		};
	};
}
