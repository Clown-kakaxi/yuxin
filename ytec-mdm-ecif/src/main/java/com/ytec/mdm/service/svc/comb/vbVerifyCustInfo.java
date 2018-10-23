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
		Element body = ecifData.getBodyNode(); // ��ȡ�ڵ�
		String txCode = body.element("txCode").getTextTrim(); // ��ȡ���ױ���
		String txName = body.element("txName").getTextTrim();// ��������
		String authType = body.element("authType").getTextTrim();// Ȩ�޿�������
		String authCode = body.element("authCode").getTextTrim();// Ȩ�޿��ƴ���
		String coreNo = body.element("coreNo").getTextTrim();// ���Ŀͻ���
		String custName = body.element("custName").getTextTrim();// ֤������
		String identType = body.element("identType").getTextTrim();// ֤������
		String identNo = body.element("identNo").getTextTrim();// ֤������
		String mobile = body.element("mobile").getTextTrim();// ��ϵ�绰

		Element responseEle = DocumentHelper
				.createElement(MdmConstants.MSG_RESPONSE_BODY);

		if (StringUtils.isEmpty(txCode) || StringUtils.isEmpty(custName)
				|| StringUtils.isEmpty(identType)
				|| StringUtils.isEmpty(identNo)) {
			String msg = "��Ϣ����������������ڵ���txCode,custMgr,currentPage������Ϊ��";
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
				inputStream.close(); // �ر���
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
					msg="�ͻ���֤�ɹ�";
					break;
				case 600703:
					msg="�ͻ���֤��ͨ��";
					break;
				default:
					break;
				}
				ecifData.setStatus(code, msg);
				ecifData.setSuccess(true);
//				if(code.equals("600711")){
//					msg="�ͻ���������ȷ";
//					ecifData.setStatus(code, msg);
//				}else if(code.equals("600299")){
//					msg="֤��У�鲻ͨ��";
//					ecifData.setStatus(code, msg);
//				}else if(code.equals("600703")){
//					msg="�ͻ�������";
//					ecifData.setStatus(code, msg);	
//				}else {//���code���Ŀͻ���
//					ecifData.setRepNode(responseEle);
//					ecifData.getRepNode().addElement("coreNo").setText(code);
//					String result="000000";
//					msg="�ͻ���֤�ɹ�";
//					ecifData.setStatus(result, msg);	
//				} 
				//ecifData.setStatus(code, msg);
				//ecifData.setSuccess(true);
			} catch (SQLException e) {
				e.printStackTrace();
				String msg = "���ô洢����ʧ��";
				log.error("{},{}", msg+"���ױ����ǣ�"+txCode);
				ecifData.setSuccess(false);
			} finally {
				conn.close();
			}

		} catch (Exception e) {
			String msg;
			if (e instanceof ParseException) {
				msg = String
						.format("����/ʱ��(%s)��ʽ�����Ϲ淶,ת������",
								e.getLocalizedMessage()
										.substring(
												e.getLocalizedMessage()
														.indexOf('"'))
										.replace("\"", ""));
				ecifData.setStatus(ErrorCode.ERR_CONVERT_ERROR.getCode(), msg);
				log.error("{},{}", msg + "���ױ����ǣ�" + txCode, e);
			} else if (e instanceof NumberFormatException) {
				msg = String
						.format("��ֵ(%s)��ʽ�����Ϲ淶,ת������",
								e.getLocalizedMessage()
										.substring(
												e.getLocalizedMessage()
														.indexOf('"'))
										.replace("\"", ""));
				ecifData.setStatus(ErrorCode.ERR_CONVERT_ERROR.getCode(), msg);
				log.error("{},{}", msg + "���ױ����ǣ�" + txCode, e);
			} else {
				msg = "��ѯ����ʧ��";
				log.error("{},{}", msg + "���ױ����ǣ�" + txCode, e);
				ecifData.setStatus(ErrorCode.ERR_DB_OPER_ERROR.getCode(), msg);
			}
			ecifData.setSuccess(false);
			return;
		}

	}

}
