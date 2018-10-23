/**
 * @项目名：ytec-mdm-fubonecif
 * @包名：com.ytec.fubonecif.integration.check
 * @文件名：CheckDateString.java
 * @版本信息：1.0.0
 * @日期：2014-4-11-下午4:47:20
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.fubonecif.integration.check;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.integration.adapter.message.ReqMsgValidation;
import com.ytec.mdm.integration.transaction.facade.IMsgNodeFilter;

/**
 * @项目名称：ytec-mdm-fubonecif 
 * @类名称：CheckDateString
 * @类描述：校验转换日期格式的字符串，将该字符串格式化为目标格式
 * @功能描述:如果查询条件为日期格式的字符串,又要验证该格式或转换成目标格式，如果配置时选择日期型，虽然可以验证，但查询时会转成日期类型作为参数，查询条件类型不匹配
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-4-11 下午4:47:20   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-4-11 下午4:47:20
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public class CheckDateString implements IMsgNodeFilter {
	private Logger log = LoggerFactory.getLogger(CheckDateString.class);
	/**
	 *@构造函数 
	 */
	public CheckDateString() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.ytec.mdm.integration.transaction.facade.IMsgNodeFilter#execute(java.lang.Object, java.lang.String, java.lang.String[])
	 */
	@Override
	public String execute(Object expression, String value, String... params) {
		// TODO Auto-generated method stub
		if (value == null || expression==null) {
			return "";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat((String)expression);
		/****
		 * 时间:格式化，排除几个默认值，前台处理可能会传来 0000-00-00,0000-01-01的默认值，对他们来说，这就是空
		 */
		if (ReqMsgValidation.excludeDates != null) {
			for (String excludeDate : ReqMsgValidation.excludeDates) {
				if (value.equals(excludeDate)) {
					log.info("过滤掉日期时间{}",value);
					return "";
				}
			}
		}
		// ///////////////////////////////////////////////////////////////////////
		dateFormat.setLenient(false);
		Date dateval = StringUtil.reverse2Date(value, dateFormat);
		if (dateval != null) {
			value = dateFormat.format(dateval);
		}else{
			return null;
		}
		return value;
	}

}
