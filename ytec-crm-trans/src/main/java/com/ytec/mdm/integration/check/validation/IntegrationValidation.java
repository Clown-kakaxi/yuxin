/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.check.validation
 * @�ļ�����IntegrationValidation.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-10:51:04
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 *
 */
package com.ytec.mdm.integration.check.validation;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.integration.transaction.facade.IVerifChain;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�IntegrationValidation
 * @�����������ɲ㱨��У��
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����10:51:05
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����10:51:05
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 *
 */
public class IntegrationValidation extends AbstractValidationChain {
	private static IVerifChain validationHead=new IntegrationValidation();
	public void init(Map arg) throws Exception{
		Collection<String> c = arg.values();
		Iterator<String> it = c.iterator();
		Class clazz=null;
		IVerifChain cc=null;
		IVerifChain point=validationHead;
		while(it.hasNext()) {
			clazz = Class.forName(it.next());
			cc=(IVerifChain)clazz.newInstance();
			point.addChain(cc);
			point=cc;
		}
		point=null;
	}

	public IntegrationValidation() {
	}


	/**
	 * @��������:getInstance
	 * @��������:��ȡУ�������
	 * @�����뷵��˵��:
	 * 		@return
	 * @�㷨����:
	 */
	public static IVerifChain getInstance(){
		return validationHead;
	}


	@Override
	public boolean reqMsgValidation(EcifData ecifData) {
		// TODO Auto-generated method stub
		if (StringUtil.isEmpty(ecifData.getTxCode())) {
			ecifData.setStatus(ErrorCode.ERR_XML_FORMAT_INVALID.getCode(),
					"���״���Ϊ��");
			log.warn("���״���Ϊ��");
			return false;
		}
		/** ����ϵͳ */
		if (StringUtil.isEmpty(ecifData.getOpChnlNo())) {
			ecifData.setStatus(ErrorCode.ERR_XML_FORMAT_INVALID.getCode(),
					"����ϵͳ�Ż�������Ϊ��");
			log.warn("����{}����ϵͳ�Ż�������Ϊ��",ecifData.getTxCode());
			return false;
		}
		return true;
	}

}
