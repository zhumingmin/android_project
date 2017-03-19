package cn.minxing.restwebservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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

import cn.minxing.activity.ShenFenRenZhengActivity;
import cn.minxing.activity.TouPiaoJieGuoActivity;
import cn.minxing.rsystem.SerachDetailActivity;
import cn.minxing.rsystem.SerachListActivity;
import cn.minxing.util.CustomArrayAdapter;
import cn.minxing.util.RS_News;
import cn.minxing.util.RS_NewsAdapter;
import cn.minxing.util.TP_PiaoShu;
import cn.minxing.util.TP_PiaoShuAdapter;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class MinYiZhengJiService extends Activity {
	private Button zhichi, weiguan, bt_renzheng_minyizhengji;
	private EditText huoqucanxuanzhiwu, huoqugerenshiji, huoqupiaoshu;
	private Spinner xuanzecanxuanren;
	private TextView shijian, toupiaogonggao;
	static String account;
	ProgressDialog m_pDialog;
	int m_count = 0;
	private LinearLayout ly_fanhui, ll_weirenzheng_minyizhengji,
			ll_yirenzheng_minyizhengji;
	private CustomArrayAdapter<CharSequence> mAdapter;

	private static final String SERVICE_URL = "http://192.168.191.1:8080/RestWebServiceDemo/rest/vote";

	private static final String TAG = "MinYiZhengJiActivity";
	private Handler handler;
	boolean isReqing = false;
	static String gonggaolan, gonggaoshijian;
	String[] strArray = null;
	String[] strArray1 = null;
	String[] strArray2 = null;
	String[] strArray3 = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.zhumingmin.vmsofminxing.R.layout.activity_minyizhengji);

		bt_renzheng_minyizhengji = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.bt_renzheng_minyizhengji);
		zhichi = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.zhichi);
		weiguan = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.weiguan);
		shijian = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.toupiaoshijian);
		toupiaogonggao = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.toupiaogonggao);
		// new TimeThread().start();
		xuanzecanxuanren = (Spinner) findViewById(com.zhumingmin.vmsofminxing.R.id.xuanzecanxuanren);
		huoqucanxuanzhiwu = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.huoqucanxuanzhiwu);
		huoqugerenshiji = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.huoqugerenshiji);
		huoqupiaoshu = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.huoqupiaoshu);

		ll_weirenzheng_minyizhengji = (LinearLayout) findViewById(R.id.ll_weirenzheng_minyizhengji);
		ll_yirenzheng_minyizhengji = (LinearLayout) findViewById(R.id.ll_yirenzheng_minyizhengji);
		LoginService ls = new LoginService();
		account = ls.renzheng();
		if (account.equals("已认证")) {
			// 如果用户属于已认证的状态
			ll_weirenzheng_minyizhengji.setVisibility(View.INVISIBLE);
		} else {

			// 如果用户属于未认证的状态
			//ll_yirenzheng_minyizhengji.setVisibility(View.INVISIBLE);
		}
		// 如果用户属于已认证的状态
		// ll_weirenzheng_minyizhengji.setVisibility(View.INVISIBLE);

		// 如果用户属于未认证的状态
		// ll_yirenzheng_minyizhengji.setVisibility(View.INVISIBLE);

		bt_renzheng_minyizhengji.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				intent = new Intent(MinYiZhengJiService.this,
						ShenFenRenZhengActivity.class);
				startActivity(intent);

			}
		});
		ly_fanhui = (LinearLayout) findViewById(R.id.ly_fanhui_toupiao);
		ly_fanhui.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		handler = new Handler() {

			@SuppressLint("HandlerLeak")
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				if (!isReqing) {
					// updating();
					updating();
					isReqing = true;
				} else {
					toupiaogonggao.setText(gonggaolan);
					shijian.setText(gonggaoshijian);
				}
			}
		};
		handler.sendEmptyMessageDelayed(0, 1000);
		String[] kexuancanxuanren = getResources()
				.getStringArray(R.array.item4);
		this.mAdapter = new CustomArrayAdapter<CharSequence>(this,
				kexuancanxuanren);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, kexuancanxuanren);
		xuanzecanxuanren.setAdapter(adapter);
		xuanzecanxuanren
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						arg0.setVisibility(View.VISIBLE);
						// huoqucanxuanzhiwu.setText("副书记");
						// huoqugerenshiji.setText("水利建设主要负责人");
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}

				});

		zhichi.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DisplayToast("您已支持该参选人！");
				int a = Integer.parseInt(huoqupiaoshu.getText().toString());
				int b = a + 1;
				String piaoshu = Integer.toString(b);
				huoqupiaoshu.setText(piaoshu);
				TextView spinnertotextview = (TextView) xuanzecanxuanren
						.getSelectedView();
				String canxuanren = spinnertotextview.getText().toString();
				WebServiceTask wst = new WebServiceTask(
						WebServiceTask.POST_TASK, MinYiZhengJiService.this,
						"上传中…");
				wst.addNameValuePair("piaoShu", piaoshu);
				wst.addNameValuePair("canXuanRen", canxuanren);

				wst.execute(new String[] { SERVICE_URL });
				Intent intent = new Intent(MinYiZhengJiService.this,
						TouPiaoJieGuoActivity.class);
				intent.putExtra("canxuanren", strArray);
				intent.putExtra("canxuanzhiwu", strArray1);
				intent.putExtra("gerenshiji", strArray2);
				intent.putExtra("piaoshu", strArray3);
				startActivity(intent);
				finish();
			}
		});

		weiguan.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DisplayToast("选出自己心目中的好村委！");
				Intent intent = new Intent(MinYiZhengJiService.this,
						TouPiaoJieGuoActivity.class);

				intent.putExtra("canxuanren", strArray);
				intent.putExtra("canxuanzhiwu", strArray1);
				intent.putExtra("gerenshiji", strArray2);
				intent.putExtra("piaoshu", strArray3);
				startActivity(intent);
				finish();

			}
		});

	}

	public static String[] convertStrToArray(String str) {
		String[] strArray = null;
		strArray = str.split(","); // 拆分字符为"," ,然后把结果交给数组strArray
		return strArray;
	}

	// protected void updating() {
	// String sampleURL = SERVICE_URL + "/toupiao";
	// WebServiceTask wst = new WebServiceTask(WebServiceTask.GET_TASK, this,
	// "加载中…");
	// wst.execute(new String[] { sampleURL });
	// }

	protected void updating() {
		String sampleURL = SERVICE_URL + "/minyi";
		WebServiceTask wst = new WebServiceTask(WebServiceTask.GET_TASK, this,
				"加载中…");
		wst.execute(new String[] { sampleURL });
	}

	public void handleResponse(String response) {
		shijian = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.toupiaoshijian);
		toupiaogonggao = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.toupiaogonggao);
		xuanzecanxuanren = (Spinner) findViewById(com.zhumingmin.vmsofminxing.R.id.xuanzecanxuanren);
		huoqucanxuanzhiwu = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.huoqucanxuanzhiwu);
		huoqugerenshiji = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.huoqugerenshiji);
		huoqupiaoshu = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.huoqupiaoshu);

		try {

			JSONObject jso = new JSONObject(response);

			String canxuanren = jso.optString("canXuanRen");
			String canxuanzhiwu = jso.optString("canXuanZhiWu");
			String gerenshiji = jso.optString("geRenShiJi");
			String piaoshu = jso.optString("piaoShu");
			String announcement = jso.optString("gonggao");
			String time = jso.optString("time");
			gonggaolan = announcement;
			gonggaoshijian = time;
			toupiaogonggao.setText(announcement);
			shijian.setText(time);
			String pattern = "([-*/^()\\]\\[])";
			canxuanren = canxuanren.replaceAll(pattern, "");
			canxuanzhiwu = canxuanzhiwu.replaceAll(pattern, "");
			gerenshiji = gerenshiji.replaceAll(pattern, "");
			piaoshu = piaoshu.replaceAll(pattern, "");

			strArray = convertStrToArray(canxuanren);
			strArray1 = convertStrToArray(canxuanzhiwu);
			strArray2 = convertStrToArray(gerenshiji);
			strArray3 = convertStrToArray(piaoshu);
			for (int i = 0; i < strArray.length; i++) {
				strArray[i] = strArray[i].replace("\"", "");
				strArray1[i] = strArray1[i].replace("\"", "");
				strArray2[i] = strArray2[i].replace("\"", "");
				strArray3[i] = strArray3[i].replace("\"", "");

			}
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_dropdown_item, strArray);
			xuanzecanxuanren.setAdapter(adapter);
			xuanzecanxuanren
					.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> arg0,
								View arg1, int arg2, long arg3) {
							// TODO Auto-generated method stub
							arg0.setVisibility(View.VISIBLE);
							huoqugerenshiji.setText(strArray1[arg2]);
							huoqucanxuanzhiwu.setText(strArray2[arg2]);
							huoqupiaoshu.setText(strArray3[arg2]);
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub

						}

					});

		} catch (Exception e) {
			Log.e(TAG, e.getLocalizedMessage(), e);
		}

	}

	// 隐藏键盘
	private void hideKeyboard() {

		InputMethodManager inputManager = (InputMethodManager) MinYiZhengJiService.this
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		inputManager.hideSoftInputFromWindow(MinYiZhengJiService.this
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
			// intent.setClass(MinYiZhengJi.this, SheZhi.class);
			// startActivity(intent);
			MinYiZhengJiService.this.finish();
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
