package com.yuchengtech.trans.impl;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.yuchengtech.bcrm.trade.model.TxLog;
import com.yuchengtech.bcrm.util.PrimaryKeyUtil;
import com.yuchengtech.trans.bo.TxData;
import com.yuchengtech.trans.inf.Transaction;
import com.yuchengtech.trans.socket.NioClient;

/**
 * 基础日志工具类
 * @author Administrator
 *
 */
public class BaseTransaction implements Transaction {
	private String host;//请求地址
	private int port;//请求端口
	private String requestCharSet;//请求报文编码
	private String transName;//交易名称
	protected TxData txData;//交易数据
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getRequestCharSet() {
		return requestCharSet;
	}

	public void setRequestCharSet(String requestCharSet) {
		this.requestCharSet = requestCharSet;
	}

	public String getTransName() {
		return transName;
	}

	public void setTransName(String transName) {
		this.transName = transName;
	}
	
	public TxData getTxData() {
		return txData;
	}

	public void setTxData(TxData txData) {
		this.txData = txData;
	}

	@Override
	public TxData analysisResMsg(TxData txData) {
		return txData;
	}

	@Override
	public TxData read(TxData txData) {
		return txData;
	}

	/**
	 * 获取交易日志
	 */
	@Override
	public TxLog getTxLog() {
		TxLog txLog = new TxLog();
		long txLogId = PrimaryKeyUtil.getIdOfLong();
		txLog.setTxLogId(txLogId);
		txLog.setTxCode(txData.getTxCode());
		txLog.setTxName(txData.getTxName());
		txLog.setTxCnName(txData.getTxCnName());
		txLog.setTxMethod(txData.getTxMethod());
		txLog.setTxDt(txData.getTxDt());
		txLog.setTxFwId(txData.getTxFwId());
		txLog.setTxReqTm(txData.getTxReqTm());
		txLog.setTxResTm(txData.getTxResTm());
		txLog.setTxResult(txData.getTxResult());
		txLog.setTxSvrIp(txData.getTxSvrIp());
		txLog.setSrcSysCd(txData.getSrcSysCd());
		txLog.setSrcSysNm(txData.getSrcSysNm());
		txLog.setTxRtnCd(txData.getTxRtnCd());
		String rtnMsg = txData.getTxRtnMsg();
		if(!StringUtils.isEmpty(rtnMsg) && rtnMsg.getBytes().length >= 255){
			rtnMsg = rtnMsg.substring(0, 100) + ".....";
		}
		txLog.setTxRtnMsg(rtnMsg);
		txLog.setReqMsg(txData.getReqMsg());
		txLog.setResMsg(txData.getResMsg());
		return txLog;
	}
	
	
	/**
	 * 设置交易结果
	 * @param txData	交易数据
	 * @param txResult	交易结果0：成功，2：失败
	 * @param status	交易状态
	 * @param msg		结果信息
	 * @return
	 */
	public TxData setTxResult(TxData txData, String txResult, String txResultCode, String rtnMsg, String status, String msg){
		txData.setTxResult(txResult);
		txData.setTxRtnCd(txResultCode);
		txData.setTxRtnMsg(rtnMsg);
		txData.setStatus(status);
		txData.setMsg(msg);
		txData.setAttribute("status", status);
		txData.setAttribute("msg", msg);
		return txData;
	}
	
	
	
	/**
	 * 定长报文解析
	 * 
	 * @param msg
	 *            报文内容
	 * @param charset
	 *            编码格式
	 * @param itemLen
	 *            各字段长度
	 * @return
	 */
	public String[] decodeMsg(TxData txData, String msg, String charset, int[] itemLen) {

		if (msg == null || msg.equals("") || itemLen == null
				|| itemLen.length < 1) {
			return null;
		}
		if (charset == null || charset.equals("")) {
			charset = "GBK";
		}
		String[] retArr = new String[itemLen.length];
		try {
			byte[] b_msg = msg.getBytes(charset);
			int startIdx = 0;
			for (int i = 0; i < itemLen.length; i++) {
				int currLen = itemLen[i];
				byte[] bt = new byte[currLen];
				System.arraycopy(b_msg, startIdx, bt, 0, currLen);
				startIdx += currLen;
				String currStr = new String(bt, charset);
				retArr[i] = currStr;
			}
		} catch (Exception e) {
			e.printStackTrace();
			String logMsg = "系统错误：" + e.getMessage();
			this.setTxResult(txData, "2", "", logMsg, "error", logMsg);
			return null;
		}
		return retArr;
	}
	
	
	/**
	 * 获取当前时间（毫秒）的字符串
	 * @return
	 */
	public String getCurrTxTm(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date date = new Date();
		return sdf.format(date);
	}
	/**
	 * 发送交易请求
	 * @return txData 封装了交易信息的VO
	 */
	public TxData request() {
		NioClient nioClient = new NioClient(host, port);
		String resp = null;
		if (requestCharSet == null || requestCharSet.equals("")) {
			requestCharSet = "GBK";
		}
		resp = nioClient.SocketCommunication(txData, requestCharSet, this.transName);
		txData.setResMsg(resp);
		return txData;
	}
	/**
	 * 处理交易，包括发送，接收，解析
	 * @return
	 */
	public TxData process() {
		request();
		txData = analysisResMsg(txData);
		return txData;
	}
	
	/**
	 * 凑够长度 增删或空格补起来
	 * @param str
	 * @param len
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String jointStr(String str,int len) throws UnsupportedEncodingException{
		StringBuffer sb = new StringBuffer();
		str = (str == null)?"":str;
		
		int count = len - str.getBytes("GBK").length;
		if(count==0){
			return str;
		}else if(count<0){
			str = subString(str,0,str.length()-1);
			return jointStr(str,len);
		}else{
			sb.append(str);
			for(int i=0;i<count;i++){
				sb.append(" ");
			}
			return sb.toString();
		}
	}
	
	/**
	 * 截取字符串
	 * @param str
	 * @param beginIndex
	 * @param endIndex
	 * @return
	 */
	public String subString(String str,int beginIndex,int endIndex){
		if(endIndex<beginIndex){
			return "";
		}else if(str.length()>=beginIndex && str.length()>=endIndex){
			return str.substring(beginIndex,endIndex);
		}else{
			return "";
		}
	}
}
