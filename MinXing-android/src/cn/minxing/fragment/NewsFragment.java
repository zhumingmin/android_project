package cn.minxing.fragment;

import java.util.ArrayList;
import java.util.List;

import cn.minxing.activity.NewsDetailActivity;
import cn.minxing.activity.SerachView;
import cn.minxing.activity.TuiJianDetailActivity;
import cn.minxing.util.News;
import cn.minxing.util.NewsListViewAdapter;
import cn.minxing.view.PullToRefreshListView;

import com.zhumingmin.vmsofminxing.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

public class NewsFragment extends Fragment {
	private PullToRefreshListView pullToRefreshListView;
	private News news;
	private ImageButton fanhui1;
	public static List<News> newsDataList = new ArrayList<News>();
	private NewsListViewAdapter newsListViewAdapter;

	private static final String ARG_POSITION = "position";

	private int position;

	public static NewsFragment newInstance(int position) {
		NewsFragment f = new NewsFragment();
		Bundle b = new Bundle();
		b.putInt(ARG_POSITION, position);
		f.setArguments(b);
		return f;
	}
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		position = getArguments().getInt(ARG_POSITION);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View listview = inflater.inflate(R.layout.fragment_tuijian, container,
				false);
		// --------------

		newsListViewAdapter = new NewsListViewAdapter(getActivity(),
				newsDataList, R.layout.news_list_item);

		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);

		FrameLayout fl = new FrameLayout(getActivity());
		fl.setLayoutParams(params);

		final int margin = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 8, getResources()
						.getDisplayMetrics());

		WebView v1 = new WebView(getActivity());
		params.setMargins(margin, margin, margin, margin);
		TextView v = new TextView(getActivity());
		v1.setLayoutParams(params);
		v1.setLayoutParams(params);

		switch (position) {
		case 0:
			// v1.loadUrl("http://baike.pcbaby.com.cn/qzbd/1270013.html");
			this.initNewsData();
			this.testLoadNewsData();
			pullToRefreshListView = (PullToRefreshListView) listview
					.findViewById(R.id.frame_listview_news);
			pullToRefreshListView.setAdapter(newsListViewAdapter);

			// pullToRefreshListView
			// .setOnItemClickListener(new AdapterView.OnItemClickListener() {
			// public void onItemClick(AdapterView<?> parent,
			// View view, int position, long id) {
			// Intent intent = new Intent(getActivity(),
			// TuiJianDetailActivity.class);
			// startActivity(intent);
			// }
			// });
			fl.addView(listview);
			break;
		case 1:
			//v1.loadUrl("http://baike.pcbaby.com.cn/qzbd/1270013.html");
			
			
			this.initNewsData();
			this.testLoadNewsData();
			pullToRefreshListView = (PullToRefreshListView) listview
					.findViewById(R.id.frame_listview_news);
			pullToRefreshListView.setAdapter(newsListViewAdapter);
			
			fl.addView(listview);
			break;
		case 2:
//			v.setText("PAGE " + position);
//			v.setGravity(Gravity.CENTER);
			
			this.initNewsData();
			this.testLoadNewsData();
			pullToRefreshListView = (PullToRefreshListView) listview
					.findViewById(R.id.frame_listview_news);
			pullToRefreshListView.setAdapter(newsListViewAdapter);
			
			
			fl.addView(listview);
			break;
		case 3:
//			v.setText("PAGE " + position);
//			v.setGravity(Gravity.CENTER);
			
			this.initNewsData();
			this.testLoadNewsData();
			pullToRefreshListView = (PullToRefreshListView) listview
					.findViewById(R.id.frame_listview_news);
			pullToRefreshListView.setAdapter(newsListViewAdapter);
			
			fl.addView(listview);
			break;
		case 4:
//			v.setText("PAGE " + position);
//			v.setGravity(Gravity.CENTER);
			
			this.initNewsData();
			this.testLoadNewsData();
			pullToRefreshListView = (PullToRefreshListView) listview
					.findViewById(R.id.frame_listview_news);
			pullToRefreshListView.setAdapter(newsListViewAdapter);
			
			fl.addView(listview);
			break;
		case 5:
//			v.setText("PAGE " + position);
//			v.setGravity(Gravity.CENTER);
			
			this.initNewsData();
			this.testLoadNewsData();
			pullToRefreshListView = (PullToRefreshListView) listview
					.findViewById(R.id.frame_listview_news);
			pullToRefreshListView.setAdapter(newsListViewAdapter);
			
			fl.addView(listview);
			break;
		case 6:
//			v.setText("PAGE " + position);
//			v.setGravity(Gravity.CENTER);
			
			this.initNewsData();
			this.testLoadNewsData();
			pullToRefreshListView = (PullToRefreshListView) listview
					.findViewById(R.id.frame_listview_news);
			pullToRefreshListView.setAdapter(newsListViewAdapter);
			
			fl.addView(listview);
			break;
		default:
			break;
		}

		return fl;
	}

	private void initNewsData() {
		for (int i = 1; i <= 10; i++) {
			news = new News("广东省广州市万顷沙镇民兴村热点新闻" + i, "为民服务的村委们", "2016-1-" + i,
					12 + i, "新闻内容" + i);
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

}