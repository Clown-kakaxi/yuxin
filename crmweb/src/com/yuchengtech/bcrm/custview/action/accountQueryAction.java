package com.yuchengtech.bcrm.custview.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ecc.echain.util.StringUtils;
import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custview.model.AcrmFciDepositAct;
import com.yuchengtech.bcrm.custview.service.AccountQueryService;
import com.yuchengtech.bcrm.trade.model.TxLog;
//import com.yuchengtech.bcrm.custview.service.AccountQueryService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.service.CommonQueryService;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.trans.bo.RequestHeader;
import com.yuchengtech.trans.bo.TxData;
import com.yuchengtech.trans.bo.cb.QueryRequestBody2CB;
//import com.yuchengtech.trans.bo.wms.QueryRequestBodyWMS;
import com.yuchengtech.trans.client.TransClient;
import com.yuchengtech.trans.client.XmlhelperUtil;
import com.yuchengtech.trans.impl.queryCustInfo.CBAccountQueryTransaction;
import com.yuchengtech.trans.inf.Transaction;

/**
 * @describtion: 存款账户查询
 * 
 * @author : dongyi
 * @date : 2014-07-25
 */
@Action("/accountQuery")
public class accountQueryAction extends CommonAction {
	private static Logger log = LoggerFactory
			.getLogger(accountQueryAction.class);

	private static final long serialVersionUID = 1L;

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;

	@Autowired
	private CommonQueryService cqs;
	
	@Autowired
	private AccountQueryService accountQueryService;
	
	
	/*@Autowired
	private AccountQueryService  accountQueryService;*/

	// @Autowired
	// private RelaPartyInfoManagerService relapartyinfomangerservice;

	@Autowired
	public void init() {
		model = new AcrmFciDepositAct();
	}
	
	/*
	 个人存款502000000132 

            机构存款501000143101 

	个人贷款501000000079 
	
	机构贷款503000010990 
	
	个人当日交易明细501000145101 
	
	机构当日交易明细501000006765 
	 */
	

	public void prepare() {
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		String id = request.getParameter("CustId");
		StringBuffer sb1 = new StringBuffer(
				"SELECT distinct t1.*,t2.prod_name FROM ACRM_F_CI_DEPOSIT_ACT t1 " +
				"  left join OCRM_F_PD_PROD_INFO t2 on t1.acct_name=t2.product_id " +
				"  WHERE 1=1  and t1.cust_id in('"
						+ id + "') order by  t1.AMOUNT_ORG_MONEY desc, t1.DEPOSITE_AVG_Y desc");
		for (String key : this.getJson().keySet()) {
		}
		SQL = sb1.toString();

		datasource = ds;
	}
    //机构贷款
	public String Loan() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		SimpleDateFormat df8 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat df20 = new SimpleDateFormat("yyyyMMddhhmmss");
		String account = request.getParameter("CustId");
		RequestHeader header = new RequestHeader();
		header.setReqSysCd("CRM");
		header.setReqSeqNo(df20.format(new Date()));
		header.setReqDt(df8.format(new Date()));
		header.setReqTm("161456");
		header.setDestSysCd("CB");
		header.setChnlNo("82");
		header.setBrchNo("503");
		header.setBizLine("209");
		header.setTrmNo("TRM10010");
		header.setTrmIP(request.getLocalAddr());
		header.setTlrNo("6101");
		Map obj = new HashMap();
		try {
			String custCd = getCustCd(account);
			//custCd="503000010990";
			QueryRequestBody2CB requestBody = new QueryRequestBody2CB();
			requestBody.setTxCode("CMCN");
			requestBody.setCustCd(custCd);
			String resXml = TransClient.process(header, requestBody);
			List list = ResXms(resXml);
			obj.put("count", list.size());
			obj.put("data", list);

		} catch (Exception e) {
			log.error("xml报文解析错误：" + e.getMessage());
			e.printStackTrace();
		}

