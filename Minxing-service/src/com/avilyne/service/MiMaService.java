package com.avilyne.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.avilyne.db.DBManager;

public class MiMaService {

	public String mima(String account, String phonenumber) {

		// ��ȡSql��ѯ���
		String mimaSql = "select password1 from account_table where account ='"
				+ account + "' and phonenumber ='" + phonenumber + "'";

		DBManager sql = DBManager.createInstance();

		sql.connectDB();

		// ����DB����
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

		return "�һ�����ʧ��!";
	}
}