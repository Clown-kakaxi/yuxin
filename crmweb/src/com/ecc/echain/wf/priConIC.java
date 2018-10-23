package com.ecc.echain.wf;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import javax.servlet.jsp.jstl.sql.Result;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ecc.echain.workflow.engine.EVO;
import com.yuchengtech.bcrm.custview.action.AcrmFCiContmethInfoAction;
import com.yuchengtech.bcrm.echain.EChainCallbackCommon;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;
import com.yuchengtech.trans.bo.RequestHeader;
import com.yuchengtech.trans.client.TransClient;

/**
 * @describtion: 工作流动态调用类-对私视图联系信息
 *
 * @author : ?
 * @date : ?
 */
public class priConIC extends EChainCallbackCommon {
	
	AcrmFCiContmethInfoAction theObj = new AcrmFCiContmethInfoAction();
	private static Logger log = Logger.getLogger(priConIC.class);
	
	/**
	 * 审批拒绝调用方法
	 * @param vo
	 */
	public void endN(EVO vo){
		try {
			AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String instanceid = vo.getInstanceID();
			String custId = instanceid.split("_")[1];
			String updateFlag = instanceid.split("_")[2];
			SQL = "update OCRM_F_CI_CUSTINFO_UPHIS V set v.appr_flag = '2',v.appr_user = '"+auth.getUserId()+"',v.appr_date = to_char(sysdate,'yyyy-MM-dd') WHERE V.CUST_ID='" + custId + "' AND  V.UPDATE_FLAG='" + updateFlag + "'";
			log.info("执行SQL:----->>>>>[" + SQL + "]");
			execteSQL(vo);
		} catch (SQLException e) {
			log.error("处理过程中发生SQL异常(错误):");
			log.error(e.getLocalizedMessage());
		} catch (Exception e) {
			log.error("处理过程中发现异常(错误):");
			log.error(e.getLocalizedMessage());
		}
	}
	
	/**
	 * 审批同意
	 * 先拼接报文、发送报文、然后根据返回报文处理CRM更新
	 * 注：此处的异常必须抛出为BizException,否则在前台不能正常提示出相应的异常信息
	 * @param vo
	 */
	public void endY(EVO vo){
		try {
			AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split("_");
			String custId = instanceids[1];
			String updateFlag = instanceids[2];
			
			//调用交易接口
			String responseXml = TranCrmToEcif(custId, updateFlag, vo);
			boolean responseFlag = doResXms(responseXml);
			
			//判断如果是新增调用操、则获取返回的主键信息,如果返回为null则调用,id_sequence.nextval生成,待现场人员实现
			String pk_xx = getResXms(responseXml);
			
			//增加处理返回报文、判断交易返回结果,用于CRM业务更新时使用
			if(responseFlag){
				//交易成功,拼装CRM 业务SQL并执行
				if("X".equals(instanceids[3])){//修改
					joinSql(vo);
				}else {
					joinInsertSql(vo,pk_xx);
				}
				SQL = "update OCRM_F_CI_CUSTINFO_UPHIS V set v.appr_flag = '1',v.appr_user = '"+auth.getUserId()+"',v.appr_date = to_char(sysdate,'yyyy-MM-dd') WHERE V.CUST_ID='" + custId + "' AND  V.UPDATE_FLAG='" + updateFlag + "'";
				log.info("执行SQL:----->>>>>[" + SQL + "]");
				execteSQL(vo);
			}else{
				throw new BizException(1,0,"10001","与ECIF报文交易失败或超时,请稍后重试或联系管理员！");
			}
			
		} catch (BizException e) {
			throw e;
		} catch (Exception e) {
			log.error("处理过程中发现异常(错误):");
			throw new BizException(1,0,"10000",e.getMessage());
		}
	}
	
