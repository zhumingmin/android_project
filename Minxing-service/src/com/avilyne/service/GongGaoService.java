package com.avilyne.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.avilyne.db.DBManager;

public class GongGaoService {

	public String caiwugonggao() {

		// 获取Sql查询语句
		String caiwugonggaoSql = "select caiwu from gonggao_table";

		DBManager sql = DBManager.createInstance();

		sql.connectDB();

		// 操作DB对象
		try {
			ResultSet rs1 = sql.executeQuery(caiwugonggaoSql);

			if (rs1.next()) {
				String caiwu = rs1.getString("caiwu");
				sql.closeDB();
				return caiwu;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql.closeDB();

		return "查询失败!";
	}

	public String caiwutime() {

		// 获取Sql查询语句
		String caiwutimeSql = "select caiwutime from gonggao_table";

		DBManager sql = DBManager.createInstance();

		sql.connectDB();

		// 操作DB对象
		try {
			ResultSet rs1 = sql.executeQuery(caiwutimeSql);

			if (rs1.next()) {
				String time = rs1.getString("caiwutime");
				sql.closeDB();
				return time;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql.closeDB();

		return "查询失败!";
	}

	public String toupiaogonggao() {

		// 获取Sql查询语句

		String toupiaogonggaoSql = "select toupiao from gonggao_table";

		DBManager sql = DBManager.createInstance();

		sql.connectDB();

		// 操作DB对象
		try {
			ResultSet rs1 = sql.executeQuery(toupiaogonggaoSql);

			if (rs1.next()) {
				String toupiao = rs1.getString("toupiao");
				sql.closeDB();
				return toupiao;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql.closeDB();

		return "查询失败!";
	}

	public String toupiaotime() {

		// 获取Sql查询语句

		String toupiaotimeSql = "select toupiaotime from gonggao_table";

		DBManager sql = DBManager.createInstance();

		sql.connectDB();

		// 操作DB对象
		try {
			ResultSet rs1 = sql.executeQuery(toupiaotimeSql);

			if (rs1.next()) {
				String time = rs1.getString("toupiaotime");
				sql.closeDB();
				return time;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql.closeDB();

		return "查询失败!";
	}
}