package com.avilyne.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Picture {

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

	public Picture() {

		id = -1;
		uploadpicture = "";
		picPath="";

	}

	public Picture(long id, String uploadpicture,String picPath) {

		this.id = id;
		this.uploadpicture = uploadpicture;
		this.picPath = picPath;
		
	}

	private long id;
	private String uploadpicture;
	private String picPath;


}