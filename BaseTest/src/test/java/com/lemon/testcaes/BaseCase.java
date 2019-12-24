package com.lemon.testcaes;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.lemon.api.Constants;
import com.lemon.api.JsonPathValidate;
import com.lemon.api.RequestCase;
import com.lemon.api.RequestType;
import com.lemon.api.WbDatas;
import com.lemon.utils.AuthenticationUtil;
import com.lemon.utils.ExcelUtils;
import com.lemon.utils.HttpUtil;
import io.qameta.allure.Step;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.SystemOutLogger;

/**
 * 测试用例基类
 * @author wurui
 */
public class BaseCase {

	/**
	 * 传入用例参数信息，返回http响应结果
	 * @param type  http请求方法
	 * @param casedata  http请求参数
	 * @return
	 */
	@Step("接口调用 {type.url}/{casedata.params}")
	public static String call(RequestType type,RequestCase casedata) {
		  String url= type.getUrl();
		  String method = type.getType();
		  String params = casedata.getParams();
		  String contenttype = type.getContenttype();
		  return HttpUtil.call(url, method, params, contenttype);	
	  }


	/**
	 * @param rowNum  行号
	 * @param cellNum 列号
	 * @param content 回写内容
	 */
	@Step("回写excel")
	public static void addWriteBackData(int rowNum,int cellNum,String content) {
		//  int rowNum = Integer.parseInt(casedata.getCaseId());
		  //int cellNum = Constants.ACTUAL_RESPONSE_DATA_CELLNUM;
		//  String content = BaseCase.call(type, casedata);
		  WbDatas wbd = new WbDatas(rowNum,cellNum,content);
		  ExcelUtils.actualdata.add(wbd);
		}

	/**
	 * 替换参数方法
	 * @param params  要替换的内容
	 * @return  替换后的内容
	 */
	@Step("参数化替换 {params}")
	public static String replaceParams(String params){
		if(StringUtils.isBlank(params)){
			return "";
		}

		for(String key : AuthenticationUtil.env.keySet()){
			params = params.replace(key,AuthenticationUtil.env.get(key) );
		//	System.out.println(AuthenticationUtil.env.toString());
		}

		return params;
	}


	/**
	 * 断言响应内容，是JSONObject直接equals判断，是JSONArray则采取多关键字匹配
	 * @param casedata  http请求参数用以获取期望响应
	 * @param content   实际响应内容
	 * @return
	 */
	@Step("断言接口响应内容 {content}")
	public static boolean assertResponsedata(RequestCase casedata,String content) {
		//获取期望响应数据
		String exepdata = casedata.getExperdata();
		//.parse方法转换期望结果
		Object parse = JSONObject.parse(exepdata);
		//判断parse对象类型是JSONObject还是JSONArray
		if(parse instanceof	JSONObject) {
			if(exepdata.equals(content)) {
				return true;
			}

		}else if(parse instanceof JSONArray) {		
			//期望数据转换成list集合
			List<JsonPathValidate> list = JSONObject.parseArray(exepdata, JsonPathValidate.class);
			
			for(JsonPathValidate json : list) {//循环list集合比对结果，都为真才返回真
				String exepctdata = json.getValue();//期望结果字段值
				Object actualdata = JSONPath.read(content, json.getExpression());//实际结果字段值  $.username
				boolean flag = exepctdata.equals(actualdata.toString());
				if(!flag) { //有假为假
					return false;
				}
				return true;
			}
		}
		return false;
	}
	

}
