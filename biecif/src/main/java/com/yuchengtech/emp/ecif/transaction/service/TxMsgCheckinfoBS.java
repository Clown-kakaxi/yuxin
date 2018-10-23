package com.yuchengtech.emp.ecif.transaction.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.ecif.transaction.entity.CustomMsgException;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsg;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsgCheckinfo;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsgNodeBFO;
@Service
@Transactional(readOnly = false)
public class TxMsgCheckinfoBS extends BaseBS<TxMsgCheckinfo> {
	
	private List<TxMsgNodeBFO> attrList = new ArrayList<TxMsgNodeBFO>();// 用于存放属性
	private Map<String, TxMsgNodeBFO> nodeMap = new HashMap<String, TxMsgNodeBFO>();// 用于存放节点
	private Map<String, Element> elementMap = new HashMap<String, Element>();// 用于存放元素
	private Document document = DocumentHelper.createDocument();
	private Element headElement = new DefaultElement("xs:schema");
	private boolean isXsd = true;
	
	@Autowired
	private TxMsgNodeAttrBS txMsgNodeAttrBS;

	
	public void createXMLAndXSDByTxCode(Long txId)
			throws CustomMsgException {
		// 查询该交易编码下得所有报文标识
		List<TxMsg> result = this.getEntityListByProperty( TxMsg.class,"txId", txId) ; 

		// 如果一个交易码没有对应的报文标识，或报文标识的数量不为2，认为数据有问题
		if (null == result || result.size() != 2) {
			throw new CustomMsgException("报文初始数据异常,一个交易编码有多个报文, 交易编码为  : ["
					+ txId + " ] ");
		}
		for (int i = 0; i < result.size(); i++) {
			TxMsg txMsg = (TxMsg)result.get(i);
			Long msgId = txMsg.getMsgId() ;
			this.createXMLAndXSDByMsgId(msgId.toString());
		}
	}
	
	/**
	 * 通过报文标识， 向TX_MSG_CHECKINFO表中插入,报文校验信息和报文描述信息
	 * 
	 * @param msgId
	 * @throws CustomMsgException
	 */
	
	public void createXMLAndXSDByMsgId(String msgId) throws CustomMsgException {

		TxMsgCheckinfo checkinfo = new TxMsgCheckinfo();
		checkinfo.setCheckId(Long.parseLong(msgId));
		checkinfo.setCheckinfo(this.getXSDByMsgId(msgId));
		checkinfo.setDescinfo(this.getXMLByMsgId(msgId));
		checkinfo.setMsgId(Long.parseLong(msgId));
		checkinfo.setState("1");
		this.saveOrUpdateEntity(checkinfo);
	}
	
	/**
	 * 通过报文标识，返回报文校验信息
	 * 
	 * @param String
	 *            msgId 报文标识
	 * @return String 报文校验
	 * @throws CustomMsgException
	 */
	private String getXSDByMsgId(String msgId) throws CustomMsgException {
		this.attrList.clear();
		this.elementMap.clear();
		this.nodeMap.clear();
		this.document.clearContent();
		this.headElement.clearContent();
		isXsd =true;
		
		headElement = new DefaultElement("xs:schema");
		// Document xsdDocument = DocumentHelper.createDocument();

		// 查询数据，封装成节点和属性两类
		List<TxMsgNodeBFO> nodeList =  txMsgNodeAttrBS.getTxMsgNodeAttrBFOList(msgId); //  this.customMsgDAO.findNodeByMsgId(msgId);
		if (nodeList == null) {
			throw new CustomMsgException("报文标识错误！");
		}
		document.add(this.headElement);
		this.headElement.addAttribute("xmlns:xs",
				"http://www.w3.org/2001/XMLSchema");
		this.headElement.addAttribute("elementFormDefault", "qualified");
		for (int i = 0; i < nodeList.size(); i++) {
			TxMsgNodeBFO node = nodeList.get(i);
			nodeMap.put(node.getNodeId(), node);// 用几点ID作为KEY值
			attrList.add(node);
		}

		// 生成所有的节点
		Collection<TxMsgNodeBFO> c = this.nodeMap.values();
		Iterator<TxMsgNodeBFO> it = c.iterator();
		int rootNodeSize = 0;

		while (it.hasNext()) {
			TxMsgNodeBFO node = it.next();
			if (node.getUpNodeId().equals("-1"))
				rootNodeSize++;
			if (rootNodeSize > 1)
				throw new CustomMsgException("报文数据有错误！(根节点不唯一)");
			this.addElementToUpElement(node);
		}
		System.out.println(document.asXML());
		return document.asXML();
	}

