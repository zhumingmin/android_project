package com.minxing.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RssFeed {

	private String title;
	private String pubdate;

	private int itemCount;
	private List<RssItem> rssItems;

	public RssFeed() {
		rssItems = new ArrayList<RssItem>();
	}

	public int addItem(RssItem rssItem) {
		rssItems.add(rssItem);
		itemCount++;
		return itemCount;
	}

	public RssItem getItem(int position) {
		return rssItems.get(position);
	}

	public List<HashMap<String, Object>> getAllItems() {
		List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < rssItems.size(); i++) {
			HashMap<String, Object> item = new HashMap<String, Object>();
			item.put(RssItem.TITLE, rssItems.get(i).getTitle());
			item.put(RssItem.PUBDATE, rssItems.get(i).getPubdate());
			data.add(item);
		}
		return data;

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPubdate() {
		return pubdate;
	}

	public void setPubdate(String pubdate) {
		this.pubdate = pubdate;
	}

	public int getItemCount() {
		return itemCount;
	}

	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}

}