/*******************************************************************************
 * $Header$
 * $Revision$
 * $Date$
 *
 *==============================================================================
 *
 * Copyright (c) 2010 CITIC Holdings All rights reserved.
 * 
 * Created on 2012-11-30
 *******************************************************************************/


package com.yuchengtech.emp.ecif.base.util;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.ecif.customer.customization.service.OrgCustomizationBS;

@Service
@Transactional(readOnly = true)
public class DownloadFile {

	protected static Logger log = LoggerFactory
			.getLogger(OrgCustomizationBS.class);
	
	private HSSFWorkbook wb=null;  //下载方法生产的worbook的总文件
	private HSSFSheet sheet = null;  //用来定义当前的sheet对象
	
	@Autowired
	private CodeUtil codeUtil;
	/**
	 * 生成的文件属性变量，包括文件名称等等
	 */
	private String promptMsg = "中信银行公司电子金融管理平台";   //作为文件生成之后赋予每页sheet中的说明文字
	private String sheetName ;   //当前生产的sheet页的sheet名称
	private String filePath = "C:/download";
	private String fileName = "newworkbook.xls";
	/**
	 * 加载类型变量，为了存储工作表格记录信息，例如业务字典等
	 */
	private Map<String,HSSFSheet> sheetMap = new HashMap<String, HSSFSheet>();
	private Map<String,Map<String,String>> dctionaryMap = new HashMap<String, Map<String,String>>();
	private ArrayList<String[][]> extraLeaderRow = new ArrayList<String[][]>();
	private Integer[] formatArray;	
	private String[][] tableReader;	
	private String[][] tableArray;	
	private Map<String,Integer> keyMap = new LinkedHashMap<String, Integer>();		
	private String propertyRead = "|";
	private String dctionaryType = "";	
	/**
	 * poi状态变量，此类变量时poi工具类中的码值
	 */
	private short COLOR_FOR_COLUMNNAME = HSSFColor.BLACK.index;
	private short ALGIN_FOR_COLUMNNAME = HSSFCellStyle.ALIGN_CENTER_SELECTION;
	private short BACKGROUND_FOR_COLUMNNAME = HSSFColor.AQUA.index;
	private short BORDER_FOR_COLUMNNAME = HSSFCellStyle.BORDER_THIN;
	private short COLOR_FOR_CELL = HSSFColor.BLACK.index;
	private short ALGIN_FOR_CELL = HSSFCellStyle.ALIGN_CENTER_SELECTION;
	private short BACKGROUND_FOR_CELL = HSSFColor.WHITE.index;
	private short BORDER_FOR_CELL = HSSFCellStyle.BORDER_THIN;	
	private short ALGIN_FOR_CHANGE = HSSFCellStyle.ALIGN_CENTER_SELECTION;	
	/**
	 * 计数类变量，用于调整表格的布局而暂时存在的变量
	 */
	private int sheetNum = 1;
	private int acquiesceFreezeStart = 0;
	private int acquiesceFreezeEnd = 0;		
	private int tableWidth = 0;
	private int frontRow = 0;	
	private int currentRow = 0;
	private int tableReaderType = 0;	
	private String fileStyle = "normal";  //下载文件模式判断字符窜
	private int HIGHT_FOR_COLUMNNAME = 13; 	
	private int HIGHT_FOR_CELL = 13; 	
	private int WIDTH_FOR_COLUMN = 10; 	
	private int NUMBER_FOR_COLUMN = 14; 	
	private boolean FORMAT_FLAG = false; 	
	private boolean TABLEREADER_FLAG = false;	
	private boolean TABLEREADER_SET_FLAG = false;		
	private boolean TABLEARRAY_FLAG = false;	
	private boolean AUTO_NULL_FLAG = false;	
	private boolean TREEREADER_FLAG = false;	
	
	
	
