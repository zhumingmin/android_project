package com.minxing.util;

import java.util.List;

import com.zhumingmin.vmsofminxing.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsListViewAdapter extends BaseAdapter {

	private Context context;

	private LayoutInflater listContainer;

	private List<News> newsDataList;

	private int itemViewResource;

	static class ListItemView {
		public TextView title;
		public TextView author;
		public TextView date;
		public TextView count;
		public ImageView flag;
	}

	public NewsListViewAdapter(Context context, List<News> newsDataList,
			int itemViewResource) {
		this.context = context;
		this.listContainer = LayoutInflater.from(context);
		this.itemViewResource = itemViewResource;
		this.newsDataList = newsDataList;
	}

	public int getCount() {
		return newsDataList.size();
	}

	public Object getItem(int arg0) {
		return null;
	}

	public long getItemId(int arg0) {
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		ListItemView listItemView = null;

		if (convertView == null) {

			convertView = listContainer.inflate(this.itemViewResource, null);

			listItemView = new ListItemView();
			listItemView.title = (TextView) convertView
					.findViewById(R.id.news_listitem_title);
			listItemView.author = (TextView) convertView
					.findViewById(R.id.news_listitem_author);
			listItemView.count = (TextView) convertView
					.findViewById(R.id.news_listitem_commentCount);
			listItemView.date = (TextView) convertView
					.findViewById(R.id.news_listitem_date);
			listItemView.flag = (ImageView) convertView
					.findViewById(R.id.news_listitem_flag);

			convertView.setTag(listItemView);

		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		News news = newsDataList.get(position);
		listItemView.title.setText(news.getTitle());
		listItemView.author.setText(news.getAuthor());
		listItemView.date.setText(news.getPubDate());
		listItemView.count.setText(news.getCommentCount() + "");
		listItemView.flag.setVisibility(View.VISIBLE);
		return convertView;
	}
}