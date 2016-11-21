package cn.minxing.activity;

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

import cn.minxing.fragment.YeWuBanLiFragment.TimeThread;
import cn.minxing.restwebservice.HeTongService;
import cn.minxing.restwebservice.WangJiMiMaService;

import com.zhumingmin.vmsofminxing.R;

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
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HeTongGuanLiActivity extends Activity {
	private Button chakanbaobiao, yiwenfankui;
	private ImageButton fanhui;
	private TextView caiwu, shijian, caiwugonggao;
	LinearLayout ly_hetong;
	private static final int msgKey1 = 1;
	private static final String SERVICE_URL = "http://192.168.191.1:8080/RestWebServiceDemo/rest/caiwugonggao";
	private static final String TAG = "HeTongGuanLiActivity";
	private Handler handler;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.zhumingmin.vmsofminxing.R.layout.activity_hetongguanli);

		caiwu = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.caiwu);
		caiwugonggao = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.caiwugonggao);
		shijian = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.shijian);

		// new TimeThread().start();

		fanhui = (ImageButton) findViewById(com.zhumingmin.vmsofminxing.R.id.FanHui);
		chakanbaobiao = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.ChaKanBaoBiao);
		yiwenfankui = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.YiWenFanKui);
		ly_hetong = (LinearLayout) findViewById(R.id.ly_hetong);

		caiwu.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DisplayToast("查询村内财务情况以及处理疑问反馈！");
			}
		});
		ly_hetong.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				HeTongGuanLiActivity.this.finish();

			}
		});
		yiwenfankui.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(HeTongGuanLiActivity.this,
						ChaKanMoBanActivity.class);

				startActivity(intent);
				HeTongGuanLiActivity.this.finish();
			}
		});
		chakanbaobiao.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// DisplayToast("网上查询，暂未开通");
				Intent intent = new Intent();
				// intent.setClass(CaiWuGuanLi.this, CW_ChaKanBaoBiao.class);
				intent.setClass(HeTongGuanLiActivity.this, HeTongService.class);
				startActivity(intent);
				HeTongGuanLiActivity.this.finish();
			}
		});
		// yiwenfankui.setOnClickListener(new Button.OnClickListener() {

		// @Override
		// public void onClick(View v) {
		// TODO Auto-generated method stub
		// DisplayToast("方法一：拨打电话，方法二：网上反馈，暂未开通");
		// }
		// });
		handler = new Handler() {

			@SuppressLint("HandlerLeak")
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				updating();
			}
		};
		handler.sendEmptyMessageDelayed(0, 1000);
	}

	protected void updating() {

		String sampleURL = SERVICE_URL + "/caiwu";
		WebServiceTask wst = new WebServiceTask(WebServiceTask.GET_TASK, this,
				"正在更新，请稍候...");
		wst.execute(new String[] { sampleURL });
	}

	public void handleResponse(String response) {
		caiwugonggao = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.caiwugonggao);
		shijian = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.shijian);
		try {

			JSONObject jso = new JSONObject(response);

			String announcement = jso.optString("gonggao");
			String time = jso.optString("time");

			caiwugonggao.setText(announcement);
			shijian.setText(time);

		} catch (Exception e) {
			Log.e(TAG, e.getLocalizedMessage(), e);
		}

	}

	// 隐藏键盘
	private void hideKeyboard() {

		InputMethodManager inputManager = (InputMethodManager) HeTongGuanLiActivity.this
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		inputManager.hideSoftInputFromWindow(HeTongGuanLiActivity.this
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

	private void DisplayToast(String string) {
		// TODO Auto-generated method stub
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getRepeatCount() == 0)) {

			// Intent intent = new Intent();
			// intent.setClass(CaiWuGuanLi.this, CaiDanLan.class);
			// startActivity(intent);
			HeTongGuanLiActivity.this.finish();

		}
		return super.onKeyDown(keyCode, event);
	}

	// public class TimeThread extends Thread {
	// @Override
	// public void run() {
	// do {
	// try {
	// Thread.sleep(1000);
	// Message msg = new Message();
	// msg.what = msgKey1;
	// mHandler.sendMessage(msg);
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	// } while (true);
	// }
	// }
	//
	// private Handler mHandler = new Handler() {
	// @Override
	// public void handleMessage(Message msg) {
	// super.handleMessage(msg);
	// switch (msg.what) {
	// case msgKey1:
	// long sysTime = System.currentTimeMillis();
	// CharSequence sysTimeStr = DateFormat.format("yyyy年MM月dd日",
	// sysTime);
	// shijian.setText(sysTimeStr);
	// break;
	//
	// default:
	// break;
	// }
	// }
	// };

}
