/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.fubonecif.service.svc.comb
 * @�ļ�����OpenCustAccForBank.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-12:05:12
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.fubonecif.service.svc.comb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ytec.mdm.base.bo.CustStatus;
import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.integration.transaction.facade.IEcifBizLogic;
import com.ytec.mdm.server.common.BusinessCfg;
import com.ytec.mdm.service.bo.Identifier;
import com.ytec.mdm.service.component.biz.comidentification.GetContIdByType;
import com.ytec.mdm.service.component.general.CustStatusMgr;
import com.ytec.fubonecif.service.svc.atomic.Accont;
import com.ytec.fubonecif.service.svc.atomic.AddGeneral;
import com.ytec.fubonecif.domain.MCiCustomer;
//import com.ytec.fubonecif.domain.MCiNametitle;
//import com.ytec.fubonecif.domain.MCiOrgIdentifier;
import com.ytec.fubonecif.domain.MCiIdentifier;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�OpenCustAccForBank
 * @��������ͬҵ�ͻ�����
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����12:05:13
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����12:05:13
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ��
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 */
@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class OpenCustAccForBank implements IEcifBizLogic {
	private static Logger log = LoggerFactory.getLogger(OpenCustAccForBank.class);

	public void process(EcifData ecifData) throws Exception {
		// TODO Auto-generated method stub
		boolean isBlank = true;
		String custName = null;
		// MCiNametitle nametilte = null;
		String custNameTemp = null;
		Map opMp = ecifData.getWriteModelObj().getOperMap();

		opMp.put(MdmConstants.TX_DEF_GETCONTID_SRCSYSCD, ecifData.getOpChnlNo());
		opMp.put(MdmConstants.TX_CUST_TYPE, MdmConstants.TX_CUST_BANK_TYPE);
		ecifData.setCustType(MdmConstants.TX_CUST_BANK_TYPE);

		// ��������֤�Ƿ�������
		boolean flag = false;
		flag = BusinessCfg.getBoolean("noIdentIsAdd");
		List generalInfoList = ecifData.getWriteModelObj().getOpModelList();
		// ��װ����ʶ�����֤ʶ��,�����ȼ�˳��
		List<Identifier> identList = new ArrayList();
		Identifier itemp = null;
		for (Object obj : generalInfoList) {
			if (obj.getClass().equals(MCiIdentifier.class)) {
				flag = true;
				MCiIdentifier ident = (MCiIdentifier) obj;
				itemp = new Identifier(ident.getIdentType(), ident.getIdentNo(), ident.getIdentCustName());
				identList.add(itemp);
				/** ȡ���� **/
				if (MdmConstants.OPENIDENTFLAG.equals(ident.getIsOpenAccIdent())) {
					custName = ident.getIdentCustName();
				}
				if (custNameTemp == null) {
					custNameTemp = ident.getIdentCustName();
				} else {
					if (!custNameTemp.equals(ident.getIdentCustName())) {
						ecifData.setStatus(ErrorCode.ERR_ECIF_CUSTNAME.getCode(), "֤����Ϣ�еĻ�����ͳһ");
						return;
					}
				}
			} else {
				// if (obj.getClass().equals(MCiNametitle.class)) {
				// nametilte = (MCiNametitle) obj;
				// }
				isBlank = false;
			}
		}

		if (!flag) {
			ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_EXIST_IDENT.getCode(),
					ErrorCode.ERR_ECIF_NOT_EXIST_IDENT.getChDesc());
			return;
		}

		if (custName == null) {
			custName = identList.get(0).getIdentCustName();
		}
		// if (nametilte != null) {
		// nametilte.setCustName(custName);
		// }

		// �����֤ʶ����Ϣ
		opMp.put("identList", identList);

		// ʶ��
		GetContIdByType findContId = (GetContIdByType) SpringContextUtils.getBean("getContIdByType");
		findContId.bizGetContId(ecifData);

		if (MdmConstants.checkCustomerStatus && ecifData.isSuccess()) {
			CustStatus custStatCtrl = null;
			if ((custStatCtrl = CustStatusMgr.getInstance().getCustStatus(ecifData.getCustStatus())) != null) {
				if (!custStatCtrl.isNormal()) {
					if (custStatCtrl.isReOpen()) {
						MCiCustomer customer = null;
						if (ecifData.getWriteModelObj().containsOpModel(MCiCustomer.class.getSimpleName())) {
							customer = (MCiCustomer) ecifData.getWriteModelObj().getOpModelByName(
									MCiCustomer.class.getSimpleName());
						} else {
							customer = new MCiCustomer();
						}
						customer.setCustStat(MdmConstants.STATE);
						ecifData.getWriteModelObj().setOpModelList(customer);
						ecifData.getWriteModelObj().setDivInsUpd(false);
						// log.info("�ͻ�({})״̬({}),���¿�������",ecifData.getEcifCustNo(),custStatCtrl.getCustStatusDesc());
						log.info("�ͻ�({})״̬({}),���¿�������", ecifData.getCustId(), custStatCtrl.getCustStatusDesc());
					} else if (!custStatCtrl.isUpdate()) {
						log.warn("�ͻ�({})״̬{}", ecifData.getCustId(), custStatCtrl.getCustStatusDesc());
						// ecifData.setStatus(ErrorCode.ERR_ECIF_CUST_STATUS.getCode(),"�ÿͻ�%s״̬:%s", ecifData.getEcifCustNo(),custStatCtrl.getCustStatusDesc());
						ecifData.setStatus(ErrorCode.ERR_ECIF_CUST_STATUS.getCode(), "�ÿͻ�%s״̬:%s",
								ecifData.getCustId(), custStatCtrl.getCustStatusDesc());
						return;
					}
				}
			}
		}
		if (ecifData.isSuccess()) {
			if (!ecifData.getWriteModelObj().isDivInsUpd()) {
				AddGeneral addGeneral = (AddGeneral) SpringContextUtils.getBean("addGeneral");
				try {
					addGeneral.process(ecifData);
					// ecifData.getWriteModelObj().setResult("custNo", ecifData.getEcifCustNo());
					ecifData.getWriteModelObj().setResult("custNo", ecifData.getCustId());
				} catch (Exception e) {
					log.error("���ݲ����쳣", e);
					if (ecifData.isSuccess()) {
						ecifData.setStatus(ErrorCode.ERR_DB_OPER_ERROR);
					}
				}
				return;
			} else {
//				ecifData.setStatus(ErrorCode.ERR_ECIF_EXIST_CUST.getCode(), "�ͻ��Ѵ���:" + ecifData.getEcifCustNo());
//				ecifData.getWriteModelObj().setResult("custNo", ecifData.getEcifCustNo());
				ecifData.setStatus(ErrorCode.ERR_ECIF_EXIST_CUST.getCode(), "�ͻ��Ѵ���:" + ecifData.getCustId());
				ecifData.getWriteModelObj().setResult("custNo", ecifData.getCustId());
				return;
			}
		} else {
			if (!ErrorCode.ERR_ECIF_NOT_EXIST_CONTID.getCode().equals(ecifData.getRepStateCd())) {
				log.warn("���ط�����������:{}", ecifData.getDetailDes());
				return;
			}
			// ʶ�𲻳ɹ�������
			Accont accont = (Accont) SpringContextUtils.getBean("accont");
			ecifData.resetStatus();
			try {
				// /*** ���ӻ��� **/
				// if (nametilte == null) {
				// nametilte = new MCiNametitle();
				// nametilte.setCustName(custName);
				// ecifData.getWriteModelObj().setOpModelList(nametilte);
				//
				// }
				accont.process(ecifData, isBlank);
//				ecifData.getWriteModelObj().setResult(MdmConstants.TX_DEF_GETCONTID_ECIFCUSTNO,
//						ecifData.getEcifCustNo());
				ecifData.getWriteModelObj().setResult(MdmConstants.TX_DEF_GETCONTID_ECIFCUSTNO,
						ecifData.getCustId());
			} catch (Exception e) {
				log.error("������Ϣ", e);
				ecifData.setStatus(ErrorCode.ERR_SERVER_BIZLOGIC_ERROR);
			}
		}
		return;
	}

}
