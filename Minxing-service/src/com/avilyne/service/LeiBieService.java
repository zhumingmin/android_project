package com.avilyne.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.avilyne.db.DBManager;

public class LeiBieService {

	public Boolean leibieservice(String leibie, int id) {

		// ��ȡSql��ѯ���
		String regSql = "insert into leibie_table values('" + leibie + "','"
				+ id + "') ";

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

	public String newleibie() {

		// ��ȡSql��ѯ���

		String leibieSql = "select leiBie from leibie_table order by idNumber desc limit 1";
		// String leibieSql =
		// "select leiBie from leibie_table where idNumber = (select idNumber from leibie_table where idNumber < {$idNumber} order by idNumber desc limit 1)";

		String role = null;
		// ��ȡDB����
		DBManager sql = DBManager.createInstance();
		sql.connectDB();

		// ����DB����
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
		return "��������������ǻᾡ�첹�䣡";
	}

}