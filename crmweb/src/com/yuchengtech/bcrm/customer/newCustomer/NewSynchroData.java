package com.yuchengtech.bcrm.customer.newCustomer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.yuchengtech.bcrm.model.OcrmFCiCustinfoUphi;
import com.yuchengtech.crm.constance.JdbcUtil;

/**
 * @describtion: CRM同步数据一致性
 * 注：Connection conn是由工作流提供过来的，不负责关闭连接，工作流自身去关闭。
 *
 * @author : lhqheli
 * @date : 2014年11月11日 上午11:11:11
 */
public class NewSynchroData {
	
	private static Logger log = Logger.getLogger(NewSynchroData.class);

	public final static String SYNC_TABLE_CUSTOMER = "ACRM_F_CI_CUSTOMER";
	public final static String SYNC_TABLE_ORG = "ACRM_F_CI_ORG";
	public final static String SYNC_TABLE_PERSON = "ACRM_F_CI_PERSON";
	public final static String SYNC_TABLE_IDENTIFIER = "ACRM_F_CI_CUST_IDENTIFIER";
	public final static String SYNC_TABLE_ADDRESS = "ACRM_F_CI_ADDRESS";
	public final static String SYNC_TABLE_CONTMETH = "ACRM_F_CI_CONTMETH";
	
	public final static String[] SYNC_FIELD_CUSTOMER = {
		"CUST_NAME","JOB_TYPE","INDUST_TYPE","VIP_FLAG"
		};
	public final static String[] SYNC_FIELD_ORG = {
		"CUST_NAME","MAIN_INDUSTRY"/*,"ORG_ADDR","ORG_ZIPCODE","ORG_FEX",
		"ORG_EMAIL","ORG_HOMEPAGE","ORG_WEIBO","ORG_WEIXIN",*/
		};
	public final static String[] SYNC_FIELD_PERSON = {
		"PERSONAL_NAME","CAREER_TYPE","PROFESSION"/*,"HUKOU_PLACE","MOBILE_PHONE","EMAIL","HOMEPAGE"
		,"WEIBO","WEIXIN","QQ","HOME_ADDR","HOME_ZIPCODE"
		,"HOME_TEL","UNIT_ADDR","UNIT_ZIPCODE"
		,"UNIT_TEL","UNIT_FEX","POST_ADDR","POST_ZIPCODE","POST_PHONE"*/
		};
	public final static String[] SYNC_FIELD_IDENTIFIER = {
		"IDENT_TYPE","IDENT_NO","IDENT_ORG","COUNTRY_OR_REGION","IDEN_REG_DATE"
		,"IDENT_EXPIRED_DATE"
		};
	public final static String[] SYNC_FIELD_ADDRESS = {
		"ADDR_TYPE","ADDR","ZIPCODE"
		};
	public final static String[] SYNC_FIELD_CONTMETH = {
		"CONTMETH_TYPE","CONTMETH_INFO"
		};
	/**
	 * 判断是客户等级表中VIP等级是否已存在
	 * @throws SQLException 
	 */
	public static String ifExist(Connection conn,String custId) throws SQLException{
		String querySql = "SELECT * FROM ACRM_F_CI_GRADE G WHERE G.CUST_ID = '"+custId+"' AND G.CUST_GRADE_TYPE='08'";
		Result result = querySQL(conn,querySql);
		String flag="";
		if(result.getRowCount()>0){
			flag="update";
		}else{
			flag="insert";
		} 
		return flag;
	}
	
