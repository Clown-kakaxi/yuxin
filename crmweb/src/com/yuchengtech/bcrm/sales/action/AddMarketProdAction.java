package com.yuchengtech.bcrm.sales.action;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.action.JDBCQueryDefindAction;
import com.yuchengtech.bcrm.sales.model.OcrmFMkActiChannel;
import com.yuchengtech.bcrm.sales.model.OcrmFMkActiCheck;
import com.yuchengtech.bcrm.sales.model.OcrmFMkActiCustomer;
import com.yuchengtech.bcrm.sales.model.OcrmFMkActiProduct;
import com.yuchengtech.bcrm.sales.service.AddMarketProdService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.JdbcUtil;
/**
 * 营销活动管理
 * @author hujun
 * @since 2014-07-01
 */

@SuppressWarnings("serial")
@Action("/addmarketprodaction")
public class AddMarketProdAction  extends CommonAction{
	
		@Autowired
		@Qualifier("dsOracle")
		private DataSource ds; //声明数据源
		private HttpServletRequest request;
	    @Autowired
    private AddMarketProdService addmarketprodservice ;
	    AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    @Autowired
	public void init(){
    	model = new OcrmFMkActiCustomer();  
    	setCommonService(addmarketprodservice);
	}
//   （自定义）批量删除
    public String batchDestroy(){
	   	ActionContext ctx = ActionContext.getContext();
	    request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String idStr = request.getParameter("idStr");
		String prodStr = request.getParameter("prodStr");
		String delSgin = request.getParameter("delSgin");
		String mktActiId = request.getParameter("mktActiId");
		String _SOURCE_PORD = request.getParameter("_SOURCE_PORD");//配置参数，目标客户来源
		String delSql="";
		BigDecimal mktActStr1=null;
		if(null!=mktActiId&&(mktActiId.length()>0)){
		mktActStr1=BigDecimal.valueOf(Integer.parseInt(mktActiId));
		}
		String jql = "delete from OcrmFMkActiProduct p where p.aimProdId in (" + idStr + ")";
		if("prod".equals(delSgin)){
			jql = "delete from OcrmFMkActiProduct p where p.aimProdId in (" + idStr + ")";
			delSql = "delete from OcrmFMkActiCustomer p where p.aimCustSource='03' and p.mktActiId ="+mktActStr1+" and p.custId in (select f.custId from OcrmFPdAimCust f where f.productId in ('"+prodStr.replace(",", "','")+"') ) ";
		}else{
			if("cust".equals(delSgin)){
				jql = "delete from OcrmFMkActiCustomer p where p.aimCustId in (" + idStr + ")";
		}else{
			if("chanel".equals(delSgin)){
				jql = "delete from OcrmFMkActiChannel p where p.actiChannelId in (" + idStr + ")";	
			}
		}}
		
		Map<String,Object> values = new HashMap<String,Object>();
		addmarketprodservice.batchUpdateByName(jql, values);
		if("false".equals(_SOURCE_PORD)){//关联产品中，目标客户添加功能
		if(!("".equals(delSql))){
		addmarketprodservice.batchUpdateByName(delSql, values);
		}
		}
		addActionMessage("batch removed successfully");	
	    return "success";
    }
    
