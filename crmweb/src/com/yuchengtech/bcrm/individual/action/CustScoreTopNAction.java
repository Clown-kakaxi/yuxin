package com.yuchengtech.bcrm.individual.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

/**
 *  @describtion: 客户TOP10(客户名称、客户积分)
 *
 * @author : luyy
 * @date : 2014-08-04
 */
@Action("/custSocreTop")
public class CustScoreTopNAction extends CommonAction {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			SQL = " select CUST_NAME,SCORE_TOTAL,COUNT_NUM from OCRM_F_SE_SCORE where CUST_ID in" +
					" (select cust_id from OCRM_F_CI_BELONG_CUSTMGR where MGR_ID='"+auth.getUserId()+"') order by COUNT_NUM desc";
		
			datasource = ds;
	}

}