	public void joinInsertSql(EVO vo,String pk_xx) throws SQLException{//新增
		String instanceid = vo.getInstanceID();
		String instanceids[] = instanceid.split("_");
		String custId = instanceids[1];
		String updateFlag = instanceids[2];
		//注：必须按order by UPDATE_TABLE,UPDATE_TABLE_ID 排序处理,根据第一个字段是否主键及其值是否为空决定是insert还是update
		SQL = " SELECT * FROM OCRM_F_CI_CUSTINFO_UPHIS V WHERE V.CUST_ID='" + custId + "' AND  V.UPDATE_FLAG='" + updateFlag + "' ORDER BY UPDATE_TABLE,UPDATE_TABLE_ID";
		Result result = querySQL(vo);
		
		StringBuffer tempInsertSql = null; 	//修改的单个表语句
		StringBuffer tempValuesSql = null;
		for (SortedMap<?, ?> row : result.getRows()) {
			String dataNew = (String) row.get("UPDATE_AF_CONT");// 修改后内容
			String colName = (String) row.get("UPDATE_ITEM_EN");// 修改项目字段名
			String tabName = (String) row.get("UPDATE_TABLE");  // 修改表名
			String pkFlag  = (String) row.get("UPDATE_TABLE_ID");// 是否主键字段标识
			String fieldType  = (String) row.get("FIELD_TYPE");// 是否主键字段标识

			//排除表、字段数据错误
			if(tabName == null || "".equals(tabName) || colName == null || "".equals(colName)){
				continue;
			}
			//添加更新字段sql
			if(tempInsertSql == null){
				
				tempInsertSql = new StringBuffer();
				tempValuesSql = new StringBuffer();
				
				if("1".equals(pkFlag) && (pk_xx == null || "".equals(pk_xx))){
					tempInsertSql.append(" INSERT INTO "+ tabName + "("+ colName);
					tempValuesSql.append(" VALUES (ID_SEQUENCE.NEXTVAL");
				}else{
					tempInsertSql.append(" INSERT INTO "+ tabName + "("+ colName);
					tempValuesSql.append(" VALUES ('"+pk_xx+"'");
				}
			}else{
				if("2".equals(fieldType)){
					tempInsertSql.append(" ,"+ colName);
					tempValuesSql.append(" ,TO_DATE('" + dataNew + "','yyyy-MM-dd')");
				}else{
					tempInsertSql.append(" ,"+ colName);
					tempValuesSql.append(" ,'" + dataNew + "'");
				}
			}
		}
		if(tempInsertSql != null && tempValuesSql != null && !"".equals(tempInsertSql.toString())
				&& !"".equals(tempValuesSql.toString())){
			SQLS.add(tempInsertSql.toString() +") "+ tempValuesSql.toString()+")");
			log.info("添加批量INSERT SQL:["+tempInsertSql.toString() +") "+ tempValuesSql.toString()+") ]");
		}
		if(SQLS.size() > 0){
			executeBatch(vo);
		}
	}
	public void joinSql(EVO vo){
		try {
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split("_");
			StringBuffer sb=new StringBuffer("update ACRM_F_CI_CONTMETH set ");
			SQL = " SELECT * FROM OCRM_F_CI_CUSTINFO_UPHIS V WHERE V.CUST_ID='"+instanceids[1]+"' AND  V.UPDATE_FLAG='"+instanceids[2]+"'";
			Result result=querySQL(vo);
			int len = result.getRowCount();
			int i = 0;
			for (SortedMap item : result.getRows()){
				i = i +1;
				if(instanceids[3].equals("X")){//修改
					if(i == len ){
						sb.append(item.get("UPDATE_ITEM_EN")).append("=").append("'"+item.get("UPDATE_AF_CONT")+"'");
					}else{
						sb.append(item.get("UPDATE_ITEM_EN")).append("=").append("'"+item.get("UPDATE_AF_CONT")+"'").append(",");
					}
				}
			}
			sb.append(" where cust_id =").append("'"+instanceids[1]+"'").append(" and CONTMETH_ID =").append("'"+instanceids[4]+"'");
			SQL =sb.toString();
			execteSQL(vo);
			
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("执行SQL出错");
		}
		
	}
	/**
	 * 负责调用拼将报文并发送交易、返回交易结果
	 * @param cust_id
	 * @param update_flag
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String TranCrmToEcif(String cust_id, String update_flag, EVO vo) throws Exception {
		RequestHeader header = new RequestHeader();
		SimpleDateFormat df8 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat df20 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		SimpleDateFormat df10 = new SimpleDateFormat("hhmmssSS");
		header.setReqSysCd("CRM");
		header.setReqSeqNo(df20.format(new Date()));
		header.setReqDt(df8.format(new Date()));
		header.setReqTm(df10.format(new Date()));
		header.setDestSysCd("ECIF");
		header.setChnlNo("82");
		header.setBrchNo("503");
		header.setBizLine("209");
		header.setTrmNo("TRM10010");
		header.setTrmIP("127.0.0.1");
		//注：必须按order by UPDATE_TABLE 排序处理,否则后续XmlTableUtil.reqBodyXml方法会存在错误
		SQL = "select update_af_cont,update_item_en,update_table,update_user from OCRM_F_CI_CUSTINFO_UPHIS where cust_id='" + cust_id
				+ "' and update_flag='" + update_flag + "' ORDER BY UPDATE_TABLE";
		Result result = querySQL(vo);
		StringBuffer sb = new StringBuffer();
		sb.append("<RequestBody>");
		sb.append("<txCode>updatePerCustInfo</txCode>");
		sb.append("<txName>修改个人客户基本信息</txName>");
		sb.append("<authType>1</authType>");
		sb.append("<authCode>1010</authCode>");
		sb.append("<custNo>" + cust_id + "</custNo>");
		List<Map<Object, Object>> list = new ArrayList<Map<Object, Object>>();
		String updateUser = null;
		for (SortedMap item : result.getRows()) {
			list.add(item);
			updateUser = (String) item.get("update_user");
		}
		header.setTlrNo(updateUser);
		XmlTableUtil util = new XmlTableUtil();
		String reqXml = util.reqBodyXml(list, vo);// 将从表里面拿到的字段和字段内容，表名进行分装
		sb.append(reqXml);
		sb.append("</RequestBody>");
		String Xml = new String(sb.toString().getBytes());
		String req = TransClient.process(header, Xml);
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
	 * 处理返回id
	 * @param xml
	 * @return
	 */
	public String getResXms(String xml) throws Exception{
		String id = "";
		try{
			xml=xml.substring(8);
			Document doc = DocumentHelper.parseText(xml);
			Element root = doc.getRootElement();
			if(root.element("ResponseBody").element("contmethId")!=null){
				id = root.element("ResponseBody").element("contmethId").getTextTrim();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return id;
	}
}