	/**
	 * 构造函数方法
	 */
	public DownloadFile(){
		wb=new HSSFWorkbook();
	}
	/**
	 * 获取文件选型的方法
	 * @return 文件选型normal是常规选型--多列列表式
	 */
	public String getFileStyle() throws Exception{
		return fileStyle;
	}
	/**
	 * 设置文件选型的方法 目前支持的类型有"normal--一般类型"
	 * @param fileStyle 文件类型 目前支持的类型有"normal--一般类型"
	 */
	public void setFileStyle(String fileStyle) throws Exception{
		this.fileStyle = fileStyle;
	}	
	/**
	 * 获取生产的工作表格中的表格描述信息
	 * @return 返回的是设置好的工作表描述信息
	 */
	public String getPromptMsg() throws Exception{
		return promptMsg;
	}
	/**
	 * 设置工作表格的表格描述信息，支持中文和英文
	 * @param promptMsg String 只支持String类型的数据 ，*请不要输入反动信息*
	 */
	public void setPromptMsg(String promptMsg) throws Exception{
		this.promptMsg = promptMsg;
	}		
	/**
	 * 用来获取文档的生成路径，暂时不开放
	 * @return 文档路径
	 */
	public String getFilePath() throws Exception{
		return filePath;
	}
	/**
	 * 用来设置文档生成路径，为3.0版本制作可以读取模板方式下载工作准备，暂不开放
	 * @param filePath 合法的文档生成地址
	 */
	public void setFilePath(String filePath) throws Exception{
		this.filePath = filePath;
	}	
	/**
	 * 获取工作表格的生成名称
	 * @return 表格文件名称
	 */
	public String getFileName() throws Exception{
		return fileName;
	}
	/**
	 * 用来设置表格生成的文件名称，也是表格在下载是的默认名称，支持中文
	 * @param fileName 表格文件名称 支持中文
	 */
	public void setFileName(String fileName) throws Exception{
		this.fileName = fileName;
	}	
	/**
	 * 获取设置工作表格表头的背景色
	 * @return short类型的码值 具体的对应关系可以查询org.apache.poi.hssf.util.HSSFColor;
	 */
	public short getColumnNameGroundColor() throws Exception{
		return BACKGROUND_FOR_COLUMNNAME;
	}
	/**
	 * 设置工作表格表头一行的背景颜色
	 * @param color short类型的码值 建议使用org.apache.poi.hssf.util.HSSFColor中的对应颜色
	 * 				 标准书写方式为（HSSFColor.BLACK.index）其中的BLACK可以挑选自己喜欢的颜色
	 */
	public void setColumnNameGroundColor(short color) throws Exception{
		this.BACKGROUND_FOR_COLUMNNAME = color;
	}	
	/**
	 * 获取表格表头单元格的对齐方式
	 * @return 表头单元格的对齐方式，具体的对应关系可以查询org.apache.poi.hssf.usermodel.HSSFCellStyle
	 */
	public short getColumnNameAlign() throws Exception{
		return ALGIN_FOR_COLUMNNAME;
	}
	/**
	 * 设置表头单元格的对齐方式
	 * @param align short类型的码值 取值参考org.apache.poi.hssf.usermodel.HSSFCellStyle
	 *               建议的填写方式为（HSSFCellStyle.ALIGN_CENTER_SELECTION0)
	 */
	public void setColumnNameAlign(short align) throws Exception{
		this.ALGIN_FOR_COLUMNNAME = align;
	}		
	/**
	 * 获取设置工作表格正文单元格的背景色
	 * @return short类型的码值 具体的对应关系可以查询org.apache.poi.hssf.util.HSSFColor;
	 */	
	public short getCellGroundColor() throws Exception{
		return BACKGROUND_FOR_CELL;
	}
	/**
	 * 设置工作表格正文单元格的背景颜色
	 * @param color short类型的码值 建议使用org.apache.poi.hssf.util.HSSFColor中的对应颜色
	 * 				 标准书写方式为（HSSFColor.BLACK.index）其中的BLACK可以挑选自己喜欢的颜色
	 */	
	public void setCellGroundColor(short color) throws Exception{
		this.BACKGROUND_FOR_CELL = color;
	}		
	/**
	 * 获取表格正文单元格的对齐方式
	 * @return 正文单元格的对齐方式，具体的对应关系可以查询org.apache.poi.hssf.usermodel.HSSFCellStyle
	 */	
	public short getCellAlign() throws Exception{
		return ALGIN_FOR_CELL;
	}
	/**
	 * 设置正文单元格的对齐方式
	 * @param align short类型的码值 取值参考org.apache.poi.hssf.usermodel.HSSFCellStyle
	 *               建议的填写方式为（HSSFCellStyle.ALIGN_CENTER_SELECTION0)
	 */	
	public void setCellAlign(short align) throws Exception{
		this.ALGIN_FOR_CELL = align;
	}		
	/**
	 * 获取格式化之后的列宽
	 * @param location 获取第几列的列宽
	 * @return 列宽值
	 */
	public int getColumnFormat(int location) throws Exception{
		if ( null == formatArray || location>formatArray.length) {
			return WIDTH_FOR_COLUMN*NUMBER_FOR_COLUMN/tableWidth+1;
		}
		return formatArray[location];
	}
	/**
	 * 设置工作表表格的列宽
	 * @param formatArray 代表列宽的2维数组 {1,2,3}代表第一列一个宽度，第二列2个宽度，第三列3个宽度
	 */
	public void setColumnFormat(Integer[] formatArray) throws Exception{
		FORMAT_FLAG = true;
		this.formatArray = formatArray;
	}	
	/**
	 * 通过单独设置的
	 * @param location 要设置宽度的列的序列号
	 * @param value 列宽值 支持int类型
	 */
	public void setColumnFormat(int location,int value) throws Exception{
		FORMAT_FLAG = true;		
		if (null != formatArray && location>formatArray.length) {
			Integer[] changeArray = new Integer[location+1];
			for (int i=0;i<location+1;i++) {
				changeArray[i] = 0;
			}			
			for (int i=0;i<formatArray.length;i++) {
				changeArray[i] = formatArray[i];
			}			
			this.formatArray = changeArray;
			this.formatArray[location] = value;			
		}else if (null != formatArray && location<formatArray.length) {
			this.formatArray[location] = value;	
		}else{
			Integer[] changeArray = new Integer[location+1];
			for (int i=0;i<location+1;i++) {
				changeArray[i] = 0;
			}
			this.formatArray = changeArray;
			this.formatArray[location] = value;		
		}
	}	
	/**
	 * 设置表格描述信息
	 * @param tableReader 一个2维数组 数组中的信息将被作为表格描述信息展示到工作表冻结行上面
	 *                    例如{[名称,测试表格],[制作时间,2012-01-02]}这样的表格描述就会在表头行上
	 *                    添加两行，分别描述为（名称:测试表格)和(制作时间:2012-01-02)
	 */
	public void setTableReader(String[][] tableReader) throws Exception{
		if ( null!=tableReader && 0!=tableReader.length) {
			TABLEREADER_FLAG = true;
			this.tableReader = tableReader;	
		}
	}	
	/**
	 * 方法用于获取工作表格描述数组
	 * @param location 表格描述数组的位置
	 * @return 描述信息
	 */
	public String[] getTableReader(int location) throws Exception{
		return tableReader[location];
	}	
	/**
	 * 设置表头行的文字描述信息，如果不设置将去实体的属性作为表头的列名
	 * @param tableReader 设置表头的列名，2维数组的第一位是实体的属性名称，第二位是列名
	 */
	public void setTableArray(String[][] tableArray) throws Exception{
		if ( null!=tableArray && 0!=tableArray.length) {
			TABLEARRAY_FLAG = true;
			tableWidth = tableArray.length;			
			this.tableArray = tableArray;	
		}
	}	
	/**
	 * 获取工作表格的列名
	 * @param keyName 字段属性名
	 * @return 列名
	 */
	public String getTableArray(String keyName) throws Exception{
		return tableArray[(keyMap.get(keyName))][1];
	}	
	/**
	 * 设置表头树状说明单元行
	 * @param extraRow 2维数组的第一位需要和并的列，第二位合并之后的列名
	 * @throws Exception
	 */
	public void addExtraLeaderRow(String[][] extraRow) throws Exception{
		if ( null!=extraRow && 0!=extraRow.length) {
			TREEREADER_FLAG = true;		
			this.extraLeaderRow.add(extraRow);			
		}
	}		
	/**
	 * 设置是否自动格式化
	 * @param autoNullFlag
	 */
	public void setAutoNull(boolean autoNullFlag) throws Exception{
		this.AUTO_NULL_FLAG = autoNullFlag;
	}	
	/**
	 * 获取生产的工作表格文件
	 * @return 工作表格文件
	 */
	public HSSFWorkbook getWorkTable() throws Exception{
		return wb;
	}		
	public void loadDctionary(String property, String dictTypeId) throws Exception{
		List<Map<String, String>> list = this.codeUtil.getComBoBox(dictTypeId);
		int length = list.size();
		Map<String,String> innerMap = new HashMap<String, String>();
		for (int i=0;i<length;i++) {
			String Dctionaryid = (String)list.get(i).get("id");;
			String Dctionaryname = (String)list.get(i).get("text");;
			innerMap.put(Dctionaryid, Dctionaryname);			
		}
		dctionaryType = dctionaryType+property.toUpperCase()+"#";		
		this.dctionaryMap.put(property.toUpperCase(), innerMap);
	}	
	/**
	 * 生产工作表格的入口方法
	 * @param tableNameBean 编辑了表头信息的实体类
	 * @param entityBeans 存储了工作表数据信息的实体类的数组
	 * @throws Exception 捕获子方法中抛出的所以异常向上抛出
	 */
	public void produce(Object tableNameBean, Object[] entityBeans) throws Exception{	
		checkParameter();
		if ( "normal".equals(fileStyle) ) {
			produceNormalXls(entityBeans);
			formatTableWidth();			
		}
	}	
	
