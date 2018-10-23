package com.yuchengtech.emp.ecif.alarm.web;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.bione.entity.page.Pager;
import com.yuchengtech.emp.ecif.alarm.entity.TxAlarmConf;
import com.yuchengtech.emp.ecif.alarm.entity.TxAlarmConfVO;
import com.yuchengtech.emp.ecif.alarm.service.TxAlarmConfBS;
import com.yuchengtech.emp.ecif.transaction.entity.TxClientAuth;
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
@RequestMapping("/ecif/alarm/txalarmconf")
public class TxAlarmConfController extends BaseController {
	protected static Logger log = LoggerFactory.getLogger(TxAlarmConfController.class);

	@Autowired
	private TxAlarmConfBS txAlarmConfBS;

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "/ecif/alarm/txalarmconf-index";
	}

	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping("/list.*")
	@ResponseBody
	public Map<String, Object> list(Pager pager) {
		SearchResult<TxAlarmConf> searchResult = this.txAlarmConfBS.getTxAlarmConfList(pager.getPageFirstIndex(),
				pager.getPagesize(), pager.getSortname(), pager.getSortorder(), pager.getSearchCondition());
		
		List<TxAlarmConf> txAlarmConfList = searchResult.getResult();
		List<TxAlarmConfVO> txAlarmConfVOList = new ArrayList<TxAlarmConfVO>();
		
		List<Map<String,String>> grouplist = getComBoBox("0");   //分组
		Map<String,String> groupmap = convertList2Map(grouplist);
		List<Map<String,String>> userlist = getComBoBox("1");   //人员
		
		Map<String,String> usermap = convertList2Map(userlist);
		
		for(TxAlarmConf TxAlarmConf:txAlarmConfList){
			TxAlarmConfVO vo = new TxAlarmConfVO();
			try {
				BeanUtils.copyProperties(TxAlarmConf, vo);
			    if(TxAlarmConf.getAlarmObjectType()!=null){
			    	if(TxAlarmConf.getAlarmObjectType().equals("0")){
			    		vo.setAlarmObjectName((String)groupmap.get(TxAlarmConf.getAlarmObjectId().toString()));
			    	}else if(TxAlarmConf.getAlarmObjectType().equals("1")){
			    		vo.setAlarmObjectName((String)usermap.get(TxAlarmConf.getAlarmObjectId().toString()));
			    	}
			    }
			} catch (Exception e) {
				log.warn("属性复制发生异常");
			}
			txAlarmConfVOList.add(vo);
		}
		
		
		Map<String, Object> resDefMap = Maps.newHashMap();
		resDefMap.put("Rows", txAlarmConfVOList);
		resDefMap.put("Total", searchResult.getTotalCount());
		return resDefMap;
	}

	/**
	 * 用于添加，或修改时的保存对象
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void create(TxAlarmConf model) {
		this.txAlarmConfBS.updateEntity(model);
	}

	/**
	 * 根据ID，加载数据
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public TxAlarmConf show(@PathVariable("id") Long id) {
		TxAlarmConf model = this.txAlarmConfBS.getEntityById(id);
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
		return new ModelAndView("/ecif/alarm/txalarmconf-edit", "id", id);
	}

	/**
	 * 执行添加前页面跳转
	 * 
	 * @return
	 * 		String	用于匹配结果页面
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String editNew() {
		return "/ecif/alarm/txalarmconf-edit";
	}

	/**
	 * 执行删除操作，可进行指删除
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void destroy(@PathVariable("id") String id) {
		String[] ids = id.split(",");
		if (ids.length > 1) {
			this.txAlarmConfBS.removeEntityByProperty("alarmConfId", id);
		} else {
			this.txAlarmConfBS.removeEntityById(new Long(id));
		}
	}
	
	/**
	 * 跳转 添加服务授权页面
	 * 
	 * @return
	 */
	@RequestMapping("/authservice")
	public ModelAndView authservice(String clientAuthId) {
		return new ModelAndView("/ecif/alarm/txserviceauth-index", "clientAuthId",
				clientAuthId);
	}
	

	/**
	 * 表单验证中的后台验证，验证模块标识是否已存在
	 */
	@RequestMapping(value = "resDefNoValid")
	@ResponseBody
	public boolean resDefNoValid(String id,String groupName) {
		
		String alarmConfId = this.txAlarmConfBS.getGroupName(groupName);
		List<TxAlarmConf> model = txAlarmConfBS.findEntityListByProperty("alarmConfId", alarmConfId);
		if (model != null){	
			for(int i=0;i<model.size();i++){
				TxAlarmConf t = model.get(i);
				if(t.getAlarmConfId().toString().equals(id)){ //修改
					return true;
				}else{
					return false;
				}
			}
			
		}else
			return true;

		return true;
	}

	/**
	 * 获取combobox
	 */
	@ResponseBody
	@RequestMapping("getComBoBox.*")
	public List<Map<String,String>> getComBoBox(String alarmObjectType) {
		List<Map<String,String>> list = this.txAlarmConfBS.getComBoBox(alarmObjectType);
		return list;
	}

	/**
	 * 获取combobox
	 */
	@ResponseBody
	@RequestMapping("getComBoBoxById.*")
	public List<Map<String,String>> getComBoBoxById(Long id) {
		List<Map<String,String>> list = this.txAlarmConfBS.getComBoBoxById(id);
		return list;
	}
	
	public Map<String, String> convertList2Map(List<Map<String,String>> list){
		Map<String, String> newmap = new HashMap<String, String>();
		for(Map<String,String> map:list ){
			String id =    (String)map.get("id");
			String text =(String)map.get("text");
			
			newmap.put(id, text);
		}
		
		return newmap;
	}	
}
