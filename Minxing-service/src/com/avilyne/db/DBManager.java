package com.avilyne.db;

import java.sql.*;

public class DBManager {

	// ���ݿ����ӳ���
	public static final String DRIVER = "com.mysql.jdbc.Driver";
	public static final String USER = "root";
	public static final String PASS = "123456";
	public static final String URL = "jdbc:mysql://localhost:3306/minxing";

	// ��̬��Ա��֧�ֵ�̬ģʽ
	private static DBManager per = null;
	private Connection conn = null;
	private Statement stmt = null;

	// ��̬ģʽ-����ģʽ
	private DBManager() {
	}

	public static DBManager createInstance() {
		if (per == null) {
			per = new DBManager();
			per.initDB();
		}
		return per;
	}

	// ��������
	public void initDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// �������ݿ⣬��ȡ���+����
	public void connectDB() {
		System.out.println("Connecting to database...");
		try {
			conn = DriverManager.getConnection(URL, USER, PASS);
			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("SqlManager:Connect to database successful.");
	}

	// �ر����ݿ� �رն����ͷž��
	public void closeDB() {
		System.out.println("Close connection to database..");
		try {
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Close connection successful");
	}

	// ��ѯ
	public ResultSet executeQuery(String sql) {
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	// ����/ɾ��/�޸�
	public int executeUpdate(String sql) {
		int ret = 0;
		try {

			ret = stmt.executeUpdate(sql);//��ָ���쳣

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}
}