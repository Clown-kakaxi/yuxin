package com.yuchengtech.bcrm.customer.action;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.model.OcrmFCiGroupMemberNew;
import com.yuchengtech.bcrm.customer.service.GroupCustMenberNewService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.core.QueryHelper;
/**
 * 
* @ClassName: GroupCustMenberListAction 
* @Description: 集团成员列表
* @author wangmk1 
 * @param <DefaultHttpHeaders>
* @date 2014-7-21 
*
 */
@SuppressWarnings("serial")
@Action("/groupCustBuy")
public class GroupCustBuyAction extends CommonAction {
	@Autowired
    @Qualifier("dsOracle")
    private DataSource ds;
	
	@Autowired
	private GroupCustMenberNewService groupCustMenberNewService;
	
	@Autowired
	public void init(){
		model= new OcrmFCiGroupMemberNew();
		setCommonService(groupCustMenberNewService);
	}
	
	@Override
	public void prepare(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String id = request.getParameter("id");
		StringBuffer sb=new StringBuffer("select * from OCRM_F_CI_BUYGROUP_INFO");
		setPrimaryKey(" GRP_NO desc ");
		SQL=sb.toString();
		datasource=ds;

	}
	/**
	 * 批量删除 
	 * @return
	 */

	
	public String saveGroupMember() throws ParseException{

	   	
	   	
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String id = request.getParameter("id");
    	String groupno = request.getParameter("groupno");
    	String grpname = request.getParameter("grpname");
    	String mainbrid = request.getParameter("mainbrid");
    	String mainbrname = request.getParameter("mainbrname");

    	OcrmFCiGroupMemberNew model= new OcrmFCiGroupMemberNew();
    	model.setGrpNo(id);
    	model.setCusId(groupno);
    	model.setCusName(grpname);
    	model.setMainBrId(mainbrid);
    	model.setMainBrName(mainbrname);
    	groupCustMenberNewService.save(model);
    	return "success";
    	
	   	
	   	

	}
}
