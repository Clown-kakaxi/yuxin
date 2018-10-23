package com.yuchengtech.bcrm.custview.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.model.AcrmFCiCreContract;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * @describtion: 授信子额度查询
 * 
 * @author : dongyi
 * @date : 2014-07-27 
 */
@Action("/AcrmFCiCreContractNew")
public class AcrmFCiCreContractNewAction extends CommonAction{
	

    private static final long serialVersionUID = 1L;
    
    @Autowired
    @Qualifier("dsOracle")
    private DataSource ds;
    
    @Autowired
    public void init(){
        model = new AcrmFCiCreContract();
    }
    
    public void prepare(){
        AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ActionContext ctx = ActionContext.getContext();
    	HttpServletRequest request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
    	String ids =request.getParameter("CustId");
    	StringBuffer sb1 = new StringBuffer("select decode(t1.LIMIT_NO,'0','总额度',t1.LIMIT_NO) as LIMIT_NO,t1.PRODUCT,t1.CURRENCY,t1.CRD_AMT,t1.ENABLE_AMT,t1.USE_AMT,t1.USE_RATE1, "+
					                        "t1.USE_RATE2,t2.FLAG1,t1.CUST_ID,t1.SUB_LIMIT_ID,t1.BAK1,t1.BAK2,t1.BAK3,t1.ID,t2.SERNO, "+
					                          "t1.LN_CUST_ID from ACRM_F_CI_CRE_CONTRACT t1 left join ( "+
												"select case when (select sum(crd_amt) from ACRM_F_CI_CRE_CONTRACT t where length(t.limit_no)=1 AND t.limit_no<>'0' "+
												"and cust_id='"+ids+"' "+
												 "and serno not in (select serno from ACRM_F_CI_CRE_CONTRACT  t "+
												"where t.PRODUCT1  in ('1001','1002','1003', '1004','1005') and cust_id='"+ids+"')) "+
												">(select sum(crd_amt) from ACRM_F_CI_CRE_CONTRACT t where t.limit_no='0' "+
												"and cust_id='"+ids+"' and serno not in (select serno from ACRM_F_CI_CRE_CONTRACT  t "+
												"where t.PRODUCT1  in ('1001','1002','1003', '1004','1005') and cust_id='"+ids+"'))  "+
												"then '1' else '0' end as FLAG1,a.serno,a.limit_no  "+
												 "from ACRM_F_CI_CRE_CONTRACT A where cust_id='"+ids+"' and LIMIT_NO='0' "+
												  "and  "+
												   "serno not in (select serno from ACRM_F_CI_CRE_CONTRACT  t "+
												"where t.PRODUCT1  in ('1001','1002','1003', '1004','1005') and cust_id='"+ids+"')) t2 "+
												"on t1.serno=t2.serno and t1.limit_no = t2.limit_no "+
												"where cust_id = '"+ids+"'   order by t1.SERNO,t1.LIMIT_NO");
        for(String key:this.getJson().keySet()){
        }
        SQL = sb1.toString();
        datasource =ds;
    }
}
