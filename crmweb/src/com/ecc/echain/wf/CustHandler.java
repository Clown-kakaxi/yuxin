package com.ecc.echain.wf;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.jsp.jstl.sql.Result;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ecc.echain.workflow.engine.EVO;
import com.yuchengtech.bcrm.custview.model.OcrmFCiBelongOrg;
import com.yuchengtech.bcrm.echain.EChainCallbackCommon;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;
import com.yuchengtech.trans.client.TransClient;

/**
 * @description 客户托管和退回--工作流动态调用类
 * @author luyueyue
 * 
 * @update helin,20140926,
 * 注：客户托管按华一需求，无需要走流程与ECIF交易接口,不属于需要交易的表
 *     代码中涉及：/////////////////////////////////注释的地方都是要增加与ECIF处理的地方
 */
public class CustHandler extends EChainCallbackCommon{
	
	private static Logger log = Logger.getLogger(CustHandler.class);

	/**
	 * 客户托管审批通过调用类
	 * @param vo
	 */
	public void endY(EVO vo){
		try {
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split("_");
			SQL = " update OCRM_F_CI_BELONG_TRUSTEESHIP set TRUST_STAT = '02' where id = "+instanceids[1]+" ";
			execteSQL(vo);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BizException(1,0,"10001","工作流动态调用客户托管通过逻辑失败!");
		}
	}
	
	/**
	 * 客户托管审批拒绝处理
	 * @param vo
	 */
	public void endN(EVO vo){
		try {
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split("_");
			SQL = " update OCRM_F_CI_BELONG_TRUSTEESHIP set TRUST_STAT = '03' where id = "+instanceids[1]+" ";
			execteSQL(vo);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BizException(1,0,"10001","工作流动态调用客户托管拒绝逻辑失败!");
		}

	}

