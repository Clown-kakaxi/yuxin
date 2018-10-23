/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.common.even
 * @文件名：CensusObject.java
 * @版本信息：1.0.0
 * @日期：2014-5-29-上午10:28:19
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.interfaces.common.even;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：CensusObject
 * @类描述：统计计数对象
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-5-29 上午10:28:19   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-5-29 上午10:28:19
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public class CensusObject {
	/**
	 * @属性名称:censusTotal
	 * @属性描述:总数(失败+成功)
	 * @since 1.0.0
	 */
	private final AtomicInteger censusTotal=new AtomicInteger(0); 
	/**
	 * @属性名称:censusError
	 * @属性描述:失败
	 * @since 1.0.0
	 */
	private final AtomicInteger censusError=new AtomicInteger(0);
	
	/**
	 * @函数名称:execuCensus
	 * @函数描述:执行统计
	 * @参数与返回说明:
	 * 		@param success
	 * @算法描述:
	 */
	public void execuCensus(boolean success){
		/**总数自增**/
		censusTotal.incrementAndGet();
		if(success){
			censusError.incrementAndGet();
		}
	}
	
	/**
	 * @函数名称:execuReset
	 * @函数描述:重置
	 * @参数与返回说明:
	 * @算法描述:
	 */
	public void execuReset(){
		censusTotal.set(0);
		censusError.set(0);
	}
	
	/**
	 * @函数名称:getTotal
	 * @函数描述:获取总数
	 * @参数与返回说明:
	 * 		@return
	 * @算法描述:
	 */
	public int getTotal(){
		return censusTotal.get();
	}
	/**
	 * @函数名称:getError
	 * @函数描述:获取错误数
	 * @参数与返回说明:
	 * 		@return
	 * @算法描述:
	 */
	public int getError(){
		return censusError.get();
	}
}
