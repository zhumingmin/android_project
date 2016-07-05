package com.minxing.restwebservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

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

import com.minxing.activity.DengLuJieMianActivity;
import com.minxing.activity.HuJiGuanLiActivity;
import com.minxing.activity.ZhuCeActivity;

import android.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
				Intent intent = new Intent();
				intent.setClass(RegisterService.this, LoginService.class);
				startActivity(intent);
				RegisterService.this.finish();

			}
		});

		zhucewancheng.setOnClickListener(new Button.OnClickListener() {

			@SuppressLint("ShowToast")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String zhanghao = TianXieZhangHao1.getText().toString();
				String xingming = TianXieXingming1.getText().toString();
				String shoujihaoma = TianXieShouJiHaoMa1.getText().toString();
				String mima1 = TianXieMiMa1.getText().toString();
				String mima2 = TianXieMiMa2.getText().toString();
				if (zhanghao.equals("")) {
					Toast.makeText(getApplicationContext(), "账号不能空！", 0).show();
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

				WebServiceTask wst = new WebServiceTask(
						WebServiceTask.POST_TASK, RegisterService.this,
						"正在注册，请稍后……");

				wst.addNameValuePair("zhanghao", zhanghao);
				wst.addNameValuePair("xingming", xingming);
				wst.addNameValuePair("shoujihaoma", shoujihaoma);
				wst.addNameValuePair("mima1", mima1);
				wst.addNameValuePair("mima2", mima2);

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

			Intent intent = new Intent();
			intent.setClass(RegisterService.this, DengLuJieMianActivity.class);
			startActivity(intent);
			RegisterService.this.finish();
			// overridePendingTransition(R.anim.slide_in_left,
			// R.anim.slide_out_right);

		}
		return super.onKeyDown(keyCode, event);
	}
}
