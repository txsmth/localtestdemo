package com.lemon.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lemon.api.Constants;
import org.apache.http.client.methods.HttpRequestBase;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;

public class AuthenticationUtil {
	public static HashMap<String, String> env = new HashMap<String, String>();
	
	/**
	 * 获取鉴权token，存储到静态变量中
	 * @param body  响应体字符串
	 */
	public static void storeToken(String body) {
		Object token = JSONPath.read(body, "$.token");
		//Object token = JSONPath.read(body, "$..token");//取出来参数多个[]			
		if(token != null) { //非空存储
			env.put("token", "JWT "+token.toString());
			Object username = JSONPath.read(body, "$.username");
			env.put(Constants.USERNAME,username.toString());
		  }			
		}	

	
   public static void addToken(HttpRequestBase request ) {
	   if(env.get("token") != null ) {
		   request.setHeader("Authorization", env.get("token"));
	   }
	   
   }
}