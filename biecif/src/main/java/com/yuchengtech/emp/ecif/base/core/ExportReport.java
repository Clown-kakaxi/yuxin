/**
 * 
 */
package com.yuchengtech.emp.ecif.base.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.common.collect.Maps;
import com.yuchengtech.emp.ecif.base.common.GlobalConstants;
import com.yuchengtech.emp.ecif.base.util.CodeUtil;
import com.yuchengtech.emp.ecif.base.util.ConvertUtils;
import com.yuchengtech.emp.ecif.customer.special.service.SpecialListAABS;

/**
 * <pre>
 * Title:程序的中文名称
 * Description: 程序功能的描述
 * </pre>
 * 
 * @author guanyb guanyb@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Service
public class ExportReport {

	private Logger log = LoggerFactory.getLogger(ExportReport.class);

	//private HSSFWorkbook workbook;
	private XSSFWorkbook workbook2;
	private SXSSFWorkbook workbook;
	//private HSSFSheet sheet/* 工作sheet页 */;
	private Sheet sheet;
	private Sheet sheet2;
	private Sheet sheet3;
	private Sheet sheet4;
	
	final private int[] reportFristRowIndex = { 3, 3, 2, 2, 2, 2, 3, 2, 3, 2 };// 各报表中开始行索引
//	final private int[] reportTableNum = { 1, 1, 1, 1, 1, 1 };// 各报表中table数量
	
	final private int[] reportFristRowIndexCust = { 2, 3, 2 };
