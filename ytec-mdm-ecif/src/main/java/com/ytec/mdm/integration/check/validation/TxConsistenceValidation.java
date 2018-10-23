/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.check.validation
 * @�ļ�����TxReqBodyValidation.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-4-15-����10:56:41
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.integration.check.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.dom4j.Element;
import org.dom4j.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.fubonecif.service.svc.atomic.AddGeneral;
import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.ReflectionUtils;
import com.ytec.mdm.integration.transaction.core.TxModelHolder;
import com.ytec.mdm.integration.transaction.model.TxModel;
import com.ytec.mdm.server.common.ServerConfiger;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�TxConsistenceValidation
 * @����������������һ������֤
 * @��������:
 * @�����ˣ�shangjf@yuchengtech.com
 * @����ʱ�䣺2014-11-1 ����10:56:41   
 * @�޸��ˣ�shangjf@yuchengtech.com
 * @�޸�ʱ�䣺2014-11-1 ����10:56:41
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class TxConsistenceValidation extends AbstractValidationChain {

	/* (non-Javadoc)
	 * @see com.ytec.mdm.integration.check.validation.AbstractValidationChain#reqMsgValidation(com.ytec.mdm.base.bo.EcifData)
	 */
	@Override
	public boolean reqMsgValidation(EcifData ecifData) {
		
		/** �������� */
		List<String> consistenceList = ServerConfiger.transConsistenceMap.get(ecifData.getTxCode());//ȡ��һ��������Ҫ���е�һ����У���б�
		
		for(String name:consistenceList){  //ȡ��һ��һ����У��
			
			Node node = ServerConfiger.consistences.selectSingleNode("//consistence[@name='"+ name +"']");
			List<Element> msgList = node.selectNodes("msg");
			List<String[]> compareList  = new ArrayList();
			
			for(int i=0;i<msgList.size();i++){
				Element msg = msgList.get(i);
				String path = msg.attributeValue("path");
				String condition = msg.attributeValue("condition");
				String conditionvalue = msg.attributeValue("conditionvalue");
				List<Element> valueList = ecifData.getBodyNode().selectNodes(path);   //��ȡ����ֵ
				if(valueList==null||valueList.size()==0){							  //����·�����ô���
					continue;
				}
				if(valueList.size()<=1){		//һ����ֻ��һ�������ֶ�ֵ���������
					if(condition==null){		//û������
						if(valueList.get(0)!=null&& !valueList.get(0).getTextTrim().equals("")){	//���Ϊnull����ֵ�Ͳ���ֵ֤һ����
							compareList.add(new String[]{valueList.get(0).getTextTrim(),path});
						}
					}else{						//������
						String conditiontext = valueList.get(0).getParent().element(condition).getTextTrim();
						if(conditiontext!=null&&!conditiontext.trim().equals("")&&conditiontext.equals(conditionvalue)){
							if(valueList.get(0)!=null&& !valueList.get(0).getTextTrim().equals("")){	//���Ϊnull����ֵ�Ͳ���ֵ֤һ����
								compareList.add(new String[]{valueList.get(0).getTextTrim(),path});
							}
						}					
					}
				}else{ 							//һ���԰�����������ֶ�ֵ���������
					for(Element val:valueList){
						String conditiontext = val.getParent().element(condition).getTextTrim();
						if(conditiontext!=null&&!conditiontext.trim().equals("")&&conditiontext.equals(conditionvalue)){
							if(val!=null&& !val.getTextTrim().equals("")){	//���Ϊnull����ֵ�Ͳ���ֵ֤һ����
								compareList.add(new String[]{val.getTextTrim(),path});
							}
						}
					}
				}
			}
			
			if(compareList!=null&&compareList.size()>1){  //��֤�����е������ֶ������Ƿ�һ��
				String[] value = compareList.get(0);
				for(String compare[]:compareList){
					if(!compare[0].trim().equals("")&&!compare[0].equals(value[0])){//���Ϊnull����ֵ�Ͳ���ֵ֤һ����
						log.warn("�����ֶ����ݲ�һ�¡�{}:{},{}:{}��",value[1],value[0],compare[1],compare[0]);
						ecifData.setStatus(ErrorCode.ERR_XML_FILTER_VALUE_CONSISTENCE);

						return false;
					}
				}
			}
		}
		
		return true;
	}

}
