package com.avilyne.service;

import com.avilyne.db.DBManager;

public class HuJiService {

	public Boolean hujiservice(String huji_name, String IDnumber,
			String reason, String picturepath, int id) {

		// ��ȡSql��ѯ���
		String regSql = "insert into huji_table values('" + huji_name + "','"
				+ IDnumber + "','" + reason + "','" + picturepath + "','" + id
				+ "') ";

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