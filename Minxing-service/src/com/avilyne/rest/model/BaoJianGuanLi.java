package com.avilyne.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BaoJianGuanLi {

	public String getBaoJianXiangMu() {
		return baojianxiangmu;
	}

	public void setBaoJianXiangMu(String baojianxiangmu) {
		this.baojianxiangmu = baojianxiangmu;
	}

	public String getBaoJianDiZhi() {
		return baojiandizhi;
	}

	public void setBaoJianDiZhi(String baojiandizhi) {
		this.baojiandizhi = baojiandizhi;
	}

	public String getLianXiFangShi() {
		return lianxifangshi;
	}

	public void setLianXiFangShi(String lianxifangshi) {
		this.lianxifangshi = lianxifangshi;
	}

	public String getYuYueShiJian() {
		return yuyueshijian;
	}

	public void setYuYueShiJian(String yuyueshijian) {
		this.yuyueshijian = yuyueshijian;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BaoJianGuanLi() {

		id = -1;
		baojianxiangmu = "";
		baojiandizhi = "";
		lianxifangshi = "";
		yuyueshijian = "";

	}

	public BaoJianGuanLi(long id, String baojianxiangmu, String baojiandizhi,
			String lianxifangshi, String yuyueshijian) {

		this.id = id;
		this.baojianxiangmu = baojianxiangmu;
		this.baojiandizhi = baojiandizhi;
		this.lianxifangshi = lianxifangshi;
		this.yuyueshijian = yuyueshijian;

	}

	private long id;
	private String baojianxiangmu;
	private String baojiandizhi;
	private String lianxifangshi;
	private String yuyueshijian;

}