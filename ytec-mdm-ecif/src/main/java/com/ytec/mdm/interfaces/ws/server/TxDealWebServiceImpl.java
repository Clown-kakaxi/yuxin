/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.ws.server
 * @文件名：TxDealWebServiceImpl.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:49:50
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */

package com.ytec.mdm.interfaces.ws.server;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.server.common.ServerConfiger;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：TxDealWebServiceImpl
 * @类描述：WEB SERVICE 服务
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:49:50   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:49:50
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
@WebService(endpointInterface = "com.ytec.mdm.interfaces.ws.server.TxDealWebService", serviceName = "txDealWebService")
public class TxDealWebServiceImpl implements TxDealWebService {
	private static Logger log = LoggerFactory.getLogger(TxDealWebServiceImpl.class);
	private Class adapterClazz;  //执行适配器
    /**
     *@构造函数 
     */
    public TxDealWebServiceImpl() {
    	try{
    		adapterClazz = Class.forName(ServerConfiger.adapter);
		}catch(Exception e){
			log.error("找不到适配器",e);
		}
	}
	/* (non-Javadoc)
	 * @see com.ytec.mdm.interfaces.ws.server.TxDealWebService#execute(java.lang.String)
	 */
	public String execute(String req) {
    	if (!StringUtil.isEmpty(req)) {
    		try {
    			WsExecutor executor = (WsExecutor)adapterClazz.newInstance();
    			//初始
    			executor.init(req);
    			//执行
    			executor.run();
    			//返回响应报文
    			return executor.getResXml();
    		} catch (Exception e) {
    			log.error("获取适配器",e);
    			return e.getMessage();
    		}catch(Throwable ex){
				log.error("服务器严重错误:", ex);
				return ex.getMessage();
			}
    	}
    	return "";
    }
    
}

