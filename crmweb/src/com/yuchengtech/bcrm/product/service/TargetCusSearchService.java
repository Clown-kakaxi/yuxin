package com.yuchengtech.bcrm.product.service;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bcrm.customer.constance.AgileSearchConstance;
import com.yuchengtech.bcrm.customer.model.AgileSearchCondition;
import com.yuchengtech.bcrm.product.model.TargetCus;
import com.yuchengtech.bcrm.system.model.DataSet;
import com.yuchengtech.bcrm.system.model.DataSetColumn;
import com.yuchengtech.bcrm.system.model.OcrmFASsRelation;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 产品目标客户
 * 
 * @author Sena
 */

@Service
@Transactional(value = "postgreTransactionManager")
public class TargetCusSearchService {
	
	@Autowired
    @Qualifier("dsOracle")
    private DataSource dsOracle;
	
	@PersistenceContext
	private EntityManager em;

	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@SuppressWarnings("unchecked")
	// 无查询条件
	public List<AgileSearchCondition> query(Long roleId) {
		StringBuffer querysql = new StringBuffer();
		querysql
				.append("select c from AgileSearchCondition c where  c.ssId=?1");
		Query q = em.createQuery(querysql.toString());
		q.setParameter(1, roleId);
		q.setFirstResult(0);
		q.setMaxResults(100000);
		List<AgileSearchCondition> list = q.getResultList();
		return list;
	}


	// 删除
	public void remove(Long roleId) {
		List<AgileSearchCondition> list = query(roleId);
		if (!list.isEmpty()) {

			for (int i = 0; i < list.size(); i++) {
				AgileSearchCondition agileSearchCondition = list.get(i);
				if (agileSearchCondition != null) {
					em.remove(agileSearchCondition);
				}
			}
		}
	}

