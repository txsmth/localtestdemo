package com.lemon.testcaes;

import com.lemon.utils.AuthenticationUtil;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.lemon.api.Constants;
import com.lemon.api.RequestCase;
import com.lemon.api.RequestType;
import com.lemon.api.UnionCase;
import com.lemon.api.WbDatas;
import com.lemon.utils.ExcelUtils;
import com.lemon.utils.HttpUtil;
import com.lemon.utils.SqlUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.protocol.HTTP;
import org.apache.poi.sl.draw.binding.STRectAlignment;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

public class RegisterCase extends BaseCase{
	private Logger log =  Logger.getLogger(RequestCase.class);
	@BeforeSuite
	public void init() {
		//套件执初始化
		//配置参数化变量
		AuthenticationUtil.env.put(Constants.REGISTER_MOBILEPHONE_TEXT,Constants.REGISTER_MOBILEPHONE_VALUE);//注册用户名
		AuthenticationUtil.env.put(Constants.REGISTER_EMAIL_TEXT,Constants.REGISTER_EMAIL_VALUE);//注册邮箱
		AuthenticationUtil.env.put(Constants.REGISTER_PASSWORD_TEXT,Constants.REGISTER_PASSWORD_VALUE);//注册密码
		AuthenticationUtil.env.put(Constants.REGISTER_COMFIRM_TEXT,Constants.REGISTER_COMFIRM_VALUE);//注册确认码
		AuthenticationUtil.env.put(Constants.LOGIN_MOBILEPHONE_TEXT,Constants.LOGIN_MOBILEPHONE_VALUE);//登陆用户名
		AuthenticationUtil.env.put(Constants.LOGIN_PASSWORD_TEXT,Constants.LOGIN_PASSWORD_VALUE);//登陆密码
	}
		 
	  @Test(dataProvider = "datas",description = "注册接口")
	  public void Register(RequestType type,RequestCase casedata) {
		 log.info("=======================RegisterCase.test=======================");
		  String params = replaceParams(casedata.getParams());//替换params参数
		  casedata.setParams(params);//把替换后参数设置回去
		  log.info("=======================Register参数化后请求参数："+casedata.getParams());

		  String sqldata = replaceParams(casedata.getSqldata());//替换sql语句
		  casedata.setSqldata(sqldata);
		  //获取sql查询语句
		  String  sql = casedata.getSqldata();
		  log.info("==================================register参数化后的sql查询语句: "+sql);

		  String desiredata = replaceParams(casedata.getExperdata());//替换期望响应数据
		  casedata.setExperdata(desiredata);//把替换后的期望响应数据替换回去
		  log.info("=======================Register参数化后请求期望数据："+casedata.getExperdata());

		  //请求前查询数据库
		 // Object beforeAssert = SqlUtil.query(sql);

		  //http请求
		  String content = call(type,casedata);
		  addWriteBackData(casedata.getCaseId(),Constants.ACTUAL_RESPONSE_DATA_CELLNUM,content);//添加响应回写内容到list集合

		  boolean responseAssert = assertResponsedata(casedata, content);
		  log.info("========================register响应断言结果：" + responseAssert);
		  //请求后查询数据库
		//  Object afterAssert = SqlUtil.query(sql);

		 //数据库断言==》beforeAssert == 0 && afterAssert == 1
		/*  boolean sqlAssert = true;
		  if(StringUtils.isNoneBlank(sql)) {
			  sqlAssert  =assertSql(beforeAssert, afterAssert);
			  log.info("==========================register数据库断言结果：" + sqlAssert);
		  }*/
		 // String assertResult =(responseAssert && sqlAssert)? "Pass" : "Fail";
		  String assertResult =(responseAssert )? "Pass" : "Fail";
		  log.info("========================register最终断言结果：" + assertResult);
		  addWriteBackData(casedata.getCaseId(),Constants.ASSERT_CELLNUM,assertResult);//添加断言回写内容到list集合
		  Assert.assertEquals(assertResult,"Pass");//testng断言用于allure报表
	  }

	/**
	 *数据库断言比对
	 * @param beforeAssert   请求前查询数据库结果
	 * @param afterAssert    请求后查询数据库结果
	 * @return
	 */
	public static boolean assertSql(Object beforeAssert,Object afterAssert) {
		if((Long)beforeAssert == 0 && (Long)afterAssert ==1) {
			return true;
		}
		return false;
	}
		
	  @DataProvider
	  public Object[][] datas() {	
		  Object[][] objects = UnionCase.getAPIAndCaseByAPIId("1");	
		  return objects;
	  }
	  
	  @AfterSuite
	  public void writeback() {
		log.info("========================测试完成数据回写excel==========================");
		ExcelUtils.write(Constants.EXCEL_PATH, 1, ExcelUtils.actualdata);
	  }
}