	/**
	 * 统一客户表变更发起同步
	 * ACRM_F_CI_CUSTOMER
	 * @throws Exception 
	 */
	public static void customerSync(Connection conn,String updateFlag,String custId) throws Exception{
		List<String> SQLS = new ArrayList<String>();
		log.info("统一客户表变更发起数据同步开始>>>>>>>>>");
		String querySql = "SELECT C.CUST_TYPE,U.UPDATE_ITEM_EN,U.UPDATE_BE_CONT,U.UPDATE_AF_CONT FROM OCRM_F_CI_CUSTINFO_UPHIS U INNER JOIN ACRM_F_CI_CUSTOMER C ON C.CUST_ID = U.CUST_ID WHERE U.CUST_ID = '"+custId+"' AND U.UPDATE_FLAG = '"+updateFlag+"' AND U.UPDATE_TABLE = '"+SYNC_TABLE_CUSTOMER+"' AND U.UPDATE_ITEM_EN IN ("+arrayToJoin(SYNC_FIELD_CUSTOMER)+")";
		Result result = querySQL(conn,querySql);
		//判断表是否发起变更,若变更则同步其它表
		for (SortedMap<?, ?> row : result.getRows()) {
			String custType = row.get("CUST_TYPE") != null ?(String) row.get("CUST_TYPE"):"";
			String updateItemEn = row.get("UPDATE_ITEM_EN") != null ?(String) row.get("UPDATE_ITEM_EN"):"";
			String updateBeCont = row.get("UPDATE_BE_CONT") != null ?(String) row.get("UPDATE_BE_CONT"):"";
			String updateAfCont = row.get("UPDATE_AF_CONT") != null ?(String) row.get("UPDATE_AF_CONT"):"";
			updateBeCont = StringEscapeUtils.escapeSql(updateBeCont);//sql特殊字符转义
			updateAfCont = StringEscapeUtils.escapeSql(updateAfCont);//sql特殊字符转义

			
			if("1".equals(custType)){
				if("CUST_NAME".equals(updateItemEn)){
					log.info("--同步机构客户ORG表CUST_NAME："+updateBeCont+" ==>> "+updateAfCont);
					SQLS.add("UPDATE ACRM_F_CI_ORG O SET O.CUST_NAME = '"+updateAfCont+"' WHERE O.CUST_ID = '"+custId+"'");
					
					log.info("--同步证件IDENTIFIER表客户名称: "+updateBeCont+" ==>> "+updateAfCont);
					SQLS.add("UPDATE ACRM_F_CI_CUST_IDENTIFIER i SET i.IDENT_CUST_NAME = '"+updateAfCont+"' WHERE i.CUST_ID = '"+custId+"'");
				}
				if("INDUST_TYPE".equals(updateItemEn)){
					log.info("--同步机构客户ORG表MAIN_INDUSTRY："+updateBeCont+" ==>> "+updateAfCont);
					SQLS.add("UPDATE ACRM_F_CI_ORG O SET O.MAIN_INDUSTRY = '"+updateAfCont+"' WHERE O.CUST_ID = '"+custId+"'");
				}
			}
			if("2".equals(custType)){
				if("CUST_NAME".equals(updateItemEn)){
					log.info("--同步个人客户PERSON表PERSONAL_NAME："+updateBeCont+" ==>> "+updateAfCont);
					SQLS.add("UPDATE ACRM_F_CI_PERSON P SET P.PERSONAL_NAME = '"+updateAfCont+"' WHERE P.CUST_ID = '"+custId+"'");
					
					log.info("--同步证件IDENTIFIER表客户名称: "+updateBeCont+" ==>> "+updateAfCont);
					SQLS.add("UPDATE ACRM_F_CI_CUST_IDENTIFIER i SET i.IDENT_CUST_NAME = '"+updateAfCont+"' WHERE i.CUST_ID = '"+custId+"'");
				}
				if("INDUST_TYPE".equals(updateItemEn)){
					log.info("--同步个人客户PERSON表PROFESSION："+updateBeCont+" ==>> "+updateAfCont);
					SQLS.add("UPDATE ACRM_F_CI_PERSON P SET P.PROFESSION = '"+updateAfCont+"' WHERE P.CUST_ID = '"+custId+"'");
				}
				if("JOB_TYPE".equals(updateItemEn)){
					log.info("--同步个人客户PERSON表CAREER_TYPE："+updateBeCont+" ==>> "+updateAfCont);
					SQLS.add("UPDATE ACRM_F_CI_PERSON P SET P.CAREER_TYPE = '"+updateAfCont+"' WHERE P.CUST_ID = '"+custId+"'");
				}
				if("VIP_FLAG".equals(updateItemEn)){
					String flag=ifExist(conn,custId);
					if("update".equals(flag)){
						SQLS.add("UPDATE ACRM_F_CI_GRADE P SET P.CUST_GRADE = '"+updateAfCont+"' WHERE P.CUST_ID = '"+custId+"' AND P.CUST_GRADE_TYPE='08'");
					}else if("insert".equals(flag)){
						SQLS.add("insert into  ACRM_F_CI_GRADE (CUST_GRADE_ID,cust_id,Cust_Grade_Type,cust_grade,Last_Update_Sys,Last_Update_Tm) values (ID_SEQUENCE.NEXTVAL,'"+custId+"','08','"+updateAfCont+"','CRM',SYSDATE)");
					}
					log.info("--同步个人客户PERSON表VIP_FLAG："+updateBeCont+" ==>> "+updateAfCont);
				}
			}
		}
		executeBatch(conn,SQLS);
		log.info("统一客户表变更发起数据同步结束>>>>>>>>>");
	}
	
	
	/**
	 * 
	 * 机构客户表变更发起同步
	 * ACRM_F_CI_ORG
	 * @throws Exception 
	 */
	public static void orgSync(Connection conn,String updateFlag,String custId) throws Exception{
		List<String> SQLS = new ArrayList<String>();
		log.info("机构客户表变更发起数据同步开始>>>>>>>>>");
		@SuppressWarnings("unchecked")
//		List<OcrmFCiCustinfoUphi> list1 = this.em.createQuery(
//				"select  v.updateItemEn,v.updateBeCont,v.updateAfCont from OcrmFCiCustinfoUphi v where v.custId='" + custId
//				+ "' and  v.updateFlag='" + updateFlag + "' and v.updateTable='"+SYNC_TABLE_ORG+"' and v.updateItemEn in ("+arrayToJoin(SYNC_FIELD_ORG)+")")
//				.getResultList();
		String querySql = "SELECT U.UPDATE_ITEM_EN,U.UPDATE_BE_CONT,U.UPDATE_AF_CONT FROM OCRM_F_CI_CUSTINFO_UPHIS U WHERE U.CUST_ID = '"+custId+"' AND U.UPDATE_FLAG = '"+updateFlag+"' AND U.UPDATE_TABLE = '"+SYNC_TABLE_ORG+"' AND U.UPDATE_ITEM_EN IN ("+arrayToJoin(SYNC_FIELD_ORG)+")";
		Result result = querySQL(conn,querySql);
		//判断表是否发起变更,若变更则同步其它表
		for (SortedMap<?, ?> row : result.getRows()) {
			String updateItemEn = row.get("UPDATE_ITEM_EN") != null ?(String) row.get("UPDATE_ITEM_EN"):"";
			String updateBeCont = row.get("UPDATE_BE_CONT") != null ?(String) row.get("UPDATE_BE_CONT"):"";
			String updateAfCont = row.get("UPDATE_AF_CONT") != null ?(String) row.get("UPDATE_AF_CONT"):"";
			updateBeCont = StringEscapeUtils.escapeSql(updateBeCont);//sql特殊字符转义
			updateAfCont = StringEscapeUtils.escapeSql(updateAfCont);//sql特殊字符转义
			
			if("CUST_NAME".equals(updateItemEn)){
				log.info("--同步客户CUSTOMER表CUST_NAME："+updateBeCont+" ==>> "+updateAfCont);
				SQLS.add("UPDATE ACRM_F_CI_CUSTOMER C SET c.CUST_NAME = '"+updateAfCont+"' WHERE c.CUST_ID = '"+custId+"'");
				
				log.info("--同步证件IDENTIFIER表客户名称: "+updateBeCont+" ==>> "+updateAfCont);
				SQLS.add("UPDATE ACRM_F_CI_CUST_IDENTIFIER i SET i.IDENT_CUST_NAME = '"+updateAfCont+"' WHERE i.CUST_ID = '"+custId+"'");
			}
			if("MAIN_INDUSTRY".equals(updateItemEn)){
				log.info("--同步客户CUSTOMER表INDUST_TYPE："+updateBeCont+" ==>> "+updateAfCont);
				SQLS.add("UPDATE ACRM_F_CI_CUSTOMER C SET c.INDUST_TYPE = '"+updateAfCont+"' WHERE c.CUST_ID = '"+custId+"'");
			}
		}
		
		/*,"ORG_ADDR","ORG_ZIPCODE",
		"ORG_FEX","ORG_EMAIL","ORG_HOMEPAGE","ORG_WEIBO","ORG_WEIXIN"*/
		///////////////////////////同步地址、联系信息
		//判断表是否发起变更,若变更则同步其它表,变更则添加变更记录SQL
		querySql = "SELECT A1.ADDR_ID,C.ORG_ADDR,C.ORG_ZIPCODE,M1.CONTMETH_ID AS CONTMETH_ID1,C.ORG_FEX,M2.CONTMETH_ID AS CONTMETH_ID2,C.ORG_EMAIL,M3.CONTMETH_ID AS CONTMETH_ID3,C.ORG_HOMEPAGE,M4.CONTMETH_ID AS CONTMETH_ID4,C.ORG_WEIBO,M5.CONTMETH_ID AS CONTMETH_ID5,C.ORG_WEIXIN "
			  + " FROM ACRM_F_CI_ORG C "
			  + " LEFT JOIN ACRM_F_CI_ADDRESS a1 on a1.cust_id = c.cust_id and a1.ADDR_TYPE = '02' "
			  + " left join ACRM_F_CI_CONTMETH m1 on m1.cust_id = c.cust_id and m1.contmeth_type = '302' "
			  + " left join ACRM_F_CI_CONTMETH m2 on m2.cust_id = c.cust_id and m2.contmeth_type = '503' "
			  + " left join ACRM_F_CI_CONTMETH m3 on m3.cust_id = c.cust_id and m3.contmeth_type = '801' "
			  + " left join ACRM_F_CI_CONTMETH m4 on m4.cust_id = c.cust_id and m4.contmeth_type = '803' "
			  + " left join ACRM_F_CI_CONTMETH m5 on m5.cust_id = c.cust_id and m5.contmeth_type = '804' "
			  + " WHERE C.CUST_ID = '"+custId+"'";
		Result custResult = querySQL(conn,querySql);
		SortedMap<?, ?> custRow = null;
		if(custResult.getRowCount()>0){
			custRow = custResult.getRows()[0];
			updateAddress(SQLS,custRow,"ADDR_ID","ORG_ADDR","ORG_ZIPCODE","通讯地址、通讯编码");
			
			updateContmeth(SQLS,custRow,"CONTMETH_ID1","ORG_FEX","传真号码");
			updateContmeth(SQLS,custRow,"CONTMETH_ID2","ORG_EMAIL","电子邮件");
			updateContmeth(SQLS,custRow,"CONTMETH_ID3","ORG_HOMEPAGE","主页");
			updateContmeth(SQLS,custRow,"CONTMETH_ID4","ORG_WEIBO","微博");
			updateContmeth(SQLS,custRow,"CONTMETH_ID4","ORG_WEIXIN","微信");
		}
		executeBatch(conn,SQLS);
		log.info("机构客户表变更发起数据同步结束>>>>>>>>>");
	}
	
