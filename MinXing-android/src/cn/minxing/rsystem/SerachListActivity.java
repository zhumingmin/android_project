package cn.minxing.rsystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

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
import org.json.JSONArray;
import org.json.JSONObject;

import cn.minxing.activity.TianJiaHuiDaActivity;
import cn.minxing.util.RS_News;
import cn.minxing.util.RS_NewsAdapter;

import com.zhumingmin.vmsofminxing.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/*
 *这里需要完成的操作有：
 *1.获取某个关键词的相关标题
 *2.获取每个标题的阅读量，推荐数，不推荐
 */
public class SerachListActivity extends Activity {
	ListView listview;
	public static List<RS_News> newslist = new ArrayList<RS_News>();
	private LinearLayout ly_fanhui;
	private static final String SERVICE_URL = "http://192.168.191.1:8080/RestWebServiceDemo/rest/news";
	private Button load;
	private TextView tv_result, tv_ugc;
	private static final String TAG = "SerachListActivity";
	static String classname;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_listview_rs);
		ly_fanhui = (LinearLayout) findViewById(R.id.ly_liebiao);
		tv_result = (TextView) findViewById(R.id.tv_result);
		tv_ugc = (TextView) findViewById(R.id.tv_ugc);
		Intent intent = getIntent();
		if (intent != null) {
			classname = intent.getStringExtra("classname");

			String sampleURL = SERVICE_URL + "/1";

			WebServiceTask wst = new WebServiceTask(WebServiceTask.GET_TASK,
					SerachListActivity.this, "正在加载，请稍候...");
			wst.execute(new String[] { sampleURL });

		}

		load = (Button) findViewById(R.id.load);
		load.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				SerachListActivity.this.finish();
				Intent intent = new Intent(SerachListActivity.this,
						SerachListActivity.class);
				startActivity(intent);

			}

		});
		ly_fanhui.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SerachListActivity.this,
						SerachActivity.class);
				startActivity(intent);
				finish();
			}
		});
		tv_ugc.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				SerachListActivity.this.finish();
				Intent intent = new Intent(SerachListActivity.this,
						TianJiaHuiDaActivity.class);
				startActivity(intent);

			}

		});
		RS_NewsAdapter adapter = new RS_NewsAdapter(SerachListActivity.this,
				R.layout.news_list_item_rs, newslist);
		adapter.notifyDataSetChanged();
		ListView listview = (ListView) findViewById(R.id.list);
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(view.getContext(),
						SerachDetailActivity.class);
				intent.putExtra("news_id", position);
				view.getContext().startActivity(intent);

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

			String biaoti = jso.getString("title");
			String leibie = jso.getString("category");
			String yueduliang = jso.getString("read");
			String like = jso.getString("like");
			String unlike = jso.getString("unlike");
			String tupian = jso.optString("picturepath");
			String neirong = jso.optString("body");

			String pattern = "([-*/^()\\]\\[])";

			biaoti = biaoti.replaceAll(pattern, "");
			neirong = neirong.replaceAll(pattern, "");
			tupian = tupian.replaceAll(pattern, "");
			leibie = leibie.replaceAll(pattern, "");
			yueduliang = yueduliang.replaceAll(pattern, "");
			like = like.replaceAll(pattern, "");
			unlike = unlike.replaceAll(pattern, "");
			String[] strArray = null;
			String[] strArray2 = null;
			String[] strArray3 = null;
			String[] strArray4 = null;
			String[] strArray5 = null;
			String[] strArray6 = null;
			String[] strArray7 = null;
			strArray = convertStrToArray(biaoti);
			strArray2 = convertStrToArray(neirong);
			strArray3 = convertStrToArray(tupian);
			strArray4 = convertStrToArray(leibie);
			strArray5 = convertStrToArray(yueduliang);
			strArray6 = convertStrToArray(like);
			strArray7 = convertStrToArray(unlike);
			newslist.clear();
			tv_result.setText("");
			for (int i = 0; i < strArray.length; i++) {
				RS_News news = new RS_News(strArray[i].replace("\"", ""),
						strArray4[i].replace("\"", ""), strArray5[i].replace(
								"\"", ""), strArray6[i].replace("\"", ""),
						strArray7[i].replace("\"", ""), strArray2[i].replace(
								"\"", "").replace("\\r\\n\\r\\n", "\r\n\r\n"),
						strArray3[i].replace("\"", "").replace("\\r\\n", ""));
				newslist.add(news);

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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getRepeatCount() == 0)) {
			Intent intent = new Intent();
			intent.setClass(SerachListActivity.this, SerachActivity.class);
			startActivity(intent);
			SerachListActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}

}
