package cn.minxing.restwebservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import cn.minxing.activity.DengLuJieMianActivity;
import cn.minxing.activity.HuJiGuanLiActivity;
import cn.minxing.activity.ZhuCeActivity;
import android.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ParseException;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class RegisterService extends Activity {

	private static final String SERVICE_URL = "http://192.168.191.1:8080/RestWebServiceDemo/rest/account";

	private static final String TAG = "RegisterService";
	private EditText TianXieZhangHao1, TianXieXingming1, TianXieShouJiHaoMa1,
			TianXieMiMa1, TianXieMiMa2;
	private Button zhucewancheng;
	private LinearLayout ly_fanhui;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.zhumingmin.vmsofminxing.R.layout.webservice_zhuce);
		TianXieZhangHao1 = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.TianXieZhangHao1);
		TianXieXingming1 = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.TianXieXingming1);
		TianXieShouJiHaoMa1 = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.TianXieShouJiHaoMa1);
		TianXieMiMa1 = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.TianXieMiMa1);
		TianXieMiMa2 = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.TianXieMiMa2);
		zhucewancheng = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.zhucewancheng);
		ly_fanhui = (LinearLayout) findViewById(com.zhumingmin.vmsofminxing.R.id.ly_fanhui_zhuce);
		ly_fanhui.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				finish();

			}
		});

		zhucewancheng.setOnClickListener(new Button.OnClickListener() {

			@SuppressLint("ShowToast")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TelephonyManager TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
				String szImei = TelephonyMgr.getDeviceId();
				String m_szDevIDShort = "35"
						+ // we make this look like a valid IMEI

						Build.BOARD.length() % 10 + Build.BRAND.length() % 10
						+ Build.CPU_ABI.length() % 10 + Build.DEVICE.length()
						% 10 + Build.DISPLAY.length() % 10
						+ Build.HOST.length() % 10 + Build.ID.length() % 10
						+ Build.MANUFACTURER.length() % 10
						+ Build.MODEL.length() % 10 + Build.PRODUCT.length()
						% 10 + Build.TAGS.length() % 10 + Build.TYPE.length()
						% 10 + Build.USER.length() % 10; // 13 digits
				String m_szAndroidID = Secure.getString(getContentResolver(),
						Secure.ANDROID_ID);
				WifiManager wm = (WifiManager) getSystemService(Context.WIFI_SERVICE);
				String m_szWLANMAC = wm.getConnectionInfo().getMacAddress();
				BluetoothAdapter m_BluetoothAdapter = null; // Local Bluetooth
															// adapter
				m_BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
				String m_szBTMAC = m_BluetoothAdapter.getAddress();

				String m_szLongID = szImei + m_szDevIDShort + m_szAndroidID
						+ m_szWLANMAC + m_szBTMAC;
				// compute md5
				MessageDigest m = null;
				try {
					m = MessageDigest.getInstance("MD5");
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
				m.update(m_szLongID.getBytes(), 0, m_szLongID.length());
				// get md5 bytes
				byte p_md5Data[] = m.digest();
				// create a hex string
				String m_szUniqueID = new String();
				for (int i = 0; i < p_md5Data.length; i++) {
					int b = (0xFF & p_md5Data[i]);
					// if it is a single digit, make sure it have 0 in front
					// (proper padding)
					if (b <= 0xF)
						m_szUniqueID += "0";
					// add number to string
					m_szUniqueID += Integer.toHexString(b);
				} // hex string to uppercase
				m_szUniqueID = m_szUniqueID.toUpperCase();

				String zhanghao = TianXieZhangHao1.getText().toString();
				String xingming = TianXieXingming1.getText().toString();
				String shoujihaoma = TianXieShouJiHaoMa1.getText().toString();
				String mima1 = TianXieMiMa1.getText().toString();
				String mima2 = TianXieMiMa2.getText().toString();

				if (zhanghao.equals("") && !IDCardValidate(zhanghao)) {
					Toast.makeText(getApplicationContext(), "身份证号码不能为空！", 0)
							.show();
					return;
				}
				if (!IDCardValidate(zhanghao)) {
					Toast.makeText(getApplicationContext(), "身份证号码格式不正确！", 0)
							.show();
					return;
				}

				if (xingming.equals("")) {
					Toast.makeText(getApplicationContext(), "姓名不能空！", 0).show();
					return;
				}
				if (shoujihaoma.equals("")) {
					Toast.makeText(getApplicationContext(), "手机号码不能为空！", 0)
							.show();
					return;
				}

				if (mima1.equals("")) {
					Toast.makeText(getApplicationContext(), "密码不能为空！", 0)
							.show();
					return;
				}
				if (mima2.equals("")) {
					Toast.makeText(getApplicationContext(), "密码不能为空！", 0)
							.show();
					return;
				}
				if (!mima1.equals(mima2)) {
					Toast.makeText(getApplicationContext(), "两次密码输入不一样！", 0)
							.show();
					return;
				}

				WebServiceTask wst = new WebServiceTask(
						WebServiceTask.POST_TASK, RegisterService.this,
						"正在注册，请稍后……");

				wst.addNameValuePair("zhanghao", zhanghao);
				wst.addNameValuePair("xingming", xingming);
				wst.addNameValuePair("shoujihaoma", shoujihaoma);
				wst.addNameValuePair("mima1", mima1);
				wst.addNameValuePair("mima2", mima2);
				wst.addNameValuePair("uniqueID", m_szUniqueID);

				// the passed String is the URL we will POST to
				wst.execute(new String[] { SERVICE_URL });
			}
		});

	}

	public void handleResponse(String response) {

		EditText TianXieZhangHao1 = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.TianXieZhangHao1);
		EditText TianXieXingming1 = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.TianXieXingming1);
		EditText TianXieShouJiHaoMa1 = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.TianXieShouJiHaoMa1);
		EditText TianXieMiMa1 = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.TianXieMiMa1);
		EditText TianXieMiMa2 = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.TianXieMiMa2);

		TianXieZhangHao1.setText("");
		TianXieXingming1.setText("");
		TianXieShouJiHaoMa1.setText("");
		TianXieMiMa1.setText("");
		TianXieMiMa2.setText("");

		try {

			JSONObject jso = new JSONObject(response);

			String zhanghao = jso.getString("zhanghao");
			String xingming = jso.getString("xingming");
			String shoujihaoma = jso.getString("shoujihaoma");
			String mima1 = jso.getString("mima1");
			String mima2 = jso.getString("mima2");

			TianXieZhangHao1.setText(zhanghao);
			TianXieXingming1.setText(xingming);
			TianXieShouJiHaoMa1.setText(shoujihaoma);
			TianXieMiMa1.setText(mima1);
			TianXieMiMa2.setText(mima2);

		} catch (Exception e) {
			Log.e(TAG, e.getLocalizedMessage(), e);
		}

	}

	// 隐藏键盘
	private void hideKeyboard() {

		InputMethodManager inputManager = (InputMethodManager) RegisterService.this
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		inputManager.hideSoftInputFromWindow(RegisterService.this
				.getCurrentFocus().getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}

	// 主要操作部分
	private class WebServiceTask extends AsyncTask<String, Integer, String> {

		public static final int POST_TASK = 1;
		public static final int GET_TASK = 2;

		private static final String TAG = "WebServiceTask";

		// connection timeout, in milliseconds (waiting to connect)
		private static final int CONN_TIMEOUT = 3000;

		// socket timeout, in milliseconds (waiting for data)
		private static final int SOCKET_TIMEOUT = 5000;

		private int taskType = GET_TASK;
		private Context mContext = null;
		private String processMessage = "Processing...";

		private ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

		private ProgressDialog pDlg = null;

		public WebServiceTask(int taskType, Context mContext,
				String processMessage) {

			this.taskType = taskType;
			this.mContext = mContext;
			this.processMessage = processMessage;
		}

		public void addNameValuePair(String name, String value) {

			params.add(new BasicNameValuePair(name, value));
			// HttpEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
		}

		private void showProgressDialog() {

			pDlg = new ProgressDialog(mContext);
			pDlg.setMessage(processMessage);
			pDlg.setProgressDrawable(mContext.getWallpaper());
			pDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pDlg.setCancelable(false);
			pDlg.show();

		}

		@Override
		protected void onPreExecute() {

			hideKeyboard();
			showProgressDialog();

		}

		protected String doInBackground(String... urls) {

			String url = urls[0];
			String result = "";

			HttpResponse response = doResponse(url);

			if (response == null) {
				return result;
			} else {

				try {

					result = inputStreamToString(response.getEntity()
							.getContent());

				} catch (IllegalStateException e) {
					Log.e(TAG, e.getLocalizedMessage(), e);

				} catch (IOException e) {
					Log.e(TAG, e.getLocalizedMessage(), e);
				}

			}

			return result;
		}

		@Override
		protected void onPostExecute(String response) {

			handleResponse(response);
			if (response != null) {
				Toast.makeText(getApplicationContext(), "注册成功！", 0).show();

			} else {
				Toast.makeText(getApplicationContext(), "注册失败！", 0).show();
			}
			pDlg.dismiss();

		}

		// Establish connection and socket (data retrieval) timeouts
		private HttpParams getHttpParams() {

			HttpParams htpp = new BasicHttpParams();

			HttpConnectionParams.setConnectionTimeout(htpp, CONN_TIMEOUT);
			HttpConnectionParams.setSoTimeout(htpp, SOCKET_TIMEOUT);

			return htpp;
		}

		private HttpResponse doResponse(String url) {

			// Use our connection and data timeouts as parameters for our
			// DefaultHttpClient
			HttpClient httpclient = new DefaultHttpClient(getHttpParams());

			HttpResponse response = null;

			try {
				switch (taskType) {

				case POST_TASK:
					HttpPost httppost = new HttpPost(url);
					// Add parameters
					httppost.setEntity(new UrlEncodedFormEntity(params,
							HTTP.UTF_8));
					response = httpclient.execute(httppost);
					break;
				case GET_TASK:
					HttpGet httpget = new HttpGet(url);
					response = httpclient.execute(httpget);
					break;
				}
			} catch (Exception e) {

				Log.e(TAG, e.getLocalizedMessage(), e);

			}

			return response;
		}

		private String inputStreamToString(InputStream is) {

			String line = "";
			StringBuilder total = new StringBuilder();

			// Wrap a BufferedReader around the InputStream
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));

			try {
				// Read response until the end
				while ((line = rd.readLine()) != null) {
					total.append(line);
				}
			} catch (IOException e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
			}

			// Return full string
			return total.toString();
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getRepeatCount() == 0)) {

			RegisterService.this.finish();
			// overridePendingTransition(R.anim.slide_in_left,
			// R.anim.slide_out_right);

		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 功能：身份证的有效验证
	 * 
	 * @param IDStr
	 *            身份证号
	 * @return 有效：返回"" 无效：返回String信息
	 * @throws ParseException
	 */
	public static boolean IDCardValidate(String IDStr) throws ParseException {
		String errorInfo = "";// 记录错误信息
		String[] ValCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4",
				"3", "2" };
		String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
				"9", "10", "5", "8", "4", "2" };
		String Ai = "";
		// ================ 号码的长度 15位或18位 ================
		if (IDStr.length() != 15 && IDStr.length() != 18) {
			errorInfo = "身份证号码长度应该为15位或18位。";
			return false;
		}
		// =======================(end)========================

		// ================ 数字 除最后以为都为数字 ================
		if (IDStr.length() == 18) {
			Ai = IDStr.substring(0, 17);
		} else if (IDStr.length() == 15) {
			Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
		}
		if (isNumeric(Ai) == false) {
			errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
			return false;
		}
		// =======================(end)========================

		// ================ 出生年月是否有效 ================
		String strYear = Ai.substring(6, 10);// 年份
		String strMonth = Ai.substring(10, 12);// 月份
		String strDay = Ai.substring(12, 14);// 月份
		if (isDataFormat(strYear + "-" + strMonth + "-" + strDay) == false) {
			errorInfo = "身份证生日无效。";
			return false;
		}
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
					|| (gc.getTime().getTime() - s.parse(
							strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
				errorInfo = "身份证生日不在有效范围。";
				return false;
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
			errorInfo = "身份证月份无效";
			return false;
		}
		if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
			errorInfo = "身份证日期无效";
			return false;
		}
		// =====================(end)=====================

		// ================ 地区码时候有效 ================
		Hashtable h = GetAreaCode();
		if (h.get(Ai.substring(0, 2)) == null) {
			errorInfo = "身份证地区编码错误。";
			return false;
		}
		// ==============================================

		// ================ 判断最后一位的值 ================
		int TotalmulAiWi = 0;
		for (int i = 0; i < 17; i++) {
			TotalmulAiWi = TotalmulAiWi
					+ Integer.parseInt(String.valueOf(Ai.charAt(i)))
					* Integer.parseInt(Wi[i]);
		}
		int modValue = TotalmulAiWi % 11;
		String strVerifyCode = ValCodeArr[modValue];
		Ai = Ai + strVerifyCode;

		if (IDStr.length() == 18) {
			if (Ai.equals(IDStr) == false) {
				errorInfo = "身份证无效，不是合法的身份证号码";
				return false;
			}
		} else {
			return true;
		}
		// =====================(end)=====================
		return true;
	}

	/**
	 * 功能：判断字符串是否为数字
	 * 
	 * @param str
	 * @return
	 */
	private static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (isNum.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 功能：设置地区编码
	 * 
	 * @return Hashtable 对象
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Hashtable GetAreaCode() {
		Hashtable hashtable = new Hashtable();
		hashtable.put("11", "北京");
		hashtable.put("12", "天津");
		hashtable.put("13", "河北");
		hashtable.put("14", "山西");
		hashtable.put("15", "内蒙古");
		hashtable.put("21", "辽宁");
		hashtable.put("22", "吉林");
		hashtable.put("23", "黑龙江");
		hashtable.put("31", "上海");
		hashtable.put("32", "江苏");
		hashtable.put("33", "浙江");
		hashtable.put("34", "安徽");
		hashtable.put("35", "福建");
		hashtable.put("36", "江西");
		hashtable.put("37", "山东");
		hashtable.put("41", "河南");
		hashtable.put("42", "湖北");
		hashtable.put("43", "湖南");
		hashtable.put("44", "广东");
		hashtable.put("45", "广西");
		hashtable.put("46", "海南");
		hashtable.put("50", "重庆");
		hashtable.put("51", "四川");
		hashtable.put("52", "贵州");
		hashtable.put("53", "云南");
		hashtable.put("54", "西藏");
		hashtable.put("61", "陕西");
		hashtable.put("62", "甘肃");
		hashtable.put("63", "青海");
		hashtable.put("64", "宁夏");
		hashtable.put("65", "新疆");
		hashtable.put("71", "台湾");
		hashtable.put("81", "香港");
		hashtable.put("82", "澳门");
		hashtable.put("91", "国外");
		return hashtable;
	}

	/**
	 * 验证日期字符串是否是YYYY-MM-DD格式
	 * 
	 * @param str
	 * @return
	 */
	private static boolean isDataFormat(String str) {
		boolean flag = false;
		// String
		// regxStr="[1-9][0-9]{3}-[0-1][0-2]-((0[1-9])|([12][0-9])|(3[01]))";
		String regxStr = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
		Pattern pattern1 = Pattern.compile(regxStr);
		Matcher isNo = pattern1.matcher(str);
		if (isNo.matches()) {
			flag = true;
		}
		return flag;
	}
}