    //新增或修改方法
    public String saveData() throws SQLException{
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String prodStr = request.getParameter("prodIdStr");//添加关联产品时，取得产品编号集合
    	String prodNameStr = request.getParameter("prodNameStr");//添加关联产品时，取得产品名称集合
    	String mktActStr = request.getParameter("mktActStr");//获取到营销活动编号
    	
    	String custIdStr = request.getParameter("custIdStr");//添加管理客户时，取得客户编号集合
    	String custNameStr = request.getParameter("custNameStr");//添加关联客户时，取得客户名称集合
    	
    	String _SOURCE_PORD = request.getParameter("_SOURCE_PORD");//配置参数，目标客户来源
    	String _IS_SAVE_CUST = request.getParameter("_IS_SAVE_CUST");//是否保存产品的关联客户到目标客户表中
    	String custStr = request.getParameter("custStr");//添加关联客户时，关联客户群取得客户编号集合
    	String custStr1 = request.getParameter("custStr1");//添加关联客户时，关联客户群取得客户名称集合
    	
    	String chanelId = request.getParameter("chanelId");
  	
    	String sign = request.getParameter("sign");//获取操作类型 维护关联客户、关联产品、渠道、附件等
    	if(!("".equals(sign))&&sign!=null&&sign.length()>0){
    		if(sign.equals("prod")){
    			model = new OcrmFMkActiProduct(); 
        		BigDecimal mktActStr1 =BigDecimal.valueOf(Integer.parseInt(mktActStr));
    			//关联产品部分
    			String[] ary = prodStr.split(",");
            	String[] ary1 = prodNameStr.split(",");
            	
            	for(int i = 0;i<ary.length;i++){
            		if(!("".equals(ary[i]))){
            		Integer test =Integer.parseInt(ary[i]);
            		BigDecimal aimProdId =BigDecimal.valueOf(test);
            		String sql = "select * from OCRM_F_MK_ACTI_PRODUCT t where t.PRODUCT_ID = '"+aimProdId+"' and t.mkt_acti_id ="+mktActStr1+" ";
            		if(!method1(sql))
            		{
            		OcrmFMkActiProduct ocrmfmkactiproduct = (OcrmFMkActiProduct) model;
            		ocrmfmkactiproduct.setAimProdId(null);//关联产品ID
                	ocrmfmkactiproduct.setProductId(aimProdId);//目标产品编号
                	ocrmfmkactiproduct.setProductName(ary1[i]);//目标产品名称
                	ocrmfmkactiproduct.setCreateDate(new Date());//创建时间
                	ocrmfmkactiproduct.setCreateUser(auth.getUserId());//创建人
                	ocrmfmkactiproduct.setMktActiId(mktActStr1);//营销活动编号
            		addmarketprodservice.saveData(model);
            		}
            		}
            	}
            	if("false".equals(_SOURCE_PORD)){//关联产品中，目标客户添加功能
            		if(null!=_IS_SAVE_CUST&&"true".equals(_IS_SAVE_CUST)){
                	prodRelateCustOperate(prodStr.replace(",", "','"),mktActStr1);	
            		}
            	}
            	
    		}else{
    			if(sign.equals("customer")){
    				model = new OcrmFMkActiCustomer(); 
    				//关联客户部分
        			String[] ary = custIdStr.split(",");
                	String[] ary1 = custNameStr.split(",");
                	
                	String[] groupCusId = custStr.split(",");
                	String[] groupCusName = custStr1.split(",");
                	
                	for(int i = 0;i<ary.length;i++){
                		if(!("".equals(ary[i]))){
                		BigDecimal mktActStr1 =BigDecimal.valueOf(Integer.parseInt(mktActStr));
                		
            			//判断当前记录是否在数据库中已经存在
                		String sql = "select * from ocrm_f_mk_acti_customer t where t.cust_id = '"+ary[i]+"' and t.mkt_acti_id ="+mktActStr1+" ";
                		if(!method1(sql))
                		{
                		//执行新增操作
                		OcrmFMkActiCustomer ocrmfmkacticustomer = (OcrmFMkActiCustomer) model;
                		ocrmfmkacticustomer.setAimCustId(null);
                		ocrmfmkacticustomer.setCustId(ary[i]);//客户编号
                		ocrmfmkacticustomer.setCustName(ary1[i]);//客户名称
                		ocrmfmkacticustomer.setAimCustSource("01");//定义目标客户来源为页面录入
                		ocrmfmkacticustomer.setCreateDate(new Date());//创建时间
                		ocrmfmkacticustomer.setProgressStep("0");//定义进展阶段为 未开始
                		ocrmfmkacticustomer.setCreateUser(auth.getUserId());//创建人
                		ocrmfmkacticustomer.setMktActiId(mktActStr1);//营销活动编号
                		addmarketprodservice.saveData(model);
                		}
                		}
                	}
                	for(int i = 0;i<groupCusId.length;i++){
                		if(!("".equals(groupCusId[i]))){
                		BigDecimal mktActStr1 =BigDecimal.valueOf(Integer.parseInt(mktActStr));
                		//判断当前记录是否在数据库中已经存在
                		String sql = "select * from ocrm_f_mk_acti_customer t where t.cust_id = '"+groupCusId[i]+"' and t.mkt_acti_id ="+mktActStr1+" ";
                		if(!method1(sql))
                		{
                		//执行新增操作
                		OcrmFMkActiCustomer ocrmfmkacticustomer = (OcrmFMkActiCustomer) model;
                		ocrmfmkacticustomer.setAimCustId(null);
                		ocrmfmkacticustomer.setCustId(groupCusId[i]);//客户编号
                		ocrmfmkacticustomer.setCustName(groupCusName[i]);//客户名称
                		ocrmfmkacticustomer.setCreateDate(new Date());//创建时间
                		ocrmfmkacticustomer.setProgressStep("0");//定义进展阶段为 未开始
                		ocrmfmkacticustomer.setAimCustSource("02");//定义目标客户来源为客户群
                		ocrmfmkacticustomer.setCreateUser(auth.getUserId());//创建人
                		ocrmfmkacticustomer.setMktActiId(mktActStr1);//营销活动编号
                		addmarketprodservice.saveData(model);
                		}
                		}
                	}
        		}else{
        			if(sign.equals("chanel")){
        				model = new OcrmFMkActiChannel(); 
            			String[] ary = chanelId.split(",");
                    	for(int i = 0;i<ary.length;i++){
                    		if(!("".equals(ary[i]))){
                    		Integer test =Integer.parseInt(ary[i]);
                    		BigDecimal aimProdId =BigDecimal.valueOf(test);
                    		BigDecimal mktActStr1 =BigDecimal.valueOf(Integer.parseInt(mktActStr));
                    		String sql = "select * from ocrm_f_mk_acti_channel t where t.PRODUCT_ID = '"+aimProdId+"' and t.mkt_acti_id ="+mktActStr1+" ";
                    		if(!method1(sql))
                    		{
                    		OcrmFMkActiChannel ocrmfmkactichannel = (OcrmFMkActiChannel) model;
                    		ocrmfmkactichannel.setActiChannelId(null);
                    		ocrmfmkactichannel.setProductId(aimProdId);//渠道编号
                    		ocrmfmkactichannel.setCreateDate(new Date());//创建时间
                    		ocrmfmkactichannel.setCreateUser(auth.getUserId());//创建人
                    		ocrmfmkactichannel.setMktActiId(mktActStr1);//营销活动编号
                    		addmarketprodservice.saveData(model);
                    		}
                    		}
                    	}	
            		}
        		}
    		}
    		addActionMessage("saveData successfully");
    	    return "success";	
    	}else{
    		return "failure";
    	}	
    }  
    
