package com.yuchengtech.bob.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.yuchengtech.bcrm.customer.service.ClientDepAndLonService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * @author yaoliang
 *
 */
@ParentPackage("json-default")

@Action(value="/dep-and-lon-increase", results={
    @Result(name="success", type="json")
})
public class DepAndLonIncreaseAction extends CommonAction{
//public class DepAndLonIncreaseAction extends BaseAction{

	//@SuppressWarnings("rawtypes")
	//private Map depAndLonIncreaseMap;
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;

	@Autowired
	@Qualifier("dsOracle")	
	private DataSource dsOracle;

	@Override
	@SuppressWarnings("rawtypes")
	public void prepare() {

		
		StringBuffer stringBuffer = new StringBuffer();
		Map paramsMap = super.getJson();
		String checkedOrg="";
		String curr_type_value =(String) paramsMap.get("cur_type");
		String level = (String)paramsMap.get("instn_level");
		String checkedNodes = (String)paramsMap.get("checkedNodes");
		String crm_date = (String)paramsMap.get("crm_dt")+"";
		String depTable=" mdm.acrm_m_ci_lon_dep t ";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		/*判断传入的统计时间,如果统计时间是近3天以前,去查询历史表*/
		GregorianCalendar calendar = new GregorianCalendar();
		
		GregorianCalendar calendarDay = new GregorianCalendar (calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.DATE)-3);

		String dataDay = ""+calendarDay.get(Calendar.YEAR)+"-"+calendarDay.get(Calendar.MONTH)+"-"+calendarDay.get(Calendar.DATE);
		
