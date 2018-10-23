/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.common.even
 * @�ļ�����TxAlarmEventDAO.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-19-����4:34:46
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
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
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�TxAlarmEventDAO
 * @������������֪ͨDAO
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-19 ����4:34:46   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-19 ����4:34:46
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
@Service
public class TxAlarmEventDAO {
	/**
	 * @throws ConfigurationException 
	 * @throws Exception 
	 * @��������:txAlarmEvent
	 * @��������:����֪ͨ����
	 * @�����뷵��˵��:
	 * @�㷨����:
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
