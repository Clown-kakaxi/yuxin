package com.yuchengtech.bcrm.custmanager.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custmanager.model.AcrmFCrDeclarantInfo;
import com.yuchengtech.bcrm.custmanager.service.RelateInfoTempService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.JPAAnnotationMetadataUtil;
import com.yuchengtech.bob.vo.AuthUser;
/**
 * 关联方的新增修改
 */
@Action("/acrmfcrdeclarantinfoTemp")
public class acrmfcrdeclarantinfoTempAction extends CommonAction {
private static final long serialVersionUID = 1L;
    
    @Autowired
    @Qualifier("dsOracle")
    private DataSource ds;
    
    @Autowired
    private RelateInfoTempService relateinfotempservice;
    
    @Autowired
    public void init(){
        model = new AcrmFCrDeclarantInfo();
        setCommonService(relateinfotempservice);
    }
    public void prepare(){
    	 /**
         *  主申报人信息查询
         */
	  ActionContext ctx = ActionContext.getContext();
	  HttpServletRequest request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
      String mainId= request.getParameter("MAIN_ID");
      StringBuffer sb1 =new StringBuffer ("select t.* from ACRM_F_CR_DECLARANT_INFO t WHERE 1=1   "	);
  	  if(mainId != null&&mainId!=""){
  		sb1.append(" and t.main_Id = '"+mainId+"'");
  	  }
      for(String key:this.getJson().keySet()){
          if(null!=this.getJson().get(key) && !"".equals(this.getJson().get(key))){
          	if("DECLARANT_NAME".equals(key)){
                 sb1.append(" AND t.DECLARANT_NAME LIKE '%"+this.getJson().get(key)+"%'");
              }
          	if("DECLARANT_ATTR".equals(key)){	
                  sb1.append(" AND t.DECLARANT_ATTR = '"+this.getJson().get(key)+"'");
              }
          }
      }
  	  SQL = sb1.toString();
  	  datasource =ds;
  	
    }
    public Object updateCancelStat(){
        ActionContext ctx = ActionContext.getContext();
        request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
        String id = request.getParameter("MAIN_ID");
        relateinfotempservice.updcancelStat(id);
        return "success";
    }				
    public DefaultHttpHeaders saveData(){	
    	 ActionContext ctx = ActionContext.getContext();
         request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
         String  relateidenty =(request.getParameter("identType2")); 
         String  relateattribute =(request.getParameter("declarantAttr")).toString();
         if(relateattribute.equals("21")||relateattribute.equals("22")){//若属性为法人
     		if(relateidenty!=null){
     			((AcrmFCrDeclarantInfo)model).setIdentType(relateidenty);
     		}
    		}
        long l= ((AcrmFCrDeclarantInfo)model).getMainId(); 
   	if(((AcrmFCrDeclarantInfo)model).getMainId()==0){//执行新增申报人
   	
   		
   		relateinfotempservice.save(model);
   		AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
   				.getAuthentication().getPrincipal();
      	JPAAnnotationMetadataUtil metadataUtil = new JPAAnnotationMetadataUtil();
     	String id = metadataUtil.getId((AcrmFCrDeclarantInfo)model).toString();
    		auth.setPid(id);
	    	return new DefaultHttpHeaders("success");
    	}else{ //更新申报人信息
    		relateinfotempservice.updataData(((AcrmFCrDeclarantInfo)model));
    		return new DefaultHttpHeaders("success");
   	}
	}
}
