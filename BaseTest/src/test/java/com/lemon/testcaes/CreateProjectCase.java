package com.lemon.testcaes;

import com.lemon.api.Constants;
import com.lemon.api.RequestCase;
import com.lemon.api.RequestType;
import com.lemon.api.UnionCase;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CreateProjectCase extends BaseCase{
	private Logger log =  Logger.getLogger(CreateProjectCase.class);
	 
	@BeforeTest
	public void before() {
		System.out.println("=========================================");
	}
	
	  @Test(dataProvider = "datas",description = "项目列表接口")
	  public void createProject(RequestType type,RequestCase casedata) {
		  log.info("=======================CreateProjectCase.test=======================");
		  String params = replaceParams(casedata.getParams());//替换params参数
		  casedata.setParams(params);//把替换后参数设置回去
		  log.info("=======================CreateProjectCase参数化后请求参数："+casedata.getParams());

		  String sqldata = replaceParams(casedata.getSqldata());//替换sql语句
		  casedata.setSqldata(sqldata);
		  //获取sql查询语句
		  String  sql = casedata.getSqldata();
		  log.info("==================CreateProjectCase参数化后的sql查询语句: "+sql);

		  String desiredata = replaceParams(casedata.getExperdata());//替换期望响应数据
		  casedata.setExperdata(desiredata);//把替换后的期望响应数据替换回去
		  log.info("==================CreateProjectCase参数化后请求期望数据："+casedata.getExperdata());

		  String content = call(type,casedata); //获取响应
		  addWriteBackData(casedata.getCaseId(), Constants.ACTUAL_RESPONSE_DATA_CELLNUM,content);//添加响应回写内容到list集合

		  boolean responseAssert = assertResponsedata(casedata, content);//断言响应数据

		 // String assertResult =(responseAssert && sqlAssert)? "Pass" : "Fail";
		  String assertResult =(responseAssert)? "Pass" : "Fail";
		  log.info("========================CreateProjectCase最终断言结果：" + assertResult);
		  addWriteBackData(casedata.getCaseId(),Constants.ASSERT_CELLNUM,assertResult);//添加断言回写内容到list集合
		  Assert.assertEquals(assertResult,"Pass");//testng断言用于allure报表

	  }


	  @DataProvider
	  public Object[][] datas() {	
		  Object[][] objects = UnionCase.getAPIAndCaseByAPIId("5");
		  return objects;
			
	  }
}
