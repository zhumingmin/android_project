package com.avilyne.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.avilyne.db.DBManager;

public class MiMaService {

	public String mima(String account, String phonenumber) {

		// 获取Sql查询语句
		String mimaSql = "select password1 from account_table where account ='"
				+ account + "' and phonenumber ='" + phonenumber + "'";

		DBManager sql = DBManager.createInstance();

		sql.connectDB();

		// 操作DB对象
		try {
			ResultSet rs = sql.executeQuery(mimaSql);
			if (rs.next()) {
				String role = rs.getString("password1");
				sql.closeDB();
				return role;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql.closeDB();

		return "找回密码失败!";
	}
}