package com.yuchengtech.bcrm.customer.action;


import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.model.OcrmFCiAttentionCustInfo;
import com.yuchengtech.bcrm.customer.service.CustConcernService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;

@SuppressWarnings("serial")
@Action("/custConcernDel")
public class CustConcernDelAction  extends CommonAction{
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
    @Autowired
    private CustConcernService custConcernService ;
    @Autowired
	public void init(){
	  	model = new OcrmFCiAttentionCustInfo(); 
		setCommonService(custConcernService);
		//新增修改删除记录是否记录日志,默认为false，不记录日志
		needLog=true;
	}
    
    //信息查询
    public void prepare() {
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		StringBuilder sb = new StringBuilder();
		sb.append("select * from (");
		sb.append(" select I.*,C.CUST_NAME,C.CUST_TYPE,C.CUST_STAT,C.IDENT_TYPE,C.IDENT_NO,a.IDENT_EXPIRED_DATE,b.IDENT_TYPE IDENT_TYPE2,b.IDENT_NO IDENT_NO2,b.IDENT_EXPIRED_DATE IDENT_EXPIRED_DATE2,g.CUST_GRADE as FXQ_RISK_LEVEL from ACRM_F_CI_CUSTOMER C left join OCRM_F_CI_ATTENTION_CUST_INFO I on I.CUST_ID = C.CUST_ID ");
		sb.append(" left join ACRM_F_CI_CUST_IDENTIFIER a on a.cust_id = c.cust_id and a.ident_type = c.ident_type");
		sb.append(" left join (select t.cust_id, t.IDENT_TYPE, t.IDENT_NO, t.IDENT_EXPIRED_DATE from acrm_f_ci_cust_identifier t");
		sb.append("             where (IS_OPEN_ACC_IDENT <> 'Y' OR IS_OPEN_ACC_IDENT IS NULL) AND ident_type NOT IN ('V', '15X', 'W', 'Y')) b");
		sb.append(" on c.cust_id = b.cust_id");
		sb.append(" left join ACRM_F_CI_GRADE g on g.cust_id=c.cust_id and g.CUST_GRADE_TYPE = '01' where i.cust_id is not null ");
		sb.append(" union ");
		sb.append(" select I.*,C.CUS_NAME,C.CUST_TYPE,C.CUST_STAT,C.CERT_TYPE as IDENT_TYPE,C.cert_code as IDENT_NO,a.IDENT_EXPIRED_DATE,b.IDENT_TYPE IDENT_TYPE2,b.IDENT_NO IDENT_NO2,b.IDENT_EXPIRED_DATE IDENT_EXPIRED_DATE2,g.CUST_GRADE as FXQ_RISK_LEVEL from ACRM_F_CI_POT_CUS_COM C left join OCRM_F_CI_ATTENTION_CUST_INFO I on I.CUST_ID = C.CUS_ID ");
		sb.append(" left join ACRM_F_CI_CUST_IDENTIFIER a on a.cust_id = c.CUS_ID and a.ident_type = c.CERT_TYPE");
		sb.append(" left join (select t.cust_id, t.IDENT_TYPE, t.IDENT_NO, t.IDENT_EXPIRED_DATE from acrm_f_ci_cust_identifier t");
		sb.append("             where (IS_OPEN_ACC_IDENT <> 'Y' OR IS_OPEN_ACC_IDENT IS NULL) AND ident_type NOT IN ('V', '15X', 'W', 'Y')) b");
		sb.append(" on b.cust_id = c.CUS_ID");
		sb.append(" left join ACRM_F_CI_GRADE g on g.cust_id=C.CUS_ID and g.CUST_GRADE_TYPE = '01'  where i.cust_id is not null ");
		sb.append(") I where 1=1 ");
		SQL=sb.toString();
		datasource = ds;
		setPrimaryKey("I.CUST_ID  ");
		configCondition("I.CUST_ID", "=", "CUST_ID",DataType.String);
		configCondition("CUST_NAME", "like", "CUST_NAME",DataType.String);
    }
    
//    //（自定义）批量删除
//    public String batchDestroy(){
//	   	ActionContext ctx = ActionContext.getContext();
//        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
//		String id = request.getParameter("id");
//		String jql="delete from OcrmFCiAttentionCustInfo i where i.id in ("+id+")";
//		Map<String,Object> values=new HashMap<String,Object>();
//		custConcernService.batchUpdateByName(jql, values);
//		addActionMessage("batch removed successfully");
//        return "success";
//    }
    
  //删除
    public DefaultHttpHeaders batchDel(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	custConcernService.batchDel(request);
		return new DefaultHttpHeaders("success");
    }

  
}