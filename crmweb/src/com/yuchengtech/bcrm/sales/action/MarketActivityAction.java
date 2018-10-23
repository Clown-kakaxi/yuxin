package com.yuchengtech.bcrm.sales.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.product.service.TargetCusSearchService;
import com.yuchengtech.bcrm.sales.model.OcrmFMkActiCustomer;
import com.yuchengtech.bcrm.sales.model.OcrmFMkActiProduct;
import com.yuchengtech.bcrm.sales.model.OcrmFMkMktActivity;
import com.yuchengtech.bcrm.sales.service.AddMarketProdService;
import com.yuchengtech.bcrm.sales.service.MarketActivityService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.upload.FileTypeConstance;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.JdbcUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 营销活动管理-查询、维护、新增
 * @author sujm
 * @since 2013-02-20 
 */
@SuppressWarnings("serial")
@Action("/market-activity")
public class MarketActivityAction  extends CommonAction {
	
    @Autowired
	private MarketActivityService marketActivityService;
    @Autowired
    private AddMarketProdService addmarketprodservice ;
    @Autowired
	private TargetCusSearchService targetCusSearchService;
    
    @Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
    
    
    private Configuration configuration = null;
    
    AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    @Autowired
	public void init(){
	  	model = new OcrmFMkMktActivity(); 
		setCommonService(marketActivityService);
		//新增修改删除记录是否记录日志,默认为false，不记录日志
		//needLog=true;
	}
    
   /**
    * 查询机构树节点信息
    */
    public void prepare() {
        StringBuilder sb = new StringBuilder("SELECT A.*,UU.USERNAME, (select count(1) from WF_MAIN_RECORD where instanceid like 'MKTACT_'　|| a.MKT_ACTI_ID || '%') as COMMIT_COUNT FROM OCRM_F_MK_MKT_ACTIVITY A  LEFT JOIN SYS_USERS UU  ON uu.userid =A.CREATE_USER WHERE 1>0 ");
        //审批页面查询时用到
        String id = request.getParameter("id");
        if(null!=id&&id.length()>0){
        	sb.append(" and a.MKT_ACTI_ID='"+id+"'");
        }
        for(String key:this.getJson().keySet()){
            if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
                if(key.equals("MKT_ACTI_NAME"))
                	sb.append(" and a."+key+" like '%"+this.getJson().get(key)+"%'");
                else if(key.equals("PSTART_DATE"))
                    sb.append(" and a.PSTART_DATE = to_date('" +this.getJson().get(key).toString().substring(0, 10)+ "','yyyy-MM-dd')");
//                else if(key.equals("PSTART_DATE_E"))
//                	 sb.append(" and a.PSTART_DATE <= to_date('" +this.getJson().get(key).toString().substring(0, 10)+ "','yyyy-MM-dd')");
                else if(key.equals("PEND_DATE"))
                    sb.append(" and a.PEND_DATE = to_date('" +this.getJson().get(key).toString().substring(0, 10)+ "','yyyy-MM-dd')");
//                else if(key.equals("PEND_DATE_E"))
//                	 sb.append(" and a.PEND_DATE <= to_date('" +this.getJson().get(key).toString().substring(0, 10)+ "','yyyy-MM-dd')");
                else if(key.equals("MKT_APP_STATE"))
               	 sb.append(" and a.MKT_APP_STATE ='" +this.getJson().get(key)+ "'");
                else{
                	sb.append(" and a."+key+" like '%"+this.getJson().get(key)+"%'");
                }
            }
        }
        if(null!=id&&id.length()>0){
        	
        }else{
           sb.append(" and a.CREATE_USER ='"+auth.getUserId()+"' ");
        }

        setPrimaryKey("a.UPDATE_DATE desc ");
        
