/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.fubonecif.service.svc.comb
 * @�ļ�����OpenCustAccForOrg.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-12:05:24
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

import com.ytec.fubonecif.domain.MCiCustomer;
import com.ytec.fubonecif.domain.MCiIdentifier;
import com.ytec.fubonecif.service.svc.atomic.Accont;
import com.ytec.fubonecif.service.svc.atomic.AddGeneral;
import com.ytec.mdm.base.bo.CustStatus;
import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.integration.transaction.facade.IEcifBizLogic;
import com.ytec.mdm.server.common.BusinessCfg;
import com.ytec.mdm.service.component.biz.comidentification.GetContIdByType;
import com.ytec.mdm.service.component.general.CustStatusMgr;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�OpenCustAccForOrg
 * @����������֯�ͻ�����
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����12:05:24
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����12:05:24
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ��
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 */
@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class OpenCustAccForOrg implements IEcifBizLogic {
	//add by liuming 20170717
	private JPABaseDAO baseDAO;
	private static Logger log = LoggerFactory.getLogger(OpenCustAccForOrg.class);

	public void process(EcifData ecifData) throws Exception {
		//add by liuming 20170717
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		log.info("�Թ����������࿪ʼ����");
		boolean isBlank = true;
		String custName = null;
		String custNameTemp = null;
		Map opMp = ecifData.getWriteModelObj().getOperMap();

		opMp.put(MdmConstants.TX_DEF_GETCONTID_SRCSYSCD, ecifData.getOpChnlNo());
		opMp.put(MdmConstants.TX_CUST_TYPE, MdmConstants.TX_CUST_ORG_TYPE);
		ecifData.setCustType(MdmConstants.TX_CUST_ORG_TYPE);

		// ��������֤�Ƿ�������
		boolean flag = false;
		flag = BusinessCfg.getBoolean("noIdentIsAdd");
		log.info("����֤�Ƿ�������:{}", flag);
		List generalInfoList = ecifData.getWriteModelObj().getOpModelList();
		// ��װ����ʶ�����֤ʶ��,�����ȼ�˳��
		List<MCiIdentifier> identList = new ArrayList();
		MCiIdentifier itemp = null;
		for (Object obj : generalInfoList) {
			if (obj.getClass().equals(MCiIdentifier.class)) {
				flag = true;
				MCiIdentifier ident = (MCiIdentifier) obj;
				itemp = new MCiIdentifier(ident.getIdentType(), ident.getIdentNo(), ident.getIdentCustName());
				opMp.put(MdmConstants.TX_DEF_GETCONTID_IDENTTPCD, ident.getIdentType());
				opMp.put(MdmConstants.TX_DEF_GETCONTID_IDENTNO, ident.getIdentNo());
				opMp.put(MdmConstants.TX_DEF_GETCONTID_IDENTNAME, ident.getIdentCustName());
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
				isBlank = false;
			}
		}

		if (!flag) {
			String msg = String.format("%s/֤����Ϣ��ֵ,֤����Ϣ(֤�����͡�֤�����롢֤������)����ȫ��������",
					ErrorCode.ERR_ECIF_NOT_EXIST_IDENT.getChDesc());
			ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_EXIST_IDENT.getCode(), msg);
			log.error("{}:{}", ErrorCode.ERR_ECIF_NOT_EXIST_IDENT.getCode(), msg);
			return;
		}

		if (custName == null) {
			custName = identList.get(0).getIdentCustName();
		}

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
						log.info("�ͻ�({})״̬({}),���¿�������", ecifData.getCustId(), custStatCtrl.getCustStatusDesc());
					} else if (!custStatCtrl.isUpdate()) {
						log.warn("�ͻ�({})״̬{}", ecifData.getCustId(), custStatCtrl.getCustStatusDesc());
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
					//add begin by liuming 20170717
					boolean isPotential = false;//�Ƿ�ΪǱ�ڿͻ�
					String selectSql="select 1 from M_CI_CUSTOMER t where t.potential_flag='1' and t.cust_id='"+ecifData.getCustId()+"'";
					List list = baseDAO.findByNativeSQLWithIndexParam(selectSql, null);
					if(list != null && list.size() >0){
						isPotential = true;
					} 
					
					//add end 
					
					addGeneral.process(ecifData);
					
					ecifData.getWriteModelObj().setResult("custNo", ecifData.getCustId());
					
					ecifData.getWriteModelObj().setResult(MdmConstants.TX_DEF_GETCONTID_ECIFCUSTNO,
							ecifData.getCustId());
					//modify by liuming 20170717 ����Ǻ��Ŀ�����Ǳ�ڿͻ�����ʽ�ͻ����Ϻϲ������޸�potential_flagΪ0
					if(MdmConstants.SRC_SYS_CD_CB.equals(ecifData.getOpChnlNo())){
						if(isPotential){
							String updateSql="update M_CI_CUSTOMER t set t.potential_flag='0' where t.cust_id='"+ecifData.getCustId()+"'";
							baseDAO.batchExecuteNativeWithIndexParam(updateSql,null);
							ecifData.setSuccess(true);
						} 
						else{
							ecifData.setStatus(ErrorCode.ERR_ECIF_EXIST_CUST_UPDATE.getCode(),
									ErrorCode.ERR_ECIF_EXIST_CUST_UPDATE.getChDesc());
							ecifData.setSuccess(true);
						}
					}
					//add end 
					else{
						ecifData.setStatus(ErrorCode.ERR_ECIF_EXIST_CUST_UPDATE.getCode(),
								ErrorCode.ERR_ECIF_EXIST_CUST_UPDATE.getChDesc());
						ecifData.setSuccess(true);
					}
				} catch (Exception e) {
					log.error("���ݲ����쳣", e);
					if (ecifData.isSuccess()) {
						ecifData.setStatus(ErrorCode.ERR_DB_OPER_ERROR);
					}
				}
				return;
			} else {
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
				// nametilte = new MCiNametitle();
				// nametilte.setCustName(custName);
				// ecifData.getWriteModelObj().setOpModelList(nametilte);
				// }
				accont.process(ecifData, isBlank);
				// ecifData.getWriteModelObj().setResult(MdmConstants.TX_DEF_GETCONTID_ECIFCUSTNO,
				// ecifData.getEcifCustNo());
				ecifData.getWriteModelObj().setResult(MdmConstants.TX_DEF_GETCONTID_ECIFCUSTNO, ecifData.getCustId());
			} catch (Exception e) {
				log.error("������Ϣ", e);
				ecifData.setStatus(ErrorCode.ERR_SERVER_BIZLOGIC_ERROR);
			}
		}
		return;
	}

}
