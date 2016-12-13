package com.avilyne.rest.model;

import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ZiXun {

	public ArrayList<String> getBiaoTi() {
		return biaoti;
	}

	public void setBiaoTi(ArrayList<String> biaoti) {
		this.biaoti = biaoti;
	}

	public ArrayList<String> getLaiYuan() {
		return laiyuan;
	}

	public void setLaiYuan(ArrayList<String> laiyuan) {
		this.laiyuan = laiyuan;
	}

	public ArrayList<String> getYueDu() {
		return yuedu;
	}

	public void setYueDu(ArrayList<String> yuedu) {
		this.yuedu = yuedu;
	}

	public ArrayList<String> getShiJian() {
		return shijian;
	}

	public void setShiJian(ArrayList<String> shijian) {
		this.shijian = shijian;
	}

	public ArrayList<String> getTuPian() {
		return tupian;
	}

	public void setTuPian(ArrayList<String> tupian) {
		this.tupian = tupian;
	}

	public ArrayList<String> getNeiRong() {
		return neirong;
	}

	public void setNeiRong(ArrayList<String> neirong) {
		this.neirong = neirong;
	}

	public ArrayList<String> getLianJie() {
		return lianjie;
	}

	public void setLianJie(ArrayList<String> lianjie) {
		this.lianjie = lianjie;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ZiXun() {

		id = -1;

		biaoti = null;
		laiyuan = null;
		yuedu = null;
		shijian = null;
		tupian = null;
		neirong = null;
		lianjie = null;
	}

	public ZiXun(long id, ArrayList<String> biaoti, ArrayList<String> laiyuan,
			ArrayList<String> yuedu, ArrayList<String> shijian,
			ArrayList<String> tupian, ArrayList<String> neirong,
			ArrayList<String> lianjie) {

		this.id = id;

		this.biaoti = biaoti;
		this.laiyuan = laiyuan;
		this.yuedu = yuedu;
		this.shijian = shijian;
		this.tupian = tupian;
		this.neirong = neirong;
		this.lianjie = lianjie;
	}

	private long id;

	private ArrayList<String> biaoti;
	private ArrayList<String> laiyuan;
	private ArrayList<String> yuedu;
	private ArrayList<String> shijian;
	private ArrayList<String> tupian;
	private ArrayList<String> neirong;
	private ArrayList<String> lianjie;
}