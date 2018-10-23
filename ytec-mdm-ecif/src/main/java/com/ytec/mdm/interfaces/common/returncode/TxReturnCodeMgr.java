/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.common.returncode
 * @�ļ�����TxReturnCodeMgr.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-24-����11:01:47
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.interfaces.common.returncode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.Error;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.domain.txp.TxReturnCode;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�TxReturnCodeMgr
 * @���������������ӳ��ʵ��
 * @��������:�ṩECIF�����������Χϵͳ�������ӳ�书��
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-24 ����11:01:47   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-24 ����11:01:47
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class TxReturnCodeMgr implements ITxReturnCode {
	private static ITxReturnCode txReturnCodeMgr=new TxReturnCodeMgr();
	private Logger log = LoggerFactory.getLogger(TxReturnCodeMgr.class);
	private Map<String,TxReturnCode> returnCodeMap=new HashMap<String,TxReturnCode>();
	/**
	 *@���캯�� 
	 */
	public TxReturnCodeMgr() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @��������:getInstance
	 * @��������:��ȡʵ��
	 * @�����뷵��˵��:
	 * 		@return
	 * @�㷨����:
	 */
	public static ITxReturnCode getInstance(){
		return txReturnCodeMgr;
	}
	
	/**
	 * @��������:init
	 * @��������:��ʼ��
	 * @�����뷵��˵��:
	 * @�㷨����:
	 */
	public void init(){
		returnCodeMap.clear();
		JPABaseDAO baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		List<TxReturnCode> txReturnCodeList= baseDAO.findWithIndexParam("FROM TxReturnCode T");
		if(txReturnCodeList!=null&&!txReturnCodeList.isEmpty()){
			for(TxReturnCode txReturnCode:txReturnCodeList){
				returnCodeMap.put(txReturnCode.getRtnCode(), txReturnCode);
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.ytec.mdm.interfaces.common.returncode.ITxReturnCode#getExterReturnCode(com.ytec.mdm.base.bo.EcifData)
	 */
	@Override
	public Error getExterReturnCode(EcifData ecifData) {
		// TODO Auto-generated method stub
		TxReturnCode txReturnCode;
		if((txReturnCode=returnCodeMap.get(ecifData.getRepStateCd()))!=null){
			if(txReturnCode.getOuterRtnCode()!=null){
				return new Error(txReturnCode.getOuterRtnCode(),txReturnCode.getEnOuterRtnMsg(),txReturnCode.getChOuterRtnMsg());
			}else{
				return null;
			}
		}else{
			return null;
		}
	}

}
