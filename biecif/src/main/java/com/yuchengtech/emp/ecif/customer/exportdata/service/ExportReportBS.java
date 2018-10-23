/**
 * 
 */
package com.yuchengtech.emp.ecif.customer.exportdata.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.util.DialectUtils;
import com.yuchengtech.emp.ecif.base.common.GlobalConstants;
import com.yuchengtech.emp.ecif.base.core.ExportReport;
import com.yuchengtech.emp.ecif.base.util.CodeUtil;
import com.yuchengtech.emp.ecif.base.util.DictTranslationUtil;
import com.yuchengtech.emp.ecif.customer.simplegroup.service.PerBS;
import com.yuchengtech.emp.ecif.customer.special.service.SpecialListAABS;
import com.yuchengtech.emp.ecif.customer.suspect.service.SuspectGroupBS;

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
public class ExportReportBS extends BaseBS<Object> {
	
	@Autowired
	private DictTranslationUtil dictTranslationUtil;
	
	protected static Logger log = LoggerFactory.getLogger(PerBS.class);

	@Autowired
	private ExportReport er;
	
	@Autowired
	private CodeUtil codeUtil;
	
	@Autowired
	private SpecialListAABS specialListBS;
	
	@Autowired
	private SuspectGroupBS suspectGroupBS;
	
	@Autowired
	private com.yuchengtech.emp.bione.common.BioneDataSource bioneDataSource;
	
	private Map<String, String> codeMapValidType = Maps.newHashMap();//有效标识
	private Map<String, String> codeMapApprovalStatus = Maps.newHashMap();//审批状态
	private Map<String, String> codeMapDisStatus = Maps.newHashMap();//操作状态
	private Map<String, String> codeMapIdentType = Maps.newHashMap();//个人与机构证件类型
	private Map<String, String> codeMapSpecialListKind = Maps.newHashMap();//黑名单类型
	private Map<String, String> codeMapSCustType = Maps.newHashMap();//客户类型标识
	
	private Map<String, String> codeMapComfirmType = Maps.newHashMap();//确认标识
	private Map<String, String> codeMapMergeType = Maps.newHashMap();//合并标识
	private Map<String, String> codeMapSuspectType = Maps.newHashMap();//疑似标识

	/**
	 * 模板中加入码值（黑名单，客户关系）
	 * @param realpath
	 * @param name
	 * @return
	 */
	public String reportTemplate(String realpath, String name) {
		return this.er.reportTemplate(realpath, name);
	}
	
	/**
	 * 生成报表入口
	 * @param reportNo
	 * @param param1
	 * @param param2
	 * @param param3
	 * @return
	 */
	public String export(int reportNo, String param1, String param2,
			String param3, String param4, String param5, String param6) {//
		// 获取报表数据
		List<List<Object[]>> reportData = null;
		switch (reportNo) {
		case 1:
			initSpecialListMap();
			reportData = getReportSpecialListData(param1, param2, param3);
			break;
		case 2:
			initSpecialListMap();
			reportData = getReportSpecialListApprovalData(param1, param2, param3, param5, param6);
			break;
		case 3:
			initSpecialListMap();
			reportData = getReportSpecialListApprovalHistroyData(param1, param2, param3, param4, param5, param6);
			break;
		case 4:
			initSuspectCustMap();
			reportData = getReportCustMergeRecordData(param1, param2);
			break;
		case 5:
			initSuspectCustMap();
			reportData = getReportCustSplitRecordData(param1, param2);
			break;
		case 6:
			initSuspectCustMap();
			reportData = getReportSuspectCustData(param1, param2, param3);
			break;
		case 7:
			reportData = getReportCustMergeRecordDataApp(param1, param2);
			break;
		case 8:
			initSpecialListMap();
			reportData = getReportCustMergeRecordDataAppHis(param1, param2, param3);
			break;
		case 9:
			reportData = getReportCustSplitRecordDataApp(param1, param2);
			break;
		case 10:
			initSpecialListMap();
			reportData = getReportCustSplitRecordDataAppHis(param1, param2, param3);
			break;
		}
		// 生成excel文件
		return this.er.createExcel(reportNo, reportData);
	}
	
	/**
	 * 
	 * @param reportNo
	 * @param custNo
	 * @param identNo
	 * @return
	 */
	public String export(int reportNo, List<List<Object[]>> errorData){
		return this.er.createExcel(reportNo, errorData);
	}
	
	/**
	 * 
	 * @param reportNo
	 * @param custNo
	 * @param identNo
	 * @return
	 */
	public String exportCustrel(int reportNo, List<List<Object[]>> errorData){
		return this.er.createCustrelExcel(reportNo, errorData);
	}
	
	/**
	 * 
	 * @param reportNo
	 * @param srcNo
	 * @param destNo
	 * @param cusrel
	 * @param lastUpdateSys
	 * @return
	 */
	public String exportCustrel(int reportNo,String rule) {
		// 获取报表数据
		List<List<Object[]>> reportData = null;
		switch (reportNo) {
		case 1:
//			reportData = getCustrelData(srcNo, destNo, custRelType,lastUpdateSys);
			reportData = getCustrelData(rule);
			break;
		case 2:
			reportData = getCustrelApprovalData(rule);
			break;
		case 3:
			reportData = getCustrelApprovalHistroyData(rule);
			break;
		}
		// 生成excel文件
		return this.er.createCustrelExcel(reportNo, reportData);
	}
	
	/**
	 * 导出个人类客户信息
	 * 
	 * @param reportNo
	 * @param tabNo
	 * @param param1
	 * @param param2
	 * @param param3
	 * @return
	 */
	public String exportPerInfo(int firstResult, int pageSize, int reportNo, int tabNo, String param1,
			String param2, String param3) {
		List<Object[]> reportData = null;
		switch (reportNo) {
		case 1:
			reportData = getReportPerBasicData(firstResult, pageSize, tabNo, param1, param2, param3);
			break;
		case 2:
			reportData = getReportPerFocusData(firstResult, pageSize, tabNo, param1, param2, param3);
			break;
		case 3:
			reportData = getReportPerProductData(firstResult, pageSize, tabNo, param1, param2, param3);
			break;
		case 4:
			reportData = getReportPerAnalysisData(firstResult, pageSize, tabNo, param1, param2, param3);
			break;
		case 5:
			reportData = getReportPerRelativeData(firstResult, pageSize, tabNo, param1, param2, param3);
			break;
		}
		return this.er.createPerExcel(reportNo, reportData);
	}

	public String exportOrgInfo(int firstResult, int pageSize, int reportNo, int tabNo, String param1,
			String param2, String param3) {
		List<Object[]> reportData = null;
		switch (reportNo) {
		case 1:
			reportData = getReportOrgBasicData(firstResult, pageSize, tabNo, param1, param2, param3);
			break;
		case 2:
			reportData = getReportOrgFocusData(firstResult, pageSize, tabNo, param1, param2, param3);
			break;
		case 3:
			reportData = getReportOrgProductData(firstResult, pageSize, tabNo, param1, param2, param3);
			break;
		case 4:
			reportData = getReportOrgAnalysisData(firstResult, pageSize, tabNo, param1, param2, param3);
			break;
		case 5:
			reportData = getReportOrgRelativeData(firstResult, pageSize, tabNo, param1, param2, param3);
			break;
		}
		return this.er.createOrgExcel(reportNo, reportData);
	}

