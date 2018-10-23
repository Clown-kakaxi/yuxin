package com.yuchengtech.emp.ecif.transaction.service;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.yuchengtech.emp.biappframe.authres.entity.BioneFuncInfo;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.bione.common.CommonTreeNode;
import com.yuchengtech.emp.ecif.base.util.BeanRefUtil;
import com.yuchengtech.emp.ecif.transaction.entity.CustomMsgException;
import com.yuchengtech.emp.ecif.transaction.entity.TxDef;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsg;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsgNode;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsgNodeAttr;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsgNodeAttrCt;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsgNodeAttrVO;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsgNodeFilter;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsgNodeTabMap;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsgNodeTabsRel;

@Service
@Transactional(readOnly = false)
public class TxMsgBS extends BaseBS<TxMsg> {

	protected static Logger log = LoggerFactory.getLogger(TxMsgBS.class);

	@Autowired
	private TxDefBS txDefBS;
	
	@Autowired
	private TxMsgNodeBS txMsgNodeBS;

	@Autowired
	private TxMsgNodeTabMapBS txMsgNodeTabMapBS;

	@Autowired
	private TxMsgNodeTabsRelBS txMsgNodeTabsRelBS;

	@Autowired
	private TxMsgNodeFilterBS txMsgNodeFilterBS;

	@Autowired
	private TxMsgNodeAttrBS txMsgNodeAttrBS;
	
	@Autowired
	private TxMsgNodeAttrCtBS txMsgNodeAttrCtBS;

	@Autowired
	private TxXmlReportBS xmlReportBS;


	/**
	 * 查找当前交易下的所有报文
	 * 
	 * @param logicSystem
	 * @param parentId
	 * @return
	 */
	public List<TxMsg> findAllMsgByTx(String txId) {

		String jql = "select TxMsg from TxMsg TxMsg where TxMsg.txId=?0";

		List<Object> values = Lists.newArrayList();
		values.add(new Long(txId));

		jql += " order by TxMsg.msgId asc";

		// 获取所有效菜单
		List<TxMsg> msgList = this.baseDAO.findWithIndexParam(jql, values.toArray());

		return msgList;

	}
	
	/**
	 * 根据节点获取交易定义
	 * @param nodeId
	 * @return
	 */
	public TxDef getTxByMsgId(Long msgId) {

		String jql = "select txDef from TxDef txDef,TxMsg txMsg where txDef.txId=TxMsg.txId and txMsg.msgId=" + msgId;

		List<Object> values = Lists.newArrayList();
		values.add(new Long(msgId));

		List<TxDef> objList =this.baseDAO.findWithNameParm(jql.toString(), null);
        return (TxDef)objList.get(0);
	}	
	
	/**
	 * 根据节点获取交易定义
	 * @param nodeId
	 * @return
	 */
	public TxDef getTxByNodeId(Long nodeId) {

		String jql = "select txDef from TxDef txDef,TxMsg txMsg,TxMsgNode txMsgNode where txDef.txId=TxMsg.txId and txMsg.msgId=txMsgNode.msgId and txMsgNode.nodeId=?0";

		List<Object> values = Lists.newArrayList();
		values.add(new Long(nodeId));

		List<TxDef> objList =this.baseDAO.findWithNameParm(jql.toString(), null);
        return (TxDef)objList.get(0);
	}	
	
	/**
	 * 查找当前报文下的所有功能
	 * 
	 * @param logicSystem
	 * @param parentId
	 * @return
	 */
	public List<TxMsgNode> findAllMsgNodes(String msgId, String parentId) {

		String jql = "select txMsgNode from TxMsgNode txMsgNode where txMsgNode.msgId=?0";

		List<Object> values = Lists.newArrayList();
		values.add(new Long(msgId));

		if (parentId != null) {

			jql += " and txMsgNode.upNodeId=?1";
			values.add(new Long(parentId));
		}

		jql += " order by txMsgNode.nodeId asc";

		// 获取所有效菜单
		List<TxMsgNode> msgNdeList = this.baseDAO.findWithIndexParam(jql, values.toArray());

		return msgNdeList;

	}
	
	/**
	 * 获取目标点的所有上级的列表
	 * @param msgNdeList
	 * 			     待查询列表
	 * @param Id
	 *            目标节点
	 * @return
	 */
	public String findUpId(List<BioneFuncInfo> msgNdeList, String Id) {
		for(int i = 0; i < msgNdeList.size(); i++) {
			if(Id.equals(msgNdeList.get(i).getFuncId())) {
				return msgNdeList.get(i).getUpId();
			}
		}
		return "0";
	}
	
