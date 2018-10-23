package com.yuchengtech.bcrm.customer.potentialMkt.action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFCiMktProspectP;
import com.yuchengtech.bcrm.customer.potentialMkt.service.OcrmFCiMktProspectPService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.bob.vo.AuthUser;
/**
 * 个金客户营销流程 -  prospect处理  luyy  2014-07-22
 */

@SuppressWarnings("serial")
@Action("/mktprospectP")
public class MktProspectPAction  extends CommonAction{
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds; //声明数据源
	private HttpServletRequest request;
	
    @Autowired
    private OcrmFCiMktProspectPService service ;
    
    AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    @Autowired
	public void init(){
        	model = new OcrmFCiMktProspectP();  
        	setCommonService(service);
		//新增修改删除记录是否记录日志,默认为false，不记录日志
		//needLog=true;
	}
    /**
	 * 设置查询SQL并为父类相关属性赋值
	 */
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String sqlapp = " select c.*,a.USER_NAME  from OCRM_F_CI_MKT_PROSPECT_P c " +
    			"left join admin_auth_account a on c.user_id = a.account_name where 1=1  ";
        
    	String id = request.getParameter("id");
    	StringBuilder sb  = new StringBuilder(sqlapp);
    	if( id != null && !"".equals(id)){//流程查询使用
    		sb.append(" and c.id = '"+id+"'");
    		addOracleLookup("CUST_SOURCE", "CUST_SOURCE");
    		addOracleLookup("VISIT_WAY", "VISIT_TYPE");
    		addOracleLookup("IF_TRANS_CUST", "IF_FLAG");
    		addOracleLookup("IF_PIPELINE", "IF_FLAG");
    	}else{
    		sb.append("and user_id='"+auth.getUserId()+"' ");
        	for (String key : this.getJson().keySet()) {// 查询条件判断
			if (null != this.getJson().get(key)&& !this.getJson().get(key).equals("")) {
				if (key.equals("CUST_SOURCE_DATE")) {
					sb.append(" AND c.CUST_SOURCE_DATE =  to_date('"+this.getJson().get(key).toString().substring(0,10)+"','yyyy-mm-dd')");
				} else if(key.equals("CUST_ID")||"CUST_SOURCE".equals(key)||"CHECK_STAT".equals(key)){
					sb.append(" AND c."+key+" = '"+this.getJson().get(key)+"'");
				}else if(key.equals("AREA_NAME")){
					sb.append(" AND c.AREA_ID = '"+this.getJson().get(key)+"'");
				}else if("DEPT_NAME".equals(key)){
					sb.append(" AND c."+key+" like '%"+this.getJson().get(key)+"%'");
				}
			}
		}
    		setPrimaryKey("c.ID desc ");
    	}
        	SQL=sb.toString();
        	datasource = ds;
	}

    
    
    public DefaultHttpHeaders save() throws Exception{
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	((OcrmFCiMktProspectP)model).setUserId(auth.getUserId());
    	((OcrmFCiMktProspectP)model).setCheckStat("2");
    	((OcrmFCiMktProspectP)model).setRecordDate(new Date());
    	((OcrmFCiMktProspectP)model).setUpdateDate(new Date());
    	
    	service.save(model);
    	
    	Long id = ((OcrmFCiMktProspectP)model).getId();
    	String name = ((OcrmFCiMktProspectP)model).getCustName();
    	
    	String instanceid = "PMKT_"+id+"_11";//最后的字符串含义  第一位：1 个金  2商金    第二位：表示营销的步骤
		String jobName = "PROSPECT复核_"+name;//自定义流程名称
    	service.initWorkflowByWfidAndInstanceid("42", jobName, null, instanceid);//调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
		  Map<String,Object> map=new HashMap<String,Object>();
			map.put("instanceid", instanceid);
		    map.put("currNode", "42_a3");
		    map.put("nextNode",  "42_a4");
		    this.setJson(map);
    	
    	
    	return new DefaultHttpHeaders("success");
    }
    //关联的电访记录
    public void searchCalls(){
    	try {
			StringBuilder sb = new StringBuilder("");
				sb.append("select CUST_NAME||'('||to_char(PHONE_DATE,'YYYY-MM-dd')||')' as value,id as key " +
						"from OCRM_F_CI_MKT_CALL_P where CHECK_STAT ='3' and CALL_RESULT='1' " +
						" and id not in (select call_id from OCRM_F_CI_MKT_PROSPECT_P) ");
			this.json = new QueryHelper(sb.toString(), ds.getConnection()).getJSON();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    //查询电访相关信息
    public void getInfo() throws IOException{
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String callId = request.getParameter("callId");
    	String info = "";
    	List<Object[]> list1 = service.getBaseDAO().findByNativeSQLWithIndexParam(" " +
    			"select a.CUST_ID,a.CUST_NAME,a.CUST_SOURCE,a.LINK_PHONE,o.institution_code from OCRM_F_CI_MKT_CALL_P a " +
    			"left join OCRM_F_CI_BELONG_ORG o on a.cust_id = o.cust_id and o.MAIN_TYPE='1'  where a.id='"+callId+"'");
    	if (list1 != null && list1.size() > 0) {
			Object[] o = list1.get(0);
			info = o[0]+"#"+o[1]+"#"+o[2]+"#"+o[3] ;
			String org = o[4]==null?"":o[4].toString();
			if(!"".equals(org)){//查询区域中心信息
				List<Object[]> list2 = service.getBaseDAO().findByNativeSQLWithIndexParam(" " +
		    			"select  org_id,org_name from admin_auth_org where (org_id = '"+org+"' and org_level='2') " +
		    			"or org_id = (select up_org_id from admin_auth_org where org_id = '"+org+"' and org_level='3')");
				if (list2 != null && list2.size() > 0) {
					Object[] oo = list2.get(0);
					info += "#"+oo[0]+"#"+oo[1];
				}else{
					info += "#null#null";
				}
			}else{
				info += "#null#null";
			}
			
    	}
    	HttpServletResponse response =(HttpServletResponse) ctx.get(ServletActionContext.HTTP_RESPONSE);
    	response.setCharacterEncoding("UTF-8");
		response.getWriter().write(info);
		response.getWriter().flush();
    	
    }
    
}