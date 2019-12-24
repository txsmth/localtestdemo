package com.lemon.utils;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.lemon.api.WbDatas;

import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.entity.ImportParams;

public class ExcelUtils {
	
	public static List<WbDatas> actualdata = new ArrayList<WbDatas>();
	/*
	 * @param path  excel文件路径
	 * @param clazz    Class文件
	 * @sheetNum    excel中sheet页码
	 */
	public static<T> List<T>  read(String path ,Class clazz,int sheetNum)  {
		FileInputStream fis=null;
		 List<T> list = null;
		try {
		 fis= new FileInputStream(path);
			 ImportParams params  = new ImportParams();
			 params.setStartSheetIndex(sheetNum);
			 params.setNeedVerify(true);//校验sheet页中的空行，和注解中的notNull配合
			 list=  ExcelImportUtil.importExcel(fis, clazz, params);
			// System.out.println("输出数据是： "+list);
			
		} catch (Exception e) {
	
			e.printStackTrace();
		}finally {
			close(fis);
		}
		return list;
	}
	
	
	public static void write(String filepath,int sheetNum ,List<WbDatas> list) {
		FileInputStream fis =null;
		FileOutputStream fos = null;
			try {
				//1、输入流
				fis = new FileInputStream(filepath);
				//2、工作簿，兼容03和07excel
				Workbook workbook = WorkbookFactory.create(fis);
				//2、获取sheet页
				org.apache.poi.ss.usermodel.Sheet sheet =   workbook.getSheetAt(sheetNum);
				if(sheet != null) {				
					for(WbDatas data: list) {
						//行
						Row row = sheet.getRow(data.getRowNum());
						//3、设置空策略,列
						Cell cell =row.getCell(data.getCellNum(), MissingCellPolicy.CREATE_NULL_AS_BLANK);
						//4、设置单元格数据类型
						cell.setCellType(CellType.STRING);
						cell.setCellValue(data.getContent());//设置值
					}					
			}
			fos = new FileOutputStream(filepath);
			workbook.write(fos);
					
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				close(fis);
				close(fos);		
			}
		}
	private static void close(Closeable stream) {
		if(stream != null) {
			try {
				stream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
}
