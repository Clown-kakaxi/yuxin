package com.ytec.mdm.service.svc.comb;

import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
public class vbVerifyCustInfo implements IEcifBizLogic {

	protected static Logger log = LoggerFactory
			.getLogger(vbVerifyCustInfo.class);
	private JPABaseDAO baseDAO;

	@Override
	public void process(EcifData ecifData) throws Exception {
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		Element body = ecifData.getBodyNode(); // 获取节点
		String txCode = body.element("txCode").getTextTrim(); // 获取交易编码
		String txName = body.element("txName").getTextTrim();// 交易名称
		String authType = body.element("authType").getTextTrim();// 权限控制类型
		String authCode = body.element("authCode").getTextTrim();// 权限控制代码
		String coreNo = body.element("coreNo").getTextTrim();// 核心客户号
		String custName = body.element("custName").getTextTrim();// 证件户名
		String identType = body.element("identType").getTextTrim();// 证件类型
		String identNo = body.element("identNo").getTextTrim();// 证件号码
		String mobile = body.element("mobile").getTextTrim();// 联系电话

		Element responseEle = DocumentHelper
				.createElement(MdmConstants.MSG_RESPONSE_BODY);

		if (StringUtils.isEmpty(txCode) || StringUtils.isEmpty(custName)
				|| StringUtils.isEmpty(identType)
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
				InputStream inputStream = vbVerifyCustInfo.class
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
				CallableStatement cstmt = null;
				String procedure = "{call SP_VB_IF_EXISTS(?,?,?,?,?)}";
				cstmt = conn.prepareCall(procedure);
				
				cstmt.setString(1, custName);
				cstmt.setString(2, identType);
				cstmt.setString(3, identNo);
				cstmt.setString(4, coreNo);
				//cstmt.setString(5, mobile);
				cstmt.registerOutParameter(5, java.sql.Types.VARCHAR);
				cstmt.executeUpdate();
				String code = cstmt.getString(5);
				String msg = "";
				switch (Integer.parseInt(code)) {
				case 000000:
					msg="客户验证成功";
					break;
				case 600703:
					msg="客户验证不通过";
					break;
				default:
					break;
				}
				ecifData.setStatus(code, msg);
				ecifData.setSuccess(true);
//				if(code.equals("600711")){
//					msg="客户户名不正确";
//					ecifData.setStatus(code, msg);
//				}else if(code.equals("600299")){
//					msg="证件校验不通过";
//					ecifData.setStatus(code, msg);
//				}else if(code.equals("600703")){
//					msg="客户不存在";
//					ecifData.setStatus(code, msg);	
//				}else {//输出code核心客户号
//					ecifData.setRepNode(responseEle);
//					ecifData.getRepNode().addElement("coreNo").setText(code);
//					String result="000000";
//					msg="客户验证成功";
//					ecifData.setStatus(result, msg);	
//				} 
				//ecifData.setStatus(code, msg);
				//ecifData.setSuccess(true);
			} catch (SQLException e) {
				e.printStackTrace();
				String msg = "调用存储过程失败";
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
