package com.avilyne.service;

import com.avilyne.db.DBManager;

public class BaoJianService {

	public Boolean hujiservice(String project, String address,
			String baojian_phonenumber, String appointment, int id) {

		// 获取Sql查询语句
		String regSql = "insert into baojian_table values('" + project + "','"
				+ address + "','" + baojian_phonenumber + "','" + appointment
				+ "','" + id + "') ";

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