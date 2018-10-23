package com.yuchengtech.bcrm.oneKeyAccount.action;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.codec.Hex;

import com.ecc.echain.util.DatetimeUtils;
import com.ecc.echain.util.UNIDProducer;
import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custmanager.service.CustomerManagerInfoService;
import com.yuchengtech.bcrm.customer.service.Onekey2EcifAccountService;
import com.yuchengtech.bcrm.customer.service.QueryCustOrderInfoService;
import com.yuchengtech.bcrm.oneKeyAccount.model.ICCardSysVO;
import com.yuchengtech.bcrm.oneKeyAccount.model.NetBankAccountChannelVO;
import com.yuchengtech.bcrm.oneKeyAccount.model.NetBankAccountVO;
import com.yuchengtech.bcrm.oneKeyAccount.model.NetCheckVO;
import com.yuchengtech.bcrm.oneKeyAccount.model.OneKeyAccountVO;
import com.yuchengtech.bcrm.oneKeyAccount.model.TelBlackListVO;
import com.yuchengtech.bcrm.oneKeyAccount.service.CrmCustInfoService;
import com.yuchengtech.bcrm.oneKeyAccount.service.CustRecheckInfoService;
import com.yuchengtech.bcrm.oneKeyAccount.service.OneKeyAccountService;
import com.yuchengtech.bcrm.oneKeyAccount.service.OnekeyAccount2CBService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.bob.upload.FileTypeConstance;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.EndecryptUtils;
import com.yuchengtech.crm.exception.BizException;

@SuppressWarnings("serial")
@Action("/oneKeyAccountAction")
public class OneKeyAccountAction extends CommonAction{
    @Autowired
    private OneKeyAccountService oneKey ;
    
    @Autowired
    private Onekey2EcifAccountService onekey2EcifAccountService;
    
    @Autowired
	private CustomerManagerInfoService customerManagerInfoService;// 定义UserManagerService属性
    
    @Autowired
    private QueryCustOrderInfoService queryCustOrderInfoService;//
    
    @Autowired
    private CrmCustInfoService crmCustInfoService;
    
    @Autowired
    private OnekeyAccount2CBService OnekeyAccount2CBService;
    
    @Autowired
    private CustRecheckInfoService custRecheckInfoService;
    
    @Autowired
    @Qualifier("dsOracle")
    private DataSource ds;
    
    private static Logger log = LoggerFactory.getLogger(OneKeyAccountAction.class);

   
    
	@Autowired
	public void init() {
		model = new OneKeyAccountVO();
	}
    
