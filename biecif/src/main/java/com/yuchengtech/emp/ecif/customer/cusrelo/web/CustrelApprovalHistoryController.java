package com.yuchengtech.emp.ecif.customer.cusrelo.web;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

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
import com.yuchengtech.emp.ecif.customer.cusrelo.service.CustrelApprovalHistoryBS;
import com.yuchengtech.emp.ecif.customer.cusrelo.web.vo.CusRelationLookVO;
import com.yuchengtech.emp.ecif.customer.exportdata.service.ExportReportBS;

/**
 * <pre>
 * Title:客户关系查看
 * Description: 程序功能的描述
 * </pre>
 * 
 * @author wuhp wuhp@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Controller
@RequestMapping("/ecif/custrelapprovalhistory")
public class CustrelApprovalHistoryController extends BaseController {

	@Autowired
	private CustrelApprovalHistoryBS custrelApprovalHistoryBS;
	
	@Autowired
	private ExportReportBS exportReportBS;
	
	//一次进入不查询
	private boolean flag = false;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		flag = false;
		return "/ecif/customer/customerrel/custrelapprovalhistory";
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
		Map<String, Object> taskMap = Maps.newHashMap();
		SearchResult<CusRelationLookVO> searchResult = custrelApprovalHistoryBS.getCustrelApproval(
				pager.getPageFirstIndex(), pager.getPagesize(),
				pager.getSortname(), pager.getSortorder(),
				pager.getSearchCondition(), GlobalConstants.APPROVAL_STAT_1);
		
		taskMap.put("Rows", searchResult.getResult());
		taskMap.put("Total", searchResult.getTotalCount());
		
		return taskMap;
	}
	
	/**
	 * 下载文件
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "export.*", method = RequestMethod.POST)
	public void export(HttpServletResponse response, String file)
			throws Exception {

		ExportUtil.download(response, new File(file), "application/vnd.ms-excel");
		ExportUtil.deleteFile(file);
	}
	
	/**
	 * 下载文件
	 * @param repo
	 * @param condition
	 * @return
	 */
	@RequestMapping(value = "/getReportFile.*", method = RequestMethod.POST)
	@ResponseBody
	public String getReportFile(String reportNo,String rule) {
		int report = Integer.parseInt(reportNo);
		return exportReportBS.exportCustrel(report, rule);
	}
	
	
}
