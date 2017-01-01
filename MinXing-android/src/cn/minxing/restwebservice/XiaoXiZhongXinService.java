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

import cn.minxing.activity.XiaoXiDetailActivity;
import cn.minxing.rsystem.SerachDetailActivity;
import cn.minxing.rsystem.SerachListActivity;
import cn.minxing.util.RS_News;
import cn.minxing.util.RS_NewsAdapter;
import cn.minxing.util.XiaoXi;
import cn.minxing.util.XiaoXiAdapter;

import com.zhumingmin.vmsofminxing.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class XiaoXiZhongXinService extends Activity {
	LinearLayout ll_xiaoxi;
	public static List<XiaoXi> xiaoxilist = new ArrayList<XiaoXi>();
	private ListView lv_xiaoxi;
	private static final String SERVICE_URL = "http://192.168.191.1:8080/RestWebServiceDemo/rest/xiaoxi";
	private static final String TAG = "XiaoXiZhongXinService";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.zhumingmin.vmsofminxing.R.layout.webservice_xiaoxizhongxin);
		ll_xiaoxi = (LinearLayout) findViewById(R.id.ly_xiaoxi);
		lv_xiaoxi = (ListView) findViewById(R.id.lv_xiaoxi);
		Intent intent = getIntent();
		if (intent != null) {

			String sampleURL = SERVICE_URL + "/1";
			WebServiceTask wst = new WebServiceTask(WebServiceTask.GET_TASK,
					XiaoXiZhongXinService.this, "正在加载，请稍候…");
			wst.execute(new String[] { sampleURL });
		}

		XiaoXiAdapter adapter = new XiaoXiAdapter(XiaoXiZhongXinService.this,
				R.layout.xiaoxi_list_item, xiaoxilist);
		adapter.notifyDataSetChanged();
		lv_xiaoxi.setAdapter(adapter);
		lv_xiaoxi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(XiaoXiZhongXinService.this,
						XiaoXiDetailActivity.class);
				intent.putExtra("xiaoxi_id", position);
				startActivity(intent);

			}
		});
		ll_xiaoxi.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				finish();

			}
		});

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
			String shijian = jso.getString("shiJian");
			String zhaiyao = jso.getString("zhaiYao");
			String neirong = jso.getString("neiRong");

			String pattern = "([-*/^()\\]\\[])";

			biaoti = biaoti.replaceAll(pattern, "");

			shijian = shijian.replaceAll(pattern, "");
			zhaiyao = zhaiyao.replaceAll(pattern, "");
			neirong = neirong.replaceAll(pattern, "");

			String[] strArray = null;
			String[] strArray2 = null;
			String[] strArray3 = null;
			String[] strArray4 = null;

			strArray = convertStrToArray(biaoti);
			strArray2 = convertStrToArray(shijian);
			strArray3 = convertStrToArray(zhaiyao);
			strArray4 = convertStrToArray(neirong);

			xiaoxilist.clear();

			for (int i = 0; i < strArray.length; i++) {
				XiaoXi xiaoxi = new XiaoXi(strArray[i].replace("\"", ""),
						strArray2[i].replace("\"", ""), strArray3[i].replace(
								"\"", ""), strArray4[i].replace("\"", ""));
				xiaoxilist.add(xiaoxi);

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