		this.json = new HashMap<String, Object>();
		this.json.put("json", obj);
		return null;
	}

	public String trans() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		SimpleDateFormat df8 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat df20 = new SimpleDateFormat("yyyyMMddhhmmss");
		String account = request.getParameter("CustId");
		RequestHeader header = new RequestHeader();
		header.setReqSysCd("CRM");
		header.setReqSeqNo(df20.format(new Date()));
		header.setReqDt(df8.format(new Date()));
		header.setReqTm("161456");
		header.setDestSysCd("CB");
		header.setChnlNo("82");
		header.setBrchNo("503");
		header.setBizLine("209");
		header.setTrmNo("TRM10010");
		header.setTrmIP(request.getLocalAddr());
		header.setTlrNo("6101");
		Map obj = new HashMap();
		try {
			String custCd = getCustCd(account);
			//custCd="501000006765";
			QueryRequestBody2CB requestBody = new QueryRequestBody2CB();
			requestBody.setTxCode("CMCT");
			requestBody.setCustCd(custCd);
			String resXml = TransClient.process(header, requestBody);
			List list = ResXms(resXml);
			obj.put("count", list.size());
			obj.put("data", list);
		} catch (Exception e) {
			log.error("xml报文解析错误：" + e.getMessage());
			e.printStackTrace();
		}

		this.json = new HashMap<String, Object>();
		this.json.put("json", obj);
		return null;
	}
   //机构存款
	public String doposit() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		SimpleDateFormat df8 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat df20 = new SimpleDateFormat("yyyyMMddhhmmss");
		String account = request.getParameter("CustId");
		RequestHeader header = new RequestHeader();
		header.setReqSysCd("CRM");
		header.setReqSeqNo(df20.format(new Date()));
		header.setReqDt(df8.format(new Date()));
		header.setReqTm("161456");
		header.setDestSysCd("CB");
		header.setChnlNo("82");
		header.setBrchNo("503");
		header.setBizLine("209");
		header.setTrmNo("TRM10010");
		header.setTrmIP(request.getLocalAddr());
		header.setTlrNo("6101");
		Map obj = new HashMap();
		System.out.println("================>" + account);
		try {
			String custCd = getCustCd(account);
			//custCd="501000143101";
			QueryRequestBody2CB requestBody = new QueryRequestBody2CB();
			requestBody.setTxCode("CMCD");
			requestBody.setCustCd(custCd);
			String resXml = TransClient.process(header, requestBody);
			List list = ResXms(resXml);
			obj.put("count", list.size());
			obj.put("data", list);
		} catch (Exception e) {
			log.error("xml报文解析错误：" + e.getMessage());
			e.printStackTrace();
		}

		this.json = new HashMap<String, Object>();
		this.json.put("json", obj);
		return null;
	}
	
	//个人贷款
	  public String perloan(){ 
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		SimpleDateFormat df8 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat df20 = new SimpleDateFormat("yyyyMMddhhmmss");
		String account = request.getParameter("custId");
		RequestHeader header = new RequestHeader();
		header.setReqSysCd("CRM");
		header.setReqSeqNo(df20.format(new Date()));
		header.setReqDt(df8.format(new Date()));
		header.setReqTm("161456");
		header.setDestSysCd("CB");
		header.setChnlNo("82");
		header.setBrchNo("503");
		header.setBizLine("209");
		header.setTrmNo("TRM10010");
		header.setTrmIP(request.getLocalAddr());
		header.setTlrNo("6101");
		Map obj = new HashMap();
		try {
			String custCd = getCustCd(account);
			// custCd="501000000079";
			QueryRequestBody2CB requestBody = new QueryRequestBody2CB();
			requestBody.setTxCode("CMSN");
			requestBody.setCustCd(custCd);

			String msg = TransClient.parseObject2Xml(header, requestBody);
			msg = String.format("%08d", msg.getBytes("GBK").length) + msg;
			TxData txData = new TxData();
			txData.setReqMsg(msg);
			txData.setTxCnName("个人贷款");
			txData.setTxCode("CMSN");
			Transaction trans = new CBAccountQueryTransaction(txData);
			txData = trans.process();
			TxLog txLog = trans.getTxLog();
			accountQueryService.save(txLog);

			String resXml = txData.getResMsg();
			List list = ResXms(resXml);
			obj.put("count", list.size());
			obj.put("data", list);
		} catch (Exception e) {
			log.error("xml报文解析错误：" + e.getMessage());
			e.printStackTrace();
		}

		this.json = new HashMap<String, Object>();
		this.json.put("json", obj);
		System.err.println("json_CMSN : " + JSONObject.fromObject(this.json));
		return null;
	}
	  //个人存款
	  @SuppressWarnings("unused")
	public String perdeposit(){
			ActionContext ctx = ActionContext.getContext();
		  	request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		  	String depType = request.getParameter("depType");
		  	depType = "["+depType+"]";
	      	SimpleDateFormat df8 = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat df20 = new SimpleDateFormat("yyyyMMddhhmmss");	
	      	String account =request.getParameter("custId");
	        RequestHeader header = new RequestHeader();
	        header.setReqSysCd("CRM");
			header.setReqSeqNo(df20.format(new Date()));
			header.setReqDt(df8.format(new Date()));
			header.setReqTm("161456");
			header.setDestSysCd("CB");
			header.setChnlNo("82");
			header.setBrchNo("503");
			header.setBizLine("209");
			header.setTrmNo("TRM10010");
			header.setTrmIP(request.getLocalAddr());
			header.setTlrNo("6101");
			Map obj = new HashMap();
			try {
				String custCd= getCustCd(account);
				//custCd="502000000132";
				QueryRequestBody2CB requestBody = new QueryRequestBody2CB();
				requestBody.setTxCode("CMSD");
				requestBody.setCustCd(custCd);
				log.info(header.toString());
				log.info(requestBody.toString());
				
				String msg = TransClient.parseObject2Xml(header, requestBody);
				msg = String.format("%08d", msg.getBytes("GBK").length) + msg;
				TxData txData = new TxData();
				txData.setReqMsg(msg);
				txData.setTxCnName("个人存款");
				txData.setTxCode("CMSD");
				Transaction trans = new CBAccountQueryTransaction(txData);
				txData = trans.process();
				TxLog txLog = trans.getTxLog();
				accountQueryService.save(txLog);
				
				String resXml = txData.getResMsg();
				List<Map> list = ResXms(resXml);
				List resultList = new ArrayList();
				for(int i=0;i<list.size();i++){
					/*if(depType.equals(list.get(0).get("acctype"))){
						resultList.add(list.get(0));
					}*/
					//////崔恒薇修改
					if(list.get(i).get("accType") != null && depType.equals(list.get(i).get("accType").toString())){
						resultList.add(list.get(i));
					}
				}
				obj.put("count", resultList.size());
		        obj.put("data", resultList);
			} catch (Exception e) {
				log.error("xml报文解析错误："+e.getMessage());
				e.printStackTrace();
			}
			
			this.json = new HashMap<String, Object>();
			this.json.put("json", obj);
			System.err.println("json_CMSD : " + JSONObject.fromObject(this.json));
	        return null;
	    }
	  
	  //大额存单
	  public String cerdeposit(){
		   	ActionContext ctx = ActionContext.getContext();
	     	request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
	      	SimpleDateFormat df8 = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat df20 = new SimpleDateFormat("yyyyMMddhhmmss");	
	      	String account =request.getParameter("custId");
	        RequestHeader header = new RequestHeader();
	        header.setReqSysCd("CRM");
			header.setReqSeqNo(df20.format(new Date()));
			header.setReqDt(df8.format(new Date()));
			header.setReqTm("161456");
			header.setDestSysCd("CB");
			header.setChnlNo("82");
			header.setBrchNo("503");
			header.setBizLine("209");
			header.setTrmNo("TRM10010");
			header.setTrmIP(request.getLocalAddr());
			header.setTlrNo("6101");
			Map obj = new HashMap();
			try {
				String custCd= getCustCd(account);
				//custCd="502000000132";
				QueryRequestBody2CB requestBody = new QueryRequestBody2CB();
				requestBody.setTxCode("CMSA");
				requestBody.setCustCd(custCd);
				log.info(header.toString());
				log.info(requestBody.toString());
				
				String msg = TransClient.parseObject2Xml(header, requestBody);
				msg = String.format("%08d", msg.getBytes("GBK").length) + msg;
				TxData txData = new TxData();
				txData.setReqMsg(msg);
				txData.setTxCnName("大额存单");
				txData.setTxCode("CMSA");
				Transaction trans = new CBAccountQueryTransaction(txData);
				txData = trans.process();
				TxLog txLog = trans.getTxLog();
				accountQueryService.save(txLog);
				
				String resXml = txData.getResMsg();
				List list = ResXms(resXml);
				obj.put("count", list.size());
		        obj.put("data", list);
			} catch (Exception e) {
				log.error("xml报文解析错误："+e.getMessage());
				e.printStackTrace();
			}
			
			this.json = new HashMap<String, Object>();
			this.json.put("json", obj);
			System.err.println("json_CMSA : " + JSONObject.fromObject(this.json));
	        return null;
	    }
	  
	  public String pertrans(){ 
		   	ActionContext ctx = ActionContext.getContext();
		     	request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		      	SimpleDateFormat df8 = new SimpleDateFormat("yyyyMMdd");
				SimpleDateFormat df20 = new SimpleDateFormat("yyyyMMddhhmmss");	
				//String CustId
		       String account =request.getParameter("CustId");
		       RequestHeader header = new RequestHeader();
		       header.setReqSysCd("CRM");
				header.setReqSeqNo(df20.format(new Date()));
				header.setReqDt(df8.format(new Date()));
				header.setReqTm("161456");
				header.setDestSysCd("CB");
				header.setChnlNo("82");
				header.setBrchNo("503");
				header.setBizLine("209");
				header.setTrmNo("TRM10010");
				header.setTrmIP(request.getLocalAddr());
				header.setTlrNo("6101");
				Map obj = new HashMap();
				try {
					String custCd= getCustCd(account);
					//custCd="501000145101";
					QueryRequestBody2CB requestBody = new QueryRequestBody2CB();
					requestBody.setTxCode("CMST");
					requestBody.setCustCd(custCd);
					String resXml = TransClient.process(header, requestBody);
					List list = ResXms(resXml);
					obj.put("count", list.size());
			        obj.put("data", list);
				} catch (Exception e) {
					log.error("xml报文解析错误："+e.getMessage());
					e.printStackTrace();
				}
				
				this.json = new HashMap<String, Object>();
				this.json.put("json", obj);
		        return null;
		    }
	  /**
	   * 请求WMS理财
	   * @return
	   */
	 public String WMSAccount(){
		 ActionContext ctx = ActionContext.getContext();
	     request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
	     String custId =request.getParameter("custId");
	     Map<String, Object> map = accountQueryService.queryWMSProductList(custId);
	     this.json = new HashMap<String, Object>();
	     this.json.put("json", map);
	     return null;
	 }

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getCustCd(String account) throws Exception {
		try {
//			String sql = "select core_no from Acrm_f_Ci_Customer where cust_id =(select cust_id from Acrm_f_Ci_Loan_Act where account ='"
//					+ account + "')";
			 String sql = "select core_no from ACRM_F_CI_Customer where CUST_ID = '" + account + "'";
			SQL = sql.toString();
			Map map = cqs.excuteQuery(sql.toString(), 0, 1);
			if (map != null) {
				List<Map> list = (List) map.get("data");
				if (list != null && list.size() > 0) {
					String core_no = list.get(0).get("CORE_NO").toString(); // 获取核心客户号
					return core_no;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询核心客户号失败:" + e.getMessage());
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List ResXms(String xml) throws Exception {
		List<Map> list = new ArrayList<Map>();
		if (xml.contains("<ResponseBody>")) {
			int beginIndex = xml.indexOf("<ResponseBody>");
			int endIndenx = xml.indexOf("</ResponseBody>");
			xml = xml.substring(beginIndex, endIndenx) + "</ResponseBody>";
			XmlhelperUtil util = new XmlhelperUtil();
			list = util.getParseXmlList(xml);
		}
		return list;
	}
	
	public static List ResTail(String xml) throws Exception {
		List<Map> list = new ArrayList<Map>();
		if (xml.contains("<ResponseTail>")) {
			int beginIndex = xml.indexOf("<ResponseTail>");
			int endIndenx = xml.indexOf("</ResponseTail>");
			xml = xml.substring(beginIndex, endIndenx) + "</ResponseTail>";
			XmlhelperUtil util = new XmlhelperUtil();
			list = util.getParseXmlList(xml);
		}
		return list;
	}
	
	//获取响应报文中的返回信息
	/*public String getReturnMessage(String xml) throws Exception {
		String retMessage = ""; 
		try {
			xml = xml.substring(8);
			Document doc = DocumentHelper.parseText(xml);
			Element root = doc.getRootElement();
			retMessage = root.element("ResponseTail").element("TxStatDesc").getTextTrim();
		} catch (Exception e) {
			e.printStackTrace();
			retMessage = "解析信贷系统返回报文失败";
		}
		return retMessage;
	}*/
	
}
