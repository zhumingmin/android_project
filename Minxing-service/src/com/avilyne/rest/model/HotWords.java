package com.avilyne.rest.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class HotWords {

	public ArrayList<String> getKeyWord() {
		return hotrWords;
	}

	public void setKeyWord(ArrayList<String> hotrWords) {
		this.hotrWords = hotrWords;
	}

	public HotWords() {

		hotrWords = null;

	}

	public HotWords(ArrayList<String> hotrWords) {

		this.hotrWords = hotrWords;

	}

	private ArrayList<String> hotrWords;

}