	/**
	 * 个人客户表变更发起同步
	 * ACRM_F_CI_PERSON
	 * @throws Exception 
	 */
	public static void personSync(Connection conn,String updateFlag,String custId) throws Exception{
		List<String> SQLS = new ArrayList<String>();
		log.info("个人客户表变更发起数据同步开始>>>>>>>>>");
		String querySql = "SELECT U.UPDATE_ITEM_EN,U.UPDATE_BE_CONT,U.UPDATE_AF_CONT FROM OCRM_F_CI_CUSTINFO_UPHIS U WHERE U.CUST_ID = '"+custId+"' AND U.UPDATE_FLAG = '"+updateFlag+"' AND U.UPDATE_TABLE = '"+SYNC_TABLE_PERSON+"' AND U.UPDATE_ITEM_EN IN ("+arrayToJoin(SYNC_FIELD_PERSON)+")";
		Result result = querySQL(conn,querySql);
		//判断表是否发起变更,若变更则同步其它表
		for (SortedMap<?, ?> row : result.getRows()) {
			String updateItemEn = row.get("UPDATE_ITEM_EN") != null ?(String) row.get("UPDATE_ITEM_EN"):"";
			String updateBeCont = row.get("UPDATE_BE_CONT") != null ?(String) row.get("UPDATE_BE_CONT"):"";
			String updateAfCont = row.get("UPDATE_AF_CONT") != null ?(String) row.get("UPDATE_AF_CONT"):"";
			
			if("PERSONAL_NAME".equals(updateItemEn)){
				log.info("--同步客户CUSTOMER表CUST_NAME："+updateBeCont+" ==>> "+updateAfCont);
				SQLS.add("UPDATE ACRM_F_CI_CUSTOMER C SET c.CUST_NAME = '"+updateAfCont+"' WHERE c.CUST_ID = '"+custId+"'");
				
				log.info("--同步证件IDENTIFIER表IDENT_CUST_NAME: "+updateBeCont+" ==>> "+updateAfCont);
				SQLS.add("UPDATE ACRM_F_CI_CUST_IDENTIFIER i SET i.IDENT_CUST_NAME = '"+updateAfCont+"' WHERE i.CUST_ID = '"+custId+"'");
			}
			if("CAREER_TYPE".equals(updateItemEn)){
				log.info("--同步客户CUSTOMER表职业："+updateBeCont+" ==>> "+updateAfCont);
				SQLS.add("UPDATE ACRM_F_CI_CUSTOMER C SET c.JOB_TYPE = '"+updateAfCont+"' WHERE c.CUST_ID = '"+custId+"'");
			}
			if("PROFESSION".equals(updateItemEn)){
				log.info("--同步客户CUSTOMER表行业："+updateBeCont+" ==>> "+updateAfCont);
				SQLS.add("UPDATE ACRM_F_CI_CUSTOMER C SET c.INDUST_TYPE = '"+updateAfCont+"' WHERE c.CUST_ID = '"+custId+"'");
			}
		}
		
		/*,HUKOU_PLACE,HOME_ADDR,HOME_ZIPCODE,UNIT_ADDR,UNIT_ZIPCODE,POST_ADDR,POST_ZIPCODE
		 * MOBILE_PHONE,EMAIL,HOMEPAGE,WEIBO,WEIXIN,QQ,HOME_TEL,UNIT_TEL,UNIT_FEX,POST_PHONE*/
		///////////////////////////同步地址、联系信息
		//判断表是否发起变更,若变更则同步其它表,变更则添加变更记录SQL
		querySql = "SELECT A1.ADDR_ID AS ADDR_ID1,P.HUKOU_PLACE,A2.ADDR_ID AS ADDR_ID2,P.HOME_ADDR,P.HOME_ZIPCODE,A3.ADDR_ID AS ADDR_ID3,P.UNIT_ADDR,P.UNIT_ZIPCODE,A4.ADDR_ID AS ADDR_ID4,P.POST_ADDR,P.POST_ZIPCODE "
			+" ,M1.CONTMETH_ID AS CONTMETH_ID1,P.MOBILE_PHONE,M2.CONTMETH_ID AS CONTMETH_ID2,P.EMAIL,M3.CONTMETH_ID AS CONTMETH_ID3,P.HOMEPAGE,M4.CONTMETH_ID AS CONTMETH_ID4,P.WEIBO,M5.CONTMETH_ID AS CONTMETH_ID5,P.WEIXIN,M6.CONTMETH_ID AS CONTMETH_ID6,P.QQ,M7.CONTMETH_ID AS CONTMETH_ID7,P.HOME_TEL,M8.CONTMETH_ID AS CONTMETH_ID8,P.UNIT_TEL,M9.CONTMETH_ID AS CONTMETH_ID9,P.UNIT_FEX,M10.CONTMETH_ID AS CONTMETH_ID10,P.POST_PHONE "
			+" FROM ACRM_F_CI_PERSON P "
			+" LEFT JOIN ACRM_F_CI_ADDRESS A1 ON A1.CUST_ID = P.CUST_ID AND A1.ADDR_TYPE = '10' "
			+" LEFT JOIN ACRM_F_CI_ADDRESS A2 ON A2.CUST_ID = P.CUST_ID AND A2.ADDR_TYPE = '04' "
			+" LEFT JOIN ACRM_F_CI_ADDRESS A3 ON A3.CUST_ID = P.CUST_ID AND A3.ADDR_TYPE = '05' "
			+" LEFT JOIN ACRM_F_CI_ADDRESS A4 ON A4.CUST_ID = P.CUST_ID AND A4.ADDR_TYPE = '02' "
			+" LEFT JOIN ACRM_F_CI_CONTMETH M1 ON M1.CUST_ID = P.CUST_ID AND M1.CONTMETH_TYPE = '100' "
			+" LEFT JOIN ACRM_F_CI_CONTMETH M2 ON M2.CUST_ID = P.CUST_ID AND M2.CONTMETH_TYPE = '501' "
			+" LEFT JOIN ACRM_F_CI_CONTMETH M3 ON M3.CUST_ID = P.CUST_ID AND M3.CONTMETH_TYPE = '801' "
			+" LEFT JOIN ACRM_F_CI_CONTMETH M4 ON M4.CUST_ID = P.CUST_ID AND M4.CONTMETH_TYPE = '803' "
			+" LEFT JOIN ACRM_F_CI_CONTMETH M5 ON M5.CUST_ID = P.CUST_ID AND M5.CONTMETH_TYPE = '804' "
			+" LEFT JOIN ACRM_F_CI_CONTMETH M6 ON M6.CUST_ID = P.CUST_ID AND M6.CONTMETH_TYPE = '600' "
			+" LEFT JOIN ACRM_F_CI_CONTMETH M7 ON M7.CUST_ID = P.CUST_ID AND M7.CONTMETH_TYPE = '204' "
			+" LEFT JOIN ACRM_F_CI_CONTMETH M8 ON M8.CUST_ID = P.CUST_ID AND M8.CONTMETH_TYPE = '203' "
			+" LEFT JOIN ACRM_F_CI_CONTMETH M9 ON M9.CUST_ID = P.CUST_ID AND M9.CONTMETH_TYPE = '302' "
			+" LEFT JOIN ACRM_F_CI_CONTMETH M10 ON M10.CUST_ID = P.CUST_ID AND M10.CONTMETH_TYPE = '201' "
			+ " WHERE P.CUST_ID = '"+custId+"'";
		Result custResult = querySQL(conn,querySql);
		SortedMap<?, ?> custRow = null;
		if(custResult.getRowCount()>0){
			custRow = custResult.getRows()[0];
			updateAddress(SQLS,custRow,"ADDR_ID1","HUKOU_PLACE","户籍地址");
			updateAddress(SQLS,custRow,"ADDR_ID2","HOME_ADDR","HOME_ZIPCODE","家庭地址、家庭邮政编码");
			updateAddress(SQLS,custRow,"ADDR_ID3","UNIT_ADDR","UNIT_ZIPCODE","单位地址、单位邮政编码");
			updateAddress(SQLS,custRow,"ADDR_ID4","POST_ADDR","POST_ZIPCODE","通讯地址、通讯邮政编码");
			
			
			updateContmeth(SQLS,custRow,"CONTMETH_ID1","MOBILE_PHONE","手机号码");
			updateContmeth(SQLS,custRow,"CONTMETH_ID2","EMAIL","邮件地址");
			updateContmeth(SQLS,custRow,"CONTMETH_ID3","HOMEPAGE","主页");
			updateContmeth(SQLS,custRow,"CONTMETH_ID4","WEIBO","微博");
			updateContmeth(SQLS,custRow,"CONTMETH_ID5","WEIXIN","微信");
			updateContmeth(SQLS,custRow,"CONTMETH_ID6","QQ","QQ");
			updateContmeth(SQLS,custRow,"CONTMETH_ID7","HOME_TEL","住宅电话");
			updateContmeth(SQLS,custRow,"CONTMETH_ID8","UNIT_TEL","单位电话");
			updateContmeth(SQLS,custRow,"CONTMETH_ID9","UNIT_FEX","传真号码");
			updateContmeth(SQLS,custRow,"CONTMETH_ID10","POST_PHONE","联系电话");
		}
		executeBatch(conn,SQLS);
		log.info("个人客户表变更发起数据同步结束>>>>>>>>>");
	}
	
