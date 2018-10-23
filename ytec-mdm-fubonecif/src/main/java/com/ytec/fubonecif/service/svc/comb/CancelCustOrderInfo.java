package com.ytec.fubonecif.service.svc.comb;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.integration.transaction.facade.IEcifBizLogic;

/**
 * @��Ŀ���ƣ�ytec-mdm-fubonecif
 * @�����ƣ�CancelCustOrderInfo
 * @��������ȡ���ͻ�ԤԼ
 * @��������:
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ�� -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 */
@Service
public class CancelCustOrderInfo implements IEcifBizLogic {
	private static Logger log = LoggerFactory.getLogger(CancelCustOrderInfo.class);
	private JPABaseDAO baseDAO;

	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public void process(EcifData ecifData) throws Exception {
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		Element body = ecifData.getBodyNode();// ��ȡ�ڵ�
		String txCode = body.element("txCode").getTextTrim();// ��ȡ���ױ��
		if(StringUtils.isEmpty(txCode)){
			String msg = "��Ϣ����������������ڵ���txCode������Ϊ��";
			log.error(msg);
			ecifData.setStatus(ErrorCode.ERR_XML_FILTER_VALUE_IS_NULL.getCode(), msg);
			return;
		}
		// String txName = body.element("txName").getTextTrim();// ��ȡ��������
		// String authType = body.element("authType").getTextTrim();// ��ȡȨ�޿�������
		// String authCode = body.element("authCode").getTextTrim();// ��ȡȨ�޿��ƴ���
		try {
			Element e_cancel = body.element("orderCancel");
			if(e_cancel == null){
				String msg = "��Ϣ��������������û��orderCancel�ڵ�";
				log.error(msg);
				ecifData.setStatus(ErrorCode.ERR_XML_FILTER_VALUE_IS_NULL.getCode(), msg);
				return;
			}
			String sOrderSerialNo = e_cancel.element("sOrderSerialNo").getTextTrim();// ֤������
			String certid = e_cancel.element("certid").getTextTrim();// ԤԼ��
			if(StringUtils.isEmpty(sOrderSerialNo) || StringUtils.isEmpty(certid)){
				String msg = "��Ϣ����������������ڵ���sOrderSerialNo��certid������Ϊ��";
				log.error(msg);
				ecifData.setStatus(ErrorCode.ERR_XML_FILTER_VALUE_IS_NULL.getCode(), msg);
				return;
			}
			//����֤�����롢ԤԼ�Ų�ѯ�ͻ���
			String sql = "SELECT * FROM ("      
					+ " SELECT T.CUSTOMERID,ROW_NUMBER()OVER (ORDER BY REGEXP_SUBSTR(CUSTOMERID,'[[:digit:]]+$') DESC) MM  FROM O_WZ_CUSTOMER_INFO T"
					+ " WHERE trim(SORDERSERIALNO)=trim('"+sOrderSerialNo+"') AND trim(CERTID)=trim('"+certid+"'))"
					+ " WHERE MM=1";//;
			List<Object[]> l_res1 = this.baseDAO.findByNativeSQLWithIndexParam(sql, null);
			if(l_res1 == null || l_res1.size() != 1){
				String msg = "����֤���ź�ԤԼ�ͻ��Ų�ѯ�ͻ���Ϣʧ��";
				log.error(msg);
				ecifData.setStatus(ErrorCode.WRN_NONE_FOUND.getCode(), msg);
				return;
			}
			Object[] res1 = l_res1.get(0);
			if(res1 != null && res1.length >= 1){
				String customerId = res1[0].toString();
				String sql_up = "update O_WZ_CUSTOMER_INFO t set t.APP_STATUS='0' where t.CUSTOMERID='"+customerId+"'";
				int resInt = this.baseDAO.batchExecuteNativeWithIndexParam(sql_up, null);
				if(resInt >= 1){
					String msg = "�ɹ�����ԤԼ��Ϣ";
					log.info(msg);
					ecifData.setStatus(ErrorCode.SUCCESS.getCode(), msg);
					ecifData.setSuccess(true);
				}else{
					String msg = "����ԤԼ��Ϣʧ��";
					log.info(msg);
					ecifData.setStatus(ErrorCode.WRN_NONE_UPDATE.getCode(), msg);
				}
			}else{
				String msg = "����ԤԼ��Ϣʧ��";
				log.info(msg);
				ecifData.setStatus(ErrorCode.WRN_NONE_UPDATE.getCode(), msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			String msg = "����ԤԼ��Ϣʧ��";
			log.info(msg);
			ecifData.setStatus(ErrorCode.ERR_ALL.getCode(), msg);
		}
	}

}
