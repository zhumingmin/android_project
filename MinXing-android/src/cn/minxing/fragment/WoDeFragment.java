package cn.minxing.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

import cn.minxing.PushMessage.ExitApplication;
import cn.minxing.activity.ChatRobtActivity;
import cn.minxing.activity.MinXingSheQuActivity;
import cn.minxing.activity.ReadTagActivity;
import cn.minxing.activity.RssActivity;
import cn.minxing.activity.SZ_GRXXActivity;
import cn.minxing.activity.SZ_SheZhiActivity;
import cn.minxing.activity.SerachView;
import cn.minxing.activity.SheZhiActivity;
import cn.minxing.activity.TianQiChaXunActivity;
import cn.minxing.activity.TongXunLuActivity;
import cn.minxing.restwebservice.LoginService;
import cn.minxing.restwebservice.MinYiZhengJiService;
import cn.minxing.restwebservice.RegisterService;
import cn.minxing.restwebservice.YiBaoService;

import com.umeng.comm.core.CommunitySDK;
import com.umeng.comm.core.impl.CommunityFactory;
import com.umeng.comm.core.sdkmanager.ShareSDKManager;
import com.zhumingmin.vmsofminxing.R;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings.Secure;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.telephony.TelephonyManager;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.comm.ui.fragments.CommunityMainFragment;

@SuppressLint("NewApi")
public class WoDeFragment extends Fragment {
	private Handler handler;
	private TableRow tr_gerenxinxi, tr_toupiao, tr_shequ, tr_tongxun,
			tr_xinxichaxun, tr_tongxunlu, tr_xianliao, tr_shezhi, tr_rss;
	private static final String SERVICE_URL = "http://192.168.191.1:8080/RestWebServiceDemo/rest/myaccount";

	private static final String TAG = "WoDeFragment";
	TextView name, phone;
	static String account;

	@SuppressLint("HandlerLeak")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// View v = inflater.inflate(R.layout.fragment_shezhi, container,
		// false);
		// 2016.11.23更改
		View v = inflater.inflate(R.layout.fragment_shezhi_new, container,
				false);
		ExitApplication.getInstance().addActivity(getActivity());

		tr_gerenxinxi = (TableRow) v.findViewById(R.id.tr_gerenxinxi);
		tr_toupiao = (TableRow) v.findViewById(R.id.tr_toupiao);
		// tr_shequ = (TableRow) v.findViewById(R.id.tr_shequ);
		// tr_tongxun = (TableRow) v.findViewById(R.id.tr_tongxun);
		tr_xinxichaxun = (TableRow) v.findViewById(R.id.tr_xinxichaxun);
		tr_tongxunlu = (TableRow) v.findViewById(R.id.tr_tongxunlu);
		tr_xianliao = (TableRow) v.findViewById(R.id.tr_xianliao);
		tr_shezhi = (TableRow) v.findViewById(R.id.tr_shezhi);
		tr_rss = (TableRow) v.findViewById(R.id.tr_rss);
		name = (TextView) v.findViewById(R.id.name);
		phone = (TextView) v.findViewById(R.id.phone);

		// 希望能实现获取账号信息后不再访问后台

		// handler = new Handler() {
		//
		// @SuppressLint("HandlerLeak")
		// @Override
		// public void handleMessage(Message msg) {
		// // TODO Auto-generated method stub
		// super.handleMessage(msg);
		//
		// postSampleData();
		// retrieveSampleData();
		//
		// }
		// };
		// handler.sendEmptyMessageDelayed(0, 2000);