	/**
	 * 同步联系信息表
	 * @param SQLS
	 * @param custRow
	 * @param idField
	 * @param infoField
	 * @param remark
	 */
	private static void updateContmeth(List<String> SQLS,SortedMap<?, ?> custRow,String idField,String infoField,String remark){
		String contmethId = custRow.get(idField) != null ?(String) custRow.get(idField):"";
		String contmethInfo = custRow.get(infoField) != null ?(String) custRow.get(infoField):"";
		if(!"".equals(contmethInfo)){
			log.info("--同步联系信息CONTMETH表"+remark+"："+contmethId+", ? ==>> "+contmethInfo);
			if(!"".equals(contmethId)){
				SQLS.add("UPDATE ACRM_F_CI_CONTMETH SET CONTMETH_INFO = '"+contmethInfo+"' WHERE CONTMETH_ID ='"+contmethId+"'");
			}else{
				log.info("---------------------------联系信息表数据不存在,不同步");
			}
		}
	}
	
	/**
	 * 同步地址信息表
	 * @param SQLS
	 * @param custRow
	 * @param idField
	 * @param infoField
	 * @param remark
	 */
	private static void updateAddress(List<String> SQLS,SortedMap<?, ?> custRow,String idField,String addrField,String zipField,String remark){
		String addrId = custRow.get(idField) != null ?(String) custRow.get(idField):"";
		String addr = custRow.get(addrField) != null ?(String) custRow.get(addrField):"";
		String zipcode = custRow.get(zipField) != null ?(String) custRow.get(zipField):"";
		addrId = StringEscapeUtils.escapeSql(addrId);//sql特殊字符转义
		addr = StringEscapeUtils.escapeSql(addr);//sql特殊字符转义
		zipcode = StringEscapeUtils.escapeSql(zipcode);//sql特殊字符转义
		if(!"".equals(addr)){
			log.info("--同步地址ADDRESS表"+remark+"："+addrId+", ? ==>> "+addr+"|"+zipcode);
			if(!"".equals(addrId)){
				SQLS.add("UPDATE ACRM_F_CI_ADDRESS SET ADDR = '"+addr+"',ZIPCODE='"+zipcode+"' WHERE ADDR_ID ='"+addrId+"'");
			}else{
				log.info("---------------------------地址表数据不存在,不同步");
			}
		}
	}
	
