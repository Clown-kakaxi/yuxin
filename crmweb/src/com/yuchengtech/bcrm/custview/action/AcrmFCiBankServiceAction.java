package com.yuchengtech.bcrm.custview.action;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.axis2.databinding.types.soapencoding.Array;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custview.model.AcrmFCiBankService;
import com.yuchengtech.bcrm.custview.model.AcrmFCiContmeth;
import com.yuchengtech.bcrm.custview.service.AcrmFCiBankServiceService;
import com.yuchengtech.bcrm.model.OcrmSysLookupItem;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.crm.exception.BizException;
/**
 * 对私客户视图银行服务信息
 * @author agile
 *
 */
@SuppressWarnings("serial")
@Action("/acrmFCiBankService")
public class AcrmFCiBankServiceAction extends CommonAction{
	@Autowired
	private AcrmFCiBankServiceService service;
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
	public void init(){
		model = new AcrmFCiBankService();
		setCommonService(service);
	}
	
	
	public void getCardNo(){
    	HttpServletRequest request = ServletActionContext.getRequest();
	    HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html:charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		try {
			List<Map<String, Object>> rstList = this.service.getCardNo(request.getParameter("custId"));
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
	
	
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
	    request = (HttpServletRequest) ctx
	             .get(ServletActionContext.HTTP_REQUEST);
	    String customerId = request.getParameter("custId");
	    String cardNo = request.getParameter("cardNo");
	    String old = request.getParameter("old");//复核流程数据查询
		StringBuilder sb = new StringBuilder();
		sb.append( "SELECT CUS.CUST_ID AS CUST_ID,"+
			       "NVL(BS.IS_CARD_APPLY,OP.IS_OPEN_CARD) AS IS_CARD_APPLY,"+
			       "NVL(DECODE(BS.ATM_LIMIT,NULL,NULL,1),OP.IS_DFTLMTD_ATM) as IS_ATM_HIGH,"+
			       "NVL(BS.ATM_HIGH,OP.LMTAMT_D_ATM) AS ATM_HIGH,"+
			       "NVL(BS.IS_DFTCNT_ATM,OP.IS_DFTCNT_ATM) AS IS_DFTCNT_ATM,"+
			       "NVL(BS.IS_DFTCNT_ATM,OP.LMTCNT_D_ATM) AS LMTCNT_D_ATM,"+
			       "NVL(BS.IS_DFTLMTY_ATM,OP.IS_DFTLMTY_ATM) AS IS_DFTLMTY_ATM,"+
			       "NVL(BS.LMTAMT_Y_ATM,OP.LMTAMT_Y_ATM) AS LMTAMT_Y_ATM,"+
			       "NVL(DECODE(BS.POS_LIMIT,NULL,NULL,1),OP.IS_DFTLMT_POS),"+
			       "NVL(BS.POS_HIGH,OP.LMTAMT_POS) AS POS_HIGH,"+
			       "NVL(BS.IS_ELEBANK_SER,OP.IS_OPEN_EBK) AS IS_ELEBANK_SER,"+
			       "NVL(BS.IS_NT_BANK,OP.IS_NETBK) AS IS_NT_BANK,"+
			       "DECODE(BS.CUST_ID,NULL,OP.IS_UKEY,decode(BS.UKEY,null,0,1)) AS UKEY,"+
			       "decode(BS.CUST_ID,NULL,OP.IS_MSG_NETBK,decode(BS.MESSAGE_CODE,null,0,1)) AS MESSAGE_CODE,"+
			       "NVL(DECODE(BS.MOBILE_BANKING,NULL,NULL,1),OP.IS_PHONE) AS MOBILE_BANKING,"+
			       "decode(BS.CUST_ID,NULL,OP.IS_MSG_PHONE,null) AS IS_MSG_PHONE,"+
			       "NVL(BS.IS_DFTLMTD_EB,OP.IS_DFTLMTD_EB) AS IS_DFTLMTD_EB,"+
			       "NVL(BS.IS_DFTCNT_EB,OP.IS_DFTCNT_EB) AS IS_DFTCNT_EB,"+
			       "NVL(BS.IS_DFTLMTY_EB,OP.IS_DFTLMTY_EB) AS IS_DFTLMTY_EB,"+
			       "NVL(BS.LMTAMT_D_EB,OP.LMTAMT_D_EB) AS LMTAMT_D_EB,"+
			       "NVL(BS.LMTCNT_D_EB,OP.LMTCNT_D_EB) AS LMTCNT_D_EB,"+
			       "NVL(BS.LMTAMT_Y_EB,OP.LMTAMT_Y_EB) AS LMTAMT_Y_EB,"+
			       "decode(BS.CUST_ID,null,OP.IS_CHK,decode(BS.STATEMENTS,null,0,1)) AS STATEMENTS,"+
			       "decode(BS.CUST_ID,null,OP.IS_EQU_EMAIL,decode(BS.MAIL_ADDRESS,null,0,1)) AS MAIL_ADDRESS,"+
			     //  "decode(BS.CUST_ID,null,CO.CONTMETH_INFO,BS.MAIL) AS MAIL,"+
			       "NVL(DECODE(BS.CHANGE_NOTICE,NULL,NULL,1),OP.is_chg_notice) AS CHANGE_NOTICE, "+
			       "NVL(BS.CARDNO,OP.CARD_NO) AS CARD_NO,"+
			       "case when SUBSTR(TRIM(NVL(BS.CARDNO,OP.CARD_NO)), 7, 2) in ('55','66','88') then '1' "+ //普通卡
			       "when SUBSTR(TRIM(NVL(BS.CARDNO,OP.CARD_NO)), 7, 2) in ('10','11') then '0' end AS CARD1,"+ //特色卡
			       "case when SUBSTR(TRIM(NVL(BS.CARDNO,OP.CARD_NO)), 7, 2) ='55' then '1' "+//金卡
			       "when SUBSTR(TRIM(NVL(BS.CARDNO,OP.CARD_NO)), 7, 2) ='66' then '2' "+//白金卡
			       "when SUBSTR(TRIM(NVL(BS.CARDNO,OP.CARD_NO)), 7, 2) ='77' then '3' "+//钻石卡
			       "when SUBSTR(TRIM(NVL(BS.CARDNO,OP.CARD_NO)), 7, 2) in ('10','11') then '001' end AS CARD2,"+//数位生活卡
			       "case when SUBSTR(TRIM(NVL(BS.CARDNO,OP.CARD_NO) ), 7, 2) ='10' then '0011' "+ //粉蓝
			       "when SUBSTR(TRIM(NVL(BS.CARDNO,OP.CARD_NO)), 7, 2) ='11' then '0012' end AS CARD3 "+ //粉红
			       "FROM ACRM_F_CI_CUSTOMER CUS "+
			       /*LEFT JOIN OCRM_F_CI_CARD_DETAIL_INFO CA
				   ON CA.CUST_ID = CUS.CUST_ID*/
			       "LEFT JOIN ACRM_F_CI_BANK_SERVICE BS "+
			       "ON BS.CUST_ID = CUS.CUST_ID "+
			       "LEFT JOIN OCRM_F_CI_OPEN_INFO  OP "+
			       "ON CUS.CUST_ID = OP.CUST_ID "+
			     //  "LEFT JOIN ACRM_F_CI_CONTMETH CO "+
			     //  "ON CUS.CUST_ID = CO.CUST_ID "+
			     //  "WHERE CO.CONTMETH_TYPE = '106' ");
			       "WHERE ROWNUM = 1");
		if(customerId != null){
			sb.append(" and CUS.CUST_ID = '"+customerId+"'");
		}
		if(cardNo == null){
			StringBuilder sb2 = new StringBuilder();
			sb2.append("SELECT NVL(BS.CARDNO,OP.CARD_NO) AS CARD_NO FROM  ACRM_F_CI_CUSTOMER CUS LEFT JOIN ACRM_F_CI_BANK_SERVICE BS ON BS.CUST_ID = CUS.CUST_ID LEFT JOIN OCRM_F_CI_OPEN_INFO  OP ON CUS.CUST_ID = OP.CUST_ID where ROWNUM = 1 AND CUS.cust_id = '"+customerId+"'");
			QueryHelper query;
			try {
				query = new QueryHelper(sb.toString(), ds.getConnection());
				List<HashMap<String, Object>> tempRowsList = (List<HashMap<String, Object>>) query.getJSON().get("data");
				if(tempRowsList!= null && tempRowsList.size()>0){
					cardNo = tempRowsList.get(0).get("CARD_NO").toString();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		if (cardNo != null && cardNo != ""){
			sb.append(" and CARD_NO = '"+cardNo+"'");
		}
		
		if("1".equals(old)){
			sb.setLength(0);
			sb.append("select o.*," +
					" i.ATM_LIMIT as ATM_LIMIT_OLD, " +
					" I.ATM_HIGH as ATM_HIGH_OLD, "  +
					" I.POS_LIMIT AS POS_LIMIT_OLD,"  +
					" I.POS_HIGH AS POS_HIGH_OLD,"  +
					" I.ukey AS ukey_OLD,"  +
					" I.MESSAGE_CODE AS MESSAGE_CODE_OLD,"  +
					" I.PHONE_BANKING AS PHONE_BANKING_OLD,"  +
					" I.MOBILE_BANKING AS MOBILE_BANKING_OLD,"  +
					" I.MICRO_BANKING AS MICRO_BANKING_OLD,"  +
					" I.STATEMENTS AS STATEMENTS_OLD,"  +
					" I.CHANGE_NOTICE AS CHANGE_NOTICE_OLD,"  +
					" I.ACCEPT AS ACCEPT_OLD,"  +
					" I.mail_address AS mail_address_OLD,"  +
					" I.TRANSACTION_SERVICE AS TRANSACTION_SERVICE_OLD,"  +
					" I.mail AS mail_OLD,  "  +
					" I.IS_CARD_APPLY AS IS_CARD_APPLY_OLD, " +
					" I.IS_NT_BANK AS IS_NT_BANK_OLD, " +
					" I.IS_ELEBANK_SER AS IS_ELEBANK_SER_OLD, " +
					" I.IS_ATM_HIGH AS IS_ATM_HIGH_OLD, " +
					" I.IS_POS_HIGH AS IS_POS_HIGH_OLD " +
					" from ACRM_F_CI_BANK_SERVICE_temp  o" +
					" left join ACRM_F_CI_BANK_SERVICE i on o.cust_id =i.cust_id  where 1=1 and o.cust_id = '"+customerId+"'");
		}
		SQL = sb.toString();
		datasource = ds;
	}	
	
	/**
	 * 发起审批工作流
	 */
	public void initFlow(){
		try {
			 ActionContext ctx = ActionContext.getContext();
		   	 request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		   	 Map<String, String> map = new HashMap<String, String>();
		   	 String custId = request.getParameter("custId");
		     String atm = request.getParameter("ATM_LIMIT");//默认每日累计限额
			 String pos = request.getParameter("POS_LIMIT");//默认单笔限额
			 String ukey = request.getParameter("id_ukey");//u-key
			 String dx = request.getParameter("id_dx");//短信认证码
			 String dh = request.getParameter("id_dh");//电话银行
			 String sj = request.getParameter("id_sj");//手机银行
			 String wx = request.getParameter("id_wx");//微信银行
			 String dz = request.getParameter("id_dz");//电子对账单
			 String cz = request.getParameter("id_cz");//传真交易服务
			 String zw = request.getParameter("id_zw");//账务变动通知
			 String yy = request.getParameter("id_yy");//若符合我行的审核条件，愿意接受我行的信贷额度
			 String dy = request.getParameter("id_dy");//同电邮地址
			 
			 String jiejika = request.getParameter("jiejika");//借记卡申请
			 String atm2 = request.getParameter("id_atm2");//每日累计转账最高限额RMB
			 String pos2 = request.getParameter("id_pos2");//单笔消费限额RMB
			 String dzyh = request.getParameter("dzyh");//电子银行服务
			 String wlyh = request.getParameter("wlyh");//网络银行
			 
			 String atmHigh = request.getParameter("atmHigh");
			 String posHigh = request.getParameter("posHigh");
			 String mail = request.getParameter("mail");
			 map.put("custId", custId);
			 map.put("atm", atm);
			 map.put("pos", pos);
			 map.put("ukey", ukey);
			 map.put("dx", dx);
			 map.put("dh", dh);
			 map.put("sj", sj);
			 map.put("wx", wx);
			 map.put("dz", dz);
			 map.put("cz", cz);
			 map.put("zw", zw);
			 map.put("yy", yy);
			 map.put("dy", dy);
			 map.put("atmHigh", atmHigh);
			 map.put("posHigh", posHigh);
			 map.put("mail", mail);
			 map.put("jiejika", jiejika);
			 map.put("atm2", atm2);
			 map.put("pos2", pos2);
			 map.put("dzyh", dzyh);
			 map.put("wlyh", wlyh);
			 
			 AcrmFCiBankService info = (AcrmFCiBankService)service.find(custId);	
		    
		     //保存临时表
		     service.saveTemp(map);
		     //设置复核状态
		     if(info!=null){
		    	 service.batchUpdateByName("update AcrmFCiBankService o set o.state = '1' where o.custId = '"+info.getCustId()+"'", null);
		     }
		     
		   	 Date date = new Date();
		   	 SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
		   	 String p = sdf.format(date);
		   	 String instanceid = "YHFW"+"_"+custId+"_"+p;//此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
			 String jobName = "银行服务信息复核_"+custId;//自定义流程名称
			 
			 int i = service.check(jobName);
			 if(i>0){
		    	 throw new BizException(1, 0, "1002", "正在审核中,不能重复审核...");
		     }
			 //以下方法调用流程引擎..
			 service.initWorkflowByWfidAndInstanceid("87", jobName, null, instanceid);//调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
			 String nextNode = "87_a4";
		     Map<String,Object> map1=new HashMap<String,Object>();
			 map1.put("instanceid", instanceid);
		     map1.put("currNode", "87_a3");
		     map1.put("nextNode",  nextNode);
		     this.setJson(map1);
		} catch (Exception e) {
			throw new BizException(0, 1, "1002",e.getMessage());
		}
	}
	/**
	 * 获取电邮地址
	 */
	public void getEmail(){
		try {
			String email = "";
			ActionContext ctx = ActionContext.getContext();
			request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
			String custId = request.getParameter("custId");
			String type = request.getParameter("type");
			if("0".equals(type)){
				List<AcrmFCiContmeth> con = service.findByJql("select e from AcrmFCiContmeth e " +
						" where e.contmethType = '500' and e.stat = '1' and e.custId = '"+custId+"'", null);
				for(AcrmFCiContmeth th : con){
					email = th.getContmethInfo().toString();
				}
			}else{
				List<AcrmFCiContmeth> con = service.findByJql("select e from AcrmFCiContmeth e " +
						" where e.contmethType = '106' and e.stat = '1' and e.custId = '"+custId+"'", null);
				for(AcrmFCiContmeth th : con){
					email = th.getContmethInfo().toString();
				}
			}
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("email",  email);
			this.setJson(map);
		} catch (Exception e) {
			throw new BizException(0, 1, "1002",e.getMessage());
		}
	}
}
