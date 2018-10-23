package com.yuchengtech.bob.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.yuchengtech.bcrm.customer.model.OcrmFCiGraph;
import com.yuchengtech.bcrm.customer.model.OcrmFCiGraphEdge;
import com.yuchengtech.bcrm.customer.model.OcrmFCiGraphVertex;
/**
 * 关系图详细信息VO,用于参数组合传递
 * @author WILLJOE
 *
 */
public class GraphDetailVo {
	
	private OcrmFCiGraph graph;
	private List<OcrmFCiGraphEdge> edges = new ArrayList<OcrmFCiGraphEdge>();
	private List<OcrmFCiGraphVertex> vertexes = new ArrayList<OcrmFCiGraphVertex>();
	
	public OcrmFCiGraph getGraph() {
		return graph;
	}
	public void setGraph(OcrmFCiGraph graph) {
		this.graph = graph;
	}
	public List<OcrmFCiGraphEdge> getEdges() {
		return edges;
	}
	public void setEdges(List<OcrmFCiGraphEdge> edges) {
		this.edges = edges;
	}
	public List<OcrmFCiGraphVertex> getVertexes() {
		return vertexes;
	}
	public void setVertexes(List<OcrmFCiGraphVertex> vertexes) {
		this.vertexes = vertexes;
	}
	
	private void addVertex(OcrmFCiGraphVertex vertex){
		vertexes.add(vertex);
	}
	
	private void addEdge(OcrmFCiGraphEdge edge){
		edges.add(edge);
	}
	
	@SuppressWarnings("unchecked")
	public void setInfos(String allInfo){
		
		JSONObject info = JSONObject.fromObject(allInfo);
		JSONObject graph = info.getJSONObject("graph");
		this.graph = (OcrmFCiGraph) JSONObject.toBean(graph,OcrmFCiGraph.class);
		
		JSONArray es = info.getJSONArray("edges");
		Collection<OcrmFCiGraphEdge> ec = JSONArray.toCollection(es, OcrmFCiGraphEdge.class);
		for(OcrmFCiGraphEdge e : ec){
			addEdge(e);
		}
		
		JSONArray vs = info.getJSONArray("vertexes");
		Collection<OcrmFCiGraphVertex> vc = JSONArray.toCollection(vs, OcrmFCiGraphVertex.class);
		for(OcrmFCiGraphVertex v : vc){
			addVertex(v);
		}
		
	}
	
}
