/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.transaction.core
 * @�ļ�����TxModelHolder.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:12:12
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.integration.transaction.core;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.domain.txp.TxDef;
import com.ytec.mdm.integration.transaction.bs.TxConfigBS;
import com.ytec.mdm.integration.transaction.model.TxModel;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�TxModelHolder
 * @����������Ž�������ģ�͵�����
 * @��������:��Ž�������ģ�͵�����
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:12:13   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:12:13
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class TxModelHolder {

	private static Logger log = LoggerFactory.getLogger(TxModelHolder.class);
    /**
     * @��������:txModelMap
     * @��������:��������ģ��
     * @since 1.0.0
     */
    private static ConcurrentHashMap<String,TxModel> txModelMap = new ConcurrentHashMap<String,TxModel>();
    /**
     * @��������:txCheckInfoMap
     * @��������:XSDУ����Ϣ
     * @since 1.0.0
     */
    private static ConcurrentHashMap<String,CheckInfo> txCheckInfoMap=new ConcurrentHashMap<String,CheckInfo>();
    
    /**
     *@���캯�� 
     */
    private TxModelHolder(){
    	
    	
    }
    
    
    
    /**
     * @��������:getTxModel
     * @��������:���ݽ��ױ�Ż�ȡ�������ö���ģ��
     * @�����뷵��˵��:
     * 		@param txCode
     * 		@return
     * @�㷨����:
     */
    public static TxModel getTxModel(String txCode){
    	
    	TxModel txModel = txModelMap.get(txCode);
    	TxConfigBS txConfigBS =null;
    	if(txModel == null){
    		txConfigBS= (TxConfigBS)SpringContextUtils.getBean("txConfigBS");
    		txModel = txConfigBS.getTxModel(txCode);
    		TxModel txModelTemp=null;
    		/**
    		 * ���̲߳�������
    		 */
    		if((txModelTemp=txModelMap.putIfAbsent(txCode, txModel))!=null){
    			txModel=txModelTemp;
    		}
    	}
    	return txModel;
    }
    
    /**
     * @��������:getTxModelFromCache
     * @��������:���ݽ��ױ�Ŵӻ����л�ȡ�������ö���ģ��,
     * @�����뷵��˵��:
     * 		@param txCode
     * 		@return
     * @�㷨����:
     */
    public static TxModel getTxModelFromCache(String txCode){
    	return txModelMap.get(txCode);
    }
    
    /**
     * @��������:getTxCheckInfo
     * @��������:��ȡ����XSDУ����Ϣ
     * @�����뷵��˵��:
     * 		@param txCode
     * 		@return
     * @�㷨����:
     */
    public static String getTxCheckInfo(String txCode){
    	CheckInfo checkInfo=txCheckInfoMap.get(txCode);
    	String txCheckInfo=null;
    	if(checkInfo==null){
    		checkInfo=getCheckInfo(txCode);
    		if(checkInfo!=null){
    			CheckInfo checkInfoTmp=null;
        		if((checkInfoTmp=txCheckInfoMap.putIfAbsent(txCode, checkInfo))!=null){
        			checkInfo=checkInfoTmp;
        		}
        		txCheckInfo=checkInfo.getTxDefCheck();
    		}
    	}else{
    		txCheckInfo=checkInfo.getTxDefCheck();
    		if(MdmConstants.globalTxDefCheck){
    			TxConfigBS txConfigBS= (TxConfigBS)SpringContextUtils.getBean("txConfigBS");
    			TxDef txDef = txConfigBS.getTxDef(txCode);
    			//����޸������Ƿ����仯
    			Timestamp newTimestamp =  txDef.getUpdateTm();
    			Timestamp oldTimestamp = checkInfo.getUpdateTm();

    			if(newTimestamp != null &&(oldTimestamp == null || newTimestamp.after(oldTimestamp))){
    				//���������޸ģ����»�ȡ����ģ��
    				TxModel txModel = txConfigBS.getTxModel(txCode);
    				txModelMap.replace(txCode, txModel);
    				
    				checkInfo=getCheckInfo(txCode);
    				txCheckInfoMap.replace(txCode, checkInfo);
    				txCheckInfo=checkInfo.getTxDefCheck();
    			}
    		}
    	}
    	return txCheckInfo;
    	
    }
    

    /**
     * @��������:getCheckInfo
     * @��������:�����ݿ��л�ȡXSDУ����Ϣ
     * @�����뷵��˵��:
     * 		@param txCode
     * 		@return
     * @�㷨����:
     */
    private static CheckInfo getCheckInfo(String txCode){
    	CheckInfo checkInfo=null;
    	try{
    		JPABaseDAO baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
    		List<Object[]> result = baseDAO.findWithIndexParam("select checkinfo.checkinfo,def.updateTm,def.txCheckXsd from TxDef def,TxMsg msg,TxMsgCheckinfo checkinfo where def.txId = msg.txId  and msg.msgId = checkinfo.msgId and msg.msgTp = '1'  and def.txCode =? and def.txState ='1'", txCode);
    		if (result == null || result.size() <= 0) {
    			return null;
    		}else if(result == null || result.size() > 1){
    			log.error("�÷����У�����ݹ���");
    			return null;
    		}else{
    			Object[] objs= (Object[])result.get(0);
    			if("0".equals(objs[2])){
    				checkInfo=new CheckInfo((String)objs[2],(Timestamp)objs[1]);
    			}else{
    				checkInfo=new CheckInfo((String)objs[0],(Timestamp)objs[1]);
    			}
        		return checkInfo;
    		}
    	}catch(Exception e){
    		log.error("��ѯУ�����ݳ���",e);
    	}
    	return null;
    }
    
   
    /**
     * @��������:txDefCheck
     * @��������:���ý����Ƿ���£������Ƿ񲻴��ڡ�
     * @�����뷵��˵��:
     * 		@param txCode
     * 		@return
     * @�㷨����:
     */
    public static boolean txDefCheck(String txCode){
    	Timestamp newTimestamp=null;
    	try{
    		JPABaseDAO baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
    		List<Object> result = baseDAO.findWithIndexParam("select def.updateTm from TxDef def where def.txCode =? and def.txState ='1'", txCode);
    		if (result == null || result.size() <= 0) {
    			return false;
    		}else{
    			newTimestamp= (Timestamp)result.get(0);
    		}
    	}catch(Exception e){
    		log.error("��ѯ�������ݳ���",e);
    		return false;
    	}
    	TxModel txModel = txModelMap.get(txCode);
    	if(txModel != null){//���ģ���Ƿ�Ϊ����
    		if(MdmConstants.globalTxDefCheck){
    			//����޸������Ƿ����仯
    			Timestamp oldTimestamp =  txModel.getTxDef().getUpdateTm();
    			if(newTimestamp != null &&(oldTimestamp == null || newTimestamp.after(oldTimestamp))){
    				TxConfigBS txConfigBS = (TxConfigBS)SpringContextUtils.getBean("txConfigBS");
    				//���������޸ģ����»�ȡ����ģ��
    				txModel = txConfigBS.getTxModel(txCode);
    				txModelMap.replace(txCode, txModel);
    			}
    		}
    	}
    	return true;
    }
   
   
}


/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�CheckInfo
 * @��������У����Ϣ����ģ��
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:14:48   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:14:48
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
class CheckInfo{
	/**
	 * @��������:txDefCheck
	 * @��������:У����Ϣ
	 * @since 1.0.0
	 */
	private String txDefCheck;
	/**
	 * @��������:updateTm
	 * @��������:������ʱ��
	 * @since 1.0.0
	 */
	private Timestamp updateTm;
	
	
	/**
	 *@���캯�� 
	 */
	public CheckInfo() {
	}
	/**
	 *@���캯�� 
	 * @param txDefCheck
	 * @param updateTm
	 */
	public CheckInfo(String txDefCheck, Timestamp updateTm) {
		this.txDefCheck = txDefCheck;
		this.updateTm = updateTm;
	}
	public String getTxDefCheck() {
		return txDefCheck;
	}
	public void setTxDefCheck(String txDefCheck) {
		this.txDefCheck = txDefCheck;
	}
	public Timestamp getUpdateTm() {
		return updateTm;
	}
	public void setUpdateTm(Timestamp updateTm) {
		this.updateTm = updateTm;
	}
}