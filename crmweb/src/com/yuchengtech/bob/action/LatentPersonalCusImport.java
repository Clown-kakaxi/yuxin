package com.yuchengtech.bob.action;


import java.math.BigDecimal;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;




import org.apache.log4j.Logger;

import com.yuchengtech.bcrm.customer.service.MyPotentialCustomerService;
import com.yuchengtech.bob.importimpl.ImportInterface;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.JdbcUtil;
import com.yuchengtech.crm.exception.BizException;
/**
 * 个金潜在客户批量导入类：LatentPersonalCusImport
 * @author Administrator
 * 2016-01-18
 *
 */
public class LatentPersonalCusImport implements ImportInterface {
	private static Logger log = Logger.getLogger(LatentPersonalCusImport.class);
	
	 // 释放连接
		public static void free(ResultSet rs, Statement st) {
			try {
				if (rs != null) {
					rs.close(); // 关闭结果集
				}
				if (st != null) {
					st.close(); // 关闭Statement
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			} 

		}
		 static String regx = "^((-?\\d+.?\\d*)[Ee]{1}(-?\\d+))$";//科学计数法正则表达式
  	     static Pattern pattern = Pattern.compile(regx);
  	     public static boolean isENum(String input){//判断输入字符串是否为科学计数法
  	        return pattern.matcher(input).matches();
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
	public void excute(Connection conn, String PKhead,AuthUser aUser)throws Exception {
		log.info("updateSQL: [LatentPersonalCusImport has been evoke!]");
		Statement  stm=null;
		Map<String,Object> map=null;
		try {
			stm = conn.createStatement();
			String updateSql="update ACRM_F_CI_POT_CUS_COM_TEMP t set t.area_code = substr(t.area_code, 0, instr(t.area_code,'-')-1),t.cust_type = substr(t.cust_type, 0, instr(t.cust_type,'-')-1), t.SOURCE_CHANNEL = substr(t.SOURCE_CHANNEL, 0, instr(t.SOURCE_CHANNEL,'-')-1), t.CUS_NATIONALITY = substr(t.CUS_NATIONALITY, 0, instr(t.CUS_NATIONALITY,'-')-1), t.CERT_TYPE = substr(t.CERT_TYPE, 0, instr(t.CERT_TYPE,'-')-1), t.JOB_TYPE = substr(t.JOB_TYPE, 0, instr(t.JOB_TYPE,'-')-1), t.INDUST_TYPE = substr(t.INDUST_TYPE, 0, instr(t.INDUST_TYPE,'-')-1),t.MKT_ACTIVITIE = substr(t.MKT_ACTIVITIE, 0, instr(t.MKT_ACTIVITIE,'-')-1) where ID like '" + PKhead + "%'";
			log.info("updateSQL: [ "+updateSql+" ] ");
			stm.execute(updateSql);
			conn.setAutoCommit(false);
			 //StringBuffer  sb1=new StringBuffer("select * from ( select d.CUS_ID, d.CUS_NAME, d.mkt_activitie, d.source_channel, d.referees_id, d.cus_nationality, d.CERT_TYPE, d.CERT_CODE, d.CUST_TYPE, d.ATTEN_NAME, d.job_type, d.indust_type, d.CALL_NO, d.zipcode, d.contmeth_info, d.CUS_ADDR from ACRM_F_CI_POT_CUS_COM D where d.cust_type='2' union select dt.ID CUS_ID, dt.CUS_NAME, dt.mkt_activitie, dt.source_channel, dt.referees_id, dt.cus_nationality, dt.CERT_TYPE, dt.CERT_CODE, dt.CUST_TYPE, dt.ATTEN_NAME, dt.job_type, dt.indust_type, dt.CALL_NO, dt.zipcode, dt.contmeth_info, dt.CUS_ADDR from ACRM_F_CI_POT_CUS_COM_TEMP dt ) t where  CUS_ID like '" + PKhead + "%'");
			StringBuffer sb1=new StringBuffer("select * from (select d.call_no,d.CUS_NAME, d.CERT_TYPE, d.CERT_CODE   from acrm_f_ci_pot_cus_createtemp d  where (d.CUS_NAME, d.CERT_TYPE, d.CERT_CODE,d.call_no) in        (select dt.CUS_NAME, dt.CERT_TYPE,dt.call_no, dt.CERT_CODE   from acrm_f_ci_pot_cus_createtemp dt   group by dt.CUS_NAME, dt.CERT_TYPE,dt.call_no, dt.CERT_CODE   having count(*) > 1)    and rowid not in (select min(rowid)  from acrm_f_ci_pot_cus_createtemp   group by CUS_NAME, CERT_TYPE,call_no, CERT_CODE  having count(*) > 1)   union   select d.call_no,d.CUS_NAME, d.CERT_TYPE, d.CERT_CODE   from ACRM_F_CI_POT_CUS_COM_TEMP d  where (d.CUS_NAME, d.CERT_TYPE, d.CERT_CODE,d.call_no) in (select dt.CUS_NAME, dt.CERT_TYPE,dt.call_no, dt.CERT_CODE           from ACRM_F_CI_POT_CUS_COM_TEMP dt          group by dt.CUS_NAME, dt.CERT_TYPE,dt.call_no, dt.CERT_CODE         having count(*) > 1)    and rowid not in (select min(rowid) from ACRM_F_CI_POT_CUS_COM_TEMP     group by CUS_NAME, CERT_TYPE,call_no, CERT_CODE  having count(*) > 1) )  ");
			Map map1=null;
			List list=read(conn,sb1.toString());
			StringBuffer sb=new StringBuffer();
			if(list.size()>0){
				for(int i=0;i<list.size();i++){
				 map1 = (Map) list.get(i);
				String line= map1.get("CUS_ID").toString();
				 sb.append(line.substring(8, line.length())+" , ");
				}					 
				aUser.putAttribute(BACK_IMPORT_ERROR, "EXCEL中第"+sb.toString()+"行数据在已经存在，请删除第"+sb.toString()+"行重新导入");												
				throw new BizException(1, 0, "10001", "请清除已存在的用户");
			}	
		  String custId = "";//客户号
  	      String custZhName = "";//客户名称
  	      String custTyp ="";//客户类型
  	      String linkUser ="";//联系人
  	      String zipcode ="";//邮编
  	      String sourceChannel ="";//来源渠道
  	      String mktActivitie ="";//营销活动
  	      String cusNationality ="";//国籍
  	      String refereesId ="";//推荐人
  	      String certType ="";//证件类型
  	      String linkPhone ="";//手机号码
  	      String contmethInfo ="";//座机号
  	      String custStat ="";//客户状态默认‘潜在’
  	      String jobType =""; //职业类型
  	      String industType =""; //所属行业  
  	      String certNum ="";//证件号码
  	      String addr ="";//通讯地址
  	      String recordSession="";//沟通话术
  	     String cusEmail="";//Email
  	     String cusWechatid="";//微信号
  	     String areaCode=""; //手机区号
  	     int sumSuccesscount=0;
  	     int sunfirescount=0;
  	     int failureBycallno=0;
  	     int failurecount=0;
  	   
  	      String tempSql="SELECT D.ID,D.CUS_NAME,D.CERT_TYPE,D.CERT_CODE,D.CUST_TYPE,D.ATTEN_NAME,D.JOB_TYPE,D.INDUST_TYPE,D.CALL_NO,D.ZIPCODE,D.CONTMETH_INFO,D.CUS_ADDR,D.MKT_ACTIVITIE, D.SOURCE_CHANNEL,D.CUS_NATIONALITY,D.REFEREES_ID,D.RECORD_SESSION,D.CUS_EMAIL,D.CUS_WECHATID,D.AREA_CODE FROM ACRM_F_CI_POT_CUS_COM_TEMP D  where ID like '" + PKhead + "%'";
			    List mycustList= read(conn, tempSql);			  
				Map dataMap = new HashMap();
				String nametype="";
				String phtype="";
				for(int i=0;i<mycustList.size();i++){ 
					 dataMap=(Map) mycustList.get(i);
					
					 custZhName=dataMap.get("CUS_NAME")==null?"":dataMap.get("CUS_NAME").toString().replaceAll("　| ", "").replaceAll("[?]", "").replaceAll("[？]", "");
					 custTyp=dataMap.get("CUST_TYPE")==null?"":dataMap.get("CUST_TYPE").toString(); 
					 linkUser=dataMap.get("ATTEN_NAME")==null?"":dataMap.get("ATTEN_NAME").toString(); 
					 zipcode=dataMap.get("ZIPCODE")==null?"":dataMap.get("ZIPCODE").toString().replaceAll("　| ", "").replaceAll("[?]", "").replaceAll("[？]", "");
					 if(!"".equals(zipcode)&&!"null".equals(zipcode)){
						 if(isENum(zipcode)){
						 zipcode=new BigDecimal(zipcode).toPlainString();
						 }
					 }
					 sourceChannel=dataMap.get("SOURCE_CHANNEL")==null?"":dataMap.get("SOURCE_CHANNEL").toString();
					 mktActivitie=dataMap.get("MKT_ACTIVITIE")==null?"":dataMap.get("MKT_ACTIVITIE").toString();
					 cusNationality=dataMap.get("CUS_NATIONALITY")==null?"":dataMap.get("CUS_NATIONALITY").toString();
					 refereesId=dataMap.get("REFEREES_ID")==null?"":dataMap.get("REFEREES_ID").toString().replaceAll("　| ", "").replaceAll("[?]", "").replaceAll("[？]", "");
					 if(!Pattern.compile("(?i)[a-z]").matcher(refereesId).find()){
						 if(!"".equals(refereesId)&&!"null".equals(refereesId)){
							 if(isENum(refereesId)){
							 refereesId=new BigDecimal(refereesId).toPlainString();
							 }
						 }
					 }
					 certType=dataMap.get("CERT_TYPE")==null?"":dataMap.get("CERT_TYPE").toString();
					 linkPhone=dataMap.get("CALL_NO")==null?"":dataMap.get("CALL_NO").toString().replaceAll("　| ", "").replaceAll("[?]", "").replaceAll("[？]", "");
					 if(!"".equals(linkPhone)&&!"null".equals(linkPhone)){
						 if(isENum(linkPhone)){
							 linkPhone=new BigDecimal(linkPhone).toPlainString();
						 }
					 }
					 contmethInfo=dataMap.get("CONTMETH_INFO")==null?"":dataMap.get("CONTMETH_INFO").toString().replaceAll("　| ", "").replaceAll("[?]", "").replaceAll("[？]", "");
					 if(!"".equals(contmethInfo)&&!"null".equals(contmethInfo)){
						 if(isENum(contmethInfo)){
						 contmethInfo=new BigDecimal(contmethInfo).toPlainString();
						 }
					 }
					
					 jobType=dataMap.get("JOB_TYPE")==null?"":dataMap.get("JOB_TYPE").toString();
					 industType=dataMap.get("INDUST_TYPE")==null?"":dataMap.get("INDUST_TYPE").toString();
					 certNum=dataMap.get("CERT_CODE")==null?"":dataMap.get("CERT_CODE").toString().replaceAll("　| ", "").replaceAll("[?]", "").replaceAll("[？]", "");
					 if(!"".equals(certNum)&&!"null".equals(certNum)){
						 if(isENum(certNum)){
						 certNum=new BigDecimal(certNum).toPlainString();
						 }
					 }
					
					 addr=dataMap.get("CUS_ADDR")==null?"":dataMap.get("CUS_ADDR").toString();
					 recordSession=dataMap.get("RECORD_SESSION")==null?"":dataMap.get("RECORD_SESSION").toString().replaceAll("　| ", "");
					 cusEmail=dataMap.get("CUS_EMAIL")==null?"":dataMap.get("CUS_EMAIL").toString();
					 cusWechatid=dataMap.get("CUS_WECHATID")==null?"":dataMap.get("CUS_WECHATID").toString();
					 areaCode=dataMap.get("AREA_CODE")==null?"":dataMap.get("AREA_CODE").toString();
					 dataMap=(Map)mycustList.get(i);	
					 log.info("数据列表: "+mycustList);
					 String pattern = "[0-9]{11}";
					 String pattern1 = "[0-9]\\d*";   //匹配数字
					 String pattern2 = "[0-9]{10}";   //台湾手机号吗 以9开头 10位数字
						//Pattern r = Pattern.compile(pattern);
						//Matcher m = r.matcher(linkPhone);
					     if(Pattern.matches(pattern1,linkPhone)==false){
					    	 failureBycallno++;
								continue;
					     }
						 if("86".equals(areaCode)&&Pattern.matches(pattern,linkPhone)==false){
							failureBycallno++;
							continue;
						 }
						 if("886".equals(areaCode)&&Pattern.matches(pattern2,linkPhone)==false){
								failureBycallno++;
								continue;
							 }
					 linkPhone=areaCode+"-#-"+linkPhone;
		            map=doParameterCheck(conn,custZhName,certType,certNum,linkPhone);
					 nametype = map.get("type").toString();
					 phtype=map.get("phtype").toString();
					if(!"".equals(custZhName)&&!"".equals(certType)&&!"".equals(certNum)){
								if ("1".equals(nametype)) {
									failurecount++;
								} else if ("2".equals(nametype)) {
									failurecount++;
								} else if ("3".equals(nametype)) {
								                   if("4".equals(phtype)){
														//保存到待处理清单
														saveAsLatent(conn,aUser,custId, custZhName, custTyp, linkUser, zipcode, sourceChannel,mktActivitie,cusNationality,refereesId,certType, linkPhone, contmethInfo, addr, custStat,certNum, jobType, industType,recordSession,cusEmail,cusWechatid,areaCode);		
														sunfirescount++;
													}else{
														//导入保存
														  saveLatent(conn,aUser,custId, custZhName, custTyp, linkUser, zipcode, sourceChannel,mktActivitie,cusNationality,refereesId,certType, linkPhone, contmethInfo, addr, custStat,certNum, jobType, industType,recordSession,cusEmail,cusWechatid,areaCode);							
														  sumSuccesscount++;
														}
								}else{
									 if("4".equals(phtype)){
										//保存到待处理清单
										saveAsLatent(conn,aUser,custId, custZhName, custTyp, linkUser, zipcode, sourceChannel,mktActivitie,cusNationality,refereesId,certType, linkPhone, contmethInfo, addr, custStat,certNum, jobType, industType,recordSession,cusEmail,cusWechatid,areaCode);							
										sunfirescount++;
									}else{
										//导入保存
										saveLatent(conn,aUser,custId, custZhName, custTyp, linkUser, zipcode, sourceChannel,mktActivitie,cusNationality,refereesId,certType, linkPhone, contmethInfo, addr, custStat,certNum, jobType, industType,recordSession,cusEmail,cusWechatid,areaCode);							
										 sumSuccesscount++;
								     }
								}
					}else{
						    if ("1".equals(nametype)) {
						    	failurecount++;
							}else if("4".equals(phtype)){
									//保存到待处理清单
									saveAsLatent(conn,aUser,custId, custZhName, custTyp, linkUser, zipcode, sourceChannel,mktActivitie,cusNationality,refereesId,certType, linkPhone, contmethInfo, addr, custStat,certNum, jobType, industType,recordSession,cusEmail,cusWechatid,areaCode);						
									sunfirescount++;
							}else{
								//导入保存
							  saveLatent(conn,aUser,custId, custZhName, custTyp, linkUser, zipcode, sourceChannel,mktActivitie,cusNationality,refereesId,certType, linkPhone, contmethInfo, addr, custStat,certNum, jobType, industType,recordSession,cusEmail,cusWechatid,areaCode);							
							  sumSuccesscount++;
							}
					}
		        }
				StringBuffer sbb=new StringBuffer();
				sbb.append(sumSuccesscount+"-");
				sbb.append(sunfirescount+"-");
				sbb.append(failureBycallno+"-");
				sbb.append(failurecount);
				aUser.setTemp1(sbb.toString());
		}catch (Exception e) {
			e.printStackTrace();
			//aUser.putAttribute(BACK_IMPORT_ERROR, e.getMessage());															
			conn.rollback();
		} finally {
			String deleteSQL = "DELETE FROM ACRM_F_CI_POT_CUS_COM_TEMP t where t.ID like '" + PKhead + "%'";//插入失败删除原先的数据
			stm.execute(deleteSQL);
			JdbcUtil.close(null, stm, conn);
		}
	}
	
	
	/**
   	 * 
   	 * @param cusId
   	 * @return
   	 * 根据创建规则 验证：
   	 * 手动新增时，首先通过客户名称、证件类型、证件号码与CRM中正式客户作精确匹配，如有匹配记录，则提示认为该客户已是正式客户，不允许作为潜在客户新增；
     * 再通过客户名称、证件类型、证件号码与个金潜在客户池中客户作精确匹配，若有匹配记录，则提示该潜在客户已存在，不允许新增；
     * 若客户名称不同，证件类型、证件号码相同，则提示用户是否为同一用户，若用户选择是，则提示用户通过修改的方式维护该客户信息，如果用户选择否，则允许新增；
     * 若客户名称不同，电话号码相同，则提示是否为同一客户，如果用户选择是，则提示用户通过修改的方式维护该客户信息，如果用户选择否，则仍允许新增
   	 */
  public Map<String,Object>  doParameterCheck(Connection conn,String custnamev,String identtypev,String identnov,String callnov){
				String type = "";
				String phtype = "";
				Map<String,Object> map=null;
				try {
					List<Object> list=null,list5=null,list6=null;
					boolean flag1 =false, flag2 =false,flag3 =false,flag4 =false,flag6=false;
					if(custnamev!=null&&!"".equals(custnamev)&&identtypev!=null&&!"".equals(identtypev)&&identnov!=null&&!"".equals(identnov)){
						String sql6 ="select cust_id from acrm_f_ci_customer c where c.cust_name='"+custnamev+"' and c.ident_type='"+identtypev+"' and c.ident_no='"+identnov+"' and c.potential_flag='0' and c.cust_type='2'";
						List<Object> list1=validationNewLatentCustomer(conn,sql6,1);
						if(list1!=null&&list1.size()>0){
							flag6=true;
						}
						String  sql3="select pc.cus_id from acrm_f_ci_pot_cus_com pc where pc.cus_name='"+custnamev+"' and pc.cert_type='"+identtypev+"' and pc.cert_code='"+identnov+"'";
						List<Object> list3=validationNewLatentCustomer(conn,sql3,1);
						if(list3!=null&&list3.size()>0){
							flag1=true;
						}
						String  sql4="select pc.cus_id from acrm_f_ci_pot_cus_com pc where pc.cus_name<>'"+custnamev+"' and pc.cert_type='"+identtypev+"' and pc.cert_code='"+identnov+"'";
						List<Object> list4=validationNewLatentCustomer(conn,sql4,1);
						if(list4!=null&&list4.size()>0){
							flag2=true;
						}
					}else{
						String sql6 =" select c.cust_id  from acrm_f_ci_customer c  left  join ACRM_F_CI_CONTMETH t on c.cust_id=t.cust_id   where  replace(t.contmeth_info,substr(t.contmeth_info,0,instr(t.contmeth_info,'-',1,LENGTH(REGEXP_REPLACE(REPLACE(t.contmeth_info, '-', '@'),  '[^@]+',  '')))))=replace('"+callnov+"',substr('"+callnov+"',0,instr('"+callnov+"','-',1,2))) and c.cust_name='"+custnamev+"' ";
						List<Object> list2=validationNewLatentCustomer(conn,sql6,1);
						if(list2!=null&&list2.size()>0){
							flag6=true;
					    }
					}
					if(callnov!=null&&!"".equals(callnov)){
						String  sql5="select pc.cus_id cust_id,pc.source_channel from acrm_f_ci_pot_cus_com pc where pc.call_no='"+callnov+"'";
						list5=validationNewLatentCustomer(conn,sql5,2);
						if(list5!=null&&list5.size()>0){
							flag3=true;
					  }
					}
					/*if(custnamev!=null&&!"".equals(custnamev)){
						String  sql7="select pc.cus_id cust_id,pc.source_channel from acrm_f_ci_pot_cus_com pc where pc.cus_name='"+custnamev+"' and pc.call_no='"+callnov+"'";
						list6=validationNewLatentCustomer(conn,sql7,2);
						if(list6!=null&&list6.size()>0){
							flag4=true;
					}}
					*/
					if(flag6==true){
						type="1";
					}else if(flag1==true){
						type="2";
					}else if(flag2==true){
						type="3";
					} 
					
					if(flag3){
						phtype="4";
						list=list5;
					}
					/*if(flag4==true){
						phtype="5";
						list=list6;
					}*/
					map = new HashMap<String, Object>();
					map.put("type", type);
					map.put("phtype", phtype);
					if(list!=null&&list.size()>0){
					map.put("custid", list.get(0));
					map.put("source", list.get(1));
					}
					log.info("doParameterCheck  type: "+type+" phtype :"+phtype);
				} catch (Exception e) {
					throw new BizException(1,0,"10001",":"+e.getMessage());
				}
				return map;
				
		   	}
		   public List<Object> validationNewLatentCustomer(Connection conn,String sql,int clom){
		    	log.info(""+sql);
		    	List<Object> List = new ArrayList<Object>();
		    	Statement stmt=null;
		    	ResultSet result=null;
		    	try{
		    		stmt = conn.createStatement();
		    		result = stmt.executeQuery(sql);
		   				String custId="";
		   				String sourceStr="";
		   			    while (result.next()){
		   			    	custId = result.getString(1);
		   			    	if(clom==2){
		   			    	sourceStr=result.getString(2);
		   			    	}
		   			    	List.add(custId);
		   			    	List.add(sourceStr);
		   			    }
		   			log.info("validationNewLatentCustomer: "+List.toString());
		   			 return List;
		   		}catch(Exception e){
		   			e.printStackTrace();
		   		} finally {
					free(result, stmt);
				}
				return null;
		       }
		   
		   public void saveLatent(Connection conn,AuthUser auth,String tempCustId,String custZhName,String custTyp,String linkUser,String zipcode,String sourceChannel,String mktActivitie,String cusNationality,String refereesId,String certType,
					String linkPhone,String contmethInfo,String addr,String custStat,String certNum,String jobType,String industType,String recordSession,String cusEmail,String cusWechatid,String areaCode){	
					  System.out.println("data>"+tempCustId+ custZhName+ custTyp+ linkUser+ zipcode+ sourceChannel+mktActivitie+cusNationality+refereesId+certType+ linkPhone+ contmethInfo+ addr+ custStat+ certNum+ jobType+ industType);		
					  	Date date1 = new Date();
						DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String date = format1.format(date1);
						date = "to_date('" + date + "','yyyy-mm-dd hh24:mi:ss')";
						String callNo = linkPhone;				
						if(industType==null){
							industType="";
						}
						String sql="";
						Statement stmt=null;
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
										   "   DELETE_CUST_STATE," +
										   "   BACK_STATE," +
										   "   CUST_MGR," +
										   "   MAIN_BR_ID," +
										   "   INPUT_ID,"+
										   "   INPUT_BR_ID,"+
										   "   INPUT_DATE,"+
										   " FORMAL_CUST_FLAG,"+
										   " CUS_EMAIL, "+
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
								     //System.out.println("转入临时客户:"+sql);                   
					                 try {
					                	 stmt=conn.createStatement();
					                	 stmt.execute(sql);
					                	 conn.commit();
									} catch (Exception e) {
										e.printStackTrace();
									}finally {
										try {
											stmt.close();
										} catch (SQLException e) {
											e.printStackTrace();
										}
									}
					
					}
		   public void saveAsLatent(Connection conn,AuthUser auth,String tempCustId,String custZhName,String custTyp,String linkUser,String zipcode,String sourceChannel,String mktActivitie,String cusNationality,String refereesId,String certType,
					String linkPhone,String contmethInfo,String addr,String custStat,String certNum,String jobType,String industType,String recordSession,String cusEmail,String cusWechatid,String areaCode){	
					  System.out.println("data>"+tempCustId+ custZhName+ custTyp+ linkUser+ zipcode+ sourceChannel+mktActivitie+cusNationality+refereesId+certType+ linkPhone+ contmethInfo+ addr+ custStat+ certNum+ jobType+ industType);		
					  	Date date1 = new Date();
						DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String date = format1.format(date1);
						date = "to_date('" + date + "','yyyy-mm-dd hh24:mi:ss')";
						String callNo = linkPhone;				
						if(industType==null){
							industType="";
						}
						String sql="";
						Statement stmt=null;
						Random random1 = new Random();
						int sp=Math.abs(random1.nextInt());
						String result="temp"+sp;
					     sql = 
										  "insert into acrm_f_ci_pot_cus_createtemp" +
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
										   "   JOB_TYPE," + 
										   "   CUS_ADDR," + 
										   "   RECORD_SESSION,"+
										   "   INDUST_TYPE,"+
										   "   DELETE_CUST_STATE," +
										   "   INPUT_ID,"+
										   "   INPUT_BR_ID,"+
										   "   INPUT_DATE,"
										   + " CUS_EMAIL, "
										   + " CUS_WECHATID,"
										   + "  AREA_CODE"+
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
										   "  '"+jobType+"'," + 
										   "  '"+addr+"'," + 
										   "  '"+recordSession+"'," + 
										   "  '"+industType+"',"+
										   " '2'," + 
										   "  '"+auth.getUserId()+"'," + 
										   "  '"+auth.getUnitId()+"'," + 
										   "  '" + format1.format(new Date()) + "'," + 
										   "  '"+cusEmail+"'," + 
										   "  '"+cusWechatid+"'," + 
										   "  '"+areaCode+"'" + 
										   "   )";
								    // System.out.println("转入临时客户:"+sql);                   
					                 try {
					                	 stmt=conn.createStatement();
					                	 stmt.execute(sql);
					                	 conn.commit();
									} catch (Exception e) {
										e.printStackTrace();
									}finally {
										try {
											stmt.close();
										} catch (SQLException e) {
											e.printStackTrace();
										}
									}
					
					}

}
