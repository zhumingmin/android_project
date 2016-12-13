package com.avilyne.service;

import com.avilyne.db.DBManager;

public class FanKuiService {

	public Boolean fankuiservice(String shoujixitong, String dangqianshijian,
			String fankuineirong, int id) {

		// 获取Sql查询语句
		String regSql = "insert into fankui_table values('" + shoujixitong
				+ "','" + dangqianshijian + "','" + fankuineirong + "','" + id
				+ "') ";

		// 获取DB对象
		DBManager sql = DBManager.createInstance();
		sql.connectDB();
		if (regSql != null) {
			int ret = sql.executeUpdate(regSql);// 空指针异常
			if (ret != 0) {
				sql.closeDB();
				return true;
			}
			sql.closeDB();
		}
		return false;

	}
}