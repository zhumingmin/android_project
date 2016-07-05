package com.avilyne.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.avilyne.db.DBManager;

public class Service {

	public Boolean login(String account, String password) {

		// 获取Sql查询语句
		String logSql = "select * from account_table where account ='"
				+ account + "' and password1 ='" + password + "'";

		// 获取DB对象
		DBManager sql = DBManager.createInstance();
		sql.connectDB();

		// 操作DB对象
		try {
			ResultSet rs = sql.executeQuery(logSql);
			if (rs.next()) {
				sql.closeDB();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql.closeDB();
		return false;
	}

	public Boolean register(String account, String name, String phonenumber,
			String password1, String password2, int id) {

		// 获取Sql查询语句
		String regSql = "insert into account_table values('" + account + "','"
				+ name + "','" + phonenumber + "','" + password1 + "','"
				+ password2 + "','" + id + "') ";

		// 获取DB对象
		DBManager sql = DBManager.createInstance();
		sql.connectDB();

		int ret = sql.executeUpdate(regSql);
		if (ret != 0) {
			sql.closeDB();
			return true;
		}
		sql.closeDB();

		return false;
	}
}