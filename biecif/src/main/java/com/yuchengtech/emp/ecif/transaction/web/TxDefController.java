package com.yuchengtech.emp.ecif.transaction.web;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.bione.entity.page.Pager;
import com.yuchengtech.emp.ecif.base.util.BeanRefUtil;
import com.yuchengtech.emp.ecif.transaction.entity.TxDef;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsg;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsgNode;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsgNodeAttr;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsgNodeAttrCt;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsgNodeFilter;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsgNodeTabMap;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsgNodeTabsRel;
import com.yuchengtech.emp.ecif.transaction.entity.TxRecoverVO;
import com.yuchengtech.emp.ecif.transaction.service.TxDefBS;
import com.yuchengtech.emp.ecif.transaction.service.TxMsgBS;
import com.yuchengtech.emp.ecif.transaction.service.TxMsgNodeAttrBS;
import com.yuchengtech.emp.ecif.transaction.service.TxMsgNodeAttrCtBS;
import com.yuchengtech.emp.ecif.transaction.service.TxMsgNodeBS;
import com.yuchengtech.emp.ecif.transaction.service.TxMsgNodeFilterBS;
import com.yuchengtech.emp.ecif.transaction.service.TxMsgNodeTabMapBS;
import com.yuchengtech.emp.ecif.transaction.service.TxMsgNodeTabsRelBS;
import com.yuchengtech.emp.ecif.transaction.service.TxXmlReportBS;
import com.yuchengtech.emp.utils.SpringContextHolder;

/**
 * <pre>
 * Title:CRUD操作演示
 * Description: 完成表的CRUD操作 
 * </pre>	
 * @author shangjf  shangjf@yuchengtech.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：尚吉峰		  修改日期:     修改内容: 
 * </pre>
 */
@Controller
@RequestMapping("/ecif/transaction/txdef")
public class TxDefController extends BaseController {
	protected static Logger log = LoggerFactory.getLogger(TxDefController.class);

	@Autowired
	private TxDefBS txDefBS;

