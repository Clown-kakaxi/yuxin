
package com.yuchengtech.bcrm.customer.belong.action;



import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.model.OcrmFCiGroupHis;
import com.yuchengtech.bcrm.customer.service.OcrmFCiGroupHisService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 集团GAO移交处理
 * @author luyy
 *@since 2014-07-09
 */

@SuppressWarnings("serial")
@Action("/groupTrans")
public class GroupTransAction  extends CommonAction {
    
    private HttpServletRequest request;

	@Autowired
	private OcrmFCiGroupHisService service;
	
	
	@Autowired
	@Qualifier("dsOracle")	
	private DataSource ds;  
	
	@Autowired
	public void init() {
		model = new OcrmFCiGroupHis();
		setCommonService(service);
	}
	 AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
	/**
	 * 关联表查询
	 */
	public void prepare(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String type = request.getParameter("type");
    	StringBuffer sb = new StringBuffer("");
    	if("1".equals(type)){
    		sb.append(" select m.*,a.user_name,o.org_name from OCRM_F_CI_GROUP_INFO m left join admin_auth_account a on m.gao=a.account_name left join admin_auth_org o on m.gao_org=o.org_id where 1=1");
    	}
    	if("2".equals(type)){//查询调整历史
    		sb.append(" select m.*,a.user_name as gao_old_name,c.user_name as gao_new_name,o1.org_name as gao_org_old_name,o2.org_name as gao_org_new_name " +
    				" from OCRM_F_CI_GROUP_his m left join admin_auth_account a on m.gao_old=a.account_name left join admin_auth_account c  on m.gao_new=c.account_name" +
    				" left join admin_auth_org o1 on m.gao_org_old=o1.org_id left join admin_auth_org o2 on m.gao_org_new = o2.org_id where 1=1");
    	}
    	
    	
    	//处理页面查询条件
//    	 for(String key : this.getJson().keySet()){
    	for(String key : getJson().keySet()){
    		 String value = getJson().get(key).toString();
     		//if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
    		 if (! "".equals(value)) {
 				if(null!=key&&key.equals("GROUP_NAME")||"GROUP_NAME_MAIN".equals(key)||"CREATE_USER_NAME".equals(key)){
// 					sb.append(" and  m."+key+" like '%"+this.getJson().get(key)+"%'  ");
 					sb.append(" and  m."+key+" like '%"+value+"%'  ");
 				}else if(null!=key&&key.equals("CREATA_DATE")){
// 					sb.append("  and m.CREATA_DATE =to_date('%"+this.getJson().get(key).toString().substring(0,10)+"%'  ,'yyyy-mm-dd')");
 					sb.append("  and m.CREATA_DATE =to_date('%"+value.toString().substring(0,10)+"%'  ,'yyyy-mm-dd')");
 				}else if(null!=key&&"USER_NAME".equals(key)){
// 					sb.append("  and a.user_name like '%"+this.getJson().get(key)+"%'  ");
 					sb.append("  and a.user_name like '%"+value+"%'  ");
 				}else if(null!=key&&"ORG_NAME".equals(key)){
// 					sb.append("  and o.org_name like '%"+this.getJson().get(key)+"%'  ");
 					sb.append("  and o.org_name like '%"+value+"%'  ");
 				}else if(null!=key){
// 					sb.append("  and  m."+key+" like '%"+this.getJson().get(key)+"%' ");
 					sb.append("  and  m."+key+" like '%"+value+"%' ");

                 }
     		}
 		}
    	SQL = sb.toString();
    	datasource = ds;
    }
	
	public void save() throws Exception{
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String GROUP_ID = ((OcrmFCiGroupHis)model).getGroupId();
    	String gao = ((OcrmFCiGroupHis)model).getGaoNew();
    	String gaoOrg = ((OcrmFCiGroupHis)model).getGaoOrgNew();
    	Map map = new HashMap();
    	service.batchUpdateByName(" update OcrmFCiGroupInfo g set g.gao='"+gao+"',g.gaoOrg='"+gaoOrg+"' where g.id='"+Long.parseLong(GROUP_ID)+"'", map); 	
    	
    	((OcrmFCiGroupHis)model).setUserId(auth.getUserId());
    	((OcrmFCiGroupHis)model).setUserName(auth.getUsername());
    	((OcrmFCiGroupHis)model).setTransDate(new Date());
    	service.save(model);
    	
		}
	
	  
}
