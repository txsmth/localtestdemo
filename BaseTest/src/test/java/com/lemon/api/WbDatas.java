package com.lemon.api;

public class WbDatas {
	private int rowNum;//行号
	private int cellNum;//列号
	private String content;//内容
	
	public WbDatas() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public WbDatas(int rowNum, int cellNum, String content) {
		super();
		this.rowNum = rowNum;
		this.cellNum = cellNum;
		this.content = content;
	}



	public int getCellNum() {
		return cellNum;
	}

	public void setCellNum(int cellNum) {
		this.cellNum = cellNum;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}



	@Override
	public String toString() {
		return "WbDatas [rowNum=" + rowNum + ", cellNum=" + cellNum + ", content=" + content + "]";
	}

	
	

}
