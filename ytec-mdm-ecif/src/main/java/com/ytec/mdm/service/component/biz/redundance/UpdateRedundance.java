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
		
		// 修改冗余字段信息
		String simpleName = newObj.getClass().getSimpleName();
		if (ServerConfiger.redundanceClassList.contains(simpleName)) {//包含需要进行冗余处理的类
			//需要进行冗余处理的字段值发生了变化，并且变化的字段所在的记录符合过滤条件（如：首选地址）
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
//					} 只有数据发生修改的时候才做同步，之前加上数据未修改也同步是为了解决客户名称客户表有。个人或者机构表没有的问题
							
					if(isDo){//值发生变化
						
						boolean updateflag = false;
						//根据类名和属性查找一个redundance-class节点
						Node node1 = ServerConfiger.redundances.selectSingleNode("//redundance/redundance-class[@class='"+ simpleName +"'][@property='"+fieldName +"']");
					   
						Object condtion = ((Element)node1).attributeValue("condition");
						if(condtion!=null){					//有条件，符合条件值可以更新
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
						}else{									//无条件设置可以更新
							updateflag = true;
						}
						
						if(updateflag){							//变化的字段所在的记录符合过滤条件
							//循环写入除自己外的其他冗余字段
							String className = ((Element)node1).attributeValue("class");
							String transtions = node1.getParent().attributeValue("transtions");
							boolean isUpdate = false;
							if(transtions!=null){				//配置了交易
								String[] transtionArray = transtions.split(",");
								if(Arrays.asList(transtionArray).contains(ecifData.getTxCode()) ){//根据配置的交易进行判断是否同步冗余字段
									isUpdate = true;	
								}
							}else{								//未配置则都所有交易都可更新
								isUpdate = true;	
							}
							
							if(isUpdate){						//满足更新条件
	
								for(Object redundanceClass: node1.getParent().elements()){
									
									Element ele = (Element)redundanceClass;
									String tmpClass = ele.attributeValue("class").trim();
									String tmpProperty = ele.attributeValue("property").trim();
									String condition = ele.attributeValue("condition");
									String conditionvalue = ele.attributeValue("conditionvalue");
									
									if(tmpClass!=null&&tmpProperty!=null&&!tmpClass.equals(className)){    //排除自己
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
