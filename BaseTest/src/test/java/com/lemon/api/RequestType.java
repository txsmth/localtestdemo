package com.lemon.api;

import javax.validation.constraints.NotNull;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class RequestType {
	
	//RequestType和excel的映射关系（RequestType和excel列的关系）
	@Excel(name  = "接口编号")
	@NotNull   //注解用来去除excel的空行
	private String apiId;
		
	@Excel(name  = "接口名称")
	@NotNull   
	private String name;
	
	@Excel(name  = "接口提交方式")
	@NotNull   
	private String type;
	
	@Excel(name  = "接口地址")
	@NotNull   
	private String url;
	
	@Excel(name  = "参数类型")
	@NotNull 
	private String contenttype;

	public RequestType() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RequestType(String apiId, String name, String type, String url, String contenttype) {
		super();
		this.apiId = apiId;
		this.name = name;
		this.type = type;
		this.url = url;
		this.contenttype = contenttype;
	}

	public String getApiId() {
		return apiId;
	}

	public void setApiId(String apiId) {
		this.apiId = apiId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContenttype() {
		return contenttype;
	}

	public void setContenttype(String contenttype) {
		this.contenttype = contenttype;
	}

	@Override
	public String toString() {
		return "RequestType [apiId=" + apiId + ", name=" + name + ", type=" + type + ", url=" + url + ", contenttype="
				+ contenttype + "]";
	}
	
	
	
	
}
