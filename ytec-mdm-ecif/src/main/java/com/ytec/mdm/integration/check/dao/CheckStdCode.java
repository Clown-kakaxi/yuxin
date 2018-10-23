/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.check.dao
 * @文件名：CheckStdCode.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-10:48:00
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
 * @类名称：CheckStdCode
 * @类描述：校验是否是标准码
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午10:48:01   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午10:48:01
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class CheckStdCode implements IMsgNodeFilter{
	private static Logger log = LoggerFactory
			.getLogger(CheckStdCode.class);
	public String execute(Object expression, String value, String... params) {
		// TODO Auto-generated method stub
		String cate_id=params[1];
		if(cate_id==null){
			log.warn("码值类别配置为空");
			return null;
		}
		if(EcifPubDataUtils.isEcifStdCode(value, cate_id)){
			return value;
		}else{
			log.warn("码值类别{}的码值{}不是ECIF标准码",cate_id,value);
			return null;
		}
	}

}
