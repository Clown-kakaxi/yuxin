package com.yuchengtech.bcrm.sales.marketTask.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.sales.marketTask.model.OcrmFMmTaskTarget;
import com.yuchengtech.bcrm.sales.marketTask.service.MarketTaskTargetService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * @describtion: 营销任务指标明细
 * 
 * @author : geyu
 * @date : 2014-07-03 10:21:33
 */
@Action("/marketTaskTarget")
public class MarketTaskTargetAction extends CommonAction{

    private static final long serialVersionUID = 1L;
    
    @Autowired
    @Qualifier("dsOracle")
    private DataSource ds;
    
    @Autowired
    private MarketTaskTargetService marketTaskTargetService;
    
    @Autowired
    public void init(){
        model = new OcrmFMmTaskTarget();
        setCommonService(marketTaskTargetService);
    }
    
    public void prepare(){
        AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        StringBuffer sb = new StringBuffer("select t.*, t1.target_name, t1.target_cycle from OCRM_F_MM_TASK_TARGET t left join OCRM_F_MM_TARGET t1 on t.target_code = t1.target_code  WHERE 1=1 ");
        for(String key:this.getJson().keySet()){
            if(null!=this.getJson().get(key) && !"".equals(this.getJson().get(key))){
                if("TASK_ID".equals(key)){
                    sb.append(" AND t.TASK_ID = '"+this.getJson().get(key)+"'");
                }
            }
        }
        SQL = sb.toString();
        datasource =ds;
    }
    
    /**
     * batch delete marketTaskTarget
     */
    public String batchDestroy(){
        ActionContext ctx = ActionContext.getContext();
        request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
        String ids = request.getParameter("ids");
        marketTaskTargetService.batchRemove(ids);
        return "success";
    }
}
