package com.yuchengtech.bcrm.customer.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bcrm.customer.constance.AgileSearchConstance;
import com.yuchengtech.bcrm.customer.model.AgileSearchCondition;
import com.yuchengtech.bcrm.system.model.DataSet;
import com.yuchengtech.bcrm.system.model.DataSetColumn;
import com.yuchengtech.bcrm.system.model.OcrmFASsRelation;
import com.yuchengtech.bob.model.AgileSearchSolution;
import com.yuchengtech.bob.model.OcrmFCiBaseSearchsolution;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.JdbcUtil;

/**
 * 灵活查询
 */

@Service
@Transactional(value = "postgreTransactionManager")
public class AgileSearchService {
	
	
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

	public void saveSolution(String solutionAttr, String conditions,String radio){
		
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userId = auth.getUserId();
		
		AgileSearchSolution solutionModel = new AgileSearchSolution();
		
		JSONObject solutionAttrObj =JSONObject.fromObject(solutionAttr);
		solutionModel.setSsName(solutionAttrObj.getString("solutionName"));
		solutionModel.setSsResult(solutionAttrObj.getString("ss_results"));
		solutionModel.setSsSort(solutionAttrObj.getString("ss_sort"));
		solutionModel.setSsUser(userId);
		solutionModel.setSsDate(new Date());
		em.persist(solutionModel);
		
		JSONArray solutionConditions = JSONArray.fromObject(conditions);
	
		for(Object sc : solutionConditions){
			JSONObject scd = (JSONObject)sc;
			AgileSearchCondition asc = new AgileSearchCondition();
			asc.setSsId(solutionModel.getId());
			asc.setSsColItem(scd.getLong("ss_col_item"));
			asc.setSsColOp(scd.getString("ss_col_op"));
			asc.setSsColValue(scd.getString("ss_col_value"));
			asc.setSsColJoin(radio);
			em.persist(asc);
		}
		auth.setPid(solutionModel.getId()+"");
	}
	
	public void saveSolution(String solutionAttr, String conditions,String radio,String solutionId){
		
		if(null == solutionId || solutionId.equals("")){
			/**
			 * 新增
			 */
			this.saveSolution(solutionAttr,conditions,radio);
			return;
		}else
		
		if(null == conditions || conditions.equals("")){
			/**
			 * 删除
			 */
			this.removeSolution(solutionId);
			return;
		}else {
		
			Query delete = em.createQuery("DELETE FROM AgileSearchCondition T WHERE T.ssId = "+solutionId);
			delete.executeUpdate();
		
			JSONArray solutionConditions = JSONArray.fromObject(conditions);
			for(Object sc : solutionConditions){
				JSONObject scd = (JSONObject)sc;
				AgileSearchCondition asc = new AgileSearchCondition();
				asc.setSsId(Long.valueOf(solutionId));
				asc.setSsColItem(scd.getLong("ss_col_item"));
				asc.setSsColOp(scd.getString("ss_col_op"));
				asc.setSsColValue(scd.getString("ss_col_value"));
				asc.setSsColJoin(radio);
				em.persist(asc);
			}
		}
	}
	
public void saveSolution1(String solutionAttr, String conditions,String radio,String groupId,String groupName){
		
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userId = auth.getUserId();
		
		OcrmFCiBaseSearchsolution solutionModel = new OcrmFCiBaseSearchsolution();
		
		JSONObject solutionAttrObj =JSONObject.fromObject(solutionAttr);
		solutionModel.setGroupName(groupName);
		solutionModel.setSsResult(solutionAttrObj.getString("ss_results"));
		solutionModel.setSsSort(solutionAttrObj.getString("ss_sort"));
		solutionModel.setGroupId(Long.valueOf(groupId));
		solutionModel.setSsUser(userId);
		solutionModel.setSsDate(new Date());
		em.persist(solutionModel);
		
		JSONArray solutionConditions = JSONArray.fromObject(conditions);
	
		for(Object sc : solutionConditions){
			JSONObject scd = (JSONObject)sc;
			AgileSearchCondition asc = new AgileSearchCondition();
			asc.setSsId(solutionModel.getId());
			asc.setSsColItem(scd.getLong("ss_col_item"));
			asc.setSsColOp(scd.getString("ss_col_op"));
			asc.setSsColValue(scd.getString("ss_col_value"));
			asc.setSsColJoin(radio);
			em.persist(asc);
		}
		auth.setPid(solutionModel.getId()+"");
	}


public void saveSolution1(String solutionAttr, String conditions,String radio,String groupId,String groupName,String flag){
		
		if("true".equals(flag)){
			/**
			 * 新增
			 */
			this.saveSolution1(solutionAttr,conditions,radio,groupId,groupName);
			return;
		}else {//修改
			Connection conn=null;
			Statement statement=null;
			ResultSet rs = null;
			Long id = 0l ;
			//查询得到筛选方案id
			try{
				conn = JdbcUtil.getConnection();
				statement = conn.createStatement();
				String sql = "select id from OCRM_F_CI_BASE_SEARCHSOLUTION t where group_id='"+groupId+"'";
				rs = statement.executeQuery(sql);
		
				if(rs.next()){
					id = rs.getLong("id");
				}
			}catch(Exception e){
	    		e.printStackTrace();
	    	}finally{
	    		JdbcUtil.close(rs, statement, conn);
	    	}
			//删除原条件
			Query delete = em.createQuery("DELETE FROM AgileSearchCondition T WHERE T.ssId="+id.toString());
			delete.executeUpdate();
			
			JSONArray solutionConditions = JSONArray.fromObject(conditions);
			for(Object sc : solutionConditions){
				JSONObject scd = (JSONObject)sc;
				AgileSearchCondition asc = new AgileSearchCondition();
				asc.setSsId(id);
				asc.setSsColItem(scd.getLong("ss_col_item"));
				asc.setSsColOp(scd.getString("ss_col_op"));
				asc.setSsColValue(scd.getString("ss_col_value"));
				asc.setSsColJoin(radio);
				em.persist(asc);
			}
		}
	}

