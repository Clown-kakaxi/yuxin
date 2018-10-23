/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.transaction.core
 * @文件名：TxModelHolder.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:12:12
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
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
 * @项目名称：ytec-mdm-ecif 
 * @类名称：TxModelHolder
 * @类描述：存放交易配置模型的容器
 * @功能描述:存放交易配置模型的容器
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:12:13   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:12:13
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class TxModelHolder {

	private static Logger log = LoggerFactory.getLogger(TxModelHolder.class);
    /**
     * @属性名称:txModelMap
     * @属性描述:交易配置模型
     * @since 1.0.0
     */
    private static ConcurrentHashMap<String,TxModel> txModelMap = new ConcurrentHashMap<String,TxModel>();
    /**
     * @属性名称:txCheckInfoMap
     * @属性描述:XSD校验信息
     * @since 1.0.0
     */
    private static ConcurrentHashMap<String,CheckInfo> txCheckInfoMap=new ConcurrentHashMap<String,CheckInfo>();
    
    /**
     *@构造函数 
     */
    private TxModelHolder(){
    	
    	
    }
    
    
    
    /**
     * @函数名称:getTxModel
     * @函数描述:根据交易编号获取交易配置对象模型
     * @参数与返回说明:
     * 		@param txCode
     * 		@return
     * @算法描述:
     */
    public static TxModel getTxModel(String txCode){
    	
    	TxModel txModel = txModelMap.get(txCode);
    	TxConfigBS txConfigBS =null;
    	if(txModel == null){
    		txConfigBS= (TxConfigBS)SpringContextUtils.getBean("txConfigBS");
    		txModel = txConfigBS.getTxModel(txCode);
    		TxModel txModelTemp=null;
    		/**
    		 * 多线程并发控制
    		 */
    		if((txModelTemp=txModelMap.putIfAbsent(txCode, txModel))!=null){
    			txModel=txModelTemp;
    		}
    	}
    	return txModel;
    }
    
    /**
     * @函数名称:getTxModelFromCache
     * @函数描述:根据交易编号从缓存中获取交易配置对象模型,
     * @参数与返回说明:
     * 		@param txCode
     * 		@return
     * @算法描述:
     */
    public static TxModel getTxModelFromCache(String txCode){
    	return txModelMap.get(txCode);
    }
    
    /**
     * @函数名称:getTxCheckInfo
     * @函数描述:获取请求XSD校验信息
     * @参数与返回说明:
     * 		@param txCode
     * 		@return
     * @算法描述:
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
    			//检查修改日期是否发生变化
    			Timestamp newTimestamp =  txDef.getUpdateTm();
    			Timestamp oldTimestamp = checkInfo.getUpdateTm();

    			if(newTimestamp != null &&(oldTimestamp == null || newTimestamp.after(oldTimestamp))){
    				//交易配置修改，重新获取交易模型
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
     * @函数名称:getCheckInfo
     * @函数描述:从数据库中获取XSD校验信息
     * @参数与返回说明:
     * 		@param txCode
     * 		@return
     * @算法描述:
     */
    private static CheckInfo getCheckInfo(String txCode){
    	CheckInfo checkInfo=null;
    	try{
    		JPABaseDAO baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
    		List<Object[]> result = baseDAO.findWithIndexParam("select checkinfo.checkinfo,def.updateTm,def.txCheckXsd from TxDef def,TxMsg msg,TxMsgCheckinfo checkinfo where def.txId = msg.txId  and msg.msgId = checkinfo.msgId and msg.msgTp = '1'  and def.txCode =? and def.txState ='1'", txCode);
    		if (result == null || result.size() <= 0) {
    			return null;
    		}else if(result == null || result.size() > 1){
    			log.error("该服务的校验数据过多");
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
    		log.error("查询校验数据出错：",e);
    	}
    	return null;
    }
    
   
    /**
     * @函数名称:txDefCheck
     * @函数描述:检测该交易是否更新，或交易是否不存在。
     * @参数与返回说明:
     * 		@param txCode
     * 		@return
     * @算法描述:
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
    		log.error("查询交易数据出错：",e);
    		return false;
    	}
    	TxModel txModel = txModelMap.get(txCode);
    	if(txModel != null){//检查模型是否为最新
    		if(MdmConstants.globalTxDefCheck){
    			//检查修改日期是否发生变化
    			Timestamp oldTimestamp =  txModel.getTxDef().getUpdateTm();
    			if(newTimestamp != null &&(oldTimestamp == null || newTimestamp.after(oldTimestamp))){
    				TxConfigBS txConfigBS = (TxConfigBS)SpringContextUtils.getBean("txConfigBS");
    				//交易配置修改，重新获取交易模型
    				txModel = txConfigBS.getTxModel(txCode);
    				txModelMap.replace(txCode, txModel);
    			}
    		}
    	}
    	return true;
    }
   
   
}


/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：CheckInfo
 * @类描述：校验信息更新模型
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:14:48   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:14:48
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
class CheckInfo{
	/**
	 * @属性名称:txDefCheck
	 * @属性描述:校验信息
	 * @since 1.0.0
	 */
	private String txDefCheck;
	/**
	 * @属性名称:updateTm
	 * @属性描述:最后更新时间
	 * @since 1.0.0
	 */
	private Timestamp updateTm;
	
	
	/**
	 *@构造函数 
	 */
	public CheckInfo() {
	}
	/**
	 *@构造函数 
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