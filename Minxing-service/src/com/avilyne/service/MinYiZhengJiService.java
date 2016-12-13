package com.avilyne.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.avilyne.db.DBManager;

public class MinYiZhengJiService {

	public ArrayList<String> canxuanren() {

		// ��ȡSql��ѯ���
		String canxuanrenSql = "select canxuanren from votes_table";
		DBManager sql = DBManager.createInstance();
		sql.connectDB();
		ArrayList<String> canxuanrenlist = new ArrayList<String>();
		String role = null;
		// ����DB����
		try {
			ResultSet rs = sql.executeQuery(canxuanrenSql);
			int rowCount = 0;

			while (rs.next()) {
				role = rs.getString("canxuanren");
				if (role != null) {
					rowCount++;
					canxuanrenlist.add(role);
				}

			}
			System.out.println(rowCount);
			System.out.println(canxuanrenlist);
			sql.closeDB();
			return canxuanrenlist;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql.closeDB();
		return null;
	}

	public ArrayList<String> canxuanzhiwu() {

		// ��ȡSql��ѯ���
		String canxuanzhiwusql = "select canxuanzhiwu from votes_table";

		DBManager sql = DBManager.createInstance();
		sql.connectDB();
		ArrayList<String> canxuanzhiwulist = new ArrayList<String>();
		String role = null;
		// ����DB����
		try {
			ResultSet rs = sql.executeQuery(canxuanzhiwusql);
			int rowCount = 0;

			while (rs.next()) {
				role = rs.getString("canxuanzhiwu");
				if (role != null) {
					rowCount++;
					canxuanzhiwulist.add(role);
				}

			}
			System.out.println(rowCount);
			System.out.println(canxuanzhiwulist);
			sql.closeDB();
			return canxuanzhiwulist;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql.closeDB();
		return null;
	}

	public ArrayList<String> gerenshiji() {

		// ��ȡSql��ѯ���
		String bodySql = "select gerenshiji from votes_table";

		DBManager sql = DBManager.createInstance();
		sql.connectDB();
		ArrayList<String> gerenshijilist = new ArrayList<String>();
		String role = null;
		// ����DB����
		try {
			ResultSet rs = sql.executeQuery(bodySql);
			int rowCount = 0;

			while (rs.next()) {
				role = rs.getString("gerenshiji");
				if (role != null) {
					rowCount++;
					gerenshijilist.add(role);
				}

			}
			System.out.println(rowCount);

			sql.closeDB();
			return gerenshijilist;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql.closeDB();
		return null;
	}

	public ArrayList<String> getpiaoshu() {

		// ��ȡSql��ѯ���
		String bodySql = "select piaoshu from votes_table";

		DBManager sql = DBManager.createInstance();
		sql.connectDB();
		ArrayList<String> piaoshulist = new ArrayList<String>();
		String role = null;
		// ����DB����
		try {
			ResultSet rs = sql.executeQuery(bodySql);
			int rowCount = 0;

			while (rs.next()) {
				role = rs.getString("piaoshu");
				if (role != null) {
					rowCount++;
					piaoshulist.add(role);
				}

			}
			System.out.println(rowCount);

			sql.closeDB();
			return piaoshulist;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql.closeDB();
		return null;
	}

	public Boolean setpiaoshu(String piaoshu, String canxuanren) {

		// ��ȡSql��ѯ���
		String regSql = "update votes_table set  piaoshu='" + piaoshu
				+ "'where canxuanren ='" + canxuanren + "'";

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
}