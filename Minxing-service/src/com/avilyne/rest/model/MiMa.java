package com.avilyne.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MiMa {

	public String getZhangHao() {
		return zhanghao;
	}

	public void setZhangHao(String zhanghao) {
		this.zhanghao = zhanghao;
	}

	public String getShouJiHao() {
		return shoujihaoma;
	}

	public void setShouJiHao(String shoujihaoma) {
		this.shoujihaoma = shoujihaoma;
	}

	public String getMiMa1() {
		return mima1;
	}

	public void setMiMa1(String mima1) {
		this.mima1 = mima1;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public MiMa() {

		id = -1;
		zhanghao = "";
		shoujihaoma = "";
		mima1 = "";

	}

	public MiMa(long id, String zhanghao, String shoujihaoma, String mima1) {

		this.id = id;
		this.zhanghao = zhanghao;

		this.shoujihaoma = shoujihaoma;
		this.mima1 = mima1;

		// this.result = result;
	}

	private long id;
	private String zhanghao;

	private String shoujihaoma;
	private String mima1;

	// private boolean result;

}