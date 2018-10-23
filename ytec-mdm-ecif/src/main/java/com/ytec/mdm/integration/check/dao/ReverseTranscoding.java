/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.check.dao
 * @文件名：ReverseTranscoding.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-10:49:27
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
 * @类名称：ReverseTranscoding
 * @类描述：逆向转码
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午10:49:40   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午10:49:40
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class ReverseTranscoding implements IMsgNodeFilter {

	/**
	 * The log.
	 * 
	 * @属性描述:
	 */
	private static Logger log = LoggerFactory
			.getLogger(ReverseTranscoding.class);
	
	/* (non-Javadoc)
	 * @see com.ytec.mdm.integration.transaction.facade.IMsgNodeFilter#execute(java.lang.Object, java.lang.String, java.lang.String[])
	 */
	public String execute(Object expression, String value, String... params) {
		String src_sys_cd=params[0];
		String cate_id=params[1];
		if(src_sys_cd==null||cate_id==null){
			log.warn("码值类别配置为空");
			return null;
		}
		String s_code=null;
		if ((s_code=EcifPubDataUtils.getSrcCode(src_sys_cd,value,cate_id))!=null) {
			return s_code;
		} else{
			log.warn("原系统号为{}的码值类型为{}在ECIF码值为{}逆向转码失败",src_sys_cd,cate_id,value);
			return value;
		}
	}

}
