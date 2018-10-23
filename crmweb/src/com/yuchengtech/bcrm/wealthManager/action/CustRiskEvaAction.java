package com.yuchengtech.bcrm.wealthManager.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.wealthManager.model.OcrmFFinCustRisk;
import com.yuchengtech.bcrm.wealthManager.service.CustRiskEvaService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.bob.service.CommonQueryService;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;
import com.yuchengtech.trans.bo.CBRequestBody;
import com.yuchengtech.trans.bo.RequestHeader;
import com.yuchengtech.trans.client.TransClient;
import com.yuchengtech.trans.client.XmlhelperUtil;

/**
 * @describtion: 风险评估
 * 
 * @author : lhqheli (email: lhqheli@gmail.com)
 * @date : 2014-06-19 17:36:10
 */
@Action("/custRiskEva")
public class CustRiskEvaAction extends CommonAction{

    private static final long serialVersionUID = 1L;
    private static Logger log = LoggerFactory
	.getLogger(CustRiskEvaAction.class);

    
    @Autowired
    @Qualifier("dsOracle")
    private DataSource ds;
    
    @Autowired
    private CustRiskEvaService custRiskEvaService;
    
    @Autowired
	 private CommonQueryService cqs;
    
    @Autowired
    public void init(){
        model = new OcrmFFinCustRisk();
        setCommonService(custRiskEvaService);
    }
    
    public void prepare(){
    	AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
		.getAuthentication().getPrincipal();
        StringBuffer sb = new StringBuffer("SELECT C.CUST_ID,C.CUST_NAME,R.CUST_Q_ID,R.CUST_RISK_CHARACT,R.EVALUATE_DATE,R.EVALUATE_RELAT_TELEPHONE,R.INDAGETE_QA_SCORING,R.LIMIT_DATE,R.HIS_FLAG,R.Q_STAT,A.USER_NAME AS EVALUATE_NAME,R.EVALUATE_INST,O.ORG_NAME AS EVALUATE_INST_NAME ");
        sb.append(" FROM ACRM_F_CI_CUSTOMER C ");
        sb.append(" LEFT JOIN OCRM_F_FIN_CUST_RISK R ON R.CUST_ID = C.CUST_ID AND R.HIS_FLAG = '0' ");
        sb.append(" LEFT JOIN ADMIN_AUTH_ACCOUNT A ON A.ACCOUNT_NAME = R.EVALUATE_NAME ");
        sb.append(" LEFT JOIN ADMIN_AUTH_ORG O ON O.ORG_ID = R.EVALUATE_INST ");
        sb.append(" LEFT JOIN OCRM_F_CI_BELONG_CUSTMGR m ON m.cust_id=c.cust_id ");
        sb.append(" WHERE C.CUST_TYPE = '2' ");
        for(String key:this.getJson().keySet()){
            if(null!=this.getJson().get(key) && !"".equals(this.getJson().get(key))){
                if("CUST_ID".equals(key)){
                    sb.append(" AND c.CUST_ID = '"+this.getJson().get(key)+"'");
                }
            	if("CUST_ZH_NAME".equals(key)){
                    sb.append(" AND c.CUST_NAME LIKE '%"+this.getJson().get(key)+"%'");
                }
            }
        }
        SQL = sb.toString();
        datasource =ds;
        
        setPrimaryKey("r.EVALUATE_DATE desc");
        configCondition("r.CUST_RISK_CHARACT","=","CUST_RISK_CHARACT",DataType.String);
        configCondition("C.CUST_NAME","=","CUST_NAME",DataType.String);
   	 	configCondition("r.Q_STAT","=","Q_STAT",DataType.String);
   	 	configCondition("r.LIMIT_DATE","=","LIMIT_DATE",DataType.Date);
        configCondition("r.HIS_FLAG","=","HIS_FLAG",DataType.String);
        configCondition("r.EVALUATE_RELAT_TELEPHONE","=","EVALUATE_RELAT_TELEPHONE",DataType.String);
   	 	configCondition("r.EVALUATE_DATE","=","EVALUATE_DATE",DataType.Date);
   	 	configCondition("a.USER_NAME","like","EVALUATE_NAME",DataType.String);
   	 	configCondition("o.ORG_NAME","=","EVALUATE_INST_NAME",DataType.String);
        
    }
    
