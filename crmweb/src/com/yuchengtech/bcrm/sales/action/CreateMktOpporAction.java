package com.yuchengtech.bcrm.sales.action;

import java.util.ListIterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.sales.model.OcrmFMmMktBusiOppor;
import com.yuchengtech.bcrm.sales.service.CreateMktOpporService;
import com.yuchengtech.bob.common.CommonAction;
/**
 * 
* @ClassName: CreateMktOpporAction 
* @Description: 创建商机组件保存商机执行action
* @author wangmk1 
* @date 2014-10-11 下午3:18:41 
*
 */

@SuppressWarnings("serial")
@Action("/createMktOppor")
public class CreateMktOpporAction extends CommonAction {
	
	@Autowired
	private CreateMktOpporService createMktOpporService;
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds ;
	
	@Autowired
	public void init(){
		model =new OcrmFMmMktBusiOppor();
		setCommonService(createMktOpporService);
	}
	
	public void save(){
		ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        String basejson= request.getParameter("basejson");
        String custjson= request.getParameter("custjson");
        String prodjson= request.getParameter("prodjson");
        
        JSONObject baseJson = JSONObject.fromObject(basejson);
        JSONArray  custJson =JSONArray.fromObject(custjson);
        JSONArray  prodJson =JSONArray.fromObject(prodjson);
        //客户信息和产品信息做笛卡儿积，拼接成商机
        ListIterator<?> iter1 = custJson.listIterator();
    	while(iter1.hasNext()) {
    		Map map1 = (JSONObject) iter1.next();
    		ListIterator<?> iter2 = prodJson.listIterator();
		  	while(iter2.hasNext()) {
		  		JSONObject temp = new JSONObject();
		  		temp.putAll(baseJson);
		  		Map map2 = (JSONObject) iter2.next();
		  		temp.putAll(map1);
		  		temp.putAll(map2);
	    		String s1=(String) temp.get("opporName");
	    		String s2=(String) temp.get("custId");
	    		String s3=(String) temp.get("prodId");
	    		String s=s1+"_"+s2+"_"+s3;
	    		//命名商机
	    		temp.put("opporName", s);
	    		//设置商机状态为（0-暂存）
	    		temp.put("opporStat","0");
	    		createMktOpporService.save(temp);
			}
		}
	}
}
