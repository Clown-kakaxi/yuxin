/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.check.bs
 * @文件名：RegexValidator.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-10:44:26
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.integration.check.bs;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ytec.mdm.integration.transaction.facade.IMsgNodeFilter;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：RegexValidator
 * @类描述：正则验证
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午10:44:27   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午10:44:27
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class RegexValidator implements IMsgNodeFilter {

	public String execute(Object expression, String value, String... params) {
		if(expression instanceof Pattern){
			Matcher match = ((Pattern)expression).matcher(value);
			if(!match.matches()){
				return null;
			}
		}
		return value;
	}
}
