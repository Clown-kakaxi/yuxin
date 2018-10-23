/**
 * 
 */
package com.yuchengtech.bob.action;

import java.util.Iterator;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bob.core.PagingInfo;
import com.yuchengtech.bob.core.QueryHelper;

/**
 * @author yaoliang
 *
 */
@ParentPackage("json-default")

@Action(value="/dep-and-lon-detail", results={
    @Result(name="success", type="json")
})
public class DepAndLonDetailAction extends BaseAction{

	private Map depAndLonDetailMap;
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;	
	public String index() throws Exception{
		StringBuffer stringBuffer = new StringBuffer();
		
		StringBuffer sb = new StringBuffer("select * from mdm.acrm_m_ci_lon_dep_desc");
		Map paramsMap = this.getJson();
		String curr_type_value =(String) paramsMap.get("cur_type");
		String caculate_type_value = (String)paramsMap.get("caculate_type");		
		if(curr_type_value ==null || curr_type_value.equals("") || curr_type_value.equals("null")){
			curr_type_value = "01";//默认币种类型是本币
		}
		if(caculate_type_value == null || caculate_type_value.equals("")|| caculate_type_value.equals("null")){
			caculate_type_value = "01";//默认统计口径是时点
		}
		if(curr_type_value.equals("02")){//如果是本币查询
			if(caculate_type_value.equals("01"))//如果统计口径是时点
			{
				stringBuffer.append("select t.instn_no,t.cust_zzdm,t.sts,t.scope,t.crm_scope,t.cust_typ,t.cust_typ_desc,t.cust_big_lev,t.cust_small_lev,t.cust_lev, " 
						+ " t.cust_name,t.cust_crm_typ," 
						+ " t.dep_bal1,t.dep_bal2,t.dep_bal3,t.dep_bal4,t.dep_bal5,t.dep_bal6,t.dep_bal7,t.dep_bal,"
						+ " t.lon_bal1,t.lon_bal2,t.lon_bal3,t.lon_bal4,t.lon_bal5,t.lon_bal6,t.lon_bal,units.system_unitname instn_name,units.system_levelunit instn_level " 
						
				);
				
				stringBuffer.append(" from mdm.acrm_m_ci_lon_dep_desc t left join fdm.acrm_f_sm_sys_units units on t.instn_no = units.system_unitid where (1=1) ");
				
				if( paramsMap.get("dep_bal_from")!=null && !paramsMap.get("dep_bal_from").equals("") && !paramsMap.get("dep_bal_from").equals("null")){
						stringBuffer.append("  and t.dep_bal>="+paramsMap.get("dep_bal_from")+"  ");
				}
				if( paramsMap.get("dep_bal_to")!=null && !paramsMap.get("dep_bal_to").equals("") && !paramsMap.get("dep_bal_to").equals("null")){
						stringBuffer.append("  and t.dep_bal<="+paramsMap.get("dep_bal_to") +"  ");
				}

				if( paramsMap.get("dep_year_from")!=null && !paramsMap.get("dep_year_from").equals("") && !paramsMap.get("dep_year_from").equals("null")){
					stringBuffer.append("  and t.dep_year>="+paramsMap.get("dep_year_from")+"  ");
				}
				if( paramsMap.get("dep_year_to")!=null && !paramsMap.get("dep_year_to").equals("") && !paramsMap.get("dep_year_to").equals("null")){
						stringBuffer.append("  and t.dep_year<="+paramsMap.get("dep_year_to") +"  ");
				}		
				
				if( paramsMap.get("lon_bal_from")!=null && !paramsMap.get("lon_bal_from").equals("") && !paramsMap.get("lon_bal_from").equals("null")){
					stringBuffer.append("  and t.lon_bal>="+paramsMap.get("lon_bal_from")+"  ");
				}
				if( paramsMap.get("lon_bal_to")!=null && !paramsMap.get("lon_bal_to").equals("") && !paramsMap.get("lon_bal_to").equals("null")){
						stringBuffer.append("  and t.lon_bal<="+paramsMap.get("lon_bal_to") +"  ");
				}
	
				if( paramsMap.get("lon_year_from")!=null && !paramsMap.get("lon_year_from").equals("") && !paramsMap.get("lon_year_from").equals("null")){
					stringBuffer.append("  and t.lon_year>="+paramsMap.get("lon_year_from")+"  ");
				}
				if( paramsMap.get("lon_year_to")!=null && !paramsMap.get("lon_year_to").equals("") && !paramsMap.get("lon_year_to").equals("null")){
						stringBuffer.append("  and t.lon_year<="+paramsMap.get("lon_year_to") +"  ");
				}				
					
			}
			else if(caculate_type_value.equals("02"))//如果统计口径是月均
			{
				stringBuffer.append("select t.instn_no,t.cust_zzdm,t.sts,t.scope,t.crm_scope,t.cust_typ,t.cust_typ_desc,t.cust_big_lev,t.cust_small_lev,t.cust_lev, " 
						+ " t.cust_name,t.cust_crm_typ," 
						+ " t.dep_mon1 dep_bal1,t.dep_mon2 dep_bal2,t.dep_mon3 dep_bal3,t.dep_mon4 dep_bal4,t.dep_mon5 dep_bal5,t.dep_mon6 dep_bal6,t.dep_mon7 dep_bal7,t.dep_mon dep_bal,"
						+ " t.lon_mon1 lon_bal1,t.lon_mon2 lon_bal2,t.lon_mon3 lon_bal3,t.lon_mon4 lon_bal4,t.lon_mon5 lon_bal5,t.lon_mon6 lon_bal6,t.lon_mon lon_bal,units.system_unitname instn_name,units.system_levelunit instn_level" 
						
				);	

				stringBuffer.append(" from mdm.acrm_m_ci_lon_dep_desc t left join fdm.acrm_f_sm_sys_units units on t.instn_no = units.system_unitid where (1=1) ");
				
				if( paramsMap.get("dep_bal_from")!=null && !paramsMap.get("dep_bal_from").equals("") && !paramsMap.get("dep_bal_from").equals("null")){
						stringBuffer.append("  and t.dep_bal>="+paramsMap.get("dep_bal_from")+"  ");
				}
				if( paramsMap.get("dep_bal_to")!=null && !paramsMap.get("dep_bal_to").equals("") && !paramsMap.get("dep_bal_to").equals("null")){
						stringBuffer.append("  and t.dep_bal<="+paramsMap.get("dep_bal_to") +"  ");
				}

				if( paramsMap.get("dep_year_from")!=null && !paramsMap.get("dep_year_from").equals("") && !paramsMap.get("dep_year_from").equals("null")){
					stringBuffer.append("  and t.dep_year>="+paramsMap.get("dep_year_from")+"  ");
				}
				if( paramsMap.get("dep_year_to")!=null && !paramsMap.get("dep_year_to").equals("") && !paramsMap.get("dep_year_to").equals("null")){
						stringBuffer.append("  and t.dep_year<="+paramsMap.get("dep_year_to") +"  ");
				}		
				
				if( paramsMap.get("lon_bal_from")!=null && !paramsMap.get("lon_bal_from").equals("") && !paramsMap.get("lon_bal_from").equals("null")){
					stringBuffer.append("  and t.lon_bal>="+paramsMap.get("lon_bal_from")+"  ");
				}
				if( paramsMap.get("lon_bal_to")!=null && !paramsMap.get("lon_bal_to").equals("") && !paramsMap.get("lon_bal_to").equals("null")){
						stringBuffer.append("  and t.lon_bal<="+paramsMap.get("lon_bal_to") +"  ");
				}
	
				if( paramsMap.get("lon_year_from")!=null && !paramsMap.get("lon_year_from").equals("") && !paramsMap.get("lon_year_from").equals("null")){
					stringBuffer.append("  and t.lon_year>="+paramsMap.get("lon_year_from")+"  ");
				}
				if( paramsMap.get("lon_year_to")!=null && !paramsMap.get("lon_year_to").equals("") && !paramsMap.get("lon_year_to").equals("null")){
						stringBuffer.append("  and t.lon_year<="+paramsMap.get("lon_year_to") +"  ");
				}				
				
				
			}
			else if(caculate_type_value.equals("03"))//如果统计口径是季军
			{
				stringBuffer.append("select t.instn_no,t.cust_zzdm,t.sts,t.scope,t.crm_scope,t.cust_typ,t.cust_typ_desc,t.cust_big_lev,t.cust_small_lev,t.cust_lev, " 
						+ " t.cust_name,t.cust_crm_typ," 
						+ " t.dep_quar1 dep_bal1,t.dep_quar2 dep_bal2,t.dep_quar3 dep_bal3,t.dep_quar4 dep_bal4,t.dep_quar5 dep_bal5,t.dep_quar6 dep_bal6,t.dep_quar7 dep_bal7,t.dep_quar dep_bal,"
						+ " t.lon_quar1 lon_bal1,t.lon_quar2 lon_bal2,t.lon_quar3 lon_bal3,t.lon_quar4 lon_bal4,t.lon_quar5 lon_bal5,t.lon_quar6 lon_bal6,t.lon_quar lon_bal,units.system_unitname instn_name,units.system_levelunit instn_level" 
						
				);
				stringBuffer.append(" from mdm.acrm_m_ci_lon_dep_desc t left join fdm.acrm_f_sm_sys_units units on t.instn_no = units.system_unitid where (1=1) ");
				
				if( paramsMap.get("dep_bal_from")!=null && !paramsMap.get("dep_bal_from").equals("") && !paramsMap.get("dep_bal_from").equals("null")){
						stringBuffer.append("  and t.dep_bal>="+paramsMap.get("dep_bal_from")+"  ");
				}
				if( paramsMap.get("dep_bal_to")!=null && !paramsMap.get("dep_bal_to").equals("") && !paramsMap.get("dep_bal_to").equals("null")){
						stringBuffer.append("  and t.dep_bal<="+paramsMap.get("dep_bal_to") +"  ");
				}

				if( paramsMap.get("dep_year_from")!=null && !paramsMap.get("dep_year_from").equals("") && !paramsMap.get("dep_year_from").equals("null")){
					stringBuffer.append("  and t.dep_year>="+paramsMap.get("dep_year_from")+"  ");
				}
				if( paramsMap.get("dep_year_to")!=null && !paramsMap.get("dep_year_to").equals("") && !paramsMap.get("dep_year_to").equals("null")){
						stringBuffer.append("  and t.dep_year<="+paramsMap.get("dep_year_to") +"  ");
				}		
				
				if( paramsMap.get("lon_bal_from")!=null && !paramsMap.get("lon_bal_from").equals("") && !paramsMap.get("lon_bal_from").equals("null")){
					stringBuffer.append("  and t.lon_bal>="+paramsMap.get("lon_bal_from")+"  ");
				}
				if( paramsMap.get("lon_bal_to")!=null && !paramsMap.get("lon_bal_to").equals("") && !paramsMap.get("lon_bal_to").equals("null")){
						stringBuffer.append("  and t.lon_bal<="+paramsMap.get("lon_bal_to") +"  ");
				}
	
				if( paramsMap.get("lon_year_from")!=null && !paramsMap.get("lon_year_from").equals("") && !paramsMap.get("lon_year_from").equals("null")){
					stringBuffer.append("  and t.lon_year>="+paramsMap.get("lon_year_from")+"  ");
				}
				if( paramsMap.get("lon_year_to")!=null && !paramsMap.get("lon_year_to").equals("") && !paramsMap.get("lon_year_to").equals("null")){
						stringBuffer.append("  and t.lon_year<="+paramsMap.get("lon_year_to") +"  ");
				}				
				
			}
			else if(caculate_type_value.equals("04"))//如果统计口径是年均
			{
				stringBuffer.append("select t.instn_no,t.cust_zzdm,t.sts,t.scope,t.crm_scope,t.cust_typ,t.cust_typ_desc,t.cust_big_lev,t.cust_small_lev,t.cust_lev, " 
						+ " t.cust_name,t.cust_crm_typ," 
						+ " t.dep_year1 dep_bal1,t.dep_year2 dep_bal2,t.dep_year3 dep_bal3,t.dep_year4 dep_bal4,t.dep_year5 dep_bal5,t.dep_year6 dep_bal6,t.dep_year7 dep_bal7,t.dep_year dep_bal,"
						+ " t.lon_year1 lon_bal1,t.lon_year2 lon_bal2,t.lon_year3 lon_bal3,t.lon_year4 lon_bal4,t.lon_year5 lon_bal5,t.lon_year6 lon_bal6,t.lon_year lon_bal,units.system_unitname instn_name,units.system_levelunit instn_level " 
						
				);	
				stringBuffer.append(" from mdm.acrm_m_ci_lon_dep_desc t  left join fdm.acrm_f_sm_sys_units units on t.instn_no = units.system_unitid where (1=1) ");
				
				if( paramsMap.get("dep_bal_from")!=null && !paramsMap.get("dep_bal_from").equals("") && !paramsMap.get("dep_bal_from").equals("null")){
						stringBuffer.append("  and t.dep_bal>="+paramsMap.get("dep_bal_from")+"  ");
				}
				if( paramsMap.get("dep_bal_to")!=null && !paramsMap.get("dep_bal_to").equals("") && !paramsMap.get("dep_bal_to").equals("null")){
						stringBuffer.append("  and t.dep_bal<="+paramsMap.get("dep_bal_to") +"  ");
				}

				if( paramsMap.get("dep_year_from")!=null && !paramsMap.get("dep_year_from").equals("") && !paramsMap.get("dep_year_from").equals("null")){
					stringBuffer.append("  and t.dep_year>="+paramsMap.get("dep_year_from")+"  ");
				}
				if( paramsMap.get("dep_year_to")!=null && !paramsMap.get("dep_year_to").equals("") && !paramsMap.get("dep_year_to").equals("null")){
						stringBuffer.append("  and t.dep_year<="+paramsMap.get("dep_year_to") +"  ");
				}		
				
				if( paramsMap.get("lon_bal_from")!=null && !paramsMap.get("lon_bal_from").equals("") && !paramsMap.get("lon_bal_from").equals("null")){
					stringBuffer.append("  and t.lon_bal>="+paramsMap.get("lon_bal_from")+"  ");
				}
				if( paramsMap.get("lon_bal_to")!=null && !paramsMap.get("lon_bal_to").equals("") && !paramsMap.get("lon_bal_to").equals("null")){
						stringBuffer.append("  and t.lon_bal<="+paramsMap.get("lon_bal_to") +"  ");
				}
	
				if( paramsMap.get("lon_year_from")!=null && !paramsMap.get("lon_year_from").equals("") && !paramsMap.get("lon_year_from").equals("null")){
					stringBuffer.append("  and t.lon_year>="+paramsMap.get("lon_year_from")+"  ");
				}
				if( paramsMap.get("lon_year_to")!=null && !paramsMap.get("lon_year_to").equals("") && !paramsMap.get("lon_year_to").equals("null")){
						stringBuffer.append("  and t.lon_year<="+paramsMap.get("lon_year_to") +"  ");
				}				
				
			}			
		}
		else if(curr_type_value.equals("01"))//如果币种是人民币
		{
			if(caculate_type_value.equals("01"))//如果统计口径是时点
			{
				stringBuffer.append("select t.instn_no,t.cust_zzdm,t.sts,t.scope,t.crm_scope,t.cust_typ,t.cust_typ_desc,t.cust_big_lev,t.cust_small_lev,t.cust_lev, " 
						+ " t.cust_name,t.cust_crm_typ," 
						+ " t.dep_bal1_cny dep_bal1,t.dep_bal2_cny dep_bal2,t.dep_bal3_cny dep_bal3,t.dep_bal4_cny dep_bal4,t.dep_bal5_cny dep_bal5,t.dep_bal6_cny dep_bal6,t.dep_bal7_cny dep_bal7,t.dep_bal_sum_cny dep_bal,"
						+ " t.lon_bal1_cny lon_bal1,t.lon_bal2_cny lon_bal2,t.lon_bal3_cny lon_bal3,t.lon_bal4_cny lon_bal4,t.lon_bal5_cny lon_bal5,t.lon_bal6_cny lon_bal6,t.lon_bal_sum_cny lon_bal,units.system_unitname instn_name,units.system_levelunit instn_level " 
						
				);	
				
				
				stringBuffer.append(" from mdm.acrm_m_ci_lon_dep_desc t left join fdm.acrm_f_sm_sys_units units on t.instn_no = units.system_unitid where (1=1) ");
				
				if( paramsMap.get("dep_bal_from")!=null && !paramsMap.get("dep_bal_from").equals("") && !paramsMap.get("dep_bal_from").equals("null")){
						stringBuffer.append("  and t.dep_bal_sum_cny>="+paramsMap.get("dep_bal_from")+"  ");
				}
				if( paramsMap.get("dep_bal_to")!=null && !paramsMap.get("dep_bal_to").equals("") && !paramsMap.get("dep_bal_to").equals("null")){
						stringBuffer.append("  and t.dep_bal_sum_cny<="+paramsMap.get("dep_bal_to") +"  ");
				}

				if( paramsMap.get("dep_year_from")!=null && !paramsMap.get("dep_year_from").equals("") && !paramsMap.get("dep_year_from").equals("null")){
					stringBuffer.append("  and t.dep_year_sum_cny>="+paramsMap.get("dep_year_from")+"  ");
				}
				if( paramsMap.get("dep_year_to")!=null && !paramsMap.get("dep_year_to").equals("") && !paramsMap.get("dep_year_to").equals("null")){
						stringBuffer.append("  and t.dep_year_sum_cny<="+paramsMap.get("dep_year_to") +"  ");
				}		
				
				if( paramsMap.get("lon_bal_from")!=null && !paramsMap.get("lon_bal_from").equals("") && !paramsMap.get("lon_bal_from").equals("null")){
					stringBuffer.append("  and t.lon_bal_sum_cny>="+paramsMap.get("lon_bal_from")+"  ");
				}
				if( paramsMap.get("lon_bal_to")!=null && !paramsMap.get("lon_bal_to").equals("") && !paramsMap.get("lon_bal_to").equals("null")){
						stringBuffer.append("  and t.lon_bal_sum_cny<="+paramsMap.get("lon_bal_to") +"  ");
				}
	
				if( paramsMap.get("lon_year_from")!=null && !paramsMap.get("lon_year_from").equals("") && !paramsMap.get("lon_year_from").equals("null")){
					stringBuffer.append("  and t.lon_year_sum_cny>="+paramsMap.get("lon_year_from")+"  ");
				}
				if( paramsMap.get("lon_year_to")!=null && !paramsMap.get("lon_year_to").equals("") && !paramsMap.get("lon_year_to").equals("null")){
						stringBuffer.append("  and t.lon_year_sum_cny<="+paramsMap.get("lon_year_to") +"  ");
				}				
				
			}
			if(caculate_type_value.equals("02"))//如果统计口径是月均
			{
				stringBuffer.append("select t.instn_no,t.cust_zzdm,t.sts,t.scope,t.crm_scope,t.cust_typ,t.cust_typ_desc,t.cust_big_lev,t.cust_small_lev,t.cust_lev, " 
						+ " t.cust_name,t.cust_crm_typ," 
						+ " t.dep_mon1_cny dep_bal1,t.dep_mon2_cny dep_bal2,t.dep_mon3_cny dep_bal3,t.dep_mon4_cny  dep_bal4,t.dep_mon5_cny dep_bal5,t.dep_mon6_cny dep_bal6,t.dep_mon7_cny dep_bal7,t.dep_mon_sum_cny dep_bal,"
						+ " t.lon_mon1_cny lon_bal1,t.lon_mon2_cny lon_bal2,t.lon_mon3_cny lon_bal3,t.lon_mon4_cny  lon_bal4,t.lon_mon5_cny lon_bal5,t.lon_mon6_cny lon_bal6,t.lon_mon_sum_cny lon_bal, units.system_unitname instn_name,units.system_levelunit instn_level " 
						
				);				
				
				stringBuffer.append(" from mdm.acrm_m_ci_lon_dep_desc t  left join fdm.acrm_f_sm_sys_units units on t.instn_no = units.system_unitid where (1=1) ");
				
				if( paramsMap.get("dep_bal_from")!=null && !paramsMap.get("dep_bal_from").equals("") && !paramsMap.get("dep_bal_from").equals("null")){
						stringBuffer.append("  and t.dep_bal_sum_cny>="+paramsMap.get("dep_bal_from")+"  ");
				}
				if( paramsMap.get("dep_bal_to")!=null && !paramsMap.get("dep_bal_to").equals("") && !paramsMap.get("dep_bal_to").equals("null")){
						stringBuffer.append("  and t.dep_bal_sum_cny<="+paramsMap.get("dep_bal_to") +"  ");
				}

				if( paramsMap.get("dep_year_from")!=null && !paramsMap.get("dep_year_from").equals("") && !paramsMap.get("dep_year_from").equals("null")){
					stringBuffer.append("  and t.dep_year_sum_cny>="+paramsMap.get("dep_year_from")+"  ");
				}
				if( paramsMap.get("dep_year_to")!=null && !paramsMap.get("dep_year_to").equals("") && !paramsMap.get("dep_year_to").equals("null")){
						stringBuffer.append("  and t.dep_year_sum_cny<="+paramsMap.get("dep_year_to") +"  ");
				}		
				
				if( paramsMap.get("lon_bal_from")!=null && !paramsMap.get("lon_bal_from").equals("") && !paramsMap.get("lon_bal_from").equals("null")){
					stringBuffer.append("  and t.lon_bal_sum_cny>="+paramsMap.get("lon_bal_from")+"  ");
				}
				if( paramsMap.get("lon_bal_to")!=null && !paramsMap.get("lon_bal_to").equals("") && !paramsMap.get("lon_bal_to").equals("null")){
						stringBuffer.append("  and t.lon_bal_sum_cny<="+paramsMap.get("lon_bal_to") +"  ");
				}
	
				if( paramsMap.get("lon_year_from")!=null && !paramsMap.get("lon_year_from").equals("") && !paramsMap.get("lon_year_from").equals("null")){
					stringBuffer.append("  and t.lon_year_sum_cny>="+paramsMap.get("lon_year_from")+"  ");
				}
				if( paramsMap.get("lon_year_to")!=null && !paramsMap.get("lon_year_to").equals("") && !paramsMap.get("lon_year_to").equals("null")){
						stringBuffer.append("  and t.lon_year_sum_cny<="+paramsMap.get("lon_year_to") +"  ");
				}				
				
			}
			if(caculate_type_value.equals("03"))//如果统计口径是季均
			{
				stringBuffer.append("select t.instn_no,t.cust_zzdm,t.sts,t.scope,t.crm_scope,t.cust_typ,t.cust_typ_desc,t.cust_big_lev,t.cust_small_lev,t.cust_lev, " 
						+ " t.cust_name,t.cust_crm_typ," 
						+ " t.dep_quar1_cny dep_bal1,t.dep_quar2_cny dep_bal2,t.dep_quar3_cny dep_bal3,t.dep_quar4_cny dep_bal4,t.dep_quar5_cny dep_bal5,t.dep_quar6_cny dep_bal6,t.dep_quar7_cny dep_bal7,t.dep_quar_sum_cny dep_bal,"
						+ " t.lon_quar1_cny lon_bal1,t.lon_quar2_cny lon_bal2,t.lon_quar3_cny lon_bal3,t.lon_quar4_cny lon_bal4,t.lon_quar5_cny lon_bal5,t.lon_quar6_cny lon_bal6,t.lon_quar_sum_cny lon_bal,units.system_unitname instn_name,units.system_levelunit instn_level " 
						
				);					
				
				stringBuffer.append(" from mdm.acrm_m_ci_lon_dep_desc t left join fdm.acrm_f_sm_sys_units units on t.instn_no = units.system_unitid where (1=1) ");
				
				if( paramsMap.get("dep_bal_from")!=null && !paramsMap.get("dep_bal_from").equals("") && !paramsMap.get("dep_bal_from").equals("null")){
						stringBuffer.append("  and t.dep_bal_sum_cny>="+paramsMap.get("dep_bal_from")+"  ");
				}
				if( paramsMap.get("dep_bal_to")!=null && !paramsMap.get("dep_bal_to").equals("") && !paramsMap.get("dep_bal_to").equals("null")){
						stringBuffer.append("  and t.dep_bal_sum_cny<="+paramsMap.get("dep_bal_to") +"  ");
				}

				if( paramsMap.get("dep_year_from")!=null && !paramsMap.get("dep_year_from").equals("") && !paramsMap.get("dep_year_from").equals("null")){
					stringBuffer.append("  and t.dep_year_sum_cny>="+paramsMap.get("dep_year_from")+"  ");
				}
				if( paramsMap.get("dep_year_to")!=null && !paramsMap.get("dep_year_to").equals("") && !paramsMap.get("dep_year_to").equals("null")){
						stringBuffer.append("  and t.dep_year_sum_cny<="+paramsMap.get("dep_year_to") +"  ");
				}		
				
				if( paramsMap.get("lon_bal_from")!=null && !paramsMap.get("lon_bal_from").equals("") && !paramsMap.get("lon_bal_from").equals("null")){
					stringBuffer.append("  and t.lon_bal_sum_cny>="+paramsMap.get("lon_bal_from")+"  ");
				}
				if( paramsMap.get("lon_bal_to")!=null && !paramsMap.get("lon_bal_to").equals("") && !paramsMap.get("lon_bal_to").equals("null")){
						stringBuffer.append("  and t.lon_bal_sum_cny<="+paramsMap.get("lon_bal_to") +"  ");
				}
	
				if( paramsMap.get("lon_year_from")!=null && !paramsMap.get("lon_year_from").equals("") && !paramsMap.get("lon_year_from").equals("null")){
					stringBuffer.append("  and t.lon_year_sum_cny>="+paramsMap.get("lon_year_from")+"  ");
				}
				if( paramsMap.get("lon_year_to")!=null && !paramsMap.get("lon_year_to").equals("") && !paramsMap.get("lon_year_to").equals("null")){
						stringBuffer.append("  and t.lon_year_sum_cny<="+paramsMap.get("lon_year_to") +"  ");
				}				
				
			}
			if(caculate_type_value.equals("04"))//如果统计口径是年均
			{
				stringBuffer.append("select t.instn_no,t.cust_zzdm,t.sts,t.scope,t.crm_scope,t.cust_typ,t.cust_typ_desc,t.cust_big_lev,t.cust_small_lev,t.cust_lev, " 
						+ " t.cust_name,t.cust_crm_typ," 
						+ " t.dep_year1_cny dep_bal1,t.dep_year2_cny dep_bal2,t.dep_year3_cny dep_bal3,t.dep_year4_cny dep_bal4,t.dep_year5_cny dep_bal5,t.dep_year6_cny dep_bal6,t.dep_year7_cny dep_bal7,t.dep_year_sum_cny dep_bal,"
						+ " t.lon_year1_cny lon_bal1,t.lon_year2_cny lon_bal2,t.lon_year3_cny lon_bal3,t.lon_year4_cny lon_bal4,t.lon_year5_cny lon_bal5,t.lon_year6_cny lon_bal6,t.lon_year_sum_cny lon_bal,units.system_unitname instn_name,units.system_levelunit instn_level " 
						
				);					
				
				stringBuffer.append(" from mdm.acrm_m_ci_lon_dep_desc t  left join fdm.acrm_f_sm_sys_units units on t.instn_no = units.system_unitid where (1=1) ");
				
				if( paramsMap.get("dep_bal_from")!=null && !paramsMap.get("dep_bal_from").equals("") && !paramsMap.get("dep_bal_from").equals("null")){
						stringBuffer.append("  and t.dep_bal_sum_cny>="+paramsMap.get("dep_bal_from")+"  ");
				}
				if( paramsMap.get("dep_bal_to")!=null && !paramsMap.get("dep_bal_to").equals("") && !paramsMap.get("dep_bal_to").equals("null")){
						stringBuffer.append("  and t.dep_bal_sum_cny<="+paramsMap.get("dep_bal_to") +"  ");
				}

				if( paramsMap.get("dep_year_from")!=null && !paramsMap.get("dep_year_from").equals("") && !paramsMap.get("dep_year_from").equals("null")){
					stringBuffer.append("  and t.dep_year_sum_cny>="+paramsMap.get("dep_year_from")+"  ");
				}
				if( paramsMap.get("dep_year_to")!=null && !paramsMap.get("dep_year_to").equals("") && !paramsMap.get("dep_year_to").equals("null")){
						stringBuffer.append("  and t.dep_year_sum_cny<="+paramsMap.get("dep_year_to") +"  ");
				}		
				
				if( paramsMap.get("lon_bal_from")!=null && !paramsMap.get("lon_bal_from").equals("") && !paramsMap.get("lon_bal_from").equals("null")){
					stringBuffer.append("  and t.lon_bal_sum_cny>="+paramsMap.get("lon_bal_from")+"  ");
				}
				if( paramsMap.get("lon_bal_to")!=null && !paramsMap.get("lon_bal_to").equals("") && !paramsMap.get("lon_bal_to").equals("null")){
						stringBuffer.append("  and t.lon_bal_sum_cny<="+paramsMap.get("lon_bal_to") +"  ");
				}
	
				if( paramsMap.get("lon_year_from")!=null && !paramsMap.get("lon_year_from").equals("") && !paramsMap.get("lon_year_from").equals("null")){
					stringBuffer.append("  and t.lon_year_sum_cny>="+paramsMap.get("lon_year_from")+"  ");
				}
				if( paramsMap.get("lon_year_to")!=null && !paramsMap.get("lon_year_to").equals("") && !paramsMap.get("lon_year_to").equals("null")){
						stringBuffer.append("  and t.lon_year_sum_cny<="+paramsMap.get("lon_year_to") +"  ");
				}				
				
			}			
		}
		else if(curr_type_value.equals("03"))//如果币种是本外币
		{
			if(caculate_type_value.equals("01"))//如果统计口径是时点
			{
				stringBuffer.append("select t.instn_no,t.cust_zzdm,t.sts,t.scope,t.crm_scope,t.cust_typ,t.cust_typ_desc,t.cust_big_lev,t.cust_small_lev,t.cust_lev, " 
						+ " t.cust_name,t.cust_crm_typ," 
						+ " t.dep_bal1_sum dep_bal1,t.dep_bal2_sum dep_bal2,t.dep_bal3_sum dep_bal3,t.dep_bal4_sum dep_bal4,t.dep_bal5_sum dep_bal5,t.dep_bal6_sum dep_bal6,t.dep_bal7_sum dep_bal7,t.dep_bal_sum dep_bal,"
						+ " t.lon_bal1_sum lon_bal1,t.lon_bal2_sum lon_bal2,t.lon_bal3_sum lon_bal3,t.lon_bal4_sum lon_bal4,t.lon_bal5_sum lon_bal5,t.lon_bal6_sum lon_bal6,t.lon_bal_sum lon_bal,units.system_unitname instn_name,units.system_levelunit instn_level " 
						
				);
				
				stringBuffer.append(" from mdm.acrm_m_ci_lon_dep_desc t left join fdm.acrm_f_sm_sys_units units on t.instn_no = units.system_unitid where (1=1) ");
				
				if( paramsMap.get("dep_bal_from")!=null && !paramsMap.get("dep_bal_from").equals("") && !paramsMap.get("dep_bal_from").equals("null")){
						stringBuffer.append("  and t.dep_bal_sum>="+paramsMap.get("dep_bal_from")+"  ");
				}
				if( paramsMap.get("dep_bal_to")!=null && !paramsMap.get("dep_bal_to").equals("") && !paramsMap.get("dep_bal_to").equals("null")){
						stringBuffer.append("  and t.dep_bal_sum<="+paramsMap.get("dep_bal_to") +"  ");
				}

				if( paramsMap.get("dep_year_from")!=null && !paramsMap.get("dep_year_from").equals("") && !paramsMap.get("dep_year_from").equals("null")){
					stringBuffer.append("  and t.dep_year_sum>="+paramsMap.get("dep_year_from")+"  ");
				}
				if( paramsMap.get("dep_year_to")!=null && !paramsMap.get("dep_year_to").equals("") && !paramsMap.get("dep_year_to").equals("null")){
						stringBuffer.append("  and t.dep_year_sum<="+paramsMap.get("dep_year_to") +"  ");
				}		
				
				if( paramsMap.get("lon_bal_from")!=null && !paramsMap.get("lon_bal_from").equals("") && !paramsMap.get("lon_bal_from").equals("null")){
					stringBuffer.append("  and t.lon_bal_sum>="+paramsMap.get("lon_bal_from")+"  ");
				}
				if( paramsMap.get("lon_bal_to")!=null && !paramsMap.get("lon_bal_to").equals("") && !paramsMap.get("lon_bal_to").equals("null")){
						stringBuffer.append("  and t.lon_bal_sum<="+paramsMap.get("lon_bal_to") +"  ");
				}
	
				if( paramsMap.get("lon_year_from")!=null && !paramsMap.get("lon_year_from").equals("") && !paramsMap.get("lon_year_from").equals("null")){
					stringBuffer.append("  and t.lon_year_sum>="+paramsMap.get("lon_year_from")+"  ");
				}
				if( paramsMap.get("lon_year_to")!=null && !paramsMap.get("lon_year_to").equals("") && !paramsMap.get("lon_year_to").equals("null")){
						stringBuffer.append("  and t.lon_year_sum<="+paramsMap.get("lon_year_to") +"  ");
				}			
				
			}
			if(caculate_type_value.equals("02"))//如果统计口径是月均
			{
				stringBuffer.append("select t.instn_no,t.cust_zzdm,t.sts,t.scope,t.crm_scope,t.cust_typ,t.cust_typ_desc,t.cust_big_lev,t.cust_small_lev,t.cust_lev, " 
						+ " t.cust_name,t.cust_crm_typ," 
						+ " t.dep_mon1_sum dep_bal1,t.dep_mon2_sum dep_bal2,t.dep_mon3_sum dep_bal3,t.dep_mon4_sum  dep_bal4,t.dep_mon5_sum dep_bal5,t.dep_mon6_sum dep_bal6,t.dep_mon7_sum dep_bal7,t.dep_mon_sum dep_bal,"
						+ " t.lon_mon1_sum lon_bal1,t.lon_mon2_sum lon_bal2,t.lon_mon3_sum lon_bal3,t.lon_mon4_sum  lon_bal4,t.lon_mon5_sum lon_bal5,t.lon_mon6_sum lon_bal6,t.lon_mon_sum lon_bal,units.system_unitname instn_name,units.system_levelunit instn_level " 
						
				);				
				stringBuffer.append(" from mdm.acrm_m_ci_lon_dep_desc t left join fdm.acrm_f_sm_sys_units units on t.instn_no = units.system_unitid  where (1=1) ");
				
				if( paramsMap.get("dep_bal_from")!=null && !paramsMap.get("dep_bal_from").equals("") && !paramsMap.get("dep_bal_from").equals("null")){
						stringBuffer.append("  and t.dep_bal_sum>="+paramsMap.get("dep_bal_from")+"  ");
				}
				if( paramsMap.get("dep_bal_to")!=null && !paramsMap.get("dep_bal_to").equals("") && !paramsMap.get("dep_bal_to").equals("null")){
						stringBuffer.append("  and t.dep_bal_sum<="+paramsMap.get("dep_bal_to") +"  ");
				}

				if( paramsMap.get("dep_year_from")!=null && !paramsMap.get("dep_year_from").equals("") && !paramsMap.get("dep_year_from").equals("null")){
					stringBuffer.append("  and t.dep_year_sum>="+paramsMap.get("dep_year_from")+"  ");
				}
				if( paramsMap.get("dep_year_to")!=null && !paramsMap.get("dep_year_to").equals("") && !paramsMap.get("dep_year_to").equals("null")){
						stringBuffer.append("  and t.dep_year_sum<="+paramsMap.get("dep_year_to") +"  ");
				}		
				
				if( paramsMap.get("lon_bal_from")!=null && !paramsMap.get("lon_bal_from").equals("") && !paramsMap.get("lon_bal_from").equals("null")){
					stringBuffer.append("  and t.lon_bal_sum>="+paramsMap.get("lon_bal_from")+"  ");
				}
				if( paramsMap.get("lon_bal_to")!=null && !paramsMap.get("lon_bal_to").equals("") && !paramsMap.get("lon_bal_to").equals("null")){
						stringBuffer.append("  and t.lon_bal_sum<="+paramsMap.get("lon_bal_to") +"  ");
				}
	
				if( paramsMap.get("lon_year_from")!=null && !paramsMap.get("lon_year_from").equals("") && !paramsMap.get("lon_year_from").equals("null")){
					stringBuffer.append("  and t.lon_year_sum>="+paramsMap.get("lon_year_from")+"  ");
				}
				if( paramsMap.get("lon_year_to")!=null && !paramsMap.get("lon_year_to").equals("") && !paramsMap.get("lon_year_to").equals("null")){
						stringBuffer.append("  and t.lon_year_sum<="+paramsMap.get("lon_year_to") +"  ");
				}			
				
			}
			if(caculate_type_value.equals("03"))//如果统计口径是季均
			{
				stringBuffer.append("select t.instn_no,t.cust_zzdm,t.sts,t.scope,t.crm_scope,t.cust_typ,t.cust_typ_desc,t.cust_big_lev,t.cust_small_lev,t.cust_lev, " 
						+ " t.cust_name,t.cust_crm_typ," 
						+ " t.dep_quar1_sum dep_bal1,t.dep_quar2_sum dep_bal2,t.dep_quar3_sum dep_bal3,t.dep_quar4_sum dep_bal4,t.dep_quar5_sum dep_bal5,t.dep_quar6_sum dep_bal6,t.dep_quar7_sum dep_bal7,t.dep_quar_sum dep_bal,"
						+ " t.lon_quar1_sum lon_bal1,t.lon_quar2_sum lon_bal2,t.lon_quar3_sum lon_bal3,t.lon_quar4_sum lon_bal4,t.lon_quar5_sum lon_bal5,t.lon_quar6_sum lon_bal6,t.lon_quar_sum lon_bal, units.system_unitname instn_name,units.system_levelunit instn_level " 
						
				);					
				stringBuffer.append(" from mdm.acrm_m_ci_lon_dep_desc t  left join fdm.acrm_f_sm_sys_units units on t.instn_no = units.system_unitid where (1=1) ");
				
				if( paramsMap.get("dep_bal_from")!=null && !paramsMap.get("dep_bal_from").equals("") && !paramsMap.get("dep_bal_from").equals("null")){
						stringBuffer.append("  and t.dep_bal_sum>="+paramsMap.get("dep_bal_from")+"  ");
				}
				if( paramsMap.get("dep_bal_to")!=null && !paramsMap.get("dep_bal_to").equals("") && !paramsMap.get("dep_bal_to").equals("null")){
						stringBuffer.append("  and t.dep_bal_sum<="+paramsMap.get("dep_bal_to") +"  ");
				}

				if( paramsMap.get("dep_year_from")!=null && !paramsMap.get("dep_year_from").equals("") && !paramsMap.get("dep_year_from").equals("null")){
					stringBuffer.append("  and t.dep_year_sum>="+paramsMap.get("dep_year_from")+"  ");
				}
				if( paramsMap.get("dep_year_to")!=null && !paramsMap.get("dep_year_to").equals("") && !paramsMap.get("dep_year_to").equals("null")){
						stringBuffer.append("  and t.dep_year_sum<="+paramsMap.get("dep_year_to") +"  ");
				}		
				
				if( paramsMap.get("lon_bal_from")!=null && !paramsMap.get("lon_bal_from").equals("") && !paramsMap.get("lon_bal_from").equals("null")){
					stringBuffer.append("  and t.lon_bal_sum>="+paramsMap.get("lon_bal_from")+"  ");
				}
				if( paramsMap.get("lon_bal_to")!=null && !paramsMap.get("lon_bal_to").equals("") && !paramsMap.get("lon_bal_to").equals("null")){
						stringBuffer.append("  and t.lon_bal_sum<="+paramsMap.get("lon_bal_to") +"  ");
				}
	
				if( paramsMap.get("lon_year_from")!=null && !paramsMap.get("lon_year_from").equals("") && !paramsMap.get("lon_year_from").equals("null")){
					stringBuffer.append("  and t.lon_year_sum>="+paramsMap.get("lon_year_from")+"  ");
				}
				if( paramsMap.get("lon_year_to")!=null && !paramsMap.get("lon_year_to").equals("") && !paramsMap.get("lon_year_to").equals("null")){
						stringBuffer.append("  and t.lon_year_sum<="+paramsMap.get("lon_year_to") +"  ");
				}			
				
			}
			if(caculate_type_value.equals("04"))//如果统计口径是年均
			{
				stringBuffer.append("select t.instn_no,t.cust_zzdm,t.sts,t.scope,t.crm_scope,t.cust_typ,t.cust_typ_desc,t.cust_big_lev,t.cust_small_lev,t.cust_lev, " 
						+ " t.cust_name,t.cust_crm_typ," 
						+ " t.dep_year1_sum dep_bal1,t.dep_year2_sum dep_bal2,t.dep_year3_sum dep_bal3,t.dep_year4_sum dep_bal4,t.dep_year5_sum dep_bal5,t.dep_year6_sum dep_bal6,t.dep_year7_sum dep_bal7,t.dep_year_sum dep_bal,"
						+ " t.lon_year1_sum lon_bal1,t.lon_year2_sum lon_bal2,t.lon_year3_sum lon_bal3,t.lon_year4_sum lon_bal4,t.lon_year5_sum lon_bal5,t.lon_year6_sum lon_bal6,t.lon_year_sum lon_bal,units.system_unitname instn_name,units.system_levelunit instn_level" 
				);	
				stringBuffer.append(" from mdm.acrm_m_ci_lon_dep_desc t left join fdm.acrm_f_sm_sys_units units on t.instn_no = units.system_unitid where (1=1) ");
				
				if( paramsMap.get("dep_bal_from")!=null && !paramsMap.get("dep_bal_from").equals("") && !paramsMap.get("dep_bal_from").equals("null")){
						stringBuffer.append("  and t.dep_bal_sum>="+paramsMap.get("dep_bal_from")+"  ");
				}
				if( paramsMap.get("dep_bal_to")!=null && !paramsMap.get("dep_bal_to").equals("") && !paramsMap.get("dep_bal_to").equals("null")){
						stringBuffer.append("  and t.dep_bal_sum<="+paramsMap.get("dep_bal_to") +"  ");
				}

				if( paramsMap.get("dep_year_from")!=null && !paramsMap.get("dep_year_from").equals("") && !paramsMap.get("dep_year_from").equals("null")){
					stringBuffer.append("  and t.dep_year_sum>="+paramsMap.get("dep_year_from")+"  ");
				}
				if( paramsMap.get("dep_year_to")!=null && !paramsMap.get("dep_year_to").equals("") && !paramsMap.get("dep_year_to").equals("null")){
						stringBuffer.append("  and t.dep_year_sum<="+paramsMap.get("dep_year_to") +"  ");
				}		
				
				if( paramsMap.get("lon_bal_from")!=null && !paramsMap.get("lon_bal_from").equals("") && !paramsMap.get("lon_bal_from").equals("null")){
					stringBuffer.append("  and t.lon_bal_sum>="+paramsMap.get("lon_bal_from")+"  ");
				}
				if( paramsMap.get("lon_bal_to")!=null && !paramsMap.get("lon_bal_to").equals("") && !paramsMap.get("lon_bal_to").equals("null")){
						stringBuffer.append("  and t.lon_bal_sum<="+paramsMap.get("lon_bal_to") +"  ");
				}
	
				if( paramsMap.get("lon_year_from")!=null && !paramsMap.get("lon_year_from").equals("") && !paramsMap.get("lon_year_from").equals("null")){
					stringBuffer.append("  and t.lon_year_sum>="+paramsMap.get("lon_year_from")+"  ");
				}
				if( paramsMap.get("lon_year_to")!=null && !paramsMap.get("lon_year_to").equals("") && !paramsMap.get("lon_year_to").equals("null")){
						stringBuffer.append("  and t.lon_year_sum<="+paramsMap.get("lon_year_to") +"  ");
				}			
				
			}			
		}

		Iterator iterator = paramsMap.keySet().iterator();
		
		while(iterator.hasNext()){
			
			String key = (String)iterator.next();
			
			if(
			key.equals("cur_type") ||  key.equals("caculate_type") || key.equals("dep_bal_from")
			|| key.equals("dep_bal_from") || key.equals("dep_bal_to") || key.equals("dep_year_from")		
			|| key.equals("dep_year_to") || key.equals("lon_bal_from") || key.equals("lon_bal_to")
			|| key.equals("lon_year_from") || key.equals("lon_year_to")
			)
			{
				continue;
			}			
			String keyValue =(String) paramsMap.get(key);			
			if(keyValue!= null && !keyValue.equals("") && keyValue.equals("null")){
				if(key.equals("crm_dt")){
					stringBuffer.append(" and crm_dt = to_date('"+keyValue+"','YYYY-MM-DD') ");
				}
				else if(key.equals("cust_name")){
					
					stringBuffer.append(" and cust_name like '%"+keyValue+"%' ");
				}
				else
				{
					stringBuffer.append(" and " + key+" = '"+keyValue + "' ");
				}
			}
		}		
		try{
			int currentPage = this.getStart()/this.getLimit()+1;
			PagingInfo pi = new PagingInfo(this.getLimit(),currentPage);
			QueryHelper qh = new QueryHelper(stringBuffer.toString(), ds.getConnection(),pi);
//			QueryHelper qh = new QueryHelper(sb.toString(), ds.getConnection(),pi);
			qh.setPrimaryKey("t.id");
			depAndLonDetailMap = qh.getJSON();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "success";
	}
	public Map getDepAndLonDetailMap() {
		return depAndLonDetailMap;
	}
	public void setDepAndLonDetailMap(Map depAndLonDetailMap) {
		this.depAndLonDetailMap = depAndLonDetailMap;
	}
	

}
