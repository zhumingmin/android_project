package com.avilyne.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class HuJiQianChu {

	public String getQianChuRenXingMing() {
		return qianchurenxingming;
	}

	public void setQianChuRenXingMing(String qianchurenxingming) {
		this.qianchurenxingming = qianchurenxingming;
	}

	public String getShenFenZhengHaoMa() {
		return shenfenzhenghaoma;
	}

	public void setShenFenZhengHaoMa(String shenfenzhenghaoma) {
		this.shenfenzhenghaoma = shenfenzhenghaoma;
	}

	public String getQianChuLiYou() {
		return qianchuliyou;
	}

	public void setQianChuLiYou(String qianchuliyou) {
		this.qianchuliyou = qianchuliyou;
	}

	public String getUploadPicture() {
		return uploadpicture;
	}

	public void setUploadPicture(String uploadpicture) {
		this.uploadpicture = uploadpicture;
	}

	public String getpicPath() {
		return picPath;
	}

	public void setpicPath(String picPath) {
		this.picPath = picPath;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public HuJiQianChu() {

		id = -1;
		qianchurenxingming = "";
		shenfenzhenghaoma = "";
		qianchuliyou = "";

	}

	public HuJiQianChu(long id, String qianchurenxingming,
			String shenfenzhenghaoma, String qianchuliyou,
			String uploadpicture, String picPath) {

		this.id = id;
		this.qianchurenxingming = qianchurenxingming;
		this.shenfenzhenghaoma = shenfenzhenghaoma;
		this.qianchuliyou = qianchuliyou;
		this.uploadpicture = uploadpicture;
		this.picPath = picPath;
	}

	private long id;
	private String qianchurenxingming;
	private String shenfenzhenghaoma;
	private String qianchuliyou;
	private String uploadpicture;
	private String picPath;

}