	public void updateSolution(JSONArray resultNodeId,JSONArray resultRankValue, Long ssId) {
		StringBuilder sResultNode = new StringBuilder("");
		StringBuilder resultRank = new StringBuilder("");
		for (int i = 0; i < resultNodeId.size(); i++) {
			String sResultNodeId = resultNodeId.get(i).toString();
			String subResultNodeId = sResultNodeId.substring(1, sResultNodeId.length());
			sResultNode.append( subResultNodeId+',');
			resultRank.append(resultRankValue.get(i).toString()+',');
		}
		AgileSearchSolution solutionModel = em.find(AgileSearchSolution.class,
				ssId);
		solutionModel.setSsResult(sResultNode.toString());
		solutionModel.setSsSort(resultRank.toString());
		em.merge(solutionModel);

	};


	public void removeSolution(String solutionId) {
		Query deleteConditions = em.createQuery("DELETE FROM AgileSearchCondition T WHERE T.ssId IN ("+solutionId+")");
		Query deleteSolutions = em.createQuery("DELETE FROM AgileSearchSolution T WHERE T.id IN ("+solutionId+")");
		deleteConditions.executeUpdate();
		deleteSolutions.executeUpdate();
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
	public void saveCondition2(JSONArray attribute, JSONArray operate,
			JSONArray attributeValue, JSONArray nodeId, Long ssId,String radio) {
		remove(ssId);
		if (attribute.size() > 0) {
			for (int i = 0; i < attribute.size(); i++) {
				// attribute
				AgileSearchCondition conditionModel = new AgileSearchCondition();
				conditionModel.setSsId(ssId);
				String sNodeId = nodeId.get(i).toString();
				String subNodeId = sNodeId.substring(1, sNodeId.length());
				conditionModel.setSsColItem(Long.parseLong(subNodeId));
				conditionModel.setSsColOp(operate.getString(i));
				conditionModel.setSsColValue(attributeValue.get(i).toString());
				conditionModel.setSsColJoin(radio);
				em.persist(conditionModel);
			}

		}
	}
	
	/**
	 * 创建查询SQL
	 * @param jaCondition：查询条件字段属性
	 * @param jaColumns：结果集字段属性
	 * @param radio：交并操作
	 * @param groupParams：分组参数
	 * @param sumParams：统计参数
	 * @return 返回Map：包括SQL:结果SQL；lookupColumns：需要增加数据MAPPING的字段
	 */
	public Map<String, Object> generatorSql(JSONArray jaCondition, JSONArray jaColumns, String radio,String groupParams,String sumParams){
		
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
		/**
		 * 查询结果列对象
		 */
		String resultColumnsId = "0";
		for(Object o:jaColumns){
			JSONObject jo = (JSONObject) o;
			resultColumnsId += ","+jo.getLong("columnId");
		}
		Query queryColumns = em.createQuery("SELECT cols FROM DataSetColumn cols WHERE cols.id in ("+resultColumnsId+")");
		List<DataSetColumn> dscs = (List<DataSetColumn>)queryColumns.getResultList();
		String tables = "0";
		for(DataSetColumn dsc : dscs){
			tables += ","+dsc.getDbtableId();
		}
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
		String selectSQL = AgileSearchConstance.createSelectColumns(dss,dscs,jaColumns,groupParams,sumParams);
		//条件语句
		String whereSQL = AgileSearchConstance.createWhereColumns(jaCondition, dss, conditionColumnsObj, radio);
		//排序语句
		String orderSQL = "";
		//if(null != groupParams && !groupParams.equals(""))
			orderSQL = AgileSearchConstance.createOrderSQL(dss,dscs,jaColumns);
		//分组语句
		String groupBySQL = AgileSearchConstance.createGroupByColumns(dss,dscs,groupParams);
		
		Map<String, Object> lastResult = new HashMap<String,Object>();
		//获取需要数据字段编码映射的字段
		Map<DataSetColumn,String> look = new HashMap<DataSetColumn,String>();
		for(DataSetColumn l:dscs){
			if(null!=l.getNotes() && !l.getNotes().equals("")){
				for(Object o : jaColumns){
					JSONObject jo = (JSONObject)o;
					Long ti = jo.getLong("columnId");
					if(ti.equals(l.getId())){
						look.put(l, jo.getString("columnTotle"));
						break;
					}
				}
			}
		}
		
		String finalSql =  "SELECT DISTINCT "+selectSQL+" ,M.MGR_ID,M.INSTITUTION FROM "+joinSQL+" left join OCRM_F_CI_BELONG_CUSTMGR M ON custinfo.CUST_ID = M.CUST_ID WHERE ("+whereSQL+" )";

		
		if(!groupBySQL.equals("")){
			finalSql += " GROUP BY " + groupBySQL;
			//finalSql += " ORDER BY " + groupBySQL;
			orderSQL="";
			
		}
		
		if(!orderSQL.equals("")){
			finalSql += " ORDER BY "+orderSQL;
		}
		
		lastResult.put("SQL",finalSql);
		lastResult.put("lookupColumns", look);
		
		return lastResult;
	}
	
}