    //卡系统生成报文
    public void invokeCardSysImpl(){
	   	ActionContext ctx = ActionContext.getContext();
	    request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	    try {
	    	HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html:charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			//AccountNo
			String accountNo = request.getParameter("AccountNo");
			String cardNo = request.getParameter("CardNo");
			String mainCardNo = request.getParameter("MainCardNo");
			String psw = request.getParameter("Psw");
			System.err.println("psw:["+psw+"]");
			ICCardSysVO cvo = new ICCardSysVO();//
			cvo.setPinPsw(psw);//原始密码
			if(psw != null && !psw.equals("")){
				byte[] decode = Hex.decode(psw);
				cvo.setPswCode(decode);
//				System.err.println("decode:["+decode+"]");
				psw = new String(decode, "GBK");//解密后的密码
//				System.err.println("psw:["+psw.getBytes()+"]");
//				System.err.println("psw-GBK:["+psw.getBytes("GBK")+"]");
			}
//			System.err.println("psw2:["+psw+"]");
//			System.err.println("psw2.length:["+psw.getBytes().length+"]");
			String serialNo = request.getParameter("SerialNo");
			String dataSource = request.getParameter("DataSource");
			String secondTrackInfo = request.getParameter("SecondTrackInfo");
			String unionpay55Info = request.getParameter("Unionpay55Info");
			cvo.setAccountNo(accountNo);
			cvo.setCardNo(cardNo);
			cvo.setMainCardNo(mainCardNo);
			cvo.setPsw(psw);
			cvo.setCardSerialNo(serialNo);
			cvo.setDataSource(dataSource);
			cvo.setSecondTrackInfo(secondTrackInfo);
			cvo.setUnionpay55Info(unionpay55Info);
		    Map<String, Object> cardSystemReportParam = this.oneKey.getCardSystemReportParam(cvo);
		    this.json = cardSystemReportParam;
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public static void main(String[] args) {
    	byte[] decode = Hex.decode("D946E21DDC0919B5");
		try {
			System.err.println(new String(decode, "GBK").getBytes());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    
    /**
     * 开通预制卡
     */
    public void accountReserceCard(){
    	try {
    		Map<String, Object> retJso = new HashMap<String, Object>();
        	HttpServletRequest request = ServletActionContext.getRequest();
        	String accountNo = request.getParameter("AccountNo");
        	String custNm = request.getParameter("custNm");
        	String speCardType = request.getParameter("speCardType");
        	ICCardSysVO vo = new ICCardSysVO();//
			vo.setAccountNo(accountNo);
			vo.setCustNm(custNm);
			vo.setSpeCardType(speCardType);
			Map<String, Object> accountReserceCard = this.oneKey.accountReserceCard(vo);
			this.json = accountReserceCard;
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	//网银开户xml生成
    public void invokeCyberBankSysImpl(){
	   	try {
	   		HttpServletRequest request = ServletActionContext.getRequest();
//		    HttpServletResponse response = ServletActionContext.getResponse();
//			response.setContentType("text/html:charset=utf-8");
//			response.setCharacterEncoding("UTF-8");
			String netBankAccountInfo = request.getParameter("netBankAccountInfo");
			if (netBankAccountInfo != null && !netBankAccountInfo.equals("")) {
				if(netBankAccountInfo.startsWith("{") && netBankAccountInfo.endsWith("}")){
					JSONObject jso_netBankAccountInfo = JSONObject.fromObject(netBankAccountInfo);
					NetBankAccountVO vo = new NetBankAccountVO();///
					vo.setSerializeId(jso_netBankAccountInfo.optString("serializeId", ""));
					vo.setCifNo(jso_netBankAccountInfo.optString("CifNo", ""));
					vo.setCustName(jso_netBankAccountInfo.optString("CustName", ""));
					vo.setIdType(jso_netBankAccountInfo.optString("IdType", ""));
					vo.setIdNo(jso_netBankAccountInfo.optString("IdNo", ""));
					vo.setBirthDate(jso_netBankAccountInfo.optString("BirthDate", ""));
					vo.setPmbsTelNo(jso_netBankAccountInfo.optString("PmbsTelNo", ""));
					vo.setCifType(jso_netBankAccountInfo.optString("CifType", ""));
					vo.setTELType(jso_netBankAccountInfo.optString("TELType", ""));
					vo.setTELNo(jso_netBankAccountInfo.optString("TELNo", ""));
					vo.setTelAuthFlg(jso_netBankAccountInfo.optString("TelAuthFlg", ""));
					vo.setHomeAddr(jso_netBankAccountInfo.optString("HomeAddr", ""));
					vo.setPostZipCode(jso_netBankAccountInfo.optString("PostZipCode", ""));
					vo.setEAddrType("Email");
					vo.setEAddr(jso_netBankAccountInfo.optString("EAddr", ""));
					vo.setDayPerLimit(jso_netBankAccountInfo.optString("DayPerLimit", ""));
					vo.setDayTransTimes(jso_netBankAccountInfo.optString("DayTransTimes", ""));
					vo.setLimitPerYear(jso_netBankAccountInfo.optString("LimitPerYear", ""));
					

//					vo.setCifNo("502000008430");
//					vo.setCustName("xxxx");
//					vo.setIdType("P13");
//					vo.setIdNo("A201025746");
					JSONArray jsa_channelList = jso_netBankAccountInfo.optJSONArray("ChannelList");
					List<NetBankAccountChannelVO> list_channel = new ArrayList<NetBankAccountChannelVO>();
					if(jsa_channelList != null && jsa_channelList.size() > 0){
						for (int i = 0; i < jsa_channelList.size(); i++) {
							JSONObject jso_channel = jsa_channelList.optJSONObject(i);
							NetBankAccountChannelVO channelVO = new NetBankAccountChannelVO();
							channelVO.setMChannelId(jso_channel.optString("MChannelId", ""));
							channelVO.setAuthMod(jso_channel.optString("AuthMod", ""));
							channelVO.setMchMobilePhone(jso_channel.optString("mchMobilePhone", ""));
							channelVO.setState("N");
							list_channel.add(channelVO);//
						}
					}
					vo.setChannelList(list_channel);
					Map<String, Object> cyberBankReportParam = this.oneKey.getCyberBankReportParam(vo);
//					PrintWriter writer = response.getWriter();
//					writer.println(JSONObject.fromObject(cyberBankReportParam).toString());
//					writer.flush();
//					writer.close();
					this.json = null;
					this.json = cyberBankReportParam;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    //联网核查生成报文
    public void invokeNetworkSysImpl() {
    	HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html:charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		try {
//			PrintWriter writer = response.getWriter();
			NetCheckVO vo = new NetCheckVO();
			vo.setID("210203198711072015");//客户证件号码
			vo.setName("叶鹏");//客户姓名
			//联网核查
			Map<String, Object> checkNetWork = this.oneKey.checkNetWork(vo);//
//			writer.println(JSONObject.fromObject(checkNetWork).toString());
//			writer.flush();
//			writer.close();
			this.json = checkNetWork;
		} catch (Exception e) {
			e.printStackTrace();
		}	
    }
    
    
    
    
    public void getDimDate() throws Exception{
    	ActionContext ctx = ActionContext.getContext();
	    request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	    
	    HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html:charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		try {
			PrintWriter writer = response.getWriter();
			List<List<String>> rstList = this.oneKey.getDimDate(request.getParameter("fcodes"));
			String retStr = JSONArray.fromObject(rstList).toString();
			writer.println(retStr);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 根据证件类型，证件号码查询客户信息
     */
    public void getCustInfoByIdent(){
    	try {
    		HttpServletRequest request = ServletActionContext.getRequest();
        	HttpServletResponse response = ServletActionContext.getResponse();
        	response.setContentType("text/html:charset=utf-8");
    		response.setCharacterEncoding("UTF-8");
        	String identType = request.getParameter("identType");
        	String identNo = request.getParameter("identNo");
    		Map<String, Object> retMap = this.crmCustInfoService.getCustInfoByIdent(identType, identNo);
    		this.json = retMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    
    //证件类型
    public void getDimZjlx(){
    	HttpServletRequest request = ServletActionContext.getRequest();
	    HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html:charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		try {
			List<Map<String, Object>> rstList = this.oneKey.getDimZjlx(request.getParameter("name"));
			if (this.json != null) {
				this.json.clear();
			}else{
				this.json = new HashMap<String, Object>();
			}
			this.json.put("JSON", rstList);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 联网及黑名单核查
     */
    public void netCheckAndBlackOrderCheck(){
    	//JSONObject retJso = new JSONObject();
    	Map<String, Object> retJso = new HashMap<String, Object>();
    	HttpServletRequest request = ServletActionContext.getRequest();
    	//HttpServletResponse response = ServletActionContext.getResponse();
		//response.setContentType("text/html:charset=utf-8");
		//response.setCharacterEncoding("UTF-8");
		String s_status1 = "";
		String s_status1_2 = "";
		String s_status2 = "";
		String s_status2_2 = "";
		String s_status3 = "";
		String s_status3_2 = "";
		String s_msg1 = "";
		String s_msg1_2 = "";
		String s_msg2 = "";
		String s_msg2_2 = "";
		String s_msg3 = "";
		String s_msg3_2 = "";
		try {
			//PrintWriter writer = response.getWriter();
			String logId = request.getParameter("logId");//交易流水号
			String jointaccount = request.getParameter("jointaccount");//联名户标志
			String custNm = request.getParameter("custNm");  //源客户姓名
			String identType = request.getParameter("certtype");  //主户证件类型
			String identNum = request.getParameter("certid"); //主户证件号
			String custNm2 = null;  //从客户姓名
			String identType2 = request.getParameter("certtype2"); //从户证件类型
			String identNum2 = request.getParameter("certid2");  //从户证件号
			if(identNum == null || identNum.equals("")
					|| custNm == null || custNm.equals("")
					|| identType == null || identType.equals("")){
				s_status1 = "error";
				s_msg1 = "缺少查询数据，请补充信息后重新查询...";
				s_status2 = "error";
				s_msg2 = "缺少查询数据，请补充信息后重新查询...";
			}
			//判断联名户，并且分拆客户名
			if(!StringUtils.isEmpty(jointaccount) && jointaccount.equals("1")){
				String custNms[] = custNm.split("/");
				if(custNms.length>1){
					custNm = custNms[0];
					custNm2 = custNms[1];
				}
			}
			
			//身份证
			if(!StringUtils.isEmpty(identType) && identType.equals("0")){
				NetCheckVO vo = new NetCheckVO();
				vo.setSEQNO(logId);
				vo.setName(custNm);//客户姓名
				vo.setID(identNum);//客户证件号码
				//联网核查
				Map<String, Object> checkNetWork = this.oneKey.checkNetWork(vo);//
				s_status1 = (String) checkNetWork.get("status");
				s_msg1 = (String) checkNetWork.get("msg");
				//屏蔽联网核查
//				s_status1 = "success";
//				s_msg1 = "";
			}else{
				s_status1 = "success";
				s_msg1 = "非身份证不需要进行联网核查";
			}
			//联名户从户身份证联网核查
			if(!StringUtils.isEmpty(jointaccount) && jointaccount.equals("1")&&!StringUtils.isEmpty(identType2) && identType2.equals("0")){
				NetCheckVO vo = new NetCheckVO();
				vo.setSEQNO(logId);
				vo.setName(custNm2);//客户姓名
				vo.setID(identNum2);//客户证件号码
				//联网核查
				Map<String, Object> checkNetWork = this.oneKey.checkNetWork(vo);//
				s_status1_2 = (String) checkNetWork.get("status");
				s_msg1_2 = (String) checkNetWork.get("msg");
				//屏蔽联网核查
//				s_status1 = "success";
//				s_msg1 = "";
			}else{
				s_status1_2 = "success";
				s_msg1_2 = "非身份证不需要进行联网核查";
			}
			
			//黑名单核查
			TelBlackListVO vo2 = new TelBlackListVO();
			vo2.setJylsh(logId);
			String str_identType = convertChinaNetIdentType(identType);
			if(str_identType == null || str_identType.equals("") || str_identType.equals("-1")){
				s_status2 = "error";
				s_msg2 = "证件类型异常，无法进行黑名单核查";
			}else{
				vo2.setSjx(identType + "_" + identNum);//
				//黑名单校验
				Map<String, Object> checkTelBlackList = this.oneKey.checkTelBlackList(vo2);
				s_status2 = (String) checkTelBlackList.get("status");
				s_msg2 = (String) checkTelBlackList.get("msg");
				/*s_status2 = "success";
				s_msg2 = "";*/
			}
			//联名户联网黑名单查询
			if(!StringUtils.isEmpty(jointaccount) && jointaccount.equals("1")){
				str_identType = convertChinaNetIdentType(identType2);
				if(str_identType == null || str_identType.equals("") || str_identType.equals("-1")){
					s_status2_2 = "error";
					s_msg2_2 = "证件类型2异常，无法进行黑名单核查";
				}else{
					vo2.setSjx(identType2 + "_" + identNum2);//
					//黑名单校验
					Map<String, Object> checkTelBlackList = this.oneKey.checkTelBlackList(vo2);
					s_status2_2 = (String) checkTelBlackList.get("status");
					s_msg2_2 = (String) checkTelBlackList.get("msg");
					/*s_status2 = "success";
					s_msg2 = "";*/
				}
			}
			//本地黑名单校验
			Map<String, Object> checkLocalBlackList = this.oneKey.checkLocalBlackList(identNum);
			s_status3 = (String) checkLocalBlackList.get("status");
			s_msg3 = (String) checkLocalBlackList.get("msg");
			if(!StringUtils.isEmpty(jointaccount) && jointaccount.equals("1")){
				//联名户从户本地黑名单校验
				checkLocalBlackList = this.oneKey.checkLocalBlackList(identNum2);
				s_status3_2 = (String) checkLocalBlackList.get("status");
				s_msg3_2 = (String) checkLocalBlackList.get("msg");
			}	
			retJso.put("status1", s_status1);
			retJso.put("msg1", s_msg1);
			retJso.put("status1_2", s_status1_2);
			retJso.put("msg1_2", s_msg1_2);
			retJso.put("status2", s_status2);
			retJso.put("msg2", s_msg2);
			retJso.put("status2_2", s_status2_2);
			retJso.put("msg2_2", s_msg2_2);
			retJso.put("status3", s_status3);
			retJso.put("msg3", s_msg3);
			retJso.put("status3_2", s_status3_2);
			retJso.put("msg3_2", s_msg3_2);
			this.json = retJso;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1, 0, "0000","系统错误,请及时联系IT部门！");
		}
    }
    
    

	/**
     * 查询客户信息
     */
    public void validateCustomerInfo(){
    	try {
    		Map<String, Object> retJso = new HashMap<String, Object>();
        	HttpServletRequest request = ServletActionContext.getRequest();
			String logId = request.getParameter("logId");//交易流水号
			String custNm = request.getParameter("custNm");//客户姓名
			String identType = request.getParameter("certtype");//证件类型
			String identNum = request.getParameter("certid");//证件号码
			String identType2 = request.getParameter("certtype2");//从户证件类型
			String identNum2 = request.getParameter("certid2");//从户证件号码
			String jointaccount = request.getParameter("jointaccount");//联名户的标志
			String ecifIsOpen = request.getParameter("ecifIsOpen");//联名户是否ECIF已经开户
			retJso = this.crmCustInfoService.validateCustomerInfo(custNm, identType, 
					identNum,identType2,identNum2,jointaccount,ecifIsOpen);
			this.json = retJso;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    
    /**
     * ECIF开户
     */
    public void ECIFAndCoreAccount(){
    	try {
    		HttpServletRequest request = ServletActionContext.getRequest();
        	HttpServletResponse response = ServletActionContext.getResponse();
        	response.setContentType("text/html:charset=utf-8");
    		response.setCharacterEncoding("UTF-8");
        	String custName = request.getParameter("custName");
    		String identyId = request.getParameter("identyId");
    		String identyType = request.getParameter("identyType");
    		//EcifAccountVO vo = new EcifAccountVO();
    		Map<String, Object> ecifMap = null;//onekey2EcifAccountService.save(vo);
    		//3 System.out.println(JSONObject.fromObject(ecifMap).toString());
//    		PrintWriter writer = response.getWriter();
//    		writer.println(JSONObject.fromObject(ecifMap).toString());
//			writer.flush();
//			writer.close();
			this.json = ecifMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 查询流程信息
     */
    public void checkWorkFlowInfo(){
    	try {
    		HttpServletRequest request = ServletActionContext.getRequest();
        	String custId = request.getParameter("custId");	
        	String instanceid = request.getParameter("instanceid");
        	Map<String, Object> retMap = this.custRecheckInfoService.checkFlowStatus(custId,instanceid);
    		this.json = retMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 查看工作流状态
     */
    public void getWkFlowStatus(){
    	try {
    		HttpServletRequest request = ServletActionContext.getRequest();
        	HttpServletResponse response = ServletActionContext.getResponse();
        	response.setContentType("text/html:charset=utf-8");
    		response.setCharacterEncoding("UTF-8");
        	String logId = request.getParameter("logId");
        	String custId = request.getParameter("custId");
        	String instanceid = request.getParameter("instanceid");
    		Map<String, Object> retMap = this.custRecheckInfoService.getWkFlowStatus(logId, instanceid);
    		this.json = retMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    

    
    /**
     * 更新客户核心客户号
     */
    public void updateCustomerCoreNo(){
    	try {
    		HttpServletRequest request = ServletActionContext.getRequest();
        	HttpServletResponse response = ServletActionContext.getResponse();
        	response.setContentType("text/html:charset=utf-8");
    		response.setCharacterEncoding("UTF-8");
        	String custId = request.getParameter("custId");
        	String coreNo = request.getParameter("coreNo");
    		Map<String, Object> retMap = this.crmCustInfoService.updateCutomerCoreNo(custId, coreNo);
    		this.json = retMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    
    /**
     * CRM请求核心
     */
    public void CRM2CBAccount(){
    	try {
    		HttpServletRequest request = ServletActionContext.getRequest();
        	String logId = request.getParameter("logId");
        	String custId = request.getParameter("custId");
    		Map<String, Object> retMap = this.OnekeyAccount2CBService.account2CB(logId, custId);
    		this.json = retMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
   /**
    * 查询标准码表信息(境内外标志)，以及对应的账户类型
    * 返回信息格式：{"code":"境内外类型编号","value":"境内外类型名称","items":[{"code":"账户类型","value":"账户类型名称"}]}
    * @throws Exception
    */
    public void getAccountType() throws Exception{
    	request = ServletActionContext.getRequest();
		try {
			JSONObject json = this.oneKey.getAccountTypeData();
			this.json = (Map) json;
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * CRM到ECIF开户
     */
	public void crm2EcifAccount() {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			Map<String, Object> saveData = this.onekey2EcifAccountService.openEcifAccount(request);
			this.json = saveData;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    /**
     * 流程初始化
     * @throws Exception
     */
    public void initFlow() throws Exception {
	ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		String cusId=request.getParameter("cusId");
		String custName = request.getParameter("custName");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String instanceid = "cus_" + cusId + "_"+ sdf.format(new Date());// 此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
		String jobName = "开户_" + custName;// 自定义流程名称
		customerManagerInfoService.initWorkflowByWfidAndInstanceid("1000012", jobName, null, instanceid);// 调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("instanceid", instanceid);
		map1.put("currNode", "1000012_a3");
		map1.put("nextNode", "1000012_a4");
		
		this.setJson(map1);
    }
    
    
    /**
	 * 查询客户信息第一页基本信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String queryComfsx(){
		try {
			ActionContext ctx = ActionContext.getContext();
	        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	        String custId = request.getParameter("custId");
	        StringBuffer sb = new StringBuffer(" select CUST_ID,PERSONAL_NAME,PINYIN_NAME,GENDER, ");
			sb.append(" NATIONALITY,BIRTHDAY,CITIZENSHIP,NATIONALITY,BIRTHLOCALE,CAREER_STAT, ");
			sb.append(" UNIT_NAME,DUTY,IF_REL_PERSON,JOINT_CUST_TYPE,OTHER_CAREER ");
			sb.append(" from ACRM_F_CI_PERSON  WHERE CUST_ID = '"+custId+"' ");
	        QueryHelper query = new QueryHelper(sb.toString(), ds.getConnection());
	        Map<String, Object> resultMap = new HashMap<String, Object>();
	        List<HashMap<String, Object>> rowsList = (List<HashMap<String, Object>>)query.getJSON().get("data");

			if(rowsList != null && rowsList.size() > 0){
				for(String key:rowsList.get(0).keySet()){
					if(rowsList.get(0).get(key)!=null){
						String value = rowsList.get(0).get(key).toString();
						resultMap.put(key, value);
//						System.err.println("key:"+key+";value:"+value);
					}
				}
				
				//职业资料
				if(rowsList.get(0).get("CAREER_STAT")!= null && 
						!"".equals(rowsList.get(0).get("CAREER_STAT").toString())){
					String careerState = rowsList.get(0).get("CAREER_STAT").toString();
					if(careerState.equals("04")){//全日制雇员
						resultMap.put("JOBINFO", "04");
						//单位名称
						resultMap.put("JOBNAME", rowsList.get(0).get("UNIT_NAME"));
						//职务
						resultMap.put("JOB", rowsList.get(0).get("DUTY"));
					}else{
						resultMap.put("JOBINFO", careerState);
						if(careerState.equals("99")){//其他
							resultMap.put("JOBREMARK", rowsList.get(0).get("OTHER_CAREER"));
						}
					}
			   }
				
				
				
			   //在我行有无关联人
				 StringBuffer sb2 = new StringBuffer(" select IS_RELATED_BANK from ACRM_F_CI_PER_KEYFLAG ");
				 sb2.append(" WHERE CUST_ID = '"+custId+"' ");
		        QueryHelper query2 = new QueryHelper(sb2.toString(), ds.getConnection());
		        List<HashMap<String, Object>> rowsList2 = (List<HashMap<String, Object>>)query2.getJSON().get("data");
				if(rowsList2.get(0).get("IS_RELATED_BANK")!=null &&
						!"".equals(rowsList2.get(0).get("IS_RELATED_BANK").toString())){
					String ifRelPerson = rowsList2.get(0).get("IS_RELATED_BANK").toString();
					resultMap.put("HASRELATED", ifRelPerson);
					 if(ifRelPerson.equals("1")){//在我行有关联人
						StringBuffer sb1 = new StringBuffer("SELECT PERSONAL_NAME,RELATION FROM OCRM_M_CI_REL_PERSON");
						sb1.append(" WHERE CUST_ID = '"+custId+"'");
						QueryHelper query1 = new QueryHelper(sb1.toString(), ds.getConnection());
						List<HashMap<String, Object>> rowsList1 = (List<HashMap<String, Object>>) query1.getJSON().get("data");
						resultMap.put("RELATEDNAME", rowsList1.get(0).get("PERSONAL_NAME"));//有关联人关联人姓名
						resultMap.put("RELATION", rowsList1.get(0).get("RELATION"));//有关联人与关联人关系
					}
				}
				
				//主户证件类型
				String identType1_0 = "";
				String identNo1_0 = "";
				//证件
				sb = new StringBuffer("SELECT IDENT_TYPE,IDENT_NO,IDENT_EXPIRED_DATE, ");
			    sb.append(" COUNTRY_OR_REGION,IDENT_ORG,IDENT_COUNT FROM ACRM_F_CI_CUST_IDENTIFIER ");
				sb.append(" WHERE CUST_ID = '"+custId+"'");
				query = new QueryHelper(sb.toString(), ds.getConnection());
				List<HashMap<String, Object>> tempRowsList = (List<HashMap<String, Object>>) query.getJSON().get("data");
				if(tempRowsList != null && tempRowsList.size() > 0){
					//发证机关所在地
					if(tempRowsList.get(0).get("COUNTRY_OR_REGION")!=null &&
							 !"".equals(tempRowsList.get(0).get("COUNTRY_OR_REGION").toString())){
						resultMap.put("qianfajiguan",tempRowsList.get(0).get("COUNTRY_OR_REGION").toString());
					}
					
					if(tempRowsList.size() == 1){
						resultMap.put("identityType3", tempRowsList.get(0).get("IDENT_TYPE"));
						resultMap.put("identityNo3", tempRowsList.get(0).get("IDENT_NO").toString().replaceAll("\\*", ""));
						identType1_0 = tempRowsList.get(0).get("IDENT_TYPE").toString();
						identNo1_0 = tempRowsList.get(0).get("IDENT_NO").toString();
						
						if(tempRowsList.get(0).get("IDENT_EXPIRED_DATE")!= null ){
							if("".equals(tempRowsList.get(0).get("IDENT_EXPIRED_DATE").toString()) ||
									"9999-12-31".equals(tempRowsList.get(0).get("IDENT_EXPIRED_DATE").toString())){
								resultMap.put("LEGAL_EXPIRED_DATE", "9999-12-31");
								resultMap.put("longterm","1");
							}else{
								resultMap.put("LEGAL_EXPIRED_DATE", tempRowsList.get(0).get("IDENT_EXPIRED_DATE").toString());
								resultMap.put("longterm","0");
							}
						}
					}else if(tempRowsList.size() ==2){
						if( tempRowsList.get(0).get("IDENT_TYPE")!= null 
								&&  !"".equals(tempRowsList.get(0).get("IDENT_TYPE").toString())
								&& tempRowsList.get(1).get("IDENT_TYPE")!= null 
								&&  !"".equals(tempRowsList.get(1).get("IDENT_TYPE").toString())){
							String identType1 = tempRowsList.get(0).get("IDENT_TYPE").toString();
							String identType2 = tempRowsList.get(1).get("IDENT_TYPE").toString();
							if(identType1.equals("6") || identType1.equals("X24") || identType1.equals("X3")){//台湾同胞来往内地通行证1、临时台胞证1、旅行证件1
								resultMap.put("identityType3", identType1);
								resultMap.put("identityNo3", tempRowsList.get(0).get("IDENT_NO").toString().replaceAll("\\*", ""));
								resultMap.put("twIdentNum3", tempRowsList.get(1).get("IDENT_NO").toString().replaceAll("\\*", ""));
								
								identType1_0 = "X2";
								identNo1_0 = tempRowsList.get(1).get("IDENT_NO").toString();
								if(identType1.equals("6")){
									//持证次数
									if(tempRowsList.get(0).get("IDENT_COUNT") != null &&
											 !"".equals(tempRowsList.get(0).get("IDENT_COUNT").toString())){
										resultMap.put("chizhengcishu", tempRowsList.get(0).get("IDENT_COUNT").toString());
									}
								}
							}else if(identType1.equals("5") ){//港澳居民来往内地通行证1
								resultMap.put("identityType3", identType1);
								resultMap.put("identityNo3", tempRowsList.get(0).get("IDENT_NO").toString().replaceAll("\\*", ""));
								resultMap.put("gaIdentNum3", tempRowsList.get(1).get("IDENT_NO").toString().replaceAll("\\*", ""));
								
								identType1_0 = "X1";
								identNo1_0 = tempRowsList.get(1).get("IDENT_NO").toString();
							}
							if(identType2.equals("6") || identType2.equals("X24") || identType2.equals("X3")){//台湾同胞来往内地通行证1、临时台胞证1、旅行证件1
								resultMap.put("identityType3", identType2);
								resultMap.put("identityNo3", tempRowsList.get(1).get("IDENT_NO").toString().replaceAll("\\*", ""));
								resultMap.put("twIdentNum3", tempRowsList.get(0).get("IDENT_NO").toString().replaceAll("\\*", ""));
								
								identType1_0 = "X2";
								identNo1_0 = tempRowsList.get(0).get("IDENT_NO").toString();
								
								if(identType2.equals("6")){
									//持证次数
									if(tempRowsList.get(1).get("IDENT_COUNT") != null &&
											 !"".equals(tempRowsList.get(1).get("IDENT_COUNT").toString())){
										resultMap.put("chizhengcishu", tempRowsList.get(1).get("IDENT_COUNT").toString());
									}
								}
							}else if(identType2.equals("5") ){//港澳居民来往内地通行证1
								resultMap.put("identityType3", identType2);
								resultMap.put("identityNo3", tempRowsList.get(1).get("IDENT_NO").toString().replaceAll("\\*", ""));
								resultMap.put("gaIdentNum3", tempRowsList.get(0).get("IDENT_NO").toString().replaceAll("\\*", ""));
								
								identType1_0 = "X1";
								identNo1_0 = tempRowsList.get(0).get("IDENT_NO").toString();
							}
							
							if(tempRowsList.get(0).get("IDENT_EXPIRED_DATE")!= null ){
								if("9999-12-31".equals(tempRowsList.get(0).get("IDENT_EXPIRED_DATE").toString()) ||
										"".equals(tempRowsList.get(0).get("IDENT_EXPIRED_DATE").toString())){
									//resultMap.put("LEGAL_EXPIRED_DATE", "9999-12-31");
									resultMap.put("youxiaoqixian", "9999-12-31");
									//resultMap.put("longterm","1");
									resultMap.put("longterm2","1");
								}else{
									//resultMap.put("LEGAL_EXPIRED_DATE", tempRowsList.get(0).get("IDENT_EXPIRED_DATE").toString());
									resultMap.put("youxiaoqixian", tempRowsList.get(0).get("IDENT_EXPIRED_DATE").toString());
									//resultMap.put("longterm","0");
									resultMap.put("longterm2","0");
								}
							}
							
							if(tempRowsList.get(1).get("IDENT_EXPIRED_DATE")!= null ){
								if("9999-12-31".equals(tempRowsList.get(1).get("IDENT_EXPIRED_DATE").toString()) ||
										"".equals(tempRowsList.get(1).get("IDENT_EXPIRED_DATE").toString())){
									//resultMap.put("youxiaoqixian", "9999-12-31");
									resultMap.put("LEGAL_EXPIRED_DATE", "9999-12-31");
									//resultMap.put("longterm2","1");
									resultMap.put("longterm","1");
								}else{
									//resultMap.put("youxiaoqixian", tempRowsList.get(1).get("IDENT_EXPIRED_DATE").toString());
									resultMap.put("LEGAL_EXPIRED_DATE", tempRowsList.get(1).get("IDENT_EXPIRED_DATE").toString());
									//resultMap.put("longterm2","0");
									resultMap.put("longterm","0");
								}
							}
							
						}
					}
				}
				
				//是否是联名户
				if(rowsList.get(0).get("JOINT_CUST_TYPE")!= null &&
						!"".equals(rowsList.get(0).get("JOINT_CUST_TYPE").toString())){
					String isLianMingHu = rowsList.get(0).get("JOINT_CUST_TYPE").toString();
					if(isLianMingHu.equals("N")){//不是联名户
						resultMap.put("isLianMingHu", "0");
						
						//3.本人声明
						StringBuffer sb3 = new StringBuffer(" SELECT PERSON_STATEMENT,USAFLAG,USTIN, ");
						sb3.append(" IF_NO_TIN_COUNTRY,IF_NO_TIN_PERSON,REASON ");
						sb3.append(" FROM OCRM_M_CI_TAX_MAIN ");
						sb3.append(" WHERE CUST_ID = '"+custId+"' ");
						QueryHelper query3 = new QueryHelper(sb3.toString(), ds.getConnection());
						List<HashMap<String, Object>> tempRowsList3 = (List<HashMap<String, Object>>) query3.getJSON().get("data");
						if(tempRowsList3 != null && tempRowsList3.size() > 0){
							if(tempRowsList3.get(0).get("PERSON_STATEMENT")!= null &&
									!"".equals(tempRowsList3.get(0).get("PERSON_STATEMENT").toString())){
								String radio1 = tempRowsList3.get(0).get("PERSON_STATEMENT").toString();
								resultMap.put("radio1",radio1);
								if(radio1.equals("02")||radio1.equals("03")){//仅为非居民或既是中国税收居民又是其他国税收居民
									//是否为美国纳税人
									if(tempRowsList3.get(0).get("USAFLAG")!= null &&
											!"".equals(tempRowsList3.get(0).get("USAFLAG").toString())){
										String usaFlag = tempRowsList3.get(0).get("USAFLAG").toString();
										
										if(usaFlag.equals("1")){//是美国纳税人
											resultMap.put("isUNtaxpayer","1");
											if(tempRowsList3.get(0).get("USTIN")!= null &&
													!"".equals(tempRowsList3.get(0).get("USTIN").toString())){
												resultMap.put("USTIN",tempRowsList3.get(0).get("USTIN").toString());
											}
										}else if(usaFlag.equals("0")){
											resultMap.put("isUNtaxpayer","2");
										}
									}
									
									//是否居民国（地区）不发放纳税人识别号
									if(tempRowsList3.get(0).get("IF_NO_TIN_COUNTRY")!= null &&
											!"".equals(tempRowsList3.get(0).get("IF_NO_TIN_COUNTRY").toString())){
										resultMap.put("REASON",tempRowsList3.get(0).get("IF_NO_TIN_COUNTRY").toString());
									}
									
									//是否账户持有人未能取得纳税人识别号
									if(tempRowsList3.get(0).get("IF_NO_TIN_PERSON")!= null &&
											!"".equals(tempRowsList3.get(0).get("IF_NO_TIN_PERSON").toString())){
										resultMap.put("REASON2",tempRowsList3.get(0).get("IF_NO_TIN_PERSON").toString());
									}
									//账户持有人未能取得纳税人识别号原因
									if(tempRowsList3.get(0).get("REASON")!= null &&
											!"".equals(tempRowsList3.get(0).get("REASON").toString())){
										resultMap.put("detailReason",tempRowsList3.get(0).get("REASON").toString());
									}
									
									StringBuffer sb4 = new StringBuffer();
									sb4.append(" SELECT TAX_COUNTRY,TIN,IF_NO_TIN_COUNTRY,IF_NO_TIN_PERSON,REASON ");
									sb4.append(" FROM OCRM_M_CI_TAX_SUB  WHERE CUST_ID = '"+ custId +"'");
									QueryHelper query4 = new QueryHelper(sb4.toString(), ds.getConnection());
									List<HashMap<String, Object>> tempRowsList4 = (List<HashMap<String, Object>>) query4.getJSON().get("data");
									if(tempRowsList4 != null && tempRowsList4.size() > 0){
										String taxCountrys = "";
										String tins = "";
										for (int i = 0; i < tempRowsList4.size(); i++) {
											if(tempRowsList4.get(i).get("TAX_COUNTRY")!=null &&
													!"".equals(tempRowsList4.get(i).get("TAX_COUNTRY").toString())){//税收居民国（地区）
												taxCountrys += tempRowsList4.get(i).get("TAX_COUNTRY").toString()+"-";
											}
											
											if(tempRowsList4.get(i).get("TIN")!=null &&
													!"".equals(tempRowsList4.get(i).get("TIN").toString())){//纳税人识别号（TIN）
												tins += tempRowsList4.get(i).get("TIN").toString()+"-";
											}
										}
										resultMap.put("taxCountrys",taxCountrys);
										resultMap.put("tins",tins);
									}
								}
							}
						}
						
						
					}else if(isLianMingHu.equals("Y")){//是联名户
						
						//从户证件类型
						String identType2_0 = "";
						String identNo2_0 = "";
						
						resultMap.put("isLianMingHu", "1");
						//2.联名户信息
						StringBuffer sb1 = new StringBuffer("SELECT PINYIN_NAME,GENDER,CITIZENSHIP, ");
						sb1.append(" IDENT_TYPE1, IDENT_NO1, IDT_EXPIRE_DT1, IS_ACT_PERM1, ");
						sb1.append(" IDENT_TYPE2, IDENT_NO2, IDT_EXPIRE_DT2, IS_ACT_PERM2, COUNTRY_OR_REGION ");
						sb1.append(" FROM OCRM_F_CI_CUSTJOIN_INFO ");
						sb1.append(" WHERE CUST_ID = '"+custId+"'");
						QueryHelper query1 = new QueryHelper(sb1.toString(), ds.getConnection());
						List<HashMap<String, Object>> rowsList1 = (List<HashMap<String, Object>>) query1.getJSON().get("data");
						resultMap.put("lianMinPinYin", rowsList1.get(0).get("PINYIN_NAME"));//联名户姓名拼音
						resultMap.put("sex", rowsList1.get(0).get("GENDER"));//联名户性别
						resultMap.put("CITIZENSHIP1", rowsList1.get(0).get("CITIZENSHIP"));//联名户国籍
						
						resultMap.put("lianMingIdenType1", rowsList1.get(0).get("IDENT_TYPE1"));//证件类型1
						resultMap.put("lianMingIdenNo1", rowsList1.get(0).get("IDENT_NO1"));//证件号码1
						
						identType2_0 = rowsList1.get(0).get("IDENT_TYPE1").toString();
						identNo2_0 = rowsList1.get(0).get("IDENT_NO1").toString();
						
						resultMap.put("LEGAL_EXPIRED_DATE2", rowsList1.get(0).get("IDT_EXPIRE_DT1"));//有效日期1
						resultMap.put("LONGTERM2", rowsList1.get(0).get("IS_ACT_PERM1"));//证件1是否长期有效
						
						if(rowsList1.get(0).get("IDENT_TYPE2") != null && !"".equals(rowsList1.get(0).get("IDENT_TYPE2").toString())){
							String identType2 = rowsList1.get(0).get("IDENT_TYPE2").toString();
							if(identType2.equals("6")||identType2.equals("X24")||identType2.equals("X3")
									){//台湾同胞来往内地通行证//临时台胞证1//旅行证件1
								resultMap.put("lianMingIdenType1", rowsList1.get(0).get("IDENT_TYPE2"));//证件类型1
								resultMap.put("lianMingIdenNo1", rowsList1.get(0).get("IDENT_NO2"));//证件号码1
								resultMap.put("LEGAL_EXPIRED_DATE1", rowsList1.get(0).get("IDT_EXPIRE_DT1"));//有效日期1
								resultMap.put("LONGTERM1", rowsList1.get(0).get("IS_ACT_PERM1"));//证件1是否长期有效
								
								resultMap.put("lianMingTwIdentNum1", rowsList1.get(0).get("IDENT_NO1"));//证件号码2
								
								identType2_0 = "X2";
								identNo2_0 = rowsList1.get(0).get("IDENT_NO1").toString();
								
								resultMap.put("LEGAL_EXPIRED_DATE2", rowsList1.get(0).get("IDT_EXPIRE_DT2"));//有效日期2
								resultMap.put("LONGTERM2", rowsList1.get(0).get("IS_ACT_PERM2"));//证件2是否长期有效
								
							}else if(identType2.equals("5")){//港澳居民来往内地通行证1
								resultMap.put("lianMingIdenType1", rowsList1.get(0).get("IDENT_TYPE2"));//证件类型1
								resultMap.put("lianMingIdenNo1", rowsList1.get(0).get("IDENT_NO2"));//证件号码1
								resultMap.put("LEGAL_EXPIRED_DATE1", rowsList1.get(0).get("IDT_EXPIRE_DT1"));//有效日期1
								resultMap.put("LONGTERM1", rowsList1.get(0).get("IS_ACT_PERM1"));//证件1是否长期有效
								
								resultMap.put("lianMingGaIdentNum1", rowsList1.get(0).get("IDENT_NO1"));//证件号码2
								
								identType2_0 = "X1";
								identNo2_0 = rowsList1.get(0).get("IDENT_NO1").toString();
								
								resultMap.put("LEGAL_EXPIRED_DATE2", rowsList1.get(0).get("IDT_EXPIRE_DT2"));//有效日期2
								resultMap.put("LONGTERM2", rowsList1.get(0).get("IS_ACT_PERM2"));//证件2是否长期有效
							}
						}
					//联名户证件发证机关所在地
					if(rowsList1.get(0).get("COUNTRY_OR_REGION")!= null && 
							!"".equals(rowsList1.get(0).get("COUNTRY_OR_REGION").toString())){
						resultMap.put("CITIZENSHIP2", rowsList1.get(0).get("COUNTRY_OR_REGION").toString());
					}
					
					identNo1_0 = identNo1_0.replaceAll("[*]", "");
					//主户本人声明
					StringBuffer sb5 = new StringBuffer(" SELECT CUST_ID FROM ACRM_F_CI_CUST_IDENTIFIER ")
					.append(" WHERE IDENT_TYPE = '"+ identType1_0 +"' AND IDENT_NO = '"+ identNo1_0 +"' ");
					QueryHelper query5 = new QueryHelper(sb5.toString(), ds.getConnection());
					List<HashMap<String, Object>> tempRowsList5 = (List<HashMap<String, Object>>) query5.getJSON().get("data");
					if(tempRowsList5 != null && tempRowsList5.size() > 0){
						if(tempRowsList5.get(0).get("CUST_ID")!= null &&
								!"".equals(tempRowsList5.get(0).get("CUST_ID").toString())){
							String custId1 = tempRowsList5.get(0).get("CUST_ID").toString();
							StringBuffer sb3 = new StringBuffer(" SELECT PERSON_STATEMENT,USAFLAG,USTIN, ");
							sb3.append(" IF_NO_TIN_COUNTRY,IF_NO_TIN_PERSON,REASON ");
							sb3.append(" FROM OCRM_M_CI_TAX_MAIN ");
							sb3.append(" WHERE CUST_ID = '"+custId1+"' ");
							QueryHelper query3 = new QueryHelper(sb3.toString(), ds.getConnection());
							List<HashMap<String, Object>> tempRowsList3 = (List<HashMap<String, Object>>) query3.getJSON().get("data");
							if(tempRowsList3 != null && tempRowsList3.size() > 0){
								if(tempRowsList3.get(0).get("PERSON_STATEMENT")!= null &&
										!"".equals(tempRowsList3.get(0).get("PERSON_STATEMENT").toString())){
									String radio1 = tempRowsList3.get(0).get("PERSON_STATEMENT").toString();
									resultMap.put("radio1",radio1);
									if(radio1.equals("02")||radio1.equals("03")){//仅为非居民或既是中国税收居民又是其他国税收居民
										//是否为美国纳税人
										if(tempRowsList3.get(0).get("USAFLAG")!= null &&
												!"".equals(tempRowsList3.get(0).get("USAFLAG").toString())){
											String usaFlag = tempRowsList3.get(0).get("USAFLAG").toString();
											
											if(usaFlag.equals("1")){//是美国纳税人
												resultMap.put("isUNtaxpayer",usaFlag);
												if(tempRowsList3.get(0).get("USTIN")!= null &&
														!"".equals(tempRowsList3.get(0).get("USTIN").toString())){
													resultMap.put("USTIN",tempRowsList3.get(0).get("USTIN").toString());
												}
											}else if(usaFlag.equals("0")){
												resultMap.put("isUNtaxpayer","2");
											}
										}
										
										//是否居民国（地区）不发放纳税人识别号
										if(tempRowsList3.get(0).get("IF_NO_TIN_COUNTRY")!= null &&
												!"".equals(tempRowsList3.get(0).get("IF_NO_TIN_COUNTRY").toString())){
											resultMap.put("REASON",tempRowsList3.get(0).get("IF_NO_TIN_COUNTRY").toString());
										}
										
										//是否账户持有人未能取得纳税人识别号
										if(tempRowsList3.get(0).get("IF_NO_TIN_PERSON")!= null &&
												!"".equals(tempRowsList3.get(0).get("IF_NO_TIN_PERSON").toString())){
											resultMap.put("REASON2",tempRowsList3.get(0).get("IF_NO_TIN_PERSON").toString());
										}
										//账户持有人未能取得纳税人识别号原因
										if(tempRowsList3.get(0).get("REASON")!= null &&
												!"".equals(tempRowsList3.get(0).get("REASON").toString())){
											resultMap.put("detailReason",tempRowsList3.get(0).get("REASON").toString());
										}
										
										StringBuffer sb4 = new StringBuffer();
										sb4.append(" SELECT TAX_COUNTRY,TIN,IF_NO_TIN_COUNTRY,IF_NO_TIN_PERSON,REASON ");
										sb4.append(" FROM OCRM_M_CI_TAX_SUB  WHERE CUST_ID = '"+ custId1 +"'");
										QueryHelper query4 = new QueryHelper(sb4.toString(), ds.getConnection());
										List<HashMap<String, Object>> tempRowsList4 = (List<HashMap<String, Object>>) query4.getJSON().get("data");
										if(tempRowsList4 != null && tempRowsList4.size() > 0){
											String taxCountrys = "";
											String tins = "";
											for (int i = 0; i < tempRowsList4.size(); i++) {
												if(tempRowsList4.get(i).get("TAX_COUNTRY")!=null &&
														!"".equals(tempRowsList4.get(i).get("TAX_COUNTRY").toString())){//税收居民国（地区）
													taxCountrys += tempRowsList4.get(i).get("TAX_COUNTRY").toString()+"-";
												}
												
												if(tempRowsList4.get(i).get("TIN")!=null &&
														!"".equals(tempRowsList4.get(i).get("TIN").toString())){//纳税人识别号（TIN）
													tins += tempRowsList4.get(i).get("TIN").toString()+"-";
												}
											}
											resultMap.put("taxCountrys",taxCountrys);
											resultMap.put("tins",tins);
										}
									}
								}
							}
						}
					}
					
					//从户本人声明
					StringBuffer sb6 = new StringBuffer(" SELECT CUST_ID FROM ACRM_F_CI_CUST_IDENTIFIER ")
					.append(" WHERE IDENT_TYPE = '"+ identType2_0 +"' AND IDENT_NO = '"+ identNo2_0 +"' ");
					QueryHelper query6 = new QueryHelper(sb6.toString(), ds.getConnection());
					List<HashMap<String, Object>> tempRowsList6 = (List<HashMap<String, Object>>) query6.getJSON().get("data");
					if(tempRowsList6 != null && tempRowsList6.size() > 0){
						if(tempRowsList6.get(0).get("CUST_ID")!= null &&
								!"".equals(tempRowsList6.get(0).get("CUST_ID").toString())){
							String custId2 = tempRowsList6.get(0).get("CUST_ID").toString();
							StringBuffer sb3 = new StringBuffer(" SELECT PERSON_STATEMENT,USAFLAG,USTIN, ");
							sb3.append(" IF_NO_TIN_COUNTRY,IF_NO_TIN_PERSON,REASON ");
							sb3.append(" FROM OCRM_M_CI_TAX_MAIN ");
							sb3.append(" WHERE CUST_ID = '"+custId2+"' ");
							QueryHelper query3 = new QueryHelper(sb3.toString(), ds.getConnection());
							List<HashMap<String, Object>> tempRowsList3 = (List<HashMap<String, Object>>) query3.getJSON().get("data");
							if(tempRowsList3 != null && tempRowsList3.size() > 0){
								if(tempRowsList3.get(0).get("PERSON_STATEMENT")!= null &&
										!"".equals(tempRowsList3.get(0).get("PERSON_STATEMENT").toString())){
									String radio2 = tempRowsList3.get(0).get("PERSON_STATEMENT").toString();
									resultMap.put("radio2",radio2);
									if(radio2.equals("02")||radio2.equals("03")){//仅为非居民或既是中国税收居民又是其他国税收居民
										//是否为美国纳税人
										if(tempRowsList3.get(0).get("USAFLAG")!= null &&
												!"".equals(tempRowsList3.get(0).get("USAFLAG").toString())){
											String usaFlag2 = tempRowsList3.get(0).get("USAFLAG").toString();
											
											if(usaFlag2.equals("1")){//是美国纳税人
												resultMap.put("isUNtaxpayer2","1");
												if(tempRowsList3.get(0).get("USTIN")!= null &&
														!"".equals(tempRowsList3.get(0).get("USTIN").toString())){
													resultMap.put("USTIN2",tempRowsList3.get(0).get("USTIN").toString());
												}
											}else if(usaFlag2.equals("0")){
												resultMap.put("isUNtaxpayer2","2");
											}
										}
										
										//是否居民国（地区）不发放纳税人识别号
										if(tempRowsList3.get(0).get("IF_NO_TIN_COUNTRY")!= null &&
												!"".equals(tempRowsList3.get(0).get("IF_NO_TIN_COUNTRY").toString())){
											resultMap.put("REASON3",tempRowsList3.get(0).get("IF_NO_TIN_COUNTRY").toString());
										}
										
										//是否账户持有人未能取得纳税人识别号
										if(tempRowsList3.get(0).get("IF_NO_TIN_PERSON")!= null &&
												!"".equals(tempRowsList3.get(0).get("IF_NO_TIN_PERSON").toString())){
											resultMap.put("REASON4",tempRowsList3.get(0).get("IF_NO_TIN_PERSON").toString());
										}
										//账户持有人未能取得纳税人识别号原因
										if(tempRowsList3.get(0).get("REASON")!= null &&
												!"".equals(tempRowsList3.get(0).get("REASON").toString())){
											resultMap.put("detailReason2",tempRowsList3.get(0).get("REASON").toString());
										}
										
										StringBuffer sb4 = new StringBuffer();
										sb4.append(" SELECT TAX_COUNTRY,TIN,IF_NO_TIN_COUNTRY,IF_NO_TIN_PERSON,REASON ");
										sb4.append(" FROM OCRM_M_CI_TAX_SUB  WHERE CUST_ID = '"+ custId2 +"'");
										QueryHelper query4 = new QueryHelper(sb4.toString(), ds.getConnection());
										List<HashMap<String, Object>> tempRowsList4 = (List<HashMap<String, Object>>) query4.getJSON().get("data");
										if(tempRowsList4 != null && tempRowsList4.size() > 0){
											String taxCountrys = "";
											String tins = "";
											for (int i = 0; i < tempRowsList4.size(); i++) {
												if(tempRowsList4.get(i).get("TAX_COUNTRY")!=null &&
														!"".equals(tempRowsList4.get(i).get("TAX_COUNTRY").toString())){//税收居民国（地区）
													taxCountrys += tempRowsList4.get(i).get("TAX_COUNTRY").toString()+"-";
												}
												
												if(tempRowsList4.get(i).get("TIN")!=null &&
														!"".equals(tempRowsList4.get(i).get("TIN").toString())){//纳税人识别号（TIN）
													tins += tempRowsList4.get(i).get("TIN").toString()+"-";
												}
											}
											resultMap.put("taxCountrys2",taxCountrys);
											resultMap.put("tins2",tins);
										}
									}
								}
							}
						}
					}
					
					
				  }
			   }
			
			
			
			
			//地址类型、国籍、详细地址、邮政编码
			sb = new StringBuffer("SELECT ADDR_TYPE,COUNTRY_OR_REGION,ADDR,ZIPCODE FROM ACRM_F_CI_ADDRESS ");
			sb.append(" WHERE CUST_ID = '"+custId+"'");
			query = new QueryHelper(sb.toString(), ds.getConnection());
			tempRowsList = (List<HashMap<String, Object>>) query.getJSON().get("data");
			if(tempRowsList != null && tempRowsList.size() > 0){
				for(int i = 0; i < tempRowsList.size(); i++){
					if(tempRowsList.get(i).get("ADDR_TYPE")!=null 
						&& !"".equals(tempRowsList.get(i).get("ADDR_TYPE").toString())){
						String addrType = tempRowsList.get(i).get("ADDR_TYPE").toString();
						if(addrType.equals("04")){//居住地址
							resultMap.put("HOME_ADDR", tempRowsList.get(0).get("COUNTRY_OR_REGION"));
							resultMap.put("HOME_ADDR_INFO", tempRowsList.get(0).get("ADDR"));
							resultMap.put("POST_ZIPCODE", tempRowsList.get(0).get("ZIPCODE"));
						}else if(addrType.equals("01")){//邮寄地址国籍
							resultMap.put("MAIL_ADDR", tempRowsList.get(0).get("COUNTRY_OR_REGION"));
							resultMap.put("MAIL_ADDR_INFO", tempRowsList.get(0).get("ADDR"));
							resultMap.put("MAIL_ZIPCODE", tempRowsList.get(0).get("ZIPCODE"));
						}
					}
				}
			}

			sb = new StringBuffer("SELECT HOUSE_STAT FROM ACRM_F_CI_PER_FAMILY ");
			sb.append(" WHERE CUST_ID = '"+custId+"'");
			query = new QueryHelper(sb.toString(), ds.getConnection());
			tempRowsList = (List<HashMap<String, Object>>) query.getJSON().get("data");
			if(tempRowsList != null && tempRowsList.size() > 0){
				if(tempRowsList.get(0).get("HOUSE_STAT")!= null && !"".equals(tempRowsList.get(0).get("HOUSE_STAT").toString())){
					String isRent = tempRowsList.get(0).get("HOUSE_STAT").toString();
					resultMap.put("isRent",isRent);
				}
			}
			
			//联系方式类型、联系方式内容
			sb = new StringBuffer("SELECT CONTMETH_TYPE,CONTMETH_INFO FROM ACRM_F_CI_CONTMETH ");
			sb.append(" WHERE CUST_ID = '"+custId+"'");
			query = new QueryHelper(sb.toString(), ds.getConnection());
			tempRowsList = (List<HashMap<String, Object>>) query.getJSON().get("data");
			if(tempRowsList != null && tempRowsList.size() > 0){
				int count = 1;
				for(int i = 0; i < tempRowsList.size(); i++){
					if(tempRowsList.get(i).get("CONTMETH_TYPE")!=null 
						&& !"".equals(tempRowsList.get(i).get("CONTMETH_TYPE").toString())){
						String contmethType = tempRowsList.get(i).get("CONTMETH_TYPE").toString();
						//联系方式内容：移动电话归属国家-移动电话国际区号-移动电话电话号
						//联系方式内容：固定电话：国家-下拉默认国家区号-区域码-电话号
						
						if(tempRowsList.get(i).get("CONTMETH_INFO") != null && 
								!"".equals(tempRowsList.get(i).get("CONTMETH_INFO").toString())){
							String contmethInfo = tempRowsList.get(i).get("CONTMETH_INFO").toString();
							if(contmethType.equals("102")){//移动电话
								if(contmethInfo != null && !"".equals(contmethInfo)){
									String[] contmethInfos = contmethInfo.split("-");
									if(contmethInfos.length == 2){
										resultMap.put("mbPhone",  contmethInfos[0]);
										resultMap.put("QUYUMA",  contmethInfos[0]);
										resultMap.put("mbPHONENUM",  contmethInfos[1]);
									}
								}
							}else if(contmethType.equals("204")){//住宅电话
								if(count==1){
									if(contmethInfo != null && !"".equals(contmethInfo)){
										String[] contmethInfos = contmethInfo.split("-");
										if(contmethInfos.length == 4){
											resultMap.put("ORTHERPHONE", contmethType);
											resultMap.put("PHONE_CITIZENSHIP", contmethInfos[0]);//其他电话1国籍
											resultMap.put("QUYUMA1", contmethInfos[1]);//其他电话1国际区号
											resultMap.put("QUYUMA2", contmethInfos[2]);//其他电话1区域码
											resultMap.put("PHONENUM1", contmethInfos[3]);//其他电话1电话号
											count++;
										}
									}
								}else{
									if(contmethInfo != null && !"".equals(contmethInfo)){
										String[] contmethInfos = contmethInfo.split("-");
										if(contmethInfos.length == 4){
											resultMap.put("ORTHERPHONE1", contmethType);
											resultMap.put("PHONE_CITIZENSHIP1", contmethInfos[0]);//其他电话2国籍
											resultMap.put("QUYUMA3", contmethInfos[1]);//其他电话2国际区号
											resultMap.put("QUYUMA4", contmethInfos[2]);//其他电话2区域码
											resultMap.put("PHONENUM2", contmethInfos[3]);//其他电话2电话号
											count++;
										}
									}
								}
							}else if(contmethType.equals("203")){//办公电话
								if(count==1){
									if(contmethInfo != null && !"".equals(contmethInfo)){
										String[] contmethInfos = contmethInfo.split("-");
										if(contmethInfos.length == 4){
											resultMap.put("ORTHERPHONE", contmethType);
											resultMap.put("PHONE_CITIZENSHIP", contmethInfos[0]);//其他电话1国籍
											resultMap.put("QUYUMA1", contmethInfos[1]);//其他电话1国际区号
											resultMap.put("QUYUMA2", contmethInfos[2]);//其他电话1区域码
											resultMap.put("PHONENUM1", contmethInfos[3]);//其他电话1电话号
											count++;
										}
									}
								}else{
									if(contmethInfo != null && !"".equals(contmethInfo)){
										String[] contmethInfos = contmethInfo.split("-");
										if(contmethInfos.length == 4){
											resultMap.put("ORTHERPHONE1", contmethType);
											resultMap.put("PHONE_CITIZENSHIP1", contmethInfos[0]);//其他电话2国籍
											resultMap.put("QUYUMA3", contmethInfos[1]);//其他电话2国际区号
											resultMap.put("QUYUMA4", contmethInfos[2]);//其他电话2区域码
											resultMap.put("PHONENUM2", contmethInfos[3]);//其他电话2电话号
											count++;
										}
									}
								}
							}else if(contmethType.equals("999")){//其他电话
								if(count==1){
									if(contmethInfo != null && !"".equals(contmethInfo)){
										String[] contmethInfos = contmethInfo.split("-");
										if(contmethInfos.length == 4){
											resultMap.put("ORTHERPHONE", contmethType);
											resultMap.put("PHONE_CITIZENSHIP", contmethInfos[0]);//其他电话1国籍
											resultMap.put("QUYUMA1", contmethInfos[1]);//其他电话1国际区号
											resultMap.put("QUYUMA2", contmethInfos[2]);//其他电话1区域码
											resultMap.put("PHONENUM1", contmethInfos[3]);//其他电话1电话号
											count++;
										}
									}
								}else{
									if(contmethInfo != null && !"".equals(contmethInfo)){
										String[] contmethInfos = contmethInfo.split("-");
										if(contmethInfos.length == 4){
											resultMap.put("ORTHERPHONE1", contmethType);
											resultMap.put("PHONE_CITIZENSHIP1", contmethInfos[0]);//其他电话2国籍
											resultMap.put("QUYUMA3", contmethInfos[1]);//其他电话2国际区号
											resultMap.put("QUYUMA4", contmethInfos[2]);//其他电话2区域码
											resultMap.put("PHONENUM2", contmethInfos[3]);//其他电话2电话号
											count++;
										}
									}
								}
							}else if(contmethType.equals("500")){//电子邮箱E-mail
								resultMap.put("EMAIL", contmethInfo);
							}
						}
					}
				}
			}
			
			//与我行关联关系
			sb = new StringBuffer("SELECT STAFFIN,SOURCE_CHANNEL,RISK_NATION_CODE FROM ACRM_F_CI_CUSTOMER ");
			sb.append(" WHERE CUST_ID = '"+custId+"'");
			query = new QueryHelper(sb.toString(), ds.getConnection());
			tempRowsList = (List<HashMap<String, Object>>) query.getJSON().get("data");
			if(tempRowsList != null && tempRowsList.size() > 0){
				//与我行关联关系(客户其实要的是：关联人属性（核心）)
				resultMap.put("RELATION1",tempRowsList.get(0).get("STAFFIN"));
				//来源渠道
				resultMap.put("SOURCECHANNEL",tempRowsList.get(0).get("SOURCE_CHANNEL"));
				//风险国别代码
				resultMap.put("RISK_NATION_CODE",tempRowsList.get(0).get("RISK_NATION_CODE"));
			}
			
			//所属客户经理
			sb = new StringBuffer("SELECT MGR_NAME FROM OCRM_F_CI_BELONG_CUSTMGR ");
			sb.append(" WHERE CUST_ID = '"+custId+"'");
			query = new QueryHelper(sb.toString(), ds.getConnection());
			tempRowsList = (List<HashMap<String, Object>>) query.getJSON().get("data");
			if(tempRowsList != null && tempRowsList.size() > 0){
				//客户经理编号
				if(tempRowsList.get(0).get("MGR_NAME")!=null &&
						!"".equals(tempRowsList.get(0).get("MGR_NAME").toString())){
					String MGR_NAME = tempRowsList.get(0).get("MGR_NAME").toString();
					resultMap.put("customManager",MGR_NAME);
				}
				
			}
		
			if(this.json != null){
				this.json.clear();
			}else{
				this.json = new HashMap<String,Object>(); 
				JSONArray jsonArray1 = new JSONArray();
				jsonArray1.add(resultMap);
			    JSONObject json1 = new JSONObject();
			    json1.put("data", jsonArray1);
//				System.err.println("json:["+json1.toString()+"]");
				this.json.put("json",json1);
			}
		  }	
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1,2,"1002",e.getMessage());
		}
		return "success";
	
	}
    
	 /**
	 * 查询客户信息第二页基本信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String queryComsecond(){
		try {
			ActionContext ctx = ActionContext.getContext();
	        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	        String custId = request.getParameter("custId");
	        
			//银行信息
			StringBuffer sb = new StringBuffer("SELECT INOUT_FLAG,IS_OPEN_CARD,CARD_CATLG,CARD_TYPE,CARD_FC, ");
			sb.append(" IS_DFTLMTD_ATM,LMTAMT_D_ATM,IS_DFTCNT_ATM,LMTCNT_D_ATM,IS_DFTLMTY_ATM,LMTAMT_Y_ATM,  ");
			sb.append(" IS_DFTLMT_POS,LMTAMT_POS,IS_OPEN_EBK,IS_NETBK,IS_UKEY,IS_MSG_NETBK,IS_TELBK,IS_PHONE, ");
			sb.append(" IS_MSG_PHONE,IS_DFTLMTD_EB,LMTAMT_D_EB,IS_DFTCNT_EB,LMTCNT_D_EB,IS_DFTLMTY_EB, ");
			sb.append(" LMTAMT_Y_EB,IS_CHK,IS_EQU_EMAIL,IS_CHG_NOTICE,IS_EQU_ADD ");
			sb.append(" FROM OCRM_F_CI_OPEN_INFO ");
			sb.append(" WHERE CUST_ID = '"+custId+"' ");
			Map<String, Object> resultMap = new HashMap<String, Object>();
			QueryHelper query = new QueryHelper(sb.toString(), ds.getConnection());
			List<HashMap<String, Object>> tempRowsList = (List<HashMap<String, Object>>) query.getJSON().get("data");
			
			resultMap.put("custId",custId);
			if(tempRowsList != null && tempRowsList.size() > 0){
				if(tempRowsList.get(0).get("INOUT_FLAG")!= null && 
						!"".equals(tempRowsList.get(0).get("INOUT_FLAG").toString())){
					String inoutFlag = tempRowsList.get(0).get("INOUT_FLAG").toString();
					if(inoutFlag.equals("D")){
						resultMap.put("cusCategory","D");
					}else if(inoutFlag.equals("F")){
						resultMap.put("cusCategory","F");
					}
					
					StringBuffer sb2 = new StringBuffer("SELECT ACT_TYPE FROM OCRM_F_CI_ACCOUNT_INFO ");
					sb2.append(" WHERE CUST_ID = '"+custId+"' ");
					QueryHelper query2 = new QueryHelper(sb2.toString(), ds.getConnection());
					List<HashMap<String, Object>> tempRowsList2 = (List<HashMap<String, Object>>) query2.getJSON().get("data");
					
					if(tempRowsList2!= null && tempRowsList2.size()>0){
						resultMap.put("DFCheckbox",JSONArray.fromObject(tempRowsList2));
					}
				}
				
				//邮寄地址与居住地址相同
				if(tempRowsList.get(0).get("IS_EQU_ADD")!= null && 
						!"".equals(tempRowsList.get(0).get("IS_EQU_ADD").toString())){
					String isEquAdd = tempRowsList.get(0).get("IS_EQU_ADD").toString();
					resultMap.put("same",isEquAdd);
				}
				
				//借记卡申请
				if(tempRowsList.get(0).get("IS_OPEN_CARD")!= null &&
						!"".equals(tempRowsList.get(0).get("IS_OPEN_CARD").toString())){
					//是否开卡
					String isOpenCard = tempRowsList.get(0).get("IS_OPEN_CARD").toString();
					resultMap.put("isOpenCard",isOpenCard);
					if(isOpenCard.equals("1")){
						//卡种
						if(tempRowsList.get(0).get("CARD_CATLG")!= null && 
								!"".equals(tempRowsList.get(0).get("CARD_CATLG").toString())){
							resultMap.put("cardType",tempRowsList.get(0).get("CARD_CATLG").toString());
						}
						//卡类型
						if(tempRowsList.get(0).get("CARD_TYPE")!= null && 
								!"".equals(tempRowsList.get(0).get("CARD_TYPE").toString())){
							resultMap.put("cardType1_0",tempRowsList.get(0).get("CARD_TYPE").toString());
						}
						//卡样式
						if(tempRowsList.get(0).get("CARD_FC")!= null && 
								!"".equals(tempRowsList.get(0).get("CARD_FC").toString())){
							resultMap.put("cardType2_0",tempRowsList.get(0).get("CARD_FC").toString());
						}
						
						// ATM转账限额设置
						// 每日累计限额
						if(tempRowsList.get(0).get("IS_DFTLMTD_ATM")!= null && 
								!"".equals(tempRowsList.get(0).get("IS_DFTLMTD_ATM").toString())){
							String IS_DFTLMTD_ATM = tempRowsList.get(0).get("IS_DFTLMTD_ATM").toString();
							resultMap.put("ATMDayLimitDefault",IS_DFTLMTD_ATM);
							if(IS_DFTLMTD_ATM.equals("0")){
								if(tempRowsList.get(0).get("LMTAMT_D_ATM")!=null &&
										!"".equals(tempRowsList.get(0).get("LMTAMT_D_ATM").toString())){}
								resultMap.put("ATMDayLimit",tempRowsList.get(0).get("LMTAMT_D_ATM").toString());
							}
						}
						
						// 每日累计笔数
						if(tempRowsList.get(0).get("IS_DFTCNT_ATM")!= null && 
								!"".equals(tempRowsList.get(0).get("IS_DFTCNT_ATM").toString())){
							String IS_DFTCNT_ATM = tempRowsList.get(0).get("IS_DFTCNT_ATM").toString();
							resultMap.put("ATMDayCountDefault",IS_DFTCNT_ATM);
							if(IS_DFTCNT_ATM.equals("0")){
								if(tempRowsList.get(0).get("LMTCNT_D_ATM")!=null &&
										!"".equals(tempRowsList.get(0).get("LMTCNT_D_ATM").toString())){}
								resultMap.put("ATMDayLimitCount",tempRowsList.get(0).get("LMTCNT_D_ATM").toString());
							}
						}
						
						// 每年累计限额
						if(tempRowsList.get(0).get("IS_DFTLMTY_ATM")!= null && 
								!"".equals(tempRowsList.get(0).get("IS_DFTLMTY_ATM").toString())){
							String IS_DFTLMTY_ATM = tempRowsList.get(0).get("IS_DFTLMTY_ATM").toString();
							resultMap.put("ATMYearLimitDefault",IS_DFTLMTY_ATM);
							if(IS_DFTLMTY_ATM.equals("0")){
								if(tempRowsList.get(0).get("LMTAMT_Y_ATM")!=null &&
										!"".equals(tempRowsList.get(0).get("LMTAMT_Y_ATM").toString())){}
								resultMap.put("ATMYearLimit",tempRowsList.get(0).get("LMTAMT_Y_ATM").toString());
							}
						}
						
						// POS消费限额设置
						if(tempRowsList.get(0).get("IS_DFTLMT_POS")!= null && 
								!"".equals(tempRowsList.get(0).get("IS_DFTLMT_POS").toString())){
							String IS_DFTLMT_POS = tempRowsList.get(0).get("IS_DFTLMT_POS").toString();
							resultMap.put("POSDefault",IS_DFTLMT_POS);
							if(IS_DFTLMT_POS.equals("0")){
								if(tempRowsList.get(0).get("LMTAMT_POS")!=null &&
										!"".equals(tempRowsList.get(0).get("LMTAMT_POS").toString())){}
								resultMap.put("eachCustemLimit",tempRowsList.get(0).get("LMTAMT_POS").toString());
							}
						}
					}
				}
				
				//电子银行服务
				if(tempRowsList.get(0).get("IS_OPEN_EBK")!= null &&
						!"".equals(tempRowsList.get(0).get("IS_OPEN_EBK").toString())){
					//是否开网银
					String IS_OPEN_EBK = tempRowsList.get(0).get("IS_OPEN_EBK").toString();
					resultMap.put("dianziBank",IS_OPEN_EBK);
					if(IS_OPEN_EBK.equals("1")){
						// 网络银行
						if(tempRowsList.get(0).get("IS_NETBK")!= null && 
								!"".equals(tempRowsList.get(0).get("IS_NETBK").toString())){
							String IS_NETBK = tempRowsList.get(0).get("IS_NETBK").toString();
							resultMap.put("netBank",IS_NETBK);
							if(IS_NETBK.equals("1")){
								// ukey
								if(tempRowsList.get(0).get("IS_UKEY")!= null && 
										!"".equals(tempRowsList.get(0).get("IS_UKEY").toString())){
									String IS_UKEY = tempRowsList.get(0).get("IS_UKEY").toString();
									resultMap.put("ukey",IS_UKEY);
								}
								
								// 短信认证
								if(tempRowsList.get(0).get("IS_MSG_NETBK")!= null && 
										!"".equals(tempRowsList.get(0).get("IS_MSG_NETBK").toString())){
									String IS_MSG_NETBK = tempRowsList.get(0).get("IS_MSG_NETBK").toString();
									resultMap.put("shortMessage",IS_MSG_NETBK);
								}
							}
						}
						
						/*//电话银行
						if(tempRowsList.get(0).get("IS_TELBK")!= null && 
								!"".equals(tempRowsList.get(0).get("IS_TELBK").toString())){
							String IS_TELBK = tempRowsList.get(0).get("IS_TELBK").toString();
							resultMap.put("phoneBank",IS_TELBK);
						}*/
						
						//手机银行
						if(tempRowsList.get(0).get("IS_PHONE")!= null && 
								!"".equals(tempRowsList.get(0).get("IS_PHONE").toString())){
							String IS_PHONE = tempRowsList.get(0).get("IS_PHONE").toString();
							resultMap.put("mobileBank",IS_PHONE);
							if(IS_PHONE.equals("1")){
								//短信验证
								if(tempRowsList.get(0).get("IS_MSG_PHONE")!= null && 
										!"".equals(tempRowsList.get(0).get("IS_MSG_PHONE").toString())){
									String IS_MSG_PHONE = tempRowsList.get(0).get("IS_MSG_PHONE").toString();
									resultMap.put("shortMessage2",IS_MSG_PHONE);
								}
							}
						}
						
						//  日累计转账限额
						if(tempRowsList.get(0).get("IS_DFTLMTD_EB")!= null && 
								!"".equals(tempRowsList.get(0).get("IS_DFTLMTD_EB").toString())){
							String IS_DFTLMTD_EB = tempRowsList.get(0).get("IS_DFTLMTD_EB").toString();
							resultMap.put("dayAccLimitDefault",IS_DFTLMTD_EB);
							if(IS_DFTLMTD_EB.equals("0")){
								if(tempRowsList.get(0).get("LMTAMT_D_EB")!=null &&
										!"".equals(tempRowsList.get(0).get("LMTAMT_D_EB").toString())){}
								resultMap.put("dayAccSelfDefine",tempRowsList.get(0).get("LMTAMT_D_EB").toString());
							}
						}
						
					    // 日累计转账笔数
						if(tempRowsList.get(0).get("IS_DFTCNT_EB")!= null && 
								!"".equals(tempRowsList.get(0).get("IS_DFTCNT_EB").toString())){
							String IS_DFTCNT_EB = tempRowsList.get(0).get("IS_DFTCNT_EB").toString();
							resultMap.put("dayAccCountDefault",IS_DFTCNT_EB);
							if(IS_DFTCNT_EB.equals("0")){
								if(tempRowsList.get(0).get("LMTCNT_D_EB")!=null &&
										!"".equals(tempRowsList.get(0).get("LMTCNT_D_EB").toString())){}
								resultMap.put("dayCountSelfDefine",tempRowsList.get(0).get("LMTCNT_D_EB").toString());
							}
						}
						
						// 年累计转账限额
						if(tempRowsList.get(0).get("IS_DFTLMTY_EB")!= null && 
								!"".equals(tempRowsList.get(0).get("IS_DFTLMTY_EB").toString())){
							String IS_DFTLMTY_EB = tempRowsList.get(0).get("IS_DFTLMTY_EB").toString();
							resultMap.put("yearAccLimitDefault",IS_DFTLMTY_EB);
							if(IS_DFTLMTY_EB.equals("0")){
								if(tempRowsList.get(0).get("LMTAMT_Y_EB")!=null &&
										!"".equals(tempRowsList.get(0).get("LMTAMT_Y_EB").toString())){}
								resultMap.put("yearAccSelfDefine",tempRowsList.get(0).get("LMTAMT_Y_EB").toString());
							}
						}
					}
				}	
				
				//电子对账单
				if(tempRowsList.get(0).get("IS_CHK")!= null &&
						!"".equals(tempRowsList.get(0).get("IS_CHK").toString())){
					String IS_CHK = tempRowsList.get(0).get("IS_CHK").toString();
					resultMap.put("elecState",IS_CHK);
					if(IS_CHK.equals("1")){
						//对账单是否同email
						if(tempRowsList.get(0).get("IS_EQU_EMAIL")!= null && 
								!"".equals(tempRowsList.get(0).get("IS_EQU_EMAIL").toString())){
							String IS_EQU_EMAIL = tempRowsList.get(0).get("IS_EQU_EMAIL").toString();
							resultMap.put("isEquEmail",IS_EQU_EMAIL);
						}
						//E-mail
						StringBuffer sb1 = new StringBuffer("SELECT CONTMETH_TYPE,CONTMETH_INFO FROM ACRM_F_CI_CONTMETH ");
						sb1.append(" WHERE CUST_ID = '"+custId+"' AND CONTMETH_TYPE = '106' ");
						QueryHelper query1 = new QueryHelper(sb1.toString(), ds.getConnection());
						List<HashMap<String, Object>> tempRowsList1 = (List<HashMap<String, Object>>) query1.getJSON().get("data");
						if(tempRowsList1 != null && tempRowsList1.size() > 0){
							resultMap.put("email",tempRowsList1.get(0).get("CONTMETH_INFO"));
						}
						
					}
				}
				
				//财务变动通知
				if(tempRowsList.get(0).get("IS_CHG_NOTICE")!= null && 
						!"".equals(tempRowsList.get(0).get("IS_CHG_NOTICE").toString())){
					String IS_CHG_NOTICE = tempRowsList.get(0).get("IS_CHG_NOTICE").toString();
					resultMap.put("chgNotice",IS_CHG_NOTICE);
				}
				
			}
			
			
			
			
			if(this.json != null){
				this.json.clear();
			}else{
				this.json = new HashMap<String,Object>(); 
				JSONArray jsonArray1 = new JSONArray();
				jsonArray1.add(resultMap);
			    JSONObject json1 = new JSONObject();
			    json1.put("data", jsonArray1);
//				System.err.println("json22222:["+json1.toString()+"]");
				this.json.put("json",json1);
			}
				
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1,2,"1002",e.getMessage());
		}
		return "success";
	}
	
	/**
	 * 直接复核
	 */
	public void directReview(){
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String orgId = auth.getUnitId();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html:charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String reviewNo = "";
		String reviewPsw = "";
		String cust_id = "";
		if(request.getParameter("reviewNo")!= null && !"".equals(request.getParameter("reviewNo").toString())){
			reviewNo = request.getParameter("reviewNo").toString();
		}
		if(request.getParameter("reviewPsw")!= null && !"".equals(request.getParameter("reviewPsw").toString())){
			reviewPsw = request.getParameter("reviewPsw").toString();
		}
		if(request.getParameter("cust_id")!= null && !"".equals(request.getParameter("cust_id").toString())){
			cust_id = request.getParameter("cust_id").toString();
		}
		if(reviewNo.length() == 7){
	    	reviewNo = reviewNo.substring(0, 3) + "N" + reviewNo.substring(3);
		}
		try {
			JSONObject retJso = new JSONObject();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String instance_id = "cus_" + cust_id + "_"+ sdf.format(new Date());
			StringBuffer sb1 = new StringBuffer(" SELECT 1 FROM  ADMIN_AUTH_ACCOUNT ");
			sb1.append(" WHERE ACCOUNT_NAME like '%"+ reviewNo +"' ");
			sb1.append(" AND PASSWORD = '"+ EndecryptUtils.encrypt(reviewPsw) +"' ");
			QueryHelper query1 = new QueryHelper(sb1.toString(), ds.getConnection());
			List<HashMap<String, Object>> tempRowsList1 = (List<HashMap<String, Object>>) query1.getJSON().get("data");
			if(tempRowsList1.size()<1){
				retJso.put("success", false);
				retJso.put("msg", "用户名或密码不正确！");
			}else{
			    
				StringBuffer sb2 = new StringBuffer(" select b.account_name,b.user_name from ADMIN_AUTH_ACCOUNT_ROLE a ");
				sb2.append(" inner join admin_auth_account b on a.account_id = b.id ");
				sb2.append(" where role_id in ('301') and b.org_id='"+ orgId +"' ");
				sb2.append(" and b.account_name like '%"+ reviewNo +"' ");
				sb2.append("  and b.password = '"+ EndecryptUtils.encrypt(reviewPsw) +"' ");
				QueryHelper query2 = new QueryHelper(sb2.toString(), ds.getConnection());
				List<HashMap<String, Object>> tempRowsList2 = (List<HashMap<String, Object>>) query2.getJSON().get("data");
				if(tempRowsList2.size() < 1){
					retJso.put("success", false);
					retJso.put("msg", "该用户没有权限复核！");
				}else{
					StringBuffer sb3 = new StringBuffer();
					String transactor = tempRowsList2.get(0).get("ACCOUNT_NAME").toString();
					String user_name = tempRowsList2.get(0).get("USER_NAME").toString();
					UNIDProducer uid = new UNIDProducer();
					String nodeActionID1 =  uid.getUNID() ;
					String actTime = DatetimeUtils.getNowDateTimeString();
					String actionName = user_name+"于"+actTime+"直接复核";
				    sb3.append(" insert into wf_node_action_recordEND( ");
					sb3.append(" NodeActionID,InstanceID,NodeID,NodeName,TransActor, ");
					sb3.append(" ActTime,ActionName,SendTo,RouteID,RouteName, ");
					sb3.append(" NextNodeID,NextNodeName,orgid,roleid) values( ");
					sb3.append("'"+nodeActionID1 + "','"+ instance_id +"','','OP复核','");
					sb3.append(transactor+ "','"+actTime+"','"+actionName+"','-',");
					sb3.append("'','','','','"+orgId+"','' )");
					Connection conn = ds.getConnection();
					Statement stat = conn.createStatement();
					stat.executeUpdate(sb3.toString());
					
					StringBuffer sb5 = new StringBuffer();
					String nodeActionID2 =  uid.getUNID() ;
					String actionName2 = auth.getUsername() + "于"+ actTime +
							"完成当前节点办理;下一办理环节：OP复核;提交下一办理人："+ user_name +";";
					sb5.append(" insert into wf_node_action_recordEND( ");
					sb5.append(" NodeActionID,InstanceID,NodeID,NodeName,TransActor, ");
					sb5.append(" ActTime,ActionName,SendTo,RouteID,RouteName, ");
					sb5.append(" NextNodeID,NextNodeName,orgid,roleid) values( ");
					sb5.append("'"+nodeActionID2 + "','"+ instance_id +"','','发起人','");
					sb5.append(auth.getUsername()+ "','"+actTime+"','"+actionName2+"','-',");
					sb5.append("'','','','','"+auth.getUnitId()+"','' )");
					stat.executeUpdate(sb5.toString());

					//办结任务列表中插入数据
					String custName = request.getParameter("cust_name");
					String jobName = "开户_" + custName;// 自定义流程名称
					StringBuffer sb4 = new StringBuffer();
					sb4.append(" INSERT INTO WF_INSTANCE_END(INSTANCEID,WFJOBNAME,WFNAME,WFSTARTTIME, ")
					.append(" WFENDTIME,SPSTATUS,WFSIGN,AUTHOR,ALLREADERSLIST) ")
					.append(" VALUES('"+ instance_id +"','"+ jobName +"','开户_直接复核', ")
					.append(" to_char(sysdate,'yyyy-MM-dd hh24:mi:ss'), to_char(sysdate,'yyyy-MM-dd hh24:mi:ss'), ")
					.append(" '1','openAccount','"+ transactor +"','"+ transactor +"') ");
					stat.executeUpdate(sb4.toString());
					
					/*StringBuffer sb5 = new StringBuffer();
					sb5.append(" INSERT INTO WF_MAIN_RECORD( INSTANCEID,BIZSEQNO ) ")
					.append(" VALUES('" + instance_id+ "','bizseqno_"+ instance_id +"')");
					stat.executeUpdate(sb5.toString());
					
					
					StringBuffer sb6 = new StringBuffer();
					sb6.append(" INSERT INTO WF_INSTANCE_NODE_PROPERTY(INSTANCEID,NODEID,NODENAME) ")
					.append(" VALUES('" + instance_id+ "','1000012','OP复核')");
					stat.executeUpdate(sb6.toString());*/
					
					retJso.put("success", true);
					retJso.put("msg", "复核完成，请进行下一步操作！");
				}
			}
			this.json = (Map) retJso;
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    //卡类型
    public void getCardType(){
    	HttpServletRequest request = ServletActionContext.getRequest();
		try {
			List<Map<String, Object>> rstList = this.oneKey.getCardType(request.getParameter("name"));
			if (this.json != null) {
				this.json.clear();
			}else{
				this.json = new HashMap<String, Object>();
			}
			this.json.put("JSON", rstList);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    //卡样式
    public void getCardType2(){
    	HttpServletRequest request = ServletActionContext.getRequest();
	    HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html:charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		try {
			List<Map<String, Object>> rstList = this.oneKey.getCardType2(request.getParameter("name"));
			if (this.json != null) {
				this.json.clear();
			}else{
				this.json = new HashMap<String, Object>();
			}
			this.json.put("JSON", rstList);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    
    /**
	 * 获取下一指定办理人
	 * @param request
	 */
	public void getNextNodeUser(){
		String sendto = "";
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html:charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		JSONObject jsonStr = new JSONObject();
		try {
//			PrintWriter writer = response.getWriter();
			if(request.getParameter("instanceid")!= null &&
					!"".equals(request.getParameter("instanceid"))){
				String instanceid = request.getParameter("instanceid");
				StringBuilder sb = new StringBuilder();
				sb.append("SELECT t.SENDTO SENDTO,m.user_name USERNAME FROM WF_NODE_ACTION_RECORD t ")
				.append(" left join admin_auth_account m on t.sendto = m.account_name ")
				.append(" WHERE t.INSTANCEID = '"+instanceid+"'");
				
				QueryHelper query = new QueryHelper(sb.toString(),ds.getConnection());
				List<HashMap<String,Object>> tempRowList = (List<HashMap<String,Object>>)query.getJSON().get("data");
				if(tempRowList!= null && tempRowList.size() > 0){
					if(tempRowList.get(0).get("SENDTO")!= null &&
							!"".equals(tempRowList.get(0).get("SENDTO").toString())){
						sendto = tempRowList.get(0).get("SENDTO").toString();
						jsonStr.put("sendto", sendto);
					}
					if(tempRowList.get(0).get("USERNAME")!= null &&
							!"".equals(tempRowList.get(0).get("USERNAME").toString())){
						String userName = tempRowList.get(0).get("USERNAME").toString();
						jsonStr.put("userName", userName);
					}
					jsonStr.put("success", true);
				}
				
			}
//			writer.println(jsonStr.toString());
//			writer.flush();
//			writer.close();
			this.json = (Map) jsonStr;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	
	/**
	 * 查询客户预约信息
	 */
	public void checkCustOrderInfo(){
		HttpServletRequest request = ServletActionContext.getRequest();
	    HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html:charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		try {
			String serializeId = request.getParameter("serializeId");
			String flag = request.getParameter("flag");
			String orderNo = request.getParameter("orderNo");
			String custName = request.getParameter("custName");
			String identiType = request.getParameter("certtype");
			String identiNo = request.getParameter("certid");
			Map<String, Object> resMap = this.queryCustOrderInfoService.queryCustOrderInfoFromEcif(serializeId, flag, orderNo, custName, identiType, identiNo);
			if (this.json != null) {
				this.json.clear();
			}else{
				this.json = new HashMap<String, Object>();
			}
			this.json.put("JSON", resMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 来源渠道下拉列表
	 */
    public void getChannels(){
    	HttpServletRequest request = ServletActionContext.getRequest();
	    HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html:charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		try {
//			System.out.println(JSONSerializer.toJSON(request.getParameterMap()));
			List<Map<String, Object>> rstList = this.oneKey.getChannels();
			if (this.json != null) {
				this.json.clear();
			}else{
				this.json = new HashMap<String, Object>();
			}
			this.json.put("JSON", rstList);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
	 * 对私联系信息类型下拉列表
	 */
    public void getContmethTypes(){
    	HttpServletRequest request = ServletActionContext.getRequest();
	    HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html:charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		try {
			List<Map<String, Object>> rstList = this.oneKey.getContmethTypes();
			if (this.json != null) {
				this.json.clear();
			}else{
				this.json = new HashMap<String, Object>();
			}
			this.json.put("JSON", rstList);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
	 * 国籍下拉列表
	 */
    public void getNationNalityStore(){
    	HttpServletRequest request = ServletActionContext.getRequest();
	    HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html:charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		try {
			List<Map<String, Object>> rstList = this.oneKey.getNationNalityList();
			if (this.json != null) {
				this.json.clear();
			}else{
				this.json = new HashMap<String, Object>();
			}
			this.json.put("JSON", rstList);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    /**
     * 国际区号
     */
    public void getglobalRoamingStore(){
    	HttpServletRequest request = ServletActionContext.getRequest();
    	HttpServletResponse response = ServletActionContext.getResponse();
    	response.setContentType("text/html:charset=utf-8");
    	response.setCharacterEncoding("UTF-8");
    	try {
    		List<Map<String, Object>> rstList = this.oneKey.getglobalRoamingStore();
    		if (this.json != null) {
    			this.json.clear();
    		}else{
    			this.json = new HashMap<String, Object>();
    		}
    		this.json.put("JSON", rstList);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    
    /**
	 * 发证机关所在地下拉列表
	 */
    public void getOrgRegionStore(){
    	HttpServletRequest request = ServletActionContext.getRequest();
	    HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html:charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		try {
			List<Map<String, Object>> rstList = this.oneKey.getOrgRegionList();
			if (this.json != null) {
				this.json.clear();
			}else{
				this.json = new HashMap<String, Object>();
			}
			this.json.put("JSON", rstList);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
	 * 风险国别下拉列表
	 */
    public void getRiskCountryStore(){
    	HttpServletRequest request = ServletActionContext.getRequest();
	    HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html:charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		try {
			List<Map<String, Object>> rstList = this.oneKey.getRiskCountryList();
			if (this.json != null) {
				this.json.clear();
			}else{
				this.json = new HashMap<String, Object>();
			}
			this.json.put("JSON", rstList);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    
    /**
     * 客户经理放大镜
     */
    public void getCustomerUserManager () {
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String org_id = request.getParameter("org_id");
		
		StringBuffer sb = new StringBuffer(); 	
		sb.append(" SELECT T.ID USER_ID,T.ACCOUNT_NAME USER_CODE,T.USER_NAME USER_NAME, ");
		sb.append(" T.ORG_ID ORG_ID,O.ORG_NAME ORG_NAME FROM V_OPENACC_MGR T ");
		sb.append(" LEFT JOIN ADMIN_AUTH_ORG O ON O.ORG_ID = T.ORG_ID ");
		sb.append(" WHERE 1=1 ");
		
		if(org_id != null && (!"".equals(org_id)) && (!org_id.equals("500"))){
			sb.append(" AND (O.ORG_ID = '"+ org_id+"'  OR O.UP_ORG_ID = '"+ org_id+"')");
		}

		for(String key : this.getJson().keySet()){
    		if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){	
				if(null!=key&&key.equals("USER_NAME")){
					sb.append(" AND (T.USER_NAME LIKE '%"+ this.getJson().get(key) +"%' OR T.ACCOUNT_NAME = '"+this.getJson().get(key)+"')");
				}
    		}
		}
		
		/*sb.append(" SELECT T1.USER_ID,T1.CUST_MANAGER_NAME USER_NAME,T1.CUST_MANAGER_ID USER_CODE,");
		sb.append(" T1.AFFI_INST_ID ORG_ID,T2.ORG_NAME ORG_NAME");
		sb.append(" FROM OCRM_F_CM_CUST_MGR_INFO T1 ");
		sb.append(" LEFT JOIN ADMIN_AUTH_ORG T2 ON T2.ORG_ID = T1.AFFI_INST_ID ");
		sb.append(" WHERE 1=1 ");
		
		if(org_id != null && (!"".equals(org_id)) && (!org_id.equals("500"))){
			sb.append(" AND (T2.ORG_ID = '"+ org_id+"'  OR T2.UP_ORG_ID = '"+ org_id+"')");
		}

		for(String key : this.getJson().keySet()){
    		if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){	
				if(null!=key&&key.equals("USER_NAME")){
					sb.append(" AND (T1.CUST_MANAGER_NAME LIKE '%"+ this.getJson().get(key) +"%' OR T1.CUST_MANAGER_ID = '"+this.getJson().get(key)+"')");
				}
    		}
		}*/
    	
    	SQL=sb.toString();
		datasource = ds;
		super.index();
    }
    
    /**
     * 为了获取客户经理数据，不可重写prepare方法！！
     */
    public void prepare () {}
    
    

    /**
     * 将CRM系统证件类型转换成电信黑名单的证件类型
     * @param str_identType
     * @return
     */
    private String convertChinaNetIdentType(String str_identType) {
		if(StringUtils.isEmpty(str_identType)){
			return "99";
		}else{
			if(str_identType.equals("0")){//境内居民身份证
				str_identType = "01";
			}else if(str_identType.equals("1")){//户口簿
				str_identType = "06";
			}else if(str_identType.equals("2")){//境外公民护照
				str_identType = "03";
			}else if(str_identType.equals("3")){//军官证
				str_identType = "04";
			}else if(str_identType.equals("5")){//港澳居民来往内地通行证
				str_identType = "07";
			}else if(str_identType.equals("6") || str_identType.equals("X24")){//台湾同胞来往内地通行证||临时台胞证
				str_identType = "10";
			}else if(str_identType.equals("7")){//临时身份证
				str_identType = "11";
			}else if(str_identType.equals("8")){//外国人居留证 X14
				str_identType = "12";
			}else if(str_identType.equals("14")){//武警身份证件
				str_identType = "08";
			}else{
				return "99";
			}
			return str_identType;
		}
	}
    
    /**
     * 上传文件
     * @throws IOException
     */
    public void uploadFile() throws IOException{
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	log.info("用户："+auth.getUserId()+" 开始上传文件......");
    	HttpServletResponse response = ServletActionContext.getResponse();
    	HttpServletRequest request = ServletActionContext.getRequest();
    	Map<String,Object> modelMap = new HashMap<String, Object>();
    	
    	String beforeFileName = FileTypeConstance.getSystemProperty("onekeySysupload");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		try{
			File dir = new File(beforeFileName);
			if(!dir.exists()){//如果目录不存在就新增一个目录
				dir.mkdir();
			}
			String[] fileNameList = dir.list();
			if(fileNameList == null || fileNameList.length == 0){//传进来的第一个文件
				String myname = request.getParameter("myname");
		    	if(!StringUtils.isEmpty(myname)){
		    		String beMyname = myname.substring(0,myname.lastIndexOf("."));
		    		String laMyname = myname.substring(myname.lastIndexOf(".")+1);
		    		String realName = beMyname+"_1." +laMyname;
		    		MultiPartRequestWrapper rap = (MultiPartRequestWrapper)(request);
					File oldfile = rap.getFiles("mypath")[0];//获取上传的新文件
		    		File newFile = new File(beforeFileName+"/"+realName);
		    		FileUtils.moveFile(oldfile,newFile);
		    		modelMap.put("success", true);
					modelMap.put("msg", "上传成功");
					out.write(JSONSerializer.toJSON(modelMap).toString());
					log.info("用户："+auth.getUserId()+" 上传文件成功");
		    	}
			}else{
				if(fileNameList.length >= 1){
					int maxFileSort = Integer.parseInt(fileNameList[0].substring(fileNameList[0].lastIndexOf("_")+1,fileNameList[0].lastIndexOf(".")));
					for (int i = 1; i < fileNameList.length; i++) {
						int sortNum = Integer.parseInt(fileNameList[i].substring(fileNameList[i].lastIndexOf("_")+1,fileNameList[i].lastIndexOf(".")));
						if(sortNum > maxFileSort){
							maxFileSort = sortNum;
						}
					}
					String myname = request.getParameter("myname");
			    	if(!StringUtils.isEmpty(myname)){
			    		String beMyname = myname.substring(0,myname.lastIndexOf("."));
			    		String laMyname = myname.substring(myname.lastIndexOf(".")+1);
			    		String realName = beMyname+"_"+(maxFileSort+1)+ "." +laMyname;
			    		MultiPartRequestWrapper rap = (MultiPartRequestWrapper)(request);
						File oldfile = rap.getFiles("mypath")[0];//获取上传的新文件
			    		File newFile = new File(beforeFileName+"/"+realName);
			    		FileUtils.moveFile(oldfile,newFile);
			    		modelMap.put("success", true);
						modelMap.put("msg", "上传成功");
						out.write(JSONSerializer.toJSON(modelMap).toString());
						log.info("用户："+auth.getUserId()+" 上传文件成功");
			    	}
				}
			}
		}catch (Exception e){
			log.error("用户：" + auth.getUserId() + " 上传文件失败*****************************");
			e.printStackTrace();
			modelMap.put("success", false);
			modelMap.put("msg", "上传失败，失败原因："+e.getMessage());
			out.write(JSONSerializer.toJSON(modelMap).toString());
		}
    }
    
    /**
     * 下载文件
     * @throws IOException
     */
    public String downloadFile() throws IOException{
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	log.info("用户："+auth.getUserId()+" 开始下载文件......");
    	HttpServletResponse response = ServletActionContext.getResponse();
    	Map<String,Object> modelMap = new HashMap<String, Object>();
    	String beforeFileName = FileTypeConstance.getSystemProperty("onekeySysupload");
		try{
			File dir = new File(beforeFileName);
			if(!dir.exists()){//如果目录不存在就报错
				modelMap.put("success", false);
				modelMap.put("msg", "找不到下载路径文件夹");
			}
			String[] fileNameList = dir.list();
			if(fileNameList.length >= 1){
				int maxFileSort = Integer.parseInt(fileNameList[0].substring(fileNameList[0].lastIndexOf("_")+1,fileNameList[0].lastIndexOf(".")));
				String maxFileName = fileNameList[0];
				for (int i = 1; i < fileNameList.length; i++) {
					int sortNum = Integer.parseInt(fileNameList[i].substring(fileNameList[i].lastIndexOf("_")+1,fileNameList[i].lastIndexOf(".")));
					if(sortNum > maxFileSort){
						maxFileSort = sortNum;
						maxFileName = fileNameList[i];
					}
				}
				File newFile = new File(beforeFileName+"/"+ maxFileName);
				//设置下载文件的类型为任意类型
				response.setContentType("application/x-msdownload");
				//添加下载文件的头信息。此信息在下载时会在下载面板上显示
				response.addHeader("Content-Disposition", "attachment;"+"filename="+new String(maxFileName.getBytes("GBK"),"ISO8859_1"));
				// 添加文件的大小信息
				response.setContentLength((int) newFile.length());
				// 获得输出网络流
				OutputStream output = response.getOutputStream();
				FileInputStream fis = new FileInputStream(newFile);
				byte[] buffer = new byte[1024];
				int i = 0;
				while ((i = fis.read(buffer)) != -1) {
					output.write(buffer, 0, i);
				}
				output.flush();
				fis.close();
				output.close();
				log.info("用户："+auth.getUserId()+" 下载成功");
			}
		}catch (Exception e){
			log.error("用户：" + auth.getUserId() + " 下载文件失败*****************************");
			e.printStackTrace();
		}
		return "success";
    }
    
    
    /**
     * 开通卡的时候需要的一些信息，如端口号，卡类型校验规则等
     */
    public void getCardOpenInfo(){
    	this.json = this.oneKey.getCardOpenInfo();
    }
    
        /**
     * 获取ID卡具端口号
     * 2017-11-21
     */
    public void getICPort(){
    	this.json = this.oneKey.getICPort();
    }
    
    /**
	 *  证件、境内外、账户类型的校验规则
	 */
	public void checkAccountAuth(){
		HttpServletRequest request = ServletActionContext.getRequest();
		//证件类型、境内外的校验规则
		Map<String, Object> resultMap1 = oneKey.getIdentAccountTypeList("DOF");
		//境内外、账户类型的校验规则
		Map<String, Object> resultMap2 = oneKey.getIdentAccountTypeList("AT");
		//境内外、账户类型的校验规则,账户-银行服务关系
		Map<String, Object> resultMap3 = oneKey.getIdentAccountTypeList("CT");
		Map<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put("resultMap1", resultMap1);
		resultMap.put("resultMap2", resultMap2);
		resultMap.put("resultMap3", resultMap3);
		if(this.json != null ){
			this.json.clear();
		}else{
			this.json = new HashMap<String,Object>();
		}
		this.json.put("JSON", resultMap);
	}
    
    /**
     * 查询卡类型和卡号的关系
     */
    public void getCardTypeCheckRelationship(){
    	this.json = this.oneKey.getCardTypeCheckRelationship();
    }
    
    
    
    /**
     * 校验是否是已开户的联名户
     */
    public void validCustHadJoined(){
    	
//    	this.json = this.oneKey.validCustHadJoined();
    }
    
    
}
