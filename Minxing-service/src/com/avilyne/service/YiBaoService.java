package com.avilyne.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.avilyne.db.DBManager;

public class YiBaoService {

	public String yibao(String idcardnumber) {

		// 获取Sql查询语句
		String yibaoSql = "select yibaoresult from yibao_table where idcardnumber ='"
				+ idcardnumber + "'";

		DBManager sql = DBManager.createInstance();

		sql.connectDB();

		// 操作DB对象
		try {
			ResultSet rs = sql.executeQuery(yibaoSql);
			if (rs.next()) {
				String role = rs.getString("yibaoresult");
				sql.closeDB();
				return role;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql.closeDB();

		return "查询失败!";
	}
}