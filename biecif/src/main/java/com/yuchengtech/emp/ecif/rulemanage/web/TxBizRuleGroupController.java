package com.yuchengtech.emp.ecif.rulemanage.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.bione.common.CommonTreeNode;
import com.yuchengtech.emp.bione.util.RandomUtils;
import com.yuchengtech.emp.ecif.rulemanage.entity.TxBizRuleGroup;
import com.yuchengtech.emp.ecif.rulemanage.service.TxBizRuleGroupBS;
/**
 * <pre>
 * Description: 规则组维护Controller 
 * </pre>	
 * @author lizongyu lizyu1@yuchengtech.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：李宗禹		  修改日期:     修改内容: 
 * </pre>
 */

@Controller
@RequestMapping("/ecif/rulemanage/txbizrulegroup")
public class TxBizRuleGroupController extends BaseController{
	protected static Logger log = LoggerFactory.getLogger(TxBizRuleGroupController.class);
	
	@Autowired
	private TxBizRuleGroupBS txBizRuleGroupBS;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "/ecif/rulemanage/rulegroup-tree";
	}
	
	/**
	 * 节点选择树
	 * 
	 * @param sysId
	 *            系统Id
	 * @param leafType
	 *            叶节点类型
	 * @return
	 */
	@RequestMapping(value = "/getNodeTree.*", method = RequestMethod.POST)
	@ResponseBody
	public List<CommonTreeNode> getNodeTree() {
		return this.txBizRuleGroupBS.getRuleGroupTree();
	}
	/**
	 * 执行添加前页面跳转
	 * 
	 * @return String 用于匹配结果页面
	 */

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String editNew() {
		return "/ecif/rulemanage/rulegroup-edit";
	}
	
	/**
	 * 用于保存添加或修改时的对象
	 */

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public void create(TxBizRuleGroup model) {
		if (model != null) {
			if (model.getRuleGroupId() == null || "".equals(model.getRuleGroupId())) {
				// 若是新增操作
				model.setRuleGroupId(RandomUtils.randomLong());
			}
			txBizRuleGroupBS.saveOrUpdateEntity(model);
		}
	}
	/**
	 * 执行修改前页面跳转
	 * 
	 * @return 跳转修改页面
	 */
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") String id) {
		return new ModelAndView("/ecif/rulemanage/rulegroup-edit", "id", id);
	}
	
	/**
	 * 读取修改信息
	 * 
	 * @return 
	 */
	 @RequestMapping(value = "/{id}", method = RequestMethod.GET)
		@ResponseBody
		public TxBizRuleGroup show(@PathVariable("id") String id) {
			return txBizRuleGroupBS.getEntityById(Long.parseLong(id));
		}
	 
	 
		/**
		 * 批量删除
		 */
		@RequestMapping("/deleteRuleGroup")
		@ResponseBody
		public int deleteRuleGroup(String id) {
			 int flag=0;
			if(txBizRuleGroupBS.deleteRuleGroup(id)){
				txBizRuleGroupBS.removeEntityById(Long.parseLong(id));
				flag=1;
			}
			return flag;
		}
		/**
		 * 标识名称验证重复
		 */
		@RequestMapping("/ruleGroupValid")
		@ResponseBody
		public boolean ruleGroupValid(TxBizRuleGroup model,String ruleGroupId){
			return txBizRuleGroupBS.ruleGroupValid(model.getRuleGroupNo(),model.getRuleGroupName(),ruleGroupId);
		}

}
