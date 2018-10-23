/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.plugins.xmlhelper
 * @�ļ�����ResponseDate.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-18-����2:59:17
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.plugins.xmlhelper;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�ResponseDate
 * @����������Ӧ��������ʱ��ӹ�
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-18 ����2:59:17   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-18 ����2:59:17
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class ResponseDate implements IResponseXmlFun {
	private Logger log = LoggerFactory.getLogger(ResponseDate.class);

	/**
	 *@���캯�� 
	 */
	public ResponseDate() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.ytec.mdm.interfaces.common.xmlhelper.IResponseXmlFun#getValueByFun(java.lang.String[])
	 */
	@Override
	public String getValueByFun(Object[] arg) {
		// TODO Auto-generated method stub
		if(arg!=null && arg.length==1){
			try{
				SimpleDateFormat df = new SimpleDateFormat(arg[0].toString());
				return df.format(new Date());
			}catch(Exception e){
				log.error("����ʱ�亯���쳣",e);
			}
			return "";
		}else{
			log.warn("����ʱ�亯������Ϊ��");
			return "";
		}
	}

}
