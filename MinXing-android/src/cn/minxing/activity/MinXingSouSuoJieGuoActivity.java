package cn.minxing.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
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
import android.widget.AdapterView.OnItemClickListener;
import cn.minxing.util.News;
import cn.minxing.util.NewsListViewAdapter;
import cn.minxing.view.PullToRefreshListView;

import com.zhumingmin.vmsofminxing.R;

public class MinXingSouSuoJieGuoActivity extends Activity {
	private PullToRefreshListView pullToRefreshListView;
	private News news;
	Button dubiaoqian, xiebiaoqian, but2, soufanshiliu, soufenjiao, soumugua,
			souqita, zhijiesousuo, jiafen, koufen;
	public static List<News> newsDataList = new ArrayList<News>();
	private NewsListViewAdapter newsListViewAdapter;
	private LinearLayout ly_fanhui;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_minxingsousuojieguo);
		this.initNewsData();
		this.testLoadNewsData();
		ly_fanhui = (LinearLayout) findViewById(R.id.ly_fanhui_jieguo);
		newsListViewAdapter = new NewsListViewAdapter(
				MinXingSouSuoJieGuoActivity.this, newsDataList,
				R.layout.news_list_item);
		jiafen = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.jiafen);
		koufen = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.koufen);
		pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.frame_listview_news);
		pullToRefreshListView.setAdapter(newsListViewAdapter);
		ly_fanhui.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		/*
		 * 点击了这个按钮需要提升这条结果被搜索到的几率
		 */
		jiafen.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DisplayToast("感谢您的反馈！");
			}
		});

		/*
		 * 对结果不满意时需要提供原因选项，之后加上
		 */
		koufen.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DisplayToast("感谢您的建议！");
			}
		});
	}

	private void DisplayToast(String string) {
		// TODO Auto-generated method stub
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
	}

	// 为ListView列添加单击事件
	class ListViewItemOnClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			DisplayToast("稍后开通！");
			System.out.println("----->OnClick");
			// 这里可以自由发挥，比如播放一首歌曲等等
		}
	}

	private void initNewsData() {
		for (int i = 1; i <= 20; i++) {

			news = new News("为您提供的相关搜索结果" + i, "华农的学生", "2016-1-" + i, 12 + i,
					"新闻内容" + i);

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

			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

}
