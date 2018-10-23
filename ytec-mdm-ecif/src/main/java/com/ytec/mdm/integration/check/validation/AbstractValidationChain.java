/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.check.validation
 * @�ļ�����AbstractValidationChain.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-10:50:08
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.integration.check.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.integration.transaction.facade.IVerifChain;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�AbstractValidationChain
 * @��������������У����
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����10:50:09   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����10:50:09
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public abstract class AbstractValidationChain implements IVerifChain {
	protected static Logger log = LoggerFactory.getLogger(AbstractValidationChain.class);
	/**
	 * @��������:nextChain
	 * @��������:��һ��У��
	 * @since 1.0.0
	 */
	protected IVerifChain nextChain = null;

	@Override
	public void addChain(IVerifChain c) {
		// TODO Auto-generated method stub
		this.nextChain=c;
	}

	@Override
	public IVerifChain getChain() {
		// TODO Auto-generated method stub
		return nextChain;
	}
	
	/* (non-Javadoc)
	 * @see com.ytec.mdm.integration.transaction.facade.IVerifChain#sendToChain(com.ytec.mdm.base.bo.EcifData)
	 */
	public boolean sendToChain(EcifData ecifData){
		 if(reqMsgValidation(ecifData)){
			 if(nextChain != null){
				 return nextChain.sendToChain(ecifData);  
			 }else {
				 return true;
			 }
		 }else{
			 return false;
		 }
		 
	}
	public abstract boolean reqMsgValidation(EcifData ecifData);

}