//	final private int[] reportTableNumCust = { 1, 1, 1};

	@Autowired
	private SpecialListAABS specialListBS;
	
	@Autowired
	private CodeUtil codeUtil;
	/**
	 * 创建xls文件
	 * 
	 * @return
	 */
	public String createExcel(int reportNo, List<List<Object[]>> reportData) {
		//validation data
		if (reportData == null)
			return null;
		//validation reportNo and table num count
//		if(reportNo < 10 ){
//			if(reportData.size() != reportTableNum[reportNo - 1])// 数据不正确
//				return null;
//		}
		// 准备工作
		String realpath = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest().getSession()
				.getServletContext().getRealPath("/");
		//
		String file = "";
		//
		switch (reportNo) {
		case 1:
			file = reportSpecialList(reportNo, realpath, reportData, GlobalConstants.EXCEL_TEMPLATE_SPECIALLIST_CN);
			break;
		case 2:
			file = reportSpecialListApproval(reportNo, realpath, reportData, GlobalConstants.EXCEL_TEMPLATE_SPECIALLISTAPP_CN);
			break;
		case 3:
			file = reportSpecialListApprovalHistroy(reportNo, realpath, reportData, GlobalConstants.EXCEL_TEMPLATE_SPECIALLISTAPPHIS_CN);
			break;
		case 4:
			file = reportCustMergeRecord(reportNo, realpath, reportData, GlobalConstants.EXCEL_TEMPLATE_CUSTMERGERECORD_CN);
			break;
		case 5:
			file = reportCustSplitRecord(reportNo, realpath, reportData, GlobalConstants.EXCEL_TEMPLATE_CUSTSPLITRECORD_CN);
			break;
		case 6:
			file = reportSuspectCust(reportNo, realpath, reportData, GlobalConstants.EXCEL_TEMPLATE_SUSPECTCUST_CN);
			break;
		case 7:
			file = reportCustMergeRecordApp(reportNo, realpath, reportData, GlobalConstants.EXCEL_TEMPLATE_CUSTMERGERECORDAPP_CN);
			break;
		case 8:
			file = reportCustMergeRecordAppHis(reportNo, realpath, reportData, GlobalConstants.EXCEL_TEMPLATE_CUSTMERGERECORDAPPHIS_CN);
			break;
		case 9:
			file = reportCustSplitRecordApp(reportNo, realpath, reportData, GlobalConstants.EXCEL_TEMPLATE_CUSTSPLITRECORDAPP_CN);
			break;
		case 10:
			file = reportCustSplitRecordAppHis(reportNo, realpath, reportData, GlobalConstants.EXCEL_TEMPLATE_CUSTSPLITRECORDAPPHIS_CN);
			break;
		case 11:
			file = reportSpecialListErr(reportNo, realpath, reportData, GlobalConstants.EXCEL_TEMPLATE_IMPSPECIALLIST_ERR);
			break;
		case 44:
			file = reportCustMergeRecordErr(reportNo, realpath, reportData, GlobalConstants.EXCEL_TEMPLATE_IMPCUSTMERGERECORD_ERR);
			break;
		case 55:
			file = reportCustSplitRecordErr(reportNo, realpath, reportData, GlobalConstants.EXCEL_TEMPLATE_IMPCUSTSPLITRECORD_ERR);
			break;
		case 66:
			file = reportCustMergeRecordAppErr(reportNo, realpath, reportData, GlobalConstants.EXCEL_TEMPLATE_CUSTMERGERECORDAPP_CN);
			break;
		case 77:
			file = reportCustSplitRecordAppErr(reportNo, realpath, reportData, GlobalConstants.EXCEL_TEMPLATE_CUSTSPLITRECORDAPP_CN);
			break;
		}
		return file;
	}
	
	/**
	 * 个人信息的导出类
	 * @param reportNo
	 * @param reportData
	 * @return
	 */
	public String createPerExcel(int reportNo, List<Object[]> reportData) {
		if (reportData == null)
				//|| reportData.size() != reportTableNum[reportNo - 1])// 数据不正确
			return null;
		String realpath = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest().getSession()
				.getServletContext().getRealPath("/");
		String templateName = "";
		String fileName = "";
		String file = "";
		switch (reportNo) {
		case 1:
			templateName = GlobalConstants.EXCEL_TEMPLATE_PERBASIC;
			fileName = GlobalConstants.EXCEL_TEMPLATE_PERBASIC_CN;
			break;
		case 2:
			templateName = GlobalConstants.EXCEL_TEMPLATE_PERFOCUS;
			fileName = GlobalConstants.EXCEL_TEMPLATE_PERFOCUS_CN;
			break;
		case 3:
			templateName = GlobalConstants.EXCEL_TEMPLATE_PERPRODUCT;
			fileName = GlobalConstants.EXCEL_TEMPLATE_PERPRODUCT_CN;
			break;
		case 4:
			templateName = GlobalConstants.EXCEL_TEMPLATE_PERANALYSISINFO;
			fileName = GlobalConstants.EXCEL_TEMPLATE_PERANALYSISINFO_CN;
			break;
		case 5:
			templateName = GlobalConstants.EXCEL_TEMPLATE_RELATIVEINFO;
			fileName = GlobalConstants.EXCEL_TEMPLATE_RELATIVEINFO_CN;
			break;
		}
		file = simpleReport(2, realpath, reportData, templateName, fileName);
		return file;
	}
	
	public String createOrgExcel(int reportNo, List<Object[]> reportData) {
		if (reportData == null)
			return null;
		String realpath = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest().getSession()
				.getServletContext().getRealPath("/");
		String templateName = "";
		String fileName = "";
		String file = "";
		switch (reportNo) {
		case 1:
			templateName = GlobalConstants.EXCEL_TEMPLATE_ORGBASIC;
			fileName = GlobalConstants.EXCEL_TEMPLATE_ORGBASIC_CN;
			break;
		case 2:
			templateName = GlobalConstants.EXCEL_TEMPLATE_ORGFOCUS;
			fileName = GlobalConstants.EXCEL_TEMPLATE_ORGFOCUS_CN;
			break;
		case 3:
			templateName = GlobalConstants.EXCEL_TEMPLATE_ORGPRODUCT;
			fileName = GlobalConstants.EXCEL_TEMPLATE_ORGPRODUCT_CN;
			break;
		case 4:
			templateName = GlobalConstants.EXCEL_TEMPLATE_ORGANALYSISINFO;
			fileName = GlobalConstants.EXCEL_TEMPLATE_ORGANALYSISINFO_CN;
			break;
		case 5:
			templateName = GlobalConstants.EXCEL_TEMPLATE_ORGRELATIVEINFO;
			fileName = GlobalConstants.EXCEL_TEMPLATE_ORGRELATIVEINFO_CN;
			break;
		}
		file = simpleReport(2, realpath, reportData, templateName, fileName);
		return file;
	}
	
	/**
	 * 创建xls文件
	 * 
	 * @return
	 */
	public String createCustrelExcel(int reportNo, List<List<Object[]>> reportData) {
		//validation
//		if (reportData == null
//				|| reportData.size() != reportTableNumCust[reportNo - 1])// 数据不正确
//		return null;		
		if (reportData == null)
			return null;
		// 准备工作
		String realpath = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest().getSession()
				.getServletContext().getRealPath("/");
		//
		String file = "";
		//
		switch (reportNo) {
		case 1:
			file = reportCustRel(reportNo, realpath, reportData, GlobalConstants.EXCEL_TEMPLATE_CUSTREL_CN);
			break;
		case 2:
			file = reportCustRelApproval(reportNo, realpath, reportData, GlobalConstants.EXCEL_TEMPLATE_CUSTRELAPPROVAL_CN);
			break;
		case 3:
			file = reportCustRelApprovalHistroy(reportNo, realpath, reportData, GlobalConstants.EXCEL_TEMPLATE_CUSTRELAPPROVALHIS_CN);
			break;
		case 11:
			file = reportCustRelErr(reportNo, realpath, reportData, GlobalConstants.EXCEL_TEMPLATE_IMPCUSTREL_ERR);
			break;
		}
		return file;
	}
	
	/**
	 * 构建excel全名
	 * 
	 * @param fileName
	 * @return
	 */
	private String getExcelName(String fileName, String path) {
		File file = new File(path);

		if (!file.isDirectory()) {
			file.mkdir();
		}
		String name = "export.xls";
		if (fileName != null && !"".equals(fileName)) {
			name = fileName;
		}
		return path + File.separator + name;
	}
	
	/**
	 * 导入模板文件
	 * @param realpath 系统对应路径
	 * @param template 模板参数
	 * @return HSSFWorkbook
	 */
	public HSSFWorkbook getTemplate(String realpath, String template){
		//
//		if(StringUtils.isEmpty(realpath) || StringUtils.isEmpty(template)){
//			return null;
//		}
//		try {
//			FileInputStream in = new FileInputStream(realpath + template);
//			workbook = new HSSFWorkbook(in);// 读取excel模板
//			in.close();			
//			return workbook;
//		} catch (IOException e) {
//			log.error(e.getMessage());
			return null;// IO异常
//		}
	}
	
	/**
	 * 导入模板文件2
	 * @param realpath 系统对应路径
	 * @param template 模板参数
	 * @return HSSFWorkbook
	 */
	public SXSSFWorkbook getTemplate2(String realpath, String template){
		//
		if(StringUtils.isEmpty(realpath) || StringUtils.isEmpty(template)){
			return null;
		}
		try {			
			//FileInputStream in = new FileInputStream(realpath + template);
			InputStream in = new FileInputStream(realpath + template);			
			workbook2 = new XSSFWorkbook(in);
			in.close();
			workbook = new SXSSFWorkbook(workbook2, 500);			
			return workbook;
		} catch (IOException e) {
			log.error(e.getMessage());
			return null;// IO异常
		}
	}
	
	/**
	 * 导出生成文件
	 * @param realpath 系统对应路径
	 * @param folder 输出文件路径
	 * @param workbook 
	 * @return HSSFWorkbook
	 */
