package com.yuchengtech.emp.biappframe.label.web;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.biappframe.label.entity.BioneLabelInfo;
import com.yuchengtech.emp.biappframe.label.entity.BioneLabelObjInfo;
import com.yuchengtech.emp.biappframe.label.entity.BioneLabelTypeInfo;
import com.yuchengtech.emp.biappframe.label.service.LabelBS;
import com.yuchengtech.emp.biappframe.label.service.LabelObjBS;
import com.yuchengtech.emp.biappframe.label.service.LabelTypeBS;
import com.yuchengtech.emp.bione.common.CommonTreeNode;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.bione.entity.page.Pager;
import com.yuchengtech.emp.bione.util.RandomUtils;
/**
 * <pre>
 * Title:标签配置
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
@Controller
@RequestMapping("/bione/label/labelConfig")
public class labelConfigController extends BaseController {
	@Autowired
	private LabelBS labelBS;
	@Autowired
	private LabelObjBS labelObjBS;
	@Autowired
	private LabelTypeBS labelTypeBS;
	/**
	 * 主页
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "/bione/label/label-config-index";
	}
	/**
	 * 树节点
	 * @param realId
	 * @param type
	 * @return
	 */
	@RequestMapping("/treeNode.json")
	@ResponseBody
	public List<CommonTreeNode> treeNode(String realId, String type) {
		return labelBS.getTreeNode(realId, type);
	}
	/**
	 * 内容列表
	 * @param pager
	 * @param realId
	 * @param type
	 * @return
	 */
	@RequestMapping("/list.json")
	@ResponseBody
	public Map<String, Object> list(Pager pager, String realId, String type) {
		Map<String, Object> page = Maps.newHashMap();
		if ("root".equals(type)) {
			SearchResult<BioneLabelObjInfo> rs = labelBS.findObjPage(
					pager.getPageFirstIndex(), pager.getPagesize(),
					pager.getSearchCondition());
			page.put("Rows", rs.getResult());
			page.put("Total", rs.getTotalCount());
			return page;
		}

		if (!StringUtils.isEmpty(realId)) {
			if ("obj".equals(type)) {
				SearchResult<BioneLabelTypeInfo> rs = labelBS.findTypePage(
						pager.getPageFirstIndex(), pager.getPagesize(),
						pager.getSearchCondition(), realId);
				page.put("Rows", rs.getResult());
				page.put("Total", rs.getTotalCount());
				return page;
			}

			if ("type".equals(type)) {
				SearchResult<BioneLabelInfo> rs = labelBS.findLabelPage(
						pager.getPageFirstIndex(), pager.getPagesize(),
						pager.getSearchCondition(), realId);
				page.put("Rows", rs.getResult());
				page.put("Total", rs.getTotalCount());
				return page;
			}
		}
		return null;
	}
	/**
	 * 新增跳转
	 * @param type
	 * @param realId
	 * @return
	 */
	@RequestMapping("/new")
	public ModelAndView editNew(String type, String realId) {
		ModelMap mm = new ModelMap();
		mm.addAttribute("realId", realId);
		mm.addAttribute("isNew", true);
		if ("root".equals(type)) {
			mm.addAttribute("logicSysNo", labelBS.getLogicSysNo());
			return new ModelAndView("/bione/label/label-obj-edit", mm);
		}
		if ("obj".equals(type)) {
			return new ModelAndView("/bione/label/label-type-edit", mm);
		}
		if ("type".equals(type)) {
			return new ModelAndView("/bione/label/label-edit", mm);
		}
		return null;
	}
	/**
	 * 修改跳转
	 * @param id
	 * @param type
	 * @return
	 */
	@RequestMapping("/{id}/edit")
	public ModelAndView edit(@PathVariable String id, String type) {
		ModelMap mm = new ModelMap();
		mm.addAttribute("id", id);
		mm.addAttribute("isNew", false);
		if ("root".equals(type)) {
			return new ModelAndView("/bione/label/label-obj-edit", mm);
		}
		if ("obj".equals(type)) {
			return new ModelAndView("/bione/label/label-type-edit", mm);
		}
		if ("type".equals(type)) {
			return new ModelAndView("/bione/label/label-edit", mm);
		}
		return null;
	}
	/**
	 * 删除
	 * @param id
	 * @param type
	 */
	@RequestMapping(value = "/{type}/{id}", method = RequestMethod.DELETE)
	public void destroy(@PathVariable("id") String id, @PathVariable("type") String type) {
		if ("root".equals(type)) {
			labelBS.deleteObj(id);
		}
		if ("obj".equals(type)) {
			labelBS.deleteType(id);
		}
		if ("type".equals(type)) {
			labelBS.deleteLabel(id);
		}
	}
	/**
	 * 新增标签对象
	 * @param model
	 */
	@RequestMapping(value = "/obj", method = RequestMethod.POST)
	public void createObj(BioneLabelObjInfo model) {
		if (StringUtils.isEmpty(model.getLabelObjId())) {
			model.setLabelObjId(RandomUtils.uuid2());
		}
		labelObjBS.updateEntity(model);
	}
	/**
	 * 新增标签类型
	 * @param model
	 */
	@RequestMapping(value = "/type", method = RequestMethod.POST)
	public void createType(BioneLabelTypeInfo model) {
		if (StringUtils.isEmpty(model.getTypeId())) {
			model.setTypeId(RandomUtils.uuid2());
		}
		labelTypeBS.updateEntity(model);
	}
	/**
	 * 新增标签
	 * @param model
	 */
	@RequestMapping(value = "/label", method = RequestMethod.POST)
	public void createLabel(BioneLabelInfo model) {
		if (StringUtils.isEmpty(model.getLabelId())) {
			model.setLabelId(RandomUtils.uuid2());
		}
		labelBS.updateEntity(model);
	}
	
	// 验证
	
	/**
	 * 验证标签对象标识
	 * @param labelObjNo
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/checkObj", method = RequestMethod.GET)
	@ResponseBody
	public boolean checkObj(String labelObjNo, String id) {
		return labelBS.isContainObj(labelObjNo, id) ? false : true;
	}
	
	/**
	 * 验证标签对象名称
	 * @param labelObjName
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/checkObjName", method = RequestMethod.GET)
	@ResponseBody
	public boolean checkObjName(String labelObjName, String id) {
		return labelBS.isContainObjName(labelObjName, id) ? false : true;
	}
	
	/**
	 * 验证标签类型名称
	 * @param id
	 * @param typeName
	 * @param realId
	 * @return
	 */
	@RequestMapping(value = "/checkTypeName")
	@ResponseBody
	public boolean checkTypeName(String id, String typeName, String realId) {
		return labelBS.isContainTypeName(id, typeName, realId) ? false : true;
	}
	
	/**
	 * 验证标签名称
	 * @param id
	 * @param labelName
	 * @param realId
	 * @return
	 */
	@RequestMapping(value = "/checkLabelName")
	@ResponseBody
	public boolean checkLabelName(String id, String labelName, String realId) {
		return labelBS.isContainLabelName(id, labelName, realId) ? false : true;
	}
	
	/**
	 * 自动填表内容
	 * @param type
	 * @param id
	 * @return
	 */
	@RequestMapping("/info.json")
	@ResponseBody
	public Object info(String type, String id) {
		if (id != null) {
			if ("obj".equals(type)) {
				return labelObjBS.getEntityById(id);
			}
			
			if ("type".equals(type)) {
				return labelTypeBS.getEntityById(id);
			}
			
			if ("label".equals(type)) {
				return labelBS.getEntityById(id);
			}
		}
		return null;
	}
}
