/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.service.svc.atomic
 * @�ļ�����DeleteGeneral.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-12:02:46
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.service.svc.atomic;


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

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�DeleteGeneral
 * @��������ͨ������ɾ��
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����12:02:46   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����12:02:46
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
@Service
@SuppressWarnings(
		{ "unchecked", "rawtypes" })
public class DeleteGeneral
{
	private JPABaseDAO baseDAO;
	private static Logger log = LoggerFactory
			.getLogger(DeleteGeneral.class);

	@Transactional
	public void process(EcifData ecifData) throws Exception
	{
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		GetObjectByBusinessKey findObj = (GetObjectByBusinessKey) SpringContextUtils.getBean("getObjectByBusinessKey");
		IColumnUtils columnUtils = (IColumnUtils) SpringContextUtils.getBean(MdmConstants.COLUMNUTILS);
		List objList=ecifData.getWriteModelObj().getOpModelList();
		for (Object newObj : objList)
		{
			ObjectReturnMessage objMessage = new ObjectReturnMessage();
			if(ecifData.getCustId()!=null){
				OIdUtils.setCustIdValue(newObj,ecifData.getCustId());
			}
			objMessage = findObj.bizGetObject(newObj,false,false,ecifData.getOpChnlNo());
			if (objMessage.isSuccessFlag()){
				Object oldObj = objMessage.getObject();
				baseDAO.remove(oldObj);
				if (MdmConstants.isSaveHistory) {
					Object hisObj=null;
					if(!MdmConstants.chooseSaveHistory||BusinessCfg.isSaveHisObj(oldObj.getClass().getSimpleName())){
						hisObj = columnUtils.toHistoryObj(oldObj,ecifData.getOpChnlNo(),MdmConstants.HIS_OPER_TYPE_D);
					}
					if(hisObj!=null){
						baseDAO.persist(hisObj);
					}
				}
			}else{
				if(ErrorCode.WRN_NONE_FOUND.getCode().equals(objMessage.getError().getCode())){
					log.warn("{}��ɾ����Ϣ������",newObj.getClass().getSimpleName());
					ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_EXIST_OTHER.getCode(),"%s��ɾ����Ϣ������",newObj.getClass().getSimpleName());
				}else{
					log.warn("{}��ɾ����Ϣ����[{}]",newObj.getClass().getSimpleName(),objMessage.getError().getChDesc());
					ecifData.setStatus(objMessage.getError());
				}
				return;
			}
		}
		baseDAO.flush();
		ecifData.resetStatus();
		return;
	}
}
