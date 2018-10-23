/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.service.facade
 * @文件名：IMCIdentifying.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-12:01:03
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.service.facade;


/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：IMCIdentifying
 * @类描述：客户号规则
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 下午12:01:15   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 下午12:01:15
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public interface IMCIdentifying {
	
	/**
	 * 客户号规则
	 * @param String custType 客户类型
	 * @param String ecif客户号
	 * **/
	public String getEcifCustNo(String custType) throws Exception;
	/**
	 * 技术主键生成规则
	 * @param String arrtName, 属性名称, 对应数据表为主键数据字段 (属性),命名规范与数据表字段名同
	 * @return String AttrId
	 * */
	public String getPriIdByAttrName(String attrName) throws Exception;
	
	/**
	 * @函数名称:getEcifCustId
	 * @函数描述:客户标识生成规则
	 * @参数与返回说明:
	 * 		@param custType 客户类型
	 * 		@return
	 * 		@throws Exception
	 * @算法描述:
	 */
	public String getEcifCustId(String custType) throws Exception;

}
