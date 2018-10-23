/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.service.component.general
 * @�ļ�����IdUtils.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:59:26
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.service.component.general;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.service.facade.IMCIdentifying;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�IdUtils
 * @�������������ͻ�������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:59:26
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:59:26
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ��
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 */
@Service
public class IdUtils implements IMCIdentifying {

	@SuppressWarnings("rawtypes")
	private JPABaseDAO baseDAO;
	private static Logger log = LoggerFactory.getLogger(IdUtils.class);

	/**
	 * ����ECIF�ͻ���EcifCustId, Ϊ��������, ��Ӧ�ͻ���(CUSTOMER)�пͻ���ʶ(cust_id)
	 * ����Ŀ�У�ͨ�����·�ʽ�ҳ�cust_id�ϵ����У� db2
	 * "select SEQSCHEMA,SEQNAME from syscat.SEQUENCES where seqschema='ECIF'"��
	 * �����������ҳ��������У����Ϊ��SEQ_CUST_ID ����������ı䣬����Ҫ�޸ģ��ҵñ�֤����Ϊ����ECIF�ͻ��ż�������������
	 * 
	 * @author: wangtingbang (wangtb@yuchengtech.com)
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String getEcifCustId(String custType) throws NumberFormatException, ConfigurationException, Exception {
		// String seqCustId = "SEQ_CUST_ID";
		String seqCustId = MdmConstants.SEQ_CUST_ID;
		if (seqCustId == null || "".equals(seqCustId)) { throw new Exception("ECIF�ͻ������������������ݿ���������Ϊ�գ����������ļ�������"); }
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		List list = new ArrayList();
		String sCustId = null;
		list = baseDAO.findByNativeSQLWithIndexParam("select " + seqCustId + ".nextval from " + MdmConstants.formSeq,
				null);
		if (list != null && list.size() > 0) {
			sCustId = custType + list.get(0).toString();
		} else {
			sCustId = null;
		}
		log.info("���ݿͻ�����[{}]���ɿͻ�ID[{}]", custType, sCustId);
		return sCustId;
	}

	/**
	 * @author wangtingbang (wangtb@yuchengtech.com)
	 * @param int custType, �ͻ�����:1Ϊ��˽��2Ϊ�Թ���3Ϊͬҵ(������������Ϊ����������CUST_TP ����)
	 * @return String custNo (ECIFϵͳ�ͻ��ţ���λ(�������һλ)Ϊ�ͻ������ͱ�ʶ����λΪ�ͻ���У��λ)
	 *         ����ECIF�ͻ���EcifCustNo, ҵ������, ��Ӧ�ͻ���(CUSTOMER)�пͻ���ʶ(cust_no)
	 *         ����Ŀ�У�ͨ�����·�ʽ�ҳ�cust_no�ϵ����У� db2
	 *         "select SEQSCHEMA,SEQNAME from syscat.SEQUENCES where seqschema='ECIF'"
	 *         �� �����������ҳ��������У����Ϊ��SEQ_CUST_NO
	 *         ����������ı䣬����Ҫ�޸ģ��ҵñ�֤����Ϊ����ECIF�ͻ��ż���ҵ������������
	 *         ���Ŀͻ���11λ��1+9+1����һλΪ1����˽��2���Թ���3��ͬҵ��9λΪ˳��ţ����һλУ��λ��
	 *         ���ɿͻ���У��λ("��λ��2��"�㷨) 1�����ұߵ�һ������(����)��ʼÿ��һλ*2
	 *         2����1�л�ó˻��ĸ�λ������ԭ������δ*2�ĸ�λ������� 3����2�еõ����ܺʹӸ�ֵ����һ����0��β�������м�ȥ e.g.:
	 *         ��У�����ֵĿͻ���Ϊ 4992739871 4 9 9 2 7 3 9 8 7 1 1-> *2 *2 *2 *2 *2
	 *         _______________________ 18 4 6 16 2
	 *         2-> 4+1+8+9+4+7+6+9+1+6+7+2=64
	 *         3-> 70-64=6 ��У��λ�ͻ���Ϊ�� 49927398716
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String getEcifCustNo(String custType) throws NumberFormatException, ConfigurationException, Exception {
		String seqCustNo = "SEQ_CUST_NO";
		if (MdmConstants.TX_CUST_PER_TYPE.equals(custType)) {
			custType = "1";
		} else if (MdmConstants.TX_CUST_ORG_TYPE.equals(custType)) {
			custType = "2";
		} else if (MdmConstants.TX_CUST_BANK_TYPE.equals(custType)) {
			custType = "3";
		} else {
			throw new Exception("�ͻ����Ͳ�֧��");
		}
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		List list = new ArrayList();
		String sCustNo = null;

		list = baseDAO.findByNativeSQLWithIndexParam("select " + seqCustNo + ".nextval from " + MdmConstants.formSeq,
				null);
		if (list != null && list.size() > 0) {
			sCustNo = custType + String.format("%09d", Long.valueOf(list.get(0).toString()));
			// �Ӻ����һλ��ʼ�� ��ǰ��λ��֮����д���ӵ�sum�� (ʾ���в�����־λ��������sCustNo�д�)
			int[] iNumArr = new int[sCustNo.length()];
			for (int idx = 0; idx < sCustNo.length(); idx++) {
				iNumArr[idx] = Integer.parseInt("" + sCustNo.substring(idx, idx + 1));
			}

			int sum = 0;

			for (int idx = iNumArr.length - 1; idx > 1; idx -= 2) {
				sum += iNumArr[idx] * 2 / 10 + iNumArr[idx] * 2 % 10 + Integer.parseInt("" + iNumArr[idx - 1]);
			}

			int check = (10 - sum % 10) % 10;
			sCustNo = sCustNo.substring(0, sCustNo.length()) + check;
			log.info("���ݿͻ�����[{}]���ɿͻ���[{}]", custType, sCustNo);
			return sCustNo;
		} else {
			throw new Exception("�Ҳ������õĿͻ��ţ����ݿ����з���ֵ����");
		}
	}

	/**
	 * ������������(attrName)ѡ�����ݿ��ж�Ӧ���У�֮������attrName�����ݿ��ж�Ӧ��������һ������ֵ������
	 * ���ɹ����������ݿ������У����������������Ҫ��SEQ_ + ATTRNAMEΪ�������У�ATTRNAME����Ӧ�ֶ���һ��
	 * 
	 * @author wangtingbang (wangtb@yuchengtech.com)
	 * @param String
	 *        arrtName, ��������, ��Ӧ���ݱ�Ϊ���������ֶ� (����),�����淶�����ݱ��ֶ���ͬ
	 * @return String AttrId
	 * @throws NumberFormatException
	 * @throws ConfigurationException
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String getPriIdByAttrName(String attrName) throws Exception {
		// TODO Auto-generated method stub
		String seqName = null;
		if (0 != attrName.indexOf("_")) {
			seqName = "SEQ_" + attrName;
		} else {
			throw new Exception("�޷�ʶ�����У��������������ϳ���淶");// TODO, ��ϸ����ECIFϵͳ�쳣
		}
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		List list = new ArrayList();
		String sAttrId = null;

		list = baseDAO.findByNativeSQLWithIndexParam("select " + seqName + ".nextval from " + MdmConstants.formSeq,
				null);
		if (list != null && list.size() > 0) {
			sAttrId = list.get(0).toString();
			return sAttrId;
		} else {
			throw new Exception("�Ҳ������õ����кţ����ݿ����з���ֵ����");// TODO, ��ϸ����ECIFϵͳ�쳣
		}
	}
}
