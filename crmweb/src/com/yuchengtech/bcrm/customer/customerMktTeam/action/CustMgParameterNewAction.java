package com.yuchengtech.bcrm.customer.customerMktTeam.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.customerMktTeam.model.AcrmFCmContriPara;
import com.yuchengtech.bcrm.customer.customerMktTeam.service.CustMgParameterNewService;
import com.yuchengtech.bob.common.CommonAction;

/**
 * @description:客户经理贡献度业绩参数设置
 * @author xiebz
 * @data 2014-07-04
 */
@Action("/custMgParameterNew")
public class CustMgParameterNewAction extends CommonAction{
	private static final long serialVersionUID = -1307317536382455940L;

	@Autowired
    @Qualifier("dsOracle")
    private DataSource ds;
    
    @Autowired
    private CustMgParameterNewService service;
    
    @Autowired
    public void init(){
        model = new AcrmFCmContriPara();
        setCommonService(service);
    }
	
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		StringBuilder sb = new StringBuilder("SELECT T.* FROM ACRM_F_CM_CONTRI_PARA T where 1=1");
		for (String key : this.getJson().keySet()) {// 查询条件判断
			if (null != this.getJson().get(key)&& !this.getJson().get(key).equals("")) {
				if (key.equals("ORG_NAME")) {
					sb.append(" AND T.ORG_NAME like '%" + this.getJson().get(key)+ "%'");
				} else if (key.equals("USER_NAME")) {
					sb.append(" AND T.USER_NAME like '%" + this.getJson().get(key)+ "%'");
				}
			}
		}
		setPrimaryKey("t.TIME desc");
	    SQL = sb.toString();
	    datasource = ds;
	}
	
	 /**
     * 删除 客户经理  -业绩参数设置
     */
    public String batchDestroy(){
        ActionContext ctx = ActionContext.getContext();
        request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
        String custmId = request.getParameter("custmId");
        String[] idString = custmId.split(",");
        StringBuffer sb = new StringBuffer();
        int i = 0;
        for(String id:idString){
        	 i++;
	         if(i == idString.length){
	        	 sb.append("'").append(id).append("'");
	         }else {
	        	 sb.append("'").append(id).append("',");
			 }
        }
        service.batchRemove(sb.toString());
        return "success";
    }

}