	/**
	 * 构造菜单显示树，并返回第一层的节点
	 * 
	 * @param parentId
	 *            上级节点id
	 * @param isLoop
	 *            是否遍历子节点
	 * @return
	 */
	public List<CommonTreeNode> buildMsgTree(String txId, boolean isLoop) {

		List<CommonTreeNode> commonTreeNodeList = new ArrayList<CommonTreeNode>();

		List<TxMsg> msgList = this.findAllMsgByTx(txId);
		
		//根节点
		CommonTreeNode treeNode = new CommonTreeNode();
		treeNode.setId("0");
		treeNode.setUpId("0");
		treeNode.setIcon(null);
		treeNode.setText("报文树");
		treeNode.setData(null);
		commonTreeNodeList.add(treeNode);		
		
		//报文根节点（请求报文、响应报文）
		for (int i=0;i<msgList.size();i++) {
			
			TxMsg txMsg = msgList.get(i);
			treeNode = new CommonTreeNode();
			treeNode.setId(Integer.toString(i+1));
			treeNode.setUpId("0");
			treeNode.setIcon(null);
			treeNode.setText(txMsg.getMsgName());
			treeNode.setData(txMsg);
			commonTreeNodeList.add(treeNode);
			
			//找到所有的报文节点（请求报文、响应报文）
			List<TxMsgNode> msgNodeList =  this.findAllMsgNodes(Long.toString(txMsg.getMsgId()) , null);
					
			if (msgNodeList != null) {
				
				for (TxMsgNode txMsgNode :msgNodeList) {
					
					treeNode = new CommonTreeNode();
					treeNode.setId(new Long(txMsgNode.getNodeId()).toString());
					//请求或接受报文的根节点
					if(txMsgNode.getUpNodeId()==-1){
						treeNode.setUpId(new Long(i+1).toString());
					}else{
						treeNode.setUpId(new Long(txMsgNode.getUpNodeId()).toString());
					}
					
					treeNode.setText(txMsgNode.getNodeName());
					treeNode.setData(txMsgNode);
					
					commonTreeNodeList.add(treeNode);
				}
			}
		
		}
		return commonTreeNodeList;
	}


