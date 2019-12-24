package com.lemon.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.lemon.api.Constants;

/**
 * mysql数据库连接工具类
 * @author wurui
 */
public class JDBCUtil {
	/**
	 * 创建数据库连接对象
	 * @return
	 */
	public static Connection getConnect() {
		//创建连接对象
		Connection conn = null;
		try {
			//导入数据库驱动包
			//Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(Constants.JDBC_URL, Constants.JDBC_USER,Constants.JDBC_PASSWORD);
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 关闭数据库连接
	 * @param conn
	 */
	public static void close(Connection conn) {
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
