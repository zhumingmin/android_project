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

import com.minxing.util.CustomArrayAdapter;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class BaoJianService extends Activity {
	private static final String SERVICE_URL = "http://192.168.191.1:8080/RestWebServiceDemo/rest/baojianguanli";
	private static final String TAG = "BaoJianGuanLi";
	private Button tijiao2, chaxunjindu;
	private ImageButton fanhui;
	private EditText et_baojiandizhi, et_lianxifangshi;
	private TextView baojian;
	private Spinner sp_baojianxiangmu, sp_yuyueshijian;
	private static final String[] kexuanxiangmu = { "项目一", "项目二", "项目三", "项目四",
			"项目五" };
	private static final String[] kexuanshijian = { "周一上午9:00~10:00",
			"周一下午3:00~4:00", "周二上午9:00~10:00", "周二下午3:00~4:00",
			"周三上午9:00~10:00", "周三下午3:00~4:00", "周四上午9:00~10:00",
			"周四下午3:00~4:00", "周五上午9:00~10:00", "周五下午3:00~4:00" };
	ProgressDialog m_pDialog;
	int m_count = 0;
	LinearLayout ly_baojian;
	private CustomArrayAdapter<CharSequence> mAdapter;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.zhumingmin.vmsofminxing.R.layout.webservice_baojianguanli);
		ly_baojian = (LinearLayout) findViewById(R.id.ly_baojian);

		fanhui = (ImageButton) findViewById(com.zhumingmin.vmsofminxing.R.id.FanHui);
		baojian = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.baojian);
		chaxunjindu = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.ChaXunJinDu);
		et_baojiandizhi = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.et_baojiandizhi);
		et_lianxifangshi = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.et_lianxifangshi);
		tijiao2 = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.TiJiao2);
		baojian.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DisplayToast("办理报建业务！");
			}
		});

		et_baojiandizhi.setHint("如民兴村3号");
		et_lianxifangshi.setHint("如1234567890");

		String[] kexuanxiangmu = getResources().getStringArray(R.array.item1);
		this.mAdapter = new CustomArrayAdapter<CharSequence>(this,
				kexuanxiangmu);

		String[] kexuanshijian = getResources().getStringArray(R.array.item2);
		this.mAdapter = new CustomArrayAdapter<CharSequence>(this,
				kexuanshijian);

		sp_baojianxiangmu = (Spinner) findViewById(com.zhumingmin.vmsofminxing.R.id.sp_baojianxiangmu);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, kexuanxiangmu);
		sp_baojianxiangmu.setAdapter(adapter);
		sp_baojianxiangmu
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						arg0.setVisibility(View.VISIBLE);
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}

				});
		sp_yuyueshijian = (Spinner) findViewById(com.zhumingmin.vmsofminxing.R.id.sp_yuyueshijian);
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, kexuanshijian);
		sp_yuyueshijian.setAdapter(adapter1);
		sp_yuyueshijian
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						arg0.setVisibility(View.VISIBLE);
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}

				});
		ly_baojian.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				BaoJianService.this.finish();

			}
		});

		chaxunjindu.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DisplayToast("网上查询，暂未开通");
			}
		});
	}

	public void postData(View vw) {
		TextView tx_spinner1 = (TextView) sp_baojianxiangmu.getSelectedView();
		String baojianxiangmu = (String) tx_spinner1.getText();
		String baojiandizhi = et_baojiandizhi.getText().toString();
		String lianxifangshi = et_lianxifangshi.getText().toString();
		TextView tx_spinner2 = (TextView) sp_yuyueshijian.getSelectedView();
		String yuyueshijian = (String) tx_spinner2.getText();

		if (baojianxiangmu.equals("") || baojiandizhi.equals("")
				|| lianxifangshi.equals("") || yuyueshijian.equals("")) {
			Toast.makeText(this, "您还有项目未填写哦！", Toast.LENGTH_LONG).show();
			return;
		}

		WebServiceTask wst = new WebServiceTask(WebServiceTask.POST_TASK, this,
				"提交中，请稍候...");

		wst.addNameValuePair("baojianxiangmu", baojianxiangmu);
		wst.addNameValuePair("baojiandizhi", baojiandizhi);
		wst.addNameValuePair("lianxifangshi", lianxifangshi);
		wst.addNameValuePair("yuyueshijian", yuyueshijian);

		// the passed String is the URL we will POST to
		wst.execute(new String[] { SERVICE_URL });

	}

	public void handleResponse(String response) {

		// sp_baojianxiangmu.setText("");
		et_baojiandizhi.setText("");
		et_lianxifangshi.setText("");
		// sp_yuyueshijian.setText("");

		try {

			JSONObject jso = new JSONObject(response);

			// String baojianxiangmu = jso.getString("baojianxiangmu");
			String baojiandizhi = jso.getString("baojiandizhi");
			String lianxifangshi = jso.getString("lianxifangshi");
			// String yuyueshijian = jso.getString("yuyueshijian");

			// sp_baojianxiangmu.setText(baojianxiangmu);
			et_baojiandizhi.setText(baojiandizhi);
			et_lianxifangshi.setText(lianxifangshi);
			// sp_yuyueshijian.setText(yuyueshijian);

		} catch (Exception e) {
			Log.e(TAG, e.getLocalizedMessage(), e);
		}

	}

	// 隐藏键盘
	private void hideKeyboard() {

		InputMethodManager inputManager = (InputMethodManager) BaoJianService.this
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		inputManager.hideSoftInputFromWindow(BaoJianService.this
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

			BaoJianService.this.finish();

		}
		return super.onKeyDown(keyCode, event);
	}
}