	@Autowired
	private TxMsgBS txMsgBS;
	
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
	private TxXmlReportBS txXmlReportBS;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "/ecif/transaction/txdef-index";
	}

	
	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping("/list.*")
	@ResponseBody
	public Map<String, Object> list(Pager pager) {
		SearchResult<TxDef> searchResult = this.txDefBS.getTxDefList(pager.getPageFirstIndex(),
				pager.getPagesize(), pager.getSortname(), pager.getSortorder(), pager.getSearchCondition());
		

		Map<String, Object> resDefMap = Maps.newHashMap();
		resDefMap.put("Rows", searchResult.getResult());
//		resDefMap.put("Rows", txClientAuthVOList);	
		resDefMap.put("Total", searchResult.getTotalCount());
		return resDefMap;
	}

	/**
	 * 用于添加，或修改时的保存对象
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void create(TxDef model) {
		
		if(model.getCreateUser()==null){
			model.setCreateTm(new Timestamp(new Date().getTime()));
			model.setCreateUser( BiOneSecurityUtils.getCurrentUserInfo().getLoginName());
		}
		model.setUpdateTm(new Timestamp(new Date().getTime()));
		model.setUpdateUser( BiOneSecurityUtils.getCurrentUserInfo().getLoginName());
		
		TxDef tx = this.txDefBS.updateEntity(model);
		
		//新增一个交易,自动添加两个报文
		if(model.getTxId()==null){
			
			TxMsg t1 = new TxMsg();
			
			t1.setTxId(tx.getTxId());
			t1.setMsgName("请求报文");
			t1.setMsgTp("1");
			t1.setMainMsgRoot("body");
			t1.setState("1");
			
			t1 = this.txMsgBS.saveOrUpdate(t1);
			
			TxMsg t2 = new TxMsg();
			
			t2.setTxId(tx.getTxId());
			t2.setMsgName("响应报文");
			t2.setMsgTp("2");
			t2.setMainMsgRoot("body");
			t2.setState("1");
			
			t2 = this.txMsgBS.saveOrUpdate(t2);
			
			//自动添加body节点			
			TxMsgNode node1 = new TxMsgNode();
			
			node1.setMsgId(t1.getMsgId());
			node1.setUpNodeId(new Long(-1));
			node1.setNodeCode("body");
			node1.setNodeName("body");
			node1.setNodeDesc("body");
			node1.setNodeTp("C");
			node1.setNodeSeq(1);
			node1.setNodeGroup("0");
			node1.setState("1");
		
			txMsgNodeBS.saveEntity(node1);
			
			//自动添加body节点			
			TxMsgNode node2 = new TxMsgNode();
			
			node2.setMsgId(t2.getMsgId());
			node2.setUpNodeId(new Long(-1));
			node2.setNodeCode("body");
			node2.setNodeName("body");
			node2.setNodeDesc("body");
			node2.setNodeTp("C");
			node2.setNodeSeq(1);
			node2.setNodeGroup("0");
			node2.setState("1");
		
			txMsgNodeBS.saveEntity(node2);
			
		}
		
		//修改后生成新版本的xml文件
		txXmlReportBS.newVersion(tx.getTxId(),tx.getTxCode(),this.getRealPath());

	}

	/**
	 * 根据ID，加载数据
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public TxDef show(@PathVariable("id") Long id) {
		TxDef model = this.txDefBS.getEntityById( id);
		//TxDef model = this.txDefBS.getTxDef(id);
		return model;
	}

	/**
	 * 执行修改前的数据加载
	 * 
	 * @return
	 * 		String	用于匹配结果页面
	 */
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") Long id) {
		return new ModelAndView("/ecif/transaction/txdef-edit", "id", id);
		
	}

	
	/**
	 * 执行复制前的数据加载
	 * 
	 * @return
	 * 		String	用于匹配结果页面
	 */
	@RequestMapping(value = "/{id}/copy", method = RequestMethod.GET)
	public ModelAndView copy(@PathVariable("id") Long id) {
		return new ModelAndView("/ecif/transaction/txdef-copy", "id", id);
		
	}
	
	
    /**
     * 交易复制
     * @param txId
     * @return
     */
	@RequestMapping(value = "/copytx", method = RequestMethod.POST)
	public void copytx(TxDef model) {
		
		Long oldtxid = model.getTxId();
		model.setTxId(null);
		
//		TxDef newtx = this.txDefBS.updateEntity(model);
//
//		copytxmsg(oldtxid.toString(),newtx.getTxId().toString());
		
		String xml = txXmlReportBS.getTxXml(oldtxid);
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xml);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	
		//重置复制后交易的内容
		Element e = (Element)doc.selectSingleNode("/TxDef");
		if(model.getState()!=null)
			e.element("State").setText(model.getState());
		if(model.getTxCfgTp()!=null)
			e.element("TxCfgTp").setText(model.getTxCfgTp());
		if(model.getTxCheckXsd()!=null)
			e.element("TxCheckXsd").setText(model.getTxCheckXsd());
		if(model.getTxCode()!=null)
			e.element("TxCode").setText(model.getTxCode());
		if(model.getTxCnName()!=null)
			e.element("TxCnName").setText(model.getTxCnName());
		if(model.getTxCustType()!=null)
			e.element("TxCustType").setText(model.getTxCustType());
		if(model.getTxDealClass()!=null)
			e.element("TxDealClass").setText(model.getTxDealClass());
		if(model.getTxDealEngine()!=null)
			e.element("TxDealEngine").setText(model.getTxDealEngine());
		if(model.getTxDesc()!=null)
			e.element("TxDesc").setText(model.getTxDesc());
		if(model.getTxDiscUrl()!=null)
			e.element("TxDiscUrl").setText(model.getTxDiscUrl());
		if(model.getTxLvl1Tp()!=null)
			e.element("TxLvl1Tp").setText(model.getTxLvl1Tp());
		if(model.getTxLvl2Tp()!=null)
			e.element("TxLvl2Tp").setText(model.getTxLvl2Tp());
		if(model.getTxName()!=null)
			e.element("TxName").setText(model.getTxName());
		if(model.getTxState()!=null)
			e.element("TxState").setText(model.getTxState());
