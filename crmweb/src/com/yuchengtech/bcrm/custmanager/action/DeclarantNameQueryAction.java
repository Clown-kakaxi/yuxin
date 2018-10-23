package com.yuchengtech.bcrm.custmanager.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bcrm.custmanager.model.AcrmFCrDeclarantInfo;
import com.yuchengtech.bcrm.custmanager.service.DeclarantNameQueryService;
import com.yuchengtech.bob.common.CommonAction;
/**
 * @describtion: 申报人信息
 * 
 * @author : dongyi
 * @date : 2014-07-21
 */
@Action("/DeclarantNameQuery")
public class DeclarantNameQueryAction extends CommonAction{

	private static final long serialVersionUID = 1L;
    
    @Autowired
    @Qualifier("dsOracle")
    private DataSource ds;
    @Autowired
    private DeclarantNameQueryService declarantnamequeryService;
    
    @Autowired
    public void init(){
        model = new AcrmFCrDeclarantInfo();
        setCommonService(declarantnamequeryService);
    }
    public void prepare(){
    	 /**
         *  申报人信息查询
         */
        
  	  StringBuffer sb2 = new StringBuffer("SELECT distinct t.* from ACRM_F_CR_DECLARANT_INFO t where 1=1 and t.CANCEL_STATE='1'");//在申报人放大镜查询的时候需要查询不重复的申报人
  	  for(String key:this.getJson().keySet()){
  		if(null!=this.getJson().get(key) && !"".equals(this.getJson().get(key))){
  		  if("DECLARANT_NAME".equals(key)){
                sb2.append(" AND t.DECLARANT_NAME LIKE '%"+this.getJson().get(key)+"%'");
            }
  		  if("IDENT_NO".equals(key)){
                sb2.append(" AND t.IDENT_NO = '"+this.getJson().get(key)+"'");
            }
  		}
  	  }
  	  SQL = sb2.toString();
  	  datasource =ds;
  	
    }
}
