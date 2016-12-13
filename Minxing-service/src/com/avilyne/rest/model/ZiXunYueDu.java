package com.avilyne.rest.model;

import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ZiXunYueDu {
	public String getLeiBie() {
		return leibie;
	}

	public void setLeiBie(String leibie) {
		this.leibie = leibie;
	}

	public String getBiaoTi() {
		return biaoti;
	}

	public void setBiaoTi(String biaoti) {
		this.biaoti = biaoti;
	}

	public String getYueDu() {
		return yuedu;
	}

	public void setYueDu(String yuedu) {
		this.yuedu = yuedu;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ZiXunYueDu() {

		id = -1;
		leibie = null;
		biaoti = null;

		yuedu = null;

	}

	public ZiXunYueDu(long id, String leibie, String biaoti, String yuedu) {

		this.id = id;
		this.biaoti = biaoti;
		this.leibie = leibie;
		this.yuedu = yuedu;

	}

	private long id;
	private String leibie;
	private String biaoti;

	private String yuedu;

}