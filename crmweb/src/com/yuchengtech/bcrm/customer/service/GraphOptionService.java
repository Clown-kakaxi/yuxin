package com.yuchengtech.bcrm.customer.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bcrm.customer.model.OcrmFCiGraph;
import com.yuchengtech.bcrm.customer.model.OcrmFCiGraphEdge;
import com.yuchengtech.bcrm.customer.model.OcrmFCiGraphVertex;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.bob.vo.GraphDetailVo;

@Service
@Transactional(value="postgreTransactionManager")
public class GraphOptionService {
	
	private EntityManager em;
	
	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}
	
	@SuppressWarnings("unchecked")
	public GraphDetailVo searchGraphDetail(Long id){
		
		GraphDetailVo detail = new GraphDetailVo();
		
		OcrmFCiGraph graph = em.find(OcrmFCiGraph.class, id);
		if(graph == null){
			return null;
		}else {
			detail.setGraph(graph);
			String searchEdeges = "SELECT e FROM OcrmFCiGraphEdge e WHERE e.graphId = ?1";
			Query edgesQuery = em.createQuery(searchEdeges);
			edgesQuery.setParameter(1, id);
			List<OcrmFCiGraphEdge> edges = edgesQuery.getResultList();
			detail.setEdges(edges);
			String searchVertexes = "SELECT v FROM OcrmFCiGraphVertex v WHERE v.graphId = ?1";
			Query vertexesQuery = em.createQuery(searchVertexes);
			vertexesQuery.setParameter(1, id);
			List<OcrmFCiGraphVertex> vertexes = vertexesQuery.getResultList();
			detail.setVertexes(vertexes);
			return detail;
		}
	}
	
	public GraphDetailVo createGraph(GraphDetailVo detail){
		
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();  
        String userId = auth.getUserId();
        String orgId = auth.getUnitId();
		OcrmFCiGraph graph = detail.getGraph();
		graph.setAuthor(userId);
		graph.setCreateOrg(orgId);
		graph.setCreateDate(new Date());
		em.persist(detail.getGraph());
		auth.setPid(detail.getGraph().getId()+"");
		for(OcrmFCiGraphVertex v : detail.getVertexes()){
			v.setGraphId(detail.getGraph().getId());
			em.persist(v);
		}
		for(OcrmFCiGraphEdge e : detail.getEdges()){
			e.setGraphId(detail.getGraph().getId());
			em.persist(e);
		}
		return detail;
	}
	
	@SuppressWarnings("unchecked")
	public void updateGraph(GraphDetailVo detail,Long id){
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();        
        String userId = auth.getUserId();
        OcrmFCiGraph graph = em.find(OcrmFCiGraph.class, id);
        graph.setUpdater(userId);
        graph.setUpdateDate(new Date());
        graph.setGraphName(detail.getGraph().getGraphName());
        graph.setGraphDescribe(detail.getGraph().getGraphDescribe());
        em.merge(graph);
        
    	String searchEdeges = "SELECT e FROM OcrmFCiGraphEdge e WHERE e.graphId = ?1";
		Query edgesQuery = em.createQuery(searchEdeges);
		edgesQuery.setParameter(1, id);
		List<OcrmFCiGraphEdge> edges = edgesQuery.getResultList();
		
		String searchVertexes = "SELECT v FROM OcrmFCiGraphVertex v WHERE v.graphId = ?1";
		Query vertexesQuery = em.createQuery(searchVertexes);
		vertexesQuery.setParameter(1, id);
		List<OcrmFCiGraphVertex> vertexes = vertexesQuery.getResultList();
        
        for(OcrmFCiGraphVertex o : detail.getVertexes()){
			em.persist(o);
		}
        for(OcrmFCiGraphVertex v : vertexes){
        	em.remove(v);
        }
		for(OcrmFCiGraphEdge o : detail.getEdges()){
			em.persist(o);
		}
		for(OcrmFCiGraphEdge e : edges){
			em.remove(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void deleteGraph(Long id){
		
		OcrmFCiGraph ofcg = em.find(OcrmFCiGraph.class, id);
		if(ofcg!=null){
			em.remove(ofcg);
			String searchEdeges = "SELECT e FROM OcrmFCiGraphEdge e WHERE e.graphId = ?1";
			Query edgesQuery = em.createQuery(searchEdeges);
			edgesQuery.setParameter(1, id);
			List<OcrmFCiGraphEdge> edges = edgesQuery.getResultList();
			for(OcrmFCiGraphEdge e : edges){
				em.remove(e);
			}
			String searchVertexes = "SELECT v FROM OcrmFCiGraphVertex v WHERE v.graphId = ?1";
			Query vertexesQuery = em.createQuery(searchVertexes);
			vertexesQuery.setParameter(1, id);
			List<OcrmFCiGraphVertex> vertexes = vertexesQuery.getResultList();
			for(OcrmFCiGraphVertex v : vertexes){
				em.remove(v);
			}
		}
	}
}
