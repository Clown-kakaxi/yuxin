/**
 * 
 */
package com.yuchengtech.emp.ecif.customer.customization.web;

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
import com.yuchengtech.emp.ecif.customer.customization.constant.CtztConstant;
import com.yuchengtech.emp.ecif.customer.customization.entity.DefinetableviewLookVO;
import com.yuchengtech.emp.ecif.customer.customization.entity.Operatorctztcolumn;
import com.yuchengtech.emp.ecif.customer.customization.service.CustomizationColumnQueryBS;
import com.yuchengtech.emp.ecif.customer.customization.service.OperColumnBS;
import com.yuchengtech.emp.ecif.customer.customization.service.OrgCustomizationBS;

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
@RequestMapping("/ecif/orgctzt")
public class OrgCtztController extends BaseController {

	protected static Logger log = LoggerFactory
			.getLogger(OrgCtztController.class);

	@Autowired
	private CustomizationColumnQueryBS customizationColumnQueryBS;
	
	@Autowired
	private OrgCustomizationBS orgCustomizationBS;
	
	@Autowired
	private OperColumnBS operColumnBS;
	
	@Autowired
	private CodeUtil codeUtil;
	
	private Boolean flag;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		flag = false;
		return "/ecif/customer/customization/orgcustomization-index";
	}
	
	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping(value = "/list.*", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(Pager pager) {
		if(flag == false){
			flag = true;
			return null;
		}
		Map<String, Object> resultMap = Maps.newHashMap();
		SearchResult<Map<String, String>> searchResult = orgCustomizationBS.queryCustomzationListMaps(
				pager.getPageFirstIndex(), pager.getPagesize(),
				pager.getSortname(), pager.getSortorder(),
				pager.getSearchCondition());
		if(searchResult != null){
			resultMap.put("Rows", searchResult.getResult());
			resultMap.put("Total", searchResult.getTotalCount());
			return resultMap;
		}else{
			return null;
		}
			
	}
	
	/**
	 * 获取定制表、列信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getTableColumn.*", method = RequestMethod.POST)
	public Map<String, Object> getTableColumn() {
		String tbType = CtztConstant.ECIF_CTZT_TABLE_TYPE_ORG;
		DefinetableviewLookVO[] tables = customizationColumnQueryBS.getIsResultDefinetableviews(tbType);
		DefinetableviewLookVO[] columns = customizationColumnQueryBS.getisMustColumn(tbType);
		Map<String, Object> map = Maps.newHashMap();
		map.put("tables", tables);
		map.put("columns", columns);
		return map;
	}
	
	/**
	 * 获取业务字典信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getComBoBox.*", method = RequestMethod.POST)
	public List<Map<String, String>> getComBoBox(String codeType) {
		return this.codeUtil.getComBoBox(codeType);
	}

	/**
	 * 获取部门树信息
	 */
	@RequestMapping(value = "/getColumns.*", method = RequestMethod.POST)
	@ResponseBody
	public List<CommonTreeNode> getCtzts() {
		String tbType = CtztConstant.ECIF_CTZT_TABLE_TYPE_ORG;
		List<CommonTreeNode> list = customizationColumnQueryBS.ctztTree(tbType);
		return list;
	}	
	
	/**
	 * 设置列是否为查询结果
	 */
	@RequestMapping(value = "/setupColumns.*", method = RequestMethod.POST)
	@ResponseBody
	public void setupColumn(String cumSerialNos) {
		BiOneUser user = BiOneSecurityUtils.getCurrentUserInfo();
		String oprno = user.getLoginName();
		String tbType = CtztConstant.ECIF_CTZT_TABLE_TYPE_ORG;
		this.customizationColumnQueryBS.deleteOprCztzColumn(oprno, tbType);
		if(cumSerialNos != null && cumSerialNos.length() != 0){
			cumSerialNos = cumSerialNos.substring(0, cumSerialNos.length()-1);
			if(cumSerialNos != null && cumSerialNos.length() > 0){
				String[] cumSerialNoss = cumSerialNos.split(",");
				for(String cumSerialNoStr : cumSerialNoss){
					int cumSerialNo = Integer.parseInt(cumSerialNoStr);
					Operatorctztcolumn occ = new Operatorctztcolumn();
					occ.setCstType(tbType);
					occ.setCumSerialNo(cumSerialNo);
					occ.setOprNo(oprno);
					this.operColumnBS.saveEntity(occ);
				}
			}
			
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
		ExportUtil.download(response, new File(file), "application/vnd.ms-excel", "对公客户定制查询客户信息_"+date+".xlsx");
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
	public String getReportFile(String rule) {
		return this.orgCustomizationBS.export(rule);
	}
}
