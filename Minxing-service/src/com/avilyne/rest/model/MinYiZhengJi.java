package com.avilyne.rest.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MinYiZhengJi {

	public ArrayList<String> getCanXuanRen() {
		return canxuanren;
	}

	public void setCanXuanRen(ArrayList<String> canxuanren) {
		this.canxuanren = canxuanren;
	}

	public ArrayList<String> getCanXuanZhiWu() {
		return canxuanzhiwu;
	}

	public void setCanXuanZhiWu(ArrayList<String> canxuanzhiwu) {
		this.canxuanzhiwu = canxuanzhiwu;
	}

	public ArrayList<String> getGeRenShiJi() {
		return gerenshiji;
	}

	public void setGeRenShiJi(ArrayList<String> gerenshiji) {
		this.gerenshiji = gerenshiji;
	}

	public ArrayList<String> getPiaoShu() {
		return piaoshu;
	}

	public void setPiaoShu(ArrayList<String> piaoshu) {
		this.piaoshu = piaoshu;
	}

	public String getGonggao() {
		return gonggao;
	}

	public void setGonggao(String gonggao) {
		this.gonggao = gonggao;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public MinYiZhengJi() {

		id = -1;
		canxuanren = null;
		canxuanzhiwu = null;
		gerenshiji = null;
		piaoshu = null;
		gonggao = "";
		time = "";
	}

	public MinYiZhengJi(long id, ArrayList<String> canxuanren,
			ArrayList<String> canxuanzhiwu, ArrayList<String> gerenshiji,
			ArrayList<String> piaoshu, String gonggao, String time) {

		this.id = id;
		this.canxuanren = canxuanren;
		this.canxuanzhiwu = canxuanzhiwu;
		this.gerenshiji = gerenshiji;
		this.piaoshu = piaoshu;
		this.gonggao = gonggao;
		this.time = time;
	}

	private long id;
	private ArrayList<String> canxuanren;
	private ArrayList<String> canxuanzhiwu;
	private ArrayList<String> gerenshiji;
	private ArrayList<String> piaoshu;
	private String gonggao;
	private String time;

}