	/**
	 * 通过条件获得黑名单信息数据
	 * 
	 * @param custNo
	 * @param identNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<List<Object[]>> getReportSpecialListData(String custNo,
			String identNo, String lastUpdateSys) {
		List<List<Object[]>> reportData = new ArrayList<List<Object[]>>();
		//证件户名	证件类型	证件号码	客户编号	黑名单分类	列入原因	状态标志	起始日期	结束日期
		String sql = "SELECT s.IDENT_TYPE,s.IDENT_NO,s.IDENT_CUST_NAME,c.CUST_NO, "
				+ " s.SPECIAL_LIST_KIND,s.ENTER_REASON,s.STAT_FLAG,s.START_DATE,s.END_DATE "
				+ " FROM M_CI_SPECIALLIST s left join M_CI_CUSTOMER c on s.CUST_ID=c.CUST_ID "
				+ " WHERE s.SPECIAL_LIST_TYPE=?0 ";
		if (!StringUtils.isEmpty(custNo)) {
			sql += " and c.CUST_NO = '" + custNo + "'";
		}
		if (!StringUtils.isEmpty(identNo)) {
			sql += " and s.IDENT_NO = '" + identNo + "'";
		}
		if (!StringUtils.isEmpty(lastUpdateSys)) {
			sql += " and s.SPECIAL_LIST_KIND = '" + lastUpdateSys + "'";
		}
		//sql += " and (s.APPROVAL_FLAG <> ?1 or s.APPROVAL_FLAG is null) ";
		sql += " order by s.CUST_ID asc ";
		List<Object[]> list = this.baseDAO.createNativeQueryWithIndexParam(
				sql,
				GlobalConstants.SPECIALLIST_TYPE)
//				.setFirstResult(GlobalConstants.FIRSTRESULT)
//				.setMaxResults(GlobalConstants.MAXRESULT)
				.getResultList();
//		List<Object[]> list = getListByDB2(temp);
		for(Object[] o : list){
			o[1] = codeMapIdentType.get(o[1].toString())!=null?codeMapIdentType.get(o[1].toString()):o[1];
			o[4] = codeMapSpecialListKind.get(o[4].toString())!=null?codeMapSpecialListKind.get(o[4].toString()):o[4];
			o[6] = codeMapValidType.get(o[6].toString())!=null?codeMapValidType.get(o[6].toString()):o[6];
		}
		reportData.add(list);
		return reportData;
	}

	/**
	 * 通过条件获得黑名单审批历史信息数据
	 * 
	 * @param custNo客户编号
	 * @param identNo证件号码
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<List<Object[]>> getReportSpecialListApprovalHistroyData(
			String custNo, String identNo, String lastUpdateSys, String specialListKind,
			String srptMonth, String erptMonth) {
		List<List<Object[]>> reportData = new ArrayList<List<Object[]>>();
		//证件户名	证件类型	证件号码	客户编号	黑名单分类	列入原因	状态标志	起始日期	结束日期	提交人	提交时间	操作状态	
		//审批人	审批时间	审批状态	审批意见
		String sql = "SELECT s.IDENT_CUST_NAME,s.IDENT_TYPE,s.IDENT_NO,c.CUST_NO,s.SPECIAL_LIST_KIND, "
				+ " s.ENTER_REASON,s.STAT_FLAG,s.START_DATE,s.END_DATE,s.OPERATOR,s.OPER_TIME,s.OPER_STAT, "
				+ " s.APPROVAL_OPERATOR,s.APPROVAL_TIME,s.APPROVAL_STAT,s.APPROVAL_NOTE "
				+ " FROM APP_SPECIALLIST_APPROVAL s left join M_CI_CUSTOMER c on s.CUST_ID=c.CUST_ID"
				+ " WHERE 1=1 and s.APPROVAL_STAT <> ?0 ";
		if (!StringUtils.isEmpty(custNo)) {
			sql += " and c.CUST_NO = '" + custNo + "'";
		}
		if (!StringUtils.isEmpty(identNo)) {
			sql += " and s.IDENT_NO = '" + identNo + "'";
		}
		if (!StringUtils.isEmpty(lastUpdateSys)) {
			sql += " and s.APPROVAL_STAT = '" + lastUpdateSys + "'";
		}
		if (!StringUtils.isEmpty(specialListKind)) {
			sql += " and s.SPECIAL_LIST_KIND = '" + specialListKind + "'";
		}
		if(!StringUtils.isEmpty(srptMonth)) {
			sql += " and s.APPROVAL_TIME >='"+srptMonth+" 00:00:00'";
		}
		if(!StringUtils.isEmpty(erptMonth)) {
			sql += " and s.APPROVAL_TIME <='"+erptMonth+" 23:59:59'";
		}
		sql += " order by s.APPROVAL_TIME desc ";
		List<Object[]> list = this.baseDAO.createNativeQueryWithIndexParam(sql,
				GlobalConstants.APPROVAL_STAT_1)
//				.setFirstResult(GlobalConstants.FIRSTRESULT)
//				.setMaxResults(GlobalConstants.MAXRESULT)
				.getResultList();
//		List<Object[]> list = getListByDB2(temp);
		for(Object[] o : list){
			o[1] = codeMapIdentType.get(o[1].toString())!=null?codeMapIdentType.get(o[1].toString()):o[1];
			o[4] = codeMapSpecialListKind.get(o[4].toString())!=null?codeMapSpecialListKind.get(o[4].toString()):o[4];
			o[6] = codeMapValidType.get(o[6].toString())!=null?codeMapValidType.get(o[6].toString()):o[6];
			o[11] = codeMapDisStatus.get(o[11].toString())!=null?codeMapDisStatus.get(o[11].toString()):o[11];
			o[14] = codeMapApprovalStatus.get(o[14].toString())!=null?codeMapApprovalStatus.get(o[14].toString()):o[14];
		}
		reportData.add(list);
		return reportData;
	}

	/**
	 * 通过条件获得黑名单待审批信息数据
	 * 
	 * @param custNo客户编号
	 * @param identNo证件号码
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<List<Object[]>> getReportSpecialListApprovalData(String custNo,
			String identNo, String specialListKind, String srptMonth, String erptMonth) {
		List<List<Object[]>> reportData = new ArrayList<List<Object[]>>();
		//信息标识	证件户名	证件类型	证件号码	客户编号	黑名单分类	列入原因	状态标志	起始日期	结束日期	提交人	提交时间	操作状态	
		//审批人 审批时间	审批状态	审批意见（待审批信息没有审批相关信息）
		String sql = "SELECT s.SPECIALLIST_APPROVAL_ID,s.IDENT_CUST_NAME,s.IDENT_TYPE,s.IDENT_NO,c.CUST_NO,s.SPECIAL_LIST_KIND, "
				+ " s.ENTER_REASON,s.STAT_FLAG,s.START_DATE,s.END_DATE,s.OPERATOR,s.OPER_TIME,s.OPER_STAT "
				+ " FROM APP_SPECIALLIST_APPROVAL s left join M_CI_CUSTOMER c on s.CUST_ID=c.CUST_ID"
				+ " WHERE 1=1 and s.APPROVAL_STAT = ?0 ";
		if (!StringUtils.isEmpty(custNo)) {
			sql += " and c.CUST_NO = '" + custNo + "'";
		}
		if (!StringUtils.isEmpty(identNo)) {
			sql += " and s.IDENT_NO = '" + identNo + "'";
		}
		if (!StringUtils.isEmpty(specialListKind)) {
			sql += " and s.SPECIAL_LIST_KIND = '" + specialListKind + "'";
		}
		if (!StringUtils.isEmpty(srptMonth)) {
			sql += " and s.OPER_TIME >='"+srptMonth+" 00:00:00'";
		}
		if (!StringUtils.isEmpty(erptMonth)) {
			sql += " and s.OPER_TIME <='" +erptMonth+" 23:59:59'";
		}
		sql += " order by s.OPER_TIME desc ";
		List<Object[]> list = this.baseDAO.createNativeQueryWithIndexParam(sql,
				GlobalConstants.APPROVAL_STAT_1)
//				.setFirstResult(GlobalConstants.FIRSTRESULT)
//				.setMaxResults(GlobalConstants.MAXRESULT)
				.getResultList();
//		List<Object[]> list = getListByDB2(temp);
		for(Object[] o : list){
			o[2] = codeMapIdentType.get(o[2].toString())!=null?codeMapIdentType.get(o[2].toString()):o[2];
			o[5] = codeMapSpecialListKind.get(o[5].toString())!=null?codeMapSpecialListKind.get(o[5].toString()):o[5];
			o[7] = codeMapValidType.get(o[7].toString())!=null?codeMapValidType.get(o[7].toString()):o[7];
			o[12] = codeMapDisStatus.get(o[12].toString())!=null?codeMapDisStatus.get(o[12].toString()):o[12];
		}
		reportData.add(list);
		return reportData;
	}

	/**
	 * 通过条件获得合并历史信息数据
	 * 
	 * @param custNo保留客户号
	 * @param identNo被合并客户号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<List<Object[]>> getReportCustMergeRecordData(String custNo,
			String identNo) {
		List<List<Object[]>> reportData = new ArrayList<List<Object[]>>();
		//合并标识	保留客户号	被合并客户号	合并提交人	合并提交时间	导入人员	导入时间	合并状态	疑似客户标志
		String sql = "SELECT c.MERGE_RECORD_ID,c.RESERVE_CUST_NO,c.MERGED_CUST_NO, "
				+ " c.MERGE_OPERATOR,c.MERGE_OPER_TIME," +
//				"c.IMPORT_OPERATOR, " 
//				+ " c.IMPORT_OPER_TIME," +
				"c.MERGE_STAT,c.SUSPECT_FLAG "
				+ " FROM APP_CUST_MERGE_RECORD c WHERE 1=1 ";
		if (!StringUtils.isEmpty(custNo)) {
			sql += " and c.RESERVE_CUST_NO = '" + custNo + "'";
		}
		if (!StringUtils.isEmpty(identNo)) {
			sql += " and c.MERGED_CUST_NO = '" + identNo + "'";
		}
		sql += " order by c.MERGE_OPER_TIME desc ";
		List<Object[]> list = this.baseDAO.createNativeQueryWithIndexParam(sql)
//				.setFirstResult(GlobalConstants.FIRSTRESULT)
//				.setMaxResults(GlobalConstants.MAXRESULT)
				.getResultList();
//		List<Object[]> list = getListByDB2(temp);
		for(Object[] o : list){
			o[5] = codeMapMergeType.get(o[5].toString())!=null?codeMapMergeType.get(o[5].toString()):o[5];
			o[6] = codeMapSuspectType.get(o[6].toString())!=null?codeMapSuspectType.get(o[6].toString()):o[6];
		}
		reportData.add(list);
		return reportData;
	}
	
	/**
	 * 通过条件获得合并历史信息数据
	 * 
	 * @param custNo保留客户号
	 * @param identNo被合并客户号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<List<Object[]>> getReportCustMergeRecordDataApp(String custNo,
			String identNo) {
		List<List<Object[]>> reportData = new ArrayList<List<Object[]>>();
		//标识 保留客户号	被合并客户号	合并提交人	合并提交时间	导入人员	导入时间
		String sql = " SELECT c.CUST_MERGE_RECORD_APPROVAL_ID," +
				" c.RESERVE_CUST_NO,c.MERGED_CUST_NO, "
				+ " c.MERGE_OPERATOR,c.MERGE_OPER_TIME," +
				" c.IMPORT_OPERATOR, c.IMPORT_OPER_TIME" 
				+ " FROM APP_CUST_MERGE_RECORD_APPROVAL c WHERE 1=1 ";
		if (!StringUtils.isEmpty(custNo)) {
			sql += " and c.RESERVE_CUST_NO = '" + custNo + "'";
		}
		if (!StringUtils.isEmpty(identNo)) {
			sql += " and c.MERGED_CUST_NO = '" + identNo + "'";
		}
		sql += " order by c.IMPORT_OPER_TIME desc ";
		List<Object[]> list = this.baseDAO.createNativeQueryWithIndexParam(sql)
//				.setFirstResult(GlobalConstants.FIRSTRESULT)
//				.setMaxResults(GlobalConstants.MAXRESULT)
				.getResultList();
//		List<Object[]> list = getListByDB2(temp);
//		for(Object[] o : temp){
//			o[5] = codeMapMergeType.get(o[5].toString())!=null?codeMapMergeType.get(o[5].toString()):o[5];
//			o[6] = codeMapSuspectType.get(o[6].toString())!=null?codeMapSuspectType.get(o[6].toString()):o[6];
//		}
		reportData.add(list);
		return reportData;
	}
	
	/**
	 * 通过条件获得合并历史信息数据
	 * 
	 * @param custNo保留客户号
	 * @param identNo被合并客户号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<List<Object[]>> getReportCustMergeRecordDataAppHis(String custNo,
			String identNo, String approval) {
		List<List<Object[]>> reportData = new ArrayList<List<Object[]>>();
		//保留客户号	被合并客户号	合并提交人	合并提交时间	导入人员	导入时间
		//审批人	审批时间	审批状态	审批意见
		String sql = " SELECT c.RESERVE_CUST_NO,c.MERGED_CUST_NO, "
				+ " c.MERGE_OPERATOR,c.MERGE_OPER_TIME," +
				" c.IMPORT_OPERATOR, c.IMPORT_OPER_TIME,"
				+ " c.APPROVAL_OPERATOR, c.APPROVAL_TIME, c.APPROVAL_STAT, c.APPROVAL_NOTE "
				+ " FROM APP_CUST_MERGE_RECORD_APPROVAL c WHERE 1=1 ";
		if (!StringUtils.isEmpty(custNo)) {
			sql += " and c.RESERVE_CUST_NO = '" + custNo + "'";
		}
		if (!StringUtils.isEmpty(identNo)) {
			sql += " and c.MERGED_CUST_NO = '" + identNo + "'";
		}
		if (!StringUtils.isEmpty(approval)) {
			sql += " and c.APPROVAL_STAT = '" + approval + "'";
		}
		sql += " order by c.IMPORT_OPER_TIME desc ";
		List<Object[]> list = this.baseDAO.createNativeQueryWithIndexParam(sql)
//				.setFirstResult(GlobalConstants.FIRSTRESULT)
//				.setMaxResults(GlobalConstants.MAXRESULT)
				.getResultList();
//		List<Object[]> list = getListByDB2(temp);
		for(Object[] o : list){
			o[8] = codeMapApprovalStatus.get(o[8].toString())!=null?codeMapApprovalStatus.get(o[8].toString()):o[8];
		}
		reportData.add(list);
		return reportData;
	}

	/**
	 * 通过条件获得拆分历史信息数据
	 * 
	 * @param custNo保留客户号
	 * @param identNo被合并客户号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<List<Object[]>> getReportCustSplitRecordData(String custNo,
			String identNo) {
		List<List<Object[]>> reportData = new ArrayList<List<Object[]>>();
		//拆分标识	保留客户号	被合并客户号	拆分提交人	拆分提交时间	导入人员	导入时间	拆分状态
		String sql = "SELECT c.SPLIT_RECORD_ID,c.RESERVE_CUST_NO,c.MERGED_CUST_NO,"
				+ " c.SPLIT_OPERATOR,c.SPLIT_OPER_TIME," +
//				"c.IMPORT_OPERATOR,c.IMPORT_OPER_TIME," +
				"c.SPLIT_STAT "
				+ " FROM APP_CUST_SPLIT_RECORD c WHERE 1=1 ";
		if (!StringUtils.isEmpty(custNo)) {
			sql += " and c.RESERVE_CUST_NO = '" + custNo + "'";
		}
		if (!StringUtils.isEmpty(identNo)) {
			sql += " and c.MERGED_CUST_NO = '" + identNo + "'";
		}
		sql += " order by c.SPLIT_OPER_TIME desc ";
		List<Object[]> list = this.baseDAO.createNativeQueryWithIndexParam(sql)
//				.setFirstResult(GlobalConstants.FIRSTRESULT)
//				.setMaxResults(GlobalConstants.MAXRESULT)
				.getResultList();
//		List<Object[]> list = getListByDB2(temp);
		for(Object[] o : list){
			o[5] = codeMapMergeType.get(o[5].toString())!=null?codeMapMergeType.get(o[5].toString()):o[5];
		}
		reportData.add(list);
		return reportData;
	}
	
	/**
	 * 通过条件获得拆分历史信息数据
	 * 
	 * @param custNo保留客户号
	 * @param identNo被合并客户号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<List<Object[]>> getReportCustSplitRecordDataApp(String custNo,
			String identNo) {
		List<List<Object[]>> reportData = new ArrayList<List<Object[]>>();
		//标识	被合并客户号	拆分提交人	拆分提交时间	导入人员	导入时间	
		//审批人	审批时间	审批状态	审批意见
		String sql = "SELECT c.CUST_SPLIT_RECORD_APPROVAL_ID, c.MERGED_CUST_NO,"
				+ " c.SPLIT_OPERATOR,c.SPLIT_OPER_TIME," +
				" c.IMPORT_OPERATOR,c.IMPORT_OPER_TIME " 
				+ " FROM APP_CUST_SPLIT_RECORD_APPROVAL c WHERE 1=1 ";
		if (!StringUtils.isEmpty(custNo)) {
			sql += " and c.RESERVE_CUST_NO = '" + custNo + "'";
		}
		if (!StringUtils.isEmpty(identNo)) {
			sql += " and c.MERGED_CUST_NO = '" + identNo + "'";
		}
		sql += " order by c.IMPORT_OPER_TIME desc ";
		List<Object[]> list = this.baseDAO.createNativeQueryWithIndexParam(sql)
//				.setFirstResult(GlobalConstants.FIRSTRESULT)
//				.setMaxResults(GlobalConstants.MAXRESULT)
				.getResultList();
//		List<Object[]> list = getListByDB2(temp);
//		for(Object[] o : temp){
//			o[5] = codeMapMergeType.get(o[5].toString())!=null?codeMapMergeType.get(o[5].toString()):o[5];
//		}
		reportData.add(list);
		return reportData;
	}
	
	/**
	 * 通过条件获得拆分历史信息数据
	 * 
	 * @param custNo保留客户号
	 * @param identNo被合并客户号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<List<Object[]>> getReportCustSplitRecordDataAppHis(String custNo,
			String identNo, String approval) {
		List<List<Object[]>> reportData = new ArrayList<List<Object[]>>();
		//被合并客户号	拆分提交人	拆分提交时间	导入人员	导入时间
		//审批人	审批时间	审批状态	审批意见
		String sql = "SELECT c.MERGED_CUST_NO,"
				+ " c.SPLIT_OPERATOR,c.SPLIT_OPER_TIME," +
				" c.IMPORT_OPERATOR,c.IMPORT_OPER_TIME," +
				" c.APPROVAL_OPERATOR, c.APPROVAL_TIME, c.APPROVAL_STAT, c.APPROVAL_NOTE "
				+ " FROM APP_CUST_SPLIT_RECORD_APPROVAL c WHERE 1=1 ";
		if (!StringUtils.isEmpty(custNo)) {
			sql += " and c.RESERVE_CUST_NO = '" + custNo + "'";
		}
		if (!StringUtils.isEmpty(identNo)) {
			sql += " and c.MERGED_CUST_NO = '" + identNo + "'";
		}
		if (!StringUtils.isEmpty(approval)) {
			sql += " and c.APPROVAL_STAT = '" + approval + "'";
		}
		sql += " order by c.IMPORT_OPER_TIME desc ";
		List<Object[]> list = this.baseDAO.createNativeQueryWithIndexParam(sql)
//				.setFirstResult(GlobalConstants.FIRSTRESULT)
//				.setMaxResults(GlobalConstants.MAXRESULT)
				.getResultList();
//		List<Object[]> list = getListByDB2(temp);
		for(Object[] o : list){
			o[7] = codeMapApprovalStatus.get(o[7].toString())!=null?codeMapApprovalStatus.get(o[7].toString()):o[7];
		}
		reportData.add(list);
		return reportData;
	}

	/**
	 * 疑似客户信息
	 * 
	 * @param custNo
	 *            客户编号
	 * @param identNo
	 *            客户类型
	 * @param lastUpdateSys
	 *            确认标志
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<List<Object[]>> getReportSuspectCustData(String custNo,
			String identNo, String lastUpdateSys) {
		List<List<Object[]>> reportData = new ArrayList<List<Object[]>>();
		//分组标识 客户编号 客户名称 客户类型 (疑似信息数据日期) 机构编号 疑似信息生成日期 合并处理标志 合并处理日期 列入原因
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ");
		sql.append(" l.SUSPECT_GROUP_ID, l.CUST_NO, l.CUST_NAME, l.CUST_TYPE, c.CREATE_BRANCH_NO, ");
		sql.append(" /*g.SUSPECT_DATA_DATE,*/ g.SUSPECT_SEARCH_DATE, ");
//		sql.append(" sl.SUSPECT_CONFIRM_FLAG, sl.SUSPECT_CONFIRM_RESULT, ");
//		sql.append(" sl.SUSPECT_CONFIRM_OPERATOR, ");
		sql.append(" l.MERGE_DEAL_FLAG, l.MERGE_DEAL_DATE, g.ENTER_REASON ");
		sql.append(" FROM APP_SUSPECT_LIST l ");
		sql.append(" left join APP_SUSPECT_GROUP g on l.SUSPECT_GROUP_ID=g.SUSPECT_GROUP_ID ");
		sql.append(" left join M_CI_CUSTOMER c on l.CUST_NO=c.CUST_NO ");
//		sql.append(" left join NAMETITLE n on l.CUST_ID=n.CUST_ID ");
		sql.append(" WHERE 1=1 ");
		if (!StringUtils.isEmpty(custNo)) {
			Long groupId = suspectGroupBS.getSuspectListByCustNo(custNo);
			sql.append(" and l.SUSPECT_GROUP_ID =" + groupId);
		}
		if (!StringUtils.isEmpty(identNo)) {
			if(identNo.equals("1")){
				sql.append(" and l.CUST_TYPE = '1' ");
			}else{
				sql.append(" and (l.CUST_TYPE = '2' or l.CUST_TYPE = '3') ");
			}
			//sql.append(" and l.CUST_TYPE = '" + identNo + "' ");
		}
//		if (!StringUtils.isEmpty(lastUpdateSys)) {
//			sql += " and sl.SUSPECT_CONFIRM_FLAG = '" + lastUpdateSys + "'";
//		}
		sql.append(" order by l.suspect_Group_Id ");
		List<Object[]> list = this.baseDAO.createNativeQueryWithIndexParam(sql.toString())
				//.setFirstResult(GlobalConstants.FIRSTRESULT)
				//.setMaxResults(GlobalConstants.MAXRESULT)
				.getResultList();
		//List<Object[]> list = getListByDB2(temp);
		for(Object[] o : list){
			if(o[3] != null){
				o[3] = codeMapSCustType.get(o[3].toString())!=null?codeMapSCustType.get(o[3].toString()):o[3];
			}
//			if(o[5] != null){
//				o[5] = codeMapComfirmType.get(o[5].toString())!=null?codeMapComfirmType.get(o[5].toString()):o[5];
//			}
			if(o[6] != null){
				o[6] = codeMapMergeType.get(o[6].toString())!=null?codeMapMergeType.get(o[6].toString()):"未合并";
			}else{
				o[6] = "未合并";
			}
			if(o[8] != null){
				o[8] = findFlag(o[8].toString());
			}
		}
		reportData.add(list);
		return reportData;
	}
	
	public String findFlag(String target){
		//疑似规则
		String result = "";
		//标志位
		if(target == null || target.equals("") || target.length() != 7){
			target = "0000000";//疑似七个规则
		}
		char[] temp = target.toCharArray();
		if(temp[0] == '1'){
			result += "规则1.";
		}
		if(temp[1] == '1'){
			result += "规则2.";
		}
		if(temp[2] == '1'){
			result += "规则3.";
		}
		if(temp[3] == '1'){
			result += "规则4.";
		}
		if(temp[4] == '1'){
			result += "规则5.";
		}
		if(temp[5] == '1'){
			result += "规则6.";
		}
		if(temp[6] == '1'){
			result += "规则7.";
		}
		return result;		
	}

	// 个人类客户基本信息
	@SuppressWarnings("unchecked")
	public List<Object[]> getReportPerBasicData(int firstResult, int pageSize, int tabNo, String param1,
			String param2, String param3) {
//		List<Object[]> customerlist = null;
		StringBuffer sqlbegin = new StringBuffer();
//		sqlbegin.append(" SELECT A.CUST_NO, A.CUST_ID FROM M_CI_CUSTOMER A ");
		sqlbegin.append(" SELECT A.CUST_ID FROM M_CI_CUSTOMER A ");
	
		sqlbegin.append(" WHERE 1 = 1 ");
		String sql = "";
		try {
			sql = perSplitJointSql(sqlbegin.toString(), tabNo, param1, param2, param3);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
//		SearchResult<Object[]> customerResults = this.baseDAO
//				.findPageWithNameParamByNativeSQL(firstResult, pageSize, sql,
//						null);
//		customerlist = customerResults.getResult();
		List<Object[]> list = this.baseDAO.createNativeQueryWithIndexParam(sql)
//			.setFirstResult(GlobalConstants.FIRSTRESULT)
//			.setMaxResults(GlobalConstants.MAXRESULT)
			.getResultList();
//		List<Object[]> list = getListByDB2(customerlist);
		String custIds = "";
//		for (Object[] obj : list) {
//			custIds = custIds + obj[1] + ",";
//		}
		custIds = "aaa";
		if(custIds.length() > 0){
			custIds = custIds.substring(0, custIds.length()-1);
			StringBuffer sqlleft = new StringBuffer();
			sqlleft.append(" SELECT A.CUST_NO,B.CUST_NAME,C.GENDER,C.BIRTHDAY,C.CITIZENSHIP,C.NATIONALITY,C.MARRIAGE,D.CAREER_TYPE,D.DUTY,C.HIGHEST_DEGREE,C.HIGHEST_SCHOOLING,E.IS_EMPLOYEE,E.IS_IMPORTANT_CUST,F.LIFECYCLE_STAT_TYPE ");
			sqlleft.append(" FROM M_CI_CUSTOMER A ");
			sqlleft.append(" LEFT JOIN M_CI_NAMETITLE B ON A.CUST_ID=B.CUST_ID ");
			sqlleft.append(" LEFT JOIN M_CI_PERSON C ON A.CUST_ID=C.CUST_ID ");
			sqlleft.append(" LEFT JOIN M_CI_PER_CAREER D ON A.CUST_ID=D.CUST_ID ");
			sqlleft.append(" LEFT JOIN M_CI_PER_KEYFLAG E ON A.CUST_ID=E.CUST_ID ");
			sqlleft.append(" LEFT JOIN M_CI_LIFECYCLE F ON A.CUST_ID=F.CUST_ID ");
//			sqlleft.append(" WHERE A.CUST_ID IN (");
//			sqlleft.append(custIds);
//			sqlleft.append(") "); 
//			sqlleft.append(" ORDER BY A.CUST_ID ");
			sqlleft.append(" WHERE A.CUST_ID IN (");
			sqlleft.append(sql); 
			sqlleft.append(") ORDER BY A.CUST_ID  "); 
			List<Object[]> customerleftlist = this.baseDAO.findByNativeSQLWithIndexParam(sqlleft.toString());
			customerleftlist = this.dictTranslationUtil.setDictPerBasicByListObject(customerleftlist);
			return customerleftlist;
		}
		return null;
	}


	// 个人类客户客户关注事件
	@SuppressWarnings("unchecked")
	public List<Object[]> getReportPerFocusData(int firstResult, int pageSize, int tabNo, String param1,
			String param2, String param3) {
		StringBuffer sqlbegin = new StringBuffer();
		sqlbegin.append(" SELECT A.CUST_NO,B.CUST_NAME,c.EVENT_TYPE,c.SUB_EVENT_TYPE,c.EVENT_DESC,c.EVENT_OCCUR_DATE,c.EVENT_REG_DATE,c.EVENT_REG_TELLER_NO,c.EVENT_REG_BRC ");
		sqlbegin.append(" FROM M_CI_CUSTOMER A ");
		sqlbegin.append(" LEFT JOIN M_CI_NAMETITLE B ON A.CUST_ID=B.CUST_ID ");
		sqlbegin.append(" LEFT JOIN M_CI_EVENT_INFO C ON A.CUST_ID=C.CUST_ID ");
//		sqlbegin.append(" LEFT JOIN ATTENTEVENT D ON C.EVENT_ID=D.EVENT_ID ");
//		sqlbegin.append(" LEFT JOIN EVENTINFO E ON D.EVENT_ID = E.EVENT_ID ");
		sqlbegin.append(" WHERE (C.EVENT_TYPE = '20' or C.EVENT_TYPE = '30') "); // 事件类型为客户关注事件类型
		
		String sql = "";
		try {
			sql = perSplitJointSql(sqlbegin.toString(), tabNo, param1, param2, param3);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
//		SearchResult<Object[]> customerResults = this.baseDAO
//				.findPageWithNameParamByNativeSQL(firstResult, pageSize, sql,
//						null);
//		List<Object[]> customerlist = customerResults.getResult();
		List<Object[]> customerlist = this.baseDAO.createNativeQueryWithIndexParam(sql)
//			.setFirstResult(GlobalConstants.FIRSTRESULT)
//			.setMaxResults(GlobalConstants.MAXRESULT)
			.getResultList();
//		List<Object[]> list = getListByDB2(customerlist);
		customerlist = this.dictTranslationUtil.setDictPerFocusByListObject(customerlist);
		return customerlist;
	}
	
	// 个人类客户产品信息
	@SuppressWarnings("unchecked")
	public List<Object[]> getReportPerProductData(int firstResult, int pageSize, int tabNo, 
			String param1, String param2, String param3) {
		List<Object[]> customerlist = null;
		StringBuffer sqlbegin = new StringBuffer();
		sqlbegin.append(" SELECT A.CUST_NO,B.CUST_NAME,D.PROD_CODE,D.PROD_NAME,D.PROD_TYPE,E.BRAND_NAME,E.BUSI_CHAR,E.PROD_CLASS,E.PROD_FORM,E.PROD_STAT,E.PROD_PATENT,E.START_DATE,E.END_DATE,E.OWN_SALE_FLAG,D.PROD_SUMMARY ");
		sqlbegin.append(" FROM M_CI_CUSTOMER A ");
		sqlbegin.append(" INNER JOIN M_CI_NAMETITLE B ON A.CUST_ID=B.CUST_ID ");
		sqlbegin.append(" INNER JOIN M_CI_CUSTPRODUCTREL C ON A.CUST_ID=C.CUST_ID ");
		sqlbegin.append(" INNER JOIN M_CI_PRODUCT D ON C.PROD_ID=D.PROD_ID ");
		sqlbegin.append(" INNER JOIN M_CI_PRODUCTBASICINFO E ON C.PROD_ID=E.PROD_ID ");
		sqlbegin.append(" WHERE 1 = 1 ");
		String sql = "";
		try {
			sql = perSplitJointSql(sqlbegin.toString(), tabNo, param1, param2, param3);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
//		SearchResult<Object[]> customerResults = this.baseDAO
//				.findPageWithNameParamByNativeSQL(firstResult, pageSize, sql, null);
//		customerlist = customerResults.getResult();
		customerlist = this.baseDAO.createNativeQueryWithIndexParam(sql)
//			.setFirstResult(GlobalConstants.FIRSTRESULT)
//			.setMaxResults(GlobalConstants.MAXRESULT)
			.getResultList();
//		List<Object[]> list = getListByDB2(customerlist);
		customerlist = this.dictTranslationUtil.setDictPerProductByListObject(customerlist);
		return customerlist;
	}

	// 个人类客户关联信息
	@SuppressWarnings("unchecked")
	public List<Object[]> getReportPerRelativeData(int firstResult, int pageSize, int tabNo,
			String param1, String param2, String param3) {
		List<Object[]> customerlist = null;
		StringBuffer sqlbegin = new StringBuffer();
		sqlbegin.append(" SELECT A.CUST_NO SRCCUSTNO,N1.CUST_NAME SRCNAME,C2.CUST_NO DESTCUSTNO,N2.CUST_NAME DESTNAME,OBJ.CUST_REL_TYPE,OBJ.CUST_REL_DESC,OBJ.REL_START_DATE,OBJ.REL_END_DATE ");
		sqlbegin.append(" FROM M_CI_CUSTREL OBJ ");
		sqlbegin.append(" LEFT JOIN M_CI_CUSTOMER A ON OBJ.SRC_CUST_ID=A.CUST_ID ");
		sqlbegin.append(" LEFT JOIN M_CI_CUSTOMER C2 ON OBJ.DEST_CUST_ID=C2.CUST_ID ");
		sqlbegin.append(" LEFT JOIN M_CI_NAMETITLE N1 ON OBJ.SRC_CUST_ID=N1.CUST_ID ");
		sqlbegin.append(" LEFT JOIN M_CI_NAMETITLE N2 ON OBJ.DEST_CUST_ID=N2.CUST_ID ");
		sqlbegin.append(" WHERE 1 = 1 ");
		String sql = "";
		try {
			sql = perSplitJointSql2(sqlbegin.toString(), tabNo, param1, param2, param3);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
//		SearchResult<Object[]> customerResults = this.baseDAO
//				.findPageWithNameParamByNativeSQL(firstResult, pageSize, sql, null);
//		customerlist = customerResults.getResult();
		customerlist = this.baseDAO.createNativeQueryWithIndexParam(sql)
//			.setFirstResult(GlobalConstants.FIRSTRESULT)
//			.setMaxResults(GlobalConstants.MAXRESULT)
			.getResultList();
//		List<Object[]> list = getListByDB2(customerlist);
		customerlist = this.dictTranslationUtil.setDictPerRelativeByListObject(customerlist);
		return customerlist;
	}

	// 个人类客户分析信息
	@SuppressWarnings("unchecked")
	public List<Object[]> getReportPerAnalysisData(int firstResult, int pageSize, int tabNo,
			String param1, String param2, String param3) {
		List<Object[]> customerlist = null;
		StringBuffer sqlbegin = new StringBuffer();
		sqlbegin.append(" SELECT A.CUST_NO,B.CUST_NAME,C.GRADE_TYPE,C.GRADE, C.EVALUATE_DATE, C.EFFECTIVE_DATE, C.EXPIRED_DATE ");
		sqlbegin.append(" FROM M_CI_CUSTOMER A ");
		sqlbegin.append(" INNER JOIN M_CI_NAMETITLE B ON A.CUST_ID=B.CUST_ID ");
		sqlbegin.append(" INNER JOIN M_CI_PERSONGRADE C ON A.CUST_ID=C.CUST_ID ");
		sqlbegin.append(" WHERE 1 = 1 ");
		String sql = "";
		try {
			sql = perSplitJointSql(sqlbegin.toString(), tabNo, param1, param2, param3);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
//		SearchResult<Object[]> customerResults = this.baseDAO
//				.findPageWithNameParamByNativeSQL(firstResult, pageSize, sql,
//						null);
//		customerlist = customerResults.getResult();
		customerlist = this.baseDAO.createNativeQueryWithIndexParam(sql)
//			.setFirstResult(GlobalConstants.FIRSTRESULT)
//			.setMaxResults(GlobalConstants.MAXRESULT)
			.getResultList();
//		List<Object[]> list = getListByDB2(customerlist);
		customerlist = this.dictTranslationUtil.setDictPerRiskByListObject(customerlist);
		return customerlist;
	}

	// 机构类客户基本信息
	@SuppressWarnings("unchecked")
	public List<Object[]> getReportOrgBasicData(int firstResult, int pageSize, int tabNo, String param1,
			String param2, String param3) {
//		List<Object[]> customerlist = null;
		StringBuffer sqlbegin = new StringBuffer();
		sqlbegin.append(" SELECT  A.CUST_ID FROM M_CI_CUSTOMER A ");
		sqlbegin.append(" WHERE 1 = 1 ");
		String sql = "";
		try {
			sql = orgSplitJointSql(sqlbegin.toString(), tabNo, param1, param2, param3);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
//		SearchResult<Object[]> customerResults = this.baseDAO
//				.findPageWithNameParamByNativeSQL(firstResult, pageSize, sql,
//						null);
//		customerlist = customerResults.getResult();
//		List<Object[]> list = this.baseDAO.findByNativeSQLWithIndexParam(sql);
//			.setFirstResult(GlobalConstants.FIRSTRESULT)
//			.setMaxResults(GlobalConstants.MAXRESULT)
			//.getResultList();
//		List<Object[]> list = getListByDB2(customerlist);
		List custIdList = new ArrayList();
		String custIds = "";
		/**
		for (Object[] obj : list) {
			//custIds = custIds + obj[1] + ",";
			custIdList.add(obj[1]);
		}
		**/
		
		custIds ="aaa";
		if(custIds.length() > 0){
			custIds = custIds.substring(0, custIds.length()-1);
			StringBuffer sqlleft = new StringBuffer();
//			sqlleft.append(" SELECT A.CUST_NO,B.CUST_NAME,C.ORG_CUST_TYPE,D.IS_GROUP_CUST,E.ADMIN_ZONE,F.NATION_CODE,F.ENT_PROPERTY,F.ENT_SCALE,F.ECONOMIC_TYPE,G.BASIC_ACCT_BANK_NO,G.BASIC_ACCT_BANK_NAME,G.BASIC_ACCT_NO,G.BASIC_ACCT_NAME,H.LIFECYCLE_STAT_TYPE ");
			sqlleft.append(" SELECT A.CUST_NO,B.CUST_NAME,C.ORG_CUST_TYPE,D.IS_GROUP_CUST,E.ADMIN_ZONE,F.NATION_CODE,F.ENT_PROPERTY,F.ENT_SCALE,F.ECONOMIC_TYPE,G.BASIC_ACCT_BANK_NO,G.BASIC_ACCT_BANK_NAME,G.BASIC_ACCT_NO,G.BASIC_ACCT_NAME,A.CUST_ID,H.LIFECYCLE_STAT_TYPE ");
			sqlleft.append(" ,F.MAIN_CUST,F.MAIN_SERVICE,F.MAIN_PRODUCT,F.ZONE_CODE,F.EMPLOYEE_SCALE,F.MAIN_BUSINESS,F.ASSETS_SCALE ");
			sqlleft.append(" FROM M_CI_CUSTOMER A ");
			sqlleft.append(" LEFT JOIN M_CI_NAMETITLE B ON A.CUST_ID=B.CUST_ID ");
			sqlleft.append(" LEFT JOIN M_CI_ORG C ON A.CUST_ID=C.CUST_ID ");
			sqlleft.append(" LEFT JOIN M_CI_ORG_KEYFLAG D ON A.CUST_ID=D.CUST_ID ");
			sqlleft.append(" LEFT JOIN M_CI_ORG_REGISTERINFO E ON A.CUST_ID=E.CUST_ID ");
			sqlleft.append(" LEFT JOIN M_CI_ORG F ON A.CUST_ID=F.CUST_ID ");
			sqlleft.append(" LEFT JOIN M_CI_ORG_BASICACCOUNT G ON A.CUST_ID=G.CUST_ID ");
			sqlleft.append(" LEFT JOIN M_CI_LIFECYCLE H ON A.CUST_ID=H.CUST_ID ");
//			sqlleft.append(" WHERE A.CUST_ID IN (");
			/**
			sqlleft.append(" WHERE ");
			String cusIdStr = getSqlStrByList(custIdList,1000,"A.CUST_ID");
			sqlleft.append(cusIdStr);
			**/
//			sqlleft.append(") "); 
			
			sqlleft.append(" WHERE A.CUST_ID IN (");
			sqlleft.append(sql); 
			sqlleft.append(") ORDER BY A.CUST_ID  "); 

			List<Object[]> customerleftlist = this.baseDAO.findByNativeSQLWithIndexParam(sqlleft.toString());
			customerleftlist = this.dictTranslationUtil.setDictOrgBasicByListObject(customerleftlist);
			return customerleftlist;
		}
		return null;
	}

	public String getSqlStrByList(List sqhList, int splitNum,String columnName) { 
		if(splitNum>1000) //因为数据库的列表sql限制，不能超过1000. 
			return null; 
		
		StringBuffer sql = new StringBuffer(""); 
		if (sqhList != null) { 
			sql.append(" ").append(columnName).append (" IN ( "); 
			for (int i = 0; i < sqhList.size(); i++) { 
				sql.append("'").append(sqhList.get(i) + "',"); 
				if ((i + 1) % splitNum == 0 && (i + 1) < sqhList.size()) { 
					sql.deleteCharAt(sql.length() - 1); 
					sql.append(" ) OR ").append(columnName).append (" IN ("); 
				} 
			} 
			sql.deleteCharAt(sql.length() - 1); 
			sql.append(" )"); 
		} 
		return sql.toString(); 
	} 
	
	// 机构类客户关注信息
	@SuppressWarnings("unchecked")
	public List<Object[]> getReportOrgFocusData(int firstResult, int pageSize, int tabNo, String param1,
			String param2, String param3) {
		List<Object[]> customerlist = null;
		StringBuffer sqlbegin = new StringBuffer();
		sqlbegin.append(" SELECT A.CUST_NO,B.CUST_NAME,c.EVENT_TYPE,c.SUB_EVENT_TYPE,c.EVENT_DESC,c.EVENT_OCCUR_DATE,c.EVENT_REG_DATE,c.EVENT_REG_TELLER_NO,c.EVENT_REG_BRC ");
		sqlbegin.append(" FROM M_CI_CUSTOMER A ");
		sqlbegin.append(" LEFT JOIN M_CI_NAMETITLE B ON A.CUST_ID=B.CUST_ID ");
		sqlbegin.append(" LEFT JOIN M_CI_EVENT_INFO C ON A.CUST_ID=C.CUST_ID ");
//		sqlbegin.append(" LEFT JOIN ATTENTEVENT D ON C.EVENT_ID=D.EVENT_ID ");
//		sqlbegin.append(" LEFT JOIN EVENTINFO E ON D.EVENT_ID = E.EVENT_ID ");
		sqlbegin.append(" WHERE (C.EVENT_TYPE = '20' or C.EVENT_TYPE = '30') "); // 事件类型为客户关注事件类型
		
		String sql = "";
		try {
			sql = orgSplitJointSql(sqlbegin.toString(), tabNo, param1, param2, param3);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
//		SearchResult<Object[]> customerResults = this.baseDAO
//				.findPageWithNameParamByNativeSQL(firstResult, pageSize, sql,
//						null);
//		customerlist = customerResults.getResult();
		customerlist = this.baseDAO.createNativeQueryWithIndexParam(sql)
//			.setFirstResult(GlobalConstants.FIRSTRESULT)
//			.setMaxResults(GlobalConstants.MAXRESULT)
			.getResultList();
//		List<Object[]> list = getListByDB2(customerlist);
		customerlist = this.dictTranslationUtil.setDictPerFocusByListObject(customerlist);
		return customerlist;
	}

	// 机构类客户产品信息
	@SuppressWarnings("unchecked")
	public List<Object[]> getReportOrgProductData(int firstResult, int pageSize, int tabNo,
			String param1, String param2, String param3) {
		List<Object[]> customerlist = null;
		StringBuffer sqlbegin = new StringBuffer();
		sqlbegin.append(" SELECT A.CUST_NO,B.CUST_NAME,D.PROD_CODE,D.PROD_NAME,D.PROD_TYPE,E.BRAND_NAME,E.BUSI_CHAR,E.PROD_CLASS,E.PROD_FORM,E.PROD_STAT,E.PROD_PATENT,E.START_DATE,E.END_DATE,E.OWN_SALE_FLAG,D.PROD_SUMMARY ");
		sqlbegin.append(" FROM M_CI_CUSTOMER A ");
		sqlbegin.append(" INNER JOIN M_CI_NAMETITLE B ON A.CUST_ID=B.CUST_ID ");
		sqlbegin.append(" INNER JOIN M_CI_CUSTPRODUCTREL C ON A.CUST_ID=C.CUST_ID ");
		sqlbegin.append(" INNER JOIN M_CI_PRODUCT D ON C.PROD_ID=D.PROD_ID ");
		sqlbegin.append(" INNER JOIN M_CI_PRODUCTBASICINFO E ON C.PROD_ID=E.PROD_ID ");
		sqlbegin.append(" WHERE 1 = 1 ");
		String sql = "";
		try {
			sql = orgSplitJointSql(sqlbegin.toString(), tabNo, param1, param2, param3);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
//		SearchResult<Object[]> customerResults = this.baseDAO
//				.findPageWithNameParamByNativeSQL(firstResult, pageSize, sql,
//						null);
//		customerlist = customerResults.getResult();
		customerlist = this.baseDAO.createNativeQueryWithIndexParam(sql)
//			.setFirstResult(GlobalConstants.FIRSTRESULT)
//			.setMaxResults(GlobalConstants.MAXRESULT)
			.getResultList();
//		List<Object[]> list = getListByDB2(customerlist);
		customerlist = this.dictTranslationUtil.setDictPerProductByListObject(customerlist);
		return customerlist;
	}

	// 机构类客户分析信息
	@SuppressWarnings("unchecked")
	public List<Object[]> getReportOrgAnalysisData(int firstResult, int pageSize, int tabNo,
			String param1, String param2, String param3) {
		List<Object[]> customerlist = null;
		StringBuffer sqlbegin = new StringBuffer();
		sqlbegin.append(" SELECT A.CUST_NO,B.CUST_NAME, C.ORG_GRADE_TYPE, C.ORG_GRADE, C.EVALUTE_DATE, EFFECTIVE_DATE, EXPIRED_DATE ");
		sqlbegin.append(" FROM M_CI_CUSTOMER A ");
		sqlbegin.append(" INNER JOIN M_CI_NAMETITLE B ON A.CUST_ID=B.CUST_ID ");
		sqlbegin.append(" INNER JOIN M_CI_ORGGRADE C ON A.CUST_ID=C.CUST_ID ");
		sqlbegin.append(" WHERE 1 = 1 ");
		String sql = "";
		try {
			sql = orgSplitJointSql(sqlbegin.toString(), tabNo, param1, param2, param3);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
//		SearchResult<Object[]> customerResults = this.baseDAO
//				.findPageWithNameParamByNativeSQL(firstResult, pageSize, sql,
//						null);
//		customerlist = customerResults.getResult();
		customerlist = this.baseDAO.createNativeQueryWithIndexParam(sql)
//			.setFirstResult(GlobalConstants.FIRSTRESULT)
//			.setMaxResults(GlobalConstants.MAXRESULT)
			.getResultList();
//		List<Object[]> list = getListByDB2(customerlist);
		customerlist = this.dictTranslationUtil.setDictOrgRiskByListObject(customerlist);
		return customerlist;
	}

	// 机构类客户关联信息
	@SuppressWarnings("unchecked")
	public List<Object[]> getReportOrgRelativeData(int firstResult, int pageSize, int tabNo,
			String param1, String param2, String param3) {
		List<Object[]> customerlist = null;
		StringBuffer sqlbegin = new StringBuffer();
		sqlbegin.append(" SELECT A.CUST_NO SRCCUSTNO,N1.CUST_NAME SRCNAME,C2.CUST_NO DESTCUSTNO,N2.CUST_NAME DESTNAME,OBJ.CUST_REL_TYPE,OBJ.CUST_REL_DESC,OBJ.REL_START_DATE,OBJ.REL_END_DATE ");
		sqlbegin.append(" FROM M_CI_CUSTREL OBJ ");
		sqlbegin.append(" LEFT JOIN M_CI_CUSTOMER A ON OBJ.SRC_CUST_ID=A.CUST_ID ");
		sqlbegin.append(" LEFT JOIN M_CI_CUSTOMER C2 ON OBJ.DEST_CUST_ID=C2.CUST_ID ");
		sqlbegin.append(" LEFT JOIN M_CI_NAMETITLE N1 ON OBJ.SRC_CUST_ID=N1.CUST_ID ");
		sqlbegin.append(" LEFT JOIN M_CI_NAMETITLE N2 ON OBJ.DEST_CUST_ID=N2.CUST_ID ");
		sqlbegin.append(" WHERE 1 = 1 ");
		String sql = "";
		try {
			sql = orgSplitJointSql2(sqlbegin.toString(), tabNo, param1, param2, param3);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
//		SearchResult<Object[]> customerResults = this.baseDAO
//				.findPageWithNameParamByNativeSQL(firstResult, pageSize, sql,
//						null);
//		customerlist = customerResults.getResult();
		customerlist = this.baseDAO.createNativeQueryWithIndexParam(sql)
//			.setFirstResult(GlobalConstants.FIRSTRESULT)
//			.setMaxResults(GlobalConstants.MAXRESULT)
			.getResultList();
//		List<Object[]> list = getListByDB2(customerlist);
		customerlist = this.dictTranslationUtil.setDictPerRelativeByListObject(customerlist);
		return customerlist;
	}
		
	/**
	 * 拼接SQL
	 * @param sql
	 * @param tabName
	 * @param fieldValue
	 * @return
	 * @throws Exception
	 */
	public String perSplitJointSql(String sql, int tabNo, String param1, String param2, String param3) throws Exception {
		StringBuffer selectCondition = new StringBuffer();
		if (tabNo == 1) {
			String custNo = param1 == null ? "" : param1.toString();
			String name = param2 == null ? "" : param2.toString();
			String ebankRegNo = param3 == null ? "" : param3.toString();
			if (!custNo.equals("")) {
				selectCondition.append(" AND A.CUST_NO ='");
				selectCondition.append(custNo);
				selectCondition.append("' ");
			}
			if (!name.equals("")) {
				selectCondition.append(" AND EXISTS(SELECT 1 FROM M_CI_NAMETITLE I WHERE A.CUST_ID = I.CUST_ID AND I.CUST_NAME like '%");
				selectCondition.append(name);
				selectCondition.append("%') ");
			}
			if (!ebankRegNo.equals("")) {
				selectCondition.append(" AND EXISTS(SELECT 1 FROM M_CI_PERSONOTHERINFO J WHERE A.CUST_ID = J.CUST_ID AND J.EBANK_REG_NO = '");
				selectCondition.append(ebankRegNo);
				selectCondition.append("') ");
			}
		}
		if (tabNo == 2) {
			String identType = param1 == null ? "": param1.toString();
			String identNo = param2 == null ? "": param2.toString();
			if (!identType.equals("")) {
				selectCondition.append(" AND EXISTS(SELECT 1 FROM M_CI_PER_IDENTIFIER H WHERE A.CUST_ID = H.CUST_ID ");
				selectCondition.append(" AND H.IDENT_TYPE ='");
				selectCondition.append(identType);
				selectCondition.append("' ");
				if (!identNo.equals("")) {
					selectCondition.append(" AND H.IDENT_NO ='");
					selectCondition.append(identNo);
					selectCondition.append("' ");
				}
				selectCondition.append(") ");
			}
		}
		if (tabNo == 3 && param1 != null && param1.toString().trim().length() > 0) {
			String productType = param1 == null ? "" : param1.toString();
			String productNo = param2 == null ? "" : param2.toString();
			switch (Integer.valueOf(productType)) {
			case 0:// 存款账户CUST_CONTR_REL
				selectCondition.append(" AND EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_DEPOSIT_ACCT G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ACCTNO = '" + productNo + "') "); // 存款账户账号
				} else {
					selectCondition.append(") ");
				}
				break;
			case 1:// 贷款账户LOANACCOUNT
				selectCondition.append(" AND EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_LOAN_ACCT G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ACCT_NO = '" + productNo + "') "); // 贷款账户账号
				} else {
					selectCondition.append(") ");
				}
				break;
			case 2:// 担保合同GUARANTYCONTRACT
				selectCondition.append(" AND EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, GUARANTYCONTRACT G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.CONTR_NO = '" + productNo + "') "); // 担保合同编号
				} else {
					selectCondition.append(") ");
				}
				break;
			case 3:// 借款合同LOANCONTRACT
				selectCondition.append(" AND EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, LOANCONTRACT G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.CONTR_NO = '" + productNo + "') "); // 贷款合同编号
				} else {
					selectCondition.append(") ");
				}
				break;
			case 4:// 理财账户信息BDSHRACCTINFO
				selectCondition.append(" AND EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_WEALTH_ACCT G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ACCTNO = '" + productNo + "') "); // 理财账户信息理财账号
				} else {
					selectCondition.append(") ");
				}
				break;
			case 5:// 债券持有信息BONDCONTRACT
				selectCondition.append(" AND EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_BOND_ACCT G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ZQDM = '" + productNo + "') "); // 债券持有信息债券代码
				} else {
					selectCondition.append(") ");
				}
				break;
			case 6:// 基金份额
				selectCondition.append(" AND EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_FUND_ACCT G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ASSET_ACC = '" + productNo + "') "); // 基金份额理财账号
				} else {
					selectCondition.append(") ");
				}
				break;
			case 7:// 网银协议
				selectCondition.append(" AND EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_EBANK_SIGN G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.IAB_ACCNO = '" + productNo + "') "); // 网银协议账号
				} else {
					selectCondition.append(") ");
				}
				break;
			case 8:// 授信协议
				selectCondition.append(" AND EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_CONTRACT G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.CREDIT_CONTR_NO = '" + productNo + "') "); // 授信协议授信合同号
				} else {
					selectCondition.append(") ");
				}
				break;
			case 9:// 黄金交易签约
				selectCondition.append(" AND EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_GOLD_SIGN G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.GOLDACCTNO = '" + productNo + "') "); // 黄金交易签约黄金账号
				} else {
					selectCondition.append(") ");
				}
				break;
			case 10:// 网银签约
				selectCondition.append(" AND EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_GOLD_SIGN G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.BANK_ACC = '" + productNo + "') "); // 网银签约银行账号
				} else {
					selectCondition.append(") ");
				}
				break;
			case 11:// 凭证式债券BDSACCTINFO
				selectCondition.append(" AND EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, BDSACCTINFO G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ACCTNO = '" + productNo + "') "); // 网银签约银行账号
				} else {
					selectCondition.append(") ");
				}
				break;
			default:
				break;
			}
		}
		if (tabNo == 4) {
			StringBuffer sql0 = new StringBuffer();
			sql0.append(" WHERE 1 = 1 ");
			String contmeth = param1 == null ? "": param1.toString();
			String address = param2 == null ? "": param2.toString();
			if (!address.equals("")) {
				selectCondition.append(" AND EXISTS(SELECT 1 FROM ADDRESS G WHERE A.CUST_ID = G.CUST_ID ");
				selectCondition.append(" AND G.ADDR LIKE '%");
				selectCondition.append(address);
				selectCondition.append("%') ");
			}
			if (!contmeth.equals("")) {
				selectCondition.append(" AND EXISTS(SELECT 1 FROM CONTMETH I WHERE A.CUST_ID = I.CUST_ID ");
				selectCondition.append(" AND I.CONTMETH_INFO = '");
				selectCondition.append(contmeth);
				selectCondition.append("') ");
			}
		}
		//selectCondition.append(" AND A.CUST_TYPE = '1' ORDER BY A.CUST_ID "); // 设置客户类型为个人类客户
		selectCondition.append(" AND A.CUST_TYPE = '1' "); // 		去掉了order by
		sql = sql + selectCondition.toString();
		return sql;
	}

	/**
	 * 拼接SQL
	 * @param sql
	 * @param tabName
	 * @param fieldValue
	 * @return
	 * @throws Exception
	 */
	public String orgSplitJointSql(String sql, int tabNo, String param1, String param2, String param3) throws Exception {
		StringBuffer selectCondition = new StringBuffer(); 
		if (tabNo == 1) {
			String custNo = param1 == null ? "" : param1.toString();
			String name = param2 == null ? "" : param2.toString();
			String ebankRegNo = param3 == null ? "": param3.toString();
			if (!custNo.equals("")) {
				selectCondition.append(" AND A.CUST_NO ='");
				selectCondition.append(custNo);
				selectCondition.append("' ");
			}
			if (!name.equals("")) {
				selectCondition.append(" AND EXISTS(SELECT 1 FROM M_CI_NAMETITLE I WHERE A.CUST_ID = I.CUST_ID AND I.CUST_NAME like '%");
				selectCondition.append(name);
				selectCondition.append("%') ");
			}
			if (!ebankRegNo.equals("")) {
				selectCondition.append(" AND EXISTS(SELECT 1 FROM M_CI_ORGOTHERINFO J WHERE A.CUST_ID = J.CUST_ID AND J.EBANK_REG_NO = '");
				selectCondition.append(ebankRegNo);
				selectCondition.append("') ");
			}
		}
		if (tabNo == 2) {
			String identType = param1 == null ? "": param1.toString();
			String identNo = param2 == null ? "": param2.toString();
			if (!identType.equals("")) {
				selectCondition.append(" AND EXISTS(SELECT 1 FROM ORGIDENTIFIER H WHERE A.CUST_ID = H.CUST_ID ");
				selectCondition.append(" AND H.IDENT_TYPE ='");
				selectCondition.append(identType);
				selectCondition.append("' ");
				if (!identNo.equals("")) {
					selectCondition.append(" AND H.IDENT_NO ='");
					selectCondition.append(identNo);
					selectCondition.append("' ");
				}
				selectCondition.append(") ");
			}
		}
		if (tabNo == 3 && param1 != null && param1.toString().trim().length() > 0) {
			String productType = param1 == null ? "" : param1.toString();
			String productNo = param2 == null ? "" : param2.toString();
			switch (Integer.valueOf(productType)) {
			case 0:// 存款账户CUST_CONTR_REL
				selectCondition.append(" AND EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_DEPOSIT_ACCT G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ACCTNO = '" + productNo + "') "); // 存款账户账号
				} else {
					selectCondition.append(") ");
				}
				break;
			case 1:// 贷款账户LOANACCOUNT
				selectCondition.append(" AND EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_LOAN_ACCT G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ACCT_NO = '" + productNo + "') "); // 贷款账户账号
				} else {
					selectCondition.append(") ");
				}
				break;
			case 2:// 担保合同GUARANTYCONTRACT
				selectCondition.append(" AND EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, GUARANTYCONTRACT G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.CONTR_NO = '" + productNo + "') "); // 担保合同编号
				} else {
					selectCondition.append(") ");
				}
				break;
			case 3:// 借款合同LOANCONTRACT
				selectCondition.append(" AND EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, LOANCONTRACT G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.CONTR_NO = '" + productNo + "') "); // 贷款合同编号
				} else {
					selectCondition.append(") ");
				}
				break;
			case 4:// 理财账户信息BDSHRACCTINFO
				selectCondition.append(" AND EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_WEALTH_ACCT G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ACCTNO = '" + productNo + "') "); // 理财账户信息理财账号
				} else {
					selectCondition.append(") ");
				}
				break;
			case 5:// 债券持有信息BONDCONTRACT
				selectCondition.append(" AND EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_BOND_ACCT G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ZQDM = '" + productNo + "') "); // 债券持有信息债券代码
				} else {
					selectCondition.append(") ");
				}
				break;
			case 6:// 基金份额
				selectCondition.append(" AND EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_FUND_ACCT G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ASSET_ACC = '" + productNo + "') "); // 基金份额理财账号
				} else {
					selectCondition.append(") ");
				}
				break;
			case 7:// 网银协议
				selectCondition.append(" AND EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_EBANK_SIGN G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.IAB_ACCNO = '" + productNo + "') "); // 网银协议账号
				} else {
					selectCondition.append(") ");
				}
				break;
			case 8:// 授信协议
				selectCondition.append(" AND EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_CONTRACT G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.CREDIT_CONTR_NO = '" + productNo + "') "); // 授信协议授信合同号
				} else {
					selectCondition.append(") ");
				}
				break;
			case 9:// 黄金交易签约
				selectCondition.append(" AND EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_GOLD_SIGN G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.GOLDACCTNO = '" + productNo + "') "); // 黄金交易签约黄金账号
				} else {
					selectCondition.append(") ");
				}
				break;
			case 10:// 网银签约
				selectCondition.append(" AND EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_GOLD_SIGN G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.BANK_ACC = '" + productNo + "') "); // 网银签约银行账号
				} else {
					selectCondition.append(") ");
				}
				break;
			case 11:// 凭证式债券BDSACCTINFO
				selectCondition.append(" AND EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, BDSACCTINFO G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ACCTNO = '" + productNo + "') "); // 
				} else {
					selectCondition.append(") ");
				}
				break;
			default:
				break;
			}
		}
		if (tabNo == 4) {
			StringBuffer sql0 = new StringBuffer();
			sql0.append(" WHERE 1 = 1 ");
			String contmeth = param1 == null ? "": param1.toString();
			String address = param2 == null ? "": param2.toString();
			if (!address.equals("")) {
				selectCondition.append(" AND EXISTS(SELECT 1 FROM ADDRESS G WHERE A.CUST_ID = G.CUST_ID ");
				selectCondition.append(" AND G.ADDR LIKE '%");
				selectCondition.append(address);
				selectCondition.append("%') ");
			}
			if (!contmeth.equals("")) {
				selectCondition.append(" AND EXISTS(SELECT 1 FROM CONTMETH I WHERE A.CUST_ID = I.CUST_ID ");
				selectCondition.append(" AND I.CONTMETH_INFO = '");
				selectCondition.append(contmeth);
				selectCondition.append("') ");
			}
		}
		//selectCondition.append(" AND (A.CUST_TYPE = '2' or A.CUST_TYPE = '3') ORDER BY A.CUST_ID "); // 设置客户类型为机构类客户
		selectCondition.append(" AND (A.CUST_TYPE = '2' or A.CUST_TYPE = '3') "); // 	去掉了order by

		sql = sql + selectCondition.toString();
		return sql;
	}
	
	/**
	 * 客户关联信息
	 * @param custNo 源客户号
	 * @param destNo 目标客户号
	 * @param custrelType 关系类型
	 * @param lastUpdateSys 客户关系类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
//	public List<List<Object[]>> getCustrelData(String srcNo, String destNo, String custrelType,String lastUpdateSys){
	public List<List<Object[]>> getCustrelData(String rule){
		JSONArray rulesJson = JSONObject.fromObject(rule).getJSONArray("rules");
		Map<String, String> queryMap = new HashMap<String, String>();
		for (Iterator<?> conditioniter = rulesJson.iterator(); conditioniter.hasNext();) {
			JSONObject jsonstr = (JSONObject) conditioniter.next();
			queryMap.put((String) jsonstr.get("field"), (String) jsonstr.get("value"));
		}
		List<List<Object[]>> reportData = new ArrayList<List<Object[]>>();
		//源客户号	源客户名称	目标客户号	目标客户名称	
		//关系类型 关系描述	关系开始时间	关系结束时间
		String sql = " select  " +
//				"custrel.CUST_REL_ID," +
				" n1.CUST_NAME srcName,"+
				" customer1.cust_no src_cust_no, /*custrel.SRC_CUST_ID,*/ " +
				" n2.CUST_NAME destName,"+
				" customer2.cust_no dest_cust_no, /*custrel.DEST_CUST_ID,*/ " +
				" cd.STD_CODE_DESC relType, custrel.CUST_REL_DESC, custrel.REL_START_DATE, custrel.REL_END_DATE" +
				//" cd1.STD_CODE_DESC relStat, custrel.LAST_UPDATE_SYS " +
				" from  M_CI_CUSTREL custrel " + 
				" left join M_CI_NAMETITLE n1 on n1.CUST_ID = custrel.SRC_CUST_ID " + 
				" left join M_CI_NAMETITLE n2 on n2.CUST_ID = custrel.DEST_CUST_ID " +
				" left join M_CI_CUSTOMER customer1 on customer1.CUST_ID = custrel.SRC_CUST_ID " +
				" left join M_CI_CUSTOMER customer2 on customer2.CUST_ID = custrel.DEST_CUST_ID " +
				" left join TX_STD_CODE cd on cd.STATE = '0' AND cd.STD_CODE = custrel.CUST_REL_TYPE" +
				" and cd.STD_CATE='"+GlobalConstants.CODE_STR_CUSTREL_TYPE+"' "+
				//" left join ECIF.STD_CODE cd1 on cd1.STD_CODE = custrel.CUST_REL_STAT "+
				//"  and cd1.STD_CATE='"+GlobalConstants.CODE_STR_VALID_TYPE+"' "+
				" where 1=1 ";
				//" and (custrel.APPROVAL_FLAG is null or custrel.APPROVAL_FLAG <> '"+GlobalConstants.APPROVAL_FLAG_1+"') ";
		
		if(!StringUtils.isEmpty(queryMap.get("srcNo"))){
			sql += " and customer1.cust_no = '"+queryMap.get("srcNo")+"'";
		}
		if(!StringUtils.isEmpty(queryMap.get("destNo"))){
			sql += " and customer2.cust_no = '"+queryMap.get("destNo")+"'";
		}
		if(!StringUtils.isEmpty(queryMap.get("custrelType"))){
			sql += " and custrel.CUST_REL_TYPE = '"+queryMap.get("custrelType")+"'";
		}
