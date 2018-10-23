/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.ws.server
 * @文件名：TxDealWebService.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:50:15
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */

package com.ytec.mdm.interfaces.ws.server;

import javax.jws.WebService;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：TxDealWebService
 * @类描述：WEB SERVICE 服务接口
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:50:16   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:50:16
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
@WebService
public interface TxDealWebService {

    /**
     * @函数名称:execute
     * @函数描述:web service执行方法
     * @参数与返回说明:
     * 		@param req
     * 		@return
     * @算法描述:
     */
    public String execute(String req);
    
}
