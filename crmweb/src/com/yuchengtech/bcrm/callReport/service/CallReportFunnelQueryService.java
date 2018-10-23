package com.yuchengtech.bcrm.callReport.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.crm.constance.SystemConstance;

/**
 * @描述：营销管理->商机管理->销售漏斗查询Service
 * @author wzy
 * @date:2013-03-25
 */
@Service
@Transactional(value = "postgreTransactionManager")
public class CallReportFunnelQueryService {

	private EntityManager em;

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@SuppressWarnings("rawtypes")
	public String getQueryResultJsonData(String sql) {
		List rList = null;
		Object[] objs = null;
		String resultJson = null;
		rList = em.createNativeQuery(sql).getResultList();
		if (rList != null && rList.size() > 0) {
			resultJson = "";
			for (int i = 0; i < rList.size(); i++) {
				objs = (Object[]) rList.get(i);
				resultJson += (objs[2] + ",");
				resultJson += (objs[6]);
				if (i < rList.size() - 1) {
					resultJson += (",");
				}
			}
		}
		return resultJson;
	}
	
	public Map getDate(String begin,String end){
		Map<String, Map> map = new HashMap<String, Map>();
		
		Map<String, String> map1 = new HashMap<String, String>();
		Map<String, String> map2 = new HashMap<String, String>();
		Map<String, String> map3 = new HashMap<String, String>();
		Map<String, String> map4 = new HashMap<String, String>();
		Map<String, String> map5 = new HashMap<String, String>();
		
		String sql = "select c.call_id from ocrm_f_se_callreport_busi c where c.sales_stage != '6'" +
				" and c.last_update_tm <= to_date('"+begin+"','yyyy-mm-dd') group by c.call_id ";
		List rList = null;
		List list = null;
		String sql1 = "";
		
		rList = em.createNativeQuery(sql).getResultList();
		if (rList != null && rList.size() > 0) {
			for (int i = 0; i < rList.size(); i++) {
				//销售阶段1
				String rownumcondition = " ";
				if("DB2".equals(SystemConstance.DB_TYPE)){
					rownumcondition = " order by b.sales_stage asc fetch first 1 rows only ";
				} else {
					rownumcondition = " and rownum = 1 order by b.sales_stage asc ";
				}
				sql1 = "select (b.last_update_tm-c.last_update_tm) as update_date from ocrm_f_se_callreport_busi b " +
						" left join ocrm_f_se_callreport_busi c on c.call_id = '"+rList.get(i)+"' and c.sales_stage = '1'" +
						" where b.call_id = '"+rList.get(i)+"' and b.sales_stage <> '1' " +
						" and b.last_update_tm <= to_date('"+end+"','yyyy-mm-dd') " +rownumcondition;
				list = em.createNativeQuery(sql1).getResultList();
				if (list != null && list.size() > 0) {
					for (int j = 0; j < list.size(); j++) {
						if(list.get(j) == null){	
							map1.put("ct1", "0");
							map1.put("date1","0");
						}else{
							map1.put("ct1", String.valueOf(Integer.parseInt(map1.get("ct1")==null?"0":map1.get("ct1"))+1));
							map1.put("date1",String.valueOf(Integer.parseInt(map1.get("date1")==null?"0":map1.get("date1"))+Integer.parseInt(list.get(j).toString())));
						}
					}
				}
				
				//销售阶段2
				sql1 = "select (b.last_update_tm-c.last_update_tm) as update_date from ocrm_f_se_callreport_busi b " +
						" left join ocrm_f_se_callreport_busi c on c.call_id = '"+rList.get(i)+"' and c.sales_stage = '2'" +
						" where b.call_id = '"+rList.get(i)+"' and b.sales_stage not in('1','2') " +
						" and b.last_update_tm <= to_date('"+end+"','yyyy-mm-dd') " +rownumcondition;
				list = em.createNativeQuery(sql1).getResultList();
				if (list != null && list.size() > 0) {
					for (int j = 0; j < list.size(); j++) {
						if(list.get(j) == null){
							map2.put("ct2", "0");
							map2.put("date2","0");
						}else{
							map2.put("ct2", String.valueOf(Integer.parseInt(map2.get("ct2")==null?"0":map2.get("ct2"))+1));
							map2.put("date2",String.valueOf(Integer.parseInt(map2.get("date2")==null?"0":map2.get("date2"))+Integer.parseInt(list.get(j).toString())));
						}
					}
				}
				
				//销售阶段3
				sql1 = "select (b.last_update_tm-c.last_update_tm) as update_date from ocrm_f_se_callreport_busi b " +
						" left join ocrm_f_se_callreport_busi c on c.call_id = '"+rList.get(i)+"' and c.sales_stage = '3'" +
						" where b.call_id = '"+rList.get(i)+"' and b.sales_stage not in('1','2','3') " +
						" and b.last_update_tm <= to_date('"+end+"','yyyy-mm-dd') " +rownumcondition;
				list = em.createNativeQuery(sql1).getResultList();
				if (list != null && list.size() > 0) {
					for (int j = 0; j < list.size(); j++) {
						if(list.get(j) == null){
							map3.put("ct3", "0");
							map3.put("date3","0");
						}else{
							map3.put("ct3", String.valueOf(Integer.parseInt(map3.get("ct3")==null?"0":map3.get("ct3"))+1));
							map3.put("date3",String.valueOf(Integer.parseInt(map3.get("date3")==null?"0":map3.get("date3"))+Integer.parseInt(list.get(j).toString())));
						}
					}
				}
				
				//销售阶段4
				sql1 = "select (b.last_update_tm-c.last_update_tm) as update_date from ocrm_f_se_callreport_busi b " +
						" left join ocrm_f_se_callreport_busi c on c.call_id = '"+rList.get(i)+"' and c.sales_stage = '4'" +
						" where b.call_id = '"+rList.get(i)+"' and b.sales_stage not in('1','2','3','4') " +
						" and b.last_update_tm <= to_date('"+end+"','yyyy-mm-dd')  " +rownumcondition;
				list = em.createNativeQuery(sql1).getResultList();
				if (list != null && list.size() > 0) {
					for (int j = 0; j < list.size(); j++) {
						if(list.get(j) == null){
							map4.put("ct4", "0");
							map4.put("date4","0");
						}else{
							map4.put("ct4", String.valueOf(Integer.parseInt(map4.get("ct4")==null?"0":map4.get("ct4"))+1));
							map4.put("date4",String.valueOf(Integer.parseInt(map4.get("date4")==null?"0":map4.get("date4"))+Integer.parseInt(list.get(j).toString())));
						}
					}
				}
				
				//销售阶段5
				sql1 = "select (b.last_update_tm-c.last_update_tm) as update_date from ocrm_f_se_callreport_busi b " +
						" left join ocrm_f_se_callreport_busi c on c.call_id = '"+rList.get(i)+"' and c.sales_stage = '5'" +
						" where b.call_id = '"+rList.get(i)+"' and b.sales_stage not in('1','2','3','4','5') " +
						" and b.last_update_tm <= to_date('"+end+"','yyyy-mm-dd')  " +rownumcondition;
				list = em.createNativeQuery(sql1).getResultList();
				if (list != null && list.size() > 0) {
					for (int j = 0; j < list.size(); j++) {
						if(list.get(j) == null){
							map5.put("ct5", "0");
							map5.put("date5","0");
						}else{
							map5.put("ct5", String.valueOf(Integer.parseInt(map5.get("ct5")==null?"0":map5.get("ct5"))+1));
							map5.put("date5",String.valueOf(Integer.parseInt(map5.get("date5")==null?"0":map5.get("date5"))+Integer.parseInt(list.get(j).toString())));
						}
					}
				}
			}
		}
		map.put("sb1", map1);
		map.put("sb2", map2);
		map.put("sb3", map3);
		map.put("sb4", map4);
		map.put("sb5", map5);
		return map;
	}
}