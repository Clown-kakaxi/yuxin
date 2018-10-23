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
		String identTypeChannel=body.element("identTypeChannel").getTextTrim();//证件类型来源渠道,1:支付宝，2：:财付通,3:快钱
		
		String newCustName=exChange(custName); //校验，如果是中文名，则返回中文名；如果是英文名，全部变成大写
		String idType=exChange(identType);     //校验，证件类型具有英文字母的，变成大写
		String idNo=exChange(identNo);         //校验，证件类型具有英文字母的，变成大写
		
		if (StringUtils.isEmpty(txCode) || StringUtils.isEmpty(coreNo)
				|| StringUtils.isEmpty(custName)
				|| StringUtils.isEmpty(identType)
				|| StringUtils.isEmpty(identNo) || StringUtils.isEmpty(mobile)) {
			String msg = "信息不完整，报文请求节点中txCode,custMgr,currentPage不允许为空";
			log.error(msg);
			ecifData.setStatus(
					ErrorCode.ERR_XML_FILTER_VALUE_IS_NULL.getCode(), msg);
			return;
		}
		
//		String sql="select t.ECIF_IDENT_TYPE from M_CI_IDENT_TYPE_MAPPING t where t.SRC_SYS_IDENT_TYPE='"+idType+"' and t.SRC_SYS_CHANNEL='"+identTypeChannel+"'";
//		List<Object> ecifIdentTypes=baseDAO.findByNativeSQLWithIndexParam(sql, null);
//		StringBuffer sb = new StringBuffer();
//		if(ecifIdentTypes != null && ecifIdentTypes.size() > 0){
//			if(ecifIdentTypes.size() == 1){//映射成Ecif系统的证件类型只有一种
//				for(int i=0;i<ecifIdentTypes.size();i++){
//					String IDType=(String)ecifIdentTypes.get(i);
//					sb.append(IDType);
//				}
//			}else {//映射成Ecif系统的证件类型具有许多种
//				for(int i=0;i<ecifIdentTypes.size();i++){
//				
//					String IDType=(String)ecifIdentTypes.get(i);
//					sb.append(IDType);
//					sb.append("_");
//				}
//				sb.deleteCharAt(sb.length()-1);//去除最后一个'_'
//			}
//		}
//		String newIdentType=sb.toString();//映射后的证件类型
		
//		log.info("----------"+newIdentType);
		Element responseEle = DocumentHelper
				.createElement(MdmConstants.MSG_RESPONSE_BODY);

		try {
			
			callProcedure(coreNo,newCustName,idType,idNo,mobile,ecifData,txCode);
		
		} catch (Exception e) {
			
			log.error("调用存储过程失败", e);
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
					msg="客户户名不正确";
					break;
				case 600299:
					msg="证件号校验不通过";
					break;
				case 600501:
					msg="手机号校验不通过";
					break;
				case 000000:
					msg="客户验证成功";
					break;
				case 600703:
					msg="客户不存在";
					break;
				default:
					break;
				}
				ecifData.setStatus(code, msg);
				ecifData.setSuccess(true);
				System.out.println(code);
				conn.commit();//提交事务
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
