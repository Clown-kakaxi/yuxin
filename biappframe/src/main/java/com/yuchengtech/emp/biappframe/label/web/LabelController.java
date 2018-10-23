package com.yuchengtech.emp.biappframe.label.web;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.biappframe.label.entity.BioneLabelObjInfo;
import com.yuchengtech.emp.biappframe.label.entity.BioneLabelObjRel;
import com.yuchengtech.emp.biappframe.label.service.LabelObjBS;
import com.yuchengtech.emp.biappframe.label.service.LabelObjRelBS;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.bione.entity.page.Pager;
@Controller
@RequestMapping("/bione/label/apply")
/**
 * <pre>
 * Title: 标签相关
 * Description:
 * </pre>
 * 
 * @author kangligong kanglg@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：		  修改日期:     修改内容:
 * </pre>
 */
public class LabelController extends BaseController {
	@Autowired
	private LabelObjRelBS objRelBS;
	@Autowired
	private LabelObjBS objBS;
	/**
	 * 标签案例1
	 * @return
	 */
	@RequestMapping("/exmaple")
	public ModelAndView exmaple(String objId, String labelObjId) {
		ModelMap mm = new ModelMap();
 		mm.put("objId", objId); 
		mm.put("labelObjId", labelObjId);
		return new ModelAndView("/bione/label/label-exmaple", mm);
	}
	/**
	 * 标签选择面板
	 * @param objId
	 * @return
	 */
	@RequestMapping("/labelCheck")
	public ModelAndView labelCheck(String labelObjId) {
		return new ModelAndView("/bione/label/label-check", "labelObjId", labelObjId);
	}
	/**
	 * 标签案例2
	 * @return
	 */
	@RequestMapping("/test")
	public ModelAndView test(String labelObjId) {
		return new ModelAndView("/bione/label/label-exmaple2", "labelObjId", labelObjId);
	}
	
	/**
	 * 通过标签对象Id获取标签
	 * 
	 * @param labelObjId
	 * @return
	 */
	@RequestMapping("/labelOfObj.json")
	@ResponseBody
	public Object labelOfObj(String labelObjId) {
		if (!StringUtils.isEmpty(labelObjId)) {
			return objRelBS.getLabelByObj(labelObjId);
		}
		return null;
	}
	
	/**
	 * 通过标签对象No获取标签
	 * 
	 * @param labelObjId
	 * @return
	 */
	@RequestMapping("/labelOfObjNo.json")
	@ResponseBody
	public Object labelOfObjNo(String labelObjNo) {
		if (!StringUtils.isEmpty(labelObjNo)) {
			String objId = (String) objBS.getLabelObjId(labelObjNo,
					BiOneSecurityUtils.getCurrentUserInfo()
							.getCurrentLogicSysNo());
			if (!StringUtils.isEmpty(objId)) {
				return objRelBS.getLabelByObj(objId);
			}
		}
		return null;
	}
	/**
	 * 案例1，创建标签
	 * @param objId
	 * @param labels
	 */
	@RequestMapping(value = "/exmaple", method = RequestMethod.POST)
	public void exmapleCreate(String objId, String labels) {
		List<String> labelIds = Lists.newArrayList();
		JSONArray ja = JSONArray.fromObject(labels);
		for (@SuppressWarnings("rawtypes")
		Iterator iterator = ja.iterator(); iterator.hasNext();) {
			String label = (String) iterator.next();
			labelIds.add(label);
		}
		objRelBS.updateRel(objId, labelIds);
	}
	
	/**
	 * 单独保存对象-标签关系
	 * @param objId 对象ID
	 * @param labels 标签ID json字符串数组
	 */
	@RequestMapping(value = "/single", method = RequestMethod.POST)
	public void singleSave(String objId, String labels) {
		List<String> labelIds = Lists.newArrayList();
		JSONArray ja = JSONArray.fromObject(labels);
		for (@SuppressWarnings("rawtypes")
		Iterator iterator = ja.iterator(); iterator.hasNext();) {
			String label = (String) iterator.next();
			labelIds.add(label);
		}
		objRelBS.updateRel(objId, labelIds);
	}
	
	/**
	 * 批量保存对象-标签关系
	 * @param objIds 对象ID json字符串数组
	 * @param labels 标签ID json字符串数组
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/batchSave", method = RequestMethod.POST)
	public void batchSave(String objIds, String labels) {
		// 获取labelId列表
		List<String> labelIds = Lists.newArrayList();
		JSONArray ja = JSONArray.fromObject(labels);
		for (Iterator iterator = ja.iterator(); iterator.hasNext();) {
			String label = (String) iterator.next();
			labelIds.add(label);
		}
		// 批量保存
		JSONArray ja1 = JSONArray.fromObject(objIds);
		for (Iterator iterator = ja1.iterator(); iterator.hasNext();) {
			String objId = (String) iterator.next();
			if (!StringUtils.isEmpty(objId) && labelIds.size() > 0) {
				objRelBS.updateRel(objId, labelIds);
			}
		}
	}
	
	/**
	 * 案例1，获取标签
	 * @param objId
	 * @return
	 */
	@RequestMapping("/labels.json")
	@ResponseBody
	public List<BioneLabelObjRel> exmapleGetLabels(String labelObjId, String objId) {
		return objRelBS.getLabels(labelObjId, objId);
	}
	/**
	 * 案例2，获取对象
	 * @param pager
	 * @param labels
	 * @return
	 */
	@RequestMapping("/exmaple2/obj.json")
	@ResponseBody
	public Map<String, Object> obj(Pager pager, String labels) {
		JSONArray ja = JSONArray.fromObject(labels);
		if (!StringUtils.isEmpty(labels) && ja.size() > 0) {
			Set<String> set = Sets.newHashSet();
			for (@SuppressWarnings("rawtypes")
			Iterator iterator = ja.iterator(); iterator.hasNext();) {
				String str = (String) iterator.next();
				set.add(str);
			}
			List<String> list = objRelBS.getOBjIdByLabel(set);
			SearchResult<BioneLabelObjInfo> rs = objRelBS.getObj(
					pager.getPageFirstIndex(), pager.getPagesize(), list);
			Map<String, Object> map = Maps.newHashMap();
			map.put("Total", rs.getTotalCount());
			map.put("Rows", rs.getResult());
			return map;
		} else {
			SearchResult<BioneLabelObjInfo> rs = objRelBS.getAllObj(
					pager.getPageFirstIndex(), pager.getPagesize());
			Map<String, Object> map = Maps.newHashMap();
			map.put("Total", rs.getTotalCount());
			map.put("Rows", rs.getResult());
			return map;
		}
	}
}
