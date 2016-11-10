package com.avilyne.rest.model;

import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NewsNumber {
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public NewsNumber() {

		id = -1;
		title = null;
		read = null;
		like = null;
		unlike = null;

	}

	public NewsNumber(long id, String title, String read, String like,
			String unlike) {

		this.id = id;
		this.title = title;
		this.read = read;
		this.like = like;
		this.unlike = unlike;

	}

	private long id;
	private String title;
	private String read;
	private String like;
	private String unlike;

}