	/**
	 * 生产工作表格的入口方法
	 * @param tableArray 编辑了表头信息的2维数组
	 * @param entityBeans 存储了工作表数据信息的实体类的数组
	 * @throws Exception 捕获子方法中抛出的所以异常向上抛出
	 */
	public void produce(Object[] entityBeans) throws Exception{		
		checkParameter();
		if ( "normal".equals(fileStyle) ) {			
			produceNormalXls(entityBeans);
			formatTableWidth();				
		}	
	}	
		
	/**
	 * 生产工作表格的入口方法
	 * @param tableArray 编辑了表头信息的2维数组
	 * @param entityBeans 存储了工作表数据信息的实体类的数组
	 * @throws Exception 捕获子方法中抛出的所以异常向上抛出
	 */
	public void produce(List<Map<String, String>> listMaps) throws Exception{		
		checkParameter();
		if ( "normal".equals(fileStyle) ) {			
			produceNormalXls(listMaps);
			formatTableWidth();				
		}	
	}	
	
	/**
	 * 生产工作表格的入口方法
	 * @param tableNameBean 编辑了表头信息的实体类
	 * @param entityBeans 存储了工作表数据信息的实体类的数组
	 * @throws Exception 捕获子方法中抛出的所以异常向上抛出
	 */
	public void init() throws Exception{
		fileStyle = "normal";  //下载文件模式判断字符窜
		promptMsg = "这个是默认的提示信息";   //作为文件生成之后赋予每页sheet中的说明文字
		sheetName = "newSheet" ;   //当前生产的sheet页的sheet名称
		filePath = "C:/download";
		fileName = "newworkbook.xls";
		sheetNum = 1;
		acquiesceFreezeStart = 0;
		acquiesceFreezeEnd = 0;	
		propertyRead  = "";		
	}	
	