	/**
	 * 
	 * @param SQLS
	 * @param custRow
	 * @param idField
	 * @param addrField
	 * @param remark
	 */
	private static void updateAddress(List<String> SQLS,SortedMap<?, ?> custRow,String idField,String addrField,String remark){
		String addrId = custRow.get(idField) != null ?(String) custRow.get(idField):"";
		String addr = custRow.get(addrField) != null ?(String) custRow.get(addrField):"";
		addrId = StringEscapeUtils.escapeSql(addrId);//sql特殊字符转义
		addr = StringEscapeUtils.escapeSql(addr);//sql特殊字符转义
		if(!"".equals(addr)){
			log.info("--同步地址ADDRESS表"+remark+"："+addrId+", ? ==>> "+addr);
			if(!"".equals(addrId)){
				SQLS.add("UPDATE ACRM_F_CI_ADDRESS SET ADDR = '"+addr+"' WHERE ADDR_ID ='"+addrId+"'");
			}else{
				log.info("---------------------------地址表数据不存在,不同步");
			}
		}
	}
	
	/**
	 * 证件表变更发起同步
	 * ACRM_F_CI_CUST_IDENTIFIER
	 * @throws Exception 
	 */
	public static void identifierSync(Connection conn,String updateFlag,String custId) throws Exception{
		List<String> SQLS = new ArrayList<String>();
	}
	
	/**
	 * 地址表变更发起同步
	 * ACRM_F_CI_ADDRESS
	 * @throws Exception 
	 */
	public static void addressSync(Connection conn,String updateFlag,String custId) throws Exception{
		List<String> SQLS = new ArrayList<String>();
		String custType = "";
		log.info("地址表变更发起数据同步开始>>>>>>>>>");
		String querySql = "SELECT C.CUST_TYPE,A.ADDR_TYPE,A.ADDR,A.ZIPCODE,A.ADDR_ID FROM ACRM_F_CI_ADDRESS A INNER JOIN ACRM_F_CI_CUSTOMER C ON C.CUST_ID = A.CUST_ID WHERE A.CUST_ID = '"+custId+"'";
		Result result = querySQL(conn,querySql);
		if(result != null && result.getRowCount() > 0){
			custType = result.getRows()[0].get("CUST_TYPE") != null ?(String) result.getRows()[0].get("CUST_TYPE"):"";
		}
		if("1".equals(custType)){
			//判断表是否发起变更,若变更则同步其它表,变更则添加变更记录SQL
			querySql = "SELECT C.ORG_ADDR,c.ORG_ZIPCODE FROM ACRM_F_CI_ORG C WHERE C.CUST_ID = '"+custId+"'";
			Result custResult = querySQL(conn,querySql);
			SortedMap<?, ?> custRow = null;
			if(custResult.getRowCount()>0){
				custRow = custResult.getRows()[0];
				for (SortedMap<?, ?> row : result.getRows()) {
					String addrType = row.get("ADDR_TYPE") != null ?(String) row.get("ADDR_TYPE"):"";
					String addr = row.get("ADDR") != null ?(String) row.get("ADDR"):"";
					String zipcode = row.get("ZIPCODE") != null ?(String) row.get("ZIPCODE"):"";
					addrType = StringEscapeUtils.escapeSql(addrType);//sql特殊字符转义
					addr = StringEscapeUtils.escapeSql(addr);//sql特殊字符转义
					zipcode = StringEscapeUtils.escapeSql(zipcode);//sql特殊字符转义
					if("02".equals(addrType) && (!addr.equals(custRow.get("ORG_ADDR")) || !zipcode.equals(custRow.get("ORG_ZIPCODE")))){
						log.info("--同步机构客户ORG表通讯地址、通讯编码："+custRow.get("ORG_ADDR")+"|"+custRow.get("ORG_ZIPCODE")+" ==>> "+addr+"|"+zipcode);
						SQLS.add("UPDATE ACRM_F_CI_ORG P SET P.ORG_ADDR = '"+addr+"',p.ORG_ZIPCODE = '"+zipcode+"' WHERE P.CUST_ID ='"+custId+"'");
					}
				}
			}
		}
		if("2".equals(custType)){
			querySql = "SELECT C.HUKOU_PLACE,c.HOME_ADDR,c.HOME_ZIPCODE,c.UNIT_ADDR,c.UNIT_ZIPCODE,c.POST_ADDR,c.POST_ZIPCODE FROM ACRM_F_CI_PERSON C WHERE C.CUST_ID = '"+custId+"'";
			Result custResult = querySQL(conn,querySql);
			SortedMap<?, ?> custRow = null;
			if(custResult.getRowCount()>0){
				custRow = custResult.getRows()[0];
				//判断表是否发起变更,若变更则同步其它表,变更则添加变更记录SQL
				for (SortedMap<?, ?> row : result.getRows()) {
					String addrType = row.get("ADDR_TYPE") != null ?(String) row.get("ADDR_TYPE"):"";
					String addr = row.get("ADDR") != null ?(String) row.get("ADDR"):"";
					String zipcode = row.get("ZIPCODE") != null ?(String) row.get("ZIPCODE"):"";
					addrType = StringEscapeUtils.escapeSql(addrType);//sql特殊字符转义
					addr = StringEscapeUtils.escapeSql(addr);//sql特殊字符转义
					zipcode = StringEscapeUtils.escapeSql(zipcode);//sql特殊字符转义
					if("10".equals(addrType) && !addr.equals(custRow.get("HUKOU_PLACE"))){
						log.info("--同步个人客户PERSON表户口地址："+custRow.get("HUKOU_PLACE")+" ==>> "+addr);
						SQLS.add("UPDATE ACRM_F_CI_PERSON P SET P.HUKOU_PLACE = '"+addr+"' WHERE P.CUST_ID ='"+custId+"'");
					}
					if("04".equals(addrType) && (!addr.equals(custRow.get("HOME_ADDR")) || !zipcode.equals(custRow.get("HOME_ZIPCODE")))){
						log.info("--同步个人客户PERSON表住宅地址、住宅邮编："+custRow.get("HOME_ADDR")+"|"+custRow.get("HOME_ZIPCODE")+" ==>> "+addr+"|"+zipcode);
						SQLS.add("UPDATE ACRM_F_CI_PERSON P SET P.HOME_ADDR = '"+addr+"',p.HOME_ZIPCODE = '"+zipcode+"' WHERE P.CUST_ID ='"+custId+"'");
					}
					if("05".equals(addrType) && (!addr.equals(custRow.get("UNIT_ADDR")) || !zipcode.equals(custRow.get("UNIT_ZIPCODE")))){
						log.info("--同步个人客户PERSON表单位地址、单位邮编："+custRow.get("UNIT_ADDR")+"|"+custRow.get("UNIT_ZIPCODE")+" ==>> "+addr+"|"+zipcode);
						SQLS.add("UPDATE ACRM_F_CI_PERSON P SET P.UNIT_ADDR = '"+addr+"',p.UNIT_ZIPCODE = '"+zipcode+"' WHERE P.CUST_ID ='"+custId+"'");
					}
					if("02".equals(addrType) && (!addr.equals(custRow.get("POST_ADDR")) || !zipcode.equals(custRow.get("POST_ZIPCODE")))){
						log.info("--同步个人客户PERSON表通讯地址、通讯编码："+custRow.get("POST_ADDR")+"|"+custRow.get("POST_ZIPCODE")+" ==>> "+addr+"|"+zipcode);
						SQLS.add("UPDATE ACRM_F_CI_PERSON P SET P.POST_ADDR = '"+addr+"',p.POST_ZIPCODE = '"+zipcode+"' WHERE P.CUST_ID ='"+custId+"'");
					}
				}
			}
		}
		executeBatch(conn,SQLS);
		log.info("地址表变更发起数据同步结束>>>>>>>>>");
	}
	
