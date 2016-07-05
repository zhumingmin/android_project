package com.avilyne.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Person {

	public String getZhangHao() {
		return zhanghao;
	}

	public void setZhangHao(String zhanghao) {
		this.zhanghao = zhanghao;
	}

	public String getXingMing() {
		return xingming;
	}

	public void setXingMing(String xingming) {
		this.xingming = xingming;
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

	public String getMiMa2() {
		return mima2;
	}

	public void setMiMa2(String mima2) {
		this.mima2 = mima2;
	}

	// public boolean getResult() {
	// return result;
	// }
	//
	// public void setResult(boolean result) {
	// this.result = result;
	// }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Person() {

		id = -1;
		zhanghao = "";
		xingming = "";
		shoujihaoma = "";
		mima1 = "";
		mima2 = "";
		// result = false;

	}

	public Person(long id, String zhanghao, String xingming,
			String shoujihaoma, String mima1, String mima2) {

		this.id = id;
		this.zhanghao = zhanghao;
		this.xingming = xingming;
		this.shoujihaoma = shoujihaoma;
		this.mima1 = mima1;
		this.mima2 = mima2;
		// this.result = result;
	}

	private long id;
	private String zhanghao;
	private String xingming;
	private String shoujihaoma;
	private String mima1;
	private String mima2;
	// private boolean result;

}