	public void saveSolution(String conditions,String radio,String solutionId) throws ParseException {
		
		Query select = em.createQuery("select t FROM TargetCus t where t.productId='"+solutionId+"'");
		List<TargetCus> list = select.getResultList();
		if(list.size()==0){
			/**
			 * 新增
			 */
			JSONArray solutionConditions = JSONArray.fromObject(conditions);//add 20141211
			if(solutionConditions.size() > 0){//add 20141211
				this.saveSolution1(conditions,radio,solutionId);
				return;
			}//add 20141211
		}else {
			Query delete = em.createQuery("DELETE FROM AgileSearchCondition T WHERE T.ssId = "+list.get(0).getId());
			delete.executeUpdate();
		
			JSONArray solutionConditions = JSONArray.fromObject(conditions);
			for(Object sc : solutionConditions){
				JSONObject scd = (JSONObject)sc;
				AgileSearchCondition asc = new AgileSearchCondition();
				asc.setSsId(list.get(0).getId());
				asc.setSsColItem(scd.getLong("ss_col_item"));
				asc.setSsColOp(scd.getString("ss_col_op"));
				asc.setSsColValue(scd.getString("ss_col_value"));
				asc.setSsColJoin(radio);
				em.persist(asc);
			}
			/**
			 * add 20141211 begin
			 */
			if(solutionConditions.size() <= 0){
				Query  cusQuery = em.createQuery("DELETE FROM TargetCus T WHERE t.productId = '"+solutionId+"'");
				cusQuery.executeUpdate();
			}
			/**
			 * add 20141211 end
			 */
		}
	}
	public void saveSolution1( String conditions,String radio,String solutionId){
		
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userId = auth.getUserId();
		TargetCus targetCus = new TargetCus();
		targetCus.setProductId(solutionId);
		targetCus.setUpdateDate(new Date());
		targetCus.setUpdateUser(userId);
		targetCus.setUpdateOrg(auth.getUnitId());
		em.persist(targetCus);
		JSONArray solutionConditions = JSONArray.fromObject(conditions);
	
		for(Object sc : solutionConditions){
			JSONObject scd = (JSONObject)sc;
			AgileSearchCondition asc = new AgileSearchCondition();
			asc.setSsId(targetCus.getId());
			asc.setSsColItem(scd.getLong("ss_col_item"));
			asc.setSsColOp(scd.getString("ss_col_op"));
			asc.setSsColValue(scd.getString("ss_col_value"));
			asc.setSsColJoin(radio);
			em.persist(asc);
		}
	}
	/**
	 * 创建查询SQL
	 * @param jaCondition：查询条件字段属性
	 * @param productId：产品编号
	 * @param radio：交并操作
	 * @return 返回Map：包括SQL:结果SQL；lookupColumns：需要增加数据MAPPING的字段
	 */
	public Map<String, Object> generatorSql(JSONArray jaCondition, String radio,String productId){
		
		String conditionColumns = "";
		for(Object o:jaCondition){
			JSONObject jo = (JSONObject) o;
			if("".equals(conditionColumns)){
				conditionColumns += jo.getLong("ss_col_item");
			}else{
				conditionColumns += "," + jo.getLong("ss_col_item");
			}
		}
		
		/**
		 * 查询条件中的列对象
		 */
		Query queryConditionColumms = em.createQuery("SELECT cols FROM DataSetColumn cols WHERE cols.id in ("+conditionColumns+")");
		List<DataSetColumn> conditionColumnsObj = queryConditionColumms.getResultList();
		String tables = "0";
		for(DataSetColumn dsc : conditionColumnsObj){
			tables += ","+dsc.getDbtableId();
		}
		/**
		 * 查询涉及数据集对象
		 */
		Query queryDatasets = em.createQuery("SELECT datasets FROM DataSet datasets WHERE datasets.id in ("+tables+") or datasets.value = \""+AgileSearchConstance.CUST_MAIN_INFO_TABLE+"\"");
		List<DataSet> dss = queryDatasets.getResultList();
		
		String relTable = "0";
		for(DataSet table : dss){
			relTable += ","+table.getId();
		}
		/**
		 * 联表所需关联关系对象
		 */
		Query queryRelations = em.createQuery("SELECT osr FROM OcrmFASsRelation osr WHERE osr.joinLeftTable in ("+relTable+") and osr.joinRightTable in ("+relTable+")");
		List<OcrmFASsRelation> ofasrs = queryRelations.getResultList();
		
		String joinColumns = "0";
		for(OcrmFASsRelation ofasr:ofasrs){
			joinColumns += ","+ofasr.getJoinLeftCol()+","+ofasr.getJoinRightCol();
		}
		
		/**
		 * 连表所需列对象
		 */
		Query queryJoinColumms = em.createQuery("SELECT cols FROM DataSetColumn cols WHERE cols.id in ("+joinColumns+")");
		List<DataSetColumn> joinColumnsObj = queryJoinColumms.getResultList();
		
		//连表语句
		String joinSQL = AgileSearchConstance.createTableJoin(dss, ofasrs, joinColumnsObj);
		//SELECT语句
		String selectSQL = " custinfo.cust_id,custinfo.cust_name as CUST_ZH_NAME,custinfo.CUST_LEVEL as CUST_LEV, mgr.MGR_ID,mgr.MGR_NAME,mgr.INSTITUTION,mgr.INSTITUTION_NAME, " +
				"(case when (SELECT count(1)  FROM Ocrm_f_Ci_p_Probuy_Info pr where pr.cust_id = custinfo.cust_id and pr.product_no = '"+productId+"')> 0 then '1' else  '0' end )as IS_BUY_THE_PROD  ";
		//条件语句
		String whereSQL = AgileSearchConstance.createWhereColumns(jaCondition, dss, conditionColumnsObj, radio);
		
		Map<String, Object> lastResult = new HashMap<String,Object>();
		String finalSql =  "SELECT "+selectSQL+" FROM "+joinSQL+" left join  OCRM_F_CI_BELONG_CUSTMGR MGR ON custinfo.cust_id =MGR.CUST_ID AND( MGR.MAIN_TYPE = '1' or MGR.MAIN_TYPE is null) WHERE "+whereSQL;
		lastResult.put("SQL",finalSql);
		return lastResult;
	}
	
