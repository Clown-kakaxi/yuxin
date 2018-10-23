package com.yuchengtech.bcrm.customer.action;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.yuchengtech.bob.common.DataType;
import com.yuchengtech.bob.core.QueryHelper;
/**
 * 
* @ClassName: GroupCustMenberListAction 
* @Description: 集团成员列表
* @author wangmk1 
* @date 2014-7-21 
*
 */
@Action("/groupCustMenberListNew1")
public class GroupCustMenberListNew1Action extends CommonAction {
	@Autowired
    @Qualifier("dsOracle")
    private DataSource ds;
	
	@Autowired
	private GroupCustMenberNewService groupCustMenberNewService;
	
	@Autowired
	public void init(){
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
	}
	
	@Override
	public void prepare(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String id = request.getParameter("id");
		StringBuffer sb=new StringBuffer("select * from OCRM_F_CI_GROUP_MEMBER_NEW where CUS_ID not in (select GRP_NO from OCRM_F_CI_BUYGROUP_INFO) and GRP_NO='"+id+"'");
		setPrimaryKey(" MEMBER_ID desc ");
		SQL=sb.toString();
		datasource=ds;

	}
	
	public String selectGroupMember(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String id = request.getParameter("id");
		StringBuffer sb=new StringBuffer("select * from OCRM_F_CI_GROUP_MEMBER_NEW where CUS_ID not in (select GRP_NO from OCRM_F_CI_BUYGROUP_INFO) and GRP_NO='"+id+"'");
		setPrimaryKey(" MEMBER_ID desc ");
		SQL=sb.toString();
		datasource=ds;
		return "success";
	}
	
	public void saveGroupMember(){
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
    //	groupCustMenberService.save(model);
	}
	/**
	 * 批量删除 
	 * @return
	 */
	public String batchDestory(){

		
//        ActionContext ctx = ActionContext.getContext();
//        request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
//        String ids = request.getParameter("idStr");
//        String grpno = request.getParameter("grpno");
////        String[] id=ids.split(",");
////        for(int i=0;i<id.length;i++){
////        	groupCustMenberNewService.batchRemove(id[i],grpno);
////        }
////        System.out.println("...............................................................................................");
//		StringBuffer sb=new StringBuffer(" delete from OCRM_F_CI_GROUP_MEMBER_NEW where cus_id in ("+ids+") and GRP_NO='"+grpno+"'");
//		SQL=sb.toString();
//		datasource=ds;
//        
//        return "success";
        
        
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
        String ids = request.getParameter("idStr");
        String grpno = request.getParameter("grpno");
        String jql="delete from OcrmFCiGroupMemberNew o where o.cusId in ("+ ids +") and o.grpNo='"+grpno+"'";
		Map<String, Object> values = new HashMap<String, Object>();
		groupCustMenberNewService.batchUpdateByName(jql, values);
		addActionMessage("batch removed successfully");
		return "success";
		
		
	}
}
