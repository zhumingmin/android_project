package com.avilyne.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.avilyne.db.DBManager;

public class YiBaoService {

	public String yibao(String idcardnumber) {

		// ��ȡSql��ѯ���
		String yibaoSql = "select yibaoresult from yibao_table where idcardnumber ='"
				+ idcardnumber + "'";

		DBManager sql = DBManager.createInstance();

		sql.connectDB();

		// ����DB����
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

		return "��ѯʧ��!";
	}
}