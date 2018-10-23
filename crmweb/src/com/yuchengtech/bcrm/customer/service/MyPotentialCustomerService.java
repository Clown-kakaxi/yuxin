package com.yuchengtech.bcrm.customer.service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import net.sf.json.JSONArray;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bcrm.customer.model.MyPotentialCustomer;
import com.yuchengtech.bcrm.custview.model.OcrmFCiBelongCustmgr;
import com.yuchengtech.bcrm.custview.model.OcrmFCiBelongOrg;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.upload.FileTypeConstance;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;
import com.yuchengtech.trans.socket.NioClient;
/**
 * 
 * @author 
 * discription：潜在客户管理功能
 * date 2014-11-01
 * 
 * 
 */
@Service
@Transactional(value="postgreTransactionManager")
public class MyPotentialCustomerService extends CommonService {		
	private EntityManager em;
	@Autowired
	private DataSource dsOracle;	
	@PersistenceContext
	public void setEntityManager(EntityManager em) {
	        this.em = em;
	}
	
	/** 过滤特殊字符  
	 *                     
    */  
    public  static  String StringFilter(String   str)   throws   PatternSyntaxException   {              
          String regEx="[`~!#$%^&*()+=|{}':;',\\[\\].<>/?~！#￥%……&*（）--+|{}【】‘；：”“’。，、？]";  
          Pattern   p   =   Pattern.compile(regEx);     
          Matcher   m   =   p.matcher(str);     
          return   m.replaceAll("").trim();     
    } 
    