	/**
	 * 拼接查询条件
	 * @param jaCondition  查询条件
	 * @param radio  交并关系
	 * @return
	 */
	public String generatorSql(JSONArray jaCondition, String radio){
		
		String conditionColumns = "";
		for(Object o:jaCondition){
			JSONObject jo = (JSONObject) o;
			if("".equals(conditionColumns)){
				conditionColumns += jo.getLong("ss_col_item");
			}else{
				conditionColumns += "," + jo.getLong("ss_col_item");
			}
		}
		
		/**
		 * 查询条件中的列对象
		 */
		Query queryConditionColumms = em.createQuery("SELECT cols FROM DataSetColumn cols WHERE cols.id in ("+conditionColumns+")");
		List<DataSetColumn> conditionColumnsObj = queryConditionColumms.getResultList();
		String tables = "0";
		for(DataSetColumn dsc : conditionColumnsObj){
			tables += ","+dsc.getDbtableId();
		}
		/**
		 * 查询涉及数据集对象
		 */
		Query queryDatasets = em.createQuery("SELECT datasets FROM DataSet datasets WHERE datasets.id in ("+tables+") or datasets.value = \""+AgileSearchConstance.CUST_MAIN_INFO_TABLE+"\"");
		List<DataSet> dss = queryDatasets.getResultList();
		
		String relTable = "0";
		for(DataSet table : dss){
			relTable += ","+table.getId();
		}
		/**
		 * 联表所需关联关系对象
		 */
		Query queryRelations = em.createQuery("SELECT osr FROM OcrmFASsRelation osr WHERE osr.joinLeftTable in ("+relTable+") and osr.joinRightTable in ("+relTable+")");
		List<OcrmFASsRelation> ofasrs = queryRelations.getResultList();
		
		String joinColumns = "0";
		for(OcrmFASsRelation ofasr:ofasrs){
			joinColumns += ","+ofasr.getJoinLeftCol()+","+ofasr.getJoinRightCol();
		}
		
		/**
		 * 连表所需列对象
		 */
		Query queryJoinColumms = em.createQuery("SELECT cols FROM DataSetColumn cols WHERE cols.id in ("+joinColumns+")");
		List<DataSetColumn> joinColumnsObj = queryJoinColumms.getResultList();
		
		//连表语句
		String joinSQL = AgileSearchConstance.createTableJoin(dss, ofasrs, joinColumnsObj);
		//SELECT语句
		String selectSQL = " custinfo.cust_id ";
		//条件语句
		String whereSQL = AgileSearchConstance.createWhereColumns(jaCondition, dss, conditionColumnsObj, radio);
		
		String finalSql =  "SELECT "+selectSQL+" FROM "+joinSQL+" WHERE "+whereSQL;
		
		return finalSql;
	}
	/**
	 * 拼接查询条件
	 * @param jaCondition  查询条件
	 * @param radio  交并关系
	 * @return
	 */
	public String generatorSql1(JSONArray jaCondition, String radio){
		
		String conditionColumns = "";
		for(Object o:jaCondition){
			JSONObject jo = (JSONObject) o;
			if("".equals(conditionColumns)){
				conditionColumns += jo.getLong("ss_col_item");
			}else{
				conditionColumns += "," + jo.getLong("ss_col_item");
			}
		}
		
		/**
		 * 查询条件中的列对象
		 */
		Query queryConditionColumms = em.createQuery("SELECT cols FROM DataSetColumn cols WHERE cols.id in ("+conditionColumns+")");
		List<DataSetColumn> conditionColumnsObj = queryConditionColumms.getResultList();
		String tables = "0";
		for(DataSetColumn dsc : conditionColumnsObj){
			tables += ","+dsc.getDbtableId();
		}
		/**
		 * 查询涉及数据集对象
		 */
		Query queryDatasets = em.createQuery("SELECT datasets FROM DataSet datasets WHERE datasets.id in ("+tables+") or datasets.value = \""+AgileSearchConstance.CUST_MAIN_INFO_TABLE+"\"");
		List<DataSet> dss = queryDatasets.getResultList();
		
		String relTable = "0";
		for(DataSet table : dss){
			relTable += ","+table.getId();
		}
		/**
		 * 联表所需关联关系对象
		 */
		Query queryRelations = em.createQuery("SELECT osr FROM OcrmFASsRelation osr WHERE osr.joinLeftTable in ("+relTable+") and osr.joinRightTable in ("+relTable+")");
		List<OcrmFASsRelation> ofasrs = queryRelations.getResultList();
		
		String joinColumns = "0";
		for(OcrmFASsRelation ofasr:ofasrs){
			joinColumns += ","+ofasr.getJoinLeftCol()+","+ofasr.getJoinRightCol();
		}
		
		/**
		 * 连表所需列对象
		 */
		Query queryJoinColumms = em.createQuery("SELECT cols FROM DataSetColumn cols WHERE cols.id in ("+joinColumns+")");
		List<DataSetColumn> joinColumnsObj = queryJoinColumms.getResultList();
		
		//连表语句
		String joinSQL = AgileSearchConstance.createTableJoin(dss, ofasrs, joinColumnsObj);
		//SELECT语句
		String selectSQL = " custinfo.cust_id,custinfo.cust_name,custinfo.IDENT_TYPE,custinfo.IDENT_NO,custinfo.CUST_TYPe ";
		//条件语句
		String whereSQL = AgileSearchConstance.createWhereColumns(jaCondition, dss, conditionColumnsObj, radio);
		
		String finalSql =  "SELECT "+selectSQL+" FROM "+joinSQL+" WHERE "+whereSQL;
		
		return finalSql;
	}

	
}