package com.avilyne.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LeiBie {

	public String getLeiBie() {
		return leibie;
	}

	public void setLeiBie(String leibie) {
		this.leibie = leibie;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LeiBie() {

		id = -1;
		leibie = "";

	}

	public LeiBie(long id, String leibie) {

		this.id = id;
		this.leibie = leibie;

	}

	private long id;
	private String leibie;

}