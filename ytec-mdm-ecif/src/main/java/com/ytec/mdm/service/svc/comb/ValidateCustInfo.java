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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
public class ValidateCustInfo implements IEcifBizLogic {

	protected static Logger log = LoggerFactory
			.getLogger(ValidateCustInfo.class);
	private JPABaseDAO baseDAO;
	private static String regEx = "[\u4e00-\u9fa5]";
	private static Pattern pat = Pattern.compile(regEx);
	
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
		String identTypeChannel=body.element("identTypeChannel").getTextTrim();//֤��������Դ����,1:֧������2��:�Ƹ�ͨ,3:��Ǯ
		
		String newCustName=exChange(custName); //У�飬��������������򷵻��������������Ӣ������ȫ����ɴ�д
		String idType=exChange(identType);     //У�飬֤�����;���Ӣ����ĸ�ģ���ɴ�д
		String idNo=exChange(identNo);         //У�飬֤�����;���Ӣ����ĸ�ģ���ɴ�д
		
		if (StringUtils.isEmpty(txCode) || StringUtils.isEmpty(coreNo)
				|| StringUtils.isEmpty(custName)
				|| StringUtils.isEmpty(identType)
				|| StringUtils.isEmpty(identNo) || StringUtils.isEmpty(mobile)) {
			String msg = "��Ϣ����������������ڵ���txCode,custMgr,currentPage������Ϊ��";
			log.error(msg);
			ecifData.setStatus(
					ErrorCode.ERR_XML_FILTER_VALUE_IS_NULL.getCode(), msg);
			return;
		}
		
//		String sql="select t.ECIF_IDENT_TYPE from M_CI_IDENT_TYPE_MAPPING t where t.SRC_SYS_IDENT_TYPE='"+idType+"' and t.SRC_SYS_CHANNEL='"+identTypeChannel+"'";
//		List<Object> ecifIdentTypes=baseDAO.findByNativeSQLWithIndexParam(sql, null);
//		StringBuffer sb = new StringBuffer();
//		if(ecifIdentTypes != null && ecifIdentTypes.size() > 0){
//			if(ecifIdentTypes.size() == 1){//ӳ���Ecifϵͳ��֤������ֻ��һ��
//				for(int i=0;i<ecifIdentTypes.size();i++){
//					String IDType=(String)ecifIdentTypes.get(i);
//					sb.append(IDType);
//				}
//			}else {//ӳ���Ecifϵͳ��֤�����;��������
//				for(int i=0;i<ecifIdentTypes.size();i++){
//				
//					String IDType=(String)ecifIdentTypes.get(i);
//					sb.append(IDType);
//					sb.append("_");
//				}
//				sb.deleteCharAt(sb.length()-1);//ȥ�����һ��'_'
//			}
//		}
//		String newIdentType=sb.toString();//ӳ����֤������
		
//		log.info("----------"+newIdentType);
		Element responseEle = DocumentHelper
				.createElement(MdmConstants.MSG_RESPONSE_BODY);

		try {
			
			callProcedure(coreNo,newCustName,idType,idNo,mobile,ecifData,txCode);
		
		} catch (Exception e) {
			
			log.error("���ô洢����ʧ��", e);
		}
	}
	public synchronized String callProcedure(String coreNo,String newCustName,String newIdentType,
			String idNo,String mobile,EcifData ecifData,String txCode){
		try {
			List<Object> para = new ArrayList<Object>();
			String flag = "7";
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
			Properties properties = new Properties();
			try {
				InputStream inputStream = ValidateCustInfo.class
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
				String procedure = "{call SP_ALIPAY_IF_EXISTS(?,?,?,?,?,?)}";
				cstmt = conn.prepareCall(procedure);
				cstmt.setString(1, coreNo);
				cstmt.setString(2, newCustName);
				cstmt.setString(3, newIdentType);
				cstmt.setString(4, idNo);
				cstmt.setString(5, mobile);
				cstmt.registerOutParameter(6, java.sql.Types.VARCHAR);
				cstmt.executeUpdate();
				
				String code = cstmt.getString(6);
				String msg = "";
				switch (Integer.parseInt(code)) {
				case 600711:
					msg="�ͻ���������ȷ";
					break;
				case 600299:
					msg="֤����У�鲻ͨ��";
					break;
				case 600501:
					msg="�ֻ���У�鲻ͨ��";
					break;
				case 000000:
					msg="�ͻ���֤�ɹ�";
					break;
				case 600703:
					msg="�ͻ�������";
					break;
				default:
					break;
				}
				ecifData.setStatus(code, msg);
				ecifData.setSuccess(true);
				System.out.println(code);
				conn.commit();//�ύ����
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
			//return null;
		}
		  return null;
	}
	 public String exChange(String str){  
		    StringBuffer sb = new StringBuffer();  
		    boolean isChinese =isContainsChinese(str);
		    if(str!=null){ 
		    	if(isChinese){
		    		
		    		return str;
		    	}
		    	if(!isChinese){
		    		for(int i=0;i<str.length();i++){  
		        		char c = str.charAt(i);  
		            	if(Character.isLowerCase(c)){  
		            		sb.append(Character.toUpperCase(c));
		            	}else{
		            		sb.append(c);
		            	}
		            	
		        	} 
		    		return sb.toString();
		    		
		   		}    
		   }
			return null;
		}  
	    
	public boolean isContainsChinese(String str){
		   Matcher matcher = pat.matcher(str);
		   boolean flag = false;
		   if (matcher.find()){
			   flag = true;
		   }
		   	   return flag;
		   }

}
