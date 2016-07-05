package com.avilyne.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class YiBaoGuanLi {

	public String getShenFenZhengHaoMa() {
		return shenfenzhenghaoma;
	}

	public void setShenFenZhengHaoMa(String shenfenzhenghaoma) {
		this.shenfenzhenghaoma = shenfenzhenghaoma;
	}

	public String getJiaoFeiQingKuan() {
		return jiaofeiqingkuan;
	}

	public void setJiaoFeiQingKuan(String jiaofeiqingkuan) {
		this.jiaofeiqingkuan = jiaofeiqingkuan;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public YiBaoGuanLi() {

		id = -1;
		shenfenzhenghaoma = "";
		jiaofeiqingkuan = "";

	}

	public YiBaoGuanLi(long id, String shenfenzhenghaoma, String jiaofeiqingkuan) {

		this.id = id;
		this.shenfenzhenghaoma = shenfenzhenghaoma;
		this.jiaofeiqingkuan = jiaofeiqingkuan;
	}

	private long id;
	private String shenfenzhenghaoma;
	private String jiaofeiqingkuan;

}