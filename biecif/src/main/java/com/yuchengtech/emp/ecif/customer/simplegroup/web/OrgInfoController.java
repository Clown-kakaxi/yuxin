package com.yuchengtech.emp.ecif.customer.simplegroup.web;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import nl.justobjects.pushlet.util.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.bione.common.CommonTreeNode;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.ecif.base.common.Pager2;
import com.yuchengtech.emp.ecif.base.util.ExportUtil;
import com.yuchengtech.emp.ecif.customer.exportdata.service.ExportReportBS;
import com.yuchengtech.emp.ecif.customer.simplegroup.service.OrgInfoBS;
import com.yuchengtech.emp.ecif.customer.simplegroup.web.vo.OrgBasicVO;
import com.yuchengtech.emp.ecif.customer.simplegroup.web.vo.OrgRiskVO;
import com.yuchengtech.emp.ecif.customer.simplegroup.web.vo.PerFocusVO;
import com.yuchengtech.emp.ecif.customer.simplegroup.web.vo.PerProductVO;
import com.yuchengtech.emp.ecif.customer.simplegroup.web.vo.PerRelativeVO;

/**
 * 
 * 
 * <pre>
 * Title:客户详细信息展示的Controller端
 * Description: 处理客户信息的CRUD操作
 * </pre>
 * 
 * @author zhengyukun zhengyk3@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Controller
@RequestMapping("/ecif/orginfo")
public class OrgInfoController extends BaseController {

	@Autowired
	private OrgInfoBS orgBS;
	
	@Autowired
	private ExportReportBS exportReportBS;
	
	//一次进入不查询
	private boolean flag = false;

	/**
	 * 显示主页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/orgdetailed", method = RequestMethod.POST)
	public ModelAndView perDetailedIndex(@RequestParam("custId") String custId, @RequestParam("queryName") String queryName, @RequestParam("tabTag") String tabTag, @RequestParam("rule") String rule, @RequestParam("pageName") String pageName) {
		JSONArray rulesJson = JSONObject.fromObject(rule).getJSONArray("rules");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("custId", custId);
		map.put("queryName", queryName);
		map.put("tabTag", tabTag);
		for (Iterator<?> conditioniter = rulesJson.iterator(); conditioniter.hasNext();) {
			JSONObject jsonstr = (JSONObject) conditioniter.next();
			map.put((String) jsonstr.get("field"), (String) jsonstr.get("value"));
		}
		map.put("pageName", pageName);
		flag = false;
		return new ModelAndView("/ecif/customer/simplegroup/org-detailedinfo-index", map);
	}

	/**
	 * 返回跳转控制
	 * 
	 * @return
	 */
	@RequestMapping(value = "/detailedreturn", method = RequestMethod.GET)
	public ModelAndView detailedReturn(@RequestParam("tabTag") String tabTag, @RequestParam("rule") String rule, @RequestParam("pageName") String pageName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("returnTabTag", tabTag);
		if(rule != null && !rule.equals("")){
			JSONArray rulesJson = JSONObject.fromObject(rule).getJSONArray("rules");
			String existsRule = "false";
			for (Iterator<?> conditioniter = rulesJson.iterator(); conditioniter.hasNext();) {
				JSONObject jsonstr = (JSONObject) conditioniter.next();
				map.put((String) jsonstr.get("field"), (String) jsonstr.get("value"));
				existsRule = "true";
			}
			map.put("existsRule", existsRule);
		}
		ModelAndView mv = null;
		if(pageName.equals("org-basicinfo-index")){
			mv = new ModelAndView("/ecif/customer/simplegroup/org-basicinfo-index", map);
		}
		if(pageName.equals("org-focusinfo-index")){
			mv = new ModelAndView("/ecif/customer/simplegroup/org-focusinfo-index", map);
		}
		if(pageName.equals("org-productinfo-index")){
			mv = new ModelAndView("/ecif/customer/simplegroup/org-productinfo-index", map);
		}
		if(pageName.equals("org-relativeinfo-index")){
			mv = new ModelAndView("/ecif/customer/simplegroup/org-relativeinfo-index", map);
		}
		if(pageName.equals("org-analysisinfo-index")){
			mv = new ModelAndView("/ecif/customer/simplegroup/org-analysisinfo-index", map);
		}
		return mv;
	}
	
	@RequestMapping(value = "/orgbasic", method = RequestMethod.GET)
	public String perBasicIndex() {
		flag = false;
		return "/ecif/customer/simplegroup/org-basicinfo-index";
	}

	@RequestMapping(value = "/orgfocus", method = RequestMethod.GET)
	public String perFocusIndex() {
		flag = false;
		return "/ecif/customer/simplegroup/org-focusinfo-index";
	}

	@RequestMapping(value = "/orgproduct", method = RequestMethod.GET)
	public String perProductIndex() {
		flag = false;
		return "/ecif/customer/simplegroup/org-productinfo-index";
	}

	@RequestMapping(value = "/organalysis", method = RequestMethod.GET)
	public String perAnalysisIndex() {
		flag = false;
		return "/ecif/customer/simplegroup/org-analysisinfo-index";
	}
	
	@RequestMapping(value = "/orgrelative", method = RequestMethod.GET)
	public String perRelativeIndex() {
		flag = false;
		return "/ecif/customer/simplegroup/org-relativeinfo-index";
	}
	
	/**
	 * 下载导出报表
	 * 
	 * @param response
	 * @param file
	 * @throws Exception
	 */
	@RequestMapping(value = "/orgbasic/export.*", method = RequestMethod.POST)
	public void exportOrgBasic(HttpServletResponse response, String file)
			throws Exception {

		ExportUtil.download(response, new File(file),
				"application/vnd.ms-excel");
		ExportUtil.deleteFile(file);
	}
	
	@RequestMapping(value = "/orgfocus/export.*", method = RequestMethod.POST)
	public void exportOrgFocus(HttpServletResponse response, String file)
			throws Exception {

		ExportUtil.download(response, new File(file),
				"application/vnd.ms-excel");
		ExportUtil.deleteFile(file);
	}
	
	@RequestMapping(value = "/orgrelative/export.*", method = RequestMethod.POST)
	public void exportOrgRelative(HttpServletResponse response, String file)
			throws Exception {

		ExportUtil.download(response, new File(file),
				"application/vnd.ms-excel");
		ExportUtil.deleteFile(file);
	}
	
	@RequestMapping(value = "/organalysis/export.*", method = RequestMethod.POST)
	public void exportOrgAnalysis(HttpServletResponse response, String file)
			throws Exception {

		ExportUtil.download(response, new File(file),
				"application/vnd.ms-excel");
		ExportUtil.deleteFile(file);
	}
	
	@RequestMapping(value = "/orgproduct/export.*", method = RequestMethod.POST)
	public void exportOrgProduct(HttpServletResponse response, String file)
			throws Exception {

		ExportUtil.download(response, new File(file),
				"application/vnd.ms-excel");
		ExportUtil.deleteFile(file);
	}
	
	/**
	 * 获取报表excel
	 * @param param0
	 * @param param1
	 * @param param2
	 * @param param3
	 * @return
	 */
	@RequestMapping(value = "/orgbasic/getReportFile")
	@ResponseBody
	public String getOrgBasicFile(String param0, String param1, String param2,
			String param3) {
		int tabNo = 0;
		if (param0.equals("basic")) {
			tabNo = 1;
		} else if (param0.equals("styles")) {
			tabNo = 2;
		} else if (param0.equals("product")) {
			tabNo = 3;
		} else if (param0.equals("verify")) {
			tabNo = 4;
		}
		int firstResult = 0, pageSize = 5000; // 设置查询超行号，每页显示多少条
		return exportReportBS.exportOrgInfo(firstResult, pageSize, 1, tabNo, param1, param2, param3);
	}

	@RequestMapping(value = "/orgfocus/getReportFile")
	@ResponseBody
	public String getOrgFocusFile(String param0, String param1, String param2,
			String param3) {
		int tabNo = 0;
		if (param0.equals("basic")) {
			tabNo = 1;
		} else if (param0.equals("styles")) {
			tabNo = 2;
		} else if (param0.equals("product")) {
			tabNo = 3;
		} else if (param0.equals("verify")) {
			tabNo = 4;
		}
		int firstResult = 0, pageSize = 5000; // 设置查询超行号，每页显示多少条
		return exportReportBS.exportOrgInfo(firstResult, pageSize, 2, tabNo, param1, param2, param3);
	}

	@RequestMapping(value = "/orgproduct/getReportFile")
	@ResponseBody
	public String getOrgProductFile(String param0, String param1,
			String param2, String param3) {
		int tabNo = 0;
		if (param0.equals("basic")) {
			tabNo = 1;
		} else if (param0.equals("styles")) {
			tabNo = 2;
		} else if (param0.equals("product")) {
			tabNo = 3;
		} else if (param0.equals("verify")) {
			tabNo = 4;
		}
		int firstResult = 0, pageSize = 5000; // 设置查询超行号，每页显示多少条
		return exportReportBS.exportOrgInfo(firstResult, pageSize, 3, tabNo, param1, param2, param3);
	}

	@RequestMapping(value = "/organalysis/getReportFile")
	@ResponseBody
	public String getOrgAnalysisFile(String param0, String param1,
			String param2, String param3) {
		int tabNo = 0;
		if (param0.equals("basic")) {
			tabNo = 1;
		} else if (param0.equals("styles")) {
			tabNo = 2;
		} else if (param0.equals("product")) {
			tabNo = 3;
		} else if (param0.equals("verify")) {
			tabNo = 4;
		}
		int firstResult = 0, pageSize = 5000; // 设置查询超行号，每页显示多少条
		return exportReportBS.exportOrgInfo(firstResult, pageSize, 4, tabNo, param1, param2, param3);
	}
	
	@RequestMapping(value = "/orgrelative/getReportFile")
	@ResponseBody
	public String getOrgRelativeFile(String param0, String param1,
			String param2, String param3) {
		int tabNo = 0;
		if (param0.equals("basic")) {
			tabNo = 1;
		} else if (param0.equals("styles")) {
			tabNo = 2;
		} else if (param0.equals("product")) {
			tabNo = 3;
		} else if (param0.equals("verify")) {
			tabNo = 4;
		}
		int firstResult = 0, pageSize = 5000; // 设置查询超行号，每页显示多少条
		return exportReportBS.exportOrgInfo(firstResult, pageSize, 5, tabNo, param1, param2, param3);
	}
	
	/**
	 * 客户基本信息页面
	 * @param pager
	 * @return
	 */
	@RequestMapping("/orgbasic/list.*")
	@ResponseBody
	public Map<String, Object> perBasicList(Pager2 pager) {
//		if(flag == false){
//			flag = true;
//			return null;
//		}
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<OrgBasicVO> searchResult = orgBS.getOrgBasicVOList(
				pager.getPageFirstIndex(), pager.getPagesize(),
				pager.getSortname(), pager.getSortorder(),
				pager.getSearchCondition(), pager.getTabName());
		if(searchResult != null){
			userMap.put("Rows", searchResult.getResult());
			userMap.put("Total", searchResult.getTotalCount());
			//flag = false;
			return userMap;
		}
		return null;
	}
	
	/**
	 * 客户产品信息页面
	 * @param pager
	 * @return
	 */
	@RequestMapping("/orgproduct/list.*")
	@ResponseBody
	public Map<String, Object> orgProductList(Pager2 pager) {
//		if(flag == false){
//			flag = true;
//			return null;
//		}
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<PerProductVO> searchResult = orgBS.getOrgProductVOList(
				pager.getPageFirstIndex(), pager.getPagesize(),
				pager.getSortname(), pager.getSortorder(),
				pager.getSearchCondition(), pager.getTabName());
		if(searchResult != null){
			userMap.put("Rows", searchResult.getResult());
			userMap.put("Total", searchResult.getTotalCount());
			return userMap;
		}
		return null;
	}
	
	/**
	 * 客户关注事件页面
	 * @param pager
	 * @return
	 */
	@RequestMapping("/orgfocus/list.*")
	@ResponseBody
	public Map<String, Object> orgFocusList(Pager2 pager) {
//		if(flag == false){
//			flag = true;
//			return null;
//		}
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<PerFocusVO> searchResult = orgBS.getOrgFocusVOList(
				pager.getPageFirstIndex(), pager.getPagesize(),
				pager.getSortname(), pager.getSortorder(),
				pager.getSearchCondition(), pager.getTabName());
		if(searchResult != null){
			userMap.put("Rows", searchResult.getResult());
			userMap.put("Total", searchResult.getTotalCount());
			return userMap;
		}
		return null;
	}
	
	/**
	 * 客户关联信息
	 * 
	 * @param pager
	 * @return
	 */
	@RequestMapping("/orgrelative/list.*")
	@ResponseBody
	public Map<String, Object> perRelativeList(Pager2 pager) {
//		if(flag == false){
//			flag = true;
//			return null;
//		}
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<PerRelativeVO> searchResult = orgBS.getorgRelativeList(
				pager.getPageFirstIndex(), pager.getPagesize(),
				pager.getSortname(), pager.getSortorder(),
				pager.getSearchCondition(), pager.getTabName());
		if(searchResult != null){
			userMap.put("Rows", searchResult.getResult());
			userMap.put("Total", searchResult.getTotalCount());
			return userMap;
		}
		return null;
	}
	
	/**
	 * 客户分析信息
	 * 
	 * @param pager
	 * @return
	 */
	@RequestMapping("/organalysis/list.*")
	@ResponseBody
	public Map<String, Object> perAnalysisList(Pager2 pager) {
//		if(flag == false){
//			flag = true;
//			return null;
//		}
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<OrgRiskVO> searchResult = orgBS.getorgAnalysisList(
				pager.getPageFirstIndex(), pager.getPagesize(),
				pager.getSortname(), pager.getSortorder(),
				pager.getSearchCondition(), pager.getTabName());
		if(searchResult != null){
			userMap.put("Rows", searchResult.getResult());
			userMap.put("Total", searchResult.getTotalCount());
			return userMap;
		}
		return null;
	}
	
	/**
	 * 显示树列表的信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/gettree", method = RequestMethod.GET)
	@ResponseBody
	public List<CommonTreeNode> getCurrentTree(String id, String level, String text) {
		List<CommonTreeNode> treeList = new ArrayList<CommonTreeNode>();
		CommonTreeNode treeNode = new CommonTreeNode();
		if (id == null || "".equals(id)) {
			Map<String, String> params = Maps.newHashMap();
			params.put("level", "0");
			params.put("URL", "");
			treeNode.setParams(params);
			treeNode.setId("000000");
			treeNode.setUpId(null);
			treeNode.setParams(params);
			treeNode.setText("信息查询");
			treeNode.setIsexpand(true);
			treeNode.setIsParent(true);
			treeList.add(treeNode);
		} else {
			if ("0".equals(level)) {
				getTableTypeTree(treeList);
			} else {
				getTableTree(id, text, treeList);
			}
		}
		return treeList;
	}


	public void getTableTypeTree(List<CommonTreeNode> treeList) {
		// 加载所有信息表
//		String[] customerInfo = { "客户基本信息", "客户联系信息", "客户关联信息", "客户财务信息",
//				"客户风险信息", "客户评价信息", "客户往来信息", "客户管理信息", "客户其他信息", "产品", "协议",
//				"事件", "资产" };
	
		String[] customerInfo = { "客户基本信息", "客户联系信息", "客户关联信息", "客户财务信息",
				"客户风险信息", "客户评价信息", "客户管理信息", "客户其他信息", 
				 "资产" };
		
		// 加载所有系统节点的信息
		for (int i = 0; i < customerInfo.length; i++) {
			CommonTreeNode treeNode = new CommonTreeNode();
			String indexStr = Integer.toString(i + 1);
			if(indexStr.length() == 1){
				indexStr = "00"+indexStr+"000";
			} else if(indexStr.length() == 2){
				indexStr = "0"+indexStr+"000";
			} else if(indexStr.length() == 3){
				indexStr = indexStr+"000";
			} else{
	        	try {
					throw new Exception("分类不合理，请重新分类！");
				} catch (Exception e) {
					Log.error(e.getMessage());
				}
			}
			treeNode.setId(indexStr);
			Map<String, String> params = Maps.newHashMap();
			params.put("level", "1");
			params.put("URL", "");
			treeNode.setParams(params);
			treeNode.setText(customerInfo[i]);
			treeNode.setUpId("000000");
			treeNode.setIsexpand(false);
			treeNode.setIsParent(true);
			treeList.add(treeNode);
		}
	}

	// 重载函数加载二级节点
	public void getTableTree(String id, String treeNodeName, List<CommonTreeNode> treeList) {
		String nodeIdBegin = id.substring(0, 3);
		if (treeNodeName.equalsIgnoreCase("客户基本信息")) {
			String[][] customerInfos = new String[5][2];
			customerInfos[0][0] = "基本概况";
			customerInfos[0][1] = "/ecif/customer/simplegroup/orgbasic/orgprofile-index";
			customerInfos[1][0] = "名称称谓";
			customerInfos[1][1] = "/ecif/customer/simplegroup/orgbasic/nametitle-index";
			customerInfos[2][0] = "基本账户";
			customerInfos[2][1] = "/ecif/customer/simplegroup/orgbasic/basicaccount-index";
//			customerInfos[3][0] = "其他账户";
//			customerInfos[3][1] = "/ecif/customer/simplegroup/orgbasic/otheraccount-index";
			customerInfos[3][0] = "注册信息";
			customerInfos[3][1] = "/ecif/customer/simplegroup/orgbasic/registerinfo-index";
			customerInfos[4][0] = "法人证件信息";
			customerInfos[4][1] = "/ecif/customer/simplegroup/orgbasic/orgidentinfo-index";
//			customerInfos[6][0] = "机构客户重要编号";
//			customerInfos[6][1] = "/ecif/customer/simplegroup/orgbasic/orgkeyno-index";
//			customerInfos[7][0] = "机构客户重要指标";
//			customerInfos[7][1] = "/ecif/customer/simplegroup/orgbasic/orgkeyindex-index";
//			customerInfos[8][0] = "机构客户重要标志";
//			customerInfos[8][1] = "/ecif/customer/simplegroup/orgbasic/orgkeyflag-index";
//			customerInfos[9][0] = "机构客户扩展信息";
//			customerInfos[9][1] = "/ecif/customer/simplegroup/orgbasic/orgextend-index";
			// 加载所有系统节点的信息
			for (int i = 0; i < customerInfos.length; i++) {
				CommonTreeNode treeNode = new CommonTreeNode();
				String indexStr = Integer.toString(i + 1);
				if(indexStr.length() == 1){
					indexStr = nodeIdBegin+"00"+indexStr;
				} else if(indexStr.length() == 2){
					indexStr = nodeIdBegin+"0"+indexStr;
				} else if(indexStr.length() == 3){
					indexStr = nodeIdBegin+indexStr;
				} else{
		        	try {
						throw new Exception("分类不合理，请重新分类！");
					} catch (Exception e) {
						Log.error(e.getMessage());
					}
				}
				treeNode.setId(indexStr);
				Map<String, String> params = Maps.newHashMap();
				params.put("level", "2");
				params.put("URL", customerInfos[i][1]);
				params.put("tableUrl", "/ecif/perbasic/form");
				treeNode.setParams(params);
				treeNode.setText(customerInfos[i][0]);
				treeNode.setUpId(id);
				treeNode.setIsexpand(false);
				treeNode.setIsParent(false);
				treeList.add(treeNode);
			}
		} else if (treeNodeName.equalsIgnoreCase("客户联系信息")) {
			String[][] customerInfos = new String[2][2];
			customerInfos[0][0] = "地址信息";
			customerInfos[0][1] = "/ecif/customer/simplegroup/customercontact/address-index";
			customerInfos[1][0] = "联系信息";
			customerInfos[1][1] = "/ecif/customer/simplegroup/customercontact/contmeth-index";
			// 加载所有系统节点的信息
			for (int i = 0; i < customerInfos.length; i++) {
				CommonTreeNode treeNode = new CommonTreeNode();
				String indexStr = Integer.toString(i + 1);
				if(indexStr.length() == 1){
					indexStr = nodeIdBegin+"00"+indexStr;
				} else if(indexStr.length() == 2){
					indexStr = nodeIdBegin+"0"+indexStr;
				} else if(indexStr.length() == 3){
					indexStr = nodeIdBegin+indexStr;
				} else{
		        	try {
						throw new Exception("分类不合理，请重新分类！");
					} catch (Exception e) {
						Log.error(e.getMessage());
					}
				}
				treeNode.setId(indexStr);
				Map<String, String> params = Maps.newHashMap();
				params.put("level", "2");
				params.put("URL", customerInfos[i][1]);
				params.put("tableUrl", "/ecif/perbasic/form");
				treeNode.setParams(params);
				treeNode.setText(customerInfos[i][0]);
				treeNode.setUpId(id);
				treeNode.setIsexpand(false);
				treeNode.setIsParent(false);
				treeList.add(treeNode);
			}
		} else if (treeNodeName.equalsIgnoreCase("客户关联信息")) {
			String[][] customerInfos = new String[2][2];
			customerInfos[0][0] = "高管信息";
			customerInfos[0][1] = "/ecif/customer/simplegroup/orgrelative/executiveinfo-index";
			customerInfos[1][0] = "股东信息";
			customerInfos[1][1] = "/ecif/customer/simplegroup/orgrelative/holderinfo-index";
//			customerInfos[2][0] = "经办人信息";
//			customerInfos[2][1] = "/ecif/customer/simplegroup/orgrelative/operatorinfo-index";
//			customerInfos[3][0] = "代理人信息";
//			customerInfos[3][1] = "/ecif/customer/simplegroup/orgrelative/agentinfo-index";
//			customerInfos[4][0] = "机构客户联系人";
//			customerInfos[4][1] = "/ecif/customer/simplegroup/orgrelative/orgkeylinkman-index";
//			customerInfos[5][0] = "上游供货商";
//			customerInfos[5][1] = "/ecif/customer/simplegroup/orgrelative/supplier-index";
//			customerInfos[6][0] = "下游销售商";
//			customerInfos[6][1] = "/ecif/customer/simplegroup/orgrelative/seller-index";
//			customerInfos[7][0] = "关联企业信息";
//			customerInfos[7][1] = "/ecif/customer/simplegroup/orgrelative/relativecorp-index";
//			customerInfos[8][0] = "实际控制人信息";
//			customerInfos[8][1] = "/ecif/customer/simplegroup/orgrelative/actualcontroller-index";
//			customerInfos[9][0] = "法定代表人信息";
//			customerInfos[9][1] = "/ecif/customer/simplegroup/orgrelative/legalreprinfo-index";
//			customerInfos[10][0] = "上下游企业信息";
//			customerInfos[10][1] = "/ecif/customer/simplegroup/orgrelative/updownstreaminfo-index";
//			customerInfos[11][0] = "机构对外担保信息";
//			customerInfos[11][1] = "/ecif/customer/simplegroup/orgrelative/orgassuinfo-index";
//			customerInfos[12][0] = "机构对外投资信息";
//			customerInfos[12][1] = "/ecif/customer/simplegroup/orgrelative/orginvestinfo-index";
			// 加载所有系统节点的信息
			for (int i = 0; i < customerInfos.length; i++) {
				CommonTreeNode treeNode = new CommonTreeNode();
				String indexStr = Integer.toString(i + 1);
				if(indexStr.length() == 1){
					indexStr = nodeIdBegin+"00"+indexStr;
				} else if(indexStr.length() == 2){
					indexStr = nodeIdBegin+"0"+indexStr;
				} else if(indexStr.length() == 3){
					indexStr = nodeIdBegin+indexStr;
				} else{
		        	try {
						throw new Exception("分类不合理，请重新分类！");
					} catch (Exception e) {
						Log.error(e.getMessage());
					}
				}
				treeNode.setId(indexStr);
				Map<String, String> params = Maps.newHashMap();
				params.put("level", "2");
				params.put("URL", customerInfos[i][1]);
				params.put("tableUrl", "/ecif/perbasic/form");
				treeNode.setParams(params);
				treeNode.setText(customerInfos[i][0]);
				treeNode.setUpId(id);
				treeNode.setIsexpand(false);
				treeNode.setIsParent(false);
				treeList.add(treeNode);
			}
		} else if (treeNodeName.equalsIgnoreCase("客户财务信息")) {
			String[][] customerInfos = new String[5][2];
			customerInfos[0][0] = "纳税信息";
			customerInfos[0][1] = "/ecif/customer/simplegroup/orgfinance/taxinfo-index";
			customerInfos[1][0] = "上市信息";
			customerInfos[1][1] = "/ecif/customer/simplegroup/orgfinance/issuestock-index";
			customerInfos[2][0] = "财务简表";
			customerInfos[2][1] = "/ecif/customer/simplegroup/orgfinance/financebriefreport-index";
			customerInfos[3][0] = "机构经营信息";
			customerInfos[3][1] = "/ecif/customer/simplegroup/orgfinance/orgbusiinfo-index";
			customerInfos[4][0] = "发行债券信息";
			customerInfos[4][1] = "/ecif/customer/simplegroup/orgfinance/issuebond-index";
//			customerInfos[5][0] = "机构其他资产负债";
//			customerInfos[5][1] = "/ecif/customer/simplegroup/orgfinance/otherassetdebt-index";
			// 加载所有系统节点的信息
			for (int i = 0; i < customerInfos.length; i++) {
				CommonTreeNode treeNode = new CommonTreeNode();
				String indexStr = Integer.toString(i + 1);
				if(indexStr.length() == 1){
					indexStr = nodeIdBegin+"00"+indexStr;
				} else if(indexStr.length() == 2){
					indexStr = nodeIdBegin+"0"+indexStr;
				} else if(indexStr.length() == 3){
					indexStr = nodeIdBegin+indexStr;
				} else{
		        	try {
						throw new Exception("分类不合理，请重新分类！");
					} catch (Exception e) {
						Log.error(e.getMessage());
					}
				}
				treeNode.setId(indexStr);
				Map<String, String> params = Maps.newHashMap();
				params.put("level", "2");
				params.put("URL", customerInfos[i][1]);
				params.put("tableUrl", "/ecif/perbasic/form");
				treeNode.setParams(params);
				treeNode.setText(customerInfos[i][0]);
				treeNode.setUpId(id);
				treeNode.setIsexpand(false);
				treeNode.setIsParent(false);
				treeList.add(treeNode);
			}
		} else if (treeNodeName.equalsIgnoreCase("客户风险信息")) {
			String[][] customerInfos = new String[7][2];
			customerInfos[0][0] = "评级信息";
			customerInfos[0][1] = "/ecif/customer/simplegroup/orgrisk/rating-index";
			customerInfos[1][0] = "违约信息";
			customerInfos[1][1] = "/ecif/customer/simplegroup/orgrisk/defaultinfo-index";
			customerInfos[2][0] = "欠息信息";
			customerInfos[2][1] = "/ecif/customer/simplegroup/orgrisk/overinterest-index";
			customerInfos[3][0] = "虚假情况";
			customerInfos[3][1] = "/ecif/customer/simplegroup/orgrisk/mendinfo-index";
			customerInfos[4][0] = "特殊名单";
			customerInfos[4][1] = "/ecif/customer/simplegroup/orgrisk/speciallist-index";
			customerInfos[5][0] = "风险预警信号";
			customerInfos[5][1] = "/ecif/customer/simplegroup/orgrisk/risksignal-index";
			customerInfos[6][0] = "综合授信信息";
			customerInfos[6][1] = "/ecif/customer/simplegroup/orgrisk/custcreditinfo-index";
			// 加载所有系统节点的信息
			for (int i = 0; i < customerInfos.length; i++) {
				CommonTreeNode treeNode = new CommonTreeNode();
				String indexStr = Integer.toString(i + 1);
				if(indexStr.length() == 1){
					indexStr = nodeIdBegin+"00"+indexStr;
				} else if(indexStr.length() == 2){
					indexStr = nodeIdBegin+"0"+indexStr;
				} else if(indexStr.length() == 3){
					indexStr = nodeIdBegin+indexStr;
				} else{
		        	try {
						throw new Exception("分类不合理，请重新分类！");
					} catch (Exception e) {
						Log.error(e.getMessage());
					}
				}
				treeNode.setId(indexStr);
				Map<String, String> params = Maps.newHashMap();
				params.put("level", "2");
				params.put("URL", customerInfos[i][1]);
				params.put("tableUrl", "/ecif/perbasic/form");
				treeNode.setParams(params);
				treeNode.setText(customerInfos[i][0]);
				treeNode.setUpId(id);
				treeNode.setIsexpand(false);
				treeNode.setIsParent(false);
				treeList.add(treeNode);
			}
		} else if (treeNodeName.equalsIgnoreCase("客户评价信息")) {
			String[][] customerInfos = new String[4][2];
			customerInfos[0][0] = "评分信息";
			customerInfos[0][1] = "/ecif/customer/simplegroup/orgevaluate/scoreinfo-index";
			customerInfos[1][0] = "征信信息";
			customerInfos[1][1] = "/ecif/customer/simplegroup/orgevaluate/creditinfo-index";
			customerInfos[2][0] = "资质认证信息";
			customerInfos[2][1] = "/ecif/customer/simplegroup/orgevaluate/orgauth-index";
			customerInfos[3][0] = "机构客户等级信息";
			customerInfos[3][1] = "/ecif/customer/simplegroup/orgevaluate/orggrade-index";
//			customerInfos[4][0] = "机构客户星级信息";
//			customerInfos[4][1] = "/ecif/customer/simplegroup/orgevaluate/orgstarlevel-index";
			// 加载所有系统节点的信息
			for (int i = 0; i < customerInfos.length; i++) {
				CommonTreeNode treeNode = new CommonTreeNode();
				String indexStr = Integer.toString(i + 1);
				if(indexStr.length() == 1){
					indexStr = nodeIdBegin+"00"+indexStr;
				} else if(indexStr.length() == 2){
					indexStr = nodeIdBegin+"0"+indexStr;
				} else if(indexStr.length() == 3){
					indexStr = nodeIdBegin+indexStr;
				} else{
		        	try {
						throw new Exception("分类不合理，请重新分类！");
					} catch (Exception e) {
						Log.error(e.getMessage());
					}
				}
				treeNode.setId(indexStr);
				Map<String, String> params = Maps.newHashMap();
				params.put("level", "2");
				params.put("URL", customerInfos[i][1]);
				params.put("tableUrl", "/ecif/perbasic/form");
				treeNode.setParams(params);
				treeNode.setText(customerInfos[i][0]);
				treeNode.setUpId(id);
				treeNode.setIsexpand(false);
				treeNode.setIsParent(false);
				treeList.add(treeNode);
			}
		} else if (treeNodeName.equalsIgnoreCase("客户往来信息")) {
			String[][] customerInfos = new String[1][2];
			customerInfos[0][0] = "机构客户渠道签约";
			customerInfos[0][1] = "/ecif/customer/simplegroup/orgdealings/orgchannelsign-index";
			// 加载所有系统节点的信息
			for (int i = 0; i < customerInfos.length; i++) {
				CommonTreeNode treeNode = new CommonTreeNode();
				String indexStr = Integer.toString(i + 1);
				if(indexStr.length() == 1){
					indexStr = nodeIdBegin+"00"+indexStr;
				} else if(indexStr.length() == 2){
					indexStr = nodeIdBegin+"0"+indexStr;
				} else if(indexStr.length() == 3){
					indexStr = nodeIdBegin+indexStr;
				} else{
		        	try {
						throw new Exception("分类不合理，请重新分类！");
					} catch (Exception e) {
						Log.error(e.getMessage());
					}
				}
				treeNode.setId(indexStr);
				Map<String, String> params = Maps.newHashMap();
				params.put("level", "2");
				params.put("URL", customerInfos[i][1]);
				params.put("tableUrl", "/ecif/perbasic/form");
				treeNode.setParams(params);
				treeNode.setText(customerInfos[i][0]);
				treeNode.setUpId(id);
				treeNode.setIsexpand(false);
				treeNode.setIsParent(false);
				treeList.add(treeNode);
			}
		} else if (treeNodeName.equalsIgnoreCase("客户管理信息")) {
			String[][] customerInfos = new String[4][2];
			customerInfos[0][0] = "生命周期信息";
			customerInfos[0][1] = "/ecif/customer/simplegroup/orgmanage/lifecycleinfo-index";
			customerInfos[1][0] = "归属机构信息";
			customerInfos[1][1] = "/ecif/customer/simplegroup/orgmanage/belongbranch-index";
			customerInfos[2][0] = "归属客户经理信息";
			customerInfos[2][1] = "/ecif/customer/simplegroup/orgmanage/belongmanager-index";
			customerInfos[3][0] = "归属条线部门信息";
			customerInfos[3][1] = "/ecif/customer/simplegroup/orgmanage/belonglinedebt-index";
			// 加载所有系统节点的信息
			for (int i = 0; i < customerInfos.length; i++) {
				CommonTreeNode treeNode = new CommonTreeNode();
				String indexStr = Integer.toString(i + 1);
				if(indexStr.length() == 1){
					indexStr = nodeIdBegin+"00"+indexStr;
				} else if(indexStr.length() == 2){
					indexStr = nodeIdBegin+"0"+indexStr;
				} else if(indexStr.length() == 3){
					indexStr = nodeIdBegin+indexStr;
				} else{
		        	try {
						throw new Exception("分类不合理，请重新分类！");
					} catch (Exception e) {
						Log.error(e.getMessage());
					}
				}
				treeNode.setId(indexStr);
				Map<String, String> params = Maps.newHashMap();
				params.put("level", "2");
				params.put("URL", customerInfos[i][1]);
				params.put("tableUrl", "/ecif/perbasic/form");
				treeNode.setParams(params);
				treeNode.setText(customerInfos[i][0]);
				treeNode.setUpId(id);
				treeNode.setIsexpand(false);
				treeNode.setIsParent(false);
				treeList.add(treeNode);
			}
		} else if (treeNodeName.equalsIgnoreCase("客户其他信息")) {
			String[][] customerInfos = new String[3][2];
			customerInfos[0][0] = "机构客户其他信息";
			customerInfos[0][1] = "/ecif/customer/simplegroup/orgother/otherinfo-index";
			customerInfos[1][0] = "客户外行业务活动情况";
			customerInfos[1][1] = "/ecif/customer/simplegroup/orgother/otherbankactivity-index";
			customerInfos[2][0] = "客户贵宾卡信息";
			customerInfos[2][1] = "/ecif/customer/simplegroup/perother/vipcardinfo-index";
			// 加载所有系统节点的信息
			for (int i = 0; i < customerInfos.length; i++) {
				CommonTreeNode treeNode = new CommonTreeNode();
				String indexStr = Integer.toString(i + 1);
				if(indexStr.length() == 1){
					indexStr = nodeIdBegin+"00"+indexStr;
				} else if(indexStr.length() == 2){
					indexStr = nodeIdBegin+"0"+indexStr;
				} else if(indexStr.length() == 3){
					indexStr = nodeIdBegin+indexStr;
				} else{
		        	try {
						throw new Exception("分类不合理，请重新分类！");
					} catch (Exception e) {
						Log.error(e.getMessage());
					}
				}
				treeNode.setId(indexStr);
				Map<String, String> params = Maps.newHashMap();
				params.put("level", "2");
				params.put("URL", customerInfos[i][1]);
				params.put("tableUrl", "/ecif/perbasic/form");
				treeNode.setParams(params);
				treeNode.setText(customerInfos[i][0]);
				treeNode.setUpId(id);
				treeNode.setIsexpand(false);
				treeNode.setIsParent(false);
				treeList.add(treeNode);
			}
		} else if (treeNodeName.equalsIgnoreCase("产品")) {
			String[][] customerInfos = new String[1][2];
			customerInfos[0][0] = "产品基本信息";
			customerInfos[0][1] = "/ecif/customer/simplegroup/product/product-index";
			// 加载所有系统节点的信息
			for (int i = 0; i < customerInfos.length; i++) {
				CommonTreeNode treeNode = new CommonTreeNode();
				String indexStr = Integer.toString(i + 1);
				if(indexStr.length() == 1){
					indexStr = nodeIdBegin+"00"+indexStr;
				} else if(indexStr.length() == 2){
					indexStr = nodeIdBegin+"0"+indexStr;
				} else if(indexStr.length() == 3){
					indexStr = nodeIdBegin+indexStr;
				} else{
		        	try {
						throw new Exception("分类不合理，请重新分类！");
					} catch (Exception e) {
						Log.error(e.getMessage());
					}
				}
				treeNode.setId(indexStr);
				Map<String, String> params = Maps.newHashMap();
				params.put("level", "2");
				params.put("URL", customerInfos[i][1]);
				params.put("tableUrl", "/ecif/perbasic/form");
				treeNode.setParams(params);
				treeNode.setText(customerInfos[i][0]);
				treeNode.setUpId(id);
				treeNode.setIsexpand(false);
				treeNode.setIsParent(false);
				treeList.add(treeNode);
			}
		} else if (treeNodeName.equalsIgnoreCase("协议")) {
			String[][] customerInfos = new String[10][2];
			customerInfos[0][0] = "存款账户信息";
			customerInfos[0][1] = "/ecif/customer/simplegroup/agreement/depositaccount-index";
			customerInfos[1][0] = "贷款账户信息";
			customerInfos[1][1] = "/ecif/customer/simplegroup/agreement/loanaccount-index";
//			customerInfos[2][0] = "网银签约信息";
//			customerInfos[2][1] = "/ecif/customer/simplegroup/agreement/ebanksignaccount-index";
			customerInfos[2][0] = "网银协议信息";
			customerInfos[2][1] = "/ecif/customer/simplegroup/agreement/ebankcontract-index";
			customerInfos[3][0] = "授信协议信息";
			customerInfos[3][1] = "/ecif/customer/simplegroup/agreement/creditcontract-index";
			customerInfos[4][0] = "借款合同信息";
			customerInfos[4][1] = "/ecif/customer/simplegroup/agreement/loancontract-index";
			customerInfos[5][0] = "担保合同信息";
			customerInfos[5][1] = "/ecif/customer/simplegroup/agreement/guarantycontract-index";
			customerInfos[6][0] = "基金份额信息";
			customerInfos[6][1] = "/ecif/customer/simplegroup/agreement/tbshare-index";
//			customerInfos[8][0] = "分项授信信息";
//			customerInfos[8][1] = "/ecif/customer/simplegroup/agreement/cesubcredit-index";
//			customerInfos[7][0] = "欠息信息";
//			customerInfos[7][1] = "/ecif/customer/simplegroup/agreement/oweinterest-index";
			customerInfos[7][0] = "理财账户信息";
			customerInfos[7][1] = "/ecif/customer/simplegroup/agreement/tbassetacc-index";
			customerInfos[8][0] = "电子式债券信息";
			customerInfos[8][1] = "/ecif/customer/simplegroup/agreement/bondcontract-index";
//			customerInfos[11][0] = "黄金交易签约";
//			customerInfos[11][1] = "/ecif/customer/simplegroup/agreement/bugoldcont-index";
			customerInfos[9][0] = "凭证式债券信息";
			customerInfos[9][1] = "/ecif/customer/simplegroup/agreement/bdshracctinfo-index";
			// 加载所有系统节点的信息
			for (int i = 0; i < customerInfos.length; i++) {
				CommonTreeNode treeNode = new CommonTreeNode();
				String indexStr = Integer.toString(i + 1);
				if(indexStr.length() == 1){
					indexStr = nodeIdBegin+"00"+indexStr;
				} else if(indexStr.length() == 2){
					indexStr = nodeIdBegin+"0"+indexStr;
				} else if(indexStr.length() == 3){
					indexStr = nodeIdBegin+indexStr;
				} else{
		        	try {
						throw new Exception("分类不合理，请重新分类！");
					} catch (Exception e) {
						Log.error(e.getMessage());
					}
				}
				treeNode.setId(indexStr);
				Map<String, String> params = Maps.newHashMap();
				params.put("level", "2");
				params.put("URL", customerInfos[i][1]);
				params.put("tableUrl", "/ecif/perbasic/form");
				treeNode.setParams(params);
				treeNode.setText(customerInfos[i][0]);
				treeNode.setUpId(id);
				treeNode.setIsexpand(false);
				treeNode.setIsParent(false);
				treeList.add(treeNode);
			}
		} else if (treeNodeName.equalsIgnoreCase("事件")) {
			String[][] customerInfos = new String[3][2];
			customerInfos[0][0] = "大事记";
			customerInfos[0][1] = "/ecif/customer/simplegroup/event/largeEvent-index";
			customerInfos[1][0] = "交易事件";
			customerInfos[1][1] = "/ecif/customer/simplegroup/event/txEvent-index";
			customerInfos[2][0] = "关注事件";
			customerInfos[2][1] = "/ecif/customer/simplegroup/event/attentEvent-index";
			// 加载所有系统节点的信息
			for (int i = 0; i < customerInfos.length; i++) {
				CommonTreeNode treeNode = new CommonTreeNode();
				String indexStr = Integer.toString(i + 1);
				if(indexStr.length() == 1){
					indexStr = nodeIdBegin+"00"+indexStr;
				} else if(indexStr.length() == 2){
					indexStr = nodeIdBegin+"0"+indexStr;
				} else if(indexStr.length() == 3){
					indexStr = nodeIdBegin+indexStr;
				} else{
		        	try {
						throw new Exception("分类不合理，请重新分类！");
					} catch (Exception e) {
						Log.error(e.getMessage());
					}
				}
				treeNode.setId(indexStr);
				Map<String, String> params = Maps.newHashMap();
				params.put("level", "2");
				params.put("URL", customerInfos[i][1]);
				params.put("tableUrl", "/ecif/perbasic/form");
				treeNode.setParams(params);
				treeNode.setText(customerInfos[i][0]);
				treeNode.setUpId(id);
				treeNode.setIsexpand(false);
				treeNode.setIsParent(false);
				treeList.add(treeNode);
			}
		} else if (treeNodeName.equalsIgnoreCase("资产")) {
			String[][] customerInfos = new String[14][2];
			customerInfos[0][0] = "房产";
			customerInfos[0][1] = "/ecif/customer/simplegroup/assets/customerrealty-index";
			customerInfos[1][0] = "车辆";
			customerInfos[1][1] = "/ecif/customer/simplegroup/assets/customervehicle-index";
			customerInfos[2][0] = "存货";
			customerInfos[2][1] = "/ecif/customer/simplegroup/assets/entinventory-index";
			customerInfos[3][0] = "厂房";
			customerInfos[3][1] = "/ecif/customer/simplegroup/assets/smeinveinfo-index";
			customerInfos[4][0] = "经营权";
			customerInfos[4][1] = "/ecif/customer/simplegroup/assets/right-index";
			customerInfos[5][0] = "固定资产";
			customerInfos[5][1] = "/ecif/customer/simplegroup/assets/entfixedassets-index";
			customerInfos[6][0] = "机器设备";
			customerInfos[6][1] = "/ecif/customer/simplegroup/assets/machine-index";
			customerInfos[7][0] = "抵质押物";
			customerInfos[7][1] = "/ecif/customer/simplegroup/assets/guaranty-index";
			customerInfos[8][0] = "应收账款";
			customerInfos[8][1] = "/ecif/customer/simplegroup/assets/receivable-index";
			customerInfos[9][0] = "无形资产";
			customerInfos[9][1] = "/ecif/customer/simplegroup/assets/customerimasset-index";
			customerInfos[10][0] = "股票信息";
			customerInfos[10][1] = "/ecif/customer/simplegroup/assets/stock-index";
			customerInfos[11][0] = "债券信息";
			customerInfos[11][1] = "/ecif/customer/simplegroup/assets/bond-index";
			customerInfos[12][0] = "票据信息";
			customerInfos[12][1] = "/ecif/customer/simplegroup/assets/billinfo-index";
			customerInfos[13][0] = "他行存款";
			customerInfos[13][1] = "/ecif/customer/simplegroup/assets/otherbankdeposit-index";
			// 加载所有系统节点的信息
			for (int i = 0; i < customerInfos.length; i++) {
				CommonTreeNode treeNode = new CommonTreeNode();
				String indexStr = Integer.toString(i + 1);
				if(indexStr.length() == 1){
					indexStr = nodeIdBegin+"00"+indexStr;
				} else if(indexStr.length() == 2){
					indexStr = nodeIdBegin+"0"+indexStr;
				} else if(indexStr.length() == 3){
					indexStr = nodeIdBegin+indexStr;
				} else{
		        	try {
						throw new Exception("分类不合理，请重新分类！");
					} catch (Exception e) {
						Log.error(e.getMessage());
					}
				}
				treeNode.setId(indexStr);
				Map<String, String> params = Maps.newHashMap();
				params.put("level", "2");
				params.put("URL", customerInfos[i][1]);
				params.put("tableUrl", "/ecif/perbasic/form");
				treeNode.setParams(params);
				treeNode.setText(customerInfos[i][0]);
				treeNode.setUpId(id);
				treeNode.setIsexpand(false);
				treeNode.setIsParent(false);
				treeList.add(treeNode);
			}
		}
//		else if (text.equalsIgnoreCase("影像")) {
//		String[][] customerInfos = new String[1][2];
//		customerInfos[0][0] = "影像信息";
//		customerInfos[0][1] = "/ecif/customer/simplegroup/image/imageinfo-index";
//		// 加载所有系统节点的信息
//		for (int i = 0; i < customerInfos.length; i++) {
//			CommonTreeNode treeNode = new CommonTreeNode();
//			String indexStr = Integer.toString(i + 1);
//			if(indexStr.length() == 1){
//				indexStr = nodeIdBegin+"00"+indexStr;
//			} else if(indexStr.length() == 2){
//				indexStr = nodeIdBegin+"0"+indexStr;
//			} else if(indexStr.length() == 3){
//				indexStr = nodeIdBegin+indexStr;
//			} else{
//	        	try {
//					throw new Exception("分类不合理，请重新分类！");
//				} catch (Exception e) {
//					Log.error(e.getMessage());
//				}
//			}
//			treeNode.setId(indexStr);
//			Map<String, String> params = Maps.newHashMap();
//			params.put("level", "2");
//			params.put("URL", customerInfos[i][1]);
//			params.put("tableUrl", "/ecif/image/form");
//			treeNode.setParams(params);
//			treeNode.setText(customerInfos[i][0]);
//			treeNode.setUpId(id);
//			treeNode.setIsexpand(false);
//			treeNode.setIsParent(false);
//			treeList.add(treeNode);
//		}
	}
	
	/**
	 * 获取下拉列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getIdentType")
	@ResponseBody
	public List<Map<String, String>> findForCombo() {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			list = orgBS.findIdentType();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}