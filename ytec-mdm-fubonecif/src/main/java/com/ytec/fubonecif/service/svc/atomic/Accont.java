/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.fubonecif.service.svc.atomic
 * @�ļ�����Accont.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-12:01:37
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.fubonecif.service.svc.atomic;


import java.sql.Timestamp;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.service.facade.IMCIdentifying;
import com.ytec.fubonecif.domain.MCiCustomer;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�Accont
 * @������������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����12:01:38   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����12:01:38
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class Accont {
	private static Logger log = LoggerFactory.getLogger(Accont.class);
	
	/**
	 * ���һ����ECIF����ģ���У�ECIF�ͺż�Ϊ�ͻ���ʶ��Ϊͬһ���ֶ�CUST_ID
	 * @param ecifData
	 * @param blankFlag
	 * @throws Exception
	 */
	public void process(EcifData ecifData, boolean blankFlag) throws Exception {
		IMCIdentifying util = (IMCIdentifying) SpringContextUtils.getBean(MdmConstants.MCIDENTIFYING);
		// ����contId
//		ecifData.setCustId(util.getEcifCustId(ecifData.getCustType()));
		String custNoId = util.getEcifCustId(ecifData.getCustType());
		ecifData.setCustId(custNoId);
		// ����ECIF�ͻ���
		String newCustNo = null;
		try {
//			newCustNo = util.getEcifCustNo(ecifData.getCustType());
			newCustNo = custNoId;
			if (StringUtil.isEmpty(newCustNo)) {
				ecifData.setStatus(ErrorCode.ERR_ECIF_GEN_ECIFCUSTNO_ERROR);
				log.error("���ɿͻ��Ŵ���{}:{}",ErrorCode.ERR_ECIF_GEN_ECIFCUSTNO_ERROR.getCode(),ErrorCode.ERR_ECIF_GEN_ECIFCUSTNO_ERROR.getChDesc());
				return;
			}
		} catch (Exception e) {
			log.error("������Ϣ", e);
			ecifData.setStatus(ErrorCode.ERR_ECIF_GEN_ECIFCUSTNO_ERROR);
			return;
		}
		// �ͻ���Ϣ
		MCiCustomer customer = null;
		if(ecifData.getWriteModelObj().containsOpModel(MCiCustomer.class.getSimpleName())){
			customer=(MCiCustomer)ecifData.getWriteModelObj().getOpModelByName(MCiCustomer.class.getSimpleName());
		}else{
			customer = new MCiCustomer();
		}
		customer.setCustId(newCustNo);
		customer.setCustType(ecifData.getCustType());
		customer.setCreateDate(new Date());
		customer.setCreateTime(new Timestamp(System.currentTimeMillis()));
		customer.setCustStat(MdmConstants.STATE);
		customer.setCreateBranchNo(ecifData.getBrchNo());
		customer.setCreateTellerNo(ecifData.getTlrNo());
		if (blankFlag) {
			customer.setBlankFlag(MdmConstants.BLANK_FLAG);
		}
		// �������ɵĶ�����Ϊ�������
		ecifData.getWriteModelObj().setOpModelList(customer);
		AddGeneral addGeneral = (AddGeneral) SpringContextUtils
				.getBean("addGeneral");
		try {
			// ����ͨ�ñ��淽������������ɵĶ���������ͻ�ʱ����������
			addGeneral.process(ecifData);
//			ecifData.setEcifCustNo(newCustNo);
			ecifData.setCustId(newCustNo);
		} catch (Exception e) {
			log.error("���ݲ����쳣",e);
			if(ecifData.isSuccess()){
				ecifData.setStatus(ErrorCode.ERR_DB_OPER_ERROR);
			}
			return;
		}
		return;
	}
}
