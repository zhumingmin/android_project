package com.avilyne.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.avilyne.db.DBManager;

public class ZiXunService {

	public ArrayList<String> biaotiservice(String leibie) {

		// 获取Sql查询语句
		String Sql = "select biaoTi from zixun_table where leiBie = '" + leibie
				+ "'";

		DBManager sql = DBManager.createInstance();
		sql.connectDB();
		ArrayList<String> sqllist = new ArrayList<String>();
		String role = null;
		// 操作DB对象
		try {
			ResultSet rs = sql.executeQuery(Sql);
			int rowCount = 0;

			while (rs.next()) {
				role = rs.getString("biaoTi");
				if (role != null) {
					rowCount++;
					sqllist.add(role);
				}

			}
			System.out.println(rowCount);
			sql.closeDB();
			return sqllist;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql.closeDB();
		return null;
	}

	public ArrayList<String> laiyuanservice(String leibie) {

		// 获取Sql查询语句
		String Sql = "select laiYuan from zixun_table where leiBie = '" + leibie
				+ "'";

		DBManager sql = DBManager.createInstance();
		sql.connectDB();
		ArrayList<String> sqllist = new ArrayList<String>();
		String role = null;
		// 操作DB对象
		try {
			ResultSet rs = sql.executeQuery(Sql);
			int rowCount = 0;

			while (rs.next()) {
				role = rs.getString("laiYuan");
				if (role != null) {
					rowCount++;
					sqllist.add(role);
				}

			}
			System.out.println(rowCount);
			sql.closeDB();
			return sqllist;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql.closeDB();
		return null;
	}

	public ArrayList<String> yueduservice(String leibie) {

		// 获取Sql查询语句
		String Sql = "select yueDu from zixun_table where leiBie = '" + leibie
				+ "'";

		DBManager sql = DBManager.createInstance();
		sql.connectDB();
		ArrayList<String> sqllist = new ArrayList<String>();
		String role = null;
		// 操作DB对象
		try {
			ResultSet rs = sql.executeQuery(Sql);
			int rowCount = 0;

			while (rs.next()) {
				role = rs.getString("yueDu");
				if (role != null) {
					rowCount++;
					sqllist.add(role);
				}

			}
			System.out.println(rowCount);
			sql.closeDB();
			return sqllist;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql.closeDB();
		return null;
	}

	public ArrayList<String> shijianservice(String leibie) {

		// 获取Sql查询语句
		String Sql = "select shiJian from zixun_table where leiBie = '" + leibie
				+ "'";

		DBManager sql = DBManager.createInstance();
		sql.connectDB();
		ArrayList<String> sqllist = new ArrayList<String>();
		String role = null;
		// 操作DB对象
		try {
			ResultSet rs = sql.executeQuery(Sql);
			int rowCount = 0;

			while (rs.next()) {
				role = rs.getString("shiJian");
				if (role != null) {
					rowCount++;
					sqllist.add(role);
				}

			}
			System.out.println(rowCount);
			sql.closeDB();
			return sqllist;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql.closeDB();
		return null;
	}

	public ArrayList<String> tupianservice(String leibie) {

		// 获取Sql查询语句
		String Sql = "select tuPian from zixun_table where leiBie = '" + leibie
				+ "'";

		DBManager sql = DBManager.createInstance();
		sql.connectDB();
		ArrayList<String> sqllist = new ArrayList<String>();
		String role = null;
		// 操作DB对象
		try {
			ResultSet rs = sql.executeQuery(Sql);
			int rowCount = 0;

			while (rs.next()) {
				role = rs.getString("tuPian");
				if (role != null) {
					rowCount++;
					sqllist.add(role);
				}

			}
			System.out.println(rowCount);
			sql.closeDB();
			return sqllist;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql.closeDB();
		return null;
	}

	public ArrayList<String> neirongservice(String leibie) {

		// 获取Sql查询语句
		String Sql = "select neiRong from zixun_table where leiBie = '" + leibie
				+ "'";

		DBManager sql = DBManager.createInstance();
		sql.connectDB();
		ArrayList<String> sqllist = new ArrayList<String>();
		String role = null;
		// 操作DB对象
		try {
			ResultSet rs = sql.executeQuery(Sql);
			int rowCount = 0;

			while (rs.next()) {
				role = rs.getString("neiRong");
				if (role != null) {
					rowCount++;
					sqllist.add(role);
				}

			}
			System.out.println(rowCount);
			sql.closeDB();
			return sqllist;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql.closeDB();
		return null;
	}

	public ArrayList<String> lianjieservice(String leibie) {

		// 获取Sql查询语句
		String Sql = "select lianJie from zixun_table where leiBie = '" + leibie
				+ "'";

		DBManager sql = DBManager.createInstance();
		sql.connectDB();
		ArrayList<String> sqllist = new ArrayList<String>();
		String role = null;
		// 操作DB对象
		try {
			ResultSet rs = sql.executeQuery(Sql);
			int rowCount = 0;

			while (rs.next()) {
				role = rs.getString("lianJie");
				if (role != null) {
					rowCount++;
					sqllist.add(role);
				}

			}
			System.out.println(rowCount);
			sql.closeDB();
			return sqllist;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql.closeDB();
		return null;
	}

	public Boolean gengxinyuedu(String biaoti, String yuedu) {

		// 获取Sql查询语句
		String regSql = "update zixun_table set  yueDu='" + yuedu
				+ "'where biaoTi='" + biaoti + "'";
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