        SQL=sb.toString();
        datasource = ds;
	}  
    
    /**
     * 查询营销活动是否需要审批
     */
    public void getIfAppl(){
    	ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		List<Object[]> list1 = marketActivityService.getBaseDAO().findByNativeSQLWithIndexParam(" select ID,PROP_VALUE from FW_SYS_PROP where PROP_NAME='mktAppType'");
		String mktAppType  = "";
		if (list1 != null && list1.size() > 0) {
			Object[] o = list1.get(0);
			mktAppType =  o[1]+"";
		}
    	HttpServletResponse response =(HttpServletResponse) ctx.get(ServletActionContext.HTTP_RESPONSE);
    	response.setCharacterEncoding("UTF-8");
    	
    	Map<String,Object> map1=new HashMap<String,Object>();
    	map1.put("mktAppType", mktAppType);
    	this.setJson(map1);
    }
    
	public String batchDestroy() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		String idStr = request.getParameter("idStr");
		marketActivityService.batchRemove(idStr);
		/*//删除关联产品信息
		String jql1="DELETE FROM OcrmFMkActiProduct C WHERE C.mktActiId IN ("+idStr+")";
		Map<String,Object> values1=new HashMap<String,Object>();
		marketActivityService.batchUpdateByName(jql1, values1);
		//删除关联客户信息
		String jql2="DELETE FROM OcrmFMkActiCustomer C WHERE C.mktActiId IN ("+idStr+")";
		Map<String,Object> values2=new HashMap<String,Object>();
		marketActivityService.batchUpdateByName(jql2, values2);*/
