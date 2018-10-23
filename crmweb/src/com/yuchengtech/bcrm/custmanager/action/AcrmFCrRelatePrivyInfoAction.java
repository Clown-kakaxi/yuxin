package com.yuchengtech.bcrm.custmanager.action;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custmanager.model.AcrmFCrRelatePrivyInfo;
import com.yuchengtech.bcrm.custmanager.service.AcrmFCrRelatePrivyInfoService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * @describtion: 关联人的查询
 * 
 * @author : dongyi
 * @date : 2014-07-17 
 */
@Action("/AcrmFCrRelatePrivyInfo")
public class AcrmFCrRelatePrivyInfoAction extends CommonAction{

    private static final long serialVersionUID = 1L;
    
    @Autowired
    @Qualifier("dsOracle")
    private DataSource ds;
    
    @Autowired
    private AcrmFCrRelatePrivyInfoService acrmfcrrelateprivyinfoservice;
    
    @Autowired
    public void init(){
        model = new AcrmFCrRelatePrivyInfo();
        setCommonService(acrmfcrrelateprivyinfoservice);
    }
    
    public void prepare(){
        AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String KeyId= request.getParameter("MAIN_ID");
        StringBuffer sb1 = new StringBuffer("SELECT distinct t.* FROM ACRM_F_CR_RELATE_PRIVY_INFO t WHERE 1=1 AND t.CANCEL_STATE ='1' ");
        if(KeyId!=null){
        	sb1.append(" AND t.MAIN_ID = '"+KeyId+"'");
        	
        }
        for(String key:this.getJson().keySet()){
            if(null!=this.getJson().get(key) && !"".equals(this.getJson().get(key))){
            	if("PRIVY_NAME".equals(key)){
                   sb1.append(" AND t.PRIVY_NAME LIKE '%"+this.getJson().get(key)+"%'");
                }
            	if("MAIN_ID".equals(key)){	
                    sb1.append(" AND t.MAIN_ID = '"+this.getJson().get(key)+"'");
                }
            }
        }
        SQL = sb1.toString();
        datasource =ds;
    }
    /**
     *  修改关联人的关联方注销状态
     */
    public Object updateCancelStat(){
        ActionContext ctx = ActionContext.getContext();
        request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
        String id = request.getParameter("RELATE_ID");
        acrmfcrrelateprivyinfoservice.updcancelStat(id);
        return "success";
        
        
    }
    /**
     *  保存申报人创建时的关联人的关联方
     */
//    public Object saveData(){
//        ActionContext ctx = ActionContext.getContext();
//        request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
//        acrmfcrrelateprivyinfoservice.saveDataservice();
//        return "success";
//    }
    /**
     *  查询关联人的关联方信息
     * @throws SQLException 
     */
    public String queryRelatePrivyInfo() throws SQLException{
    	ActionContext ctx = ActionContext.getContext();
        request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
        
    	return "success";
    }
    
}