	//创建潜在客户时 按照 客户名称+手机号的方式（行方提出的要求）判断该客户是否在CRM中存在。
 public  List queryExsitTempInCrm(String custZhName,String certNum,String certType){
	String TcustName=custZhName.trim();
//	String TTeleNo="";
//	if(TeleNo!=null){
//	 TTeleNo=TeleNo.trim();
//	}else{
//		 TTeleNo="";
//	}
	String tempSql = "select * from ACRM_F_CI_CUSTOMER t,ACRM_F_CI_CONTMETH t2   where t.cust_id=t2.cust_id  and  t.cust_name='"+TcustName+"' " +
			" and t.IDENT_TYPE ='"+certType+"'" +
					" and t.IDENT_no = '"+certNum+"' ";				
	List<Object[]> custList = this.em.createNativeQuery(tempSql).getResultList();		
    return custList;	
	}
	/**
	 *  一、新建一张表存放潜在客户扩展信息(存放目前模型表没有的字段)； 
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
	 * */
	public String save(String tempCustId,String custZhName,String custTyp,String linkUser,String zipcode,String custEnName,String certType,
			String linkPhone,String contmethInfo,String addr,String custStat,String otherName,String certNum,String jobType,String industType){	
//		    contmethInfo=StringFilter(contmethInfo);
		
	    //    System.out.println("data>"+tempCustId+ custZhName+ custTyp+ linkUser+ zipcode+ custEnName+ certType+ linkPhone+ contmethInfo+ addr+ custStat+ otherName+ certNum+ jobType+ industType);		
			String sign = null;
			OcrmFCiBelongOrg ocrmFCiBelongOrg = new OcrmFCiBelongOrg();
			OcrmFCiBelongCustmgr ocrmFCiBelongCustmgr = new OcrmFCiBelongCustmgr();
			String sql = "";
			String sql1 = "";
			String sql2 = "";
			String sql6 ="";
			AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Date date1 = new Date();
			DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = format1.format(date1);
			date = "to_date('" + date + "','yyyy-mm-dd hh24:mi:ss')";
			String currenUserId = auth.getUserId();
			String tempUnitId = auth.getUnitId();
			String callNo = linkPhone;				
			if(industType==null){
				industType="";
			}		
			//生成CUST_ID
			Random random1 = new Random();
			int sp=Math.abs(random1.nextInt());
			String result="crm"+sp;
			List custlist=queryExsitTempInCrm(custZhName,certNum,certType);
			//新增潜在客户：
			if(tempCustId==null||tempCustId==""){					
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
				     // System.out.println("crm data exsit-------->"+sb.toString());					   
			          StringBuilder sb2 = new StringBuilder("update ACRM_F_CI_ADDRESS set  ");
				      if(!"".equals(zipcode)||zipcode!=null){							  
						  sb2.append("zipcode = '"+zipcode+"',  ") ;
					  }
				      if(!"".equals(addr)||addr!=null){							  
						  sb2.append("addr = '"+addr+"'") ;
					  }
				      sb2.append("   where CUST_ID = '"+tempCustId+"'") ;	
					  //修改联系方式：座机号码：和手机号码： select * from Acrm_f_Ci_Per_Linkman      
				      sql2="update ACRM_F_CI_CONTMETH c set c.contmeth_info = '"+contmethInfo+"' where c.CUST_ID = '"+tempCustId+"' and c.contmeth_type = '202'";				    
				      String sql3="update ACRM_F_CI_CONTMETH c set c.contmeth_info = '"+linkPhone+"' where c.CUST_ID = '"+tempCustId+"' and c.contmeth_type = '100'";				    
				      //修改证件表
				      String updateIdentsql="update acrm_f_ci_cust_identifier set ident_type='"+certType+"',ident_no= '"+certNum+"',ident_cust_name='"+custZhName+"'  where CUST_ID = '"+tempCustId+"'";
				      String custsql="";
					  if("2".equals(custTyp)){
						 custsql="update ACRM_F_CI_PERSON  set personal_name='"+custZhName+"', post_addr='"+addr+"'  where CUST_ID = '"+tempCustId+"'";
					  }else{
						 custsql="update ACRM_F_CI_ORG     set cust_name='"+custZhName+"',org_addr='"+addr+"'  where CUST_ID = '"+tempCustId+"'";
					  }				    
				     this.em.createNativeQuery(sb.toString()).executeUpdate();
				     this.em.createNativeQuery(sb2.toString()).executeUpdate();				    
				     this.em.createNativeQuery(sql2).executeUpdate();
				     this.em.createNativeQuery(sql3).executeUpdate();
				     this.em.createNativeQuery(updateIdentsql).executeUpdate();	
				     this.em.createNativeQuery(custsql).executeUpdate();					   
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
								repPhone=CallIntrfacePhone(tempCustId, custZhName, custTyp,
									linkUser, zipcode, custEnName, certType,
									linkPhone, contmethInfo, addr, custStat,
									otherName, certNum, jobType, industType);
							}else{
								repPhone=CallIntrfacePhone(tempCustId, custZhName, custTyp,
										linkUser, zipcode, custEnName, certType,
										linkPhone, contmethInfo, addr, custStat,
										otherName, certNum, jobType, industType);
								repComenth=CallIntrfaceComenth(tempCustId, custZhName, custTyp,
										linkUser, zipcode, custEnName, certType,
										linkPhone, contmethInfo, addr, custStat,
										otherName, certNum, jobType, industType);
								
//								System.out.println("手机"+repPhone.get("contmethId"));
//								System.out.println("座机"+repComenth.get("contmethId"));
								
								
							}
						} catch (Exception e) {
							throw new BizException(1, 0, "10001",e.getMessage()+"该用户转为临时客户");
						}finally{
							 //未正常返回的客户号的转入临时客户：
							  //System.out.println("返回的客户号为：["+repPhone.get("custNo")+"]");
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
								   "   INDUST_TYPE," +
								   "   CUST_MGR," +
								   "   MAIN_BR_ID)" + 
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
								   "  '"+industType+"'," + 
								   "  '"+auth.getUserId()+"'," + 
								   "  '"+auth.getUnitId()+"'" + 
								   "   )";
		               //  System.out.println("转入临时客户:"+sql);                   
		                 this.em.createNativeQuery(sql).executeUpdate();		                   
						 }
						 }						
                         //System.out.println("response cusId："+repPhone.get("custNo")); 
                         //新增
				  		 sql = "insert into ACRM_F_CI_CUSTOMER(cust_id,cust_name,cust_type,linkman_name,en_name,ident_type,cust_stat,short_name,ident_no,create_teller_no,create_date,create_branch_no,POTENTIAL_FLAG," +
						 		"JOB_TYPE,indust_Type)" 
			    	  			+" values('"+repPhone.get("custNo")+"','"+custZhName+"','"+custTyp+"','"+linkUser+"','"+custEnName+"','"+certType+"','','"+otherName+"','"+certNum+"','"+currenUserId+"',"+date+",'"+tempUnitId+"','1'," +
			    	  					"'"+jobType+"','"+industType+"')";
						 sql1 = "insert into ACRM_F_CI_ADDRESS(addr_id,cust_id,zipcode,addr,addr_type) values('"+repPhone.get("addrId")+"','"+repPhone.get("custNo")+"','"+zipcode+"','"+addr+"','02')";
						 if("".equals(contmethInfo)){
						 sql6 = "insert into ACRM_F_CI_CONTMETH(contmeth_id,cust_id,contmeth_type,contmeth_info) values('"+repPhone.get("contmethId")+"','"+repPhone.get("custNo")+"','100','"+linkPhone+"')";																			 
						 }else{
						 sql2 = "insert into ACRM_F_CI_CONTMETH(contmeth_id,cust_id,contmeth_type,contmeth_info) values('"+repPhone.get("contmethId")+"','"+repPhone.get("custNo")+"','202','"+contmethInfo+"')";
						 sql6 = "insert into ACRM_F_CI_CONTMETH(contmeth_id,cust_id,contmeth_type,contmeth_info) values('"+repComenth.get("contmethId")+"','"+repPhone.get("custNo")+"','100','"+linkPhone+"')";																				  							 
						 }						 
						 //插入crm 证件表
						 String identSql="insert into acrm_f_ci_cust_identifier (ident_id,cust_id,ident_type,ident_no,ident_cust_name,last_update_sys) values('"+repPhone.get("identId")+"','"+repPhone.get("custNo")+"','"+certType+"','"+certNum+"','"+custZhName+"','CRM')";
						 String custsql="";
						 if("2".equals(custTyp)){
							 custsql="insert into ACRM_F_CI_PERSON (cust_id,personal_name,post_addr,last_update_sys) values('"+repPhone.get("custNo")+"','"+custZhName+"','"+addr+"','CRM')";
						 }else{
							 custsql="insert into ACRM_F_CI_ORG(cust_id,cust_name,org_addr) values('"+repPhone.get("custNo")+"','"+custZhName+"','"+addr+"')";
						 }						 
						 this.em.createNativeQuery(sql).executeUpdate();
						 this.em.createNativeQuery(sql1).executeUpdate();
						 if(!"".equals(contmethInfo)){
							 this.em.createNativeQuery(sql2).executeUpdate();
						 }
						 this.em.createNativeQuery(sql6).executeUpdate();
						 this.em.createNativeQuery(identSql).executeUpdate();
						 this.em.createNativeQuery(custsql).executeUpdate();
						 
						 ocrmFCiBelongOrg.setCustId(repPhone.get("custNo").toString());
						 ocrmFCiBelongOrg.setInstitutionCode(tempUnitId);
						 ocrmFCiBelongOrg.setInstitutionName(auth.getUnitName());					 
						 ocrmFCiBelongOrg.setMainType("1");						 
						 List li=auth.getRolesInfo();
						 for(Object m:li){
							 Map map = (Map)m;
							 if("R304".equals(map.get("ROLE_CODE"))){
								 ocrmFCiBelongCustmgr.setMgrId(auth.getUserId());
								 ocrmFCiBelongCustmgr.setAssignUsername(auth.getUsername());
								 break;
							 }else{
								 String user="VM"+auth.getUnitId();
								 String name=auth.getUnitName();
								 ocrmFCiBelongCustmgr.setMgrId(user);
								 ocrmFCiBelongCustmgr.setAssignUsername(name);
							 }
						 }						 
						 ocrmFCiBelongCustmgr.setCustId(repPhone.get("custNo").toString());
						 ocrmFCiBelongCustmgr.setInstitution(auth.getUnitId());
						 ocrmFCiBelongCustmgr.setInstitutionName(auth.getUnitName());
						 ocrmFCiBelongCustmgr.setAssignUser(auth.getUserId());
						 ocrmFCiBelongCustmgr.setMgrName(auth.getCname());
						 ocrmFCiBelongCustmgr.setMainType("1");
						 em.persist(ocrmFCiBelongOrg);
						 em.persist(ocrmFCiBelongCustmgr);  
				  		//调用接口：						  
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
					   "   INDUST_TYPE," +
					   "   CUST_MGR," +
					   "   MAIN_BR_ID)" + 
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
					   "  '"+industType+"'," + 
					   "  '"+auth.getUserId()+"'," + 
					   "  '"+auth.getUnitId()+"'" + 
					   "   )";                  
					this.em.createNativeQuery(sql).executeUpdate();									  						  
					}				  		
				  }
				}else{					 
		             /**
					 * 1:三证齐全的客户信息补录完全调用接口callInterface保存到正式库里面去修改潜在客户：客户信息补录完全
					 */
					if(custZhName!=""&&certNum!=""&&certType!=""){							
						if(custlist.size()==0){ //不存在crm 						    														
							Map repPhone=null;
							Map repComenth=null;
							try {
								if("".equals(contmethInfo)){
									repPhone=CallIntrfacePhone(tempCustId, custZhName, custTyp,
										linkUser, zipcode, custEnName, certType,
										linkPhone, contmethInfo, addr, custStat,
										otherName, certNum, jobType, industType);
								}else{
									repPhone=CallIntrfacePhone(tempCustId, custZhName, custTyp,
											linkUser, zipcode, custEnName, certType,
											linkPhone, contmethInfo, addr, custStat,
											otherName, certNum, jobType, industType);
									repComenth=CallIntrfaceComenth(tempCustId, custZhName, custTyp,
											linkUser, zipcode, custEnName, certType,
											linkPhone, contmethInfo, addr, custStat,
											otherName, certNum, jobType, industType);
									
//									System.out.println("手机"+repPhone.get("contmethId"));
//									System.out.println("座机"+repComenth.get("contmethId"));
									
								}							
							} catch (Exception e) {	
								throw new BizException(1,0,"10001",":"+e.getMessage());
								//e.printStackTrace();
							}							
				  		  	//删除扩展表数据：							
							String delsql="update ACRM_F_CI_POT_CUS_COM c set c.del = '1' where c.CUS_ID = '"+tempCustId+"'";
							this.em.createNativeQuery(delsql).executeUpdate();					  					  			
					  		 sql = "insert into ACRM_F_CI_CUSTOMER(cust_id,cust_name,cust_type,linkman_name,en_name,ident_type,cust_stat,short_name,ident_no,create_teller_no,create_date,create_branch_no,POTENTIAL_FLAG," +
							 		"JOB_TYPE,indust_Type)" 
				    	  			+" values('"+repPhone.get("custNo")+"','"+custZhName+"','"+custTyp+"','"+linkUser+"','"+custEnName+"','"+certType+"','','"+otherName+"','"+certNum+"','"+currenUserId+"',"+date+",'"+tempUnitId+"','1'," +
				    	  					"'"+jobType+"','"+industType+"')";
							 sql1 = "insert into ACRM_F_CI_ADDRESS(addr_id,cust_id,zipcode,addr,addr_type) values('"+repPhone.get("addrId")+"','"+repPhone.get("custNo")+"','"+zipcode+"','"+addr+"','02')";
							 //根据contmethInfo
							 
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
								 custsql="insert into ACRM_F_CI_ORG(cust_id,cust_name,org_addr) values('"+repPhone.get("custNo")+"','"+custZhName+"','"+addr+"')";
							 }							 
							 this.em.createNativeQuery(sql).executeUpdate();
							 this.em.createNativeQuery(sql1).executeUpdate();
							 if(!"".equals(contmethInfo)){
								 this.em.createNativeQuery(sql2).executeUpdate();
							 }
							 this.em.createNativeQuery(sql6).executeUpdate();
							 this.em.createNativeQuery(identSql).executeUpdate();
							 this.em.createNativeQuery(custsql).executeUpdate();							 
							 ocrmFCiBelongOrg.setCustId(repPhone.get("custNo").toString());
							 ocrmFCiBelongOrg.setInstitutionCode(tempUnitId);
							 ocrmFCiBelongOrg.setInstitutionName(auth.getUnitName());					 
							 ocrmFCiBelongOrg.setMainType("1");						 
							 List li=auth.getRolesInfo();
							 for(Object m:li){
								 Map map = (Map)m;
								 if("R304".equals(map.get("ROLE_CODE"))){
									 ocrmFCiBelongCustmgr.setMgrId(auth.getUserId());
									 ocrmFCiBelongCustmgr.setAssignUsername(auth.getUsername());
									 break;
								 }else{
									 String user="VM"+auth.getUnitId();
									 String name=auth.getUnitName();
									 ocrmFCiBelongCustmgr.setMgrId(user);
									 ocrmFCiBelongCustmgr.setAssignUsername(name);
								 }
							 }						 
							 ocrmFCiBelongCustmgr.setCustId(repPhone.get("custNo").toString());
							 ocrmFCiBelongCustmgr.setInstitution(auth.getUnitId());
							 ocrmFCiBelongCustmgr.setInstitutionName(auth.getUnitName());
							 ocrmFCiBelongCustmgr.setAssignUser(auth.getUserId());
							 ocrmFCiBelongCustmgr.setMgrName(auth.getCname());
							 ocrmFCiBelongCustmgr.setMainType("1");
							 em.persist(ocrmFCiBelongOrg);
							 em.persist(ocrmFCiBelongCustmgr); 
						}else{//存在crm						
					    Map rep = null;					    
						try {
							rep = CallIntrfaceUpdate(tempCustId,custZhName,custTyp,linkUser,zipcode,custEnName,certType,
							        linkPhone,contmethInfo,addr,custStat,otherName,certNum,jobType,industType);
						} catch (Exception e) {	
							 throw new BizException(1,0,"10001",":"+e.getMessage());
						}						
						//返回客户号后做下面的操作
					    if(rep!=null){							
						String delsql="update ACRM_F_CI_POT_CUS_COM c set c.del = '1' where c.CUS_ID = '"+tempCustId+"'";
						this.em.createNativeQuery(delsql).executeUpdate();
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
					    //  System.out.println("-------->"+sb.toString());					   
					      StringBuilder sb2 = new StringBuilder("update ACRM_F_CI_ADDRESS set  ");
					      if(!"".equals(zipcode)||zipcode!=null){							  
							  sb2.append("zipcode = '"+zipcode+"', ") ;
						  }
					      if(!"".equals(addr)||addr!=null){							  
							  sb2.append("addr = '"+addr+"'") ;
						  }
					      sb2.append("   where CUST_ID = '"+tempCustId+"'") ;					   
					     		
					//判断座机号是否为空
//					String querySql="select * from  ACRM_F_CI_CONTMETH  t where t.CUST_ID = '"+tempCustId+"' and contmeth_type = '202' " ;    					  
//					List<Object[]> querySqlList = this.em.createNativeQuery(querySql).getResultList(); 
//					if(querySqlList.size()>0){
						 sql2="update ACRM_F_CI_CONTMETH c set c.contmeth_info = '"+contmethInfo+"' where c.CUST_ID = '"+tempCustId+"' and c.contmeth_type = '202'";	
						 this.em.createNativeQuery(sql2).executeUpdate();
//					}else{					
//						String sql5= "insert into ACRM_F_CI_CONTMETH(contmeth_id,cust_id,contmeth_type,contmeth_info) values('"+rep.get("contmethId")+"','"+tempCustId+"','202','"+contmethInfo+"')";							
//						this.em.createNativeQuery(sql5).executeUpdate();
//				    }
		     		 //修改证件表
	                 String updateIdentsql="update acrm_f_ci_cust_identifier set ident_type='"+certType+"',ident_no= '"+certNum+"',ident_cust_name='"+custZhName+"'  where CUST_ID = '"+tempCustId+"'";
	    
					 String custsql="";
					 if("2".equals(custTyp)){
						 custsql="update ACRM_F_CI_PERSON  set personal_name='"+custZhName+"', post_addr='"+addr+"'  where CUST_ID = '"+tempCustId+"'";
					 }else{
						 custsql="update ACRM_F_CI_ORG     set cust_name='"+custZhName+"',org_addr='"+addr+"'  where CUST_ID = '"+tempCustId+"'";
					 }
			
				    String sql4="update ACRM_F_CI_CONTMETH c set c.contmeth_info = '"+linkPhone+"' where c.CUST_ID = '"+tempCustId+"' and c.contmeth_type = '100'";					
				    this.em.createNativeQuery(sb.toString()).executeUpdate();
					this.em.createNativeQuery(sb2.toString()).executeUpdate();				    					
					this.em.createNativeQuery(sql4).executeUpdate();
					this.em.createNativeQuery(updateIdentsql).executeUpdate();
					this.em.createNativeQuery(custsql).executeUpdate();
					}
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
					        this.em.createNativeQuery(sb.toString()).executeUpdate();
					}					
				}			
		return sign;
	}	
	
	public Map CallIntrfaceComenth(String tempCustId,String custZhName,String custTyp,String linkUser,String zipcode,String custEnName,String certType,
			String linkPhone,String contmethInfo,String addr,String custStat,String otherName,String certNum,String jobType,String industType) throws Exception{			
		SimpleDateFormat df8 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat df20 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		SimpleDateFormat df10 = new SimpleDateFormat("hhmmssSS");	
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
				"</contmeth>" ;			   
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
			"    </contmeth>";
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
        Map rep = process(msg);		
		return rep;
	}	
	
	public Map CallIntrfacePhone(String tempCustId,String custZhName,String custTyp,String linkUser,String zipcode,String custEnName,String certType,
			String linkPhone,String contmethInfo,String addr,String custStat,String otherName,String certNum,String jobType,String industType) throws Exception{			
		SimpleDateFormat df8 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat df20 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		SimpleDateFormat df10 = new SimpleDateFormat("hhmmssSS");	
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
		"    </contmeth>" ;			
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
        Map rep = process(msg);		
		return rep;
	}	
	public Map CallIntrfaceUpdate(String tempCustId,String custZhName,String custTyp,String linkUser,String zipcode,String custEnName,String certType,
			String linkPhone,String contmethInfo,String addr,String custStat,String otherName,String certNum,String jobType,String industType) throws Exception{
			
		SimpleDateFormat df8 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat df20 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		SimpleDateFormat df10 = new SimpleDateFormat("hhmmssSS");	
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
        	"    <addrType>02</addrType>" + 
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
        	"  <identifier>" + 
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
        	"  </identifier>" + 
        	"</RequestBody>"+
             "</TransBody>";
		    }else{
		    	perIdentifier=
	        	"  <identifier>" + 
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
	        	"  </identifier>" + 
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
	    	  "    <addrType>02</addrType>" + 
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
	    	  "  <identifier>" + 
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
	    	  "  </identifier>" ;
		  }else{
			  orgIdentifier=
			  "  <identifier>" + 
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
	    	  "  </identifier>" ;  			  
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
        Map rep = process(msg);		
		return rep;
	}
	
	//将异常全部抛出
	public static Map process(String mxlmsg) throws Exception {	
		Map idsMap=new HashMap();
		System.out.println("访问报文:"+mxlmsg);
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
			throw e;
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
			   throw new BizException(1,0,"10001",TxStatDesc+"调用ECIF接口失败或超时,请稍后重试！");
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
			//e.printStackTrace();
			throw e;
		}catch(Exception e){
			//e.printStackTrace();
		   throw e;
		}		
		return idsMap;
	}

	public void update(JSONArray jarray)  {
		for (int i=0;i<jarray.size();i++)  {
		MyPotentialCustomer myPotentialCustomer = em.find(MyPotentialCustomer.class,Long.parseLong(jarray.get(i).toString()));
		if(("0").equals(myPotentialCustomer.getAssignSts())){
		myPotentialCustomer.setAssignSts("1");
		em.merge(myPotentialCustomer);}}
		
	}
    public String getDate() throws ParseException{   
         //DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");    
         Calendar ca = Calendar.getInstance();   
         int year = ca.get(Calendar.YEAR);//获取年份   
         int month=ca.get(Calendar.MONTH);//获取月份    
         int day=ca.get(Calendar.DATE);//获取日   
         String date = year + "" + (month + 1 )+ "" + (day-1);      	         
         //Date  date1 = format1.parse(date);  
         return date;   
      }
    public Date getDate2() throws ParseException{   
         DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");    
         Calendar ca = Calendar.getInstance();   
         int year = ca.get(Calendar.YEAR);//获取年份   
         int month=ca.get(Calendar.MONTH);//获取月份    
         int day=ca.get(Calendar.DATE);//获取日   
         String date = year + "-" + (month + 1 )+ "-" + day;   
         Date  date1 = format1.parse(date);  
         return date1;   
    }
    
    public String saveLatent(String tempCustId,String custZhName,String custTyp,String linkUser,String zipcode,String sourceChannel,String mktActivitie,String cusNationality,String refereesId,String certType,
			String linkPhone,String contmethInfo,String addr,String custStat,String certNum,String jobType,String industType,String recordSession,String cusEmail,String cusWechatid){	
			  System.out.println("data>"+tempCustId+ custZhName+ custTyp+ linkUser+ zipcode+ sourceChannel+mktActivitie+cusNationality+refereesId+certType+ linkPhone+ contmethInfo+ addr+ custStat+ certNum+ jobType+ industType);		
			  String sign=null;	
			  AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			  	Date date1 = new Date();
				DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String date = format1.format(date1);
				date = "to_date('" + date + "','yyyy-mm-dd hh24:mi:ss')";
				String callNo = linkPhone;				
				if(industType==null){
					industType="";
				}
				String sql="";
				if(!"".equals(tempCustId)&&tempCustId!=null){
					sql="update ACRM_F_CI_POT_CUS_COM" +
							"   set  "+
							"       CUS_NAME = '"+custZhName+"'," + 
							"       CONTMETH_INFO = '"+contmethInfo+"'," + 
							"       JOB_TYPE = '"+jobType+"'," + 
							"       CERT_TYPE = '2'," + 
							"       CERT_CODE = '"+certNum+"'," + 
							"       ZIPCODE = '"+zipcode+"'," + 
							"       SOURCE_CHANNEL = '"+sourceChannel+"',"+
							"       MKT_ACTIVITIE ='"+mktActivitie+"'," + 
							"       CUS_NATIONALITY ='"+cusNationality+"'," +
							"       REFEREES_ID ='"+refereesId+"'," +
							"       INDUST_TYPE = '"+industType+"'," + 
							"       CALL_NO = '" +callNo+"'," + 
							"       ATTEN_NAME = '"+linkUser+"'," + 
							"       CUST_TYPE = '"+custTyp+"'," + 
							"       RECORD_SESSION = '"+recordSession+"'," + 
							"       CUS_ADDR = '"+addr+"' ,  "+
							"       CUS_EMAIL = '"+cusEmail+"' ,  "+
							"       CUS_WECHATID = '"+cusWechatid+"'   "+
							"       where CUS_ID = '"+tempCustId+"'";
					
	                 try {
	                	 String selectSql="select c.cus_name,c.call_no,c.cert_type,c.cert_code,c.mkt_activitie,c.source_channel,c.cus_nationality,c.referees_id,c.atten_name,c.contmeth_info,c.zipcode,c.indust_type,c.job_type,c.cus_addr,c.cust_mgr,c.record_session,c.CUS_EMAIL,c.CUS_WECHATID "
		         					+ " from acrm_f_ci_pot_cus_com c where 1=1 and c.cus_id='"+tempCustId+"'";
		         			List<Object[]> listResult=new ArrayList<Object[]>();
		         			listResult = this.em.createNativeQuery(selectSql).getResultList();
		         			
						  this.em.createNativeQuery(sql).executeUpdate();
						 
						if(listResult!=null&&listResult.size()>0){
							Object[] listResults=listResult.get(0);
						insertEditablelatent(listResults,tempCustId,custZhName,custTyp,linkUser,zipcode,sourceChannel,mktActivitie,cusNationality,refereesId,certType,
								linkPhone,contmethInfo,addr,certNum,jobType,industType,format1.format(new Date()),recordSession,cusEmail,cusWechatid);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else{
				//生成CUST_ID
				Random random1 = new Random();
				int sp=Math.abs(random1.nextInt());
				String result="crm"+sp;
			     sql = 
								   "insert into ACRM_F_CI_POT_CUS_COM" +
								   "  (cus_id," + 
								   "   cus_name," + 
								   "   CUST_TYPE," + 
								   "   ATTEN_NAME," + 
								   "   ZIPCODE," + 
								   "   SOURCE_CHANNEL," + 
								   "   MKT_ACTIVITIE," + 
								   "   CUS_NATIONALITY," + 
								   "   REFEREES_ID," + 
								   "   CERT_TYPE," + 
								   "   CERT_CODE," + 
								   "   CALL_NO," + 
								   "   CONTMETH_INFO," + 
								   "   CUST_STAT," + 
								   "   JOB_TYPE," + 
								   "   CUS_ADDR," + 
								   "   RECORD_SESSION,"+
								   "   INDUST_TYPE,"+
								   "   delete_cust_state," +
								   "   BACK_STATE," +
								   "   CUST_MGR," +
								   "   MAIN_BR_ID," +
								   "   INPUT_ID,"+
								   "   INPUT_BR_ID,"+
								   "   INPUT_DATE,"+
								   " FORMAL_CUST_FLAG,"+
								   " CUS_EMAIL,"+
								   " CUS_WECHATID"+
								   "   )" + 
								   "values" + 
								   "  (" + 
								   "  '"+result+"'," + 
								   "  '"+custZhName+"'," + 
								   "  '2'," + 
								   "  '"+linkUser+"'," + 
								   "  '"+zipcode+"'," + 
								   "  '"+sourceChannel+"'," + 
								   "  '"+mktActivitie+"'," + 
								   "  '"+cusNationality+"'," + 
								   "  '"+refereesId+"'," + 
								   "  '"+certType+"'," + 
								   "  '"+certNum+"'," + 
								   "  '"+callNo+"'," + 
								   "  '"+contmethInfo+"'," + 
								   "  '',"  + 
								   "  '"+jobType+"'," + 
								   "  '"+addr+"'," + 
								   "  '"+recordSession+"'," + 
								   "  '"+industType+"',"+
								   " '2'," + 
								   " '3'," + 
								   "  '"+auth.getUserId()+"'," + 
								   "  '"+auth.getUnitId()+"'," + 
								   "  '"+auth.getUserId()+"'," + 
								   "  '"+auth.getUnitId()+"'," + 
								   "  '" + format1.format(new Date()) + "',"+
								   " '1'," + 
								   "  '"+cusEmail+"'," + 
								   "  '"+cusWechatid+"'" + 
								   "   )";
						    // System.out.println("转入临时客户:"+sql);                   
			                 try {
			                	
								this.em.createNativeQuery(sql).executeUpdate();
							} catch (Exception e) {
								e.printStackTrace();
							}
				}
		                
				
		           return sign;
			}
    
    //记录个金潜在客户修改项
   public void insertEditablelatent(Object[] listResults,String tempCustId,String custZhName,String custTyp,String linkUser,String zipcode,String sourceChannel,String mktActivitie,String cusNationality,String refereesId,String certType,
			String linkPhone,String contmethInfo,String addr,String certNum,String jobType,String industType,String edit_date,String recordSession,String cusEmail,String cusWechatid){
    	try {
    	    AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(!custZhName.equals(listResults[0])&&listResults[0]!=null){
				String sql1="insert into acrm_f_ci_pot_cus_changehis (cus_id,cus_for_mgr,edit_user,edit_date,edit_before_item,edit_before_content,edit_after_item,edit_after_content) "
						+ "values ('"+tempCustId+"','"+listResults[14]+"','"+auth.getUserId()+"','"+edit_date+"','客户名称','"+listResults[0]+"','客户名称','"+custZhName+"')";
				this.em.createNativeQuery(sql1).executeUpdate();
			}
			if(!linkPhone.equals(listResults[1])&&listResults[1]!=null){
				String sql2="insert into acrm_f_ci_pot_cus_changehis (cus_id,cus_for_mgr,edit_user,edit_date,edit_before_item,edit_before_content,edit_after_item,edit_after_content) "
						+ "values ('"+tempCustId+"','"+listResults[14]+"','"+auth.getUserId()+"','"+edit_date+"','手机号码','"+listResults[1]+"','手机号码','"+linkPhone+"')";
				this.em.createNativeQuery(sql2).executeUpdate();
			}
			if(!certType.equals(listResults[2])&&listResults[2]!=null){
				String sql3="insert into acrm_f_ci_pot_cus_changehis (cus_id,cus_for_mgr,edit_user,edit_date,edit_before_item,edit_before_content,edit_after_item,edit_after_content) "
						+ "values ('"+tempCustId+"','"+listResults[14]+"','"+auth.getUserId()+"','"+edit_date+"','证件类型','"+listResults[2]+"','证件类型','"+certType+"')";
				this.em.createNativeQuery(sql3).executeUpdate();
			}
			if(!certNum.equals(listResults[3])&&listResults[3]!=null){
				String sql4="insert into acrm_f_ci_pot_cus_changehis (cus_id,cus_for_mgr,edit_user,edit_date,edit_before_item,edit_before_content,edit_after_item,edit_after_content) "
						+ "values ('"+tempCustId+"','"+listResults[14]+"','"+auth.getUserId()+"','"+edit_date+"','证件号码','"+listResults[3]+"','证件号码','"+certNum+"')";
				this.em.createNativeQuery(sql4).executeUpdate();
			}
			if(!mktActivitie.equals(listResults[4])&&listResults[4]!=null){
				String sql5="insert into acrm_f_ci_pot_cus_changehis (cus_id,cus_for_mgr,edit_user,edit_date,edit_before_item,edit_before_content,edit_after_item,edit_after_content) "
						+ "values ('"+tempCustId+"','"+listResults[14]+"','"+auth.getUserId()+"','"+edit_date+"','营销活动','"+listResults[4]+"','营销活动','"+mktActivitie+"')";
				this.em.createNativeQuery(sql5).executeUpdate();
			}
			if(!sourceChannel.equals(listResults[5])&&listResults[5]!=null){
				String sql6="insert into acrm_f_ci_pot_cus_changehis (cus_id,cus_for_mgr,edit_user,edit_date,edit_before_item,edit_before_content,edit_after_item,edit_after_content) "
						+ "values ('"+tempCustId+"','"+listResults[14]+"','"+auth.getUserId()+"','"+edit_date+"','来源渠道','"+listResults[5]+"','来源渠道','"+sourceChannel+"')";
				this.em.createNativeQuery(sql6).executeUpdate();
			}
			if(!cusNationality.equals(listResults[6])&&listResults[6]!=null){
				String sql7="insert into acrm_f_ci_pot_cus_changehis (cus_id,cus_for_mgr,edit_user,edit_date,edit_before_item,edit_before_content,edit_after_item,edit_after_content) "
						+ "values ('"+tempCustId+"','"+listResults[14]+"','"+auth.getUserId()+"','"+edit_date+"','国籍','"+listResults[6]+"','国籍','"+cusNationality+"')";
				this.em.createNativeQuery(sql7).executeUpdate();
			}
			if(!refereesId.equals(listResults[7])&&listResults[7]!=null){
				String sql8="insert into acrm_f_ci_pot_cus_changehis (cus_id,cus_for_mgr,edit_user,edit_date,edit_before_item,edit_before_content,edit_after_item,edit_after_content) "
						+ "values ('"+tempCustId+"','"+listResults[14]+"','"+auth.getUserId()+"','"+edit_date+"','推荐人','"+listResults[7]+"','推荐人','"+refereesId+"')";
				this.em.createNativeQuery(sql8).executeUpdate();
			}
			if(!linkUser.equals(listResults[8])&&listResults[8]!=null){
				String sql9="insert into acrm_f_ci_pot_cus_changehis (cus_id,cus_for_mgr,edit_user,edit_date,edit_before_item,edit_before_content,edit_after_item,edit_after_content) "
						+ "values ('"+tempCustId+"','"+listResults[14]+"','"+auth.getUserId()+"','"+edit_date+"','联系人','"+listResults[8]+"','联系人','"+linkUser+"')";
				this.em.createNativeQuery(sql9).executeUpdate();
			}
			if(!contmethInfo.equals(listResults[9])&&listResults[9]!=null){
				String sql10="insert into acrm_f_ci_pot_cus_changehis (cus_id,cus_for_mgr,edit_user,edit_date,edit_before_item,edit_before_content,edit_after_item,edit_after_content) "
						+ "values ('"+tempCustId+"','"+listResults[14]+"','"+auth.getUserId()+"','"+edit_date+"','座机号','"+listResults[9]+"','座机号','"+contmethInfo+"')";
				this.em.createNativeQuery(sql10).executeUpdate();
			}
			if(!zipcode.equals(listResults[10])&&listResults[10]!=null){
				String sql11="insert into acrm_f_ci_pot_cus_changehis (cus_id,cus_for_mgr,edit_user,edit_date,edit_before_item,edit_before_content,edit_after_item,edit_after_content) "
						+ "values ('"+tempCustId+"','"+listResults[14]+"','"+auth.getUserId()+"','"+edit_date+"','邮编','"+listResults[10]+"','邮编','"+zipcode+"')";
				this.em.createNativeQuery(sql11).executeUpdate();
			}
			if(!industType.equals(listResults[11])&&listResults[11]!=null){
				String sql12="insert into acrm_f_ci_pot_cus_changehis (cus_id,cus_for_mgr,edit_user,edit_date,edit_before_item,edit_before_content,edit_after_item,edit_after_content) "
						+ "values ('"+tempCustId+"','"+listResults[14]+"','"+auth.getUserId()+"','"+edit_date+"','所属行业','"+listResults[11]+"','所属行业','"+industType+"')";
				this.em.createNativeQuery(sql12).executeUpdate();
			}
			if(!jobType.equals(listResults[12])&&listResults[12]!=null){
				String sql13="insert into acrm_f_ci_pot_cus_changehis (cus_id,cus_for_mgr,edit_user,edit_date,edit_before_item,edit_before_content,edit_after_item,edit_after_content) "
						+ "values ('"+tempCustId+"','"+listResults[14]+"','"+auth.getUserId()+"','"+edit_date+"','职业类别','"+listResults[12]+"','职业类别','"+jobType+"')";
				this.em.createNativeQuery(sql13).executeUpdate();
			}
			if(!addr.equals(listResults[13])&&listResults[13]!=null){
				String sql14="insert into acrm_f_ci_pot_cus_changehis (cus_id,cus_for_mgr,edit_user,edit_date,edit_before_item,edit_before_content,edit_after_item,edit_after_content) "
						+ "values ('"+tempCustId+"','"+listResults[14]+"','"+auth.getUserId()+"','"+edit_date+"','通信地址','"+listResults[13]+"','通信地址','"+addr+"')";
				this.em.createNativeQuery(sql14).executeUpdate();
			}
			if(!recordSession.equals(listResults[15])&&listResults[15]!=null){
				String sql15="insert into acrm_f_ci_pot_cus_changehis (cus_id,cus_for_mgr,edit_user,edit_date,edit_before_item,edit_before_content,edit_after_item,edit_after_content) "
						+ "values ('"+tempCustId+"','"+listResults[14]+"','"+auth.getUserId()+"','"+edit_date+"','沟通话术','"+listResults[15]+"','沟通话术','"+recordSession+"')";
				this.em.createNativeQuery(sql15).executeUpdate();
			}
			if(!cusEmail.equals(listResults[16])&&listResults[16]!=null){
				String sql16="insert into acrm_f_ci_pot_cus_changehis (cus_id,cus_for_mgr,edit_user,edit_date,edit_before_item,edit_before_content,edit_after_item,edit_after_content) "
						+ "values ('"+tempCustId+"','"+listResults[14]+"','"+auth.getUserId()+"','"+edit_date+"','Email','"+listResults[16]+"','Email','"+cusEmail+"')";
				this.em.createNativeQuery(sql16).executeUpdate();
			}
			if(!cusWechatid.equals(listResults[17])&&listResults[17]!=null){
				String sql17="insert into acrm_f_ci_pot_cus_changehis (cus_id,cus_for_mgr,edit_user,edit_date,edit_before_item,edit_before_content,edit_after_item,edit_after_content) "
						+ "values ('"+tempCustId+"','"+listResults[14]+"','"+auth.getUserId()+"','"+edit_date+"','微信号','"+listResults[17]+"','微信号','"+cusWechatid+"')";
				this.em.createNativeQuery(sql17).executeUpdate();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
 // 删除
 	public void batchDel(HttpServletRequest request) {
 		String cusId = request.getParameter("cusId");
 		String deleteReason = request.getParameter("deleteReason");
 		String deleteNote = request.getParameter("deleteNote");
 		this.em.createNativeQuery("update ACRM_F_CI_POT_CUS_COM set state='1',delete_reason='"+deleteReason+"',delete_note='"+deleteNote+"',OPERATE_TIME =systimestamp where CUS_ID in ('"+cusId+"')").executeUpdate();
 	}
 
 	 // 恢复
 	public void recoverBackInfo(HttpServletRequest request) {
 		 AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 		String cusId = request.getParameter("cusId");
 		String userId=auth.getUserId();//当前用户ID
	   	String orgId = auth.getUnitId();//当前用户的机构编号
 		this.em.createNativeQuery("update ACRM_F_CI_POT_CUS_COM set state='0',cust_mgr='"+userId+"', main_br_id='"+orgId+"',OPERATE_TIME =systimestamp where CUS_ID in ("+cusId+")").executeUpdate();
 	}
 	
 	 // 设置审批状态
 	public void updatedelete_cust_state(String cusId) {
 		this.em.createNativeQuery("update ACRM_F_CI_POT_CUS_COM set delete_cust_state='1',OPERATE_TIME =systimestamp where CUS_ID in ('"+cusId+"')").executeUpdate();
 	}
 	
 // 更新营销活动
  	public void editedmktActivitie(HttpServletRequest request) {
  	  AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  		String cusId = request.getParameter("cusId");
  		String mktActivitie = request.getParameter("mktActivitie");
  		SimpleDateFormat sdfs = new SimpleDateFormat("yyyyMMddHHmmss");
  	  	String time = sdfs.format(new Date());
  		 String selectSql="select c.MKT_ACTIVITIE,c.cust_mgr from acrm_f_ci_pot_cus_com c where 1=1 and c.cus_id='"+cusId+"'";
			List<Object[]> listResult=new ArrayList<Object[]>();
			listResult = this.em.createNativeQuery(selectSql).getResultList();
			Object[] listResults=null;
			if(listResult!=null&&listResult.size()>0){
				 listResults=listResult.get(0);
			}
  		this.em.createNativeQuery("update ACRM_F_CI_POT_CUS_COM set MKT_ACTIVITIE='"+mktActivitie+"',OPERATE_TIME =systimestamp where CUS_ID in ('"+cusId+"')").executeUpdate();
  		String sql6="insert into acrm_f_ci_pot_cus_changehis (cus_id,cus_for_mgr,edit_user,edit_date,edit_before_item,edit_before_content,edit_after_item,edit_after_content) "
				+ "values ('"+cusId+"','"+listResults[1]+"','"+auth.getUserId()+"','"+time+"','营销活动','"+listResults[0]+"','营销活动','"+mktActivitie+"')";
		this.em.createNativeQuery(sql6).executeUpdate();
  	}
  	
  //分配
  	public void fpLatentInfo(HttpServletRequest request){
  		String cusId = request.getParameter("cusId");
  		String type=request.getParameter("type");
  		String custMgr = request.getParameter("custMgr");
  		if(type.equals("2")){//分配到人	
  			boolean flag= checkRecevierRole(custMgr);
  			if(flag){//分配的人已经是RM
  				 this.em.createNativeQuery("update acrm_f_ci_pot_cus_com set back_state='0' where cus_id in("+cusId+")").executeUpdate();	
  			}
  			List list = this.em.createNativeQuery("select ORG_ID from  ADMIN_AUTH_ACCOUNT  where account_name='"+custMgr+"'").getResultList();
  			String orgId="";
  			for(int i=0;i<list.size();i++){
  				orgId = (String) list.get(i);
  			}
  			if(orgId!=null && !"".equals(orgId)){
  				this.em.createNativeQuery("update ACRM_F_CI_POT_CUS_COM set cust_mgr='"+custMgr+"', main_br_id='"+orgId+"'where  CUS_ID in ("+cusId+")").executeUpdate();
  			}
  		}else{//分配到机构
  		    this.em.createNativeQuery("update ACRM_F_CI_POT_CUS_COM set cust_mgr='"+custMgr+"' where CUS_ID in ("+cusId+")").executeUpdate();
  		    
  		}
  	}
  	
  	 //接受角色
      public boolean checkRecevierRole(String custMgr){	
      	boolean flag = false;
      	try{	
      		List list = this.em.createNativeQuery("SELECT ROLE_CODE FROM ( SELECT F.*," +
  						"(CASE WHEN " +
  						" F.IDCHECK IS NULL THEN " +
  						" 0 " +
  						" ELSE " +
  						" 1 " +
  						" END) IS_CHECKED " +
  						" FROM (SELECT * " +
  						" FROM ADMIN_AUTH_ROLE T0 " +
  						" LEFT JOIN (SELECT T1.ID AS IDCHECK, T1.ROLE_ID " +
  						"  FROM ADMIN_AUTH_ACCOUNT_ROLE T1 " +
  						"  LEFT JOIN ADMIN_AUTH_ACCOUNT T2 " +
  						"  ON T2.ID = T1.ACCOUNT_ID " +
  						"  WHERE T1.ACCOUNT_ID = (SELECT T2.ID FROM  ADMIN_AUTH_ACCOUNT T2 WHERE T2.ACCOUNT_NAME IN ('"+custMgr+"'))) E " +
  						"   ON E.ROLE_ID = T0.ID " +
  						"  WHERE T0.ROLE_LEVEL >= '1') F " +
  						" WHERE 1 = 1 " +
  						" ORDER BY F.ROLE_LEVEL) WHERE IS_CHECKED='1'").getResultList();
  		    if(list!=null && list.size()==1){
  		    	if(list.get(0).equals("R305") || list.get(0).equals("R105")){
  		    		flag = true;
  		    	}
  		    }
      	}catch(Exception e){
      		e.printStackTrace();
      	}
      	return flag;
      }
  	
  	public void updatePotCusInfo(String sql){
  		this.em.createNativeQuery(sql).executeUpdate();
  	}
  	
  	public List selectBySql(String sql){
  		List list = this.em.createNativeQuery(sql).getResultList();
		return list;
  	}
}