	/**
	 * 制表结构格式调整，如果使用者没有调整过表单长度，则系统自动调整
	 * @throws Exception 在调整表单过程中抛出的异常
	 */
	private void formatTableWidth() throws Exception{
		if ( 0 != tableWidth ) {			
			if ( !FORMAT_FLAG ) {
				for ( int i=0 ; i<tableWidth ; i++) {
					sheet.setColumnWidth(i, 288*WIDTH_FOR_COLUMN*NUMBER_FOR_COLUMN/tableWidth);
				}
			}else{
				for ( int i=0 ; i<formatArray.length ; i++) {
					if (0 != formatArray[i]) {
						sheet.setColumnWidth(i, formatArray[i]*288);				
					}
				}		
			}
		}
	}	
	
	/**
	 * 编辑工作表中具体的一个单元格的字方法
	 * @param row 需要编辑的行
	 * @param col 需要编辑的列
	 * @param val 编辑选中的单元格中的值
	 * @throws Exception 填写单元格信息的过程中抛出的异常
	 */
	private void cteateCell(HSSFRow row,int col,String val,String dataType) throws Exception{
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(val);
		HSSFCellStyle cellStyle = wb.createCellStyle();
		if ( null == dataType || "".equals(dataType) || "STRING".equals(dataType) ) {
			cellStyle.setFillForegroundColor(BACKGROUND_FOR_CELL);
			cellStyle.setFillPattern(cellStyle.SOLID_FOREGROUND);	
			cellStyle.setAlignment(ALGIN_FOR_CELL);
			cellStyle.setBorderBottom(BORDER_FOR_CELL);
			cellStyle.setBorderLeft(BORDER_FOR_CELL);
			cellStyle.setBorderRight(BORDER_FOR_CELL);
			cellStyle.setBorderTop(BORDER_FOR_CELL);
			cellStyle.setWrapText(true);
		}else if ("DATE".equals(dataType)) {
			cellStyle.setFillForegroundColor(BACKGROUND_FOR_CELL);	
			cellStyle.setFillPattern(cellStyle.SOLID_FOREGROUND);			
			cellStyle.setAlignment(ALGIN_FOR_CELL);
			cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
			cellStyle.setBorderBottom(BORDER_FOR_CELL);
			cellStyle.setBorderLeft(BORDER_FOR_CELL);
			cellStyle.setBorderRight(BORDER_FOR_CELL);
			cellStyle.setBorderTop(BORDER_FOR_CELL);	
			cellStyle.setWrapText(true);		
		}else if ("BIGDECIMAL".equals(dataType)) {
			cellStyle.setFillForegroundColor(BACKGROUND_FOR_CELL);	
			cellStyle.setFillPattern(cellStyle.SOLID_FOREGROUND);			
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
			cellStyle.setBorderBottom(BORDER_FOR_CELL);
			cellStyle.setBorderLeft(BORDER_FOR_CELL);
			cellStyle.setBorderRight(BORDER_FOR_CELL);
			cellStyle.setBorderTop(BORDER_FOR_CELL);	
			cellStyle.setWrapText(true);		
		}
		cell.setCellStyle(cellStyle);
	}	
	