	/**
	 * 通过报文标识， 返回报文描述信息
	 * 
	 * @param String
	 *            msgId 报文标识
	 * @return String 报文描述信息
	 * @throws CustomMsgException
	 */
	private String getXMLByMsgId(String msgId) throws CustomMsgException {
		this.attrList.clear();
		this.elementMap.clear();
		this.nodeMap.clear();
		this.document.clearContent();
		isXsd = false;

		// 查询数据，封装成节点和属性两类
		List<TxMsgNodeBFO> nodeList = txMsgNodeAttrBS.getTxMsgNodeAttrBFOList(msgId); 
		if (nodeList == null) {
			throw new CustomMsgException("报文标识错误！");
		}
		int rootNodeSize = 0;
		
		for (int i = 0; i < nodeList.size(); i++) {
			TxMsgNodeBFO node = nodeList.get(i);
			if (node.getUpNodeId().equals("-1"))
				rootNodeSize++;
//			if (rootNodeSize > 1)
//				throw new CustomMsgException("报文数据有错误！(根节点不唯一)");
			nodeMap.put(node.getNodeId(), node);// 用几点ID作为KEY值
			attrList.add(node);
		}
		
		// 生成所有的节点
		Collection<TxMsgNodeBFO> c = this.nodeMap.values();
		Iterator<TxMsgNodeBFO> it = c.iterator();
		while (it.hasNext()) {
			TxMsgNodeBFO node = it.next();
			this.addElementToUpElement(node);
		}
		return document.asXML();

	}
	
	
	/**
	 * 把一个节点添加到它的父节点中
	 * 
	 * @param node
	 * @throws CustomMsgException
	 */
	private void addElementToUpElement(TxMsgNodeBFO node) throws CustomMsgException {
		// 在elementMap中，获得该节点的父节点
		
		Element upElement = this.elementMap.get(node.getUpNodeId());

		if (!node.getUpNodeId().equals("-1")
				&& nodeMap.get(node.getUpNodeId()) != null && upElement == null) {
			addElementToUpElement(nodeMap.get(node.getUpNodeId()));
		}
		if (!node.getUpNodeId().equals("-1")
				&& nodeMap.get(node.getUpNodeId()) == null) {
			//throw new CustomMsgException("报文数据有错误！"+node.getNodeCode()+"没有父节点");
		}
		upElement = this.elementMap.get(node.getUpNodeId());
		if (isXsd) {
			this.addXSDElements(upElement, node);
		} else {
			this.addXMLElements(upElement, node);
		}
	}
	
	private void addXSDElements(Element upElement, TxMsgNodeBFO node) {
		if (!node.isFlag()) {
			Element element = new DefaultElement("xs:element");
			if (upElement != null) {
				upElement.add(element);
			} else {
				this.headElement.add(element);
			}
			// 把该节点设为已添加过得节点
			node.setFlag(true);
			if("1".equals(node.getNodeGroup())){
				
				Element complexType = new DefaultElement("xs:complexType");
				element.add(complexType);				
				if((node.getNodeLabel()!=null&&node.getNodeLabel().equals("1"))){
					element.addAttribute("name", node.getNodeCode()+"List");
					complexType.addAttribute("mixed", "true");
				}

				Element choice = new DefaultElement("xs:choice");
				complexType.add(choice);
				choice.addAttribute("minOccurs", "0");
				choice.addAttribute("maxOccurs", "unbounded");
				node.setFlag(false);
				node.setNodeGroup("0");			//????
				this.addXSDElements(choice,node);
			}else{
				element.addAttribute("name", node.getNodeCode());
				// 判断该节点是否有子节点，如果有子节点，该几点的元素为xs:complexType，并把该节点装入elementMap中，否则为普通元素
				if (this.isChild((node.getNodeId()).toString(), this.attrList)) {
					Element complexType = new DefaultElement("xs:complexType");
					element.add(complexType);
					complexType.addAttribute("mixed", "true");
					Element choice = new DefaultElement("xs:all");
					complexType.add(choice);
					this.elementMap.put(node.getNodeId(), choice);
					this.addXSDAttrs(choice, node);
				} else {
					// element.addAttribute("type", "xs:string");
					this.addXSDAttrs(element, node);
				}
			}
		}

	}
	
