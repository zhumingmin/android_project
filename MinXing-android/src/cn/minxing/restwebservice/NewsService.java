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

import cn.minxing.activity.NewsDetailActivity;
import cn.minxing.activity.SZ_SheZhiActivity;
import cn.minxing.util.JianChaGengXin;
import cn.minxing.util.News;
import cn.minxing.util.NewsListViewAdapter;
import cn.minxing.view.PullToRefreshListView;

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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class NewsService extends Activity {

	private PullToRefreshListView pullToRefreshListView;
	private News news;
	private static final String TAG = "NewsService";
	private static final String SERVICE_URL = "http://192.168.191.1:8080/RestWebServiceDemo/rest/news";
	public static List<News> newsDataList = new ArrayList<News>();
	private NewsListViewAdapter newsListViewAdapter;
	LinearLayout ly_xinwen;
	private TextView title, author, date, commentcount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_news);

		ly_xinwen = (LinearLayout) findViewById(R.id.ly_xinwen);

		ly_xinwen.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				NewsService.this.finish();
			}
		});

		newsListViewAdapter = new NewsListViewAdapter(this, newsDataList,
				R.layout.news_list_item);
		title = (TextView) findViewById(R.id.news_listitem_title);
		author = (TextView) findViewById(R.id.news_listitem_author);
		date = (TextView) findViewById(R.id.news_listitem_date);
		commentcount = (TextView) findViewById(R.id.news_listitem_commentCount);
		pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.frame_listview_news);

		pullToRefreshListView.setAdapter(newsListViewAdapter);
		this.initNewsData();
		this.testLoadNewsData();
		pullToRefreshListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Intent intent = new Intent(view.getContext(),
								NewsDetailActivity.class);
						intent.putExtra("news_id", position);
						view.getContext().startActivity(intent);
					}
				});
	}

	private void initNewsData() {
		for (int i = 1; i <= 10; i++) {
			news = new News(
					title.toString() + i,
					author.toString(),
					date.toString() + i,
					12 + i,
					"新闻内容新闻内容新闻内容新闻内容新闻内容新闻内容新闻内容新闻内容新闻内容新闻内容新闻内容新闻内容新闻内容新闻内容新闻内容新闻内容新闻内容新闻内容新闻内容新闻内容新闻内容新闻内容新闻内容新闻内容新闻内容新闻内容新闻内容"
							+ i);
			newsDataList.add(news);
		}
	}

	private void testLoadNewsData() {
		Log.w("当前newsDataList中新闻数量为", String.valueOf(newsDataList.size()));
		for (int i = 1; i <= newsDataList.size(); i++) {
			News news = newsDataList.get(i - 1);
			Log.i("第" + i + "条新闻标题", news.getTitle());
			Log.i("第" + i + "条新闻作者", news.getAuthor());
			Log.i("第" + i + "条新闻发表日期", news.getPubDate());
			Log.i("第" + i + "条新闻评论数", String.valueOf(news.getCommentCount()));
			Log.i("第" + i + "条新闻内容", news.getBody());
		}
	}

	// 从服务器端获取信息 GET方法
	public void retrieveSampleData(View vw) {

		String sampleURL = SERVICE_URL + "/1";
		WebServiceTask wst = new WebServiceTask(WebServiceTask.GET_TASK, this,
				"查询中…");
		wst.execute(new String[] { sampleURL });

	}

	public void handleResponse(String response) {

		title.setText("");
		author.setText("");
		date.setText("");
		commentcount.setText("");

		try {

			JSONObject jso = new JSONObject(response);

			String news_title = jso.getString("title");
			String news_author = jso.optString("author");
			String news_date = jso.optString("date");
			String news_commentcount = jso.optString("commentCount");

			title.setText(news_title);
			author.setText(news_author);
			date.setText(news_date);
			commentcount.setText(news_commentcount);

		} catch (Exception e) {
			Log.e(TAG, e.getLocalizedMessage(), e);
		}

	}

	// 隐藏键盘
	private void hideKeyboard() {

		InputMethodManager inputManager = (InputMethodManager) NewsService.this
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		inputManager.hideSoftInputFromWindow(NewsService.this.getCurrentFocus()
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

			NewsService.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}

}