	/**
	 * 构造菜单显示树，并返回第一层的节点
	 * 
	 * @param parentId
	 *            上级节点id
	 * @param isLoop
	 *            是否遍历子节点
	 * @return
	 */
	public List<CommonTreeNode> buildRequestMsgTree(String txId, boolean isLoop) {

		List<CommonTreeNode> commonTreeNodeList = new ArrayList<CommonTreeNode>();

		List<TxMsg> msgList = this.findAllMsgByTx(txId);
		
		//根节点
		CommonTreeNode treeNode = new CommonTreeNode();
		treeNode.setId("0");
		treeNode.setUpId("0");
		treeNode.setIcon(null);
		treeNode.setText("报文树");
		treeNode.setData(null);
		commonTreeNodeList.add(treeNode);		
		
		//报文根节点（请求报文、响应报文）
		for (int i=0;i<msgList.size();i++) {
			
			TxMsg txMsg = msgList.get(i);
			
			//请求报文
			if(txMsg.getMsgTp().equals("1")){
				treeNode = new CommonTreeNode();
				treeNode.setId(Integer.toString(i+1));
				treeNode.setUpId("0");
				treeNode.setIcon(null);
				treeNode.setText(txMsg.getMsgName());
				treeNode.setData(txMsg);
				commonTreeNodeList.add(treeNode);
				
				//找到所有的报文节点（请求报文、响应报文）
				List<TxMsgNode> msgNodeList =  this.findAllMsgNodes(Long.toString(txMsg.getMsgId()) , null);
						
				if (msgNodeList != null) {
					
					for (TxMsgNode txMsgNode :msgNodeList) {
						
						treeNode = new CommonTreeNode();
						treeNode.setId(new Long(txMsgNode.getNodeId()).toString());
						//请求或接受报文的根节点
						if(txMsgNode.getUpNodeId()==-1){
							treeNode.setUpId(new Long(i+1).toString());
						}else{
							treeNode.setUpId(new Long(txMsgNode.getUpNodeId()).toString());
						}
						
						treeNode.setText(txMsgNode.getNodeName());
						treeNode.setData(txMsgNode);
						
						commonTreeNodeList.add(treeNode);
					}
				}
			}
		}
		return commonTreeNodeList;
	}

	
	/*
	 * @param entity
	 * @return 被持化的对象
	 *
	@Transactional(readOnly = false)
	public BioneFuncInfo merge(BioneFuncInfo entity) {
		return this.baseDAO.merge(entity);
	}*/
	
	
	/**
	 * 带返回值的保存
	 * 
	 * @param model
	 * @return
	 */
	@Transactional(readOnly = false)
	public TxMsg saveOrUpdate(TxMsg model) {
		return this.baseDAO.save(model);
	}
	
	
	@Transactional(readOnly = false,propagation=Propagation.REQUIRES_NEW)
	public void save(String msgnodeObj,String queryArrayObj, String tabrelArrayObj,String tabfilterArrayObj,String nodeattrArrayObj) {
			
		TxMsgNode tn = new TxMsgNode();
		if (msgnodeObj != null && !"".equals(msgnodeObj)) {
			// 解析json
			JSONObject qObj = JSONObject.fromObject(msgnodeObj);
			JSONArray msgnodeArray = qObj.getJSONArray("msgnodeArray");
			for (Iterator<?> it = msgnodeArray.iterator(); it.hasNext();) {
				JSONObject objTmp = (JSONObject) it.next();
				
				tn.setNodeId(new Long(objTmp.get("nodeId").toString()));
				tn.setMsgId(new Long(objTmp.get("msgId").toString()));
				tn.setUpNodeId(new Long(objTmp.get("upNodeId").toString()));
				tn.setNodeCode((String)objTmp.get("nodeCode"));
				tn.setNodeName((String)objTmp.get("nodeName"));
				tn.setNodeDesc((String)objTmp.get("nodeDesc"));
				tn.setNodeTp((String)objTmp.get("nodeTp"));
				tn.setNodeSeq(new Integer(objTmp.get("nodeSeq").toString()));
				tn.setNodeGroup((String)objTmp.get("nodeGroup"));
				tn.setNodeLabel((String)objTmp.get("nodeLabel"));
				tn.setNodeDisplay((String)objTmp.get("nodeDisplay"));

				if(objTmp.get("state")!=null){
					tn.setState((String)objTmp.get("state"));
				}

			}
		}
		
		List<TxMsgNodeTabMap> tabList = Lists.newArrayList();
		if (queryArrayObj != null && !"".equals(queryArrayObj)) {
			// 解析json
			JSONObject qObj = JSONObject.fromObject(queryArrayObj);
			JSONArray queryArray = qObj.getJSONArray("queryArray");
			for (Iterator<?> it = queryArray.iterator(); it.hasNext();) {
				JSONObject objTmp = (JSONObject) it.next();
				
				TxMsgNodeTabMap t = new TxMsgNodeTabMap();
				if (objTmp.get("nodeTabMapId") != null) {
					t.setNodeTabMapId( new Long(objTmp.get("nodeTabMapId").toString()));	
				}
				if (objTmp.get("tabId") != null) {
					t.setTabId(  (Integer)objTmp.get("tabId"));	
				}
				
				t.setNodeId(new Long(objTmp.get("nodeId").toString()));
				
				if(objTmp.get("tabRoleTp")!=null){
					t.setTabRoleTp((String)objTmp.get("tabRoleTp"));
				}
				
				if(objTmp.get("state")!=null&&!objTmp.get("state").equals("null")){
					t.setState((String)objTmp.get("state"));
				}
				
				tabList.add(t);
			}
		}
		
	
		List<TxMsgNodeTabsRel> tabrelsList = Lists.newArrayList();
		if (tabrelArrayObj != null && !"".equals(tabrelArrayObj)) {
			// 解析json
			JSONObject qObj = JSONObject.fromObject(tabrelArrayObj);
			JSONArray tabrelArray = qObj.getJSONArray("tabrelArray");
			for (Iterator<?> it = tabrelArray.iterator(); it.hasNext();) {
				JSONObject objTmp = (JSONObject) it.next();
				
				TxMsgNodeTabsRel t = new TxMsgNodeTabsRel();
				if (objTmp.get("nodeTabsRelId") != null) {
					t.setNodeTabsRelId( new Long(objTmp.get("nodeTabsRelId").toString()));	
				}
				t.setNodeId(new Long(objTmp.get("nodeId").toString()));
				if (objTmp.get("tabId1") != null) {
					t.setTabId1((Integer)objTmp.get("tabId1"));	
				}
				
				if (objTmp.get("colId1") != null) {
					t.setColId1( (Integer)objTmp.get("colId1"));	
				}
				
				if (objTmp.get("tabId2") != null) {
					t.setTabId2((Integer)objTmp.get("tabId2"));	
				}
				
				if (objTmp.get("colId2") != null) {
					t.setColId2((Integer)objTmp.get("colId2"));		
				}
				if(objTmp.get("rel")!=null&&!objTmp.get("rel").equals("null")){
					t.setRel((String)objTmp.get("rel"));
				}else{
					t.setRel("=");				//暂定
				}
				
				if(objTmp.get("state")!=null){
					t.setState((String)objTmp.get("state"));
				}
				
				tabrelsList.add(t);
			}
		}
		
		List<TxMsgNodeFilter> tabfilterList = Lists.newArrayList();
		if (tabfilterArrayObj != null && !"".equals(tabfilterArrayObj)) {
			// 解析json
			JSONObject qObj = JSONObject.fromObject(tabfilterArrayObj);
			JSONArray tabfilterArray = qObj.getJSONArray("tabfilterArray");
			for (Iterator<?> it = tabfilterArray.iterator(); it.hasNext();) {
				JSONObject objTmp = (JSONObject) it.next();
				
				TxMsgNodeFilter t = new TxMsgNodeFilter();
				if (objTmp.get("filterId") != null) {
					t.setFilterId( new Long(objTmp.get("filterId").toString()));	
					//t.setFilterId( new Long(1111));
				}
				t.setNodeId(new Long(objTmp.get("nodeId").toString()));
				
				if (objTmp.get("tabId") != null) {
					t.setTabId( (Integer)objTmp.get("tabId"));	
				}
				
				
				if (objTmp.get("colId") != null) {
					t.setColId( (Integer)objTmp.get("colId"));	
				}
				
				if (objTmp.get("attrId") != null&&!objTmp.get("attrId").equals("null")) {
					t.setAttrId(new Long((String)objTmp.get("attrId")));	
				}
				

				if(objTmp.get("rel")!=null){
					t.setRel((String)objTmp.get("rel"));
				}else{
					t.setRel("=");
				}
	
				if(objTmp.get("filterConditions")!=null){
					t.setFilterConditions((String)objTmp.get("filterConditions"));
				}
				
				if(objTmp.get("state")!=null){
					t.setState((String)objTmp.get("state"));
				}
				
				tabfilterList.add(t);
			}
		}
		
		List<TxMsgNodeAttrVO> attrList = Lists.newArrayList();
		if (nodeattrArrayObj != null && !"".equals(nodeattrArrayObj)) {
			// 解析json
			JSONObject qObj = JSONObject.fromObject(nodeattrArrayObj);
			JSONArray nodeattrArray = qObj.getJSONArray("nodeattrArray");
			for (Iterator<?> it = nodeattrArray.iterator(); it.hasNext();) {
				JSONObject objTmp = (JSONObject) it.next();
				
				TxMsgNodeAttrVO t = new TxMsgNodeAttrVO();
				if (objTmp.get("attrId") != null&&!objTmp.get("attrId").equals("null")) {
					t.setAttrId( new Long(objTmp.get("attrId").toString()));	
				}
				t.setNodeId(new Long(objTmp.get("nodeId").toString()));
				
				if(objTmp.get("attrCode")!=null){
					t.setAttrCode((String)objTmp.get("attrCode"));
				}

				if(objTmp.get("attrName")!=null){
					t.setAttrName((String)objTmp.get("attrName"));
				}
			
				if (objTmp.get("tabId") != null&&!objTmp.get("tabId").equals("null")) {
					t.setTabId( (Integer)objTmp.get("tabId"));	
				}
				
				
				if (objTmp.get("colId") != null&&!objTmp.get("colId").equals("null")) {
					t.setColId( (Integer)objTmp.get("colId"));	
				}
				

				if(objTmp.get("nulls")!=null){
					t.setNulls((String)objTmp.get("nulls"));
				}
				
				if(objTmp.get("dataType")!=null){
					t.setDataType((String)objTmp.get("dataType"));
				}
	
				if(objTmp.get("dataLen")!=null&&!objTmp.get("dataLen").equals("null")){
					t.setDataLen((Integer)objTmp.get("dataLen"));
				}

				if(objTmp.get("dataFmt")!=null){
					t.setDataFmt((String)objTmp.get("dataFmt"));
				}
				
				if(objTmp.get("checkRule")!=null){
					t.setCheckRule((String)objTmp.get("checkRule"));
				}
				
				if(objTmp.get("cateId")!=null&&!objTmp.get("cateId").equals("null")){
					t.setCateId(((String)objTmp.get("cateId")));
				}

				if(objTmp.get("fkAttrId")!=null&&!objTmp.get("fkAttrId").equals("null")&&!objTmp.get("fkAttrId").toString().equals("0")){
					t.setFkAttrId(new Long((String)objTmp.get("fkAttrId")));
				}
				
				if(objTmp.get("attrSeq")!=null&&!objTmp.get("attrSeq").equals("null")){
					t.setAttrSeq( new Integer(objTmp.get("attrSeq").toString()));
				}else{
					t.setAttrSeq(new Integer(0));
				}
				
				if(objTmp.get("defaultVal")!=null){
					t.setDefaultVal((String)objTmp.get("defaultVal"));
				}
				
				if(objTmp.get("state")!=null){
					t.setState((String)objTmp.get("state"));
				}
				
				if(objTmp.get("ctRule")!=null&&!objTmp.get("ctRule").equals("null")){
					t.setCtRule((String)objTmp.get("ctRule"));
				}
				
				attrList.add(t);
			}
		}
		
		
		//保存
		txMsgNodeBS.saveOrUpdateEntity(tn);
		
		txMsgNodeTabMapBS.removeEntityByProperty("nodeId", tn.getNodeId().toString());
		for (TxMsgNodeTabMap txMsgNodeTabMap : tabList) {
			txMsgNodeTabMapBS.updateEntity(txMsgNodeTabMap);
		}
		
		txMsgNodeTabsRelBS.removeEntityByProperty("nodeId", tn.getNodeId().toString());
		for (TxMsgNodeTabsRel txMsgNodeTabsRel : tabrelsList) {
			txMsgNodeTabsRelBS.updateEntity(txMsgNodeTabsRel);
		}
	
		txMsgNodeFilterBS.removeEntityByProperty("nodeId", tn.getNodeId().toString());
		for (TxMsgNodeFilter txMsgNodeFilter : tabfilterList) {
			txMsgNodeFilterBS.updateEntity(txMsgNodeFilter);
		}

		txMsgNodeAttrBS.removeEntityByProperty("nodeId", tn.getNodeId().toString());
		for (TxMsgNodeAttrVO txMsgNodeAttrVO : attrList) {
			
			TxMsgNodeAttr txMsgNodeAttr = new TxMsgNodeAttr();
			BeanUtils.copyProperties(txMsgNodeAttrVO, txMsgNodeAttr);
			
			txMsgNodeAttr = txMsgNodeAttrBS.updateEntity(txMsgNodeAttr);
			
			txMsgNodeAttrCtBS.removeEntityByProperty("attrId", txMsgNodeAttr.getAttrId().toString());
			String ctRule = txMsgNodeAttrVO.getCtRule();
			
			if(ctRule!=null){
				String[] rules = ctRule.split(",");
			    for(int j=0;j<rules.length;j++){
			    	String rule = rules[j];
			    	TxMsgNodeAttrCt ct = new TxMsgNodeAttrCt();
			    	ct.setAttrId(txMsgNodeAttr.getAttrId());
			    	ct.setCtFlag(rule.substring(0,1));
			    	ct.setCtRule(rule);
			    	ct.setCtDesc(getCtDesc(rule));
			    	ct.setState("1");
			    	
			    	txMsgNodeAttrCtBS.updateEntity(ct);
			    	
			    }
			}
			
			//获取引用当前节点作为父节点的所有下级节点,根据新生成的父节点ID更新子节点
			List<TxMsgNodeAttr> attrChildList =txMsgNodeAttrBS.getEntityListByProperty(TxMsgNodeAttr.class, "fkAttrId", txMsgNodeAttrVO.getAttrId());
			 for(int j=0;j<attrChildList.size();j++){
				 TxMsgNodeAttr child = (TxMsgNodeAttr)attrChildList.get(j);
				 child.setFkAttrId(txMsgNodeAttr.getAttrId());
				 txMsgNodeAttrBS.updateEntity(child);
			 }
			
		}
		
		//自动生成报文文件，更新交易修改时间
		TxMsg txMsg  = getEntityById(tn.getMsgId());
		
		TxDef txdef = txDefBS.getEntityById(txMsg.getTxId());
		txdef.setUpdateTm(new Timestamp(new Date().getTime()));
		txdef.setUpdateUser(BiOneSecurityUtils.getCurrentUserInfo().getLoginName());

		txDefBS.updateEntity(txdef);
	}
	
	
	@Transactional(readOnly = false)
	public void doUploadDatabase(Document doc , String saveType)
			throws InstantiationException, IllegalAccessException,
			InvocationTargetException {
		if (doc == null) {
			return;
		}
		
		//存在相同交易跳过
		if (!"1".equals(saveType)) {		
			// 判断数据库中是否有此交易
			if ("2".equals(saveType)) {
				// 检查TxCode
				Element e = (Element)doc.selectSingleNode("/TxDef/TxCode");
				String txCode = e.getText();

				if (txCode != null
						&& !StringUtils.isEmpty(txCode)) {
					String jql = "select count(t) from TxDef t where t.txCode=?0";
					Long count = baseDAO.findUniqueWithIndexParam(jql,txCode);
					if (count > 0) {
						return;
					}
				}
				return;
			}
		}
		
		//先删除以前的交易
		Element eTx = (Element)doc.selectSingleNode("/TxDef/TxCode");
		TxDef tempTxDef = txDefBS.getEntityByProperty(TxDef.class, "txCode", eTx.getText());
		if(tempTxDef!=null){
			deleteTx(tempTxDef.getTxId().toString());
		}
		
		//保存新交易
		saveTxByDoc(doc);
		
	}

