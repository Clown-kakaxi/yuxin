/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.check.dao
 * @文件名：PositiveTranscoding.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-10:48:52
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.integration.check.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.util.EcifPubDataUtils;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.integration.transaction.facade.IMsgNodeFilter;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：PositiveTranscoding
 * @类描述：正向转码
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午10:48:52   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午10:48:52
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class PositiveTranscoding implements IMsgNodeFilter {
	private static Logger log = LoggerFactory
			.getLogger(PositiveTranscoding.class);

	public String execute(Object expression, String value, String... params) {
		// TODO Auto-generated method stub
		String src_sys_cd=params[0];
		String cate_id=params[1];
		if(src_sys_cd==null||cate_id==null){
			log.warn("码值类别配置为空");
			return null;
		}
		String code_id=null;
		if ((code_id=EcifPubDataUtils.getEcifCode(src_sys_cd,value,cate_id))!=null) {
			return code_id;
		} else{
			log.warn("原系统号为{}的码值类型为{}的源码值为{}转码失败",src_sys_cd,cate_id,value);
			if(StringUtil.isEmpty(expression)){
				return null;
			}else{
				code_id=expression.toString().replace("{0}", value).replace("{1}", src_sys_cd).replace("{2}", cate_id);
				log.warn("原码值{}被转换为{}",value,code_id);
				return code_id;
			}
		}
	}

}
