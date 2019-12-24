package com.lemon.api;

public class Constants {
	
	//excel路径
	public static final String EXCEL_PATH = "src/test/resources/test001.xlsx";
	//excel实际响应列号
	public static final int ACTUAL_RESPONSE_DATA_CELLNUM=5;
	// excel断言列号
	public static final int ASSERT_CELLNUM = 6;
	
	//数据库连接url
	public static final String JDBC_URL = "jdbc:mysql://api.lemonban.com:3306/futureloan?useUnicode=true&characterEncoding=utf-8";
	//数据库连接用户名
	public static final String JDBC_USER = "future";
	//数据库连接密码
	public static final String JDBC_PASSWORD = "123456";

	// 参数化替换字段
	public static final String REGISTER_MOBILEPHONE_TEXT = "${toBeRegisterMobilephone}";//用户名
	public static final String REGISTER_EMAIL_TEXT = "${toBeEmail}";//邮箱
	public static final String REGISTER_PASSWORD_TEXT = "${toBeRegisterPassword}";//密码
	public static final String REGISTER_COMFIRM_TEXT = "${toBeConfirmPassword}";//确认密码
	public static final String LOGIN_MOBILEPHONE_TEXT = "${toBeLoginMobilephone}";
	public static final String LOGIN_PASSWORD_TEXT = "${toBeLoginPassword}";//登陆用户名
	public static final String USERNAME = "${username}";//登陆密码
	// 参数化替换值
	public static final String REGISTER_MOBILEPHONE_VALUE = getTel();
	public static final String REGISTER_EMAIL_VALUE = REGISTER_MOBILEPHONE_VALUE + "@qq.com";//邮箱
	public static final String REGISTER_PASSWORD_VALUE = "123456";
	public static final String REGISTER_COMFIRM_VALUE= REGISTER_PASSWORD_VALUE;//确认密码
	public static final String LOGIN_MOBILEPHONE_VALUE = REGISTER_MOBILEPHONE_VALUE;
	public static final String LOGIN_PASSWORD_VALUE = REGISTER_PASSWORD_VALUE;

	public static int getNum(int start, int end) {
		return (int) (Math.random() * (end - start + 1) + start);
	}

	private static String getTel() {
		String[] telFirst = { "134", "135", "136", "137", "138", "139", "150", "151", "152", "157", "158", "159", "130",
				"131", "132", "155", "156", "133", "153" };
		int index = getNum(0, telFirst.length - 1);
		String first = telFirst[index];
		String second = String.valueOf(getNum(1, 888) + 10000).substring(1);
		String third = String.valueOf(getNum(1, 9100) + 10000).substring(1);
		return first + second + third;
	}

}
