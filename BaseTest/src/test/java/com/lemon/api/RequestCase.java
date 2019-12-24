package com.lemon.api;

import javax.validation.constraints.NotNull;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class RequestCase {
	
	@Excel(name = "用例编号")
	@NotNull 
	private int caseId;
	
	@Excel(name = "用例描述")
	@NotNull 
	private String description;
	
	@Excel(name = "参数")
	@NotNull 
	private String params;
	
	@Excel(name = "接口编号")
	@NotNull 
	private String apiId;
	
	@Excel(name = "期望响应数据")
	@NotNull 
	private String experdata;

	@Excel(name="检验SQL")
	private String sqldata;
	
	public RequestCase() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RequestCase(int caseId, String description, String params, String apiId, String experdata, String sqldata) {
		this.caseId = caseId;
		this.description = description;
		this.params = params;
		this.apiId = apiId;
		this.experdata = experdata;
		this.sqldata = sqldata;
	}

	public int getCaseId() {
		return caseId;
	}

	public void setCaseId(int caseId) {
		this.caseId = caseId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getApiId() {
		return apiId;
	}

	public void setApiId(String apiId) {
		this.apiId = apiId;
	}

	public String getExperdata() {
		return experdata;
	}

	public void setExperdata(String experdata) {
		this.experdata = experdata;
	}

	public String getSqldata() {
		return sqldata;
	}

	public void setSqldata(String sqldata) {
		this.sqldata = sqldata;
	}

	@Override
	public String toString() {
		return "RequestCase{" +
				"caseId=" + caseId +
				", description='" + description + '\'' +
				", params='" + params + '\'' +
				", apiId='" + apiId + '\'' +
				", experdata='" + experdata + '\'' +
				", sqldata='" + sqldata + '\'' +
				'}';
	}
}
