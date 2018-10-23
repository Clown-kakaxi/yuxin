/**
 * 
 */
package com.yuchengtech.emp.ecif.customer.special.web;

import java.io.File;
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
import com.yuchengtech.emp.ecif.base.util.CodeUtil;
import com.yuchengtech.emp.ecif.base.util.ExportUtil;
import com.yuchengtech.emp.ecif.customer.exportdata.service.ExportReportBS;
import com.yuchengtech.emp.ecif.customer.special.service.SpecialListApprovalBS;
import com.yuchengtech.emp.ecif.customer.special.web.vo.SpecialListApprovalVO;

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
@RequestMapping("/ecif/customer/speciallistapprovalhistroy")
public class SpecialListApprovalHistroyController extends BaseController {

	protected static Logger log = LoggerFactory
			.getLogger(SpecialListApprovalHistroyController.class);
	
	@Autowired
	private ExportReportBS exportReportBS;
	
	@Autowired
	private SpecialListApprovalBS specialListApprovalBS;
	
	@Autowired
	private CodeUtil codeUtil;
	
	//一次进入不查询
	private boolean flag = false;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		flag = false;
		return "/ecif/customer/special/speciallistapprovalhistroy-index";
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
		SearchResult<SpecialListApprovalVO> searchResult = specialListApprovalBS.getSpecialListApprovalList(
				pager.getPageFirstIndex(), pager.getPagesize(),
				pager.getSortname(), pager.getSortorder(),
				pager.getSearchCondition(), GlobalConstants.APPROVAL_STAT_2);
		userMap.put("Rows", searchResult.getResult());
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
	}
	
	/**
	 * 获取code combobox
	 */
	@ResponseBody
	@RequestMapping("getCodeComBoBox.*")
	public String getCodeComBoBox(String paramTypeNo, String paramValue) {
		//域名标识
		String flag = "";
		Map<String, String> temp = this.codeUtil.getCodeMap(flag);
		if(temp == null || temp.size() == 0){
			return paramValue;
		}
		
		String result = "";
		if(temp.get(paramValue)!= null && !temp.get(paramValue).equals("")){
			result = temp.get(paramValue);
		}else{
			return paramValue;
		}
		return result;
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
	@RequestMapping("/getReportFile")
	@ResponseBody
	public String getReportFile(
			String repo, String custNo, String identNo, 
			String lastUpdateSys, String specialListKind,
			String srptMonth, String erptMonth) {
		int report = Integer.parseInt(repo);
		return exportReportBS.export(
			report, custNo, identNo, lastUpdateSys, specialListKind, srptMonth, erptMonth);
	}

}
