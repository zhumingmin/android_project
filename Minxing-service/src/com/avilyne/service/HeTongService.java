package com.avilyne.service;

import com.avilyne.db.DBManager;

public class HeTongService {

	public Boolean hetongservice(String jiafang, String yifang,
			String yuedingfeiyong, String qiandingnianxian,
			String qitashuoming, int id) {

		// 获取Sql查询语句
		String regSql = "insert into hetong_table values('" + jiafang + "','"
				+ yifang + "','" + yuedingfeiyong + "','" + qiandingnianxian
				+ "','" + qitashuoming + "','" + id + "') ";

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