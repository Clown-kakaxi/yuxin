/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.common.returncode
 * @文件名：TxReturnCodeMgr.java
 * @版本信息：1.0.0
 * @日期：2013-12-24-上午11:01:47
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
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
 * @项目名称：ytec-mdm-ecif 
 * @类名称：TxReturnCodeMgr
 * @类描述：错误代码映射实现
 * @功能描述:提供ECIF错误代码与外围系统错误代码映射功能
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-24 上午11:01:47   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-24 上午11:01:47
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class TxReturnCodeMgr implements ITxReturnCode {
	private static ITxReturnCode txReturnCodeMgr=new TxReturnCodeMgr();
	private Logger log = LoggerFactory.getLogger(TxReturnCodeMgr.class);
	private Map<String,TxReturnCode> returnCodeMap=new HashMap<String,TxReturnCode>();
	/**
	 *@构造函数 
	 */
	public TxReturnCodeMgr() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @函数名称:getInstance
	 * @函数描述:获取实例
	 * @参数与返回说明:
	 * 		@return
	 * @算法描述:
	 */
	public static ITxReturnCode getInstance(){
		return txReturnCodeMgr;
	}
	
	/**
	 * @函数名称:init
	 * @函数描述:初始化
	 * @参数与返回说明:
	 * @算法描述:
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