	/**
	 * @param upElement
	 * @param node
	 */
	private void addXMLElements(Element upElement, TxMsgNodeBFO node) {
		if (!node.isFlag()) {
			
			Element element = new DefaultElement(node.getNodeCode());
			if (upElement != null) {
				upElement.add(element);
			} else {
				this.document.add(element);
			}
			// 把该节点设为已添加过得节点
			node.setFlag(true);
			if("1".equals(node.getNodeGroup())&&(node.getNodeLabel()!=null&&node.getNodeLabel().equals("1"))){
				element.setName(node.getNodeCode()+"List");
				element.addComment(node.getNodeName()+"组");
				node.setFlag(false);
				node.setNodeGroup("0");
				this.addXMLElements(element,node);
			}else{
				element.addComment(node.getNodeName());
				// 判断该节点是否有子节点，如果有子节点
				if (this.isChild((node.getNodeId()).toString(), this.attrList)) {
					this.elementMap.put(node.getNodeId(), element);
				}
				this.addXMLAttrs(element, node);
			}
			
		}
	}
	
	private void addXSDAttrs(Element upElement, TxMsgNodeBFO node) {
		for (TxMsgNodeBFO nodeAttr : attrList) {
			if (nodeAttr.getNodeId().equals(node.getNodeId())) {
				if (nodeAttr.getAttrId() != null
						&& !"".equals(nodeAttr.getAttrId())) {
					Element element = new DefaultElement("xs:element");
					element.addAttribute("name", nodeAttr.getAttrCode());
					upElement.add(element);
					// 判断该属性是否有校验，如果有添加该属性的校验

					Element simpleTypeElement = new DefaultElement(
							"xs:simpleType");
					element.add(simpleTypeElement);
					Element restrictionElement = new DefaultElement(
							"xs:restriction");
					simpleTypeElement.add(restrictionElement);

					Element patternElement;
//去掉长度校验					
//					if(nodeAttr.getDataLen() != null&& !nodeAttr.getDataLen().equals("")&& Integer.parseInt(nodeAttr.getDataLen())>0){
//						patternElement = new DefaultElement(
//								"xs:maxLength");
//						restrictionElement.add(patternElement);
//						patternElement.addAttribute("value",
//								nodeAttr.getDataLen());
//					}
					restrictionElement.addAttribute("base", "xs:string");
					patternElement = new DefaultElement(
							"xs:whiteSpace");
					restrictionElement.add(patternElement);
					patternElement.addAttribute("value",
							"collapse");
					
//					if(nodeAttr.getCheckRule() != null&& !nodeAttr.getCheckRule().equals("")){
////去掉正则校验							
////						patternElement = new DefaultElement(
////								"xs:pattern");
////						restrictionElement.add(patternElement);
////						patternElement.addAttribute("value",
////								nodeAttr.getCheckRule());
//					}else if(nodeAttr.getNulls()!=null && "N".equals(nodeAttr.getNulls())){
//						patternElement = new DefaultElement(
//								"xs:pattern");
//						restrictionElement.add(patternElement);
//						patternElement.addAttribute("value",
//								".+");
//					}
				}
			}
		}
	}
	
