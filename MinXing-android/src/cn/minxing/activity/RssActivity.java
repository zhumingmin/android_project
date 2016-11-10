package cn.minxing.activity;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

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
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class RssActivity extends Activity implements OnItemClickListener {

	public final String RSS_URL = "http://news.ifeng.com/rss/index.xml";

	public final String tag = "RSSReader";
	private RssFeed feed = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_rss_main);
		new Thread(new Runnable() {
			@Override
			public void run() {

				try {

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

	}

	private void showListView() {

		ListView itemList = (ListView) this.findViewById(R.id.itemlist);
		if (feed == null) {
			Toast.makeText(getApplicationContext(), "该RSS源无效！", 0).show();
			return;

		}
		SimpleAdapter simpleAdapter = new SimpleAdapter(this,
				feed.getAllItems(), android.R.layout.simple_list_item_2,
				new String[] { RssItem.TITLE, RssItem.PUBDATE }, new int[] {
						android.R.id.text1, android.R.id.text2 });
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
