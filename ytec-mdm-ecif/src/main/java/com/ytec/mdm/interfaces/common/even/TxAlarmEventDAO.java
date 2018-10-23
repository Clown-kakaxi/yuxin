/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.common.even
 * @文件名：TxAlarmEventDAO.java
 * @版本信息：1.0.0
 * @日期：2013-12-19-下午4:34:46
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.interfaces.common.even;

import java.sql.Timestamp;

import org.apache.commons.configuration.ConfigurationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.OIdUtils;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.domain.txp.TxAlarmInfo;
import com.ytec.mdm.domain.txp.TxAlarmRule;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：TxAlarmEventDAO
 * @类描述：报警通知DAO
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-19 下午4:34:46   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-19 下午4:34:46
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
@Service
public class TxAlarmEventDAO {
	/**
	 * @throws ConfigurationException 
	 * @throws Exception 
	 * @函数名称:txAlarmEvent
	 * @函数描述:报警通知保存
	 * @参数与返回说明:
	 * @算法描述:
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class })
	public void txAlarmEvent(TxAlarmRule txAlarmRule,String alarmInfo,Timestamp tm,String srcSysCd) throws Exception{
		JPABaseDAO baseDAO=(JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		TxAlarmInfo txAlarmInfo=new TxAlarmInfo();
		txAlarmInfo.setAlarmInfoId(OIdUtils.getIdOfLong());
		txAlarmInfo.setAlarmSys(txAlarmRule.getAlarmSys());
		txAlarmInfo.setAlarmModule(txAlarmRule.getAlarmModule());
		txAlarmInfo.setErrorCode(txAlarmRule.getErrorCode());
		txAlarmInfo.setAlarmLevel(txAlarmRule.getAlarmLevel());
		txAlarmInfo.setAlarmInfo(alarmInfo);
		txAlarmInfo.setOccurDate(tm);
		txAlarmInfo.setOccurTime(tm);
		txAlarmInfo.setSrcSysCd(srcSysCd);
		txAlarmInfo.setAlarmStat("0");
		baseDAO.persist(txAlarmInfo);
		baseDAO.flush();
	}

}
