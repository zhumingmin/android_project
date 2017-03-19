package cn.minxing.restwebservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
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
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import cn.minxing.activity.TongXunLuActivity;
import cn.minxing.activity.YeWuBanLiActivity;
import cn.minxing.util.CustomArrayAdapter;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.zhumingmin.vmsofminxing.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
	private TextView baojian, tv_tijiao, tv_chuli, tv_wancheng;
	private Spinner sp_baojianxiangmu, sp_yuyueshijian;
	private static final String[] kexuanxiangmu = { "项目一", "项目二", "项目三", "项目四",
			"项目五" };
	private static final String[] kexuanshijian = { "周一上午9:00~10:00",
			"周一下午3:00~4:00", "周二上午9:00~10:00", "周二下午3:00~4:00",
			"周三上午9:00~10:00", "周三下午3:00~4:00", "周四上午9:00~10:00",
			"周四下午3:00~4:00", "周五上午9:00~10:00", "周五下午3:00~4:00" };
	ProgressDialog m_pDialog;
	int m_count = 0;
	LinearLayout ly_baojian, ll_chaxun;
	private CustomArrayAdapter<CharSequence> mAdapter;
	public static final int SHOW_LOCATION = 0;
	private LocationManager locationManager;
	private String provider;

	// 声明AMapLocationClient类对象
	public AMapLocationClient mLocationClient = null;
	// 声明定位回调监听器
	public AMapLocationListener mLocationListener = new AMapLocationListener() {

		@Override
		public void onLocationChanged(AMapLocation amapLocation) {
			// TODO Auto-generated method stub
			if (amapLocation != null) {
				if (amapLocation.getErrorCode() == 0) {
					// 可在其中解析amapLocation获取相应内容。
					double locationType = amapLocation.getLocationType();// 获取当前定位结果来源，如网络定位结果，详见定位类型表
					double latitude = amapLocation.getLatitude();// 获取纬度

					Log.e("Amap==经度：纬度", "locationType:" + locationType
							+ ",latitude:" + latitude);
					et_baojiandizhi.setText(amapLocation.getAddress());
				} else {
					// 定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
					Log.e("AmapError", "location Error, ErrCode:"
							+ amapLocation.getErrorCode() + ", errInfo:"
							+ amapLocation.getErrorInfo());
				}
			}
		}
	};

	// 声明AMapLocationClientOption对象
	public AMapLocationClientOption mLocationOption = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.zhumingmin.vmsofminxing.R.layout.webservice_baojianguanli);
		ly_baojian = (LinearLayout) findViewById(R.id.ly_baojian);
		ll_chaxun = (LinearLayout) findViewById(R.id.ll_chaxun);
		fanhui = (ImageButton) findViewById(com.zhumingmin.vmsofminxing.R.id.FanHui);
		baojian = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.baojian);
		tv_tijiao = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.tv_tijiao);
		tv_chuli = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.tv_chuli);
		tv_wancheng = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.tv_wancheng);
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

				finish();

			}
		});

		chaxunjindu.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				intent = new Intent(BaoJianService.this, ChaXunService.class);
				startActivity(intent);
			}
		});

		// 初始化定位
		mLocationClient = new AMapLocationClient(getApplicationContext());
		// 设置定位回调监听
		mLocationClient.setLocationListener(mLocationListener);

		// 初始化AMapLocationClientOption对象
		mLocationOption = new AMapLocationClientOption();
		// 设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
		mLocationOption.setLocationMode(AMapLocationMode.Battery_Saving);

		// 给定位客户端对象设置定位参数
		mLocationClient.setLocationOption(mLocationOption);
		// 启动定位
		mLocationClient.startLocation();

		/**
		 * 获取一次定位
		 */
		// 该方法默认为false，true表示只定位一次
		mLocationOption.setOnceLocation(true);

		// /*
		// * 通过locationManager实现定位功能（将经纬度通过Geocoding API进行反向解码）
		// */
		// locationManager = (LocationManager)
		// getSystemService(Context.LOCATION_SERVICE);
		// // 获取所有可用的位置提供器
		// List<String> providerList = locationManager.getProviders(true);
		// if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
		// provider = LocationManager.GPS_PROVIDER;
		// } else if (providerList.contains(LocationManager.GPS_PROVIDER)) {
		// provider = LocationManager.NETWORK_PROVIDER;
		// } else {
		// // 当没有可用的位置提供器时，弹出Toast提示用户
		// Toast.makeText(this, "No location provider to use",
		// Toast.LENGTH_SHORT).show();
		// return;
		// }
		// Location location = locationManager.getLastKnownLocation(provider);
		// if (location != null) {
		// // 显示当前设备的位置信息
		// showLocation(location);
		// }
		// locationManager.requestLocationUpdates(provider, 5000, 1,
		// locationListener);
		// }
		//
		// protected void onDestroy() {
		// super.onDestroy();
		// if (locationManager != null) {
		// // 关闭程序时将监听器移除
		// locationManager.removeUpdates(locationListener);
		// }
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		mLocationClient.stopLocation();
	}

	//
	// LocationListener locationListener = new LocationListener() {
	//
	// @Override
	// public void onStatusChanged(String provider, int status, Bundle extras) {
	// }
	//
	// @Override
	// public void onProviderEnabled(String provider) {
	// }
	//
	// @Override
	// public void onProviderDisabled(String provider) {
	// }
	//
	// @Override
	// public void onLocationChanged(Location location) {
	// // 更新当前设备的位置信息
	// showLocation(location);
	// }
	// };
	//
	// private void showLocation(final Location location) {
	// // String currentPosition =
	// //
	// "latitude is"+location.getLatitude()+"\n"+"longitude is"+location.getLongitude();
	// // et_baojiandizhi.setText(currentPosition);
	// new Thread(new Runnable() {
	// @Override
	// public void run() {
	// try {
	// // 组装反向地理编码的接口地址
	// StringBuilder url = new StringBuilder();
	// url.append("http://maps.googleapis.com/maps/api/geocode/json?latlng=");
	// url.append(location.getLatitude()).append(",")
	// .append(location.getLongitude());
	// url.append("&sensor=true");
	// // AIzaSyB5LUMOXNvNn0DiLnHGJY9wSfqj-e4wJkE
	// HttpClient httpClient = new DefaultHttpClient();
	// HttpGet httpGet = new HttpGet(url.toString());
	// // 在请求消息头中指定语言，保证服务器会返回中文数据
	// httpGet.addHeader("Accept-Language", "zh-CN");
	// HttpResponse httpResponse = httpClient.execute(httpGet);
	// if (httpResponse.getStatusLine().getStatusCode() == 200) {
	// HttpEntity entity = httpResponse.getEntity();
	// String response = EntityUtils.toString(entity, "utf-8");
	// JSONObject jsonObject = new JSONObject(response);
	// // 获取results节点下的位置信息
	// JSONArray resultArray = jsonObject
	// .getJSONArray("results");
	// if (resultArray.length() > 0) {
	// JSONObject subObject = resultArray.getJSONObject(0);
	// // 取出格式化后的位置信息
	// String address = subObject
	// .getString("formatted_address");
	// Message message = new Message();
	// message.what = SHOW_LOCATION;
	// message.obj = address;
	// handler.sendMessage(message);
	// }
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// }).start();
	// }
	//
	// /*
	// * 开启一个线程来实现位置的显示
	// */
	// private Handler handler = new Handler() {
	//
	// public void handleMessage(Message msg) {
	// switch (msg.what) {
	// case SHOW_LOCATION:
	// String currentPosition = (String) msg.obj;
	// et_baojiandizhi.setText(currentPosition);
	// break;
	// default:
	// break;
	// }
	// }
	//
	// };

	public void postData(View vw) {
		TextView tx_spinner1 = (TextView) sp_baojianxiangmu.getSelectedView();
		String baojianxiangmu = (String) tx_spinner1.getText();
		String baojiandizhi = et_baojiandizhi.getText().toString();
		String lianxifangshi = et_lianxifangshi.getText().toString();
		TextView tx_spinner2 = (TextView) sp_yuyueshijian.getSelectedView();
		String yuyueshijian = (String) tx_spinner2.getText();

		if (baojiandizhi.equals("")) {
			Toast.makeText(getApplicationContext(), "报建地址不能为空！", 0).show();
			return;
		}
		if (lianxifangshi.equals("")) {
			Toast.makeText(getApplicationContext(), "联系方式不能为空！", 0).show();
			return;
		}

		WebServiceTask wst = new WebServiceTask(WebServiceTask.POST_TASK, this,
				"提交中…");

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

			finish();

		}
		return super.onKeyDown(keyCode, event);
	}
}
