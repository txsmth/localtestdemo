package com.lemon.testcaes;

import com.alibaba.fastjson.JSONPath;
import com.lemon.api.Constants;
import com.lemon.utils.SqlUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;
import com.lemon.api.RequestCase;
import com.lemon.api.RequestType;
import com.lemon.api.UnionCase;
import com.lemon.utils.HttpUtil;

import org.apache.http.protocol.HTTP;
import org.apache.poi.sl.draw.binding.STRectAlignment;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import static com.lemon.testcaes.RegisterCase.assertSql;

public class ConfiguresCase extends BaseCase{
	private Logger log =  Logger.getLogger(ConfiguresCase.class);
	 
	  @Test(dataProvider = "datas",description = "配置列表接口")
	  public void configures(RequestType type,RequestCase casedata) {
		  log.info("=======================ConfiguresCase.test=======================");
		  String params = replaceParams(casedata.getParams());//替换params参数
		  casedata.setParams(params);//把替换后参数设置回去
		  log.info("=======================ConfiguresCase参数化后请求参数："+casedata.getParams());

		  String sqldata = replaceParams(casedata.getSqldata());//替换sql语句
		  casedata.setSqldata(sqldata);
		  //获取sql查询语句
		  String  sql = casedata.getSqldata();
		  log.info("==================ConfiguresCase参数化后的sql查询语句: "+sql);

		  String desiredata = replaceParams(casedata.getExperdata());//替换期望响应数据
		  casedata.setExperdata(desiredata);//把替换后的期望响应数据替换回去
		  log.info("===================ConfiguresCase参数化后请求期望数据："+casedata.getExperdata());

		  //Object beforesql = SqlUtil.query(sql);//请求前查询数据库
		  String content = call(type,casedata); //获取响应
		  addWriteBackData(casedata.getCaseId(), Constants.ACTUAL_RESPONSE_DATA_CELLNUM,content);//添加响应回写内容到list集合

		  boolean responseAssert = assertResponsedata(casedata, content);//断言响应数据
		 // System.out.println("assertResult: "+responseAssert);

		  //请求后查询数据库
		 /* Object aftersql = SqlUtil.query(sql);
		  //数据库断言==》afterAssert -beforeAssert=amount(jsonpath  $.amount)
		  String amountstr = JSONPath.read(casedata.getParams(), "$.amount").toString();
		  boolean sqlAssert = true;
		  if(StringUtils.isNoneBlank(sql)) {
//			afterSQLResult - beforeSQLResult == c.getParams().($.amount)
			  sqlAssert = assertSQL(beforesql, aftersql,amountstr);
			  System.out.println("数据库断言结果：" + sqlAssert);
		  }*/

		 // String assertResult =(responseAssert && sqlAssert)? "Pass" : "Fail";
		  String assertResult =(responseAssert)? "Pass" : "Fail";
		  log.info("========================ConfiguresCase最终断言结果：" + assertResult);
		  addWriteBackData(casedata.getCaseId(),Constants.ASSERT_CELLNUM,assertResult);//添加断言回写内容到list集合
		  Assert.assertEquals(assertResult,"Pass");//testng断言用于allure报表

	  }

	/*public static boolean assertSQL(Object beforeSQLResult, Object afterSQLResult, String c) {
		System.out.println("beforeSQLResult:" + beforeSQLResult.getClass());
	    if(beforeSQLResult !=null &&afterSQLResult != null){
			String beforeMoneyStr = beforeSQLResult.toString();
			String afterMoneyStr = afterSQLResult.toString();
			double beforeMoney = Double.parseDouble(beforeMoneyStr);
			double afterMoney = Double.parseDouble(afterMoneyStr);
			double money = Double.parseDouble(c);
			return afterMoney - beforeMoney == money;
		}
		return true;
	}*/

	  @DataProvider
	  public Object[][] datas() {	
		  Object[][] objects = UnionCase.getAPIAndCaseByAPIId("3");				
		  return objects;
			
	  }
}
