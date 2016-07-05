package com.minxing.activity;

import java.util.ArrayList;
import java.util.List;

import com.minxing.activity.SZ_SheZhiActivity;
import com.minxing.util.JianChaGengXin;
import com.minxing.util.News;
import com.minxing.util.NewsListViewAdapter;
import com.minxing.view.PullToRefreshListView;
import com.zhumingmin.vmsofminxing.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class NewsActivity extends Activity {

	private PullToRefreshListView pullToRefreshListView;
	private News news;

	public static List<News> newsDataList = new ArrayList<News>();
	private NewsListViewAdapter newsListViewAdapter;
	LinearLayout ly_xinwen;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_news);

		ly_xinwen = (LinearLayout) findViewById(R.id.ly_xinwen);

		this.initNewsData();
		this.testLoadNewsData();
		ly_xinwen.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				NewsActivity.this.finish();
			}
		});

		newsListViewAdapter = new NewsListViewAdapter(this, newsDataList,
				R.layout.news_list_item);

		pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.frame_listview_news);

		pullToRefreshListView.setAdapter(newsListViewAdapter);

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
		for (int i = 1; i <= 20; i++) {
			news = new News(
					"广东省广州市万顷沙镇民兴村新闻" + i,
					"为民服务的村委们",
					"2016-1-" + i,
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getRepeatCount() == 0)) {

			NewsActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}

}