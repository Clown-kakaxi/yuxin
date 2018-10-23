/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.transaction.util
 * @�ļ�����SimpleDecide.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-23-����4:38:10
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.integration.transaction.util;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.integration.transaction.facade.IExtCaseDispatch;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�SimpleDecide
 * @���������򵥷�֧�����ж�����
 * @��������:ͨ�������ֶ���ĳ��ֵ�ж���֧
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-23 ����4:38:10   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-23 ����4:38:10
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class SimpleDecide implements IExtCaseDispatch {
	private String decideAttrName;

	/* (non-Javadoc)
	 * @see com.ytec.mdm.integration.transaction.facade.ICaseDispatch#decide(com.ytec.mdm.base.bo.EcifData)
	 */
	@Override
	public String decide(EcifData ecifData) {
		// TODO Auto-generated method stub
		String v=(String)ecifData.getParameterMap().get(decideAttrName);
		if(StringUtil.isEmpty(v)){
			ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_EXIST_OTHER.getCode(), "�����ж���ʶ%s����Ϊ��", decideAttrName);
			return null;
		}else{
			return v;
		}
	}

	/* (non-Javadoc)
	 * @see com.ytec.mdm.integration.transaction.facade.IExtCaseDispatch#init(java.lang.String)
	 */
	@Override
	public void init(String args) {
		// TODO Auto-generated method stub
		decideAttrName=args;
	}

}
