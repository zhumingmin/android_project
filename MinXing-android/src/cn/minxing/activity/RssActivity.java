package cn.minxing.activity;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import cn.minxing.util.CustomArrayAdapter;
import cn.minxing.util.RssFeed;
import cn.minxing.util.RssFeed_SAXParser;
import cn.minxing.util.RssItem;

import com.zhumingmin.vmsofminxing.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RssActivity extends Activity implements OnItemClickListener {
	private Spinner sp_rss;
	String RSS_URL = "http://news.163.com/special/00011K6L/rss_newstop.xml";
	// http://news.163.com/special/00011K6L/rss_newstop.xml
	private CustomArrayAdapter<CharSequence> mAdapter;
	public final String tag = "RSSReader";
	private RssFeed feed = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_rss_main);

		String[] kexuanxiangmu = getResources().getStringArray(R.array.item3);
		this.mAdapter = new CustomArrayAdapter<CharSequence>(this,
				kexuanxiangmu);

		sp_rss = (Spinner) findViewById(com.zhumingmin.vmsofminxing.R.id.sp_rss);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, kexuanxiangmu);
		sp_rss.setAdapter(adapter);
		sp_rss.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub

				new Thread(new Runnable() {
					@Override
					public void run() {

						try {
							TextView tx_spinner1 = (TextView) sp_rss
									.getSelectedView();
							String rss = (String) tx_spinner1.getText();
							if (rss == "网易新闻") {

							} else if (rss == "新浪新闻") {

								RSS_URL = "http://rss.sina.com.cn/news/marquee/ddt.xml";
							} else if (rss == "腾讯新闻") {
								RSS_URL = "http://news.qq.com/newsgn/rss_newsgn.xml";
							} else if (rss == "搜狐新闻") {
								RSS_URL = "http://news.sohu.com/rss/guonei.xml";
							} else if (rss == "凤凰新闻") {
								RSS_URL = "http://news.ifeng.com/rss/index.xml";
							}

							feed = new RssFeed_SAXParser().getFeed(RSS_URL);
							System.out.println(feed.getAllItems());

						} catch (ParserConfigurationException e) {
							e.printStackTrace();
						} catch (SAXException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
						// 利用Activity.runOnUiThread(Runnable)把更新ui的代码创建在Runnable中，然后在需要更新ui时，把这个Runnable对象传给Activity.runOnUiThread(Runnable)。
						RssActivity.this.runOnUiThread(new Runnable() {

							@Override
							public void run() {

								// TODO Auto-generated method stub
								showListView();

							}

						});

					}
				}).start();
				arg0.setVisibility(View.VISIBLE);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});

	}

	private void showListView() {

		ListView itemList = (ListView) this.findViewById(R.id.itemlist);
		if (feed == null) {
			Toast.makeText(getApplicationContext(), "该RSS源无效！", 0).show();
			return;

		}
		SimpleAdapter simpleAdapter = new SimpleAdapter(this,
				feed.getAllItems(), R.layout.minxing_list_item_2, new String[] {
						RssItem.TITLE, RssItem.PUBDATE }, new int[] {
						R.id.minxingtext1, R.id.minxingtext2 });
		itemList.setAdapter(simpleAdapter);
		itemList.setOnItemClickListener(this);
		itemList.setSelection(0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view,
			int position, long id) {

		Intent intent = new Intent();
		intent.setClass(this, ShowDescriptionActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("title", feed.getItem(position).getTitle());
		bundle.putString("description", feed.getItem(position).getDescription());
		bundle.putString("link", feed.getItem(position).getLink());
		bundle.putString("pubdate", feed.getItem(position).getPubdate());

		intent.putExtra("android.intent.extra.rssItem", bundle);
		startActivityForResult(intent, 0);
	}

}
