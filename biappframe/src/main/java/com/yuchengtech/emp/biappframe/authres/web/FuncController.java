package com.yuchengtech.emp.biappframe.authres.web;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yuchengtech.emp.biappframe.authres.entity.BioneFuncInfo;
import com.yuchengtech.emp.biappframe.authres.service.FuncBS;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.bione.common.CommonTreeNode;
import com.yuchengtech.emp.bione.util.EhcacheUtils;
import com.yuchengtech.emp.bione.util.RandomUtils;

/**
 * <pre>
 * Title:CRUD操作演示
 * Description: 完成用户信息表的CRUD操作
 * </pre>
 * 
 * @author xuguangyuan xuguangyuansh@gmail.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：许广源		  修改日期:     修改内容:
 * </pre>
 */
@Controller
@RequestMapping("/bione/admin/func")
public class FuncController extends BaseController {
	@Autowired
	private FuncBS funcBS;

	@RequestMapping(value = "/{id}/index", method = RequestMethod.GET)
	public ModelAndView index(@PathVariable("id") String id) {
		return new ModelAndView("/bione/module/func-index", "id", id);
	}

	/**
	 * 加载下拉图标选择
	 */
	@RequestMapping("/buildIconCombox.*")
	@ResponseBody
	public List<Map<String, String>> buildIconCombox() {
		List<Map<String, String>> list = this.buildIconCombox("menuicons");
		return list;
	}

	/**
	 * 图标选择
	 * 
	 * @return
	 */
	@RequestMapping("/selectIcon")
	public ModelAndView selectIcon() {
		String iconsHTML = this.buildIconSelectHTML("menuicons");
		return new ModelAndView("/bione/module/func-icons", "iconsHTML", iconsHTML);
	}

	/**
	 * 获取生成树数据，以树显示功能树
	 */
	@RequestMapping("/{id}/list.*")
	@ResponseBody
	public List<CommonTreeNode> list(@PathVariable("id") String id) {
		String targetId;
		targetId = (String) EhcacheUtils.get(BiOneSecurityUtils.getCurrentUserInfo().getLoginName(), "funcId");
		EhcacheUtils.remove(BiOneSecurityUtils.getCurrentUserInfo().getLoginName(), "funcId");
		List<CommonTreeNode> list = funcBS.buildFuncTree(
				BiOneSecurityUtils.getCurrentUserInfo().getCurrentLogicSysNo(), id, true, targetId);
		addProjectUrl(list);
		return list;
	}

	/**
	 * 补全导航图标的URL地址
	 * @param list
	 */
	public void addProjectUrl(List<CommonTreeNode> list) {
		for (CommonTreeNode treeNode : list) {
			if (treeNode.getChildren() != null) {
				addProjectUrl(treeNode.getChildren());
			}
			if (treeNode.getIcon() != null)
				treeNode.setIcon(this.getProjectUrl() + treeNode.getIcon());
		}
	}

	/**
	 * 根据ID，加载数据
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public BioneFuncInfo show(@PathVariable("id") String id) {
		BioneFuncInfo model = this.funcBS.getEntityById(id);
		return model;
	}

	/**
	 * 获取上级功能的名称
	 */
	@RequestMapping(value = "/{id}/up", method = RequestMethod.GET)
	@ResponseBody
	public BioneFuncInfo showUp(@PathVariable("id") String id) {
		if (!"0".equals(id) && id != null) {
			BioneFuncInfo model = new BioneFuncInfo();
			model.setFuncName(funcBS.getEntityById(id).getFuncName());
			return model;
		}
		return null;
	}

	/**
	 * 用于添加，或修改时的保存对象
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void create(BioneFuncInfo model) {
		if (StringUtils.isEmpty(model.getFuncId())) {
			model.setFuncId(null);
		}
		if (StringUtils.isEmpty(model.getNavIcon())) {
			model.setNavIcon(null);
		}
		if(model.getFuncId()==null||model.getFuncId().equals("")){
			model.setFuncId(RandomUtils.uuid2());
		}
		model = funcBS.saveOrUpdate(model);
		EhcacheUtils.put(BiOneSecurityUtils.getCurrentUserInfo().getLoginName(), "funcId", model.getFuncId());
	}

	/**
	 * 执行删除操作，依据指定ID
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void destroy(@PathVariable("id") String id) {
		String[] ids = id.split(",");
		funcBS.removeEntityAndChild(ids[0]);
		EhcacheUtils.put(BiOneSecurityUtils.getCurrentUserInfo().getLoginName(), "funcId", ids[1]);
	}

}
