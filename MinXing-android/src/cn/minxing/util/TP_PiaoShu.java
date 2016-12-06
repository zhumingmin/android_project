package cn.minxing.util;

public class TP_PiaoShu {

	public String getCanXuanRen() {
		return canxuanren;
	}

	public void setCanXuanRen(String canxuanren) {
		this.canxuanren = canxuanren;
	}

	public String getCanXuanZhiWu() {
		return canxuanzhiwu;
	}

	public void setCanXuanZhiWu(String canxuanzhiwu) {
		this.canxuanzhiwu = canxuanzhiwu;
	}

	public String getGeRenShiJi() {
		return gerenshiji;
	}

	public void setGeRenShiJi(String gerenshiji) {
		this.gerenshiji = gerenshiji;
	}

	public String getPiaoShu() {
		return piaoshu;
	}

	public void setPiaoShu(String piaoshu) {
		this.piaoshu = piaoshu;
	}

	public TP_PiaoShu() {

		canxuanren = null;
		canxuanzhiwu = null;
		gerenshiji = null;
		piaoshu = null;

	}

	public TP_PiaoShu(String canxuanren, String canxuanzhiwu,
			String gerenshiji, String piaoshu) {

		this.canxuanren = canxuanren;
		this.canxuanzhiwu = canxuanzhiwu;
		this.gerenshiji = gerenshiji;
		this.piaoshu = piaoshu;

	}

	private String canxuanren;
	private String canxuanzhiwu;
	private String gerenshiji;
	private String piaoshu;

}