	/**
	 * 根据XML文档保存交易
	 * @param doc
	 */
	public TxDef saveTxByDoc(Document doc) {
		
		Map txNodeFilterAttrCodeMap = new HashMap();
		Map txNodeAttrCodeMap = new HashMap();
		
		//先对响应报文引用属性不为空的查找其属性
		List filterElementList = doc.selectNodes("//TxMsgNodeFilter[AttrId != '']");		//引用请求报文的节点
		for (int i = 0; i < filterElementList.size(); i++) {
			Element filterEle = (Element)filterElementList.get(i);
			String FilterId= filterEle.element("FilterId").getText();
			String AttrId = filterEle.element("AttrId").getText();
			String TabId = filterEle.element("TabId").getText();
			String ColId = filterEle.element("ColId").getText();
			Element requestAttrEle = (Element)doc.selectSingleNode("//TxMsgNodeAttr[AttrId = "+ AttrId+"]");
			//txNodeFilterAttrCodeMap.put( filterEle.getUniquePath() , requestAttrEle.getUniquePath());		//将引用报文节点的路径与被引用报文节点路径关联起来
			txNodeFilterAttrCodeMap.put(FilterId,AttrId);
		}
				
		//保存交易
		TxDef txDef = new TxDef();
		Element e = (Element)doc.selectSingleNode("/TxDef");
		xml2Bean(e,txDef);
		txDef.setTxId(null);
		//自动生成报文文件，更新交易修改时间
		txDef.setUpdateTm(new Timestamp(new Date().getTime()));
		txDef.setUpdateUser(BiOneSecurityUtils.getCurrentUserInfo().getLoginName());		
		txDef = txDefBS.updateEntity(txDef);
		
		//保存报文
		List<Element> txMsgList = doc.selectNodes("/TxDef/TxMsg","MsgTp");
		for (int i = 0; i < txMsgList.size(); i++) {
			
			Element txMsgEle = txMsgList.get(i);
			TxMsg txMsg = new TxMsg();
			xml2Bean(txMsgEle,txMsg);
			txMsg.setMsgId(null);
			txMsg.setTxId(txDef.getTxId());
			
			txMsg = this.updateEntity(txMsg);
			
			//保存节点
			List<Element> txMsgNodeList = txMsgEle.elements("TxMsgNode") ;
			
			saveNode(txMsg,txMsgNodeList,null,null,txNodeFilterAttrCodeMap,txNodeAttrCodeMap);
		}
		
		return txDef;
	}

