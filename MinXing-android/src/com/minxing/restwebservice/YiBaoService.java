package com.minxing.restwebservice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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

import com.minxing.PushMessage.XiaoMiTuiSong;
import com.minxing.activity.YiBaoSheBaoActivity;
import com.minxing.restwebservice.RegisterService;

import com.zhumingmin.vmsofminxing.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class YiBaoService extends Activity {
	private TextView xianshiexcel;
	private Button chaxun, xianshangjiaofei;
	private ImageButton fanhui;
	Bitmap bitmap;
	private OutputStream is;
	LinearLayout ly_jiaofei;
	OutputStream out;
	private static final String SERVICE_URL = "http://192.168.191.1:8080/RestWebServiceDemo/rest/yibaoguanli";

	private static final String TAG = "YiBaoService";
	EditText tianxiechaxunzhanghao, xianshichaxunjieguo;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.zhumingmin.vmsofminxing.R.layout.webservice_chakanjiaofei);
		ly_jiaofei = (LinearLayout) findViewById(R.id.ly_jiaofei);
		tianxiechaxunzhanghao = (EditText) findViewById(R.id.tianxiechaxunzhanghao);
		xianshichaxunjieguo = (EditText) findViewById(R.id.xianshichaxunjieguo);
		fanhui = (ImageButton) findViewById(com.zhumingmin.vmsofminxing.R.id.FanHui4);
		chaxun = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.XiaZaiExcel);
		xianshangjiaofei = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.xianshangjiaofei);

		xianshangjiaofei.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DisplayToast("暂未开通，请稍后！可前往支付宝付款，付款账号为：123456@qq.com");
				// Intent intent = new Intent();
				// intent.setClass(YiBaoService.this, XiaoMiTuiSong.class);
				// startActivity(intent);
				// YiBaoService.this.finish();
			}
		});

		ly_jiaofei.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(YiBaoService.this, YiBaoSheBaoActivity.class);
				startActivity(intent);
				YiBaoService.this.finish();
				// overridePendingTransition(R.anim.activity_right_out,
				// R.anim.activity_left_in);
			}
		});
		tianxiechaxunzhanghao.setOnKeyListener(new View.OnKeyListener() {// 输入完后按键盘上的搜索键

					public boolean onKey(View v, int keyCode, KeyEvent event) {
						if (keyCode == KeyEvent.KEYCODE_ENTER
								&& event.getAction() == KeyEvent.ACTION_DOWN) {// 修改回车键功能
							// 先隐藏键盘
							((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
									.hideSoftInputFromWindow(getCurrentFocus()
											.getWindowToken(),
											InputMethodManager.HIDE_NOT_ALWAYS);
							// 按完搜索键后将当前查询的关键字保存起来,如果该关键字已经存在就不执行保存
							String shenfenzhenghaoma = tianxiechaxunzhanghao
									.getText().toString();

							if (shenfenzhenghaoma.equals("")) {
								Toast.makeText(getApplicationContext(),
										"身份证号码不能空！", 0).show();
							}

							WebServiceTask wst = new WebServiceTask(
									WebServiceTask.POST_TASK,
									YiBaoService.this, "Posting data...");

							wst.addNameValuePair("shenfenzhenghaoma",
									shenfenzhenghaoma);

							wst.execute(new String[] { SERVICE_URL });

						}
						return false;
					}
				});

	}

	// 从服务器端获取信息 GET方法
	public void retrieveSampleData(View vw) {

		String sampleURL = SERVICE_URL + "/cx";
		WebServiceTask wst = new WebServiceTask(WebServiceTask.GET_TASK, this,
				"正在查询，请稍候...");
		wst.execute(new String[] { sampleURL });

	}

	public void handleResponse(String response) {

		try {

			JSONObject jso = new JSONObject(response);

			String jiaofeiqingkuan = jso.optString("jiaoFeiQingKuan");

			xianshichaxunjieguo.setText(jiaofeiqingkuan);

		} catch (Exception e) {
			Log.e(TAG, e.getLocalizedMessage(), e);
		}

	}

	// 隐藏键盘
	private void hideKeyboard() {

		InputMethodManager inputManager = (InputMethodManager) YiBaoService.this
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		inputManager.hideSoftInputFromWindow(YiBaoService.this
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
			Intent intent = new Intent();
			intent.setClass(YiBaoService.this, YiBaoSheBaoActivity.class);
			startActivity(intent);
			YiBaoService.this.finish();

		}
		return super.onKeyDown(keyCode, event);
	}

	private void DisplayToast(String string) {
		// TODO Auto-generated method stub
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
	}

}
