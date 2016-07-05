package com.minxing.util;

public class RssItem {

	private String title;
	private String link;
	private String author;
	private String category;
	private String pubdate;
	private String comments;
	private String description;


	public static final String TITLE = "title";
	public static final String PUBDATE = "pubdate";

	public RssItem() {

	}

	public String getTitle() {
		if (title.length() > 20) {
			return title.substring(0, 19) + "...";
		}
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPubdate() {
		return pubdate;
	}

	public void setPubdate(String pubdate) {
		this.pubdate = pubdate;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	@Override
	public String toString() {
		return "RssItem [title=" + title + ", description=" + description
				+ ", link=" + link + ", category=" + category + ", pubdate="
				+ pubdate + "]";
	}

}