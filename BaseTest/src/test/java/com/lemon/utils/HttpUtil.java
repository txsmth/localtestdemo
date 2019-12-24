package com.lemon.utils;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;

public class HttpUtil {
	private static Logger log = Logger.getLogger(HttpUtil.class);
	/*

     * @param  url 请求url和method

     * @param type 请求方式

     * @param params 请求参数

     * @param contenttype  请求参数类型

     */
	public static String call(String url, String type, String params, String contenttype) {
		if(contenttype.equalsIgnoreCase("json")) {
			  if(type.equalsIgnoreCase("post") ) {
				  //设置请求头
				  HashMap<String, String> postHeader = new HashMap<String, String>();
				  postHeader.put("Content-Type", "application/json");
				  //请求添加token
				  if(AuthenticationUtil.env.get("token") != null) {
					//  System.out.println(type+"的token是："+AuthenticationUtil.tokenHeader.get("token") );
					  postHeader.put("Authorization", AuthenticationUtil.env.get("token"));
				  }
				  String body = HttpUtil.post(url, postHeader, params);				  
				//如果有，存储token
				  AuthenticationUtil.storeToken(body);
				  return body;
			  }else if(type.equalsIgnoreCase("get") ){
				//设置请求头
				  HashMap<String, String> getHeader = new HashMap<String, String>();
				  //请求添加token
				  if(AuthenticationUtil.env.get("token") != null) {
					//  System.out.println(type+"的token是："+AuthenticationUtil.tokenHeader.get("token") );
					  getHeader.put("Authorization", AuthenticationUtil.env.get("token"));
				  }
				  String body = HttpUtil.get(url, getHeader, params);
				  //如果有，存储token
				  AuthenticationUtil.storeToken(body);
				  return body;
			  }
		  }else if(contenttype.equalsIgnoreCase("form")) {
			  HashMap<String, String> formHeader = new HashMap<String, String>();
			  formHeader.put("Content-Type", "application/x-www-form-urlencoded");
			//请求添加token
			  if(AuthenticationUtil.env.get("token") != null) {
				//out.println(type+"的token是："+AuthenticationUtil.tokenHeader.get("token") );
				  formHeader.put("Authorization", AuthenticationUtil.env.get("token"));
			  }
			  Map<String, String> map = JSONObject.parseObject(params,Map.class);
			  String strparams = "";
			  for(String key : map.keySet()) {		  
				  strparams += key+"="+map.get(key)+"&";				 
			  }
			  strparams = strparams.substring(0, strparams.length()-1);
			//  System.out.println("拼接参数： "+ strparams);
			  String body = HttpUtil.postform(url, formHeader, strparams);
			  AuthenticationUtil.storeToken(body);
			  return body;
			//  System.out.println(status);      
			//  Assert.assertEquals(status, 200);//断言响应状态码
		  }
		return null;
	} 
	
	/*

     * @param  url 请求url和method

     * @param headerMap 请求头

     * @param array 需要操作的数据

     * @throws Exception

     */
	public static String get(String url, HashMap<String, String> headerMap, String params) {
		//1、创建一个get请求。携带url+method

		HttpGet httpGet = new HttpGet(url+"?"+params);
		//1.1添加请求头
		if(headerMap!=null) {
			for (String key : headerMap.keySet()) {
				httpGet.setHeader(key, headerMap.get(key));				
			}
		}
		//2、创建发送请求客户端  HttpClients是HttpClient的工具类
		CloseableHttpClient client = HttpClients.createDefault();		
		//3、客户端发送get请求，立即返回http响应
		CloseableHttpResponse response =null;
		try {
			 response = (CloseableHttpResponse) client.execute(httpGet);
			 //4、response整个响应对象（body、header、statuscode）
			 //4.1	 获取响应体body，格式化返回body
			 HttpEntity entity = response.getEntity();
			 String responsebody = EntityUtils.toString(entity);
			 //4.2 获取所有的响应头allHeaders
			// Header[] allHeaders = response.getAllHeaders();
			 log.info("get请求响应头是： "+ Arrays.toString(response.getAllHeaders()));
			 //4.3 	 获取状态码statusCode
			 int statusCode = response.getStatusLine().getStatusCode();
			log.info("Get请求状态响应码是： "+statusCode);
			log.info("Get请求响应体是： "+responsebody);
			 return responsebody;			 			 
		} catch (Exception e) {			
			e.printStackTrace();
			log.error("Get请求异常： "+e.getMessage());
		}finally {// 消耗实体内容
			Close(response, client);
		}
		return null;
	}
	