    /**
     * batch delete custRiskEva
     */
    public String batchDestroy(){
        ActionContext ctx = ActionContext.getContext();
        request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
        String ids = request.getParameter("ids");
        custRiskEvaService.batchRemove(ids);
        return "success";
    }
    
    /**
	 * 查询当前可用的风险评估试题
	 * @return
	 */
	public HttpHeaders queryCustRiskQuestion() {
		try {
			ActionContext ctx = ActionContext.getContext();
			request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
			StringBuilder sb = new StringBuilder("select t0.title_id,t0.title_name,t2.result_id,t2.result,t2.result_scoring from  OCRM_F_SE_TITLE t0 ");
			sb.append(" inner join (select q.question_id,q.question_order from ocrm_f_sm_papers_question_rel q where q.paper_id = (SELECT id from OCRM_F_SM_PAPERS t where option_type = '1' and available = '4')) t1 on t1.question_id = t0.title_id");
			sb.append(" left join OCRM_F_SE_TITLE_RESULT t2 on t2.title_id = t0.title_id");
			sb.append(" order by t1.question_order,t0.title_id,t2.result_sort");
			if(this.json!=null){
        		this.json.clear();
			}else {
        		this.json = new HashMap<String,Object>(); 
        	}
			this.json.put("json",new QueryHelper(sb.toString(), ds.getConnection()).getJSON());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1,2,"1002",e.getMessage());
		}
		return new DefaultHttpHeaders("success").disableCaching();
	}
	
	/**
	 * 提交在线风险评估
	 */
	public String commitRiskEva(){
        ActionContext ctx = ActionContext.getContext();
        request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
        String questionObjStr = request.getParameter("questionObj");
        Map questionObj = null;
        try {
			questionObj = (HashMap) JSONUtil.deserialize(questionObjStr);
					custRiskEvaService.commitRiskEva(questionObj);
				       SimpleDateFormat df8 = new SimpleDateFormat("yyyyMMdd");
					   SimpleDateFormat df20 = new SimpleDateFormat("yyyyMMddhhmmss");	
				       String account =request.getParameter("accountNo");
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
							CBRequestBody requestBody = new CBRequestBody();
							requestBody.setTxCode("CMFR");
							requestBody.setCustCd(custCd);															
							requestBody.setEvaDate("evaDate");//从页面取值			
							requestBody.setEffDate("effDate");//从页面取值		
							requestBody.setEvaScore("evaScore");//从页面取值		
							requestBody.setOpUser("opUser");//从页面取值							
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
				
			
			
		} catch (JSONException e) {
			e.printStackTrace();
			throw new BizException(1,2,"1002",e.getMessage());
		}
        return "success";
    }
	
	 @SuppressWarnings({"rawtypes","unchecked"})
	  	public static List ResXms(String xml) throws Exception{
	    	List<Map> list = new ArrayList<Map>();
	    	if(xml.contains("<ResponseBody>")){
		    	int beginIndex =xml.indexOf("<ResponseBody>");
				int endIndenx=xml.indexOf("</ResponseBody>");
				xml = xml.substring(beginIndex, endIndenx)+"</ResponseBody>";
				XmlhelperUtil util = new XmlhelperUtil();
				list = util.getParseXmlList(xml);
	    	 }
	         return list;
	  	}	 
	
	  @SuppressWarnings({ "unchecked", "rawtypes" })
	    public String getCustCd(String account) throws Exception{
		  String core_no="";
	   	 try{
	   		 String sql="select core_no from Acrm_f_Ci_Customer where cust_id =(select cust_id from Acrm_f_Ci_Loan_Act where account ='"+account+"')";
		    	 //String sql = "select core_no from ACRM_F_CI_Customer where CUST_ID = '" + custId + "'";
		    	 SQL = sql.toString();	 
				 Map map = cqs.excuteQuery(sql.toString(),0,1);
		    	 if(map!=null){
					List<Map> list = (List) map.get("data");
					if(list!=null && list.size()>0){
				    	 core_no =list.get(0).get("CORE_NO").toString(); //获取核心客户号
				    	 return core_no; 
					}
		    	 }
	   	 }catch(Exception e){
	   		 e.printStackTrace();
	   		 log.error("查询核心客户号失败:"+e.getMessage());
	   	 }
	   	 return core_no;
	    }	
}
