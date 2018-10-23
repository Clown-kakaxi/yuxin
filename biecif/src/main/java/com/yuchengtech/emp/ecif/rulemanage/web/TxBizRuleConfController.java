package com.yuchengtech.emp.ecif.rulemanage.web;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.bione.entity.page.Pager;
import com.yuchengtech.emp.bione.util.BeanUtils;
import com.yuchengtech.emp.bione.util.RandomUtils;
import com.yuchengtech.emp.ecif.base.util.CustomTimestampEditor;
import com.yuchengtech.emp.ecif.rulemanage.entity.TxBizRuleConf;
import com.yuchengtech.emp.ecif.rulemanage.service.TxBizRuleConfBS;
import com.yuchengtech.emp.ecif.rulemanage.vo.TxBizRuleConfVO;

/**
 * <pre>
 * Description: 规则组维护Controller
 * </pre>
 * 
 * @author lizongyu lizyu1@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：		  修改日期:     修改内容:
 * </pre>
 */

@Controller
@RequestMapping("/ecif/rulemanage/txbizruleconf")
public class TxBizRuleConfController extends BaseController {
	protected static Logger log = LoggerFactory.getLogger(TxBizRuleConfController.class);
	
	@Autowired
	private TxBizRuleConfBS txBizRuleConfBS;

	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping("/list.*")
	@ResponseBody
	public Map<String, Object> list(Pager pager, String ruleGroupId) {
		SearchResult<TxBizRuleConfVO> searchResult = txBizRuleConfBS
				.getRuleConfList(pager.getPageFirstIndex(),
						pager.getPagesize(), pager.getSortname(),
						pager.getSortorder(), pager.getSearchCondition(),
						ruleGroupId);
		Map<String, Object> txBizRuleConfMap = Maps.newHashMap();
		txBizRuleConfMap.put("Rows", searchResult.getResult());
		txBizRuleConfMap.put("Total", searchResult.getTotalCount());
		return txBizRuleConfMap;
	}

	/**
	 * 执行添加前页面跳转
	 * 
	 * @return String 用于匹配结果页面
	 */

	@RequestMapping(value = "/{ruleGroupId}/new", method = RequestMethod.GET)
	public ModelAndView editNew(@PathVariable("ruleGroupId") String ruleGroupId) {
		return new ModelAndView("/ecif/rulemanage/ruleconf-edit",
				"ruleGroupId", ruleGroupId);
	}

	/**
	 * 用于保存添加或修改时的对象
	 */

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public void create(TxBizRuleConf model) {
		if (model != null) {
			if (model.getRuleId() == null || "".equals(model.getRuleId())) {
				// 若是新增操作
				
				model.setRuleId(RandomUtils.randomLong());				
				model.setCreateOper(BiOneSecurityUtils.getCurrentUserInfo().getUserName());
				model.setCreateTime(new Timestamp(System.currentTimeMillis()));
			}else{
				
				TxBizRuleConf tbrc = txBizRuleConfBS.getEntityById(model.getRuleId());
				model.setRuleGroupId(tbrc.getRuleGroupId());
				model.setCreateOper(tbrc.getCreateOper());
				model.setCreateTime(tbrc.getCreateTime());
				model.setUpdateOper(BiOneSecurityUtils.getCurrentUserInfo().getUserName());
				model.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			}
			txBizRuleConfBS.saveOrUpdateEntity(model);
		}
	}

	/**
	 * 执行修改前页面跳转
	 * 
	 * @return 跳转修改页面
	 */
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") String id) {
		return new ModelAndView("/ecif/rulemanage/ruleconf-edit", "id", id);
	}

	/**
	 * 读取修改信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public TxBizRuleConfVO show(@PathVariable("id") String id) {
		TxBizRuleConf conf = txBizRuleConfBS.getEntityById(Long.parseLong(id));
		TxBizRuleConfVO vo = new TxBizRuleConfVO();
		this.txBizRuleConfBS.mappingEntVo(vo, conf);
		if(conf.getParentRuleId()!=null){
			vo.setParentRuleName(txBizRuleConfBS.getEntityById(conf.getParentRuleId()).getRuleName());
		}
		return vo;
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping("/deleteRuleConf")
	@ResponseBody
	public int deleteBatch(String ids) {
		 int flag=0;
		String[] idArr = ids.split(",");
		if(txBizRuleConfBS.deleteBatch(idArr)){
			flag=1;
		}
		return flag;
	}
	
	/**
	 * 标识名称验证重复
	 */
	@RequestMapping("/ruleConfValid")
	@ResponseBody
	public boolean ruleConfValid(TxBizRuleConf model,String ruleId){
		return txBizRuleConfBS.ruleConfValid(model.getRuleNo(),model.getRuleName(),ruleId);
	}
	
	/**
	 * 获取规则INFO
	 */
	@RequestMapping(value = "/ruleConfInfo", method = RequestMethod.GET)
	public String tiggerInfo() {
		return "/ecif/rulemanage/ruleconfinfo";
	}
	
	/**
	 * 获取规则INFO
	 */
	@RequestMapping(value = "/ruleConfInfo-txdef", method = RequestMethod.GET)
	public String ruleConfInfoTxdef() {
		return "/ecif/rulemanage/ruleconfinfo-txdef";
	}
		
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		 datetimeFormat.setLenient(false); 
		binder.registerCustomEditor(Timestamp.class,   new CustomTimestampEditor(datetimeFormat, true));
	}
	
	/**
	 * 获取combobox
	 */
	@ResponseBody
	@RequestMapping("getComBoBox.*")
	public List<Map<String,String>> getComBoBox() {
		List<Map<String,String>> list = this.txBizRuleConfBS.getComBoBox();
		return list;
	}
	
}
