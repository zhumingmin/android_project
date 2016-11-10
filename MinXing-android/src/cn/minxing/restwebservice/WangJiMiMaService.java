package cn.minxing.restwebservice;

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

import cn.minxing.activity.ZaiRuActivity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class WangJiMiMaService extends Activity {
	private Button tijiao, fanhui;
	// private TextView zhucezhanghao, lianxidianhua;
	private EditText tianxiezhucezhanghao, tianxielianxidianhua, dedaomima;
	ProgressDialog m_pDialog;
	int m_count = 0;
	private Handler handler;
	private LinearLayout ly_fanhui;
	private static final String SERVICE_URL = "http://192.168.191.1:8080/RestWebServiceDemo/rest/mima";
	private static final String TAG = "WangJiMiMaActivity";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.zhumingmin.vmsofminxing.R.layout.activity_wangjimima);
		tijiao = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.wjmm_TiJiao);
		ly_fanhui = (LinearLayout) findViewById(com.zhumingmin.vmsofminxing.R.id.ly_fanhui_wangjimima);

		tianxiezhucezhanghao = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.TianXieZhuCeZhangHao);
		tianxielianxidianhua = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.TianXieLianXiDianHua);
		dedaomima = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.dedaomima);
		dedaomima.setEnabled(false);
		ly_fanhui.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				WangJiMiMaService.this.finish();
			}
		});
		// 填写完成自动检测是否为空
		tijiao.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// 按完搜索键后将当前查询的关键字保存起来,如果该关键字已经存在就不执行保存
				String zhucezhanghao = tianxiezhucezhanghao.getText()
						.toString();
				String lianxidianhua = tianxielianxidianhua.getText()
						.toString();
				if (zhucezhanghao.equals("")) {
					Toast.makeText(getApplicationContext(), "注册帐号不能空！", 0)
							.show();
				}
				if (lianxidianhua.equals("")) {
					Toast.makeText(getApplicationContext(), "手机号码不能空！", 0)
							.show();
				}

				WebServiceTask wst = new WebServiceTask(
						WebServiceTask.POST_TASK, WangJiMiMaService.this,
						"找回密码中...");

				wst.addNameValuePair("zhanghao", zhucezhanghao);
				wst.addNameValuePair("shoujihaoma", lianxidianhua);

				wst.execute(new String[] { SERVICE_URL });
				handler = new Handler() {

					@SuppressLint("HandlerLeak")
					@Override
					public void handleMessage(Message msg) {
						// TODO Auto-generated method stub
						super.handleMessage(msg);
						jump();

					}
				};

				handler.sendEmptyMessageDelayed(0, 1000);
			}

		});

	}

	protected void jump() {
		// TODO jump to homepage activity
		String sampleURL = SERVICE_URL + "/zh";
		WebServiceTask wst = new WebServiceTask(WebServiceTask.GET_TASK, this,
				"正在找回，请稍候...");
		wst.execute(new String[] { sampleURL });
	}

	// 从服务器端获取信息 GET方法
	// public void retrieveSampleData(View vw) {
	//
	// String sampleURL = SERVICE_URL + "/zh";
	// WebServiceTask wst = new WebServiceTask(WebServiceTask.GET_TASK, this,
	// "正在找回，请稍候...");
	// wst.execute(new String[] { sampleURL });
	//
	// }

	public void handleResponse(String response) {
		dedaomima = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.dedaomima);

		try {

			JSONObject jso = new JSONObject(response);

			String result = jso.optString("miMa1");

			dedaomima.setText(result);

		} catch (Exception e) {
			Log.e(TAG, e.getLocalizedMessage(), e);
		}

	}

	// 隐藏键盘
	private void hideKeyboard() {

		InputMethodManager inputManager = (InputMethodManager) WangJiMiMaService.this
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		inputManager.hideSoftInputFromWindow(WangJiMiMaService.this
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

		@SuppressWarnings("deprecation")
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
				Toast.makeText(getApplicationContext(), "查询成功！", 0).show();

			} else {
				Toast.makeText(getApplicationContext(), "查询失败！", 0).show();
			}
			pDlg.dismiss();
			// System.out.println("输出"+response);

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

			WangJiMiMaService.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