	/**
	 * 客户退回审批通过调用逻辑处理方法
	 * @param vo
	 * @throws SQLException
	 */
	public void endY1(EVO vo) throws SQLException{
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		try {
			conn = vo.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split("_");
			
			//修改退回业务申请表状态
			sql =  " update OCRM_F_CI_BELONG_BACK set BACK_STAT = '2' where id = "+instanceids[1]+" ";
			log.info("---endY1--UPDATE--SQL: "+sql);
			stmt.execute(sql);
			
			/**
			 * 通过查询获取到要退回的归属客户经理关系，
			 * 发起ECIF交易或者拼接交易报文,注此处的只能拼装报文处理,因为整个退回属于一个事务交易,必须保证所有操作成功
			 */
			//1、拼装退回归属关系的报文 OCRM_F_CI_BELONG_CUSTMGR,查询条件与下述删除退回关系条件一致
			////////////////////////////////////
			//循环增加删除归属客户经理报文
			////////////////////////////////////
			
			//2、删除要退回的关系
			sql = " delete from OCRM_F_CI_BELONG_CUSTMGR where id in ( select RECORD_ID from OCRM_F_CI_BELONG_BACK  where id = "+instanceids[1]+") ";
			log.info("---endY1--UPDATE--SQL: "+sql);
			stmt.execute(sql);
			
			//3、查询所有需要退回的客户归属
			SQL = " select b.*,to_char(BACK_DATE,'YYYY-MM-DD') as bdate from OCRM_F_CI_BELONG_BACK b where b.id = "+instanceids[1]+" ";
			Result rs = querySQL(vo);
			//循环处理单个客户退回逻辑
			for(int i=0;i<rs.getRowCount();i++){
				String custId = (String) rs.getRows()[i].get("CUST_ID");
				String custType = (String) rs.getRows()[i].get("CUST_TYPE");
				String orgId = (String) rs.getRows()[i].get("ORG_ID");
				String mgrIdOld = (String) rs.getRows()[i].get("MGR_ID");
				String mgrNameOld = (String) rs.getRows()[i].get("MGR_NAME");
				String date = (String) rs.getRows()[i].get("bdate");
				String recordId = (String) rs.getRows()[i].get("RECORD_ID");
				
				//查询本机构的虚拟客户经理
				SQL = "select * from admin_auth_account where id in(select account_id from admin_auth_account_role where role_id='100009') and org_id='"+orgId+"'";
				Result rs1 = querySQL(vo);
				/*wzy,20140925,modify:做判空处理，避免出现数组越界异常
				String mgrId = (String) rs1.getRows()[0].get("ACCOUNT_NAME");
				String mgrName = (String) rs1.getRows()[0].get("USER_NAME");*/
				String mgrId = (rs1.getRows() != null && rs1.getRows().length > 0)?(String) rs1.getRows()[0].get("ACCOUNT_NAME"):"";
				String mgrName = (rs1.getRows() != null && rs1.getRows().length > 0)?(String) rs1.getRows()[0].get("USER_NAME"):"";
				
				//处理被退回客户对应的商机的数据（商机做退回处理）
				this.dealMktOpprBack(custId, conn);
				
				//如果客户为对私客户  直接添加与虚拟客户经理的归属关系  机构关系不用修改
				if("2".equals(custType)){
					sql = " insert into OCRM_F_CI_BELONG_CUSTMGR " +
					"(ID,CUST_ID,MGR_ID,MAIN_TYPE,MAINTAIN_RIGHT,CHECK_RIGHT,ASSIGN_USER,ASSIGN_USERNAME,ASSIGN_DATE,INSTITUTION,INSTITUTION_NAME,MGR_NAME,EFFECT_DATE) " +
					"select '"+recordId+"',cust_id,'"+mgrId+"','','1','1',MGR_ID," +
					"MGR_NAME,BACK_DATE,ORG_ID,ORG_NAME,'"+mgrName+"',to_date(to_char(sysdate,'YYYY-MM-DD'),'YYYY-MM-DD') " +
					" from OCRM_F_CI_BELONG_BACK where id = "+instanceids[1]+" ";
					log.info("---endY1--UPDATE--SQL: "+sql);
					stmt.execute(sql);
					
					////////////////////////////////////
					//增加插入归属客户经理报文
					String responseXml = TranCrmToEcif(instanceids[1],vo,null,null);
					boolean responseFlag;
					responseFlag = doResXms(responseXml);
					if(!responseFlag){
						throw new BizException(1,0,"0000","Warning-168:数据信息同步失败,请及时联系IT部门!");
					}
					////////////////////////////////////
				}
				
				//如果客户为对公客户  处理与虚拟客户经理的归属关系  机构关系不用修改
				if("1".equals(custType)){
					String mainType = (String) rs.getRows()[0].get("MAIN_TYPE");
					//查询虚拟客户经理与客户的已有主协办关系
					SQL = "select * from OCRM_F_CI_BELONG_CUSTMGR where CUST_ID='"+custId+"' and MGR_ID ='"+mgrId+"'";
					Result rs2 = querySQL(vo);
					if(rs2.getRowCount() == 0){//直接添加新关系
						sql = " insert into OCRM_F_CI_BELONG_CUSTMGR " +
						"(ID,CUST_ID,MGR_ID,MAIN_TYPE,MAINTAIN_RIGHT,CHECK_RIGHT,ASSIGN_USER,ASSIGN_USERNAME,ASSIGN_DATE,INSTITUTION,INSTITUTION_NAME,MGR_NAME,EFFECT_DATE) " +
						"select '"+recordId+"',cust_id,'"+mgrId+"',MAIN_TYPE,decode(MAIN_TYPE,'1','1','2','0','1'),'1',MGR_ID," +
						"MGR_NAME,BACK_DATE,ORG_ID,ORG_NAME,'"+mgrName+"',to_date(to_char(sysdate,'YYYY-MM-DD'),'YYYY-MM-DD') " +
						" from OCRM_F_CI_BELONG_BACK where id = "+instanceids[1]+" ";
						log.info("---endY1--UPDATE--SQL: "+sql);
						stmt.execute(sql);
						
						////////////////////////////////////
						//增加插入归属客户经理报文
						String responseXml = TranCrmToEcif(instanceids[1],vo,null,null);
						boolean responseFlag;
						responseFlag = doResXms(responseXml);
						if(!responseFlag){
							throw new BizException(1,0,"0000","Warning-168:数据信息同步失败,请及时联系IT部门!");
						}
						////////////////////////////////////
					}else{
						String mainTypeOld = (String) rs2.getRows()[0].get("MAIN_TYPE");
						if("2".equals(mainTypeOld)&&"1".equals(mainType)){//原来是协办现在是主办才需要修改   其他情况不需要修改
							sql = " update from OCRM_F_CI_BELONG_CUSTMGR set main_type='1',EFFECT_DATE=to_date(to_char(sysdate,'YYYY-MM-DD'),'YYYY-MM-DD')," +
									"ASSIGN_USER='"+mgrIdOld+"'ASSIGN_USERNAME='"+mgrNameOld+"',ASSIGN_DATE=to_date('"+date+"','YYYY-MM-dd') where CUST_ID='"+custId+"' and MGR_ID ='"+mgrId+"'";
							log.info("---endY1--UPDATE--SQL: "+sql);
							stmt.execute(sql);
							////////////////////////////////////
							//增加修改归属客户经理报文,协办更改为主办
							String responseXml = TranCrmToEcif(instanceids[1],vo,mgrIdOld,mgrNameOld);
							boolean responseFlag;
							responseFlag = doResXms(responseXml);
							if(!responseFlag){
								throw new BizException(1,0,"0000","Warning-168:数据信息同步失败,请及时联系IT部门!");
							}
							////////////////////////////////////
						}
					}
				}
			}
			
			//获取拼装的所有报文、发起与ECIF的交易、根据交易状态返回结果、判定是否抛出异常
			//throw new BizException(1,0,"10001","与ECIF报文交易失败或超时,请稍后重试或联系管理员！");
			
			conn.commit();
		}catch(BizException e){
			conn.rollback();
			throw e;
		}catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
			throw new BizException(1,0,"10001","工作流动态调用通过客户退回逻辑失败!");
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}

