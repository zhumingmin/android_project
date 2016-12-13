package com.avilyne.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PiaoShuNumber {
	public String getPiaoShu() {
		return piaoshu;
	}

	public void setPiaoShu(String piaoshu) {
		this.piaoshu = piaoshu;
	}

	public String getCanXuanRen() {
		return canxuanren;
	}

	public void setCanXuanRen(String canxuanren) {
		this.canxuanren = canxuanren;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public PiaoShuNumber() {

		id = -1;
		piaoshu = null;

	}

	public PiaoShuNumber(long id, String piaoshu, String canxuanren) {

		this.id = id;
		this.piaoshu = piaoshu;
		this.canxuanren = canxuanren;
	}

	private long id;
	private String piaoshu;
	private String canxuanren;

}