package com.avilyne.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.avilyne.db.DBManager;

public class KeyWordService {

	public Boolean keywordservice(String keyword, int id) {

		// ��ȡSql��ѯ���
		String regSql = "insert into keyword_table values('" + keyword + "','"
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

	public String newkeyword() {

		// ��ȡSql��ѯ���
		// String newkeywordSql =
		// "select max(keywordnumber) from keyword_table";
		String newkeywordSql = "select keyword from keyword_table order by keywordnumber desc limit 1";
		String role = null;
		// ��ȡDB����
		DBManager sql = DBManager.createInstance();
		sql.connectDB();

		// ����DB����
		try {
			ResultSet rs = sql.executeQuery(newkeywordSql);
			if (rs.next()) {
				role = rs.getString("keyword");
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

	public ArrayList<String> hotwords() {

		// ��ȡSql��ѯ���
		String hotwordsSql = "SELECT keyword ,COUNT(*) FROM keyword_table GROUP BY keyword HAVING COUNT(*) >3";

		DBManager sql = DBManager.createInstance();
		sql.connectDB();
		ArrayList<String> hotwordslist = new ArrayList<String>();
		String role = null;
		// ����DB����
		try {
			ResultSet rs = sql.executeQuery(hotwordsSql);
			int rowCount = 0;

			while (rs.next()) {
				role = rs.getString("keyword");
				if (role != null) {
					rowCount++;
					hotwordslist.add(role);
				}

			}
			System.out.println(rowCount);
			System.out.println(hotwordslist);
			sql.closeDB();
			return hotwordslist;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql.closeDB();
		return null;
	}
}