	public static String post(String url,  HashMap<String, String> headerMap, String body) {		
		//1、创建一个get请求。携带url+method
		HttpPost Post = new HttpPost(url);			
		CloseableHttpResponse response =null;
		CloseableHttpClient client =null;
		try {
			//1.1添加请求头
			if(headerMap!=null) {
				for (String key : headerMap.keySet()) {
					Post.setHeader(key, headerMap.get(key));	
				}
			}			
			//1.2添加请求体
			StringEntity Entity = new StringEntity(body);
			Post.setEntity(Entity);
			
			//2、创建发送请求客户端  HttpClients是HttpClient的工具类
			 client = HttpClients.createDefault();
			
			//3、客户端发送post请求，立即返回http响应	
			 response = (CloseableHttpResponse) client.execute(Post);
			 
			 //4、response整个响应对象（body、header、statuscode）
			 //4.1	 获取响应体body，格式化返回body
			 HttpEntity entity = response.getEntity();
			 String responsebody = EntityUtils.toString(entity);
			 //4.2 获取所有的响应头allHeaders
			 Header[] allHeaders = response.getAllHeaders();
			 log.info("Post请求响应头是： "+Arrays.toString(allHeaders));
			 //4.3 	 获取状态码statusCode
			 int statusCode = response.getStatusLine().getStatusCode();
			// String code = String.valueOf(statusCode);
		//	 System.out.println("Post请求状态响应码是： "+statusCode) ;
			log.info("Post请求状态响应码是： "+statusCode);
			log.info("Post请求响应体是： "+responsebody);
			 return responsebody;			 			 
		} catch (Exception e) {
			e.printStackTrace();
		}finally {// 消耗实体内容
			Close(response, client);
				
		}
		return null;
	}
	
	public static String postform(String url,  HashMap<String, String> headerMap, String body) {		
		//1、创建一个get请求。携带url+method
		HttpPost Postform = new HttpPost(url);			
		CloseableHttpResponse response =null;
		CloseableHttpClient client =null;
		//int statusCode=0;
		try {
			//1.1添加请求头
			if(headerMap!=null) {
				for (String key : headerMap.keySet()) {
					Postform.setHeader(key, headerMap.get(key));	
				}
			}		
			//1.2添加请求体
			StringEntity Entity = new StringEntity(body);
			Postform.setEntity(Entity);			
			//2、创建发送请求客户端  HttpClients是HttpClient的工具类
			 client = HttpClients.createDefault();			
			//3、客户端发送post请求，立即返回http响应	
			 response = (CloseableHttpResponse) client.execute(Postform);			 
			 //4、response整个响应对象（body、header、statuscode）
			 //4.1	 获取响应体body，格式化返回body
			 HttpEntity entity = response.getEntity();
			 String responsebody = EntityUtils.toString(entity);
			 log.info("Postform请求响应体是： "+responsebody);
			 //4.2 获取所有的响应头allHeaders
			 Header[] allHeaders = response.getAllHeaders();			
			 //4.3 	 获取状态码statusCode
			 int statusCode = response.getStatusLine().getStatusCode();
			log.info("Postform请求状态响应码是： "+statusCode);
			log.info("Postform请求响应体是： "+responsebody);
			 return responsebody;			 			 
		} catch (Exception e) {
			e.printStackTrace();
		}finally {// 消耗实体内容
			Close(response, client);
				
		}
		return null;
	}
	

	private static void Close(CloseableHttpResponse response, CloseableHttpClient client) {
		if (response != null || client != null) {
			try {
				response.close();
				client.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
