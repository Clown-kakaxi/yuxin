/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.service.component.biz.identification
 * @�ļ�����GetCustomerName.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:58:15
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.service.component.biz.identification;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.SpringContextUtils;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�GetCustomerName
 * @����������ȡ�ͻ��Ļ���
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:58:16
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:58:16
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ��
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 */
@Service
@SuppressWarnings("rawtypes")
public class GetCustomerName {
	private JPABaseDAO baseDAO;

	/**
	 * @param custId �ͻ���ʶ
	 * @return String ����
	 */
	public String bizGetCustName(Object custId) {
		String custName = null;
		if (custId != null) {
			baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
			/**
			 * FBECIF��NameTitle��ɾ��
			 * List result = baseDAO.findByNativeSQLWithIndexParam("SELECT CUST_NAME FROM M_CI_NAMETITLE WHERE CUST_ID=?",custId);
			 */
			List result = baseDAO.findByNativeSQLWithIndexParam("SELECT CUST_NAME FROM M_CI_CUSTOMER WHERE CUST_ID=?",
					custId);
			// �ɹ����ؿͻ���ʶ��ʧ�ܷ������ݲ�����
			if (result != null && !result.isEmpty()) {
				custName = result.get(0) == null ? null : result.get(0).toString();
			}
		}
		// ���ػ���
		return custName;
	}
}
