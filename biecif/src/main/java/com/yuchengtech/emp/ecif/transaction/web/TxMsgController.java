package com.yuchengtech.emp.ecif.transaction.web;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.bione.common.CommonTreeNode;
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
import com.yuchengtech.emp.ecif.transaction.service.TxDefBS;
import com.yuchengtech.emp.ecif.transaction.service.TxMsgBS;
import com.yuchengtech.emp.ecif.transaction.service.TxMsgCheckinfoBS;
import com.yuchengtech.emp.ecif.transaction.service.TxMsgNodeAttrBS;
import com.yuchengtech.emp.ecif.transaction.service.TxMsgNodeAttrCtBS;
import com.yuchengtech.emp.ecif.transaction.service.TxMsgNodeBS;
import com.yuchengtech.emp.ecif.transaction.service.TxMsgNodeFilterBS;
import com.yuchengtech.emp.ecif.transaction.service.TxMsgNodeTabMapBS;
import com.yuchengtech.emp.ecif.transaction.service.TxMsgNodeTabsRelBS;
import com.yuchengtech.emp.ecif.transaction.service.TxXmlReportBS;

/**
 * <pre>
 * Title:CRUD操作演示
 * Description: 完成用户信息表的CRUD操作
 * </pre>
 * 
 * @author shangjifeng shangjf@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：许广源		  修改日期:     修改内容:
 * </pre>
 */
@Controller
@RequestMapping("/ecif/transaction/txmsg")
public class TxMsgController extends BaseController {
	@Autowired
	private TxMsgBS txMsgBS;
	
	@Autowired
	private TxMsgCheckinfoBS txMsgCheckinfoBS;	
	
	@Autowired
	private TxDefBS txDefBS;

	@Autowired
	private TxXmlReportBS txXmlReportBS;	

	@RequestMapping(value = "/{id}/index", method = RequestMethod.GET)
	public ModelAndView index(@PathVariable("id") String id) {
		return new ModelAndView("/ecif/transaction/txmsg-index", "id", id);
	}

	@RequestMapping(value = "/treeindex", method = RequestMethod.GET)
	public ModelAndView treeindex(String id) {
		return new ModelAndView("/ecif/transaction/txmsgtree-index", "id", id);
	}

	/**
	 * 加载下拉图标选择
	 */
	@RequestMapping("/buildIconCombox.*")
	@ResponseBody
	public List<Map<String, String>> buildIconCombox() {
		List<Map<String, String>> list = this.buildIconCombox("menuicons");
		return list;
	}

	/**
	 * 图标选择
	 * 
	 * @return
	 */
	@RequestMapping("/selectIcon")
	public ModelAndView selectIcon() {
		String iconsHTML = this.buildIconSelectHTML("menuicons");
		return new ModelAndView("/ecif/transaction/txmsg-icons", "iconsHTML", iconsHTML);
	}

	/**
	 * 获取生成树数据，以树显示功能树
	 */
	@RequestMapping("/{id}/list.*")
	@ResponseBody
	public List<CommonTreeNode> list(@PathVariable("id") String id) {
		
		List<CommonTreeNode> list = txMsgBS.buildMsgTree( id, true);
		//addProjectUrl(list);
		return list;
	}

	/**
	 * 获取请求报文生成树数据，以树显示功能树
	 */
	@RequestMapping("/{id}/requestmsglist.*")
	@ResponseBody
	public List<CommonTreeNode> requestmsglist(@PathVariable("id") String id) {
		
		List<CommonTreeNode> list = txMsgBS.buildRequestMsgTree( id, true);
		//addProjectUrl(list);
		return list;
	}
	
	
	/**
	 * 补全导航图标的URL地址
	 * @param list
	 */
	public void addProjectUrl(List<CommonTreeNode> list) {
		for (CommonTreeNode treeNode : list) {
			if (treeNode.getChildren() != null) {
				addProjectUrl(treeNode.getChildren());
			}
			if (treeNode.getIcon() != null)
				treeNode.setIcon(this.getProjectUrl() + treeNode.getIcon());
		}
	}

	/**
	 * 根据ID，加载数据
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public TxMsg show(@PathVariable("id") String id) {
		TxMsg model = this.txMsgBS.getEntityById(id);
		return model;
	}


	
	/**
	 * 保存属性，生成报文
	 */
	@RequestMapping("/save.*")
	public void save(String msgnodeObj,String queryArrayObj, String tabrelArrayObj,String tabfilterArrayObj,String nodeattrArrayObj) {
		
		//保存所有属性修改
		txMsgBS.save(msgnodeObj, queryArrayObj, tabrelArrayObj, tabfilterArrayObj, nodeattrArrayObj);
		
		//修改后自动生成报文文件
		createXMLAndXSD(msgnodeObj);
		
		//修改后生成新版本的xml文件
		TxDef tx = txMsgBS.getTxByMsgId(getMsgIdByJSON(msgnodeObj));
		txXmlReportBS.newVersion(tx.getTxId(),tx.getTxCode(),this.getRealPath());
	}
	
	/**
	 * 生成报文文件
	 * @param msgnodeObj
	 */
	private void createXMLAndXSD(String msgnodeObj){
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
				if(objTmp.get("state")!=null){
					tn.setState((String)objTmp.get("state"));
				}
			}
		}
		
		TxMsg txMsg  = txMsgBS.getEntityById(tn.getMsgId());
		try {
			this.txMsgCheckinfoBS.createXMLAndXSDByTxCode(txMsg.getTxId());
		} catch (CustomMsgException e) {
			e.printStackTrace();
		}

	}
	
	private Long getMsgIdByJSON(String msgnodeObj){
		Long msgId = null;
		if (msgnodeObj != null && !"".equals(msgnodeObj)) {
			// 解析json
			JSONObject qObj = JSONObject.fromObject(msgnodeObj);
			JSONArray msgnodeArray = qObj.getJSONArray("msgnodeArray");
			for (Iterator<?> it = msgnodeArray.iterator(); it.hasNext();) {
				JSONObject objTmp = (JSONObject) it.next();
				msgId = new Long(objTmp.get("msgId").toString());
			}
		}
		return msgId;
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
