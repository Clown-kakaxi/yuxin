package com.yuchengtech.emp.ecif.alarm.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
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
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.bione.entity.page.Pager;
import com.yuchengtech.emp.ecif.alarm.entity.TxAlarmUser;
import com.yuchengtech.emp.ecif.alarm.entity.TxAlarmUserGroupRel;
import com.yuchengtech.emp.ecif.alarm.entity.TxAlarmUserGroupRelVO;
import com.yuchengtech.emp.ecif.alarm.service.TxAlarmUserBS;
import com.yuchengtech.emp.ecif.alarm.service.TxAlarmUserGroupRelBS;
import com.yuchengtech.emp.ecif.transaction.entity.TxClientAuthVO;

/**
 * <pre>
 * Title:CRUD操作演示
 * Description: 完成表的CRUD操作 
 * </pre>	
 * @author shangjf  shangjf@yuchengtech.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：尚吉峰		  修改日期:     修改内容: 
 * </pre>
 */
@Controller
@RequestMapping("/ecif/alarm/txalarmusergrouprel")
public class TxAlarmUserGroupRelController extends BaseController {
	@Autowired
	private TxAlarmUserGroupRelBS txAlarmUserGroupRelBS;

	@Autowired
	private TxAlarmUserBS txAlarmUserBS;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		
		return "/ecif/alarm/txalarmusergrouprel-index";
	}

	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping("/list.*")
	@ResponseBody
	public Map<String, Object> list(Pager pager,String groupId) {
		SearchResult<TxAlarmUserGroupRel> searchResult = this.txAlarmUserGroupRelBS.getTxAlarmUserGroupRelList(pager.getPageFirstIndex(),
				pager.getPagesize(), pager.getSortname(), pager.getSortorder(), pager.getSearchCondition(),groupId);
		
		List<TxAlarmUserGroupRelVO> txAlarmUserGroupRelVOList = new ArrayList<TxAlarmUserGroupRelVO>();

		List<TxAlarmUserGroupRel> list = searchResult.getResult();
		for(TxAlarmUserGroupRel txAlarmUserGroupRel:list){
			TxAlarmUserGroupRelVO vo = new TxAlarmUserGroupRelVO();
			try {
				BeanUtils.copyProperties(txAlarmUserGroupRel, vo);
				TxAlarmUser txAlarmUser =this.txAlarmUserBS.getEntityById(txAlarmUserGroupRel.getUserId());
				vo.setUserName(txAlarmUser.getUserName());
				
			} catch (Exception e) {
			}
			txAlarmUserGroupRelVOList.add(vo);
		}
		
		Map<String, Object> resDefMap = Maps.newHashMap();
		resDefMap.put("Rows", txAlarmUserGroupRelVOList);
		resDefMap.put("Total", searchResult.getTotalCount());
		return resDefMap;
	}

	/**
	 * 用于添加，或修改时的保存对象
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void create(TxAlarmUserGroupRel model) {
		this.txAlarmUserGroupRelBS.updateEntity(model);
	}

	/**
	 * 根据ID，加载数据
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public TxAlarmUserGroupRel show(@PathVariable("id") Long id) {
		TxAlarmUserGroupRel model = this.txAlarmUserGroupRelBS.getEntityById(id);
		return model;
	}

	/**
	 * 执行修改前的数据加载
	 * 
	 * @return
	 * 		String	用于匹配结果页面
	 */
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") Long id) {
		return new ModelAndView("/ecif/alarm/txalarmusergrouprel-edit", "id", new Long(id));
	}

	/**
	 * 执行添加前页面跳转
	 * 
	 * @return
	 * 		String	用于匹配结果页面
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView editNew(Long groupId) {
		ModelMap mm = new ModelMap();
		mm.put("groupId", groupId);
		
		return new ModelAndView("/ecif/alarm/txalarmusergrouprel-edit", mm);
	}

	/**
	 * 执行删除操作，可进行指删除
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void destroy(@PathVariable("id") String id) {
		String[] ids = id.split(",");
		if (ids.length > 1) {
			this.txAlarmUserGroupRelBS.removeEntityByProperty("relId", id);
		} else {
			this.txAlarmUserGroupRelBS.removeEntityById(new Long(id));
		}
	}


	/**
	 * 跳转 添加服务授权页面
	 * 
	 * @return
	 */
	@RequestMapping("/authservice")
	public ModelAndView authservice(String groupId) {
		return new ModelAndView("/ecif/alarm/txalarmusergrouprel-index", "groupId",
				groupId);
	}
	
	@RequestMapping(value = "/checkserviceId", method = RequestMethod.GET)
	@ResponseBody
	public String checkGroupName(String txCode,Long groupId) {

		boolean flag = this.txAlarmUserGroupRelBS.checkGroupName(groupId, txCode);
		return flag ? "true" : "false";
	}

	
	/**
	 * 获取combobox
	 */
	@ResponseBody
	@RequestMapping("getComBoBox.*")
	public List<Map<String,String>> getComBoBox() {
		List<Map<String,String>> list = this.txAlarmUserGroupRelBS.getComBoBox();
		return list;
	}
	
	
}
