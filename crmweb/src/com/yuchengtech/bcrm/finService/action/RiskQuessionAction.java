package com.yuchengtech.bcrm.finService.action;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.finService.service.RiskQuessionService;
import com.yuchengtech.bob.action.BaseQueryAction;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.JdbcUtil;
/***
 * 试题管理
 * @author luyueyue
 *
 */
@ParentPackage("json-default")
@Action(value = "/RiskQuession", results = { @Result(name = "success", type = "json") })
@SuppressWarnings("unchecked")
public class RiskQuessionAction extends BaseQueryAction {
	@Autowired
	private HttpServletRequest request;

	@Autowired
	private RiskQuessionService riskQuessionService;

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;

	@Override
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);

		StringBuilder sb = new StringBuilder("");

		sb.append(" select O.TITLE_NAME,O.TITLE_TYPE,O.TITLE_SORT,O.AVAILABLE,A.USER_NAME,O.UPDATE_DATE,O.TITLE_ID,o.TITLE_REMARK," +
				"decode(count,0,'no','','no',null,'no','yes') as hasUsed ");
		sb.append(" from OCRM_F_SE_TITLE O");
		sb.append(" LEFT OUTER JOIN ADMIN_AUTH_ACCOUNT A ON O.UPDATOR = A.ACCOUNT_NAME left join " +
				"(select count(QUESTION_ID) as count,QUESTION_ID from OCRM_F_SM_PAPERS_QUESTION_REL group by QUESTION_ID )r on r.QUESTION_ID = o.title_id  ");
		sb.append(" where 1=1");

		for (String key : this.getJson().keySet()) {
			if (null != this.getJson().get(key)
					&& !this.getJson().get(key).equals("")) {
				if (key.equals("TITLE_TYPE")) {
					sb.append(" and O.TITLE_TYPE='" + this.getJson().get(key)+ "'");
				} else if (key.equals("TITLE_NAME")) {
					sb.append(" and O.TITLE_NAME like '%"+ this.getJson().get(key) + "%'");
				}else if (key.equals("USER_NAME")) {
					sb.append(" and A.USER_NAME like '%"+ this.getJson().get(key) + "%'");
				} else  {
					sb.append(" and O."+key+" like '%"+ this.getJson().get(key) + "%'");
				}
			}
		}

		
		SQL = sb.toString();
		setPrimaryKey("o.update_date desc");
		datasource = ds;

	}

	public String findResult() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		this.json = this.riskQuessionService.findResult(request
				.getParameter("titleId"));
		return "sucess";
	}

	public String createQuession() {
		//如果是修改，在此处删除子表Ocrm_f_Se_Title_Result的数据
		String id = "";
		//this.setJson(request.getParameter("condition"));
		//this.json.put("resultInfo", request.getParameter("resultInfo"));
		if(this.json.get("TITLE_ID")!=null)
			id=this.json.get("TITLE_ID").toString();
		if(!"".equals(id)){
			Connection conn = null;
			Statement stmt1 = null;
			 try {
				 	conn = ds.getConnection();
		            stmt1 = conn.createStatement();
		            String sql1 = "delete from  Ocrm_F_Se_Title_Result p where p.title_Id ='"+id+"'";
		            stmt1.executeUpdate(sql1);
		            
		        } catch (SQLException e) {
		            e.printStackTrace();
		        } finally {
		        	JdbcUtil.close(null, stmt1, conn);
		        }
		}
		riskQuessionService.createQuession(this.json);
		return "sucess";
	}

	public String openOrCloseQuession() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String titleId = request.getParameter("titleId");
		String available = request.getParameter("available");
		this.riskQuessionService.openOrCloseQuession(auth.getUserId(), titleId,
				available);
		return "sucess";
	}

	public void setResultInfo(List resultInfo) {
		this.json.put("resultInfo", resultInfo);
	}

	public String findRiskParam() {
		this.json = this.riskQuessionService.findRiskParam();
		return "sucess";
	}
	
	 public DefaultHttpHeaders batchDel(){
	    	ActionContext ctx = ActionContext.getContext();
	    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	    	String s[] = request.getParameter("idStr").split(",");
		   	Connection conn = null;
			Statement stmt = null;
			Statement stmt1 = null;
		   	for(int i=0;i<s.length;i++){
		   	 try {
		            conn = ds.getConnection();
		            stmt = conn.createStatement();
		            String sql = "delete from  Ocrm_F_Se_Title p where p.title_Id ='"+s[i]+"'";
		            stmt.executeUpdate(sql); 
		            stmt1 = conn.createStatement();
		            String sql1 = "delete from  Ocrm_F_Se_Title_Result p where p.title_Id ='"+s[i]+"'";
		            stmt1.executeUpdate(sql1);
		            
		        } catch (SQLException e) {
		            e.printStackTrace();
		        } finally {
		        	JdbcUtil.closeStatement(stmt1);
		        	JdbcUtil.close(null, stmt, conn);
		        }
		        
		   	}
	    	
		return new DefaultHttpHeaders("success");
	   }
	 
}