//		if(!StringUtils.isEmpty(lastUpdateSys)){
//			sql += " and custrel.LAST_UPDATE_SYS = '"+lastUpdateSys+"'";
//		}
		sql += " order by custrel.CUST_REL_ID desc ";
		List<Object[]> list = this.baseDAO.createNativeQueryWithIndexParam(sql)
//				.setFirstResult(GlobalConstants.FIRSTRESULT)
//				.setMaxResults(GlobalConstants.MAXRESULT)
				.getResultList();
//		List<Object[]> list = getListByDB2(temp);
		reportData.add(list);
		return reportData;
	}
	
	/**
	 * 客户关联信息
	 * @param custNo 源客户号
	 * @param destNo 目标客户号
	 * @param custrelType 关系类型
	 * @param lastUpdateSys 客户关系类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<List<Object[]>> getCustrelApprovalData(String rule){
		JSONArray rulesJson = JSONObject.fromObject(rule).getJSONArray("rules");
		StringBuffer queryJql = new StringBuffer();
		for (Iterator<?> conditioniter = rulesJson.iterator(); conditioniter.hasNext();) {
			JSONObject jsonstr = (JSONObject) conditioniter.next();
			String value = (String) jsonstr.get("value");
			String field = (String) jsonstr.get("field");
			String op = (String) jsonstr.get("op");
			if("custrelApproval.OPER_TIME".equals(field)){
				if(">=".equals(op)){
					value = value + " 00:00:00";
				}
				if("<=".equals(op)){
					value = value + " 23:59:59";
				}
			}
			queryJql.append(" AND ");
			queryJql.append(field);
			queryJql.append(" ");
			queryJql.append(op);
			queryJql.append(" '");
			queryJql.append(value);
			queryJql.append("' ");
		}
		List<List<Object[]>> reportData = new ArrayList<List<Object[]>>();
		//关系信息标识	源客户标识	源客户号	源客户名称	目标客户标识	目标客户号	目标客户名称	
		//关系类型	关系开始时间	关系结束时间	提交人	提交时间	操作状态	
		//审批人	审批时间	审批状态	审批意见
		String sql = " SELECT CUSTREL_APPROVAL_ID," +
					" SRC_CUST_ID, customer1.cust_no src_cust_no, n1.CUST_NAME srcName,"+
					" DEST_CUST_ID, customer2.cust_no dest_cust_no, n2.CUST_NAME destName,"+
				    " cd.STD_CODE_DESC custType,"+
//					" CUST_REL_DESC,"+
//				    " cd1.STD_CODE_DESC custStat,"+
					" REL_START_DATE,"+
				    " REL_END_DATE,"+
					" OPERATOR,"+
				    " OPER_TIME," +
				    " OPER_STAT "+
//				    " APPROVAL_STAT"+
					" FROM APP_CUSTREL_APPROVAL custrelApproval"+
					" left join M_CI_NAMETITLE n1 on n1.CUST_ID = custrelApproval.SRC_CUST_ID " + 
					" left join M_CI_NAMETITLE n2 on n2.CUST_ID = custrelApproval.DEST_CUST_ID " +
					" left join M_CI_CUSTOMER customer1 on customer1.CUST_ID = custrelApproval.SRC_CUST_ID " +
					" left join M_CI_CUSTOMER customer2 on customer2.CUST_ID = custrelApproval.DEST_CUST_ID " +
					" left join TX_STD_CODE cd on cd.STATE = '0' AND cd.STD_CODE = custrelApproval.CUST_REL_TYPE " +
				    " and cd.STD_CATE='"+GlobalConstants.CODE_STR_CUSTREL_TYPE+"' " +
//				    " left join STD_CODE cd1 on cd1.STD_CODE = custrelApproval.CUST_REL_STAT"+
//				    " and cd1.STD_CATE='"+GlobalConstants.CODE_STR_VALID_TYPE+"'"+
				    " WHERE 1=1 AND (custrelApproval.APPROVAL_STAT = '"+GlobalConstants.APPROVAL_STAT_1+"') ";

		if(!StringUtils.isEmpty(queryJql)){
			sql += queryJql.toString();
		}
//		if(!StringUtils.isEmpty(srcNo)){
//			sql += "  and customer1.cust_no = '"+srcNo+"'";
//		}
//		if(!StringUtils.isEmpty(destNo)){
//			sql += "  and customer2.cust_no = '"+destNo+"'";
//		}
//		if(!StringUtils.isEmpty(custrelType)){
//			sql += " and custrelApproval.CUST_REL_TYPE = '"+custrelType+"'";
//		}
//		if(!StringUtils.isEmpty(lastUpdateSys)){
//			sql += " and custrelApproval.LAST_UPDATE_SYS = '"+lastUpdateSys+"'";
//		}
		sql += " order by custrelApproval.OPER_TIME asc ";
		List<Object[]> list = this.baseDAO.createNativeQueryWithIndexParam(sql)
//				.setFirstResult(GlobalConstants.FIRSTRESULT)
//				.setMaxResults(GlobalConstants.MAXRESULT)
				.getResultList();
//		List<Object[]> list = getListByDB2(temp);
		for(Object[] obj : list){
			if(GlobalConstants.OPER_STAT_INSERT.equals(obj[12].toString())){
				obj[12] = "新增";
			}else if(GlobalConstants.OPER_STAT_UPDATE.equals(obj[12].toString())){
				obj[12] = "修改";
			}else if(GlobalConstants.OPER_STAT_DELETE.equals(obj[12].toString())){
				obj[12] = "删除";
			}
//			obj[12] = "待审批";
		}
		
		reportData.add(list);
		return reportData;
		
	}
	
	
	/**
	 * 客户关联信息
	 * @param custNo 源客户号
	 * @param destNo 目标客户号
	 * @param custrelType 关系类型
	 * @param lastUpdateSys 客户关系类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<List<Object[]>> getCustrelApprovalHistroyData(String rule){
		JSONArray rulesJson = JSONObject.fromObject(rule).getJSONArray("rules");
		StringBuffer queryJql = new StringBuffer();
		for (Iterator<?> conditioniter = rulesJson.iterator(); conditioniter.hasNext();) {
			JSONObject jsonstr = (JSONObject) conditioniter.next();
			String value = (String) jsonstr.get("value");
			String field = (String) jsonstr.get("field");
			String op = (String) jsonstr.get("op");
			if("custrelApproval.APPROVAL_TIME".equals(field)){
				if(">=".equals(op)){
					value = value + " 00:00:00";
				}
				if("<=".equals(op)){
					value = value + " 23:59:59";
				}
			}
			queryJql.append(" AND ");
			queryJql.append(field);
			queryJql.append(" ");
			queryJql.append(op);
			queryJql.append(" '");
			queryJql.append(value);
			queryJql.append("' ");
		}
		List<List<Object[]>> reportData = new ArrayList<List<Object[]>>();
		//源客户号	源客户名称	目标客户号	目标客户名称	
		//关系类型 关系描述	关系开始时间	关系结束时间	提交人	提交时间	操作状态	
		//审批状态	审批人	审批日期	审批意见
		String sql = " SELECT " +
				" SRC_CUST_ID, customer1.cust_no src_cust_no, n1.CUST_NAME srcName,"+
				" DEST_CUST_ID, customer2.cust_no dest_cust_no, n2.CUST_NAME destName,"+
			    " cd.STD_CODE_DESC custType, CUST_REL_DESC, "+
//				" CUST_REL_DESC,"+
//			    " cd1.STD_CODE_DESC custStat,"+
				" REL_START_DATE,"+
			    " REL_END_DATE,"+
				" OPERATOR,"+
			    " OPER_TIME," +
			    " OPER_STAT, "+
			    " APPROVAL_STAT,"+
			    " APPROVAL_OPERATOR,"
				+ " APPROVAL_TIME,"
				+ " APPROVAL_NOTE"
				+
				" FROM APP_CUSTREL_APPROVAL custrelApproval"+
				" left join M_CI_NAMETITLE n1 on n1.CUST_ID = custrelApproval.SRC_CUST_ID " + 
				" left join M_CI_NAMETITLE n2 on n2.CUST_ID = custrelApproval.DEST_CUST_ID " +
				" left join M_CI_CUSTOMER customer1 on customer1.CUST_ID = custrelApproval.SRC_CUST_ID " +
				" left join M_CI_CUSTOMER customer2 on customer2.CUST_ID = custrelApproval.DEST_CUST_ID " +
			    " left join TX_STD_CODE cd on cd.STATE = '0' AND cd.STD_CODE = custrelApproval.CUST_REL_TYPE " +
			    " and cd.STD_CATE='"+GlobalConstants.CODE_STR_CUSTREL_TYPE+"' " +
//			    " left join STD_CODE cd1 on cd1.STD_CODE = custrelApproval.CUST_REL_STAT"+
//			    " and cd1.STD_CATE='"+GlobalConstants.CODE_STR_VALID_TYPE+"'"+
			    " WHERE 1=1 AND (custrelApproval.APPROVAL_STAT <> '"+GlobalConstants.APPROVAL_STAT_1+"') ";
//		if(!StringUtils.isEmpty(srcNo)){
//			sql += " and custrelApproval.SRC_CUST_ID = '"+srcNo+"'";
//		}
//		if(!StringUtils.isEmpty(destNo)){
//			sql += " and custrelApproval.DEST_CUST_ID = '"+destNo+"'";
//		}
		if(!StringUtils.isEmpty(queryJql)){
			sql += queryJql.toString();
		}
//		if(!StringUtils.isEmpty(srcNo)){
//			sql += "  and customer1.cust_no = '"+srcNo+"'";
//		}
//		if(!StringUtils.isEmpty(destNo)){
//			sql += "  and customer2.cust_no = '"+destNo+"'";
//		}
//		if(!StringUtils.isEmpty(custrelType)){
//			sql += " and custrelApproval.CUST_REL_TYPE = '"+custrelType+"'";
//		}
//		if(!StringUtils.isEmpty(lastUpdateSys)){
//			sql += " and custrelApproval.APPROVAL_STAT = '"+lastUpdateSys+"'";
//		}
		sql += " order by custrelApproval.APPROVAL_TIME desc ";
		List<Object[]> list = this.baseDAO.createNativeQueryWithIndexParam(sql)
//				.setFirstResult(GlobalConstants.FIRSTRESULT)
//				.setMaxResults(GlobalConstants.MAXRESULT)
				.getResultList();
//		List<Object[]> list = getListByDB2(temp);
		
		for(Object[] obj : list){
			if(obj[12] == null){
				obj[12] = "";
			}else{
				if(GlobalConstants.OPER_STAT_INSERT.equals(obj[12].toString())){
					obj[12] = "新增";
				}else if(GlobalConstants.OPER_STAT_UPDATE.equals(obj[12].toString())){
					obj[12] = "修改";
				}else if(GlobalConstants.OPER_STAT_DELETE.equals(obj[12].toString())){
					obj[12] = "删除";
				}
			}
			
			if(obj[13] == null){
				obj[13] = "";
			}else{
				if(GlobalConstants.APPROVAL_STAT_2.equals(obj[13].toString())){
					obj[13] = "审批通过";
				}else if(GlobalConstants.APPROVAL_STAT_3.equals(obj[13].toString())){
					obj[13] = "审批未通过";
				}
			}
		}		
		reportData.add(list);
		return reportData;
	}
	/**
	 * 黑名单码值
	 */
	public void initSpecialListMap(){
		if(codeMapValidType == null || codeMapValidType.isEmpty()){
			codeMapValidType = Maps.newHashMap();
			codeMapValidType = this.codeUtil.getCodeMap(GlobalConstants.CODE_STR_VALID_TYPE);
		}
		if(codeMapDisStatus == null || codeMapDisStatus.isEmpty()){
			codeMapDisStatus = Maps.newHashMap();
			codeMapDisStatus.put(GlobalConstants.OPER_STAT_INSERT, "新增");
			codeMapDisStatus.put(GlobalConstants.OPER_STAT_UPDATE, "更新");
			codeMapDisStatus.put(GlobalConstants.OPER_STAT_DELETE, "删除");
		}
		if(codeMapApprovalStatus == null || codeMapApprovalStatus.isEmpty()){
			codeMapApprovalStatus = Maps.newHashMap();
			codeMapApprovalStatus.put(GlobalConstants.APPROVAL_STAT_2, "审批通过");
			codeMapApprovalStatus.put(GlobalConstants.APPROVAL_STAT_3, "审批未通过");
		}
		if(codeMapIdentType == null || codeMapIdentType.isEmpty()){
			codeMapIdentType = Maps.newHashMap();
			codeMapIdentType = this.codeUtil.getCodeMap(
					GlobalConstants.CODE_STR_ORGIDENT_TYPE + ","
					+ GlobalConstants.CODE_STR_PERSONIDENT_TYPE);
		}
		if(codeMapSpecialListKind == null || codeMapSpecialListKind.isEmpty()){
			codeMapSpecialListKind = Maps.newHashMap();
			codeMapSpecialListKind = this.specialListBS.getCodeMapSpecialListKind(
					GlobalConstants.CODE_STR_SPECIALlIST_TYPE);
		}
	}
	/**
	 * 疑似客户码值
	 */
	public void initSuspectCustMap(){
		if(codeMapComfirmType == null || codeMapComfirmType.isEmpty()){
			codeMapComfirmType = Maps.newHashMap();
			codeMapComfirmType.put(GlobalConstants.CUST_COMFIRM_0, "未确认");
			codeMapComfirmType.put(GlobalConstants.CUST_COMFIRM_1, "确认");
		}
		if(codeMapMergeType == null || codeMapMergeType.isEmpty()){
			codeMapMergeType = Maps.newHashMap();
			codeMapMergeType.put(GlobalConstants.CUST_MERGE_TYPE, "已合并");
			codeMapMergeType.put(GlobalConstants.CUST_SPLIT_TYPE, "已拆分");
			codeMapMergeType.put("", "未合并");
		}
		if(codeMapSuspectType == null || codeMapSuspectType.isEmpty()){
			codeMapSuspectType = Maps.newHashMap();
			codeMapSuspectType.put(GlobalConstants.CUST_SUSPECT_0, "非疑似");
			codeMapSuspectType.put(GlobalConstants.CUST_SUSPECT_1, "疑似");
		}
		if(codeMapSCustType == null || codeMapSCustType.isEmpty()){
			codeMapSCustType = Maps.newHashMap();
			codeMapSCustType.put(GlobalConstants.CUST_PERSON_TYPE, "个人");
			codeMapSCustType.put(GlobalConstants.CUST_ORG_TYPE, "机构");
			codeMapSCustType.put(GlobalConstants.CUST_ORG_TYPE2, "机构");
		}
	}
	//
	public List<Object[]> getListByDB2(List<Object[]> objectList){
		String databaseType=DialectUtils.getDataBaseType(bioneDataSource.getDataSource());
		if(databaseType.equals("db2")){
			List<Object[]> newobjectList = new ArrayList<Object[]>();
			for(Object[] object:objectList){
				Object[] newobject=new Object[object.length-1];
				for(int i=0;i<object.length-1;i++){
					newobject[i]=object[i+1];
				}
				newobjectList.add(newobject);
			}
			objectList=newobjectList;
		}
		return objectList;
	}
	
	/**
	 * 拼接SQL
	 * @param sql
	 * @param tabName
	 * @param fieldValue
	 * @return
	 * @throws Exception
	 */
	public String perSplitJointSql2(String sql, int tabNo, String param1, String param2, String param3) throws Exception {
		StringBuffer selectCondition = new StringBuffer();
		if (tabNo == 1) {
			String custNo = param1 == null ? "" : param1.toString();
			String name = param2 == null ? "" : param2.toString();
			String ebankRegNo = param3 == null ? "" : param3.toString();
			if (!custNo.equals("")) {
				selectCondition.append(" AND (A.CUST_NO ='");
				selectCondition.append(custNo);
				selectCondition.append("' or  C2.CUST_NO='");
				selectCondition.append(custNo);
				selectCondition.append("') ");
			}
			if (!name.equals("")) {
				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM M_CI_NAMETITLE I WHERE A.CUST_ID = I.CUST_ID AND I.CUST_NAME like '%");
				selectCondition.append(name);
				selectCondition.append("%') ");
				selectCondition.append(" or EXISTS(SELECT 1 FROM M_CI_NAMETITLE I WHERE c2.CUST_ID = I.CUST_ID AND I.CUST_NAME like '%");
				selectCondition.append(name);
				selectCondition.append("%') ) ");
			}
			if (!ebankRegNo.equals("")) {
				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM PERSONOTHERINFO J WHERE A.CUST_ID = J.CUST_ID AND J.EBANK_REG_NO = '");
				selectCondition.append(ebankRegNo);
				selectCondition.append("') ");
				selectCondition.append(" or EXISTS(SELECT 1 FROM PERSONOTHERINFO J WHERE c2.CUST_ID = J.CUST_ID AND J.EBANK_REG_NO = '");
				selectCondition.append(ebankRegNo);
				selectCondition.append("') ) ");
			}
		}
		if (tabNo == 2) {
			String identType = param1 == null ? "": param1.toString();
			String identNo = param2 == null ? "": param2.toString();
			if (!identType.equals("")) {
				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM M_CI_PER_IDENTIFIER H WHERE A.CUST_ID = H.CUST_ID ");
				selectCondition.append(" AND H.IDENT_TYPE ='");
				selectCondition.append(identType);
				selectCondition.append("' ");
				if (!identNo.equals("")) {
					selectCondition.append(" AND H.IDENT_NO ='");
					selectCondition.append(identNo);
					selectCondition.append("' ");
				}
				selectCondition.append(") ");
				selectCondition.append(" or EXISTS(SELECT 1 FROM M_CI_PER_IDENTIFIER H WHERE c2.CUST_ID = H.CUST_ID ");
				selectCondition.append(" AND H.IDENT_TYPE ='");
				selectCondition.append(identType);
				selectCondition.append("' ");
				if (!identNo.equals("")) {
					selectCondition.append(" AND H.IDENT_NO ='");
					selectCondition.append(identNo);
					selectCondition.append("' ");
				}
				selectCondition.append(") ) ");
			}
		}
		if (tabNo == 3 && param1 != null && param1.toString().trim().length() > 0) {
			String productType = param1 == null ? "" : param1.toString();
			String productNo = param2 == null ? "" : param2.toString();
			switch (Integer.valueOf(productType)) {
			case 0:// 存款账户CUST_CONTR_REL
				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_DEPOSIT_ACCT G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ACCTNO = '" + productNo + "') "); // 存款账户账号
				} else {
					selectCondition.append(") ");
				}
				selectCondition.append(" or EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_DEPOSIT_ACCT G WHERE F.CUST_ID = c2.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ACCTNO = '" + productNo + "') ) "); // 存款账户账号
				} else {
					selectCondition.append(") ) ");
				}
				break;
			case 1:// 贷款账户LOANACCOUNT
				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_LOAN_ACCT G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ACCT_NO = '" + productNo + "') "); // 贷款账户账号
				} else {
					selectCondition.append(") ");
				}
				selectCondition.append(" or EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_LOAN_ACCT G WHERE F.CUST_ID = c2.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ACCT_NO = '" + productNo + "') ) "); // 贷款账户账号
				} else {
					selectCondition.append(") ) ");
				}
				break;
			case 2:// 担保合同GUARANTYCONTRACT
				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, GUARANTYCONTRACT G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.CONTR_NO = '" + productNo + "') "); // 担保合同编号
				} else {
					selectCondition.append(") ");
				}
				selectCondition.append(" or EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, GUARANTYCONTRACT G WHERE F.CUST_ID = c2.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.CONTR_NO = '" + productNo + "') ) "); // 担保合同编号
				} else {
					selectCondition.append(") ) ");
				}
				break;
			case 3:// 借款合同LOANCONTRACT
				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, LOANCONTRACT G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.CONTR_NO = '" + productNo + "') "); // 贷款合同编号
				} else {
					selectCondition.append(") ");
				}
				selectCondition.append(" or EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, LOANCONTRACT G WHERE F.CUST_ID = c2.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.CONTR_NO = '" + productNo + "') ) "); // 贷款合同编号
				} else {
					selectCondition.append(") ) ");
				}
				break;
			case 4:// 理财账户信息BDSHRACCTINFO
				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_WEALTH_ACCT G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ACCTNO = '" + productNo + "') "); // 理财账户信息理财账号
				} else {
					selectCondition.append(") ");
				}
				selectCondition.append(" or EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_WEALTH_ACCT G WHERE F.CUST_ID = c2.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ACCTNO = '" + productNo + "') ) "); // 理财账户信息理财账号
				} else {
					selectCondition.append(") ) ");
				}
				break;
			case 5:// 电子式债券
				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_BOND_ACCT G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ZQDM = '" + productNo + "') "); // 债券持有信息债券代码
				} else {
					selectCondition.append(") ");
				}
				selectCondition.append(" or EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_BOND_ACCT G WHERE F.CUST_ID = c2.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ZQDM = '" + productNo + "') ) "); // 债券持有信息债券代码
				} else {
					selectCondition.append(") ) ");
				}
				break;
			case 6:// 基金份额
				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_FUND_ACCT G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ASSET_ACC = '" + productNo + "') "); // 基金份额理财账号
				} else {
					selectCondition.append(") ");
				}
				selectCondition.append(" or EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_FUND_ACCT G WHERE F.CUST_ID = c2.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ASSET_ACC = '" + productNo + "') ) "); // 基金份额理财账号
				} else {
					selectCondition.append(") ) ");
				}
				break;
			case 7:// 网银协议
				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_EBANK_SIGN G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.IAB_ACCNO = '" + productNo + "') "); // 网银协议账号
				} else {
					selectCondition.append(") ");
				}
				selectCondition.append(" or EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_EBANK_SIGN G WHERE F.CUST_ID = c2.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.IAB_ACCNO = '" + productNo + "') ) "); // 网银协议账号
				} else {
					selectCondition.append(") ) ");
				}
				break;
			case 8:// 授信协议
				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_CONTRACT G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.CREDIT_CONTR_NO = '" + productNo + "') "); // 授信协议授信合同号
				} else {
					selectCondition.append(") ");
				}
				selectCondition.append(" or EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_CONTRACT G WHERE F.CUST_ID = c2.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.CREDIT_CONTR_NO = '" + productNo + "') ) "); // 授信协议授信合同号
				} else {
					selectCondition.append(") ) ");
				}
				break;
			case 9:// 黄金交易签约
				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_GOLD_SIGN G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.GOLDACCTNO = '" + productNo + "') "); // 黄金交易签约黄金账号
				} else {
					selectCondition.append(") ");
				}
				selectCondition.append(" or EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_GOLD_SIGN G WHERE F.CUST_ID = c2.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.GOLDACCTNO = '" + productNo + "') ) "); // 黄金交易签约黄金账号
				} else {
					selectCondition.append(") ) ");
				}
				break;
			case 10:// 网银签约
				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_GOLD_SIGN G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.BANK_ACC = '" + productNo + "') "); // 网银签约银行账号
				} else {
					selectCondition.append(") ");
				}
				selectCondition.append(" or EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_GOLD_SIGN G WHERE F.CUST_ID = c2.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.BANK_ACC = '" + productNo + "') ) "); // 网银签约银行账号
				} else {
					selectCondition.append(") ) ");
				}
				break;
			case 11:// 凭证式债券BDSACCTINFO
				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, BDSACCTINFO G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ACCTNO = '" + productNo + "') "); // 
				} else {
					selectCondition.append(") ");
				}
				selectCondition.append(" or EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, BDSACCTINFO G WHERE F.CUST_ID = c2.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ACCTNO = '" + productNo + "') ) "); // 
				} else {
					selectCondition.append(") ) ");
				}
				break;
			default:
				break;
			}
		}
		if (tabNo == 4) {
			StringBuffer sql0 = new StringBuffer();
			sql0.append(" WHERE 1 = 1 ");
			String contmeth = param1 == null ? "": param1.toString();
			String address = param2 == null ? "": param2.toString();
			if (!address.equals("")) {
				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM ADDRESS G WHERE A.CUST_ID = G.CUST_ID ");
				selectCondition.append(" AND G.ADDR LIKE '%");
				selectCondition.append(address);
				selectCondition.append("%') ");
				selectCondition.append(" or EXISTS(SELECT 1 FROM ADDRESS G WHERE c2.CUST_ID = G.CUST_ID ");
				selectCondition.append(" AND G.ADDR LIKE '%");
				selectCondition.append(address);
				selectCondition.append("%') ) ");
			}
			if (!contmeth.equals("")) {
				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM CONTMETH I WHERE A.CUST_ID = I.CUST_ID ");
				selectCondition.append(" AND I.CONTMETH_INFO = '");
				selectCondition.append(contmeth);
				selectCondition.append("') ");
				selectCondition.append(" or EXISTS(SELECT 1 FROM CONTMETH I WHERE c2.CUST_ID = I.CUST_ID ");
				selectCondition.append(" AND I.CONTMETH_INFO = '");
				selectCondition.append(contmeth);
				selectCondition.append("') ) ");
			}
		}
		selectCondition.append(" AND A.CUST_TYPE = '1' ORDER BY A.CUST_ID "); // 设置客户类型为个人类客户
		sql = sql + selectCondition.toString();
		return sql;
	}

	/**
	 * 拼接SQL
	 * @param sql
	 * @param tabName
	 * @param fieldValue
	 * @return
	 * @throws Exception
	 */
	public String orgSplitJointSql2(String sql, int tabNo, String param1, String param2, String param3) throws Exception {
		StringBuffer selectCondition = new StringBuffer(); 
		if (tabNo == 1) {
			String custNo = param1 == null ? "" : param1.toString();
			String name = param2 == null ? "" : param2.toString();
			String ebankRegNo = param3 == null ? "": param3.toString();
			if (!custNo.equals("")) {
				selectCondition.append(" AND (A.CUST_NO ='");
				selectCondition.append(custNo);
				selectCondition.append("' or  C2.CUST_NO='");
				selectCondition.append(custNo);
				selectCondition.append("') ");
			}
			if (!name.equals("")) {
				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM M_CI_NAMETITLE I WHERE A.CUST_ID = I.CUST_ID AND I.CUST_NAME like '%");
				selectCondition.append(name);
				selectCondition.append("%') ");
				selectCondition.append(" or EXISTS(SELECT 1 FROM M_CI_NAMETITLE I WHERE c2.CUST_ID = I.CUST_ID AND I.CUST_NAME like '%");
				selectCondition.append(name);
				selectCondition.append("%') ) ");
			}
			if (!ebankRegNo.equals("")) {
				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM ORGOTHERINFO J WHERE A.CUST_ID = J.CUST_ID AND J.EBANK_REG_NO = '");
				selectCondition.append(ebankRegNo);
				selectCondition.append("') ");
				selectCondition.append(" or EXISTS(SELECT 1 FROM ORGOTHERINFO J WHERE c2.CUST_ID = J.CUST_ID AND J.EBANK_REG_NO = '");
				selectCondition.append(ebankRegNo);
				selectCondition.append("') ) ");
			}
		}
		if (tabNo == 2) {
			String identType = param1 == null ? "": param1.toString();
			String identNo = param2 == null ? "": param2.toString();
			if (!identType.equals("")) {
				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM ORGIDENTIFIER H WHERE A.CUST_ID = H.CUST_ID ");
				selectCondition.append(" AND H.IDENT_TYPE ='");
				selectCondition.append(identType);
				selectCondition.append("' ");
				if (!identNo.equals("")) {
					selectCondition.append(" AND H.IDENT_NO ='");
					selectCondition.append(identNo);
					selectCondition.append("' ");
				}
				selectCondition.append(") ");
				selectCondition.append(" or EXISTS(SELECT 1 FROM ORGIDENTIFIER H WHERE c2.CUST_ID = H.CUST_ID ");
				selectCondition.append(" AND H.IDENT_TYPE ='");
				selectCondition.append(identType);
				selectCondition.append("' ");
				if (!identNo.equals("")) {
					selectCondition.append(" AND H.IDENT_NO ='");
					selectCondition.append(identNo);
					selectCondition.append("' ");
				}
				selectCondition.append(") ) ");
			}
		}
		if (tabNo == 3 && param1 != null && param1.toString().trim().length() > 0) {
			String productType = param1 == null ? "" : param1.toString();
			String productNo = param2 == null ? "" : param2.toString();
			switch (Integer.valueOf(productType)) {
			case 0:// 存款账户CUST_CONTR_REL
				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_DEPOSIT_ACCT G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ACCTNO = '" + productNo + "') "); // 存款账户账号
				} else {
					selectCondition.append(") ");
				}
				selectCondition.append(" or EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_DEPOSIT_ACCT G WHERE F.CUST_ID = c2.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ACCTNO = '" + productNo + "') ) "); // 存款账户账号
				} else {
					selectCondition.append(") ) ");
				}
				break;
			case 1:// 贷款账户LOANACCOUNT
				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_LOAN_ACCT G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ACCT_NO = '" + productNo + "') "); // 贷款账户账号
				} else {
					selectCondition.append(") ");
				}
				selectCondition.append(" or EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_LOAN_ACCT G WHERE F.CUST_ID = c2.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ACCT_NO = '" + productNo + "') ) "); // 贷款账户账号
				} else {
					selectCondition.append(") ) ");
				}
				break;
