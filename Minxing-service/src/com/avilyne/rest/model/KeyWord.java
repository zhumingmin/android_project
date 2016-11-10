package com.avilyne.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class KeyWord {

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public KeyWord() {

		id = -1;
		keyWord = "";

	}

	public KeyWord(long id, String keyWord) {

		this.id = id;
		this.keyWord = keyWord;

	}

	private long id;
	private String keyWord;

}