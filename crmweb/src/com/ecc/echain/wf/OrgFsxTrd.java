package com.ecc.echain.wf;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import javax.servlet.jsp.jstl.sql.Result;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
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
 * @describtion: 对公非授信信息第三屏工作流动态调用处理类
 *	于20141203日变更,不能再作为工作流动态调用的入口类
 * @author : 
 * @date : 
 * @update 20140924,helin,增加CRM业务更新操作实现
 */
public class OrgFsxTrd extends EChainCallbackCommon{
	
	private static Logger log = Logger.getLogger(OrgFsxTrd.class);
	
	/**
	 * 审批同意
	 * 先拼接报文、发送报文、然后根据返回报文处理CRM更新
	 * 注：此处的异常必须抛出为BizException,否则在前台不能正常提示出相应的异常信息
	 * @param vo
	 */
	public void endY(EVO vo,String custId,String updateFlag,AuthUser auth) {
		try {
			//20141105,判断是否存在新增证件信息,若存在则优先处理证件信息
			identifierTrans(vo,custId,updateFlag,auth);
			
			//此SQL必须按UPDATE_TABLE项排序，否则会存在逻辑错误---TranCrmToEcif
			SQL = "select update_af_cont,update_item_en,update_table,update_user from OCRM_F_CI_CUSTINFO_UPHIS where cust_id='" + custId
					+ "' and update_flag='" + updateFlag + "' ORDER BY UPDATE_TABLE";
			//调用交易接口
			String responseXml = TranCrmToEcif(custId, updateFlag, vo);
			boolean responseFlag = doResXms(responseXml);
			
			//增加处理返回报文、判断交易返回结果,用于CRM业务更新时使用
			if(responseFlag){
				//交易成功,拼装CRM 业务SQL并执行
				joinSql(vo,custId,updateFlag,auth);
				
				//客户信息变更同步同步
				SynchroData.customerSync(vo.getConnection(), updateFlag, custId);
				SynchroData.orgSync(vo.getConnection(), updateFlag, custId);
				SynchroData.sendRemind(vo.getConnection(), updateFlag, custId);
				
				SQL = "update OCRM_F_CI_CUSTINFO_UPHIS V set v.appr_flag = '1',v.appr_user = '"+auth.getUserId()+"',v.appr_date = to_char(sysdate,'yyyy-MM-dd HH24:mi:ss') WHERE V.CUST_ID='" + custId + "' AND  V.UPDATE_FLAG='" + updateFlag + "'";
				log.info("执行SQL:----->>>>>[" + SQL + "]");
				execteSQL(vo);
			}else{
				throw new BizException(1,0,"0000","Warning-168：数据信息同步失败，请及时联系IT部门！");
			}
			
		} catch (BizException e) {
			throw e;
		} catch (Exception e) {
			log.error("处理过程中发现异常(错误):"+e.getMessage());
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
	 * 封装请求报文
	 * SQL 变量必须先赋值才能调用此方法
	 * @param cust_id
	 * @param update_flag
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String TranCrmToEcif(String cust_id,String update_flag,EVO vo) throws Exception{
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
		//前提是先拼装好SQL
		Result result = querySQL(vo);
		StringBuffer sb = new StringBuffer();
		sb.append("<RequestBody>");
		sb.append("<txCode>updateOrgCustInfo</txCode>");
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
	 * 
	 * 注：1、此方法最后会把SQL中的ACRM_F_CI_GRADE1 替换为 ACRM_F_CI_GRADE
	 *    2、设置了FIELD_TYPE 值为2的,必须的数据库字段必须为date类型
	 *    3、若是新增操作UPDATE_TABLE_ID 必须为1且字段修改后值为空,
	 *       后续如果考虑到新增操作插入的sequence不一样的话,可考虑在其修改前字段中放置里面sequence名称
	 *    4、此方法在第一屏信息保存时,存在一个潜在的问题,
	 *       如零售、公司客户基本信息不存在,是直接调用update语句,故信息无法保存，因此必须保证要修改的信息基本信息表必须存在
	 *    5、关于级联修改的问题,建议不在此方法中处理，将其转换到前台处理,如客户名称、证件信息涉及同时修改多个表的,请在前台相应的添加到修改历史上去
	 *       右不想在审批界面上看到重复数据可通过将其UPDATE_ITEM项设置为空
	 * @param vo 
	 * @throws SQLException 
	 */
	public void joinSql(EVO vo,String custId,String updateFlag,AuthUser auth) throws SQLException{
		SQLS = new ArrayList<String>();
		//注：必须按order by UPDATE_TABLE,UPDATE_TABLE_ID 排序处理,根据第一个字段是否主键及其值是否为空决定是insert还是update
		SQL = " SELECT * FROM OCRM_F_CI_CUSTINFO_UPHIS V WHERE V.CUST_ID='" + custId + "' AND  V.UPDATE_FLAG='" + updateFlag + "' ORDER BY UPDATE_TABLE,UPDATE_TABLE_ID";
		Result result = querySQL(vo);
		
		boolean tableFlag = false;  		//是否同一表操作标识
		boolean insertFlag = false;			//插入与否标识，false表示update
		String lastTable = null;			//上一次的tableName
		
		StringBuffer tempUpdateSql = null; 	//修改的单个表语句
		String tempWhereSql = null;
		StringBuffer tempInsertSql = null;  //插入语句
		StringBuffer tempValuesSql = null;
		int totalCount = result.getRowCount();
		for (SortedMap<?, ?> row : result.getRows()) {
			totalCount = totalCount -1;
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
			if(lastTable != null && lastTable.equals(tabName)){
				tableFlag = true;
			}else{
				tableFlag = false;
			}
			//添加更新字段sql
			if(!tableFlag){
				if(tempUpdateSql != null && !"".equals(tempUpdateSql.toString())){
					if(tempWhereSql == null){
						tempWhereSql = " WHERE CUST_ID='" + custId + "'";
					}
					SQLS.add(tempUpdateSql.toString() + tempWhereSql);
					log.info("添加批量UPDATE SQL:["+tempUpdateSql.toString() + tempWhereSql+"]");
				}
				if(tempInsertSql != null && tempValuesSql != null && !"".equals(tempInsertSql.toString())
						&& !"".equals(tempValuesSql.toString())){
					SQLS.add(tempInsertSql.toString() +") "+ tempValuesSql.toString()+")");
					log.info("添加批量INSERT SQL:["+tempInsertSql.toString() +") "+ tempValuesSql.toString()+") ]");
				}
				
				lastTable = tabName;
				tempUpdateSql = new StringBuffer();
				tempWhereSql = null;
				tempInsertSql = new StringBuffer();
				tempValuesSql = new StringBuffer();
				
				//判断是否新增操作
				if("1".equals(pkFlag) && ((dataNew == null || "".equals(dataNew)) || ((dataNew != null && !"".equals(dataNew))
						&& (dataOld == null || "".equals(dataOld))))){
					insertFlag = true;
				}else{
					insertFlag = false;
				}
				if(insertFlag){
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
				}else{
					if("2".equals(fieldType)){
						tempUpdateSql.append(" UPDATE "+ tabName + " SET "+ colName + "=TO_DATE('" + dataNew + "','yyyy-MM-dd')");
					}else{
						tempUpdateSql.append(" UPDATE "+ tabName + " SET "+ colName + "='" + dataNew + "'");
					}
				}
			}else{
				if(insertFlag){
					if("2".equals(fieldType)){
						tempInsertSql.append(" ,"+colName);
						tempValuesSql.append(" ,TO_DATE('" + dataNew + "','yyyy-MM-dd')");
					}else{
						tempInsertSql.append(" ,"+colName);
						tempValuesSql.append(" ,'"+ dataNew +"'");
					}
				}else{
					if("2".equals(fieldType)){
						tempUpdateSql.append(" ,"+ colName + "=TO_DATE('" + dataNew + "','yyyy-MM-dd')");
					}else{
						tempUpdateSql.append(" ,"+ colName + "='" + dataNew + "'");
					}
				}
			}
			//添加update where条件sql
			if(!insertFlag && "1".equals(pkFlag)){
				tempWhereSql = " WHERE "+ colName + "='" + dataNew + "'";
			}
			//判断是否最后一行操作右是增加相应的sql
			if(totalCount == 0){
				if(tempUpdateSql != null && !"".equals(tempUpdateSql.toString())){
					if(tempWhereSql == null){
						tempWhereSql = " WHERE CUST_ID='" + custId + "'";
					}
					SQLS.add(tempUpdateSql.toString() + tempWhereSql);
					log.info("添加批量UPDATE SQL:["+tempUpdateSql.toString() + tempWhereSql+"]");
				}
				if(tempInsertSql != null && tempValuesSql != null && !"".equals(tempInsertSql.toString())
						&& !"".equals(tempValuesSql.toString())){
					SQLS.add(tempInsertSql.toString() +") "+ tempValuesSql.toString()+")");
					log.info("添加批量INSERT SQL:["+tempInsertSql.toString() +") "+ tempValuesSql.toString()+") ]");
				}
			}
		}
		//批量处理存在表别名的SQL语句
		for(int i=0,len =SQLS.size();i<len;i++){
			SQLS.set(i, StringUtils.replace(SQLS.get(i), "ACRM_F_CI_GRADE1", "ACRM_F_CI_GRADE"));
			SQLS.set(i, StringUtils.replace(SQLS.get(i), "ACRM_F_CI_CUST_IDENTIFIER1", "ACRM_F_CI_CUST_IDENTIFIER"));
			SQLS.set(i, StringUtils.replace(SQLS.get(i), "ACRM_F_CI_CUST_IDENTIFIER2", "ACRM_F_CI_CUST_IDENTIFIER"));
		}
		if(SQLS.size() > 0){
			executeBatch(vo);
		}
	}
	
	/**
	 * 单独处理证件新增
	 * @param vo
	 * @throws SQLException
	 */
	public void identifierTrans(EVO vo,String custId,String updateFlag,AuthUser auth) throws Exception{
		//此SQL必须按UPDATE_TABLE项排序，否则会存在逻辑错误---TranCrmToEcif
		SQL = "SELECT T.UPDATE_TABLE FROM OCRM_F_CI_CUSTINFO_UPHIS T WHERE T.CUST_ID = '"+custId+"' AND T.UPDATE_FLAG = '"+updateFlag+"' AND (T.UPDATE_BE_CONT IS NULL OR T.UPDATE_BE_CONT = '') AND (T.UPDATE_AF_CONT IS NULL OR T.UPDATE_AF_CONT = '') "
				+ " AND (T.UPDATE_TABLE = 'ACRM_F_CI_CUST_IDENTIFIER' OR T.UPDATE_TABLE = 'ACRM_F_CI_CUST_IDENTIFIER1'OR T.UPDATE_TABLE = 'ACRM_F_CI_CUST_IDENTIFIER2') AND T.UPDATE_ITEM_EN = 'IDENT_ID' AND T.UPDATE_TABLE_ID = '1'";
		Result result = querySQL(vo);
		for (SortedMap<?, ?> item : result.getRows()) {
			String updateTable = (String) item.get("UPDATE_TABLE");
			//调用交易接口
			SQL = "select update_af_cont,update_item_en,update_table,update_user from OCRM_F_CI_CUSTINFO_UPHIS where cust_id='" + custId
					+ "' and update_flag='" + updateFlag + "' and UPDATE_TABLE = '"+ updateTable +"' ORDER BY UPDATE_TABLE";
			String responseXml = TranCrmToEcif(custId, updateFlag, vo);
			boolean responseFlag = doResXms(responseXml);
			//判断如果是新增调用操、则获取返回的主键信息,
			String pk_xx = getResXms(responseXml);
			
			//增加处理返回报文、判断交易返回结果,用于CRM业务更新时使用
			if(responseFlag){
				SQL = "UPDATE OCRM_F_CI_CUSTINFO_UPHIS V SET V.UPDATE_AF_CONT = '"+pk_xx+"' WHERE V.CUST_ID='" + custId + "' AND V.UPDATE_FLAG='" + updateFlag + "' AND V.UPDATE_TABLE = '"+ updateTable +"' AND V.UPDATE_ITEM_EN = 'IDENT_ID'";
				log.info("存储ECIF返回证件ID:----->>>>>[" + SQL + "]");
				execteSQL(vo);
			}else{
				throw new BizException(1,0,"0000","Warning-168：数据信息同步失败，请及时联系IT部门！");
			}
		}
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
			if(root.element("ResponseBody").element("identId")!=null){
				id = root.element("ResponseBody").element("identId").getTextTrim();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return id;
	}
}