	/**
	 * 联系信息表变更发起同步
	 * ACRM_F_CI_CONTMETH
	 * @throws Exception 
	 */
	public static void contmethSync(Connection conn,String updateFlag,String custId) throws Exception{
		List<String> SQLS = new ArrayList<String>();
		String custType = "";
		log.info("联系信息表变更发起数据同步开始>>>>>>>>>");
		String querySql = "SELECT C.CUST_TYPE,A.CONTMETH_TYPE,A.CONTMETH_INFO,A.REMARK,A.CONTMETH_ID FROM ACRM_F_CI_CONTMETH A INNER JOIN ACRM_F_CI_CUSTOMER C ON C.CUST_ID = A.CUST_ID WHERE A.CUST_ID = '"+custId+"'";
		Result result = querySQL(conn,querySql);
		if(result != null && result.getRowCount() > 0){
			custType = result.getRows()[0].get("CUST_TYPE") != null ?(String) result.getRows()[0].get("CUST_TYPE"):"";
		}
		if("1".equals(custType)){
			//"ORG_FEX","ORG_EMAIL","ORG_HOMEPAGE","ORG_WEIBO","ORG_WEIXIN"
			//判断表是否发起变更,若变更则同步其它表,变更则添加变更记录SQL
			querySql = "SELECT C.ORG_FEX,c.ORG_EMAIL,c.ORG_HOMEPAGE,c.ORG_WEIBO,c.ORG_WEIXIN FROM ACRM_F_CI_ORG C WHERE C.CUST_ID = '"+custId+"'";
			Result custResult = querySQL(conn,querySql);
			SortedMap<?, ?> custRow = null;
			if(custResult.getRowCount()>0){
				custRow = custResult.getRows()[0];
				//判断表是否发起变更,若变更则同步其它表,变更则添加变更记录SQL
				for (SortedMap<?, ?> row : result.getRows()) {
					String contmethType = row.get("CONTMETH_TYPE") != null ?(String) row.get("CONTMETH_TYPE"):"";
					String contmethInfo = row.get("CONTMETH_INFO") != null ?(String) row.get("CONTMETH_INFO"):"";
					contmethType = StringEscapeUtils.escapeSql(contmethType);//sql特殊字符转义
					contmethInfo = StringEscapeUtils.escapeSql(contmethInfo);//sql特殊字符转义
					if("302".equals(contmethType) && !contmethInfo.equals(custRow.get("ORG_FEX"))){
						log.info("--同步机构客户ORG表传真号码："+custRow.get("ORG_FEX")+" ==>> "+contmethInfo);
						SQLS.add("UPDATE ACRM_F_CI_ORG P SET P.ORG_FEX = '"+contmethInfo+"' WHERE P.CUST_ID ='"+custId+"'");
					}
					if("503".equals(contmethType) && !contmethInfo.equals(custRow.get("ORG_EMAIL"))){
						log.info("--同步机构客户ORG表邮件地址："+custRow.get("ORG_EMAIL")+" ==>> "+contmethInfo);
						SQLS.add("UPDATE ACRM_F_CI_ORG P SET P.ORG_EMAIL = '"+contmethInfo+"' WHERE P.CUST_ID ='"+custId+"'");
					}
					if("801".equals(contmethType) && !contmethInfo.equals(custRow.get("ORG_HOMEPAGE"))){
						log.info("--同步机构客户ORG表主页："+custRow.get("ORG_HOMEPAGE")+" ==>> "+contmethInfo);
						SQLS.add("UPDATE ACRM_F_CI_ORG P SET P.ORG_HOMEPAGE = '"+contmethInfo+"' WHERE P.CUST_ID ='"+custId+"'");
					}
					if("803".equals(contmethType) && !contmethInfo.equals(custRow.get("ORG_WEIBO"))){
						log.info("--同步机构客户ORG表微博："+custRow.get("ORG_WEIBO")+" ==>> "+contmethInfo);
						SQLS.add("UPDATE ACRM_F_CI_ORG P SET P.ORG_WEIBO = '"+contmethInfo+"' WHERE P.CUST_ID ='"+custId+"'");
					}
					if("804".equals(contmethType) && !contmethInfo.equals(custRow.get("ORG_WEIXIN"))){
						log.info("--同步机构客户ORG表微信："+custRow.get("ORG_WEIXIN")+" ==>> "+contmethInfo);
						SQLS.add("UPDATE ACRM_F_CI_ORG P SET P.ORG_WEIXIN = '"+contmethInfo+"' WHERE P.CUST_ID ='"+custId+"'");
					}
					if("201".equals(contmethType) && !contmethInfo.equals(custRow.get("ORG_TEL"))){
						log.info("--同步机构客户ORG表联系电话："+custRow.get("ORG_TEL")+" ==>> "+contmethInfo);
						SQLS.add("UPDATE ACRM_F_CI_ORG P SET P.ORG_TEL = '"+contmethInfo+"' WHERE P.CUST_ID ='"+custId+"'");
					}
				}
			}
		}
		//"MOBILE_PHONE","EMAIL","HOMEPAGE","WEIBO","WEIXIN","QQ","HOME_TEL","UNIT_TEL","UNIT_FEX","POST_PHONE"
		if("2".equals(custType)){
			//判断表是否发起变更,若变更则同步其它表,变更则添加变更记录SQL
			querySql = "SELECT C.MOBILE_PHONE,c.EMAIL,c.HOMEPAGE,c.WEIBO,c.WEIXIN,c.QQ,c.HOME_TEL,c.UNIT_TEL,c.UNIT_FEX,c.POST_PHONE FROM ACRM_F_CI_PERSON C WHERE C.CUST_ID = '"+custId+"'";
			Result custResult = querySQL(conn,querySql);
			SortedMap<?, ?> custRow = null;
			if(custResult.getRowCount()>0){
				custRow = custResult.getRows()[0];
				for (SortedMap<?, ?> row : result.getRows()) {
					String contmethType = row.get("CONTMETH_TYPE") != null ?(String) row.get("CONTMETH_TYPE"):"";
					String contmethInfo = row.get("CONTMETH_INFO") != null ?(String) row.get("CONTMETH_INFO"):"";
					contmethType = StringEscapeUtils.escapeSql(contmethType);//sql特殊字符转义
					contmethInfo = StringEscapeUtils.escapeSql(contmethInfo);//sql特殊字符转义
					if("100".equals(contmethType) && !contmethInfo.equals(custRow.get("MOBILE_PHONE"))){
						log.info("--同步个人客户PERSON表手机号码："+custRow.get("MOBILE_PHONE")+" ==>> "+contmethInfo);
						SQLS.add("UPDATE ACRM_F_CI_PERSON P SET P.MOBILE_PHONE = '"+contmethInfo+"' WHERE P.CUST_ID ='"+custId+"'");
					}
					if("501".equals(contmethType) && !contmethInfo.equals(custRow.get("EMAIL"))){
						log.info("--同步个人客户PERSON表邮件地址："+custRow.get("EMAIL")+" ==>> "+contmethInfo);
						SQLS.add("UPDATE ACRM_F_CI_PERSON P SET P.EMAIL = '"+contmethInfo+"' WHERE P.CUST_ID ='"+custId+"'");
					}
					if("801".equals(contmethType) && !contmethInfo.equals(custRow.get("HOMEPAGE"))){
						log.info("--同步个人客户PERSON表主页："+custRow.get("HOMEPAGE")+" ==>> "+contmethInfo);
						SQLS.add("UPDATE ACRM_F_CI_PERSON P SET P.HOMEPAGE = '"+contmethInfo+"' WHERE P.CUST_ID ='"+custId+"'");
					}
					if("803".equals(contmethType) && !contmethInfo.equals(custRow.get("WEIBO"))){
						log.info("--同步个人客户PERSON表微博："+custRow.get("WEIBO")+" ==>> "+contmethInfo);
						SQLS.add("UPDATE ACRM_F_CI_PERSON P SET P.WEIBO = '"+contmethInfo+"' WHERE P.CUST_ID ='"+custId+"'");
					}
					if("804".equals(contmethType) && !contmethInfo.equals(custRow.get("WEIXIN"))){
						log.info("--同步个人客户PERSON表微信："+custRow.get("WEIXIN")+" ==>> "+contmethInfo);
						SQLS.add("UPDATE ACRM_F_CI_PERSON P SET P.WEIXIN = '"+contmethInfo+"' WHERE P.CUST_ID ='"+custId+"'");
					}
					if("600".equals(contmethType) && !contmethInfo.equals(custRow.get("QQ"))){
						log.info("--同步个人客户PERSON表QQ："+custRow.get("QQ")+" ==>> "+contmethInfo);
						SQLS.add("UPDATE ACRM_F_CI_PERSON P SET P.QQ = '"+contmethInfo+"' WHERE P.CUST_ID ='"+custId+"'");
					}
					if("204".equals(contmethType) && !contmethInfo.equals(custRow.get("HOME_TEL"))){
						log.info("--同步个人客户PERSON表住宅电话："+custRow.get("HOME_TEL")+" ==>> "+contmethInfo);
						SQLS.add("UPDATE ACRM_F_CI_PERSON P SET P.HOME_TEL = '"+contmethInfo+"' WHERE P.CUST_ID ='"+custId+"'");
					}
					if("203".equals(contmethType) && !contmethInfo.equals(custRow.get("UNIT_TEL"))){
						log.info("--同步个人客户PERSON表单位电话："+custRow.get("UNIT_TEL")+" ==>> "+contmethInfo);
						SQLS.add("UPDATE ACRM_F_CI_PERSON P SET P.UNIT_TEL = '"+contmethInfo+"' WHERE P.CUST_ID ='"+custId+"'");
					}
					if("302".equals(contmethType) && !contmethInfo.equals(custRow.get("UNIT_FEX"))){
						log.info("--同步个人客户PERSON表传真号码："+custRow.get("UNIT_FEX")+" ==>> "+contmethInfo);
						SQLS.add("UPDATE ACRM_F_CI_PERSON P SET P.UNIT_FEX = '"+contmethInfo+"' WHERE P.CUST_ID ='"+custId+"'");
					}
					if("201".equals(contmethType) && !contmethInfo.equals(custRow.get("POST_PHONE"))){
						log.info("--同步个人客户PERSON表联系电话："+custRow.get("POST_PHONE")+" ==>> "+contmethInfo);
						SQLS.add("UPDATE ACRM_F_CI_PERSON P SET P.POST_PHONE = '"+contmethInfo+"' WHERE P.CUST_ID ='"+custId+"'");
					}
				}
			}
		}
		executeBatch(conn,SQLS);
		log.info("联系信息表变更发起数据同步结束>>>>>>>>>");
	}
	
