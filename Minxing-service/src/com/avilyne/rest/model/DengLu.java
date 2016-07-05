package com.avilyne.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DengLu {

	public String getZhangHao() {
		return zhanghao;
	}

	public void setFirstName(String zhanghao) {
		this.zhanghao = zhanghao;
	}

	public String getMiMa() {
		return mima;
	}

	public void setMiMa(String mima) {
		this.mima = mima;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public DengLu() {

		id = -1;
		zhanghao = "";
		mima = "";
		result = "";
		

	}

	public DengLu(long id, String zhanghao, String mima,
			String result) {

		this.id = id;
		this.zhanghao = zhanghao;
		this.mima = mima;
		this.result = result;
		
	}

	private long id;
	private String zhanghao;
	private String mima;
	private String result;
	

}