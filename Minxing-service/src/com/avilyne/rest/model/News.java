package com.avilyne.rest.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class News {

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPubDate() {
		return pubdate;
	}

	public void setPubDate(String pubdate) {
		this.pubdate = pubdate;
	}

	public int getCommentCount() {
		return commentcount;
	}

	public void setCommentCount(int commentcount) {
		this.commentcount = commentcount;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public News() {

		id = -1;
		title = "";
		author = "";
		pubdate = null;
		commentcount = 0;
		body = "";
		link = "";
	}

	public News(long id, String title, String author, String pubdate,
			int commentcount, String body, String link) {

		this.id = id;
		this.title = title;
		this.author = author;
		this.pubdate = pubdate;
		this.commentcount = commentcount;
		this.body = body;
		this.link = link;
	}

	private long id;
	private String title;
	private String author;
	private String pubdate;
	private int commentcount;
	private String body;
	private String link;
}