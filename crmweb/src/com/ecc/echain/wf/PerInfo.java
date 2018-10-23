package com.ecc.echain.wf;

import com.ecc.echain.workflow.engine.EVO;
import com.ecc.echain.workflow.model.CommentVO;
import com.ibm.icu.text.SimpleDateFormat;
import com.yuchengtech.bcrm.echain.EChainCallbackCommon;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;
import com.yuchengtech.trans.bo.RequestHeader;
import com.yuchengtech.trans.client.TransClient;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;

import javax.servlet.jsp.jstl.sql.Result;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class PerInfo extends EChainCallbackCommon
{
  private static Logger log = Logger.getLogger(PerInfo.class);
  
  
  public  void endCB(EVO vo){
	  try{
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split("_");
			SQL =  "update ocrm_f_ci_custinfo_uphis t set  t.appr_flag= '3'  where t.UPDATE_FLAG='"+instanceids[2]+"'";
			execteSQL(vo);
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("执行SQL出错");
		}
	  
  }

  public void endN(EVO vo)
  {
    try
    {
      AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      String instanceid = vo.getInstanceID();
      String custId = instanceid.split("_")[1];
      String updateFlag = instanceid.split("_")[2];
      this.SQL = ("update OCRM_F_CI_CUSTINFO_UPHIS V set v.appr_flag = '2',v.appr_user = '" + auth.getUserId() + "',v.appr_date = to_char(sysdate,'yyyy-MM-dd HH24:mi:ss') WHERE V.CUST_ID='" + custId + "' AND  V.UPDATE_FLAG='" + updateFlag + "'");
      log.info("执行SQL:");
      log.info("----->>>>>[" + this.SQL + "]");
      execteSQL(vo);
    } catch (SQLException e) {
      log.error("处理过程中发生SQL异常(错误):");
      log.error(e.getLocalizedMessage());
    } catch (Exception e) {
      log.error("处理过程中发现异常(错误):");
      log.error(e.getLocalizedMessage());
    }
  }

  public void endY(EVO vo)
  {
    try
    {
      CommentVO vo2= vo.getCommentVO();
      vo2.getCommentContent();
      AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      String instanceid = vo.getInstanceID();
      String[] instanceids = instanceid.split("_");
      String custId = instanceids[1];
      String updateFlag = instanceids[2];

      String responseXml = TranCrmToEcif(custId, updateFlag, vo);
      boolean responseFlag = doResXms(responseXml);

      if (responseFlag)
      {
        joinSql(vo);
        this.SQL = ("update OCRM_F_CI_CUSTINFO_UPHIS V set v.appr_flag = '1',v.appr_user = '" + auth.getUserId() + "',v.appr_date = to_char(sysdate,'yyyy-MM-dd HH24:mi:ss') WHERE V.CUST_ID='" + custId + "' AND  V.UPDATE_FLAG='" + updateFlag + "'");
        log.info("执行SQL:----->>>>>[" + this.SQL + "]");
        execteSQL(vo);
      } else {
        throw new BizException(1, 0, "0000", "Warning-168：数据信息同步失败，请及时联系IT部门！", new Object[0]);
      }
    }
    catch (BizException e) {
      throw e;
    } catch (Exception e) {
      log.error("处理过程中发现异常(错误):" + e.getMessage());
      throw new BizException(1, 0, "0000", "Warning-169：数据信息同步失败，请及时联系IT部门！", new Object[0]);
    }
  }

  public boolean doResXms(String xml)
    throws Exception
  {
    try
    {
      xml = xml.substring(8);
      Document doc = DocumentHelper.parseText(xml);
      Element root = doc.getRootElement();
      String TxStatCode = root.element("ResponseTail").element("TxStatCode").getTextTrim();
      if ((TxStatCode != null) && (!TxStatCode.trim().equals("")) && (TxStatCode.trim().equals("000000")))
        return true;
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  public String TranCrmToEcif(String cust_id, String update_flag, EVO vo)
    throws Exception
  {
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

    this.SQL = 
      ("select update_af_cont,update_item_en,update_table,update_user from OCRM_F_CI_CUSTINFO_UPHIS where cust_id='" + cust_id + 
      "' and update_flag='" + update_flag + "' ORDER BY UPDATE_TABLE");
    Result result = querySQL(vo);
    StringBuffer sb = new StringBuffer();
    sb.append("<RequestBody>");
    sb.append("<txCode>updatePerCustInfo</txCode>");
    sb.append("<txName>修改个人客户基本信息</txName>");
    sb.append("<authType>1</authType>");
    sb.append("<authCode>1010</authCode>");
    sb.append("<custNo>" + cust_id + "</custNo>");
    List list = new ArrayList();
    String updateUser = null;
    for (SortedMap item : result.getRows()) {
      list.add(item);
      updateUser = (String)item.get("update_user");
    }
    header.setTlrNo(updateUser);
    XmlTableUtil util = new XmlTableUtil();
    String reqXml = util.reqBodyXml(list, vo);
    sb.append(reqXml);
    sb.append("</RequestBody>");
    String Xml = new String(sb.toString().getBytes());
    String req = TransClient.process(header, Xml);
    return req;
  }

  public void joinSql(EVO vo)
    throws SQLException
  {
    String instanceid = vo.getInstanceID();
    String[] instanceids = instanceid.split("_");
    String custId = instanceids[1];
    String updateFlag = instanceids[2];
    this.SQLS = new ArrayList();

    this.SQL = (" SELECT * FROM OCRM_F_CI_CUSTINFO_UPHIS V WHERE V.CUST_ID='" + custId + "' AND  V.UPDATE_FLAG='" + updateFlag + "' AND V.UPDATE_TABLE NOT IN ('ACRM_F_CI_FINA_BUSI','ACRM_F_CI_PER_FAMILY','ACRM_F_CI_PER_KEYFLAG','ACRM_F_CI_PER_PREFERENCE') ORDER BY UPDATE_TABLE,UPDATE_TABLE_ID");
    Result result = querySQL(vo);

    boolean tableFlag = false;
    boolean insertFlag = false;
    String lastTable = null;

    StringBuffer tempUpdateSql = null;
    String tempWhereSql = null;
    StringBuffer tempInsertSql = null;
    StringBuffer tempValuesSql = null;
    int totalCount = result.getRowCount();
    for (SortedMap row : result.getRows()) {
      totalCount--;
      String dataOld = row.get("UPDATE_BE_CONT") != null ? (String)row.get("UPDATE_BE_CONT") : "";
      dataOld = StringEscapeUtils.escapeSql(dataOld);
      String dataNew = row.get("UPDATE_AF_CONT") != null ? (String)row.get("UPDATE_AF_CONT") : "";
      dataNew = StringEscapeUtils.escapeSql(dataNew);
      String colName = row.get("UPDATE_ITEM_EN") == null ? "" : String.valueOf(row.get("UPDATE_ITEM_EN"));
      String tabName = row.get("UPDATE_TABLE") == null ? "" : String.valueOf(row.get("UPDATE_TABLE"));
      String pkFlag = row.get("UPDATE_TABLE_ID") == null ? "" : String.valueOf(row.get("UPDATE_TABLE_ID"));
      String fieldType = row.get("FIELD_TYPE") == null ? "" : String.valueOf(row.get("FIELD_TYPE"));

      if ((tabName != null) && (!"".equals(tabName)) && (colName != null) && (!"".equals(colName)))
      {
        if ((lastTable != null) && (lastTable.equals(tabName)))
          tableFlag = true;
        else {
          tableFlag = false;
        }

        if (!tableFlag) {
          if ((tempUpdateSql != null) && (!"".equals(tempUpdateSql.toString()))) {
            if (tempWhereSql == null) {
              tempWhereSql = " WHERE CUST_ID='" + custId + "'";
            }
            this.SQLS.add(tempUpdateSql.toString() + tempWhereSql);
            log.info("添加批量UPDATE SQL:[" + tempUpdateSql.toString() + tempWhereSql + "]");
          }
          if ((tempInsertSql != null) && (tempValuesSql != null) && (!"".equals(tempInsertSql.toString())) && 
            (!"".equals(tempValuesSql.toString()))) {
            this.SQLS.add(tempInsertSql.toString() + ") " + tempValuesSql.toString() + ")");
            log.info("添加批量INSERT SQL:[" + tempInsertSql.toString() + ") " + tempValuesSql.toString() + ") ]");
          }

          lastTable = tabName;
          tempUpdateSql = new StringBuffer();
          tempWhereSql = null;
          tempInsertSql = new StringBuffer();
          tempValuesSql = new StringBuffer();

          if (("1".equals(pkFlag)) && ((dataNew == null) || ("".equals(dataNew)) || ((dataNew != null) && (!"".equals(dataNew)) && (
            (dataOld == null) || ("".equals(dataOld))))))
            insertFlag = true;
          else {
            insertFlag = false;
          }
          if (insertFlag) {
            if ((dataNew == null) || ("".equals(dataNew)))
            {
              if ((dataOld == null) || ("".equals(dataOld))) {
                if (!"ACRM_F_CI_PER_PREFERENCE".equals(tabName)) {
                  tempInsertSql.append(" INSERT INTO " + tabName + "(" + colName);
                  tempValuesSql.append(" VALUES (ID_SEQUENCE.NEXTVAL");
                }
              } else if (dataOld.indexOf("NEXTVAL") > -1) {
                tempInsertSql.append(" INSERT INTO " + tabName + "(" + colName);
                tempValuesSql.append(" VALUES (" + dataOld);
              } else {
                tempInsertSql.append(" INSERT INTO " + tabName + "(" + colName);
                tempValuesSql.append(" VALUES ('" + dataOld + "'");
              }
            } else {
              tempInsertSql.append(" INSERT INTO " + tabName + "(" + colName);
              tempValuesSql.append(" VALUES ('" + dataNew + "'");
            }
            if ("ACRM_F_CI_PER_PREFERENCE".equals(tabName)) {
              tempInsertSql.append(" INSERT INTO " + tabName + "(" + colName);
              tempValuesSql.append(" VALUES (" + custId);
            }
          }
          else if ("2".equals(fieldType)) {
            tempUpdateSql.append(" UPDATE " + tabName + " SET " + colName + "=TO_DATE('" + dataNew + "','yyyy-MM-dd')");
          } else {
            tempUpdateSql.append(" UPDATE " + tabName + " SET " + colName + "='" + dataNew + "'");
          }

        }
        else if (insertFlag) {
          if ("2".equals(fieldType)) {
            tempInsertSql.append(" ," + colName);
            tempValuesSql.append(" ,TO_DATE('" + dataNew + "','yyyy-MM-dd')");
          } else {
            tempInsertSql.append(" ," + colName);
            tempValuesSql.append(" ,'" + dataNew + "'");
          }
        }
        else if ("2".equals(fieldType)) {
          tempUpdateSql.append(" ," + colName + "=TO_DATE('" + dataNew + "','yyyy-MM-dd')");
        } else {
          tempUpdateSql.append(" ," + colName + "='" + dataNew + "'");
        }

        if ((!insertFlag) && ("1".equals(pkFlag))) {
          tempWhereSql = " WHERE " + colName + "='" + dataNew + "'";
        }

        if (totalCount == 0) {
          if ((tempUpdateSql != null) && (!"".equals(tempUpdateSql.toString()))) {
            if (tempWhereSql == null) {
              tempWhereSql = " WHERE CUST_ID='" + custId + "'";
            }
            this.SQLS.add(tempUpdateSql.toString() + tempWhereSql);
            log.info("添加批量UPDATE SQL:[" + tempUpdateSql.toString() + tempWhereSql + "]");
          }
          if ((tempInsertSql != null) && (tempValuesSql != null) && (!"".equals(tempInsertSql.toString())) && 
            (!"".equals(tempValuesSql.toString()))) {
            this.SQLS.add(tempInsertSql.toString() + ") " + tempValuesSql.toString() + ")");
            log.info("添加批量INSERT SQL:[" + tempInsertSql.toString() + ") " + tempValuesSql.toString() + ") ]");
          }
        }
      }
    }
    if (this.SQLS.size() > 0) {
      executeBatch(vo);
    }
    joinSql2(vo, "ACRM_F_CI_FINA_BUSI");
    joinSql2(vo, "ACRM_F_CI_PER_FAMILY");
    joinSql2(vo, "ACRM_F_CI_PER_KEYFLAG");
    joinSql2(vo, "ACRM_F_CI_PER_PREFERENCE");
  }

  public void joinSql2(EVO vo, String tableName)
    throws SQLException
  {
    String instanceid = vo.getInstanceID();
    String[] instanceids = instanceid.split("_");
    String custId = instanceids[1];
    String updateFlag = instanceids[2];
    this.SQLS = new ArrayList();

    this.SQL = (" SELECT * FROM OCRM_F_CI_CUSTINFO_UPHIS V WHERE V.CUST_ID='" + custId + "' AND  V.UPDATE_FLAG='" + updateFlag + "' AND V.UPDATE_TABLE ='" + tableName + "' ORDER BY UPDATE_TABLE,UPDATE_TABLE_ID");
    Result result = querySQL(vo);
    boolean tableFlag = false;
    boolean insertFlag = false;
    this.SQL = (" SELECT * FROM " + tableName + " WHERE CUST_ID='" + custId + "'");
    Result result2 = querySQL(vo);
    if (result2.getRowCount() > 0)
      insertFlag = false;
    else {
      insertFlag = true;
    }
    String lastTable = null;

    StringBuffer tempUpdateSql = null;
    String tempWhereSql = null;
    StringBuffer tempInsertSql = null;
    StringBuffer tempValuesSql = null;
    int totalCount = result.getRowCount();
    for (SortedMap row : result.getRows()) {
      totalCount--;
      String dataOld = row.get("UPDATE_BE_CONT") != null ? (String)row.get("UPDATE_BE_CONT") : "";
      dataOld = StringEscapeUtils.escapeSql(dataOld);
      String dataNew = row.get("UPDATE_AF_CONT") != null ? (String)row.get("UPDATE_AF_CONT") : "";
      dataNew = StringEscapeUtils.escapeSql(dataNew);
      String colName = row.get("UPDATE_ITEM_EN") == null ? "" : String.valueOf(row.get("UPDATE_ITEM_EN"));
      String tabName = row.get("UPDATE_TABLE") == null ? "" : String.valueOf(row.get("UPDATE_TABLE"));
      String pkFlag = row.get("UPDATE_TABLE_ID") == null ? "" : String.valueOf(row.get("UPDATE_TABLE_ID"));
      String fieldType = row.get("FIELD_TYPE") == null ? "" : String.valueOf(row.get("FIELD_TYPE"));

      if ((tabName != null) && (!"".equals(tabName)) && (colName != null) && (!"".equals(colName)))
      {
        if ((lastTable != null) && (lastTable.equals(tabName)))
          tableFlag = true;
        else {
          tableFlag = false;
        }

        if (!tableFlag) {
          if ((tempUpdateSql != null) && (!"".equals(tempUpdateSql.toString()))) {
            if (tempWhereSql == null) {
              tempWhereSql = " WHERE CUST_ID='" + custId + "'";
            }
            this.SQLS.add(tempUpdateSql.toString() + tempWhereSql);
            log.info("添加批量UPDATE SQL:[" + tempUpdateSql.toString() + tempWhereSql + "]");
          }
          if ((tempInsertSql != null) && (tempValuesSql != null) && (!"".equals(tempInsertSql.toString())) && 
            (!"".equals(tempValuesSql.toString()))) {
            this.SQLS.add(tempInsertSql.toString() + ") " + tempValuesSql.toString() + ")");
            log.info("添加批量INSERT SQL:[" + tempInsertSql.toString() + ") " + tempValuesSql.toString() + ") ]");
          }

          lastTable = tabName;
          tempUpdateSql = new StringBuffer();
          tempWhereSql = null;
          tempInsertSql = new StringBuffer();
          tempValuesSql = new StringBuffer();

          if (insertFlag) {
            if ((dataNew == null) || ("".equals(dataNew)))
            {
              if ((dataOld == null) || ("".equals(dataOld))) {
                if (!"ACRM_F_CI_PER_PREFERENCE".equals(tabName)) {
                  tempInsertSql.append(" INSERT INTO " + tabName + "(" + colName);
                  tempValuesSql.append(" VALUES (ID_SEQUENCE.NEXTVAL");
                }
                if("ACRM_F_CI_PER_PREFERENCE".equals(tabName)){
            		if("CUST_ID".equals(colName)){
                		tempInsertSql.append(" INSERT INTO " + tabName + "(" + colName);
                        tempValuesSql.append(" VALUES ('" + custId + "'");
                	}else{//特殊处理联络频率字段
                		tempInsertSql.append(" INSERT INTO " + tabName + "(CUST_ID," + colName);
                        tempValuesSql.append(" VALUES ('"+custId+"'," + dataNew + "'");
                	}
            	}
              } else if (dataOld.indexOf("NEXTVAL") > -1) {
                tempInsertSql.append(" INSERT INTO " + tabName + "(" + colName);
                tempValuesSql.append(" VALUES (" + dataOld);
              } else {
                tempInsertSql.append(" INSERT INTO " + tabName + "(" + colName);
                tempValuesSql.append(" VALUES ('" + dataOld + "'");
              }
            } else {
            	if(!"ACRM_F_CI_PER_PREFERENCE".equals(tabName)){
            		 tempInsertSql.append(" INSERT INTO " + tabName + "(" + colName);
                     tempValuesSql.append(" VALUES ('" + dataNew + "'");
            	}
            	if("ACRM_F_CI_PER_PREFERENCE".equals(tabName)){
            		if("CUST_ID".equals(colName)){
                		tempInsertSql.append(" INSERT INTO " + tabName + "(" + colName);
                        tempValuesSql.append(" VALUES ('" + custId + "'");
                	}else{//特殊处理联络频率字段
                		tempInsertSql.append(" INSERT INTO " + tabName + "(CUST_ID," + colName);
                        tempValuesSql.append(" VALUES ('"+custId+"','" + dataNew + "'");
                	}
            	}
            	
            }
          }
          else if ("2".equals(fieldType)) {
            tempUpdateSql.append(" UPDATE " + tabName + " SET " + colName + "=TO_DATE('" + dataNew + "','yyyy-MM-dd')");
          } else {
            tempUpdateSql.append(" UPDATE " + tabName + " SET " + colName + "='" + dataNew + "'");
          }

        }
        
        else if (insertFlag) {
          if ("2".equals(fieldType)) {
            tempInsertSql.append(" ," + colName);
            tempValuesSql.append(" ,TO_DATE('" + dataNew + "','yyyy-MM-dd')");
          } else {
            tempInsertSql.append(" ," + colName);
            tempValuesSql.append(" ,'" + dataNew + "'");
          }
        } 
        else if ("2".equals(fieldType)) {
          tempUpdateSql.append(" ," + colName + "=TO_DATE('" + dataNew + "','yyyy-MM-dd')");
        } else {
          tempUpdateSql.append(" ," + colName + "='" + dataNew + "'");
        }

        if ((!insertFlag) && ("1".equals(pkFlag))) {
          tempWhereSql = " WHERE " + colName + "='" + dataNew + "'";
        }

        if (totalCount == 0) {
          if ((tempUpdateSql != null) && (!"".equals(tempUpdateSql.toString()))) {
            if (tempWhereSql == null) {
              tempWhereSql = " WHERE CUST_ID='" + custId + "'";
            }
            this.SQLS.add(tempUpdateSql.toString() + tempWhereSql);
            log.info("添加批量UPDATE SQL:[" + tempUpdateSql.toString() + tempWhereSql + "]");
          }
          if ((tempInsertSql != null) && (tempValuesSql != null) && (!"".equals(tempInsertSql.toString())) && 
            (!"".equals(tempValuesSql.toString()))) {
            this.SQLS.add(tempInsertSql.toString() + ") " + tempValuesSql.toString() + ")");
            log.info("添加批量INSERT SQL:[" + tempInsertSql.toString() + ") " + tempValuesSql.toString() + ") ]");
          }
        }
      }
    }
    if (this.SQLS.size() > 0)
      executeBatch(vo);
  }
}