	/**
	 * 退回 拒绝处理
	 * @param vo
	 */
	public void endN1(EVO vo){
		try {
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split("_");
			SQL = " update OCRM_F_CI_BELONG_BACK set BACK_STAT = '3' where id = "+instanceids[1]+" ";
			execteSQL(vo);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BizException(1,0,"10001","工作流动态调用拒绝客户退回逻辑失败!");
		}
	}

	/**
	 * 客户退回逻辑处理，及与ECIF交易接口调用方法
	 * @param id 流程实例号
	 * @param vo 工作流流入对象
	 * 不能直接单个进行交易,要一次性把所有报文发送过去进行交易
	 */
	public String TranCrmToEcif(String id,EVO vo,String mgrIdOld,String mgrNameOld){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String req = "";
    	//注：工作流动态调用类中,是无法获取Session用户数据的
    	try {
    		SQL = " select b.*,to_char(BACK_DATE,'YYYY-MM-DD') as bdate from OCRM_F_CI_BELONG_BACK b where b.id = "+id+" ";
			Result rs = querySQL(vo);//13882283365
			//循环处理单个客户退回逻辑
			for(int i=0;i<rs.getRowCount();i++){
				 String custId = (String) rs.getRows()[i].get("CUST_ID");
				 String orgId = (String) rs.getRows()[i].get("ORG_ID");
				
				 //查询本机构的虚拟客户经理
				 SQL = "select * from admin_auth_account where id in(select account_id from admin_auth_account_role where role_id='100009') and org_id='"+orgId+"'";
				 Result rs1 = querySQL(vo);
				 String mgrId = (rs1.getRows() != null && rs1.getRows().length > 0)?(String) rs1.getRows()[0].get("ACCOUNT_NAME"):"";
		
    			 com.yuchengtech.trans.bo.RequestHeader header = new com.yuchengtech.trans.bo.RequestHeader();
    			 header.setReqSysCd("CRM");
    			 header.setReqSeqNo(format.format(new Date()));//交易流水号
    			 header.setReqDt(new SimpleDateFormat("yyyyMMdd").format(new Date()));//请求日期
    			 header.setReqTm(new SimpleDateFormat("HHmmssSS").format(new Date()));//请求时间
    			 
    			 header.setDestSysCd("ECIF");
				 header.setChnlNo("82");
				 header.setBrchNo("503");
				 header.setBizLine("209");
				 header.setTrmNo("TRM10010");
				 header.setTrmIP("127.0.0.1");
    			 header.setTlrNo(vo.getAuthor());//用户编号
    			 
    			 StringBuffer sb = new StringBuffer();
				 sb.append("<RequestBody>");
				 sb.append("<txCode>updateBelong</txCode>");
				 sb.append("<txName>修改客户归属信息</txName>");
				 sb.append("<authType>1</authType>");
				 sb.append("<authCode>1010</authCode>");
				 sb.append("<custNo>" + custId + "</custNo>");
				 sb.append("<belongBranch></belongBranch>");
				 sb.append("<belongManager>");
				 sb.append("<custManagerType></custManagerType>");
				 sb.append("<validFlag></validFlag>");
				 sb.append("<startDate></startDate>");
				 sb.append("<endDate></endDate>");
				 if(mgrIdOld!=null && mgrIdOld!=""){
					 sb.append("<custManagerNo>"+mgrIdOld+"</custManagerNo>"); //客户经理编号
					 sb.append("<mainType>1</mainType>"); //主协办类型
    			 }else{
    				 sb.append("<custManagerNo>"+mgrId+"</custManagerNo>"); //客户经理编号
    				 sb.append("<mainType></mainType>"); 
    			 }
				 sb.append("</belongManager>");
				 sb.append("</RequestBody>");
				 String Xml = new String(sb.toString().getBytes());
    			 req  = TransClient.process(header, Xml);
    		}
		}catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1,0,"0000","Warning-168:数据信息同步失败,请及时联系IT部门!");
		}
		return req;
	}
	
	/**
	 * 处理返回报文
	 * @param xml
	 * @return
	 */
	public boolean doResXms(String xml) throws Exception{
		try{
			xml=xml.substring(8);
			Document doc = DocumentHelper.parseText(xml);
			Element root = doc.getRootElement();
			String TxStatCode = root.element("ResponseTail").element("TxStatCode").getTextTrim();
			if(TxStatCode!=null && !TxStatCode.trim().equals("") && (TxStatCode.trim().equals("000000"))){
				return true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * wzy,20140925,处理被退回客户对应的商机的数据（商机做退回处理）
	 * @param cust_id
	 * @param con
	 */
	@SuppressWarnings("unchecked")
	private void dealMktOpprBack(String cust_id,Connection con){
    	AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//查询被退回客户对应的归属机构（有主办机构，取主办机构信息，没有，则取协办机构中的任意一个）
		OcrmFCiBelongOrg ofcbo = this.getBelongOrg(cust_id, con);
		//查询被退回客户对应的所有的商机（状态为“执行中”）
		StringBuffer updateSql = null;
		StringBuffer insertSql = null;
		StringBuffer querySql = new StringBuffer("");
		querySql.append("select t.oppor_id from ocrm_f_mm_mkt_busi_oppor t where t.oppor_stat = '4'");//状态为“执行中”
		querySql.append(" and t.cust_id = '"+cust_id+"'");
		try{
			ResultSet rs = con.createStatement().executeQuery(querySql.toString());
			while(rs.next()){
				//执行回退操作
				updateSql = new StringBuffer("");
				updateSql.append("update ocrm_f_mm_mkt_busi_oppor t set");
				updateSql.append(" t.execute_user_id = '',");// 清空“执行人ID”
				updateSql.append(" t.execute_user_name = '',");// 清空“执行人名称”
				updateSql.append(" t.execute_org_id = '',");// 清空“执行机构ID”
				updateSql.append(" t.execute_org_name = '',");// 清空“执行机构名称”
				updateSql.append(" t.claim_user_id = '',");// 清空“认领人ID”
				updateSql.append(" t.claim_user_name = '',");// 清空“认领人名称”
				updateSql.append(" t.claim_org_id = '',");// 清空“认领机构IDa”
				updateSql.append(" t.claim_org_name = '',");// 清空“认领机构名称”
				if (ofcbo != null) {
					// 如果客户有归属机构，将商机状态置成“1-待分配”，将待分配机构设置成客户对应的归属机构
					updateSql.append(" t.oppor_stat = '1',");// 商机状态置成“1-待分配”
					updateSql.append(" t.assign_ogr_id = '"+ofcbo.getInstitutionCode()+"',");// 待分配机构ID置成“归属机构代码”
					updateSql.append(" t.assign_org_name = '"+ofcbo.getInstitutionName()+"',");// 待分配机构名称置成“归属机构名称”
				}else{
					updateSql.append(" t.oppor_stat = '5',");// 商机状态(退回(5))
					updateSql.append(" t.assign_ogr_id = '',");// 清空“待分配机构ID”
					updateSql.append(" t.assign_org_name = '',");// 清空“待分配机构名称”
				}
				updateSql.append(" t.update_user_id = '"+auth.getUserId()+"',");// 最近更新人ID
				updateSql.append(" t.update_user_name = '"+auth.getUsername()+"',");// 最近更新人名称
				updateSql.append(" t.update_org_id = '"+((HashMap<String, String>) (auth.getPathOrgList().get(0))).get("ID")+"',");// 最近更新机构ID
				updateSql.append(" t.update_org_name = '"+((HashMap<String, String>) (auth.getPathOrgList().get(0))).get("UNITNAME")+"',");// 最近更新机构名称
				updateSql.append(" t.update_date_time = sysdate");// 最近更新时间
				updateSql.append(" where t.oppor_id = '"+rs.getString("oppor_id")+"'");
				log.info("---dealMktOpprBack--UPDATE--SQL: "+updateSql.toString());
				con.createStatement().executeUpdate(updateSql.toString());
				// 新增商机操作历史记录
				insertSql = new StringBuffer("");
				insertSql.append("insert into ocrm_f_mm_mkt_busi_oppor_his_s(");
				insertSql.append("step_id,");
				insertSql.append("oppor_id,");
				insertSql.append("opr_user_id,");					
				insertSql.append("opr_user_name,");
				insertSql.append("opr_org_id,");
				insertSql.append("opr_org_name,");
				insertSql.append("opr_content,");
				insertSql.append("opr_date_time)");
				insertSql.append(" values (");
				insertSql.append("id_sequence.nextval,");
				insertSql.append("'"+rs.getString("oppor_id")+"',");;
				insertSql.append("'"+auth.getUserId()+"',");
				insertSql.append("'"+auth.getUsername()+"',");
				insertSql.append("'"+((HashMap<String, String>) (auth.getPathOrgList().get(0))).get("ID")+"',");
				insertSql.append("'"+((HashMap<String, String>) (auth.getPathOrgList().get(0))).get("UNITNAME")+"',");
				insertSql.append("'“" + auth.getUsername()+ "”审批客户退回通过，同时退回商机。',");
				insertSql.append("sysdate)");
				log.info("---dealMktOpprBack--UPDATE--SQL: "+insertSql.toString());
				con.createStatement().executeUpdate(insertSql.toString());
			}
		} catch (BizException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1,0,"10001","客户退回对应的商机操作失败!");
		}
	}
	
	/**
	 * 根据客户ID，查询客户归属的主办机构对象
	 * @param cust_id
	 * @param con
	 * @return
	 */
	private OcrmFCiBelongOrg getBelongOrg(String cust_id,Connection con) {
		String sql = null;
		OcrmFCiBelongOrg ofcbo = null;
		sql = "select t.main_type,t.institution_code,t.institution_name from ocrm_f_ci_belong_org t where t.cust_id = '"+ cust_id + "' ";
		int count = 0;
		try{
			ResultSet rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				if(count == 0 || "1".equals(rs.getString("main_type"))){
					//取主办机构或者是协办机构中的第一个
					if(ofcbo == null){
						ofcbo = new OcrmFCiBelongOrg();
					}
					ofcbo.setMainType(rs.getString("main_type"));
					ofcbo.setInstitutionCode(rs.getString("institution_code"));
					ofcbo.setInstitutionName(rs.getString("institution_name"));
				}
				count++;
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new BizException(1,0,"10001","查询客户归属机构SQL错误!");
		}
		return ofcbo;
	}
}
