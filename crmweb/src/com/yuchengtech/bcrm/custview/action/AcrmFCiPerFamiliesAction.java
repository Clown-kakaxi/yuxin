package com.yuchengtech.bcrm.custview.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custview.model.AcrmFCiPerFamilies;
import com.yuchengtech.bcrm.custview.service.AcrmFCiPerFamiliesService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;
/**
 * 成员 Action
 * @author YOYOGI
 * 2014-8-15
 * 
 * 修改人：chixinli
 * 修改内容：查询语句sql
 */
@Action("/acrmFCiPerFamilies")
public class AcrmFCiPerFamiliesAction extends CommonAction {
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
	private AcrmFCiPerFamiliesService acrmFCiPerFamiliesService;
	
	AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
	@Autowired
	public void init(){
		model = new AcrmFCiPerFamilies();
		setCommonService(acrmFCiPerFamiliesService);
	}
	
	/**
	 * 数据查询
	 */
	public void prepare(){
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId = request.getParameter("custId");
//		StringBuffer sb = new StringBuffer("SELECT * FROM ACRM_F_CI_PER_FAMILIES WHERE CUST_ID = '"+ custId +"'");
		StringBuffer sb = new StringBuffer("SELECT ACF.MXTID,ACF.CUST_ID,ACF.MEMBERNAME,ACF.FAMILYRELA,ACF.MEMBERCRET_TYP,ACF.MEMBERCRET_NO,REPLACE(ACF.TEL,'/','-') TEL,REPLACE(ACF.MOBILE,'/','-') MOBILE,ACF.EMAIL,ACF.BIRTHDAY,ACF.COMPANY,ACF.MEMBER_ID,ACF.MANAGER_ID,ACF.REMARK,ACF.LAST_UPDATE_SYS,ACF.LAST_UPDATE_USER,to_char(ACF.LAST_UPDATE_TM,'yyyy-MM-dd hh24:mi:ss') LAST_UPDATE_TM,ACF.TX_SEQ_NO FROM ACRM_F_CI_PER_FAMILIES ACF WHERE CUST_ID = '"+ custId +"'");
		SQL = sb.toString();
		datasource = ds;
	}
	
	/**
     * 删除
     */
    public String batchDestroy(){
        ActionContext ctx = ActionContext.getContext();
        request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
        String id = request.getParameter("id");
        acrmFCiPerFamiliesService.batchRemove(id);
        return "success";
    }
}
