package com.avilyne.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Login {

	public String getZhangHao() {
		return zhanghao;
	}

	public void setZhangHao(String zhanghao) {
		this.zhanghao = zhanghao;
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

	public Login() {

		id = -1;
		zhanghao = "";
		mima1 = "";

	}

	public Login(long id, String zhanghao, String mima1) {

		this.id = id;
		this.zhanghao = zhanghao;
		this.mima1 = mima1;

	}

	private long id;
	private String zhanghao;
	private String mima1;

}