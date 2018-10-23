/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.check.validation
 * @文件名：TxReqBodyValidation.java
 * @版本信息：1.0.0
 * @日期：2014-4-15-上午10:56:41
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
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
 * @项目名称：ytec-mdm-ecif 
 * @类名称：TxConsistenceValidation
 * @类描述：请求报文体一致性验证
 * @功能描述:
 * @创建人：shangjf@yuchengtech.com
 * @创建时间：2014-11-1 上午10:56:41   
 * @修改人：shangjf@yuchengtech.com
 * @修改时间：2014-11-1 上午10:56:41
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public class TxConsistenceValidation extends AbstractValidationChain {

	/* (non-Javadoc)
	 * @see com.ytec.mdm.integration.check.validation.AbstractValidationChain#reqMsgValidation(com.ytec.mdm.base.bo.EcifData)
	 */
	@Override
	public boolean reqMsgValidation(EcifData ecifData) {
		
		/** 请求报文体 */
		List<String> consistenceList = ServerConfiger.transConsistenceMap.get(ecifData.getTxCode());//取出一个交易需要进行的一致性校验列表
		
		for(String name:consistenceList){  //取出一个一致性校验
			
			Node node = ServerConfiger.consistences.selectSingleNode("//consistence[@name='"+ name +"']");
			List<Element> msgList = node.selectNodes("msg");
			List<String[]> compareList  = new ArrayList();
			
			for(int i=0;i<msgList.size();i++){
				Element msg = msgList.get(i);
				String path = msg.attributeValue("path");
				String condition = msg.attributeValue("condition");
				String conditionvalue = msg.attributeValue("conditionvalue");
				List<Element> valueList = ecifData.getBodyNode().selectNodes(path);   //获取报文值
				if(valueList==null||valueList.size()==0){							  //报文路径配置错误
					continue;
				}
				if(valueList.size()<=1){		//一致性只有一个报文字段值，检查条件
					if(condition==null){		//没有条件
						if(valueList.get(0)!=null&& !valueList.get(0).getTextTrim().equals("")){	//如果为null、空值就不验证值一致性
							compareList.add(new String[]{valueList.get(0).getTextTrim(),path});
						}
					}else{						//有条件
						String conditiontext = valueList.get(0).getParent().element(condition).getTextTrim();
						if(conditiontext!=null&&!conditiontext.trim().equals("")&&conditiontext.equals(conditionvalue)){
							if(valueList.get(0)!=null&& !valueList.get(0).getTextTrim().equals("")){	//如果为null、空值就不验证值一致性
								compareList.add(new String[]{valueList.get(0).getTextTrim(),path});
							}
						}					
					}
				}else{ 							//一致性包含多个报文字段值，检查条件
					for(Element val:valueList){
						String conditiontext = val.getParent().element(condition).getTextTrim();
						if(conditiontext!=null&&!conditiontext.trim().equals("")&&conditiontext.equals(conditionvalue)){
							if(val!=null&& !val.getTextTrim().equals("")){	//如果为null、空值就不验证值一致性
								compareList.add(new String[]{val.getTextTrim(),path});
							}
						}
					}
				}
			}
			
			if(compareList!=null&&compareList.size()>1){  //验证报文中的冗余字段数据是否一致
				String[] value = compareList.get(0);
				for(String compare[]:compareList){
					if(!compare[0].trim().equals("")&&!compare[0].equals(value[0])){//如果为null、空值就不验证值一致性
						log.warn("报文字段数据不一致。{}:{},{}:{}。",value[1],value[0],compare[1],compare[0]);
						ecifData.setStatus(ErrorCode.ERR_XML_FILTER_VALUE_CONSISTENCE);

						return false;
					}
				}
			}
		}
		
		return true;
	}

}
