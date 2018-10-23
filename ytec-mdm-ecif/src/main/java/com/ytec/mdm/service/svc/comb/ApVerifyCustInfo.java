package com.ytec.mdm.service.svc.comb;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.Error;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.integration.transaction.facade.IEcifBizLogic;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ApVerifyCustInfo
  implements IEcifBizLogic
{
  protected static Logger log = LoggerFactory.getLogger(ApVerifyCustInfo.class);
  private JPABaseDAO baseDAO;
  private static String regEx = "[一-龥]";
  private static Pattern pat = Pattern.compile(regEx);

  public void process(EcifData ecifData) throws Exception
  {
    this.baseDAO = ((JPABaseDAO)SpringContextUtils.getBean("baseDAO"));
    Element body = ecifData.getBodyNode();
    String txCode = body.element("txCode").getTextTrim();
    String txName = body.element("txName").getTextTrim();
    String authType = body.element("authType").getTextTrim();
    String authCode = body.element("authCode").getTextTrim();
    String coreNo = body.element("coreNo").getTextTrim();
    String custName = body.element("custName").getTextTrim();
    String custNameFlag = body.element("custNameFlag").getTextTrim();
    String identType = body.element("identType").getTextTrim();
    String identTypeFlag = body.element("identTypeFlag").getTextTrim();
    String identNo = body.element("identNo").getTextTrim();
    String identNoFlag = body.element("identNoFlag").getTextTrim();
    String mobile = body.element("mobile").getTextTrim();
    String mobileFlag = body.element("mobileFlag").getTextTrim();

    String newCustName = exChange(custName);
    String idType = exChange(identType);
    String idNo = exChange(identNo);

    if ((StringUtils.isEmpty(txCode)) || (StringUtils.isEmpty(coreNo))) {
      String msg = "信息不完整，报文请求节点txCode,coreNo不允许空";
      log.error(msg);
      ecifData.setStatus(
        ErrorCode.ERR_XML_FILTER_VALUE_IS_NULL.getCode(), msg);
      return;
    }
    String newIdentType = null;
    if (idType != null) {
      String sql = "select t.ECIF_IDENT_TYPE from M_CI_IDENT_TYPE_MAPPING t where t.SRC_SYS_IDENT_TYPE='" + idType + "'";
      List ecifIdentTypes = this.baseDAO.findByNativeSQLWithIndexParam(sql, null);
      StringBuffer sb = new StringBuffer();
      if ((ecifIdentTypes != null) && (ecifIdentTypes.size() > 0)) {
        if (ecifIdentTypes.size() == 1) {
          for (int i = 0; i < ecifIdentTypes.size(); i++) {
            String IDType = (String)ecifIdentTypes.get(i);
            sb.append(IDType);
          }
        } else {
          for (int i = 0; i < ecifIdentTypes.size(); i++)
          {
            String IDType = (String)ecifIdentTypes.get(i);
            sb.append(IDType);
            sb.append("_");
          }
          sb.deleteCharAt(sb.length() - 1);
        }
      }
      newIdentType = sb.toString();
    }

    try
    {
      Map paraMap = new HashMap();
      paraMap.put("coreNo", coreNo);
      paraMap.put("newCustName", newCustName);
      paraMap.put("flag1", custNameFlag);
      paraMap.put("newIdentType", newIdentType);
      paraMap.put("flag2", identTypeFlag);
      paraMap.put("identNo", idNo);
      paraMap.put("flag3", identNoFlag);
      paraMap.put("mobile", mobile);
      paraMap.put("flag4", mobileFlag);

      callProcedure(paraMap, ecifData, txCode);
    }
    catch (Exception e) {
      log.error("调用存储过程失败", e);
    }
  }

  public synchronized String callProcedure(Map paraMap, EcifData ecifData, String txCode) {
    try { Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
      Properties properties = new Properties();
      try {
        InputStream inputStream = ApVerifyCustInfo.class
          .getResourceAsStream("/jdbc.properties");
        properties.load(inputStream);
        inputStream.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
      String url = properties.getProperty("jdbc.url");
      String user = properties.getProperty("jdbc.username");
      String password = properties.getProperty("jdbc.password");
      Connection conn = DriverManager.getConnection(url, user, password);
      try {
        CallableStatement cstmt = null;
        String procedure = "{call SP_ALIPAY_IF_EXISTS_APPLY(?,?,?,?,?,?,?,?,?,?)}";
        cstmt = conn.prepareCall(procedure);
        cstmt.setString(1, (String)paraMap.get("coreNo"));
        cstmt.setString(2, paraMap.get("newCustName") == null ? "" : (String)paraMap.get("newCustName"));
        cstmt.setString(3, (String)paraMap.get("flag1"));
        cstmt.setString(4, paraMap.get("newIdentType") == null ? "" : (String)paraMap.get("newIdentType"));
        cstmt.setString(5, (String)paraMap.get("flag2"));
        cstmt.setString(6, paraMap.get("identNo") == null ? "" : (String)paraMap.get("identNo"));
        cstmt.setString(7, (String)paraMap.get("flag3"));
        cstmt.setString(8, paraMap.get("mobile") == null ? "" : (String)paraMap.get("mobile"));
        cstmt.setString(9, (String)paraMap.get("flag4"));
        cstmt.registerOutParameter(10, 12);
        cstmt.executeUpdate();
        String result = cstmt.getString(10);
        String msg = "";
        switch (Integer.parseInt(result)) {
        case 600711:
          msg = "客户名称不匹配";
          break;
        case 600298:
          msg = "证件类型检验不通过";
          break;
        case 600299:
          msg = "证件号校验不通过";
          break;
        case 600501:
          msg = "手机号不匹配";
          break;
        case 600502:
        	msg = "手机号不存在";
        	break;
        case 0:
          msg = "客户验证成功";
          break;
        case 600703:
          msg = "客户不存在";
          break;
        }

        Element responseEle = 
          DocumentHelper.createElement(MdmConstants.MSG_RESPONSE_BODY);
        Element mobileEle = responseEle.addElement("mobile");
        String flag = (String)paraMap.get("flag4");

        if ("0".equals(flag)) {
          if ("000000".equals(result))
          {
            CallableStatement tempCstmt = null;
            String tempProcedure = "{call SP_APPLY_PAY_GETPHONE(?,?)}";
            tempCstmt = conn.prepareCall(tempProcedure);
            tempCstmt.setString(1, (String)paraMap.get("coreNo"));
            tempCstmt.registerOutParameter(2, 12);
            tempCstmt.executeUpdate();
            String phoneNum = tempCstmt.getString(2);
            mobileEle.setText(phoneNum == null ? "" : phoneNum);
            ecifData.setRepNode(responseEle);
          } else {
            mobileEle.setText("");
            ecifData.setRepNode(responseEle);
          }
        } else if ("1".equals(flag)) {
          if ("000000".equals(result)) {
            mobileEle.setText((String)paraMap.get("mobile"));
            ecifData.setRepNode(responseEle);
          } else {
            mobileEle.setText("");
            ecifData.setRepNode(responseEle);
          }
        }
        ecifData.setStatus(result, msg);
        ecifData.setSuccess(true);
        System.out.println(result);
        conn.commit();
      } catch (SQLException e) {
        e.printStackTrace();
        String msg = "调用存储过程失败";
        log.error("{},{}", msg + "交易编码是：" + txCode);
        ecifData.setSuccess(false);
      } finally {
        conn.close();
      }
    } catch (Exception e)
    {
      if ((e instanceof ParseException)) {
        String msg = 
          String.format("日期/时间(%s)格式不符合规范,转换错误", new Object[] { 
          e.getLocalizedMessage()
          .substring(
          e.getLocalizedMessage()
          .indexOf(34))
          .replace("\"", "") });
        ecifData.setStatus(ErrorCode.ERR_CONVERT_ERROR.getCode(), msg);
        log.error("{},{}", msg + "交易编码是：" + txCode, e);
      } else if ((e instanceof NumberFormatException)) {
        String msg = 
          String.format("数值(%s)格式不符合规范,转换错误", new Object[] { 
          e.getLocalizedMessage()
          .substring(
          e.getLocalizedMessage()
          .indexOf(34))
          .replace("\"", "") });
        ecifData.setStatus(ErrorCode.ERR_CONVERT_ERROR.getCode(), msg);
        log.error("{},{}", msg + "交易编码是：" + txCode, e);
      } else {
        String msg = "查询数据失败";
        log.error("{},{}", msg + "交易编码是：" + txCode, e);
        ecifData.setStatus(ErrorCode.ERR_DB_OPER_ERROR.getCode(), msg);
      }
      ecifData.setSuccess(false);
    }
    return null;
  }

  public String exChange(String str) {
    StringBuffer sb = new StringBuffer();
    boolean isChinese = isContainsChinese(str);
    if (str != null) {
      if (isChinese) {
        return str;
      }
      if (!isChinese) {
        for (int i = 0; i < str.length(); i++) {
          char c = str.charAt(i);
          if (Character.isLowerCase(c))
            sb.append(Character.toUpperCase(c));
          else {
            sb.append(c);
          }
        }
        return sb.toString();
      }
    }
    return null;
  }

  public boolean isContainsChinese(String str) {
    Matcher matcher = pat.matcher(str);
    boolean flag = false;
    if (matcher.find()) {
      flag = true;
    }
    return flag;
  }

  public Object queryCustomerEntiry(String coreNo, String tableName)
  {
    StringBuffer jql = new StringBuffer();
    Map paramMap = new HashMap();

    jql.append("FROM " + tableName + " a");

    jql.append(" WHERE 1=1");
    jql.append(" AND a.coreNo =:coreNo");

    paramMap.put("coreNo", coreNo);
    List result = null;
    result = this.baseDAO.findWithNameParm(jql.toString(), paramMap);
    if ((result != null) && (result.size() > 0)) return result.get(0);

    return null;
  }
}