		tr_toupiao.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				intent = new Intent(getActivity(), MinYiZhengJiService.class);
				startActivity(intent);
			}
		});
		tr_tongxunlu.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				intent = new Intent(getActivity(), TongXunLuActivity.class);
				startActivity(intent);
			}
		});

		/*
		 * //SDK出现了问题 tr_shequ.setOnClickListener(new Button.OnClickListener() {
		 * 
		 * @Override public void onClick(View v) {
		 * 
		 * // TODO Auto-generated method stub DisplayToast("稍后开通！");
		 * 
		 * CommunitySDK mCommSDK = CommunityFactory .getCommSDK(getActivity());
		 * // 打开微社区的接口, 参数1为Context类型 mCommSDK.openCommunity(getActivity());
		 * 
		 * }
		 * 
		 * });
		 */

		/*
		 * //根本没实现 tr_tongxun.setOnClickListener(new Button.OnClickListener() {
		 * 
		 * @Override public void onClick(View v) {
		 * 
		 * // TODO Auto-generated method stub DisplayToast("稍后开通！"); }
		 * 
		 * });
		 */
		tr_xinxichaxun.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (hasNfc(getActivity())) {
					Intent intent = new Intent();
					intent = new Intent(getActivity(), ReadTagActivity.class);
					startActivity(intent);
				} else {
					DisplayToast("您的设备暂不支持该功能！");
				}
			}

		});
		tr_xianliao.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				intent = new Intent(getActivity(), ChatRobtActivity.class);
				startActivity(intent);
			}
		});
		tr_rss.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				intent = new Intent(getActivity(), RssActivity.class);
				startActivity(intent);
			}
		});
		tr_shezhi.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String data1 = name.getText().toString();
				String data2 = phone.getText().toString();
				String data3 = account;
				Intent intent = new Intent();
				intent = new Intent(getActivity(), SZ_SheZhiActivity.class);
				intent.putExtra("data1", data1);
				intent.putExtra("data2", data2);
				intent.putExtra("data3", data3);
				startActivity(intent);

			}
		});
		tr_gerenxinxi.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String data1 = name.getText().toString();
				String data2 = phone.getText().toString();
				String data3 = account;
				Intent intent = new Intent();
				intent = new Intent(getActivity(), SZ_GRXXActivity.class);
				intent.putExtra("data1", data1);
				intent.putExtra("data2", data2);
				intent.putExtra("data3", data3);
				startActivity(intent);
			}
		});

		return v;

	}

	@SuppressLint("NewApi")
	public static boolean hasNfc(Context context) {
		boolean bRet = false;
		if (context == null)
			return bRet;
		NfcManager manager = (NfcManager) context
				.getSystemService(Context.NFC_SERVICE);
		NfcAdapter adapter = manager.getDefaultAdapter();
		if (adapter != null && adapter.isEnabled()) {
			// adapter存在，能启用
			bRet = true;
		}
		return bRet;
	}

	// 从服务器端获取信息 GET方法
	public void retrieveSampleData() {

		String sampleURL = SERVICE_URL + "/cx";
		WebServiceTask wst = new WebServiceTask(WebServiceTask.GET_TASK,
				getActivity(), "加载中…");
		wst.execute(new String[] { sampleURL });

	}

	public void postSampleData() {
		LoginService ls = new LoginService();
		account = ls.account();

		// account = "340881199211110332";

		if (account.equals("")) {
			Toast.makeText(getActivity().getApplicationContext(), "账号有误！", 0)
					.show();
		}

		WebServiceTask wst = new WebServiceTask(WebServiceTask.POST_TASK,
				getActivity(), "Posting data...");

		wst.addNameValuePair("account", account);

		wst.execute(new String[] { SERVICE_URL });

	}

	public void handleResponse(String response) {

		try {

			JSONObject jso = new JSONObject(response);

			String xingming = jso.optString("name");
			String dianhua = jso.optString("phonenumber");

			name.setText(xingming);
			phone.setText(dianhua);

		} catch (Exception e) {
			Log.e(TAG, e.getLocalizedMessage(), e);
		}

	}

	// 隐藏键盘
	private void hideKeyboard() {

		InputMethodManager inputManager = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus()
				.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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
			// if (response != null) {
			// Toast.makeText(getApplicationContext(), "查询成功！", 0).show();
			//
			// } else {
			// Toast.makeText(getApplicationContext(), "查询失败！", 0).show();
			// }
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

	protected void DisplayToast(String string) {

		// TODO Auto-generated method stub
		Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT).show();
	}

	/*
	 * 即可实现在fragment可见时才进行数据加载操作，即Fragment的懒加载。
	 * 
	 * @see android.support.v4.app.Fragment#setUserVisibleHint(boolean)
	 */
	// 以下的方法可以实现单个fragment的网络加载，而不会带动上下文fragment的动态加载
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			// 相当于Fragment的onResume
			// handler = new Handler() {
			//
			// @SuppressLint("HandlerLeak")
			// @Override
			// public void handleMessage(Message msg) {
			// // TODO Auto-generated method stub
			// super.handleMessage(msg);

			postSampleData();
			retrieveSampleData();

			// }
			// };
			// handler.sendEmptyMessageDelayed(0, 0);
		} else {
			// 相当于Fragment的onPause
		}
	}
}
