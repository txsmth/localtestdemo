package com.lemon.api;

import java.util.ArrayList;
import java.util.List;

import com.lemon.utils.ExcelUtils;

public class UnionCase {
	
	/*public static void main(String[] args) {
		Object[][] obj = UnionCase.getAPIAndCaseByAPIId("1");
		for(Object[] a : obj) {
			for(Object b : a) {
				System.out.println("数据是： "+ b);
			}
		}
	}*/
	
	public static List<RequestType> listtype = ExcelUtils.read(Constants.EXCEL_PATH,RequestType.class,0);//获取请求type页数据
	public static List<RequestCase> listcase = ExcelUtils.read(Constants.EXCEL_PATH,RequestCase.class,1);//获取请求case页数据
	/**
	 * @param apiId 接口的关联id
	 *
	 */
	public static Object[][] getAPIAndCaseByAPIId(String apiId) {
	
		Object[][] datas = null ;
	    //1、获取wantAPI
		RequestType wantAPI = null;
		for(RequestType a : listtype) {
			if(a.getApiId().equalsIgnoreCase(apiId)) {
				wantAPI = a;
			}
		}
	    //2、获取wantCases
		ArrayList<RequestCase> wantCases = new ArrayList<RequestCase>();
		for(RequestCase b : listcase) {
			if(b.getApiId().equalsIgnoreCase(apiId)) {
				wantCases.add(b);
			}
		}		
	    //3、把wantAPI和wantCases合并。				
	    //4、把wantAPI和wantCase存入到二维数组中。
		datas =  new Object[wantCases.size()][2];
		for(int i = 0; i< datas.length;i++) {
			datas[i][0] = wantAPI;
			datas[i][1] = wantCases.get(i);
		}
		return datas;
	}

}
