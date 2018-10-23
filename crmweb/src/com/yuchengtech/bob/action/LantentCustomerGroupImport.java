package com.yuchengtech.bob.action;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Random;

import org.apache.log4j.Logger;

import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.bob.importimpl.ImportInterface;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.JdbcUtil;

/**
 * 法金潜在客户批量导入 类：LantentCustomerGroupImport.java
 * 
 * @author Administrator time:2015-2-4
 */

public class LantentCustomerGroupImport implements ImportInterface{

	private static Logger log = Logger.getLogger(QueryHelper.class);

	public void excute(Connection conn, String PKhead, AuthUser aUser) throws Exception {
		String userId = aUser.getUserId();
		log.info("updateSQL: 【LantentCustomerGroupImport has been evoke!】");
		Statement stm = null;
		try {
			stm = conn.createStatement();
			conn.setAutoCommit(false);
			String sql="update acrm_f_ci_pot_cus_com_temp set " +
					" ATTEN_BUSI= substr(ATTEN_BUSI, 0,instr(ATTEN_BUSI,'-')-1)," +
					" CUS_RESOURCE= substr(CUS_RESOURCE, 0,instr(CUS_RESOURCE,'-')-1)," +
					" LICENSE_FLAG= substr(LICENSE_FLAG, 0,instr(LICENSE_FLAG,'-')-1)," +
					" TAX_REC_FLAG= substr(TAX_REC_FLAG, 0,instr(TAX_REC_FLAG,'-')-1)," +
					" CREDIT_CARD_FLAG= substr(CREDIT_CARD_FLAG, 0,instr(CREDIT_CARD_FLAG,'-')-1)," +
					" BAD_CREDIT_FLAG= substr(BAD_CREDIT_FLAG, 0,instr(BAD_CREDIT_FLAG,'-')-1)," +
					" DEBIT_FLAG= substr(DEBIT_FLAG, 0,instr(DEBIT_FLAG,'-')-1)," +
					" PER_CARD_FLAG= substr(PER_CARD_FLAG, 0,instr(PER_CARD_FLAG,'-')-1)," +
					" CREDIT_USE= substr(CREDIT_USE, 0,instr(CREDIT_USE,'-')-1)," +
					" GUA_MOR_FLAG= substr(GUA_MOR_FLAG, 0,instr(GUA_MOR_FLAG,'-')-1)," +
					" Q_CUSTOMERTYPE= substr(Q_CUSTOMERTYPE, 0,instr(Q_CUSTOMERTYPE,'-')-1)," +
					" Q_INTERVIEWEEPOST= substr(Q_INTERVIEWEEPOST, 0,instr(Q_INTERVIEWEEPOST,'-')-1)," +
					" Q_BUSINESS= substr(Q_BUSINESS, 0,instr(Q_BUSINESS,'-')-1)," +
					" Q_MARKETIN= substr(Q_MARKETIN, 0,instr(Q_MARKETIN,'-')-1)," +
					" G_HOUSE= substr(G_HOUSE, 0,instr(G_HOUSE,'-')-1)," +
					" G_HOUSEPLEDGE= substr(G_HOUSEPLEDGE, 0,instr(G_HOUSEPLEDGE,'-')-1)," +
					" G_LAND= substr(G_LAND, 0,instr(G_LAND,'-')-1)," +
					" G_LANDPLEDGE= substr(G_LANDPLEDGE, 0,instr(G_LANDPLEDGE,'-')-1)," +
					" G_EQUIPMENT= substr(G_EQUIPMENT, 0,instr(G_EQUIPMENT,'-')-1)," +
					" G_EQUIPMENTPLEDGE= substr(G_EQUIPMENTPLEDGE, 0,instr(G_EQUIPMENTPLEDGE,'-')-1)," +
					" G_FOREST= substr(G_FOREST, 0,instr(G_FOREST,'-')-1)," +
					" G_FORESTPLEDGE= substr(G_FORESTPLEDGE, 0,instr(G_FORESTPLEDGE,'-')-1)," +
					" G_MINING= substr(G_MINING, 0,instr(G_MINING,'-')-1)," +
					" G_MININGPLEDGE= substr(G_MININGPLEDGE, 0,instr(G_MININGPLEDGE,'-')-1)," +
					" G_FLOATING= substr(G_FLOATING, 0,instr(G_FLOATING,'-')-1)," +
					" G_FLOATPLEDGE= substr(G_FLOATPLEDGE, 0,instr(G_FLOATPLEDGE,'-')-1)," +
					" G_DEPOSIT= substr(G_DEPOSIT, 0,instr(G_DEPOSIT,'-')-1)," +
					" G_DEPOSITPLEDGE= substr(G_DEPOSITPLEDGE, 0,instr(G_DEPOSITPLEDGE,'-')-1)," +
					" G_VEHICLE= substr(G_VEHICLE, 0,instr(G_VEHICLE,'-')-1)," +
					" G_VEHICLEPLEDGE= substr(G_VEHICLEPLEDGE, 0,instr(G_VEHICLEPLEDGE,'-')-1)," +
					" G_RECEIVABLEMONEY= substr(G_RECEIVABLEMONEY, 0,instr(G_RECEIVABLEMONEY,'-')-1)," +
					" G_RECEIVABLEMPLEDGE= substr(G_RECEIVABLEMPLEDGE, 0,instr(G_RECEIVABLEMPLEDGE,'-')-1)," +
					" G_STOCK= substr(G_STOCK, 0,instr(G_STOCK,'-')-1)," +
					" G_STOCKPLEDGE= substr(G_STOCKPLEDGE, 0,instr(G_STOCKPLEDGE,'-')-1)," +
					" A_FILETYPE= substr(A_FILETYPE, 0,instr(A_FILETYPE,'-')-1)," +
					" B_FILETYPE= substr(B_FILETYPE, 0,instr(B_FILETYPE,'-')-1)," +
					" C_FILETYPE= substr(C_FILETYPE, 0,instr(C_FILETYPE,'-')-1)," +
					" CUS_STATUS= substr(CUS_STATUS, 0,instr(CUS_STATUS,'-')-1)," +
					" ISNEW= substr(ISNEW, 0,instr(ISNEW,'-')-1)," +
					" REQ_CURRENCY= substr(REQ_CURRENCY, 0,instr(REQ_CURRENCY,'-')-1),"+
					" CUST_MGR=(CASE WHEN CUST_MGR IS NULL OR CUST_MGR='null' THEN  '"+userId+"' ELSE CUST_MGR END )"+
					" where ID like '" + PKhead + "%'";
			stm.execute(sql);
			Random random1 = new Random();
			int sp=Math.abs(random1.nextInt());
			String result="crm"+sp;
			String updateSQL = " insert into ACRM_F_CI_POT_CUS_COM("
					+ "CUS_ID,CUS_NAME,CUS_PHONE,ATTEN_NAME,ATTEN_BUSI,ATTEN_PHONE,LEGAL_NAME,REG_CAP_AMT,CUS_RESOURCE,ACT_CTL_NAME,"
					+ "ACT_CTL_PHONE,ACT_CTL_WIFE,PARTNER_INFO1,PARTNER_RATE1,PARTNER_INFO2,PARTNER_RATE2,PARTNER_INFO3,"
					+ "PARTNER_RATE3,AMOUNT2,AMOUNT1,"
					+ "AMOUNT,PRE_AMOUNT,AVE_BALANCE,TOTAL_ASS,LICENSE_FLAG,TAX_REC_FLAG,DEBT_AMOUNT,CAP_AMOUNT,LOAN_AMOUNT,FINA_AMOUNT,"
					+ "CREDIT_CARD_FLAG,BAD_CREDIT_FLAG,DEBIT_FLAG,PER_CARD_FLAG,CREDIT_CARD_BANK,CREDIT_USE,PRE_CREDIT_AMOUNT,REPAY_SOURCE,"
					+ "TERM,SUP_INF,SUP_INF_RATE,BUYER_INF_RATE,SUP_INF_S,SUP_INF_S_RATE,BUYER_INF,BUYER_INF_S,"
					+ "BUYER_INF_S_RATE,RELATION_COM,RELATION_COM_S,GUA_MOR_FLAG,CUST_MGR,Q_CUSTOMERTYPE,Q_INTERVIEWEENAME,"
					+ "Q_INTERVIEWEEPOST,Q_BUSINESS,Q_OPERATEYEARS,Q_MARKETIN,Q_ASSTOTAL,Q_MAGYEARS,Q_FOUNDEDYEARS,Q_ADDRYEARS,"
					+ "Q_WORKYEARS,Q_CREDITLIMIT,Q_PYEARINCOME,Q_LYEARINCOME,Q_TOTALINCOME,Q_PLANINCOME,G_HOUSE,G_HOUSEPLEDGE,"
					+ "G_LAND,G_LANDPLEDGE,G_EQUIPMENT,G_EQUIPMENTPLEDGE,G_FOREST,G_FORESTPLEDGE,G_MINING,G_MININGPLEDGE,G_FLOATING,"
					+ "G_FLOATPLEDGE,G_DEPOSIT,G_DEPOSITPLEDGE,G_VEHICLE,G_VEHICLEPLEDGE,G_RECEIVABLEMONEY,G_RECEIVABLEMPLEDGE,"
					+ "G_STOCK,G_STOCKPLEDGE,A_FILETYPE,B_FILETYPE,C_FILETYPE,CUS_STATUS,ISNEW,CUS_ADDR,REQ_CURRENCY,CUS_RESOURCE_BAK1,CUS_RESOURCE_BAK2,CUS_RESOURCE_BAK3,CUST_TYPE)"
//					+ "select ID," 
					+ "select 'crm'||id_sequence.nextval,"
					+ "replace(CUS_NAME,'null',''),replace(CUS_PHONE,'null',''),replace(ATTEN_NAME,'null',''),replace(ATTEN_BUSI,'null',''),replace(ATTEN_PHONE,'null',''),replace(LEGAL_NAME,'null',''),replace(REG_CAP_AMT,'null',''),replace(CUS_RESOURCE,'null',''),replace(ACT_CTL_NAME,'null',''),"
					+ "replace(ACT_CTL_PHONE,'null',''),replace(ACT_CTL_WIFE,'null',''),replace(PARTNER_INFO1,'null',''),replace(PARTNER_RATE1,'null',''),replace(PARTNER_INFO2,'null',''),replace(PARTNER_RATE2,'null',''),replace(PARTNER_INFO3,'null',''),"
					+ "replace(PARTNER_RATE3,'null',''),replace(AMOUNT2,'null',''),replace(AMOUNT1,'null',''),"
					+ "replace(AMOUNT,'null',''),replace(PRE_AMOUNT,'null',''),replace(AVE_BALANCE,'null',''),replace(TOTAL_ASS,'null',''),replace(LICENSE_FLAG,'null',''),replace(TAX_REC_FLAG,'null',''),replace(DEBT_AMOUNT,'null',''),replace(CAP_AMOUNT,'null',''),replace(LOAN_AMOUNT,'null',''),replace(FINA_AMOUNT,'null',''),"
					+ "replace(CREDIT_CARD_FLAG,'null',''),replace(BAD_CREDIT_FLAG,'null',''),replace(DEBIT_FLAG,'null',''),replace(PER_CARD_FLAG,'null',''),replace(CREDIT_CARD_BANK,'null',''),replace(CREDIT_USE,'null',''),replace(PRE_CREDIT_AMOUNT,'null',''),replace(REPAY_SOURCE,'null',''),"
					+ "replace(TERM,'null',''),replace(SUP_INF,'null',''),replace(SUP_INF_RATE,'null',''),replace(BUYER_INF_RATE,'null',''),replace(SUP_INF_S,'null',''),replace(SUP_INF_S_RATE,'null',''),replace(BUYER_INF,'null',''),replace(BUYER_INF_S,'null',''),"
					+ "replace(BUYER_INF_S_RATE,'null',''),replace(RELATION_COM,'null',''),replace(RELATION_COM_S,'null',''),replace(GUA_MOR_FLAG,'null',''),replace(CUST_MGR,'null',''),replace(Q_CUSTOMERTYPE,'null',''),replace(Q_INTERVIEWEENAME,'null',''),"
					+ "replace(Q_INTERVIEWEEPOST,'null',''),replace(Q_BUSINESS,'null',''),replace(Q_OPERATEYEARS,'null',''),replace(Q_MARKETIN,'null',''),replace(Q_ASSTOTAL,'null',''),replace(Q_MAGYEARS,'null',''),replace(Q_FOUNDEDYEARS,'null',''),replace(Q_ADDRYEARS,'null',''),"
					+ "replace(Q_WORKYEARS,'null',''),replace(Q_CREDITLIMIT,'null',''),replace(Q_PYEARINCOME,'null',''),replace(Q_LYEARINCOME,'null',''),replace(Q_TOTALINCOME,'null',''),replace(Q_PLANINCOME,'null',''),replace(G_HOUSE,'null',''),replace(G_HOUSEPLEDGE,'null',''),"
					+ "replace(G_LAND,'null',''),replace(G_LANDPLEDGE,'null',''),replace(G_EQUIPMENT,'null',''),replace(G_EQUIPMENTPLEDGE,'null',''),replace(G_FOREST,'null',''),replace(G_FORESTPLEDGE,'null',''),replace(G_MINING,'null',''),replace(G_MININGPLEDGE,'null',''),replace(G_FLOATING,'null',''),"
					+ "replace(G_FLOATPLEDGE,'null',''),replace(G_DEPOSIT,'null',''),replace(G_DEPOSITPLEDGE,'null',''),replace(G_VEHICLE,'null',''),replace(G_VEHICLEPLEDGE,'null',''),replace(G_RECEIVABLEMONEY,'null',''),replace(G_RECEIVABLEMPLEDGE,'null',''),"
					+ "replace(G_STOCK,'null',''),replace(G_STOCKPLEDGE,'null',''),replace(A_FILETYPE,'null',''),replace(B_FILETYPE,'null',''),replace(C_FILETYPE,'null',''),replace(CUS_STATUS,'null',''),replace(ISNEW,'null',''),replace(CUS_ADDR,'null',''),replace(REQ_CURRENCY,'null',''),replace(CUS_RESOURCE_BAK1,'null',''),replace(CUS_RESOURCE_BAK2,'null',''),replace(CUS_RESOURCE_BAK3,'null',''),'1'"
					+ " from ACRM_F_CI_POT_CUS_COM_TEMP where ID like '" + PKhead + "%' AND CUS_NAME NOT IN(SELECT CUST_NAME FROM ACRM_F_CI_CUSTOMER)";
			stm.execute(updateSQL);
			log.info("updateSQL: 【" + updateSQL + "】");
			String deleteSQL = "DELETE FROM ACRM_F_CI_POT_CUS_COM_TEMP t where t.ID like '" + PKhead + "%' AND CUS_NAME NOT IN(SELECT CUST_NAME FROM ACRM_F_CI_CUSTOMER) ";//插入成功之后删除名称与正式客户名称不相同的数据
			stm.execute(deleteSQL);
			conn.commit();
		} catch (Exception e) {
			String deleteSQL = "DELETE FROM ACRM_F_CI_POT_CUS_COM_TEMP t where t.ID like '" + PKhead + "%'";//插入失败删除原先的数据
			stm.execute(deleteSQL);
			e.printStackTrace();
			conn.rollback();
		} finally {
			JdbcUtil.close(null, stm, conn);
		}
	}

}
