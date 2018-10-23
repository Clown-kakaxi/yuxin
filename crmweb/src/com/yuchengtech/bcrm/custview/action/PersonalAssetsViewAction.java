package com.yuchengtech.bcrm.custview.action;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custview.model.AcrmFCiPerFinance;
import com.yuchengtech.bcrm.custview.service.AcrmFCiPerFinanceService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.crm.exception.BizException;

/**
 * 对私客户视图（个人理财规划概览）
 * @author denghj
 * @since 2015-2-23
 */

@SuppressWarnings("serial")
@Action("/personalAssetsView")
public class PersonalAssetsViewAction extends CommonAction {
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
	private AcrmFCiPerFinanceService service;
	
	@Autowired
	public void init(){
		model = new AcrmFCiPerFinance();
		setCommonService(service);
	}
	
	public void prepare(){
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String custId = request.getParameter("custId");
		StringBuffer sb = new StringBuffer();
		sb.append("select fi.finance_id,fi.cust_id,fi.system_pro,fi.manager_pro,fi.manager_pro_zm,fi.last_update_tm,");
		sb.append("case when fi.manager_pro is null then fi.system_pro_zm else '' end as system_pro_zm,");
		sb.append("tt.ansower2 family_income,tt.ansower7 invest_expr,tt.ansower8 invest_cycle,tt.score_all,cu.cust_name,cu.current_aum/10000 current_aum,");
		sb.append("case when to_number(to_char(sysdate,'mm')) - to_number(to_char(pe.birthday,'mm')) >= 0 then  to_number(to_char(sysdate,'yyyy')) - to_number(to_char(pe.birthday,'yyyy')) ");
        sb.append("else to_number(to_char(sysdate,'yyyy')) - to_number(to_char(pe.birthday,'yyyy'))-1 end cust_age "); 
		sb.append("from (SELECT T1.CUST_ID,T1.SYSTEM_PRO,fun_product_codestonames(T1.SYSTEM_PRO) SYSTEM_PRO_ZM,T1.MANAGER_PRO,");
		sb.append("fun_product_codestonames(T1.MANAGER_PRO) MANAGER_PRO_ZM,T1.LAST_UPDATE_TM,T1.FINANCE_ID FROM ACRM_F_CI_PER_FINANCE T1) fi ");
		sb.append("left join ACRM_F_CI_CUSTOMER cu on fi.cust_id = cu.cust_id ");
		sb.append("left join ACRM_F_CI_PERSON pe on fi.cust_id = pe.cust_id ");
		sb.append("left join (SELECT core_no,max(score_all) score_all,MAX(ANSOWER2) ANSOWER2, MAX(ANSOWER7) ANSOWER7, MAX(ANSOWER8) ANSOWER8 FROM (SELECT core_no,score_all,");
		sb.append("case when dtitle = '2' and dvalue = '10' AND (TEMPLATEID IS NULL OR TEMPLATEID = 1) then '80万元以上(含)' ");
		sb.append("		when dtitle = '2' and dvalue = '8' AND (TEMPLATEID IS NULL OR TEMPLATEID = 1) then '50(含)-80万元' ");
		sb.append("		when dtitle = '2' and dvalue = '5' AND (TEMPLATEID IS NULL OR TEMPLATEID = 1) then '30(含)-50万元' ");
		sb.append("		when dtitle = '2' and dvalue = '3' AND (TEMPLATEID IS NULL OR TEMPLATEID = 1) then '10(含)-30万元' ");
		sb.append("		when dtitle = '2' and dvalue = '0' AND (TEMPLATEID IS NULL OR TEMPLATEID = 1) then '10万元以下' ");
        sb.append("		when dtitle = '2' and dvalue = '10' AND (TEMPLATEID IS NOT NULL AND TEMPLATEID <> 1) then '1000万元（不含）以上' ");
        sb.append("		when dtitle = '2' and dvalue = '8' AND (TEMPLATEID IS NOT NULL AND TEMPLATEID <> 1) then '100-1000万元（含）' ");
        sb.append("		when dtitle = '2' and dvalue = '6' AND (TEMPLATEID IS NOT NULL AND TEMPLATEID <> 1) then '50（不含）-100万元（含）' ");
        sb.append("		when dtitle = '2' and dvalue = '2' AND (TEMPLATEID IS NOT NULL AND TEMPLATEID <> 1) then '15（不含）-50万元（含）' ");
        sb.append("		when dtitle = '2' and dvalue = '0' AND (TEMPLATEID IS NOT NULL AND TEMPLATEID <> 1) then '15万元以下' ");
        sb.append("END ANSOWER2,");
		sb.append("case when dtitle = '7' and dvalue = '10' AND (TEMPLATEID IS NULL OR TEMPLATEID = 1) then '5年以上' ");
		sb.append("		when dtitle = '7' and dvalue = '8' AND (TEMPLATEID IS NULL OR TEMPLATEID = 1) then '3-5年' ");
		sb.append("		when dtitle = '7' and dvalue = '5' AND (TEMPLATEID IS NULL OR TEMPLATEID = 1) then '1-3年' ");
		sb.append("		when dtitle = '7' and dvalue = '3' AND (TEMPLATEID IS NULL OR TEMPLATEID = 1) then '1年以内' ");
		sb.append("		when dtitle = '7' and dvalue = '0' AND (TEMPLATEID IS NULL OR TEMPLATEID = 1) then '无经验' ");
        sb.append("		when dtitle = '7' and dvalue = '10' AND (TEMPLATEID IS NOT NULL AND TEMPLATEID <> 1) then '8年以上' ");
        sb.append("		when dtitle = '7' and dvalue = '8' AND (TEMPLATEID IS NOT NULL AND TEMPLATEID <> 1) then '5-8年' ");
        sb.append("		when dtitle = '7' and dvalue = '6' AND (TEMPLATEID IS NOT NULL AND TEMPLATEID <> 1) then '2-5年' ");
        sb.append("		when dtitle = '7' and dvalue = '2' AND (TEMPLATEID IS NOT NULL AND TEMPLATEID <> 1) then '有经验，但少于2年' ");
        sb.append("		when dtitle = '7' and dvalue = '0' AND (TEMPLATEID IS NOT NULL AND TEMPLATEID <> 1) then '无经验' ");
        sb.append("END ANSOWER7,");
        sb.append("case when dtitle = '8' and dvalue = '10' AND (TEMPLATEID IS NULL OR TEMPLATEID = 1) then '5年以上' ");
        sb.append("		when dtitle = '8' and dvalue = '8' AND (TEMPLATEID IS NULL OR TEMPLATEID = 1) then '3-5年' ");
        sb.append("		when dtitle = '8' and dvalue = '5' AND (TEMPLATEID IS NULL OR TEMPLATEID = 1) then '2-3年' ");
        sb.append("		when dtitle = '8' and dvalue = '3' AND (TEMPLATEID IS NULL OR TEMPLATEID = 1) then '1-2年' ");
        sb.append("		when dtitle = '8' and dvalue = '0' AND (TEMPLATEID IS NULL OR TEMPLATEID = 1) then '1年以内' ");
        sb.append("		when dtitle = '8' and dvalue = '10' AND (TEMPLATEID IS NOT NULL AND TEMPLATEID <> 1) then '5年以上' ");
        sb.append("		when dtitle = '8' and dvalue = '8' AND (TEMPLATEID IS NOT NULL AND TEMPLATEID <> 1) then '3-5年' ");
        sb.append("		when dtitle = '8' and dvalue = '6' AND (TEMPLATEID IS NOT NULL AND TEMPLATEID <> 1) then '2-3年' ");
        sb.append("		when dtitle = '8' and dvalue = '4' AND (TEMPLATEID IS NOT NULL AND TEMPLATEID <> 1) then '1-2年' ");
        sb.append("		when dtitle = '8' and dvalue = '2' AND (TEMPLATEID IS NOT NULL AND TEMPLATEID <> 1) then '1年以内' ");
        sb.append("END ANSOWER8 ");
        sb.append("FROM OCRM_F_CI_RISK_INFO) group by core_no) tt ");
        sb.append("on cu.core_no = tt.core_no ");
		sb.append("where 1=1 ");
		if(custId != "" && custId != null){
			sb.append("and fi.cust_id = '"+custId+"' ");
		}
        sb.append("order by fi.last_update_tm desc");
		SQL = sb.toString();
		datasource = ds;
	}
	
