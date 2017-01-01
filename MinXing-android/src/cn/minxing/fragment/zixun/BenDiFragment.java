package cn.minxing.fragment.zixun;

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

import cn.minxing.PushMessage.ExitApplication;
import cn.minxing.activity.ZiXunDetailActivity;
import cn.minxing.fragment.MyFragment;
import cn.minxing.fragment.ZiXunFragment;
import cn.minxing.util.ZiXun;
import cn.minxing.util.ZiXunListViewAdapter;
import cn.minxing.view.PullToRefreshListView;
import cn.minxing.view.PullToRefreshListView.OnRefreshListener;

import com.zhumingmin.vmsofminxing.R;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.FrameLayout.LayoutParams;

public class BenDiFragment extends MyFragment {
	private PullToRefreshListView pullToRefreshListView2;
	private boolean isReady = false;
	View v;
	public static List<ZiXun> zixunDataList = new ArrayList<ZiXun>();
	private ZiXunListViewAdapter zixunListViewAdapter;
	private static final String SERVICE_URL = "http://192.168.191.1:8080/RestWebServiceDemo/rest/leibie";
	private static final String SERVICE_URL2 = "http://192.168.191.1:8080/RestWebServiceDemo/rest/zixun";
	private static final String ARG_POSITION = "position";
	private static final String TAG = "ZiXunFragment";
	private int position;
	private Handler handler;
	final static String leibie = "本地";
	ImageView zx_tupian;
	FrameLayout fl;
	LayoutParams params;

	// 标志位，标志已经初始化完成。
	private void loadData() {

		postZiXunData();
		getZiXunData();
		// new Thread() {
		// public void run() {
		// try {
		// sleep(2000);
		// getZiXunData();
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		// }.start();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		ExitApplication.getInstance().addActivity(getActivity());

		if (v == null) {

			v = inflater.inflate(R.layout.fragment_bendi, container, false);
			isReady = true;
			delayLoad();
			Log.d("info", "onCreateView");
		} else {
			Log.d("info", "rootView != null");
		}

		// Cache rootView.
		// remove rootView from its parent
		ViewGroup parent = (ViewGroup) v.getParent();
		if (parent != null) {
			parent.removeView(v);
		}

		return v;
	}

	@Override
	protected void delayLoad() {
		if (!isReady || !isVisible) {
			return;
		}

		// 　This is a random widget, it will be instantiation in init()
		if (pullToRefreshListView2 == null) {
			init();
		}
	}

	public void init() {

		zixunListViewAdapter = new ZiXunListViewAdapter(getActivity()
				.getApplicationContext(), R.layout.zixun_list_item,
				zixunDataList);
		zixunListViewAdapter.notifyDataSetChanged();

		zx_tupian = (ImageView) v.findViewById(R.id.zx_tupian);
		pullToRefreshListView2 = (PullToRefreshListView) v
				.findViewById(R.id.ptlv_bendi);
		handler = new Handler() {
			@SuppressLint("HandlerLeak")
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				loadData();
			}
		};

		handler.sendEmptyMessageDelayed(0, 1000);

		pullToRefreshListView2.setAdapter(zixunListViewAdapter);
		pullToRefreshListView2
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Intent intent = new Intent(getActivity(),
								BenDiDetailActivity.class);
						intent.putExtra("zixun_id", position-1);

						startActivity(intent);

					}
				});

	}

	public void postZiXunData() {
		WebServiceTask wst = new WebServiceTask(WebServiceTask.POST_TASK,
				getActivity(), "加载中…");

		wst.addNameValuePair("leibie", leibie);

		// the passed String is the URL we will POST to
		wst.execute(new String[] { SERVICE_URL });
	}

	public void getZiXunData() {
		String sampleURL = SERVICE_URL2 + "/1";
		WebServiceTask wst = new WebServiceTask(WebServiceTask.GET_TASK,
				getActivity(), "更新中…");
		wst.execute(new String[] { sampleURL });
	}

	public static String[] convertStrToArray(String str) {
		String[] strArray = null;
		strArray = str.split(","); // 拆分字符为"," ,然后把结果交给数组strArray
		return strArray;
	}

	public void handleResponse(String response) {

		try {

			JSONObject jso = new JSONObject(response);

			String biaoti = jso.getString("biaoTi");
			String laiyuan = jso.getString("laiYuan");
			String yuedu = jso.getString("yueDu");
			String shijian = jso.getString("shiJian");
			String tupian = jso.getString("tuPian");
			String neirong = jso.optString("neiRong");
			String lianjie = jso.optString("lianJie");

			String pattern1 = "[\"]|[\\[]|[\\]]";
			String pattern2 = "[\"]|[\\[]|[\\]]";
			String[] str_biaoti = biaoti.replaceAll(pattern1, "").split(",");
			String[] str_laiyuan = laiyuan.replaceAll(pattern1, "").split(",");
			String[] str_yuedu = yuedu.replaceAll(pattern1, "").split(",");
			String[] str_shijian = shijian.replaceAll(pattern1, "").split(",");
			String[] str_tupian = tupian.replaceAll(pattern2, "").split(",");
			String[] str_neirong = neirong.replaceAll(pattern1, "")
					.replace("\\r\\n\\r\\n", "\r\n\r\n").split(",");
			String[] str_lianjie = lianjie.replaceAll(pattern2, "").split(",");

			zixunDataList.clear();
			for (int i = 0; i < str_biaoti.length; i++) {
				ZiXun zixun = new ZiXun(str_biaoti[i], str_laiyuan[i],
						str_yuedu[i], str_shijian[i], str_tupian[i].replace(
								"\\", ""), str_neirong[i],
						str_lianjie[i].replace("\\", ""));
				zixunDataList.add(zixun);

			}

		} catch (Exception e) {
			Log.e(TAG, e.getLocalizedMessage(), e);
		}

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
			Window dialogWindow = pDlg.getWindow();
			WindowManager.LayoutParams lp = dialogWindow.getAttributes();
			// dialogWindow.setGravity(Gravity.RIGHT | Gravity.TOP);
			// lp.x = 100; // 新位置X坐标
			// lp.y = 100; // 新位置Y坐标
			// lp.width = 10; // 宽度
			// lp.height = 10; // 高度
			lp.alpha = 0.7f; // 透明度
			dialogWindow.setAttributes(lp);

			pDlg.show();

		}

		@Override
		protected void onPreExecute() {

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

}
