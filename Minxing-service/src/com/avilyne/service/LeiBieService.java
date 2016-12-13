package com.avilyne.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.avilyne.db.DBManager;

public class LeiBieService {

	public Boolean leibieservice(String leibie, int id) {

		// 获取Sql查询语句
		String regSql = "insert into leibie_table values('" + leibie + "','"
				+ id + "') ";

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

	public String newleibie() {

		// 获取Sql查询语句

		String leibieSql = "select leiBie from leibie_table order by idNumber desc limit 1";
		// String leibieSql =
		// "select leiBie from leibie_table where idNumber = (select idNumber from leibie_table where idNumber < {$idNumber} order by idNumber desc limit 1)";

		String role = null;
		// 获取DB对象
		DBManager sql = DBManager.createInstance();
		sql.connectDB();

		// 操作DB对象
		try {
			ResultSet rs = sql.executeQuery(leibieSql);
			if (rs.next()) {
				role = rs.getString("leiBie");
				sql.closeDB();
				return role;
			}
			System.out.println(role);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql.closeDB();
		return "无搜索结果，我们会尽快补充！";
	}

}