package com.yuchengtech.emp.ecif.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.yuchengtech.emp.ecif.base.common.GlobalConstants;

/**
 * <p>
 * Description: ��ݿ��ѯ�������
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * <p>
 * Create Date: 2009-2-19
 * </p>
 * <p>
 * Company: CITIC BANK
 * </p>
 * 
 * @author pengsenlin
 * @version $Id: ResultUtil.java,v 1.1 2009/07/14 03:23:22 pengsenlin Exp $
 */
@Service
@Transactional(readOnly = true)
public class ExcelReadWriteUtil{
	protected Logger log = LoggerFactory.getLogger(ExcelReadWriteUtil.class);
	String realpath = ((ServletRequestAttributes) RequestContextHolder
			.getRequestAttributes()).getRequest().getSession()
			.getServletContext().getRealPath("/");
	
	/**
	 * 读取Excel信息 
	 * @param sheetIndex 读取的sheet index
	 * @param readBeginRowIndex 开始读取行 index
	 * @param attribute 自己定义读取信息的属性名称
	 * @param filePath 被读取Excel的路径
	 * @return List<Map<String, String>>
	 * @throws Exception
	 */
	public List<Map<String, String>> readFromExcel(int sheetIndex, int readBeginRowIndex, String attribute, String filePath) throws Exception{
		File excelFile = null;  //  文件对象
		InputStream inputStream = null; // 输入流对象
		List<Map<String, String>> list = new ArrayList<Map<String, String>>(); // 返回封装数据的List
		try{
			excelFile = new File(filePath);
			inputStream = new FileInputStream(excelFile); // 获取文件输入流
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream); // 创建Excel对象
			XSSFSheet sheet = workbook.getSheetAt(sheetIndex);  // 获取第一个工作表，索引是0
			// 判断attributes是否存在
			
			if(attribute != null && attribute.length() != 0){ // 当自己定义了属性名时
				existAttrgetList(readBeginRowIndex, attribute, sheet, list);
			} else { // 当没有自定义属性名时
				notExistAttrgetList(readBeginRowIndex, sheet, list);
			}
		} catch (IOException e){
			log.error(e.getMessage());
		} finally {
			if(inputStream != null){
				try{
					inputStream.close();
				} catch (IOException e){
					log.error(e.getMessage());
				}
			}
		}
		return list;
	}
	
	/**
	 * 自定义了属性名称时按自定义属性返回结果集
	 * @param readBeginRowIndex 结果信息第一行index
	 * @param attribute 属性名称
	 * @param sheet XSSFSheet对象
	 * @param list 返回结果集
	 */
	public void existAttrgetList(int readBeginRowIndex, String attribute, XSSFSheet sheet, List<Map<String, String>> list){
		String cellStr = null; // 单元格，最终按字符处理
		String[] attributes = attribute.split(","); // 获取自定义属性名称
		// 开始循环遍历行, 表头不处理 从readBeginRowIndex开始
		for(int rowIndex = readBeginRowIndex; rowIndex < sheet.getLastRowNum(); rowIndex ++){
			Map<String, String> map = new HashMap<String, String>();
			XSSFRow row = sheet.getRow(rowIndex); // 获取行对象
			if(row == null){ // 如果为空，则不处理
				continue;
			}
			// 循环遍历单元格
			for(int cellIndex = 0; cellIndex < row.getLastCellNum(); cellIndex ++){
				XSSFCell cell = row.getCell(cellIndex); // 获取单元格对象
				if(cell == null){ // 如果为空，则设置 cellStr为空字符串
					cellStr = "";
				} else if(cell.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN){ // 对布尔值的处理
					cellStr = String.valueOf(cell.getBooleanCellValue());
				} else if(cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC){ // 对数字值的处理
					cellStr = cell.getNumericCellValue() + "";
				} else {
					cellStr = cell.getStringCellValue();
				}
				map.put(attributes[cellIndex], cellStr);
			}
			list.add(map);
		}
	}
	
	/**
	 * 没有自定义属性名称时获取表头做为属性名称并返回结果集
	 * @param readBeginRowIndex 结果集第一行index
	 * @param sheet XSSFSheet对象
	 * @param list 结果集
	 */
	public void notExistAttrgetList(int readBeginRowIndex, XSSFSheet sheet, List<Map<String, String>> list){
		String cellStr = null; // 单元格，最终按字符处理
		if(sheet.getLastRowNum() > 0 && sheet.getRow(readBeginRowIndex-1).getLastCellNum() > 0){
			String[] attributes = new String[sheet.getRow(readBeginRowIndex-1).getLastCellNum()+1];
			// 开始循环遍历行, 表头不处理 从readBeginRowIndex开始
			for(int rowIndex = readBeginRowIndex - 1; rowIndex < sheet.getLastRowNum(); rowIndex ++){
				Map<String, String> map = new HashMap<String, String>();
				XSSFRow row = sheet.getRow(rowIndex); // 获取行对象
				if(row == null){ // 如果为空，则不处理
					continue;
				}
				if(rowIndex == readBeginRowIndex - 1){ // 当为表头行时
					// 循环遍历表头行单元格，设置属性名称
					for(int cellIndex = 0; cellIndex < row.getLastCellNum(); cellIndex ++){
						XSSFCell cell = row.getCell(cellIndex); // 获取单元格对象
						if(cell == null){ // 如果为空，则设置 cellStr为空字符串
							attributes[cellIndex] = "CELLNAME"+(cellIndex + 1);
						} else {
							attributes[cellIndex] = cell.getStringCellValue();
						}
					}
				} else {
					// 循环遍历单元格
					for(int cellIndex = 0; cellIndex < row.getLastCellNum(); cellIndex ++){
						XSSFCell cell = row.getCell(cellIndex); // 获取单元格对象
						if(cell == null){ // 如果为空，则设置 cellStr为空字符串
							cellStr = "";
						} else if(cell.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN){ // 对布尔值的处理
							cellStr = String.valueOf(cell.getBooleanCellValue());
						} else if(cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC){ // 对数字值的处理
							cellStr = cell.getNumericCellValue() + "";
						} else {
							cellStr = cell.getStringCellValue();
						}
						map.put(attributes[cellIndex], cellStr);
					}
					list.add(map);
				}	
			}
		}
	}
	/**
	 * 获取Excel模板并通过List<Map<String, String>>并生成Workbook  开始写入行默认索引为1
	 * @param sheetIndex 写入sheet index
	 * @param writeBeginRowIndex 写入开始行 index
	 * @param list 写入信息List<Map<String, String>>
	 * @param filePath 读取的Excel模板文件路径
	 * @return Workbook 
	 */
	public Workbook getWorkbookByListMap(int sheetIndex, int writeBeginRowIndex, List<Map<String, String>> list, String filePath){
		Workbook workbook = getWorkbookByListMap(sheetIndex, writeBeginRowIndex, list, filePath, null);
		return workbook;
	}
	
	/**
	 * 获取Excel模板并通过List<Map<String, String>>并生成Workbook 获取sheet为默认第一页 开始写入行默认为第二行
	 * @param list 写入信息List<Map<String, String>>
	 * @param filePath 读取的Excel模板文件路径
	 * @param attributs 需要导出属性的名称
	 * @return Workbook 
	 */
	public Workbook getWorkbookByListMap(List<Map<String, String>> list, String filePath, String attributs){
		int sheetIndex = 0;
		int writeBeginRowIndex = 1;
		Workbook workbook = getWorkbookByListMap(sheetIndex, writeBeginRowIndex, list, filePath, attributs);
		return workbook;
	}
	
	/**
	 * 获取Excel模板并通过List<Map<String, String>>并生成Workbook 获取sheet为默认第一页 开始写入行默认为第二行 属性默认全导
	 * @param list 写入信息List<Map<String, String>>
	 * @param filePath 读取的Excel模板文件路径
	 * @return Workbook 
	 */
	public Workbook getWorkbookByListMap(List<Map<String, String>> list, String filePath){
		int sheetIndex = 0;
		int writeBeginRowIndex = 1;
		Workbook workbook = getWorkbookByListMap(sheetIndex, writeBeginRowIndex, list, filePath, null);
		return workbook;
	}
	/**
	 * 获取Excel模板并通过List<Map<String, String>>并生成Workbook
	 * @param sheetIndex 写入sheet index
	 * @param writeBeginRowIndex 写入开始行
	 * @param list 写入信息List<Map<String, String>>
	 * @param filePath 读取的Excel模板文件路径
	 * @return Workbook 
	 */
	public Workbook getWorkbookByListMap(int sheetIndex, int writeBeginRowIndex, List<Map<String, String>> list, String filePath, String attributs){
		if(list != null && list.size() > 0){
			File excelFile = null;  //  模板文件对象
			InputStream inputStream = null; // 输入流对象
			Workbook workbook = null;
			try{
				excelFile = new File(filePath);
				inputStream = new FileInputStream(excelFile); // 获取文件输入流
				workbook = new XSSFWorkbook(inputStream); // 创建Excel对象
				Sheet sheet = workbook.getSheetAt(sheetIndex);  // 获取第一个工作表，索引是0
				for(Map<String, String> map : list){
					Row row = sheet.getRow(writeBeginRowIndex);
					if(row == null){
						row = sheet.createRow(writeBeginRowIndex);
					}
					if(map != null){
						Set<Entry<String, String>> mapEntries = map.entrySet();
						Iterator<Entry<String, String>> mapIterator = mapEntries.iterator();
						int columnIndex = 0;
						while(mapIterator.hasNext()){
							Map.Entry<String, String> mapEntry = (Entry<String, String>) mapIterator.next();
							String value = mapEntry.getValue();
							Cell cell = row.getCell(columnIndex);
							if(cell == null){
								cell = row.createCell(columnIndex);
							}
							if(value == null || value.length() == 0){
								cell.setCellValue("");
							}else{
								cell.setCellValue(value);
							}
							columnIndex ++;
						}
					}
					writeBeginRowIndex ++;
				}
			} catch (IOException e){
				log.error(e.getMessage());
			} finally {
				if(inputStream != null){
					try{
						inputStream.close();
					} catch (IOException e){
						log.error(e.getMessage());
					}
				}
			}
			return workbook;
		}else{
			return null;
		}
	}
	
	/**
	 * 获取Excel模板并通过List<Object[]>并生成Workbook  开始写入行默认索引为1
	 * @param sheetIndex 写入sheet index
	 * @param writeBeginRowIndex 写入开始行 index
	 * @param list 写入信息List<Map<String, String>>
	 * @param filePath 读取的Excel模板文件路径
	 * @return Workbook 
	 */
	public Workbook getWorkbookByListObjectReport(int sheetIndex, int writeBeginRowIndex, List<Object[]> list, String filePath, String rptDate){
		Workbook workbook = getWorkbookByListObjectReport(sheetIndex, writeBeginRowIndex, list, filePath, null, rptDate);
		return workbook;
	}
	
	/**
	 * 获取Excel模板并通过List<Object[]>并生成Workbook 获取sheet为默认第一页 开始写入行默认为第二行
	 * @param list 写入信息List<Map<String, String>>
	 * @param filePath 读取的Excel模板文件路径
	 * @param attributs 需要导出属性的名称
	 * @return Workbook 
	 */
	public Workbook getWorkbookByListObject(List<Object[]> list, String filePath, String attrIndexStr){
		int sheetIndex = 0;
		int writeBeginRowIndex = 1;
		Workbook workbook = getWorkbookByListObject(sheetIndex, writeBeginRowIndex, list, filePath, attrIndexStr);
		return workbook;
	}
	
	/**
	 * 获取Excel模板并通过List<Object[]>并生成Workbook 获取sheet为默认第一页 开始写入行默认为第二行 属性默认全导
	 * @param list 写入信息List<Map<String, String>>
	 * @param filePath 读取的Excel模板文件路径
	 * @return Workbook 
	 */
	public Workbook getWorkbookByListObject(List<Object[]> list, String filePath){
		int sheetIndex = 0;
		int writeBeginRowIndex = 1;
		Workbook workbook = getWorkbookByListObject(sheetIndex, writeBeginRowIndex, list, filePath, null);
		return workbook;
	}
	
	/**
	 * 获取Excel模板并通过List<Object[]>并生成Workbook
	 * @param sheetIndex 写入sheet index
	 * @param writeBeginRowIndex 写入开始行 index
	 * @param list 写入信息List<Map<String, String>>
	 * @param filePath 读取的Excel模板文件路径
	 * @param attrIndexStr 写入列信息在Object[] 中的index字符串，多个以"|"隔开 例 |0|2|4|5|
	 * @return Workbook 
	 */
	public Workbook getWorkbookByListObject(int sheetIndex, int writeBeginRowIndex, List<Object[]> list, String filePath, String attrIndexStr){
		if(list != null && list.size() > 0){
			File excelFile = null;  //  模板文件对象
			InputStream inputStream = null; // 输入流对象
			Workbook workbook = null;
			try{
				excelFile = new File(filePath);
				inputStream = new FileInputStream(excelFile); // 获取文件输入流
				workbook = new XSSFWorkbook(inputStream); // 创建Excel对象
				Sheet sheet = workbook.getSheetAt(sheetIndex);  // 获取第一个工作表，索引是0
				//
				for(Object[] objs : list){
					Row row = sheet.getRow(writeBeginRowIndex);
					if(row == null){
						row = sheet.createRow(writeBeginRowIndex);
					}
					int columnIndex = 0;
					int objIndex = 0;
					for(Object obj : objs){
						if(attrIndexStr != null && attrIndexStr.length() > 0){
							String indexStr = "|"+objIndex+"|";
							if(attrIndexStr.indexOf(indexStr) != -1){
								Cell cell = row.getCell(columnIndex);
								if(cell == null){
									cell = row.createCell(columnIndex);
								}
								cell.setCellValue(obj == null ? "" : obj + "");
								columnIndex ++;
							}
							objIndex ++;
						} else {
							Cell cell = row.getCell(columnIndex);
							if(cell == null){
								cell = row.createCell(columnIndex);
							}
							cell.setCellValue(obj == null ? "" : obj + "");
							columnIndex ++;
						}
					}
					writeBeginRowIndex ++;
				}
			} catch (IOException e){
				log.error(e.getMessage());
			} finally {
				if(inputStream != null){
					try{
						inputStream.close();
					} catch (IOException e){
						log.error(e.getMessage());
					}
				}
			}
			return workbook;
		}else{
			return null;
		}
	}
	public Workbook getWorkbookByListObjectReport(int sheetIndex, int writeBeginRowIndex, List<Object[]> list, String filePath, String attrIndexStr, String rptDate){
		if(list != null && list.size() > 0){
			File excelFile = null;  //  模板文件对象
			InputStream inputStream = null; // 输入流对象
			Workbook workbook = null;
			try{
				excelFile = new File(filePath);
				inputStream = new FileInputStream(excelFile); // 获取文件输入流
				workbook = new XSSFWorkbook(inputStream); // 创建Excel对象
				Sheet sheet = workbook.getSheetAt(sheetIndex);  // 获取第一个工作表，索引是0
				//
				Row firstRow = sheet.getRow(0);
				Cell firstCell = firstRow.getCell(0);
				String title = firstCell.getStringCellValue();
				title = title + " - " + rptDate;
				firstCell.setCellValue(title);
				
				for(Object[] objs : list){
					Row row = sheet.getRow(writeBeginRowIndex);
					if(row == null){
						row = sheet.createRow(writeBeginRowIndex);
					}
					int columnIndex = 0;
					int objIndex = 0;
					for(Object obj : objs){
						if(attrIndexStr != null && attrIndexStr.length() > 0){
							String indexStr = "|"+objIndex+"|";
							if(attrIndexStr.indexOf(indexStr) != -1){
								Cell cell = row.getCell(columnIndex);
								if(cell == null){
									cell = row.createCell(columnIndex);
								}
								cell.setCellValue(obj == null ? "" : obj + "");
								columnIndex ++;
							}
							objIndex ++;
						} else {
							Cell cell = row.getCell(columnIndex);
							if(cell == null){
								cell = row.createCell(columnIndex);
							}
							cell.setCellValue(obj == null ? "" : obj + "");
							columnIndex ++;
						}
					}
					writeBeginRowIndex ++;
				}
			} catch (IOException e){
				log.error(e.getMessage());
			} finally {
				if(inputStream != null){
					try{
						inputStream.close();
					} catch (IOException e){
						log.error(e.getMessage());
					}
				}
			}
			return workbook;
		}else{
			return null;
		}
	}
	
	/**
	 * 导出生成文件
	 * @param outputFileName 输出文件名
	 * @param realpath 系统对应路径
	 * @param outputFilePath 输出文件路径
	 * @param workbook 
	 * @return String 导出文件名
	 */
	public void outputStreamExcel(String outputFilePathName, Workbook workbook) throws Exception{
		if(workbook == null){
			throw new Exception("Workbook是null!");
		}
		//
		if(StringUtils.isEmpty(outputFilePathName)){
			throw new Exception("文件路径全称是空!");
		}
		try {
			FileOutputStream out = new FileOutputStream(outputFilePathName); // 获取文件输出流
			workbook.write(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	/**
	 * 构建excel全名
	 * @param fileName 文件名称
	 * @param path 文件路径
	 * @return 文件全称
	 */
	public String getFilePathName(String fileName, String path) {
		if(StringUtils.isEmpty(fileName)){
			fileName = "export_"+new Date().getTime()+".xlsx";
		} else {
			if(fileName.indexOf(".xlsx") == -1 && fileName.indexOf(".xls") == -1){
				fileName = fileName + "_" + new Date().getTime()+".xlsx";
			}
		}
		if(StringUtils.isEmpty(path)){
			path = GlobalConstants.EXCEL_REPORT_FOLDER;
			File file = new File(realpath + path);

			if (!file.isDirectory()) {
				file.mkdir();
			}
		}
		return realpath + path + File.separator + fileName;
	}
}
