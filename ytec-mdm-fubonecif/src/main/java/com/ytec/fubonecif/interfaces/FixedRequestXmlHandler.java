/**
 * @��Ŀ����ytec-mdm-fubonecif
 * @������com.ytec.fubonecif.interfaces
 * @�ļ�����FixedRequestXmlHandler.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-4-16-����2:28:06
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.fubonecif.interfaces;

import org.springframework.stereotype.Component;

import com.ytec.mdm.interfaces.socket.normalsocket.coder.FixedRequestCoderHandler;

/**
 * @��Ŀ���ƣ�ytec-mdm-fubonecif 
 * @�����ƣ�FixedRequestXmlHandler
 * @��������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-4-16 ����2:28:06   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-4-16 ����2:28:06
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
@Component
public class FixedRequestXmlHandler extends FixedRequestCoderHandler {
	private byte SP = 32;
	/* (non-Javadoc)
	 * @see com.ytec.mdm.interfaces.socket.normalsocket.coder.FixedRequestCoderHandler#fixedAttrFormat(java.lang.String, int, java.lang.String)
	 */
	@Override
	public byte[] fixedAttrFormat(byte[] src, int leng, String DataType) {
		// TODO Auto-generated method stub
		byte[] b=new byte[leng];
		int length=0;
		if(src!=null){
			length=src.length;
			if(length>leng){
				log.warn("���ݳ���,length[{}]",leng);
				System.arraycopy(src, 0, b, 0, leng);
				length=leng;
			}else if(length>0){
				System.arraycopy(src, 0, b, 0, length);
			}
			for(int i=length;i<leng;i++){
				b[i]=SP;
			}
		}else{
			for(int i=length;i<leng;i++){
				b[i]=SP;
			}
		}
		return b;
	}

}
