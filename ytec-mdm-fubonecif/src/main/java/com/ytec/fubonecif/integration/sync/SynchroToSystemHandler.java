/**
 * @项目名：ytec-mdm-fubonecif
 * @包名：com.ytec.fubonecif.integration.sync
 * @文件名：SynchroToSystemHandler.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-12:08:43
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.fubonecif.integration.sync;
import org.dom4j.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.ytec.mdm.domain.txp.TxSyncConf;
import com.ytec.mdm.integration.transaction.xml.XMLUtils;
import com.ytec.mdm.plugins.synchelper.SynchroToSystemExecute;

/**
 * @项目名称：ytec-mdm-fubonecif 
 * @类名称：SynchroToSystemHandler
 * @类描述：数据同步案例(调用外系统接口的报文接口转换)
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 下午12:08:44   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 下午12:08:44
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
@Component
@Scope("prototype")
public class SynchroToSystemHandler extends SynchroToSystemExecute {
	private static Logger log = LoggerFactory.getLogger(SynchroToSystemHandler.class);
			

	@Override
	public boolean asseReqMsg(TxSyncConf txSyncConf, Document doc) {
		synchroRequestMsg = XMLUtils.xmlToString(doc);
		log.info(synchroRequestMsg);
		return true;
	}

	@Override
	public boolean executeResult() {
		// TODO Auto-generated method stub
		if(this.synchroResponseMsg!=null){
			log.info(this.synchroResponseMsg);
		}else{
			log.info("同步响应报文为空");
		}
		return true;
	}

}
