package com.avilyne.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.avilyne.db.DBManager;

public class NewsService {

	public Boolean news(String title, String author, Date pubdate,
			int commentcount, String boday, String link, int newsnumber) {

		// 获取Sql查询语句
		String logSql = "select * from news_table where title ='" + title
				+ "' and author ='" + author + "' and pubdate ='" + pubdate
				+ "' and commentcount ='" + commentcount + "' and boday ='"
				+ boday + "' and link ='" + link + "' and newsnumber ='"
				+ newsnumber + "'";

		// 获取DB对象
		DBManager sql = DBManager.createInstance();
		sql.connectDB();

		// 操作DB对象
		try {
			ResultSet rs = sql.executeQuery(logSql);
			if (rs.next()) {
				sql.closeDB();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql.closeDB();
		return false;
	}

}