package com.yuchengtech.bcrm.sales.marketTask.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.sales.marketTask.model.OcrmFMmTarget;
import com.yuchengtech.bcrm.sales.marketTask.service.MarketTargetService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * @describtion: 营销指标信息
 * 
 * @author : helin
 * @date : 2014-07-03 10:21:57
 */
@Action("/marketTarget")
public class MarketTargetAction extends CommonAction{

    private static final long serialVersionUID = 1L;
    
    @Autowired
    @Qualifier("dsOracle")
    private DataSource ds;
    
    @Autowired
    private MarketTargetService marketTargetService;
    
    @Autowired
    public void init(){
        model = new OcrmFMmTarget();
        setCommonService(marketTargetService);
    }
    
    public void prepare(){
        AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        StringBuffer sb = new StringBuffer("SELECT t.*,A.USER_NAME AS UPDATE_USER_NAME FROM OCRM_F_MM_TARGET t LEFT JOIN ADMIN_AUTH_ACCOUNT A ON A.ACCOUNT_NAME = T.UPDATE_USER WHERE 1=1 ");
        for(String key:this.getJson().keySet()){
            if(null!=this.getJson().get(key) && !"".equals(this.getJson().get(key))){
                if("TARGET_CODE".equals(key)){
                    sb.append(" AND t.TARGET_CODE = '"+this.getJson().get(key)+"'");
                }
            	if("TARGET_NAME".equals(key)){
                    sb.append(" AND t.TARGET_NAME LIKE '%"+this.getJson().get(key)+"%'");
                }
            	if("UPDATE_USER_NAME".equals(key)){
                    sb.append(" AND a.USER_NAME LIKE '%"+this.getJson().get(key)+"%'");
                }
            	if("TARGET_TYPE".equals(key)){	//指标选择器需要用到
                    sb.append(" AND t.TARGET_TYPE = '"+this.getJson().get(key)+"'");
                }
            }
        }
        SQL = sb.toString();
        setPrimaryKey("t.TARGET_TYPE,t.TARGET_CODE");
        addOracleLookup("TARGET_TYPE", "TARGET_TYPE");//指标选择器需要用到
        datasource =ds;
    }
    
    /**
     * batch delete 营销指标
     */
    public String batchDestroy(){
        ActionContext ctx = ActionContext.getContext();
        request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
        String ids = request.getParameter("idStr");
        marketTargetService.batchRemove(ids);
        return "success";
    }
}
