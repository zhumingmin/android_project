package cn.minxing.util;

public class ZiXun {

	public String getBiaoTi() {
		return biaoti;
	}

	public void setBiaoTi(String biaoti) {
		this.biaoti = biaoti;
	}

	public String getLaiYuan() {
		return laiyuan;
	}

	public void setLaiYuan(String laiyuan) {
		this.laiyuan = laiyuan;
	}

	public String getYueDu() {
		return yuedu;
	}

	public void setYueDu(String yuedu) {
		this.yuedu = yuedu;
	}

	public String getShiJian() {
		return shijian;
	}

	public void setShiJian(String shijian) {
		this.shijian = shijian;
	}

	public String getTuPian() {
		return tupian;
	}

	public void setTuPian(String tupian) {
		this.tupian = tupian;
	}

	public String getNeiRong() {
		return neirong;
	}

	public void setNeiRong(String neirong) {
		this.neirong = neirong;
	}

	public String getLianJie() {
		return lianjie;
	}

	public void setLianJie(String lianjie) {
		this.lianjie = lianjie;
	}

	public ZiXun() {

		biaoti = null;
		laiyuan = null;
		yuedu = null;
		shijian = null;
		tupian = null;
		neirong = null;
		lianjie = null;
	}

	public ZiXun(String biaoti, String laiyuan, String yuedu, String shijian,
			String tupian, String neirong, String lianjie) {

		this.biaoti = biaoti;
		this.laiyuan = laiyuan;
		this.yuedu = yuedu;
		this.shijian = shijian;
		this.tupian = tupian;
		this.neirong = neirong;
		this.lianjie = lianjie;
	}

	private String biaoti;
	private String laiyuan;
	private String yuedu;
	private String shijian;
	private String tupian;
	private String neirong;
	private String lianjie;
}