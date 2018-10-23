package com.ytec.mdm.service.svc.atomic;

import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.integration.transaction.facade.IEcifBizLogic;

@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class QueryCustState implements IEcifBizLogic{
	
	protected static Logger log = LoggerFactory
			.getLogger(QueryCustState.class);
	private JPABaseDAO baseDAO;

	@Override
	public void process(EcifData ecifData) throws Exception {
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		Element body = ecifData.getBodyNode(); // 获取节点
		String txCode = body.element("txCode").getTextTrim(); // 获取交易编码
		String txName = body.element("txName").getTextTrim();// 交易名称
		String authType = body.element("authType").getTextTrim();// 权限控制类型
		String authCode = body.element("authCode").getTextTrim();// 权限控制代码
		//String custId = body.element("custId").getTextTrim();// 客户号
		String identType = body.element("identType").getTextTrim();// 证件类型
		String identNo = body.element("identNo").getTextTrim();// 证件号码
		Element responseEle = DocumentHelper
				.createElement(MdmConstants.MSG_RESPONSE_BODY);

//		if (StringUtils.isEmpty(txCode) || StringUtils.isEmpty(custId)) {
//			String msg = "信息不完整，报文请求节点中txCode,custMgr,currentPage不允许为空";
//			log.error(msg);
//			ecifData.setStatus(
//					ErrorCode.ERR_XML_FILTER_VALUE_IS_NULL.getCode(), msg);
//			return;
//		}
		if (StringUtils.isEmpty(txCode) || StringUtils.isEmpty(identType)
				|| StringUtils.isEmpty(identNo)) {
			String msg = "信息不完整，报文请求节点中txCode,custMgr,currentPage不允许为空";
			log.error(msg);
			ecifData.setStatus(
					ErrorCode.ERR_XML_FILTER_VALUE_IS_NULL.getCode(), msg);
			return;
		}
		try {
			List<Object> para = new ArrayList<Object>();
			String flag = "7";
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
			Properties properties = new Properties();
			try {
				InputStream inputStream = QueryCustState.class
						.getResourceAsStream("/jdbc.properties");
				properties.load(inputStream);
				inputStream.close(); // 关闭流
			} catch (IOException e) {
				e.printStackTrace();
			}
			String url = properties.getProperty("jdbc.url");
			String user = properties.getProperty("jdbc.username");
			String password = properties.getProperty("jdbc.password");
			Connection conn = DriverManager.getConnection(url, user, password);
			try {
				Statement  stmt = null;
				ResultSet res = null;  
				stmt = conn.createStatement();  
				String sql1="SELECT T.CUST_ID FROM ACRM_F_CI_CUST_IDENTIFIER T WHERE T.IDENT_TYPE = '" 
				            +identType
				            +"' and T.IDENT_NO = '"
				            +identNo
				            +"'";
				ResultSet result=stmt.executeQuery(sql1);
				String custId="";
				while(result.next()){
					 custId=result.getString("CUST_ID");
					 break;
				}
				String instancePre="CI_"+custId;
				String sql="SELECT WM_CONCAT(DISTINCT A.USER_NAME||'['||T.AUTHOR||']') AS AUTHOR FROM WF_WORKLIST T LEFT JOIN ADMIN_AUTH_ACCOUNT A ON A.ACCOUNT_NAME = T.AUTHOR where t.WFSTATUS <> '3' and t.instanceid like '%"
						+ instancePre
						+ "%'"
						+ " union  "
						+ " select WM_CONCAT(DISTINCT ac.USER_NAME || '[' || his.update_user || ']') AS AUTHOR  from OCRM_F_CI_CALLCENTER_UPHIS  his "
						+ " left join ADMIN_AUTH_ACCOUNT ac on his.update_user=ac.account_name "
						+ " where his.cust_id=substr('"+instancePre+"',instr ('"+instancePre+"','_')+1) and his.appr_flag='0' ";
		        res = stmt.executeQuery(sql);
		        String AUTHOR="";
		        String msg = "";
		        String code="";
		        while(res.next())  
	            {  
		        	AUTHOR= res.getString("AUTHOR");  
		        	break;
	            }
		        if(!"".equals(AUTHOR)&&AUTHOR!=null){
		        	code="000001";
		        	msg="客户记录被锁定，操作员"+AUTHOR;
		        }else{
		        	code="000000";
		        	msg="客户状态正常,可修改";
		        }
				
				ecifData.setStatus(code, msg);
				ecifData.setSuccess(true);
				System.out.println(code);
			} catch (SQLException e) {
				e.printStackTrace();
				String msg = "客户状态查询失败";
				log.error("{},{}", msg+"交易编码是："+txCode);
				ecifData.setSuccess(false);
			} finally {
				conn.close();
			}

		} catch (Exception e) {
			String msg;
			if (e instanceof ParseException) {
				msg = String
						.format("日期/时间(%s)格式不符合规范,转换错误",
								e.getLocalizedMessage()
										.substring(
												e.getLocalizedMessage()
														.indexOf('"'))
										.replace("\"", ""));
				ecifData.setStatus(ErrorCode.ERR_CONVERT_ERROR.getCode(), msg);
				log.error("{},{}", msg + "交易编码是：" + txCode, e);
			} else if (e instanceof NumberFormatException) {
				msg = String
						.format("数值(%s)格式不符合规范,转换错误",
								e.getLocalizedMessage()
										.substring(
												e.getLocalizedMessage()
														.indexOf('"'))
										.replace("\"", ""));
				ecifData.setStatus(ErrorCode.ERR_CONVERT_ERROR.getCode(), msg);
				log.error("{},{}", msg + "交易编码是：" + txCode, e);
			} else {
				msg = "查询数据失败";
				log.error("{},{}", msg + "交易编码是：" + txCode, e);
				ecifData.setStatus(ErrorCode.ERR_DB_OPER_ERROR.getCode(), msg);
			}
			ecifData.setSuccess(false);
			return;
		}

	}

}
