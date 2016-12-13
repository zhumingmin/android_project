package com.avilyne.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.avilyne.db.DBManager;

public class GongGaoService {

	public String caiwugonggao() {

		// ��ȡSql��ѯ���
		String caiwugonggaoSql = "select caiwu from gonggao_table";

		DBManager sql = DBManager.createInstance();

		sql.connectDB();

		// ����DB����
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

		return "��ѯʧ��!";
	}

	public String caiwutime() {

		// ��ȡSql��ѯ���
		String caiwutimeSql = "select caiwutime from gonggao_table";

		DBManager sql = DBManager.createInstance();

		sql.connectDB();

		// ����DB����
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

		return "��ѯʧ��!";
	}

	public String toupiaogonggao() {

		// ��ȡSql��ѯ���

		String toupiaogonggaoSql = "select toupiao from gonggao_table";

		DBManager sql = DBManager.createInstance();

		sql.connectDB();

		// ����DB����
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

		return "��ѯʧ��!";
	}

	public String toupiaotime() {

		// ��ȡSql��ѯ���

		String toupiaotimeSql = "select toupiaotime from gonggao_table";

		DBManager sql = DBManager.createInstance();

		sql.connectDB();

		// ����DB����
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

		return "��ѯʧ��!";
	}
}