	private void saveNode(TxMsg txMsg, List<Element> txMsgNodeList,TxMsgNode upNode,Map pTxMsgNodeAttrMap,Map txNodeFilterAttrCodeMap, Map txNodeAttrCodeMap){


		for (int j = 0; j < txMsgNodeList.size(); j++) {
			

			Element txMsgNodeEle = txMsgNodeList.get(j);
			TxMsgNode txMsgNode = new TxMsgNode();
			xml2Bean(txMsgNodeEle,txMsgNode);
			txMsgNode.setNodeId(null);
			txMsgNode.setMsgId(txMsg.getMsgId());
			if(upNode == null){
				txMsgNode.setUpNodeId(new Long(-1));
			}else{
				txMsgNode.setUpNodeId(upNode.getNodeId());
			}

			txMsgNode = txMsgNodeBS.updateEntity(txMsgNode);
			
			//保存节点表
			List<Element> txMsgNodeTabMapList = txMsgNodeEle.elements("TxMsgNodeTabMap");
			for (int m = 0; m < txMsgNodeTabMapList.size(); m++) {
				
				Element txMsgNodeTabMapEle = txMsgNodeTabMapList.get(m);
				TxMsgNodeTabMap txMsgNodeTabMap = new TxMsgNodeTabMap();
				xml2Bean(txMsgNodeTabMapEle,txMsgNodeTabMap);
				txMsgNodeTabMap.setNodeTabMapId(null);
				txMsgNodeTabMap.setNodeId(txMsgNode.getNodeId());
				
				txMsgNodeTabMapBS.updateEntity(txMsgNodeTabMap);
			}
			
			
			//保存节点表关系
			List<Element> txMsgNodeTabsRelList = txMsgNodeEle.elements("TxMsgNodeTabsRel");
			for (int m = 0; m < txMsgNodeTabsRelList.size(); m++) {
				
				Element txMsgNodeTabsRelEle = txMsgNodeTabsRelList.get(m);
				TxMsgNodeTabsRel txMsgNodeTabsRel = new TxMsgNodeTabsRel();
				xml2Bean(txMsgNodeTabsRelEle,txMsgNodeTabsRel);
				txMsgNodeTabsRel.setNodeTabsRelId(null);
				txMsgNodeTabsRel.setNodeId(txMsgNode.getNodeId());
				
				txMsgNodeTabsRelBS.updateEntity(txMsgNodeTabsRel);
			}
			
			//保存节点过滤条件
			List<Element> txMsgNodeFilterList = txMsgNodeEle.elements("TxMsgNodeFilter");
			for (int m = 0; m < txMsgNodeFilterList.size(); m++) {
				
				Element txMsgNodeFilterEle = txMsgNodeFilterList.get(m);
				TxMsgNodeFilter txMsgNodeFilter = new TxMsgNodeFilter();
				xml2Bean(txMsgNodeFilterEle,txMsgNodeFilter);
				Long oldFilterId = txMsgNodeFilter.getFilterId();
				txMsgNodeFilter.setFilterId(null);
				txMsgNodeFilter.setNodeId(txMsgNode.getNodeId());
				
				if(txMsgNodeFilter.getAttrId()!=null){
					String  oldAttrId = (String)txNodeFilterAttrCodeMap.get(oldFilterId.toString());
					Long attrId = (Long)txNodeAttrCodeMap.get(new Long(oldAttrId));
					
					txMsgNodeFilter.setAttrId(attrId);
				}
				
				txMsgNodeFilter = txMsgNodeFilterBS.updateEntity(txMsgNodeFilter);
			}

			//保存节点属性
			List<Element> txMsgNodeAttrList = txMsgNodeEle.elements("TxMsgNodeAttr");
			Map txMsgNodeAttrMap = new HashMap();

			for (int m = 0; m < txMsgNodeAttrList.size(); m++) {
				
				Element txMsgNodeAttrEle = txMsgNodeAttrList.get(m);
				TxMsgNodeAttr txMsgNodeAttr = new TxMsgNodeAttr();
				xml2Bean(txMsgNodeAttrEle,txMsgNodeAttr);
				Long oldAttrId = txMsgNodeAttr.getAttrId();
				
				txMsgNodeAttr.setAttrId(null);
				txMsgNodeAttr.setNodeId(txMsgNode.getNodeId());
				
				//设置新生成的外键的属性
				if(txMsgNodeAttr.getFkAttrId()!= null){
					txMsgNodeAttr = getFk(txMsgNodeAttrEle,txMsgNodeAttr,pTxMsgNodeAttrMap);
				}
				
				txMsgNodeAttr = txMsgNodeAttrBS.updateEntity(txMsgNodeAttr);
				
				txMsgNodeAttrMap.put(txMsgNodeAttr.getAttrCode(), txMsgNodeAttr.getAttrId());
				
				txNodeAttrCodeMap.put(oldAttrId, txMsgNodeAttr.getAttrId());	//将新的属性编号与节点属性对应
			}
			
			saveNode(txMsg,txMsgNodeEle.elements("TxMsgNode"),txMsgNode,txMsgNodeAttrMap,txNodeFilterAttrCodeMap,txNodeAttrCodeMap);
			
		}
	}
	
