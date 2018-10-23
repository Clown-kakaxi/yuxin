/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.auth
 * @文件名：IAuthVerify.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-10:39:14
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.integration.auth;

import java.util.List;

import com.ytec.mdm.base.bo.AuthModel;


/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：IAuthVerify
 * @类描述：交易授权
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午10:39:24   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午10:39:24
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public interface IAuthVerify {
	
	/**
	 * @函数名称:clientAuth
	 * @函数描述:客户端授权
	 * @参数与返回说明:
	 * 		@param authModel
	 * 		@return
	 * @算法描述:
	 */
	public List clientAuth(AuthModel authModel);

	
	/**
	 * @函数名称:serviceAuth
	 * @函数描述:服务授权
	 * @参数与返回说明:
	 * 		@param authModel
	 * 		@return
	 * @算法描述:
	 */
	public List serviceAuth(AuthModel authModel); 
	
}
