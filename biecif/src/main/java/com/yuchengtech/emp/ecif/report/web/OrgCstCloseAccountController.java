package com.yuchengtech.emp.ecif.report.web;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.bione.entity.page.Pager;
import com.yuchengtech.emp.ecif.base.common.GlobalConstants;
import com.yuchengtech.emp.ecif.base.util.ExportUtil;
import com.yuchengtech.emp.ecif.report.constant.ReportConstant;
import com.yuchengtech.emp.ecif.report.service.RptCstAcctChangeInfoBS;


/**
 * 
 * 
 * <pre>
 * Title:对公客户销户情况Controller端
 * Description: 
 * </pre>
 * 
 * @author pengsenlin pengsl@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Controller
@RequestMapping("/ecif/report/closeaccount")
public class OrgCstCloseAccountController extends BaseController {

	//一次进入不查询
	private boolean flag = false;
	
	@Autowired
	private RptCstAcctChangeInfoBS rptCstAcctChangeInfoBS;
	
	protected static Logger log = LoggerFactory.getLogger(OrgCstCloseAccountController.class);
	
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		flag = false;
		return "/ecif/report/orgcstcloseaccount-index";
	}

	// grid形式展示// 对公客户AUM值分布情况
	@RequestMapping(value = "/list.*", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(Pager pager) {
		if(flag == false){
			flag = true;
			return null;
		}
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<Map<String, String>> searchResult = rptCstAcctChangeInfoBS
				.rptCstAcctChangeInfo(pager.getPageFirstIndex(),
						pager.getPagesize(), pager.getSortname(),
						pager.getSortorder(), pager.getSearchCondition(),
						ReportConstant.RPT_CUST_ACCT_CHANGE_INFO_CUST_ACCT_STS_CSTCLOSE);
		if(searchResult != null){
			userMap.put("Rows", searchResult.getResult());
			userMap.put("Total", searchResult.getTotalCount());
			return userMap;
		} else {
			return null;
		}
	}
	
	/**
	 * 下载文件
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "export.*", method = RequestMethod.POST)
	public void export(HttpServletResponse response, String file)
			throws Exception {
		Date d = new Date();
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		String date = f.format(d);
		ExportUtil.download(response, new File(file), "application/vnd.ms-excel", GlobalConstants.EXCEL_REPORT_ORGCSTCLOSE_CN + "_" + date + ".xlsx");
		ExportUtil.deleteFile(file);
	}
	
	/**
	 * 下载客户信息
	 * @param repo
	 * @param condition
	 * @return
	 */
	@RequestMapping(value = "/getExportFile.*", method = RequestMethod.POST)
	@ResponseBody
	public String getReportFile(String srptMonth, String erptMonth, String createBranchNo) {
		String custAcctSts = ReportConstant.RPT_CUST_ACCT_CHANGE_INFO_CUST_ACCT_STS_CSTCLOSE;
		return this.rptCstAcctChangeInfoBS.export(custAcctSts, srptMonth, erptMonth, createBranchNo);
	}
}
