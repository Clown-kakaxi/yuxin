/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.check.dao
 * @文件名：CodeExplanation.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-10:48:32
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.integration.check.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.util.EcifPubDataUtils;
import com.ytec.mdm.integration.transaction.facade.IMsgNodeFilter;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：CodeExplanation
 * @类描述：码值翻译
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午10:48:32   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午10:48:32
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class CodeExplanation implements IMsgNodeFilter {
	private static Logger log = LoggerFactory
			.getLogger(CodeExplanation.class);
	public String execute(Object expression, String value, String... params) {
		String cate_id=params[1];
		if(cate_id==null){
			log.warn("码值类别配置为空");
			return null;
		}
		String desc=null;
		if((desc=EcifPubDataUtils.getStdCodeDes(value, cate_id))!=null){
			return desc;
		}else{
			log.warn("码值类别{}的码值{}解释为空",cate_id,value);
			return "";
		}
	}

}