//		e.element("UpdateUser").setText(model.getUpdateUser());
				
		//保存交易
		TxDef newtx = txMsgBS.saveTxByDoc(doc);
		
		//复制后生成新版本的xml文件
		txXmlReportBS.newVersion(newtx.getTxId(),newtx.getTxCode(),this.getRealPath());

	}
	
	/**
	 * 执行添加前页面跳转
	 * 
	 * @return
	 * 		String	用于匹配结果页面
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String editNew() {
		return "/ecif/transaction/txdef-edit";
	}
	
    /**
     * 交易文件下载
     * @param txId
     * @return
     */
	@RequestMapping(value = "/downfile", method = RequestMethod.GET)
	public ModelAndView downfile(String txId) {
		
		String tx[] = txId.split(",");
		String txName = "";
		
		for(int i=0;i<tx.length;i++){
			TxDef model = this.txDefBS.getEntityById(new Long(tx[i]));
			txName += model.getTxName()+",";
		}
		
		ModelMap mm = new ModelMap();
		mm.put("txId", txId);
		mm.put("txName", txName);
		return new ModelAndView("/ecif/transaction/txCheckInfoFileDownload", mm);
	}


	@RequestMapping(value = "/getnodetabs", method = RequestMethod.GET)
	public Map<String, String> getnodetabs(Long nodeId) {
		
		String tabids = "";
		List<Object[]> list = this.txMsgNodeTabMapBS.getVOList(nodeId);
		
		if(list!=null){
			for(int i=0;i<list.size();i++){
				Object[] o = (Object[])list.get(i);

				if(i==0){
//					tabids = ((BigDecimal)o[2]).toString();
					tabids = o[2].toString();
				}else{
//					tabids += ","  + ((BigDecimal)o[2]).toString();
					tabids += ","  + o[2].toString();
				}
			}
		}
		
		Map<String, String> returnMap = new HashMap<String, String>();
		returnMap.put("tabids", tabids);
		return returnMap;
	}
	
	/**
	 * 执行删除操作，可进行指删除
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void destroy(@PathVariable("id") String id) {
		String[] ids = id.split(",");
		if (ids.length > 1) {
			//this.txDefBS.removeEntityByProperty("txId", id);
			for (int i=0;i<ids.length;i++) {
				txMsgBS.deleteTx(ids[i]);
			}
		} else {
			txMsgBS.deleteTx(id);
		}
	}
	
	
	/**
	 * 跳转 添加服务授权页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/recover", method = RequestMethod.GET)
	public ModelAndView recover(String txCode) {
		return new ModelAndView("/ecif/transaction/txdef-recover", "txCode",
				txCode);
	}	
	
	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping("/recoverlist.*")
	@ResponseBody
	public Map<String, Object> recoverlist(String txCode) {
		
		List voList = new ArrayList();
		
		List<File> searchResult = this.txXmlReportBS.getTxDefRecoverList(this.getRealPath(),txCode);
		for(File file:searchResult){
			TxRecoverVO vo = new TxRecoverVO();
			vo.setTxCode(txCode);
			vo.setFileName(file.getName());
			
			SimpleDateFormat sdf= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			//前面的lSysTime是秒数，先乘1000得到毫秒数，再转为java.util.Date类型
			java.util.Date dt = new Date(file.lastModified() );  
			String sDateTime = sdf.format(dt);  //得到精确到秒的表示：08/31/2006 21:08:00
			vo.setRelPath("<A href=# onclick=javascript:window.open('"+ this.getRequest().getContextPath() +"/txversion/"+file.getName() +"')>"+"查看</A>");
			vo.setCreateTime(sDateTime);
			
			voList.add(vo);
		}

		Map<String, Object> resDefMap = Maps.newHashMap();
		resDefMap.put("Rows", voList);
		resDefMap.put("Total", voList.size());
		return resDefMap;
	}

	/* * 跳转 添加服务授权页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/recovertodb", method = RequestMethod.GET)
	public String recovertodb(String filename) {
		boolean flag = false;
		
		String filepath =this.getRequest().getSession().getServletContext().getRealPath("/txversion/")+ "/" +filename ;
		try {
			FileInputStream fis = new FileInputStream( filepath);
			SAXReader saxReader = new SAXReader();
			saxReader.setEncoding("UTF-8");
			Document doc = saxReader.read(fis);

			TxMsgBS bs = SpringContextHolder.getBean(TxMsgBS.class);
			bs.doUploadDatabase(doc, "1");		//覆盖模式
			
			flag = true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "/ecif/transaction/txdef-index";
	}		
	
	@RequestMapping(value = "/viewfile")
	public ModelAndView viewfile(String filename) {
		
		return new ModelAndView("/ecif/txversion/" + filename );
	}	
	

	/**
	 * 跳转 添加服务授权页面
	 * 
	 * @return
	 */
	@RequestMapping("/authservice")
	public ModelAndView authservice(String txId) {
		return new ModelAndView("/ecif/transaction/txserviceauth-index", "txId",
				txId);
	}
	

	/**
	 * 表单验证中的后台验证，验证模块标识是否已存在
	 */
	@RequestMapping(value = "/resDefNoValid")
	@ResponseBody
	public boolean resDefNoValid(String id,String txCode) {
		//TxDef model = txDefBS.findUniqueEntityByProperty("txCode", txCode);
		
		List<TxDef> models = txDefBS.findEntityListByProperty("txCode", txCode); 
		
		if (models != null&&models.size()>0){
			TxDef model = models.get(0);
			if(model.getTxId().toString().equals(id)){ //修改
				return true;
			}
			return false;
		}else
			return true;
	}

	/**
	 * 获取combobox
	 */
	@ResponseBody
	@RequestMapping("getComBoBox.*")
	public List<Map<String,String>> getComBoBox() {
		List<Map<String,String>> list = this.txDefBS.getComBoBox();
		return list;
	}
	
	public Map<String, String> convertList2Map(List<Map<String,String>> list){
		Map<String, String> newmap = new HashMap<String, String>();
		for(Map<String,String> map:list ){
			String id =    (String)map.get("id");
			String text =(String)map.get("text");
			
			newmap.put(id, text);
		}
		
		return newmap;
	}
	
	/**
	 * 复制一个交易
	 * @param txId
	 * @param newTxId
	 */
	public void copytxmsg(String txId, String newTxId){
		
		 List<TxMsg> list = this.txMsgBS.findAllMsgByTx(txId);
		 
		 //复制报文
		 for(TxMsg txMsg:list){
			 
			 Long oldMsgId = txMsg.getMsgId();
			 txMsg.setMsgId(null);
			 txMsg.setTxId(new Long(newTxId));
			 TxMsg t  = this.txMsgBS.updateEntity(txMsg);
			 
			 Long newMsgId = t.getMsgId();
			 
			 //复制节点
			 copytxnode(oldMsgId,newMsgId,new Long(-1),new Long(-1), new ArrayList());
		 }
	}
	
	
	public void copytxnode(Long oldMsgId,Long newMsgId,Long oldUpnodeId, Long upNodeId,List<TxMsgNodeAttr> newTxMsgNodeAttrList){
		
		 //List<TxMsgNode> txMsgNodeList =  this.txMsgNodeBS.getTxMsgNodeList(oldMsgId);
		 List<TxMsgNode> txMsgNodeList =  this.txMsgNodeBS.getTxMsgNodeChildList(oldMsgId,oldUpnodeId);
		 for(TxMsgNode txMsgNode:txMsgNodeList){
			 
			 Long oldNodeId = txMsgNode.getNodeId();
			 txMsgNode.setNodeId(null);
			 txMsgNode.setMsgId(newMsgId);
			 txMsgNode.setUpNodeId(upNodeId);
			 
			 TxMsgNode newTxMsgNode = this.txMsgNodeBS.updateEntity(txMsgNode); 
			 
			//复制节点对应数据库表
			List<TxMsgNodeTabMap> txMsgNodeTabMapList =  this.txMsgNodeTabMapBS.getEntityListByProperty(TxMsgNodeTabMap.class, "nodeId", oldNodeId);
			for(TxMsgNodeTabMap txMsgNodeTabMap:txMsgNodeTabMapList){
				 txMsgNodeTabMap.setNodeTabMapId(null);
				 txMsgNodeTabMap.setNodeId(newTxMsgNode.getNodeId());
				 this.txMsgNodeTabMapBS.updateEntity(txMsgNodeTabMap);
			}
			
			//复制报文节点关联关系表
			List<TxMsgNodeTabsRel> txMsgNodeTabsRelList =  this.txMsgNodeTabsRelBS.getEntityListByProperty(TxMsgNodeTabsRel.class, "nodeId", oldNodeId);
			for(TxMsgNodeTabsRel txMsgNodeTabsRel:txMsgNodeTabsRelList){
				txMsgNodeTabsRel.setNodeTabsRelId(null);
				txMsgNodeTabsRel.setNodeId(newTxMsgNode.getNodeId());
				 this.txMsgNodeTabsRelBS.updateEntity(txMsgNodeTabsRel);
			}
			
			//复制报文节点查询条件表
			List<TxMsgNodeFilter> txMsgNodeFilterList =  this.txMsgNodeFilterBS.getEntityListByProperty(TxMsgNodeFilter.class, "nodeId", oldNodeId);
			for(TxMsgNodeFilter txMsgNodeFilter:txMsgNodeFilterList){
				txMsgNodeFilter.setFilterId(null);
				txMsgNodeFilter.setNodeId(newTxMsgNode.getNodeId());
				this.txMsgNodeFilterBS.updateEntity(txMsgNodeFilter);
			}

			//复制报文节点属性表
			List<TxMsgNodeAttr> txMsgNodeAttrList =  this.txMsgNodeAttrBS.getEntityListByProperty(TxMsgNodeAttr.class, "nodeId", oldNodeId);
			
			for(TxMsgNodeAttr txMsgNodeAttr:txMsgNodeAttrList){
				
				Long oldAttrId = txMsgNodeAttr.getAttrId();
				txMsgNodeAttr.setAttrId(null);
				txMsgNodeAttr.setNodeId(newTxMsgNode.getNodeId());
				
				//外键已改变，重新设置父ID
				if(txMsgNodeAttr.getFkAttrId()!=null){
					for(TxMsgNodeAttr newtxMsgNodeAttr:newTxMsgNodeAttrList){
						if(newtxMsgNodeAttr.getAttrCode().equals(txMsgNodeAttr.getAttrCode()) ){
							txMsgNodeAttr.setFkAttrId(newtxMsgNodeAttr.getAttrId());
						}
					}
				}
				TxMsgNodeAttr newTxMsgNodeAttr = this.txMsgNodeAttrBS.updateEntity(txMsgNodeAttr);
				
				newTxMsgNodeAttrList.add(newTxMsgNodeAttr);
				
				//复制报文校验转换配置表
				List<TxMsgNodeAttrCt> txMsgNodeAttrCtList =  this.txMsgNodeAttrCtBS.getEntityListByProperty(TxMsgNodeAttrCt.class, "attrId", oldAttrId);
				for(TxMsgNodeAttrCt TxMsgNodeAttrCt:txMsgNodeAttrCtList){
					TxMsgNodeAttrCt.setCtId(null);
					TxMsgNodeAttrCt.setAttrId(newTxMsgNodeAttr.getAttrId());
					this.txMsgNodeAttrCtBS.updateEntity(TxMsgNodeAttrCt);
				}
			}
			
			//递归
			copytxnode(oldMsgId,newMsgId,oldNodeId,newTxMsgNode.getNodeId(),newTxMsgNodeAttrList);
		 }
	}
	
	private Map<String, Method> getClassMethodMap(Class clazz, String type) {
		Map<String, Method> mm = Maps.newHashMap();
		Method[] ma = clazz.getDeclaredMethods();
		for (int i = 0; i < ma.length; i++) {
			Method m = ma[i];
			String name = m.getName();
			if (name.startsWith(type)) {
				String key = name.substring(3);
				mm.put(key, m);
			}
		}
		return mm;
	}
		
}
