package com.avilyne.service;

import com.avilyne.db.DBManager;

public class FanKuiService {

	public Boolean fankuiservice(String shoujixitong, String dangqianshijian,
			String fankuineirong, int id) {

		// ��ȡSql��ѯ���
		String regSql = "insert into fankui_table values('" + shoujixitong
				+ "','" + dangqianshijian + "','" + fankuineirong + "','" + id
				+ "') ";

		// ��ȡDB����
		DBManager sql = DBManager.createInstance();
		sql.connectDB();
		if (regSql != null) {
			int ret = sql.executeUpdate(regSql);// ��ָ���쳣
			if (ret != 0) {
				sql.closeDB();
				return true;
			}
			sql.closeDB();
		}
		return false;

	}
}