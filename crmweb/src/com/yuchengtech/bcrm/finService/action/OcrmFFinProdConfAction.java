package com.yuchengtech.bcrm.finService.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.finService.model.OcrmFFinProdConf;
import com.yuchengtech.bcrm.finService.service.OcrmFFinProdConfService;
import com.yuchengtech.bob.common.CommonAction;

@Action("/ocrmFFinProdConf")
public class OcrmFFinProdConfAction extends CommonAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
	private OcrmFFinProdConfService ocrmFFinProdConfService;
	
	@Autowired
	public void init(){
		model = new OcrmFFinProdConf();
		setCommonService(ocrmFFinProdConfService);
	}
	
	@Override
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		
		String targetId = request.getParameter("targetId");
		StringBuffer sb = new StringBuffer("select t.*,i.prod_name from OCRM_F_FIN_PROD_CONF t left join ocrm_f_pd_prod_info i on i.product_id = t.prod_id where target_id = '"+targetId+"'");
		
		SQL = sb.toString();
		datasource = ds;
	}
	
	/**
     * batch delete 
     */
    public String batchDestroy(){
        ActionContext ctx = ActionContext.getContext();
        request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
        String ids = request.getParameter("ids");
        ocrmFFinProdConfService.batchRemove(ids);
        return "success";
    }
	
}
