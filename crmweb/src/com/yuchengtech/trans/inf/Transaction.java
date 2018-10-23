package com.yuchengtech.trans.inf;

import com.yuchengtech.bcrm.trade.model.TxLog;
import com.yuchengtech.trans.bo.TxData;

/**
 * 通用的日志工具
 * @author Administrator
 *
 */
public interface Transaction {
	public String sdf_8 = "yyyy-MM-dd";
	public String sdf_16 = "yyyy-MM-dd HH:mm:ss";
	public String sdf_20 = "yyyy-MM-dd HH:mm:ss:SSS";
	public static String TX_RESULT_SUCCESS = "1";
	public static String TX_RESULT_ERROR = "2";
	/**
	 * 解析响应报文字符串，并设置交易时间等其他参数
	 * @return
	 */
	public TxData analysisResMsg(TxData txData);
	
	/**
	 * 解析报文字符串
	 * @param txStr 报文
	 * @return TxLog
	 */
	public TxData read(TxData txData);
	
	/**
	 * 获取日志对象
	 * @param txData
	 * @return
	 */
	public TxLog getTxLog();
	/**
	 * 处理交易，包括发送，接收，解析
	 * @return
	 */
	public TxData process();
}
