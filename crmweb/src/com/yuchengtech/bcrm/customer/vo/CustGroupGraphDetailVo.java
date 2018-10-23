package com.yuchengtech.bcrm.customer.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.yuchengtech.bcrm.customer.model.OcrmFCiCustgroupGraph;
import com.yuchengtech.bcrm.customer.model.OcrmFCiCustgroupGraphEdge;
import com.yuchengtech.bcrm.customer.model.OcrmFCiCustgroupGraphVert;

/**
 * @描述：客户群组关系图详细信息VO,用于参数组合传递
 * @author wzy
 * @date 2013-04-11
 */
public class CustGroupGraphDetailVo {

	private OcrmFCiCustgroupGraph graph;// 关系图对象
	private List<OcrmFCiCustgroupGraphEdge> edges = new ArrayList<OcrmFCiCustgroupGraphEdge>();// 关系图关系
	private List<OcrmFCiCustgroupGraphVert> vertexes = new ArrayList<OcrmFCiCustgroupGraphVert>();// 关系图成员

	public OcrmFCiCustgroupGraph getGraph() {
		return graph;
	}

	public void setGraph(OcrmFCiCustgroupGraph graph) {
		this.graph = graph;
	}

	public List<OcrmFCiCustgroupGraphEdge> getEdges() {
		return edges;
	}

	public void setEdges(List<OcrmFCiCustgroupGraphEdge> edges) {
		this.edges = edges;
	}

	public List<OcrmFCiCustgroupGraphVert> getVertexes() {
		return vertexes;
	}

	public void setVertexes(List<OcrmFCiCustgroupGraphVert> vertexes) {
		this.vertexes = vertexes;
	}

	private void addVertex(OcrmFCiCustgroupGraphVert vertex) {
		vertexes.add(vertex);
	}

	private void addEdge(OcrmFCiCustgroupGraphEdge edge) {
		edges.add(edge);
	}

	@SuppressWarnings("unchecked")
	public void setInfos(String allInfo) {
		JSONObject info = JSONObject.fromObject(allInfo);
		JSONObject graph = info.getJSONObject("graph");
		this.graph = (OcrmFCiCustgroupGraph) JSONObject.toBean(graph,
				OcrmFCiCustgroupGraph.class);

		JSONArray es = info.getJSONArray("edges");
		Collection<OcrmFCiCustgroupGraphEdge> ec = JSONArray.toCollection(es,
				OcrmFCiCustgroupGraphEdge.class);
		for (OcrmFCiCustgroupGraphEdge e : ec) {
			addEdge(e);
		}

		JSONArray vs = info.getJSONArray("vertexes");
		Collection<OcrmFCiCustgroupGraphVert> vc = JSONArray.toCollection(vs,
				OcrmFCiCustgroupGraphVert.class);
		for (OcrmFCiCustgroupGraphVert v : vc) {
			addVertex(v);
		}
	}
}