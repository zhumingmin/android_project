package cn.minxing.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import com.zhumingmin.vmsofminxing.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class CatchActivity extends Activity {
	ListView listview;
	TextView textview;
	Handler handler;
	LinearLayout ly_fanhui;
	List<Map<String, Object>> data;

	// final String CSDNURL = "http://www.csdn.net/";
	final String CSDNURL = "http://j.news.163.com/#newsindexguide";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.listview);
		ly_fanhui = (LinearLayout) findViewById(R.id.xw_fanhui);
		handler = getHandler();
		ThreadStart();
	}

	/**
	 * 新开辟线程处理联网操作
	 */
	private void ThreadStart() {
		new Thread() {
			public void run() {
				Message msg = new Message();
				try {
					data = getCsdnNetDate();
					msg.what = data.size();
				} catch (Exception e) {
					e.printStackTrace();
					msg.what = -1;
				}
				handler.sendMessage(msg);
			}
		}.start();
		ly_fanhui.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				finish();

			}
		});
	}

	/**
	 * 联网获得数据
	 */
	private List<Map<String, Object>> getCsdnNetDate() {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		String csdnString = http_get(CSDNURL);

		// Pattern p = Pattern.compile("title=\"(.*?)\" href=\"(.*?)\".*?363");
		Pattern p = Pattern
				.compile("title=\"(.*?)\" docid=\"(.*?)\" href=\"(.*?)\".*?106");
		Matcher m = p.matcher(csdnString);
		while (m.find()) {
			MatchResult mr = m.toMatchResult();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", mr.group(1));
			map.put("url", mr.group(3));

			result.add(map);
		}
		return result;
	}

	/**
	 * 处理联网结果，显示在listview
	 */
	private Handler getHandler() {
		return new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what < 0) {
					Toast.makeText(CatchActivity.this, "数据获取失败",
							Toast.LENGTH_SHORT).show();
				} else {

					initListview();

				}
			}
		};
	}

	/**
	 * 在listview里显示数据
	 */
	private void initListview() {

		// listview = getListView();

		listview = (ListView) findViewById(R.id.list);

		SimpleAdapter adapter = new SimpleAdapter(this, data,
				android.R.layout.simple_list_item_1, new String[] { "title" },
				new int[] { android.R.id.text1 });
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Map<String, Object> map = data.get(arg2);
				String url = (String) (map.get("url"));
				// Intent intent = new Intent(CatchActivity.this,
				// CatchDetailActivity.class);
				// intent.putExtra("puturl", url);
				// startActivity(intent);
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse(url));
				startActivity(intent);
			}
		});
	}

	/**
	 * get请求URL，失败时尝试三次
	 * 
	 * @param url
	 *            请求网址
	 * @return 网页内容的字符串
	 */
	@SuppressWarnings("deprecation")
	private String http_get(String url) {
		final int RETRY_TIME = 3;
		HttpClient httpClient = null;
		HttpGet httpGet = null;

		String responseBody = "";
		int time = 0;
		do {
			try {
				httpClient = getHttpClient();
				httpGet = new HttpGet(url);
				HttpResponse response = httpClient.execute(httpGet);
				if (response.getStatusLine().getStatusCode() == 200) {
					// 用utf-8编码转化为字符串
					byte[] bResult = EntityUtils.toByteArray(response
							.getEntity());
					if (bResult != null) {
						responseBody = new String(bResult, "utf-8");
					}
				}
				break;
			} catch (IOException e) {
				time++;
				if (time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
					continue;
				}
				e.printStackTrace();
			} finally {
				httpClient = null;
			}
		} while (time < RETRY_TIME);

		return responseBody;
	}

	@SuppressWarnings("deprecation")
	private HttpClient getHttpClient() {
		HttpParams httpParams = new BasicHttpParams();
		// 设定连接超时和读取超时时间
		HttpConnectionParams.setConnectionTimeout(httpParams, 6000);
		HttpConnectionParams.setSoTimeout(httpParams, 30000);
		return new DefaultHttpClient(httpParams);
	}
}