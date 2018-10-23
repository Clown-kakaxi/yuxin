/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.common.even
 * @文件名：StatisticsEvent.java
 * @版本信息：1.0.0
 * @日期：2014-5-29-上午10:14:33
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
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
 * @项目名称：ytec-mdm-ecif 
 * @类名称：StatisticsEvent
 * @类描述：
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-5-29 上午10:14:33   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-5-29 上午10:14:33
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public class StatisticsEvent implements Observer {
	/**
	 * @属性名称:txCensus
	 * @属性描述:交易总量计数
	 * @since 1.0.0
	 */
	public static final CensusObject txCensus=new CensusObject(); 
	/**
	 * @属性名称:txCodeCensus
	 * @属性描述:按交易码统计
	 * @since 1.0.0
	 */
	public static final ConcurrentMap<String,CensusObject> txCodeCensus= new ConcurrentHashMap<String,CensusObject>();
	
	/**
	 * @属性名称:reqSysCensus
	 * @属性描述:按请求系统/渠道统计
	 * @since 1.0.0
	 */
	public static final ConcurrentMap<String,CensusObject> reqSysCensus= new ConcurrentHashMap<String,CensusObject>();
	
	/**
	 * @属性名称:censusDate
	 * @属性描述:统计日期
	 * @since 1.0.0
	 */
	private Date censusDate=new Date();
	
	/**
	 *@构造函数 
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
		/**服务忙,服务流量控制的不做统计**/
		if(ErrorCode.ERR_SERVER_SERVICE_BUSY.getCode().equals(ecifData.getRepStateCd())){
			return;
		}
		reSet();
		boolean success=ecifData.isSuccess();
		/**统计总数**/
		txCensus.execuCensus(success);
		CensusObject censusObject=null;
		/**按交易码统计**/
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
		/**按请求系统/渠道统计**/
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
	 * @函数名称:reSet
	 * @函数描述:统计重置
	 * @参数与返回说明:
	 * @算法描述:
	 */
	private void reSet(){
		/**按天统计**/
		Date dd=new Date();
		if(dd.before(censusDate)){
			txCensus.execuReset();
			txCodeCensus.clear();
			reqSysCensus.clear();
			censusDate=dd;
		}
	}

}