	/**
	 * @param element
	 * @param node
	 */
	private void addXMLAttrs(Element upElement, TxMsgNodeBFO node) {
		for (TxMsgNodeBFO nodeAttr : attrList) {
			if (nodeAttr.getNodeId().equals(node.getNodeId())) {
				if (nodeAttr.getAttrId() != null
						&& !"".equals(nodeAttr.getAttrId())) {
					Element element = new DefaultElement(nodeAttr.getAttrCode());
					element.addComment(nodeAttr.getAttrName());
					upElement.add(element);
				}
			}
		}
	}
	
	
	private boolean isChild(String nodeId, List list) {
		for (int i = 0; i < list.size(); i++) {
			TxMsgNodeBFO node = (TxMsgNodeBFO) list.get(i);

			// 检验该节点是否为其他节点的父节点
			if (nodeId.equals(node.getUpNodeId()))
				return true;

			// 检验该节点是否有元素
			else if (nodeId.equals(node.getNodeId())
					&& node.getAttrId() != null && !node.getAttrId().equals(""))
				return true;
		}
		return false;
	}
	
	
	@SuppressWarnings("unchecked")
	public SearchResult<TxMsgCheckinfo> getTxMsgNodeTabMapList(int firstResult, int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap,Integer nodeId) {
		
		StringBuffer jql = new StringBuffer("");
		jql.append("select TxMsgCheckinfo from TxMsgCheckinfo TxMsgCheckinfo where nodeId=" + nodeId);
		
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by TxMsgCheckinfo." + orderBy + " " + orderType);
		}	
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		SearchResult<TxMsgCheckinfo> TxMsgCheckinfo = this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql.toString(), values);
		return TxMsgCheckinfo;
	}
	
	@SuppressWarnings("unchecked")
	public SearchResult<TxMsgCheckinfo> getTxMsgNodeTabMapListByTab(int firstResult, int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap, String  nodeId) {
		
		StringBuffer jql = new StringBuffer("");
		jql.append(" select TxMsgCheckinfo from TxMsgCheckinfo TxMsgCheckinfo where nodeId="+nodeId);
		jql.append(" order by TxMsgCheckinfo.nodeId asc" );
		
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		SearchResult<TxMsgCheckinfo> TxMsgCheckinfo = this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql.toString(), values);
		return TxMsgCheckinfo;
	}	
	
	/**
	 * 获取信息, 用于生成下拉框
	 * @return
	 */
	public List<Map<String, String>> getComBoBox() {
		StringBuffer jql = new StringBuffer("select txCode, txName from TxDef  t");

		List<Object[]> objList = this.baseDAO.findWithNameParm(jql.toString(), null);
		List<Map<String, String>> harvComboList = Lists.newArrayList();
		Map<String, String> harvMap;
		for(Object[] obj: objList) {
			harvMap = Maps.newHashMap();
			harvMap.put("id", obj[0] != null ? obj[0].toString() : "");
			harvMap.put("text", obj[1] != null ? obj[1].toString() : "");
			harvComboList.add(harvMap);
		}
		return harvComboList;
	}
		
	
	/**
	 * 获取信息, 用于生成下拉框
	 * @return
	 */
	public List<Map<String, String>> getTabDefComBoBox() {
		StringBuffer jql = new StringBuffer("select tabId,tabDesc from TabDef  t");

		List<Object[]> objList = this.baseDAO.findWithNameParm(jql.toString(), null);
		List<Map<String, String>> harvComboList = Lists.newArrayList();
		Map<String, String> harvMap;
		for(Object[] obj: objList) {
			harvMap = Maps.newHashMap();
			harvMap.put("id", obj[0] != null ? obj[0].toString() : "");
			harvMap.put("text", obj[1] != null ? obj[1].toString() : "");
			
			harvComboList.add(harvMap);
		}
		return harvComboList;
	}
	
	/**
	 * 获取信息
	 * @return
	 */
	public List<Object[]> getVOList(Long nodeId) {
		
		StringBuffer sql = new StringBuffer(" select  NODE_TAB_MAP_ID,NODE_ID,t1.TAB_ID,TAB_ROLE_TP,STATE,TAB_NAME,TAB_DESC from TX_MSG_NODE_TAB_MAP t1, TabDef  t2");
								sql.append("  where t1.TAB_ID = t1.TAB_ID ");
								sql.append("  and t1.NODE_ID=?");
								
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("nodeId", nodeId);
		
		List<Object[]> objList = this.baseDAO.findByNativeSQLWithNameParam(sql.toString(), params);
		
		return objList;
	}
		
	
}