//	public String getFilePath(String realpath, String folder, HSSFWorkbook workbook){
//		//
//		if(StringUtils.isEmpty(realpath) || StringUtils.isEmpty(folder) || workbook == null){
//			return null;
//		}
//		String file = "";
//		String fileName = getExcelName("report_" + new Date().getTime()
//				+ ".xls", realpath + folder);
//		try {
//			FileOutputStream out = new FileOutputStream(fileName);
//			file = fileName;
//			workbook.write(out);
//			out.flush();
//			out.close();
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			return null;// IO异常
//		}
//		return file;
//	}
	
	/**
	 * 导出生成文件
	 * @param realpath 系统对应路径
	 * @param folder 输出文件路径
	 * @param workbook 
	 * @return
	 */
	public String getFilePath(String realpath, String folder, Workbook workbook){
		//
		if(StringUtils.isEmpty(realpath) || StringUtils.isEmpty(folder) || workbook == null){
			return null;
		}
		String file = "";
		String fileName = getExcelName("report_" + new Date().getTime()
				+ ".xlsx", realpath + folder);
		try {
			FileOutputStream out = new FileOutputStream(fileName);
			file = fileName;
			workbook.write(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;// IO异常
		}
		return file;
	}
	
	/**
	 * 导出生成文件2
	 * @param realpath 系统对应路径
	 * @param folder 输出文件路径
	 * @param workbook 
	 * @return SXSSFWorkbook
	 */
	public String getFilePath2(String realpath, String folder, SXSSFWorkbook workbook, String name){
		//
		if(StringUtils.isEmpty(realpath) || 
				StringUtils.isEmpty(folder) || workbook == null){
			return null;
		}
		//File srcFile = new File(realpath + template);
		String file = "";
		/*"report_" + new Date().getTime()*/
		String fileName = getExcelName(name+ConvertUtils.getDateToString(new Date())
				+ ".xlsx", realpath + folder);
		//File destFile = new File(fileName);
		try {
			//FilesUtils.copyFile(srcFile, destFile, false);
			FileOutputStream out = new FileOutputStream(fileName);
			file = fileName;
			workbook.write(out);
			//out.flush();
			out.close();
	        // dispose of temporary files backing this workbook on disk
			workbook.dispose();			
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;// IO异常
		}
		return file;
	}
	//大数据直接sheet分页存储
	public SXSSFWorkbook inputData(
			int reportNo, SXSSFWorkbook workbook, 
			List<List<Object[]>> reportData, int array){
		int count = 50000;
		for(int ii = 0 ; ii < reportData.size(); ii++){
			List<Object[]> temp = reportData.get(ii);
			int totalCount = temp.size();
			int totalNum = totalCount / count;
			if (totalCount % count != 0) {
				totalNum++;
			}
			if(totalNum == 1){
				sheet = workbook.getSheetAt(0);// 读取第一个工作簿
				// data info
				for (int j = 0; j < temp.size(); j++) {
					Row row = sheet.createRow(j + array);
					Object[] obj = temp.get(j);
					for(int k = 0; k < obj.length; k++ ){
						Cell cell = row.createCell(k);
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(obj[k]!=null?obj[k].toString():"");
					}
				}
			}else{
				for(int i=0; i<totalNum; i++){
					Sheet sheet;
					if(i == 0){
						sheet = workbook.getSheetAt(0);// 读取第一个工作簿
					}else{
						sheet = workbook.createSheet("Sheet"+(i+1));
					}
					int current = i*count;
					int max = (i+1)*count;
					if(i == (totalNum-1)){
						max = totalCount;
					}
					// data info
					int num = 0;
					for(int j=current; j<max; j++){
						Row row = sheet.createRow(num + array);
						Object[] obj = temp.get(j);
						for(int k = 0; k < obj.length; k++ ){
							Cell cell = row.createCell(k);
							cell.setCellType(Cell.CELL_TYPE_STRING);
							cell.setCellValue(obj[k]!=null?obj[k].toString():"");
						}
						num++;
					}
				}
			}
		}
		return workbook;
	}
	
	/**
	 * 黑名单导出信息
	 * @param reportNo
	 * @param realpath
	 * @param reportData
	 * @return
	 */
	public String reportSpecialList(int reportNo, String realpath, List<List<Object[]>> reportData, String name) {
		// 引入模板
		workbook = getTemplate2(realpath, GlobalConstants.EXCEL_TEMPLATE_SPECIALLIST);
		workbook = inputData(reportNo, workbook, reportData, reportFristRowIndex[reportNo-1]);
		// 导出文件
		return getFilePath2(realpath, GlobalConstants.EXCEL_REPORT_FOLDER, workbook, name);
	}
	
	/**
	 * 黑名单待审核信息
	 * @param reportNo
	 * @param realpath
	 * @param reportData
	 * @return
	 */
	public String reportSpecialListApproval(int reportNo, String realpath, List<List<Object[]>> reportData, String name) {
		// 引入模板
		workbook = getTemplate2(realpath, GlobalConstants.EXCEL_TEMPLATE_SPECIALLISTAPP);
		workbook = inputData(reportNo, workbook, reportData, reportFristRowIndex[reportNo-1]);
		// 导出文件
		return getFilePath2(realpath, GlobalConstants.EXCEL_REPORT_FOLDER, workbook, name);
	}
	
	/**
	 * 黑名单审批历史
	 * @param reportNo
	 * @param realpath
	 * @param reportData
	 * @return
	 */
	public String reportSpecialListApprovalHistroy(int reportNo, String realpath, List<List<Object[]>> reportData, String name) {
		// 引入模板
		workbook = getTemplate2(realpath, GlobalConstants.EXCEL_TEMPLATE_SPECIALLISTAPPHIS);
		workbook = inputData(reportNo, workbook, reportData, reportFristRowIndex[reportNo-1]);
		// 导出文件
		return getFilePath2(realpath, GlobalConstants.EXCEL_REPORT_FOLDER, workbook, name);
	}

	/**
	 * 客户合并信息记录
	 * @param reportNo
	 * @param realpath
	 * @param reportData
	 * @return
	 */
	public String reportCustMergeRecord(int reportNo, String realpath, List<List<Object[]>> reportData, String name) {
		// 引入模板
		workbook = getTemplate2(realpath, GlobalConstants.EXCEL_TEMPLATE_CUSTMERGERECORD);
		workbook = inputData(reportNo, workbook, reportData, reportFristRowIndex[reportNo-1]);
		// 导出文件
		return getFilePath2(realpath, GlobalConstants.EXCEL_REPORT_FOLDER, workbook, name);
	}
	
	/**
	 * 客户合并信息记录
	 * @param reportNo
	 * @param realpath
	 * @param reportData
	 * @return
	 */
	public String reportCustMergeRecordApp(int reportNo, String realpath, List<List<Object[]>> reportData, String name) {
		// 引入模板
		workbook = getTemplate2(realpath, GlobalConstants.EXCEL_TEMPLATE_CUSTMERGERECORDAPP);
		workbook = inputData(reportNo, workbook, reportData, reportFristRowIndex[reportNo-1]);
		// 导出文件
		return getFilePath2(realpath, GlobalConstants.EXCEL_REPORT_FOLDER, workbook, name);
	}
	
	/**
	 * 客户合并信息记录
	 * @param reportNo
	 * @param realpath
	 * @param reportData
	 * @return
	 */
	public String reportCustMergeRecordAppHis(int reportNo, String realpath, List<List<Object[]>> reportData, String name) {
		// 引入模板
		workbook = getTemplate2(realpath, GlobalConstants.EXCEL_TEMPLATE_CUSTMERGERECORDAPPHIS);
		workbook = inputData(reportNo, workbook, reportData, reportFristRowIndex[reportNo-1]);
		// 导出文件
		return getFilePath2(realpath, GlobalConstants.EXCEL_REPORT_FOLDER, workbook, name);
	}
	
	/**
	 * 客户黑名单信息错误记录
	 * @param reportNo
	 * @param realpath
	 * @param reportData
	 * @return
	 */
	public String reportSpecialListErr(int reportNo, String realpath, List<List<Object[]>> reportData, String name) {
		// 引入模板
		workbook = getTemplate2(realpath, GlobalConstants.EXCEL_TEMPLATE_IMPSPECIALLIST);
		workbook = inputData(reportNo, workbook, reportData, reportFristRowIndex[reportNo-11]);
		// 导出文件
		return getFilePath2(realpath, GlobalConstants.EXCEL_REPORT_FOLDER, workbook, name);
	}
	
	/**
	 * 客户合并信息错误记录
	 * @param reportNo
	 * @param realpath
	 * @param reportData
	 * @return
	 */
	public String reportCustMergeRecordErr(int reportNo, String realpath, List<List<Object[]>> reportData, String name) {
		// 引入模板
		workbook = getTemplate2(realpath, GlobalConstants.EXCEL_TEMPLATE_IMPCUSTMERGERECORD_CN);
		workbook = inputData(reportNo, workbook, reportData, reportFristRowIndex[reportNo-44]);
		// 导出文件
		return getFilePath2(realpath, GlobalConstants.EXCEL_REPORT_FOLDER, workbook, name);
	}
	
	/**
	 * 客户合并信息错误记录
	 * @param reportNo
	 * @param realpath
	 * @param reportData
	 * @return
	 */
	public String reportCustMergeRecordAppErr(int reportNo, String realpath, List<List<Object[]>> reportData, String name) {
		// 引入模板
		workbook = getTemplate2(realpath, GlobalConstants.EXCEL_TEMPLATE_CUSTMERGERECORDAPP);
		workbook = inputData(reportNo, workbook, reportData, reportFristRowIndex[reportNo-66]);
		// 导出文件
		return getFilePath2(realpath, GlobalConstants.EXCEL_REPORT_FOLDER, workbook, name);
	}
	
	/**
	 * 客户拆分信息记录
	 * @param reportNo
	 * @param realpath
	 * @param reportData
	 * @return
	 */
	public String reportCustSplitRecord(int reportNo, String realpath, List<List<Object[]>> reportData, String name) {
		// 引入模板
		workbook = getTemplate2(realpath, GlobalConstants.EXCEL_TEMPLATE_CUSTSPLITRECORD);
		workbook = inputData(reportNo, workbook, reportData, reportFristRowIndex[reportNo-1]);
		// 导出文件
		return getFilePath2(realpath, GlobalConstants.EXCEL_REPORT_FOLDER, workbook, name);
	}
	
	/**
	 * 客户拆分信息记录
	 * @param reportNo
	 * @param realpath
	 * @param reportData
	 * @return
	 */
	public String reportCustSplitRecordApp(int reportNo, String realpath, List<List<Object[]>> reportData, String name) {
		// 引入模板
		workbook = getTemplate2(realpath, GlobalConstants.EXCEL_TEMPLATE_CUSTSPLITRECORDAPP);
		workbook = inputData(reportNo, workbook, reportData, reportFristRowIndex[reportNo-1]);
		// 导出文件
		return getFilePath2(realpath, GlobalConstants.EXCEL_REPORT_FOLDER, workbook, name);
	}
	
	/**
	 * 客户拆分信息记录
	 * @param reportNo
	 * @param realpath
	 * @param reportData
	 * @return
	 */
	public String reportCustSplitRecordAppHis(int reportNo, String realpath, List<List<Object[]>> reportData, String name) {
		// 引入模板
		workbook = getTemplate2(realpath, GlobalConstants.EXCEL_TEMPLATE_CUSTSPLITRECORDAPPHIS);
		workbook = inputData(reportNo, workbook, reportData, reportFristRowIndex[reportNo-1]);
		// 导出文件
		return getFilePath2(realpath, GlobalConstants.EXCEL_REPORT_FOLDER, workbook, name);
	}
	
	/**
	 * 客户拆分信息错误记录
	 * @param reportNo
	 * @param realpath
	 * @param reportData
	 * @return
	 */
	public String reportCustSplitRecordErr(int reportNo, String realpath, List<List<Object[]>> reportData, String name) {
		// 引入模板
		workbook = getTemplate2(realpath, GlobalConstants.EXCEL_TEMPLATE_IMPCUSTSPLITRECORD_CN);
		workbook = inputData(reportNo, workbook, reportData, reportFristRowIndex[reportNo-55]);
		// 导出文件
		return getFilePath2(realpath, GlobalConstants.EXCEL_REPORT_FOLDER, workbook, name);
	}
	
	/**
	 * 客户拆分信息错误记录
	 * @param reportNo
	 * @param realpath
	 * @param reportData
	 * @return
	 */
	public String reportCustSplitRecordAppErr(int reportNo, String realpath, List<List<Object[]>> reportData, String name) {
		// 引入模板
		workbook = getTemplate2(realpath, GlobalConstants.EXCEL_TEMPLATE_CUSTSPLITRECORDAPP);
		workbook = inputData(reportNo, workbook, reportData, reportFristRowIndex[reportNo-77]);
		// 导出文件
		return getFilePath2(realpath, GlobalConstants.EXCEL_REPORT_FOLDER, workbook, name);
	}
	
	/**
	 * 疑似客户列表信息
	 * @param reportNo
	 * @param realpath
	 * @param reportData
	 * @return
	 */
	public String reportSuspectCust(int reportNo, String realpath, List<List<Object[]>> reportData, String name) {
		// 引入模板
		workbook = getTemplate2(realpath, GlobalConstants.EXCEL_TEMPLATE_SUSPECTCUST);
		workbook = inputData(reportNo, workbook, reportData, reportFristRowIndex[reportNo-1]);
//		sheet = workbook.getSheetAt(0);
////		HSSFRow Row row = null;
////		HSSFCell Cell cell = null;
//		// data info
//		for(int i = 0 ; i < reportData.size(); i++){
//			List<Object[]> temp = reportData.get(i);
//			for (int j = 0; j < temp.size(); j++) {
//				Row row = sheet.createRow(j + reportFristRowIndex[reportNo-1]);
//				Object[] obj = temp.get(j);
//				for(int k = 0; k < obj.length; k++ ){
//					Cell cell = row.createCell(k);
//					cell.setCellValue(obj[k]!=null?obj[k].toString():"");
//				}
//			}
//		}
		// 导出文件
		return getFilePath2(realpath, GlobalConstants.EXCEL_REPORT_FOLDER, workbook, name);
	}
	
	public String simpleReport(int beginIndex, String realpath, List<Object[]> reportData, String templateName, String name) {
		// 引入模板
		List<List<Object[]>> reportDataAll = new ArrayList<List<Object[]>>();
		reportDataAll.add(reportData);
		workbook = getTemplate2(realpath, templateName);
		workbook = inputData(beginIndex, workbook, reportDataAll, 2);
//		sheet = workbook.getSheetAt(0);
//		for (int j = 0; j < reportData.size(); j++) {
//			Row row = sheet.createRow(beginIndex);
//			Object[] obj = reportData.get(j);
//			for(int k = 0; k < obj.length; k++ ){
//				Cell cell = row.createCell(k);
//				cell.setCellValue(obj[k]!=null?obj[k].toString():"");
//			}
//			beginIndex ++;
//		}
		// 导出文件
		return getFilePath2(realpath, GlobalConstants.EXCEL_REPORT_FOLDER, workbook, name);
	}
	
	/**
	 * 客户关系信息导出
	 * @param reportNo
	 * @param realpath
	 * @param reportData
	 * @return
	 */
	public String reportCustRel(int reportNo, String realpath, List<List<Object[]>> reportData, String name) {
		// 引入模板
		//workbook
		workbook = getTemplate2(realpath, GlobalConstants.EXCEL_TEMPLATE_CUSTREL);
		workbook = inputData(reportNo, workbook, reportData, reportFristRowIndexCust[reportNo-1]);
//		sheet = workbook.getSheetAt(0);
//		//writeWB = new SXSSFWorkbook(500);
//		//writeSheet = writeWB.createSheet();
//		// data info
//		for(int i = 0 ; i < reportData.size(); i++){
//			List<Object[]> temp = reportData.get(i);
//			for (int j = 0; j < temp.size(); j++) {
//				//Row row = sheet.createRow(j + reportFristRowIndexCust[reportNo-1]);
//				Row writeRow = sheet.createRow(j + reportFristRowIndexCust[reportNo-1]);
//				Object[] obj = temp.get(j);
//				for(int k = 0; k < obj.length; k++ ){
//					//Cell cell = row.createCell(k);
//					//cell.setCellValue(obj[k]!=null?obj[k].toString():"");
//					writeRow.createCell(k).setCellValue(obj[k]!=null?obj[k].toString():"");
//				}
//			}
//		}
		// 导出文件
		return getFilePath2(realpath, GlobalConstants.EXCEL_REPORT_FOLDER, workbook, name);
	}
	
	/**
	 * 客户关系信息导出
	 * @param reportNo
	 * @param realpath
	 * @param reportData
	 * @return
	 */
	public String reportCustRelErr(int reportNo, String realpath, List<List<Object[]>> reportData, String name) {
		// 引入模板
		workbook = getTemplate2(realpath, GlobalConstants.EXCEL_TEMPLATE_IMPCUSTREL);
		workbook = inputData(reportNo, workbook, reportData, 3);
//		sheet = workbook.getSheetAt(0);
////		HSSFRow Row row = null;
////		HSSFCell Cell cell = null;
//		// data info
//		for(int i = 0 ; i < reportData.size(); i++){
//			List<Object[]> temp = reportData.get(i);
//			for (int j = 0; j < temp.size(); j++) {
//				Row row = sheet.createRow(j + 3);
//				Object[] obj = temp.get(j);
//				for(int k = 0; k < obj.length; k++ ){
//					Cell cell = row.createCell(k);
//					cell.setCellValue(obj[k]!=null?obj[k].toString():"");
//				}
//			}
//		}
		// 导出文件
		return getFilePath2(realpath, GlobalConstants.EXCEL_REPORT_FOLDER, workbook, name);
	}
	
	
	/**
	 * 客户关系信息导出
	 * @param reportNo
	 * @param realpath
	 * @param reportData
	 * @return
	 */
	public String reportCustRelApproval(int reportNo, String realpath, List<List<Object[]>> reportData, String name) {
		// 引入模板
		workbook = getTemplate2(realpath, GlobalConstants.EXCEL_TEMPLATE_IMPCUSTRELAPPROVAL_CN);
		workbook = inputData(reportNo, workbook, reportData, reportFristRowIndexCust[reportNo-1]);
		// 导出文件
		return getFilePath2(realpath, GlobalConstants.EXCEL_REPORT_FOLDER, workbook, name);
	}
	
	/**
	 * 客户关系信息导出
	 * @param reportNo
	 * @param realpath
	 * @param reportData
	 * @return
	 */
	public String reportCustRelApprovalHistroy(int reportNo, String realpath, List<List<Object[]>> reportData, String name) {
		// 引入模板
		workbook = getTemplate2(realpath, GlobalConstants.EXCEL_TEMPLATE_CUSTRELAPPROVALHIS);
		workbook = inputData(reportNo, workbook, reportData, reportFristRowIndexCust[reportNo-1]);
		// 导出文件
		return getFilePath2(realpath, GlobalConstants.EXCEL_REPORT_FOLDER, workbook, name);
	}
	
	/**
	 * 模板的码值创建
	 * @param realpath
	 * @param name
	 * @return
	 */
	public String reportTemplate(String realpath, String name) {
		String templateName = "";
		// 引入模板
		Map<String, String> codeMapSpecialListKind = Maps.newHashMap();
		Map<String,String> custrelTypeMap = Maps.newHashMap();
		Map<String, String> codeMapPersonIdentType =  
				this.codeUtil.getDescCodeMapCustIdent(GlobalConstants.CODE_STR_PERSONIDENT_TYPE);
		Map<String, String> codeMapOrgIdentType = 
				this.codeUtil.getDescCodeMapCustIdent(GlobalConstants.CODE_STR_ORGIDENT_TYPE);
		//
		Map<String, String> custrelooTypeTemp = Maps.newHashMap();
		Map<String, String> custrelopTypeTemp = Maps.newHashMap();
		Map<String, String> custrelppTypeTemp = Maps.newHashMap();
		if(name.equals(GlobalConstants.EXCEL_TEMPLATE_IMPSPECIALLIST_CN)){
			codeMapSpecialListKind = 
				this.specialListBS.getDescCodeMapSpecialListKind(GlobalConstants.CODE_STR_SPECIALlIST_TYPE);
		}else{
			custrelTypeMap = 
				this.codeUtil.getDescCodeMap(GlobalConstants.CODE_STR_CUSTREL_TYPE);
			
			for(Map.Entry<String, String> entry : custrelTypeMap.entrySet()){
				long intCustrelType = Long.valueOf(entry.getValue());
				if(intCustrelType < 2001001000L && intCustrelType > 1001001000L){
					custrelooTypeTemp.put(entry.getKey(), entry.getValue());
				}else if(intCustrelType < 3000000000L && intCustrelType > 2001001000L){
					custrelopTypeTemp.put(entry.getKey(), entry.getValue());
				}else if(intCustrelType < 5000000000L && intCustrelType > 3000000000L){
					custrelppTypeTemp.put(entry.getKey(), entry.getValue());
				}
			}
			custrelTypeMap.clear();
		}
		//
		workbook = getTemplate2(realpath, name);
		sheet2 = workbook.createSheet("个人证件名称列表");
		int flag = 0;
		for(Map.Entry<String, String> entry : codeMapPersonIdentType.entrySet()){
			flag ++;
			Row row = sheet2.createRow(flag);
			Cell cell = row.createCell(0);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(entry.getKey());
		}
		sheet3 = workbook.createSheet("机构证件名称列表");
		flag = 0;
		for(Map.Entry<String, String> entry : codeMapOrgIdentType.entrySet()){
			flag ++;
			Row row = sheet3.createRow(flag);
			Cell cell = row.createCell(0);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(entry.getKey());
		}
		flag = 0;
		if(name.equals(GlobalConstants.EXCEL_TEMPLATE_IMPSPECIALLIST_CN)){
			templateName = "导入黑名单信息模板";
			sheet4 = workbook.createSheet("黑名单类别名称列表");
			for(Map.Entry<String, String> entry : codeMapSpecialListKind.entrySet()){
				flag ++;
				Row row = sheet4.createRow(flag);
				Cell cell = row.createCell(0);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(entry.getKey());
			}
		}else{
			templateName = "导入客户关系信息模板";
			sheet4 = workbook.createSheet("客户间关系名称列表");
			Row row0 = sheet4.createRow(++flag);
			Cell cell0 = row0.createCell(0);
			cell0.setCellType(Cell.CELL_TYPE_STRING);
			cell0.setCellValue("机构与机构间关系名称如下：");
			for(Map.Entry<String, String> entry : custrelooTypeTemp.entrySet()){
				flag ++;
				Row row = sheet4.createRow(flag);
				Cell cell = row.createCell(0);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(entry.getKey());
			}
			Row row1 = sheet4.createRow(++flag);
			Cell cell1 = row1.createCell(0);
			cell1.setCellType(Cell.CELL_TYPE_STRING);
			cell1.setCellValue("机构与个人间关系名称如下：");
			for(Map.Entry<String, String> entry : custrelopTypeTemp.entrySet()){
				flag ++;
				Row row = sheet4.createRow(flag);
				Cell cell = row.createCell(0);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(entry.getKey());
			}
			Row row2 = sheet4.createRow(++flag);
			Cell cell2 = row2.createCell(0);
			cell2.setCellType(Cell.CELL_TYPE_STRING);
			cell2.setCellValue("个人与个人间关系名称如下：");
			for(Map.Entry<String, String> entry : custrelppTypeTemp.entrySet()){
				flag ++;
				Row row = sheet4.createRow(flag);
				Cell cell = row.createCell(0);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(entry.getKey());
			}
		}
		// 导出文件
		return getFilePath2(realpath, GlobalConstants.EXCEL_REPORT_FOLDER, workbook, templateName);
	}
}
