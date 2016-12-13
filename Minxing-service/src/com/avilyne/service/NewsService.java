package com.avilyne.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.avilyne.db.DBManager;

public class NewsService {

	public ArrayList<String> news(String title) {

		// 获取Sql查询语句
		String bodySql = "select body from news_table where title ='" + title
				+ "'";

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

	public String picture(String title) {

		// 获取Sql查询语句
		String pictureSql = "select picturepath from news_table where title ='"
				+ title + "'";

		DBManager sql = DBManager.createInstance();
		sql.connectDB();

		// 操作DB对象
		try {
			ResultSet rs = sql.executeQuery(pictureSql);
			if (rs.next()) {
				String role = rs.getString("picturepath");
				sql.closeDB();
				return role;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql.closeDB();
		return null;
	}

	public ArrayList<String> newspicture(String title) {

		// 获取Sql查询语句
		String bodySql = "select picturepath from news_table where title LIKE '"
				+ "%" + title + "%" + "'";

		DBManager sql = DBManager.createInstance();
		sql.connectDB();
		ArrayList<String> pathlist = new ArrayList<String>();
		String role = null;
		// 操作DB对象
		try {
			ResultSet rs = sql.executeQuery(bodySql);
			int rowCount = 0;

			while (rs.next()) {
				role = rs.getString("picturepath");
				if (role != null) {
					rowCount++;
					pathlist.add(role);
				}

			}
			System.out.println(rowCount);
			// System.out.println(pathlist);
			sql.closeDB();
			return pathlist;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql.closeDB();
		return null;
	}

	public ArrayList<String> getCategory(String title) {

		// 获取Sql查询语句
		String bodySql = "select category from news_table where title LIKE '"
				+ "%" + title + "%" + "'";

		DBManager sql = DBManager.createInstance();
		sql.connectDB();
		ArrayList<String> categorylist = new ArrayList<String>();
		String role = null;
		// 操作DB对象
		try {
			ResultSet rs = sql.executeQuery(bodySql);
			int rowCount = 0;

			while (rs.next()) {
				role = rs.getString("category");
				if (role != null) {
					rowCount++;
					categorylist.add(role);
				}

			}
			System.out.println(rowCount);
			// System.out.println(pathlist);
			sql.closeDB();
			return categorylist;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql.closeDB();
		return null;
	}

	public ArrayList<String> getPubdate(String title) {

		// 获取Sql查询语句
		String bodySql = "select pubdate from news_table where title LIKE '"
				+ "%" + title + "%" + "'";

		DBManager sql = DBManager.createInstance();
		sql.connectDB();
		ArrayList<String> pubdatelist = new ArrayList<String>();
		String role = null;
		// 操作DB对象
		try {
			ResultSet rs = sql.executeQuery(bodySql);
			int rowCount = 0;

			while (rs.next()) {
				role = rs.getString("pubdate");
				if (role != null) {
					rowCount++;
					pubdatelist.add(role);
				}

			}
			System.out.println(rowCount);
			// System.out.println(pathlist);
			sql.closeDB();
			return pubdatelist;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql.closeDB();
		return null;
	}

	public ArrayList<String> getRead(String title) {

		// 获取Sql查询语句
		String bodySql = "select readnumber from news_table where title LIKE '"
				+ "%" + title + "%" + "'";

		DBManager sql = DBManager.createInstance();
		sql.connectDB();
		ArrayList<String> readlist = new ArrayList<String>();
		String role = null;
		// 操作DB对象
		try {
			ResultSet rs = sql.executeQuery(bodySql);
			int rowCount = 0;

			while (rs.next()) {
				role = rs.getString("readnumber");
				if (role != null) {
					rowCount++;
					readlist.add(role);
				}

			}
			System.out.println(rowCount);
			// System.out.println(pathlist);
			sql.closeDB();
			return readlist;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql.closeDB();
		return null;
	}

	public ArrayList<String> getLike(String title) {

		// 获取Sql查询语句
		String bodySql = "select likenumber from news_table where title LIKE '"
				+ "%" + title + "%" + "'";

		DBManager sql = DBManager.createInstance();
		sql.connectDB();
		ArrayList<String> likelist = new ArrayList<String>();
		String role = null;
		// 操作DB对象
		try {
			ResultSet rs = sql.executeQuery(bodySql);
			int rowCount = 0;

			while (rs.next()) {
				role = rs.getString("likenumber");
				if (role != null) {
					rowCount++;
					likelist.add(role);
				}

			}
			System.out.println(rowCount);
			// System.out.println(pathlist);
			sql.closeDB();
			return likelist;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql.closeDB();
		return null;
	}

	public ArrayList<String> getUnlike(String title) {

		// 获取Sql查询语句
		String bodySql = "select unlikenumber from news_table where title LIKE '"
				+ "%" + title + "%" + "'";

		DBManager sql = DBManager.createInstance();
		sql.connectDB();
		ArrayList<String> unlikelist = new ArrayList<String>();
		String role = null;
		// 操作DB对象
		try {
			ResultSet rs = sql.executeQuery(bodySql);
			int rowCount = 0;

			while (rs.next()) {
				role = rs.getString("unlikenumber");
				if (role != null) {
					rowCount++;
					unlikelist.add(role);
				}

			}
			System.out.println(rowCount);
			// System.out.println(pathlist);
			sql.closeDB();
			return unlikelist;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql.closeDB();
		return null;
	}

	public Boolean insertTuijian(String read, String like, String unlike,
			String title) {

		// 获取Sql查询语句
		String regSql = "update news_table set  readnumber='" + read
				+ "',likenumber='" + like + "',unlikenumber='" + unlike
				+ "'where title='" + title + "'";
		// 获取DB对象
		DBManager sql = DBManager.createInstance();
		sql.connectDB();
		if (regSql != null) {
			int ret = sql.executeUpdate(regSql);// 空指针异常
			if (ret != 0) {
				sql.closeDB();
				return true;
			}
			sql.closeDB();
		}
		return false;

	}

	public Boolean zidingyiservice(String title, String category,
			String pubdate, String readnumber, String likenumber,
			String unlikenumber, String body, String picturepath, String link,
			int id) {

		// 获取Sql查询语句
		String regSql = "insert into news_table values('" + title + "','"
				+ category + "','" + pubdate + "','" + readnumber + "','"
				+ likenumber + "','" + unlikenumber + "','" + body + "','"
				+ picturepath + "','" + link + "','" + id + "') ";

		// 获取DB对象
		DBManager sql = DBManager.createInstance();
		sql.connectDB();
		if (regSql != null) {
			int ret = sql.executeUpdate(regSql);// 空指针异常
			if (ret != 0) {
				sql.closeDB();
				return true;
			}
			sql.closeDB();
		}
		return false;

	}
}