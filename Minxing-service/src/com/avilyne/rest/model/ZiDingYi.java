package com.avilyne.rest.model;

import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ZiDingYi {

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPubDate() {
		return pubdate;
	}

	public void setPubDate(String pubdate) {
		this.pubdate = pubdate;
	}

	public String getRead() {
		return read;
	}

	public void setRead(String read) {
		this.read = read;
	}

	public String getLike() {
		return like;
	}

	public void setLike(String like) {
		this.like = like;
	}

	public String getUnlike() {
		return unlike;
	}

	public void setUnlike(String unlike) {
		this.unlike = unlike;
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

	public String getPicturepath() {
		return picturepath;
	}

	public void setPicturepath(String picturepath) {
		this.picturepath = picturepath;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ZiDingYi() {

		id = -1;
		title = null;
		category = null;
		pubdate = null;
		read = null;
		like = null;
		unlike = null;
		body = null;
		link = "";
		picturepath = null;
	}

	public ZiDingYi(long id, String title, String category, String pubdate,
			String read, String like, String unlike, String body, String link,
			String picturepath) {

		this.id = id;
		this.title = title;
		this.category = category;
		this.pubdate = pubdate;
		this.read = read;
		this.like = like;
		this.unlike = unlike;
		this.body = body;
		this.link = link;
		this.picturepath = picturepath;
	}

	private long id;
	private String title;
	private String category;
	private String pubdate;
	private String read;
	private String like;
	private String unlike;
	private String body;
	private String link;
	private String picturepath;
}