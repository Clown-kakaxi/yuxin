/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.service.facade
 * @文件名：ICoveringRule.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-12:00:44
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.service.facade;

import com.ytec.mdm.base.bo.EcifData;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：ICoveringRule
 * @类描述：覆盖规则接口
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 下午12:00:44   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 下午12:00:44
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public interface ICoveringRule {
	public Object cover(EcifData ecifData,Object oldObject, Object newObject) throws Exception;
}
