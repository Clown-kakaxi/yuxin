/**
 * 
 */
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

@Action(value="/customer-contribution", results={
    @Result(name="success", type="json")
})
public class CustomerContributionAction extends CommonAction{

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;	
	public void prepare() {
		StringBuffer stringBuffer = new StringBuffer();
		Map paramsMap = this.getJson();
		
		String checkedOrg="";
		String level = (String)paramsMap.get("instn_level");
		String checkedNodes = (String)paramsMap.get("checkedNodes")+"";
		String crm_date = (String)paramsMap.get("crm_dt")+"";
		String depTable=" mdm.acrm_m_ci_contribution t ";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		/*判断传入的统计时间,如果统计时间是近3天以前,去查询历史表*/
		GregorianCalendar calendar = new GregorianCalendar();
		
		GregorianCalendar calendarDay = new GregorianCalendar (calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.DATE)-3);

		String dataDay = ""+calendarDay.get(Calendar.YEAR)+"-"+calendarDay.get(Calendar.MONTH)+"-"+calendarDay.get(Calendar.DATE);
		
		try{
			Date dateParam = dateFormat.parse(crm_date);
			Date dateToday = dateFormat.parse(dataDay);
			if(dateParam.before(dateToday)){
				depTable = " mdm.acrm_m_ci_contribution_h t ";
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
		
		else
		{
			level = "level_3";
		}
		
		stringBuffer.append("select t.cust_id, t.crm_dt,t.cust_id,cust_name,t.cust_zzdm,t.scope,t.crm_scope crm_scope,t.hy_class  hy_class,t.hy_typ,t.cust_sts,t.create_dt," 
				+" t3."+level+" instn_level_id,t4.unitname, "
				+"sum(t.cbal) cbal,sum(t.avg_cbal) avg_cbal,sum(t.dbal) dbal,sum(davg_dbal) davg_dbal,sum(t.rcv) rcv,sum(t.zbzy) zbzy,sum(t.cbfy) cbfy," +
				"sum(t.cmncon) cmncon,sum(t.dmncon) dmncon,sum(t.dtzcon) dtzcon,sum(t.mrcv) mrcv,sum(t.mncon) mncon,sum(t.tzcon) tzcon," 
				+"sum(t.mnjcon) mnjcon  ");
		
		stringBuffer.append(" from "+depTable+" inner join (select acrm_f_sm_sys_units_sta.unitid from fdm.acrm_f_sm_sys_units_sta  "+checkedOrg+") " 
				+" t2 on t.instn_id = t2.unitid inner join fdm.acrm_f_sm_sys_units_sta t3 on t3.level_4 = t.instn_id inner join fdm.acrm_f_sm_sys_units_sta t4 on t3."+level 
				+" = t4.unitid ");		
		Iterator iterator = paramsMap.keySet().iterator();

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
		
		if(paramsMap.get("cbal_from")!=null && !(paramsMap.get("cbal_from")+"").equals("") && !(paramsMap.get("cbal_from")+"").equals("null")){
			stringBuffer.append("  and t.cbal>="+paramsMap.get("cbal_from")+"");
		}
		if(paramsMap.get("cbal_to") !=null && !(paramsMap.get("cbal_to")+"").equals("") && !(paramsMap.get("cbal_to")+"").equals("null")){
			stringBuffer.append("  and t.cbal<="+paramsMap.get("cbal_to")+"");
		}
		if(paramsMap.get("avg_cbal_from") !=null && !(paramsMap.get("avg_cbal_from")+"").equals("") && !(paramsMap.get("avg_cbal_from")+"").equals("null")){
			stringBuffer.append("  and t.avg_cbal>="+paramsMap.get("avg_cbal_from")+"");
		}
		if(paramsMap.get("avg_cbal_to") !=null && !(paramsMap.get("avg_cbal_to")+"").equals("") && !(paramsMap.get("avg_cbal_to")+"").equals("null")){
			stringBuffer.append("  and t.avg_cbal<="+paramsMap.get("avg_cbal_to")+"");
		}	

		if(paramsMap.get("dbal_from")!=null && !(paramsMap.get("dbal_from")+"").equals("") && !(paramsMap.get("dbal_from")+"").equals("null")){
			stringBuffer.append("  and t.dbal>="+paramsMap.get("dbal_from")+"");
		}
		if(paramsMap.get("dbal_to") !=null && !(paramsMap.get("dbal_to")+"").equals("") && !(paramsMap.get("dbal_to")+"").equals("null")){
			stringBuffer.append("  and t.dbal<="+paramsMap.get("dbal_to")+"");
		}
		if(paramsMap.get("davg_dbal_from") !=null && !(paramsMap.get("davg_dbal_from")+"").equals("") && !(paramsMap.get("davg_dbal_from")+"").equals("null")){
			stringBuffer.append("  and t.davg_dbal>="+paramsMap.get("davg_dbal_from")+"");
		}
		if(paramsMap.get("davg_dbal_to") !=null && !(paramsMap.get("davg_dbal_to")+"").equals("") && !(paramsMap.get("davg_dbal_to")+"").equals("null")){
			stringBuffer.append("  and t.davg_dbal<="+paramsMap.get("davg_dbal_to")+"");
		}		
		
		if(paramsMap.get("mrcv") !=null && !(paramsMap.get("mrcv")+"").equals("") && !(paramsMap.get("mrcv")+"").equals("null")){
			stringBuffer.append("  and t.mrcv>"+paramsMap.get("mrcv")+"");
		}		
		if(paramsMap.get("tzcon") !=null && !(paramsMap.get("tzcon")+"").equals("") && !(paramsMap.get("tzcon")+"").equals("null")){
			stringBuffer.append("  and t.tzcon>"+paramsMap.get("tzcon")+"");
		}	
		if(paramsMap.get("mnjcon") !=null && !(paramsMap.get("mnjcon")+"").equals("") && !(paramsMap.get("mnjcon")+"").equals("null")){
			stringBuffer.append("  and t.mnjcon>"+paramsMap.get("mnjcon")+"");
		}
		
		if(paramsMap.get("dtzcon") !=null && !(paramsMap.get("dtzcon")+"").equals("") && !(paramsMap.get("dtzcon")+"").equals("null")){
			stringBuffer.append("  and t.dtzcon>"+paramsMap.get("dtzcon")+"");
		}		
		if(paramsMap.get("mncon") !=null && !(paramsMap.get("mncon")+"").equals("") && !(paramsMap.get("mncon")+"").equals("null")){
			stringBuffer.append("  and t.mncon>"+paramsMap.get("mncon")+"");
		}	
		
		if(paramsMap.get("rcv_from") !=null && !(paramsMap.get("rcv_from")+"").equals("") && !(paramsMap.get("rcv_from")+"").equals("null")){
			stringBuffer.append("  and t.rcv>="+paramsMap.get("rcv_from")+"");
		}		
		if(paramsMap.get("rcv_to") !=null && !(paramsMap.get("rcv_to")+"").equals("") && !(paramsMap.get("rcv_to")+"").equals("null")){
			stringBuffer.append("  and t.rcv<="+paramsMap.get("rcv_to")+"");
		}

		if(paramsMap.get("cmncon_from") !=null && !(paramsMap.get("cmncon_from")+"").equals("") && !(paramsMap.get("cmncon_from")+"").equals("null")){
			stringBuffer.append("  and t.cmncon>"+paramsMap.get("cmncon_from")+"");
		}		
		if(paramsMap.get("dmncon_from") !=null && !(paramsMap.get("dmncon_from")+"").equals("") && !(paramsMap.get("dmncon_from")+"").equals("null")){
			stringBuffer.append("  and t.dmncon>"+paramsMap.get("dmncon_from")+"");
		}
		
		while(iterator.hasNext()){
			
			String key = (String)iterator.next();
			
			if(key.equals("cur_type") ||  key.equals("caculate_type") || key.equals("cbal_from") || key.equals("cbal_to") || key.equals("avg_cbal_from")
			|| key.equals("avg_cbal_to") || key.equals("dbal_from") || key.equals("dbal_to") || key.equals("davg_dbal_from") || key.equals("davg_dbal_to")
			|| key.equals("mrcv") || key.equals("tzcon")||key.equals("mnjcon")||key.equals("dtzcon") || key.equals("mncon") ||key.equals("rcv_from")
			|| key.equals("rcv_to") || key.equals("cmncon_from")||key.equals("cmncon_to") || key.equals("dmncon_from")|| key.equals("instn_level") || key.equals("checkedNodes")
			||key.equals("instn_no") || key.equals("level") || key.equals("cust_base_name") || key.equals("roll_name")
			){
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
				else
				{
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
		
		
		String groupStr=" group by t.crm_dt,t.cust_id,cust_name,t.cust_zzdm,t.scope,t.crm_scope,t.hy_class,t.hy_typ,t.cust_sts,t.create_dt, t3."+level+",t4.unitname ";

		setGroupByFields(groupStr);
		
		SQL = stringBuffer.toString();
		try{
//			int currentPage = this.getStart()/this.getLimit()+1;
//			PagingInfo pi = new PagingInfo(this.getLimit(),currentPage);
//			QueryHelper qh = new QueryHelper(stringBuffer.toString(), ds.getConnection(),pi);
//			QueryHelper qh = new QueryHelper(sb.toString(), ds.getConnection(),pi);
			setPrimaryKey("t.cust_zzdm");
//			addGreenplumLookup("scope", "QYGM");
//			addGreenplumLookup("crm_scope", "KHQYGM");
//			addGreenplumLookup("hy_class", "HYFL");
//			addGreenplumLookup("hy_typ", "HYMXFL");
//			addGreenplumLookup("cust_sts", "KHZT");
//			contributionMap = getJSON();
			datasource = ds;
		}catch(Exception ex){
			ex.printStackTrace();
		}
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
	
//	public Map getContributionMap() {
//		return contributionMap;
//	}
//	public void setContributionMap(Map contributionMap) {
//		this.contributionMap = contributionMap;
//	}
	
	public Map<String, Object> getJson() {
        return super.getJson();
    }	
}
