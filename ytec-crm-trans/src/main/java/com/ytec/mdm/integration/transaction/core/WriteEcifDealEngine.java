/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.transaction.core
 * @文件名：WriteEcifDealEngine.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:18:05
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 *
 */
package com.ytec.mdm.integration.transaction.core;

import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.MethodUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.bo.WriteModel;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.exception.BizException;
import com.ytec.mdm.base.util.EcifPubDataUtils;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.NameUtil;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.domain.biz.AcrmFCiPotCusCom;
import com.ytec.mdm.domain.txp.TxMsgNodeAttr;
import com.ytec.mdm.domain.txp.TxNode4CRM;
import com.ytec.mdm.integration.check.bs.TxBizRuleFactory;
import com.ytec.mdm.integration.transaction.bs.ServiceEntityMgr;
import com.ytec.mdm.integration.transaction.facade.IEcifBizLogic;
import com.ytec.mdm.server.common.BusinessCfg;

/**
 * @项目名称：ytec-mdm-ecif
 * @类名称：WriteEcifDealEngine
 * @类描述：写交易处理引擎
 * @功能描述:
 * @创建人：wangtb@yuchengtech.com
 * @创建时间：2013-12-17 上午11:18:05
 *                  -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class WriteEcifDealEngine extends AbstractEcifDealEngine {
	//add by liuming 20170719
	private JPABaseDAO baseDAO;

	private static Logger log = LoggerFactory.getLogger(WriteEcifDealEngine.class);
	/**
	 * @属性名称:authType
	 * @属性描述:数据权限类型
	 * @since 1.0.0
	 */
	private String authType = null;
	/**
	 * @属性名称:authCode
	 * @属性描述:数据权限码
	 * @since 1.0.0
	 */
	private String authCode = null;

	@Override
	public void execute(EcifData data) {
		try {
			//add by liuming 20170719
			baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
			
			this.ecifData = data;
			this.ecifData.setWriteModelObj(new WriteModel());
			this.txModel = TxModelHolder.getTxModel(data.getTxCode());
			WriteModel generalInfoList = this.ecifData.getWriteModelObj();
			if (StringUtil.isEmpty(data.getAuthCode())) {
				authType = (String) data.getParameterMap().get(MdmConstants.AUTH_TYPE);
				authCode = (String) data.getParameterMap().get(MdmConstants.AUTH_CODE);
			} else {
				authCode = data.getAuthCode();
				authType = (String) data.getParameterMap().get(MdmConstants.AUTH_TYPE);
			}

			// 获得过滤条件节点
			this.ecifData.getWriteModelObj().getOperMap().putAll(data.getParameterMap());

			// 从报文中获取数据并封装到数据操作对象中，如操作失败则返回
			boolean ref = this.getTableNodeObect(ecifData.getPrimalDoc(), generalInfoList);
			if (!ref) {
				log.error("从报文中获取数据并封装到数据操作对象中，操作失败");
				return;
			}
			/**
			 * 交易处理类
			 */
			String txDealClass = txModel.getTxDef().getDealClass();
			IEcifBizLogic createBizLogic = (IEcifBizLogic) SpringContextUtils.getBean(txDealClass);
			if (createBizLogic == null) {
				log.error("交易处理类为空");
				this.ecifData.setStatus(ErrorCode.ERR_POJO_IS_NULL);
				return;
			}
			//add by liuming 客户名已存在，保存失败
			if (ecifData.getTxCode() != null 
					&& ecifData.getTxCode().equals("addPotenCust")) {//移动信贷新增潜在客户
				boolean isNew = true;//是否新增潜在客户
				List list = ecifData.getWriteModelObj().getOpModelList();
				if (list != null && list.size() > 0) {
					if (list.get(0).getClass().equals(AcrmFCiPotCusCom.class)) {
						AcrmFCiPotCusCom cuscom = (AcrmFCiPotCusCom) list.get(0);
						// 新客户名称
						String custName = (cuscom != null ? cuscom.getCusName().trim(): "");
						// 旧客户名称
						String oldCustName = "";
						String custId = (cuscom != null ? cuscom.getCusId(): "");
						// 根据custId判断是新增还是修改
						String sql1 = "select c.cus_name from ACRM_F_CI_POT_CUS_COM c where c.cus_id ='"+ custId + "'";
						List list1 = baseDAO.findByNativeSQLWithIndexParam(sql1, null);
						if (list1 != null && list1.size() > 0) {
							// 修改
							isNew = false;
							oldCustName = (list1.get(0) == null ? "" : list1.get(0).toString());
							if (!custName.equals(oldCustName)) {
								// 修改了客户名称，暂不处理。
							}
						} else {
							// 新增
							String sql2 = "select 1 from acrm_f_ci_customer c where c.cust_name ='"
									+ custName + "'";
							List list2 = baseDAO.findByNativeSQLWithIndexParam(
									sql2, null);
							if (list2 != null && list2.size() > 0) {
								log.error("客户已存在");
								isNew = false;
								this.ecifData.setStatus(ErrorCode.ERR_ECIF_EXIST_CUST);
								//this.ecifData.setStatus(ErrorCode.SUCCESS);
								return;
							}else{
								isNew = true;
							}
						}
					}
				}
				createBizLogic.process(this.ecifData);
				//add by liuming 20170720
				if(isNew){
				    saveToCustomer(this.ecifData);
				}
			}else{
				createBizLogic.process(this.ecifData);
			}
			if (this.ecifData.isSuccess()) {
				/***
				 * 打印数据变更记录
				 */
				if (this.ecifData.getDataSynchro() != null && BusinessCfg.getBoolean("printChangeLog")) {
					log.info("客户[{}]信息变更如下:", this.ecifData.getEcifCustNo());
					for (Object o : this.ecifData.getDataSynchro()) {
						log.info(o.toString());
					}
				}
			}
		} catch (Exception e) {
			log.error("错误信息", e);
			this.ecifData.setStatus(ErrorCode.ERR_SERVER_BIZLOGIC_ERROR);
			return;
		}
		if (this.ecifData.getRepNode() != null) { return; }
		Element responseEle = DocumentHelper.createElement("ResponseBody");
		this.ecifData.setRepNode(responseEle);
		return;
	}

	/**
	 * @param doc 请求报文体文档节点
	 * @param info 写交易数据模型（数据结构），用于获取请求报文中数据对象，将解析后的对象存储于OpModelList中
	 * @return
	 */
	private boolean getTableNodeObect(Document doc, WriteModel info) {

		Element root = doc.getRootElement();
		List<TxNode4CRM> nodeList = this.getTxModel().getTxNodeList();
		for (TxNode4CRM nodeDef : nodeList) {
			String className = nodeDef.getTable();
			String xpath = nodeDef.getXpath();
			try {
				// 根据交易配置(transConf.xml)中对于节点的配置信息，获取XML报文节点，取得数据，如报文节点中数据为空，则继续
				List<Element> table_s = (List<Element>) root.selectNodes(xpath);
				if (table_s == null || table_s.size() == 0) {
					continue;
				}
				for (Element table : table_s) {
					Object clazz = Class.forName(className).newInstance();
					List<Element> column_s = table.elements();
					for (Element column : column_s) {
						String fieldValue = column.getTextTrim();
						if (!StringUtil.isEmpty(fieldValue)) {

							// 属性名、方法名获取、拼接
							String fieldName = column.getName();
							Field field = clazz.getClass().getDeclaredField(fieldName);

							// 原则上裸体类中setter方法名自动生成，与属性名相关联，如有特殊属性对应setter方法名不符合此规范，则会出异常。
							String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

							field.setAccessible(true);
							try {
								// 需要根据JPA实体类中属性具体数据类型做反射处理，否则会出异常
								Class fieldType = field.getType();
								if (fieldType.equals(Long.class) || fieldType.equals(long.class)) {
									// LONG 类型
									MethodUtils.invokeMethod(clazz, methodName, Long.parseLong(fieldValue));
								} else if (fieldType.equals(String.class)) {
									// 字符类型
									MethodUtils.invokeMethod(clazz, methodName, fieldValue);
								} else if (fieldType.equals(BigInteger.class)) {
									// BigInteger 类型
									MethodUtils.invokeMethod(clazz, methodName, BigInteger.valueOf(Long.parseLong(fieldValue)));
								} else if (fieldType.equals(BigDecimal.class)) {
									// BigDecimal 类型
									// 用new BigDecimal方法将值做类型转换，其他方法会出点问题
									MethodUtils.invokeMethod(clazz, methodName, new BigDecimal(fieldValue));
								} else if (fieldType.equals(java.util.Date.class) || fieldType.equals(java.sql.Date.class)) {
									// 日期类型
									MethodUtils.invokeMethod(clazz, methodName, MdmConstants.DATE_FORMAT_10.parse(fieldValue));
								} else if (fieldType.equals(Timestamp.class)) {
									// 时间类型(时间戳)
									// MethodUtils.invokeMethod(clazz, methodName, MdmConstants.DATE_FORMAT_19.parse(fieldValue).getTime());
									field.set(clazz, Timestamp.valueOf(fieldValue));
								} else {
									// 其他类型
									MethodUtils.invokeMethod(clazz, methodName, fieldValue);
								}
							} catch (Exception e) {
								String err = String.format("交易处理错误，无法根据报文数据(位置于 %s 的 %s=%s)做数据库操作", xpath, fieldName, fieldValue);
								log.error("{}", err);
								log.error("{}", e);
								this.ecifData.setSuccess(false);
								this.ecifData.setStatus(ErrorCode.ERR_CONVERT_ERROR.getCode(), err);
								return false;
							}
						}
					}
					info.setOpModelList(clazz);
				}
			} catch (Exception e) {
				String err = String.format("交易处理错误，无法根据报文构造数据处理实体类(%s)", className);
				log.error("{}", err);
				log.error("{}", e);
				this.ecifData.setSuccess(false);
				this.ecifData.setStatus(ErrorCode.ERR_ECIF_DATA_ERROR.getCode(), err);
				return false;
			}
		}

		return true;
	}

	/**
	 * @函数名称:getClassByParams
	 * @函数描述:对象赋值
	 * @参数与返回说明:
	 * @param className
	 * @param context
	 * @param txMsgNodeAttrs
	 * @param tabId
	 * @return
	 * @算法描述:
	 * @WARN 该方法已废弃不用
	 * @see getTableNodeObect
	 */
	public <T> T getClassByParams(String className, Element context, List<TxMsgNodeAttr> txMsgNodeAttrs, Long tabId, Long nodeId) {
		T entity = null;
		boolean isAllEmpty = true;
		String val = null;
		StringBuffer txMsgNodeAttrCtList = null;
		if (!StringUtil.isEmpty(className)) {
			try {
				/**
				 * 生成类
				 */
				Class clazz = ServiceEntityMgr.getEntityByName(className);
				if (clazz == null) {
					log.error("没有找到{}的实体类", className);
					this.ecifData.setStatus(ErrorCode.ERR_SERVER_BIZLOGIC_ERROR);
					return null;
				}
				/**
				 * 实例化该类
				 */
				entity = (T) clazz.newInstance();
				Object value = null;
				Class typeClass = null;
				Field field = null;
				for (TxMsgNodeAttr txMsgNodeAttr : txMsgNodeAttrs) {
					if (tabId.equals(txMsgNodeAttr.getTabId())) {
						/**
						 * 修改权限控制
						 */
						/***
						 * 信息漂白
						 ****/
						if ("Y".equals(txMsgNodeAttr.getNulls())
								&& !EcifPubDataUtils.infoLevelRead(authType, authCode, MdmConstants.CTRLTYPE_WRITE, txMsgNodeAttr.getTabId(), txMsgNodeAttr.getColId())) {
							continue;
						}
						Element attrElement = context.element(txMsgNodeAttr.getAttrCode());
						if (!StringUtil.isEmpty(txMsgNodeAttr.getCheckRule())) {
							if (txMsgNodeAttrCtList == null) {
								txMsgNodeAttrCtList = new StringBuffer(txMsgNodeAttr.getCheckRule());
							} else {
								txMsgNodeAttrCtList.append(',').append(txMsgNodeAttr.getCheckRule());
							}
						}
						if (attrElement != null) {
							val = attrElement.getTextTrim();
						} else {
							val = txMsgNodeAttr.getDefaultVal();
							/*** 默认值规则 ***/
							if (!StringUtil.isEmpty(val)) {
								/**
								 * 解析默认值
								 */
								val = parseDefaultVal(val, context);
								if (!this.ecifData.isSuccess()) { return null; }
							}
						}
						if (!StringUtil.isEmpty(val)) {
							/**
							 * 获取的属性名称是数据库的字段名称，需要转换成java的命名规范名称
							 */
							field = clazz.getDeclaredField(NameUtil.getColumToJava(txMsgNodeAttr.getColName()));
							typeClass = field.getType();
							/**
							 * 将字符型的数据转换成实体属性的类型
							 */
							value = convertStringToObject(typeClass, val, txMsgNodeAttr.getDataFmt());
							if (value != null) {
								/**
								 * 设置对私的访问权限
								 */
								field.setAccessible(true);
								/**
								 * 设置数据
								 */
								field.set(entity, value);
								isAllEmpty = false;
							} else {
								return null;
							}
						} else {
							/** 判定是否是业务主键，非空 **/
							if ("P".equals(txMsgNodeAttr.getNulls())) { return null; }
						}
					}
				}

			} catch (Exception e) {
				log.error("错误信息：", e);
				this.ecifData.setStatus(ErrorCode.ERR_POJO_UNKNOWN_ERROR);
				return null;
			}
		} else {
			log.warn("数据库数据有误");
			this.ecifData.setStatus(ErrorCode.ERR_SERVER_INTERNAL_ERROR.getCode(), "数据库数据有误");
			return null;
		}
		/**
		 * 所有的属性为空，返回空
		 */
		if (isAllEmpty) { return null; }
		/******
		 * 写交易过滤器
		 */
		if (txMsgNodeAttrCtList != null && txMsgNodeAttrCtList.length() > 0) {
			boolean rel = TxBizRuleFactory.doFilter(this.ecifData, txMsgNodeAttrCtList.toString(), entity);
			if (!rel) { return null; }
		}
		return entity;
	}

	public void createRespDocument(EcifData data, Element node) {

	}
	
	/**
	 * 保存客户信息到客户相关表
	 */
	@SuppressWarnings("unused")
	public void saveToCustomer(EcifData data){
		//拼接请求报文
		List list = data.getWriteModelObj().getOpModelList();
		String custId = null;//移动信贷客户号
		String custMgr = null;//客户经理
		String custName = null;//客户名称
		String ecifId = null;//ecif客户号
		String identId = null;//证件id号
		String belongManagerId = null;
		String belongBranchId = null;
		SimpleDateFormat df8 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat df20 = new SimpleDateFormat("yyyyMMddHHmmssSS");
		SimpleDateFormat df10 = new SimpleDateFormat("hhmmssSS");
		StringBuffer reQxmlorg = new StringBuffer();
		String reqXml = "";
		String orgId = "";
		
		if(list != null && list.size()>0){
			if(list.get(0).getClass().equals(AcrmFCiPotCusCom.class)){
				AcrmFCiPotCusCom cuscom = (AcrmFCiPotCusCom)list.get(0);
				 custId = cuscom.getCusId();
				 custMgr = cuscom.getCustMgr();
				 custName = cuscom.getCusName();
				 String c = " SELECT t.org_id FROM ADMIN_AUTH_ACCOUNT T WHERE T.ACCOUNT_NAME='"+custMgr+"'";
				 List list1 = baseDAO.findByNativeSQLWithIndexParam(c, null);
				 if(list1 != null && list1.size() >0){
					 orgId =  list1.get(0) != null ? list1.get(0).toString() : custMgr.substring(0,3);
				 }else{
					 orgId = custMgr.substring(0,3);
				 }
				 String Hxml = "<?xml version=\"1.0\" encoding=\"GBK\"?>\n"
							+ "<TransBody>\n" 
							+ " <RequestHeader>\n"
							+ "    <ReqSysCd>CRM</ReqSysCd>\n" 
							+ "    <ReqSeqNo>"+ df20.format(new Date())+ "</ReqSeqNo>\n"
							+ "    <ReqDt>"+ df8.format(new Date())+ "</ReqDt>\n"
							+ "    <ReqTm>"+ df10.format(new Date())+ "</ReqTm>\n"
							+ "    <DestSysCd>ECIF</DestSysCd>\n"
							+ "    <ChnlNo>82</ChnlNo>\n"
//							+ "    <BrchNo>6801</BrchNo>\n"
							+ "    <BrchNo>"+orgId+"</BrchNo>\n"
							+ "    <BizLine>6491</BizLine>\n"
							+ "    <TrmNo>TRM10010</TrmNo>\n"
							+ "    <TrmIP>127.0.0.1</TrmIP>\n"
							+ "    <TlrNo>"+ custMgr+ "</TlrNo>\n"
							+ " </RequestHeader>\n"
							+ " <RequestBody>\n"
							+ "    <txCode>openOrgAccount4CrmAndDms</txCode>\n"
							+ "    <txName>潜在机构客户开户(移动信贷)</txName>\n"
							+ "    <authType>1</authType>\n"
							+ "    <authCode>1010</authCode>\n";

					String orgIdentifier = " <orgIdentifier>\n" 
							+"    <identType>20</identType>\n"
							+"    <identNo></identNo>\n"
							+"    <identCustName>"+custName+"</identCustName>\n"
							+"	  <identDesc></identDesc>\n"
							+"	  <countryOrRegion></countryOrRegion>\n"
							+"	  <identOrg></identOrg>\n"
							+"	  <identApproveUnit></identApproveUnit>\n"
							+"	  <identCheckFlag></identCheckFlag>\n"
							+"	  <idenRegDate></idenRegDate>\n"
							+"	  <identCheckingDate></identCheckingDate>\n"
							+"	  <identCheckedDate></identCheckedDate>\n"
							+"	  <identValidPeriod></identValidPeriod>\n"
							+"	  <identEffectiveDate></identEffectiveDate>\n"
							+"	  <identExpiredDate></identExpiredDate>\n"
							+"	  <identValidFlag></identValidFlag>\n"
							+"	  <txSeqNo></txSeqNo>\n"
							+"	  <identPeriod></identPeriod>\n"
							+"	  <isOpenAccIdent></isOpenAccIdent>\n"
							+"	  <isOpenAccIdentLn></isOpenAccIdentLn>\n"
							+"	  <openAccIdentModifiedFlag></openAccIdentModifiedFlag>\n"
							+"	  <identModifiedTime></identModifiedTime>\n"
							+"	  <verifyDate></verifyDate>\n"
							+"	  <verifyEmployee></verifyEmployee>\n"
							+"	  <verifyResult></verifyResult>\n"
							+"	  <lastUpdateSys></lastUpdateSys>\n"
							+"	  <lastUpdateUser></lastUpdateUser>\n"
							+"	  <lastUpdateTm></lastUpdateTm>\n"
							+"  </orgIdentifier>\n";
					
					String customer ="<customer>\n" 
							  +"	    <identType>20</identType>\n"
							  +"		<identNo></identNo>\n"
							  +"		<custName>"+custName+"</custName>\n"
							  +"		<custType>1</custType>\n" // 客户类型：默认1:企业
							  +"		<shortName></shortName>\n" // 客户简称
							  +"		<enName></enName>\n" // 英文名称
							  +"		<industType></industType>" //所属行业
							  +"		<riskNationCode></riskNationCode>\n" // 国别风险国别代码
						  	  +"		<potentialFlag>1</potentialFlag>\n" // 潜在客户标志:默认1:潜在客户
							  +"		<inoutFlag></inoutFlag>\n" // 境内境外标志
							  +"		<arCustFlag></arCustFlag>\n" // AR客户标志
							  +"		<arCustType></arCustType>\n" // AR客户类型
							  +"        <createDate>"+df8.format(new Date())+"</createDate>\n                          "
							  +"        <createTime>"+df8.format(new Date())+"</createTime>\n"
							  +"        <createBranchNo></createBranchNo>\n"
							  +"        <createTellerNo></createTellerNo>\n"
							  +"        <createDateLn></createDateLn>\n"
							  +"        <createTimeLn></createTimeLn>\n"
							  +"        <createBranchNoLn></createBranchNoLn>\n"
							  +"        <createTellerNoLn></createTellerNoLn>\n"
							  +"        <custLevel></custLevel>\n"
							  +"        <riskLevel></riskLevel>\n"
							  +"        <riskValidDate></riskValidDate>\n"
							  +"        <creditLevel></creditLevel>\n"
							  +"        <currentAum></currentAum>\n"
							  +"        <totalDebt></totalDebt>\n"
							  +"        <infoPer></infoPer>\n"
							  +"        <faxtradeNorecNum></faxtradeNorecNum>\n"
							  +"        <coreNo></coreNo>\n"
							  +"        <postName></postName>\n"
							  +"        <enShortName></enShortName>\n"
							  +"        <custStat></custStat>\n"
							  +"        <jobType></jobType>\n"
							  +"        <industType></industType>\n"
							  +"        <riskNationCode></riskNationCode>\n"
							  +"        <ebankFlag></ebankFlag>\n"
							  +"        <realFlag></realFlag>\n"
							  +"        <blankFlag></blankFlag>\n"
							  +"        <vipFlag></vipFlag>\n"
							  +"        <mergeFlag></mergeFlag>\n"
							  +"        <custnmIdentModifiedFlag></custnmIdentModifiedFlag>\n"
							  +"        <linkmanName></linkmanName>\n"
							  +"        <linkmanTel></linkmanTel>\n"
							  +"        <firstLoanDate></firstLoanDate>\n"
							  +"        <loanCustMgr></loanCustMgr>\n"
							  +"        <loanMainBrId></loanMainBrId>\n"
							  +"        <arCustFlag></arCustFlag>\n"
							  +"        <arCustType></arCustType>\n"
							  +"        <sourceChannel></sourceChannel>\n"
							  +"        <recommender></recommender>\n"
							  +"        <loanCustRank></loanCustRank>\n"
							  +"        <loanCustStat></loanCustStat>\n"
							  +"        <staffin></staffin>\n"
							  +"        <swift></swift>\n"
							  +"        <cusBankRel></cusBankRel>\n"
							  +"        <cusCorpRel></cusCorpRel>\n"
							  +"        <profctr></profctr>\n"
							  +"        <loanCustId></loanCustId>\n"
							  +"     </customer>\n";			
					String contmeth  = " <contmeth>\n" 
							         + "    <contmethType></contmethType>\n"
						             + "    <contmethInfo></contmethInfo>\n"
							         + "    <contmethSeq></contmethSeq>\n"
							         + "	<remark></remark>\n" 
							         + "	<stat></stat>\n"
							         + " </contmeth>\n";
					String crossindex = 
							  " <crossindex>\n" 
							+ "     <srcSysNo>DMS</srcSysNo>\n"
							+ "     <srcSysCustNo>"+custId+"</srcSysCustNo>\n" 
							+ " </crossindex>";
					String org=
							 "	<org>\n"
							+"		<comSpLicOrg></comSpLicOrg>\n"
							+"		<comSpDetail></comSpDetail>\n"
							+"		<comSpLicNo></comSpLicNo>\n"
							+"		<comSpBusiness></comSpBusiness>\n"
							+"		<topCorpLevel></topCorpLevel>\n"
							+"		<fexcPrmCode></fexcPrmCode>\n"
							+"		<fundSource></fundSource>\n"
							+"		<induDeveProspect></induDeveProspect>\n"
							+"		<busiStartDate></busiStartDate>\n"
							+"		<businessMode></businessMode>\n"
							+"		<minorBusiness></minorBusiness>\n"
							+"		<mainBusiness></mainBusiness>\n"
							+"		<superDept></superDept>\n"
							+"		<buildDate></buildDate>\n"
							+"		<entBelong></entBelong>\n"
							+"		<investType></investType>\n"
							+"		<industryCategory></industryCategory>\n"
							+"		<inCllType></inCllType>\n"
							+"		<governStructure></governStructure>\n"
							+"		<orgForm></orgForm>\n"
							+"		<comHoldType></comHoldType>\n"
							+"		<economicType></economicType>\n"
							+"		<employeeScale></employeeScale>\n"
							+"		<assetsScale></assetsScale>\n"
							+"		<entScaleCk></entScaleCk>\n"
							+"		<entScaleRh></entScaleRh>\n"
							+"		<entScale></entScale>\n"
							+"		<entProperty></entProperty>\n"
							+"		<industryChar></industryChar>\n"
							+"		<industryDivision></industryDivision>\n"
							+"		<minorIndustry></minorIndustry>\n"
							+"		<mainIndustry></mainIndustry>\n"
							+"		<busiLicNo></busiLicNo>\n"
							+"		<orgCodeAnnDate></orgCodeAnnDate>\n"
							+"		<orgCodeUnit></orgCodeUnit>\n"
							+"		<orgExpDate></orgExpDate>\n"
							+"		<orgRegDate></orgRegDate>\n"
							+"		<orgCode></orgCode>\n"
							+"		<orgSubType></orgSubType>\n"
							+"		<orgType></orgType>\n"
							+"		<areaCode></areaCode>\n"
							+"		<remark></remark>\n"
							+"		<lastDealingsDesc></lastDealingsDesc>\n"
							+"		<orgWeixin></orgWeixin>\n"
							+"		<orgWeibo></orgWeibo>\n"
							+"		<orgHomepage></orgHomepage>\n"
							+"		<orgEmail></orgEmail>\n"
							+"		<orgFex></orgFex>\n"
							+"		<orgTel></orgTel>\n"
							+"		<orgCus></orgCus>\n"
							+"		<orgZipcode></orgZipcode>\n"
							+"		<orgAddr></orgAddr>\n"
							+"		<holdStockAmt></holdStockAmt>\n"
							+"		<isStockHolder></isStockHolder>\n"
							+"		<industryPosition></industryPosition>\n"
							+"		<annualProfit></annualProfit>\n"
							+"		<annualIncome></annualIncome>\n"
							+"		<totalDebt></totalDebt>\n"
							+"		<totalAssets></totalAssets>\n"
							+"		<finRepType></finRepType>\n"
							+"		<legalReprNationCode></legalReprNationCode>\n"
							+"		<legalReprPhoto></legalReprPhoto>\n"
							+"		<legalReprAddr></legalReprAddr>\n"
							+"		<legalReprTel></legalReprTel>\n"
							+"		<legalReprIdentNo></legalReprIdentNo>\n"
							+"		<legalReprIdentType></legalReprIdentType>\n"
							+"		<legalReprGender></legalReprGender>\n"
							+"		<legalReprName></legalReprName>\n"
							+"		<partnerType></partnerType>\n"
							+"		<loadCardAuditDt></loadCardAuditDt>\n"
							+"		<loadCardPwd></loadCardPwd>\n"
							+"		<loanCardStat></loanCardStat>\n"
							+"		<loanCardNo></loanCardNo>\n"
							+"		<loanCardFlag></loanCardFlag>\n"
							+"		<comSpEndDate></comSpEndDate>\n"
							+"		<comSpStrDate></comSpStrDate>\n"
							+"		<zoneCode></zoneCode>\n"
							+"		<nationCode></nationCode>\n"
							+"		<hqNationCode></hqNationCode>\n"
							+"		<orgBizCustType></orgBizCustType>\n"
							+"		<churcustype></churcustype>\n"
							+"		<jointCustType></jointCustType>\n"
							+"		<creditCode></creditCode>\n"
							+"		<lncustp></lncustp>\n"
							+"		<orgCustType></orgCustType>\n"
							+"		<custName>"+custName+"</custName>\n"
							+"		<ifOrgSubType></ifOrgSubType>\n"
							+"	</org>\n";
					//归属经理
					String belongManager = " <belongManager>\n "
					                      +"	<custManagerNo>"+custMgr+"</custManagerNo>\n"
					                      +"	<mainType>1</mainType>\n"
					                      +"	<validFlag></validFlag>\n"
					                      +"	<startDate></startDate>\n"
					                      +"	<endDate></endDate>\n"
					                      +"	<custManagerType></custManagerType>\n"
					                      +" </belongManager>\n";
					//归属机构
					String belongBranch = " <belongBranch>\n "
			                              +"	<belongBranchNo>"+orgId+"</belongBranchNo>\n"
			                              +" </belongBranch>\n";
					reQxmlorg.append(Hxml);
					reQxmlorg.append(orgIdentifier); 
					reQxmlorg.append(customer);
					reQxmlorg.append(contmeth);
					reQxmlorg.append(crossindex);
					reQxmlorg.append(org);
					reQxmlorg.append(belongManager);
					reQxmlorg.append(belongBranch);
					reQxmlorg.append("</RequestBody></TransBody>\n");
					reqXml = reQxmlorg.toString();
					try {
						//调用ECIF开户
						Map map = process(reqXml);
						log.info("获取ECIF客户号:" + map);
						ecifId = map.get("custNo") != null ? map.get("custNo").toString() : "";
						identId =  map.get("identId") != null ? map.get("identId").toString() : "";
						belongManagerId =  map.get("belongManagerId") != null ? map.get("belongManagerId").toString() : "";
						belongBranchId =  map.get("belongBranchId") != null ? map.get("belongBranchId").toString() : "";
						if(ecifId != null && !ecifId.equals("")){
							//保存到customer,默认开户证件：20-境内组织机构代码
							String customerInsert = " INSERT INTO ACRM_F_CI_CUSTOMER(CUST_ID,CUST_NAME,CUST_TYPE,POTENTIAL_FLAG,CREATE_TIME_LN,LAST_UPDATE_SYS,LAST_UPDATE_TM,LAST_UPDATE_USER,IDENT_TYPE)"
									+ " VALUES('"+ ecifId + "','"+ custName + "','1','1',sysDate,'DMS',sysDate,'"+ custMgr + "','20')";
							baseDAO.batchExecuteNativeWithIndexParam(customerInsert);
							//保存到机构表
							String orgInsert = " INSERT INTO ACRM_F_CI_ORG(CUST_ID,CUST_NAME)"
									+ " VALUES('"+ ecifId + "','"+ custName + "')";
							baseDAO.batchExecuteNativeWithIndexParam(orgInsert);
							if(identId != null && !identId.equals("")){
								//保存到证件表,默认开户证件：20-境内组织机构代码
								String identInsert = " INSERT INTO ACRM_F_CI_CUST_IDENTIFIER(IDENT_ID,CUST_ID,IDENT_TYPE)"
										+ " VALUES('"+identId+"','"+ ecifId + "','20')";
								baseDAO.batchExecuteNativeWithIndexParam(identInsert);
							}
							String belongMgrInsert = "";
							if(belongManagerId != null && !belongManagerId.equals("")){
								belongMgrInsert= " INSERT INTO OCRM_F_CI_BELONG_CUSTMGR(ID,CUST_ID,MGR_ID,MAIN_TYPE,INSTITUTION,INSTITUTION_NAME,MGR_NAME)"
										+ " VALUES('"+belongManagerId+"','"+ ecifId + "','"+ custMgr + "','1'," +
										  " (SELECT t.org_id FROM ADMIN_AUTH_ACCOUNT T WHERE T.ACCOUNT_NAME='"+ custMgr + "')," +
										  " (select t.org_name from admin_auth_org t where t.org_id in (SELECT t.org_id FROM ADMIN_AUTH_ACCOUNT T WHERE T.ACCOUNT_NAME='"+ custMgr + "'))," +
										  " (SELECT t.user_name FROM ADMIN_AUTH_ACCOUNT T WHERE T.ACCOUNT_NAME='"+ custMgr + "'))";
							}else{
							    belongMgrInsert = " INSERT INTO OCRM_F_CI_BELONG_CUSTMGR(ID,CUST_ID,MGR_ID,MAIN_TYPE,INSTITUTION,INSTITUTION_NAME,MGR_NAME)"
										+ " VALUES(ID_SEQUENCE.NEXTVAL,'"+ ecifId + "','"+ custMgr + "','1'," +
										  " (SELECT t.org_id FROM ADMIN_AUTH_ACCOUNT T WHERE T.ACCOUNT_NAME='"+ custMgr + "')," +
										  " (select t.org_name from admin_auth_org t where t.org_id in (SELECT t.org_id FROM ADMIN_AUTH_ACCOUNT T WHERE T.ACCOUNT_NAME='"+ custMgr + "'))," +
										  " (SELECT t.user_name FROM ADMIN_AUTH_ACCOUNT T WHERE T.ACCOUNT_NAME='"+ custMgr + "'))";
							}
							//保存到归属经理表
							baseDAO.batchExecuteNativeWithIndexParam(belongMgrInsert);
							//保存到归属机构表
							String belongOrgInsert = "";
							if(belongBranchId != null && !belongBranchId.equals("")){
								 belongOrgInsert = " INSERT INTO OCRM_F_CI_BELONG_ORG(ID,CUST_ID,MAIN_TYPE,INSTITUTION_CODE,INSTITUTION_NAME)"
										+ " VALUES('"+belongBranchId+"','"+ ecifId + "','1',(SELECT t.org_id FROM ADMIN_AUTH_ACCOUNT T WHERE T.ACCOUNT_NAME='"+ custMgr + "'),(select t.org_name from admin_auth_org t where t.org_id in (SELECT t.org_id FROM ADMIN_AUTH_ACCOUNT T WHERE T.ACCOUNT_NAME='"+ custMgr + "')))";
							}else{
								 belongOrgInsert = " INSERT INTO OCRM_F_CI_BELONG_ORG(ID,CUST_ID,MAIN_TYPE,INSTITUTION_CODE,INSTITUTION_NAME)"
										+ " VALUES(ID_SEQUENCE.NEXTVAL,'"+ ecifId + "','1',(SELECT t.org_id FROM ADMIN_AUTH_ACCOUNT T WHERE T.ACCOUNT_NAME='"+ custMgr + "'),(select t.org_name from admin_auth_org t where t.org_id in (SELECT t.org_id FROM ADMIN_AUTH_ACCOUNT T WHERE T.ACCOUNT_NAME='"+ custMgr + "')))";
							}
							baseDAO.batchExecuteNativeWithIndexParam(belongOrgInsert);
							//保存到交叉索引表
							String crossIndexInsert = " INSERT INTO ACRM_F_CI_CROSSINDEX(CROSSINDEX_ID,SRC_SYS_NO,SRC_SYS_CUST_NO,CUST_ID)"
									+ " VALUES(sq_crossindex.nextval,'DMS','"+ custId + "','"+ ecifId + "')";
							baseDAO.batchExecuteNativeWithIndexParam(crossIndexInsert);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		 	   }
		 }
	}
	
	/**
	 * 调用ECIF开户
	 * 移动信贷
	 */
	public static Map process(String mxlmsg) throws Exception {
		Map idsMap = new HashMap();
		log.info("ECIF开户请求报文:" + mxlmsg);
		String msg = mxlmsg;
//		String ip="127.0.0.1";
//		uat的ecif地址
		String ip = "10.20.34.108";
//		生产的ecif地址
//		String ip = "10.7.34.10";
		int port = 9500;
	
		NioClient cl = new NioClient(ip, port);
		String resp = null;
		try {
			resp = cl.SocketCommunication(String.format("%08d",
					msg.getBytes("GBK").length)
					+ msg);
		} catch (IOException e) {
			log.info("调用ECIF系统超时!");
		}
		log.info("ECIF开户返回报文:" + resp);
		String custNo = "";
		String custId = "";
		String identId = "";
		String belongManagerId = "";
		String belongBranchId = "";
		/**
		 * 处理返回报文
		 * 
		 * @param xml
		 * @return
		 */
		try {
			resp = resp.substring(8);
			Document doc = DocumentHelper.parseText(resp);
			Element root = doc.getRootElement();
			String TxStatDesc = root.element("ResponseTail")
					.element("TxStatDesc").getTextTrim();
			String TxStatCode = root.element("ResponseTail")
					.element("TxStatCode").getTextTrim();
			if ("000000".equals(TxStatCode)) {
				// 返回技术主键：
				custNo = root.element("ResponseBody").element("custNo")
						.getTextTrim();
				if (root.element("ResponseBody").element("custId") != null) {
					custId = root.element("ResponseBody").element("custId")
							.getTextTrim();
				}
				if (root.element("ResponseBody").element("identId") != null) {
					identId = root.element("ResponseBody").element("identId")
							.getTextTrim();
				}
				if (root.element("ResponseBody").element("belongManagerId") != null) {
					belongManagerId = root.element("ResponseBody").element("belongManagerId")
							.getTextTrim();
				}
				if (root.element("ResponseBody").element("belongBranchId") != null) {
					belongBranchId = root.element("ResponseBody").element("belongBranchId")
							.getTextTrim();
				}
				idsMap.put("custNo", custNo);
				idsMap.put("custId", custId);
				idsMap.put("identId", identId);
				idsMap.put("belongManagerId", belongManagerId);
				idsMap.put("belongBranchId", belongBranchId);
				
			} else {
				log.info("调用ECIF开户接口异常:"+TxStatDesc);
			}
		} catch (BizException e) {
			log.info("调用ECIF开户接口异常1:"+e.getMessage());
		} catch (Exception e) {
			log.info("调用ECIF开户接口异常2:"+e.getMessage());
		}
		return idsMap;
	}
}
