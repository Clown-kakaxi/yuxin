package com.ytec.mdm.service.component.biz.redundance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.stereotype.Service;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.util.ReflectionUtils;
import com.ytec.mdm.server.common.ServerConfiger;

@Service
@SuppressWarnings(
{ "rawtypes", "unchecked" })
public class UpdateRedundance {
	
	public List getRedundanceSql(EcifData ecifData, Object newObj,
			Object oldObj) {
		
		List sqlList = new ArrayList();
		
		// �޸������ֶ���Ϣ
		String simpleName = newObj.getClass().getSimpleName();
		if (ServerConfiger.redundanceClassList.contains(simpleName)) {//������Ҫ�������ദ�����
			//��Ҫ�������ദ����ֶ�ֵ�����˱仯�����ұ仯���ֶ����ڵļ�¼���Ϲ����������磺��ѡ��ַ��
			List<String> PropertiesList = ServerConfiger.redundanceClassPropertyMap.get(simpleName);
			if(PropertiesList!=null){
				
				for(String fieldName:PropertiesList){
					Object oldValue = ReflectionUtils.getFieldValue(oldObj, fieldName);
					Object newValue = ReflectionUtils.getFieldValue(newObj, fieldName);
					boolean isDo = false;
					
					if(newValue!=null&&oldValue!=null&&!newValue.toString().equals(oldValue.toString())){
						isDo = true;
					}else if(newValue!=null&&(oldValue==null||oldValue.equals(""))){
						isDo = true;
					}
//					else if(newValue!=null&&oldValue!=null&&newValue.toString().equals(oldValue.toString())){
//						isDo = true;
//					} ֻ�����ݷ����޸ĵ�ʱ�����ͬ����֮ǰ��������δ�޸�Ҳͬ����Ϊ�˽���ͻ����ƿͻ����С����˻��߻�����û�е�����
							
					if(isDo){//ֵ�����仯
						
						boolean updateflag = false;
						//�������������Բ���һ��redundance-class�ڵ�
						Node node1 = ServerConfiger.redundances.selectSingleNode("//redundance/redundance-class[@class='"+ simpleName +"'][@property='"+fieldName +"']");
					   
						Object condtion = ((Element)node1).attributeValue("condition");
						if(condtion!=null){					//����������������ֵ���Ը���
//							String[] cond = condtion.toString().split("=");
//							String condProperty = cond[0];
							String condValue = ((Element)node1).attributeValue("conditionvalue");
							
							Object codnNewValue = ReflectionUtils.getFieldValue(newObj, condtion.toString());
							
							Object codnOldValue = ReflectionUtils.getFieldValue(oldObj, condtion.toString());
							
							if(codnNewValue!=null) codnNewValue = "'"+ codnNewValue.toString()+"'";
							
							if(codnOldValue!=null) codnOldValue = "'"+ codnOldValue.toString()+"'";
							
							if((codnNewValue!=null &&  codnNewValue.equals(condValue)) || (codnOldValue!=null&&codnOldValue.equals(condValue))){
								updateflag = true;
							}
						}else{									//���������ÿ��Ը���
							updateflag = true;
						}
						
						if(updateflag){							//�仯���ֶ����ڵļ�¼���Ϲ�������
							//ѭ��д����Լ�������������ֶ�
							String className = ((Element)node1).attributeValue("class");
							String transtions = node1.getParent().attributeValue("transtions");
							boolean isUpdate = false;
							if(transtions!=null){				//�����˽���
								String[] transtionArray = transtions.split(",");
								if(Arrays.asList(transtionArray).contains(ecifData.getTxCode()) ){//�������õĽ��׽����ж��Ƿ�ͬ�������ֶ�
									isUpdate = true;	
								}
							}else{								//δ���������н��׶��ɸ���
								isUpdate = true;	
							}
							
							if(isUpdate){						//�����������
	
								for(Object redundanceClass: node1.getParent().elements()){
									
									Element ele = (Element)redundanceClass;
									String tmpClass = ele.attributeValue("class").trim();
									String tmpProperty = ele.attributeValue("property").trim();
									String condition = ele.attributeValue("condition");
									String conditionvalue = ele.attributeValue("conditionvalue");
									
									if(tmpClass!=null&&tmpProperty!=null&&!tmpClass.equals(className)){    //�ų��Լ�
										String jql = "update "+ tmpClass+" set "+tmpProperty+"=?1 where custId=?2 ";
										if(condition!=null){
											jql += " and " +condition+"=" + conditionvalue;
										}
										Map map = new HashMap();
										map.put("jql", jql);
										map.put("newValue", newValue);
										map.put("custId", ecifData.getCustId());
										sqlList.add(map);
									}
								}
							}
							
						}
					}
				}
			}
		}

		return sqlList;
	}
}
