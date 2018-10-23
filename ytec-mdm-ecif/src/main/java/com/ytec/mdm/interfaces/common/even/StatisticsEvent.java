/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.common.even
 * @�ļ�����StatisticsEvent.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-5-29-����10:14:33
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.interfaces.common.even;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.util.StringUtil;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�StatisticsEvent
 * @��������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-5-29 ����10:14:33   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-5-29 ����10:14:33
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class StatisticsEvent implements Observer {
	/**
	 * @��������:txCensus
	 * @��������:������������
	 * @since 1.0.0
	 */
	public static final CensusObject txCensus=new CensusObject(); 
	/**
	 * @��������:txCodeCensus
	 * @��������:��������ͳ��
	 * @since 1.0.0
	 */
	public static final ConcurrentMap<String,CensusObject> txCodeCensus= new ConcurrentHashMap<String,CensusObject>();
	
	/**
	 * @��������:reqSysCensus
	 * @��������:������ϵͳ/����ͳ��
	 * @since 1.0.0
	 */
	public static final ConcurrentMap<String,CensusObject> reqSysCensus= new ConcurrentHashMap<String,CensusObject>();
	
	/**
	 * @��������:censusDate
	 * @��������:ͳ������
	 * @since 1.0.0
	 */
	private Date censusDate=new Date();
	
	/**
	 *@���캯�� 
	 */
	public StatisticsEvent() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.ytec.mdm.interfaces.common.even.Observer#executeObserver(com.ytec.mdm.base.bo.EcifData)
	 */
	@Override
	public void executeObserver(EcifData ecifData) {
		// TODO Auto-generated method stub
		/**����æ,�����������ƵĲ���ͳ��**/
		if(ErrorCode.ERR_SERVER_SERVICE_BUSY.getCode().equals(ecifData.getRepStateCd())){
			return;
		}
		reSet();
		boolean success=ecifData.isSuccess();
		/**ͳ������**/
		txCensus.execuCensus(success);
		CensusObject censusObject=null;
		/**��������ͳ��**/
		if(!StringUtil.isEmpty(ecifData.getTxCode())){
			censusObject=txCodeCensus.get(ecifData.getTxCode());
			if(censusObject == null){
				censusObject=new CensusObject();
				CensusObject censusObjectTemp=null;
				if((censusObjectTemp=txCodeCensus.putIfAbsent(ecifData.getTxCode(), censusObject))!=null){
					censusObject=censusObjectTemp;
	    		}
			}
			censusObject.execuCensus(success);
		}
		/**������ϵͳ/����ͳ��**/
		if(!StringUtil.isEmpty(ecifData.getOpChnlNo())){
			censusObject=reqSysCensus.get(ecifData.getOpChnlNo());
			if(censusObject == null){
				censusObject=new CensusObject();
				CensusObject censusObjectTemp=null;
				if((censusObjectTemp=reqSysCensus.putIfAbsent(ecifData.getOpChnlNo(), censusObject))!=null){
					censusObject=censusObjectTemp;
	    		}
			}
			censusObject.execuCensus(success);
		}
		
	}

	/* (non-Javadoc)
	 * @see com.ytec.mdm.interfaces.common.even.Observer#init()
	 */
	@Override
	public void init() throws Exception {
		// TODO Auto-generated method stub
		txCensus.execuReset();
		txCodeCensus.clear();
		reqSysCensus.clear();
	}
	
	/**
	 * @��������:reSet
	 * @��������:ͳ������
	 * @�����뷵��˵��:
	 * @�㷨����:
	 */
	private void reSet(){
		/**����ͳ��**/
		Date dd=new Date();
		if(dd.before(censusDate)){
			txCensus.execuReset();
			txCodeCensus.clear();
			reqSysCensus.clear();
			censusDate=dd;
		}
	}

}