	private TxMsgNodeAttr getFk(Element txMsgNodeAttrEle,TxMsgNodeAttr txMsgNodeAttr,Map txMsgNodeAttrMap){
		
		String fkAttrId = txMsgNodeAttr.getFkAttrId().toString();
		String attrCode = txMsgNodeAttr.getAttrCode();
		//获取父节点的父节点。即上级Node节点
		Element pNode = txMsgNodeAttrEle.getParent().getParent();
		
		//获取父节点的所有属性
		for(Iterator it = pNode.elements("TxMsgNodeAttr").iterator();it.hasNext();){  
			Element e = (Element)it.next();  
			
			//查找对应的外键属性
			if(e.element("AttrCode").getText().trim().equals(attrCode)){
				txMsgNodeAttr.setFkAttrId((Long)txMsgNodeAttrMap.get(txMsgNodeAttr.getAttrCode()));
				return txMsgNodeAttr;
			}
		}
		
		return txMsgNodeAttr;
	}
	
	
	private void xml2Bean(Element ele,Object obj){
		Map valMap = new HashMap<String, String>();
		for(Iterator it = ele.elements().iterator();it.hasNext();){  
			Element e = (Element)it.next();  
			valMap.put(e.getName().substring(0,1).toLowerCase().concat(e.getName().substring(1)), e.getText());
			
			BeanRefUtil.setFieldValue(obj, valMap);
		}
	}
	
	
	/**
	 * 删除交易及其关联表
	 * @param id
	 */
	public void deleteTx(String id){
		List<TxMsg> msgList = this.findAllMsgByTx(id);
		for (int i=0;i<msgList.size();i++) {
			
			TxMsg txMsg = msgList.get(i);
			Long msgId = txMsg.getMsgId();
			
			List<TxMsgNode> msgNodeList = this.findAllMsgNodes(Long.toString(txMsg.getMsgId()) , null);
			
			if (msgNodeList != null) {
				
				for (TxMsgNode txMsgNode :msgNodeList) {
					Long nodeId = txMsgNode.getNodeId();
							
					this.txMsgNodeTabMapBS.removeEntityByProperty("nodeId", nodeId.toString());
					this.txMsgNodeTabsRelBS.removeEntityByProperty("nodeId", nodeId.toString());
					this.txMsgNodeFilterBS.removeEntityByProperty("nodeId", nodeId.toString());
					this.txMsgNodeAttrBS.removeEntityByProperty("nodeId", nodeId.toString());
					
					List<TxMsgNodeAttr> msgNodeAttrList = this.txMsgNodeAttrBS.getEntityListByProperty(TxMsgNodeAttr.class, "nodeId", nodeId);
					for (TxMsgNodeAttr txMsgNodeAttr :msgNodeAttrList) {
						
						this.txMsgNodeAttrCtBS.removeEntityByProperty("attrId", txMsgNodeAttr.getAttrId().toString());
					}
					this.txMsgNodeBS.removeEntityById(nodeId);
					
				}
			}
			this.removeEntityById(msgId);
		}
		
		this.txDefBS.removeEntityById(new Long(id));
	}	
	
	
	private String getCtDesc(String ctRule){
		
		Map<String,String> m = new HashMap<String,String>();
		m.put("C01", "校验身份证");
		m.put("C02", "校验组织机构代码");
		m.put("C03", "校验邮件地址");
		m.put("C04", "校验手机号");
		m.put("C05", "校验电话号码");
		m.put("T01", "转换（身份证15转18位）");
		m.put("T02", "转换（身份证18转15位）");
		m.put("T03", "清洗（去空格）");
		m.put("T04", "清洗（全角转半角）");
		m.put("D01", "转码（正向转码）");
		m.put("R01", "转码（逆向转码）");
		
		return (String)m.get(ctRule);
	}
	
	
}
