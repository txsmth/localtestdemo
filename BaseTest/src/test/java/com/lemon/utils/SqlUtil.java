package com.lemon.utils;

import java.sql.Connection;
import java.sql.SQLException;

import com.alibaba.fastjson.JSONPath;
import com.lemon.api.RequestCase;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;

public class SqlUtil {
	public static void main(String[] args) {
		String sql = "select count(*) from member where mobile_phone = '186684069851'";
		String sql2 = "select leave_amount from member where id = 70368";
		Object result = query(sql2);
		System.out.println("result: "+result.toString());
		//System.out.println(result.getClass());

	}


	/**
	 * 调用JBDCUtil执行sql语句
	 * @param sql
	 * @return
	 */
	public static Object query(String sql) {
		if(StringUtils.isBlank(sql)) {
			return null;
		}	
		
		//创建QueryRunner对象
		QueryRunner query = new QueryRunner();
		//获取数据库连接
		Connection conn = JDBCUtil.getConnect();
		try {
			//创建返回类型结果对象
			ScalarHandler rsh = new ScalarHandler();
			//执行sql语句
			Object resultdata = query.query(conn,sql, rsh);
			return resultdata;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			//关闭数据库连接
			JDBCUtil.close(conn);
		}
		return null;
	}
}
