package com.yuchengtech.bob.action;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.yuchengtech.bcrm.custview.model.OcrmFCiBelongCustmgr;
import com.yuchengtech.bcrm.custview.model.OcrmFCiBelongOrg;
import com.yuchengtech.bob.importimpl.ImportInterface;
import com.yuchengtech.bob.upload.FileTypeConstance;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;
import com.yuchengtech.trans.socket.NioClient;

/**
 * 潜在客户导入
 * update 调用接口：
 * 2014-11-01
 */
public class LantentCustomerImport  implements ImportInterface  {
	private static Logger log = Logger.getLogger(LantentCustomerImport.class);
    // 释放连接
	public static void free(ResultSet rs, Statement st) {
		try {
			if (rs != null) {
				rs.close(); // 关闭结果集
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null) {
					st.close(); // 关闭Statement
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

    // 通过结果集元数据封装List结果集
	public static List<Map<String, Object>> read(Connection conn, String sql) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = ps.getMetaData();
			// 取得结果集列数
			int columnCount = rsmd.getColumnCount();
			List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
			Map<String, Object> data = null;
			// 循环结果集
			while (rs.next()) {
				data = new HashMap<String, Object>();
				// 每循环一条将列名和列值存入Map
				for (int i = 1; i < columnCount+1; i++) {
					data.put(rsmd.getColumnLabel(i),
							rs.getObject(rsmd.getColumnLabel(i)));
				}
				// 将整条数据的Map存入到List中
				datas.add(data);
			}
			return datas;
		} catch (Exception e) {
			throw new RuntimeException();
		} finally {
			free(rs, ps);
		}
	}
	//公用的保存方法
	public void saveData(Connection conn,String sql){		
		Statement updateStmt=null;
		try {
			updateStmt = conn.createStatement();
			updateStmt.execute(sql);
			updateStmt.close();
		} catch (SQLException e) {			
			e.printStackTrace();
		}		
	}
	
	public void excute(Connection conn, String PKhead,AuthUser aUser) throws Exception {
		log.info("updateSQL: [LatentCustImport has been evoke!]");	
		Statement stm = null;
		Date now=new Date();
		SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd");
		List<?> li= aUser.getRolesInfo();//获取当前登陆人的角色
		boolean ifkejingl=false;//判断当前登陆人是否是客户经理角色			
		try{
			stm = conn.createStatement();
			stm.execute("update ACRM_F_CI_CUSTOMER_TEMP t set t.IDENT_TYPE = substr(t.ident_type, 0,instr(t.ident_type,'-')-1),t.cust_type = substr(t.cust_type, 0, instr(t.cust_type,'-')-1), t.source_channel = substr(t.source_channel, 0, instr(t.source_channel,'-')-1)");
			conn.setAutoCommit(false);									
			String sql = 
				"select *" +
				"      from ACRM_F_CI_CUSTOMER_TEMP t," + 
				"           (SELECT C.CUST_ID," + 
				"                   C.CUST_NAME," + 
				"                   C.IDENT_TYPE," + 
				"                   C.IDENT_NO," + 
				"                   C.CUST_STAT," + 
				"                   C.CUST_TYPE," + 
				"                   C.EN_NAME," + 
				"                   C.SHORT_NAME," + 
				"                   C.LINKMAN_NAME," + 
				"                   C.JOB_TYPE," + 
				"                   C.INDUST_TYPE," + 
				"                   T1.CONTMETH_INFO CALL_NO," + 
				"                   T2.CONTMETH_INFO CONTMETH_INFO" + 
				"              FROM ACRM_F_CI_CUSTOMER C" + 
				"              LEFT JOIN ACRM_F_CI_CONTMETH T1" + 
				"                ON T1.CUST_ID = C.CUST_ID" + 
				"               AND T1.CONTMETH_TYPE = '100'" + 
				"              LEFT JOIN ACRM_F_CI_CONTMETH T2" + 
				"                ON T2.CUST_ID = C.CUST_ID" + 
				"               AND T2.CONTMETH_TYPE = '202'" + 
				"             WHERE 1 = 1" + 
				"               AND C.POTENTIAL_FLAG = '1') t2" + 
				"     where 1 = 1" + 
				"       and t.cust_name = t2.cust_name" + 
				"       AND T.CUST_ID LIKE '"+PKhead+"%'"+
				"       and (t.LINKMAN_TEL = t2.call_no or t.LINKMAN_TEL = CONTMETH_INFO)";										
			Map map1=null;
			List list=read(conn,sql);
			StringBuffer sb=new StringBuffer();
			if(list.size()>0){
				for(int i=0;i<list.size();i++){
				 map1 = (Map) list.get(i);
				String line= map1.get("CUST_ID").toString();
				 sb.append(line.substring(8, line.length())+" , ");
				}					 
				aUser.putAttribute(BACK_IMPORT_ERROR, "EXCEL中第"+sb.toString()+"行数据在已经存在，请删除第"+sb.toString()+"行重新导入");												
				throw new BizException(1, 0, "10001", "请清除已存在的用户");
			}					
	       String custZhName="";
           String custTyp="";
           String linkUser="";
           String zipcode="";
           String custEnName="";
           String certType="";
           String linkPhone="";
           String contmethInfo="";             
           String addr="";
           String otherName="";
           String certNum="";
           String jobType="";
           String industType="";                     
		   String tempSql =
		   "select CUST_NAME," +
		   "       CUST_TYPE," + 
		   "       LINKMAN_NAME," + 
		   "       PORT_NO," + 
		   "       EN_NAME," + 
		   "       IDENT_TYPE," + 
		   "       LINKMAN_TEL," + 
		   "       TELEPHONE_NO," + 
		   "       COMMU_ADDR," + 
		   "       SHORT_NAME," + 
		   "       IDENT_NO" + 
		   "  from ACRM_F_CI_CUSTOMER_TEMP where CUST_ID like '"+PKhead+"%'";			   
		   //"select t.* from ACRM_F_CI_CUSTOMER_TEMP  t where t.CUST_ID like '"+PKhead+"%'";////,ACRM_F_CI_CONTMETH t2   where t.cust_id=t2.cust_id  and  t.cust_name='"+TcustName+"' and t2.contmeth_info ='"+TTeleNo+"'";						 
		    List mycustList= read(conn, tempSql);			  
			Map dataMap = new HashMap();
			for(int i=0;i<mycustList.size();i++){ 
				 dataMap=(Map) mycustList.get(i);
				 custZhName=dataMap.get("CUST_NAME")==null?"":dataMap.get("CUST_NAME").toString();
				 custTyp=dataMap.get("CUST_TYPE")==null?"":dataMap.get("CUST_TYPE").toString();
				 linkUser=dataMap.get("LINKMAN_NAME")==null?"":dataMap.get("LINKMAN_NAME").toString();
				 zipcode=dataMap.get("PORT_NO")==null?"":dataMap.get("PORT_NO").toString();
				 custEnName=dataMap.get("EN_NAME")==null?"":dataMap.get("EN_NAME").toString();
				 certType=dataMap.get("IDENT_TYPE")==null?"":dataMap.get("IDENT_TYPE").toString();
				 linkPhone=dataMap.get("LINKMAN_TEL")==null?"":dataMap.get("LINKMAN_TEL").toString();
				 contmethInfo=dataMap.get("TELEPHONE_NO")==null?"":dataMap.get("TELEPHONE_NO").toString();			
				 addr=dataMap.get("COMMU_ADDR")==null?"":dataMap.get("COMMU_ADDR").toString();				
				 otherName=dataMap.get("SHORT_NAME")==null?"":dataMap.get("SHORT_NAME").toString();
				 certNum=dataMap.get("IDENT_NO")==null?"":dataMap.get("IDENT_NO").toString();
				 jobType="";//dataMap.get("JOB_TYPE")==null?"":dataMap.get("JOB_TYPE").toString();
				 industType="";//dataMap.get("INDUST_TYPE")==null?"":dataMap.get("INDUST_TYPE").toString();			 
				 dataMap=(Map)mycustList.get(i);	
				 System.out.println("数据列表:"+mycustList);					 
				 save(conn,aUser,"", custZhName, custTyp, linkUser, zipcode, custEnName, certType, linkPhone, contmethInfo, addr, "", otherName, certNum, jobType, industType);				
				
			}			
//            //清除
//			String delSql = "delete from ACRM_F_CI_CUSTOMER_TEMP t where t.CUST_ID like '"+PKhead+"%'";
//			stm.execute(delSql);
//			conn.commit();
		}catch(Exception e){
			aUser.putAttribute(BACK_IMPORT_ERROR, e.getMessage());															
			conn.rollback();
		}finally{
			String delSql = "delete from ACRM_F_CI_CUSTOMER_TEMP t where t.CUST_ID like '"+PKhead+"%'";
			stm.execute(delSql);
			conn.commit();
			try {
				stm.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	//创建潜在客户时 按照 客户名称+手机号的方式（行方提出的要求）判断该客户是否在CRM中存在。
	public  List queryExsitTempInCrm(Connection conn,String custName,String TeleNo){
		String TcustName=custName.trim();
		String TTeleNo="";
		if(TeleNo!=null){
		 TTeleNo=TeleNo.trim();
		}else{
			 TTeleNo="";
		}
		String tempSql = "select * from ACRM_F_CI_CUSTOMER t,ACRM_F_CI_CONTMETH t2   where t.cust_id=t2.cust_id  and  t.cust_name='"+TcustName+"' and t2.contmeth_info ='"+TTeleNo+"'";				
		List  custList = read(conn,tempSql);				
	 return custList;	
	}	
	/**
	            一、新建一张表存放潜在客户扩展信息(存放目前模型表没有的字段)； 
		二、创建潜在客户时 按照 客户名称+手机号的方式（行方提出的要求）判断该客户是否在CRM中存在。
		A.如果存在，需判断该客户是正式客户还是潜在客户，若是潜在客户，则覆盖该条潜在客户信息，若已是正式客户，则不覆盖，也不创建。 
		B.如果不存在可有两种方式处理： 
		1、一种方式是对于三证齐全的客户立即调用ECIF接口，由ECIF生成客户号，返回给CRM，CRM将数据保存到客户表和扩展表中，但如果是导入创建，多次调用ECIF接口， 
		会存在效率问题，能否限制导入文件的大小		
		对于三证不齐全的客户则不调用ECIF接口，由CRM生成潜在客户号，保存到客户表和扩展表中。 
		三、三证不齐全的客户信息补录完全，点击生效（如：联动同步ECIF）按钮，调用ECIF接口生成客户号，将CRM中的客户表和扩展表中的CRM潜在客户号更新为ECIF客户号， 
		同时更新其他表中的CRM潜在客户号为ECIF客户号，需要确定更新哪些表 
		四、按上述，客户表中存在证件齐全客户和证件不齐全客户，所以晚间ETL时三证不齐全的客户需保留，不能删除； 
		附：后续需要改造的内容： 
		1、CRM页面添加功能，潜在客户录入/导入后，有相应功能（如：联动同步ECIF按钮），与ECIF同步，调用ECIF接口，生成有效的客户号，之后CRM根据ECIF返回的客户号，修改之前客户号。 
		2、ECIF开发潜在客户新增客户。 
		3、CRM后台ETL修改原先逻辑，针对客户证件不齐全的潜在客户，不删除（将原先truncate或delete全表的逻辑修改） 
		4、客户群导入/创建只能基于已有在正式客户或证件信息齐全的潜在客户。
	                  潜在客户创建action，操作前做唯一性校验，判定是否存在该类型的潜在客户，字段为证件类型和证件号码
	                  判定内容为：1是否存在符合校验条件的前潜在客户状态为【潜在】的客户，
	              2是否存在符合校验条件且潜在客户状态为【已转正式】的客户
	    if("01".equals(phoneArea)){
				callNo="+86"+linkPhone;//大陆加上86区号
			}else{
				callNo="+886"+linkPhone;//台湾加上886区号
			}
	   */
	public String save(Connection conn,AuthUser aUser,String tempCustId,String custZhName,String custTyp,String linkUser,String zipcode,String custEnName,String certType,
			String linkPhone,String contmethInfo,String addr,String custStat,String otherName,String certNum,String jobType,String industType){			
	        System.out.println("data->"+tempCustId+"*"+ custZhName+"*"+ custTyp+"*"+ linkUser+"*"+ zipcode+"*"+ custEnName+"*"+ certType+"*"+ linkPhone+"*"+ contmethInfo+"*"+ addr+"*"+ custStat+"*"+ otherName+"*"+ certNum+"*"+ jobType+"*"+ industType);					
	        String sign = null;
			OcrmFCiBelongOrg ocrmFCiBelongOrg = new OcrmFCiBelongOrg();
			OcrmFCiBelongCustmgr ocrmFCiBelongCustmgr = new OcrmFCiBelongCustmgr();
			String sql = "";
			String sql1 = "";
			String sql2 = "";	
			String sql6 ="";
			Date date1 = new Date();
			DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = format1.format(date1);
			date = "to_date('" + date + "','yyyy-mm-dd hh24:mi:ss')";
			String currenUserId = aUser.getUserId();
			String tempUnitId = aUser.getUnitId();
			String callNo = linkPhone;				
			if(industType==null){
				industType="";
			}
			try{
				//生成CUST_ID
				Random random1 = new Random();
				int sp=Math.abs(random1.nextInt());
				String result="crm"+sp;
				List custlist=queryExsitTempInCrm(conn,custZhName,linkPhone);
				//新增潜在客户：
				if(tempCustId==null||"".equals(tempCustId)){					
				   //1.该客户在CRM中存在					
				 if(custlist.size()>0){
				    Map map=(Map) custlist.get(0);
				   // 得到该用户 ： 潜在客户标记 0正式、1潜在判断该客户是正式客户还是潜在客户						
				   if("1".equals(map.get("POTENTIAL_FLAG").toString())){
				   //潜在客户 修改潜在客户信息 进行覆盖：						
					StringBuilder sb = new StringBuilder("update ACRM_F_CI_CUSTOMER set  ");
						  if(!"".equals(custZhName)||custZhName!=null){							  
							  sb.append("cust_name = '"+custZhName+"', " ) ;
						  }
						  if(!"".equals(custTyp)||custTyp!=null){							  
							  sb.append("cust_type = '"+custTyp+"' , ") ;
						  }
						  if(!"".equals(linkUser)||linkUser!=null){							  
							  sb.append("LINKMAN_NAME = '"+linkUser+"' ,") ;
						  }
						  if(!"".equals(custEnName)||custEnName!=null){							  
							  sb.append("en_name = '"+custEnName+"' , ") ;
						  }
						  if(!"".equals(certType)||certType!=null){							  
							  sb.append("ident_type = '"+certType+"', ") ;
						  }												  
						  if(!"".equals(otherName)||otherName!=null){							  
							  sb.append("SHORT_NAME = '"+otherName+"', ") ;
						  }
						  if(!"".equals(certNum)||certNum!=null){							  
							  sb.append("IDENT_NO = '"+certNum+"', ") ;
						  }						  
						  if(!"".equals(jobType)||jobType!=null){							  
							  sb.append("job_type = '"+jobType+"', ") ;
						  }						  
						  if(!"".equals(industType)||industType!=null){							  
							  sb.append("indust_type = '"+industType.trim()+"'  ") ;
						  }						  
						  sb.append("   where CUST_ID = '"+tempCustId+"'") ;					   
					      System.out.println(":"+sb.toString());					   
				          StringBuilder sb2 = new StringBuilder("update ACRM_F_CI_ADDRESS set  ");
					      if(!"".equals(zipcode)||zipcode!=null){							  
							  sb2.append("zipcode = '"+zipcode+"',  ") ;
						  }
					      if(!"".equals(addr)||addr!=null){							  
							  sb2.append("addr = '"+addr+"'") ;
						  }
					      sb2.append("   where CUST_ID = '"+tempCustId+"'") ;					   
					   
					//修改联系方式：座机号码：和手机号码：      
				    sql2="update ACRM_F_CI_CONTMETH c set c.contmeth_info = '"+contmethInfo+"' where c.CUST_ID = '"+tempCustId+"' and c.contmeth_type = '202'";				    
				    String sql3="update ACRM_F_CI_CONTMETH c set c.contmeth_info = '"+linkPhone+"' where c.CUST_ID = '"+tempCustId+"' and c.contmeth_type = '100'";				    				   
					Statement updateStmt = conn.createStatement();
					updateStmt.execute(sb.toString());
					updateStmt.close();					
					Statement updateStmt2 = conn.createStatement();
					updateStmt2.execute(sb2.toString());
					updateStmt2.close();					
					Statement updateStmtsql2 = conn.createStatement();
					updateStmtsql2.execute(sql2);
					updateStmtsql2.close();				   
					Statement updateStmtsql3 = conn.createStatement();
					updateStmtsql3.execute(sql3);
					updateStmtsql3.close();
			       }
				   }										
				   //2.该客户在CRM中不存在									  
				   if(custlist.size()==0){
				  	    // A三证齐全【页面传递过来判定】的客户立即调用ECIF接口，由ECIF生成客户号，返回给CRM，CRM将数据保存到客户表和扩展表中，但如果是导入创建，多次调用ECIF接口					 
				  		if(custZhName!=""&&certNum!=""&&certType!=""){						 
				  		 Map repPhone=null;
				  		 Map repComenth=null;				  		 
						 try {
							   if("".equals(contmethInfo)){
								   repPhone= CallIntrfacePhone(aUser,tempCustId,custZhName,custTyp,linkUser,zipcode,custEnName,certType,
		                                     linkPhone,contmethInfo,addr,custStat,otherName,certNum,jobType,industType);
							   }else{
								   repPhone= CallIntrfacePhone(aUser,tempCustId,custZhName,custTyp,linkUser,zipcode,custEnName,certType,
		                                     linkPhone,contmethInfo,addr,custStat,otherName,certNum,jobType,industType);
								   repComenth= CallIntrfaceComenth(aUser,tempCustId,custZhName,custTyp,linkUser,zipcode,custEnName,certType,
		                                     linkPhone,contmethInfo,addr,custStat,otherName,certNum,jobType,industType);
								   
							   }
									
								} catch (Exception e) {	
									 aUser.putAttribute(BACK_IMPORT_ERROR, "ECIF客户号未正常返回转入临时客户");
									 throw new BizException(1,0,"10001",":"+e.getMessage());
						  }finally{
							  //未正常返回的客户转入临时客户：
							  System.out.println("返回的客户号为：["+repPhone.get("custNo")+"]");
							  if(repPhone==null||"".equals(repPhone)){
							   sql = 
								   "insert into ACRM_F_CI_POT_CUS_COM" +
								   "  (cus_id," + 
								   "   cus_name," + 
								   "   CUST_TYPE," + 
								   "   ATTEN_NAME," + 
								   "   ZIPCODE," + 
								   "   EN_NAME," + 
								   "   CERT_TYPE," + 
								   "   CERT_CODE," + 
								   "   CALL_NO," + 
								   "   short_name," + 
								   "   CONTMETH_INFO," + 
								   "   CUST_STAT," + 
								   "   JOB_TYPE," + 
								   "   CUS_ADDR," + 
								   "   INDUST_TYPE)" + 
								   "values" + 
								   "  (" + 
								   "  '"+result+"'," + 
								   "  '"+custZhName+"'," + 
								   "  '"+custTyp+"'," + 
								   "  '"+linkUser+"'," + 
								   "  '"+zipcode+"'," + 
								   "  '"+custEnName+"'," + 
								   "  ''," + 
								   "  ''," + 
								   "  '"+callNo+"'," + 
								   "  '"+otherName+"'," + 
								   "  '"+contmethInfo+"'," + 
								   "  '',"  + 
								   "  '"+jobType+"'," + 
								   "  '"+addr+"'," + 
								   "  '"+industType+"'" + 
								   "   )";
		                    System.out.println("转入临时客户:"+sql);                   
		                    Statement updateSt = conn.createStatement();
		                    updateSt.execute(sql);
		                    updateSt.close();	
							}
						  }			
                         System.out.println("接口响应的客户号："+repPhone.get("custNo"));                           
				  		 sql = "insert into ACRM_F_CI_CUSTOMER(cust_id,cust_name,cust_type,linkman_name,en_name,ident_type,cust_stat,short_name,ident_no,create_teller_no,create_date,create_branch_no,POTENTIAL_FLAG," +
						 		"JOB_TYPE,indust_Type)" 
			    	  			+" values('"+repPhone.get("custNo")+"','"+custZhName+"','"+custTyp+"','"+linkUser+"','"+custEnName+"','"+certType+"','','"+otherName+"','"+certNum+"','"+currenUserId+"',"+date+",'"+tempUnitId+"','1'," +
			    	  					"'"+jobType+"','"+industType+"')";				  		 
						 sql1 = "insert into ACRM_F_CI_ADDRESS(addr_id,cust_id,zipcode,addr,addr_type) values('"+repPhone.get("addrId")+"','"+repPhone.get("custNo")+"','"+zipcode+"','"+addr+"','02')";						
						 if("".equals(contmethInfo)){
							 sql6 = "insert into ACRM_F_CI_CONTMETH(contmeth_id,cust_id,contmeth_type,contmeth_info) values('"+repPhone.get("contmethId")+"','"+repPhone.get("custNo")+"','100','"+linkPhone+"')";							
						 }else{
							 sql2 = "insert into ACRM_F_CI_CONTMETH(contmeth_id,cust_id,contmeth_type,contmeth_info) values('"+repPhone.get("contmethId")+"','"+repPhone.get("custNo")+"','202','"+contmethInfo+"')";
							 sql6 = "insert into ACRM_F_CI_CONTMETH(contmeth_id,cust_id,contmeth_type,contmeth_info) values('"+repComenth.get("contmethId")+"','"+repComenth.get("custNo")+"','100','"+linkPhone+"')";														 
						 }
						 //插入crm 证件表
						 String identSql="insert into acrm_f_ci_cust_identifier (ident_id,cust_id,ident_type,ident_no,ident_cust_name,last_update_sys) values('"+repPhone.get("identId")+"','"+repPhone.get("custNo")+"','"+certType+"','"+certNum+"','"+custZhName+"','CRM')";						 
						 String custsql="";
						 if("2".equals(custTyp)){
							 custsql="insert into ACRM_F_CI_PERSON (cust_id,personal_name,post_addr,last_update_sys) values('"+repPhone.get("custNo")+"','"+custZhName+"','"+addr+"','CRM')";
						 }else{
							 custsql="insert into ACRM_F_CI_ORG (cust_id,cust_name,org_addr) values('"+repPhone.get("custNo")+"','"+custZhName+"','"+addr+"')";
						 }
						 
						    Statement updateStmtsql = conn.createStatement();
						    updateStmtsql.execute(sql);
						    updateStmtsql.close();							
							Statement updateStmtsql1 = conn.createStatement();
							updateStmtsql1.execute(sql1);
							updateStmtsql1.close();														
							Statement updateStmtsql2 = conn.createStatement();
							updateStmtsql2.execute(sql2);
							updateStmtsql2.close();						   
							Statement updateStmtsql6 = conn.createStatement();
							updateStmtsql6.execute(sql6);
							updateStmtsql6.close();	
							//证件表保存数据
							Statement updateidentSql = conn.createStatement();
							updateidentSql.execute(identSql);
							updateidentSql.close();							
							Statement ccustsql = conn.createStatement();
							ccustsql.execute(custsql);
							ccustsql.close();																						
							List li= aUser.getRolesInfo();
							boolean ifkejingl=false;//判断当前登陆人是否是客户经理角色
							Date now=new Date();
							SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd");
							Statement stm6 = conn.createStatement();
							Statement stm7 = conn.createStatement();
							String delSql3="";
							String delSql4="";	
							for(Object m:li){
								Map map123 = (Map)m;
								//个金ARM或法金ARM为客户经理
								if("R302".equals(map123.get("ROLE_CODE")) || "R304".equals(map123.get("ROLE_CODE"))){
									ifkejingl=true;
									break;
								}
							}
							if(ifkejingl){//是客户经理时
							   delSql3=" INSERT INTO OCRM_F_CI_BELONG_CUSTMGR (ID,CUST_ID,MGR_ID,MAIN_TYPE,MAINTAIN_RIGHT," +//把导入的潜在客户归属到当前登录的人
									"CHECK_RIGHT,ASSIGN_USER,ASSIGN_USERNAME,ASSIGN_DATE,INSTITUTION,INSTITUTION_NAME,MGR_NAME)" +
									" VALUES(ID_SEQUENCE.nextval,'"+repPhone.get("custNo")+"','"+aUser.getUserId()+"','1'," +
									"'1','1','"+aUser.getUserId()+"','"+aUser.getUsername()+"',to_date('"+f.format(now)+"','yyyy-MM-dd'),'"+aUser.getUnitId()+"'," +
									"'"+aUser.getUnitName()+"','"+aUser.getUsername()+"')";
											
							}else{//不是客户经理时
								delSql3=" INSERT INTO OCRM_F_CI_BELONG_CUSTMGR (ID,CUST_ID,MGR_ID,MAIN_TYPE,MAINTAIN_RIGHT," +//把导入的潜在客户归属到当前登录的人
								"CHECK_RIGHT,ASSIGN_USER,ASSIGN_USERNAME,ASSIGN_DATE,INSTITUTION,INSTITUTION_NAME,MGR_NAME)" +
								" VALUES(ID_SEQUENCE.nextval,'"+repPhone.get("custNo")+"','"+"VM"+aUser.getUserId()+"','1'," +
								"'1','1','"+aUser.getUserId()+"','"+aUser.getUsername()+"',to_date('"+f.format(now)+"','yyyy-MM-dd'),'"+aUser.getUnitId()+"'," +
								"'"+aUser.getUnitName()+"','"+aUser.getUnitName()+"虚拟客户经理"+"')";
							}
							//归属机构保存
							delSql4=" INSERT INTO OCRM_F_CI_BELONG_ORG (ID,CUST_ID,INSTITUTION_CODE,INSTITUTION_NAME,MAIN_TYPE," +//把导入的潜在客户归属到当前登录的人
									"ASSIGN_USER,ASSIGN_USERNAME,ASSIGN_DATE)" +
								    " VALUES(ID_SEQUENCE.nextval,'"+repPhone.get("custNo")+"','"+aUser.getUnitId()+"','"+aUser.getUnitName()+"'," +
								    "'1','"+aUser.getUserId()+"','"+aUser.getUsername()+"',to_date('"+f.format(now)+"','yyyy-MM-dd'))";
						    stm6.execute(delSql3);
						    stm7.execute(delSql4);
							stm6.close();
							stm7.close();
								
							
				   }else{ 					   								   
				     // B三证不齐全的客户则不调用ECIF接口，由CRM生成潜在客户号，扩展表中  					   
					   sql = 
						   "insert into ACRM_F_CI_POT_CUS_COM" +
						   "  (cus_id," + 
						   "   cus_name," + 
						   "   CUST_TYPE," + 
						   "   ATTEN_NAME," + 
						   "   ZIPCODE," + 
						   "   EN_NAME," + 
						   "   CERT_TYPE," + 
						   "   CERT_CODE," + 
						   "   CALL_NO," + 
						   "   short_name," + 
						   "   CONTMETH_INFO," + 
						   "   CUST_STAT," + 
						   "   JOB_TYPE," + 
						   "   CUS_ADDR," + 
						   "   INDUST_TYPE)" + 
						   "values" + 
						   "  (" + 
						   "  '"+result+"'," + 
						   "  '"+custZhName+"'," + 
						   "  '"+custTyp+"'," + 
						   "  '"+linkUser+"'," + 
						   "  '"+zipcode+"'," + 
						   "  '"+custEnName+"'," + 
						   "  '"+certType+"'," + 
						   "  '"+certNum+"'," + 
						   "  '"+callNo+"'," + 
						   "  '"+otherName+"'," + 
						   "  '"+contmethInfo+"'," + 
						   "  '',"  + 
						   "  '"+jobType+"'," + 
						   "  '"+addr+"'," + 
						   "  '"+industType+"'" + 
						   "   )";
                    System.out.println("新增证件不齐全:"+sql);                   
                    Statement updateSt = conn.createStatement();
                    updateSt.execute(sql);
                    updateSt.close();													  						  
					}				  		
				  }
				}else{					
		             /**
					 * 1:三证齐全的客户信息补录完全调用接口callInterface保存到正式库里面去 //修改潜在客户：客户信息补录完全
					 */					
					if(custZhName!=""&&certNum!=""&&certType!=""){							
						if(custlist.size()==0){ //不存在crm 						    
					  	  Map repPhone=null;
					  	  Map repComenth=null;						
						  try {
							  if("".equals(contmethInfo)){
								   repPhone= CallIntrfacePhone(aUser,tempCustId,custZhName,custTyp,linkUser,zipcode,custEnName,certType,
		                                     linkPhone,contmethInfo,addr,custStat,otherName,certNum,jobType,industType);
							   }else{
								   repPhone= CallIntrfacePhone(aUser,tempCustId,custZhName,custTyp,linkUser,zipcode,custEnName,certType,
		                                     linkPhone,contmethInfo,addr,custStat,otherName,certNum,jobType,industType);
								   repComenth= CallIntrfaceComenth(aUser,tempCustId,custZhName,custTyp,linkUser,zipcode,custEnName,certType,
		                                     linkPhone,contmethInfo,addr,custStat,otherName,certNum,jobType,industType);
								   
							   }	
							} catch (Exception e) {	
								 aUser.putAttribute(BACK_IMPORT_ERROR, "ECIF客户号未正常返回请联系管理员查看潜在客户接口");
								 throw new BizException(1,0,"10001",""+e.getMessage());
							}							
				  		  	//删除扩展表数据：							
							String delsql="update ACRM_F_CI_POT_CUS_COM c set c.del = '1' where c.CUS_ID = '"+tempCustId+"'";							
							Statement updateStmtsql7 = conn.createStatement();
							updateStmtsql7.execute(delsql);
							updateStmtsql7.close();											  			
							System.out.println("接口响应的客户号："+repPhone.get("custNo"));  	                         
					  		sql = "insert into ACRM_F_CI_CUSTOMER(cust_id,cust_name,cust_type,linkman_name,en_name,ident_type,cust_stat,short_name,ident_no,create_teller_no,create_date,create_branch_no,POTENTIAL_FLAG," +
							 		"JOB_TYPE,indust_Type)" 
				    	  			+" values('"+repPhone.get("custNo")+"','"+custZhName+"','"+custTyp+"','"+linkUser+"','"+custEnName+"','"+certType+"','','"+otherName+"','"+certNum+"','"+currenUserId+"',"+date+",'"+tempUnitId+"','1'," +
				    	  					"'"+jobType+"','"+industType+"')";
					  		 
							 sql1 = "insert into ACRM_F_CI_ADDRESS(addr_id,cust_id,zipcode,addr,addr_type) values('"+repPhone.get("addrId")+"','"+repPhone.get("custNo")+"','"+zipcode+"','"+addr+"','02')";							
							 if("".equals(contmethInfo)){
								 sql6 = "insert into ACRM_F_CI_CONTMETH(contmeth_id,cust_id,contmeth_type,contmeth_info) values('"+repPhone.get("contmethId")+"','"+repPhone.get("custNo")+"','100','"+linkPhone+"')";							
							 }else{
								 sql2 = "insert into ACRM_F_CI_CONTMETH(contmeth_id,cust_id,contmeth_type,contmeth_info) values('"+repPhone.get("contmethId")+"','"+repPhone.get("custNo")+"','202','"+contmethInfo+"')";
								 sql6 = "insert into ACRM_F_CI_CONTMETH(contmeth_id,cust_id,contmeth_type,contmeth_info) values('"+repComenth.get("contmethId")+"','"+repComenth.get("custNo")+"','100','"+linkPhone+"')";															 
							 }
							 //插入crm 证件表
							 String identSql="insert into acrm_f_ci_cust_identifier (ident_id,cust_id,ident_type,ident_no,ident_cust_name,last_update_sys) values('"+repPhone.get("identId")+"','"+repPhone.get("custNo")+"','"+certType+"','"+certNum+"','"+custZhName+"','CRM')";						 
							 String custsql="";
							 if("2".equals(custTyp)){
								 custsql="insert into ACRM_F_CI_PERSON (cust_id,personal_name,post_addr,last_update_sys) values('"+repPhone.get("custNo")+"','"+custZhName+"','"+addr+"','CRM')";
							 }else{
								 custsql="insert into ACRM_F_CI_ORG (cust_id,cust_name,org_addr) values('"+repPhone.get("custNo")+"','"+custZhName+"','"+addr+"')";
							 }
							 
							    Statement updateStmtsql = conn.createStatement();
							    updateStmtsql.execute(sql);
							    updateStmtsql.close();							
								Statement updateStmtsql1 = conn.createStatement();
								updateStmtsql1.execute(sql1);
								updateStmtsql1.close();														
								Statement updateStmtsql2 = conn.createStatement();
								updateStmtsql2.execute(sql2);
								updateStmtsql2.close();						   
								Statement updateStmtsql6 = conn.createStatement();
								updateStmtsql6.execute(sql6);
								updateStmtsql6.close();	
								//证件表保存数据
								Statement updateidentSql = conn.createStatement();
								updateidentSql.execute(identSql);
								updateidentSql.close();						
								Statement ccustsql = conn.createStatement();
								ccustsql.execute(custsql);
								ccustsql.close();								
					  }else{
					  /*
					  //存在crm												
					  String rep=CallIntrfaceUpdate(aUser,tempCustId,custZhName,custTyp,linkUser,zipcode,custEnName,certType,
                                linkPhone,contmethInfo,addr,custStat,otherName,certNum,jobType,industType);	
					  
					  if(rep==null){
						   aUser.putAttribute(BACK_IMPORT_ERROR, "ECIF客户号未正常返回请联系管理员查看潜在客户接口");
						   throw new BizException(1,0,"10001","与ECIF报文交易失败或超时,请稍后重试或联系管理员！");
						}
						String delsql="update ACRM_F_CI_POT_CUS_COM c set c.del = '1' where c.CUS_ID = '"+tempCustId+"'";
						
						Statement del = conn.createStatement();
						del.execute(delsql);
						del.close();						
					
						
						StringBuilder sb = new StringBuilder("update ACRM_F_CI_CUSTOMER set  ");
						  if(!"".equals(custZhName)||custZhName!=null){							  
							  sb.append("cust_name = '"+custZhName+"', ") ;
						  }
						  if(!"".equals(custTyp)||custTyp!=null){							  
							  sb.append("cust_type = '"+custTyp+"', ") ;
						  }
						  if(!"".equals(linkUser)||linkUser!=null){							  
							  sb.append("LINKMAN_NAME = '"+linkUser+"', ") ;
						  }
						  if(!"".equals(custEnName)||custEnName!=null){							  
							  sb.append("en_name = '"+custEnName+"', ") ;
						  }
						  if(!"".equals(certType)||certType!=null){							  
							  sb.append("ident_type = '"+certType+"', ") ;
						  }												  
						
						  if(!"".equals(otherName)||otherName!=null){							  
							  sb.append("SHORT_NAME = '"+otherName+"', ") ;
						  }
						  if(!"".equals(certNum)||certNum!=null){							  
							  sb.append("IDENT_NO = '"+certNum+"', ") ;
						  }
						  
						  if(!"".equals(jobType)||jobType!=null){							  
							  sb.append("job_type = '"+jobType+"', ") ;
						  }
						  
						  if(!"".equals(industType)||industType!=null){							  
							  sb.append("indust_type = '"+industType+"'") ;
						  }					 
						  sb.append("   where CUST_ID = '"+tempCustId+"'") ;					   
					      System.out.println(":"+sb.toString());					   
					      StringBuilder sb2 = new StringBuilder("update ACRM_F_CI_ADDRESS set  ");
					      if(!"".equals(zipcode)||zipcode!=null){							  
							  sb2.append("zipcode = '"+zipcode+"', ") ;
						  }
					      if(!"".equals(addr)||addr!=null){							  
							  sb2.append("addr = '"+addr+"'") ;
						  }
					      sb2.append("   where CUST_ID = '"+tempCustId+"'") ;					   
					      System.out.println("三证齐全"+sb2.toString());		
					     //判断座机号是否为空
					     String querySql="select * from  ACRM_F_CI_CONTMETH  t where t.CUST_ID = '"+tempCustId+"' and contmeth_type = '202' " ;    					  
					     List querySqlList = read(conn,querySql);
					     if(querySqlList.size()>0){
						 sql2="update ACRM_F_CI_CONTMETH c set c.contmeth_info = '"+contmethInfo+"' where c.CUST_ID = '"+tempCustId+"' and c.contmeth_type = '202'";	

							Statement update8 = conn.createStatement();
							update8.execute(sql2);
							update8.close();
					}else{					
						String sql5= "insert into ACRM_F_CI_CONTMETH(contmeth_id,cust_id,contmeth_type,contmeth_info) values(seq_contmeth_id.nextval,'"+tempCustId+"','202','"+contmethInfo+"')";							
						Statement update9 = conn.createStatement();
						update9.execute(sql5);
						update9.close();
				    }				   
				    String sql4="update ACRM_F_CI_CONTMETH c set c.contmeth_info = '"+linkPhone+"' where c.CUST_ID = '"+tempCustId+"' and c.contmeth_type = '100'";
					
				    
					Statement update1 = conn.createStatement();
					update1.execute(sb.toString());
					update1.close();						
				
					Statement update2 = conn.createStatement();
					update2.execute(sb2.toString());
					update2.close();
				    
					Statement update4 = conn.createStatement();
					update4.execute(sql4);
					update4.close();
				    
				    */
					}
					}else{
					//2:三证不齐全的客户信息没有补录完全修改但不调用接口	
						StringBuilder sb = new StringBuilder(
								"update ACRM_F_CI_POT_CUS_COM" +
								"   set  "+
								"       CUS_NAME = '"+custZhName+"'," + 
								"       CONTMETH_INFO = '"+contmethInfo+"'," + 
								"       JOB_TYPE = '"+jobType+"'," + 
								"       CERT_TYPE = '"+certType+"'," + 
								"       CERT_CODE = '"+certNum+"'," + 
								"       ZIPCODE = '"+zipcode+"'," + 
								"       EN_NAME = '"+custEnName+"'," + 
								"       INDUST_TYPE = '"+industType+"'," + 
								"       CALL_NO = '" +callNo+"'," + 
								"       SHORT_NAME = '"+otherName+"'," + 
								"       ATTEN_NAME = '"+linkUser+"'," + 
								"       CUST_TYPE = '"+custTyp+"'," + 
								"       CUS_ADDR = '"+addr+"'   "+
								"       where CUS_ID = '"+tempCustId+"'"
                            );						 						  
					    System.out.println("不调用接口"+sb.toString());							      					   
					    Statement update5 = conn.createStatement();
						update5.execute(sb.toString());
						update5.close();
					    
					}					
				}
			}catch(Exception ex){
				ex.printStackTrace();			
			}
		return sign;
	}
	

	public Map CallIntrfaceComenth(AuthUser auth,String tempCustId,String custZhName,String custTyp,String linkUser,String zipcode,String custEnName,String certType,
			String linkPhone,String contmethInfo,String addr,String custStat,String otherName,String certNum,String jobType,String industType) throws Exception{			
		SimpleDateFormat df8 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat df20 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		SimpleDateFormat df10 = new SimpleDateFormat("hhmmssSS");	
		
		String msg="";
		if("2".equals(custTyp)){
		//对于地址和证件要处理	    
	   StringBuffer reQxml=new StringBuffer();	   
	   String Hxml=
		"<?xml version=\"1.0\" encoding=\"GBK\"?>"+
		"<TransBody>" +
		"  <RequestHeader>" + 
		"    <ReqSysCd>CRM</ReqSysCd>" + 
		"    <ReqSeqNo>"+df20.format(new Date())+"</ReqSeqNo>" + 
		"    <ReqDt>"+df8.format(new Date())+"</ReqDt>" + 
		"    <ReqTm>"+df10.format(new Date())+"</ReqTm>" + 
		"    <DestSysCd>ECIF</DestSysCd>" + 
		"    <ChnlNo>82</ChnlNo>" + 
		"    <BrchNo>503</BrchNo>" + 
		"    <BizLine>209</BizLine>" + 
		"    <TrmNo>TRM10010</TrmNo>" + 
		"    <TrmIP>127.0.0.1</TrmIP>" + 
		"    <TlrNo>"+auth.getUserId()+"</TlrNo>" + 
		"  </RequestHeader>" + 
		"  <RequestBody>" + 
		"    <txCode>openPerAccountforCrm</txCode>" + 
		"    <txName>潜在个人客户开户</txName>" + 
		"    <authType>1</authType>" + 
		"    <authCode>1010</authCode>" ;
	   String perIdent="";
	   if("".equals(certType)||"".equals(certNum)){
		   perIdent=
				"  <perIdentifier>" + 
				"    <identType></identType>" + 
				"    <identNo></identNo>" + 
				"    <identCustName></identCustName>" + 
				"  </perIdentifier>"; 	  	
	   }else{
		   perIdent=
				"  <perIdentifier>" + 
				"    <identType>"+certType+"</identType>" + 
				"    <identNo>"+certNum+"</identNo>" + 
				"    <identCustName>"+custZhName+"</identCustName>" + 
				"  </perIdentifier>"; 
	   }
	   String contmeth="";
	   if("".equals(contmethInfo)){
		contmeth=
		"  <contmeth>" + 
		"  <contmethType>100</contmethType>" + 
		"  <contmethInfo>"+linkPhone+"</contmethInfo>" + 
		"  <contmethSeq>1</contmethSeq>" + 
		"  <isPriori>1</isPriori>" + 
		"    </contmeth>" ;			
	   }else{		   
		contmeth=
				" <contmeth>" + 
				" <contmethType>202</contmethType>" + 
				"    <contmethInfo>"+contmethInfo+"</contmethInfo>" + 
				"    <contmethSeq>2</contmethSeq>" + 
				"    <isPriori>0</isPriori>" + 
				"    </contmeth>" ;			   
	   }
	   String address="";
	   if("".equals(addr)){
		address=
	    "<address>" + 
		"<addr></addr>" + 
		"    <addrType></addrType>" + 
		"    <zipcode>"+zipcode+"</zipcode>" + 
		"</address>";
	   }else{
		   address=
			    "<address>" + 
				"<addr>"+addr+"</addr>" + 
				"    <addrType>02</addrType>" + 
				"    <zipcode>"+zipcode+"</zipcode>" + 
				"</address>";  
		   
	   }
	   String customer=	"";	
	   if("".equals(certType)||"".equals(certNum)){
	   customer="<customer>" + 
			"<custType>"+custTyp+"</custType>" + 
			"    <identType></identType>" + 
			"    <identNo></identNo>" + 
			"    <custName>"+custZhName+"</custName>" + 
			"    <enName>"+custEnName+"</enName>" + 
			"    <jobType>"+jobType+"</jobType>" + 
			"    <industType>"+industType+"</industType>" + 
			"    <potentialFlag>1</potentialFlag>" + 
			"    <linkmanName>"+linkUser+"</linkmanName>" + 
			"    <linkmanTel>"+linkPhone+"</linkmanTel>" + 
			"    <shortName>"+otherName+"</shortName>" + 
			"    <createDate>"+df8.format(new Date())+"</createDate>" + 
			"  </customer>" ;
	   }else{
		   customer="<customer>" + 
			"<custType>"+custTyp+"</custType>" + 
			"    <identType>"+certType+"</identType>" + 
			"    <identNo>"+certNum+"</identNo>" + 
			"    <custName>"+custZhName+"</custName>" + 
			"    <enName>"+custEnName+"</enName>" + 
			"    <jobType>"+jobType+"</jobType>" + 
			"    <industType>"+industType+"</industType>" + 
			"    <potentialFlag>1</potentialFlag>" + 
			"    <linkmanName>"+linkUser+"</linkmanName>" + 
			"    <linkmanTel>"+linkPhone+"</linkmanTel>" + 
			"    <shortName>"+otherName+"</shortName>" + 
			"    <createDate>"+df8.format(new Date())+"</createDate>" + 
			"  </customer>" ;     
	   }
	   String person=			
		" <person>" + 
		" <postAddr>"+addr+"</postAddr>" + 
		"    <personalName>"+custZhName+"</personalName>" + 
		"  </person>"+
		"  </RequestBody>" + 
		"</TransBody>";
	   
	   reQxml.append(Hxml);
	   reQxml.append(perIdent);
	   reQxml.append(contmeth);
	   reQxml.append(address);
	   reQxml.append(customer);
	   reQxml.append(person);	   
	   msg=reQxml.toString();	   
	  }else{
		  StringBuffer reQxmlorg=new StringBuffer();	  
	 String Hxml="<?xml version=\"1.0\" encoding=\"GBK\"?>"+
		"<TransBody>" +
  		"  <RequestHeader>" + 
  		"    <ReqSysCd>CRM</ReqSysCd>" + 
  		"    <ReqSeqNo>"+df20.format(new Date())+"</ReqSeqNo>" + 
  		"    <ReqDt>"+df8.format(new Date())+"</ReqDt>" + 
  		"    <ReqTm>"+df10.format(new Date())+"</ReqTm>" + 
  		"    <DestSysCd>ECIF</DestSysCd>" + 
  		"    <ChnlNo>82</ChnlNo>" + 
  		"    <BrchNo>503</BrchNo>" + 
  		"    <BizLine>209</BizLine>" + 
  		"    <TrmNo>TRM10010</TrmNo>" + 
  		"    <TrmIP>127.0.0.1</TrmIP>" + 
  		"    <TlrNo>"+auth.getUserId()+"</TlrNo>" + 
  		"  </RequestHeader>" + 
  		"  <RequestBody>" + 
  		"    <txCode>openOrgAccountforCrm</txCode>" + 
  		"    <txName>潜在机构客户开户</txName>" + 
  		"    <authType>1</authType>" + 
  		"    <authCode>1010</authCode>" ;
	 
	 String customer="";
	     if("".equals(certType)||"".equals(certNum)){
	       customer=
		   "<customer>" +
	       " <linkmanTel>"+linkPhone+"</linkmanTel>" + 
	       "<custType>"+custTyp+"</custType>" + 
	        " <identType></identType>" + 
			"<identNo></identNo>" + 
			" <custName>"+custZhName+"</custName>" +
			"    <shortName>"+otherName+"</shortName>" + 
			"    <enName>"+custEnName+"</enName>" + 
			"    <potentialFlag>1</potentialFlag>" + 
			"    <linkmanName>"+linkUser+"</linkmanName>" +
			" </customer>";
			}else{
		   customer=
		   "<customer>" +
	       " <linkmanTel>"+linkPhone+"</linkmanTel>" + 
	       "<custType>"+custTyp+"</custType>" + 
	        " <identType>"+certType+"</identType>" + 
			"<identNo>"+certNum+"</identNo>" + 
			" <custName>"+custZhName+"</custName>" +
			"    <shortName>"+otherName+"</shortName>" + 
			"    <enName>"+custEnName+"</enName>" + 
			"    <potentialFlag>1</potentialFlag>" + 
			"    <linkmanName>"+linkUser+"</linkmanName>" +
			" </customer>";	
			}
	     String contmeth="";	 
			contmeth=
	    	" <contmeth>" + 
	 		" <contmethType>202</contmethType>" + 
	 		"    <contmethInfo>"+contmethInfo+"</contmethInfo>" + 
	 		"    <contmethSeq>2</contmethSeq>" + 
	 		"    <isPriori>0</isPriori>" + 
	 		"  </contmeth>"; 	    	 
	   
	     String orgIdentifier="";
	     if("".equals(certType)||"".equals(certNum)){
	     orgIdentifier=
		  "   <orgIdentifier>" + 
	      "    <identType></identType>" + 
		  "    <identNo></identNo>"  +
		  "    <identCustName></identCustName>" +			
		  "   </orgIdentifier>";
		  }else{
			  orgIdentifier=
				  "   <orgIdentifier>" + 
			      "    <identType>"+certType+"</identType>" + 
				  "    <identNo>"+certNum+"</identNo>"  +
				  "    <identCustName>"+custZhName+"</identCustName>" +			
				  "   </orgIdentifier>"; 
			  
		  }
	     String address="";
	     if("".equals(addr)){
	    	address=
			"  <address>"  +
			"  <addr></addr>" + 
			"    <addrType></addrType>" + 
			"    <zipcode>"+zipcode+"</zipcode>" + 
		"</address>" + 	
	    "</RequestBody>"+
	    "</TransBody>";
	     }else{
	    	 address=
	    	    "  <address>"  +
				"  <addr>"+addr+"</addr>" + 
				"    <addrType>02</addrType>" + 
				"    <zipcode>"+zipcode+"</zipcode>" + 
			"</address>" + 	
		    "</RequestBody>"+
		    "</TransBody>"; 	    	 
	     }
	     reQxmlorg.append(Hxml);
	     reQxmlorg.append(customer);
	     reQxmlorg.append(contmeth);
	     reQxmlorg.append(orgIdentifier);
	     reQxmlorg.append(address);	     
	     msg=reQxmlorg.toString();
		}		
        Map rep=null;	
		rep = process(msg,auth);	
		return rep;
	}		
	public Map CallIntrfacePhone(AuthUser auth,String tempCustId,String custZhName,String custTyp,String linkUser,String zipcode,String custEnName,String certType,
			String linkPhone,String contmethInfo,String addr,String custStat,String otherName,String certNum,String jobType,String industType) throws Exception{			
		SimpleDateFormat df8 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat df20 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		SimpleDateFormat df10 = new SimpleDateFormat("hhmmssSS");			
		String msg="";
		if("2".equals(custTyp)){
		//对于地址和证件要处理	    
	    StringBuffer reQxml=new StringBuffer();	   
	    String Hxml=
		"<?xml version=\"1.0\" encoding=\"GBK\"?>"+
		"<TransBody>" +
		"  <RequestHeader>" + 
		"    <ReqSysCd>CRM</ReqSysCd>" + 
		"    <ReqSeqNo>"+df20.format(new Date())+"</ReqSeqNo>" + 
		"    <ReqDt>"+df8.format(new Date())+"</ReqDt>" + 
		"    <ReqTm>"+df10.format(new Date())+"</ReqTm>" + 
		"    <DestSysCd>ECIF</DestSysCd>" + 
		"    <ChnlNo>82</ChnlNo>" + 
		"    <BrchNo>503</BrchNo>" + 
		"    <BizLine>209</BizLine>" + 
		"    <TrmNo>TRM10010</TrmNo>" + 
		"    <TrmIP>127.0.0.1</TrmIP>" + 
		"    <TlrNo>"+auth.getUserId()+"</TlrNo>" + 
		"  </RequestHeader>" + 
		"  <RequestBody>" + 
		"    <txCode>openPerAccountforCrm</txCode>" + 
		"    <txName>潜在个人客户开户</txName>" + 
		"    <authType>1</authType>" + 
		"    <authCode>1010</authCode>" ;
	   String perIdent="";
	   if("".equals(certType)||"".equals(certNum)){
		   perIdent=
				"  <perIdentifier>" + 
				"    <identType></identType>" + 
				"    <identNo></identNo>" + 
				"    <identCustName></identCustName>" + 
				"  </perIdentifier>"; 	  	
	   }else{
		   perIdent=
				"  <perIdentifier>" + 
				"    <identType>"+certType+"</identType>" + 
				"    <identNo>"+certNum+"</identNo>" + 
				"    <identCustName>"+custZhName+"</identCustName>" + 
				"  </perIdentifier>"; 
	   }
	   String contmeth="";	
		contmeth=
		"  <contmeth>" + 
		"  <contmethType>100</contmethType>" + 
		"  <contmethInfo>"+linkPhone+"</contmethInfo>" + 
		"  <contmethSeq>1</contmethSeq>" + 
		"  <isPriori>1</isPriori>" + 
		"  </contmeth>" ;			
	
	   String address="";
	   if("".equals(addr)){
		address=
	    "<address>" + 
		"<addr></addr>" + 
		"    <addrType></addrType>" + 
		"    <zipcode>"+zipcode+"</zipcode>" + 
		"</address>";
	   }else{
		   address=
			    "<address>" + 
				"<addr>"+addr+"</addr>" + 
				"    <addrType>02</addrType>" + 
				"    <zipcode>"+zipcode+"</zipcode>" + 
				"</address>";  
		   
	   }
	   String customer=	"";	
	   if("".equals(certType)||"".equals(certNum)){
	   customer="<customer>" + 
			"<custType>"+custTyp+"</custType>" + 
			"    <identType></identType>" + 
			"    <identNo></identNo>" + 
			"    <custName>"+custZhName+"</custName>" + 
			"    <enName>"+custEnName+"</enName>" + 
			"    <jobType>"+jobType+"</jobType>" + 
			"    <industType>"+industType+"</industType>" + 
			"    <potentialFlag>1</potentialFlag>" + 
			"    <linkmanName>"+linkUser+"</linkmanName>" + 
			"    <linkmanTel>"+linkPhone+"</linkmanTel>" + 
			"    <shortName>"+otherName+"</shortName>" + 
			"    <createDate>"+df8.format(new Date())+"</createDate>" + 
			"  </customer>" ;
	   }else{
		   customer="<customer>" + 
			"<custType>"+custTyp+"</custType>" + 
			"    <identType>"+certType+"</identType>" + 
			"    <identNo>"+certNum+"</identNo>" + 
			"    <custName>"+custZhName+"</custName>" + 
			"    <enName>"+custEnName+"</enName>" + 
			"    <jobType>"+jobType+"</jobType>" + 
			"    <industType>"+industType+"</industType>" + 
			"    <potentialFlag>1</potentialFlag>" + 
			"    <linkmanName>"+linkUser+"</linkmanName>" + 
			"    <linkmanTel>"+linkPhone+"</linkmanTel>" + 
			"    <shortName>"+otherName+"</shortName>" + 
			"    <createDate>"+df8.format(new Date())+"</createDate>" + 
			"  </customer>" ;     
	   }
	   String person=			
		" <person>" + 
		" <postAddr>"+addr+"</postAddr>" + 
		"    <personalName>"+custZhName+"</personalName>" + 
		"  </person>"+
		"  </RequestBody>" + 
		"</TransBody>";	   
	   reQxml.append(Hxml);
	   reQxml.append(perIdent);
	   reQxml.append(contmeth);
	   reQxml.append(address);
	   reQxml.append(customer);
	   reQxml.append(person);	   
	   msg=reQxml.toString();	   
	  }else{
	 StringBuffer reQxmlorg=new StringBuffer();	  
	 String Hxml="<?xml version=\"1.0\" encoding=\"GBK\"?>"+
		"<TransBody>" +
  		"  <RequestHeader>" + 
  		"    <ReqSysCd>CRM</ReqSysCd>" + 
  		"    <ReqSeqNo>"+df20.format(new Date())+"</ReqSeqNo>" + 
  		"    <ReqDt>"+df8.format(new Date())+"</ReqDt>" + 
  		"    <ReqTm>"+df10.format(new Date())+"</ReqTm>" + 
  		"    <DestSysCd>ECIF</DestSysCd>" + 
  		"    <ChnlNo>82</ChnlNo>" + 
  		"    <BrchNo>503</BrchNo>" + 
  		"    <BizLine>209</BizLine>" + 
  		"    <TrmNo>TRM10010</TrmNo>" + 
  		"    <TrmIP>127.0.0.1</TrmIP>" + 
  		"    <TlrNo>"+auth.getUserId()+"</TlrNo>" + 
  		"  </RequestHeader>" + 
  		"  <RequestBody>" + 
  		"    <txCode>openOrgAccountforCrm</txCode>" + 
  		"    <txName>潜在机构客户开户</txName>" + 
  		"    <authType>1</authType>" + 
  		"    <authCode>1010</authCode>" ;
	 
	  String customer="";
	     if("".equals(certType)||"".equals(certNum)){
	       customer=
		   "<customer>" +
	       " <linkmanTel>"+linkPhone+"</linkmanTel>" + 
	       "<custType>"+custTyp+"</custType>" + 
	        " <identType></identType>" + 
			"<identNo></identNo>" + 
			" <custName>"+custZhName+"</custName>" +
			"    <shortName>"+otherName+"</shortName>" + 
			"    <enName>"+custEnName+"</enName>" + 
			"    <potentialFlag>1</potentialFlag>" + 
			"    <linkmanName>"+linkUser+"</linkmanName>" +
			" </customer>";
			}else{
		   customer=
		   "<customer>" +
	       " <linkmanTel>"+linkPhone+"</linkmanTel>" + 
	       "<custType>"+custTyp+"</custType>" + 
	        " <identType>"+certType+"</identType>" + 
			"<identNo>"+certNum+"</identNo>" + 
			" <custName>"+custZhName+"</custName>" +
			"    <shortName>"+otherName+"</shortName>" + 
			"    <enName>"+custEnName+"</enName>" + 
			"    <potentialFlag>1</potentialFlag>" + 
			"    <linkmanName>"+linkUser+"</linkmanName>" +
			" </customer>";	
			}
	     String contmeth="";
	     if("".equals(contmethInfo)){
		    contmeth=
			"  <contmeth>" + 
			"  <contmethType>100</contmethType>" + 
			"  <contmethInfo>"+linkPhone+"</contmethInfo>" + 
			"  <contmethSeq>1</contmethSeq>" + 
			"  <isPriori>1</isPriori>" + 
			"  </contmeth>";
		}else{
			contmeth=
	    	" <contmeth>" + 
	 		" <contmethType>202</contmethType>" + 
	 		"    <contmethInfo>"+contmethInfo+"</contmethInfo>" + 
	 		"    <contmethSeq>2</contmethSeq>" + 
	 		"    <isPriori>0</isPriori>" + 
	 		"  </contmeth>" + 
	 		"  <contmeth>" + 
	 		"  <contmethType>100</contmethType>" + 
	 		"  <contmethInfo>"+linkPhone+"</contmethInfo>" + 
	 		"  <contmethSeq>1</contmethSeq>" + 
	 		"  <isPriori>1</isPriori>" + 
	 		"  </contmeth>"; 	    	 
	     }
	     String orgIdentifier="";
	     if("".equals(certType)||"".equals(certNum)){
	     orgIdentifier=
		  "   <orgIdentifier>" + 
	      "    <identType></identType>" + 
		  "    <identNo></identNo>"  +
		  "    <identCustName></identCustName>" +			
		  "   </orgIdentifier>";
		  }else{
			  orgIdentifier=
				  "   <orgIdentifier>" + 
			      "    <identType>"+certType+"</identType>" + 
				  "    <identNo>"+certNum+"</identNo>"  +
				  "    <identCustName>"+custZhName+"</identCustName>" +			
				  "   </orgIdentifier>"; 
			  
		  }
	     String address="";
	     if("".equals(addr)){
	    	address=
			"  <address>"  +
			"  <addr></addr>" + 
			"    <addrType></addrType>" + 
			"    <zipcode>"+zipcode+"</zipcode>" + 
		"</address>" + 	
	    "</RequestBody>"+
	    "</TransBody>";
	     }else{
	    	 address=
	    	    "  <address>"  +
				"  <addr>"+addr+"</addr>" + 
				"    <addrType>02</addrType>" + 
				"    <zipcode>"+zipcode+"</zipcode>" + 
			"</address>" + 	
		    "</RequestBody>"+
		    "</TransBody>"; 	    	 
	     }
	     reQxmlorg.append(Hxml);
	     reQxmlorg.append(customer);
	     reQxmlorg.append(contmeth);
	     reQxmlorg.append(orgIdentifier);
	     reQxmlorg.append(address);	     
	     msg=reQxmlorg.toString();
		}		
        Map rep=null;	
		rep = process(msg,auth);	
		return rep;
	}	
	public Map CallIntrfaceUpdate(AuthUser auth,String tempCustId,String custZhName,String custTyp,String linkUser,String zipcode,String custEnName,String certType,
			String linkPhone,String contmethInfo,String addr,String custStat,String otherName,String certNum,String jobType,String industType){
			
		SimpleDateFormat df8 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat df20 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		SimpleDateFormat df10 = new SimpleDateFormat("hhmmssSS");		
		String msg="";
		if("2".equals(custTyp)){			
		StringBuffer updateperXml=new StringBuffer();			
		String headXml=	"<?xml version=\"1.0\" encoding=\"GBK\"?>" +
    	" <TransBody>" +
    	"  <RequestHeader>" + 
  		"    <ReqSysCd>CRM</ReqSysCd>" + 
  		"    <ReqSeqNo>"+df20.format(new Date())+"</ReqSeqNo>" + 
  		"    <ReqDt>"+df8.format(new Date())+"</ReqDt>" + 
  		"    <ReqTm>"+df10.format(new Date())+"</ReqTm>" + 
  		"    <DestSysCd>ECIF</DestSysCd>" + 
  		"    <ChnlNo>82</ChnlNo>" + 
  		"    <BrchNo>503</BrchNo>" + 
  		"    <BizLine>209</BizLine>" + 
  		"    <TrmNo>TRM10010</TrmNo>" + 
  		"    <TrmIP>127.0.0.1</TrmIP>" + 
  		"    <TlrNo>"+auth.getUserId()+"</TlrNo>" + 
  		"  </RequestHeader>" + 
  		"  <RequestBody>" + 
  		"    <txCode>updatePerCustInfo</txCode>" + 
  		"    <txName>潜在个人客户基本信息</txName>" + 
  		"    <authType>1</authType>" + 
  		"    <authCode>1010</authCode>" + 
  		"    <custNo>"+tempCustId+"</custNo>" ;
		String contmeth="";
		if("".equals(contmethInfo)){
			contmeth=
			"  <contmeth>" + 
        	"    <isPriori>1</isPriori>" + 
        	"    <contmethType>100</contmethType>" + 
        	"    <contmethInfo>"+linkPhone+"</contmethInfo>" + 
        	"    <contmethSeq>1</contmethSeq>" + 
        	"    <remark></remark>" + 
        	"    <stat></stat>" + 
        	"    <contmethId></contmethId>" + 
        	"  </contmeth>" ;
		}else{
			contmeth=
			"  <contmeth>" + 
        	"    <isPriori>1</isPriori>" + 
        	"    <contmethType>100</contmethType>" + 
        	"    <contmethInfo>"+linkPhone+"</contmethInfo>" + 
        	"    <contmethSeq>1</contmethSeq>" + 
        	"    <remark></remark>" + 
        	"    <stat></stat>" + 
        	"    <contmethId></contmethId>" + 
        	"  </contmeth>" + 
        	"  <contmeth>" + 
        	"    <isPriori>1</isPriori>" + 
        	"    <contmethType>202</contmethType>" + 
        	"    <contmethInfo>"+contmethInfo+"</contmethInfo>" + 
        	"    <contmethSeq>1</contmethSeq>" + 
        	"    <remark></remark>" + 
        	"    <stat></stat>" + 
        	"    <contmethId></contmethId>" + 
        	"  </contmeth>" ; 				
		}
		String address="";
		if("".equals(addr)){
			address=        	
        	"  <address>" + 
        	"    <townCode></townCode>" + 
        	"    <townName></townName>" + 
        	"    <streetName></streetName>" + 
        	"    <villageNo></villageNo>" + 
        	"    <villageName></villageName>" + 
        	"    <addrType></addrType>" + 
        	"    <addr></addr>" + 
        	"    <enAddr></enAddr>" + 
        	"    <contmethInfo>"+linkPhone+"</contmethInfo>" + 
        	"    <zipcode>"+zipcode+"</zipcode>" + 
        	"    <countryOrRegion></countryOrRegion>" + 
        	"    <adminZone></adminZone>" + 
        	"    <areaCode></areaCode>" + 
        	"    <provinceCode></provinceCode>" + 
        	"    <cityCode></cityCode>" + 
        	"    <countyCode></countyCode>" + 
        	"    <addrId></addrId>" + 
        	"  </address>";
        	
		}else{
			address=
	        	
			"  <address>" + 
        	"    <townCode></townCode>" + 
        	"    <townName></townName>" + 
        	"    <streetName></streetName>" + 
        	"    <villageNo></villageNo>" + 
        	"    <villageName></villageName>" + 
        	"    <addrType>02</addrType>" + 
        	"    <addr>"+addr+"</addr>" + 
        	"    <enAddr></enAddr>" + 
        	"    <contmethInfo>"+linkPhone+"</contmethInfo>" + 
        	"    <zipcode>"+zipcode+"</zipcode>" + 
        	"    <countryOrRegion></countryOrRegion>" + 
        	"    <adminZone></adminZone>" + 
        	"    <areaCode></areaCode>" + 
        	"    <provinceCode></provinceCode>" + 
        	"    <cityCode></cityCode>" + 
        	"    <countyCode></countyCode>" + 
        	"    <addrId></addrId>" + 
        	"  </address>";        							
		}
		String customer="";
		 if("".equals(certType)||"".equals(certNum)){
			customer=
        	"  <customer>" + 
        	"    <linkmanTel>"+linkPhone+"</linkmanTel>" + 
        	"    <firstLoanDate></firstLoanDate>" + 
        	"    <loanCustMgr></loanCustMgr>" + 
        	"    <coreNo></coreNo>" + 
        	"    <arCustFlag></arCustFlag>" + 
        	"    <arCustType></arCustType>" + 
        	"    <sourceChannel></sourceChannel>" + 
        	"    <recommender></recommender>" + 
        	"    <cusBankRel></cusBankRel>" + 
        	"    <infoPer></infoPer>" + 
        	"    <createDate></createDate>" + 
        	"    <createTime></createTime>" + 
        	"    <createBranchNo></createBranchNo>" + 
        	"    <createTellerNo></createTellerNo>" + 
        	"    <loanMainBrId></loanMainBrId>" + 
        	"    <custType>"+custTyp+"</custType>" + 
        	"    <identType></identType>" + 
        	"    <identNo></identNo>" + 
        	"    <custName></custName>" + 
        	"    <postName></postName>" + 
        	"    <shortName>"+otherName+"</shortName>" + 
        	"    <enName>"+custEnName+"</enName>" + 
        	"    <enShortName></enShortName>" + 
        	"    <custStat></custStat>" + 
        	"    <riskNationCode></riskNationCode>" + 
        	"    <potentialFlag>1</potentialFlag>" + 
        	"    <ebankFlag></ebankFlag>" + 
        	"    <realFlag></realFlag>" + 
        	"    <inoutFlag></inoutFlag>" + 
        	"    <blankFlag></blankFlag>" + 
        	"    <vipFlag></vipFlag>" + 
        	"    <mergeFlag></mergeFlag>" + 
        	"    <linkmanName>"+linkUser+"</linkmanName>" + 
        	"  </customer>" ;
        	}else{
        		customer=
        		"  <customer>" + 
            	"    <linkmanTel>"+linkPhone+"</linkmanTel>" + 
            	"    <firstLoanDate></firstLoanDate>" + 
            	"    <loanCustMgr></loanCustMgr>" + 
            	"    <coreNo></coreNo>" + 
            	"    <arCustFlag></arCustFlag>" + 
            	"    <arCustType></arCustType>" + 
            	"    <sourceChannel></sourceChannel>" + 
            	"    <recommender></recommender>" + 
            	"    <cusBankRel></cusBankRel>" + 
            	"    <infoPer></infoPer>" + 
            	"    <createDate></createDate>" + 
            	"    <createTime></createTime>" + 
            	"    <createBranchNo></createBranchNo>" + 
            	"    <createTellerNo></createTellerNo>" + 
            	"    <loanMainBrId></loanMainBrId>" + 
            	"    <custType>"+custTyp+"</custType>" + 
            	"    <identType>"+certType+"</identType>" + 
            	"    <identNo>"+certNum+"</identNo>" + 
            	"    <custName>"+custZhName+"</custName>" + 
            	"    <postName></postName>" + 
            	"    <shortName>"+otherName+"</shortName>" + 
            	"    <enName>"+custEnName+"</enName>" + 
            	"    <enShortName></enShortName>" + 
            	"    <custStat></custStat>" + 
            	"    <riskNationCode></riskNationCode>" + 
            	"    <potentialFlag>1</potentialFlag>" + 
            	"    <ebankFlag></ebankFlag>" + 
            	"    <realFlag></realFlag>" + 
            	"    <inoutFlag></inoutFlag>" + 
            	"    <blankFlag></blankFlag>" + 
            	"    <vipFlag></vipFlag>" + 
            	"    <mergeFlag></mergeFlag>" + 
            	"    <linkmanName>"+linkUser+"</linkmanName>" + 
            	"  </customer>" ;						
		     }
		   String person=
        	"  <person>" + 
        	"    <perCustType></perCustType>" + 
        	"    <jointCustType></jointCustType>" + 
        	"    <surName></surName>" + 
        	"    <personalName>"+custEnName+"</personalName>" + 
        	"    <pinyinName></pinyinName>" + 
        	"    <pinyinAbbr></pinyinAbbr>" + 
        	"    <personTitle></personTitle>" + 
        	"    <nickName></nickName>" + 
        	"    <usedName></usedName>" + 
        	"    <gender></gender>" + 
        	"    <birthday></birthday>" + 
        	"    <birthlocale></birthlocale>" + 
        	"    <citizenship></citizenship>" + 
        	"    <nationality></nationality>" + 
        	"    <nativeplace></nativeplace>" + 
        	"    <household></household>" + 
        	"    <hukouPlace></hukouPlace>" + 
        	"    <marriage></marriage>" + 
        	"    <residence></residence>" + 
        	"    <health></health>" + 
        	"    <religiousBelief></religiousBelief>" + 
        	"    <politicalFace></politicalFace>" + 
        	"    <mobilePhone></mobilePhone>" + 
        	"    <email></email>" + 
        	"    <homepage></homepage>" + 
        	"    <weibo></weibo>" + 
        	"    <weixin></weixin>" + 
        	"    <qq></qq>" + 
        	"    <starSign></starSign>" + 
        	"    <homeAddr></homeAddr>" + 
        	"    <homeZipcode></homeZipcode>" + 
        	"    <homeTel></homeTel>" + 
        	"    <highestSchooling></highestSchooling>" + 
        	"    <highestDegree></highestDegree>" + 
        	"    <graduateSchool></graduateSchool>" + 
        	"    <major></major>" + 
        	"    <graduationDate></graduationDate>" + 
        	"    <careerStat></careerStat>" + 
        	"    <careerType></careerType>" + 
        	"    <profession></profession>" + 
        	"    <unitName></unitName>" + 
        	"    <unitChar></unitChar>" + 
        	"    <unitAddr></unitAddr>" + 
        	"    <unitZipcode>"+zipcode+"</unitZipcode>" + 
        	"    <unitTel></unitTel>" + 
        	"    <unitFex></unitFex>" + 
        	"    <postAddr>"+addr+"</postAddr>" + 
        	"    <postZipcode></postZipcode>" + 
        	"    <postPhone>"+linkUser+"</postPhone>" + 
        	"    <adminLevel></adminLevel>" + 
        	"    <cntName></cntName>" + 
        	"    <duty></duty>" + 
        	"    <workResult></workResult>" + 
        	"    <careerStartDate></careerStartDate>" + 
        	"    <annualIncomeScope></annualIncomeScope>" + 
        	"    <annualIncome></annualIncome>" + 
        	"    <currCareerStartDate></currCareerStartDate>" + 
        	"    <hasQualification></hasQualification>" + 
        	"    <qualification></qualification>" + 
        	"    <careerTitle></careerTitle>" + 
        	"    <holdStockAmt></holdStockAmt>" + 
        	"    <bankDuty></bankDuty>" + 
        	"    <salaryAcctBank></salaryAcctBank>" + 
        	"    <salaryAcctNo></salaryAcctNo>" + 
        	"    <loanCardNo></loanCardNo>" + 
        	"    <holdAcct></holdAcct>" + 
        	"    <holdCard></holdCard>" + 
        	"    <resume></resume>" + 
        	"    <usaTaxIdenNo></usaTaxIdenNo>" + 
        	"    <lastDealingsDesc></lastDealingsDesc>" + 
        	"    <remark></remark>" + 
        	"  </person>";
		    String perIdentifier="";
		    if("".equals(certType)||"".equals(certNum)){
		    perIdentifier=	
        	"  <perIdentifier>" + 
        	"    <verifyDate></verifyDate>" + 
        	"    <verifyEmployee></verifyEmployee>" + 
        	"    <verifyResult></verifyResult>" + 
        	"    <identType>"+certType+"</identType>" + 
        	"    <identNo>"+certNum+"</identNo>" + 
        	"    <identCustName>"+custZhName+"</identCustName>" + 
        	"    <identDesc></identDesc>" + 
        	"    <countryOrRegion></countryOrRegion>" + 
        	"    <identOrg></identOrg>" + 
        	"    <identApproveUnit></identApproveUnit>" + 
        	"    <identCheckFlag></identCheckFlag>" + 
        	"    <identCheckingDate></identCheckingDate>" + 
        	"    <identCheckedDate></identCheckedDate>" + 
        	"    <identValidPeriod></identValidPeriod>" + 
        	"    <identEffectiveDate></identEffectiveDate>" + 
        	"    <identExpiredDate></identExpiredDate>" + 
        	"    <identValidFlag></identValidFlag>" + 
        	"    <identPeriod></identPeriod>" + 
        	"    <isOpenAccIdent></isOpenAccIdent>" + 
        	"    <openAccIdentModifiedFlag></openAccIdentModifiedFlag>" + 
        	"    <identModifiedTime></identModifiedTime>" + 
        	"    <identId></identId>" + 
        	"  </perIdentifier>" + 
        	"</RequestBody>"+
             "</TransBody>";
		    }else{
		    	perIdentifier=
	        	"  <perIdentifier>" + 
	        	"    <verifyDate></verifyDate>" + 
	        	"    <verifyEmployee></verifyEmployee>" + 
	        	"    <verifyResult></verifyResult>" + 
	        	"    <identType>"+certType+"</identType>" + 
	        	"    <identNo>"+certNum+"</identNo>" + 
	        	"    <identCustName>"+custZhName+"</identCustName>" + 
	        	"    <identDesc></identDesc>" + 
	        	"    <countryOrRegion></countryOrRegion>" + 
	        	"    <identOrg></identOrg>" + 
	        	"    <identApproveUnit></identApproveUnit>" + 
	        	"    <identCheckFlag></identCheckFlag>" + 
	        	"    <identCheckingDate></identCheckingDate>" + 
	        	"    <identCheckedDate></identCheckedDate>" + 
	        	"    <identValidPeriod></identValidPeriod>" + 
	        	"    <identEffectiveDate></identEffectiveDate>" + 
	        	"    <identExpiredDate></identExpiredDate>" + 
	        	"    <identValidFlag></identValidFlag>" + 
	        	"    <identPeriod></identPeriod>" + 
	        	"    <isOpenAccIdent></isOpenAccIdent>" + 
	        	"    <openAccIdentModifiedFlag></openAccIdentModifiedFlag>" + 
	        	"    <identModifiedTime></identModifiedTime>" + 
	        	"    <identId></identId>" + 
	        	"  </perIdentifier>" + 
	        	"</RequestBody>"+
	             "</TransBody>";		    	
		    	updateperXml.append(headXml);
		    	updateperXml.append(contmeth);
		    	updateperXml.append(address);
		    	updateperXml.append(customer);
		    	updateperXml.append(person);
		    	updateperXml.append(perIdentifier);		    	
		    	msg=updateperXml.toString();
		    }
		}else{	
		StringBuffer orgsb=new StringBuffer();
		String headorg= "<?xml version=\"1.0\" encoding=\"GBK\"?>" +
    	" <TransBody>" +
		"  <RequestHeader>" + 
  		"    <ReqSysCd>CRM</ReqSysCd>" + 
  		"    <ReqSeqNo>"+df20.format(new Date())+"</ReqSeqNo>" + 
  		"    <ReqDt>"+df8.format(new Date())+"</ReqDt>" + 
  		"    <ReqTm>"+df10.format(new Date())+"</ReqTm>" + 
  		"    <DestSysCd>ECIF</DestSysCd>" + 
  		"    <ChnlNo>82</ChnlNo>" + 
  		"    <BrchNo>503</BrchNo>" + 
  		"    <BizLine>209</BizLine>" + 
  		"    <TrmNo>TRM10010</TrmNo>" + 
  		"    <TrmIP>127.0.0.1</TrmIP>" + 
  		"    <TlrNo>"+auth.getUserId()+"</TlrNo>" + 
  		"  </RequestHeader>" + 
  		"  <RequestBody>" + 
  		"    <txCode>updateOrgCustInfo</txCode>" + 
  		"    <txName>修改潜在机构客户基本信息</txName>" + 
  		"    <authType>1</authType>" + 
  		"    <authCode>1010</authCode>" + 
  		"    <custNo>"+tempCustId+"</custNo>" ;
		String address="";
		if("".equals(addr)){
	       address=	    	 
	    	  "  <address>" + 
	    	  "    <countyCode></countyCode>" + 
	    	  "    <cityCode></cityCode>" + 
	    	  "    <provinceCode></provinceCode>" + 
	    	  "    <areaCode></areaCode>" + 
	    	  "    <adminZone></adminZone>" + 
	    	  "    <countryOrRegion></countryOrRegion>" + 
	    	  "    <zipcode>"+zipcode+"</zipcode>" + 
	    	  "    <contmethInfo>"+contmethInfo+"</contmethInfo>" + 
	    	  "    <enAddr></enAddr>" + 
	    	  "    <addr></addr>" + 
	    	  "    <addrType></addrType>" + 
	    	  "    <villageName></villageName>" + 
	    	  "    <villageNo></villageNo>" + 
	    	  "    <streetName></streetName>" + 
	    	  "    <townName></townName>" + 
	    	  "    <townCode></townCode>" + 
	    	  "  </address>" ;
		}else{
			 address=
			  "  <address>" + 
	    	  "    <countyCode></countyCode>" + 
	    	  "    <cityCode></cityCode>" + 
	    	  "    <provinceCode></provinceCode>" + 
	    	  "    <areaCode></areaCode>" + 
	    	  "    <adminZone></adminZone>" + 
	    	  "    <countryOrRegion></countryOrRegion>" + 
	    	  "    <zipcode>"+zipcode+"</zipcode>" + 
	    	  "    <contmethInfo>"+contmethInfo+"</contmethInfo>" + 
	    	  "    <enAddr></enAddr>" + 
	    	  "    <addr>"+addr+"</addr>" + 
	    	  "    <addrType>02</addrType>" + 
	    	  "    <villageName></villageName>" + 
	    	  "    <villageNo></villageNo>" + 
	    	  "    <streetName></streetName>" + 
	    	  "    <townName></townName>" + 
	    	  "    <townCode></townCode>" + 
	    	  "  </address>";										
		}
		String org=
	    	  "  <org>" + 
	    	  "    <comSpLicOrg></comSpLicOrg>" + 
	    	  "    <comSpStrDate></comSpStrDate>" + 
	    	  "    <comSpEndDate></comSpEndDate>" + 
	    	  "    <loanCardFlag></loanCardFlag>" + 
	    	  "    <loanCardNo></loanCardNo>" + 
	    	  "    <loanCardStat></loanCardStat>" + 
	    	  "    <loadCardPwd></loadCardPwd>" + 
	    	  "    <loadCardAuditDt></loadCardAuditDt>" + 
	    	  "    <legalReprName></legalReprName>" + 
	    	  "    <legalReprGender></legalReprGender>" + 
	    	  "    <legalReprPhoto></legalReprPhoto>" + 
	    	  "    <legalReprIdentType></legalReprIdentType>" + 
	    	  "    <legalReprIdentNo></legalReprIdentNo>" + 
	    	  "    <legalReprTel></legalReprTel>" + 
	    	  "    <legalReprAddr></legalReprAddr>" + 
	    	  "    <legalReprNationCode></legalReprNationCode>" + 
	    	  "    <finRepType></finRepType>" + 
	    	  "    <totalAssets></totalAssets>" + 
	    	  "    <totalDebt></totalDebt>" + 
	    	  "    <annualIncome></annualIncome>" + 
	    	  "    <annualProfit></annualProfit>" + 
	    	  "    <industryPosition></industryPosition>" + 
	    	  "    <isStockHolder></isStockHolder>" + 
	    	  "    <holdStockAmt></holdStockAmt>" + 
	    	  "    <orgAddr>"+addr+"</orgAddr>" + 
	    	  "    <orgZipcode>"+zipcode+"</orgZipcode>" + 
	    	  "    <orgCus>"+linkUser+"</orgCus>" + 
	    	  "    <orgTel>"+linkPhone+"</orgTel>" + 
	    	  "    <orgFex></orgFex>" + 
	    	  "    <orgEmail></orgEmail>" + 
	    	  "    <orgHomepage></orgHomepage>" + 
	    	  "    <orgWeibo></orgWeibo>" + 
	    	  "    <orgWeixin></orgWeixin>" + 
	    	  "    <lastDealingsDesc></lastDealingsDesc>" + 
	    	  "    <remark></remark>" + 
	    	  "    <orgCustType></orgCustType>" + 
	    	  "    <churcustype></churcustype>" + 
	    	  "    <orgBizCustType></orgBizCustType>" + 
	    	  "    <nationCode></nationCode>" + 
	    	  "    <areaCode></areaCode>" + 
	    	  "    <orgType></orgType>" + 
	    	  "    <orgCode></orgCode>" + 
	    	  "    <orgRegDate></orgRegDate>" + 
	    	  "    <orgExpDate></orgExpDate>" + 
	    	  "    <orgCodeUnit></orgCodeUnit>" + 
	    	  "    <orgCodeAnnDate></orgCodeAnnDate>" + 
	    	  "    <busiLicNo></busiLicNo>" + 
	    	  "    <mainIndustry></mainIndustry>" + 
	    	  "    <minorIndustry></minorIndustry>" + 
	    	  "    <industryDivision></industryDivision>" + 
	    	  "    <industryChar></industryChar>" + 
	    	  "    <entProperty></entProperty>" + 
	    	  "    <entScale></entScale>" + 
	    	  "    <entScaleRh></entScaleRh>" + 
	    	  "    <entScaleCk></entScaleCk>" + 
	    	  "    <assetsScale></assetsScale>" + 
	    	  "    <employeeScale></employeeScale>" + 
	    	  "    <economicType></economicType>" + 
	    	  "    <comHoldType></comHoldType>" + 
	    	  "    <orgForm></orgForm>" + 
	    	  "    <governStructure></governStructure>" + 
	    	  "    <inCllType></inCllType>" + 
	    	  "    <industryCategory></industryCategory>" + 
	    	  "    <investType></investType>" + 
	    	  "    <entBelong></entBelong>" + 
	    	  "    <buildDate></buildDate>" + 
	    	  "    <superDept></superDept>" + 
	    	  "    <mainBusiness></mainBusiness>" + 
	    	  "    <minorBusiness></minorBusiness>" + 
	    	  "    <businessMode></businessMode>" + 
	    	  "    <busiStartDate></busiStartDate>" + 
	    	  "    <induDeveProspect></induDeveProspect>" + 
	    	  "    <fundSource></fundSource>" + 
	    	  "    <zoneCode></zoneCode>" + 
	    	  "    <fexcPrmCode></fexcPrmCode>" + 
	    	  "    <topCorpLevel></topCorpLevel>" + 
	    	  "    <comSpBusiness></comSpBusiness>" + 
	    	  "    <comSpLicNo></comSpLicNo>" + 
	    	  "    <comSpDetail></comSpDetail>" + 
	    	  "  </org>" ;
		String contmeth="";
		if("".equals(contmethInfo)){
			 contmeth=
	    	  "  <contmeth>" + 
	    	  "    <remark></remark>" + 
	    	  "    <contmethSeq>1</contmethSeq>" + 
	    	  "    <contmethInfo>"+linkPhone+"</contmethInfo>" + 
	    	  "    <contmethType>100</contmethType>" + 
	    	  "    <isPriori>1</isPriori>" + 
	    	  "    <stat></stat>" + 
	    	  "  </contmeth>";	    	  
		}else{
			contmeth=
			  "  <contmeth>" + 
	    	  "    <remark></remark>" + 
	    	  "    <contmethSeq>1</contmethSeq>" + 
	    	  "    <contmethInfo>"+linkPhone+"</contmethInfo>" + 
	    	  "    <contmethType>100</contmethType>" + 
	    	  "    <isPriori>1</isPriori>" + 
	    	  "    <stat></stat>" + 
	    	  "  </contmeth>" + 
	    	  "  <contmeth>" + 
	    	  "    <remark></remark>" + 
	    	  "    <contmethSeq>2</contmethSeq>" + 
	    	  "    <contmethInfo>"+contmethInfo+"</contmethInfo>" + 
	    	  "    <contmethType>202</contmethType>" + 
	    	  "    <isPriori>1</isPriori>" + 
	    	  "    <stat></stat>" + 
	    	  "  </contmeth>";
			
		} String orgIdentifier="";
		 if("".equals(certType)||"".equals(certNum)){
			  orgIdentifier=
	    	  "  <orgIdentifier>" + 
	    	  "    <verifyDate></verifyDate>" + 
	    	  "    <verifyEmployee></verifyEmployee>" + 
	    	  "    <verifyResult></verifyResult>" + 
	    	  "    <identType></identType>" + 
	    	  "    <identNo></identNo>" + 
	    	  "    <identCustName></identCustName>" + 
	    	  "    <identDesc></identDesc>" + 
	    	  "    <countryOrRegion></countryOrRegion>" + 
	    	  "    <identOrg></identOrg>" + 
	    	  "    <identApproveUnit></identApproveUnit>" + 
	    	  "    <identCheckFlag></identCheckFlag>" + 
	    	  "    <idenRegDate></idenRegDate>" + 
	    	  "    <identCheckingDate></identCheckingDate>" + 
	    	  "    <identCheckedDate></identCheckedDate>" + 
	    	  "    <identValidPeriod></identValidPeriod>" + 
	    	  "    <identEffectiveDate></identEffectiveDate>" + 
	    	  "    <identExpiredDate></identExpiredDate>" + 
	    	  "    <identValidFlag></identValidFlag>" + 
	    	  "    <identPeriod></identPeriod>" + 
	    	  "    <openAccIdentModifiedFlag></openAccIdentModifiedFlag>" + 
	    	  "    <identModifiedTime></identModifiedTime>" + 
	    	  "    <isOpenAccIdent></isOpenAccIdent>" + 
	    	  "  </orgIdentifier>" ;
		  }else{
			  orgIdentifier=
			  "  <orgIdentifier>" + 
	    	  "    <verifyDate></verifyDate>" + 
	    	  "    <verifyEmployee></verifyEmployee>" + 
	    	  "    <verifyResult></verifyResult>" + 
	    	  "    <identType>"+certType+"</identType>" + 
	    	  "    <identNo>"+certNum+"</identNo>" + 
	    	  "    <identCustName>"+custZhName+"</identCustName>" + 
	    	  "    <identDesc></identDesc>" + 
	    	  "    <countryOrRegion></countryOrRegion>" + 
	    	  "    <identOrg></identOrg>" + 
	    	  "    <identApproveUnit></identApproveUnit>" + 
	    	  "    <identCheckFlag></identCheckFlag>" + 
	    	  "    <idenRegDate></idenRegDate>" + 
	    	  "    <identCheckingDate></identCheckingDate>" + 
	    	  "    <identCheckedDate></identCheckedDate>" + 
	    	  "    <identValidPeriod></identValidPeriod>" + 
	    	  "    <identEffectiveDate></identEffectiveDate>" + 
	    	  "    <identExpiredDate></identExpiredDate>" + 
	    	  "    <identValidFlag></identValidFlag>" + 
	    	  "    <identPeriod></identPeriod>" + 
	    	  "    <openAccIdentModifiedFlag></openAccIdentModifiedFlag>" + 
	    	  "    <identModifiedTime></identModifiedTime>" + 
	    	  "    <isOpenAccIdent></isOpenAccIdent>" + 
	    	  "  </orgIdentifier>" ;  
			  
		  }
		  String customer="";
		 if("".equals(certType)||"".equals(certNum)){
			 customer=
	    	  "  <customer>" + 
	    	  "    <identNo></identNo>" + 
	    	  "    <custName>"+custZhName+"</custName>" + 
	    	  "    <postName></postName>" + 
	    	  "    <shortName>"+otherName+"</shortName>" + 
	    	  "    <enName>"+custEnName+"</enName>" + 
	    	  "    <enShortName></enShortName>" + 
	    	  "    <custStat></custStat>" + 
	    	  "    <riskNationCode></riskNationCode>" + 
	    	  "    <potentialFlag>1</potentialFlag>" + 
	    	  "    <ebankFlag></ebankFlag>" + 
	    	  "    <realFlag></realFlag>" + 
	    	  "    <inoutFlag></inoutFlag>" + 
	    	  "    <blankFlag></blankFlag>" + 
	    	  "    <vipFlag></vipFlag>" + 
	    	  "    <mergeFlag></mergeFlag>" + 
	    	  "    <linkmanName>"+linkUser+"</linkmanName>" + 
	    	  "    <linkmanTel>"+linkPhone+"</linkmanTel>" + 
	    	  "    <firstLoanDate></firstLoanDate>" + 
	    	  "    <loanCustMgr></loanCustMgr>" + 
	    	  "    <arCustFlag></arCustFlag>" + 
	    	  "    <arCustType></arCustType>" + 
	    	  "    <sourceChannel></sourceChannel>" + 
	    	  "    <recommender></recommender>" + 
	    	  "    <loanCustRank></loanCustRank>" + 
	    	  "    <loanCustStat></loanCustStat>" + 
	    	  "    <cusBankRel></cusBankRel>" + 
	    	  "    <cusCorpRel></cusCorpRel>" + 
	    	  "    <infoPer></infoPer>" + 
	    	  "    <createDate></createDate>" + 
	    	  "    <createTime></createTime>" + 
	    	  "    <createBranchNo></createBranchNo>" + 
	    	  "    <createTellerNo></createTellerNo>" + 
	    	  "    <coreNo></coreNo>" + 
	    	  "    <custType>"+custTyp+"</custType>" + 
	    	  "    <identType></identType>" + 
	    	  "  </customer>" + 
	    	  "</RequestBody>"+
	          "</TransBody>";
		 }else{
			 customer=
			  "  <customer>" + 
	    	  "    <identNo>"+certNum+"</identNo>" + 
	    	  "    <custName>"+custZhName+"</custName>" + 
	    	  "    <postName></postName>" + 
	    	  "    <shortName>"+otherName+"</shortName>" + 
	    	  "    <enName>"+custEnName+"</enName>" + 
	    	  "    <enShortName></enShortName>" + 
	    	  "    <custStat></custStat>" + 
	    	  "    <riskNationCode></riskNationCode>" + 
	    	  "    <potentialFlag>1</potentialFlag>" + 
	    	  "    <ebankFlag></ebankFlag>" + 
	    	  "    <realFlag></realFlag>" + 
	    	  "    <inoutFlag></inoutFlag>" + 
	    	  "    <blankFlag></blankFlag>" + 
	    	  "    <vipFlag></vipFlag>" + 
	    	  "    <mergeFlag></mergeFlag>" + 
	    	  "    <linkmanName>"+linkUser+"</linkmanName>" + 
	    	  "    <linkmanTel>"+linkPhone+"</linkmanTel>" + 
	    	  "    <firstLoanDate></firstLoanDate>" + 
	    	  "    <loanCustMgr></loanCustMgr>" + 
	    	  "    <arCustFlag></arCustFlag>" + 
	    	  "    <arCustType></arCustType>" + 
	    	  "    <sourceChannel></sourceChannel>" + 
	    	  "    <recommender></recommender>" + 
	    	  "    <loanCustRank></loanCustRank>" + 
	    	  "    <loanCustStat></loanCustStat>" + 
	    	  "    <cusBankRel></cusBankRel>" + 
	    	  "    <cusCorpRel></cusCorpRel>" + 
	    	  "    <infoPer></infoPer>" + 
	    	  "    <createDate></createDate>" + 
	    	  "    <createTime></createTime>" + 
	    	  "    <createBranchNo></createBranchNo>" + 
	    	  "    <createTellerNo></createTellerNo>" + 
	    	  "    <coreNo></coreNo>" + 
	    	  "    <custType>"+custTyp+"</custType>" + 
	    	  "    <identType>"+certType+"</identType>" + 
	    	  "  </customer>" + 
	    	  "</RequestBody>"+
	          "</TransBody>"; 
		 }
		 orgsb.append(headorg);
		 orgsb.append(address);
		 orgsb.append(org);
		 orgsb.append(contmeth);
		 orgsb.append(orgIdentifier);
		 orgsb.append(customer);
		 msg=orgsb.toString();
		 
		}
		//调用两次：
		Map rep=null;
		try {
		   	rep = process(msg,auth);
		} catch (Exception e) {			
			e.printStackTrace();
		}
		return rep;
	}
	public static Map process(String mxlmsg,AuthUser aUser) throws Exception {			
		System.out.println("访问报文"+mxlmsg);
		Map idsMap=new HashMap();		
		String msg=mxlmsg;			
		//String ip = "10.20.34.108";
		//int port = 9500;  
	    String port=FileTypeConstance.getBipProperty("ECIF.PORT");
	    String ip=FileTypeConstance.getBipProperty("ECIF.IP");
		NioClient cl = new NioClient(ip, Integer.parseInt(port));
		String resp = null;
		try {			
			resp = cl.SocketCommunication(String.format("%08d", msg.getBytes().length) + msg);
		} catch (IOException e) {
			//System.out.printf("destSysNo:%s, ip:%s, port:%d\n",  ip, port);
			e.printStackTrace();
		}
		System.out.println("resp:\n" + resp);
		
		String custNo ="";
		String identId="";//证件ID acrm_f_ci_cust_identifier
		String contmethId="";//联系ID 
		String addrId="";//地址ID
		String ErrorCode="";
		/**
		 * 处理返回报文
		 * @param xml
		 * @return
		 */
		try{
			resp=resp.substring(8);
			Document doc = DocumentHelper.parseText(resp);
			Element root = doc.getRootElement();									
			String TxStatDesc= root.element("ResponseTail").element("TxStatDesc").getTextTrim();
			String TxStatCode= root.element("ResponseTail").element("TxStatCode").getTextTrim();
			ErrorCode =root.element("ResponseTail").element("TxStatCode").getTextTrim();		
			if(!"000000".equals(TxStatCode)){				
			   throw new BizException(1,0,"10001",TxStatDesc+"调用ECIF接口失败或超时,请稍后重试或联系管理员！");
			}			
			if("000000".equals(TxStatCode)){
					//返回技术主键：
					custNo = root.element("ResponseBody").element("custNo").getTextTrim();
					if(root.element("ResponseBody").element("identId")!=null){
					identId = root.element("ResponseBody").element("identId").getTextTrim();
					}
					if(root.element("ResponseBody").element("contmethId")!=null){
					contmethId = root.element("ResponseBody").element("contmethId").getTextTrim();
					}
					if(root.element("ResponseBody").element("addrId")!=null){
					addrId = root.element("ResponseBody").element("addrId").getTextTrim();
					}
					idsMap.put("custNo", custNo);
					idsMap.put("identId", identId);
					idsMap.put("contmethId", contmethId);
					idsMap.put("addrId", addrId);
				    }
							
		}catch(BizException e){
			throw e;
		}catch(Exception e){
		    throw e;
		}		
//		Random r =new Random();
//	    String custId="ECIF"+r.nextInt();		
//		String custNo=custId;		
		return idsMap;
	}
}