    //将产品的关联客户插入到活动的关联客户中
    public String prodRelateCustOperate(String str,BigDecimal mktActStr1) throws SQLException
    {
		ActionContext ctx = ActionContext.getContext();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date= new Date();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		Connection conn=null;
		Statement stat=null;
		ResultSet rs = null;
		 try {
			conn = ds.getConnection();
			 stat = conn.createStatement();
			 String sequences = "ID_SEQUENCE.NEXTVAL";
			 String sql = "delete from OCRM_F_MK_ACTI_CUSTOMER t where t.MKT_ACTI_ID = '"+mktActStr1+"'  and t.AIM_CUST_SOURCE = '03' ";
			 String kindSql = " insert into OCRM_F_MK_ACTI_CUSTOMER "+
			 "select "+sequences+", 					"+
			 "		"+mktActStr1+",						"+
			 "		 mgr.cust_id,                       "+
			 "       ac.CUST_NAME,                     "+
			 "       mgr.mgr_id,                        "+
			 "       mgr.institution,                   "+
			 "       '03',                    			"+
			 "       '0',                        		"+
			 "        '"+auth.getUserId()+"',			"+
			 " TO_DATE('"+sdf.format(date)+"','YYYY-MM-dd') "+
			 //luyy 结合ocrm_f_ci_cust_fit_prod表查询产品目标客户信息
			 "  from ACRM_F_CI_CUSTOMER ac ,ocrm_f_ci_cust_fit_prod aim             "+
			 "  left join ocrm_f_ci_belong_custmgr mgr "+
			 "    on mgr.cust_id = aim.cust_id           "+
			 "   and (mgr.main_type = '1' or   mgr.main_type is null)             "+
			 " where ac.cust_id=aim.cust_id and aim.prod_id in (select distinct(t.product_id) from ocrm_f_mk_acti_product t where t.mkt_acti_id = '"+mktActStr1+"') "+
			 " and aim.cust_id not in (select cust_id from OCRM_F_MK_ACTI_CUSTOMER ac where ac.mkt_acti_id = '"+mktActStr1+"' and ac.AIM_CUST_SOURCE<>'03' )";
			 
			 
			 int ext = stat.executeUpdate(sql);//首先清空当前营销活动的相关客户
			 int tt = stat.executeUpdate(kindSql);//更新成最新的关联产品的关联客户
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			JdbcUtil.close(rs, stat, conn);
		}
		addActionMessage(" lookupMapping removed successfully");
		return "success";
    }
    
