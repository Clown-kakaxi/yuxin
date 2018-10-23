package com.yuchengtech.bcrm.customer.service;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bcrm.customer.model.OcrmFCiCustgroupGraph;
import com.yuchengtech.bcrm.customer.model.OcrmFCiCustgroupGraphEdge;
import com.yuchengtech.bcrm.customer.model.OcrmFCiCustgroupGraphVert;
import com.yuchengtech.bcrm.customer.vo.CustGroupGraphDetailVo;
import com.yuchengtech.bob.common.CommonService;

/**
 * @描述：客户管理->客户群组管理->客户群组成员关系图维护功能操作Service
 * @author wzy
 * @date:2013-04-11
 */
@Service
@Transactional(value = "postgreTransactionManager")
public class CustGroupMemberGraphOperationService extends CommonService {

	private EntityManager em;

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	// 查询关系图数据详细信息
	@SuppressWarnings("unchecked")
	public CustGroupGraphDetailVo getGraphDetail(String graphId) {
		CustGroupGraphDetailVo vo = null;
		OcrmFCiCustgroupGraph graph = null;
		graph = new OcrmFCiCustgroupGraph();
		vo = new CustGroupGraphDetailVo();
		vo.setGraph(graph);
		String searchEdeges = "select e from OcrmFCiCustgroupGraphEdge e where e.custgroupId = ?1";
		Query edgesQuery = em.createQuery(searchEdeges);
		edgesQuery.setParameter(1, new BigDecimal(graphId));
		List<OcrmFCiCustgroupGraphEdge> edges = edgesQuery.getResultList();
		vo.setEdges(edges);
		String searchVertexes = "select v from OcrmFCiCustgroupGraphVert v where v.custgroupId = ?1";
		Query vertexesQuery = em.createQuery(searchVertexes);
		vertexesQuery.setParameter(1, new BigDecimal(graphId));
		List<OcrmFCiCustgroupGraphVert> vertexes = vertexesQuery
				.getResultList();
		vo.setVertexes(vertexes);
		return vo;
	}

	// 新增保存客户群组成员关系图数据
	public void saveCustGroupMemberRelationGraph(CustGroupGraphDetailVo model) {
		// 1、先删除所有的关系图数据
		this.delRelationGraphData(model.getGraph().getId());
		// 2、保存关系图中包含的客户信息
		for (OcrmFCiCustgroupGraphVert v : model.getVertexes()) {
			v.setCustgroupId(new BigDecimal(model.getGraph().getId()));
			em.persist(v);
		}
		// 3、保存关系图中包包含的关系数据
		for (OcrmFCiCustgroupGraphEdge e : model.getEdges()) {
			e.setCustgroupId(new BigDecimal(model.getGraph().getId()));
			em.persist(e);
		}
	}

	// 删除关系图数据
	public void delRelationGraphData(long custgroupId) {
		String sql = null;
		sql = "delete from OcrmFCiCustgroupGraphVert t";
		sql += " where t.custgroupId = " + new BigDecimal(custgroupId);
		em.createQuery(sql).executeUpdate();
		sql = "delete from OcrmFCiCustgroupGraphEdge t";
		sql += " where t.custgroupId = " + new BigDecimal(custgroupId);
		em.createQuery(sql).executeUpdate();
	}
}