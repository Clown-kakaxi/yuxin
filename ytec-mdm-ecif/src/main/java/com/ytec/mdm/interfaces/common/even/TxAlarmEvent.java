/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.common.even
 * @�ļ�����TxAlarmEvent.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-19-����3:21:51
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
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
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�TxAlarmEvent
 * @�������������¼�֪ͨ
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-19 ����3:21:51   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-19 ����3:21:51
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class TxAlarmEvent implements Observer {
	private Logger log = LoggerFactory.getLogger(TxAlarmEvent.class);
	/**
	 * @��������:txAlarmRuleMap
	 * @��������:������ֵ��������
	 * @since 1.0.0
	 */
	private Map<String,TxAlarmRule> txAlarmRuleMap=new HashMap<String,TxAlarmRule>();
	/**
	 * @��������:init
	 * @��������:��ʼ��
	 * @�����뷵��˵��:
	 * @�㷨����:
	 * @throws Exception 
	 */
	public void init() throws Exception{
		txAlarmRuleMap.clear();
		JPABaseDAO baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		/**����ϵͳ*/
		String alarmSys=BusinessCfg.getString("alarmSys");
		/**����ģ��*/
		String alarmModule=BusinessCfg.getString("alarmModule");
		if(StringUtil.isEmpty(alarmSys)||StringUtil.isEmpty(alarmModule)){
			throw new Exception("�����¼�֪ͨ:����ϵͳ(alarmSys)�򱨾�ģ��(alarmModule)������Ϊ��");
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
			if ((txAlarmRule=txAlarmRuleMap.get(ecifData.getRepStateCd()))!=null){		//���ð��������룬�����ɱ�����Ϣ
				isAlarm = true;
			}
			if ((txAlarmRule=txAlarmRuleMap.get(ErrorCode.ERR_ALL.getCode()))!=null&&ecifData.getRepStateCd().compareTo(ErrorCode.ERR_ALL.getCode())>0 ){	//���ð������������10000�����ҽ��״��������10000��С��10000���Ǵ�����Ϣ��
				isAlarm = true;
			}
			
			if (isAlarm){
				try{
					log.info("�����¼�֪ͨ:{}:[{}]:��ˮ��:{}",ecifData.getRepStateCd(),ecifData.getDetailDes(),ecifData.getReqSeqNo());
					TxAlarmEventDAO txAlarmEventDAO=(TxAlarmEventDAO) SpringContextUtils.getBean("txAlarmEventDAO");
					txAlarmRule.setErrorCode(ecifData.getRepStateCd());  //�˴����ڱ���������������������룬���Ը�Ϊ���׵Ĵ�����
					txAlarmEventDAO.txAlarmEvent(txAlarmRule,String.format("��ˮ��(�¼���):%s,%s", ecifData.getReqSeqNo(),ecifData.getDetailDes()), 
							new Timestamp(ecifData.getStopWatch().getStopTime()), ecifData.getOpChnlNo());
				}catch(Exception e){
					log.error("����ͬ���¼�¼���쳣",e);
				}
			}
		}

	}

}