//			case 2:// 担保合同GUARANTYCONTRACT
//				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, GUARANTYCONTRACT G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
//				if (productNo != null && productNo.trim().length() > 0) {
//					selectCondition.append(" AND G.CONTR_NO = '" + productNo + "') "); // 担保合同编号
//				} else {
//					selectCondition.append(") ");
//				}
//				selectCondition.append(" or EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, GUARANTYCONTRACT G WHERE F.CUST_ID = c2.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
//				if (productNo != null && productNo.trim().length() > 0) {
//					selectCondition.append(" AND G.CONTR_NO = '" + productNo + "') ) "); // 担保合同编号
//				} else {
//					selectCondition.append(") ) ");
//				}
//				break;
//			case 3:// 借款合同LOANCONTRACT
//				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, LOANCONTRACT G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
//				if (productNo != null && productNo.trim().length() > 0) {
//					selectCondition.append(" AND G.CONTR_NO = '" + productNo + "') "); // 贷款合同编号
//				} else {
//					selectCondition.append(") ");
//				}
//				selectCondition.append(" or EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, LOANCONTRACT G WHERE F.CUST_ID = c2.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
//				if (productNo != null && productNo.trim().length() > 0) {
//					selectCondition.append(" AND G.CONTR_NO = '" + productNo + "') ) "); // 贷款合同编号
//				} else {
//					selectCondition.append(") ) ");
//				}
//				break;
			case 4:// 理财账户信息BDSHRACCTINFO
				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_WEALTH_ACCT G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ACCTNO = '" + productNo + "') "); // 理财账户信息理财账号
				} else {
					selectCondition.append(") ");
				}
				selectCondition.append(" or EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_WEALTH_ACCT G WHERE F.CUST_ID = c2.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ACCTNO = '" + productNo + "') ) "); // 理财账户信息理财账号
				} else {
					selectCondition.append(") ) ");
				}
				break;
			case 5:// 债券持有信息BONDCONTRACT
				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_BOND_ACCT G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ZQDM = '" + productNo + "') "); // 债券持有信息债券代码
				} else {
					selectCondition.append(") ");
				}
				selectCondition.append(" or EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_BOND_ACCT G WHERE F.CUST_ID = c2.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ZQDM = '" + productNo + "') ) "); // 债券持有信息债券代码
				} else {
					selectCondition.append(") ) ");
				}
				break;
			case 6:// 基金份额
				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_FUND_ACCT G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ASSET_ACC = '" + productNo + "') "); // 基金份额理财账号
				} else {
					selectCondition.append(") ");
				}
				selectCondition.append(" or EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_FUND_ACCT G WHERE F.CUST_ID = c2.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ASSET_ACC = '" + productNo + "') ) "); // 基金份额理财账号
				} else {
					selectCondition.append(") ) ");
				}
				break;
			case 7:// 网银协议
				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_EBANK_SIGN G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.IAB_ACCNO = '" + productNo + "') "); // 网银协议账号
				} else {
					selectCondition.append(") ");
				}
				selectCondition.append(" or EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_EBANK_SIGN G WHERE F.CUST_ID = c2.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.IAB_ACCNO = '" + productNo + "') ) "); // 网银协议账号
				} else {
					selectCondition.append(") ) ");
				}
				break;
			case 8:// 授信协议
				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_CONTRACT G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.CREDIT_CONTR_NO = '" + productNo + "') "); // 授信协议授信合同号
				} else {
					selectCondition.append(") ");
				}
				selectCondition.append(" or EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_CONTRACT G WHERE F.CUST_ID = c2.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.CREDIT_CONTR_NO = '" + productNo + "') ) "); // 授信协议授信合同号
				} else {
					selectCondition.append(") ) ");
				}
				break;
			case 9:// 黄金交易签约
				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_GOLD_SIGN G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.GOLDACCTNO = '" + productNo + "') "); // 黄金交易签约黄金账号
				} else {
					selectCondition.append(") ");
				}
				selectCondition.append(" or EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_GOLD_SIGN G WHERE F.CUST_ID = c2.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.GOLDACCTNO = '" + productNo + "') ) "); // 黄金交易签约黄金账号
				} else {
					selectCondition.append(") ) ");
				}
				break;
			case 10:// 网银签约
				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_EBANK_SIGN G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.BANK_ACC = '" + productNo + "') "); // 网银签约银行账号
				} else {
					selectCondition.append(") ");
				}
				selectCondition.append(" or EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_EBANK_SIGN G WHERE F.CUST_ID = c2.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.BANK_ACC = '" + productNo + "') ) "); // 网银签约银行账号
				} else {
					selectCondition.append(") ) ");
				}
				break;
