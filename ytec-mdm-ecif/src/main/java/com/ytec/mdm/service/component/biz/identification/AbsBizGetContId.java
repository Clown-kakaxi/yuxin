/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.service.component.biz.identification
 * @文件名：AbsBizGetContId.java
 * @版本信息：1.0.0
 * @日期：2014-7-8-上午9:42:25
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.service.component.biz.identification;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.service.facade.IBizGetContId;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：AbsBizGetContId
 * @类描述：
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-7-8 上午9:42:25   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-7-8 上午9:42:25
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public abstract class AbsBizGetContId implements IBizGetContId {

	/* (non-Javadoc)
	 * @see com.ytec.mdm.service.facade.IBizGetContId#bizGetContId(com.ytec.mdm.base.bo.EcifData)
	 */
	@Override
	public abstract void bizGetContId(EcifData ecifData);

}
