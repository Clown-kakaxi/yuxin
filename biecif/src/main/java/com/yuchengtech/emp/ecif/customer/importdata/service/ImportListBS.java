/**
 * 
 */
package com.yuchengtech.emp.ecif.customer.importdata.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.biappframe.security.BiOneUser;
import com.yuchengtech.emp.ecif.base.common.GlobalConstants;
import com.yuchengtech.emp.ecif.base.util.CodeUtil;
import com.yuchengtech.emp.ecif.base.util.ConvertUtils;
import com.yuchengtech.emp.ecif.customer.cusrelo.service.CustomerRelationBS;
import com.yuchengtech.emp.ecif.customer.cusrelo.service.CustrelApprovalBS;
import com.yuchengtech.emp.ecif.customer.customerinfo.service.CustomerInfoBS;
import com.yuchengtech.emp.ecif.customer.entity.customer.Customer;
import com.yuchengtech.emp.ecif.customer.entity.customer.Custrel;
import com.yuchengtech.emp.ecif.customer.entity.customer.CustrelApproval;
import com.yuchengtech.emp.ecif.customer.entity.customerbaseorg.Orgidentinfo;
import com.yuchengtech.emp.ecif.customer.entity.customerbaseperson.PersonIdentifier;

/**
 * <pre>
 * Title:程序的中文名称
 * Description: 程序功能的描述 
 * </pre>
 * @author guanyb  guanyb@yuchengtech.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
@Service
public class ImportListBS extends BaseBS<Object> {
	
	@Autowired
	private CustomerInfoBS customerInfoBS;
	
//	@Autowired
//	private SpecialListBS specialListBS;
//	
//	@Autowired
//	private SpecialListApprovalBS specialListApprovalBS;
//	
//	@Autowired
//	private SuspectGroupBS suspectGroupBS;
//	
//	@Autowired
//	private CustMergeRecordBS custMergeRecordBS;
//	
//	@Autowired
//	private CustSplitRecordBS custSplitRecordBS;
	
	@Autowired
	private CustomerRelationBS customerRelationLookBS;
	
	@Autowired
	private CustrelApprovalBS custrelApprovalBS;
	
	@Autowired
	private CodeUtil codeUtil;
	
	@Autowired
	private ConvertUtils convertUtils;
	
	
	/**
	 * 获取  workbook
	 * @param fileName
	 * @return
	 */
	private Workbook getWorkbook(String fileName){
		if(fileName == null || "".equals(fileName)|| fileName.indexOf(".")== -1){
			return null;
		}
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
		Workbook wb = null;
		if("xls".equals(suffix)){
			//报表为2003
			 try {
				wb = new HSSFWorkbook(new FileInputStream(fileName));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else if("xlsx".equals(suffix)){
			 try {
				wb = new XSSFWorkbook(new FileInputStream(fileName)) ;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return wb;
	}
	
	/**
	 * 导入文件数据
	 * @param fileName 文件名称
	 * @param vnum	模版中数据起始行
	 * @return List<List<Object[]>> 返回各个sheet中的List<Object[]>
	 */
	public List<List<Object[]>> importFileData(String fileName, int vnum){
		// 获取报表数据
		List<List<Object[]>> reportData = null;
		try{
			Workbook wb = this.getWorkbook(fileName);
			if(wb == null){
				return null;
			}
			Sheet sheet = null;
			Row row = null;
			Cell cell = null;
			//配置参数 读取当前sheet页的  固定的 起始行和结束行 起始列数和结束列数 
			int startRowNum;//有效数据的 终止 列数
			int endRowNum;//有效数据的 终止 行数
			int startCellNum;//有效数据的 起始 列数    
			int	endCellNum;//有效数据的 终止 列数
			
			//存储一个row中数据
			Object[] objList = null;
			//这里ecif系统指定只读取第一个sheet中的数据 wb.getNumberOfSheets()=1
			int count = 1;
			
			reportData = new ArrayList<List<Object[]>>();
			//存储一个xls中数据
			for (int n = 0; n < count; n++) {
				sheet = wb.getSheetAt(n);
				if( sheet == null){
					break;
				}
				Object val = null;
				DecimalFormat df = new DecimalFormat("#.##");// 格式化数字
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 格式化日期字符串
				startRowNum = sheet.getFirstRowNum() + vnum;//有效数据的 起始 行数
				endRowNum = sheet.getPhysicalNumberOfRows();//有效数据的 终止 行数
				row = sheet.getRow(startRowNum-1);	//获取 标题行对象，根据标题行内容确定有效数据的起始和终止列数
				startCellNum = row.getFirstCellNum();//有效数据的 起始 列数    
				endCellNum = row.getLastCellNum();//有效数据的 终止 列数
				
				List<Object[]> objLists = new ArrayList<Object[]>();//当前sheet页保存
				//从有效数据行开始读取
				for (int i = startRowNum; i < endRowNum; i++) {
					//获得一行有效数据
					row = sheet.getRow(i);
					if (row == null) {
						continue;
					}
					
					objList = new Object[endCellNum];//一行数据数组
					//取出当前行中每一列数据
					for (int j = startCellNum; j < endCellNum; j++) {
						
						cell = row.getCell(j);
						if (cell == null) {
							objList[j]="";
							continue;
						}
						//根据不同数据格式进行存储
						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_STRING:
							val = cell.getStringCellValue();
							break;
						case Cell.CELL_TYPE_NUMERIC:
							if(HSSFDateUtil.isCellDateFormatted(cell)){
								val = sdf.format(HSSFDateUtil.getJavaDate(cell
										.getNumericCellValue()));
							}else{
								val = df.format(cell.getNumericCellValue());
							}
							/*if ("@".equals(cell.getCellStyle()
									.getDataFormatString())) {
								val = df.format(cell.getNumericCellValue());
							} else if ("General".equals(cell.getCellStyle()
									.getDataFormatString())) {
								val = df.format(cell.getNumericCellValue());
							} else {
								val = sdf.format(HSSFDateUtil.getJavaDate(cell
										.getNumericCellValue()));
							}*/
							break;
						case Cell.CELL_TYPE_BOOLEAN:
							val = cell.getBooleanCellValue();
							break;
						case Cell.CELL_TYPE_BLANK:
							val = "";
							break;
						 case Cell.CELL_TYPE_FORMULA: // 公式   
                              //val = cell.getCellFormula();
                              val = df.format(cell.getNumericCellValue());
                             break;
						default:
							val = cell.toString();
							break;
						}
						objList[j]=val;
					}
					objLists.add(objList);
				}
				//根据不同的sheet页保存
				reportData.add(objLists);
				//objLists.clear();//清空集合
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
		return reportData;//返回值
	}
	
	/**
	 * 导入黑名单
	 * @return
	 */
	public List<List<Object[]>> importSpecialList(String fileName, int vnum){
//		String result = "";
		List<List<Object[]>> reportData = importFileData(fileName, vnum);
//		if(reportData == null){
//			return null;
//		}
//		for(int i=0; i < reportData.size(); i++){
//			List<Object[]> temp = reportData.get(i);
//			if(temp == null){
//				continue;
//			}
//			if(i == 0){
//				saveSpecialList(temp);
//			}
//		}
		return reportData;
	}
	
	/**
	 * 导入黑名单待审批信息
	 * @return
	 */
	public List<List<Object[]>> importSpecialListApproval(String fileName, int vnum){
//		String result = "";
		List<List<Object[]>> reportData = importFileData(fileName, vnum);
//		if(reportData == null){
//			return null;
//		}
//		for(int i=0; i < reportData.size(); i++){
//			List<Object[]> temp = reportData.get(i);
//			if(temp == null){
//				continue;
//			}
//			if(i == 0){
//				saveSpecialListApproval(temp);
//			}
//		}
		return reportData;
	}
	
	/**
	 * 导入疑似客户待确认信息
	 * @return
	 */
	public List<List<Object[]>> importSuspectGroup(String fileName, int vnum){
//		String result = "";
		List<List<Object[]>> reportData = importFileData(fileName, vnum);
//		if(reportData == null){
//			return null;
//		}
//		for(int i=0; i < reportData.size(); i++){
//			List<Object[]> temp = reportData.get(i);
//			if(temp == null){
//				continue;
//			}
//			if(i == 0){
//				saveSuspectGroup(temp);
//			}
//		}
		return reportData;
	}
	
	/**
	 * 导入客户合并信息
	 * @return
	 */
	public List<List<Object[]>> importCustMergeRecord(String fileName, int vnum){
//		String result = "";
		List<List<Object[]>> reportData = importFileData(fileName, vnum);
//		if(reportData == null){
//			return null;
//		}
//		for(int i=0; i < reportData.size(); i++){
//			List<Object[]> temp = reportData.get(i);
//			if(temp == null){
//				continue;
//			}
//			if(i == 0){
//				saveCustMergeRecord(temp);
//			}
//		}
		return reportData;
	}
	
	/**
	 * 导入客户拆分信息
	 * @return
	 */
	public List<List<Object[]>> importCustSplitRecord(String fileName, int vnum){
//		String result = "";
		List<List<Object[]>> reportData = importFileData(fileName, vnum);
//		if(reportData == null){
//			return null;
//		}
//		for(int i=0; i < reportData.size(); i++){
//			List<Object[]> temp = reportData.get(i);
//			if(temp == null){
//				continue;
//			}
//			if(i == 0){
//				saveCustSplitRecord(temp);
//			}
//		}
		return reportData;
	}
	
//	/**
//	 * 检查并保存黑名单数据
//	 */
//	public void saveSpecialList(List<Object[]> data){
//		BiOneUser user = BiOneSecurityUtils.getCurrentUserInfo();
//		//List<SpecialList> list = new ArrayList<SpecialList>();
//		SpecialList sl = null;
//		for(Object[] o : data){
//			sl = new SpecialList();
//			sl.setSpecialListId(null);//主键
//			sl.setSpecialListType(GlobalConstants.SPECIALLIST_TYPE);//黑名单类型
//			sl.setSpecialListFlag("1");//黑名单标识
//			//客户编号	证件类型	证件号码	证件户名	
//			//列入原因	状态标志	起始日期	结束日期	
//			//更新系统	更新人	更新时间	交易流水号
//			Customer c = customerInfoBS.getCustIdByCustNo(o[0]!=null?o[0].toString():"");
//			sl.setCustId(c!=null?c.getCustId():0L);
//			//检验三证是否齐全
//			if(o[1] != null && !o[1].toString().equals("") ||
//					o[2] != null && !o[2].toString().equals("") ||
//					o[3] != null && !o[3].toString().equals("")){
//				sl.setIdentType(o[1].toString());
//				sl.setIdentNo(o[2].toString());
//				sl.setIdentCustName(o[3].toString());				
//			}else{
//				//
//			}
//			//检验包括必填项
//			if(o[4] != null && !o[4].toString().equals("") ||
//					o[5] != null && !o[5].toString().equals("")){
//				sl.setEnterReason(o[4].toString());
//				sl.setStatFlag(o[5].toString());
//			}else{
//				//
//			}
//			try {
//				Date tempDate = o[6]!=null?ConvertUtils.getDateStrToData(o[6].toString()):null;
//				Date tempDate2 = o[7]!=null?ConvertUtils.getDateStrToData(o[7].toString()):null;
//				sl.setStartDate(tempDate);
//				sl.setEndDate(tempDate2);
//			} catch (ParseException e) {
//				//
//			}
//			sl.setLastUpdateSys("ECF");
//			sl.setLastUpdateUser(user.getUserName());
//			sl.setLastUpdateTm(new Timestamp(new Date().getTime()));
//			sl.setTxSeqNo("");
//			sl.setApprovalFlag(GlobalConstants.APPROVAL_FLAG_0);
//			specialListBS.saveEntity(sl);
//			//list.add(sl);
//		}		
//	}
	
//	/**
//	 * 检查并保存黑名单待审批信息
//	 * @param data
//	 */
//	public String saveSpecialListApproval(List<Object[]> data){
//		int result = 0;
//		BiOneUser user = BiOneSecurityUtils.getCurrentUserInfo();
//		//List<SpecialList> list = new ArrayList<SpecialList>();
//		//SpecialListApproval sl = null;
//		int count = data.size();
//		for(Object[] o : data){
//			//sl = new SpecialListApproval();
//			//信息标识	证件户名	证件类型	证件号码	客户编号	黑名单分类	
//			//列入原因	状态标志	起始日期	结束日期	提交人	
//			//提交时间	操作状态	
//			//审批人	审批时间	审批状态	审批意见
//			if(o[0] != null && !o[0].toString().equals("") ||
//					o[13] != null && !o[13].toString().equals("") ||
//					o[14] != null && !o[14].toString().equals("") ||
//					o[15] != null && !o[15].toString().equals("")){
//				Date tempDate = null;
//				try {
//					tempDate = ConvertUtils.getDateStrToData(o[14].toString());
//					//sl.setApprovalTime(tempDate);
//				} catch (ParseException e) {
//					//
//				}
//				specialListApprovalBS.updateApprovalInfo(
//						Long.valueOf(o[0].toString()), 
//						o[13].toString(), 
//						tempDate, 
//						o[15].toString(), 
//						o[16]==null?"":o[16].toString());
//			}else{
//				result++;
//			}
//		}
//		if(result == 0){
//			return "导入" + count;
//		}else{
//			return "导入" + (count-result) + "条记录，错误" + result + "条";
//		}
//	}

//	/**
//	 * 检查并保存疑似客户确认信息
//	 * @param data
//	 */
//	public void saveSuspectGroup(List<Object[]> data){
//		//BiOneUser user = BiOneSecurityUtils.getCurrentUserInfo();
//		//List<SpecialList> list = new ArrayList<SpecialList>();
//		SuspectList sl = null;
//		for(Object[] o : data){
//			//分组标识	客户编号	疑似客户分组描述	疑似信息数据日期	疑似信息生成日期	
//			//疑似确认标志	疑似确认结果	合并处理标志	合并处理日期
//			if(o[0] != null && !o[0].toString().equals("") ||
//					o[1] != null && !o[1].toString().equals("") ||
//					o[5] != null && !o[5].toString().equals("") ||
//					o[6] != null && !o[6].toString().equals("")){			
//				sl = suspectGroupBS.getSuspectListByCustId(Long.valueOf(o[0].toString()), o[1].toString());
//				if(sl != null){
//					suspectGroupBS.updateComfirmInfo(
//							sl.getSuspectGroupId(), 
//							o[5].toString(), 
//							o[6].toString());					
//				}
//			}
//		}
//	}
	
//	/**
//	 * 保存客户合并信息
//	 */
//	public void saveCustMergeRecord(List<Object[]> data){
//		BiOneUser user = BiOneSecurityUtils.getCurrentUserInfo();
//		CustMergeRecord sl = null;
//		String result = "a,b,合并成功";
//		for(Object[] o : data){
//			//保留客户号	被合并客户号	合并提交人	合并提交时间
//			if(o[0] != null && !o[0].toString().equals("") ||
//					o[1] != null && !o[1].toString().equals("") ||
//					o[2] != null && !o[2].toString().equals("") ||
//					o[3] != null && !o[3].toString().equals("")){			
//				sl = new CustMergeRecord();
//				sl.setImportOperator(user.getUserName());
//				sl.setImportOperTime(new Timestamp(new Date().getTime()));
//				sl.setReserveCustId(o[0].toString());
//				sl.setMergedCustId(o[1].toString());
//				sl.setMergeOperator(o[2].toString());
//				try {
//					sl.setMergeOperTime(ConvertUtils.getStrToTimestamp2(o[3].toString()));
//				} catch (ParseException e) {
//					//
//				}
//				
//				sl.setMergeStat(result.split(",")[2]);
//				//根据结果改变疑似客户的合并状态
//				//if(result.split(",")[2].equals("")){//成功合并
//					SuspectList t1 = suspectGroupBS.getSuspectListGroupByCustId(o[0].toString());
//					SuspectList t2 = suspectGroupBS.getSuspectListGroupByCustId(o[1].toString());
//					if(t1 != null || t2 != null){//
//						if(t1.getSuspectGroupId() == t2.getSuspectGroupId()){//疑似
//							//疑似，非疑似标志（新增）
//							sl.setSuspectFlag(GlobalConstants.CUST_SUSPECT_1);
//							suspectGroupBS.updateMergeInfo(t1.getSuspectGroupId(), 
//									"已合并", new Date().toString());
//							suspectGroupBS.updateMergeInfo(t2.getSuspectGroupId(), 
//									"已合并", new Date().toString());
//						}else{//非疑似
//							//疑似，非疑似标志（新增）
//							sl.setSuspectFlag(GlobalConstants.CUST_SUSPECT_0);
//						}
//					}else{//非疑似
//						//疑似，非疑似标志（新增）
//						sl.setSuspectFlag(GlobalConstants.CUST_SUSPECT_0);
//					}
//				//}else{//失败合并
//				
//				//}
//				custMergeRecordBS.updateEntity(sl);
//			}
//		}
//	}
	
//	/**
//	 * 保存客户拆分信息
//	 */
//	public void saveCustSplitRecord(List<Object[]> data){
//		BiOneUser user = BiOneSecurityUtils.getCurrentUserInfo();
//		CustSplitRecord sl = null;
//		String result = "a,b,拆分成功";
//		for(Object[] o : data){
//			//保留客户号	被合并客户号	合并提交人	合并提交时间
//			if(o[0] != null && !o[0].toString().equals("") ||
//					o[1] != null && !o[1].toString().equals("") ||
//					o[2] != null && !o[2].toString().equals("") ||
//					o[3] != null && !o[3].toString().equals("")){			
//				sl = new CustSplitRecord();
//				sl.setImportOperator(user.getUserName());
//				sl.setImportOperTime(new Timestamp(new Date().getTime()));
//				sl.setReserveCustId(o[0].toString());
//				sl.setMergedCustId(o[1].toString());
//				sl.setSplitOperator(o[2].toString());
//				try {
//					sl.setSplitOperTimeDate(ConvertUtils.getStrToTimestamp2(o[3].toString()));
//				} catch (ParseException e) {
//					//
//				}
//				sl.setSplitStat(result.split(",")[2]);
//				//根据结果改变疑似客户的合并状态
//				//if(result.split(",")[2].equals("")){//成功拆分
//					SuspectList t1 = suspectGroupBS.getSuspectListGroupByCustId(o[0].toString());
//					SuspectList t2 = suspectGroupBS.getSuspectListGroupByCustId(o[1].toString());
//					if(t1 != null || t2 != null){//
//						if(t1.getSuspectGroupId() == t2.getSuspectGroupId()){//疑似
//							suspectGroupBS.updateMergeInfo(t1.getSuspectGroupId(), 
//									"已拆分", new Date().toString());
//							suspectGroupBS.updateMergeInfo(t2.getSuspectGroupId(), 
//									"已拆分", new Date().toString());
//						}
//					}
//				//}else{//失败拆分
//				
//				//}				
//				custSplitRecordBS.updateEntity(sl);
//			}
//		}
//	}
	
	/**
	 * 导入客户关系信息
	 * vnum 从第几行开始读取数据
	 * @return
	 */
	public List<List<Object[]>> importCustrelList(String fileName, int vnum){
//		String result = "";
		List<List<Object[]>> reportData = importFileData(fileName, vnum);
//		if(reportData == null){
//			return null;
//		}
//		for(int i=0; i < reportData.size(); i++){
//			List<Object[]> temp = reportData.get(i);
//			if(temp == null){
//				continue;
//			}
//			if(i == 0){
//				saveCustrelList(temp);
//			}
//		}
//		return result;
		return reportData;
	}
	
	
	/**
	 * 检查并保存客户关系单数据
	 */
	public void saveCustrelList(List<Object[]> data){
		
		BiOneUser user = BiOneSecurityUtils.getCurrentUserInfo();
		
		//读取数据
		for(Object[] o : data){
			
			Custrel custrel = new Custrel();
			custrel.setCustRelId(null);//主键
		
			//源客户证件类型	源客户证件号码	源客户证件户名	源客户号	目标客户证件类型	目标客户证件号码	目标客户证件户名	目标客户号	客户间关系类型	客户间关系状态	关系开始日期	关系结束日期
			boolean isfull = isfull(o[0].toString().trim(), o[1].toString().trim(), o[2].toString().trim(), o[4].toString().trim(), o[5].toString().trim(), o[6].toString().trim(),o[8].toString().trim(),o[9].toString().trim(),o[10].toString().trim(),o[11].toString().trim(),o[3].toString().trim(),o[7].toString().trim());
			boolean isSrcRight = isRightThreeCert(o[3].toString(),o[0].toString(),o[1].toString(),o[2].toString());
			boolean isDestRight = isRightThreeCert(o[7].toString(),o[5].toString(),o[6].toString(),o[7].toString());
			
			//所有导入信息都正确
			if(isfull == true){
				
				//是否输入源客户号
				if(o[3].toString().trim() != null && !"".equals(o[3].toString().trim())){
					//判断客户号是否正确
					if(isSrcRight){
						//是同一人设置客户标识
						Customer customer = customerInfoBS.getCustIdByCustNo(o[3].toString());
						custrel.setSrcCustId(customer.getCustId());
					}else{
						//不是同一个人
						continue;
					}
				}else{
					String flag = isPersonOrg(o[0].toString().trim());
					//没输入源客户号，但是3证信息是正确的，向证件表里插入数据
					if("per".equals(flag)){
						saveId(o[0].toString(), o[1].toString(), o[2].toString(), flag, custrel,"src");
					}
					//机构
					if("org".equals(flag)){
						saveId(o[0].toString(), o[1].toString(), o[2].toString(), flag, custrel,"src");
						
					}
				}
				
				
				//是否输入目标客户号
				if(o[7].toString().trim() != null && !"".equals(o[7].toString().trim())){
					//判断目标客户号是否正确
					if(isDestRight){
						Customer customer = customerInfoBS.getCustIdByCustNo(o[7].toString());
						custrel.setDestCustId(customer.getCustId());
					}else{
						//不是同一个人
						continue;
					}
				}else{
					String flag = isPersonOrg(o[4].toString().trim());
					//个人
					if("per".equals(flag)){
						saveId(o[4].toString(), o[5].toString(), o[6].toString(), flag, custrel,"dest");
					}
					//机构
					if("org".equals(flag)){
						saveId(o[4].toString(), o[5].toString(), o[6].toString(), flag, custrel,"dest");
					}
				}
				
			}else{
				continue;
			}
			
			//关系类型
			saveRelType(o[8].toString().trim(),custrel);
			
			//关系状态
			if("有效".equals(o[9].toString().trim())){
				custrel.setCustRelStat("1");
			}else if("无效".equals(o[9].toString().trim())){
				custrel.setCustRelStat("2");
			}
			

			try {
				//关系开始日期
				custrel.setRelStartDate(ConvertUtils.getDateStrToData(o[10].toString()));
				//关系结束日期
				custrel.setRelEndDate(ConvertUtils.getDateStrToData(o[11].toString()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//最后更新人
			custrel.setLastUpdateUser(user.getUserName());
			//最后更新系统
			custrel.setLastUpdateSys("ecif");
			
//			custrel.setApprovalFlag("02");//默认审批通过
			custrel.setApprovalFlag(GlobalConstants.APPROVAL_FLAG_0);
			
			customerRelationLookBS.updateEntity(custrel);
		}		
	}
	
	
	/**
	 * 导入客户关系审批信息
	 * @return
	 */
	public List<List<Object[]>> importCustrelApprovalList(String fileName, int vnum){
//		String result = "";
		List<List<Object[]>> reportData = importFileData(fileName, vnum);
//		if(reportData == null){
//			return null;
//		}
//		for(int i=0; i < reportData.size(); i++){
//			List<Object[]> temp = reportData.get(i);
//			if(temp == null){
//				continue;
//			}
//			if(i == 0){
//				saveCustrelApprovalList(temp);
//			}
//		}
//		return result;
		return reportData;
	}
	
	/**
	 * 保存客户关系审批信息
	 * @param data
	 */
	public void saveCustrelApprovalList(List<Object[]> data){
		BiOneUser user = BiOneSecurityUtils.getCurrentUserInfo();
		//List<SpecialList> list = new ArrayList<SpecialList>();    
		CustrelApproval custrelApproval = null;
		for(Object[] o : data){
			custrelApproval = new CustrelApproval();
			custrelApproval.setApprovalOperator(user.getUserName());
			//判断导入的审批id是否存在
			if(isApproalExist(o[0].toString(),custrelApproval)){
				
				//判断提交人和审批人是否是同一人
		    	if(custrelApproval.getOperator().equals(custrelApproval.getApprovalOperator())){
		    		custrelApproval.setApprovalStat("01");
		    	}
		    	
		    	//设置审批状态
		    	if("审批通过".equals(o[11].toString().trim())){
		    		custrelApproval.setApprovalStat(GlobalConstants.APPROVAL_STAT_2);
		    	}else if("审批未通过".equals(o[11].toString().trim())){
		    		custrelApproval.setApprovalStat(GlobalConstants.APPROVAL_STAT_3);
		    	}else{
		    		//没填写审批状态，或者填错审批状态
		    		continue;
		    	}
		    	
		    	//设置审批时间
		    	custrelApproval.setApprovalTime((new Timestamp(new Date().getTime())));
				
			    //审批通过
			    if(GlobalConstants.APPROVAL_STAT_2.equals(custrelApproval.getApprovalStat())){
			    	
			    	//insert or update
					if("01".equals(custrelApproval.getOperStat()) || "02".equals(custrelApproval.getOperStat())){
						
						Custrel custrel = new Custrel();
						custrel.setCustRelId(custrelApproval.getCustRelId());
						custrel.setCustRelType(custrelApproval.getCustRelType());
						custrel.setCustRelDesc(custrelApproval.getCustRelDesc());
						custrel.setCustRelStat(custrelApproval.getCustRelStat());
						custrel.setSrcCustId(custrelApproval.getSrcCustId());
						custrel.setDestCustId(custrelApproval.getDestCustId());
						custrel.setRelStartDate(custrelApproval.getRelStartDate());
						custrel.setRelEndDate(custrelApproval.getRelEndDate());
						custrel.setLastUpdateSys("ecif");
						custrel.setLastUpdateUser(custrelApproval.getOperator());
						custrel.setLastUpdateTm(custrelApproval.getOperTime());
						custrel.setTxSeqNo("");
						custrel.setApprovalFlag(custrelApproval.getApprovalStat());
						
						this.customerRelationLookBS.updateEntity(custrel);
					}
					//delete
					if("03".equals(custrelApproval.getOperStat())){
						this.customerRelationLookBS.removeEntityById(custrelApproval.getCustRelId());
					}
			    }
			    
			    //审批未通过
			    if("03".equals(custrelApproval.getApprovalStat())){
			    	//insert 
					if("01".equals(custrelApproval.getOperStat()) ){
						custrelApproval.setApprovalStat(GlobalConstants.APPROVAL_STAT_3);
					}else{
						
						if("02".equals(custrelApproval.getOperStat())){
							Custrel custrel = customerRelationLookBS.getEntityById(custrelApproval.getCustRelId());
							custrel.setApprovalFlag(GlobalConstants.APPROVAL_FLAG_0);
							customerRelationLookBS.updateEntity(custrel);
						}
						if("03".equals(custrelApproval.getOperator())){
							customerRelationLookBS.removeEntityById(custrelApproval.getCustRelId());
						}
					}
			    	
			    }
			    
			    this.custrelApprovalBS.updateEntity(custrelApproval);
				
			}else{
		    //不存在
				continue;
			}
			
			custrelApprovalBS.updateEntity(custrelApproval);
		}		
	}
	
	/**
	 * 验证证件类型等信息填写的是否正确
	 * 源客户证件类型	源客户证件号码	源客户证件户名	源客户号	目标客户证件类型	目标客户证件号码	目标客户证件户名	目标客户号	客户间关系类型	客户间关系状态  关系开始时间 关系结束时间
	 */
	public  boolean isfull(String srcIdentType,String srcIdentNo,String srcIdentName,String destIdentType,String destIdentNo,String destIdentName,String custrelType,String custrelState,String relStartDate,String relEndDate,String srcNo,String destNo){
		boolean flag = false;
		boolean isNotNull = false;
		//输入数据不为空
		if(srcIdentType != null && !"".equals(srcIdentType) && srcIdentNo != null && !"".equals(srcIdentNo) 
				&& srcIdentName != null && !"".equals(srcIdentName) && destIdentType != null && !"".equals(destIdentType) 
					&& destIdentNo != null && !"".equals(destIdentNo) && destIdentName != null && !"".equals(destIdentName)
					 && custrelType != null && !"".equals(custrelType) && custrelState!= null && !"".equals(custrelState) 
					 	&& relStartDate != null && !"".equals(relStartDate) && relEndDate != null && !"".equals(relEndDate)){
			
			//源客户号和目标客户号不为同一个客户
			if(!srcIdentNo.equals(destIdentNo)){
				isNotNull = true;
			}
			
			//源客户号，目标客户号，关系类型都不为空
			if(srcNo != null && !"".equals(srcNo) && destNo != null && !"".equals(destNo)){
				isNotNull = isNoTypeRight(srcNo,destNo,custrelType);
			}
			
		}
		
		
		
		//关系开始日期和关系结束日期的判断
		boolean isDateRight = false;
		try {
			isDateRight = convertUtils.getDateStrToLong(relStartDate) < convertUtils.getDateStrToLong(relEndDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//证件类型和关系类型都正确
		boolean  isIdentRelType = false;
		isIdentRelType = isPerOrgTypeRight(srcIdentType,destIdentType,custrelType);
		
		//判断证件类型和证件号码是否正确
		boolean isIdentTypeNo = false;
		isIdentTypeNo = isTypeNo(srcIdentType,srcIdentNo) && isTypeNo(destIdentType,destIdentNo);
		
		//判断关系类型是否正确
		boolean custrelTypeFlag = false;
		Map<String,String> custrelMap = codeUtil.getDescCodeMap(GlobalConstants.CODE_STR_CUSTREL_TYPE);
		if(custrelMap.get(custrelType) != null && !"".equals(custrelMap.get(custrelMap))){
			custrelTypeFlag = true;
		}
		
		//判断关系状态是否正确
		boolean custrelStatFlag = false;
		Map<String,String> custrelStatMap = codeUtil.getDescCodeMap(GlobalConstants.CODE_STR_VALID_TYPE);
		if(custrelStatMap.get(custrelState) != null && !"".equals(custrelState)){
			custrelStatFlag = true;
		}
		
		flag = isNotNull && isIdentTypeNo && custrelTypeFlag && custrelStatFlag && isIdentRelType;  
		
		return flag;
	}
	
	/**
	 * 判断输入证件类型和证件号码是否正确
	 */
	public boolean isTypeNo(String type,String no){
		boolean flag = false;
		boolean typeFlag = false;
		boolean noFlag = false;
		
		Map<String,String> perMap = codeUtil.getDescCodeMap(GlobalConstants.CODE_STR_PERSONIDENT_TYPE);
		Map<String,String> orgMap = codeUtil.getDescCodeMap(GlobalConstants.CODE_STR_ORGIDENT_TYPE);
		
		String perTypeCode = perMap.get(type);
		String orgTypeCode = orgMap.get(type);
		
		//证件类型正确
		if(perTypeCode != null ||  orgTypeCode != null ){
			typeFlag = true;
		}
		
		//只能为数字
		String onlyNum = "^[0-9]*{1}";
		//判断身份证号是否正确,判断前十五位是否是数字
		if("居民身份证".equals(type)){
			no = no.substring(0,15);
			noFlag = no.matches(onlyNum);
		}else{
			//大于6位，且不能有空格
			if(no.length() > 6 && (no.indexOf(" ") == -1)){
				noFlag = true;
			}
		}
		
		if(typeFlag && noFlag){
			flag = true;
		}
		
		return flag;
	}
	
	/**
	 * 3证齐全并且有客户号,判断是否是同一用户
	 * @return
	 */
	public boolean isRightThreeCert(String custNo , String identType, String identNo, String identCustName){
		
		boolean flag = false;
		
		Customer c = customerInfoBS.getCustIdByCustNo(custNo);
			//查看该客户的三证是否对应
		if(c!=null){
			//机构
			if(GlobalConstants.CUST_ORG_TYPE.equals(c.getCustType())){
				PersonIdentifier person = customerInfoBS.getPersonIdentifierByCustId(identType,identNo,identCustName);
				if(person.getCustId() == c.getCustId()){
					flag = true;
				}
			}else if(GlobalConstants.CUST_PERSON_TYPE.equals(c.getCustType())){
				Orgidentinfo org = customerInfoBS.getOrgidentinfoByCustId(identType,identNo,identCustName);
				if(org.getCustId() == c.getCustId()){
					flag = true;
				}
			}
		}
		
		return flag;
	}
	
	
	/**
	 * 判断是个人还是机构
	 * 变量 证件类型
	 */
	public  String isPersonOrg(String desc){
		
		String flag = "";
		
		StringBuffer sqlPer = new StringBuffer(" SELECT STD_CODE_DESC val,STD_CODE code FROM TX_STD_CODE WHERE STATE = '0' AND STD_CODE.STD_CATE = '"+GlobalConstants.CODE_STR_PERSONIDENT_TYPE+"' ");
		StringBuffer sqlOrg = new StringBuffer(" SELECT STD_CODE_DESC val,STD_CODE code FROM TX_STD_CODE WHERE STATE = '0' AND STD_CODE.STD_CATE = '"+GlobalConstants.CODE_STR_ORGIDENT_TYPE+"' ");

		List<Object[]> objListPer = this.baseDAO.findByNativeSQLWithIndexParam(sqlPer.toString());
		List<Object[]> objListOrg = this.baseDAO.findByNativeSQLWithIndexParam(sqlOrg.toString());
		Map<String,String> map = null;
		List<Map<String,String>> listPer = new ArrayList<Map<String,String>>();
		List<Map<String,String>> listOrg = new ArrayList<Map<String,String>>();
		
		for(Object[] obj : objListPer){
			map = new HashMap<String,String>();
			map.put("stdCodeDesc", obj[0] != null ? obj[0].toString() : "");
			map.put("stdCode", obj[1] != null ? obj[1].toString() : "");
			listPer.add(map);
		}
		
		for(Object[] obj : objListOrg){
			map = new HashMap<String,String>();
			map.put("stdCodeDesc", obj[0] != null ? obj[0].toString() : "");
			map.put("stdCode", obj[1] != null ? obj[1].toString() : "");
			listOrg.add(map);
		}
		
		for(Map<String,String> mapPer : listPer ){
			if(desc.equals(mapPer.get("stdCodeDesc"))){
				flag = "per";
				break;
			}else{
				continue;
			}
		}
		
		for(Map<String,String> mapOrg : listOrg ){
			if(desc.equals(mapOrg.get("stdCodeDesc"))){
				flag = "org";
				break;
			}else{
				continue;
			}
		}
		
		return flag;

	}
	
	/**
	 * 保存客户标识,切向证件表里插入数据
	 * 
	 */
	public void saveId(String identType, String identNo, String identCustName,String flag,Custrel custrel,String srcOrDest){
		
		Map<String,String> perMap = codeUtil.getDescCodeMap(GlobalConstants.CODE_STR_PERSONIDENT_TYPE);
		Map<String,String> orgMap = codeUtil.getDescCodeMap(GlobalConstants.CODE_STR_ORGIDENT_TYPE);
		identType = perMap.get(identType);
		if(	identType == null || "".equals(identType)){
			identType = orgMap.get(identType);
		}
		 
		
		if("per".equals(flag)){
			//是个人客户，查找个人证件表，判断是否有该客户存在,存在保存客户标识，不存在生成客户标识，将证件信息存入证件表
			PersonIdentifier personidentifier = customerInfoBS.getPersonIdentifierByCustId(identType,identNo,identCustName);
			if(personidentifier == null){
				//没有客户号，切证件类型中也没有客户标识，生成客户标识
				String sql = "select SEQ_CUST_ID.NEXTVAL FROM SYSIBM.SYSDUMMY1";
				
				List objList = this.baseDAO.findByNativeSQLWithIndexParam(sql);
				//保存证件信息
				personidentifier = new PersonIdentifier();
				personidentifier.setIdentId(null);
                
				BigInteger b = (BigInteger)(objList.get(0));
				personidentifier.setCustId(Long.parseLong(b.toString()));
				personidentifier.setIdentType(identType);
				personidentifier.setIdentNo(identNo);
				personidentifier.setIdentCustName(identCustName);
				this.baseDAO.save(personidentifier);
				
				//设置客户关系标识,判断是源客户还是目标客户
				if("src".equals(srcOrDest)){
					custrel.setSrcCustId(Long.parseLong(b.toString()));
				}else if("dest".equals(srcOrDest)){
					custrel.setDestCustId(Long.parseLong(b.toString()));
				}
				
			}else{
				//设置客户关系标识,判断是源客户还是目标客户
				if("src".equals(srcOrDest)){
					custrel.setSrcCustId(personidentifier.getCustId());
				}else if("dest".equals(srcOrDest)){
					custrel.setDestCustId(personidentifier.getCustId());
				}
			}
		}
		
		if("org".equals(flag)){
			//是机构客户，查找个人证件表，判断是否有该客户存在,存在保存客户标识，不存在生成客户标识，将证件信息存入证件表
			Orgidentinfo orgidentinfo = customerInfoBS.getOrgidentinfoByCustId(identType,identNo,identCustName);
			if(orgidentinfo == null){
				//没有客户号，切证件类型中也没有客户标识，生成客户标识
				String sql = "SELECT SEQ_CUST_ID.NEXTVAL  FROM SYSIBM.SYSDUMMY1";
				//看一下返回值是啥
				List objList = this.baseDAO.findByNativeSQLWithIndexParam(sql);
				//保存证件信息
				orgidentinfo = new Orgidentinfo();
				orgidentinfo.setIdentId(null);
				BigInteger b = (BigInteger)(objList.get(0));
				orgidentinfo.setCustId(Long.parseLong(b.toString()));
				orgidentinfo.setIdentType(identType);
				orgidentinfo.setIdentNo(identNo);
				orgidentinfo.setIdentCustName(identCustName);
				this.baseDAO.save(orgidentinfo);
				//设置客户关系标识
				
				if("src".equals(srcOrDest)){
					custrel.setSrcCustId(Long.parseLong(b.toString()));
				}else if("dest".equals(srcOrDest)){
					custrel.setDestCustId(Long.parseLong(b.toString()));
				}
			}else{
				if("src".equals(srcOrDest)){
					custrel.setSrcCustId(orgidentinfo.getCustId());
				}else if("dest".equals(srcOrDest)){
					custrel.setDestCustId(orgidentinfo.getCustId());
				}
			}
		}
		
	}
	
	/**
	 * 保存关系类型
	 */
	public void saveRelType(String relType,Custrel custrel){
		//域名标识
		String flag = GlobalConstants.CODE_STR_CUSTREL_TYPE;
		//返回所有查询值
		Map<String, String> codeMap = Maps.newHashMap();
		
		codeMap = this.codeUtil.getDescCodeMap(flag);
		String stdCode = codeMap.get(relType);
		
		custrel.setCustRelType(stdCode);
	}
	
	/**
	 * 判断导入的审批标识是否存在
	 */
	public boolean isApproalExist(String approvalRelId,CustrelApproval custrelApproval){
		boolean flag = false;
		Long approvalRelIdLong = 0L;
		if(!"".equals(approvalRelId)){
			approvalRelIdLong= Long.parseLong(approvalRelId);
		}
		
//		StringBuffer sql = new StringBuffer(" SELECT custrelApproval FROM CUSTREL_APPROVAL custrelApproval where custrelApproval.CUSTREL_APPROVAL_ID = ?0 ");
//		CustrelApproval custrelApprovalData = this.baseDAO.findUniqueWithIndexParam(sql.toString(),approvalRelIdLong);
		CustrelApproval custrelApprovalData = custrelApprovalBS.getEntityById(approvalRelIdLong);
		
		if(custrelApprovalData != null && GlobalConstants.APPROVAL_STAT_1.equals(custrelApprovalData.getApprovalStat())){
			 custrelApproval.setCustrelApprovalId(custrelApprovalData.getCustrelApprovalId()); 
			 custrelApproval.setCustRelId(custrelApprovalData.getCustRelId()); 
			 custrelApproval.setCustRelType(custrelApprovalData.getCustRelType()); 
			 custrelApproval.setCustRelDesc(custrelApprovalData.getCustRelDesc()); 
			 custrelApproval.setCustRelStat(custrelApprovalData.getCustRelStat()); 
			 custrelApproval.setSrcCustId(custrelApprovalData.getSrcCustId()); 
			 custrelApproval.setDestCustId(custrelApprovalData.getDestCustId()); 
			 custrelApproval.setRelStartDate(custrelApprovalData.getRelStartDate()); 
			 custrelApproval.setRelEndDate(custrelApprovalData.getRelEndDate()); 
			 custrelApproval.setOperator(custrelApprovalData.getOperator()); 
			 custrelApproval.setOperTime(custrelApprovalData.getOperTime()); 
			 custrelApproval.setOperStat(custrelApprovalData.getOperStat()); 
			 flag = true;
		}
		
		return flag;
	}
	
	/**
	 * 判断新增的用户是否已经在审批表中或者已经在关系表中
	 */
	public boolean isNoTypeRight( String srcCustNo,String destCustNo,String custrelType) {
		
		boolean flag = true;
		
		Customer srcCustomer = customerInfoBS.getCustIdByCustNo(srcCustNo);
		Customer destCustomer = customerInfoBS.getCustIdByCustNo(destCustNo);
		
		//存在返回true
		boolean custrelFlag = customerRelationLookBS.isExistCustRel(srcCustomer.getCustId(), destCustomer.getCustId(), custrelType);
		
		//存在返回true
		boolean custrelAprovalFlag = custrelApprovalBS.isExistCustRelApproval(srcCustomer.getCustId(), destCustomer.getCustId(), custrelType);
		
		//在关系表中有该条数据，或者在关系审批表中这条数据在待审批
		if(custrelFlag == true || custrelAprovalFlag == true){
			flag = false;
		}
		
		return flag;
	}
	
	/**
	 * 判断源证件类型，目标证件类型和关系类型是否正确
	 */
	public boolean isPerOrgTypeRight(String srcIdentType,String destIdentType,String custrelType){
		boolean flag = false;
		
		Map<String,String> perMap = codeUtil.getDescCodeMap(GlobalConstants.CODE_STR_PERSONIDENT_TYPE);
		Map<String,String> orgMap = codeUtil.getDescCodeMap(GlobalConstants.CODE_STR_ORGIDENT_TYPE);
		Map<String,String> custrelTypeMap = codeUtil.getDescCodeMap(GlobalConstants.CODE_STR_CUSTREL_TYPE);
		
		//返回关系的码值
		long intCustrelType = Long.parseLong(custrelTypeMap.get(custrelType));
		
		String srcType = "";
		String destType = "";
		
		//判断源客户证件类型
		if(perMap.get(srcIdentType) != null){
			srcType= "per";
		}else if(orgMap.get(srcIdentType) != null){
			srcType= "org";
		}
		
		//判断目标客户证件类型
		if(perMap.get(destIdentType) != null){
			destType = "per";
		}else if(orgMap.get(destIdentType) != null){
			destType= "org";
		}
		
		
		if("org".equals(srcType) && "org".equals(destType) && intCustrelType < 2001001000L && intCustrelType > 1001001000L){
			flag = true;
		}else if("org".equals(srcType) && "per".equals(destType) && intCustrelType < 3000000000L && intCustrelType > 2001001000L){
			flag = true;
		}else if("per".equals(srcType) && "per".equals(destType) && intCustrelType < 5000000000L && intCustrelType > 3000000000L){
			flag = true;
		}
		
		return flag ;
	}
	
	
	
}
