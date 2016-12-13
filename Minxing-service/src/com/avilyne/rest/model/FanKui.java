package com.avilyne.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FanKui {

	public String getShouJiXiTong() {
		return shoujixitong;
	}

	public void setShouJiXiTong(String shoujixitong) {
		this.shoujixitong = shoujixitong;
	}

	public String getDangQianShiJian() {
		return dangqianshijian;
	}

	public void setDangQianShiJian(String dangqianshijian) {
		this.dangqianshijian = dangqianshijian;
	}

	public String getFanKuiNeiRong() {
		return fankuineirong;
	}

	public void setFanKuiNeiRong(String fankuineirong) {
		this.fankuineirong = fankuineirong;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public FanKui() {

		id = -1;
		shoujixitong = "";
		dangqianshijian = "";
		fankuineirong = "";

	}

	public FanKui(long id, String shoujixitong, String dangqianshijian,
			String fankuineirong) {

		this.id = id;
		this.shoujixitong = shoujixitong;
		this.dangqianshijian = dangqianshijian;
		this.fankuineirong = fankuineirong;

	}

	private long id;
	private String shoujixitong;
	private String dangqianshijian;
	private String fankuineirong;

}