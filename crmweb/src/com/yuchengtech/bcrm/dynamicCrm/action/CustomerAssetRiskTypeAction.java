package com.yuchengtech.bcrm.dynamicCrm.action;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.dynamicCrm.model.CustomerAssetRiskType;
import com.yuchengtech.bcrm.dynamicCrm.service.CustomerAssetRiskTypeService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.core.QueryHelper;

/**
 * 资产配置区间Action
 * @author 亮
 *
 */
@ParentPackage("json-default")
@Action("/customerAssetRiskType")
@SuppressWarnings("serial")
public class CustomerAssetRiskTypeAction extends CommonAction {
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
	private CustomerAssetRiskTypeService service;
	
	@Autowired
	public void init(){
		model = new CustomerAssetRiskTypeAction();
		setCommonService(service);
	}

	@Override
	public void prepare() {
		StringBuilder sb = new StringBuilder("");
		sb.append(" select t.RISK_TYPE_ID, t.RISK_TYPE_NAME ");
		sb.append(" from OCRM_F_CI_CUST_ASSET_RISK_TYPE t ");
		sb.append(" where 1=1");
		SQL = sb.toString();
		setPrimaryKey("t.RISK_TYPE_ID");
		datasource = ds;
	}

	/**
	 * 保存资产配置区间信息
	 */
	public void save(){
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String riskTypeId = request.getParameter("riskTypeId");
		String riskTypeName = request.getParameter("riskTypeName");
		CustomerAssetRiskType cas = new CustomerAssetRiskType();
		if(riskTypeId != null && riskTypeId.equals("")){
			cas.setRiskTypeId(null);		
		}else{
			cas.setRiskTypeId(riskTypeId);
		}
		cas.setRiskTypeName(riskTypeName);
		service.save(cas);
	}
	
	/**
	 * 删除客户属性
	 * @param id
	 * @return 
	 */
	public String delete(){
    	ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        String riskTypeId = request.getParameter("riskTypeId");
        String riskTypeName = request.getParameter("riskTypeName");
		service.delete(riskTypeId, riskTypeName);
		return "success";
	}
	
	//查询产品类别字典
    public String searchLookup() {
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		try {
			StringBuilder sb = new StringBuilder("");
			sb.append("select RISK_TYPE_NAME as value, RISK_TYPE_ID as key from OCRM_F_CI_CUST_ASSET_RISK_TYPE order by RISK_TYPE_ID ");			
  			QueryHelper query =  new QueryHelper(sb.toString(), ds.getConnection());

			Map<String, Object> result = query.getJSON();
			if (this.json != null){
				this.json.clear();
			}else{
				this.json = new HashMap<String, Object>();
			}
			this.json.put("json", result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "success";
	}
}
