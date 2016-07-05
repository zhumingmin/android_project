package com.avilyne.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class HeTongGuanLi {

	public String getJiaFang() {
		return jiafang;
	}

	public void setJiaFang(String jiafang) {
		this.jiafang = jiafang;
	}

	public String getYiFang() {
		return yifang;
	}

	public void setYiFang(String yifang) {
		this.yifang = yifang;
	}

	public String getYueDingFeiYong() {
		return yuedingfeiyong;
	}

	public void setYueDingFeiYong(String yuedingfeiyong) {
		this.yuedingfeiyong = yuedingfeiyong;
	}

	public String getQianDingNianXian() {
		return qiandingnianxian;
	}

	public void setQianDingNianXian(String qiandingnianxian) {
		this.qiandingnianxian = qiandingnianxian;
	}

	public String getQiTaShuoMing() {
		return qitashuoming;
	}

	public void setQiTaShuoMing(String qitashuoming) {
		this.qitashuoming = qitashuoming;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public HeTongGuanLi() {

		id = -1;
		jiafang = "";
		yifang = "";
		yuedingfeiyong = "";
		qiandingnianxian = "";
		qitashuoming = "";
	}

	public HeTongGuanLi(long id, String jiafang, String yifang,
			String yuedingfeiyong, String qiandingnianxian, String qitashuoming) {

		this.id = id;
		this.jiafang = jiafang;
		this.yifang = yifang;
		this.yuedingfeiyong = yuedingfeiyong;
		this.qiandingnianxian = qiandingnianxian;
		this.qitashuoming = qitashuoming;
	}

	private long id;
	private String jiafang;
	private String yifang;
	private String yuedingfeiyong;
	private String qiandingnianxian;
	private String qitashuoming;

}