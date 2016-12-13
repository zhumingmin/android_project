package com.avilyne.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GongGao {

	public String getGonggao() {
		return gonggao;
	}

	public void setGonggao(String gonggao) {
		this.gonggao = gonggao;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public GongGao() {

		id = -1;
		gonggao = "";
		time = "";

	}

	public GongGao(long id, String gonggao, String time) {

		this.id = id;
		this.gonggao = gonggao;
		this.time = time;

	}

	private long id;
	private String gonggao;
	private String time;

}