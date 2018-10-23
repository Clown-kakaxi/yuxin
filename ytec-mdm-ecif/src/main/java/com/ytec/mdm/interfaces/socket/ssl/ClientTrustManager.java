/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.socket.ssl
 * @文件名：ClientTrustManager.java
 * @版本信息：1.0.0
 * @日期：2014-4-8-上午11:01:49
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.interfaces.socket.ssl;
import java.security.KeyStore;

import javax.net.ssl.TrustManager;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：ClientTrustManager
 * @类描述：客户端证书验证接口
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-4-8 上午11:01:49   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-4-8 上午11:01:49
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public interface ClientTrustManager extends TrustManager {
	public void init(String paramString, KeyStore paramKeyStore) throws Exception;
}
