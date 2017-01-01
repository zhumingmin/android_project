package cn.minxing.util;

import java.io.Serializable;

public class Info implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Info(String url) {
		super();
		this.url = url;
	}
}
