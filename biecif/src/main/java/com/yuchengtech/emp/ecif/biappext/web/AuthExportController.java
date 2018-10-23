/**
 * 
 */
package com.yuchengtech.emp.ecif.biappext.web;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.biappframe.security.BiOneUser;
import com.yuchengtech.emp.bione.common.CommonTreeNode;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.bione.entity.page.Pager;
import com.yuchengtech.emp.ecif.base.util.CodeUtil;
import com.yuchengtech.emp.ecif.base.util.ExportUtil;
import com.yuchengtech.emp.ecif.biappext.service.AuthExportBS;
import com.yuchengtech.emp.ecif.customer.customization.constant.CtztConstant;
import com.yuchengtech.emp.ecif.customer.customization.entity.DefinetableviewLookVO;
import com.yuchengtech.emp.ecif.customer.customization.entity.Operatorctztcolumn;
import com.yuchengtech.emp.ecif.customer.customization.service.OperColumnBS;

/**
 * <pre>
 * Title:程序的中文名称
 * Description: 程序功能的描述
 * </pre>
 * 
 * @author caiqy caiqy@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Controller
@RequestMapping("/ecif/authexport")
public class AuthExportController extends BaseController {

	protected static Logger log = LoggerFactory
			.getLogger(AuthExportController.class);
	
	@Autowired
	private AuthExportBS authExportBS;
	
	@Autowired
	private CodeUtil codeUtil;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "/bione/role/role-index";
	}
	
	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping(value = "/list.*", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(Pager pager) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<Map<String, String>> searchResult = authExportBS.queryCustomzationListMaps(
				pager.getPageFirstIndex(), pager.getPagesize(),
				pager.getSortname(), pager.getSortorder(),
				pager.getSearchCondition());
		if(searchResult != null){
			userMap.put("Rows", searchResult.getResult());
			userMap.put("Total", searchResult.getTotalCount());
			return userMap;
		}else{
			return null;
		}
	}
		
	/**
	 * 下载用户角色信息
	 * @param repo
	 * @param condition
	 * @return
	 */
	@RequestMapping(value = "/getExportFile.*", method = RequestMethod.POST)
	@ResponseBody
	public String getReportFile(String ids) {
		return this.authExportBS.export(ids);
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
		ExportUtil.download(response, new File(file), "application/vnd.ms-excel", "用户角色信息_"+date+".xlsx");
		ExportUtil.deleteFile(file);
	}
	
	
	/**
	 * 下载用角色户信息
	 * @param repo
	 * @param condition
	 * @return
	 */
	@RequestMapping(value = "/getExportFileGrant.*", method = RequestMethod.POST)
	@ResponseBody
	public String getReportFileGrant(String ids) {
		return this.authExportBS.exportGrant(ids);
	}
	
	/**
	 * 下载文件
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "exportGrant.*", method = RequestMethod.POST)
	public void exportGrant(HttpServletResponse response, String file)
			throws Exception {
		Date d = new Date();
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		String date = f.format(d);
		ExportUtil.download(response, new File(file), "application/vnd.ms-excel", "角色权限信息_"+date+".xlsx");
		ExportUtil.deleteFile(file);
	}
	
}
