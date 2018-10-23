package com.yuchengtech.bcrm.serviceManage.action;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
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
import com.yuchengtech.bcrm.serviceManage.model.OcrmFSeGoodsHis;
import com.yuchengtech.bcrm.serviceManage.service.OcrmFSeGoodsHisService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.JPAAnnotationMetadataUtil;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.JdbcUtil;

/**
 * 礼品领取Action
 * 
 * @author luyy
 * @since 2014-06-11
 */

@SuppressWarnings("serial")
@Action("/ocrmFSeGoodsHis")
public class OcrmFSeGoodsHisAction extends CommonAction {

	@Autowired
	private OcrmFSeGoodsHisService service;
	

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;// 定义数据源属性

	@Autowired
	public void init() {
		model = new OcrmFSeGoodsHis();
		setCommonService(service);
		needLog = true;// 新增修改删除记录是否记录日志,默认为false，不记录日志
	}
	AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String id = request.getParameter("id");
		StringBuilder sb = new StringBuilder("");
		if(!"".equals(id)&&id != null){
			sb.append(" select s.*,a.user_name as create_name,so.score_total,so.score_todel from OCRM_F_SE_GOODS_HIS s ,admin_auth_account a,OCRM_F_SE_SCORE so where s.OPARTER_ID = a.account_name and s.cust_id=so.cust_id and s.id='"+id+"'");
		}else{
			sb.append("select  s.*, a.user_name as create_name  from OCRM_F_SE_GOODS_HIS s, admin_auth_account a where s.OPARTER_ID = a.account_name and s.OPARTER_ID ='"+auth.getUserId()+"' ");
			
			for (String key : this.getJson().keySet()) {// 查询条件判断
				if (null != this.getJson().get(key)&& !this.getJson().get(key).equals("")) {
					if (key.equals("CREATE_DATE")||key.equals("GIVE_DATE")) {
						sb.append(" AND s."+key+" =  to_date('"+this.getJson().get(key).toString().substring(0,10)+"','yyyy-mm-dd')");
					} else if (key.equals("CREATE_NAME")) {
						sb.append(" AND a.user_name like '%" + this.getJson().get(key)+ "%'");
					}else if (key.equals("ORG_NAME")) {
						sb.append(" AND s.ORG_ID = '" + this.getJson().get(key)+ "'");
					}else{ 
						sb.append(" AND s." + key + " like '%" + this.getJson().get(key)+ "%'");
					}
				}
			}
		}

		SQL = sb.toString();
		datasource = ds;
	}
	
	
	//保存操作（锁定客户积分）
	public void save() {
		Connection conn = null;
		Statement stmt = null;
		// 锁定积分
		if (((OcrmFSeGoodsHis) model).getId() == null) {// 自己的将所需积分给score_todel字段
			((OcrmFSeGoodsHis) model).setOparterId(auth.getUserId());
			((OcrmFSeGoodsHis) model).setOrgId(auth.getUnitId());
			((OcrmFSeGoodsHis) model).setCreateDate(new Date());
			String custId = ((OcrmFSeGoodsHis) model).getCustId();
			BigDecimal needScore = ((OcrmFSeGoodsHis) model).getNeedScore();
			try {
				conn = ds.getConnection();
				stmt = conn.createStatement();
				String sql = " update OCRM_F_SE_SCORE set "
						+ "score_todel = decode(score_todel,null,0,'',0,score_todel)+"
						+ needScore + " where cust_id='" + custId + "'";
				stmt.executeUpdate(sql);

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				JdbcUtil.close(null, stmt, conn);
			}
		} else {// 减去原来分数值，添加现在分数值
			OcrmFSeGoodsHis his = (OcrmFSeGoodsHis) service
					.find(((OcrmFSeGoodsHis) model).getId());
			BigDecimal needScore = ((OcrmFSeGoodsHis) model).getNeedScore();
			String custId = ((OcrmFSeGoodsHis) model).getCustId();
			BigDecimal needScoreOld = his.getNeedScore();
			try {
				conn = ds.getConnection();
				stmt = conn.createStatement();
				String sql = " update OCRM_F_SE_SCORE set "
						+ "score_todel = decode(score_todel,null,0,'',0,score_todel)-"
						+ needScoreOld + "+" + needScore + " where cust_id='"
						+ custId + "'";
				stmt.executeUpdate(sql);

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				JdbcUtil.close(null, stmt, conn);
			}
		}

		model = service.save(model);

		AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		JPAAnnotationMetadataUtil metadataUtil = new JPAAnnotationMetadataUtil();
		auth.setPid(metadataUtil.getId(model).toString());
	}
	
	
	 /**
	 * 发起工作流
	 * */
	public void initFlow() throws Exception{
	  	ActionContext ctx = ActionContext.getContext();
		 request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String requestId =  request.getParameter("instanceid");
		String name =  request.getParameter("name");
		String instanceid = "LQ_"+requestId;//此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
		  String jobName = "礼品领取_"+name;//自定义流程名称
		  service.initWorkflowByWfidAndInstanceid("15", jobName, null, instanceid);//调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
		  
		  Map<String,Object> map=new HashMap<String,Object>();
			map.put("instanceid", instanceid);
		    map.put("currNode", "15_a3");
		    map.put("nextNode",  "15_a4");
		    this.setJson(map);
		  
		  
	}
	
	   /**
	 * 流程提交
	 * */
	public void initFlowJob() throws Exception{
//	  	ActionContext ctx = ActionContext.getContext();
//		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
//		String instanceid = "LQ_"+request.getParameter("instanceid");
//		  
//		service.wfCompleteJob(instanceid, "15_a3", "15_a4", null, null);
	}

	 public void batchDel(){
		 	Connection conn = null;
			Statement stmt = null;
	    	ActionContext ctx = ActionContext.getContext();
	    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	    	String idStr = request.getParameter("idStr");
	    	String ids[] = idStr.split(",");
	    	 try {
		            conn = ds.getConnection();
		            stmt = conn.createStatement();
		    		 for(String id : ids){
		 	    		//修改积分数据
		    			 String sql = " update OCRM_F_SE_SCORE set " +
							"score_todel = decode(score_todel,null,0,'',0,score_todel)-(select h.need_Score from  Ocrm_F_Se_Goods_His h where h.id='"+Long.parseLong(id)+"') "
							+" where cust_id=(select h.cust_Id from Ocrm_F_Se_Goods_His h where  h.id='"+Long.parseLong(id)+"')";
				            stmt.executeUpdate(sql);
				            
		 	    		service.batchUpdateByName(" delete from OcrmFSeGoodsHis g where g.id='"+Long.parseLong(id)+"'", new HashMap());
		 	    		
		 	    	}
		            
		        } catch (SQLException e) {
		            e.printStackTrace();
		        } finally {
		        	JdbcUtil.close(null, stmt, conn);
		        }
		        
	    	
	    }
	}