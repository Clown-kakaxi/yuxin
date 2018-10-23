package com.yuchengtech.bcrm.dynamicCrm.action;

import java.util.HashSet;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.dynamicCrm.model.CustomerAssetConf;
import com.yuchengtech.bcrm.dynamicCrm.service.CustomerAssetConfService;
import com.yuchengtech.bob.common.CommonAction;

/**
 * 资产配置Action
 * @author 亮
 *
 */
@ParentPackage("json-default")
@Action("/customerAssetConf")
@SuppressWarnings("serial")
public class CustomerAssetConfAction extends CommonAction {
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
	private CustomerAssetConfService service;
	
	@Autowired
	public void init(){
		model = new CustomerAssetConf();
		setCommonService(service);
	} 

	@Override
	public void prepare() {
		StringBuilder sb = new StringBuilder("");
		sb.append("select t.ID, t.RISK_TYPE_ID, t.RECOMMEND_PRO, a.RISK_TYPE_NAME,");
		sb.append("fun_product_codestonames(t.RECOMMEND_PRO) RECOMMEND_PRO_ZM ");
		sb.append("from OCRM_F_CI_CUST_ASSET_CONF t ");
		sb.append("left join OCRM_F_CI_CUST_ASSET_RISK_TYPE a on a.RISK_TYPE_ID = t.RISK_TYPE_ID ");
		sb.append("where 1=1 ");
		SQL = sb.toString();
		setPrimaryKey("t.ID");
		datasource = ds;
	}
	
	/**
	 * 保存资产配置信息
	 */
//	public DefaultHttpHeaders create() {
//		try {
//			CustomerAssetConf caf = (CustomerAssetConf)model;
//			service.saveCustomerAssetConf(caf);
//		} catch (BizException e) {
//			throw e;
//		}catch(Exception e){
//    		e.printStackTrace();
//    		throw new BizException(1, 2, "1002", e.getMessage());
//    	}
//		return new DefaultHttpHeaders("success").setLocationId(model);
//	}
	
	/**
	 * 保存风险等级配置推荐产品方案
	 */
	public String save(){
		ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        CustomerAssetConf caf = (CustomerAssetConf)model;
        String id = request.getParameter("id");
        String riskTypeId = request.getParameter("riskTypeId");
        String recommendPro = request.getParameter("recommendPro");
        String addGrantsStr = request.getParameter("addGrantsStr");
        String delGrantsStr = request.getParameter("delGrantsStr");
        
        if(id == null || id.equals("")){//新增记录
        	caf.setId(null);
        	caf.setRiskTypeId(riskTypeId);
        	caf.setRecommendPro(addGrantsStr);
        }else{//修改
        	String[] recommendPros = recommendPro.split(",");
        	//将recommendPro加入HashSet中
        	HashSet <String> recommendProSet = new HashSet<String>();
        	for(int i=0;i<recommendPros.length;i++){
        		recommendProSet.add(recommendPros[i]);
        	}
        	//与delGrantsStr对比，删除相同项
        	String[] delGrants = delGrantsStr.split(",");
        	for(int i=0;i<delGrants.length;i++){
        		if(recommendProSet.contains(delGrants[i])){
        			recommendProSet.remove(delGrants[i]);
        		}
        	}
        	//与addGrantsStr对比，加入HashSet
        	String[] addGrants = addGrantsStr.split(",");
        	for(int i=0;i<addGrants.length;i++){
        		if(!recommendProSet.contains(addGrants[i])){
        			recommendProSet.add(addGrants[i]);
        		}
        	}
        	//更新
        	String recommendNew = null;
        	Iterator<String> it = recommendProSet.iterator();
        	while(it.hasNext()){
        		if(recommendNew == null || recommendNew.equals("")){
        			recommendNew = it.next();
        		}else{
        			recommendNew += "," + it.next();
        		}
        	}
        	caf.setRiskTypeId(riskTypeId);
        	caf.setRecommendPro(recommendNew);
        	caf.setId(id);
        }
        service.saveCustomerAssetConf(caf);
        
		return "success";
	}
	
	/**
	 * 删除资产配置
	 * @param id
	 * @return 
	 */
	public String destroy(){
    	ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        String id = request.getParameter("id");
		service.destroy(id);
		return "success";
	}

}
