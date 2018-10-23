/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.common.even
 * @文件名：TxAlarmEvent.java
 * @版本信息：1.0.0
 * @日期：2013-12-19-下午3:21:51
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.interfaces.common.even;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.domain.txp.TxAlarmRule;
import com.ytec.mdm.server.common.BusinessCfg;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：TxAlarmEvent
 * @类描述：报警事件通知
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-19 下午3:21:51   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-19 下午3:21:51
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class TxAlarmEvent implements Observer {
	private Logger log = LoggerFactory.getLogger(TxAlarmEvent.class);
	/**
	 * @属性名称:txAlarmRuleMap
	 * @属性描述:报警码值规则配置
	 * @since 1.0.0
	 */
	private Map<String,TxAlarmRule> txAlarmRuleMap=new HashMap<String,TxAlarmRule>();
	/**
	 * @函数名称:init
	 * @函数描述:初始化
	 * @参数与返回说明:
	 * @算法描述:
	 * @throws Exception 
	 */
	public void init() throws Exception{
		txAlarmRuleMap.clear();
		JPABaseDAO baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		/**报警系统*/
		String alarmSys=BusinessCfg.getString("alarmSys");
		/**报警模块*/
		String alarmModule=BusinessCfg.getString("alarmModule");
		if(StringUtil.isEmpty(alarmSys)||StringUtil.isEmpty(alarmModule)){
			throw new Exception("报警事件通知:报警系统(alarmSys)或报警模块(alarmModule)配置项为空");
		}
		List<TxAlarmRule> txAlarmRuleList=baseDAO.findWithIndexParam("FROM TxAlarmRule t where t.alarmSys=? and t.alarmModule=? and t.isAlarm=?",alarmSys,alarmModule,"1");
		if(txAlarmRuleList!=null && !txAlarmRuleList.isEmpty()){
			for(TxAlarmRule txAlarmRule:txAlarmRuleList){
				if(!StringUtil.isEmpty(txAlarmRule.getErrorCode())){
					txAlarmRuleMap.put(txAlarmRule.getErrorCode(), txAlarmRule);
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.ytec.mdm.interfaces.common.even.Observer#executeObserver(com.ytec.mdm.base.bo.EcifData)
	 */
	@Override
	public void executeObserver(EcifData ecifData) {
		// TODO Auto-generated method stub
		if(!ecifData.isSuccess()){
			TxAlarmRule txAlarmRule=null;
			boolean isAlarm = false;
			if ((txAlarmRule=txAlarmRuleMap.get(ecifData.getRepStateCd()))!=null){		//配置包含错误码，则生成报警信息
				isAlarm = true;
			}
			if ((txAlarmRule=txAlarmRuleMap.get(ErrorCode.ERR_ALL.getCode()))!=null&&ecifData.getRepStateCd().compareTo(ErrorCode.ERR_ALL.getCode())>0 ){	//配置包含特殊错误码10000，并且交易错误码大于10000（小于10000不是错误信息）
				isAlarm = true;
			}
			
			if (isAlarm){
				try{
					log.info("报警事件通知:{}:[{}]:流水号:{}",ecifData.getRepStateCd(),ecifData.getDetailDes(),ecifData.getReqSeqNo());
					TxAlarmEventDAO txAlarmEventDAO=(TxAlarmEventDAO) SpringContextUtils.getBean("txAlarmEventDAO");
					txAlarmRule.setErrorCode(ecifData.getRepStateCd());  //此处由于报警规则设置了特殊错误码，所以改为交易的错误码
					txAlarmEventDAO.txAlarmEvent(txAlarmRule,String.format("流水号(事件号):%s,%s", ecifData.getReqSeqNo(),ecifData.getDetailDes()), 
							new Timestamp(ecifData.getStopWatch().getStopTime()), ecifData.getOpChnlNo());
				}catch(Exception e){
					log.error("数据同步事件录入异常",e);
				}
			}
		}

	}

}