//		//删除渠道信息
//		String jql3="DELETE FROM OcrmFMkActiChannel C WHERE C.mktActiId IN ("+idStr+")";
//		Map<String,Object> values3=new HashMap<String,Object>();
//		marketActivityService.batchUpdateByName(jql3, values3);
		
		addActionMessage(" lookupMapping removed successfully");
		return "success";
	}
	
    //执行营销活动
    public String activityExecute() throws SQLException
    {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String idStr = request.getParameter("idStr");
		String sign = request.getParameter("sign");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date= new Date();
//		System.out.println(sdf.format(date));
		marketActivityService.saveActivity(idStr,sign);
		if("execute".equals(sign)){	
			Connection conn=null;
			Statement stat=null;
				 try {
					conn = ds.getConnection();
					 stat = conn.createStatement();
					 String tempName = "的营销活动";
					 String sequences = "ID_SEQUENCE.NEXTVAL";
					 //执行营销活动，为每个客户的主办客户经理生成我的营销活动，S_ASSIGN置为1，没有主办客户经理的客户IS_ASSIGN置为0，由支行主管手工选取辖内的客户经理作为执行人。
					 String kindSql = " insert into OCRM_F_MK_MKT_MY_ACTI "+
					 " select "+sequences+", "+
					 " t.mkt_acti_id, "+
					 " t.cust_id,"+
					 " t.cust_name,"+
					 "'关于客户:'||trim(t.cust_name)||'"+tempName+"', "+
					 " cus.mgr_id, "+
					 " cus.mgr_name,"+
					 " t.progress_step, "+
//				 " (case when cus.mgr_id is null then '2' else '0' end) as ddd ,"+
					 "'0',"+
					 " t.create_user, "+//修正，创建人更正为主营销活动创建人
					 " TO_DATE('"+sdf.format(date)+"','YYYY-MM-dd'), "+
					 " '',"+
					 " '', "+
					 " (case when cus.mgr_id is null then '0' else '1' end) as eee, "+
					 " (select acc.org_id from admin_auth_account acc where acc.account_name =t.create_user), "+//修正，创建人更正为主营销活动创建人
					 " s.PRODUCT_ID,"+
					 " S.PRODUCT_NAME"+
					 " from ocrm_f_mk_acti_customer t "+
					 " left join OCRM_F_MK_ACTI_PRODUCT s"+
					 " on s.Mkt_Acti_Id=t.Mkt_Acti_Id "+
					 " left join ocrm_f_ci_belong_custmgr cus "+
					 " on cus.cust_id = t.cust_id "+
					 " and cus.main_type = '1' where t.Mkt_Acti_Id = '"+idStr+"' ";
					 stat.executeUpdate(kindSql);
				} catch (Exception e) {
					e.printStackTrace();
				} finally{
					JdbcUtil.close(null, stat, conn);
				}
		}
		addActionMessage(" lookupMapping removed successfully");
		return "success";
    }
    //用于其他页面调用生成营销活动的情况   在基本信息保存之后会调用本方法处理相关客户信息及产品信息的保存
    public void saveCustAndProd(){
    	ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String id = request.getParameter("id");//活动id
		String prodIdStrs = request.getParameter("prodIdStrs"); //关联产品信息
		String ifGroup = request.getParameter("ifGroup");//是否客户群创建
		String ifProd = request.getParameter("ifProd");//是否客户群创建
		//处理目标客户
		if("true".equals(ifGroup)){
			String groupId = request.getParameter("groupId");
			saveCust(id,groupId);
		}else{
			String custIdStrs = request.getParameter("custIdStrs");
			List<Object[]>  listall = null;
			listall = addmarketprodservice.getBaseDAO().findByNativeSQLWithIndexParam(" select " +
					"cust_id,cust_name  from  ACRM_F_CI_CUSTOMER where cust_id in ('"+custIdStrs.replaceAll(",", "','")+"')");
			String custId = "";
			String custName = "";
			if(listall != null && listall.size()>0){
				for(int i=0;i<listall.size();i++){
					Object[] o1 = listall.get(i);
					custId = (o1[0]==null)?"":o1[0].toString();
					custName = (o1[1]==null)?"":o1[1].toString();
					OcrmFMkActiCustomer ocrmfmkacticustomer = new OcrmFMkActiCustomer();
					ocrmfmkacticustomer.setAimCustId(null);
					ocrmfmkacticustomer.setCustId(custId);//客户编号
					ocrmfmkacticustomer.setCustName(custName);//客户名称
					ocrmfmkacticustomer.setCreateDate(new Date());//创建时间
					ocrmfmkacticustomer.setProgressStep("0");//定义进展阶段为 未开始
					if("true".equals(ifProd))
						ocrmfmkacticustomer.setAimCustSource("03");
					else
						ocrmfmkacticustomer.setAimCustSource("01");
					ocrmfmkacticustomer.setCreateUser(auth.getUserId());//创建人
					ocrmfmkacticustomer.setMktActiId(BigDecimal.valueOf(Long.parseLong(id)));//营销活动编号
					addmarketprodservice.saveData(ocrmfmkacticustomer);
			}
			}
			
		}
		//处理关联产品
		List<Object[]>  listall = null;
		listall = addmarketprodservice.getBaseDAO().findByNativeSQLWithIndexParam(" select " +
				"PRODUCT_ID,PROD_NAME  from  Ocrm_f_Pd_Prod_Info where PRODUCT_ID in ('"+prodIdStrs.replaceAll(",", "','")+"')");
		String prodId = "";
		String prodName = "";
		if(listall != null && listall.size()>0){
			for(int i=0;i<listall.size();i++){
				Object[] o1 = listall.get(i);
				prodId = (o1[0]==null)?"":o1[0].toString();
				prodName = (o1[1]==null)?"":o1[1].toString();
				OcrmFMkActiProduct prod = new OcrmFMkActiProduct(); 
				prod.setCreateDate(new Date());
				prod.setCreateUser(auth.getUserId());
				prod.setProductId(BigDecimal.valueOf(Long.parseLong(prodId)));
				prod.setProductName(prodName);
				prod.setMktActiId(BigDecimal.valueOf(Long.parseLong(id)));
				
				addmarketprodservice.saveData(prod);

		}
		}
    }
    
    /**
     * 客户群生成营销活动时将客户群客户添加到目标
     */
    public void saveCust(String id ,String groupId){
		List<Object[]> list = addmarketprodservice.getBaseDAO().findByNativeSQLWithIndexParam(" select  id,CUST_FROM,GROUP_MEMBER_TYPE from " +
				"OCRM_F_CI_BASE where id='"+groupId+"'");
		
		List<Object[]>  listall = null;
		if(list!=null&&list.size()>0){
			Object[] o = list.get(0);
			String custForm = (o[1]==null)?"":o[1].toString();
			String custType = (o[2]==null)?"":o[2].toString();
			if("2".equals(custForm)){//动态客户群，需要通过客户群筛选条件查询客户
				List<Object[]> list1 = addmarketprodservice.getBaseDAO().findByNativeSQLWithIndexParam(" select ss_col_item,ss_col_op,ss_col_value,ss_col_join " +
						"from OCRM_F_A_SS_COL where ss_id=(select id from OCRM_F_CI_BASE_SEARCHSOLUTION where group_id='"+groupId+"')");
				if(list1!=null&&list1.size()>0){
					JSONArray jaCondition = new JSONArray();
					String	radio = "true";
					for(int i=0;i<list1.size();i++){
						Object[] oo = list1.get(i);
						radio = (oo[3]==null)?"":oo[3].toString();
						Map map = new HashMap<String,Object>();
		    			map.put("ss_col_item", (oo[0]==null)?"":oo[0].toString());
		    			map.put("ss_col_op", (oo[1]==null)?"":oo[1].toString());
		    			map.put("ss_col_value", (oo[2]==null)?"":oo[2].toString());
						jaCondition.add(map);
					}
					//客户筛选
					String res = targetCusSearchService.generatorSql(jaCondition,radio);
					 if("1".equals(custType)||"2".equals(custType))//客户群的类别有要求 2：对私，1：对公
						 res +=" and custinfo.CUST_TYPE='"+custType+"'";
					listall = addmarketprodservice.getBaseDAO().findByNativeSQLWithIndexParam(" select " +
							"cust_id,cust_name as cust_zh_name  from  ACRM_F_CI_CUSTOMER where cust_id in ( select t.cust_id from ("+res+") t)");
	    			
				}
			}else {
				//静态客户群，直接关联客户查询
				listall = addmarketprodservice.getBaseDAO().findByNativeSQLWithIndexParam(" select " +
							"a.cust_id,a.cust_zh_name  from ocrm_f_ci_relate_cust_base a where cust_base_id='"+groupId+"'");
			}
		
		}
		
		String custId = "";
		String custName = "";
		if(listall != null && listall.size()>0){
			for(int i=0;i<listall.size();i++){
				Object[] o1 = listall.get(i);
				custId = (o1[0]==null)?"":o1[0].toString();
				custName = (o1[1]==null)?"":o1[1].toString();
			OcrmFMkActiCustomer ocrmfmkacticustomer = new OcrmFMkActiCustomer();
			ocrmfmkacticustomer.setAimCustId(null);
			ocrmfmkacticustomer.setCustId(custId);//客户编号
			ocrmfmkacticustomer.setCustName(custName);//客户名称
			ocrmfmkacticustomer.setCreateDate(new Date());//创建时间
			ocrmfmkacticustomer.setProgressStep("0");//定义进展阶段为 未开始
			ocrmfmkacticustomer.setAimCustSource("02");//定义目标客户来源为客户群
			ocrmfmkacticustomer.setCreateUser(auth.getUserId());//创建人
			ocrmfmkacticustomer.setMktActiId(BigDecimal.valueOf(Long.parseLong(id)));//营销活动编号
			addmarketprodservice.saveData(ocrmfmkacticustomer);
		}
		}
    }
   
    /**
   	 * 发起工作流
   	 * */
   	public void applySave() throws Exception{
   	  	ActionContext ctx = ActionContext.getContext();
   		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
   		HttpServletResponse response = (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
   		String requestId =  request.getParameter("instanceid");
   		String name =  request.getParameter("name");
   		String mktActiMode =  request.getParameter("mktActiMode");
   		
   		String nextNode = "134_a4";
   		Map<String, Object> paramMap = new HashMap<String, Object>();
   		if("1".equals(mktActiMode)){    // 费用来源 总行
   			paramMap.put("flag", "1");
   			
   		}else if("2".equals(mktActiMode)){   //费用来源 分支行
   			paramMap.put("flag", "0");
   			
   		}
   		
   	 List<?> listr = auth.getRolesInfo();//获得的角色信息是当前角色
	 for(Object m:listr){
		Map<?, ?> map = (Map<?, ?>)m;//map自m引自list，ROLE_CODE为键, R000为值
		paramMap.put("role", map.get("ROLE_CODE"));
		if( "R303".equals(map.get("ROLE_CODE"))&&"1".equals(paramMap.get("flag"))){//RM个金   费用来源 总行 
			nextNode = "134_a8";
			continue ;
		}else if( "R303".equals(map.get("ROLE_CODE"))&&"0".equals(paramMap.get("flag"))){//RM个金    费用来源 分支行
			nextNode = "134_a4";
			continue ;
		}else if("R310".equals(map.get("ROLE_CODE"))&&"1".equals(paramMap.get("flag"))){//AO主管发起  费用来源 总行
			nextNode = "134_a9";
			continue ;
		}else if("R310".equals(map.get("ROLE_CODE"))&&"0".equals(paramMap.get("flag"))){//AO主管发起   费用来源 分支行
			nextNode = "134_a5";
			continue ;
		}
	 }
   		
   		
   		String jobName = "营销活动_"+name;//自定义流程名称
   		int times = 0;
   		List list = marketActivityService.getBaseDAO().findByNativeSQLWithIndexParam(" select * from WF_MAIN_RECORD where instanceid like 'MKTACT_"+requestId+"%'");
   		if(list!= null && list.size()>0)
   			times = list.size();
   		String instanceid = "MKTACT_"+requestId+"_"+times;//此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
   		marketActivityService.initWorkflowByWfidAndInstanceid("134", jobName, paramMap, instanceid);//调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
   		Long idLong = new Long(Integer.parseInt(requestId));
   		marketActivityService.setState(idLong);
   		response.getWriter().write("{\"instanceid\":\""+instanceid+"\",\"currNode\":\"134_a3\",\"nextNode\":\""+nextNode+"\"}");
   		response.getWriter().flush();
   	}
   	
   	/**
   	 * 获取营销活动编号下一个行号
   	 */
   	public void getlargestNumberByorgid() {
   		try {
   			ActionContext ctx = ActionContext.getContext();
   			request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
   			String orgId = request.getParameter("orgId");
   			StringBuilder sb = new StringBuilder(); 
   			sb.append("  select  case when  max(TO_NUMBER(substr(MKT_ACTI_CODE, 12))) is null then 0 else max(TO_NUMBER(substr(MKT_ACTI_CODE, 12)))  end  larges  from OCRM_F_MK_MKT_ACTIVITY where substr(MKT_ACTI_CODE, 9, 3)  = '"+orgId+"'  ");
   		  Connection  connection=null;
    		Statement stmt=null;
    		ResultSet result=null;
    		String larges="";
     	try{
     		  connection = ds.getConnection();
  		 stmt = connection.createStatement();
     		 result = stmt.executeQuery(sb.toString());
     		while(result.next()){
     			larges=result.getString("larges");
     		}	
     	}catch(Exception e){
     		e.printStackTrace();
     	}finally{
     		JdbcUtil.close(result, stmt, connection);
     	}
     	 Map<String,Object> maps = new HashMap<String, Object>();
 		maps.put("larges", larges);
 		this.setJson(maps);
   		} catch (Exception e) {
   			e.printStackTrace();
   		}
      }
   	
   	/**
   	 * 总行新增营销活动直接同步到数据字典
   	 * @param sql
   	 */
    public void updateMarketActivityql(){
       	Connection  connection=null;
       	Statement stmt=null;
       	ResultSet result=null;
       	String sql="";
       	try{
       		ActionContext ctx = ActionContext.getContext();
   			request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
   			String mktActiId = request.getParameter("mktActiId");
   			String s2= request.getParameter("mktActiCode")!=null?request.getParameter("mktActiCode"):"";
			String s3= request.getParameter("mktActiName")!=null?request.getParameter("mktActiName"):"";
			String s4= request.getParameter("actiRemark")!=null?request.getParameter("actiRemark"):"";
			List<Object> list=getResultStr("select f_code,F_VALUE from ocrm_sys_lookup_item where f_code='"+s2+"' ");
			if(list!=null&&list.size()>0){
				sql=" update ocrm_sys_lookup_item set F_VALUE='"+s3+"' ,f_comment='"+s4+"' where f_code='"+s2+"' ";
			}else{
				sql =" insert into ocrm_sys_lookup_item(f_id,f_code,F_VALUE,f_comment,f_lookup_id) values('"+mktActiId+"','"+s2+"','"+s3+"','"+s4+"','XD000353')";
			}
			
      				 connection = ds.getConnection();
      				 connection.setAutoCommit(false);
      				 stmt = connection.createStatement();
      				 stmt.executeUpdate(sql);
      				 connection.commit();
      		}catch(Exception e){
      			try {
    				connection.rollback();
    			} catch (SQLException e1) {
    				e1.printStackTrace();
    			}
      			e.printStackTrace();
      		}finally{
      			JdbcUtil.close(result, stmt, connection);
      		}
          }
       
    public List<Object> getResultStr(String sql){
       	List<Object> List = new ArrayList<Object>();
       	Connection  connection=null;
       	Statement stmt=null;
       	ResultSet result=null;
       	try{
      				 connection = ds.getConnection();
      				 stmt = connection.createStatement();
      				 result = stmt.executeQuery(sql);
      				String ResultStr="";
      			    while (result.next()){
      			    	ResultStr = result.getString(1);
      			    	List.add(ResultStr);
      			    }
      			 return List;
      		}catch(Exception e){
      			e.printStackTrace();
      		}finally{
      			JdbcUtil.close(result, stmt, connection);
      		}
    		return null;
          }
    
	//制作word报告
	public void createWordReport() throws IOException{
		ActionContext ctx = ActionContext.getContext();
   	    request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
   	    
   	    String mktActiId = request.getParameter("mktActiId");
   	    String report = request.getParameter("reportType");
   	    String username = request.getParameter("username");
   	    String createDate = request.getParameter("createDate");
   	    
   	    configuration = new Configuration();  
		configuration.setDefaultEncoding("UTF-8");  
		configuration.setClassForTemplateLoading(this.getClass(), "template");  //FTL文件所存在的位置
		Template t=null; 
		Writer out = null;  
		
		Map<String,Object> dataMap=new HashMap<String,Object>();  
		dataMap.put("username", username);  
		dataMap.put("createDate", createDate);  
		
		List<Object[]> list = null;
		StringBuffer sb=new StringBuffer();
		sb.append(" select  M.MKT_ACTI_ID, ");
		sb.append(" substr(M.MKT_ACTI_CODE, 0,8) CODE, ");
		sb.append(" (select org.org_name from ADMIN_AUTH_ORG org where org.org_id=substr(M.MKT_ACTI_CODE, 9, 3))  CODE_F_NAME, ");
		sb.append(" substr(M.MKT_ACTI_CODE, 12)  LARGEST_NUMBER, ");
		sb.append(" M.MKT_ACTI_CODE, ");
		sb.append(" M.MKT_ACTI_NAME, ");
		sb.append(" (select ol.f_value from ocrm_sys_lookup_item ol where ol.f_lookup_id='MKT_CHANEL' and ol.f_code=M.MKT_CHANEL) MKT_CHANEL, ");
		sb.append(" (select ol.f_value from ocrm_sys_lookup_item ol where ol.f_lookup_id='ACTI_TYPE' and ol.f_code=M.MKT_ACTI_TYPE) MKT_ACTI_TYPE, ");
		sb.append(" (select ol.f_value from ocrm_sys_lookup_item ol where ol.f_lookup_id='MKT_TARGET_CUSTOMER' and ol.f_code=M.MKT_ACTI_STAT) MKT_ACTI_STAT, ");
		sb.append(" (select ol.f_value from ocrm_sys_lookup_item ol where ol.f_lookup_id='MKT_COST_SOURCES' and ol.f_code=M.MKT_ACTI_MODE) MKT_ACTI_MODE, ");
		sb.append(" M.MKT_ACTI_COST, ");
		sb.append(" M.PSTART_DATE, ");
		sb.append(" M.PEND_DATE, ");
		sb.append(" M.CREATE_DATE, ");
		sb.append(" M.CREATE_USER, ");
		sb.append(" O.ORG_NAME,    "); 
		sb.append(" C.USER_NAME,   ");
		sb.append(" (select ol.f_value from ocrm_sys_lookup_item ol where ol.f_lookup_id='MACTI_APPROVE_STAT' and ol.f_code=M.MKT_APP_STATE) MKT_APP_STATE, ");
		sb.append(" M.UPDATE_DATE,   ");
		sb.append(" M.UPDATE_USER,   ");
		sb.append(" M.MKT_ACTI_ADDR, ");
		sb.append(" M.MKT_ACTI_CONT, ");
		sb.append(" M.ACTI_CUST_DESC, ");
		sb.append(" M.ACTI_OPER_DESC, ");
		sb.append(" M.ACTI_PROD_DESC, ");
		sb.append(" M.ACTI_REMARK ");
		sb.append(" from OCRM_F_MK_MKT_ACTIVITY M ");
		sb.append(" left join ADMIN_AUTH_ACCOUNT C on M.Create_User=C.ACCOUNT_NAME ");
		sb.append(" left join ADMIN_AUTH_ORG  O on M.Create_Org=O.ORG_ID ");
		sb.append(" where M.MKT_ACTI_ID='"+mktActiId+"' ");
		list = marketActivityService.getBaseDAO().findByNativeSQLWithIndexParam(sb.toString());
			if(list != null && list.size()>0){
				Object[] o = list.get(0);
				dataMap.put("MKT_ACTI_ID", o[0]!=null?o[0].toString():"");
				dataMap.put("CODE", o[1]!=null?o[1].toString():"");
				dataMap.put("CODE_F_NAME", o[2]!=null?o[2].toString():"");
				dataMap.put("LARGEST_NUMBER", o[3]!=null?o[3].toString():"");
				dataMap.put("MKT_ACTI_CODE", o[4]!=null?o[4].toString():"");
				dataMap.put("MKT_ACTI_NAME", o[5]!=null?o[5].toString():"");
				dataMap.put("MKT_CHANEL", o[6]!=null?o[6].toString():"");
				dataMap.put("MKT_ACTI_TYPE", o[7]!=null?o[7].toString():"");
				dataMap.put("MKT_ACTI_STAT", o[8]!=null?o[8].toString():"");
				dataMap.put("MKT_ACTI_MODE", o[9]!=null?o[9].toString():"");
				dataMap.put("MKT_ACTI_COST", o[10]!=null?o[10].toString():"");
				dataMap.put("PSTART_DATE", o[11]!=null?o[11].toString():"");
				dataMap.put("PEND_DATE", o[12]!=null?o[12].toString():"");
				dataMap.put("CREATE_DATE", o[13]!=null?o[13].toString():"");
				dataMap.put("CREATE_USER", o[14]!=null?o[14].toString():"");
				dataMap.put("ORG_NAME", o[15]!=null?o[15].toString():"");
				dataMap.put("USER_NAME", o[16]!=null?o[16].toString():"");
				dataMap.put("MKT_APP_STATE", o[17]!=null?o[17].toString():"");
				dataMap.put("UPDATE_DATE", o[18]!=null?o[18].toString():"");
				dataMap.put("UPDATE_USER", o[19]!=null?o[19].toString():"");
				dataMap.put("MKT_ACTI_ADDR", o[20]!=null?o[20].toString():"");
				dataMap.put("MKT_ACTI_CONT", o[21]!=null?o[21].toString():"");
				dataMap.put("ACTI_CUST_DESC", o[22]!=null?o[22].toString():"");
				dataMap.put("ACTI_OPER_DESC", o[23]!=null?o[23].toString():"");
				dataMap.put("ACTI_PROD_DESC", o[24]!=null?o[24].toString():"");
				dataMap.put("ACTI_REMARK", o[25]!=null?o[25].toString():"");
				
			}else{
				dataMap.put("MKT_ACTI_ID", "");
				dataMap.put("CODE","");
				dataMap.put("CODE_F_NAME","");
				dataMap.put("LARGEST_NUMBER", "");
				dataMap.put("MKT_ACTI_CODE","");
				dataMap.put("MKT_ACTI_NAME","");
				dataMap.put("MKT_CHANEL", "");
				dataMap.put("MKT_ACTI_TYPE", "");
				dataMap.put("MKT_ACTI_STAT","");
				dataMap.put("MKT_ACTI_MODE","");
				dataMap.put("MKT_ACTI_COST","");
				dataMap.put("PSTART_DATE","");
				dataMap.put("PEND_DATE","");
				dataMap.put("CREATE_DATE", "");
				dataMap.put("CREATE_USER","");
				dataMap.put("ORG_NAME", "");
				dataMap.put("USER_NAME", "");
				dataMap.put("MKT_APP_STATE","");
				dataMap.put("UPDATE_DATE","");
				dataMap.put("UPDATE_USER","");
				dataMap.put("MKT_ACTI_ADDR","");
				dataMap.put("MKT_ACTI_CONT","");
				dataMap.put("ACTI_CUST_DESC","");
				dataMap.put("ACTI_OPER_DESC","");
				dataMap.put("ACTI_REMARK","");
				
			}
		
		
		try {  
			t = configuration.getTemplate(report+".ftl"); //文件名  
			String path = FileTypeConstance.getImportTempaltePath();//文件路径
//			String path=FileTypeConstance.getSystemProperty("sysExport");
			//创建文件
			File outFile = new File(path+File.separator+report+mktActiId+".doc");//文件名
		    out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),"UTF-8"));
		    t.process(dataMap, out);  //填写模板中的标签处
		} catch (Exception e) {  
			e.printStackTrace();  
		}finally{
			out.flush();
			out.close();
		}

	}
}