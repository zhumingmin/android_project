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
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import com.zhumingmin.vmsofminxing.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class HeTongService extends Activity {

	public HeTongService() {
		// TODO Auto-generated constructor stub
	}

	private static final String SERVICE_URL = "http://192.168.191.1:8080/RestWebServiceDemo/rest/hetongguanli";
	private static final String TAG = "HeTongGuanLi";
	private TextView yuanyin;
	private EditText et_jiafang, et_yifang, et_yuedingfeiyong,
			et_qiandingnianxian, et_qitashuoming;
	private ImageButton fanhuishangyiye;
	private Button tijiaohetong;
	int m_count = 0;
	ProgressDialog m_pDialog;
	LinearLayout ly_tianxiehetong;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.zhumingmin.vmsofminxing.R.layout.webservice_zaixiantianxiehetong);
		ly_tianxiehetong = (LinearLayout) findViewById(R.id.ly_tianxiehetong);

		et_jiafang = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.et_jiafang);
		et_yifang = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.et_yifang);
		et_yuedingfeiyong = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.et_yuedingfeiyong);
		et_qiandingnianxian = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.et_qiandingnianxian);
		et_qitashuoming = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.et_qitashuoming);

		fanhuishangyiye = (ImageButton) findViewById(com.zhumingmin.vmsofminxing.R.id.FanHuiShangYiYe);
		tijiaohetong = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.tijiaohetong);

		ly_tianxiehetong.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(HeTongService.this, HeTongGuanLiService.class);
				startActivity(intent);
				HeTongService.this.finish();

			}
		});
	}

	public void postData(View vw) {

		String jiafang = et_jiafang.getText().toString();
		String yifang = et_yifang.getText().toString();
		String yuedingfeiyong = et_yuedingfeiyong.getText().toString();
		String qiandingnianxian = et_qiandingnianxian.getText().toString();
		String qitashuoming = et_qitashuoming.getText().toString();

		if (jiafang.equals("") || yifang.equals("")
				|| yuedingfeiyong.equals("") || qiandingnianxian.equals("")
				|| qitashuoming.equals("")) {
			Toast.makeText(this, "您还有项目未填写哦！", Toast.LENGTH_LONG).show();
			return;
		}

		WebServiceTask wst = new WebServiceTask(WebServiceTask.POST_TASK, this,
				"提交中，请稍候...");

		wst.addNameValuePair("jiafang", jiafang);
		wst.addNameValuePair("yifang", yifang);
		wst.addNameValuePair("yuedingfeiyong", yuedingfeiyong);
		wst.addNameValuePair("qiandingnianxian", qiandingnianxian);
		wst.addNameValuePair("qitashuoming", qitashuoming);

		// the passed String is the URL we will POST to
		wst.execute(new String[] { SERVICE_URL });

	}

	public void handleResponse(String response) {

		et_jiafang.setText("");
		et_yifang.setText("");
		et_yuedingfeiyong.setText("");
		et_qiandingnianxian.setText("");
		et_qitashuoming.setText("");

		try {

			JSONObject jso = new JSONObject(response);

			String jiafang = jso.getString("jiafang");
			String yifang = jso.getString("yifang");
			String yuedingfeiyong = jso.getString("yuedingfeiyong");
			String qiandingnianxian = jso.getString("qiandingnianxian");
			String qitashuoming = jso.getString("qitashuoming");
			et_jiafang.setText(jiafang);
			et_yifang.setText(yifang);
			et_yuedingfeiyong.setText(yuedingfeiyong);
			et_qiandingnianxian.setText(qiandingnianxian);
			et_qitashuoming.setText(qitashuoming);

		} catch (Exception e) {
			Log.e(TAG, e.getLocalizedMessage(), e);
		}

	}

	// 隐藏键盘
	private void hideKeyboard() {

		InputMethodManager inputManager = (InputMethodManager) HeTongService.this
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		inputManager.hideSoftInputFromWindow(HeTongService.this
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
			if (response != null) {
				Toast.makeText(getApplicationContext(), "上传成功！", 0).show();

			} else {
				Toast.makeText(getApplicationContext(), "上传失败！", 0).show();
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
					// Add parameters 解决服务器端中文乱码的问题
					httppost.setEntity(new UrlEncodedFormEntity(params,
							HTTP.UTF_8));
					// httppost.setEntity(new
					// StringEntity(params.toString(),HTTP.UTF_8));
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
			intent.setClass(HeTongService.this, HeTongGuanLiService.class);
			startActivity(intent);
			HeTongService.this.finish();

		}
		return super.onKeyDown(keyCode, event);
	}
}
