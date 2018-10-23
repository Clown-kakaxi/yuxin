/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.fubonecif.service.svc.atomic
 * @文件名：AddGeneral.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-12:01:52
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.fubonecif.service.svc.atomic;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ytec.fubonecif.domain.MCiBelongBranch;
import com.ytec.fubonecif.domain.MCiBelongManager;
import com.ytec.fubonecif.domain.MCiCustomer;
import com.ytec.fubonecif.domain.MCiIdentifier;
import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.GetKeyNameUtil;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.OIdUtils;
import com.ytec.mdm.base.util.ReflectionUtils;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.server.common.BusinessCfg;
import com.ytec.mdm.service.bo.ObjectReturnMessage;
import com.ytec.mdm.service.component.biz.identification.GetCustomerName;
import com.ytec.mdm.service.component.biz.identification.GetObjectByBusinessKey;
import com.ytec.mdm.service.facade.IColumnUtils;
import com.ytec.mdm.service.facade.ICoveringRule;

/**
 * @项目名称：ytec-mdm-ecif
 * @类名称：AddGeneral
 * @类描述：通用保存
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 下午12:01:53
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 下午12:01:53
 * @修改备注：
 * @修改日期 修改人员 修改原因 -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AddGeneral {
	private JPABaseDAO baseDAO;
	private static Logger log = LoggerFactory.getLogger(AddGeneral.class);

	private Map<String, Object> resultMap = new HashMap<String, Object>();

	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public void process(EcifData ecifData) throws Exception {
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		GetObjectByBusinessKey findObj = (GetObjectByBusinessKey) SpringContextUtils.getBean("getObjectByBusinessKey");
		ICoveringRule cover = (ICoveringRule) SpringContextUtils.getBean(MdmConstants.COVERINGRULE);
		IColumnUtils columnUtils = (IColumnUtils) SpringContextUtils.getBean(MdmConstants.COLUMNUTILS);
		ObjectReturnMessage objMessage = new ObjectReturnMessage();
		boolean custNameChanged = false;// 户名是否有变化
		boolean idenCustNameChanged = false;// 开户证件户名是否有变化
		String custName = null;// 户名
		String oldCustName = null;
		boolean isOpen = false;// 是否开户
		List infoList = ecifData.getWriteModelObj().getOpModelList();
		
		//add begin by liuming 20170717
		boolean isPotential = false;//是否为潜在客户
		String selectSql="select 1 from M_CI_CUSTOMER t where t.potential_flag='1' and t.cust_id='"+ecifData.getCustId()+"'";
		List list = baseDAO.findByNativeSQLWithIndexParam(selectSql, null);
		if(list != null && list.size() >0){
			isPotential = true;
		}
		//add end 
		
		for (Object newObj : infoList) {

			log.info("处理对象[{}]数据", newObj.getClass().getSimpleName());

			// 为属性设置CustId
			if (ecifData.getCustId() != null) {
				OIdUtils.setCustIdValue(newObj, ecifData.getCustId());
			}

			/**
			 * 根据交易类型，做不同的信息识别
			 */
			if (MdmConstants.TX_CODE_U.equals(ecifData.getTxType())) {
				objMessage = findObj.bizGetObject(newObj, false, true, ecifData.getOpChnlNo());
			} else {
				objMessage = findObj.bizGetObject(newObj, false, false, ecifData.getOpChnlNo());
			}

			/**
			 * 如果存在，根据覆盖原则，生成一个更新的对象，然后更新该数据
			 */
			if (objMessage.isSuccessFlag()) {
				Object oldObj = objMessage.getObject();

				/**
				 * 针对核心客户号校验
				 * wangtb@yuchengtech.com
				 * 20141110
				 */
				if (newObj.getClass().equals(MCiCustomer.class)) {
					MCiCustomer newCust = (MCiCustomer) newObj;
					MCiCustomer oldCust = (MCiCustomer) oldObj;

					if (MdmConstants.SRC_SYS_CD_CB.equals(ecifData.getOpChnlNo())) {
						if (newCust.getCoreNo() == null) {
							String msg = String.format("核心系统(%s)开户不允许核心客户号(coreNo)为空", ecifData.getOpChnlNo());
							log.error(msg);
							ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_EXIST_SRCSYSCUSTNO.getCode(), msg);
							throw new Exception(msg);
						}
						if (!StringUtils.isEmpty(oldCust.getCoreNo()) && !newCust.getCoreNo().equals(oldCust.getCoreNo())) {
							String msg = String.format("新传入核心客户号(%s)与既有核心客户号(%s)不一致", newCust.getCoreNo(),oldCust.getCoreNo());
							log.error(msg);
							ecifData.setStatus(ErrorCode.ERR_ECIF_INVALID_SRCCUSTNO.getCode(), msg);
							throw new Exception(msg);
						}
					}
				}//核心客户号校验结束
				
				if (newObj.getClass().equals(MCiIdentifier.class)) {
					MCiIdentifier identifier = (MCiIdentifier) newObj;
					MCiIdentifier odlIdentifier = (MCiIdentifier) oldObj;
					custName = identifier.getIdentCustName();
					if (custName != null && !custName.isEmpty() && !custName.equals(odlIdentifier.getIdentCustName())) {
						custNameChanged = true;
						idenCustNameChanged = true;
					}
				}
				
				//add by liuming 20170717
				//潜在客户开户时，归属客户经理和归属机构以crm数据为准
				if(isPotential && 
						(newObj.getClass().equals(MCiBelongManager.class) 
								|| newObj.getClass().equals(MCiBelongBranch.class))){
					continue;
				}
				//add end 
				
				newObj = cover.cover(ecifData, oldObj, newObj);
				if (newObj != null) {
					newObj = columnUtils.setUpdateGeneralColumns(ecifData, newObj);
					if (MdmConstants.isSaveHistory) {

						log.info("记录操作前历史数据，数据对象:{}", newObj.getClass().getSimpleName());

						Object hisObj = null;
						if (!MdmConstants.chooseSaveHistory || BusinessCfg.isSaveHisObj(oldObj.getClass().getSimpleName())) {
							hisObj = columnUtils.toHistoryObj(oldObj, ecifData.getOpChnlNo(), MdmConstants.HIS_OPER_TYPE_U);
						}
						if (hisObj != null) {
							baseDAO.persist(hisObj);
							// TODO
						}
					}
					baseDAO.merge(newObj);

					/**
					 * 向响应报文对象中添加技术主键值
					 */
					String keyName = GetKeyNameUtil.getInstance().getKeyName(newObj);
					String keyValue = (String) ReflectionUtils.getFieldValue(newObj, keyName);
					log.info("向响应报文中添加返回信息[{}-->>{}={}]", newObj.getClass().getSimpleName(), keyName, keyValue);
					ecifData.getWriteModelObj().setResult(keyName, keyValue);
				}
			}
			/**
			 * 如果不存在记录，新增
			 */
			else {
				if (ErrorCode.ERR_ECIF_NOT_EXIST_BUSINESSKEY.getCode().equals(objMessage.getError().getCode())) {
					if (MdmConstants.existBusinesskeyError) {
						String msg = objMessage.getError().getChDesc();// newObj.getClass().getSimpleName() + "数据中有垃圾数据:业务信息项为空";
						log.error(msg);
						ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_EXIST_BUSINESSKEY.getCode(), msg);
						throw new Exception(msg);
					} else {
						continue;
					}
				}
				if (ErrorCode.ERR_ECIF_NOT_EXIST_TECHNICALKEY.getCode().equals(objMessage.getError().getCode())) {
					String msg = objMessage.getError().getChDesc();// newObj.getClass().getSimpleName() + "中有垃圾数据:ECIF不存在该数据";
					log.error(msg);
					ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_EXIST_TECHNICALKEY.getCode(), msg);
					throw new Exception(msg);
				}
				/***
				 * 户名修正
				 */
				String custNameTmp = null;
				if (newObj.getClass().equals(MCiIdentifier.class)) {
					MCiIdentifier identifier = (MCiIdentifier) newObj;
					custNameTmp = identifier.getIdentCustName();
				}
				if (custNameTmp != null) {
					if (!isOpen && oldCustName == null) {
						GetCustomerName getCustomerName = (GetCustomerName) SpringContextUtils.getBean("getCustomerName");
						oldCustName = getCustomerName.bizGetCustName(ecifData.getCustId(MdmConstants.CUSTID_TYPE));
						if (oldCustName == null) {
							isOpen = true;
						}
					}
					if (!isOpen && !custNameTmp.equals(oldCustName)) {
						custName = custNameTmp;
						custNameChanged = true;
						idenCustNameChanged = true;
					}
				}
				newObj = OIdUtils.createId(newObj);
				newObj = columnUtils.setCreateGeneralColumns(ecifData, newObj);
				baseDAO.persist(newObj);

				/**
				 * 向响应报文对象中添加技术主键值
				 */
				String keyName = GetKeyNameUtil.getInstance().getKeyName(newObj);
				String keyValue = (String) ReflectionUtils.getFieldValue(newObj, keyName);
				log.info("向响应报文中添加返回信息[{}-->>{}={}]", newObj.getClass().getSimpleName(), keyName, keyValue);
				ecifData.getWriteModelObj().setResult(keyName, keyValue);
			}
		}
		if (custNameChanged) {
			String jql = null;
			Map paramMap = new HashMap();
			paramMap.put("custName", custName);
			paramMap.put("contId", ecifData.getCustId(MdmConstants.CUSTID_TYPE));
			paramMap.put("updateTm", new Timestamp(System.currentTimeMillis()));
			paramMap.put("updateSrcSysCd", ecifData.getOpChnlNo());
			if (idenCustNameChanged) {
				jql = "update MCiCustomer i set i.custName=:custName,i.lastUpdateTm=:updateTm,i.lastUpdateSys=:updateSrcSysCd where i." + MdmConstants.CUSTID + "=:contId";
				if (custName != null && !custName.isEmpty()) {
					baseDAO.batchExecuteWithNameParam(jql, paramMap);
					log.info("{}户名有变化", ecifData.getCustId());
				}
			}
			String identifierTab = null;
			if (MdmConstants.TX_CUST_PER_TYPE.equals(ecifData.getCustType())) {
				identifierTab = MdmConstants.MODEL_PERSONIDENTIFIER;
			} else {
				identifierTab = MdmConstants.MODEL_ORGIDENTIFIER;
			}
			jql = "update " + identifierTab + " i set i.identCustName=:custName,i.lastUpdateTm=:updateTm,i.lastUpdateSys=:updateSrcSysCd where i." + MdmConstants.CUSTID + "=:contId";
			if (custName != null && !custName.isEmpty()) {
				baseDAO.batchExecuteWithNameParam(jql, paramMap);
				log.info("{}户名有变化", ecifData.getCustId());
			}
		}
		baseDAO.flush();
		ecifData.resetStatus();
		return;
	}
}
