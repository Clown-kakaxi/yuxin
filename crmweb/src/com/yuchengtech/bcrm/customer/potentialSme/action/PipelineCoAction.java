package com.yuchengtech.bcrm.customer.potentialSme.action;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ibm.icu.text.SimpleDateFormat;
import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.potentialSme.model.OcrmFMkPipeline;
import com.yuchengtech.bcrm.customer.potentialSme.model.OcrmFMkPipelineBackHis;
import com.yuchengtech.bcrm.customer.potentialSme.service.OcrmFMKPipelineHisService;
import com.yuchengtech.bcrm.customer.potentialSme.service.OcrmFMKPipelineService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.trans.client.TransClient;

/**
 * 中小企Pipeline营销概览New - 
 * @author yangyue3
 * @since 2017-05-07
 */
@SuppressWarnings("serial")
@Action("/pipelineCo")
public class PipelineCoAction extends CommonAction {

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds; //声明数据源
	private HttpServletRequest request;
	
    @Autowired
    private OcrmFMKPipelineService service ;
    
    @Autowired
    private OcrmFMKPipelineHisService hisService;
    
    AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    @Autowired
	public void init(){
        	model = new OcrmFMkPipeline();  
        	setCommonService(service);
		   //新增修改删除记录是否记录日志,默认为false，不记录日志
		    needLog=true;
	}
    /**
	 * 设置查询SQL并为父类相关属性赋值
	 * 20141112 to_char(sysdate,'yyyymmdd')
	 */
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	StringBuilder sb = new StringBuilder("SELECT O.ID, O.PIPELINE_ID, O.CUST_ID, O.CUST_NAME, O.AREA_ID, O.AREA_NAME, O.DEPT_ID, O.DEPT_NAME, O.RM_ID, O.RM, O.IF_TAIWAN, S.F_VALUE AS CUST_TYPE_NAME, Y.F_VALUE AS NOW_PROGRESS_NAME,"
    			+ " O.NOW_PROGRESS, O.CUST_TYPE, O.CUST_PROPERTIES, O.PRODUCT_SUBJECT, O.PRODUCT_FORM, O.BUY_NAME, O.VISIT_DATE, "
    			+ " ROUND(FN_EXCHANGE_PBOC_BYDATE(O.APPLY_CURRENCY, O.APPLY_AMT, to_char(sysdate,'yyyymmdd')),2) AS APPLY_AMT_TORMB," 
    			// add by liuming 20170613 核批额度(折人民币/元)
    			+ " ROUND(FN_EXCHANGE_PBOC_BYDATE(O.INSURE_CURRENCY, O.INSURE_AMT, to_char(sysdate,'yyyymmdd')),2) AS INSURE_AMT_TORMB," 
    			// add by liuming 20170613 首次动拨金额(折人民币/元)
    			+ " ROUND(FN_EXCHANGE_PBOC_BYDATE(O.FIRST_M_CURRENCY, O.FIRST_M_AMT, to_char(sysdate,'yyyymmdd')),2) AS FIRST_M_AMT_TORMB," 
    			// add by liuming 20170613 剩余动拨余额(折人民币/元)
    			+ " ROUND(FN_EXCHANGE_PBOC_BYDATE(O.INSURE_CURRENCY, O.SURPLUS_M_AMT, to_char(sysdate,'yyyymmdd')),2) AS SURPLUS_M_AMT_TORMB," 
    			+ " O.APPLY_AMT, O.APPLY_CURRENCY, O.APPLY_DATE, O.IF_MUNI, O.CA_SP_STATE, O.INSURE_MONEY,"
    			+ " O.SUB_CASE_DATE, O.INSURE_AMT, O.INSURE_CURRENCY, O.INSURE_DATE, O.EXPECT_M_AMT, O.EXPECT_M_DATE, O.FIRST_M_AMT, O.FIRST_M_DATE, O.FIRST_M_CURRENCY, O.SURPLUS_M_AMT, O.CA_VALIDITY, O.CA_DUE,"
    			+ " O.CA_NUMBER, O.IF_SAME, (TRUNC(SYSDATE, 'DD')-TRUNC(FIRST_IN_DATE, 'DD')+1)||'天' AS STEP_DAYS, O.NEW_HAS_CUST, O.CREATER, to_char(O.CREATE_DATE,'yyyy-mm-dd hh24:mi:ss') CREATE_DATE, O.UPDATER, to_char(O.UPDATE_DATE,'yyyy-mm-dd hh24:mi:ss') UPDATE_DATE, to_char(O.FIRST_IN_DATE,'yyyy-mm-dd') FIRST_IN_DATE,"
//    			+ " (TRUNC(SYSDATE, 'DD')-TRUNC((SELECT MIN(P.FIRST_IN_DATE) FROM OCRM_F_MK_PIPELINE P WHERE P.PIPELINE_ID=O.PIPELINE_ID),'DD')+1)||'天' AS CASE_DAYS,"
    			+ " case when O.First_m_Date is not null then  (TRUNC(First_m_Date, 'DD') - TRUNC(o.apply_date, 'DD') + 1) || '天'  else (TRUNC(SYSDATE, 'DD') - TRUNC((SELECT MIN(P.FIRST_IN_DATE) FROM OCRM_F_MK_PIPELINE P WHERE P.PIPELINE_ID = O.PIPELINE_ID), 'DD') + 1) || '天'  end CASE_DAYS,"
    			+ " O.IF_NEXT, O.MEMO, T.POTENTIAL_FLAG AS IF_POTENTIAL,(SELECT COUNT(*) FROM OCRM_F_MK_PIPELINE_BACK_HIS H WHERE H.PIPELINE_ID = O.PIPELINE_ID AND H.BACK_TYPE = '1') BACK_NUMBER " 
    			+ " ,case when O.INSURE_AMT is not null then NVL(O.INSURE_AMT, 0) - NVL(O.INSURE_MONEY, 0) else 0 end CREDIT_GROWTH "
    			+ " ,ROUND(FN_EXCHANGE_PBOC_BYDATE(O.INSURE_CURRENCY, NVL(O.INSURE_AMT, 0) - NVL(O.INSURE_MONEY, 0), to_char(sysdate,'yyyymmdd')),2) AS CREDIT_GROWTH_TORMB "
    			+ " FROM OCRM_F_MK_PIPELINE O "
    			+ " LEFT JOIN OCRM_SYS_LOOKUP_ITEM S ON S.F_CODE=O.CUST_TYPE "
    			+ " LEFT JOIN OCRM_SYS_LOOKUP_ITEM Y ON O.NOW_PROGRESS=Y.F_CODE "
    			+ " LEFT JOIN OCRM_F_CI_BELONG_CUSTMGR C ON C.CUST_ID = O.CUST_ID "
    			+ " LEFT JOIN ACRM_F_CI_CUSTOMER T ON T.CUST_ID = O.CUST_ID "
    			+ " LEFT JOIN ADMIN_AUTH_ORG O1 ON O1.ORG_ID = O.AREA_ID "
    			+ " LEFT JOIN ADMIN_AUTH_ORG O2 ON O2.ORG_ID = O.DEPT_ID "
    			+ " LEFT JOIN ADMIN_AUTH_ACCOUNT A on o.rm_id = a.account_name "
    			+ " WHERE (S.F_LOOKUP_ID='CUST_TYPE') "
    			+ " AND (O.IF_NEXT <> '1' or O.IF_NEXT is null) "
    			+ " AND (Y.F_LOOKUP_ID='PIPELINE_STEP') "
    			+ " AND (O.CASE_STATE IS NULL OR O.CASE_STATE NOT IN('0','1'))");
        for(String key : this.getJson().keySet()){
        	if(this.getJson().get(key) != null && !this.getJson().get(key).equals("")){
        		if(key != null && (key.equals("NOW_PROGRESS_NAME") || key.equals("NOW_PROGRESS"))){ //目前阶段
        			sb.append(" AND (O.NOW_PROGRESS='" + this.getJson().get(key) + "' OR Y.F_VALUE LIKE '%" + this.getJson().get(key) + "%')");
        		}else if(key != null && key.equals("CUST_NAME")){ //客户名称
        			sb.append(" AND O.CUST_NAME LIKE '%" + this.getJson().get(key) + "%'");
        		}else if(key != null && (key.equals("AREA_NAME") || key.equals("AREA"))){ //区域中心
        			sb.append(" AND (O.AREA_ID='" + this.getJson().get(key) + "' OR O1.ORG_NAME LIKE '%" + this.getJson().get(key) + "%')");
        		}else if(key != null && (key.equals("DEPT_NAME") || key.equals("DEPT"))){ //营业单位
        			sb.append(" AND (O.DEPT_ID='" + this.getJson().get(key) + "' OR O2.ORG_NAME LIKE '%" + this.getJson().get(key) + "%')");
        		}else if(key != null && (key.equals("CUST_TYPE_NAME") || key.equals("CUST_TYPE"))){ //客户细分
        			sb.append(" AND O.CUST_TYPE='" + this.getJson().get(key) + "'");
        		}else if(key != null && key.equals("RM") ){ //客户经理
        			sb.append(" AND A.USER_NAME LIKE '%" + this.getJson().get(key) + "%'");
        		}else if(key != null && key.equals("IF_POTENTIAL")){ //潜在客户标志
        			sb.append(" AND T.POTENTIAL_FLAG='" + this.getJson().get(key) + "'");
        		}else if(key != null && key.equals("NEW_HAS_CUST")){ //新户/既有增贷
        			sb.append(" AND O.NEW_HAS_CUST='" + this.getJson().get(key) + "'");
        		}
        	}
        }
    	SQL = sb.toString();
    	setPrimaryKey("O.UPDATE_DATE DESC");//设置查询条件,按修改时间降序排列
    	datasource = ds;
	}
    
	//新增/修改功能
    public DefaultHttpHeaders saveOrUpdate() throws Exception{
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	OcrmFMkPipeline ocrmFMkPipeline = (OcrmFMkPipeline)model;
    	//获取pipeline_id，为0的是新增，不为0的是修改
    	if(ocrmFMkPipeline.getPipelineId() == 0){//新增
    		ocrmFMkPipeline.setCreateDate(new Date());//创建日期
    		ocrmFMkPipeline.setCreater(auth.getUserId());//创建人
    		ocrmFMkPipeline.setFirstInDate(ocrmFMkPipeline.getCreateDate());//首次进入本阶段日期
    		ocrmFMkPipeline.setUpdateDate(ocrmFMkPipeline.getCreateDate());//修改日期
    		ocrmFMkPipeline.setUpdater(ocrmFMkPipeline.getCreater());//修改人
    		ocrmFMkPipeline.setPipelineId((new Date()).getTime());//设置PipelineId必须保证其唯一性
    		ocrmFMkPipeline.setId(new Date().getTime());//设置主键ID
    		service.save(model);
    	}else{//修改
    		long his_id = ocrmFMkPipeline.getId();
    		@SuppressWarnings("unchecked")
			List<OcrmFMkPipeline> list = service.findByJql("select O from OcrmFMkPipeline O where O.id='" + his_id + "'", null);
    		OcrmFMkPipeline his_ocrmFMkPipeline = list.get(0);
    		String now_step = ocrmFMkPipeline.getNowProgress();//当前阶段
    		String if_next = ocrmFMkPipeline.getIfNext();//是否进入下一阶段
    		//1.点“进入下一阶段”
    		if(if_next != null && if_next.equals("1")){
    			//保存历史记录
    			his_ocrmFMkPipeline.setIfNext("1");
    			his_ocrmFMkPipeline.setUpdater(auth.getUserId());
    			his_ocrmFMkPipeline.setUpdateDate(new Date());
    			service.save(his_ocrmFMkPipeline);
    			
    			//保存新的数据
    			ocrmFMkPipeline.setId(new Date().getTime());
        		ocrmFMkPipeline.setNowProgress((Integer.parseInt(now_step) + 1)+"");
        		ocrmFMkPipeline.setCreateDate(new Date());//创建日期
    			ocrmFMkPipeline.setCreater(auth.getUserId());//创建人
    			ocrmFMkPipeline.setFirstInDate(ocrmFMkPipeline.getCreateDate());//首次进入本阶段日期
    			ocrmFMkPipeline.setUpdater(auth.getUserId());//修改人
    			ocrmFMkPipeline.setUpdateDate(new Date());//修改日期
    			ocrmFMkPipeline.setIfNext("2");
    			service.save(ocrmFMkPipeline);
    		}else{
    			//2.只修改部分值，不进入下一阶段
    			if(ocrmFMkPipeline.getNowProgress().equals(his_ocrmFMkPipeline.getNowProgress())){
    				ocrmFMkPipeline.setUpdater(auth.getUserId());//修改人
    				ocrmFMkPipeline.setUpdateDate(new Date());//修改日期
    				service.save(model);
    			}else{
    				//3.点“同步”后反显，当前阶段被更改
    				his_ocrmFMkPipeline.setIfNext("1");
    				his_ocrmFMkPipeline.setUpdateDate(new Date());
    				his_ocrmFMkPipeline.setUpdater(auth.getUserId());
    				service.save(his_ocrmFMkPipeline);
    				
    				ocrmFMkPipeline.setId(new Date().getTime());
    				ocrmFMkPipeline.setCreater(auth.getUserId());
    				ocrmFMkPipeline.setCreateDate(new Date());
    				ocrmFMkPipeline.setUpdater(ocrmFMkPipeline.getCreater());
    				ocrmFMkPipeline.setUpdateDate(ocrmFMkPipeline.getCreateDate());
    				ocrmFMkPipeline.setFirstInDate(ocrmFMkPipeline.getCreateDate());
    				ocrmFMkPipeline.setIfNext("2");
    				service.save(ocrmFMkPipeline);
    			}
    		}
    	}	
    	return new DefaultHttpHeaders("success");
    }

    //区域中心码值查询
    public  void searchArea(){
    	try {
			StringBuilder sb = new StringBuilder("");
				sb.append("SELECT UNIT.ORG_NAME as VALUE,UNIT.UNITID as KEY FROM SYS_UNITS UNIT WHERE UNITSEQ LIKE '500%'  and levelunit='2'  order by UNIT.levelunit,UNIT.id ");
			this.json = new QueryHelper(sb.toString(), ds.getConnection()).getJSON();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    //新增案件时选择当前阶段,只需返回合作意向
    public void addStepName(){
    	try {
    		StringBuilder sb = new StringBuilder("");
    		sb.append("SELECT L.F_CODE AS KEY, L.F_VALUE AS VALUE FROM OCRM_SYS_LOOKUP_ITEM L WHERE L.F_LOOKUP_ID='PIPELINE_STEP' AND L.F_CODE = '2'");
			this.json = new QueryHelper(sb.toString(), ds.getConnection()).getJSON();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    //查询区域的阶段字典，过滤掉'prospect信息'
    public void searchStepName(){
    	try {
    		StringBuilder sb = new StringBuilder("");
    		sb.append("SELECT L.F_CODE AS KEY, L.F_VALUE AS VALUE FROM OCRM_SYS_LOOKUP_ITEM L WHERE L.F_LOOKUP_ID='PIPELINE_STEP' AND L.F_CODE IN(2,3,4,5,6)");
			this.json = new QueryHelper(sb.toString(), ds.getConnection()).getJSON();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    //删除--将案件状态设为1
    public void batchDel(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String idStr = request.getParameter("idStr");
    	String ids[] = idStr.split(",");
    	for (String id : ids) {
			service.batchUpdateByName("update OcrmFMkPipeline set caseState = 1 where id = '" + id + "'", new HashMap<String, Object>());
		}
    }
    
    /**
     * 同步信贷Pipeline
     * 
     **/
    @SuppressWarnings("unchecked")
    public String syncLN(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	OcrmFMkPipeline ocrmFMkPipeline = (OcrmFMkPipeline)model;
    	long id = ocrmFMkPipeline.getId();
    	//String caNumber = ocrmFMkPipeline.getCaNumber();
    	String custId = ocrmFMkPipeline.getCustId();
    	String custName = ocrmFMkPipeline.getCustName();
    	String caNo = ocrmFMkPipeline.getCaNumber();
    	String responseXml = "";
    	HashMap<String, String> resMap = new HashMap<String, String>();
    	Map<String, Object> map1 = new HashMap<String, Object>();
    	List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
    	
    	if (this.json != null)
			this.json.clear();
		else
			this.json = new HashMap<String, Object>();
		try {
			//responseXml = TranCrmToLN(custId, caNumber);
			if(caNo.equals("ca1111")){
				responseXml = getResponseXml1(custId, custName, caNo);//已核批动拨
			}else if(caNo.equals("ca1000")){
				responseXml = getResponseXml10(custId, custName, caNo);//已核批未动拨
			}else if(caNo.equals("ca2222")){
				responseXml = getResponseXml2(custId, custName, caNo);//核批阶段
			}else if(caNo.equals("ca3333")){
				responseXml = getResponseXml3(custId, custName, caNo);//信审阶段
			}else if(caNo.equals("ca4444")){
				responseXml = getResponseXml4(custId, custName, caNo);//CA准备阶段1-审批中(AO)
			}else if(caNo.equals("ca5555")){
				responseXml = getResponseXml5(custId, custName, caNo);//CA准备阶段2-打回(AO)
			}else if(caNo.equals("ca6666")){
				responseXml = getResponseXml6(custId, custName, caNo);//CA准备阶段3-审批中(主管)
			}else if(caNo.equals("ca7777")){
				responseXml = getResponseXml7(custId, custName, caNo);//返回信息都未空
			}else if(caNo.equals("ca9999")){
				responseXml = getResponseXml9(custId, custName, caNo);//CA准备阶段4-待发起
			}else{
				responseXml = getResponseXml8(custId, custName, caNo);//CA业务流水号查询不到
			}
			boolean responseFlag = doResXms(responseXml);
			if(responseFlag){//请求成功,处理业务
				Document doc = DocumentHelper.parseText(responseXml.substring(8));
				//获取业务报文
				Element resElement = doc.getRootElement().element("Packet").element("Data").element("Res");
				//获取CA业务流水号
				String resCaNumber = resElement.element("caNumber").getTextTrim();
				//1.有返回CA流水号
				if(resCaNumber != null && !resCaNumber.equals("")){
					//获取申请金额
					String applyAmt0 = resElement.element("applyAmt").getTextTrim();
					//如果审批流程为“待发起(000)”，则进入CA准备阶段
					if(resElement.element("caSPState").getTextTrim().equals("000")){
						List<OcrmFMkPipeline> syn_list = service.findByJql("select o from OcrmFMkPipeline o where o.id='" + id + "'", null);
						OcrmFMkPipeline syn_ocrmFMkPipeline = syn_list.get(0);
						
						resMap.put("stepName", "3");
						resMap.put("custId", resElement.element("custId").getTextTrim());
						resMap.put("custName", resElement.element("custName").getTextTrim());
						resMap.put("newHasCust", resElement.element("newHasCust").getTextTrim());
						resMap.put("insureMoney", resElement.element("insureMoney").getTextTrim());
						resMap.put("caNumber", resElement.element("caNumber").getTextTrim());
						resMap.put("applyAmt", applyAmt0);
						resMap.put("applyCurrency", resElement.element("applyCurrency").getTextTrim());
						resMap.put("applyDate", resElement.element("applyDate").getTextTrim());
						resMap.put("subCaseDate", resElement.element("subCaseDate").getTextTrim());
						resMap.put("caValitidy", resElement.element("caValitidy").getTextTrim());
						resMap.put("caDue", resElement.element("caDue").getTextTrim());
						resMap.put("caSPState", resElement.element("caSPState").getTextTrim());
						resMap.put("custType", resElement.element("custType").getTextTrim());
						resMap.put("postId", resElement.element("postId").getTextTrim());
						//add by yangyue3 2017/06/08
						//将返回报文的金额折成人民币，显示在页面上
						String applyCurrency = resElement.element("applyCurrency").getTextTrim();
						BigDecimal applyAmt = applyAmt0 != null && !applyAmt0.equals("") ? new BigDecimal(applyAmt0) : null;
						StringBuffer sql = new StringBuffer("select ROUND(FN_EXCHANGE_PBOC_BYDATE('" + applyCurrency +"', " + applyAmt + ", to_char(sysdate,'yyyymmdd')),2) AS APPLY_AMT_TORMB from ocrm_f_mk_pipeline T");
						Map<String, Object> map_toRMB = new QueryHelper(sql.toString(), ds.getConnection()).getJSON();
						List<?> list_toRMB = (List<?>) map_toRMB.get("data");
						String APPLY_AMT_TORMB = (String) ((Map<String, Object>)list_toRMB.get(0)).get("APPLY_AMT_TORMB");
						resMap.put("APPLY_AMT_TORMB", APPLY_AMT_TORMB);
						//保存同步信息
						syn_ocrmFMkPipeline.setId(new Date().getTime());
						syn_ocrmFMkPipeline.setNowProgress("3");
						syn_ocrmFMkPipeline.setCustId(resMap.get("custId"));
						syn_ocrmFMkPipeline.setCustName(resMap.get("custName"));
						syn_ocrmFMkPipeline.setNewHasCust(resMap.get("newHasCust"));
						if(resMap.get("insureMoney").equals("")){
							syn_ocrmFMkPipeline.setInsureMoney(null);
						}else{
							syn_ocrmFMkPipeline.setInsureMoney(new BigDecimal(resMap.get("insureMoney")));
						}
						syn_ocrmFMkPipeline.setCaNumber(resMap.get("caNumber"));
						syn_ocrmFMkPipeline.setCaSpState(resMap.get("caSPState"));
						syn_ocrmFMkPipeline.setApplyAmt(applyAmt);
						syn_ocrmFMkPipeline.setApplyCurrency(resMap.get("applyCurrency"));
						syn_ocrmFMkPipeline.setApplyDate(resMap.get("applyDate") != null && !resMap.get("applyDate").equals("") ? new SimpleDateFormat("yyyy-MM-dd").parse(resMap.get("applyDate")):null);
						syn_ocrmFMkPipeline.setCreater(auth.getUserId());
						syn_ocrmFMkPipeline.setCreateDate(new Date());
						syn_ocrmFMkPipeline.setUpdater(auth.getUserId());
						syn_ocrmFMkPipeline.setUpdateDate(new Date());
						service.save(syn_ocrmFMkPipeline);
						
						//更改上一条记录
						ocrmFMkPipeline.setIfNext("1");
						ocrmFMkPipeline.setUpdater(syn_ocrmFMkPipeline.getCreater());
						ocrmFMkPipeline.setUpdateDate(syn_ocrmFMkPipeline.getCreateDate());
						service.save(ocrmFMkPipeline);
						//将新数据返回页面
						resMap.put("id", syn_ocrmFMkPipeline.getId() + "");
						list.add(resMap);
						map1.put("data", list);
						this.json.put("json", map1);
					}else{
						String firstMAmt = resElement.element("firstMAmt").getTextTrim();
						//2.是否有动拨信息
						if(firstMAmt != null && !firstMAmt.equals("")){
							List<OcrmFMkPipeline> syn_list = service.findByJql("select o from OcrmFMkPipeline o where o.id='" + id + "'", null);
							OcrmFMkPipeline syn_ocrmFMkPipeline = syn_list.get(0);
							resMap.put("stepName", "6");
							resMap.put("custId", resElement.element("custId").getTextTrim());
							resMap.put("custName", resElement.element("custName").getTextTrim());
							resMap.put("newHasCust", resElement.element("newHasCust").getTextTrim());
							resMap.put("insureMoney", resElement.element("insureMoney").getTextTrim());
							resMap.put("caNumber", resElement.element("caNumber").getTextTrim());
							resMap.put("applyAmt", resElement.element("applyAmt").getTextTrim());
							resMap.put("applyCurrency", resElement.element("applyCurrency").getTextTrim());
							resMap.put("applyDate", resElement.element("applyDate").getTextTrim());
							resMap.put("subCaseDate", resElement.element("subCaseDate").getTextTrim());
							resMap.put("insureAmt", resElement.element("insureAmt").getTextTrim());
							resMap.put("insureCurrency", resElement.element("insureCurrency").getTextTrim());
							resMap.put("insureDate", resElement.element("insureDate").getTextTrim());
							resMap.put("firstMAmt", resElement.element("firstMAmt").getTextTrim());
							resMap.put("firstMCurrency", resElement.element("firstMCurrency").getTextTrim());
							resMap.put("firstMDate", resElement.element("firstMDate").getTextTrim());
							resMap.put("surplusMAmt", resElement.element("surplusMAmt").getTextTrim());
							resMap.put("caValitidy", resElement.element("caValitidy").getTextTrim());
							resMap.put("caDue", resElement.element("caDue").getTextTrim());
							resMap.put("caSPState", resElement.element("caSPState").getTextTrim());
							resMap.put("custType", resElement.element("custType").getTextTrim());
							resMap.put("postId", resElement.element("postId").getTextTrim());
							
							//add by yangyue3 2017/06/08
							//将返回报文的金额折成人民币，显示在页面上
							String applyCurrency = resElement.element("applyCurrency").getTextTrim();
							BigDecimal applyAmt = new BigDecimal(resElement.element("applyAmt").getTextTrim());
							String insureCurrency = resElement.element("insureCurrency").getTextTrim();
							BigDecimal insureyAmt = new BigDecimal(resElement.element("insureAmt").getTextTrim());
							String firstMCurrency = resElement.element("firstMCurrency").getTextTrim();
							BigDecimal firstMAmt1 = new BigDecimal(resElement.element("firstMAmt").getTextTrim());
							BigDecimal surplusMAmt = new BigDecimal(resElement.element("surplusMAmt").getTextTrim());
							StringBuffer sql = new StringBuffer("select ROUND(FN_EXCHANGE_PBOC_BYDATE('" + applyCurrency +"', " + applyAmt + ", to_char(sysdate,'yyyymmdd')),2) AS APPLY_AMT_TORMB,"
									+ "ROUND(FN_EXCHANGE_PBOC_BYDATE('" + insureCurrency +"', " + insureyAmt + ", to_char(sysdate,'yyyymmdd')),2) AS INSURE_AMT_TORMB,"
									+ "ROUND(FN_EXCHANGE_PBOC_BYDATE('" + firstMCurrency +"', " + firstMAmt1 + ", to_char(sysdate,'yyyymmdd')),2) AS FIRST_M_AMT_TORMB,"
									+ "ROUND(FN_EXCHANGE_PBOC_BYDATE('" + firstMCurrency +"', " + surplusMAmt + ", to_char(sysdate,'yyyymmdd')),2) AS SURPLUS_M_AMT_TORMB from ocrm_f_mk_pipeline T");
							Map<String, Object> map_toRMB = new QueryHelper(sql.toString(), ds.getConnection()).getJSON();
							List<?> list_toRMB = (List<?>) map_toRMB.get("data");
							String APPLY_AMT_TORMB = (String) ((Map<String, Object>)list_toRMB.get(0)).get("APPLY_AMT_TORMB");
							String INSURE_AMT_TORMB = (String) ((Map<String, Object>)list_toRMB.get(0)).get("INSURE_AMT_TORMB");
							String FIRST_M_AMT_TORMB = (String) ((Map<String, Object>)list_toRMB.get(0)).get("FIRST_M_AMT_TORMB");
							String SURPLUS_M_AMT_TORMB = (String) ((Map<String, Object>)list_toRMB.get(0)).get("SURPLUS_M_AMT_TORMB");
							resMap.put("APPLY_AMT_TORMB", APPLY_AMT_TORMB);
							resMap.put("INSURE_AMT_TORMB", INSURE_AMT_TORMB);
							resMap.put("FIRST_M_AMT_TORMB", FIRST_M_AMT_TORMB);
							resMap.put("SURPLUS_M_AMT_TORMB", SURPLUS_M_AMT_TORMB);
							
							syn_ocrmFMkPipeline.setId(new Date().getTime());
							syn_ocrmFMkPipeline.setNowProgress("6");
							syn_ocrmFMkPipeline.setCustId(resMap.get("custId"));
							syn_ocrmFMkPipeline.setCustName(resMap.get("custName"));
							syn_ocrmFMkPipeline.setNewHasCust(resMap.get("newHasCust"));
							if(resMap.get("insureMoney").equals("")){
								syn_ocrmFMkPipeline.setInsureMoney(null);
							}else{
								syn_ocrmFMkPipeline.setInsureMoney(new BigDecimal(resMap.get("insureMoney")));
							}
							syn_ocrmFMkPipeline.setCaNumber(resMap.get("caNumber"));
							syn_ocrmFMkPipeline.setCaSpState(resMap.get("caSPState"));
							syn_ocrmFMkPipeline.setApplyAmt(new BigDecimal(resMap.get("applyAmt")));
							syn_ocrmFMkPipeline.setApplyCurrency(resMap.get("applyCurrency"));
							syn_ocrmFMkPipeline.setApplyDate(new SimpleDateFormat("yyyy-MM-dd").parse(resMap.get("applyDate")));
							syn_ocrmFMkPipeline.setSubCaseDate(new SimpleDateFormat("yyyy-MM-dd").parse(resMap.get("subCaseDate")));
							syn_ocrmFMkPipeline.setInsureAmt(new BigDecimal(resMap.get("insureAmt")));
							syn_ocrmFMkPipeline.setInsureCurrency(resMap.get("insureCurrency"));
							syn_ocrmFMkPipeline.setInsureDate(new SimpleDateFormat("yyyy-MM-dd").parse(resMap.get("insureDate")));
							syn_ocrmFMkPipeline.setFirstMAmt(new BigDecimal(resMap.get("firstMAmt")));
							syn_ocrmFMkPipeline.setFirstMCurrency(resMap.get("firstMCurrency"));
							syn_ocrmFMkPipeline.setFirstMDate(new SimpleDateFormat("yyyy-MM-dd").parse(resMap.get("firstMDate")));
							syn_ocrmFMkPipeline.setSurplusMAmt(new BigDecimal(resMap.get("surplusMAmt")));
							syn_ocrmFMkPipeline.setCreater(auth.getUserId());
							syn_ocrmFMkPipeline.setCreateDate(new Date());
							syn_ocrmFMkPipeline.setUpdater(auth.getUserId());
							syn_ocrmFMkPipeline.setUpdateDate(new Date());
							service.save(syn_ocrmFMkPipeline);
							//更改上一条记录
							ocrmFMkPipeline.setIfNext("1");
							ocrmFMkPipeline.setUpdater(syn_ocrmFMkPipeline.getCreater());
							ocrmFMkPipeline.setUpdateDate(syn_ocrmFMkPipeline.getCreateDate());
							service.save(ocrmFMkPipeline);
							//将新数据返回页面
							resMap.put("id", syn_ocrmFMkPipeline.getId() + "");
							list.add(resMap);
							map1.put("data", list);
							this.json.put("json", map1);
						}else{
							String insureAmt = resElement.element("insureAmt").getTextTrim();
							//3.是否有核批信息
							if(insureAmt != null && !insureAmt.equals("")){
								List<OcrmFMkPipeline> syn_list = service.findByJql("select o from OcrmFMkPipeline o where o.id='" + id + "'", null);
								OcrmFMkPipeline syn_ocrmFMkPipeline = syn_list.get(0);
								resMap.put("stepName", "5");
								resMap.put("custId", resElement.element("custId").getTextTrim());
								resMap.put("custName", resElement.element("custName").getTextTrim());
								resMap.put("newHasCust", resElement.element("newHasCust").getTextTrim());
								resMap.put("insureMoney", resElement.element("insureMoney").getTextTrim());
								resMap.put("caNumber", resElement.element("caNumber").getTextTrim());
								resMap.put("applyAmt", resElement.element("applyAmt").getTextTrim());
								resMap.put("applyCurrency", resElement.element("applyCurrency").getTextTrim());
								resMap.put("applyDate", resElement.element("applyDate").getTextTrim());
								resMap.put("subCaseDate", resElement.element("subCaseDate").getTextTrim());
								resMap.put("insureAmt", resElement.element("insureAmt").getTextTrim());
								resMap.put("insureCurrency", resElement.element("insureCurrency").getTextTrim());
								resMap.put("insureDate", resElement.element("insureDate").getTextTrim());
								resMap.put("caValitidy", resElement.element("caValitidy").getTextTrim());
								resMap.put("caDue", resElement.element("caDue").getTextTrim());
								resMap.put("caSPState", resElement.element("caSPState").getTextTrim());
								resMap.put("custType", resElement.element("custType").getTextTrim());
								resMap.put("postId", resElement.element("postId").getTextTrim());
								
								//add by yangyue3 2017/06/08
								//将返回报文的金额折成人民币，显示在页面上
								String applyCurrency = resElement.element("applyCurrency").getTextTrim();
								BigDecimal applyAmt = new BigDecimal(resElement.element("applyAmt").getTextTrim());
								String insureCurrency = resElement.element("insureCurrency").getTextTrim();
								BigDecimal insureyAmt = new BigDecimal(resElement.element("insureAmt").getTextTrim());
								StringBuffer sql = new StringBuffer("select ROUND(FN_EXCHANGE_PBOC_BYDATE('" + applyCurrency +"', " + applyAmt + ", to_char(sysdate,'yyyymmdd')),2) AS APPLY_AMT_TORMB,"
										+ "ROUND(FN_EXCHANGE_PBOC_BYDATE('" + insureCurrency +"', " + insureyAmt + ", to_char(sysdate,'yyyymmdd')),2) AS INSURE_AMT_TORMB from ocrm_f_mk_pipeline T");
								Map<String, Object> map_toRMB = new QueryHelper(sql.toString(), ds.getConnection()).getJSON();
								List<?> list_toRMB = (List<?>) map_toRMB.get("data");
								String APPLY_AMT_TORMB = (String) ((Map<String, Object>)list_toRMB.get(0)).get("APPLY_AMT_TORMB");
								String INSURE_AMT_TORMB = (String) ((Map<String, Object>)list_toRMB.get(0)).get("INSURE_AMT_TORMB");
								resMap.put("APPLY_AMT_TORMB", APPLY_AMT_TORMB);
								resMap.put("INSURE_AMT_TORMB", INSURE_AMT_TORMB);
								
								syn_ocrmFMkPipeline.setId(new Date().getTime());
								syn_ocrmFMkPipeline.setNowProgress("5");
								syn_ocrmFMkPipeline.setCustId(resMap.get("custId"));
								syn_ocrmFMkPipeline.setCustName(resMap.get("custName"));
								syn_ocrmFMkPipeline.setNewHasCust(resMap.get("newHasCust"));
								if(resMap.get("insureMoney").equals("")){
									syn_ocrmFMkPipeline.setInsureMoney(null);
								}else{
									syn_ocrmFMkPipeline.setInsureMoney(new BigDecimal(resMap.get("insureMoney")));
								}
								syn_ocrmFMkPipeline.setCaNumber(resMap.get("caNumber"));
								syn_ocrmFMkPipeline.setCaSpState(resMap.get("caSPState"));
								syn_ocrmFMkPipeline.setApplyAmt(new BigDecimal(resMap.get("applyAmt")));
								syn_ocrmFMkPipeline.setApplyCurrency(resMap.get("applyCurrency"));
								syn_ocrmFMkPipeline.setApplyDate(new SimpleDateFormat("yyyy-MM-dd").parse(resMap.get("applyDate")));
								syn_ocrmFMkPipeline.setSubCaseDate(new SimpleDateFormat("yyyy-MM-dd").parse(resMap.get("subCaseDate")));
								syn_ocrmFMkPipeline.setInsureAmt(new BigDecimal(resMap.get("insureAmt")));
								syn_ocrmFMkPipeline.setInsureCurrency(resMap.get("insureCurrency"));
								syn_ocrmFMkPipeline.setInsureDate(new SimpleDateFormat("yyyy-MM-dd").parse(resMap.get("insureDate")));
								syn_ocrmFMkPipeline.setCreater(auth.getUserId());
								syn_ocrmFMkPipeline.setCreateDate(new Date());
								syn_ocrmFMkPipeline.setUpdater(auth.getUserId());
								syn_ocrmFMkPipeline.setUpdateDate(new Date());
								service.save(syn_ocrmFMkPipeline);
								//更改上一条记录
								ocrmFMkPipeline.setIfNext("1");
								ocrmFMkPipeline.setUpdater(syn_ocrmFMkPipeline.getCreater());
								ocrmFMkPipeline.setUpdateDate(syn_ocrmFMkPipeline.getCreateDate());
								service.save(ocrmFMkPipeline);
								//将新数据返回页面
								resMap.put("id", syn_ocrmFMkPipeline.getId() + "");
								list.add(resMap);
								map1.put("data", list);
								this.json.put("json", map1);
							}else{
								String caSPState = resElement.element("caSPState").getTextTrim();
								//4.CA流程状态是否有值,审批中(111),打回(992)
								if(!caSPState.equals("") && (caSPState.equals("111") || caSPState.equals("992"))){
									//5.判断结点所处岗位
									String postId = resElement.element("postId").getTextTrim();
									if(postId.equals("1061")){
										List<OcrmFMkPipeline> syn_list = service.findByJql("select o from OcrmFMkPipeline o where o.id='" + id + "'", null);
										OcrmFMkPipeline syn_ocrmFMkPipeline = syn_list.get(0);
										//进入信审阶段
										resMap.put("stepName", "4");
										resMap.put("custId", resElement.element("custId").getTextTrim());
										resMap.put("custName", resElement.element("custName").getTextTrim());
										resMap.put("newHasCust", resElement.element("newHasCust").getTextTrim());
										resMap.put("insureMoney", resElement.element("insureMoney").getTextTrim());
										resMap.put("caNumber", resElement.element("caNumber").getTextTrim());
										resMap.put("applyAmt", resElement.element("applyAmt").getTextTrim());
										resMap.put("applyCurrency", resElement.element("applyCurrency").getTextTrim());
										resMap.put("applyDate", resElement.element("applyDate").getTextTrim());
										resMap.put("subCaseDate", resElement.element("subCaseDate").getTextTrim());
										resMap.put("caValitidy", resElement.element("caValitidy").getTextTrim());
										resMap.put("caDue", resElement.element("caDue").getTextTrim());
										resMap.put("caSPState", resElement.element("caSPState").getTextTrim());
										resMap.put("custType", resElement.element("custType").getTextTrim());
										resMap.put("postId", resElement.element("postId").getTextTrim());
										
										//add by yangyue3 2017/06/08
										//将返回报文的金额折成人民币，显示在页面上
										String applyCurrency = resElement.element("applyCurrency").getTextTrim();
										BigDecimal applyAmt = new BigDecimal(resElement.element("applyAmt").getTextTrim());
										StringBuffer sql = new StringBuffer("select ROUND(FN_EXCHANGE_PBOC_BYDATE('" + applyCurrency +"', " + applyAmt + ", to_char(sysdate,'yyyymmdd')),2) AS APPLY_AMT_TORMB from ocrm_f_mk_pipeline T");
										Map<String, Object> map_toRMB = new QueryHelper(sql.toString(), ds.getConnection()).getJSON();
										List<?> list_toRMB = (List<?>) map_toRMB.get("data");
										String APPLY_AMT_TORMB = (String) ((Map<String, Object>)list_toRMB.get(0)).get("APPLY_AMT_TORMB");
										resMap.put("APPLY_AMT_TORMB", APPLY_AMT_TORMB);
										
										syn_ocrmFMkPipeline.setId(new Date().getTime());
										syn_ocrmFMkPipeline.setNowProgress("4");
										syn_ocrmFMkPipeline.setCustId(resMap.get("custId"));
										syn_ocrmFMkPipeline.setCustName(resMap.get("custName"));
										syn_ocrmFMkPipeline.setNewHasCust(resMap.get("newHasCust"));
										if(resMap.get("insureMoney").equals("")){
											syn_ocrmFMkPipeline.setInsureMoney(null);
										}else{
											syn_ocrmFMkPipeline.setInsureMoney(new BigDecimal(resMap.get("insureMoney")));
										}
										syn_ocrmFMkPipeline.setCaNumber(resMap.get("caNumber"));
										syn_ocrmFMkPipeline.setCaSpState(resMap.get("caSPState"));
										syn_ocrmFMkPipeline.setApplyAmt(new BigDecimal(resMap.get("applyAmt")));
										syn_ocrmFMkPipeline.setApplyCurrency(resMap.get("applyCurrency"));
										syn_ocrmFMkPipeline.setApplyDate(new SimpleDateFormat("yyyy-MM-dd").parse(resMap.get("applyDate")));
										syn_ocrmFMkPipeline.setSubCaseDate(new SimpleDateFormat("yyyy-MM-dd").parse(resMap.get("subCaseDate")));
										syn_ocrmFMkPipeline.setCreater(auth.getUserId());
										syn_ocrmFMkPipeline.setCreateDate(new Date());
										syn_ocrmFMkPipeline.setUpdater(auth.getUserId());
										syn_ocrmFMkPipeline.setUpdateDate(new Date());
										service.save(syn_ocrmFMkPipeline);
										//更改上一条记录
										ocrmFMkPipeline.setIfNext("1");
										ocrmFMkPipeline.setUpdater(syn_ocrmFMkPipeline.getCreater());
										ocrmFMkPipeline.setUpdateDate(syn_ocrmFMkPipeline.getCreateDate());
										service.save(ocrmFMkPipeline);
										//将新数据返回页面
										resMap.put("id", syn_ocrmFMkPipeline.getId() + "");
										list.add(resMap);
										map1.put("data", list);
										this.json.put("json", map1);
									}else{
										List<OcrmFMkPipeline> syn_list = service.findByJql("select o from OcrmFMkPipeline o where o.id='" + id + "'", null);
										OcrmFMkPipeline syn_ocrmFMkPipeline = syn_list.get(0);
										//进入CA准备阶段
										resMap.put("stepName", "3");
										resMap.put("custId", resElement.element("custId").getTextTrim());
										resMap.put("custName", resElement.element("custName").getTextTrim());
										resMap.put("newHasCust", resElement.element("newHasCust").getTextTrim());
										resMap.put("insureMoney", resElement.element("insureMoney").getTextTrim());
										resMap.put("caNumber", resElement.element("caNumber").getTextTrim());
										resMap.put("applyAmt", resElement.element("applyAmt").getTextTrim());
										resMap.put("applyCurrency", resElement.element("applyCurrency").getTextTrim());
										resMap.put("applyDate", resElement.element("applyDate").getTextTrim());
										resMap.put("subCaseDate", resElement.element("subCaseDate").getTextTrim());
										resMap.put("caValitidy", resElement.element("caValitidy").getTextTrim());
										resMap.put("caDue", resElement.element("caDue").getTextTrim());
										resMap.put("caSPState", resElement.element("caSPState").getTextTrim());
										resMap.put("custType", resElement.element("custType").getTextTrim());
										resMap.put("postId", resElement.element("postId").getTextTrim());
										
										//add by yangyue3 2017/06/08
										//将返回报文的金额折成人民币，显示在页面上
										String applyCurrency = resElement.element("applyCurrency").getTextTrim();
										BigDecimal applyAmt = new BigDecimal(resElement.element("applyAmt").getTextTrim());
										StringBuffer sql = new StringBuffer("select ROUND(FN_EXCHANGE_PBOC_BYDATE('" + applyCurrency +"', " + applyAmt + ", to_char(sysdate,'yyyymmdd')),2) AS APPLY_AMT_TORMB from ocrm_f_mk_pipeline T");
										Map<String, Object> map_toRMB = new QueryHelper(sql.toString(), ds.getConnection()).getJSON();
										List<?> list_toRMB = (List<?>) map_toRMB.get("data");
										String APPLY_AMT_TORMB = (String) ((Map<String, Object>)list_toRMB.get(0)).get("APPLY_AMT_TORMB");
										resMap.put("APPLY_AMT_TORMB", APPLY_AMT_TORMB);
										//保存同步信息
										syn_ocrmFMkPipeline.setId(new Date().getTime());
										syn_ocrmFMkPipeline.setNowProgress("3");
										syn_ocrmFMkPipeline.setCustId(resMap.get("custId"));
										syn_ocrmFMkPipeline.setCustName(resMap.get("custName"));
										syn_ocrmFMkPipeline.setNewHasCust(resMap.get("newHasCust"));
										if(resMap.get("insureMoney").equals("")){
											syn_ocrmFMkPipeline.setInsureMoney(null);
										}else{
											syn_ocrmFMkPipeline.setInsureMoney(new BigDecimal(resMap.get("insureMoney")));
										}
										syn_ocrmFMkPipeline.setCaNumber(resMap.get("caNumber"));
										syn_ocrmFMkPipeline.setCaSpState(resMap.get("caSPState"));
										syn_ocrmFMkPipeline.setApplyAmt(new BigDecimal(resMap.get("applyAmt")));
										syn_ocrmFMkPipeline.setApplyCurrency(resMap.get("applyCurrency"));
										syn_ocrmFMkPipeline.setApplyDate(new SimpleDateFormat("yyyy-MM-dd").parse(resMap.get("applyDate")));
										syn_ocrmFMkPipeline.setSubCaseDate(new SimpleDateFormat("yyyy-MM-dd").parse(resMap.get("subCaseDate")));
										syn_ocrmFMkPipeline.setCreater(auth.getUserId());
										syn_ocrmFMkPipeline.setCreateDate(new Date());
										syn_ocrmFMkPipeline.setUpdater(auth.getUserId());
										syn_ocrmFMkPipeline.setUpdateDate(new Date());
										service.save(syn_ocrmFMkPipeline);
										//更改上一条记录
										ocrmFMkPipeline.setIfNext("1");
										ocrmFMkPipeline.setUpdater(syn_ocrmFMkPipeline.getCreater());
										ocrmFMkPipeline.setUpdateDate(syn_ocrmFMkPipeline.getCreateDate());
										service.save(ocrmFMkPipeline);
										//将新数据返回页面
										resMap.put("id", syn_ocrmFMkPipeline.getId() + "");
										list.add(resMap);
										map1.put("data", list);
										this.json.put("json", map1);
									}
								}else{
									//CA流程状态没有值则保留在CA准备阶段
									resMap.put("errMsg", "CA业务流水号不存在!");
									list.add(resMap);
									map1.put("data", list);
									this.json.put("json", map1);
								}
							}
						}
					}
				}else{
					resMap.put("errMsg", "CA业务流水号不存在!");
					list.add(resMap);
					map1.put("data", list);
					this.json.put("json", map1);
				}
			}else{
				//请求失败
				resMap.put("errMsg", "同步信贷系统异常!");
				list.add(resMap);
				map1.put("data", list);
				this.json.put("json", map1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
    }
    
    //同步信贷2
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public String syncLN2(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	OcrmFMkPipeline ocrmFMkPipeline = (OcrmFMkPipeline)model;
    	long id = ocrmFMkPipeline.getId();
    	String caNumber = ocrmFMkPipeline.getCaNumber();
    	String custId = ocrmFMkPipeline.getCustId();
    	String custName = ocrmFMkPipeline.getCustName();
    	String responseXml = "";
    	HashMap<String, String> resMap = new HashMap<String, String>();
    	Map<String, Object> map1 = new HashMap<String, Object>();
    	List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
    	if (this.json != null)
			this.json.clear();
		else
			this.json = new HashMap<String, Object>();
			try {
//				if(caNumber.equals("ca1111")){
//					responseXml = getResponseXml1(custId, custName, caNumber);//已核批已动拨
//				}else if(caNumber.equals("ca1000")){
//					responseXml = getResponseXml10(custId, custName, caNumber);//已核批未动拨
//				}else if(caNumber.equals("ca2222")){
//					responseXml = getResponseXml2(custId, custName, caNumber);//核批阶段
//				}else if(caNumber.equals("ca3333")){
//					responseXml = getResponseXml3(custId, custName, caNumber);//信审阶段
//				}else if(caNumber.equals("ca4444")){
//					responseXml = getResponseXml4(custId, custName, caNumber);//CA准备阶段1-审批中(AO)
//				}else if(caNumber.equals("ca5555")){
//					responseXml = getResponseXml5(custId, custName, caNumber);//CA准备阶段2-打回(AO)
//				}else if(caNumber.equals("ca6666")){
//					responseXml = getResponseXml6(custId, custName, caNumber);//CA准备阶段3-审批中(主管)
//				}else if(caNumber.equals("ca7777")){
//					responseXml = getResponseXml7(custId, custName, caNumber);//拿到返回报文
//				}else if(caNumber.equals("ca9999")){
//					responseXml = getResponseXml9(custId, custName, caNumber);//CA准备阶段4-待发起
//				}else{
//					responseXml = getResponseXml8(custId, custName, caNumber);//CA业务流水号查询不到
//				}
				//调用信贷接口
				responseXml = TranCrmToLN(custId, caNumber);
				
				boolean responseFlag = doResXms(responseXml);
				if(responseFlag){//请求成功，开始处理逻辑
					Document doc = DocumentHelper.parseText(responseXml.substring(8));
					//获取业务报文
					Element resElement = doc.getRootElement().element("Packet").element("Data").element("Res");
					//获取CA业务流水号
					String resCaNumber = resElement.element("caNumber").getTextTrim();
					//首先返回报文中必须要有CA业务流水号，否则判断输入的CA业务流水号不存在
					if(resCaNumber != null && !resCaNumber.equals("")){
						resMap.clear();
						//获取CA流程审批状态
						String caState = resElement.element("caSPState").getTextTrim();
						//获取流程审批岗位id
						String postId = resElement.element("postId").getTextTrim();
						//获取CO提交审批官日期
						String receiveData = resElement.element("receiveData").getTextTrim();
						//获取退回修改列表信息
						Iterator iterator = resElement.element("backEditList").elementIterator("backEdit");
						//获取申请金额
						BigDecimal applyAmt = resElement.element("applyAmt").getTextTrim() != null && !resElement.element("applyAmt").getTextTrim().equals("") ? new BigDecimal(resElement.element("applyAmt").getTextTrim()) : null;
						//获取新案、既有增贷标志
						String newHasCustValue = resElement.element("newHasCust").getTextTrim();
						if(resElement.element("newHasCust").getTextTrim() !=null &&
								resElement.element("newHasCust").getTextTrim().equals("3")){
							newHasCustValue = "2";
						}
						
						/**CA准备阶段
						 *业务场景 1.审批状态为"待发起=000"，则为CA准备阶段;
						 *业务场景 2.审批状态为“审批中=111”,且（审批岗位）节点处于营业部门岗位,则为CA准备阶段;
						 *业务场景 3.审批状态为“打回=992”（退回修改、退回重办）,且（审批岗位）节点处于营业部门岗位,则为CA准备阶段;
						 */
						if(caState != null 
								&& (caState.equals("000") 
									|| (caState.equals("111") && (postId.equals("1001")||postId.equals("2001")||postId.equals("3001") ||postId.equals("1201")||postId.equals("2005")||postId.equals("3005")))
									|| (caState.equals("992") && (postId.equals("1001")||postId.equals("2001")||postId.equals("3001") ||postId.equals("1201")||postId.equals("2005")||postId.equals("3005"))))){
							List<OcrmFMkPipeline> syn_list = service.findByJql("select o from OcrmFMkPipeline o where o.id='" + id + "'", null);
							OcrmFMkPipeline syn_ocrmFMkPipeline = syn_list.get(0);
							
							resMap.put("stepName", "3");//所属阶段
							resMap.put("custId", resElement.element("custId").getTextTrim());//客户号
							resMap.put("custName", resElement.element("custName").getTextTrim());//客户名称
							resMap.put("newHasCust", newHasCustValue);//新户或既有增贷
							resMap.put("insureMoney", resElement.element("insureMoney").getTextTrim());//原额度(折)人民币/千元
							resMap.put("caNumber", resElement.element("caNumber").getTextTrim());//CA业务流水号
							resMap.put("applyAmt", resElement.element("applyAmt").getTextTrim());//申请金额
							resMap.put("applyCurrency", resElement.element("applyCurrency").getTextTrim());//申请币别
							resMap.put("applyDate", resElement.element("applyDate").getTextTrim());//申请日期
							resMap.put("subCaseDate", resElement.element("subCaseDate").getTextTrim());//提案日期
							resMap.put("caValitidy", resElement.element("caValitidy").getTextTrim());//CA生效日期
							resMap.put("caDue", resElement.element("caDue").getTextTrim());//CA到期日
							resMap.put("caSPState", resElement.element("caSPState").getTextTrim());//CA流程审批状态
							resMap.put("custType", resElement.element("custType").getTextTrim());//客户细分
							resMap.put("postId", resElement.element("postId").getTextTrim());//当前审批岗位id
                         	if(newHasCustValue.equals("2")){
                         		resMap.put("subCaseDate", "");//提案日期
                         		resMap.put("insureAmt", "");//核准金额
                         		resMap.put("insureCurrency", "");//核准币种
                         		resMap.put("insureDate", "");//核准日期
                         		resMap.put("firstMAmt", "");//首次动拨金额
                         		resMap.put("firstMCurrency", "");//首次动拨币种
                         		resMap.put("firstMDate", "");//首次动拨日期
//                         		resMap.put("surplusMAmt", "");//剩余动拨余额
//                         		resMap.put("caValidity", "");//CA生效日期
//                         		resMap.put("caDue", "");//CA到期日
                         		resMap.put("receiveDate", "");//业务端审批官接收日期
							}
							//add by yangyue3 2017/06/08
							//将返回报文的金额折成人民币，显示在页面上
							String applyCurrency = resElement.element("applyCurrency").getTextTrim();//申请币别
							StringBuffer sql = new StringBuffer("select ROUND(FN_EXCHANGE_PBOC_BYDATE('" + applyCurrency +"', " + applyAmt + ", to_char(sysdate,'yyyymmdd')),2) AS APPLY_AMT_TORMB from ocrm_f_mk_pipeline T");
							Map<String, Object> map_toRMB = new QueryHelper(sql.toString(), ds.getConnection()).getJSON();
							List<?> list_toRMB = (List<?>) map_toRMB.get("data");
							String APPLY_AMT_TORMB = (String) ((Map<String, Object>)list_toRMB.get(0)).get("APPLY_AMT_TORMB");
							resMap.put("APPLY_AMT_TORMB", APPLY_AMT_TORMB);//申请金额折人民币
							//保存同步信息
							syn_ocrmFMkPipeline.setId(new Date().getTime());
							syn_ocrmFMkPipeline.setNowProgress("3");
							syn_ocrmFMkPipeline.setCustId(resMap.get("custId"));//客户号
							syn_ocrmFMkPipeline.setCustName(resMap.get("custName"));//客户名称
							syn_ocrmFMkPipeline.setNewHasCust(resMap.get("newHasCust"));//新户或既有增贷
							if(resMap.get("insureMoney").equals("")){//原额度(折)人民币
								syn_ocrmFMkPipeline.setInsureMoney(null);
							}else{
								syn_ocrmFMkPipeline.setInsureMoney(new BigDecimal(resMap.get("insureMoney")));
							}
							syn_ocrmFMkPipeline.setCaNumber(resMap.get("caNumber"));//CA业务流水号
							syn_ocrmFMkPipeline.setCaSpState(resMap.get("caSPState"));//CA流程审批状态
							syn_ocrmFMkPipeline.setApplyAmt(applyAmt);//申请金额
							syn_ocrmFMkPipeline.setApplyCurrency(resMap.get("applyCurrency"));//申请币别
							syn_ocrmFMkPipeline.setApplyDate(resMap.get("applyDate") != null && !resMap.get("applyDate").equals("") ? new SimpleDateFormat("yyyy-MM-dd").parse(resMap.get("applyDate")):null);//申请日期
							syn_ocrmFMkPipeline.setFirstInDate(resMap.get("applyDate") != null && !resMap.get("applyDate").equals("") ? new SimpleDateFormat("yyyy-MM-dd").parse(resMap.get("applyDate")):null);//首次进入本阶段日期
							syn_ocrmFMkPipeline.setCreater(auth.getUserId());
							syn_ocrmFMkPipeline.setCreateDate(new Date());
							syn_ocrmFMkPipeline.setUpdater(auth.getUserId());
							syn_ocrmFMkPipeline.setUpdateDate(new Date());
							//既有增贷-ca准备阶段时清空subCaseDate、insureAmt、insureCurrency、insureDate、
							//firstMAmt、firstMCurrency、firstMDate、surplusMAmt、caValitidy
							if(newHasCustValue.equals("2")){
								syn_ocrmFMkPipeline.setSubCaseDate(null);//提案日期，信用审查部CO主管首次接收日期
								syn_ocrmFMkPipeline.setInsureAmt(null);//核准金额
								syn_ocrmFMkPipeline.setInsureCurrency("");//核准币种
								syn_ocrmFMkPipeline.setInsureDate(null);//核准日期
								syn_ocrmFMkPipeline.setFirstMAmt(null);//首次动拨金额
								syn_ocrmFMkPipeline.setFirstMCurrency("");//首次动拨币种
								syn_ocrmFMkPipeline.setFirstMDate(null);//首次动拨日期
//								syn_ocrmFMkPipeline.setSurplusMAmt(null);//剩余动拨余额
//								syn_ocrmFMkPipeline.setCaValidity(null);//CA生效日期
//								syn_ocrmFMkPipeline.setCaDue(null);//CA到期日
								syn_ocrmFMkPipeline.setReceiveDate(null);//业务端审批官接收日期
							}
							service.save(syn_ocrmFMkPipeline);
							//更改上一条记录
							ocrmFMkPipeline.setIfNext("1");
							ocrmFMkPipeline.setUpdater(syn_ocrmFMkPipeline.getCreater());
							ocrmFMkPipeline.setUpdateDate(syn_ocrmFMkPipeline.getCreateDate());
							service.save(ocrmFMkPipeline);
							//现将该案件之前的退回修改记录清空，再重新保存退回修改记录
							hisService.batchUpdateByName("delete from OcrmFMkPipelineBackHis h where h.pipelineId='" + syn_ocrmFMkPipeline.getPipelineId() + "'", null);
							//保存退回修改记录
							while(iterator.hasNext()){
								Element backEdit = (Element) iterator.next();
								//退回修改时间
								String backDate = backEdit.element("backEditTime").getTextTrim();
								//退回修改原因
								String backReason = backEdit.element("backEditReason").getTextTrim();
								OcrmFMkPipelineBackHis his = new OcrmFMkPipelineBackHis();
								his.setId(new Date().getTime());
								his.setPipelineId(syn_ocrmFMkPipeline.getPipelineId());
								his.setBackType("1");//1=退回修改
								his.setBackDate((backDate == null || backDate.equals("")) ? null : new SimpleDateFormat("yyyy-MM-dd").parse(backDate));
								his.setBackReason(backReason == null ? "" :backReason);
								if(his.getBackDate() != null && !his.getBackDate().equals("")){
									hisService.save(his);	
								}
							}
							//将新数据返回页面
							resMap.put("id", syn_ocrmFMkPipeline.getId() + "");
							list.add(resMap);
							map1.put("data", list);
							this.json.put("json", map1);
						}
						/**
						 * 信用审查阶段
						 * 审批状态为111(审批中) & （审批岗位）节点不处于营业部门岗位  & 且审批官接收时间没有值
						 */
						else if(caState != null 
								&& caState.equals("111") 
								&& !(postId.equals("1001")||postId.equals("2001")||postId.equals("3001") ||postId.equals("1201")||postId.equals("2005")||postId.equals("3005")) 
								&& (receiveData == null || receiveData.equals(""))
								&& !postId.equals("")){
							List<OcrmFMkPipeline> syn_list = service.findByJql("select o from OcrmFMkPipeline o where o.id='" + id + "'", null);
							OcrmFMkPipeline syn_ocrmFMkPipeline = syn_list.get(0);
							//进入信审阶段
							resMap.put("stepName", "4");
							resMap.put("custId", resElement.element("custId").getTextTrim());
							resMap.put("custName", resElement.element("custName").getTextTrim());
							resMap.put("newHasCust", newHasCustValue);
							resMap.put("insureMoney", resElement.element("insureMoney").getTextTrim());
							resMap.put("caNumber", resElement.element("caNumber").getTextTrim());
							resMap.put("applyAmt", resElement.element("applyAmt").getTextTrim());
							resMap.put("applyCurrency", resElement.element("applyCurrency").getTextTrim());
							resMap.put("applyDate", resElement.element("applyDate").getTextTrim());
							resMap.put("subCaseDate", resElement.element("subCaseDate").getTextTrim());
							resMap.put("caValitidy", resElement.element("caValitidy").getTextTrim());
							resMap.put("caDue", resElement.element("caDue").getTextTrim());
							resMap.put("caSPState", resElement.element("caSPState").getTextTrim());
							resMap.put("custType", resElement.element("custType").getTextTrim());
							resMap.put("postId", resElement.element("postId").getTextTrim());
						   	if(newHasCustValue.equals("2")){
                         		resMap.put("insureAmt", "");//核准金额
                         		resMap.put("insureCurrency", "");//核准币种
                         		resMap.put("insureDate", "");//核准日期
                         		resMap.put("firstMAmt", "");//首次动拨金额
                         		resMap.put("firstMCurrency", "");//首次动拨币种
                         		resMap.put("firstMDate", "");//首次动拨日期
//                         		resMap.put("surplusMAmt", "");//剩余动拨余额
//                         		resMap.put("caValidity", "");//CA生效日期
//                         		resMap.put("caDue", "");//CA到期日
                         		resMap.put("receiveDate", "");//业务端审批官接收日期
							}
							
							//add by yangyue3 2017/06/08
							//将返回报文的金额折成人民币，显示在页面上
							String applyCurrency = resElement.element("applyCurrency").getTextTrim();
							StringBuffer sql = new StringBuffer("select ROUND(FN_EXCHANGE_PBOC_BYDATE('" + applyCurrency +"', " + applyAmt + ", to_char(sysdate,'yyyymmdd')),2) AS APPLY_AMT_TORMB from ocrm_f_mk_pipeline T");
							Map<String, Object> map_toRMB = new QueryHelper(sql.toString(), ds.getConnection()).getJSON();
							List<?> list_toRMB = (List<?>) map_toRMB.get("data");
							String APPLY_AMT_TORMB = (String) ((Map<String, Object>)list_toRMB.get(0)).get("APPLY_AMT_TORMB");
							resMap.put("APPLY_AMT_TORMB", APPLY_AMT_TORMB);
							
							syn_ocrmFMkPipeline.setId(new Date().getTime());
							syn_ocrmFMkPipeline.setNowProgress("4");
							syn_ocrmFMkPipeline.setCustId(resMap.get("custId"));
							syn_ocrmFMkPipeline.setCustName(resMap.get("custName"));
							syn_ocrmFMkPipeline.setNewHasCust(resMap.get("newHasCust"));
							if(resMap.get("insureMoney").equals("")){
								syn_ocrmFMkPipeline.setInsureMoney(null);
							}else{
								syn_ocrmFMkPipeline.setInsureMoney(new BigDecimal(resMap.get("insureMoney")));
							}
							syn_ocrmFMkPipeline.setCaNumber(resMap.get("caNumber"));
							syn_ocrmFMkPipeline.setCaSpState(resMap.get("caSPState"));
							syn_ocrmFMkPipeline.setApplyAmt(applyAmt);
							syn_ocrmFMkPipeline.setApplyCurrency(resMap.get("applyCurrency"));
							syn_ocrmFMkPipeline.setApplyDate(resMap.get("applyDate") != null && !resMap.get("applyDate").equals("") ? new SimpleDateFormat("yyyy-MM-dd").parse(resMap.get("applyDate")):null);
							syn_ocrmFMkPipeline.setSubCaseDate(resMap.get("subCaseDate") != null && !resMap.get("subCaseDate").equals("") ? new SimpleDateFormat("yyyy-MM-dd").parse(resMap.get("subCaseDate")):null);
							syn_ocrmFMkPipeline.setFirstInDate(resMap.get("subCaseDate") != null && !resMap.get("subCaseDate").equals("") ? new SimpleDateFormat("yyyy-MM-dd").parse(resMap.get("subCaseDate")):null);
							syn_ocrmFMkPipeline.setCreater(auth.getUserId());
							syn_ocrmFMkPipeline.setCreateDate(new Date());
							syn_ocrmFMkPipeline.setUpdater(auth.getUserId());
							syn_ocrmFMkPipeline.setUpdateDate(new Date());
							//既有增贷-信用审查阶段时清空subCaseDate、insureAmt、insureCurrency、insureDate、
							//firstMAmt、firstMCurrency、firstMDate、surplusMAmt、caValitidy
							if(newHasCustValue.equals("2")){
								syn_ocrmFMkPipeline.setInsureAmt(null);//核准金额
								syn_ocrmFMkPipeline.setInsureCurrency("");//核准币种
								syn_ocrmFMkPipeline.setInsureDate(null);//核准日期
								syn_ocrmFMkPipeline.setFirstMAmt(null);//首次动拨金额
								syn_ocrmFMkPipeline.setFirstMCurrency("");//首次动拨币种
								syn_ocrmFMkPipeline.setFirstMDate(null);//首次动拨日期
//								syn_ocrmFMkPipeline.setSurplusMAmt(null);//剩余动拨余额
//								syn_ocrmFMkPipeline.setCaValidity(null);//CA生效日期
//								syn_ocrmFMkPipeline.setCaDue(null);//CA到期日
								syn_ocrmFMkPipeline.setReceiveDate(null);//业务端审批官接收日期
							}
							service.save(syn_ocrmFMkPipeline);
							//更改上一条记录
							ocrmFMkPipeline.setIfNext("1");
							ocrmFMkPipeline.setUpdater(syn_ocrmFMkPipeline.getCreater());
							ocrmFMkPipeline.setUpdateDate(syn_ocrmFMkPipeline.getCreateDate());
							service.save(ocrmFMkPipeline);
							//现将该案件之前的退回修改记录清空，再重新保存退回修改记录
							hisService.batchUpdateByName("delete from OcrmFMkPipelineBackHis h where h.pipelineId='" + syn_ocrmFMkPipeline.getPipelineId() + "'", null);
							//保存退回修改记录
							while(iterator.hasNext()){
								Element backEdit = (Element) iterator.next();
								String backDate = backEdit.element("backEditTime").getTextTrim();
								//退回修改原因
								String backReason = backEdit.element("backEditReason").getTextTrim();
								OcrmFMkPipelineBackHis his = new OcrmFMkPipelineBackHis();
								his.setId(new Date().getTime());
								his.setPipelineId(syn_ocrmFMkPipeline.getPipelineId());
								his.setBackType("1");//1=退回修改
								his.setBackDate((backDate == null || backDate.equals("")) ? null : new SimpleDateFormat("yyyy-MM-dd").parse(backDate));
								his.setBackReason(backReason == null ? "" :backReason);
								if(his.getBackDate() != null && !his.getBackDate().equals("")){
									hisService.save(his);	
								}
							}
							//将新数据返回页面
							resMap.put("id", syn_ocrmFMkPipeline.getId() + "");
							list.add(resMap);
							map1.put("data", list);
							this.json.put("json", map1);
						}
						/**
						 * 核批阶段
						 * 审批状态为111(审批中)，且（审批岗位）节点不处于营业部门岗位,且审批官接收时间有值
						 */
						else if(caState != null 
								&& caState.equals("111") 
								&& !(postId.equals("1001")||postId.equals("2001")||postId.equals("3001") ||postId.equals("1201")||postId.equals("2005")||postId.equals("3005"))  
								&& (receiveData != null && !receiveData.equals(""))
								&& !postId.equals("")){
							List<OcrmFMkPipeline> syn_list = service.findByJql("select o from OcrmFMkPipeline o where o.id='" + id + "'", null);
							OcrmFMkPipeline syn_ocrmFMkPipeline = syn_list.get(0);
							resMap.put("stepName", "5");
							resMap.put("custId", resElement.element("custId").getTextTrim());
							resMap.put("custName", resElement.element("custName").getTextTrim());
							resMap.put("newHasCust", newHasCustValue);
							resMap.put("insureMoney", resElement.element("insureMoney").getTextTrim());
							resMap.put("caNumber", resElement.element("caNumber").getTextTrim());
							resMap.put("applyAmt", resElement.element("applyAmt").getTextTrim());
							resMap.put("applyCurrency", resElement.element("applyCurrency").getTextTrim());
							resMap.put("applyDate", resElement.element("applyDate").getTextTrim());
							resMap.put("subCaseDate", resElement.element("subCaseDate").getTextTrim());
							resMap.put("insureAmt", resElement.element("insureAmt").getTextTrim());
							resMap.put("insureCurrency", resElement.element("insureCurrency").getTextTrim());
							resMap.put("insureDate", resElement.element("insureDate").getTextTrim());
							resMap.put("caValitidy", resElement.element("caValitidy").getTextTrim());
							resMap.put("caDue", resElement.element("caDue").getTextTrim());
							resMap.put("caSPState", resElement.element("caSPState").getTextTrim());
							resMap.put("custType", resElement.element("custType").getTextTrim());
							resMap.put("postId", resElement.element("postId").getTextTrim());
						   	if(newHasCustValue.equals("2")){
                         		resMap.put("insureAmt", "");//核准金额
                         		resMap.put("insureCurrency", "");//核准币种
                         		resMap.put("insureDate", "");//核准日期
                         		resMap.put("firstMAmt", "");//首次动拨金额
                         		resMap.put("firstMCurrency", "");//首次动拨币种
                         		resMap.put("firstMDate", "");//首次动拨日期
                         		resMap.put("surplusMAmt", "");//剩余动拨余额
							}
                     		resMap.put("receiveDate", resElement.element("receiveData").getTextTrim());//业务端审批官接收日期
							//add by yangyue3 2017/06/08
							//将返回报文的金额折成人民币，显示在页面上
							String applyCurrency = resElement.element("applyCurrency").getTextTrim();
							String insureCurrency = resElement.element("insureCurrency").getTextTrim();
							BigDecimal insureyAmt = resElement.element("insureAmt").getTextTrim() != null && !resElement.element("insureAmt").getTextTrim().equals("") ? new BigDecimal(resElement.element("insureAmt").getTextTrim()):null;
							StringBuffer sql = new StringBuffer("select ROUND(FN_EXCHANGE_PBOC_BYDATE('" + applyCurrency +"', " + applyAmt + ", to_char(sysdate,'yyyymmdd')),2) AS APPLY_AMT_TORMB,"
									+ "ROUND(FN_EXCHANGE_PBOC_BYDATE('" + insureCurrency +"', " + insureyAmt + ", to_char(sysdate,'yyyymmdd')),2) AS INSURE_AMT_TORMB from ocrm_f_mk_pipeline T");
							Map<String, Object> map_toRMB = new QueryHelper(sql.toString(), ds.getConnection()).getJSON();
							List<?> list_toRMB = (List<?>) map_toRMB.get("data");
							String APPLY_AMT_TORMB = (String) ((Map<String, Object>)list_toRMB.get(0)).get("APPLY_AMT_TORMB");
							String INSURE_AMT_TORMB = (String) ((Map<String, Object>)list_toRMB.get(0)).get("INSURE_AMT_TORMB");
							resMap.put("APPLY_AMT_TORMB", APPLY_AMT_TORMB);
							resMap.put("INSURE_AMT_TORMB", INSURE_AMT_TORMB);
							
							syn_ocrmFMkPipeline.setId(new Date().getTime());
							syn_ocrmFMkPipeline.setNowProgress("5");
							syn_ocrmFMkPipeline.setCustId(resMap.get("custId"));
							syn_ocrmFMkPipeline.setCustName(resMap.get("custName"));
							syn_ocrmFMkPipeline.setNewHasCust(resMap.get("newHasCust"));
							if(resMap.get("insureMoney").equals("")){
								syn_ocrmFMkPipeline.setInsureMoney(null);
							}else{
								syn_ocrmFMkPipeline.setInsureMoney(new BigDecimal(resMap.get("insureMoney")));
							}
							syn_ocrmFMkPipeline.setCaNumber(resMap.get("caNumber"));
							syn_ocrmFMkPipeline.setCaSpState(resMap.get("caSPState"));
							syn_ocrmFMkPipeline.setApplyAmt(applyAmt);
							syn_ocrmFMkPipeline.setApplyCurrency(resMap.get("applyCurrency"));
							syn_ocrmFMkPipeline.setApplyDate(resMap.get("applyDate") != null && !resMap.get("applyDate").equals("") ? new SimpleDateFormat("yyyy-MM-dd").parse(resMap.get("applyDate")):null);
							syn_ocrmFMkPipeline.setSubCaseDate(resMap.get("subCaseDate") != null && !resMap.get("subCaseDate").equals("") ? new SimpleDateFormat("yyyy-MM-dd").parse(resMap.get("subCaseDate")):null);
							if(resMap.get("insureAmt").equals("")){
								syn_ocrmFMkPipeline.setInsureAmt(null);
							}else{
								syn_ocrmFMkPipeline.setInsureAmt(new BigDecimal(resMap.get("insureAmt")));
							}
							syn_ocrmFMkPipeline.setInsureCurrency(resMap.get("insureCurrency"));
							syn_ocrmFMkPipeline.setInsureDate(resMap.get("insureDate") != null && !resMap.get("insureDate").equals("") ? new SimpleDateFormat("yyyy-MM-dd").parse(resMap.get("insureDate")):null);
							syn_ocrmFMkPipeline.setFirstInDate(resMap.get("receiveDate") != null && !resMap.get("receiveDate").equals("") ? new SimpleDateFormat("yyyy-MM-dd").parse(resMap.get("receiveDate")):null);
							syn_ocrmFMkPipeline.setCreater(auth.getUserId());
							syn_ocrmFMkPipeline.setCreateDate(new Date());
							syn_ocrmFMkPipeline.setUpdater(auth.getUserId());
							syn_ocrmFMkPipeline.setUpdateDate(new Date());
							//既有增贷-信用审查阶段时清空subCaseDate、insureAmt、insureCurrency、insureDate、
							//firstMAmt、firstMCurrency、firstMDate、surplusMAmt、caValitidy
							if(newHasCustValue.equals("2")){
								syn_ocrmFMkPipeline.setInsureAmt(null);//核准金额
								syn_ocrmFMkPipeline.setInsureCurrency("");//核准币种
								syn_ocrmFMkPipeline.setInsureDate(null);//核准日期
								syn_ocrmFMkPipeline.setFirstMAmt(null);//首次动拨金额
								syn_ocrmFMkPipeline.setFirstMCurrency("");//首次动拨币种
								syn_ocrmFMkPipeline.setFirstMDate(null);//首次动拨日期
//								syn_ocrmFMkPipeline.setSurplusMAmt(null);//剩余动拨余额
//								syn_ocrmFMkPipeline.setCaValidity(null);//CA生效日期
//								syn_ocrmFMkPipeline.setCaDue(null);//CA到期日
//								syn_ocrmFMkPipeline.setReceiveDate(null);//业务端审批官接收日期
							}
							syn_ocrmFMkPipeline.setReceiveDate(resMap.get("receiveDate") != null && !resMap.get("receiveDate").equals("") ? new SimpleDateFormat("yyyy-MM-dd").parse(resMap.get("receiveDate")):null);
							service.save(syn_ocrmFMkPipeline);
							//更改上一条记录
							ocrmFMkPipeline.setIfNext("1");
							ocrmFMkPipeline.setUpdater(syn_ocrmFMkPipeline.getCreater());
							ocrmFMkPipeline.setUpdateDate(syn_ocrmFMkPipeline.getCreateDate());
							service.save(ocrmFMkPipeline);
							//现将该案件之前的退回修改记录清空，再重新保存退回修改记录
							hisService.batchUpdateByName("delete from OcrmFMkPipelineBackHis h where h.pipelineId='" + syn_ocrmFMkPipeline.getPipelineId() + "'", null);
							//保存退回修改记录
							while(iterator.hasNext()){
								Element backEdit = (Element) iterator.next();
								String backDate = backEdit.element("backEditTime").getTextTrim();
								//退回修改原因
								String backReason = backEdit.element("backEditReason").getTextTrim();
								OcrmFMkPipelineBackHis his = new OcrmFMkPipelineBackHis();
								his.setId(new Date().getTime());
								his.setPipelineId(syn_ocrmFMkPipeline.getPipelineId());
								his.setBackType("1");//1=退回修改
								his.setBackDate((backDate == null || backDate.equals("")) ? null : new SimpleDateFormat("yyyy-MM-dd").parse(backDate));
								his.setBackReason(backReason == null ? "" :backReason);
								if(his.getBackDate() != null && !his.getBackDate().equals("")){
									hisService.save(his);	
								}
							}
							//将新数据返回页面
							resMap.put("id", syn_ocrmFMkPipeline.getId() + "");
							list.add(resMap);
							map1.put("data", list);
							this.json.put("json", map1);
						}
						/**
						 * 已核批动拨
						 * 审批状态为188(通过（对保文件待签回）)
						 * 审批状态为997(已通过)
						 */
						else if(caState != null && (caState.equals("188") || caState.equals("997"))){
							List<OcrmFMkPipeline> syn_list = service.findByJql("select o from OcrmFMkPipeline o where o.id='" + id + "'", null);
							OcrmFMkPipeline syn_ocrmFMkPipeline = syn_list.get(0);
							resMap.put("stepName", "6");
							resMap.put("custId", resElement.element("custId").getTextTrim());
							resMap.put("custName", resElement.element("custName").getTextTrim());
							resMap.put("newHasCust", newHasCustValue);
							resMap.put("insureMoney", resElement.element("insureMoney").getTextTrim());
							resMap.put("caNumber", resElement.element("caNumber").getTextTrim());
							resMap.put("applyAmt", resElement.element("applyAmt").getTextTrim());
							resMap.put("applyCurrency", resElement.element("applyCurrency").getTextTrim());
							resMap.put("applyDate", resElement.element("applyDate").getTextTrim());
							resMap.put("subCaseDate", resElement.element("subCaseDate").getTextTrim());
							resMap.put("insureAmt", resElement.element("insureAmt").getTextTrim());
							resMap.put("insureCurrency", resElement.element("insureCurrency").getTextTrim());
							resMap.put("insureDate", resElement.element("insureDate").getTextTrim());
							resMap.put("firstMAmt", resElement.element("firstMAmt").getTextTrim());
							resMap.put("firstMCurrency", resElement.element("firstMCurrency").getTextTrim());
							resMap.put("firstMDate", resElement.element("firstMDate").getTextTrim());
							resMap.put("surplusMAmt", resElement.element("surplusMAmt").getTextTrim());
							resMap.put("caValitidy", resElement.element("caValitidy").getTextTrim());
							resMap.put("caDue", resElement.element("caDue").getTextTrim());
							resMap.put("caSPState", resElement.element("caSPState").getTextTrim());
							resMap.put("custType", resElement.element("custType").getTextTrim());
							resMap.put("postId", resElement.element("postId").getTextTrim());
                     		resMap.put("receiveData", resElement.element("receiveData").getTextTrim());//业务端审批官接收日期
							//add by yangyue3 2017/06/08
							//将返回报文的金额折成人民币，显示在页面上
							String applyCurrency = resElement.element("applyCurrency").getTextTrim();
							String insureCurrency = resElement.element("insureCurrency").getTextTrim();
							BigDecimal insureyAmt = resElement.element("insureAmt").getTextTrim() != null && !resElement.element("insureAmt").getTextTrim().equals("") ? new BigDecimal(resElement.element("insureAmt").getTextTrim()) : null;
							String firstMCurrency = resElement.element("firstMCurrency").getTextTrim();
							BigDecimal firstMAmt1 = resElement.element("firstMAmt").getTextTrim() != null && !resElement.element("firstMAmt").getTextTrim().equals("") ? new BigDecimal(resElement.element("firstMAmt").getTextTrim()) : null;
							BigDecimal surplusMAmt = resElement.element("surplusMAmt").getTextTrim() != null && !resElement.element("surplusMAmt").getTextTrim().equals("") ? new BigDecimal(resElement.element("surplusMAmt").getTextTrim()) : null;
							StringBuffer sql = new StringBuffer("select ROUND(FN_EXCHANGE_PBOC_BYDATE('" + applyCurrency +"', " + applyAmt + ", to_char(sysdate,'yyyymmdd')),2) AS APPLY_AMT_TORMB,"
									+ "ROUND(FN_EXCHANGE_PBOC_BYDATE('" + insureCurrency +"', " + insureyAmt + ", to_char(sysdate,'yyyymmdd')),2) AS INSURE_AMT_TORMB,"
									+ "ROUND(FN_EXCHANGE_PBOC_BYDATE('" + firstMCurrency +"', " + firstMAmt1 + ", to_char(sysdate,'yyyymmdd')),2) AS FIRST_M_AMT_TORMB,"
									+ "ROUND(FN_EXCHANGE_PBOC_BYDATE('" + insureCurrency +"', " + surplusMAmt + ", to_char(sysdate,'yyyymmdd')),2) AS SURPLUS_M_AMT_TORMB from ocrm_f_mk_pipeline T");
							Map<String, Object> map_toRMB = new QueryHelper(sql.toString(), ds.getConnection()).getJSON();
							List<?> list_toRMB = (List<?>) map_toRMB.get("data");
							String APPLY_AMT_TORMB = (String) ((Map<String, Object>)list_toRMB.get(0)).get("APPLY_AMT_TORMB");
							String INSURE_AMT_TORMB = (String) ((Map<String, Object>)list_toRMB.get(0)).get("INSURE_AMT_TORMB");
							String FIRST_M_AMT_TORMB = (String) ((Map<String, Object>)list_toRMB.get(0)).get("FIRST_M_AMT_TORMB");
							String SURPLUS_M_AMT_TORMB = (String) ((Map<String, Object>)list_toRMB.get(0)).get("SURPLUS_M_AMT_TORMB");
							resMap.put("APPLY_AMT_TORMB", APPLY_AMT_TORMB);
							resMap.put("INSURE_AMT_TORMB", INSURE_AMT_TORMB);
							resMap.put("FIRST_M_AMT_TORMB", FIRST_M_AMT_TORMB);
							resMap.put("SURPLUS_M_AMT_TORMB", SURPLUS_M_AMT_TORMB);
							
							syn_ocrmFMkPipeline.setId(new Date().getTime());
							syn_ocrmFMkPipeline.setNowProgress("6");
							syn_ocrmFMkPipeline.setCustId(resMap.get("custId"));
							syn_ocrmFMkPipeline.setCustName(resMap.get("custName"));
							syn_ocrmFMkPipeline.setNewHasCust(resMap.get("newHasCust"));
							if(resMap.get("insureMoney").equals("")){
								syn_ocrmFMkPipeline.setInsureMoney(null);
							}else{
								syn_ocrmFMkPipeline.setInsureMoney(new BigDecimal(resMap.get("insureMoney")));
							}
							syn_ocrmFMkPipeline.setCaNumber(resMap.get("caNumber"));
							syn_ocrmFMkPipeline.setCaSpState(resMap.get("caSPState"));
							syn_ocrmFMkPipeline.setApplyAmt(applyAmt);
							syn_ocrmFMkPipeline.setApplyCurrency(resMap.get("applyCurrency"));
							syn_ocrmFMkPipeline.setApplyDate(resMap.get("applyDate") != null && !resMap.get("applyDate").equals("") ? new SimpleDateFormat("yyyy-MM-dd").parse(resMap.get("applyDate")):null);
							syn_ocrmFMkPipeline.setSubCaseDate(resMap.get("subCaseDate") != null && !resMap.get("subCaseDate").equals("") ? new SimpleDateFormat("yyyy-MM-dd").parse(resMap.get("subCaseDate")):null);
							syn_ocrmFMkPipeline.setInsureAmt(insureyAmt);
							syn_ocrmFMkPipeline.setInsureCurrency(resMap.get("insureCurrency"));
							syn_ocrmFMkPipeline.setInsureDate(new SimpleDateFormat("yyyy-MM-dd").parse(resMap.get("insureDate")));
							syn_ocrmFMkPipeline.setFirstMAmt(firstMAmt1);
							syn_ocrmFMkPipeline.setFirstMCurrency(resMap.get("firstMCurrency"));
							syn_ocrmFMkPipeline.setFirstMDate(resMap.get("firstMDate") != null && !resMap.get("firstMDate").equals("") ? new SimpleDateFormat("yyyy-MM-dd").parse(resMap.get("firstMDate")):null);
							syn_ocrmFMkPipeline.setSurplusMAmt(surplusMAmt);
							syn_ocrmFMkPipeline.setFirstInDate(resMap.get("insureDate") != null && !resMap.get("insureDate").equals("") ? new SimpleDateFormat("yyyy-MM-dd").parse(resMap.get("insureDate")):null);
							syn_ocrmFMkPipeline.setCreater(auth.getUserId());
							syn_ocrmFMkPipeline.setCreateDate(new Date());
							syn_ocrmFMkPipeline.setUpdater(auth.getUserId());
							syn_ocrmFMkPipeline.setUpdateDate(new Date());
							syn_ocrmFMkPipeline.setReceiveDate(resMap.get("receiveData") != null && !resMap.get("receiveData").equals("") ? new SimpleDateFormat("yyyy-MM-dd").parse(resMap.get("receiveData")):null);
							service.save(syn_ocrmFMkPipeline);
							//更改上一条记录
							ocrmFMkPipeline.setIfNext("1");
							ocrmFMkPipeline.setUpdater(syn_ocrmFMkPipeline.getCreater());
							ocrmFMkPipeline.setUpdateDate(syn_ocrmFMkPipeline.getCreateDate());
							service.save(ocrmFMkPipeline);
							//现将该案件之前的退回修改记录清空，再重新保存退回修改记录
							hisService.batchUpdateByName("delete from OcrmFMkPipelineBackHis h where h.pipelineId='" + syn_ocrmFMkPipeline.getPipelineId() + "'", null);
							//保存退回修改记录
							while(iterator.hasNext()){
								Element backEdit = (Element) iterator.next();
								String backDate = backEdit.element("backEditTime").getTextTrim();
								//退回修改原因
								String backReason = backEdit.element("backEditReason").getTextTrim();
								OcrmFMkPipelineBackHis his = new OcrmFMkPipelineBackHis();
								his.setId(new Date().getTime());
								his.setPipelineId(syn_ocrmFMkPipeline.getPipelineId());
								his.setBackType("1");//1=退回修改
								his.setBackDate((backDate == null || backDate.equals("")) ? null : new SimpleDateFormat("yyyy-MM-dd").parse(backDate));
								his.setBackReason(backReason == null ? "" :backReason);
								if(his.getBackDate() != null && !his.getBackDate().equals("")){
									hisService.save(his);	
								}
							}
							//将新数据返回页面
							resMap.put("id", syn_ocrmFMkPipeline.getId() + "");
							list.add(resMap);
							map1.put("data", list);
							this.json.put("json", map1);
						}
					}else{ //CA流水号不存在
						resMap.put("errMsg", "CA业务流水号不存在!请输入正确的CA业务流水号!");
						list.add(resMap);
						map1.put("data", list);
						this.json.put("json", map1);
					}
				}else{
					String errorCode = doResXms2(responseXml);
					if(errorCode == null || errorCode.trim().equals("")){
						//请求失败
						resMap.put("errMsg", "调用信贷系统异常!请联系IT");
						list.add(resMap);
						map1.put("data", list);
						this.json.put("json", map1);
					}else if(errorCode.endsWith("111111")){
						resMap.put("errMsg", "客户编号为空!");
						list.add(resMap);
						map1.put("data", list);
						this.json.put("json", map1);
					}else if(errorCode.endsWith("222222")){
						resMap.put("errMsg", "CA业务流水号为空!");
						list.add(resMap);
						map1.put("data", list);
						this.json.put("json", map1);
					}else if(errorCode.endsWith("333333")){
						resMap.put("errMsg", "没有查询到客户信息");
						list.add(resMap);
						map1.put("data", list);
						this.json.put("json", map1);
					}else if(errorCode.endsWith("444444")){
						resMap.put("errMsg", "CA业务流水号不存在!请输入正确的CA业务流水号!");
						list.add(resMap);
						map1.put("data", list);
						this.json.put("json", map1);
					}else if(errorCode.endsWith("ERROR")){
						resMap.put("errMsg", "信贷系统发生异常!");
						list.add(resMap);
						map1.put("data", list);
						this.json.put("json", map1);
					}else {
						resMap.put("errMsg", "调用信贷系统异常!");
						list.add(resMap);
						map1.put("data", list);
						this.json.put("json", map1);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				//同步失败
				list.clear();
				resMap.put("errMsg", "同步信贷系统异常!");
//				resMap.put("errMsg", e.getMessage());
				list.add(resMap);
				map1.put("data", list);
				this.json.put("json", map1);
			}
			
    	return "success";
    }
    
    
    /**
	 * 封装请求报文
	 * SQL 变量必须先赋值才能调用此方法
	 * @param cust_id
	 * @param ca_Number
	 * @return
	 * @throws Exception
	 */
	public String TranCrmToLN(String cust_id,String caNumber) throws Exception{
	   //拼接请求报文
		StringBuffer Xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"GB2312\"?>\n");
		Xml.append("<TransBody>\n");
		Xml.append("  <RequestHeader>\n");
		Xml.append("      <DestSysCd>LN</DestSysCd>\n");
		Xml.append("  </RequestHeader>\n");
		Xml.append("  <RequestBody>\n");
		Xml.append("   <Packet>\n");
		Xml.append("    <Data>\n");
		Xml.append("     <Req>\n");
		Xml.append("      <custId>" + cust_id + "</custId>\n");
		Xml.append("      <caNumber>" + caNumber + "</caNumber>\n");
		Xml.append("     </Req>\n");
		Xml.append("     <Pub>\n");
		Xml.append("       <prcscd>PipelineByCrm</prcscd>\n");
		Xml.append("     </Pub>\n");
		Xml.append("    </Data>\n");
		Xml.append("   </Packet>\n");
		Xml.append("  </RequestBody>\n");
		Xml.append("</TransBody>\n");

	    StringBuffer sbReq = new StringBuffer();
	    sbReq.append(String.format("%08d", Xml.toString().getBytes("GBK").length));
	    sbReq.append(Xml.toString());
	    String req = TransClient.processLN(sbReq.toString());
	    return req;
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
	 * 处理返回报文TxStatCode
	 * @param xml
	 * @return
	 */
	public String doResXms2(String xml) throws Exception{
		try{
			xml=xml.substring(8);
			Document doc = DocumentHelper.parseText(xml);
			Element root = doc.getRootElement();
			String TxStatCode = root.element("ResponseTail").element("TxStatCode").getTextTrim();
			return TxStatCode;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 模拟返回报文-已核批已动拨
	 * 1.有动拨信息，处于已动拨阶段
	 * 信贷当前审批岗位(postId)码值:
	 * 1001=总行客户经理(AO),2001=分行/直属支行 客户经理(AO),3001=支行客户经理(AO),
	 * 1201=TT事业部主管,2005=分行主管,3005=支行主管
	 * 1060=信用审查部审查员,1061=信用审查部主管
	 * 1300=业务端审批官,1301=审查端审批官
	 * 信贷CA流程审批状态(caSPState)码值:
	 * 000=待发起 、111=审批中、992=打回、188=通过（对保文件待签回）、997=通过
	*/
	public String getResponseXml1(String custId, String custName, String caNo) throws Exception {
		// 拼接响应报文
		StringBuffer Xml = new StringBuffer("00000000<?xml version=\"1.0\" encoding=\"GB2312\"?>\n");
		Xml.append("<TransBody>\n");
		Xml.append("  <Packet>\n");
		Xml.append("   <Data>\n");
		Xml.append("    <Res>\n");
		Xml.append("      <custId>" + custId + "</custId>\n");//客户号
		Xml.append("      <custName>" + custName + "</custName>\n");//客户名称
		Xml.append("      <newHasCust>1</newHasCust>\n");//新户或既有增贷
		Xml.append("      <insureMoney></insureMoney>\n");//原额度(折)人民币/千元
		Xml.append("      <caNumber>" + caNo + "</caNumber>\n");//ca业务流水号
		Xml.append("      <applyAmt>10000</applyAmt>\n");//申请金额
		Xml.append("      <applyCurrency>USD</applyCurrency>\n");//申请币别
		Xml.append("      <applyDate>2017-3-4</applyDate>\n");//申请日期
		Xml.append("      <subCaseDate>2017-3-4</subCaseDate>\n");//提案日期,Co主管首次接收案件日期
		Xml.append("      <insureAmt>8000</insureAmt>\n");//核批金额
		Xml.append("      <insureCurrency>USD</insureCurrency>\n");//核批币别
		Xml.append("      <insureDate>2017-4-29</insureDate>\n");//核批日期，信用审查员复核填写的核准日期
		Xml.append("      <firstMAmt>5000</firstMAmt>\n");//首次动拨金额
		Xml.append("      <firstMCurrency>USD</firstMCurrency>\n");//首次动拨币别
		Xml.append("      <firstMDate>2017-5-22</firstMDate>\n");//首次动拨日期
		Xml.append("      <surplusMAmt>3000</surplusMAmt>\n");//剩余动拨余额
		Xml.append("      <caValitidy>2017-1-22</caValitidy>\n");//CA生效日期
		Xml.append("      <caDue>2018-1-22</caDue>\n");//CA到期日
		Xml.append("      <caSPState>997</caSPState>\n");//CA流程审批状态
		Xml.append("      <custType></custType>\n");//客户类型
		Xml.append("      <postId></postId>\n");//当前审批岗位节点
		Xml.append("      <receiveData>2017-5-20</receiveData>\n");//CO提交审批官日期
		Xml.append("      <backEditList>\n");//案件退回修改列表
		Xml.append("      	<backEdit>\n");
		Xml.append("      		<backEditTime>2017-3-28</backEditTime>\n");
		Xml.append("      		<backEditReason>退回修改原因1</backEditReason>\n");
		Xml.append("      	</backEdit>\n");
		Xml.append("      	<backEdit>\n");
		Xml.append("      		<backEditTime>2017-4-8</backEditTime>\n");
		Xml.append("      		<backEditReason>退回修改原因2</backEditReason>\n");
		Xml.append("      	</backEdit>\n");
		Xml.append("      	<backEdit>\n");
		Xml.append("      		<backEditTime>2017-4-22</backEditTime>\n");
		Xml.append("      		<backEditReason>退回修改原因3</backEditReason>\n");
		Xml.append("      	</backEdit>\n");
		Xml.append("      </backEditList>\n");
		Xml.append("      <errorCode>000000</errorCode>\n");
		Xml.append("      <errorMsg>error</errorMsg>\n");
		Xml.append("    </Res>\n");
		Xml.append("   </Data>\n");
		Xml.append("  </Packet>\n");
		Xml.append(" <ResponseTail>\n");
		Xml.append("   <TxStatCode>000000</TxStatCode>\n");
		Xml.append("   <TxStatString>000000</TxStatString>\n");
		Xml.append("   <TxStatDesc>error</TxStatDesc>\n");
		Xml.append(" </ResponseTail>\n");
		Xml.append("</TransBody>\n");
		return Xml.toString();
	}
	
	/**
	 * 模拟返回报文-核批阶段
	 * 2.当前流程审批节点(postId)不处于营业单位  && CA流程审批状态(caSPState)处于 审批中(111)  && 有审批管接收日期(receiveData)
	 * 处于核批阶段,有3次退回记录
	*/
	public String getResponseXml2(String custId, String custName, String caNo) throws Exception {
		// 拼接响应报文
		StringBuffer Xml = new StringBuffer("00000000<?xml version=\"1.0\" encoding=\"GB2312\"?>\n");
//		Xml.append("<TransBody>\n");
//		Xml.append("  <Packet>\n");
//		Xml.append("   <Data>\n");
//		Xml.append("    <Res>\n");
//		Xml.append("      <custId>" + custId + "</custId>\n");
//		Xml.append("      <custName>" + custName + "</custName>\n");
//		Xml.append("      <newHasCust>1</newHasCust>\n");
//		Xml.append("      <insureMoney></insureMoney>\n");
//		Xml.append("      <caNumber>" + caNo + "</caNumber>\n");
//		Xml.append("      <applyAmt>10000</applyAmt>\n");
//		Xml.append("      <applyCurrency>USD</applyCurrency>\n");
//		Xml.append("      <applyDate>2017-3-4</applyDate>\n");
//		Xml.append("      <subCaseDate>2017-3-4</subCaseDate>\n");
//		Xml.append("      <insureAmt>8000</insureAmt>\n");
//		Xml.append("      <insureCurrency>USD</insureCurrency>\n");
//		Xml.append("      <insureDate>2017-4-29</insureDate>\n");
//		Xml.append("      <firstMAmt></firstMAmt>\n");
//		Xml.append("      <firstMCurrency></firstMCurrency>\n");
//		Xml.append("      <firstMDate></firstMDate>\n");
//		Xml.append("      <surplusMAmt></surplusMAmt>\n");
//		Xml.append("      <caValitidy></caValitidy>\n");
//		Xml.append("      <caDue></caDue>\n");
//		Xml.append("      <caSPState>111</caSPState>\n");
//		Xml.append("      <custType></custType>\n");
//		Xml.append("      <postId>1300</postId>\n");//1300=业务端审批官,1301=审查端审批官
//		Xml.append("      <receiveData>2017-5-20</receiveData>\n");
//		Xml.append("      <backEditList>\n");
//		Xml.append("      	<backEdit>\n");
//		Xml.append("      		<backEditTime>2017-3-28</backEditTime>\n");
//		Xml.append("      		<backEditReason>退回修改原因3</backEditReason>\n");
//		Xml.append("      	</backEdit>\n");
//		Xml.append("      	<backEdit>\n");
//		Xml.append("      		<backEditTime>2017-4-8</backEditTime>\n");
//		Xml.append("      		<backEditReason>退回修改原因2</backEditReason>\n");
//		Xml.append("      	</backEdit>\n");
//		Xml.append("      	<backEdit>\n");
//		Xml.append("      		<backEditTime>2017-4-22</backEditTime>\n");
//		Xml.append("      		<backEditReason>退回修改原因1</backEditReason>\n");
//		Xml.append("      	</backEdit>\n");
//		Xml.append("      </backEditList>\n");
//		Xml.append("      <errorCode>000000</errorCode>\n");
//		Xml.append("      <errorMsg>error</errorMsg>\n");
//		Xml.append("    </Res>\n");
//		Xml.append("   </Data>\n");
//		Xml.append("  </Packet>\n");
//		Xml.append(" <ResponseTail>\n");
//		Xml.append("   <TxStatCode>000000</TxStatCode>\n");
//		Xml.append("   <TxStatString>000000</TxStatString>\n");
//		Xml.append("   <TxStatDesc>error</TxStatDesc>\n");
//		Xml.append(" </ResponseTail>\n");
//		Xml.append("</TransBody>\n");
		Xml.append("<TransBody>\n");
		Xml.append("<Packet>\n");
		Xml.append("<Data>\n");
		Xml.append("     <Res>\n");
		Xml.append("       <custId>110000106416</custId>\n");
		Xml.append("      <caNumber>SQ2016011275072</caNumber>\n");
		Xml.append("     <custName>上海北极熊文具胶带有限公司</custName>\n");
		Xml.append("    <insureMoney/>\n");
		Xml.append("   <newHasCust>1</newHasCust>\n");
		Xml.append("  <applyAmt>11000</applyAmt>\n");
		Xml.append("   <applyCurrency>CNY</applyCurrency>\n");
		Xml.append("   <applyDate>2016-01-12</applyDate>\n");
		Xml.append("   <subCaseDate/>\n");
		Xml.append("  <insureAmt/>\n");
		Xml.append("       <insureCurrency/>\n");
		Xml.append("      <insureDate/>\n");
		Xml.append("     <firstMAmt/>\n");
		Xml.append("    <firstMCurrency/>\n");
		Xml.append("    <firstMDate/>\n");
		Xml.append("     <surplusMAmt/>\n");
		Xml.append("     <caValitidy/>\n");
		Xml.append("     <caDue/>\n");
		Xml.append("    <caSPState>992</caSPState>\n");
		Xml.append("   <postId/>\n");
		Xml.append("       <receiveData/>\n");
		Xml.append(" <backEditList>\n");
		Xml.append("    <backEdit>\n");
		Xml.append(" <backEditTime>2017-07-11 17:08:35.0</backEditTime>\n");
		Xml.append(" <backEditReason>2017-07-11 17:10:18.0</backEditReason>\n");
		Xml.append(" </backEdit>\n");
		Xml.append("        </backEditList>\n");
		Xml.append("      <custType>TT</custType>\n");
		Xml.append("  <errorCode>000000</errorCode>\n");
		Xml.append("   <errorMsg>成功</errorMsg>\n");
		Xml.append(" </Res>\n");
		Xml.append(" </Data>\n");
		Xml.append("</Packet>\n");
		Xml.append("<ResponseTail>\n");
		Xml.append(" <TxStatCode>000000</TxStatCode>\n");
		Xml.append("<TxStatString>000000</TxStatString>\n");
		Xml.append(" <TxStatDesc>成功</TxStatDesc>\n");
		Xml.append(" </ResponseTail>\n");
		Xml.append("</TransBody>\n");
		return Xml.toString();
	}
	
	/**
	 * 模拟返回报文-信审阶段
	 * 3.当前流程审批节点(postId)不处于营业单位   && CA流程审批状态(caSPState)处于 审批中(111) && 没有审批管接收日期(receiveData)
	 *  处于信审阶段,有3次退回记录
	 */
	public String getResponseXml3(String custId, String custName, String caNo) throws Exception {
		// 拼接响应报文
		StringBuffer Xml = new StringBuffer("00000000<?xml version=\"1.0\" encoding=\"GB2312\"?>\n");
		Xml.append("<TransBody>\n");
		Xml.append("  <Packet>\n");
		Xml.append("   <Data>\n");
		Xml.append("    <Res>\n");
		Xml.append("      <custId>" + custId + "</custId>\n");
		Xml.append("      <custName>" + custName + "</custName>\n");
		Xml.append("      <newHasCust>1</newHasCust>\n");
		Xml.append("      <insureMoney></insureMoney>\n");
		Xml.append("      <caNumber>" + caNo + "</caNumber>\n");
		Xml.append("      <applyAmt>6000</applyAmt>\n");
		Xml.append("      <applyCurrency>EUR</applyCurrency>\n");
		Xml.append("      <applyDate>2017-3-4</applyDate>\n");
		Xml.append("      <subCaseDate></subCaseDate>\n");
		Xml.append("      <insureAmt></insureAmt>\n");
		Xml.append("      <insureCurrency></insureCurrency>\n");
		Xml.append("      <insureDate></insureDate>\n");
		Xml.append("      <firstMAmt></firstMAmt>\n");
		Xml.append("      <firstMCurrency></firstMCurrency>\n");
		Xml.append("      <firstMDate></firstMDate>\n");
		Xml.append("      <surplusMAmt>2017-3-4</surplusMAmt>\n");
		Xml.append("      <caValitidy></caValitidy>\n");
		Xml.append("      <caDue></caDue>\n");
		Xml.append("      <caSPState></caSPState>\n");
		Xml.append("      <custType></custType>\n");
		Xml.append("      <postId>1061</postId>\n");//1061=审查端审批官,1060=信用审查部审查员
		Xml.append("      <receiveData></receiveData>\n");
		Xml.append("      <backEditList>\n");
		Xml.append("      	<backEdit>\n");
		Xml.append("      		<backEditTime>2017-3-28</backEditTime>\n");
		Xml.append("      		<backEditReason>退回修改原因3</backEditReason>\n");
		Xml.append("      	</backEdit>\n");
		Xml.append("      	<backEdit>\n");
		Xml.append("      		<backEditTime>2017-4-8</backEditTime>\n");
		Xml.append("      		<backEditReason>退回修改原因2</backEditReason>\n");
		Xml.append("      	</backEdit>\n");
		Xml.append("      	<backEdit>\n");
		Xml.append("      		<backEditTime>2017-4-22</backEditTime>\n");
		Xml.append("      		<backEditReason>退回修改原因1</backEditReason>\n");
		Xml.append("      	</backEdit>\n");
		Xml.append("      </backEditList>\n");
		Xml.append("      <errorCode>000000</errorCode>\n");
		Xml.append("      <errorMsg>error</errorMsg>\n");
		Xml.append("    </Res>\n");
		Xml.append("   </Data>\n");
		Xml.append("  </Packet>\n");
		Xml.append(" <ResponseTail>\n");
		Xml.append("   <TxStatCode>000000</TxStatCode>\n");
		Xml.append("   <TxStatString>000000</TxStatString>\n");
		Xml.append("   <TxStatDesc>error</TxStatDesc>\n");
		Xml.append(" </ResponseTail>\n");
		Xml.append("</TransBody>\n");
		return Xml.toString();
	}
	
	/**
	 * 模拟返回报文-CA准备阶段1
	 * 4.CA流程审批状态(caSPState)为审批中(111) && 当前流程审批节点(postId)处于营业单位(AO)
	*/
	public String getResponseXml4(String custId, String custName, String caNo) throws Exception {
		// 拼接响应报文
		StringBuffer Xml = new StringBuffer("00000000<?xml version=\"1.0\" encoding=\"GB2312\"?>\n");
		Xml.append("<TransBody>\n");
		Xml.append("  <Packet>\n");
		Xml.append("   <Data>\n");
		Xml.append("    <Res>\n");
		Xml.append("      <custId>" + custId + "</custId>\n");
		Xml.append("      <custName>" + custName + "</custName>\n");
		Xml.append("      <newHasCust>1</newHasCust>\n");
		Xml.append("      <insureMoney></insureMoney>\n");
		Xml.append("      <caNumber>" + caNo + "</caNumber>\n");
		Xml.append("      <applyAmt>550</applyAmt>\n");
		Xml.append("      <applyCurrency>EUR</applyCurrency>\n");
		Xml.append("      <applyDate>2017-3-4</applyDate>\n");
		Xml.append("      <subCaseDate>2017-3-4</subCaseDate>\n");
		Xml.append("      <insureAmt></insureAmt>\n");
		Xml.append("      <insureCurrency></insureCurrency>\n");
		Xml.append("      <insureDate></insureDate>\n");
		Xml.append("      <firstMAmt></firstMAmt>\n");
		Xml.append("      <firstMCurrency></firstMCurrency>\n");
		Xml.append("      <firstMDate></firstMDate>\n");
		Xml.append("      <surplusMAmt></surplusMAmt>\n");
		Xml.append("      <caValitidy></caValitidy>\n");
		Xml.append("      <caDue></caDue>\n");
		Xml.append("      <caSPState>111</caSPState>\n");
		Xml.append("      <custType></custType>\n");
		Xml.append("      <postId>1001</postId>\n");
		//1001=总行客户经理(AO),2001=分行/直属支行 客户经理(AO),3001=支行客户经理(AO),2005=分行主管,3005=支行主管
		Xml.append("      <receiveData>2017-4-22</receiveData>\n");
		Xml.append("      <backEditList>\n");
		Xml.append("      	<backEdit>\n");
		Xml.append("      		<backEditTime>2017-3-28</backEditTime>\n");
		Xml.append("      		<backEditReason>退回修改原因11</backEditReason>\n");
		Xml.append("      	</backEdit>\n");
		Xml.append("      	<backEdit>\n");
		Xml.append("      		<backEditTime>2017-4-8</backEditTime>\n");
		Xml.append("      		<backEditReason>退回修改原因12</backEditReason>\n");
		Xml.append("      	</backEdit>\n");
		Xml.append("      	<backEdit>\n");
		Xml.append("      		<backEditTime>2017-4-22</backEditTime>\n");
		Xml.append("      		<backEditReason>退回修改原因13</backEditReason>\n");
		Xml.append("      	</backEdit>\n");
		Xml.append("      </backEditList>\n");
		Xml.append("      <errorCode>000000</errorCode>\n");
		Xml.append("      <errorMsg>error</errorMsg>\n");
		Xml.append("    </Res>\n");
		Xml.append("   </Data>\n");
		Xml.append("  </Packet>\n");
		Xml.append(" <ResponseTail>\n");
		Xml.append("   <TxStatCode>000000</TxStatCode>\n");
		Xml.append("   <TxStatString>000000</TxStatString>\n");
		Xml.append("   <TxStatDesc>error</TxStatDesc>\n");
		Xml.append(" </ResponseTail>\n");
		Xml.append("</TransBody>\n");
		return Xml.toString();
	}
	
	/**
	 * 模拟返回报文-CA准备阶段2
	 * 5.CA流程审批状态(caSPState)为打回(992) && 当前流程审批节点(postId)处于营业单位(AO)
	*/
	public String getResponseXml5(String custId, String custName, String caNo) throws Exception {
		// 拼接响应报文
		StringBuffer Xml = new StringBuffer("00000000<?xml version=\"1.0\" encoding=\"GB2312\"?>\n");
		Xml.append("<TransBody>\n");
		Xml.append("  <Packet>\n");
		Xml.append("   <Data>\n");
		Xml.append("    <Res>\n");
		Xml.append("      <custId>" + custId + "</custId>\n");
		Xml.append("      <custName>" + custName + "</custName>\n");
		Xml.append("      <newHasCust>1</newHasCust>\n");
		Xml.append("      <insureMoney></insureMoney>\n");
		Xml.append("      <caNumber>" + caNo + "</caNumber>\n");
		Xml.append("      <applyAmt>1050</applyAmt>\n");
		Xml.append("      <applyCurrency>EUR</applyCurrency>\n");
		Xml.append("      <applyDate>2017-3-4</applyDate>\n");
		Xml.append("      <subCaseDate>2017-3-4</subCaseDate>\n");
		Xml.append("      <insureAmt></insureAmt>\n");
		Xml.append("      <insureCurrency></insureCurrency>\n");
		Xml.append("      <insureDate></insureDate>\n");
		Xml.append("      <firstMAmt></firstMAmt>\n");
		Xml.append("      <firstMCurrency></firstMCurrency>\n");
		Xml.append("      <firstMDate></firstMDate>\n");
		Xml.append("      <surplusMAmt></surplusMAmt>\n");
		Xml.append("      <caValitidy></caValitidy>\n");
		Xml.append("      <caDue></caDue>\n");
		Xml.append("      <caSPState>992</caSPState>\n");
		Xml.append("      <custType></custType>\n");
		Xml.append("      <postId>2001</postId>\n");
		//1001=总行客户经理(AO),2001=分行/直属支行 客户经理(AO),3001=支行客户经理(AO),2005=分行主管,3005=支行主管
		Xml.append("      <receiveData></receiveData>\n");
		Xml.append("      <backEditList>\n");
		Xml.append("      	<backEdit>\n");
		Xml.append("      		<backEditTime>2017-3-28</backEditTime>\n");
		Xml.append("      		<backEditReason>退回修改原因1</backEditReason>\n");
		Xml.append("      	</backEdit>\n");
		Xml.append("      	<backEdit>\n");
		Xml.append("      		<backEditTime>2017-4-8</backEditTime>\n");
		Xml.append("      		<backEditReason>退回修改原因2</backEditReason>\n");
		Xml.append("      	</backEdit>\n");
		Xml.append("      	<backEdit>\n");
		Xml.append("      		<backEditTime>2017-4-22</backEditTime>\n");
		Xml.append("      		<backEditReason>退回修改原因3</backEditReason>\n");
		Xml.append("      	</backEdit>\n");
		Xml.append("      </backEditList>\n");
		Xml.append("      <errorCode>000000</errorCode>\n");
		Xml.append("      <errorMsg>error</errorMsg>\n");
		Xml.append("    </Res>\n");
		Xml.append("   </Data>\n");
		Xml.append("  </Packet>\n");
		Xml.append(" <ResponseTail>\n");
		Xml.append("   <TxStatCode>000000</TxStatCode>\n");
		Xml.append("   <TxStatString>000000</TxStatString>\n");
		Xml.append("   <TxStatDesc>error</TxStatDesc>\n");
		Xml.append(" </ResponseTail>\n");
		Xml.append("</TransBody>\n");
		return Xml.toString();
	}
	
	/**
	 * 模拟返回报文-CA准备阶段3
	 * 6.CA流程审批状态(caSPState)为审批中(111) && 当前流程审批节点(postId)处于营业单位(主管)
	*/
	public String getResponseXml6(String custId, String custName, String caNo) throws Exception {
		// 拼接响应报文
		StringBuffer Xml = new StringBuffer("00000000<?xml version=\"1.0\" encoding=\"GB2312\"?>\n");
		Xml.append("<TransBody>\n");
		Xml.append("  <Packet>\n");
		Xml.append("   <Data>\n");
		Xml.append("    <Res>\n");
		Xml.append("      <custId>" + custId + "</custId>\n");
		Xml.append("      <custName>" + custName + "</custName>\n");
		Xml.append("      <newHasCust>1</newHasCust>\n");
		Xml.append("      <insureMoney></insureMoney>\n");
		Xml.append("      <caNumber>" + caNo + "</caNumber>\n");
		Xml.append("      <applyAmt>1050</applyAmt>\n");
		Xml.append("      <applyCurrency>EUR</applyCurrency>\n");
		Xml.append("      <applyDate>2017-3-4</applyDate>\n");
		Xml.append("      <subCaseDate>2017-3-4</subCaseDate>\n");
		Xml.append("      <insureAmt></insureAmt>\n");
		Xml.append("      <insureCurrency></insureCurrency>\n");
		Xml.append("      <insureDate></insureDate>\n");
		Xml.append("      <firstMAmt></firstMAmt>\n");
		Xml.append("      <firstMCurrency></firstMCurrency>\n");
		Xml.append("      <firstMDate></firstMDate>\n");
		Xml.append("      <surplusMAmt></surplusMAmt>\n");
		Xml.append("      <caValitidy></caValitidy>\n");
		Xml.append("      <caDue></caDue>\n");
		Xml.append("      <caSPState>111</caSPState>\n");
		Xml.append("      <custType></custType>\n");
		Xml.append("      <postId>2005</postId>\n");
		//1001=总行客户经理(AO),2001=分行/直属支行 客户经理(AO),3001=支行客户经理(AO),2005=分行主管,3005=支行主管
		Xml.append("      <receiveData></receiveData>\n");
		Xml.append("      <backEditList>\n");
		Xml.append("      	<backEdit>\n");
		Xml.append("      		<backEditTime>2017-3-28</backEditTime>\n");
		Xml.append("      		<backEditReason>退回修改原因3</backEditReason>\n");
		Xml.append("      	</backEdit>\n");
		Xml.append("      	<backEdit>\n");
		Xml.append("      		<backEditTime>2017-4-8</backEditTime>\n");
		Xml.append("      		<backEditReason>退回修改原因2</backEditReason>\n");
		Xml.append("      	</backEdit>\n");
		Xml.append("      	<backEdit>\n");
		Xml.append("      		<backEditTime>2017-4-22</backEditTime>\n");
		Xml.append("      		<backEditReason>退回修改原因1</backEditReason>\n");
		Xml.append("      	</backEdit>\n");
		Xml.append("      </backEditList>\n");
		Xml.append("      <errorCode>000000</errorCode>\n");
		Xml.append("      <errorMsg>error</errorMsg>\n");
		Xml.append("    </Res>\n");
		Xml.append("   </Data>\n");
		Xml.append("  </Packet>\n");
		Xml.append(" <ResponseTail>\n");
		Xml.append("   <TxStatCode>000000</TxStatCode>\n");
		Xml.append("   <TxStatString>000000</TxStatString>\n");
		Xml.append("   <TxStatDesc>error</TxStatDesc>\n");
		Xml.append(" </ResponseTail>\n");
		Xml.append("</TransBody>\n");
		return Xml.toString();
	}
	
	/**
	 * 模拟返回报文
	 *
	*/
	public String getResponseXml7(String custId, String custName, String caNo) throws Exception {
		// 拼接响应报文
		StringBuffer Xml = new StringBuffer("00000000<?xml version=\"1.0\" encoding=\"GB2312\"?>\n");
		Xml.append("<TransBody>\n");
		Xml.append("  <Packet>\n");
		Xml.append("   <Data>\n");
		Xml.append("    <Res>\n");
		Xml.append("      <custId>" + custId + "</custId>\n");
		Xml.append("      <custName>" + custName + "</custName>\n");
		Xml.append("      <newHasCust>1</newHasCust>\n");
		Xml.append("      <insureMoney></insureMoney>\n");
		Xml.append("      <caNumber>" + caNo + "</caNumber>\n");
		Xml.append("      <applyAmt></applyAmt>\n");
		Xml.append("      <applyCurrency></applyCurrency>\n");
		Xml.append("      <applyDate></applyDate>\n");
		Xml.append("      <subCaseDate></subCaseDate>\n");
		Xml.append("      <insureAmt></insureAmt>\n");
		Xml.append("      <insureCurrency></insureCurrency>\n");
		Xml.append("      <insureDate></insureDate>\n");
		Xml.append("      <firstMAmt></firstMAmt>\n");
		Xml.append("      <firstMCurrency></firstMCurrency>\n");
		Xml.append("      <firstMDate></firstMDate>\n");
		Xml.append("      <surplusMAmt></surplusMAmt>\n");
		Xml.append("      <caValitidy></caValitidy>\n");
		Xml.append("      <caDue></caDue>\n");
		Xml.append("      <caSPState>111</caSPState>\n");
		Xml.append("      <custType></custType>\n");
		Xml.append("      <postId>1001</postId>\n");
		Xml.append("      <receiveData></receiveData>\n");
		Xml.append("      <errorCode>000000</errorCode>\n");
		Xml.append("      <errorMsg>error</errorMsg>\n");
		Xml.append("      <backEditList>\n");
		Xml.append("      	<backEdit>\n");
		Xml.append("      		<backEditTime></backEditTime>\n");
		Xml.append("      		<backEditReason></backEditReason>\n");
		Xml.append("      	</backEdit>\n");
		Xml.append("      </backEditList>\n");
		Xml.append("    </Res>\n");
		Xml.append("   </Data>\n");
		Xml.append("  </Packet>\n");
		Xml.append(" <ResponseTail>\n");
		Xml.append("   <TxStatCode>000000</TxStatCode>\n");
		Xml.append("   <TxStatString>000000</TxStatString>\n");
		Xml.append("   <TxStatDesc>error</TxStatDesc>\n");
		Xml.append(" </ResponseTail>\n");
		Xml.append("</TransBody>\n");
		return Xml.toString();
	}
	
	/**
	 * 模拟返回报文
	 * 8.无CA流水号,页面提示“CA流水号不存在”
	*/
	public String getResponseXml8(String custId, String custName, String caNo) throws Exception {
		// 拼接响应报文
		StringBuffer Xml = new StringBuffer("00000000<?xml version=\"1.0\" encoding=\"GB2312\"?>\n");
		Xml.append("<TransBody>\n");
		Xml.append("  <Packet>\n");
		Xml.append("   <Data>\n");
		Xml.append("    <Res>\n");
		Xml.append("      <custId>" + custId + "</custId>\n");
		Xml.append("      <custName>" + custName + "</custName>\n");
		Xml.append("      <newHasCust></newHasCust>\n");
		Xml.append("      <insureMoney></insureMoney>\n");
		Xml.append("      <caNumber></caNumber>\n");
		Xml.append("      <applyAmt></applyAmt>\n");
		Xml.append("      <applyCurrency></applyCurrency>\n");
		Xml.append("      <applyDate></applyDate>\n");
		Xml.append("      <subCaseDate>2017-3-4</subCaseDate>\n");
		Xml.append("      <insureAmt></insureAmt>\n");
		Xml.append("      <insureCurrency></insureCurrency>\n");
		Xml.append("      <insureDate></insureDate>\n");
		Xml.append("      <firstMAmt></firstMAmt>\n");
		Xml.append("      <firstMCurrency></firstMCurrency>\n");
		Xml.append("      <firstMDate></firstMDate>\n");
		Xml.append("      <surplusMAmt></surplusMAmt>\n");
		Xml.append("      <caValitidy></caValitidy>\n");
		Xml.append("      <caDue></caDue>\n");
		Xml.append("      <caSPState></caSPState>\n");
		Xml.append("      <custType></custType>\n");
		Xml.append("      <postId></postId>\n");
		Xml.append("      <receiveData></receiveData>\n");
		Xml.append("      <backEditList>\n");
		Xml.append("      	<backEdit>\n");
		Xml.append("      		<backEditTime></backEditTime>\n");
		Xml.append("      		<backEditReason></backEditReason>\n");
		Xml.append("      	</backEdit>\n");
		Xml.append("      </backEditList>\n");
		Xml.append("      <errorCode>000000</errorCode>\n");
		Xml.append("      <errorMsg>error</errorMsg>\n");
		Xml.append("    </Res>\n");
		Xml.append("   </Data>\n");
		Xml.append("  </Packet>\n");
		Xml.append(" <ResponseTail>\n");
		Xml.append("   <TxStatCode>000000</TxStatCode>\n");
		Xml.append("   <TxStatString>000000</TxStatString>\n");
		Xml.append("   <TxStatDesc>error</TxStatDesc>\n");
		Xml.append(" </ResponseTail>\n");
		Xml.append("</TransBody>\n");
		return Xml.toString();
	}
	
	/**
	 * 模拟返回报文-CA准备阶段4
	 * 9.CA流程审批状态为“待发起=000”，判断为进入CA准备状态
	*/
	public String getResponseXml9(String custId, String custName, String caNo) throws Exception {
		// 拼接响应报文
		StringBuffer Xml = new StringBuffer("00000000<?xml version=\"1.0\" encoding=\"GB2312\"?>\n");
		Xml.append("<TransBody>\n");
		Xml.append("  <Packet>\n");
		Xml.append("   <Data>\n");
		Xml.append("    <Res>\n");
		Xml.append("      <custId>" + custId + "</custId>\n");
		Xml.append("      <custName>" + custName + "</custName>\n");
		Xml.append("      <newHasCust>2</newHasCust>\n");
		Xml.append("      <insureMoney>3000</insureMoney>\n");
		Xml.append("      <caNumber>" + caNo + "</caNumber>\n");
		Xml.append("      <applyAmt>3333</applyAmt>\n");
		Xml.append("      <applyCurrency>AUD</applyCurrency>\n");
		Xml.append("      <applyDate>2017-3-21</applyDate>\n");
		Xml.append("      <subCaseDate>2017-3-4</subCaseDate>\n");
		Xml.append("      <insureAmt></insureAmt>\n");
		Xml.append("      <insureCurrency></insureCurrency>\n");
		Xml.append("      <insureDate></insureDate>\n");
		Xml.append("      <firstMAmt></firstMAmt>\n");
		Xml.append("      <firstMCurrency></firstMCurrency>\n");
		Xml.append("      <firstMDate></firstMDate>\n");
		Xml.append("      <surplusMAmt></surplusMAmt>\n");
		Xml.append("      <caValitidy></caValitidy>\n");
		Xml.append("      <caDue></caDue>\n");
		Xml.append("      <caSPState>000</caSPState>\n");
		Xml.append("      <custType></custType>\n");
		Xml.append("      <postId></postId>\n");
		Xml.append("      <receiveData></receiveData>\n");
		Xml.append("      <backEditList>\n");
		Xml.append("      	<backEdit>\n");
		Xml.append("      		<backEditTime></backEditTime>\n");
		Xml.append("      		<backEditReason></backEditReason>\n");
		Xml.append("      	</backEdit>\n");
		Xml.append("      </backEditList>\n");
		Xml.append("      <errorCode>000000</errorCode>\n");
		Xml.append("      <errorMsg>error</errorMsg>\n");
		Xml.append("    </Res>\n");
		Xml.append("   </Data>\n");
		Xml.append("  </Packet>\n");
		Xml.append(" <ResponseTail>\n");
		Xml.append("   <TxStatCode>000000</TxStatCode>\n");
		Xml.append("   <TxStatString>000000</TxStatString>\n");
		Xml.append("   <TxStatDesc>error</TxStatDesc>\n");
		Xml.append(" </ResponseTail>\n");
		Xml.append("</TransBody>\n");
		return Xml.toString();
	}
	
	/**
	 * 模拟返回报文-已核批未动拨
	 * 10.CA流程审批状态(caSPState)处于188或997 && 没有首次动拨信息(firstMAmt)
	*/
	public String getResponseXml10(String custId, String custName, String caNo) throws Exception {
		// 拼接响应报文
		StringBuffer Xml = new StringBuffer("00000000<?xml version=\"1.0\" encoding=\"GB2312\"?>\n");
		Xml.append("<TransBody>\n");
		Xml.append("  <Packet>\n");
		Xml.append("   <Data>\n");
		Xml.append("    <Res>\n");
		Xml.append("      <custId>" + custId + "</custId>\n");//客户号
		Xml.append("      <custName>" + custName + "</custName>\n");//客户名称
		Xml.append("      <newHasCust>1</newHasCust>\n");//新户或既有增贷
		Xml.append("      <insureMoney></insureMoney>\n");//原额度(折)人民币/千元
		Xml.append("      <caNumber>" + caNo + "</caNumber>\n");//ca业务流水号
		Xml.append("      <applyAmt>10000</applyAmt>\n");//申请金额
		Xml.append("      <applyCurrency>USD</applyCurrency>\n");//申请币别
		Xml.append("      <applyDate>2017-3-4</applyDate>\n");//申请日期
		Xml.append("      <subCaseDate>2017-3-4</subCaseDate>\n");//提案日期,Co主管首次接收案件日期
		Xml.append("      <insureAmt>8000</insureAmt>\n");//核批金额
		Xml.append("      <insureCurrency>USD</insureCurrency>\n");//核批币别
		Xml.append("      <insureDate>2017-4-29</insureDate>\n");//核批日期，信用审查员复核填写的核准日期
		Xml.append("      <firstMAmt></firstMAmt>\n");//首次动拨金额
		Xml.append("      <firstMCurrency></firstMCurrency>\n");//首次动拨币别
		Xml.append("      <firstMDate></firstMDate>\n");//首次动拨日期
		Xml.append("      <surplusMAmt></surplusMAmt>\n");//剩余动拨余额
		Xml.append("      <caValitidy>2017-1-22</caValitidy>\n");//CA生效日期
		Xml.append("      <caDue>2018-1-22</caDue>\n");//CA到期日
		Xml.append("      <caSPState>188</caSPState>\n");//CA流程审批状态（188、997）
		Xml.append("      <custType></custType>\n");//客户类型
		Xml.append("      <postId></postId>\n");//当前审批岗位节点，流程已结束
		Xml.append("      <receiveData>2017-5-20</receiveData>\n");//CO提交审批官日期
		Xml.append("      <backEditList>\n");//案件退回修改列表
		Xml.append("      	<backEdit>\n");
		Xml.append("      		<backEditTime>2017-3-28</backEditTime>\n");
		Xml.append("      		<backEditReason>退回修改原因1</backEditReason>\n");
		Xml.append("      	</backEdit>\n");
		Xml.append("      	<backEdit>\n");
		Xml.append("      		<backEditTime>2017-4-8</backEditTime>\n");
		Xml.append("      		<backEditReason>退回修改原因2</backEditReason>\n");
		Xml.append("      	</backEdit>\n");
		Xml.append("      	<backEdit>\n");
		Xml.append("      		<backEditTime>2017-4-22</backEditTime>\n");
		Xml.append("      		<backEditReason>退回修改原因3</backEditReason>\n");
		Xml.append("      	</backEdit>\n");
		Xml.append("      </backEditList>\n");
		Xml.append("      <errorCode>000000</errorCode>\n");
		Xml.append("      <errorMsg>error</errorMsg>\n");
		Xml.append("    </Res>\n");
		Xml.append("   </Data>\n");
		Xml.append("  </Packet>\n");
		Xml.append(" <ResponseTail>\n");
		Xml.append("   <TxStatCode>000000</TxStatCode>\n");
		Xml.append("   <TxStatString>000000</TxStatString>\n");
		Xml.append("   <TxStatDesc>error</TxStatDesc>\n");
		Xml.append(" </ResponseTail>\n");
		Xml.append("</TransBody>\n");
		return Xml.toString();
	}
}
