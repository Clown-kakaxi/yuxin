/**
 * 
 */
package com.yuchengtech.emp.ecif.customer.suspect.web;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.bione.entity.page.Pager;
import com.yuchengtech.emp.ecif.base.common.GlobalConstants;
import com.yuchengtech.emp.ecif.base.util.CodeUtil;
import com.yuchengtech.emp.ecif.base.util.ExportUtil;
import com.yuchengtech.emp.ecif.customer.customerinfo.service.CustomerInfoBS;
import com.yuchengtech.emp.ecif.customer.entity.other.CustMergeRecordApproval;
import com.yuchengtech.emp.ecif.customer.exportdata.service.ExportReportBS;
import com.yuchengtech.emp.ecif.customer.importdata.service.ImportListBS;
import com.yuchengtech.emp.ecif.customer.suspect.service.CustMergeRecordAppBS;
import com.yuchengtech.emp.ecif.customer.suspect.service.CustMergeRecordBS;
import com.yuchengtech.emp.ecif.customer.suspect.service.SuspectGroupBS;

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
@Controller
@RequestMapping("/ecif/customer/custmergerecordapphis")
public class CustMergeRecordAppHisController extends BaseController {
	
	@Autowired
	private CustMergeRecordBS custMergeRecordBS;
	
	@Autowired
	private CustMergeRecordAppBS custMergeRecordAppBS;
	
	@Autowired
	private CodeUtil codeUtil;
	
	@Autowired
	private ExportReportBS exportReportBS;
	
	@Autowired
	private ImportListBS importListBS;
	
	@Autowired
	private SuspectGroupBS suspectGroupBS;
	
	@Autowired
	private CustomerInfoBS customerInfoBS;
	
	private Map<String, String> codeMapApprovalStatus = Maps.newHashMap();//审批状态
	
	//一次进入不查询
	private boolean flag = false;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		flag = false;
		return "/ecif/customer/suspect/custmergerecordapphis-index";
	}
	
	/**
	 * 获取combobox
	 */
	@ResponseBody
	@RequestMapping("getComBoBox.*")
	public List<Map<String,String>> getComBoBox() {
		
		List<Map<String, String>> harvComboList = Lists.newArrayList();
		Map<String, String> harvMap = Maps.newHashMap();
//		harvMap.put("id", GlobalConstants.APPROVAL_STAT_1);
//		harvMap.put("text", "待审批");
//		harvComboList.add(harvMap);
//		harvMap = Maps.newHashMap();
		harvMap.put("id", GlobalConstants.APPROVAL_STAT_2);
		harvMap.put("text", "审批通过");
		harvComboList.add(harvMap);
		harvMap = Maps.newHashMap();
		harvMap.put("id", GlobalConstants.APPROVAL_STAT_3);
		harvMap.put("text", "审批未通过");
		harvComboList.add(harvMap);
		
		return harvComboList;
	}
	
	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping("/list.*")
	@ResponseBody
	public Map<String, Object> list(Pager pager) {		
		if(flag == false){
			flag = true;
			return null;
		}
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<CustMergeRecordApproval> searchResult = custMergeRecordAppBS.getCustMergeRecordList(
				pager.getPageFirstIndex(), pager.getPagesize(),
				pager.getSortname(), pager.getSortorder(),
				pager.getSearchCondition(), GlobalConstants.APPROVAL_STAT_2);
		userMap.put("Rows", searchResult.getResult());
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
	}
	
	/**
	 * 下载文件
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "export.*", method = RequestMethod.POST)
	public void export(HttpServletResponse response, String file)
			throws Exception {

		if(StringUtils.isEmpty(file) || file.equals("[object Object]")){
			// 准备工作
			String realpath = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest().getSession()
				.getServletContext().getRealPath("/");
			file = realpath + GlobalConstants.EXCEL_TEMPLATE_IMPCUSTMERGERECORD_CN;
			ExportUtil.download(response, new File(file), "application/vnd.ms-excel");
		}else{
			ExportUtil.download(response, new File(file), "application/vnd.ms-excel");
			ExportUtil.deleteFile(file);
		}
	}
	
	/**
	 * 下载文件
	 * @param repo
	 * @param condition
	 * @return
	 */
	@RequestMapping("/getReportFile")
	@ResponseBody
	public String getReportFile(String repo, String custNo, String identNo, String lastUpdateSys) {
		int report = Integer.parseInt(repo);
		return exportReportBS.export(report, custNo, identNo, lastUpdateSys, "", "", "");
	}
	
	/**
	 * 获取审批状态的码值
	 */
	@ResponseBody
	@RequestMapping("getCodeMapApprovalStatus.*")
	public String getCodeMapApprovalStatus(String paramTypeNo, String paramValue){
		//
		if(codeMapApprovalStatus == null || codeMapApprovalStatus.isEmpty()){
			codeMapApprovalStatus = Maps.newHashMap();
			codeMapApprovalStatus.put(GlobalConstants.APPROVAL_STAT_2, "审批通过");
			codeMapApprovalStatus.put(GlobalConstants.APPROVAL_STAT_3, "审批未通过");
		}
		String result = "";
		if(codeMapApprovalStatus.get(paramValue)!= null && !codeMapApprovalStatus.get(paramValue).equals("")){
			result = codeMapApprovalStatus.get(paramValue);
		}else{
			return paramValue;
		}
		return result;
	}
}
