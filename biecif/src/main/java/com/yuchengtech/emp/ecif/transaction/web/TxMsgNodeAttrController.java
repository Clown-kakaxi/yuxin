package com.yuchengtech.emp.ecif.transaction.web;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.yuchengtech.emp.ecif.transaction.entity.TxMsgNode;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsgNodeAttr;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsgNodeAttrVO;
import com.yuchengtech.emp.ecif.transaction.service.TxMsgNodeAttrBS;
import com.yuchengtech.emp.ecif.transaction.service.TxMsgNodeBS;

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
@RequestMapping("/ecif/transaction/txmsgnodeattr")
public class TxMsgNodeAttrController extends BaseController {
	@Autowired
	private TxMsgNodeAttrBS txMsgNodeAttrBS;

	@Autowired
	private TxMsgNodeBS txMsgNodeBS;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		
		return "/ecif/transaction/txmsgnodeattr-index";
	}

	@RequestMapping(value ="/tableindex",method = RequestMethod.GET)
	public String tableindex() {
		
		return "/ecif/transaction/txmsgnodeattr-listtabs";
	}
	
	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping("/initlist.*")
	@ResponseBody
	public Map<String, Object> initlist(Pager pager,String nodeId ) {
		//pager.setCondition(condition);

		SearchResult<TxMsgNodeAttr> searchResult = this.txMsgNodeAttrBS.getTxMsgNodeAttrListByTab(pager.getPageFirstIndex(),
				pager.getPagesize(), pager.getSortname(), pager.getSortorder(), pager.getSearchCondition(),nodeId);
		
		Map<String, Object> resDefMap = Maps.newHashMap();
		resDefMap.put("Rows", searchResult.getResult());
		resDefMap.put("Total", searchResult.getTotalCount());
		return resDefMap;
	}	
	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping("/list.*")
	@ResponseBody
	public Map<String, Object> list(Pager pager,String nodeId ) {

        Map<String, Object> resDefMap = this.txMsgNodeAttrBS.getTxMsgNodeAttrVOList(nodeId);
		
		return resDefMap;
	}
	
	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping("/listtables.*")
	@ResponseBody
	public Map<String, Object> listtables(String nodeId,String tabids ) {
		
		Map<String, Object> map = Maps.newHashMap();
		//获取已经设定的属性
		Map<String, Object> resDefMap1 = this.txMsgNodeAttrBS.getTxMsgNodeAttrVOList(nodeId);
		Map<String, Object> resDefMap2 = null;

	    List<TxMsgNodeAttrVO> results1 = (List<TxMsgNodeAttrVO>)resDefMap1.get("Rows");
	    List<TxMsgNodeAttrVO> results2 = null;		
		//对于数据库节点属性，获取和节点表相对应且不在选中属性的字段
		if(!tabids.equals("")){
			resDefMap2 = this.txMsgNodeAttrBS.getTxMsgNodeAttrVOListByTabids(nodeId,tabids);
			results2 = (List<TxMsgNodeAttrVO>)resDefMap2.get("Rows");
		}
	    
	    
	    for(TxMsgNodeAttrVO txMsgNodeAttrVO:results1){
	    	txMsgNodeAttrVO.setIsSelected("1");
	    }
	    
	    if(results2!=null){
		    for(TxMsgNodeAttrVO txMsgNodeAttrVO:results2){
		    	txMsgNodeAttrVO.setIsSelected("0");
		    	txMsgNodeAttrVO.setAttrCode(convertColumn2Attr(txMsgNodeAttrVO.getAttrCode()));
		    	results1.add(txMsgNodeAttrVO);
		    }
	    }
		map.put("Rows", results1);
		map.put("Total", results1.size());	  
			
		return map;
	}
	

	@RequestMapping(value = "/nodeattr", method = RequestMethod.GET)
	
	public ModelAndView userattr(String nodeId) {
		ModelMap mm = new ModelMap();
		mm.put("nodeId", nodeId);
		
		return new ModelAndView("/ecif/transaction/txmsgtree-listattrs", mm);
	}
	
	@RequestMapping(value = "/nodeattrparent", method = RequestMethod.GET)
	
	public ModelAndView userattrparent(String nodeId) {
		
		TxMsgNode pNode =  txMsgNodeBS.getEntityById(new Long(nodeId));
		
		ModelMap mm = new ModelMap();
		mm.put("nodeId", pNode.getUpNodeId());
		
		return new ModelAndView("/ecif/transaction/txmsgtree-listattrs-parent", mm);
	}
	
	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping(value ="/nodeattrlist", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> nodeattrlist(Pager pager,String nodeId ) {

		SearchResult<TxMsgNodeAttr> searchResult = this.txMsgNodeAttrBS.getTxMsgNodeAttrListByTab(pager.getPageFirstIndex(),
				pager.getPagesize(), pager.getSortname(), pager.getSortorder(), pager.getSearchCondition(),nodeId);
		
		Map<String, Object> resDefMap = Maps.newHashMap();
		resDefMap.put("Rows", searchResult.getResult());
		resDefMap.put("Total", searchResult.getTotalCount());
		return resDefMap;
	}	
	
	/**
	 * 用于添加，或修改时的保存对象
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void create(TxMsgNodeAttr model) {
		this.txMsgNodeAttrBS.updateEntity(model);
	}

	/**
	 * 根据ID，加载数据
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public TxMsgNodeAttr show(@PathVariable("id") Long id) {
		TxMsgNodeAttr model = this.txMsgNodeAttrBS.getEntityById(id);
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
		return new ModelAndView("/ecif/transaction/txmsgnodeattr-edit", "id", new Long(id));
	}

	/**
	 * 执行添加前页面跳转
	 * 
	 * @return
	 * 		String	用于匹配结果页面
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView editNew(Long clientAuthId) {
		ModelMap mm = new ModelMap();
		mm.put("clientAuthId", clientAuthId);
		//return "/ecif/transaction/txmsgnodeattr-edit";
		
		return new ModelAndView("/ecif/transaction/txmsgnodeattr-edit", mm);
	}

	/**
	 * 执行删除操作，可进行指删除
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void destroy(@PathVariable("id") String id) {
		String[] ids = id.split(",");
		if (ids.length > 1) {
			this.txMsgNodeAttrBS.removeEntityByProperty("attrId", id);
		} else {
			this.txMsgNodeAttrBS.removeEntityById(new Long(id));
		}
	}


	@RequestMapping(value = "/rule", method = RequestMethod.GET)
	public String rule() {
		return "/ecif/transaction/txmsgnodeattr-listrules";
	}
	
	@RequestMapping(value = "/ruleconf", method = RequestMethod.GET)
	public String ruleconf() {
		return "/ecif/transaction/txmsgnodearrt-ruleconf";
	}
	
	/**
	 * 执行添加前页面跳转
	 * 
	 * @return
	 * 		String	用于匹配结果页面
	 */
	@RequestMapping(value = "/rulelist", method = RequestMethod.GET)
	public Map<String, Object> rulelist() {
//		ModelMap mm = new ModelMap();
//		mm.put("C01", "校验身份证");
//		mm.put("C02", "校验组织机构代码");
//		mm.put("C03", "校验邮件地址");
//		mm.put("C04", "校验手机号");
//		mm.put("C05", "校验电话号码");
//		mm.put("T01", "转换（身份证15转18位）");
//		mm.put("T02", "转换（身份证18转15位）");
//		mm.put("T03", "清洗（去空格）");
//		mm.put("T04", "清洗（全角转半角）");
//		mm.put("D01", "转码（正向转码）");
//		mm.put("R01", "转码（逆向转码）");
		
		List<Map> list = new ArrayList<Map>();
		
		ModelMap mm1 = new ModelMap(); mm1.put("ruleId","C01");mm1.put("ruleName","校验身份证");list.add(mm1);
		ModelMap mm2 = new ModelMap(); mm2.put("ruleId","C02");mm2.put("ruleName","校验组织机构代码");list.add(mm2);
		ModelMap mm3 = new ModelMap(); mm3.put("ruleId","C03");mm3.put("ruleName","校验邮件地址");list.add(mm3);
		ModelMap mm4 = new ModelMap(); mm4.put("ruleId","C04");mm4.put("ruleName","校验手机号");list.add(mm4);
		ModelMap mm5 = new ModelMap(); mm5.put("ruleId","C05");mm5.put("ruleName","校验电话号码");list.add(mm5);
		ModelMap mm6 = new ModelMap(); mm6.put("ruleId","T01");mm6.put("ruleName","转换（身份证15转18位）");list.add(mm6);
		ModelMap mm7 = new ModelMap(); mm7.put("ruleId","T02");mm7.put("ruleName","转换（身份证18转15位）");list.add(mm7);
		ModelMap mm8 = new ModelMap(); mm8.put("ruleId","T03");mm8.put("ruleName","清洗（去空格）");list.add(mm8);
		ModelMap mm9 = new ModelMap(); mm9.put("ruleId","T04");mm9.put("ruleName","清洗（全角转半角）");list.add(mm9);
		ModelMap mm10 = new ModelMap(); mm10.put("ruleId","D01");mm10.put("ruleName","转码（正向转码）");list.add(mm10);
		ModelMap mm11 = new ModelMap(); mm11.put("ruleId","R01");mm11.put("ruleName","转码（逆向转码）");list.add(mm11);
		
		Map<String, Object> resDefMap = Maps.newHashMap();
		resDefMap.put("Rows", list);
		resDefMap.put("Total", list.size());

		return resDefMap;
	}

	/**
	 * 获取combobox
	 */
	@ResponseBody
	@RequestMapping("getComBoBox.*")
	public List<Map<String,String>> getComBoBox() {
		return this.txMsgNodeAttrBS.getComBoBox();
	}
	
	
	private String convertColumn2Attr(String colname){
		
		if(colname!=null){
			String attr ="";
			String[] strs = colname.split("_");
			for(int i=0;i<strs.length;i++){
				if(i==0){
					attr = strs[i].toLowerCase();
				}else{
					String firstStr = strs[i].substring(0, 1).toUpperCase();
					String restStr  = strs[i].substring(1, strs[i].length()).toLowerCase();
					attr = attr + firstStr + restStr ;
				}
			}
			
			return attr;
		}
		
		return "";
		
	}
}