	/**
	 * 判断客户是否进行风险评估测试
	 * @param custId
	 * @return
	 * @throws SQLException 
	 */
	public String judgeRiskInfo(){
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String custId = request.getParameter("custId");
		String sql = "select max(te.score_all) score_all from OCRM_F_CI_RISK_INFO te where te.core_no in (select cu.core_no from ACRM_F_CI_CUSTOMER cu where cu.cust_id = '"+custId+"')";
		
		if(this.json!=null)
    		this.json.clear();
    	else 
    		this.json = new HashMap<String,Object>(); 
		try {
			Map<String, Object> result=new QueryHelper(sql, ds.getConnection()).getJSON();
			this.json.put("json",result);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "success";
	}
	
	/**
	 * 生成系统推荐产品
	 * @return
	 * @throws SQLException 
	 */
	public  synchronized  String saveSystemProducts(){
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String custId = request.getParameter("custId");
		CallableStatement call = null;
		String message = "";
		try {
			call = ds.getConnection().prepareCall("{call SP_ACRM_F_CI_PRO_RECOMMENT(?,?)}");
			call.setString(1, custId);
			call.registerOutParameter(2, Types.VARCHAR);
			call.execute();
			message = call.getString(2);
			if(message.equals("200")){
				throw new BizException(1, 0, "", "系统推荐失败，请联系管理人员！");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BizException(1, 0, "", "系统推荐失败，请联系管理人员！");
		}finally{
			try {
				call.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new BizException(1, 0, "", "系统推荐失败，请联系管理人员！");
			}
		}
		return "success";
	}
	
	/**
	 * 保存客户经理推荐产品
	 * @return
	 */
	public String saveManagerProducts(){
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String managerPro = "";
		String managerProNews = "";
		String financeId = request.getParameter("financeId");
		String addGrantsStr = request.getParameter("addGrantsStr");
		String delGrantsStr = request.getParameter("delGrantsStr");
		//取出financeId对应的managerPro
		String selectJql = "select f from AcrmFCiPerFinance f where f.financeId = '"+financeId+"'";
		List<AcrmFCiPerFinance> list = service.findByJql(selectJql, null);
		for(AcrmFCiPerFinance f : list){
			managerPro = f.getManagerPro();
		}
		if(managerPro == null){
			managerProNews = addGrantsStr;
		}else{
			String[] managerProStrs = managerPro.split(",");
			HashSet <String> managerSet = new HashSet<String>();
			for(int i=0;i<managerProStrs.length;i++){
				managerSet.add(managerProStrs[i]);
			}
			//删除managerPro中的delGrantsStr
			if(delGrantsStr.length() > 0){
				String[] delGrantsStrs = delGrantsStr.split(",");
				HashSet <String> delSet = new HashSet<String>();
				for(int i=0;i<delGrantsStrs.length;i++){
					if(managerSet.contains(delGrantsStrs[i])){
						delSet.add(delGrantsStrs[i]);
						managerSet.removeAll(delSet);
						delSet.clear();
					}
				}
			}
			//managerPro添加addGrantsStr
			if(addGrantsStr.length() > 0){
				String[] addGrantsStrs = addGrantsStr.split(",");
				for(int i=0;i<addGrantsStrs.length;i++){
					if(!managerSet.contains(addGrantsStrs[i])){
						managerSet.add(addGrantsStrs[i]);
					}
				}
			}
			
			Iterator<String> iterator=managerSet.iterator();
			while(iterator.hasNext()){
				if(managerProNews.equals("")){
					managerProNews = iterator.next();
				}else{
					managerProNews += "," + iterator.next();
				}
			}
		}
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String updateJql = "update AcrmFCiPerFinance f set f.managerPro = '"+managerProNews+"',f.lastUpdateTm = '"+sdf.format(date)+"' where f.financeId = '"+financeId+"'";
		service.batchUpdateByName(updateJql, null);
		
		return "success";
	}
	
	/**
	 * 删除客户理财产品推荐记录
	 */
	public void delete(){
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String financeIds = request.getParameter("financeId");
		String[] financeId = financeIds.split(",");
		for(int i=0;i<financeId.length;i++){
			service.delete(financeId[i]);
		}
	}
}
