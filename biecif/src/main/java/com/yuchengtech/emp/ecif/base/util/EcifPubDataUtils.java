/**
 * @��Ŀ��ytec-mdm-ecif
 * @����com.ytec.mdm.base.util
 * @�ļ���EcifPubDataUtils.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-10:08:29
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.yuchengtech.emp.ecif.base.util;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.dao.JPABaseDAO;
import com.yuchengtech.emp.ecif.core.entity.TxSysParam;
import com.yuchengtech.emp.ecif.syncmanage.entity.TxEvtNotice;

/**
 * @��Ŀ��ƣ�ytec-mdm-ecif
 * @����ƣ�EcifPubDataUtils
 * @��������������ݼ���
 * @��������:���ڴ���ݿ��м��ع����������
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����10:08:50
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����10:08:50
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ�� -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class EcifPubDataUtils extends BaseBS<TxSysParam> {

	/**
	 * The log.
	 * 
	 * @��������:
	 */
	private static Logger log = LoggerFactory.getLogger(EcifPubDataUtils.class);

	/**
	 * The info grant object model map.
	 * 
	 * @��������:��Ϣ�ּ���Ȩ
	 */
	/**.
	 * 
	 * @��������:��ֵ���Լ���
	 */
	public static Set<String> ecifTableCode = new HashSet<String>();

	/**
	 * The src to ecif code map.
	 * 
	 * @��������:ԭϵͳ����תECIF��׼��**.
	 */
	public static Map<String, String> srcToEcifCodeMap = new ConcurrentHashMap<String, String>();
	/**
	 * 
	 * @��������:ECIF��׼��תԭϵͳ����**.
	 */
	public static Map<String, String> ecifToSrcCodeMap = new ConcurrentHashMap<String, String>();
	/**
	 * 
	 * 
	 * @��������:ECIF��׼��
	 */
	public static Map<String, String> ecifStdCodeMap = new ConcurrentHashMap<String, String>();

	/**
	 * @���캯��
	 */
	public EcifPubDataUtils() {
	}
    public void initialize(){
    	log.info("��ʼ����ݿ�������Ϣ");
		/*** ������Ϣ�ּ�ӳ��� **/
		/**����ϵͳJVM ID***/
		List<TxSysParam> jvmList=this.baseDAO.findWithIndexParam("from TxSysParam where paramName=?", MdmConstants.TXSYSPARAMNAME);
		int jvmId=1;
		TxSysParam txSysParam=null;
		if(jvmList!=null && !jvmList.isEmpty()){
			txSysParam=(TxSysParam)jvmList.get(0);
			if(!txSysParam.getParamValue().isEmpty()){
				jvmId=Integer.valueOf(txSysParam.getParamValue());
				jvmId=jvmId%80;
				if(jvmId==0){
					jvmId++;
				}
			}
		}
		MdmConstants.SYSTEMJVMID=jvmId;
    }
	/**
	 * @�������:initPubData
	 * @��������:��ʼ����ݿ�������Ϣ
	 * @�����뷵��˵��:
	 * @return
	 * @�㷨����:
	 */
	public void initPubData() {
		log.info("��ʼ����ݿ�������Ϣ");
		/*** ������Ϣ�ּ�ӳ��� **/
		/**����ϵͳJVM ID***/
		List<TxSysParam> jvmList=this.baseDAO.findWithIndexParam("from TxSysParam where paramName=?", MdmConstants.TXSYSPARAMNAME);
		int jvmId=1;
		TxSysParam txSysParam=null;
		if(jvmList!=null && !jvmList.isEmpty()){
			txSysParam=(TxSysParam)jvmList.get(0);
			if(!txSysParam.getParamValue().isEmpty()){
				jvmId=Integer.valueOf(txSysParam.getParamValue());
				jvmId=jvmId%80;
				if(jvmId==0){
					jvmId++;
				}
			}
		}
		MdmConstants.SYSTEMJVMID=jvmId;
		
	}

	/**
	 * ECIF��Ϣ�������.
	 * 
	 * @param authType
	 *            ��Ȩ����
	 * @param authCode
	 *            ��Ȩ��
	 * @param ctrlType
	 *            ���Ʒ���
	 * @param tabId
	 *            ECIF��ID
	 * @param colId
	 *            ECIF�ֶ�ID
	 * @return boolean false��ûȨ�� true����Ȩ��
	 * @�㷨����:
	 */
	

}
