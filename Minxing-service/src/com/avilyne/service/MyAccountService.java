package com.avilyne.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.avilyne.db.DBManager;

public class MyAccountService {

	public String account1(String account) {

		// ��ȡSql��ѯ���
		String accountSql1 = "select name from account_table where account ='"
				+ account + "'";

		DBManager sql = DBManager.createInstance();

		sql.connectDB();

		// ����DB����
		try {
			ResultSet rs1 = sql.executeQuery(accountSql1);

			if (rs1.next()) {
				String name = rs1.getString("name");
				sql.closeDB();
				return name;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql.closeDB();

		return "��ѯʧ��!";
	}

	public String account2(String account) {

		// ��ȡSql��ѯ���

		String accountSql2 = "select phonenumber from account_table where account ='"
				+ account + "'";
		DBManager sql = DBManager.createInstance();

		sql.connectDB();

		// ����DB����
		try {

			ResultSet rs2 = sql.executeQuery(accountSql2);

			if (rs2.next()) {
				String phonenumber = rs2.getString("phonenumber");
				sql.closeDB();
				return phonenumber;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql.closeDB();

		return "��ѯʧ��!";
	}
}