/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.check.bs
 * @文件名：TxBizRuleFun.java
 * @版本信息：1.0.0
 * @日期：2014-3-28-下午3:48:45
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.integration.check.bs;

import com.ytec.mdm.base.bo.EcifData;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：TxBizRuleFun
 * @类描述：业务规则校验转换
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-3-28 下午3:48:45   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-3-28 下午3:48:45
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public class TxBizRuleFun {
	
	/**
	 * @函数名称:doFilter
	 * @函数描述:业务规则校验
	 * @参数与返回说明:
	 * 		@param ecifData
	 * 		@param ruler
	 * 		@param attrName
	 * 		@param value
	 * 		@return
	 * @算法描述:
	 */
	public static String doFilter(EcifData ecifData, String ruler,String attrName, String value){
		return  TxBizRuleFactory.doFilter(ecifData,ruler, attrName,value, new String[] {});
	}

}
