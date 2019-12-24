package com.lemon.api;

public class JsonPathValidate {
	private String value;
	private String expression;
	
	public JsonPathValidate() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JsonPathValidate(String value, String expression) {
		super();
		this.value = value;
		this.expression = expression;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	@Override
	public String toString() {
		return "JsonPathValidate [value=" + value + ", expression=" + expression + "]";
	}
	
	
}
