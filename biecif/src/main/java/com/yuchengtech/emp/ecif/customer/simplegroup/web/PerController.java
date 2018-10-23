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
import com.yuchengtech.emp.ecif.customer.simplegroup.service.PerBS;
import com.yuchengtech.emp.ecif.customer.simplegroup.web.vo.PerBasicVO;
import com.yuchengtech.emp.ecif.customer.simplegroup.web.vo.PerFocusVO;
import com.yuchengtech.emp.ecif.customer.simplegroup.web.vo.PerProductVO;
import com.yuchengtech.emp.ecif.customer.simplegroup.web.vo.PerRelativeVO;
import com.yuchengtech.emp.ecif.customer.simplegroup.web.vo.PerRiskVO;

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
@RequestMapping("/ecif/perinfo")
public class PerController extends BaseController {

	@Autowired
	private PerBS perBS;

	@Autowired
	private ExportReportBS exportReportBS;

	/**
	 * 显示客户详情页面
	 * @return
	 */
	@RequestMapping(value = "/perdetailed", method = RequestMethod.POST)
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
		return new ModelAndView("/ecif/customer/simplegroup/per-detailedinfo-index", map);
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
		if(pageName.equals("per-basicinfo-index")){
			mv = new ModelAndView("/ecif/customer/simplegroup/per-basicinfo-index", map);
		}
		if(pageName.equals("per-focusinfo-index")){
			mv = new ModelAndView("/ecif/customer/simplegroup/per-focusinfo-index", map);
		}
		if(pageName.equals("per-productinfo-index")){
			mv = new ModelAndView("/ecif/customer/simplegroup/per-productinfo-index", map);
		}
		if(pageName.equals("per-relativeinfo-index")){
			mv = new ModelAndView("/ecif/customer/simplegroup/per-relativeinfo-index", map);
		}
		if(pageName.equals("per-analysisinfo-index")){
			mv = new ModelAndView("/ecif/customer/simplegroup/per-analysisinfo-index", map);
		}
		return mv;
	}

	/**
	 * 显示客户基本信息页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/perbasic", method = RequestMethod.GET)
	public String perBasicIndex() {
		return "/ecif/customer/simplegroup/per-basicinfo-index";
	}

	/**
	 * 下载导出报表
	 * 
	 * @param response
	 * @param file
	 * @throws Exception
	 */
	@RequestMapping(value = "/perbasic/export.*", method = RequestMethod.POST)
	public void exportPerBasic(HttpServletResponse response, String file)
			throws Exception {

		ExportUtil.download(response, new File(file),
				"application/vnd.ms-excel");
		ExportUtil.deleteFile(file);
	}

	@RequestMapping(value = "/perfocus/export.*", method = RequestMethod.POST)
	public void exportPerfocus(HttpServletResponse response, String file)
			throws Exception {

		ExportUtil.download(response, new File(file),
				"application/vnd.ms-excel");
		ExportUtil.deleteFile(file);
	}

	@RequestMapping(value = "/perproduct/export.*", method = RequestMethod.POST)
	public void exportPerproduct(HttpServletResponse response, String file)
			throws Exception {

		ExportUtil.download(response, new File(file),
				"application/vnd.ms-excel");
		ExportUtil.deleteFile(file);
	}

	@RequestMapping(value = "/perrelative/export.*", method = RequestMethod.POST)
	public void exportRelative(HttpServletResponse response, String file)
			throws Exception {

		ExportUtil.download(response, new File(file),
				"application/vnd.ms-excel");
		ExportUtil.deleteFile(file);
	}
	
	@RequestMapping(value = "/peranalysis/export.*", method = RequestMethod.POST)
	public void exportAnalysis(HttpServletResponse response, String file)
			throws Exception {

		ExportUtil.download(response, new File(file),
				"application/vnd.ms-excel");
		ExportUtil.deleteFile(file);
	}

	/**
	 * 
	 * @param param0
	 *            tab页名称
	 * @param param1
	 *            查询参数
	 * @param param2
	 * @param param3
	 * @return
	 */
	@RequestMapping(value = "/perbasic/getReportFile")
	@ResponseBody
	public String getPerBasicFile(String param0, String param1, String param2,
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
		return exportReportBS.exportPerInfo(firstResult, pageSize, 1, tabNo, param1, param2, param3);
	}

	@RequestMapping(value = "/perfocus/getReportFile")
	@ResponseBody
	public String getPerFocusFile(String param0, String param1, String param2,
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
		return exportReportBS.exportPerInfo(firstResult, pageSize, 2, tabNo, param1, param2, param3);
	}

	@RequestMapping(value = "/perproduct/getReportFile")
	@ResponseBody
	public String getPerProductFile(String param0, String param1,
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
		return exportReportBS.exportPerInfo(firstResult, pageSize, 3, tabNo, param1, param2, param3);
	}

	@RequestMapping(value = "/peranalysis/getReportFile")
	@ResponseBody
	public String getPerAnalysisFile(String param0, String param1,
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
		return exportReportBS.exportPerInfo(firstResult, pageSize, 4, tabNo, param1, param2, param3);
	}
	
	@RequestMapping(value = "/perrelative/getReportFile")
	@ResponseBody
	public String getPerRelativeFile(String param0, String param1,
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
		return exportReportBS.exportPerInfo(firstResult, pageSize, 5, tabNo, param1, param2, param3);
	}

	/**
	 * 显示客户关注事件页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/perfocus", method = RequestMethod.GET)
	public String perFocusIndex() {
		return "/ecif/customer/simplegroup/per-focusinfo-index";
	}

	/**
	 * 显示客户产品页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/perproduct", method = RequestMethod.GET)
	public String perProductIndex() {
		return "/ecif/customer/simplegroup/per-productinfo-index";
	}
	
	/**
	 * 显示客户关联页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/perrelative", method = RequestMethod.GET)
	public String perRelativeIndex() {
		return "/ecif/customer/simplegroup/per-relativeinfo-index";
	}

	/**
	 * 显示客户分析信息页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/peranalysis", method = RequestMethod.GET)
	public String perAnalysisIndex() {
		return "/ecif/customer/simplegroup/per-analysisinfo-index";
	}

	/**
	 * 客户基本信息页面
	 * @param pager
	 * @return
	 */
	@RequestMapping("/perbasic/list.*")
	@ResponseBody
	public Map<String, Object> perBasicList(Pager2 pager) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<PerBasicVO> searchResult = perBS.getPerBasicVOList(
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
	 * 客户关注信息页面
	 * 
	 * @param pager
	 * @return
	 */
	@RequestMapping("/perfocus/list.*")
	@ResponseBody
	public Map<String, Object> perFocusList(Pager2 pager) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<PerFocusVO> searchResult = perBS.getPerFocusVOList(
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
	 * 客户产品信息
	 * 
	 * @param pager
	 * @return
	 */
	@RequestMapping("/perproduct/list.*")
	@ResponseBody
	public Map<String, Object> perAnalysisList(Pager2 pager) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<PerProductVO> searchResult = perBS.getPerProductVOList(
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
	@RequestMapping("/peranalysis/list.*")
	@ResponseBody
	public Map<String, Object> perProductList(Pager2 pager) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<PerRiskVO> searchResult = perBS.perAnalysisList(
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
	@RequestMapping("/perrelative/list.*")
	@ResponseBody
	public Map<String, Object> perRelativeList(Pager2 pager) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<PerRelativeVO> searchResult = perBS.perRelativeList(
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
				"客户风险信息", "客户评价信息",  "客户管理信息", 
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
			String[][] customerInfos = new String[10][2];
			customerInfos[0][0] = "人口轮廓";
			customerInfos[0][1] = "/ecif/customer/simplegroup/perbasic/personprofile-index";
			customerInfos[1][0] = "名称称谓";
			customerInfos[1][1] = "/ecif/customer/simplegroup/perbasic/nametitle-index";
			customerInfos[2][0] = "工作履历";
			customerInfos[2][1] = "/ecif/customer/simplegroup/perbasic/jobresume-index";
			customerInfos[3][0] = "职业信息";
			customerInfos[3][1] = "/ecif/customer/simplegroup/perbasic/career-index";
			customerInfos[4][0] = "学业履历";
			customerInfos[4][1] = "/ecif/customer/simplegroup/perbasic/eduresume-index";
			customerInfos[5][0] = "家庭状况";
			customerInfos[5][1] = "/ecif/customer/simplegroup/perbasic/family-index";
			customerInfos[6][0] = "联网核查信息";
			customerInfos[6][1] = "/ecif/customer/simplegroup/perbasic/identityverify-index";
			customerInfos[7][0] = "个人证件信息";
			customerInfos[7][1] = "/ecif/customer/simplegroup/perbasic/personidentifier-index";
//			customerInfos[8][0] = "个人客户扩展信息";
//			customerInfos[8][1] = "/ecif/customer/simplegroup/perbasic/personextend-index";
			customerInfos[8][0] = "个人客户重要标志";
			customerInfos[8][1] = "/ecif/customer/simplegroup/perbasic/personkeyflag-index";
			customerInfos[9][0] = "个人客户重要指标";
			customerInfos[9][1] = "/ecif/customer/simplegroup/perbasic/personkeyindex-index";
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
				params.put("tableUrl", "/ecif/customercontact/form");
				treeNode.setParams(params);
				treeNode.setText(customerInfos[i][0]);
				treeNode.setUpId(id);
				treeNode.setIsexpand(false);
				treeNode.setIsParent(false);
				treeList.add(treeNode);
			}
		} else if (treeNodeName.equalsIgnoreCase("客户关联信息")) {
			String[][] customerInfos = new String[3][2];
			customerInfos[0][0] = "配偶信息";
			customerInfos[0][1] = "/ecif/customer/simplegroup/perrelative/mateinfo-index";
			customerInfos[1][0] = "亲属信息";
			customerInfos[1][1] = "/ecif/customer/simplegroup/perrelative/relativeinfo-index";
			customerInfos[2][0] = "联系人信息";
			customerInfos[2][1] = "/ecif/customer/simplegroup/perrelative/linkman-index";
//			customerInfos[3][0] = "个人对外担保信息";
//			customerInfos[3][1] = "/ecif/customer/simplegroup/perrelative/personforeassu-index";
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
				params.put("tableUrl", "/ecif/perrelative/form");
				treeNode.setParams(params);
				treeNode.setText(customerInfos[i][0]);
				treeNode.setUpId(id);
				treeNode.setIsexpand(false);
				treeNode.setIsParent(false);
				treeList.add(treeNode);
			}
		} else if (treeNodeName.equalsIgnoreCase("客户财务信息")) {
			String[][] customerInfos = new String[4][2];
			customerInfos[0][0] = "个人资产";
			customerInfos[0][1] = "/ecif/customer/simplegroup/perfinance/persondebt-index";
			customerInfos[1][0] = "个人负债";
			customerInfos[1][1] = "/ecif/customer/simplegroup/perfinance/persondebt-index";
			customerInfos[2][0] = "个人经营信息";
			customerInfos[2][1] = "/ecif/customer/simplegroup/perfinance/personbusiinfo-index";
			customerInfos[3][0] = "个人投资信息";
			customerInfos[3][1] = "/ecif/customer/simplegroup/perfinance/personinvestment-index";
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
			String[][] customerInfos = new String[10][2];
			customerInfos[0][0] = "评级信息";
			customerInfos[0][1] = "/ecif/customer/simplegroup/perrisk/rating-index";
			customerInfos[1][0] = "违约信息";
			customerInfos[1][1] = "/ecif/customer/simplegroup/perrisk/defaultinfo-index";
			customerInfos[2][0] = "欠息信息";
			customerInfos[2][1] = "/ecif/customer/simplegroup/perrisk/overinterest-index";
			customerInfos[3][0] = "虚假情况";
			customerInfos[3][1] = "/ecif/customer/simplegroup/perrisk/mendinfo-index";
			customerInfos[4][0] = "特殊名单";
			customerInfos[4][1] = "/ecif/customer/simplegroup/perrisk/speciallist-index";
			customerInfos[5][0] = "风险预警信号";
			customerInfos[5][1] = "/ecif/customer/simplegroup/perrisk/risksignal-index";
			customerInfos[6][0] = "个人公积金信息";
			customerInfos[6][1] = "/ecif/customer/simplegroup/perrisk/persongjj-index";
			customerInfos[7][0] = "个人社会保障信息";
			customerInfos[7][1] = "/ecif/customer/simplegroup/perrisk/personsocialsecurity-index";
			customerInfos[8][0] = "个人社会保险信息";
			customerInfos[8][1] = "/ecif/customer/simplegroup/perrisk/personsocialinsurance-index";
			customerInfos[9][0] = "个人商业保险信息";
			customerInfos[9][1] = "/ecif/customer/simplegroup/perrisk/personbusiinsurance-index";
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
			String[][] customerInfos = new String[6][2];
			customerInfos[0][0] = "评分信息";
			customerInfos[0][1] = "/ecif/customer/simplegroup/perevaluate/scoreinfo-index";
			customerInfos[1][0] = "征信信息";
			customerInfos[1][1] = "/ecif/customer/simplegroup/perevaluate/creditinfo-index";
			customerInfos[2][0] = "客户评价信息";
			customerInfos[2][1] = "/ecif/customer/simplegroup/perevaluate/evaluate-index";
//			customerInfos[3][0] = "个人客户星级信息";
//			customerInfos[3][1] = "/ecif/customer/simplegroup/perevaluate/perstarlevel-index";
			customerInfos[3][0] = "个人客户等级信息";
			customerInfos[3][1] = "/ecif/customer/simplegroup/perevaluate/persongrade-index";
			customerInfos[4][0] = "个人客户积分信息";
			customerInfos[4][1] = "/ecif/customer/simplegroup/perevaluate/personpoint-index";
			customerInfos[5][0] = "个人客户偏好信息";
			customerInfos[5][1] = "/ecif/customer/simplegroup/perevaluate/personpreference-index";
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
//		} else if (treeNodeName.equalsIgnoreCase("客户往来信息")) {
//			String[][] customerInfos = new String[1][2];
//			customerInfos[0][0] = "个人客户渠道签约";
//			customerInfos[0][1] = "/ecif/customer/simplegroup/perdealing/personchannelsign-index";
//			// 加载所有系统节点的信息
//			for (int i = 0; i < customerInfos.length; i++) {
//				CommonTreeNode treeNode = new CommonTreeNode();
//				String indexStr = Integer.toString(i + 1);
//				if(indexStr.length() == 1){
//					indexStr = nodeIdBegin+"00"+indexStr;
//				} else if(indexStr.length() == 2){
//					indexStr = nodeIdBegin+"0"+indexStr;
//				} else if(indexStr.length() == 3){
//					indexStr = nodeIdBegin+indexStr;
//				} else{
//		        	try {
//						throw new Exception("分类不合理，请重新分类！");
//					} catch (Exception e) {
//						Log.error(e.getMessage());
//					}
//				}
//				treeNode.setId(indexStr);
//				Map<String, String> params = Maps.newHashMap();
//				params.put("level", "2");
//				params.put("URL", customerInfos[i][1]);
//				params.put("tableUrl", "/ecif/perbasic/form");
//				treeNode.setParams(params);
//				treeNode.setText(customerInfos[i][0]);
//				treeNode.setUpId(id);
//				treeNode.setIsexpand(false);
//				treeNode.setIsParent(false);
//				treeList.add(treeNode);
//			}
		} else if (treeNodeName.equalsIgnoreCase("客户管理信息")) {
			String[][] customerInfos = new String[4][2];
			customerInfos[0][0] = "生命周期信息";
			customerInfos[0][1] = "/ecif/customer/simplegroup/permanage/lifecycleinfo-index";
			customerInfos[1][0] = "归属机构信息";
			customerInfos[1][1] = "/ecif/customer/simplegroup/permanage/belongbranch-index";
			customerInfos[2][0] = "归属客户经理信息";
			customerInfos[2][1] = "/ecif/customer/simplegroup/permanage/belongmanager-index";
			customerInfos[3][0] = "归属条线部门信息";
			customerInfos[3][1] = "/ecif/customer/simplegroup/permanage/belonglinedept-index";
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
//		} else if (treeNodeName.equalsIgnoreCase("客户其他信息")) {
//			String[][] customerInfos = new String[6][2];
//			customerInfos[0][0] = "个人客户居住信息";
//			customerInfos[0][1] = "/ecif/customer/simplegroup/perother/inhabinfo-index";
//			customerInfos[1][0] = "个人客户其他信息";
//			customerInfos[1][1] = "/ecif/customer/simplegroup/perother/otherinfo-index";
//			customerInfos[2][0] = "个人资产最大行信息";
//			customerInfos[2][1] = "/ecif/customer/simplegroup/perother/assetmaxbranch-index";
//			customerInfos[3][0] = "个体经营户水电信息";
//			customerInfos[3][1] = "/ecif/customer/simplegroup/perother/consinfo-index";
//			customerInfos[4][0] = "客户外行业务活动情况";
//			customerInfos[4][1] = "/ecif/customer/simplegroup/perother/otherbankactivity-index";
//			customerInfos[5][0] = "客户贵宾卡信息";
//			customerInfos[5][1] = "/ecif/customer/simplegroup/perother/vipcardinfo-index";
//			// 加载所有系统节点的信息
//			for (int i = 0; i < customerInfos.length; i++) {
//				CommonTreeNode treeNode = new CommonTreeNode();
//				String indexStr = Integer.toString(i + 1);
//				if(indexStr.length() == 1){
//					indexStr = nodeIdBegin+"00"+indexStr;
//				} else if(indexStr.length() == 2){
//					indexStr = nodeIdBegin+"0"+indexStr;
//				} else if(indexStr.length() == 3){
//					indexStr = nodeIdBegin+indexStr;
//				} else{
//		        	try {
//						throw new Exception("分类不合理，请重新分类！");
//					} catch (Exception e) {
//						Log.error(e.getMessage());
//					}
//				}
//				treeNode.setId(indexStr);
//				Map<String, String> params = Maps.newHashMap();
//				params.put("level", "2");
//				params.put("URL", customerInfos[i][1]);
//				params.put("tableUrl", "/ecif/perbasic/form");
//				treeNode.setParams(params);
//				treeNode.setText(customerInfos[i][0]);
//				treeNode.setUpId(id);
//				treeNode.setIsexpand(false);
//				treeNode.setIsParent(false);
//				treeList.add(treeNode);
//			}
//		} else if (treeNodeName.equalsIgnoreCase("产品")) {
//			
//			
//			String[][] customerInfos = new String[1][2];
//			customerInfos[0][0] = "产品基本信息";
//			customerInfos[0][1] = "/ecif/customer/simplegroup/product/product-index";
//			// 加载所有系统节点的信息
//			for (int i = 0; i < customerInfos.length; i++) {
//				CommonTreeNode treeNode = new CommonTreeNode();
//				String indexStr = Integer.toString(i + 1);
//				if(indexStr.length() == 1){
//					indexStr = nodeIdBegin+"00"+indexStr;
//				} else if(indexStr.length() == 2){
//					indexStr = nodeIdBegin+"0"+indexStr;
//				} else if(indexStr.length() == 3){
//					indexStr = nodeIdBegin+indexStr;
//				} else{
//		        	try {
//						throw new Exception("分类不合理，请重新分类！");
//					} catch (Exception e) {
//						Log.error(e.getMessage());
//					}
//				}
//				treeNode.setId(indexStr);
//				Map<String, String> params = Maps.newHashMap();
//				params.put("level", "2");
//				params.put("URL", customerInfos[i][1]);
//				params.put("tableUrl", "/ecif/perbasic/form");
//				treeNode.setParams(params);
//				treeNode.setText(customerInfos[i][0]);
//				treeNode.setUpId(id);
//				treeNode.setIsexpand(false);
//				treeNode.setIsParent(false);
//				treeList.add(treeNode);
//			}
//		} else if (treeNodeName.equalsIgnoreCase("协议")) {
//			String[][] customerInfos = new String[10][2];
//			customerInfos[0][0] = "存款账户信息";
//			customerInfos[0][1] = "/ecif/customer/simplegroup/agreement/depositaccount-index";
//			customerInfos[1][0] = "贷款账户信息";
//			customerInfos[1][1] = "/ecif/customer/simplegroup/agreement/loanaccount-index";
////			customerInfos[2][0] = "网银签约信息";
////			customerInfos[2][1] = "/ecif/customer/simplegroup/agreement/ebanksignaccount-index";
//			customerInfos[2][0] = "网银协议信息";
//			customerInfos[2][1] = "/ecif/customer/simplegroup/agreement/ebankcontract-index";
//			customerInfos[3][0] = "授信协议信息";
//			customerInfos[3][1] = "/ecif/customer/simplegroup/agreement/creditcontract-index";
//			customerInfos[4][0] = "借款合同信息";
//			customerInfos[4][1] = "/ecif/customer/simplegroup/agreement/loancontract-index";
//			customerInfos[5][0] = "担保合同信息";
//			customerInfos[5][1] = "/ecif/customer/simplegroup/agreement/guarantycontract-index";
//			customerInfos[6][0] = "基金份额信息";
//			customerInfos[6][1] = "/ecif/customer/simplegroup/agreement/tbshare-index";
////			customerInfos[8][0] = "分项授信信息";
////			customerInfos[8][1] = "/ecif/customer/simplegroup/agreement/cesubcredit-index";
////			customerInfos[7][0] = "欠息信息";
////			customerInfos[7][1] = "/ecif/customer/simplegroup/agreement/oweinterest-index";
//			customerInfos[7][0] = "理财账户信息";
//			customerInfos[7][1] = "/ecif/customer/simplegroup/agreement/tbassetacc-index";
//			customerInfos[8][0] = "电子式债券信息";
//			customerInfos[8][1] = "/ecif/customer/simplegroup/agreement/bondcontract-index";
////			customerInfos[11][0] = "黄金交易签约信息";
////			customerInfos[11][1] = "/ecif/customer/simplegroup/agreement/bugoldcont-index";
//			customerInfos[9][0] = "凭证式债券信息";
//			customerInfos[9][1] = "/ecif/customer/simplegroup/agreement/bdshracctinfo-index";
//			// 加载所有系统节点的信息
//			for (int i = 0; i < customerInfos.length; i++) {
//				CommonTreeNode treeNode = new CommonTreeNode();
//				String indexStr = Integer.toString(i + 1);
//				if(indexStr.length() == 1){
//					indexStr = nodeIdBegin+"00"+indexStr;
//				} else if(indexStr.length() == 2){
//					indexStr = nodeIdBegin+"0"+indexStr;
//				} else if(indexStr.length() == 3){
//					indexStr = nodeIdBegin+indexStr;
//				} else{
//		        	try {
//						throw new Exception("分类不合理，请重新分类！");
//					} catch (Exception e) {
//						Log.error(e.getMessage());
//					}
//				}
//				treeNode.setId(indexStr);
//				Map<String, String> params = Maps.newHashMap();
//				params.put("level", "2");
//				params.put("URL", customerInfos[i][1]);
//				params.put("tableUrl", "/ecif/perbasic/form");
//				treeNode.setParams(params);
//				treeNode.setText(customerInfos[i][0]);
//				treeNode.setUpId(id);
//				treeNode.setIsexpand(false);
//				treeNode.setIsParent(false);
//				treeList.add(treeNode);
//			}
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
			String[][] customerInfos = new String[15][2];
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
//			customerInfos[7][0] = "抵质押物";
//			customerInfos[7][1] = "/ecif/customer/simplegroup/assets/guaranty-index";
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
			customerInfos[14][0] = "个人其他资产";
			customerInfos[14][1] = "/ecif/customer/simplegroup/assets/indoasset-index";
			// 加载所有系统节点的信息
			for (int i = 0; i < customerInfos.length; i++) {
				
				if(customerInfos[i][0]==null||customerInfos[i][0].equals(""))
					continue;
					
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
			list = perBS.findIdentType();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}