	/**
	 * 编辑工作表中具体的一个单元格的字方法
	 * @param row 需要编辑的行
	 * @param col 需要编辑的列
	 * @param val 编辑选中的单元格中的值
	 * @throws Exception 填写单元格信息的过程中抛出的异常
	 */
	private void cteateReaderCell(HSSFRow row,int col,String val,String dataType) throws Exception{
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(val);
		HSSFCellStyle cellStyle = wb.createCellStyle();
		if ( null == dataType || "".equals(dataType) || "STRING".equals(dataType) ) {	
			cellStyle.setAlignment(ALGIN_FOR_CHANGE);
		}else if ("DATE".equals(dataType)) {	
			cellStyle.setAlignment(ALGIN_FOR_CHANGE);
			cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));		
		}
		cell.setCellStyle(cellStyle);
	}	
		
	/**
	 * 编辑工作表中具体的一个表头单元格的字方法
	 * @param row 需要编辑的行
	 * @param col 需要编辑的列
	 * @param val 编辑选中的单元格中的值
	 * @throws Exception 填写单元格信息的过程中抛出的异常
	 */
	private void cteateCell(HSSFRow row,int col,String val) throws Exception{
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(val);
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFillForegroundColor(BACKGROUND_FOR_COLUMNNAME);
		cellStyle.setFillPattern(cellStyle.SOLID_FOREGROUND);		
		cellStyle.setAlignment(ALGIN_FOR_COLUMNNAME);
		cellStyle.setBorderBottom(BORDER_FOR_COLUMNNAME);
		cellStyle.setBorderLeft(BORDER_FOR_COLUMNNAME);
		cellStyle.setBorderRight(BORDER_FOR_COLUMNNAME);
		cellStyle.setBorderTop(BORDER_FOR_COLUMNNAME);		
		cell.setCellStyle(cellStyle);
	}	
	
	/**
	 * 为工作表格添加注释信息，注释信息用单元格合并，且长度根据工作表的实际长度计算
	 * @throws Exception
	 */
	private void addPrompt() throws Exception{
		HSSFRow commentRow = sheet.createRow((short)currentRow+3);	
		HSSFCell cell = commentRow.createCell(0);
		cell.setCellValue(promptMsg);
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFillForegroundColor(BACKGROUND_FOR_COLUMNNAME);
		cellStyle.setFillPattern(cellStyle.SOLID_FOREGROUND);		
		cellStyle.setAlignment(ALGIN_FOR_COLUMNNAME);
		cell.setCellStyle(cellStyle);
		CellRangeAddress range = new CellRangeAddress(currentRow+3, currentRow+3, 0, tableWidth-1);
		sheet.addMergedRegion(range);
	}		
	
	/**
	 * 为工作表生成表前说明文件，使用者可以自定义说明生成类型
	 * @throws Exception 在创造表前声明的过程中出现的异常
	 */
	private void setTableReader() throws Exception{
		if (TABLEREADER_FLAG) {
			autoTableReaader();
			int tableReaderlength = tableReader.length;
			switch (tableReaderType) {
			case 0:
				for(int i=0;i<tableReaderlength;i++){
					HSSFRow readerRow = sheet.createRow((short)currentRow);	
					currentRow = currentRow +1;
					acquiesceFreezeEnd = acquiesceFreezeEnd + 1;
					readerRow.setHeight((short)(HIGHT_FOR_COLUMNNAME*21));
					ALGIN_FOR_CHANGE = HSSFCellStyle.ALIGN_RIGHT;
					cteateReaderCell(readerRow,0,tableReader[i][0]+":",null);
					ALGIN_FOR_CHANGE = HSSFCellStyle.ALIGN_LEFT;					
					cteateReaderCell(readerRow,1,tableReader[i][1],null);
					CellRangeAddress range = new CellRangeAddress(currentRow-1, currentRow-1, 1, tableWidth-1);
					sheet.addMergedRegion(range);					
				}
				break;
			case 1:
				for(int i=0;i<tableReaderlength;i++){
					HSSFRow readerRowA = sheet.createRow((short)currentRow);	
					currentRow = currentRow +1;
					acquiesceFreezeEnd = acquiesceFreezeEnd + 1;					
					readerRowA.setHeight((short)(HIGHT_FOR_COLUMNNAME*21));	
					ALGIN_FOR_CHANGE = HSSFCellStyle.ALIGN_CENTER_SELECTION;					
					cteateReaderCell(readerRowA,0,tableReader[i][0]+":",null);
					HSSFRow readerRowB = sheet.createRow((short)currentRow);	
					currentRow = currentRow +1;
					acquiesceFreezeEnd = acquiesceFreezeEnd + 1;					
					readerRowA.setHeight((short)(HIGHT_FOR_COLUMNNAME*21));	
					cteateReaderCell(readerRowB,0,tableReader[i][1],null);									
				}				
				break;
			case 2:
				for(int i=0;i<tableReaderlength;i++){
					HSSFRow readerRow = sheet.createRow((short)currentRow);	
					currentRow = currentRow +1;
					acquiesceFreezeEnd = acquiesceFreezeEnd + 1;					
					readerRow.setHeight((short)(HIGHT_FOR_COLUMNNAME*21));	
					ALGIN_FOR_CHANGE = HSSFCellStyle.ALIGN_RIGHT;					
					cteateReaderCell(readerRow,0,tableReader[i][0]+":",null);
					ALGIN_FOR_CHANGE = HSSFCellStyle.ALIGN_LEFT;						
					cteateReaderCell(readerRow,2,tableReader[i][1],null);
					CellRangeAddress rangeA = new CellRangeAddress(currentRow-1, currentRow-1, 0, 1);
					sheet.addMergedRegion(rangeA);	
					CellRangeAddress rangeB = new CellRangeAddress(currentRow-1, currentRow-1, 2, tableWidth-1);
					sheet.addMergedRegion(rangeB);					
				}				
				break;				
			default:
				for(int i=0;i<tableReaderlength;i++){
					HSSFRow readerRow = sheet.createRow((short)currentRow);	
					currentRow = currentRow +1;
					acquiesceFreezeEnd = acquiesceFreezeEnd + 1;					
					readerRow.setHeight((short)(HIGHT_FOR_COLUMNNAME*21));	
					ALGIN_FOR_CHANGE = HSSFCellStyle.ALIGN_RIGHT;					
					cteateReaderCell(readerRow,0,tableReader[i][0]+":",null);
					ALGIN_FOR_CHANGE = HSSFCellStyle.ALIGN_LEFT;					
					cteateReaderCell(readerRow,1,tableReader[i][1],null);
					CellRangeAddress range = new CellRangeAddress(currentRow-1, currentRow-1, 1, tableWidth-1);
					sheet.addMergedRegion(range);					
				}			
				break;
			}
		}
	}		
	
	/**
	 * 生成工作表中的一个新的sheet，并且存储在sheetmap中方便在将来复杂的方法中进行sheet的切换
	 * @throws Exception 在创建工作表sheet的过程中抛出的异常
	 */
	private void creatSheet() throws Exception{
		if ( null!=sheetName && !"".equals(sheetName)) {
			sheet = wb.createSheet(sheetName);	
			sheetMap.put(sheetName, sheet);
		}else {
			sheet = wb.createSheet("sheet"+String.valueOf(sheetNum));
			sheetNum = sheetNum + 1;
			sheetMap.put("sheet"+String.valueOf(sheetNum), sheet);
		}
	}	
	
	/**
	 * 此方法用于切换当前正在操作的sheet，主要用于复杂模式的工作表格生成
	 * @param anotherSheet 需要切换的sheet标签名称
	 * @throws Exception 在切换过程中抛出的异常
	 */
	private void changeSheetTo(String anotherSheet) throws Exception{
		sheetName = anotherSheet;
		sheet = sheetMap.get(anotherSheet);
	}	
	
	/**
	 * 设置工作表的冻结冻结行
	 * @param startline 冻结行的起始行号
	 * @param endline 冻结行的结束行号
	 * @throws Exception 有poi的基础方法抛出的异常
	 */
	private void setFreezePane(int startline, int endline) throws Exception{
		sheet.createFreezePane(startline,endline);
	}	
	
	/**
	 * 设置工作表的冻结冻结行
	 * @throws Exception 有poi的基础方法抛出的异常
	 */
	private void setFreezePane() throws Exception{
		sheet.createFreezePane(acquiesceFreezeStart,acquiesceFreezeEnd);
	}		
	
	/**
	 * 按照标准样式生成工作表格的总体方法（各种子方法的入口）
	 * @param tableNameBean 标记了表头信息的实体对象
	 * @param entityBeans 记录了工作表信息的实体对象的数组
	 * @throws Exception 在操作过程中捕获的异常
	 */
	private void produceNormalXls(Object tableNameBean, Object[] entityBeans) throws Exception{
		init();
		creatSheet();		
		HSSFRow nameRow = sheet.createRow((short)currentRow);
		currentRow = currentRow + 1;		
		nameRow.setHeight((short)(HIGHT_FOR_COLUMNNAME*21));		
		setTableName(nameRow,tableNameBean);
		for(Object entityBean:entityBeans){
			HSSFRow valueRow = sheet.createRow((short)currentRow);
			valueRow.setHeight((short)(HIGHT_FOR_CELL*21));
			entityBeanToRow(valueRow,entityBean);
			currentRow = currentRow + 1;
		}
		setFreezePane();
		addPrompt();
	}	
	
	/**
	 * 按照标准样式生成工作表格的总体方法（各种子方法的入口）
	 * @param tableArray 标记了表头信息的2维数组
	 * @param entityBeans 记录了工作表信息的实体对象的数组
	 * @throws Exception 在操作过程中捕获的异常
	 */
	private void produceNormalXls(Object[] entityBeans) throws Exception{
		creatSheet();		
		if ( !TABLEARRAY_FLAG ) {
			Object tableNameBean = entityBeans[0];
			autoTableName(tableNameBean);			
		}
		setTableReader();
		if ( TREEREADER_FLAG ) {
			creatTreeReader();			
		}		
		HSSFRow nameRow = sheet.createRow((short)currentRow);
		currentRow = currentRow + 1;
		nameRow.setHeight((short)(HIGHT_FOR_COLUMNNAME*21));
		setTableName(nameRow,tableArray);		
		for(Object entityBean:entityBeans){
			HSSFRow valueRow = sheet.createRow((short)currentRow);
			valueRow.setHeight((short)(HIGHT_FOR_CELL*21));
			entityBeanToRow(valueRow,entityBean);
			currentRow = currentRow + 1;
		}
		setFreezePane();
		addPrompt();		
	}	
	
	/**
	 * 按照标准样式生成工作表格的总体方法（各种子方法的入口）
	 * @param tableArray 标记了表头信息的2维数组
	 * @param entityBeans 记录了工作表信息的实体对象的数组
	 * @throws Exception 在操作过程中捕获的异常
	 */
	private void produceNormalXls(List<Map<String, String>> listMaps) throws Exception{
		creatSheet();		
		if ( !TABLEARRAY_FLAG ) {
			Map<String, String> map = listMaps.get(0);
			autoTableName(map);			
		}
		setTableReader();	
		if ( TREEREADER_FLAG ) {
			creatTreeReader();			
		}			
		HSSFRow nameRow = sheet.createRow((short)currentRow);
		currentRow = currentRow + 1;
		nameRow.setHeight((short)(HIGHT_FOR_COLUMNNAME*21));
		setTableName(nameRow,tableArray);		
		int i = 0;
		for(Map<String, String> map:listMaps){
			HSSFRow valueRow = sheet.createRow((short)currentRow);
			valueRow.setHeight((short)(HIGHT_FOR_CELL*21));
			entityBeanToRow(valueRow,map);
			currentRow = currentRow + 1;
			i ++;
		}
		setFreezePane();
		addPrompt();		
	}
	
	/**
	 * 此方法是在工作表中生成表头文件的方法
	 * @param row 需要生成表头文件对应的行信息
	 * @param entityBean 写有表头信息的实体对象（该对象用方法调用者自行生产，支持中文）
	 * @throws Exception 在编写表头时捕获的异常信息
	 */
	private void setTableName(HSSFRow row, Object entityBean) throws Exception{
		acquiesceFreezeEnd = acquiesceFreezeEnd +1;		
		int columnNumber = 0;
		Class entityBeanClass = entityBean.getClass();
		Method []entityBeanMethod= entityBeanClass.getDeclaredMethods();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		for(int i=0;i<entityBeanMethod.length;i++){//遍历实体bean的所有声明方法
			//当实体bean的的方法以get大头，表示为一个属性获取方法
			String Methodname = entityBeanMethod[i].getName();
			if(Methodname.startsWith("get")){ 
				String keyName = entityBeanMethod[i].getName().substring("get".length()).toUpperCase();				
				Object beanValue = entityBeanMethod[i].invoke(entityBean, null);
				String keyValue = null;
				if(beanValue!=null){
					//获取方法参数类型
					Class methodParameterType = entityBeanMethod[i].getReturnType();
					if (methodParameterType.getName().equals("java.util.Date")) {//时间类型特殊处理
						keyValue = df.format((Date)beanValue);
					}else if (methodParameterType.getName().equals("java.lang.Integer")){
						keyValue = beanValue.toString();						
					}else{
						keyValue  = String.valueOf(beanValue);
					}
					keyMap.put(keyName, i);
					propertyRead = Methodname + "|";
					cteateCell(row,(short)columnNumber,keyValue);
					columnNumber = columnNumber+1;
				}
			}
		}
	}	
	
	/**
	 * 根据传入实体类的属性名称默认自动生成表头数组，用于没有传入表头参数时使用
	 * @param entityBean 
	 * @throws Exception
	 */
	private void autoTableName(Object entityBean) throws Exception{
		ArrayList<String> tableNamelist = new ArrayList<String>();
		Class entityBeanClass = entityBean.getClass();
		Method []entityBeanMethod= entityBeanClass.getDeclaredMethods();
		for(int i=0;i<entityBeanMethod.length;i++){//遍历实体bean的所有声明方法
			//当实体bean的的方法以get大头，表示为一个属性获取方法
			String Methodname = entityBeanMethod[i].getName();
			if(Methodname.startsWith("get")){ 
				String keyName = entityBeanMethod[i].getName().substring("get".length()).toUpperCase();				
				if (AUTO_NULL_FLAG) {
					tableNamelist.add(keyName);
				}else{
					Object beanValue = entityBeanMethod[i].invoke(entityBean, null);
					if(beanValue!=null){
						tableNamelist.add(keyName);
					}	
				}

			}
			
		}
		int arraySize = tableNamelist.size();
		String[][] innerArray = new String[arraySize][2];
		if (null==tableNamelist||0==arraySize) {
			log.error("表头为空！"); 
		}else{
			for(int j=0;j<arraySize;j++){
				innerArray[j][0] = tableNamelist.get(j);
				innerArray[j][1] = tableNamelist.get(j);				
			}
			tableArray = innerArray;
			tableWidth = innerArray.length;
		}		
	}	

	/**
	 * 生产表头树状描述说明行
	 * @throws Exception
	 */
	private void creatTreeReader() throws Exception{
		int size = extraLeaderRow.size();
		for (int i=0;i<size;i++) {
			String[][] exaArray = extraLeaderRow.get(i);
			int cellsize = exaArray.length;
			int column = 0;
			HSSFRow readerRow = sheet.createRow((short)currentRow);	
			currentRow = currentRow +1;
			acquiesceFreezeEnd = acquiesceFreezeEnd + 1;					
			readerRow.setHeight((short)(HIGHT_FOR_COLUMNNAME*21));
			for (int j=0;j<cellsize;j++) {
				int blankNum = Integer.valueOf(exaArray[j][0]);
				cteateCell(readerRow,column,exaArray[j][1]);
				for (int z=1;z<blankNum;z++) {
					cteateCell(readerRow,column+z,"");				
				}
				int newColumn = column + blankNum;
				CellRangeAddress range = new CellRangeAddress(currentRow-1, currentRow-1, column, newColumn-1);
				sheet.addMergedRegion(range);					
				column = newColumn;				
			}
		}		
	}	
	
	/**
	 * 根据传入实体类的属性名称默认自动生成表头数组，用于没有传入表头参数时使用
	 * @param entityBean 
	 * @throws Exception
	 */
	private void autoTableReaader() throws Exception{
		if (0 == tableWidth) {
			tableReaderType = 1;
		}else if(1 == tableWidth){
			tableReaderType = 1;
		}else if(1<tableWidth){
			if(FORMAT_FLAG){
				int tableReaderwidth = formatArray[0]+formatArray[1];
				if(tableReaderwidth<20){
					tableReaderType = 2;
				}else{
					tableReaderType = 0;
				}
			}else{
				if(tableWidth<7){
					tableReaderType = 0;
				}else{
					tableReaderType = 2;
				}				
			}
		}else{
			tableReaderType = 1;
		}
	}	
	
	/**
	 * 此方法是在工作表中生成表头文件的方法
	 * @param row 需要生成表头文件对应的行信息
	 * @param tableArray 写有表头信息的2维数组，数组的第一位是实体对象中的属性，第二位是中文列名
	 * @throws Exception
	 */
	private void setTableName(HSSFRow row, String[][] tableArray) throws Exception{
		acquiesceFreezeEnd = acquiesceFreezeEnd +1;			
		int length = tableArray.length;
		int columnNumber = 0;
		for(int i=0;i<length;i++){//遍历实体bean的所有声明方法
			//当实体bean的的方法以get大头，表示为一个属性获取方法
			String tableKey = tableArray[i][0].toUpperCase();
			String tableValue = tableArray[i][1];
			keyMap.put(tableKey, i);
			propertyRead = propertyRead + tableKey + "|";
			cteateCell(row,(short)columnNumber,tableValue);
			columnNumber = columnNumber+1;
		}
		tableWidth = columnNumber;
	}	
	
	
	/**
	 * 此方法是用于生成工作表格正文一行的方法
	 * @param row 需要写入的信息的工作表中对应的一行
	 * @param entityBean 需要在当前行写入的数据信息
	 * @throws Exception 在信息写入过程中捕获的操作方法中的异常，通常为sun的异常
	 */
	private void entityBeanToRow(HSSFRow row, Object entityBean) throws Exception{
		Class entityBeanClass = entityBean.getClass();
		Method []entityBeanMethod= entityBeanClass.getDeclaredMethods();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		for(int i=0;i<entityBeanMethod.length;i++){//遍历实体bean的所有声明方法
			//当实体bean的的方法以get大头，表示为一个属性获取方法
			String Methodname = entityBeanMethod[i].getName();
			String keyName = Methodname.substring("get".length()).toUpperCase();	
			if(Methodname.startsWith("get") && propertyRead.indexOf("|"+keyName+"|") > -1){ 
				Object beanValue = entityBeanMethod[i].invoke(entityBean, null);
				String keyValue = null;
				int columnNumber = keyMap.get(keyName);				
				String dataType = "STRING";					
				if(beanValue!=null){
					//获取方法参数类型
					Class methodParameterType = entityBeanMethod[i].getReturnType();
					if (methodParameterType.getName().equals("java.util.Date")) {//时间类型特殊处理
						keyValue = df.format((Date)beanValue);
						dataType = "DATE";						
					}else if (methodParameterType.getName().equals("java.math.BigDecimal")){
						keyValue = beanValue.toString();
						dataType = "BIGDECIMAL";						
					}else if (methodParameterType.getName().equals("java.lang.Integer")){
						keyValue = beanValue.toString();						
					}else{
						keyValue  = String.valueOf(beanValue).trim();
					}
					if(dctionaryType.indexOf(keyName)>-1){
						keyValue = dctionaryMap.get(keyName).get(keyValue);
					}
					cteateCell(row,(short)columnNumber,keyValue,dataType);
					columnNumber = columnNumber+1;
				} else {
					cteateCell(row,(short)columnNumber,"",dataType);
					columnNumber = columnNumber+1;					
				}
			}
		}
	}	
	
	/**
	 * 此方法是用于生成工作表格正文一行的方法
	 * @param row 需要写入的信息的工作表中对应的一行
	 * @param map 需要在当前行写入的数据信息
	 * @throws Exception 在信息写入过程中捕获的操作方法中的异常，通常为sun的异常
	 */
	private void entityBeanToRow(HSSFRow row, Map<String, String> map) throws Exception{
		Set<Entry<String, String>> mapEntries = map.entrySet();
		if(mapEntries != null){
			Iterator<Entry<String, String>> mapIterator = mapEntries.iterator();
			String columns = propertyRead.substring(1,propertyRead.length());
			String propertyReadStr = propertyRead.substring(1,propertyRead.length()-1);
			String[] propertyReads = propertyReadStr.split("\\|");
			String dataType = "STRING";	
			while(mapIterator.hasNext()){
				Map.Entry<String, String> mapEntry = (Entry<String, String>) mapIterator.next();
				for(String cellName : propertyReads){
					String mapKeyName = mapEntry.getKey();
					if(mapKeyName.toUpperCase().equals(cellName.toUpperCase())){
						String mapKeyValue = mapEntry.getValue();
						int columnNumber = keyMap.get(mapKeyName.toUpperCase());	
						cteateCell(row,(short)columnNumber,mapKeyValue,dataType);
						columns = columns.replace(cellName.toUpperCase()+"|", "");
					}
				}
			}
			if(columns.length() > 0){
				String[] columnss = columns.split("\\|");
				for(String column : columnss){
					int columnNumber = keyMap.get(column.toUpperCase());
					cteateCell(row,(short)columnNumber,"",dataType);
				}
			}
		}
	}	
	
	/**
	 * 此方法用于校验工具类中的所有参数是否正常
	 * @throws Exception 发现参数有异常状况是抛出对应的异常信息
	 */
	private void checkParameter() throws Exception{
		if ( null == fileStyle || "".equals(fileStyle) ) {
			log.error("生产xls的方法类的模型参数已经被清空，不能继续生产工作表文件");
		}
		if ( null == filePath || "".equals(filePath) ) {
			log.error("生产xls的方法类的文件导出地址参数已经被清空，不能继续生产工作表文件");
		}
		if ( null == fileName || "".equals(fileName) ) {
			log.error("生产xls的方法类的文件导出名称参数已经被清空，不能继续生产工作表文件");
		}
	}		
}
