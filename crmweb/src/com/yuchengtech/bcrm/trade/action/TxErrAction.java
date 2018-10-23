package com.yuchengtech.bcrm.trade.action;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bcrm.trade.model.TxErr;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;



/***
 * 交易日志查询
 */
@Action("/txErrAction")
public class TxErrAction extends CommonAction {
	
    @Autowired
    @Qualifier("dsOracle")
    private DataSource ds;
        
    @Autowired
    public void init(){
    	model =new TxErr();
    }

	   
	   public void prepare() {
	    	StringBuffer sb=new StringBuffer("select L.* from  TX_ERR L"	);
	    	for(String key : this.getJson().keySet()){
	    		if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
	    			if("TX_LOG_ID".equals(key)){
	    				sb.append("and L.TX_LOG_ID='"+this.getJson().get(key)+"'");
	    			}
	    		}
	    	}
	    	SQL=sb.toString();
	    	datasource =ds;
	    	if (StringUtils.isNotEmpty((String) this.getJson().get("TX_CODE"))) {
				configCondition("L.TX_CODE", "=", "'"
						+ this.getJson().get("TX_CODE") + "'");
			}
	    	if (StringUtils.isNotEmpty((String) this.getJson().get("TX_CN_NAME"))) {
				configCondition("L.TX_CN_NAME", "like", "'%"
						+ this.getJson().get("TX_CN_NAME") + "%'");
			}
//	    	if (StringUtils.isNotEmpty((String) this.getJson().get("TX_DT"))) {
//				configCondition("L.TX_DT", "=", "'"
//						+ this.getJson().get("TX_DT") + "'");
//			}
	    	
	       this.configCondition("L.TX_DT", "=", "TX_DT", DataType.Date);//交易日期

	    	if (StringUtils.isNotEmpty((String) this.getJson().get("SRC_SYS_CD"))) {
				configCondition("L.SRC_SYS_CD", "=", "'"
						+ this.getJson().get("SRC_SYS_CD") + "'");
			}
	    	if (StringUtils.isNotEmpty((String) this.getJson().get("SRC_SYS_NM"))) {
				configCondition("L.SRC_SYS_NM", "like", "'%"
						+ this.getJson().get("SRC_SYS_NM") + "%'");
			}
	    }

	}