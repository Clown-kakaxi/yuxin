/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.transaction.facade
 * @文件名：IExtCaseDispatch.java
 * @版本信息：1.0.0
 * @日期：2013-12-23-下午5:15:10
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.integration.transaction.facade;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：IExtCaseDispatch
 * @类描述：分支判别扩展接口
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-23 下午5:15:10   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-23 下午5:15:10
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public interface IExtCaseDispatch extends ICaseDispatch {
	public void init(String args);

}
