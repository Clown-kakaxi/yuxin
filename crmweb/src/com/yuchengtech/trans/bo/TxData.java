package com.yuchengtech.trans.bo;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.yuchengtech.bcrm.trade.model.TxLog;


/**
 * 交易数据类
 * @author Administrator
 *
 */
public class TxData extends TxLog{
	private Map<String,Object> txMap = new HashMap<String,Object>();//交易结果
	
	private String status;//交易结果状态
	
	private String msg;//交易结果信息
	
//	private TxLog logData;// 日志数据
	public Map<String, Object> getTxMap() {
		return txMap;
	}

	public void setTxMap(Map<String, Object> txMap) {
		this.txMap = txMap;
	}

	public void setAttribute(String key, Object value){
		txMap.put(key, value);
	}
	
	public Object getAttribute(String key){
		return txMap.get(key);
	}



	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * 判断返回结果中是否存在某个参数
	 * @param key	参数的key
	 * @return
	 */
	public boolean containsKey(String key){
		if(StringUtils.isEmpty(key)){
			return false;
		}else{
			if(txMap == null || txMap.isEmpty()){
				return false;
			}else{
				if(txMap.containsKey(key)){
					return true;
				}else{
					return false;
				}
			}
		}
	}
	
}
