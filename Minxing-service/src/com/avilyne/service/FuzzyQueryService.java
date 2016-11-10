package com.avilyne.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.avilyne.db.DBManager;

public class FuzzyQueryService {

	public ArrayList<String> fqs(String title) {

		// 获取Sql查询语句
		String bodySql = "select body from news_table where title LIKE '" + "%"
				+ title + "%" + "'";

		DBManager sql = DBManager.createInstance();
		sql.connectDB();
		ArrayList<String> bodylist = new ArrayList<String>();
		String role = null;
		// 操作DB对象
		try {
			ResultSet rs = sql.executeQuery(bodySql);
			int rowCount = 0;

			while (rs.next()) {
				role = rs.getString("body");
				if (role != null) {
					rowCount++;
					bodylist.add(role);
				}

			}
			System.out.println(rowCount);
			System.out.println(bodylist);
			sql.closeDB();
			return bodylist;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql.closeDB();
		return null;
	}

	public ArrayList<String> fqtitle(String title) {

		// 获取Sql查询语句
		String bodySql = "select title from news_table where title LIKE '"
				+ "%" + title + "%" + "'";

		DBManager sql = DBManager.createInstance();
		sql.connectDB();
		ArrayList<String> bodylist = new ArrayList<String>();
		String role = null;
		// 操作DB对象
		try {
			ResultSet rs = sql.executeQuery(bodySql);
			int rowCount = 0;

			while (rs.next()) {
				role = rs.getString("title");
				if (role != null) {
					rowCount++;
					bodylist.add(role);
				}

			}
			System.out.println(rowCount);
			System.out.println(bodylist);
			sql.closeDB();
			return bodylist;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql.closeDB();
		return null;
	}

}