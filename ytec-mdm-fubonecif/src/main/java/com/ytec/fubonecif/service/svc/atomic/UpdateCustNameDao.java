/**
 * @��Ŀ����ytec-mdm-fubonecif
 * @������com.ytec.fubonecif.service.dao
 * @�ļ�����UpdateCustNameDao.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-2-12-10:24:20
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */

package com.ytec.fubonecif.service.svc.atomic;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.OIdUtils;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.server.common.BusinessCfg;
import com.ytec.mdm.service.bo.ObjectReturnMessage;
import com.ytec.mdm.service.component.biz.identification.GetObjectByBusinessKey;
import com.ytec.mdm.service.facade.IColumnUtils;
import com.ytec.mdm.service.facade.ICoveringRule;
//import com.ytec.fubonecif.domain.MCiNametitle;
//import com.ytec.fubonecif.domain.MCiOrgIdentifier;
//import com.ytec.fubonecif.domain.MCiPerIdentifier;
import com.ytec.fubonecif.domain.MCiIdentifier;


/**
 * @��Ŀ���ƣ�ytec-mdm-fubonecif 
 * @�����ƣ�UpdateCustNameDao
 * @�������������޸�DAO
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-2-12 ����10:24:10   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-2-12 ����10:24:10
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class UpdateCustNameDao {
	private static Logger log = LoggerFactory.getLogger(UpdateCustNameDao.class);
	@Transactional(rollbackFor={Exception.class,RuntimeException.class})
	public void process(EcifData ecifData,String custName) throws Exception {
			JPABaseDAO baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
			
			String identifierTab = null;
			if (MdmConstants.TX_CUST_PER_TYPE.equals(ecifData.getCustType())) {
				identifierTab = MdmConstants.MODEL_PERSONIDENTIFIER;
			}else{
				identifierTab = MdmConstants.MODEL_ORGIDENTIFIER;
			}
			/**�ж����޸õĻ�����ͬ��֤�Ŀͻ����ҿͻ���״̬Ϊ��Ч�Ŀͻ�,��ҵ��ȷ��***/
			List result=baseDAO.findWithIndexParam("select count(1) from "+identifierTab+" t1,"+identifierTab+" t2 where t1."+MdmConstants.CUSTID+"=? and t1.identType=t2.identType and t1.identNo=t2.identNo and t2.identCustName=? and t2."+MdmConstants.CUSTID+"<>t1."+MdmConstants.CUSTID, ecifData.getCustId(MdmConstants.CUSTID_TYPE),custName);
			if(result!=null && !result.isEmpty()){
				Long count=(Long)result.get(0);
				if(count>0){
					String msg = "������ÿͻ���ͬ��֤�������Ŀͻ�";
					log.error(msg);
					ecifData.setStatus(ErrorCode.ERR_ECIF_CUSTNAME.getCode(), msg);
					return;
				}
			}
			
			List infoList=new ArrayList();
			List identifierList=baseDAO.findWithIndexParam("from "+identifierTab+" t where t."+MdmConstants.CUSTID+"=?",ecifData.getCustId(MdmConstants.CUSTID_TYPE));
			if(identifierList!=null){
				infoList.addAll(identifierList);
			}
//			MCiNametitle nametitle=new MCiNametitle();
//			nametitle.setCustName(custName);
//			infoList.add(nametitle);
			
			GetObjectByBusinessKey findObj = (GetObjectByBusinessKey) SpringContextUtils.getBean("getObjectByBusinessKey");
			ICoveringRule cover = (ICoveringRule) SpringContextUtils.getBean(MdmConstants.COVERINGRULE);
			IColumnUtils columnUtils = (IColumnUtils) SpringContextUtils.getBean(MdmConstants.COLUMNUTILS);
			ObjectReturnMessage objMessage = new ObjectReturnMessage();
			for (Object newObj : infoList) {
				// Ϊ��������CustId
				OIdUtils.setCustIdValue(newObj,ecifData.getCustId());
				if (newObj.getClass().equals(MCiIdentifier.class)) {
					MCiIdentifier identifier = (MCiIdentifier) newObj;
					identifier.setIdentCustName(custName);
				}
//				if (newObj.getClass().equals(MCiOrgIdentifier.class)) {
//					MCiOrgIdentifier identifier = (MCiOrgIdentifier) newObj;
//					identifier.setIdentCustName(custName);
//				}
				if (MdmConstants.TX_CODE_U.equals(ecifData.getTxType())) {
					objMessage = findObj.bizGetObject(newObj, false, true,ecifData.getOpChnlNo());
				} else {
					objMessage = findObj.bizGetObject(newObj, false, false,ecifData.getOpChnlNo());
				}
				if (objMessage.isSuccessFlag()) {
					// ������ڣ����ݸ���ԭ������һ�����µĶ���Ȼ����¸�����
					Object oldObj = objMessage.getObject();
					newObj = cover.cover(ecifData,oldObj, newObj);
					if(newObj!=null){
						newObj = columnUtils.setUpdateGeneralColumns(ecifData,
							newObj);
						if (MdmConstants.isSaveHistory) {
							Object hisObj = null;
							if (!MdmConstants.chooseSaveHistory
								|| BusinessCfg.isSaveHisObj(oldObj.getClass()
										.getSimpleName())) {
								hisObj = columnUtils.toHistoryObj(oldObj,
									ecifData.getOpChnlNo(),
									MdmConstants.HIS_OPER_TYPE_U);
							}
							if (hisObj != null) {
								baseDAO.persist(hisObj);
							}
						}
						baseDAO.merge(newObj);
					}
				} else {
					if (ErrorCode.ERR_ECIF_NOT_EXIST_BUSINESSKEY.getCode().equals(objMessage.getError().getCode())) {
						if (MdmConstants.existBusinesskeyError) {
							String msg = newObj.getClass().getSimpleName()+ "����������������:ҵ����Ϣ��Ϊ��";
							log.error(msg);
							ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_EXIST_BUSINESSKEY.getCode(), msg);
							throw new Exception(msg);
						} else {
							continue;
						}
					}
					if(ErrorCode.ERR_ECIF_NOT_EXIST_TECHNICALKEY.getCode().equals(objMessage.getError().getCode())){
						String msg = newObj.getClass().getSimpleName()+ "������������:ECIF�����ڸ�����";
						log.error(msg);
						ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_EXIST_TECHNICALKEY.getCode(), msg);
						throw new Exception(msg);
					}
					
					newObj = OIdUtils.createId(newObj);
					newObj = columnUtils.setCreateGeneralColumns(ecifData,
							newObj);
					baseDAO.persist(newObj);
				}
			}
			baseDAO.flush();
			ecifData.resetStatus();
		return;
	}
}
