package com.yuchengtech.emp.ecif.transaction.web;


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
import com.yuchengtech.emp.ecif.transaction.entity.TxClientAuth;
import com.yuchengtech.emp.ecif.transaction.entity.TxClientAuthVO;
import com.yuchengtech.emp.ecif.transaction.service.TxClientAuthBS;

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
@RequestMapping("/ecif/transaction/txclientauth")
public class TxClientAuthController extends BaseController {
	protected static Logger log = LoggerFactory.getLogger(TxClientAuthController.class);

	@Autowired
	private TxClientAuthBS txClientAuthBS;

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "/ecif/transaction/txclientauth-index";
	}

	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping("/list.*")
	@ResponseBody
	public Map<String, Object> list(Pager pager) {
		SearchResult<TxClientAuth> searchResult = this.txClientAuthBS.getTxClientAuthList(pager.getPageFirstIndex(),
				pager.getPagesize(), pager.getSortname(), pager.getSortorder(), pager.getSearchCondition());
		
		List<TxClientAuth> txClientAuthList = searchResult.getResult();
		
		List<Map<String,String>> list = getComBoBox();
		Map<String,String> map = convertList2Map(list);
		
		List<TxClientAuthVO> txClientAuthVOList = new ArrayList<TxClientAuthVO>();
		for(TxClientAuth txClientAuth:txClientAuthList){
			TxClientAuthVO vo = new TxClientAuthVO();
			try {
				BeanUtils.copyProperties(txClientAuth, vo);
			    vo.setSrcSysNm(map.get(txClientAuth.getSrcSysCd().toString()));
			} catch (Exception e) {
				log.warn("属性复制发生异常");
			}
			txClientAuthVOList.add(vo);
		}
		

		Map<String, Object> resDefMap = Maps.newHashMap();
//		resDefMap.put("Rows", searchResult.getResult());
		resDefMap.put("Rows", txClientAuthVOList);	
		resDefMap.put("Total", searchResult.getTotalCount());
		return resDefMap;
	}

	/**
	 * 用于添加，或修改时的保存对象
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void create(TxClientAuth model) {
		this.txClientAuthBS.updateEntity(model);
	}

	/**
	 * 根据ID，加载数据
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public TxClientAuth show(@PathVariable("id") Long id) {
		TxClientAuth model = this.txClientAuthBS.getEntityById(id);
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
		return new ModelAndView("/ecif/transaction/txclientauth-edit", "id", id);
	}

	/**
	 * 执行添加前页面跳转
	 * 
	 * @return
	 * 		String	用于匹配结果页面
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String editNew() {
		return "/ecif/transaction/txclientauth-edit";
	}

	/**
	 * 执行删除操作，可进行指删除
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void destroy(@PathVariable("id") String id) {
		String[] ids = id.split(",");
		if (ids.length > 1) {
			this.txClientAuthBS.removeEntityByProperty("clientAuthId", id);
		} else {
			this.txClientAuthBS.removeEntityById(new Long(id));
		}
	}
	
	/**
	 * 跳转 添加服务授权页面
	 * 
	 * @return
	 */
	@RequestMapping("/authservice")
	public ModelAndView authservice(String clientAuthId) {
		return new ModelAndView("/ecif/transaction/txserviceauth-index", "clientAuthId",
				clientAuthId);
	}
	

	/**
	 * 表单验证中的后台验证，验证模块标识是否已存在
	 */
	@RequestMapping(value = "resDefNoValid")
	@ResponseBody
	public boolean resDefNoValid(String id,String srcSysCdName) {
		
		String srcSysCd = this.txClientAuthBS.getSrcSystem(srcSysCdName);
		List<TxClientAuth> model = txClientAuthBS.findEntityListByProperty("srcSysCd", srcSysCd);
		if (model != null){	
			for(int i=0;i<model.size();i++){
				TxClientAuth t = model.get(i);
				if(t.getClientAuthId().toString().equals(id)){ //修改
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
	public List<Map<String,String>> getComBoBox() {
		List<Map<String,String>> list = this.txClientAuthBS.getComBoBox();
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