		try{
			Date dateParam = dateFormat.parse(crm_date);
			Date dateToday = dateFormat.parse(dataDay);
			if(dateParam.before(dateToday)){
				depTable = " mdm.acrm_m_ci_lon_dep_h t ";
			}			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		if(level == null || level.equals("") || level.equals("undefined") || level.equals("null")){
			level = "level_3";
		}
		if(checkedNodes != null && !checkedNodes.equals("") && !checkedNodes.equals("undefined") && !checkedNodes.equals("null"))
		{
			 checkedOrg = buildCheckedNodes(checkedNodes);
//			 checkedOrg = " and acrm_f_sm_sys_units_sta.unitid in ("+(String)paramsMap.get("checkedNodes")+")";
		}
		else{
			level = "level_3";
		}
		
		String caculate_type_value = (String)paramsMap.get("caculate_type");		
		if(curr_type_value ==null || curr_type_value.equals("") || curr_type_value.equals("null")){
			curr_type_value = "01";//默认币种类型是人民币
		}
		if(caculate_type_value == null || caculate_type_value.equals("")|| caculate_type_value.equals("null")){
			caculate_type_value = "01";//默认对比口径是较年初
		}
		if(curr_type_value.equals("02")){//如果是本币查询
			if(caculate_type_value.equals("01"))//如果对比口径是较年初
			{
				stringBuffer.append("select '较年初'  caculate_type_desc,t.crm_dt,t.cust_zzdm,t.cust_id,t.sts,t.scope,t.crm_scope,t.cust_typ,t.cust_typ_desc,t.cust_big_lev,t.cust_small_lev,t.cust_lev, " 
						+ " t.cust_name,t.cust_crm_typ,t3."+level+" instn_level_id,t4.unitname, " 
						+ " sum(t.dep_bal) dep_bal,sum(t.dep_avg) dep_avg,sum(t.dep_qua) dep_qua,sum(t.dep_year) dep_year , sum(t.lon_bal) lon_bal ,sum(t.lon_avg) lon_avg, sum(t.lon_qua) lon_qua, sum(t.lon_year) lon_year, "
						+ " sum(t.dep_cy) dep_cy,sum(t.dep_avg_cy) dep_avg_cy,sum(t.dep_qua_cy) dep_qua_cy,sum(t.dep_year_cy) dep_year_cy,sum(t.dep_year_cy_11) dep_year_11," 
						+ " sum(t.lon_cy) lon_cy,sum(t.lon_avg_cy) lon_avg_cy,sum(t.lon_qua_cy) lon_qua_cy,sum(t.lon_year_cy) lon_qua_cy,sum(t.lon_year_cy_11) lon_year_cy_11 "
				);
				
				stringBuffer.append(" from "+ depTable +" inner join (select acrm_f_sm_sys_units_sta.unitid from fdm.acrm_f_sm_sys_units_sta  "+checkedOrg+" ) t2  on t.instn_no = t2.unitid " 
						+" inner join fdm.acrm_f_sm_sys_units_sta t3 on t3.level_4 = t.instn_no inner join fdm.acrm_f_sm_sys_units_sta t4 on t3."+level+" = t4.unitid  ");

				String clusterStr="";
				String rollStr=""; 
				if(paramsMap.get("cust_base_name")!=null && !(paramsMap.get("cust_base_name")+"").equals("") && !(paramsMap.get("cust_base_name")+"").equals("null")){
					 clusterStr = custCluster(paramsMap.get("cust_base_name")+"","ocrm_f_ci_relate_cust_base","cust_base_id");
				}if(paramsMap.get("roll_name")!=null && !(paramsMap.get("roll_name")+"").equals("") && !(paramsMap.get("roll_name")+"").equals("null")){
					 rollStr = custCluster(paramsMap.get("roll_name")+"","ocrm_f_mm_rcust_list","roll_id");
				}		
				if(clusterStr.equals("null") && rollStr.equals("null")){
					stringBuffer.append(" where  (0>1) ");
				}
				else if(clusterStr.equals("") && rollStr.equals("null")){
					stringBuffer.append(" where (0>1)");
				}
				else if(clusterStr.equals("null") && rollStr.equals("")){
					stringBuffer.append(" where (0>1)");					
				}else {
					stringBuffer.append(clusterStr);
					stringBuffer.append(rollStr);
				}
				
				
				if(paramsMap.get("dep_bal_from")!=null && !(paramsMap.get("dep_bal_from")+"").equals("") && !(paramsMap.get("dep_bal_from")+"").equals("null")){
					stringBuffer.append(" and t.dep_bal >="+paramsMap.get("dep_bal_from")+" ");
				}
				if(paramsMap.get("dep_bal_to")!=null && !(paramsMap.get("dep_bal_to")+"").equals("") && !(paramsMap.get("dep_bal_to")+"").equals("null")){
					stringBuffer.append(" and t.dep_bal <="+paramsMap.get("dep_bal_to")+" ");
				}
				if(paramsMap.get("dep_year_from")!=null && !(paramsMap.get("dep_year_from")+"").equals("") && !(paramsMap.get("dep_year_from")+"").equals("null")){
					stringBuffer.append(" and t.dep_year>="+paramsMap.get("dep_year_from")+" ");
				}
				if(paramsMap.get("dep_year_to")!=null && !(paramsMap.get("dep_year_to")+"").equals("") && !(paramsMap.get("dep_year_to")+"").equals("null")){
					stringBuffer.append(" and t.dep_year<="+paramsMap.get("dep_year_to")+" ");
				}	
				
				if(paramsMap.get("lon_bal_from")!=null && !(paramsMap.get("lon_bal_from")+"").equals("") && !(paramsMap.get("lon_bal_from")+"").equals("null")){
					stringBuffer.append(" and t.lon_bal >="+paramsMap.get("lon_bal_from")+" ");
				}
				if(paramsMap.get("lon_bal_to")!=null && !(paramsMap.get("lon_bal_to")+"").equals("") && !(paramsMap.get("lon_bal_to")+"").equals("null")){
					stringBuffer.append(" and t.lon_bal <="+paramsMap.get("lon_bal_to")+" ");
				}
				if(paramsMap.get("lon_year_from")!=null && !(paramsMap.get("lon_year_from")+"").equals("") && !(paramsMap.get("lon_year_from")+"").equals("null")){
					stringBuffer.append(" and t.lon_year>="+paramsMap.get("lon_year_from")+" ");
				}
				if(paramsMap.get("lon_year_to")!=null && !(paramsMap.get("lon_year_to")+"").equals("") && !(paramsMap.get("lon_year_to")+"").equals("null")){
					stringBuffer.append(" and t.lon_year<="+paramsMap.get("lon_year_to")+" ");
				}				
				if(paramsMap.get("dep_cy")!=null && !(paramsMap.get("dep_cy")+"").equals("") && !(paramsMap.get("dep_cy")+"").equals("null")){
					stringBuffer.append(" and t.dep_cy >="+paramsMap.get("dep_cy")+" ");
				}
				if(paramsMap.get("dep_avg_cy")!=null && !(paramsMap.get("dep_avg_cy")+"").equals("") && !(paramsMap.get("dep_avg_cy")+"").equals("null")){
					stringBuffer.append(" and t.dep_avg_cy >="+paramsMap.get("dep_avg_cy")+" ");
				}
				if(paramsMap.get("dep_qua_cy")!=null && !(paramsMap.get("dep_qua_cy")+"").equals("") && !(paramsMap.get("dep_qua_cy")+"").equals("null")){
					stringBuffer.append(" and t.dep_qua_cy >="+paramsMap.get("dep_qua_cy")+" ");
				}
				if(paramsMap.get("dep_year_cy")!=null && !(paramsMap.get("dep_year_cy")+"").equals("") && !(paramsMap.get("dep_year_cy")+"").equals("null")){
					stringBuffer.append(" and t.dep_year_cy>="+paramsMap.get("dep_year_cy")+" ");
				}		
				
				if(paramsMap.get("dep_year_cy_11")!=null && !(paramsMap.get("dep_year_cy_11")+"").equals("") && !(paramsMap.get("dep_year_cy_11")+"").equals("null")){
					stringBuffer.append(" and t.dep_year_cy_11>="+paramsMap.get("dep_year_cy_11")+" ");
				}
				
				if(paramsMap.get("lon_cy")!=null && !(paramsMap.get("lon_cy")+"").equals("") && !(paramsMap.get("lon_cy")+"").equals("null")){
					stringBuffer.append(" and t.lon_cy >="+paramsMap.get("lon_cy")+" ");
				}
				if(paramsMap.get("lon_avg_cy")!=null && !(paramsMap.get("lon_avg_cy")+"").equals("") && !(paramsMap.get("lon_avg_cy")+"").equals("null")){
					stringBuffer.append(" and t.lon_avg_cy >="+paramsMap.get("lon_avg_cy")+" ");
				}
				if(paramsMap.get("lon_qua_cy")!=null && !(paramsMap.get("lon_qua_cy")+"").equals("") && !(paramsMap.get("lon_qua_cy")+"").equals("null")){
					stringBuffer.append(" and t.lon_qua_cy >="+paramsMap.get("lon_qua_cy")+" ");
				}
				if(paramsMap.get("lon_year_cy")!=null && !(paramsMap.get("lon_year_cy")+"").equals("") && !(paramsMap.get("lon_year_cy")+"").equals("null")){
					stringBuffer.append(" and t.lon_year_cy>="+paramsMap.get("lon_year_cy")+" ");
				}				
				if(paramsMap.get("lon_year_cy_11")!=null && !(paramsMap.get("lon_year_cy_11")+"").equals("") && (paramsMap.get("lon_year_cy_11")+"").equals("null")){
					stringBuffer.append(" and t.lon_year_cy_11>="+paramsMap.get("lon_year_cy_11")+" ");
				}				
				
				
			}
			else if(caculate_type_value.equals("02"))//如果对比口径是较季初
			{
				stringBuffer.append("select '较季初'  caculate_type_desc,t.crm_dt,t.cust_zzdm,t.cust_id,t.sts,t.scope,t.crm_scope,t.cust_typ,t.cust_typ_desc,t.cust_big_lev,t.cust_small_lev,t.cust_lev, " 
						+ " t.cust_name,t.cust_crm_typ,t3."+level+" instn_level_id,t4.unitname, " 
						+ " sum (t.dep_bal) dep_bal,sum(t.dep_avg) dep_avg,sum(t.dep_qua) dep_qua,sum(t.dep_year) dep_year," 
						+ " sum (t.lon_bal) lon_bal,sum(t.lon_avg) lon_avg,sum(t.lon_qua) lon_qua,sum(t.lon_year) lon_year, "
						+ " sum (t.dep_cq)  dep_cy, sum(t.dep_avg_cq)  dep_avg_cy,sum(t.dep_qua_cq) dep_qua_cy ,sum(t.dep_year_cq) dep_year_cy, sum(t.dep_year_cq_11) dep_year_cy_11," 
						+ " sum (t.lon_cq)  lon_cy, sum(t.lon_avg_cq) lon_avg_cy,sum(t.lon_qua_cq) lon_qua_cy,sum(t.lon_year_cq) lon_year_cy,sum(t.lon_year_cq_11) lon_year_cy_11 "
				);
				
//				stringBuffer.append(" from "+ depTable +"  left join fdm.acrm_f_sm_sys_units units on t.instn_no = units.system_unitid where (1=1) ");
				stringBuffer.append(" from "+ depTable +" inner join (select acrm_f_sm_sys_units_sta.unitid  from fdm.acrm_f_sm_sys_units_sta "+checkedOrg+") t2  on t.instn_no = t2.unitid " 
						+" inner join fdm.acrm_f_sm_sys_units_sta t3 on t3.level_4 = t.instn_no inner join fdm.acrm_f_sm_sys_units_sta t4 on t3."+level+" = t4.unitid  ");

				String clusterStr="";
				String rollStr=""; 
				if(paramsMap.get("cust_base_name")!=null && !(paramsMap.get("cust_base_name")+"").equals("") && !(paramsMap.get("cust_base_name")+"").equals("null")){
					 clusterStr = custCluster(paramsMap.get("cust_base_name")+"","ocrm_f_ci_relate_cust_base","cust_base_id");
				}if(paramsMap.get("roll_name")!=null && !(paramsMap.get("roll_name")+"").equals("") && !(paramsMap.get("roll_name")+"").equals("null")){
					 rollStr = custCluster(paramsMap.get("roll_name")+"","ocrm_f_mm_rcust_list","roll_id");
				}
				if(clusterStr.equals("null") && rollStr.equals("null")){
					stringBuffer.append(" where  (0>1) ");
				}
				else if(clusterStr.equals("") && rollStr.equals("null")){
					stringBuffer.append(" where (0>1)");
				}
				else if(clusterStr.equals("null") && rollStr.equals("")){
					stringBuffer.append(" where (0>1)");					
				}else {
					stringBuffer.append(clusterStr);
					stringBuffer.append(rollStr);
				}
				
				if(paramsMap.get("dep_bal_from")!=null && !(paramsMap.get("dep_bal_from")+"").equals("") && !(paramsMap.get("dep_bal_from")+"").equals("null")){
					stringBuffer.append(" and t.dep_bal >="+paramsMap.get("dep_bal_from")+" ");
				}
				if(paramsMap.get("dep_bal_to")!=null && !(paramsMap.get("dep_bal_to")+"").equals("") && !(paramsMap.get("dep_bal_to")+"").equals("null")){
					stringBuffer.append(" and t.dep_bal <="+paramsMap.get("dep_bal_to")+" ");
				}
				if(paramsMap.get("dep_year_from")!=null && !(paramsMap.get("dep_year_from")+"").equals("") && !(paramsMap.get("dep_year_from")+"").equals("null")){
					stringBuffer.append(" and t.dep_year>="+paramsMap.get("dep_year_from")+" ");
				}
				if(paramsMap.get("dep_year_to")!=null && !(paramsMap.get("dep_year_to")+"").equals("") && !(paramsMap.get("dep_year_to")+"").equals("null")){
					stringBuffer.append(" and t.dep_year<="+paramsMap.get("dep_year_to")+" ");
				}	
				
				if(paramsMap.get("lon_bal_from")!=null && !(paramsMap.get("lon_bal_from")+"").equals("") && !(paramsMap.get("lon_bal_from")+"").equals("null")){
					stringBuffer.append(" and t.lon_bal >="+paramsMap.get("lon_bal_from")+" ");
				}
				if(paramsMap.get("lon_bal_to")!=null && !(paramsMap.get("lon_bal_to")+"").equals("") && !(paramsMap.get("lon_bal_to")+"").equals("null")){
					stringBuffer.append(" and t.lon_bal <="+paramsMap.get("lon_bal_to")+" ");
				}
				if(paramsMap.get("lon_year_from")!=null && !(paramsMap.get("lon_year_from")+"").equals("") && !(paramsMap.get("lon_year_from")+"").equals("null")){
					stringBuffer.append(" and t.lon_year>="+paramsMap.get("lon_year_from")+" ");
				}
				if(paramsMap.get("lon_year_to")!=null && !(paramsMap.get("lon_year_to")+"").equals("") && !(paramsMap.get("lon_year_to")+"").equals("null")){
					stringBuffer.append(" and t.lon_year<="+paramsMap.get("lon_year_to")+" ");
				}
				
				if(paramsMap.get("dep_cy")!=null && !(paramsMap.get("dep_cy")+"").equals("") && !(paramsMap.get("dep_cy")+"").equals("null")){
					stringBuffer.append(" and t.dep_cq >="+paramsMap.get("dep_cy")+" ");
				}
				if(paramsMap.get("dep_avg_cy")!=null && !(paramsMap.get("dep_avg_cy")+"").equals("") && !(paramsMap.get("dep_avg_cy")+"").equals("null")){
					stringBuffer.append(" and t.dep_avg_cq >="+paramsMap.get("dep_avg_cy")+" ");
				}
				if(paramsMap.get("dep_qua_cy")!=null && !(paramsMap.get("dep_qua_cy")+"").equals("") && !(paramsMap.get("dep_qua_cy")+"").equals("null")){
					stringBuffer.append(" and t.dep_qua_cq >="+paramsMap.get("dep_qua_cy")+" ");
				}
				if(paramsMap.get("dep_year_cy")!=null && !(paramsMap.get("dep_year_cy")+"").equals("") && !(paramsMap.get("dep_year_cy")+"").equals("null")){
					stringBuffer.append(" and t.dep_year_cq>="+paramsMap.get("dep_year_cy")+" ");
				}		
				
				if(paramsMap.get("dep_year_cy_11")!=null && !(paramsMap.get("dep_year_cy_11")+"").equals("") && !(paramsMap.get("dep_year_cy_11")+"").equals("null")){
					stringBuffer.append(" and t.dep_year_cq_11>="+paramsMap.get("dep_year_cy_11")+" ");
				}
				
				if(paramsMap.get("lon_cy")!=null && !(paramsMap.get("lon_cy")+"").equals("") && !(paramsMap.get("lon_cy")+"").equals("null")){
					stringBuffer.append(" and t.lon_cq >="+paramsMap.get("lon_cy")+" ");
				}
				if(paramsMap.get("lon_avg_cy")!=null && !(paramsMap.get("lon_avg_cy")+"").equals("") && !(paramsMap.get("lon_avg_cy")+"").equals("null")){
					stringBuffer.append(" and t.lon_avg_cq >="+paramsMap.get("lon_avg_cy")+" ");
				}
				if(paramsMap.get("lon_qua_cy")!=null && !(paramsMap.get("lon_qua_cy")+"").equals("") && !(paramsMap.get("lon_qua_cy")+"").equals("null")){
					stringBuffer.append(" and t.lon_qua_cq >="+paramsMap.get("lon_qua_cy")+" ");
				}
				if(paramsMap.get("lon_year_cy")!=null && !(paramsMap.get("lon_year_cy")+"").equals("") && !(paramsMap.get("lon_year_cy")+"").equals("null")){
					stringBuffer.append(" and t.lon_year_cq>="+paramsMap.get("lon_year_cy")+" ");
				}				
				if(paramsMap.get("lon_year_cy_11")!=null && !(paramsMap.get("lon_year_cy_11")+"").equals("") && !(paramsMap.get("lon_year_cy_11")+"").equals("null")){
					stringBuffer.append(" and t.lon_year_cq_11>="+paramsMap.get("lon_year_cy_11")+" ");
				}				
				
				
				
			}
			else if(caculate_type_value.equals("03"))//如果对比口径是较月初
			{
				stringBuffer.append("select  '较月初'  caculate_type_desc,t.crm_dt,t.cust_zzdm,t.cust_id,t.sts,t.scope,t.crm_scope,t.cust_typ,t.cust_typ_desc,t.cust_big_lev,t.cust_small_lev,t.cust_lev, " 
						+ " t.cust_name,t.cust_crm_typ,t3."+level+"  instn_level_id,t4.unitname, " 
						+ " sum(t.dep_bal) dep_bal,sum(t.dep_avg) dep_avg,sum(t.dep_qua) dep_qua,sum(t.dep_year) dep_year," 
						+ " sum(t.lon_bal) lon_bal,sum(t.lon_avg) lon_avg,sum(t.lon_qua) lon_qua,sum(t.lon_year) lon_year, "
						+ " sum(t.dep_cm)  dep_cy,sum(t.dep_avg_cm)  dep_avg_cy,sum(t.dep_qua_cm) dep_qua_cy ,sum(t.dep_year_cm) dep_year_cy,sum(t.dep_year_cm_11) dep_year_cy_11, " 
						+ " sum(t.lon_cm)  lon_cy,sum(t.lon_avg_cm)  lon_avg_cy,sum(t.lon_qua_cm) lon_qua_cy,sum(t.lon_year_cm) lon_year_cy,sum(t.lon_year_cm_11) lon_year_cy_11 "
				);	

				stringBuffer.append(" from "+ depTable +" inner join (select acrm_f_sm_sys_units_sta.unitid  from fdm.acrm_f_sm_sys_units_sta "+checkedOrg+") t2  on t.instn_no = t2.unitid " 
						+" inner join fdm.acrm_f_sm_sys_units_sta t3 on t3.level_4 = t.instn_no inner join fdm.acrm_f_sm_sys_units_sta t4 on t3."+level+" = t4.unitid  ");

				String clusterStr="";
				String rollStr=""; 
				if(paramsMap.get("cust_base_name")!=null && !(paramsMap.get("cust_base_name")+"").equals("") && !(paramsMap.get("cust_base_name")+"").equals("null")){
					 clusterStr = custCluster(paramsMap.get("cust_base_name")+"","ocrm_f_ci_relate_cust_base","cust_base_id");
				}if(paramsMap.get("roll_name")!=null && !(paramsMap.get("roll_name")+"").equals("") && !(paramsMap.get("roll_name")+"").equals("null")){
					 rollStr = custCluster(paramsMap.get("roll_name")+"","ocrm_f_mm_rcust_list","roll_id");
				}					
				if(clusterStr.equals("null") && rollStr.equals("null")){
					stringBuffer.append(" where  (0>1) ");
				}
				else if(clusterStr.equals("") && rollStr.equals("null")){
					stringBuffer.append(" where (0>1)");
				}
				else if(clusterStr.equals("null") && rollStr.equals("")){
					stringBuffer.append(" where (0>1)");					
				}else {
					stringBuffer.append(clusterStr);
					stringBuffer.append(rollStr);
				}
				
				
				if(paramsMap.get("dep_bal_from")!=null && !(paramsMap.get("dep_bal_from")+"").equals("") && !(paramsMap.get("dep_bal_from")+"").equals("null")){
					stringBuffer.append(" and t.dep_bal >="+paramsMap.get("dep_bal_from")+" ");
				}
				if(paramsMap.get("dep_bal_to")!=null && !(paramsMap.get("dep_bal_to")+"").equals("") && !(paramsMap.get("dep_bal_to")+"").equals("null")){
					stringBuffer.append(" and t.dep_bal <="+paramsMap.get("dep_bal_to")+" ");
				}
				if(paramsMap.get("dep_year_from")!=null && !(paramsMap.get("dep_year_from")+"").equals("") && !(paramsMap.get("dep_year_from")+"").equals("null")){
					stringBuffer.append(" and t.dep_year>="+paramsMap.get("dep_year_from")+" ");
				}
				if(paramsMap.get("dep_year_to")!=null && !(paramsMap.get("dep_year_to")+"").equals("") && !(paramsMap.get("dep_year_to")+"").equals("null")){
					stringBuffer.append(" and t.dep_year<="+paramsMap.get("dep_year_to")+" ");
				}	
				
				if(paramsMap.get("lon_bal_from")!=null && !(paramsMap.get("lon_bal_from")+"").equals("") && !(paramsMap.get("lon_bal_from")+"").equals("null")){
					stringBuffer.append(" and t.lon_bal >="+paramsMap.get("lon_bal_from")+" ");
				}
				if(paramsMap.get("lon_bal_to")!=null && !(paramsMap.get("lon_bal_to")+"").equals("") && !(paramsMap.get("lon_bal_to")+"").equals("null")){
					stringBuffer.append(" and t.lon_bal <="+paramsMap.get("lon_bal_to")+" ");
				}
				if(paramsMap.get("lon_year_from")!=null && !(paramsMap.get("lon_year_from")+"").equals("") && !(paramsMap.get("lon_year_from")+"").equals("null")){
					stringBuffer.append(" and t.lon_year>="+paramsMap.get("lon_year_from")+" ");
				}
				if(paramsMap.get("lon_year_to")!=null && !(paramsMap.get("lon_year_to")+"").equals("") && !(paramsMap.get("lon_year_to")+"").equals("null")){
					stringBuffer.append(" and t.lon_year<="+paramsMap.get("lon_year_to")+" ");
				}
				
				if(paramsMap.get("dep_cy")!=null && !(paramsMap.get("dep_cy")+"").equals("") && !(paramsMap.get("dep_cy")+"").equals("null")){
					stringBuffer.append(" and t.dep_cm >="+paramsMap.get("dep_cy")+" ");
				}
				if(paramsMap.get("dep_avg_cy")!=null && !(paramsMap.get("dep_avg_cy")+"").equals("") && !(paramsMap.get("dep_avg_cy")+"").equals("null")){
					stringBuffer.append(" and t.dep_avg_cm >="+paramsMap.get("dep_avg_cy")+" ");
				}
				if(paramsMap.get("dep_qua_cy")!=null && !(paramsMap.get("dep_qua_cy")+"").equals("") && !(paramsMap.get("dep_qua_cy")+"").equals("null")){
					stringBuffer.append(" and t.dep_qua_cm >="+paramsMap.get("dep_qua_cy")+" ");
				}
				if(paramsMap.get("dep_year_cy")!=null && !(paramsMap.get("dep_year_cy")+"").equals("") && !(paramsMap.get("dep_year_cy")+"").equals("null")){
					stringBuffer.append(" and t.dep_year_cm>="+paramsMap.get("dep_year_cy")+" ");
				}		
				
				if(paramsMap.get("dep_year_cy_11")!=null && !(paramsMap.get("dep_year_cy_11")+"").equals("") && !(paramsMap.get("dep_year_cy_11")+"").equals("null")){
					stringBuffer.append(" and t.dep_year_cm_11>="+paramsMap.get("dep_year_cy_11")+" ");
				}
				
				if(paramsMap.get("lon_cy")!=null && !(paramsMap.get("lon_cy")+"").equals("") && !(paramsMap.get("lon_cy")+"").equals("null")){
					stringBuffer.append(" and t.lon_cm >="+paramsMap.get("lon_cy")+" ");
				}
				if(paramsMap.get("lon_avg_cy")!=null && !(paramsMap.get("lon_avg_cy")+"").equals("") && !(paramsMap.get("lon_avg_cy")+"").equals("null")){
					stringBuffer.append(" and t.lon_avg_cm >="+paramsMap.get("lon_avg_cy")+" ");
				}
				if(paramsMap.get("lon_qua_cy")!=null && !(paramsMap.get("lon_qua_cy")+"").equals("") && !(paramsMap.get("lon_qua_cy")+"").equals("null")){
					stringBuffer.append(" and t.lon_qua_cm >="+paramsMap.get("lon_qua_cy")+" ");
				}
				if(paramsMap.get("lon_year_cy")!=null && !(paramsMap.get("lon_year_cy")+"").equals("") && !(paramsMap.get("lon_year_cy")+"").equals("null")){
					stringBuffer.append(" and t.lon_year_cm>="+paramsMap.get("lon_year_cy")+" ");
				}				
				if(paramsMap.get("lon_year_cy_11")!=null && !(paramsMap.get("lon_year_cy_11")+"").equals("") && !(paramsMap.get("lon_year_cy_11")+"").equals("null")){
					stringBuffer.append(" and t.lon_year_cm_11>="+paramsMap.get("lon_year_cy_11")+" ");
				}
				
			}
			else if(caculate_type_value.equals("04"))//如果对比口径是较昨日
			{
				stringBuffer.append("select  '较昨日'  caculate_type_desc,t.crm_dt,t.cust_zzdm,t.cust_id,t.sts,t.scope,t.crm_scope,t.cust_typ,t.cust_typ_desc,t.cust_big_lev,t.cust_small_lev,t.cust_lev, " 
						+ " t.cust_name,t.cust_crm_typ,t3."+level+"  instn_level_id,t4.unitname, " 
						+ " sum(t.dep_bal) dep_bal,sum(t.dep_avg) dep_avg,sum(t.dep_qua) dep_qua, sum(t.dep_year) dep_year," 
						+ " sum(t.lon_bal) lon_bal,sum(t.lon_avg) lon_avg,sum(t.lon_qua) lon_qua,sum(t.lon_year) lon_year, "
						+ " sum(t.dep_cd)  dep_cy,sum(t.dep_avg_cd) dep_avg_cy,sum(t.dep_qua_cd) dep_qua_cy ,sum(t.dep_year_cd) dep_year_cy,sum(t.dep_year_cd_11) dep_year_cy_11," 
						+ " sum(t.lon_cd)  lon_cy,sum(t.lon_avg_cd) lon_avg_cy,sum(t.lon_qua_cd) lon_qua_cy,sum(t.lon_year_cd) lon_year_cy, sum(t.lon_year_cd_11) lon_year_cy_11 "
				);
				
//				stringBuffer.append(" from "+ depTable +" left join fdm.acrm_f_sm_sys_units units on t.instn_no = units.system_unitid where (1=1) ");
				stringBuffer.append(" from "+ depTable +" inner join (select acrm_f_sm_sys_units_sta.unitid  from fdm.acrm_f_sm_sys_units_sta "+checkedOrg+") t2  on t.instn_no = t2.unitid " 
						+" inner join fdm.acrm_f_sm_sys_units_sta t3 on t3.level_4 = t.instn_no inner join fdm.acrm_f_sm_sys_units_sta t4 on t3."+level+" = t4.unitid  ");

				String clusterStr="";
				String rollStr=""; 
				if(paramsMap.get("cust_base_name")!=null && !(paramsMap.get("cust_base_name")+"").equals("") && !(paramsMap.get("cust_base_name")+"").equals("null")){
					 clusterStr = custCluster(paramsMap.get("cust_base_name")+"","ocrm_f_ci_relate_cust_base","cust_base_id");
				}if(paramsMap.get("roll_name")!=null && !(paramsMap.get("roll_name")+"").equals("") && !(paramsMap.get("roll_name")+"").equals("null")){
					 rollStr = custCluster(paramsMap.get("roll_name")+"","ocrm_f_mm_rcust_list","roll_id");
				}					
				if(clusterStr.equals("null") && rollStr.equals("null")){
					stringBuffer.append(" where  (0>1) ");
				}
				else if(clusterStr.equals("") && rollStr.equals("null")){
					stringBuffer.append(" where (0>1)");
				}
				else if(clusterStr.equals("null") && rollStr.equals("")){
					stringBuffer.append(" where (0>1)");					
				}else {
					stringBuffer.append(clusterStr);
					stringBuffer.append(rollStr);
				}
			
				
				if(paramsMap.get("dep_bal_from")!=null && !(paramsMap.get("dep_bal_from")+"").equals("") && !(paramsMap.get("dep_bal_from")+"").equals("null")){
					stringBuffer.append(" and t.dep_bal >="+paramsMap.get("dep_bal_from")+" ");
				}
				if(paramsMap.get("dep_bal_to")!=null && !(paramsMap.get("dep_bal_to")+"").equals("") && !(paramsMap.get("dep_bal_to")+"").equals("null")){
					stringBuffer.append(" and t.dep_bal <="+paramsMap.get("dep_bal_to")+" ");
				}
				if(paramsMap.get("dep_year_from")!=null && !(paramsMap.get("dep_year_from")+"").equals("") && !(paramsMap.get("dep_year_from")+"").equals("null")){
					stringBuffer.append(" and t.dep_year>="+paramsMap.get("dep_year_from")+" ");
				}
				if(paramsMap.get("dep_year_to")!=null && !(paramsMap.get("dep_year_to")+"").equals("") && !(paramsMap.get("dep_year_to")+"").equals("null")){
					stringBuffer.append(" and t.dep_year<="+paramsMap.get("dep_year_to")+" ");
				}	
				
				if(paramsMap.get("lon_bal_from")!=null && !(paramsMap.get("lon_bal_from")+"").equals("") && !(paramsMap.get("lon_bal_from")+"").equals("null")){
					stringBuffer.append(" and t.lon_bal >="+paramsMap.get("lon_bal_from")+" ");
				}
				if(paramsMap.get("lon_bal_to")!=null && !(paramsMap.get("lon_bal_to")+"").equals("") && !(paramsMap.get("lon_bal_to")+"").equals("null")){
					stringBuffer.append(" and t.lon_bal <="+paramsMap.get("lon_bal_to")+" ");
				}
				if(paramsMap.get("lon_year_from")!=null && !(paramsMap.get("lon_year_from")+"").equals("") && !(paramsMap.get("lon_year_from")+"").equals("null")){
					stringBuffer.append(" and t.lon_year>="+paramsMap.get("lon_year_from")+" ");
				}
				if(paramsMap.get("lon_year_to")!=null && !(paramsMap.get("lon_year_to")+"").equals("") && !(paramsMap.get("lon_year_to")+"").equals("null")){
					stringBuffer.append(" and t.lon_year<="+paramsMap.get("lon_year_to")+" ");
				}
				
				if(paramsMap.get("dep_cy")!=null && !(paramsMap.get("dep_cy")+"").equals("") && !(paramsMap.get("dep_cy")+"").equals("null")){
					stringBuffer.append(" and t.dep_cd >="+paramsMap.get("dep_cy")+" ");
				}
				if(paramsMap.get("dep_avg_cy")!=null && !(paramsMap.get("dep_avg_cy")+"").equals("") && !(paramsMap.get("dep_avg_cy")+"").equals("null")){
					stringBuffer.append(" and t.dep_avg_cd >="+paramsMap.get("dep_avg_cy")+" ");
				}
				if(paramsMap.get("dep_qua_cy")!=null && !(paramsMap.get("dep_qua_cy")+"").equals("") && !(paramsMap.get("dep_qua_cy")+"").equals("null")){
					stringBuffer.append(" and t.dep_qua_cd >="+paramsMap.get("dep_qua_cy")+" ");
				}
				if(paramsMap.get("dep_year_cy")!=null && !(paramsMap.get("dep_year_cy")+"").equals("") && !(paramsMap.get("dep_year_cy")+"").equals("null")){
					stringBuffer.append(" and t.dep_year_cd>="+paramsMap.get("dep_year_cy")+" ");
				}		
				
				if(paramsMap.get("dep_year_cy_11")!=null && !(paramsMap.get("dep_year_cy_11")+"").equals("") && !(paramsMap.get("dep_year_cy_11")+"").equals("null")){
					stringBuffer.append(" and t.dep_year_cd_11>="+paramsMap.get("dep_year_cy_11")+" ");
				}
				
				if(paramsMap.get("lon_cy")!=null && !(paramsMap.get("lon_cy")+"").equals("") && !(paramsMap.get("lon_cy")+"").equals("null")){
					stringBuffer.append(" and t.lon_cd >="+paramsMap.get("lon_cy")+" ");
				}
				if(paramsMap.get("lon_avg_cy")!=null && !(paramsMap.get("lon_avg_cy")+"").equals("") && !(paramsMap.get("lon_avg_cy")+"").equals("null")){
					stringBuffer.append(" and t.lon_avg_cd >="+paramsMap.get("lon_avg_cy")+" ");
				}
				if(paramsMap.get("lon_qua_cy")!=null && !(paramsMap.get("lon_qua_cy")+"").equals("") && !(paramsMap.get("lon_qua_cy")+"").equals("null")){
					stringBuffer.append(" and t.lon_qua_cd >="+paramsMap.get("lon_qua_cy")+" ");
				}
				if(paramsMap.get("lon_year_cy")!=null && !(paramsMap.get("lon_year_cy")+"").equals("") && !(paramsMap.get("lon_year_cy")+"").equals("null")){
					stringBuffer.append(" and t.lon_year_cd>="+paramsMap.get("lon_year_cy")+" ");
				}				
				if(paramsMap.get("lon_year_cy_11")!=null && !(paramsMap.get("lon_year_cy_11")+"").equals("") && !(paramsMap.get("lon_year_cy_11")+"").equals("null")){
					stringBuffer.append(" and t.lon_year_cd_11>="+paramsMap.get("lon_year_cy_11")+" ");
				}

			}			
		}		
		
		else if(curr_type_value.equals("01"))//如果币种是人民币
		{
			if(caculate_type_value.equals("01"))//如果对比口径是较年初
			{
				stringBuffer.append("select  '较年初'  caculate_type_desc,t.crm_dt,t.cust_zzdm,t.cust_id,t.sts,t.scope,t.crm_scope,t.cust_typ,t.cust_typ_desc,t.cust_big_lev,t.cust_small_lev,t.cust_lev, " 
						+ " t.cust_name,t.cust_crm_typ,t3."+level+"  instn_level_id,t4.unitname, " 
						+ " sum(t.dep_bal_cny) dep_bal ,sum(t.dep_avg_cny) dep_avg,sum(t.dep_qua_cny) dep_qua,sum(t.dep_year_cny) dep_year, " 
						+ " sum(t.lon_bal_cny) lon_bal ,sum(t.lon_avg_cny) lon_avg,sum(t.lon_qua_cny) lon_qua ,sum(t.lon_year_cny) lon_year, "
						+ " sum(t.dep_cy_cny)  dep_cy,sum(t.dep_avg_cy_cny)  dep_avg_cy,sum(t.dep_qua_cy_cny) dep_qua_cy ,sum(t.dep_year_cy_cny) dep_year_cy,sum(t.dep_year_cy_cny_11) dep_year_cy_11," 
						+ " sum(t.lon_cy_cny)  lon_cy,sum(t.lon_avg_cy_cny)  lon_avg_cy,sum(t.lon_qua_cy_cny) lon_qua_cy,sum(t.lon_year_cy_cny) lon_year_cy,sum(t.lon_year_cy_cny_11) lon_year_cy_11"
				);	
				
//				stringBuffer.append(" from "+ depTable +" left join fdm.acrm_f_sm_sys_units units on t.instn_no = units.system_unitid where (1=1) ");

				stringBuffer.append(" from "+ depTable +" inner join (select acrm_f_sm_sys_units_sta.unitid  from fdm.acrm_f_sm_sys_units_sta "+checkedOrg+") t2  on t.instn_no = t2.unitid " 
						+" inner join fdm.acrm_f_sm_sys_units_sta t3 on t3.level_4 = t.instn_no inner join fdm.acrm_f_sm_sys_units_sta t4 on t3."+level+" = t4.unitid  ");

				String clusterStr="";
				String rollStr=""; 
				if(paramsMap.get("cust_base_name")!=null && !(paramsMap.get("cust_base_name")+"").equals("") && !(paramsMap.get("cust_base_name")+"").equals("null")){
					 clusterStr = custCluster(paramsMap.get("cust_base_name")+"","ocrm_f_ci_relate_cust_base","cust_base_id");
				}if(paramsMap.get("roll_name")!=null && !(paramsMap.get("roll_name")+"").equals("") && !(paramsMap.get("roll_name")+"").equals("null")){
					 rollStr = custCluster(paramsMap.get("roll_name")+"","ocrm_f_mm_rcust_list","roll_id");
				}					
				if(clusterStr.equals("null") && rollStr.equals("null")){
					stringBuffer.append(" where  (0>1) ");
				}
				else if(clusterStr.equals("") && rollStr.equals("null")){
					stringBuffer.append(" where (0>1)");
				}
				else if(clusterStr.equals("null") && rollStr.equals("")){
					stringBuffer.append(" where (0>1)");					
				}else {
					stringBuffer.append(clusterStr);
					stringBuffer.append(rollStr);
				}
				
				
				
				if(paramsMap.get("dep_bal_from")!=null && !(paramsMap.get("dep_bal_from")+"").equals("") && !(paramsMap.get("dep_bal_from")+"").equals("null")){
					stringBuffer.append(" and t.dep_bal_cny >="+paramsMap.get("dep_bal_from")+" ");
				}
				if(paramsMap.get("dep_bal_to")!=null && !(paramsMap.get("dep_bal_to")+"").equals("") && !(paramsMap.get("dep_bal_to")+"").equals("null")){
					stringBuffer.append(" and t.dep_bal_cny <="+paramsMap.get("dep_bal_to")+" ");
				}
				if(paramsMap.get("dep_year_from")!=null && !(paramsMap.get("dep_year_from")+"").equals("") && !(paramsMap.get("dep_year_from")+"").equals("null")){
					stringBuffer.append(" and t.dep_year_cny>="+paramsMap.get("dep_year_from")+" ");
				}
				if(paramsMap.get("dep_year_to")!=null && !(paramsMap.get("dep_year_to")+"").equals("") && !(paramsMap.get("dep_year_to")+"").equals("null")){
					stringBuffer.append(" and t.dep_year_cny<="+paramsMap.get("dep_year_to")+" ");
				}	
				
				if(paramsMap.get("lon_bal_from")!=null && !(paramsMap.get("lon_bal_from")+"").equals("") && !(paramsMap.get("lon_bal_from")+"").equals("null")){
					stringBuffer.append(" and t.lon_bal_cny >="+paramsMap.get("lon_bal_from")+" ");
				}
				if(paramsMap.get("lon_bal_to")!=null && !(paramsMap.get("lon_bal_to")+"").equals("") && !(paramsMap.get("lon_bal_to")+"").equals("null")){
					stringBuffer.append(" and t.lon_bal_cny <="+paramsMap.get("lon_bal_to")+" ");
				}
				if(paramsMap.get("lon_year_from")!=null && !(paramsMap.get("lon_year_from")+"").equals("") && !(paramsMap.get("lon_year_from")+"").equals("null")){
					stringBuffer.append(" and t.lon_year_cny >="+paramsMap.get("lon_year_from")+" ");
				}
				if(paramsMap.get("lon_year_to")!=null && !(paramsMap.get("lon_year_to")+"").equals("") && !(paramsMap.get("lon_year_to")+"").equals("null")){
					stringBuffer.append(" and t.lon_year_cny <="+paramsMap.get("lon_year_to")+" ");
				}
				
				if(paramsMap.get("dep_cy")!=null && !(paramsMap.get("dep_cy")+"").equals("") && !(paramsMap.get("dep_cy")+"").equals("null")){
					stringBuffer.append(" and t.dep_cy_cny >="+paramsMap.get("dep_cy")+" ");
				}
				if(paramsMap.get("dep_avg_cy")!=null && !(paramsMap.get("dep_avg_cy")+"").equals("") && !(paramsMap.get("dep_avg_cy")+"").equals("null")){
					stringBuffer.append(" and t.dep_avg_cy_cny >="+paramsMap.get("dep_avg_cy")+" ");
				}
				if(paramsMap.get("dep_qua_cy")!=null && !(paramsMap.get("dep_qua_cy")+"").equals("") && !(paramsMap.get("dep_qua_cy")+"").equals("null")){
					stringBuffer.append(" and t.dep_qua_cy_cny >="+paramsMap.get("dep_qua_cy")+" ");
				}
				if(paramsMap.get("dep_year_cy")!=null && !(paramsMap.get("dep_year_cy")+"").equals("") && !(paramsMap.get("dep_year_cy")+"").equals("null")){
					stringBuffer.append(" and t.dep_year_cy_cny>="+paramsMap.get("dep_year_cy")+" ");
				}		
				
				if(paramsMap.get("dep_year_cy_11")!=null && !(paramsMap.get("dep_year_cy_11")+"").equals("") && !(paramsMap.get("dep_year_cy_11")+"").equals("null")){
					stringBuffer.append(" and t.dep_year_cy_cny_11>="+paramsMap.get("dep_year_cy_11")+" ");
				}							
				
				if(paramsMap.get("lon_cy")!=null && !(paramsMap.get("lon_cy")+"").equals("") && !(paramsMap.get("lon_cy")+"").equals("null")){
					stringBuffer.append(" and t.lon_cy_cny >="+paramsMap.get("lon_cy")+" ");
				}
				if(paramsMap.get("lon_avg_cy")!=null && !(paramsMap.get("lon_avg_cy")+"").equals("") && !(paramsMap.get("lon_avg_cy")+"").equals("null")){
					stringBuffer.append(" and t.lon_avg_cy_cny >="+paramsMap.get("lon_avg_cy")+" ");
				}
				if(paramsMap.get("lon_qua_cy")!=null && !(paramsMap.get("lon_qua_cy")+"").equals("") && !(paramsMap.get("lon_qua_cy")+"").equals("null")){
					stringBuffer.append(" and t.lon_qua_cy_cny >="+paramsMap.get("lon_qua_cy")+" ");
				}
				if(paramsMap.get("lon_year_cy")!=null && !(paramsMap.get("lon_year_cy")+"").equals("") && !(paramsMap.get("lon_year_cy")+"").equals("null")){
					stringBuffer.append(" and t.lon_year_cy_cny>="+paramsMap.get("lon_year_cy")+" ");
				}				
				if(paramsMap.get("lon_year_cy_11")!=null && !(paramsMap.get("lon_year_cy_11")+"").equals("") && !(paramsMap.get("lon_year_cy_11")+"").equals("null")){
					stringBuffer.append(" and t.lon_year_cy_cny_11>="+paramsMap.get("lon_year_cy_11")+" ");
				}				
				
				
			}
			if(caculate_type_value.equals("02"))//如果对比口径是较季初
			{
				stringBuffer.append("select  '较季初'  caculate_type_desc,t.crm_dt,t.cust_zzdm,t.cust_id,t.sts,t.scope,t.crm_scope,t.cust_typ,t.cust_typ_desc,t.cust_big_lev,t.cust_small_lev,t.cust_lev, " 
						+ " t.cust_name,t.cust_crm_typ,t3."+level+"  instn_level_id,t4.unitname, "
						+ " sum(t.dep_bal_cny) dep_bal,sum(t.dep_avg_cny) dep_avg,sum(t.dep_qua_cny) dep_qua,sum(t.dep_year_cny) dep_year, " 
						+ " sum(t.lon_bal_cny) lon_bal,sum(t.lon_avg_cny) lon_avg,sum(t.lon_qua_cny) lon_qua,sum(t.lon_year_cny) lon_year, "
						+ " sum(t.dep_cq_cny)  dep_cy,sum(t.dep_avg_cq_cny)  dep_avg_cy,sum(t.dep_qua_cq_cny) dep_qua_cy ,sum(t.dep_year_cq_cny) dep_year_cy,sum(t.dep_year_cq_cny_11) dep_year_cy_11," 
						+ " sum(t.lon_cq_cny)  lon_cy,sum(t.lon_avg_cq_cny)  lon_avg_cy,sum(t.lon_qua_cq_cny) lon_qua_cy,sum(t.lon_year_cq_cny) lon_year_cy,sum(t.lon_year_cq_cny_11) lon_year_cy_11"
				);
				
//				stringBuffer.append(" from "+ depTable +" left join fdm.acrm_f_sm_sys_units units on t.instn_no = units.system_unitid  where (1=1) ");
				stringBuffer.append(" from "+ depTable +" inner join (select acrm_f_sm_sys_units_sta.unitid  from fdm.acrm_f_sm_sys_units_sta "+checkedOrg+") t2  on t.instn_no = t2.unitid " 
						+" inner join fdm.acrm_f_sm_sys_units_sta t3 on t3.level_4 = t.instn_no inner join fdm.acrm_f_sm_sys_units_sta t4 on t3."+level+" = t4.unitid  ");

				String clusterStr="";
				String rollStr=""; 
				if(paramsMap.get("cust_base_name")!=null && !(paramsMap.get("cust_base_name")+"").equals("") && !(paramsMap.get("cust_base_name")+"").equals("null")){
					 clusterStr = custCluster(paramsMap.get("cust_base_name")+"","ocrm_f_ci_relate_cust_base","cust_base_id");
				}if(paramsMap.get("roll_name")!=null && !(paramsMap.get("roll_name")+"").equals("") && !(paramsMap.get("roll_name")+"").equals("null")){
					 rollStr = custCluster(paramsMap.get("roll_name")+"","ocrm_f_mm_rcust_list","roll_id");
				}					
				if(clusterStr.equals("null") && rollStr.equals("null")){
					stringBuffer.append(" where  (0>1) ");
				}
				else if(clusterStr.equals("") && rollStr.equals("null")){
					stringBuffer.append(" where (0>1)");
				}
				else if(clusterStr.equals("null") && rollStr.equals("")){
					stringBuffer.append(" where (0>1)");					
				}else {
					stringBuffer.append(clusterStr);
					stringBuffer.append(rollStr);
				}
				
				if(paramsMap.get("dep_bal_from")!=null && !(paramsMap.get("dep_bal_from")+"").equals("") && !(paramsMap.get("dep_bal_from")+"").equals("null")){
					stringBuffer.append(" and t.dep_bal_cny >="+paramsMap.get("dep_bal_from")+" ");
				}
				if(paramsMap.get("dep_bal_to")!=null && !(paramsMap.get("dep_bal_to")+"").equals("") && !(paramsMap.get("dep_bal_to")+"").equals("null")){
					stringBuffer.append(" and t.dep_bal_cny <="+paramsMap.get("dep_bal_to")+" ");
				}
				if(paramsMap.get("dep_year_from")!=null && !(paramsMap.get("dep_year_from")+"").equals("") && !(paramsMap.get("dep_year_from")+"").equals("null")){
					stringBuffer.append(" and t.dep_year_cny>="+paramsMap.get("dep_year_from")+" ");
				}
				if(paramsMap.get("dep_year_to")!=null && !(paramsMap.get("dep_year_to")+"").equals("") && !(paramsMap.get("dep_year_to")+"").equals("null")){
					stringBuffer.append(" and t.dep_year_cny<="+paramsMap.get("dep_year_to")+" ");
				}	
				
				if(paramsMap.get("lon_bal_from")!=null && !(paramsMap.get("lon_bal_from")+"").equals("") && !(paramsMap.get("lon_bal_from")+"").equals("null")){
					stringBuffer.append(" and t.lon_bal_cny >="+paramsMap.get("lon_bal_from")+" ");
				}
				if(paramsMap.get("lon_bal_to")!=null && !(paramsMap.get("lon_bal_to")+"").equals("") && !(paramsMap.get("lon_bal_to")+"").equals("null")){
					stringBuffer.append(" and t.lon_bal_cny <="+paramsMap.get("lon_bal_to")+" ");
				}
				if(paramsMap.get("lon_year_from")!=null && !(paramsMap.get("lon_year_from")+"").equals("") && !(paramsMap.get("lon_year_from")+"").equals("null")){
					stringBuffer.append(" and t.lon_year_cny>="+paramsMap.get("lon_year_from")+" ");
				}
				if(paramsMap.get("lon_year_to")!=null && !(paramsMap.get("lon_year_to")+"").equals("") && !(paramsMap.get("lon_year_to")+"").equals("null")){
					stringBuffer.append(" and t.lon_year_cny<="+paramsMap.get("lon_year_to")+" ");
				}
				
				if(paramsMap.get("dep_cy")!=null && !(paramsMap.get("dep_cy")+"").equals("") && !(paramsMap.get("dep_cy")+"").equals("null")){
					stringBuffer.append(" and t.dep_cq_cny >="+paramsMap.get("dep_cy")+" ");
				}
				if(paramsMap.get("dep_avg_cy")!=null && !(paramsMap.get("dep_avg_cy")+"").equals("") && !(paramsMap.get("dep_avg_cy")+"").equals("null")){
					stringBuffer.append(" and t.dep_avg_cq_cny >="+paramsMap.get("dep_avg_cy")+" ");
				}
				if(paramsMap.get("dep_qua_cy")!=null && !(paramsMap.get("dep_qua_cy")+"").equals("") && !(paramsMap.get("dep_qua_cy")+"").equals("null")){
					stringBuffer.append(" and t.dep_qua_cq_cny >="+paramsMap.get("dep_qua_cy")+" ");
				}
				if(paramsMap.get("dep_year_cy")!=null && !(paramsMap.get("dep_year_cy")+"").equals("") && !(paramsMap.get("dep_year_cy")+"").equals("null")){
					stringBuffer.append(" and t.dep_year_cq_cny>="+paramsMap.get("dep_year_cy")+" ");
				}		
				
				if(paramsMap.get("dep_year_cy_11")!=null && !(paramsMap.get("dep_year_cy_11")+"").equals("") && !(paramsMap.get("dep_year_cy_11")+"").equals("null")){
					stringBuffer.append(" and t.dep_year_cq_cny_11>="+paramsMap.get("dep_year_cy_11")+" ");
				}
				
				if(paramsMap.get("lon_cy")!=null && !(paramsMap.get("lon_cy")+"").equals("") && !(paramsMap.get("lon_cy")+"").equals("null")){
					stringBuffer.append(" and t.lon_cq_cny >="+paramsMap.get("lon_cy")+" ");
				}
				if(paramsMap.get("lon_avg_cy")!=null && !(paramsMap.get("lon_avg_cy")+"").equals("") && !(paramsMap.get("lon_avg_cy")+"").equals("null")){
					stringBuffer.append(" and t.lon_avg_cq_cny >="+paramsMap.get("lon_avg_cy")+" ");
				}
				if(paramsMap.get("lon_qua_cy")!=null && !(paramsMap.get("lon_qua_cy")+"").equals("") && !(paramsMap.get("lon_qua_cy")+"").equals("null")){
					stringBuffer.append(" and t.lon_qua_cq_cny >="+paramsMap.get("lon_qua_cy")+" ");
				}
				if(paramsMap.get("lon_year_cy")!=null && !(paramsMap.get("lon_year_cy")+"").equals("") && !(paramsMap.get("lon_year_cy")+"").equals("null")){
					stringBuffer.append(" and t.lon_year_cq_cny>="+paramsMap.get("lon_year_cy")+" ");
				}				
				if(paramsMap.get("lon_year_cy_11")!=null && !(paramsMap.get("lon_year_cy_11")+"").equals("") && !(paramsMap.get("lon_year_cy_11")+"").equals("null")){
					stringBuffer.append(" and t.lon_year_cq_cny_11>="+paramsMap.get("lon_year_cy_11")+" ");
				}				
				
				
			}
			if(caculate_type_value.equals("03"))//如果对比口径是较月初
			{
				
				stringBuffer.append("select  '较月初'  caculate_type_desc,t.crm_dt,t.cust_zzdm,t.cust_id,t.sts,t.scope,t.crm_scope,t.cust_typ,t.cust_typ_desc,t.cust_big_lev,t.cust_small_lev,t.cust_lev, " 
						+ " t.cust_name,t.cust_crm_typ,t3."+level+"  instn_level_id,t4.unitname, "
						+ " sum(t.dep_bal_cny) dep_bal,sum(t.dep_avg_cny) dep_avg,sum(t.dep_qua_cny) dep_qua,sum(t.dep_year_cny) dep_year, " 
						+ " sum(t.lon_bal_cny) lon_bal,sum(t.lon_avg_cny) lon_avg,sum(t.lon_qua_cny) lon_qua,sum(t.lon_year_cny) lon_year, "
						+ " sum(t.dep_cm_cny)  dep_cy,sum(t.dep_avg_cm_cny)  dep_avg_cy,sum(t.dep_qua_cm_cny) dep_qua_cy ,sum(t.dep_year_cm_cny) dep_year_cy, sum(t.dep_year_cm_cny_11) dep_year_cy_11," 
						+ " sum(t.lon_cm_cny)  lon_cy,sum(t.lon_avg_cm_cny)  lon_avg_cy,sum(t.lon_qua_cm_cny) lon_qua_cy,sum(t.lon_year_cm_cny) lon_year_cy,sum(t.lon_year_cm_cny_11) lon_year_cy_11 "
				);
				
//				stringBuffer.append(" from "+ depTable +" left join fdm.acrm_f_sm_sys_units units on t.instn_no = units.system_unitid  where (1=1) ");
				stringBuffer.append(" from "+ depTable +" inner join (select acrm_f_sm_sys_units_sta.unitid  from fdm.acrm_f_sm_sys_units_sta "+checkedOrg+") t2  on t.instn_no = t2.unitid " 
						+" inner join fdm.acrm_f_sm_sys_units_sta t3 on t3.level_4 = t.instn_no inner join fdm.acrm_f_sm_sys_units_sta t4 on t3."+level+" = t4.unitid  ");

				String clusterStr="";
				String rollStr=""; 
				if(paramsMap.get("cust_base_name")!=null && !(paramsMap.get("cust_base_name")+"").equals("") && !(paramsMap.get("cust_base_name")+"").equals("null")){
					 clusterStr = custCluster(paramsMap.get("cust_base_name")+"","ocrm_f_ci_relate_cust_base","cust_base_id");
				}if(paramsMap.get("roll_name")!=null && !(paramsMap.get("roll_name")+"").equals("") && !(paramsMap.get("roll_name")+"").equals("null")){
					 rollStr = custCluster(paramsMap.get("roll_name")+"","ocrm_f_mm_rcust_list","roll_id");
				}					
				
				if(clusterStr.equals("null") && rollStr.equals("null")){
					stringBuffer.append(" where  (0>1) ");
				}
				else if(clusterStr.equals("") && rollStr.equals("null")){
					stringBuffer.append(" where (0>1)");
				}
				else if(clusterStr.equals("null") && rollStr.equals("")){
					stringBuffer.append(" where (0>1)");					
				}else {
					stringBuffer.append(clusterStr);
					stringBuffer.append(rollStr);
				}

				
				
				if(paramsMap.get("dep_bal_from")!=null && !(paramsMap.get("dep_bal_from")+"").equals("") && !(paramsMap.get("dep_bal_from")+"").equals("null")){
					stringBuffer.append(" and t.dep_bal_cny >="+paramsMap.get("dep_bal_from")+" ");
				}
				if(paramsMap.get("dep_bal_to")!=null && !(paramsMap.get("dep_bal_to")+"").equals("") && !(paramsMap.get("dep_bal_to")+"").equals("null")){
					stringBuffer.append(" and t.dep_bal_cny <="+paramsMap.get("dep_bal_to")+" ");
				}
				if(paramsMap.get("dep_year_from")!=null && !(paramsMap.get("dep_year_from")+"").equals("") && !(paramsMap.get("dep_year_from")+"").equals("null")){
					stringBuffer.append(" and t.dep_year_cny>="+paramsMap.get("dep_year_from")+" ");
				}
				if(paramsMap.get("dep_year_to")!=null && !(paramsMap.get("dep_year_to")+"").equals("") && !(paramsMap.get("dep_year_to")+"").equals("null")){
					stringBuffer.append(" and t.dep_year_cny<="+paramsMap.get("dep_year_to")+" ");
				}	
				
				if(paramsMap.get("lon_bal_from")!=null && !(paramsMap.get("lon_bal_from")+"").equals("") && !(paramsMap.get("lon_bal_from")+"").equals("null")){
					stringBuffer.append(" and t.lon_bal_cny >="+paramsMap.get("lon_bal_from")+" ");
				}
				if(paramsMap.get("lon_bal_to")!=null && !(paramsMap.get("lon_bal_to")+"").equals("") && !(paramsMap.get("lon_bal_to")+"").equals("null")){
					stringBuffer.append(" and t.lon_bal_cny <="+paramsMap.get("lon_bal_to")+" ");
				}
				if(paramsMap.get("lon_year_from")!=null && !(paramsMap.get("lon_year_from")+"").equals("") && !(paramsMap.get("lon_year_from")+"").equals("null")){
					stringBuffer.append(" and t.lon_year_cny>="+paramsMap.get("lon_year_from")+" ");
				}
				if(paramsMap.get("lon_year_to")!=null && !(paramsMap.get("lon_year_to")+"").equals("") && !(paramsMap.get("lon_year_to")+"").equals("null")){
					stringBuffer.append(" and t.lon_year_cny<="+paramsMap.get("lon_year_to")+" ");
				}
				
				if(paramsMap.get("dep_cy")!=null && !(paramsMap.get("dep_cy")+"").equals("") && !(paramsMap.get("dep_cy")+"").equals("null")){
					stringBuffer.append(" and t.dep_cm_cny >="+paramsMap.get("dep_cy")+" ");
				}
				if(paramsMap.get("dep_avg_cy")!=null && !(paramsMap.get("dep_avg_cy")+"").equals("") && !(paramsMap.get("dep_avg_cy")+"").equals("null")){
					stringBuffer.append(" and t.dep_avg_cm_cny >="+paramsMap.get("dep_avg_cy")+" ");
				}
				if(paramsMap.get("dep_qua_cy")!=null && !(paramsMap.get("dep_qua_cy")+"").equals("") && !(paramsMap.get("dep_qua_cy")+"").equals("null")){
					stringBuffer.append(" and t.dep_qua_cm_cny >="+paramsMap.get("dep_qua_cy")+" ");
				}
				if(paramsMap.get("dep_year_cy")!=null && !(paramsMap.get("dep_year_cy")+"").equals("") && !(paramsMap.get("dep_year_cy")+"").equals("null")){
					stringBuffer.append(" and t.dep_year_cm_cny>="+paramsMap.get("dep_year_cy")+" ");
				}		
				
				if(paramsMap.get("dep_year_cy_11")!=null && !(paramsMap.get("dep_year_cy_11")+"").equals("") && !(paramsMap.get("dep_year_cy_11")+"").equals("null")){
					stringBuffer.append(" and t.dep_year_cm_cny_11>="+paramsMap.get("dep_year_cy_11")+" ");
				}
				
				if(paramsMap.get("lon_cy")!=null && !(paramsMap.get("lon_cy")+"").equals("") && !(paramsMap.get("lon_cy")+"").equals("null")){
					stringBuffer.append(" and t.lon_cm_cny >="+paramsMap.get("lon_cy")+" ");
				}
				if(paramsMap.get("lon_avg_cy")!=null && !(paramsMap.get("lon_avg_cy")+"").equals("") && !(paramsMap.get("lon_avg_cy")+"").equals("null")){
					stringBuffer.append(" and t.lon_avg_cm_cny >="+paramsMap.get("lon_avg_cy")+" ");
				}
				if(paramsMap.get("lon_qua_cy")!=null && !(paramsMap.get("lon_qua_cy")+"").equals("") && !(paramsMap.get("lon_qua_cy")+"").equals("null")){
					stringBuffer.append(" and t.lon_qua_cm_cny >="+paramsMap.get("lon_qua_cy")+" ");
				}
				if(paramsMap.get("lon_year_cy")!=null && !(paramsMap.get("lon_year_cy")+"").equals("") && !(paramsMap.get("lon_year_cy")+"").equals("null")){
					stringBuffer.append(" and t.lon_year_cm_cny>="+paramsMap.get("lon_year_cy")+" ");
				}				
				if(paramsMap.get("lon_year_cy_11")!=null && !(paramsMap.get("lon_year_cy_11")+"").equals("") && !(paramsMap.get("lon_year_cy_11")+"").equals("null")){
					stringBuffer.append(" and t.lon_year_cm_cny_11>="+paramsMap.get("lon_year_cy_11")+" ");
				}
				
			}
			if(caculate_type_value.equals("04"))//如果对比口径是较昨日
			{
				stringBuffer.append("select  '较昨日'  caculate_type_desc,t.crm_dt,t.cust_zzdm,t.cust_id,t.sts,t.scope,t.crm_scope,t.cust_typ,t.cust_typ_desc,t.cust_big_lev,t.cust_small_lev,t.cust_lev, " 
						+ " t.cust_name,t.cust_crm_typ,t3."+level+"  instn_level_id,t4.unitname, " 
						+ " sum(t.dep_bal_cny) dep_bal,sum(t.dep_avg_cny) dep_avg,sum(t.dep_qua_cny) dep_qua,sum(t.dep_year_cny) dep_year, " 
						+ " sum(t.lon_bal_cny) lon_bal,sum(t.lon_avg_cny) lon_avg,sum(t.lon_qua_cny) lon_qua,sum(t.lon_year_cny) lon_year, "
						+ " sum(t.dep_cd_cny)  dep_cy,sum(t.dep_avg_cd_cny)  dep_avg_cy,sum(t.dep_qua_cd_cny) dep_qua_cy ,sum(t.dep_year_cd_cny) dep_year_cy, sum(t.dep_year_cd_cny_11) dep_year_cy_11," 
						+ " sum(t.lon_cd_cny)  lon_cy,sum(t.lon_avg_cd_cny)  lon_avg_cy,sum(t.lon_qua_cd_cny) lon_qua_cy,sum(t.lon_year_cd_cny) lon_year_cy, sum(t.lon_year_cd_cny_11) lon_year_cy_11  "
				);					
				
//				stringBuffer.append(" from "+ depTable +" left join fdm.acrm_f_sm_sys_units units on t.instn_no = units.system_unitid  where (1=1) ");

				stringBuffer.append(" from "+ depTable +" inner join (select acrm_f_sm_sys_units_sta.unitid  from fdm.acrm_f_sm_sys_units_sta "+checkedOrg+") t2  on t.instn_no = t2.unitid " 
						+" inner join fdm.acrm_f_sm_sys_units_sta t3 on t3.level_4 = t.instn_no inner join fdm.acrm_f_sm_sys_units_sta t4 on t3."+level+" = t4.unitid  ");

				String clusterStr="";
				String rollStr=""; 
				if(paramsMap.get("cust_base_name")!=null && !(paramsMap.get("cust_base_name")+"").equals("") && !(paramsMap.get("cust_base_name")+"").equals("null")){
					 clusterStr = custCluster(paramsMap.get("cust_base_name")+"","ocrm_f_ci_relate_cust_base","cust_base_id");
				}if(paramsMap.get("roll_name")!=null && !(paramsMap.get("roll_name")+"").equals("") && !(paramsMap.get("roll_name")+"").equals("null")){
					 rollStr = custCluster(paramsMap.get("roll_name")+"","ocrm_f_mm_rcust_list","roll_id");
				}					
				
				if(clusterStr.equals("null") && rollStr.equals("null")){
					stringBuffer.append(" where  (0>1) ");
				}
				else if(clusterStr.equals("") && rollStr.equals("null")){
					stringBuffer.append(" where (0>1)");
				}
				else if(clusterStr.equals("null") && rollStr.equals("")){
					stringBuffer.append(" where (0>1)");					
				}else {
					stringBuffer.append(clusterStr);
					stringBuffer.append(rollStr);
				}
				
				if(paramsMap.get("dep_bal_from")!=null && !(paramsMap.get("dep_bal_from")+"").equals("") && !(paramsMap.get("dep_bal_from")+"").equals("null")){
					stringBuffer.append(" and t.dep_bal_cny >="+paramsMap.get("dep_bal_from")+" ");
				}
				if(paramsMap.get("dep_bal_to")!=null && !(paramsMap.get("dep_bal_to")+"").equals("") && !(paramsMap.get("dep_bal_to")+"").equals("null")){
					stringBuffer.append(" and t.dep_bal_cny <="+paramsMap.get("dep_bal_to")+" ");
				}
				if(paramsMap.get("dep_year_from")!=null && !(paramsMap.get("dep_year_from")+"").equals("") && !(paramsMap.get("dep_year_from")+"").equals("null")){
					stringBuffer.append(" and t.dep_year_cny>="+paramsMap.get("dep_year_from")+" ");
				}
				if(paramsMap.get("dep_year_to")!=null && !(paramsMap.get("dep_year_to")+"").equals("") && !(paramsMap.get("dep_year_to")+"").equals("null")){
					stringBuffer.append(" and t.dep_year_cny<="+paramsMap.get("dep_year_to")+" ");
				}	
				
				if(paramsMap.get("lon_bal_from")!=null && !(paramsMap.get("lon_bal_from")+"").equals("") && !(paramsMap.get("lon_bal_from")+"").equals("null")){
					stringBuffer.append(" and t.lon_bal_cny >="+paramsMap.get("lon_bal_from")+" ");
				}
				if(paramsMap.get("lon_bal_to")!=null && !(paramsMap.get("lon_bal_to")+"").equals("") && !(paramsMap.get("lon_bal_to")+"").equals("null")){
					stringBuffer.append(" and t.lon_bal_cny <="+paramsMap.get("lon_bal_to")+" ");
				}
				if(paramsMap.get("lon_year_from")!=null && !(paramsMap.get("lon_year_from")+"").equals("") && !(paramsMap.get("lon_year_from")+"").equals("null")){
					stringBuffer.append(" and t.lon_year_cny>="+paramsMap.get("lon_year_from")+" ");
				}
				if(paramsMap.get("lon_year_to")!=null && !(paramsMap.get("lon_year_to")+"").equals("") && !(paramsMap.get("lon_year_to")+"").equals("null")){
					stringBuffer.append(" and t.lon_year_cny<="+paramsMap.get("lon_year_to")+" ");
				}
				
				if(paramsMap.get("dep_cy")!=null && !(paramsMap.get("dep_cy")+"").equals("") && !(paramsMap.get("dep_cy")+"").equals("null")){
					stringBuffer.append(" and t.dep_cd_cny >="+paramsMap.get("dep_cy")+" ");
				}
				if(paramsMap.get("dep_avg_cy")!=null && !(paramsMap.get("dep_avg_cy")+"").equals("") && !(paramsMap.get("dep_avg_cy")+"").equals("null")){
					stringBuffer.append(" and t.dep_avg_cd_cny >="+paramsMap.get("dep_avg_cy")+" ");
				}
				if(paramsMap.get("dep_qua_cy")!=null && !(paramsMap.get("dep_qua_cy")+"").equals("") && !(paramsMap.get("dep_qua_cy")+"").equals("null")){
					stringBuffer.append(" and t.dep_qua_cd_cny >="+paramsMap.get("dep_qua_cy")+" ");
				}
				if(paramsMap.get("dep_year_cy")!=null && !(paramsMap.get("dep_year_cy")+"").equals("") && !(paramsMap.get("dep_year_cy")+"").equals("null")){
					stringBuffer.append(" and t.dep_year_cd_cny>="+paramsMap.get("dep_year_cy")+" ");
				}		
				
				if(paramsMap.get("dep_year_cy_11")!=null && !(paramsMap.get("dep_year_cy_11")+"").equals("") && !(paramsMap.get("dep_year_cy_11")+"").equals("null")){
					stringBuffer.append(" and t.dep_year_cd_cny_11>="+paramsMap.get("dep_year_cy_11")+" ");
				}
				
				if(paramsMap.get("lon_cy")!=null && !(paramsMap.get("lon_cy")+"").equals("") && !(paramsMap.get("lon_cy")+"").equals("null")){
					stringBuffer.append(" and t.lon_cd_cny >="+paramsMap.get("lon_cy")+" ");
				}
				if(paramsMap.get("lon_avg_cy")!=null && !(paramsMap.get("lon_avg_cy")+"").equals("") && !(paramsMap.get("lon_avg_cy")+"").equals("null")){
					stringBuffer.append(" and t.lon_avg_cd_cny >="+paramsMap.get("lon_avg_cy")+" ");
				}
				if(paramsMap.get("lon_qua_cy")!=null && !(paramsMap.get("lon_qua_cy")+"").equals("") && !(paramsMap.get("lon_qua_cy")+"").equals("null")){
					stringBuffer.append(" and t.lon_qua_cd_cny >="+paramsMap.get("lon_qua_cy")+" ");
				}
				if(paramsMap.get("lon_year_cy")!=null && !(paramsMap.get("lon_year_cy")+"").equals("") && !(paramsMap.get("lon_year_cy")+"").equals("null")){
					stringBuffer.append(" and t.lon_year_cd_cny>="+paramsMap.get("lon_year_cy")+" ");
				}				
				if(paramsMap.get("lon_year_cy_11")!=null && !(paramsMap.get("lon_year_cy_11")+"").equals("") && !(paramsMap.get("lon_year_cy_11")+"").equals("null")){
					stringBuffer.append(" and t.lon_year_cd_cny_11>="+paramsMap.get("lon_year_cy_11")+" ");
				}
				
			}			
		}
		
		
		else if(curr_type_value.equals("03"))//如果币种是本外币
		{
			if(caculate_type_value.equals("01"))//如果对比口径是较年初
			{
				stringBuffer.append("select '较年初'  caculate_type_desc,t.crm_dt,t.cust_zzdm,t.cust_id,t.sts,t.scope,t.crm_scope,t.cust_typ,t.cust_typ_desc,t.cust_big_lev,t.cust_small_lev,t.cust_lev, " 
						+ " t.cust_name,t.cust_crm_typ,t3."+level+"  instn_level_id,t4.unitname, "
						+ " sum(t.dep_bal_sum) dep_bal,sum(t.dep_avg_sum) dep_avg,sum(t.dep_qua_sum) dep_qua,sum(t.dep_year_sum) dep_year, " 
						+ " sum(t.lon_bal_sum) lon_bal,sum(t.lon_avg_sum) lon_avg,sum(t.lon_qua_sum) lon_qua,sum(t.lon_year_sum) lon_year, "
						+ " sum(t.dep_cy_sum)  dep_cy,sum(t.dep_avg_cy_sum)  dep_avg_cy,sum(t.dep_qua_cy_sum) dep_qua_cy ,sum(t.dep_year_cy_sum) dep_year_cy, sum(t.dep_year_cy_sum_11) dep_year_cy_11," 
						+ " sum(t.lon_cy_sum)  lon_cy,sum(t.lon_avg_cy_sum)  lon_avg_cy,sum(t.lon_qua_cy_sum) lon_qua_cy,sum(t.lon_year_cy_sum) lon_year_cy, sum(t.lon_year_cy_sum_11) lon_year_cy_11 "
				);						

				
//				stringBuffer.append(" from "+ depTable +" left join fdm.acrm_f_sm_sys_units units on t.instn_no = units.system_unitid where (1=1) ");

				stringBuffer.append(" from "+ depTable +" inner join (select acrm_f_sm_sys_units_sta.unitid  from fdm.acrm_f_sm_sys_units_sta "+checkedOrg+") t2  on t.instn_no = t2.unitid " 
						+" inner join fdm.acrm_f_sm_sys_units_sta t3 on t3.level_4 = t.instn_no inner join fdm.acrm_f_sm_sys_units_sta t4 on t3."+level+" = t4.unitid  ");

				String clusterStr="";
				String rollStr=""; 
				if(paramsMap.get("cust_base_name")!=null && !(paramsMap.get("cust_base_name")+"").equals("") && !(paramsMap.get("cust_base_name")+"").equals("null")){
					 clusterStr = custCluster(paramsMap.get("cust_base_name")+"","ocrm_f_ci_relate_cust_base","cust_base_id");
				}if(paramsMap.get("roll_name")!=null && !(paramsMap.get("roll_name")+"").equals("") && !(paramsMap.get("roll_name")+"").equals("null")){
					 rollStr = custCluster(paramsMap.get("roll_name")+"","ocrm_f_mm_rcust_list","roll_id");
				}					
				
				if(clusterStr.equals("null") && rollStr.equals("null")){
					stringBuffer.append(" where  (0>1) ");
				}
				else if(clusterStr.equals("") && rollStr.equals("null")){
					stringBuffer.append(" where (0>1)");
				}
				else if(clusterStr.equals("null") && rollStr.equals("")){
					stringBuffer.append(" where (0>1)");					
				}else {
					stringBuffer.append(clusterStr);
					stringBuffer.append(rollStr);
				}
				
				if(paramsMap.get("dep_bal_from")!=null && !(paramsMap.get("dep_bal_from")+"").equals("") && !(paramsMap.get("dep_bal_from")+"").equals("null")){
					stringBuffer.append(" and t.dep_bal_sum >="+paramsMap.get("dep_bal_from")+" ");
				}
				if(paramsMap.get("dep_bal_to")!=null && !(paramsMap.get("dep_bal_to")+"").equals("") && !(paramsMap.get("dep_bal_to")+"").equals("null")){
					stringBuffer.append(" and t.dep_bal_sum <="+paramsMap.get("dep_bal_to")+" ");
				}
				if(paramsMap.get("dep_year_from")!=null && !(paramsMap.get("dep_year_from")+"").equals("") && (paramsMap.get("dep_year_from")+"").equals("null")){
					stringBuffer.append(" and t.dep_year_sum>="+paramsMap.get("dep_year_from")+" ");
				}
				if(paramsMap.get("dep_year_to")!=null && !(paramsMap.get("dep_year_to")+"").equals("") && (paramsMap.get("dep_year_to")+"").equals("null")){
					stringBuffer.append(" and t.dep_year_sum<="+paramsMap.get("dep_year_to")+" ");
				}	
				
				if(paramsMap.get("lon_bal_from")!=null && !(paramsMap.get("lon_bal_from")+"").equals("") && !(paramsMap.get("lon_bal_from")+"").equals("null")){
					stringBuffer.append(" and t.lon_bal_sum >="+paramsMap.get("lon_bal_from")+" ");
				}
				if(paramsMap.get("lon_bal_to")!=null && !(paramsMap.get("lon_bal_to")+"").equals("") && !(paramsMap.get("lon_bal_to")+"").equals("null")){
					stringBuffer.append(" and t.lon_bal_sum <="+paramsMap.get("lon_bal_to")+" ");
				}
				if(paramsMap.get("lon_year_from")!=null && !(paramsMap.get("lon_year_from")+"").equals("") && (paramsMap.get("lon_year_from")+"").equals("null")){
					stringBuffer.append(" and t.lon_year_sum >="+paramsMap.get("lon_year_from")+" ");
				}
				if(paramsMap.get("lon_year_to")!=null && !(paramsMap.get("lon_year_to")+"").equals("") && (paramsMap.get("lon_year_to")+"").equals("null")){
					stringBuffer.append(" and t.lon_year_sum <="+paramsMap.get("lon_year_to")+" ");
				}
				
				if(paramsMap.get("dep_cy")!=null && !(paramsMap.get("dep_cy")+"").equals("") && !(paramsMap.get("dep_cy")+"").equals("null")){
					stringBuffer.append(" and t.dep_cy_sum >="+paramsMap.get("dep_cy")+" ");
				}
				if(paramsMap.get("dep_avg_cy")!=null && !(paramsMap.get("dep_avg_cy")+"").equals("") && !(paramsMap.get("dep_avg_cy")+"").equals("null")){
					stringBuffer.append(" and t.dep_avg_cy_sum >="+paramsMap.get("dep_avg_cy")+" ");
				}
				if(paramsMap.get("dep_qua_cy")!=null && !(paramsMap.get("dep_qua_cy")+"").equals("") && (paramsMap.get("dep_qua_cy")+"").equals("null")){
					stringBuffer.append(" and t.dep_qua_cy_sum >="+paramsMap.get("dep_qua_cy")+" ");
				}
				if(paramsMap.get("dep_year_cy")!=null && !(paramsMap.get("dep_year_cy")+"").equals("") && (paramsMap.get("dep_year_cy")+"").equals("null")){
					stringBuffer.append(" and t.dep_year_cy_sum>="+paramsMap.get("dep_year_cy")+" ");
				}		
				
				if(paramsMap.get("dep_year_cy_11")!=null && !(paramsMap.get("dep_year_cy_11")+"").equals("") && (paramsMap.get("dep_year_cy_11")+"").equals("null")){
					stringBuffer.append(" and t.dep_year_cy_11_sum>="+paramsMap.get("dep_year_cy_11")+" ");
				}
				
				if(paramsMap.get("lon_cy")!=null && !(paramsMap.get("lon_cy")+"").equals("") && !(paramsMap.get("lon_cy")+"").equals("null")){
					stringBuffer.append(" and t.lon_cy_sum >="+paramsMap.get("lon_cy")+" ");
				}
				if(paramsMap.get("lon_avg_cy")!=null && !(paramsMap.get("lon_avg_cy")+"").equals("") && !(paramsMap.get("lon_avg_cy")+"").equals("null")){
					stringBuffer.append(" and t.lon_avg_cy_sum >="+paramsMap.get("lon_avg_cy")+" ");
				}
				if(paramsMap.get("lon_qua_cy")!=null && !(paramsMap.get("lon_qua_cy")+"").equals("") && (paramsMap.get("lon_qua_cy")+"").equals("null")){
					stringBuffer.append(" and t.lon_qua_cy_sum >="+paramsMap.get("lon_qua_cy")+" ");
				}
				if(paramsMap.get("lon_year_cy")!=null && !(paramsMap.get("lon_year_cy")+"").equals("") && (paramsMap.get("lon_year_cy")+"").equals("null")){
					stringBuffer.append(" and t.lon_year_cy_sum>="+paramsMap.get("lon_year_cy")+" ");
				}				
				if(paramsMap.get("lon_year_cy_11")!=null && !(paramsMap.get("lon_year_cy_11")+"").equals("") && (paramsMap.get("lon_year_cy_11")+"").equals("null")){
					stringBuffer.append(" and t.lon_year_cy_sum_11>="+paramsMap.get("lon_year_cy_11")+" ");
				}				
				
				
			}
			else if(caculate_type_value.equals("02"))//如果对比口径是较季初
			{
				stringBuffer.append("select '较季出'  caculate_type_desc,t.crm_dt,t.cust_zzdm,t.cust_id,t.sts,t.scope,t.crm_scope,t.cust_typ,t.cust_typ_desc,t.cust_big_lev,t.cust_small_lev,t.cust_lev, " 
						+ " t.cust_name,t.cust_crm_typ,t3."+level+"  instn_level_id,t4.unitname, "
						+ " sum(t.dep_bal_sum) dep_bal,sum(t.dep_avg_sum) dep_avg,sum(t.dep_qua_sum) dep_qua,sum(t.dep_year_sum) dep_year, " 
						+ " sum(t.lon_bal_sum) lon_bal,sum(t.lon_avg_sum) lon_avg,sum(t.lon_qua_sum) lon_qua,sum(t.lon_year_sum) lon_year, "
						+ " sum(t.dep_cq_sum)  dep_cy,sum(t.dep_avg_cq_sum)  dep_avg_cy,sum(t.dep_qua_cq_sum) dep_qua_cy ,sum(t.dep_year_cq_sum) dep_year_cy, sum(t.dep_year_cq_sum_11) dep_year_cy_11," 
						+ " sum(t.lon_cq_sum)  lon_cy,sum(t.lon_avg_cq_sum)  lon_avg_cy,sum(t.lon_qua_cq_sum) lon_qua_cy,sum(t.lon_year_cq_sum) lon_year_cy,  sum(t.lon_year_cq_sum_11) lon_year_cy_11 "
				);
				
//				stringBuffer.append(" from "+ depTable +" left join fdm.acrm_f_sm_sys_units units on t.instn_no = units.system_unitid where (1=1) ");
				stringBuffer.append(" from "+ depTable +" inner join (select acrm_f_sm_sys_units_sta.unitid  from fdm.acrm_f_sm_sys_units_sta "+checkedOrg+") t2  on t.instn_no = t2.unitid " 
						+" inner join fdm.acrm_f_sm_sys_units_sta t3 on t3.level_4 = t.instn_no inner join fdm.acrm_f_sm_sys_units_sta t4 on t3."+level+" = t4.unitid  ");

				String clusterStr="";
				String rollStr=""; 
				if(paramsMap.get("cust_base_name")!=null && !(paramsMap.get("cust_base_name")+"").equals("") && !(paramsMap.get("cust_base_name")+"").equals("null")){
					 clusterStr = custCluster(paramsMap.get("cust_base_name")+"","ocrm_f_ci_relate_cust_base","cust_base_id");
				}if(paramsMap.get("roll_name")!=null && !(paramsMap.get("roll_name")+"").equals("") && !(paramsMap.get("roll_name")+"").equals("null")){
					 rollStr = custCluster(paramsMap.get("roll_name")+"","ocrm_f_mm_rcust_list","roll_id");
				}					
				
				
				if(clusterStr.equals("null") && rollStr.equals("null")){
					stringBuffer.append(" where  (0>1) ");
				}
				else if(clusterStr.equals("") && rollStr.equals("null")){
					stringBuffer.append(" where (0>1)");
				}
				else if(clusterStr.equals("null") && rollStr.equals("")){
					stringBuffer.append(" where (0>1)");					
				}else {
					stringBuffer.append(clusterStr);
					stringBuffer.append(rollStr);
				}
				
				if(paramsMap.get("dep_bal_from")!=null && !(paramsMap.get("dep_bal_from")+"").equals("") && !(paramsMap.get("dep_bal_from")+"").equals("null")){
					stringBuffer.append(" and t.dep_bal_sum >="+paramsMap.get("dep_bal_from")+" ");
				}
				if(paramsMap.get("dep_bal_to")!=null && !(paramsMap.get("dep_bal_to")+"").equals("") && !(paramsMap.get("dep_bal_to")+"").equals("null")){
					stringBuffer.append(" and t.dep_bal_sum <="+paramsMap.get("dep_bal_to")+" ");
				}
				if(paramsMap.get("dep_year_from")!=null && !(paramsMap.get("dep_year_from")+"").equals("") && (paramsMap.get("dep_year_from")+"").equals("null")){
					stringBuffer.append(" and t.dep_year_sum>="+paramsMap.get("dep_year_from")+" ");
				}
				if(paramsMap.get("dep_year_to")!=null && !(paramsMap.get("dep_year_to")+"").equals("") && (paramsMap.get("dep_year_to")+"").equals("null")){
					stringBuffer.append(" and t.dep_year_sum<="+paramsMap.get("dep_year_to")+" ");
				}	
				
				if(paramsMap.get("lon_bal_from")!=null && !(paramsMap.get("lon_bal_from")+"").equals("") && !(paramsMap.get("lon_bal_from")+"").equals("null")){
					stringBuffer.append(" and t.lon_bal_sum >="+paramsMap.get("lon_bal_from")+" ");
				}
				if(paramsMap.get("lon_bal_to")!=null && !(paramsMap.get("lon_bal_to")+"").equals("") && !(paramsMap.get("lon_bal_to")+"").equals("null")){
					stringBuffer.append(" and t.lon_bal_sum <="+paramsMap.get("lon_bal_to")+" ");
				}
				if(paramsMap.get("lon_year_from")!=null && !(paramsMap.get("lon_year_from")+"").equals("") && (paramsMap.get("lon_year_from")+"").equals("null")){
					stringBuffer.append(" and t.lon_year_sum>="+paramsMap.get("lon_year_from")+" ");
				}
				if(paramsMap.get("lon_year_to")!=null && !(paramsMap.get("lon_year_to")+"").equals("") && (paramsMap.get("lon_year_to")+"").equals("null")){
					stringBuffer.append(" and t.lon_year_sum<="+paramsMap.get("lon_year_to")+" ");
				}
				
				if(paramsMap.get("dep_cy")!=null && !(paramsMap.get("dep_cy")+"").equals("") && !(paramsMap.get("dep_cy")+"").equals("null")){
					stringBuffer.append(" and t.dep_cq_sum >="+paramsMap.get("dep_cy")+" ");
				}
				if(paramsMap.get("dep_avg_cy")!=null && !(paramsMap.get("dep_avg_cy")+"").equals("") && !(paramsMap.get("dep_avg_cy")+"").equals("null")){
					stringBuffer.append(" and t.dep_avg_cq_sum >="+paramsMap.get("dep_avg_cy")+" ");
				}
				if(paramsMap.get("dep_qua_cy")!=null && !(paramsMap.get("dep_qua_cy")+"").equals("") && (paramsMap.get("dep_qua_cy")+"").equals("null")){
					stringBuffer.append(" and t.dep_qua_cq_sum >="+paramsMap.get("dep_qua_cy")+" ");
				}
				if(paramsMap.get("dep_year_cy")!=null && !(paramsMap.get("dep_year_cy")+"").equals("") && (paramsMap.get("dep_year_cy")+"").equals("null")){
					stringBuffer.append(" and t.dep_year_cq_sum>="+paramsMap.get("dep_year_cy")+" ");
				}		
				
				if(paramsMap.get("dep_year_cy_11")!=null && !(paramsMap.get("dep_year_cy_11")+"").equals("") && (paramsMap.get("dep_year_cy_11")+"").equals("null")){
					stringBuffer.append(" and t.dep_year_cq_sum_11>="+paramsMap.get("dep_year_cy_11")+" ");
				}
				
				if(paramsMap.get("lon_cy")!=null && !(paramsMap.get("lon_cy")+"").equals("") && !(paramsMap.get("lon_cy")+"").equals("null")){
					stringBuffer.append(" and t.lon_cq_sum >="+paramsMap.get("lon_cy")+" ");
				}
				if(paramsMap.get("lon_avg_cy")!=null && !(paramsMap.get("lon_avg_cy")+"").equals("") && !(paramsMap.get("lon_avg_cy")+"").equals("null")){
					stringBuffer.append(" and t.lon_avg_cq_sum >="+paramsMap.get("lon_avg_cy")+" ");
				}
				if(paramsMap.get("lon_qua_cy")!=null && !(paramsMap.get("lon_qua_cy")+"").equals("") && (paramsMap.get("lon_qua_cy")+"").equals("null")){
					stringBuffer.append(" and t.lon_qua_cq_sum >="+paramsMap.get("lon_qua_cy")+" ");
				}
				if(paramsMap.get("lon_year_cy")!=null && !(paramsMap.get("lon_year_cy")+"").equals("") && (paramsMap.get("lon_year_cy")+"").equals("null")){
					stringBuffer.append(" and t.lon_year_cq_sum>="+paramsMap.get("lon_year_cy")+" ");
				}				
				if(paramsMap.get("lon_year_cy_11")!=null && !(paramsMap.get("lon_year_cy_11")+"").equals("") && (paramsMap.get("lon_year_cy_11")+"").equals("null")){
					stringBuffer.append(" and t.lon_year_cq_sum_11>="+paramsMap.get("lon_year_cy_11")+" ");
				}				
				
				
			}
			else if(caculate_type_value.equals("03"))//如果对比口径是较月初
			{
				stringBuffer.append("select '较月初'  caculate_type_desc,t.crm_dt,t.cust_zzdm,t.cust_id,t.sts,t.scope,t.crm_scope,t.cust_typ,t.cust_typ_desc,t.cust_big_lev,t.cust_small_lev,t.cust_lev, " 
						+ " t.cust_name,t.cust_crm_typ,t3."+level+"  instn_level_id,t4.unitname, "
						+ " sum(t.dep_bal_sum) dep_bal,sum(t.dep_avg_sum) dep_avg,sum(t.dep_qua_sum) dep_qua,sum(t.dep_year_sum) dep_year, " 
						+ " sum(t.lon_bal_sum) lon_bal,sum(t.lon_avg_sum) lon_avg,sum(t.lon_qua_sum) lon_qua,sum(t.lon_year_sum) lon_year, "
						+ " sum(t.dep_cm_sum)  dep_cy,sum(t.dep_avg_cm_sum)  dep_avg_cy,sum(t.dep_qua_cm_sum) dep_qua_cy ,sum(t.dep_year_cm_sum) dep_year_cy,sum(t.dep_year_cm_sum_11) dep_year_cy_11," 
						+ " sum(t.lon_cm_sum)  lon_cy,sum(t.lon_avg_cm_sum)  lon_avg_cy,sum(t.lon_qua_cm_sum) lon_qua_cy ,sum(t.lon_year_cm_sum) lon_year_cy,sum(t.lon_year_cm_sum_11) lon_year_cy_11 "
				);		
//				stringBuffer.append(" from "+ depTable +"  left join fdm.acrm_f_sm_sys_units units on t.instn_no = units.system_unitid where (1=1) ");
				stringBuffer.append(" from "+ depTable +" inner join (select acrm_f_sm_sys_units_sta.unitid  from fdm.acrm_f_sm_sys_units_sta "+checkedOrg+") t2  on t.instn_no = t2.unitid " 
						+" inner join fdm.acrm_f_sm_sys_units_sta t3 on t3.level_4 = t.instn_no inner join fdm.acrm_f_sm_sys_units_sta t4 on t3."+level+" = t4.unitid  ");

				String clusterStr="";
				String rollStr=""; 
				if(paramsMap.get("cust_base_name")!=null && !(paramsMap.get("cust_base_name")+"").equals("") && !(paramsMap.get("cust_base_name")+"").equals("null")){
					 clusterStr = custCluster(paramsMap.get("cust_base_name")+"","ocrm_f_ci_relate_cust_base","cust_base_id");
				}if(paramsMap.get("roll_name")!=null && !(paramsMap.get("roll_name")+"").equals("") && !(paramsMap.get("roll_name")+"").equals("null")){
					 rollStr = custCluster(paramsMap.get("roll_name")+"","ocrm_f_mm_rcust_list","roll_id");
				}					
				
				if(clusterStr.equals("null") && rollStr.equals("null")){
					stringBuffer.append(" where  (0>1) ");
				}
				else if(clusterStr.equals("") && rollStr.equals("null")){
					stringBuffer.append(" where (0>1)");
				}
				else if(clusterStr.equals("null") && rollStr.equals("")){
					stringBuffer.append(" where (0>1)");					
				}else {
					stringBuffer.append(clusterStr);
					stringBuffer.append(rollStr);
				}
				
				
				if(paramsMap.get("dep_bal_from")!=null && !(paramsMap.get("dep_bal_from")+"").equals("") && !(paramsMap.get("dep_bal_from")+"").equals("null")){
					stringBuffer.append(" and t.dep_bal_sum >="+paramsMap.get("dep_bal_from")+" ");
				}
				if(paramsMap.get("dep_bal_to")!=null && !(paramsMap.get("dep_bal_to")+"").equals("") && !(paramsMap.get("dep_bal_to")+"").equals("null")){
					stringBuffer.append(" and t.dep_bal_sum <="+paramsMap.get("dep_bal_to")+" ");
				}
				if(paramsMap.get("dep_year_from")!=null && !(paramsMap.get("dep_year_from")+"").equals("") && (paramsMap.get("dep_year_from")+"").equals("null")){
					stringBuffer.append(" and t.dep_year_sum>="+paramsMap.get("dep_year_from")+" ");
				}
				if(paramsMap.get("dep_year_to")!=null && !(paramsMap.get("dep_year_to")+"").equals("") && (paramsMap.get("dep_year_to")+"").equals("null")){
					stringBuffer.append(" and t.dep_year_sum<="+paramsMap.get("dep_year_to")+" ");
				}	
				
				if(paramsMap.get("lon_bal_from")!=null && !(paramsMap.get("lon_bal_from")+"").equals("") && !(paramsMap.get("lon_bal_from")+"").equals("null")){
					stringBuffer.append(" and t.lon_bal_sum >="+paramsMap.get("lon_bal_from")+" ");
				}
				if(paramsMap.get("lon_bal_to")!=null && !(paramsMap.get("lon_bal_to")+"").equals("") && !(paramsMap.get("lon_bal_to")+"").equals("null")){
					stringBuffer.append(" and t.lon_bal_sum <="+paramsMap.get("lon_bal_to")+" ");
				}
				if(paramsMap.get("lon_year_from")!=null && !(paramsMap.get("lon_year_from")+"").equals("") && (paramsMap.get("lon_year_from")+"").equals("null")){
					stringBuffer.append(" and t.lon_year_sum>="+paramsMap.get("lon_year_from")+" ");
				}
				if(paramsMap.get("lon_year_to")!=null && !(paramsMap.get("lon_year_to")+"").equals("") && (paramsMap.get("lon_year_to")+"").equals("null")){
					stringBuffer.append(" and t.lon_year_sum<="+paramsMap.get("lon_year_to")+" ");
				}
				
				if(paramsMap.get("dep_cy")!=null && !(paramsMap.get("dep_cy")+"").equals("") && !(paramsMap.get("dep_cy")+"").equals("null")){
					stringBuffer.append(" and t.dep_cm_sum >="+paramsMap.get("dep_cy")+" ");
				}
				if(paramsMap.get("dep_avg_cy")!=null && !(paramsMap.get("dep_avg_cy")+"").equals("") && !(paramsMap.get("dep_avg_cy")+"").equals("null")){
					stringBuffer.append(" and t.dep_avg_cm_sum >="+paramsMap.get("dep_avg_cy")+" ");
				}
				if(paramsMap.get("dep_qua_cy")!=null && !(paramsMap.get("dep_qua_cy")+"").equals("") && (paramsMap.get("dep_qua_cy")+"").equals("null")){
					stringBuffer.append(" and t.dep_qua_cm_sum >="+paramsMap.get("dep_qua_cy")+" ");
				}
				if(paramsMap.get("dep_year_cy")!=null && !(paramsMap.get("dep_year_cy")+"").equals("") && (paramsMap.get("dep_year_cy")+"").equals("null")){
					stringBuffer.append(" and t.dep_year_cm_sum>="+paramsMap.get("dep_year_cy")+" ");
				}		
				
				if(paramsMap.get("dep_year_cy_11")!=null && !(paramsMap.get("dep_year_cy_11")+"").equals("") && (paramsMap.get("dep_year_cy_11")+"").equals("null")){
					stringBuffer.append(" and t.dep_year_cm_sum_11>="+paramsMap.get("dep_year_cy_11")+" ");
				}
				
				if(paramsMap.get("lon_cy")!=null && !(paramsMap.get("lon_cy")+"").equals("") && !(paramsMap.get("lon_cy")+"").equals("null")){
					stringBuffer.append(" and t.lon_cm_sum >="+paramsMap.get("lon_cy")+" ");
				}
				if(paramsMap.get("lon_avg_cy")!=null && !(paramsMap.get("lon_avg_cy")+"").equals("") && !(paramsMap.get("lon_avg_cy")+"").equals("null")){
					stringBuffer.append(" and t.lon_avg_cm_sum >="+paramsMap.get("lon_avg_cy")+" ");
				}
				if(paramsMap.get("lon_qua_cy")!=null && !(paramsMap.get("lon_qua_cy")+"").equals("") && (paramsMap.get("lon_qua_cy")+"").equals("null")){
					stringBuffer.append(" and t.lon_qua_cm_sum >="+paramsMap.get("lon_qua_cy")+" ");
				}
				if(paramsMap.get("lon_year_cy")!=null && !(paramsMap.get("lon_year_cy")+"").equals("") && (paramsMap.get("lon_year_cy")+"").equals("null")){
					stringBuffer.append(" and t.lon_year_cm_sum>="+paramsMap.get("lon_year_cy")+" ");
				}				
				if(paramsMap.get("lon_year_cy_11")!=null && !(paramsMap.get("lon_year_cy_11")+"").equals("") && (paramsMap.get("lon_year_cy_11")+"").equals("null")){
					stringBuffer.append(" and t.lon_year_cm_sum_11>="+paramsMap.get("lon_year_cy_11")+" ");
				}
				
				
			}
			else if(caculate_type_value.equals("04"))//如果对比口径是较昨日
			{
				stringBuffer.append("select '较昨日'  caculate_type_desc,t.crm_dt,t.cust_zzdm,t.cust_id,t.sts,t.scope,t.crm_scope,t.cust_typ,t.cust_typ_desc,t.cust_big_lev,t.cust_small_lev,t.cust_lev, " 
						+ " t.cust_name,t.cust_crm_typ,t3."+level+"  instn_level_id,t4.unitname, "
						+ " sum(t.dep_bal_sum) dep_bal,sum(t.dep_avg_sum) dep_avg,sum(t.dep_qua_sum) dep_qua,sum(t.dep_year_sum) dep_year, " 
						+ " sum(t.lon_bal_sum) lon_bal,sum(t.lon_avg_sum) lon_avg,sum(t.lon_qua_sum) lon_qua,sum(t.lon_year_sum) lon_year, "
						+ " sum(t.dep_cd_sum)  dep_cy,sum(t.dep_avg_cd_sum)  dep_avg_cy,sum(t.dep_qua_cd_sum) dep_qua_cy ,sum(t.dep_year_cd_sum) dep_year_cy, sum(t.dep_year_cd_sum_11) dep_year_cy_11," 
						+ " sum(t.lon_cd_sum)  lon_cy,sum(t.lon_avg_cd_sum)  lon_avg_cy,sum(t.lon_qua_cd_sum) lon_qua_cy ,sum(t.lon_year_cd_sum) lon_year_cy, sum(t.lon_year_cd_sum_11) lon_year_cy_11 "
				);					
//				stringBuffer.append(" from "+ depTable +" left join fdm.acrm_f_sm_sys_units units on t.instn_no = units.system_unitid where (1=1) ");

				stringBuffer.append(" from "+ depTable +" inner join (select acrm_f_sm_sys_units_sta.unitid  from fdm.acrm_f_sm_sys_units_sta "+checkedOrg+") t2  on t.instn_no = t2.unitid " 
						+" inner join fdm.acrm_f_sm_sys_units_sta t3 on t3.level_4 = t.instn_no inner join fdm.acrm_f_sm_sys_units_sta t4 on t3."+level+" = t4.unitid  ");

				String clusterStr="";
				String rollStr=""; 
				if(paramsMap.get("cust_base_name")!=null && !(paramsMap.get("cust_base_name")+"").equals("") && !(paramsMap.get("cust_base_name")+"").equals("null")){
					 clusterStr = custCluster(paramsMap.get("cust_base_name")+"","ocrm_f_ci_relate_cust_base","cust_base_id");
				}if(paramsMap.get("roll_name")!=null && !(paramsMap.get("roll_name")+"").equals("") && !(paramsMap.get("roll_name")+"").equals("null")){
					 rollStr = custCluster(paramsMap.get("roll_name")+"","ocrm_f_mm_rcust_list","roll_id");
				}			
				
				if(clusterStr.equals("null") && rollStr.equals("null")){
					stringBuffer.append(" where  (0>1) ");
				}
				else if(clusterStr.equals("") && rollStr.equals("null")){
					stringBuffer.append(" where (0>1)");
				}
				else if(clusterStr.equals("null") && rollStr.equals("")){
					stringBuffer.append(" where (0>1)");					
				}else {
					stringBuffer.append(clusterStr);
					stringBuffer.append(rollStr);
				}
				
				
				if(paramsMap.get("dep_bal_from")!=null && !(paramsMap.get("dep_bal_from")+"").equals("") && !(paramsMap.get("dep_bal_from")+"").equals("null")){
					stringBuffer.append(" and t.dep_bal_sum >="+paramsMap.get("dep_bal_from")+" ");
				}
				if(paramsMap.get("dep_bal_to")!=null && !(paramsMap.get("dep_bal_to")+"").equals("") && !(paramsMap.get("dep_bal_to")+"").equals("null")){
					stringBuffer.append(" and t.dep_bal_sum <="+paramsMap.get("dep_bal_to")+" ");
				}
				if(paramsMap.get("dep_year_from")!=null && !(paramsMap.get("dep_year_from")+"").equals("") && (paramsMap.get("dep_year_from")+"").equals("null")){
					stringBuffer.append(" and t.dep_year_sum>="+paramsMap.get("dep_year_from")+" ");
				}
				if(paramsMap.get("dep_year_to")!=null && !(paramsMap.get("dep_year_to")+"").equals("") && (paramsMap.get("dep_year_to")+"").equals("null")){
					stringBuffer.append(" and t.dep_year_sum<="+paramsMap.get("dep_year_to")+" ");
				}	
				
				if(paramsMap.get("lon_bal_from")!=null && !(paramsMap.get("lon_bal_from")+"").equals("") && !(paramsMap.get("lon_bal_from")+"").equals("null")){
					stringBuffer.append(" and t.lon_bal_sum >="+paramsMap.get("lon_bal_from")+" ");
				}
				if(paramsMap.get("lon_bal_to")!=null && !(paramsMap.get("lon_bal_to")+"").equals("") && !(paramsMap.get("lon_bal_to")+"").equals("null")){
					stringBuffer.append(" and t.lon_bal_sum <="+paramsMap.get("lon_bal_to")+" ");
				}
				if(paramsMap.get("lon_year_from")!=null && !(paramsMap.get("lon_year_from")+"").equals("") && (paramsMap.get("lon_year_from")+"").equals("null")){
					stringBuffer.append(" and t.lon_year_sum>="+paramsMap.get("lon_year_from")+" ");
				}
				if(paramsMap.get("lon_year_to")!=null && !(paramsMap.get("lon_year_to")+"").equals("") && (paramsMap.get("lon_year_to")+"").equals("null")){
					stringBuffer.append(" and t.lon_year_sum<="+paramsMap.get("lon_year_to")+" ");
				}
				
				if(paramsMap.get("dep_cy")!=null && !(paramsMap.get("dep_cy")+"").equals("") && !(paramsMap.get("dep_cy")+"").equals("null")){
					stringBuffer.append(" and t.dep_cd_sum >="+paramsMap.get("dep_cy")+" ");
				}
				if(paramsMap.get("dep_avg_cy")!=null && !(paramsMap.get("dep_avg_cy")+"").equals("") && !(paramsMap.get("dep_avg_cy")+"").equals("null")){
					stringBuffer.append(" and t.dep_avg_cd_sum >="+paramsMap.get("dep_avg_cy")+" ");
				}
				if(paramsMap.get("dep_qua_cy")!=null && !(paramsMap.get("dep_qua_cy")+"").equals("") && (paramsMap.get("dep_qua_cy")+"").equals("null")){
					stringBuffer.append(" and t.dep_qua_cd_sum >="+paramsMap.get("dep_qua_cy")+" ");
				}
				if(paramsMap.get("dep_year_cy")!=null && !(paramsMap.get("dep_year_cy")+"").equals("") && (paramsMap.get("dep_year_cy")+"").equals("null")){
					stringBuffer.append(" and t.dep_year_cd_sum>="+paramsMap.get("dep_year_cy")+" ");
				}		
				
				if(paramsMap.get("dep_year_cy_11")!=null && !(paramsMap.get("dep_year_cy_11")+"").equals("") && (paramsMap.get("dep_year_cy_11")+"").equals("null")){
					stringBuffer.append(" and t.dep_year_cd_sum_11>="+paramsMap.get("dep_year_cy_11")+" ");
				}
				
				if(paramsMap.get("lon_cy")!=null && !(paramsMap.get("lon_cy")+"").equals("") && !(paramsMap.get("lon_cy")+"").equals("null")){
					stringBuffer.append(" and t.lon_cd_sum >="+paramsMap.get("lon_cy")+" ");
				}
				if(paramsMap.get("lon_avg_cy")!=null && !(paramsMap.get("lon_avg_cy")+"").equals("") && !(paramsMap.get("lon_avg_cy")+"").equals("null")){
					stringBuffer.append(" and t.lon_avg_cd_sum >="+paramsMap.get("lon_avg_cy")+" ");
				}
				if(paramsMap.get("lon_qua_cy")!=null && !(paramsMap.get("lon_qua_cy")+"").equals("") && (paramsMap.get("lon_qua_cy")+"").equals("null")){
					stringBuffer.append(" and t.lon_qua_cd_sum >="+paramsMap.get("lon_qua_cy")+" ");
				}
				if(paramsMap.get("lon_year_cy")!=null && !(paramsMap.get("lon_year_cy")+"").equals("") && (paramsMap.get("lon_year_cy")+"").equals("null")){
					stringBuffer.append(" and t.lon_year_cd_sum>="+paramsMap.get("lon_year_cy")+" ");
				}				
				if(paramsMap.get("lon_year_cy_11")!=null && !(paramsMap.get("lon_year_cy_11")+"").equals("") && (paramsMap.get("lon_year_cy_11")+"").equals("null")){
					stringBuffer.append(" and t.lon_year_cd_sum_11>="+paramsMap.get("lon_year_cy_11")+" ");
				}
				
			}			
			
		}
		
		
		Iterator iterator = paramsMap.keySet().iterator();
		
		while(iterator.hasNext()){
			
			String key = (String)iterator.next();
			
			if(key.equals("cur_type") ||  key.equals("caculate_type")|| key.equals("dep_bal_from") || key.equals("dep_bal_to") 
					||	key.equals("dep_year_from") || key.equals("dep_year_to") || key.equals("lon_bal_from")|| key.equals("lon_bal_to")
					||  key.equals("lon_year_from") || key.equals("lon_year_to") || key.equals("dep_cy") || key.equals("dep_avg_cy")
					||  key.equals("dep_qua_cy") || key.equals("dep_year_cy") || key.equals("dep_year_cy_11")
					||  key.equals("lon_cy") || key.equals("lon_avg_cy") || key.equals("lon_qua_cy") || key.equals("lon_year_cy")
					||  key.equals("lon_year_cy_11")|| key.equals("instn_level") || key.equals("checkedNodes") || key.equals("instn_no")
					||  key.equals("level")||key.equals("roll_name") || key.equals("cust_base_name") )
			{
				continue;
			}	
			
			String keyValue =(String) paramsMap.get(key);			
			if(keyValue!= null && !keyValue.equals("") && !keyValue.equals("null")){
				if(key.equals("crm_dt")){
					stringBuffer.append(" and crm_dt = to_date('"+keyValue+"','YYYY-MM-DD') ");
				}
				else if(key.equals("cust_name")){
					stringBuffer.append(" and cust_name like '%"+keyValue+"%' ");
				}
				else{
					stringBuffer.append(" and " + key+" = '"+keyValue + "' ");
				}
			}
		}	
		
			AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();        
			String userId = auth.getUserId();
			String role = "";
			for(GrantedAuthority ga : auth.getAuthorities() ){
			    role = ga.getAuthority()+"";
			    if(role.equals("2")){
			    	role ="2";
			    	stringBuffer.append("  where t.cust_id in ( select cust_id from fdm.v_acrm_f_relacust where user_id ='"+userId+"' )");
			    	break;
			    }			   
			}
			
			String groupStr = " group by  (caculate_type_desc,t.crm_dt,t.cust_zzdm,t.cust_id,t.sts,t.scope,t.crm_scope,t.cust_typ,t.cust_typ_desc,t.cust_big_lev,t.cust_small_lev,t.cust_lev, " 
						+ " t.cust_name,t.cust_crm_typ,t3."+level+",t4.unitname )";
		
			
			setGroupByFields(groupStr);
			
			//system.out.printlnln( " queryString : "+stringBuffer.toString());
			
			
			setPrimaryKey("t.cust_zzdm");
//			addGreenplumLookup("sts", "KHZT");
//			addGreenplumLookup("scope", "QYGM");
//			addGreenplumLookup("crm_scope", "KHQYGM");
//			addGreenplumLookup("cust_typ", "HYFL");
//			addGreenplumLookup("cust_typ_desc", "HYMXFL");
//			addGreenplumLookup("cust_big_lev", "CUST_LEVEL1");
//			addGreenplumLookup("cust_small_lev", "CUST_LEVEL2");
			SQL = stringBuffer.toString();
			datasource = ds;
	}
	
