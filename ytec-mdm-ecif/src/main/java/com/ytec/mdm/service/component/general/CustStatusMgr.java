/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.service.component.general
 * @�ļ�����CustStatusMgr.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-6-9-����2:32:03
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.service.component.general;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.ytec.mdm.base.bo.CustStatus;
import com.ytec.mdm.base.util.StringUtil;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�CustStatusMgr
 * @���������ͻ�״̬�ж�����
 * @��������:����ͻ�״̬�Ĳ��ɾ�ĵ�Ȩ�ޣ�Ϊ�ͻ���Ϣ�������ж�����
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-6-9 ����2:32:03
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-6-9 ����2:32:03
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ�� -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class CustStatusMgr {
	/**
	 * @��������:custStatusMgr
	 * @��������:�ͻ�״̬�ж�����
	 * @since 1.0.0
	 */
	private static CustStatusMgr custStatusMgr = new CustStatusMgr();
	/**
	 * @��������:custStatusMap
	 * @��������:�ͻ�״̬����
	 * @since 1.0.0
	 */
	private Map<String, CustStatus> custStatusMap = new HashMap<String, CustStatus>();

	/**
	 * @���캯��
	 */
	public CustStatusMgr() {
	}

	/**
	 * @��������:getInstance
	 * @��������:������ȡ����
	 * @�����뷵��˵��:
	 * @return
	 * @�㷨����:
	 */
	public static CustStatusMgr getInstance() {
		return custStatusMgr;
	}

	/**
	 * @��������:init
	 * @��������:��ʼ������
	 * @�����뷵��˵��:
	 * @param arg
	 * @throws Exception
	 * @�㷨����:
	 */
	public void init(Map arg) throws Exception {
		/** �ͻ�״̬���� ***/
		custStatusMap.clear();
		/** ��ȡ�ͻ�״̬���� */
		Collection<String> c = arg.values();
		Iterator<String> it = c.iterator();
		while (it.hasNext()) {
			String value_i[] = it.next().split("\\:");
			if (value_i != null && value_i.length == 4) {
				// װ���ɶ���
				// �ͻ�״̬Ȩ��
				// custStatus=״̬��:����:�Ƿ�����(true����,false�쳣):Ȩ��(�ͻ��������ã�ά������ѯ)
				custStatusMap.put(
						value_i[0],
						new CustStatus(value_i[0], value_i[1], Boolean
								.parseBoolean(value_i[2]), Integer
								.parseInt(value_i[3])));
			}
		}
	}

	/***
	 * �ͻ�״̬��֤
	 * 
	 * @param custStat
	 *            �ͻ�״̬����
	 * @return
	 */
	public CustStatus getCustStatus(String custStat) {
		if (StringUtil.isEmpty(custStat) || custStatusMap.isEmpty()) {
			return null;
		}
		CustStatus custStatObj = custStatusMap.get(custStat);
		if (custStatObj == null) {
			custStatObj = new CustStatus(custStat, "�쳣", false, 0);
		}
		return custStatObj;
	}
}