	/**
	 * 控股类型、行业类别、行业变更发起提醒
	 * OCRM_F_WP_REMIND
	 * @throws Exception 
	 */
	public static void sendRemind(Connection conn,String updateFlag,String custId) throws Exception{
		List<String> SQLS = new ArrayList<String>();
		String querySql = "SELECT U.UPDATE_ITEM_EN,U.UPDATE_BE_CONT,U.UPDATE_AF_CONT FROM OCRM_F_CI_CUSTINFO_UPHIS U WHERE U.CUST_ID = '"+custId+"' AND U.UPDATE_FLAG = '"+updateFlag+"' AND U.UPDATE_ITEM_EN IN ('COM_HOLD_TYPE','MAIN_INDUSTRY','INDUSTRY_CATEGORY','IN_CLL_TYPE','PROFESSION')";
		log.info("控股类型/行业类别/行业名称变更生成提醒开始>>>>>>>>>");
		Result result = querySQL(conn,querySql);
		if(result.getRowCount()>0){
			String sql =" INSERT INTO OCRM_F_WP_REMIND(INFO_ID,RULE_ID,RULE_CODE,CUST_ID,CUST_NAME,HAPPENED_DATE,MSG_CRT_DATE,MSG_END_DATE,REMIND_REMARK,IF_MESSAGE,IF_CALL,IF_MAIL,USER_ID) "
				+" SELECT SEQ_ID.NEXTVAL,'267608','506',C.CUST_ID,C.CUST_NAME,to_char(SYSDATE,'YYYY-MM-DD'),SYSDATE,SYSDATE,CUST_NAME || '的控股类型或行业类别或行业发生变更，请悉知！','0','0','0',T.ACCOUNT_NAME "
				+" FROM ACRM_F_CI_CUSTOMER C "
				+" LEFT JOIN (	SELECT DISTINCT A.ACCOUNT_NAME,'"+custId+"' AS CUST_ID FROM ADMIN_AUTH_ACCOUNT A  "
				+" LEFT JOIN ADMIN_AUTH_ACCOUNT_ROLE R1 ON R1.ACCOUNT_ID = A.ID "
				+" LEFT JOIN ADMIN_AUTH_ROLE R ON R.ID = R1.ROLE_ID "
				+" WHERE R.ROLE_CODE IN (SELECT R1.RULE_ROLE FROM OCRM_F_WP_REMIND_RULE R1 WHERE R1.RULE_CODE = '506') ) T ON T.CUST_ID = C.CUST_ID "
				+" WHERE C.CUST_ID = '"+custId+"' ";
			SQLS.add(sql);
			executeBatch(conn,SQLS);
		}
		log.info("控股类型/行业类别/行业名称变更生成提醒结束>>>>>>>>>");
	}
	
