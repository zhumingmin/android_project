package com.avilyne.rest.model;

import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class News {

	public ArrayList<String> getTitle() {
		return title;
	}

	public void setTitle(ArrayList<String> title) {
		this.title = title;
	}

	public ArrayList<String> getCategory() {
		return category;
	}

	public void setCategory(ArrayList<String> category) {
		this.category = category;
	}

	public ArrayList<String> getPubDate() {
		return pubdate;
	}

	public void setPubDate(ArrayList<String> pubdate) {
		this.pubdate = pubdate;
	}

	public ArrayList<String> getRead() {
		return read;
	}

	public void setRead(ArrayList<String> read) {
		this.read = read;
	}

	public ArrayList<String> getLike() {
		return like;
	}

	public void setLike(ArrayList<String> like) {
		this.like = like;
	}

	public ArrayList<String> getUnlike() {
		return unlike;
	}

	public void setUnlike(ArrayList<String> unlike) {
		this.unlike = unlike;
	}

	public ArrayList<String> getBody() {
		return body;
	}

	public void setBody(ArrayList<String> body) {
		this.body = body;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public ArrayList<String> getPicturepath() {
		return picturepath;
	}

	public void setPicturepath(ArrayList<String> picturepath) {
		this.picturepath = picturepath;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public News() {

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

	public News(long id, ArrayList<String> title, ArrayList<String> category,
			ArrayList<String> pubdate, ArrayList<String> read,
			ArrayList<String> like, ArrayList<String> unlike,
			ArrayList<String> body, String link, ArrayList<String> picturepath) {

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
	private ArrayList<String> title;
	private ArrayList<String> category;
	private ArrayList<String> pubdate;
	private ArrayList<String> read;
	private ArrayList<String> like;
	private ArrayList<String> unlike;
	private ArrayList<String> body;
	private String link;
	private ArrayList<String> picturepath;
}