//			case 11:// 凭证式债券BDSACCTINFO
//				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, BDSACCTINFO G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
//				if (productNo != null && productNo.trim().length() > 0) {
//					selectCondition.append(" AND G.ACCTNO = '" + productNo + "') "); // 
//				} else {
//					selectCondition.append(") ");
//				}
//				selectCondition.append(" or EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, BDSACCTINFO G WHERE F.CUST_ID = c2.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
//				if (productNo != null && productNo.trim().length() > 0) {
//					selectCondition.append(" AND G.ACCTNO = '" + productNo + "') ) "); // 
//				} else {
//					selectCondition.append(") ) ");
//				}
//				break;
			default:
				break;
			}
		}
		if (tabNo == 4) {
			StringBuffer sql0 = new StringBuffer();
			sql0.append(" WHERE 1 = 1 ");
			String contmeth = param1 == null ? "": param1.toString();
			String address = param2 == null ? "": param2.toString();
			if (!address.equals("")) {
				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM ADDRESS G WHERE A.CUST_ID = G.CUST_ID ");
				selectCondition.append(" AND G.ADDR LIKE '%");
				selectCondition.append(address);
				selectCondition.append("%') ");
				selectCondition.append(" or EXISTS(SELECT 1 FROM ADDRESS G WHERE c2.CUST_ID = G.CUST_ID ");
				selectCondition.append(" AND G.ADDR LIKE '%");
				selectCondition.append(address);
				selectCondition.append("%') ) ");
			}
			if (!contmeth.equals("")) {
				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM CONTMETH I WHERE A.CUST_ID = I.CUST_ID ");
				selectCondition.append(" AND I.CONTMETH_INFO = '");
				selectCondition.append(contmeth);
				selectCondition.append("') ");
				selectCondition.append(" or EXISTS(SELECT 1 FROM CONTMETH I WHERE c2.CUST_ID = I.CUST_ID ");
				selectCondition.append(" AND I.CONTMETH_INFO = '");
				selectCondition.append(contmeth);
				selectCondition.append("') ) ");
			}
		}
		selectCondition.append(" AND (A.CUST_TYPE = '2' or A.CUST_TYPE = '3') ORDER BY A.CUST_ID "); // 设置客户类型为机构类客户
		sql = sql + selectCondition.toString();
		return sql;
	}
}