    /**
     * 营销活动审批
     * @throws SQLException 
     * 
     * */   
    public String approve() throws SQLException{
		    	ActionContext ctx = ActionContext.getContext();
		    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		    	
		    	String mktActStr = request.getParameter("mktActStr");//获取到营销活动编号
		    	String checkIdea = request.getParameter("checkIdea");//获取到审批意见
		    	String appReason = request.getParameter("appReason");//获取到申请理由
		    	String sign = request.getParameter("sign");//获取操作类型 审批通过、拒绝
		    	String approveUser = request.getParameter("approveUser");//审批人编号
		    	String actiCheckId = request.getParameter("actiCheckId");//审批单编号
		    	
		    	String jql ="";
		    	BigDecimal mktActiId =BigDecimal.valueOf(Integer.parseInt(mktActStr));
		    	
		    	if(!("".equals(sign))&&sign!=null&&sign.length()>0)
		    	{
		    		if(sign.equals("approve")){
		    			
		    		//营销活动审批通过，执行中
		    		jql = "update OcrmFMkMktActivity p set p.mktActiStat = '3',astartDate=:newDate  where p.mktActiId ='"+mktActiId+"' ";
		    		//更新关联客户表，将进展阶段更新为1：执行中
		    		String ttt = "update OcrmFMkActiCustomer p set p.progressStep = '1' where p.mktActiId ='"+mktActiId+"' ";
		    		//更新申请表，更新审批时间，审批状态
		    		String ttt1 = "update OcrmFMkActiCheck p set p.checkStatus = '1',p.checkIdea = '"+checkIdea+"',p.checkDate=:newDate where p.actiCheckId ='"+Long.parseLong(actiCheckId)+"' ";
			    	Map<String,Object> values = new HashMap<String,Object>();
			    	Map<String,Object> values1 = new HashMap<String,Object>();
			    	values.put("newDate", new Date());
            		addmarketprodservice.batchUpdateByName(jql, values);
            		addmarketprodservice.batchUpdateByName(ttt, values1);
            		addmarketprodservice.batchUpdateByName(ttt1, values);
            		
        			Connection conn=null;
        			Statement stat=null;
        			ResultSet rs = null;
				    try {
						conn = ds.getConnection();
						stat = conn.createStatement();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						Date date= new Date();
						String tempName = "的营销活动";
						String sequences = "ID_SEQUENCE.NEXTVAL";
						   //执行营销活动，为每个客户的主办客户经理生成我的营销活动，S_ASSIGN置为1，没有主办客户经理的客户IS_ASSIGN置为0，由支行主管手工选取辖内的客户经理作为执行人。
						  String kindSql = " insert into OCRM_F_MK_MKT_MY_ACTI "+
						  " select "+sequences+", "+
						  " t.mkt_acti_id, "+
						  " t.cust_id, "+
						  " t.cust_name,"+
						  "'关于客户:'||trim(t.cust_name)||'"+tempName+"', "+
						  " cus.mgr_id, "+
						  " cus.mgr_name,"+
						  " t.progress_step, "+
						  " '0',"+
						  " '"+auth.getUserId()+"',  "+
						  " TO_DATE('"+sdf.format(date)+"','YYYY-MM-dd'), "+
						  " '',"+
						  " '', "+
						  " (case when cus.mgr_id is null then '0' else '1' end) as eee,"+
						  " t.create_user "+//修正，创建人更正为主营销活动创建人
						  " from ocrm_f_mk_acti_customer t "+
						  " left join ocrm_f_ci_belong_custmgr cus "+
						  " on cus.cust_id = t.cust_id "+
						  " and cus.main_type = '1' where t.Mkt_Acti_Id = '"+mktActiId+"' ";
						  int tt = stat.executeUpdate(kindSql);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{
						JdbcUtil.close(rs, stat, conn);
					}
		    	}else if(sign.equals("refuse")){//营销活动审批拒绝
	    			jql = "update OcrmFMkMktActivity p set p.mktActiStat = '6'  where p.mktActiId ='"+mktActiId+"' ";
	    			//更新申请表，更新审批时间，审批状态 拒绝
		    		String ttt1 = "update OcrmFMkActiCheck p set p.checkStatus = '0',p.checkIdea = '"+checkIdea+"',p.checkDate=:newDate where p.actiCheckId ='"+Long.parseLong(actiCheckId)+"' ";
			    	
			    	Map<String,Object> values = new HashMap<String,Object>();
			    	Map<String,Object> values1 = new HashMap<String,Object>();
			    	values1.put("newDate", new Date());
            		addmarketprodservice.batchUpdateByName(jql, values);	
            		addmarketprodservice.batchUpdateByName(ttt1, values1);	
            		
		    		}else if(sign.equals("apply")){//提交营销活动申请，更新营销活动表的活动状态
		    			
	    			//查询是否关联了目标客户
	    	    	JDBCQueryDefindAction jdbcq = new JDBCQueryDefindAction();
	    	    	jdbcq.create(mktActStr);
	    	    	//END
	    			jql = "update OcrmFMkMktActivity p set p.mktActiStat = '2'  where p.mktActiId ='"+mktActiId+"' ";
			    	Map<String,Object> values = new HashMap<String,Object>();
            		addmarketprodservice.batchUpdateByName(jql, values);	
		    		}
					model = new OcrmFMkActiCheck(); 
	
	        		//提交营销活动申请---向活动审批表添加申请人、审批人信息
	        		OcrmFMkActiCheck ocrmfmkacticheck = (OcrmFMkActiCheck) model;
	        		ocrmfmkacticheck.setCheckDate(new Date());
	        		if(("apply").equals(sign)){//判定当操作为申请时，新增一条记录，指明待审批人为XXX
	        		
	    			ocrmfmkacticheck.setCheckUser(approveUser);	//待审批人
	    			ocrmfmkacticheck.setCheckStatus("2");//审批状态2暂定为已提交
	    			ocrmfmkacticheck.setMktActiId(mktActiId);//营销活动编号
	        		ocrmfmkacticheck.setAppReason(appReason);//审批理由
	        		addmarketprodservice.saveData(model);  	
	        		}
	        		return "success";	
			    	}else{
			    		return "failure";
			    	}
	    }
    
    /*
	  * 查询出营销活动关联客户表中与关联产品表有：产品目标客户关系的客户数据
	  * 2014-07-29
	  * */
	public String queryRelateCustomer(){
 		ActionContext ctx = ActionContext.getContext();
 		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
 		String idStr = request.getParameter("idStr");
 		String mktActiId = request.getParameter("mktActiId");
 		json = addmarketprodservice.loadRelateCustomer(idStr,mktActiId);
 		return "success";
 	}
    
    /**
	 * 设置查询SQL并为父类相关属性赋值
	 */
	public void prepare() {
		String sortStr = "D.ID DESC"; //设置默认排序
		StringBuilder queryStr = new StringBuilder("SELECT * " +
				" FROM FW_SYS_PROP D " +
				" WHERE 1>0 and D.PROP_NAME NOT LIKE 'CustOnwerPara%'");
		for (String key : this.getJson().keySet()) {
			if (null != this.getJson().get(key)
					&& !this.getJson().get(key).equals("")) {
				if (key.equals("propName")) {
					queryStr.append(" AND D.PROP_NAME LIKE '%" + this.getJson().get(key) + "%'");
				} else if (key.equals("propDesc")) {
					queryStr.append(" AND D.PROP_DESC LIKE '%" + this.getJson().get(key) + "%'");
				} else if(key.equals("id")){
					queryStr.append(" AND D.ID =" + Long.parseLong((String) this.getJson().get(key)));
                } 				
			}
		}
		
		SQL = queryStr.toString();
		setPrimaryKey(sortStr);
		datasource = ds;
	}

    //分页查询
    public HttpHeaders indexPage() throws Exception {
    	try{	
    		StringBuilder  sb = new StringBuilder("select p from FwSysProp p where 1=1 ");
			Map<String,Object> values = new HashMap<String,Object>();
			ActionContext ctx = ActionContext.getContext();
			request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
			
			if(request.getParameter("start") != null) {
				start = new Integer(request.getParameter("start")).intValue();
			}
			if(request.getParameter("limit") != null){
				limit = new Integer(request.getParameter("limit")).intValue();
			}
			this.setJson(request.getParameter("condition"));
			for(String key:this.getJson().keySet()){
			    if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
			        if(key.equals("propName")){
			            sb.append(" and p.propName like :propName");
			            values.put("propName", "%" + (String) this.getJson().get(key) + "%");
			        }
			        else if(key.equals("p.propDesc")){
			            sb.append(" and p.propDesc like :propDesc");
			            values.put("propDesc", "%" + (String)this.getJson().get(key) + "%");
			        } else if(key.equals("id")){
	                    sb.append(" and p.id = :id");
	                    values.put("id", Long.parseLong((String) this.getJson().get(key)));
	                } else{
			        	sb.append(" and p." + key + " = :" + key);
			        	values.put(key, this.getJson().get(key));
			        }
			    }
			}
			return super.indexPageByJql(sb.toString(), values);
    	} catch (Exception e){
    		e.printStackTrace();
    		throw e;
    	}
	}
  //查询当前操作的客户，是否已经存在于营销活动的关联客户表中，有则返回true，否则返回false
	public boolean method1(String sql) {
		Boolean sign = true;
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;

		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		try {

			conn = ds.getConnection();
			statement = conn.createStatement();
			rs = statement.executeQuery(sql);
			if (rs.next()) {
				sign = true;
			} else {
				sign = false;
			}
			rs.close();
			statement.close();
    		conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JdbcUtil.close(rs, statement, conn);
		}
		return sign;
	};
}