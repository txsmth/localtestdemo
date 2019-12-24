package com.lemon.testcaes;

import com.lemon.utils.AuthenticationUtil;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;
import com.lemon.api.Constants;
import com.lemon.api.RequestCase;
import com.lemon.api.RequestType;
import com.lemon.api.UnionCase;
import com.lemon.api.WbDatas;
import com.lemon.utils.ExcelUtils;
import com.lemon.utils.HttpUtil;

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

public class LoginCase extends BaseCase{
	private Logger log =  Logger.getLogger(LoginCase.class);

	  @Test(dataProvider = "datas",description = "登陆接口")
	  public void login(RequestType type,RequestCase casedata) {
		  log.info("=======================LoginCase.test=======================");
		  String params = replaceParams(casedata.getParams());//替换params参数
		  casedata.setParams(params);//把替换后参数设置回去
		  log.info("=======================LoginCase参数化后请求参数："+casedata.getParams());

		  String sqldata = replaceParams(casedata.getSqldata());//替换sql语句
		  casedata.setSqldata(sqldata);
		  //获取sql查询语句
		  String  sql = casedata.getSqldata();
		  log.info("=====================LoginCase参数化后的sql查询语句: "+sql);

		  String desiredata = replaceParams(casedata.getExperdata());//替换期望响应数据
		  casedata.setExperdata(desiredata);//把替换后的期望响应数据替换回去
		  log.info("=======================LoginCase参数化后请求期望数据："+casedata.getExperdata());

		  String content = call(type,casedata);
		  addWriteBackData(casedata.getCaseId(), Constants.ACTUAL_RESPONSE_DATA_CELLNUM,content);//添加响应回写内容到list集合
		  boolean responseAssert = assertResponsedata(casedata, content);//响应比对
		//  System.out.println("assertResult: "+responseAssert);
		  String assertResult = (responseAssert) ? "Pass" : "Fail";
		  log.info("========================LoginCase终断言结果：" + assertResult);
		  //4、添加断言回写内容
		  addWriteBackData(casedata.getCaseId(),Constants.ASSERT_CELLNUM, assertResult);
		  Assert.assertEquals(assertResult,"Pass");//testng断言用于allure报表
	  }
		
	  @DataProvider
	  public Object[][] datas() {	
		  Object[][] objects = UnionCase.getAPIAndCaseByAPIId("2");	
		  return objects;
	  }

}
