package cn.minxing.fragment;

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

import cn.minxing.activity.YeWuBanLiActivity;
import cn.minxing.activity.ZiXunDetailActivity;
import cn.minxing.restwebservice.LoginService;
import cn.minxing.rsystem.SerachActivity;
import cn.minxing.rsystem.SerachDetailActivity;
import cn.minxing.rsystem.SerachListActivity;
import cn.minxing.util.BroadCastManager;
import cn.minxing.util.EventUtil;
import cn.minxing.util.RS_News;
import cn.minxing.util.RS_NewsAdapter;
import cn.minxing.util.VolleyLoadPicture;
import cn.minxing.util.ZiXun;
import cn.minxing.util.ZiXunListViewAdapter;
import cn.minxing.view.PullToRefreshListView;

import com.ypy.eventbus.EventBus;
import com.zhumingmin.vmsofminxing.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

public class ZiXunFragment extends Fragment {
	private PullToRefreshListView pullToRefreshListView;
	// private ZiXun zixun;
	public static List<ZiXun> zixunDataList = new ArrayList<ZiXun>();
	private ZiXunListViewAdapter zixunListViewAdapter;
	private static final String SERVICE_URL = "http://192.168.191.1:8080/RestWebServiceDemo/rest/leibie";
	private static final String SERVICE_URL2 = "http://192.168.191.1:8080/RestWebServiceDemo/rest/zixun";
	private static final String ARG_POSITION = "position";
	private static final String TAG = "ZiXunFragment";
	private int position;
	String leibie = null;
	// private Activity mActivity;
	ImageView zx_tupian;
	FrameLayout fl;
	LayoutParams params;
	// 标志位，标志已经初始化完成。
	private boolean isPrepared;

	public static ZiXunFragment newInstance(int position) {
		ZiXunFragment f = new ZiXunFragment();
		Bundle b = new Bundle();
		b.putInt(ARG_POSITION, position);
		f.setArguments(b);
		return f;
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			isPrepared = true;
		} else {
			// 相当于Fragment的onPause
			isPrepared = false;
		}
	}

	private void loadData() {

		postZiXunData();

		new Thread() {
			public void run() {
				try {
					sleep(5000);
					getZiXunData();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		position = getArguments().getInt(ARG_POSITION);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_zixun, container, false);
		// --------------
		// isPrepared = true;
		// lazyLoad();
		zixunListViewAdapter = new ZiXunListViewAdapter(getActivity(),
				R.layout.zixun_list_item, zixunDataList);
		zx_tupian = (ImageView) v.findViewById(R.id.zx_tupian);
		zixunListViewAdapter.notifyDataSetChanged();
		pullToRefreshListView = (PullToRefreshListView) v
				.findViewById(R.id.frame_listview_zx);
		params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);

		fl = new FrameLayout(getActivity());
		fl.setLayoutParams(params);

		switch (position) {
		case 0:
			leibie = "热点";
			// if (isPrepared) {
			// loadData();
			// }
			loadData();
			pullToRefreshListView.setAdapter(zixunListViewAdapter);
			fl.addView(v);
			break;
		case 1:
			leibie = "本地";
			loadData();
			pullToRefreshListView.setAdapter(zixunListViewAdapter);
			fl.addView(v);
			break;
		case 2:
			leibie = "农业新闻";
			loadData();
			pullToRefreshListView.setAdapter(zixunListViewAdapter);
			fl.addView(v);
			break;
		case 3:
			leibie = "农业政策";
			loadData();
			pullToRefreshListView.setAdapter(zixunListViewAdapter);
			fl.addView(v);
			break;
		case 4:
			leibie = "生产指导";
			loadData();
			pullToRefreshListView.setAdapter(zixunListViewAdapter);
			fl.addView(v);
			break;
		case 5:
			leibie = "其他";
			loadData();
			pullToRefreshListView.setAdapter(zixunListViewAdapter);
			fl.addView(v);
			break;

		default:
			break;
		}

		pullToRefreshListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Intent intent = new Intent(getActivity(),
								ZiXunDetailActivity.class);
						intent.putExtra("position", position);

						startActivity(intent);

					}
				});

		return fl;

	}

	// @Override
	// protected void lazyLoad() {
	// if (!isPrepared || !isVisible) {
	// return;
	// }
	// // 填充各控件的数据
	//
	// }

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

			String pattern = "([-*/^()\\]\\[])";
			String pattern1 = "([-*^()\\]\\[])";
			biaoti = biaoti.replaceAll(pattern, "");
			laiyuan = laiyuan.replaceAll(pattern, "");
			yuedu = yuedu.replaceAll(pattern, "");
			shijian = shijian.replaceAll(pattern, "");
			tupian = tupian.replaceAll(pattern1, "");
			neirong = neirong.replaceAll(pattern, "");
			lianjie = lianjie.replaceAll(pattern, "");
			String[] strArray = null;
			String[] strArray2 = null;
			String[] strArray3 = null;
			String[] strArray4 = null;
			String[] strArray5 = null;
			String[] strArray6 = null;
			String[] strArray7 = null;
			strArray = convertStrToArray(biaoti);
			strArray2 = convertStrToArray(laiyuan);
			strArray3 = convertStrToArray(yuedu);
			strArray4 = convertStrToArray(shijian);
			strArray5 = convertStrToArray(tupian);
			strArray6 = convertStrToArray(neirong);
			strArray7 = convertStrToArray(lianjie);
			zixunDataList.clear();

			for (int i = 0; i < strArray.length; i++) {
				ZiXun zixun = new ZiXun(strArray[i].replace("\"", ""),
						strArray2[i].replace("\"", ""), strArray3[i].replace(
								"\"", ""), strArray4[i].replace("\"", ""),
						strArray5[i].replace("\"", ""), strArray6[i].replace(
								"\"", "").replace("\\r\\n\\r\\n", "\r\n\r\n"),
						strArray7[i].replace("\"", "").replace("\\r\\n", ""));

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