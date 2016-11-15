package cn.minxing.util;

public class RS_News {
	private String biaoti;
	private String leibie;
	private String tupiandizhi;
	private String yueduliang2;
	private String body;
	private String like;
	private String unlike;

	public RS_News(String biaoti, String leibie, String yueduliang2, String like,
			String unlike, String body, String tupiandizhi) {
		this.biaoti = biaoti;
		this.leibie = leibie;
		this.yueduliang2 = yueduliang2;
		this.like = like;
		this.unlike = unlike;
		this.body = body;
		this.tupiandizhi = tupiandizhi;
	}

	public String getBiaoTi() {
		return biaoti;
	}

	public String getLeiBie() {
		return leibie;
	}

	public String getYueDuLiang2() {
		return yueduliang2;
	}

	public String getLike() {
		return like;
	}

	public String getUnlike() {
		return unlike;
	}

	public String getBody() {
		return body;
	}

	public String getTupiandizhi() {
		return tupiandizhi;
	}

}