	public String buildCheckedNodes(String checkedNodes){
		
		String[] checkedOrg = checkedNodes.split(",");
		StringBuffer stringBuffer = new StringBuffer(" inner join ( ");
		for(int i=0;i<checkedOrg.length;i++)
		{
			stringBuffer.append("select "+checkedOrg[i]+" unitid union ");
		}
		int lastUnion = stringBuffer.lastIndexOf("union");
		stringBuffer = stringBuffer.replace(lastUnion, stringBuffer.length(), " ");
		stringBuffer.append("  )  t7 on acrm_f_sm_sys_units_sta.unitid = t7.unitid ");
		return stringBuffer.toString();
	}	
	
	@SuppressWarnings("rawtypes")
	public String custCluster(String id,String table,String base_id){
		
		StringBuffer stringBuffer = new StringBuffer("select "+table+".cust_id  from "+table+" where "+table+"."+base_id+" = "+id+"");
		
		ClientDepAndLonService clusterService = new ClientDepAndLonService();
		
		Map clusterMap = clusterService.clientDepAndLonList(stringBuffer.toString());
		
		List clusterList = (List)clusterMap.get("data");
		if(clusterList==null || clusterList.size() ==0){
			return "null";
		}
		stringBuffer = new StringBuffer(" inner join  ( ");
		for(int i=0;i<clusterList.size();i++){
			Map map = (Map)clusterList.get(i);
			String cust_id = map.get("CUST_ID")+"";
			stringBuffer.append("select '"+cust_id+"' cust_id union ");
		}
		int lastUnion = stringBuffer.lastIndexOf("union");
		
		stringBuffer = stringBuffer.replace(lastUnion, stringBuffer.length(), " ");		
		
		stringBuffer.append(" ) "+table+" on t.cust_id = "+table+".cust_id  ");
		
		return stringBuffer.toString();
		
	}


	
	@SuppressWarnings("rawtypes")
	public String custRoll(String id){
		
		StringBuffer stringBuffer = new StringBuffer("");
		
		stringBuffer.append("select t4.zzdm from ocrm_f_mm_rcust_list t4 where t4.roll_id = "+id+"");
		
		ClientDepAndLonService rollService = new ClientDepAndLonService();
		
		Map rollMap = rollService.clientDepAndLonList(stringBuffer.toString());
		
		List rollList = (List)rollMap.get("data");
		
		if(rollList==null || rollList.size()==0){
			return "null";
		}		
		stringBuffer = new StringBuffer(" inner join ( ");
		for(int i=0;i<rollList.size();i++){
			Map map = (Map)rollList.get(i);
			String cust_zzdm = map.get("ZZDM")+"";
			stringBuffer.append("select '"+cust_zzdm+"' cust_zzdm union ");
		}
		int lastUnion = stringBuffer.lastIndexOf("union");
		
		stringBuffer = stringBuffer.replace(lastUnion, stringBuffer.length(), " ");		
		
		stringBuffer.append(" ) t6 on t.cust_zzdm = t6.cust_zzdm ");
		
		return stringBuffer.toString();
		
	}
	
	public Map<String, Object> getJson() {
        return super.getJson();
    }
}
