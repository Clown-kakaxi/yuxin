/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.common.sensitinfo
 * @�ļ�����SensitInforXmlFilter.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-10:27:17
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.interfaces.common.sensitinfo;

import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�SensitInforXmlFilter
 * @��������������Ϣ����
 * @��������:����XML��������Ϣ����
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����10:27:36   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����10:27:36
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class SensitInforXmlFilter extends AbsSensitiveFilter implements SensitiveFilter {
	/**
	 * The log.
	 * 
	 * @��������:��־
	 */
	private Logger log = LoggerFactory
			.getLogger(SensitInforXmlFilter.class);
	
	/**
	 * @��������:doInforFilter
	 * @��������:������Ϣ����
	 * @�����뷵��˵��:
	 * 		@param xmlStr  ��Ϣ
	 * 		@param sensitInforSet ������Ϣ����
	 * 		@return
	 * @�㷨����:
	 */
	public String doInforFilter(String xmlStr,Set sensitInforSet) {
		try {
			if(sensitInforSet==null||sensitInforSet.isEmpty()){
				sensitInforSet=sensitInforMap;
			}else{
				sensitInforSet.addAll(sensitInforMap);
			}
			return parser(xmlStr.getBytes(),sensitInforSet);
		} catch (Exception e) {
			log.error("�ͻ�������Ϣ����ʧ��", e);
		}
		return xmlStr;
	}
	
	private String parser(byte[] bytes,Set sensitInforSet){
		int state;   //0:��������,1:��ǩ ,2����    
		byte[] bb=new byte[512];
		int length=bytes.length;
		boolean skip=false;
		state=0;
		byte b;
		byte c;
		int j;
		int blength=0;
		for(int index=0;index<length;index++){
			b=bytes[index];
			switch (b) {
				case '<':
					j=index;
					c=bytes[j+1];
					if(c=='/'){//������ǩ
//						if(state==2&& blength!=0 && skip){
//							
//						}
						state=0;
					}else if(c=='!'){//ע��
						if(bytes[j+2]=='-'&&bytes[j+3]=='-'){
							state=0;
						}else{
							state=1;
						}
					}else if(c=='?'){//Э��ͷ
						if(bytes[j+2]=='x'&&bytes[j+3]=='m'&&bytes[j+4]=='l'){
							state=0;
						}else{
							state=1;
						}
					}else{//��ǩ��ʼ
						state=1;
					}
					blength=0;
					break;
				case '>': 
					j=index;
					c=bytes[j-1];
					if(c=='/'){//��ʼ��ǩ����,����Ϣ
						state=0;
					}else if(c=='?'){//   />
						state=0;
					}else if(c=='-'){//   ->
						if(bytes[j-2]=='-'){//ע�ͽ���
							state=0;
						}else{
							if(state==1){
								String nodeName=new String(bb,0,blength);
								if(sensitInforSet.contains(nodeName)){
									skip=true;
								}else{
									skip=false;
								}
								state=2;
							}else{
								state=0;
							}
						}
					}else{ //>
						if(state==1){
							String nodeName=new String(bb,0,blength);
							if(sensitInforSet.contains(nodeName)){
								skip=true;
							}else{
								skip=false;
							}
							state=2;
						}else{
							state=0;
						}
					}
					blength=0;
					break;
				default:
					if(state==1){
						bb[blength++]=b;
					}else if(state==2 && skip&&!Character.isWhitespace((int)bytes[index])){
						bytes[index]='*';
					}
			}
		}
		return new String(bytes);
	}

	/* (non-Javadoc)
	 * @see com.ytec.mdm.interfaces.common.sensitinfo.SensitiveFilter#init(java.util.Map)
	 */
	@Override
	public void init(Map arg) throws Exception {
		// TODO Auto-generated method stub
		super.initXmlSensitive(arg);
	}

	/* (non-Javadoc)
	 * @see com.ytec.mdm.interfaces.common.sensitinfo.SensitiveFilter#isSensitiveInfo(java.lang.String)
	 */
	@Override
	public boolean isSensitiveInfo(String info) {
		// TODO Auto-generated method stub
		return this.sensitInforMap.contains(info);
	}
}
