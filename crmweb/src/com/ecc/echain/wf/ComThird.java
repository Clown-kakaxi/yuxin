package com.ecc.echain.wf;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import javax.servlet.jsp.jstl.sql.Result;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ecc.echain.workflow.engine.EVO;
import com.ibm.icu.text.SimpleDateFormat;
import com.yuchengtech.bcrm.echain.EChainCallbackCommon;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;
import com.yuchengtech.trans.bo.RequestHeader;
import com.yuchengtech.trans.client.TransClient;

/**
 * @describtion: 对公非授信信息第二屏工作流动态调用类
 *	于20141203日变更,不能再作为工作流动态调用的入口类
 * @author : 
 * @date : 
 * @update 20140924,helin,增加CRM业务更新操作实现
 */
public class ComThird extends EChainCallbackCommon{
	
	private static Logger log = Logger.getLogger(ComThird.class);
	
	/**
	 * 审批同意
	 * 先拼接报文、发送报文、然后根据返回报文处理CRM更新
	 * 注：此处的异常必须抛出为BizException,否则在前台不能正常提示出相应的异常信息
	 * @param vo
	 */
	public void endY(EVO vo,String custId,String updateFlag,AuthUser auth,int pageItem,String modifyFlag){
		try {
			//调用交易接口
			String responseXml = TranCrmToEcif(custId, updateFlag, vo);
			boolean responseFlag = doResXms(responseXml);
			//判断如果是新增调用操、则获取返回的主键信息,如果返回为null则调用,id_sequence.nextval生成,待现场人员实现
			String pk_xx = getResXms(responseXml);
			
			//增加处理返回报文、判断交易返回结果,用于CRM业务更新时使用
			if(responseFlag){
				//交易成功,拼装CRM 业务SQL并执行
				if("0".equals(modifyFlag)){
					joinUpdateSql(vo,custId,updateFlag,auth);
				}else {
					joinInsertSql(vo,pk_xx,custId,updateFlag,auth);
				}				
				//客户信息变更同步
				if(pageItem == 2){ //地址
					SynchroDataNew.addressSync(vo.getConnection(), updateFlag, custId);
				}else if(pageItem == 3){  //3第三页联系人
//					SynchroDataNew.contmethSync(vo.getConnection(), updateFlag, custId);
				}else if(pageItem == 4){  //4第三页联系信息
					SynchroDataNew.contmethSync(vo.getConnection(), updateFlag, custId);
				}else if(pageItem == 5){  //5第三页证件信息
//					SynchroDataNew.contmethSync(vo.getConnection(), updateFlag, custId);
				}
				SQL = "update OCRM_F_CI_CUSTINFO_UPHIS V set v.appr_flag = '1',v.appr_user = '"+auth.getUserId()+"',v.appr_date = to_char(sysdate,'yyyy-MM-dd HH24:mi:ss') WHERE V.CUST_ID='" + custId + "' AND  V.UPDATE_FLAG='" + updateFlag + "'";
				log.info("执行SQL:----->>>>>[" + SQL + "]");
				execteSQL(vo);
			}else{
				throw new BizException(1,0,"0000","Warning-168：数据信息同步失败，请及时联系IT部门！");
			}			
		} catch (BizException e) {
			throw e;
		} catch (Exception e) {
			log.error("处理过程中发现异常(错误):");
			throw new BizException(1,0,"0000","Warning-169：数据信息同步失败，请及时联系IT部门！");
		}
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
			if(root.element("ResponseBody").element("addrId")!=null){
				id = root.element("ResponseBody").element("addrId").getTextTrim();
			}
			if(root.element("ResponseBody").element("contmethId")!=null){
				id = root.element("ResponseBody").element("contmethId").getTextTrim();
			}
			if(root.element("ResponseBody").element("identId")!=null){
				id = root.element("ResponseBody").element("identId").getTextTrim();
			}
			if(root.element("ResponseBody").element("linkmanId")!=null){
				id = root.element("ResponseBody").element("linkmanId").getTextTrim();
			}
			if(root.element("ResponseBody").element("issueStockId")!=null){
				id = root.element("ResponseBody").element("issueStockId").getTextTrim();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return id;
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
		//此SQL必须按UPDATE_TABLE项排序，否则会存在逻辑错误---TranCrmToEcif
		SQL = "select update_af_cont,update_item_en,update_table,update_user from OCRM_F_CI_CUSTINFO_UPHIS where cust_id='" + cust_id
				+ "' and update_flag='" + update_flag + "' ORDER BY UPDATE_TABLE";
		Result result = querySQL(vo);
		StringBuffer sb = new StringBuffer();
		sb.append("<RequestBody>");
		sb.append("<txCode>updateOrgCustInfoNew</txCode>");
		sb.append("<txName>修改机构客户基本信息</txName>");
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
	 * 拼接业务更新SQL并add到SQLS
	 * 注：单表单条记录修改操作
	 *  ACRM_F_CI_ORG_EXECUTIVEINFO
	 *  ACRM_F_CI_ADDRESS
	 *  ACRM_F_CI_CONTMETH
	 * @param vo 
	 * @throws SQLException 
	 */
	public void joinUpdateSql(EVO vo,String custId,String updateFlag,AuthUser auth) throws SQLException{
		//注：必须按order by UPDATE_TABLE,UPDATE_TABLE_ID 排序处理,根据第一个字段是否主键及其值是否为空决定是insert还是update
		SQL = " SELECT * FROM OCRM_F_CI_CUSTINFO_UPHIS V WHERE V.CUST_ID='" + custId + "' AND  V.UPDATE_FLAG='" + updateFlag + "' ORDER BY UPDATE_TABLE,UPDATE_TABLE_ID";
		Result result = querySQL(vo);
		
		StringBuffer tempUpdateSql = null; 	//修改的单个表语句
		String tempWhereSql = null;
		boolean tableFlag = false;  		//是否同一表操作标识
		String lastTable = null;			//上一次的tableName
		int totalCount = result.getRowCount();
		for (SortedMap<?, ?> row : result.getRows()) {
			totalCount=totalCount-1;
			//String dataOld = row.get("UPDATE_BE_CONT") != null ?(String) row.get("UPDATE_BE_CONT"):"";// 修改前内容
			//dataOld = StringEscapeUtils.escapeSql(dataOld);//sql特殊字符转义
			String dataNew = row.get("UPDATE_AF_CONT") != null ?(String) row.get("UPDATE_AF_CONT"):"";// 修改后内容
			dataNew = StringEscapeUtils.escapeSql(dataNew);//sql特殊字符转义
			String colName = row.get("UPDATE_ITEM_EN") != null ?(String) row.get("UPDATE_ITEM_EN"):"";// 修改项目字段名
			String tabName = row.get("UPDATE_TABLE") != null ?(String) row.get("UPDATE_TABLE"):"";// 修改表名
			String pkFlag  = row.get("UPDATE_TABLE_ID") != null ?(String) row.get("UPDATE_TABLE_ID"):"";// 是否主键字段标识
			String fieldType  = row.get("FIELD_TYPE") != null ?(String) row.get("FIELD_TYPE"):"";// 是否主键字段标识

			//排除表、字段数据错误
			if(tabName == null || "".equals(tabName) || colName == null || "".equals(colName)){
				continue;
			}
			if(lastTable != null && lastTable.equals(tabName)){
				tableFlag = true;
			}else{
				tableFlag = false;
			}
			//添加更新字段sql
			if(!tableFlag){//不同的表时
				if(tempUpdateSql != null && !"".equals(tempUpdateSql.toString())){
					if(tempWhereSql == null){
						if(lastTable.equals("ACRM_F_CI_ORG_EXECUTIVEINFO")||lastTable.equals("ACRM_F_CI_ORG_EXECUTIVEINFO1")){								
							tempWhereSql = " WHERE ORG_CUST_ID='" + custId + "'";
						}
						else if(lastTable.equals("OCRM_F_CI_GROUP_MEMBER_NEW")){
							tempWhereSql = " WHERE CUS_ID='" + custId + "'";
						}else{
							tempWhereSql = " WHERE CUST_ID='" + custId + "'";
						}
					}
					SQLS.add(tempUpdateSql.toString() + tempWhereSql);
					log.info("添加批量UPDATE SQL:["+tempUpdateSql.toString() + tempWhereSql+"]");
				}
				lastTable = tabName;
				tempUpdateSql = new StringBuffer();
				tempWhereSql = null;
				
				if("2".equals(fieldType)){
					tempUpdateSql.append(" UPDATE "+ tabName + " SET "+ colName + "=TO_DATE('" + dataNew + "','yyyy-MM-dd')");
				}else{
					tempUpdateSql.append(" UPDATE "+ tabName + " SET "+ colName + "='" + dataNew + "'");
				}
			}else{//相同的表时
				if("2".equals(fieldType)){
					tempUpdateSql.append(" ,"+ colName + "=TO_DATE('" + dataNew + "','yyyy-MM-dd')");
				}else{
					tempUpdateSql.append(" ,"+ colName + "='" + dataNew + "'");
				}
			}

			//添加update where条件sql
			if("1".equals(pkFlag)){
				tempWhereSql = " WHERE "+ colName + "='" + dataNew + "'";
			}
		}
		//判断是否最后一行操作右是增加相应的sql
		if(totalCount == 0){
			if(tempUpdateSql != null && !"".equals(tempUpdateSql.toString())){
				if(tempWhereSql == null){
					if(lastTable.equals("ACRM_F_CI_ORG_EXECUTIVEINFO")||lastTable.equals("ACRM_F_CI_ORG_EXECUTIVEINFO1")){								
						tempWhereSql = " WHERE ORG_CUST_ID='" + custId + "'";
					}
					else if(lastTable.equals("OCRM_F_CI_GROUP_MEMBER_NEW")){
						tempWhereSql = " WHERE CUS_ID='" + custId + "'";
					}else{
						tempWhereSql = " WHERE CUST_ID='" + custId + "'";
					}
				}
				SQLS.add(tempUpdateSql.toString() + tempWhereSql);
				log.info("添加批量UPDATE SQL:["+tempUpdateSql.toString() + tempWhereSql+"]");
			}
		}
		if(SQLS.size() > 0){
			executeBatch(vo);
		}
	}
	
	/**
	 * 拼接业务更新SQL并add到SQLS
	 * 注：单表单条记录新增操作
	 *  ACRM_F_CI_ORG_EXECUTIVEINFO
	 *  ACRM_F_CI_ADDRESS
	 *  ACRM_F_CI_CONTMETH
	 * @param vo 
	 * @param pk_xx 新增主键/如若为空,则使用默认的id_sequence.nextval,或者可通过设置修改历史字段的修改前值存放sequence名称
	 * @throws SQLException 
	 */
	public void joinInsertSql(EVO vo,String pk_xx,String custId,String updateFlag,AuthUser auth) throws SQLException{
		//注：必须按order by UPDATE_TABLE,UPDATE_TABLE_ID 排序处理,根据第一个字段是否主键及其值是否为空决定是insert还是update
		SQL = " SELECT * FROM OCRM_F_CI_CUSTINFO_UPHIS V WHERE V.CUST_ID='" + custId + "' AND  V.UPDATE_FLAG='" + updateFlag + "' ORDER BY UPDATE_TABLE,UPDATE_TABLE_ID";
		Result result = querySQL(vo);
		
		StringBuffer tempInsertSql = null; 	//修改的单个表语句
		StringBuffer tempValuesSql = null;
		for (SortedMap<?, ?> row : result.getRows()) {
			String dataOld = row.get("UPDATE_BE_CONT") != null ?(String) row.get("UPDATE_BE_CONT"):"";// 修改前内容
			dataOld = StringEscapeUtils.escapeSql(dataOld);//sql特殊字符转义
			String dataNew = row.get("UPDATE_AF_CONT") != null ?(String) row.get("UPDATE_AF_CONT"):"";// 修改后内容
			dataNew = StringEscapeUtils.escapeSql(dataNew);//sql特殊字符转义
			String colName = row.get("UPDATE_ITEM_EN") != null ?(String) row.get("UPDATE_ITEM_EN"):"";// 修改项目字段名
			String tabName = row.get("UPDATE_TABLE") != null ?(String) row.get("UPDATE_TABLE"):"";// 修改表名
			String pkFlag  = row.get("UPDATE_TABLE_ID") != null ?(String) row.get("UPDATE_TABLE_ID"):"";// 是否主键字段标识
			String fieldType  = row.get("FIELD_TYPE") != null ?(String) row.get("FIELD_TYPE"):"";// 是否主键字段标识
			
			//排除表、字段数据错误
			if(tabName == null || "".equals(tabName) || colName == null || "".equals(colName)){
				continue;
			}
			//添加更新字段sql
			if(tempInsertSql == null){
				tempInsertSql = new StringBuffer();
				tempValuesSql = new StringBuffer();
				
				if("1".equals(pkFlag) && (pk_xx == null || "".equals(pk_xx))){
					if(dataNew == null || "".equals(dataNew)){
						//当前台未指定主键时,则使用默认主键
						if(dataOld == null || "".equals(dataOld)){
							tempInsertSql.append(" INSERT INTO "+ tabName + "("+colName);
							tempValuesSql.append(" VALUES (ID_SEQUENCE.NEXTVAL");
						}else if(dataOld.indexOf("NEXTVAL") > -1){//前台指定了主键序列
							tempInsertSql.append(" INSERT INTO "+ tabName + "("+colName);
							tempValuesSql.append(" VALUES ("+dataOld);
						}else{//前台指定了具体的主键值
							tempInsertSql.append(" INSERT INTO "+ tabName + "("+colName);
							tempValuesSql.append(" VALUES ('"+dataOld+"'");
						}
					}else{//ECIF交易返回了主键值
						tempInsertSql.append(" INSERT INTO "+ tabName + "("+colName);
						tempValuesSql.append(" VALUES ('"+dataNew+"'");
					}
				}else{//ECIF交易返回了主键值
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
}
