package com.yuchengtech.bcrm.trade.action;


import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bcrm.trade.model.TxErr;
import com.yuchengtech.bob.common.CommonAction;



/***
 * 交易异常  交易代码下拉框查询
 */
@Action("/txErrCodeAction")
public class TxErrCodeAction extends CommonAction {
	
    @Autowired
    @Qualifier("dsOracle")
    private DataSource ds;
        
    @Autowired
    public void init(){
    	model =new TxErr();
    }

    
    public void prepare() {
    	StringBuffer sb=new StringBuffer(" select distinct L.TX_CODE from  TX_ERR L"	);
    	for(String key : this.getJson().keySet()){
    		if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
    			if("TX_ERR_ID".equals(key)){
    				sb.append("and L.TX_ERR_ID='"+this.getJson().get(key)+"'");
    			}
    		}
    	}
    	SQL=sb.toString();
    	datasource =ds;
    }
}