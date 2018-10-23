package com.ecc.echain.wf;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SortedMap;

import javax.servlet.jsp.jstl.sql.Result;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ecc.echain.workflow.engine.EVO;
import com.yuchengtech.bcrm.custmanager.model.AcrmFCiSpeciallist;
import com.yuchengtech.bcrm.echain.EChainCallbackCommon;
import com.yuchengtech.crm.exception.BizException;
import com.yuchengtech.trans.bo.RequestHeader;
import com.yuchengtech.trans.client.TransClient;

public class SpecialList extends EChainCallbackCommon {
	
	private static Logger log = Logger.getLogger(SpecialList.class);
	
	// 通过处理
	@SuppressWarnings("rawtypes")
	public void endY(EVO vo){
		try {
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split("_");
			StringBuffer sb=new StringBuffer("");
			/*
			 * 直接查询源表数据
			 * 修改时间：2014-10-17
			 * 修改人：wuxl2
			 */
			SQL = "SELECT S.* FROM ACRM_F_CI_SPECIALLIST S WHERE S.CUST_ID='"+instanceids[1]+"'";
//			SQL = " SELECT * FROM OCRM_F_CI_CUSTINFO_UPHIS V WHERE V.CUST_ID='"+instanceids[1]+"' AND  V.UPDATE_FLAG='"+instanceids[2]+"'";
			Result result=querySQL(vo);
			String custId="";
			String custName="";
			String identType="";
			String identNo="";
			String specialListType="";
			String specialListKind="";
			String specialListFlag="";
			String origin="";
			String statFlag="";
			String startDate="";
			String endDate="";
			String enterReason="";
			String lastUpdateUser = "";
			for (SortedMap item : result.getRows()){
				sb.setLength(0);
				custId=item.get("CUST_ID").toString();
				custName=item.get("CUST_NAME").toString();
				identType=item.get("IDENT_TYPE").toString();
				identNo=item.get("IDENT_NO").toString();
				specialListType=item.get("SPECIAL_LIST_TYPE").toString();
				specialListKind=item.get("SPECIAL_LIST_KIND").toString();
				specialListFlag=item.get("SPECIAL_LIST_FLAG").toString();
				origin=item.get("ORIGIN").toString();
				statFlag=item.get("STAT_FLAG").toString();
				startDate=item.get("START_DATE").toString();
				endDate=item.get("END_DATE").toString();
				enterReason=item.get("ENTER_REASON").toString();
				lastUpdateUser = item.get("LAST_UPDATE_USER").toString();
				
			}
			AcrmFCiSpeciallist spec = new AcrmFCiSpeciallist();
			spec.setCustName(custName);
			spec.setApprovalFlag("3");
			spec.setCustId(custId);
			spec.setIdentType(identType);
			spec.setIdentNo(identNo);
			spec.setSpecialListType(specialListType);
			spec.setSpecialListKind(specialListKind);
			spec.setSpecialListFlag(specialListFlag);
			spec.setOrigin(origin);
			spec.setStatFlag(statFlag);
			spec.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(startDate));
			spec.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(endDate));
			spec.setEnterReason(enterReason);
			spec.setLastUpdateUser(lastUpdateUser);
			//调用交易接口
			String responseXml = TranCrmToEcif(spec, vo);
			boolean responseFlag = doResXms(responseXml);
			
			//增加处理返回报文、判断交易返回结果,用于CRM业务更新时使用
			if(responseFlag){
				/**
				 * 更新ACRM_F_CI_SPECIALLIST审批状态（APPROVAL_FLAG=3）为3
				 * 修改日期：2014-10-17
				 * 修改人：wuxl2
				 */
				String sql = "update ACRM_F_CI_SPECIALLIST set APPROVAL_FLAG=3 where CUST_ID='"+instanceids[1]+"'";
				SQL = sql;
				log.info("执行SQL:----->>>>>[" + SQL + "]");
				execteSQL(vo);
			}else{
				throw new BizException(1,0,"0000","Warning-168:数据信息同步失败,请及时联系IT部门!");
			}
			
		}catch (Exception e) {
			log.error("处理过程中发现异常(错误):");
			throw new BizException(1,0,"10000",e.getMessage());
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
	 * 负责调用拼将报文并发送交易、返回交易结果
	 * SQL 变量必须先赋值才能调用此方法
	 * @param cust_id
	 * @param update_flag
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public String TranCrmToEcif(AcrmFCiSpeciallist spc,EVO vo) throws Exception{
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
		header.setTlrNo(spc.getLastUpdateUser());
		
		StringBuffer sb = new StringBuffer();
		sb.append("<RequestBody>");
		sb.append("<txCode>updateSpecialList</txCode>");
		sb.append("<txName>修改特殊名单</txName>");
		sb.append("<authType>1</authType>");
		sb.append("<authCode>001</authCode>");
		sb.append("<custNo>"+spc.getCustId()+"</custNo>");
		sb.append("<speciallist>");
	        sb.append("<specialListType>"+spc.getSpecialListType()+"</specialListType>");
			sb.append("<specialListKind>"+spc.getSpecialListKind()+"</specialListKind>");
			sb.append("<specialListFlag>"+spc.getSpecialListFlag()+"</specialListFlag>");
			sb.append("<identType>"+spc.getIdentType()+"</identType>");
			sb.append("<identNo>"+spc.getIdentNo()+"</identNo>");
			sb.append("<custName>"+spc.getCustName()+"</custName>");
			sb.append("<origin>"+spc.getOrigin()+"</origin>");
			sb.append("<enterReason>"+spc.getEnterReason()+"</enterReason>");
			sb.append("<statFlag>"+spc.getStatFlag()+"</statFlag>");
			sb.append("<startDate>"+new SimpleDateFormat("yyyy-MM-dd").format(spc.getStartDate())+"</startDate>");
			sb.append("<endDate>"+new SimpleDateFormat("yyyy-MM-dd").format(spc.getEndDate())+"</endDate>");
			sb.append("<approvalFlag>"+spc.getApprovalFlag()+"</approvalFlag>");
		sb.append("</speciallist>");
		sb.append("</RequestBody>");
		String Xml = new String(sb.toString().getBytes());
		String req=TransClient.process(header, Xml);
		return req;
	}	
	
	public void endN(EVO vo) {
		try {
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split("_");
			/**
			 * 更新ACRM_F_CI_SPECIALLIST审批状态（APPROVAL_FLAG=0）为0
			 * 修改日期：2014-10-17
			 * 修改人：wuxl2
			 */
			String sql = "update ACRM_F_CI_SPECIALLIST set APPROVAL_FLAG=0 where CUST_ID='"+instanceids[1]+"'";
			SQL = sql;
			execteSQL(vo);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("执行SQL出错");
			throw new BizException(1,0,"","特殊用户工作流审核不通过执行SQL出错");
		}
	}
	/**
	 * 撤销办理
	 * @param vo
	 */
	public void endCB(EVO vo) {
		try {
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split("_");
			/**
			 * 更新ACRM_F_CI_SPECIALLIST审批状态（APPROVAL_FLAG=1）为1
			 */
			String sql = "update ACRM_F_CI_SPECIALLIST set APPROVAL_FLAG=1 where CUST_ID='"+instanceids[1]+"'";
			SQL = sql;
			execteSQL(vo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1,0,"","特殊用户工作流撤办执行SQL出错");
		}
	}
}
