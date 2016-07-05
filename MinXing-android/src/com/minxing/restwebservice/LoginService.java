package com.minxing.restwebservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

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
import org.xml.sax.SAXException;

import com.minxing.activity.DengLuJieMianActivity;
import com.minxing.activity.WangJiMiMaActivity;
import com.minxing.activity.YeWuBanLiActivity;
import com.minxing.activity.ZhuCeActivity;
import com.zhumingmin.vmsofminxing.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class LoginService extends Activity {
	CheckBox savePasswordCB;
	int m_count = 0;
	ProgressDialog m_pDialog;
	public SQLiteDatabase db;
	private TextView zhaohuimima, mianfeizhuce, huanyindenglu, zhanghao, mima;
	private EditText tianxiezhanghao, tianxiemima;

	private Button denglu;
	private String resultGET = null;
	private static final String SERVICE_URL = "http://192.168.191.1:8080/RestWebServiceDemo/rest/login";

	private static final String TAG = "LoginService";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setProgressBarVisibility(true);
		setContentView(com.zhumingmin.vmsofminxing.R.layout.webservice_denglu);

		denglu = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.DengLu);
		mianfeizhuce = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.MianFeiZhuCe);
		zhaohuimima = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.ZhaoHuiMiMa);
		tianxiezhanghao = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.TianXieZhangHao);
		tianxiemima = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.TianXieMiMa);
		// EditText edEmail = (EditText)
		// findViewById(com.zhumingmin.vmsofminxing.R.id.email);
		savePasswordCB = (CheckBox) findViewById(R.id.savePasswordCB);

		tianxiezhanghao.setHint("您的身份证号码");
		tianxiemima.setHint("六位包括字母和数字");
		tianxiezhanghao
				.setFilters(new InputFilter[] { new InputFilter.LengthFilter(18) });
		tianxiemima
				.setFilters(new InputFilter[] { new InputFilter.LengthFilter(6) });

		SharedPreferences remdname = getPreferences(Activity.MODE_PRIVATE);
		String name_str = remdname.getString("name", "");
		String pass_str = remdname.getString("pass", "");
		tianxiezhanghao.setText(name_str);
		tianxiemima.setText(pass_str);
		savePasswordCB = (CheckBox) findViewById(R.id.savePasswordCB);
		// 接收设置中的注销登录的操作
		Intent intent = getIntent();
		int from = intent.getIntExtra("from", 0);
		switch (from) {
		case 1:
			tianxiezhanghao.setText("");
			tianxiemima.setText("");
			savePasswordCB.setSaveEnabled(false);
			break;
		}
		savePasswordCB
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							SharedPreferences remdname = getPreferences(Activity.MODE_PRIVATE);
							SharedPreferences.Editor edit = remdname.edit();
							edit.putString("name", tianxiezhanghao.getText()
									.toString());
							edit.putString("pass", tianxiemima.getText()
									.toString());
							edit.commit();
						}
						if (!isChecked) {

							SharedPreferences remdname = getPreferences(Activity.MODE_PRIVATE);
							SharedPreferences.Editor edit = remdname.edit();
							edit.putString("name", "");
							edit.putString("pass", "");
							edit.commit();
						}
					}

				});

		zhaohuimima.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(LoginService.this, WangJiMiMaActivity.class);
				startActivity(intent);

			}
		});

		denglu.setOnClickListener(new Button.OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				m_pDialog = new ProgressDialog(LoginService.this);

				m_pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

				m_pDialog.setTitle("提示");

				m_pDialog.setMessage("正在登陆，请稍后……");

				m_pDialog
						.setIcon(com.zhumingmin.vmsofminxing.R.drawable.tubiao1);

				m_pDialog.setIndeterminate(false);

				m_pDialog.setCancelable(true);

				m_pDialog.setButton("确定",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int i) {

								dialog.cancel();
							}
						});

				m_pDialog.show();
				if (savePasswordCB.isChecked())// 检测用户名密码
				{
					SharedPreferences remdname = getPreferences(Activity.MODE_PRIVATE);
					SharedPreferences.Editor edit = remdname.edit();
					edit.putString("name", tianxiezhanghao.getText().toString());
					edit.putString("pass", tianxiemima.getText().toString());
					edit.commit();
				}
				Intent intent = new Intent();
				// intent.setClass(DengLuJieMian.this, Home.class);
				intent.setClass(LoginService.this, YeWuBanLiActivity.class);
				startActivity(intent);
				LoginService.this.finish();

			}
		});

		mianfeizhuce.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(LoginService.this, RegisterService.class);
				startActivity(intent);
				LoginService.this.finish();
			}
		});
		denglu.setOnClickListener(new LoginListener());

	}

	public void handleResponse(String response) {

		// tianxiezhanghao.setText("");
		// tianxiemima.setText("");

		try {

			JSONObject jso = new JSONObject(response);

			String firstName = jso.getString("zhanghao");
			String lastName = jso.getString("mima1");

			tianxiezhanghao.setText(firstName);
			tianxiemima.setText(lastName);

		} catch (Exception e) {
			Log.e(TAG, e.getLocalizedMessage(), e);
		}

	}

	// 隐藏键盘
	private void hideKeyboard() {

		InputMethodManager inputManager = (InputMethodManager) LoginService.this
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		inputManager.hideSoftInputFromWindow(LoginService.this
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
			pDlg.dismiss();

		}

		// Establish connection and socket (data retrieval) timeouts
		private HttpParams getHttpParams() {

			HttpParams htpp = new BasicHttpParams();

			HttpConnectionParams.setConnectionTimeout(htpp, CONN_TIMEOUT);
			HttpConnectionParams.setSoTimeout(htpp, SOCKET_TIMEOUT);

			return htpp;
		}

		@SuppressWarnings("deprecation")
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

	@SuppressLint("ShowToast")
	class LoginListener implements OnClickListener {

		@SuppressWarnings("deprecation")
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// String zhanghao = tianxiezhanghao.getText().toString();
			// String mima1 = tianxiemima.getText().toString();
			// if (!isNetworkAvailable(LoginService.this)) {
			//
			// Toast.makeText(getApplicationContext(), "网络未连接，请检查您的网络！", 0)
			// .show();
			// return;
			// }
			// if (zhanghao.equals("")) {
			// Toast.makeText(getApplicationContext(), "账号不能空！", 0).show();
			// return;
			// }
			// if (mima1.equals("")) {
			// Toast.makeText(getApplicationContext(), "密码不能为空！", 0).show();
			// return;
			// }
			//
			// WebServiceTask wst = new WebServiceTask(WebServiceTask.POST_TASK,
			// LoginService.this, "正在登陆...");
			//
			// wst.addNameValuePair("loginzhanghao", zhanghao);
			// wst.addNameValuePair("loginmima", mima1);
			//
			// // the passed String is the URL we will POST to
			// wst.execute(new String[] { SERVICE_URL });

			// post信息成功后在Android显示连接后台成功的标识
			// if (wst.doInBackground(SERVICE_URL) != null) {
			// Toast.makeText(getApplicationContext(), "登陆成功！", 0).show();
			// Intent intent = new Intent();
			// intent.setClass(LoginService.this, YeWuBanLi.class);
			// startActivity(intent);
			// LoginService.this.finish();
			// } else {
			// Toast.makeText(getApplicationContext(), "登陆失败！", 0).show();
			// }
			Intent intent = new Intent();
			intent.setClass(LoginService.this, YeWuBanLiActivity.class);
			startActivity(intent);
			LoginService.this.finish();

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater()
				.inflate(com.zhumingmin.vmsofminxing.R.menu.menu, menu);
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// 创建退出对话框
			AlertDialog isExit = new AlertDialog.Builder(this).create();
			// 设置对话框标题
			isExit.setTitle("系统提示");
			// 设置对话框消息
			isExit.setMessage("确定要退出吗");
			// 添加选择按钮并注册监听
			isExit.setButton("确定", listener);
			isExit.setButton2("取消", listener);

			// 显示对话框]
			isExit.show();

		}

		return false;

	}

	/** 监听对话框里面的button点击事件 */
	DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
				finish();
				break;
			case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 检测当的网络（WLAN、3G/2G）状态
	 * 
	 * @param context
	 *            Context
	 * @return true 表示网络可用
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo info = connectivity.getActiveNetworkInfo();
			if (info != null && info.isConnected()) {
				// 当前网络是连接的
				if (info.getState() == NetworkInfo.State.CONNECTED) {
					// 当前所连接的网络可用
					return true;
				}
			}
		}
		return false;
	}
}
