package com.avilyne.service;

import com.avilyne.db.DBManager;

public class NfcTagService {

	public Boolean nfcservice(String nfctag, int id) {

		// ��ȡSql��ѯ���
		String regSql = "insert into nfc_table values('" + nfctag + "','" + id
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