	/**
	 * 处理冗余数据问题
	 * @throws Exception 
	 */
	public static void deleteRedundancy(Connection conn,String updateFlag,String custId) throws Exception{
		List<String> SQLS = new ArrayList<String>();
		log.info("处理反洗钱冗余数据开始>>>>>>>>>");
		String querySql = "SELECT g.CUST_GRADE_ID FROM ACRM_F_CI_GRADE g where G.CUST_GRADE_TYPE = '01' and G.CUST_ID = '"+custId+"'";
		Result result = querySQL(conn,querySql);
		if(result.getRowCount()>1){
			//等级表产生的反洗钱冗余记录,由于当天开户，修改未审批，后台跑批会新增一条，第二天审批也会新增一条，以前台新增为准
			String sql ="delete from ACRM_F_CI_GRADE g where G.CUST_GRADE_TYPE = '01' and G.CUST_ID = '"+custId+"'"
					+ " and G.CUST_GRADE_ID not in (SELECT min(to_number(CUST_GRADE_ID)) FROM ACRM_F_CI_GRADE G WHERE G.CUST_GRADE_TYPE = '01' AND G.CUST_ID = '"+custId+"' GROUP BY G.CUST_ID,G.CUST_GRADE_TYPE)";
			SQLS.add(sql);
			executeBatch(conn,SQLS);
		}
		log.info("处理反洗钱冗余数据结束>>>>>>>>>");
	}
	
	/**
	 * 
	 * @param conn 数据源连接
	 * @param querySql 查询SQL
	 * @return
	 * @throws SQLException
	 */
	private static Result querySQL(Connection conn,String querySql) throws SQLException{
		Statement stmt = null;
		ResultSet rs = null;
		Result result = null;
		try {
			stmt = conn.createStatement();
			log.info("--执行查询SQL:"+querySql);
			rs = stmt.executeQuery(querySql);
		    result = ResultSupport.toResult(rs); 
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			JdbcUtil.close(rs, stmt, null);//conn在工作流中调用并关闭,这里不做关闭处理
		}
		return result;
	}
	
	/**
	 * 
	 * @param conn
	 * @param SQLS
	 * @return
	 * @throws SQLException
	 */
	private static String executeBatch(Connection conn,List<String> SQLS) throws SQLException {
		Statement stmt = null;
		try {
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			for (String sql : SQLS) {
				log.info("--执行SQL:"+sql);
				stmt.addBatch(sql);
			}
			stmt.executeBatch();
			conn.commit();
		} catch (SQLException e) {
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			JdbcUtil.closeStatement(stmt);//conn在工作流中调用并关闭,这里不做关闭处理
		}
		return "success";
	}
	
	/**
	 * java数据转换为 'A','B','C','D'....
	 * @param array
	 * @return
	 */
	private static String arrayToJoin(String[] array){
		StringBuffer sb = new StringBuffer();
		if(array != null && array.length > 0){
			for(int i=0,len=array.length;i<len;i++){
				sb.append(",'");
				sb.append(array[i]);
				sb.append("'");
			}
		}else{
			sb.append(",''");
		}
		return sb.toString().substring(1);
	}
	
}
