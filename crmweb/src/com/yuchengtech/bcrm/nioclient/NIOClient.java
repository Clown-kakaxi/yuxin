/*******************************************************************************
 * $Header$
 * $Revision$ 1.0
 * $Date$
 *
 *==============================================================================
 *
 * Copyright (c) 2010 CITIC Holdings All rights reserved.
 * Created on 2013-7-28
 * 通过该类，连接ESB系统，调用ECIF接口
 *******************************************************************************/


package com.yuchengtech.bcrm.nioclient;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.yuchengtech.bcrm.http.HttpUtils;
import com.yuchengtech.bcrm.util.CrmConstants;
import com.yuchengtech.crm.exception.BizException;


/**
 * ESB交互的客户端代码主类
 * @author wudi1@yuchengtech.com
 *
 */

public class NIOClient {

	/*缓冲区大小*/     
	private static int BLOCK = 4096;      
	/*接受数据缓冲区*/     
	private static ByteBuffer sendbuffer = ByteBuffer.allocate(BLOCK);      
	/*发送数据缓冲区*/     
	private static ByteBuffer receivebuffer = ByteBuffer.allocate(BLOCK);      
	/*服务器端地址*/
	//private final static InetSocketAddress SERVER_ADDRESS = new InetSocketAddress("130.1.9.182", 12117);  
	private Long SELECTTIMEOUT = 1000L;//CrmConstants.SELECTTIMEOUT;
	private Long TIMEOUT = 60000L;//CrmConstants.TIMEOUT;//超时60秒
	private String ip;//地址
	private int port;//端口
	private ResponseDecoder decoder = new ResponseDecoder();
	private int sleepTime = 50;//网络不稳定时，解析器等待时间
	private int readMaxNum = 100;//网络不稳定时，一次没有解析完，读取多少次
	private static Logger log = Logger.getLogger(NIOClient.class);
	

	public NIOClient(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}
	
	/**
	 * 与ESB交互方法
	 * @param sendMsg 发送的报文信息
	 * @param txnCd 调用服务的代码值 
	 * @return ESB返回的信息
	 * @throws Exception 异常
	 */
	public String interactive(String sendMsg, String txnCd) throws Exception {
		//打开socket通道
		SocketChannel socketChannel = SocketChannel.open();
		//设置为非阻塞方式
		socketChannel.configureBlocking(false);
		//打开选择器
		Selector selector = Selector.open();
		//注册连接服务端socket动作
		socketChannel.register(selector, SelectionKey.OP_CONNECT);
		/*服务器端地址*/
		InetSocketAddress SERVER_ADDRESS = new InetSocketAddress(this.ip, this.port);
		//连接
		socketChannel.connect(SERVER_ADDRESS);
		//分配缓冲区大小内存
		Set<SelectionKey> selectionKeys;
		Iterator<SelectionKey> iterator;
		SelectionKey selectionKey;
		SocketChannel client = null;
		String receiveText = null;
		int len = 0;
		Long sendtime = 0L;
		while (true) {
			if( selector.select(SELECTTIMEOUT) <= 0 ) {
				if( sendtime > 0 && System.currentTimeMillis()-sendtime > TIMEOUT) {
					if( client != null ) {
						log.info(">>>[TIMEOUT] Client[" + client.socket().getRemoteSocketAddress() + "] timeout("+TIMEOUT+"ms)");
						client.close();
						throw new BizException(1, 0, "1003", "Socket连接超时");//TIMEOUT后，抛出T错误码
//						return "T";//超时返回T
					} 
				}
			}
			//选择一组键，其相应的通道已为 I/O 操作准备就绪。
			//此方法执行处于阻塞模式的选择操作。
			selector.select();
			//返回此选择器的已选择键集.
			selectionKeys = selector.selectedKeys();
			iterator = selectionKeys.iterator();
			while (iterator.hasNext()) {
				selectionKey = iterator.next();
				if (selectionKey.isConnectable()) {
					log.info("client connect)");
					client = (SocketChannel) selectionKey.channel();
					//判断此通道上是否正在进行连接操作。
					// 完成套接字通道的连接过程.
					if (client.isConnectionPending()) {
						client.finishConnect();
						log.info("完成连接!");
						sendbuffer.clear();
						//sendbuffer.put("Hello,Server".getBytes());
						sendbuffer.flip();
						client.write(sendbuffer);
					}
					client.register(selector, SelectionKey.OP_WRITE);
				} else if (selectionKey.isReadable()) { //读取esb信息
					log.info("接受响应信息，开始读取ESB返回报文信息...");
					client = (SocketChannel) selectionKey.channel();
					receivebuffer.clear();
					len = client.read(receivebuffer);
					if (len >= 0) {
						receivebuffer.flip();
						int rcount = 0;
						do {
							try {
								if (decoder.decode(receivebuffer)) {
									log.info("接收到:"+decoder.contentLength+",http status "+decoder.getResStatus().getCode()+" "+decoder.getResStatus().getReasonPhrase());
									receiveText = decoder.getBody();//获取报文体内容
									log.info("receiveText:"+receiveText);
									break;
								} else {
									if (rcount > readMaxNum) {
										log.info("http报文解析超时");
										throw new BizException(1, 0, "1003", "http报文解析超时");
									}
									if (HttpUtils.BUFFER_SIZE != len) {
										log.info("网络状态不好，需要读取多次...");
										try {
											Thread.sleep(sleepTime);
										} catch(Exception e) {
											e.printStackTrace();
											log.info(e.getMessage());
										}
									}
									rcount++;
									log.info("读取数据"+len+"B,需要读取多次...");
									this.receivebuffer.clear(); // 再读
									len = client.read(receivebuffer);
									receivebuffer.flip(); // flip for read
									if (len < 0) {
										selector.close();
										client.close();
										socketChannel.close();
										log.info("!>exit.");
										return "";
									} else if (len == 0) {
										log.info("读取0Byte数据...");
									}
								}
							} catch(Exception e) {
								e.printStackTrace();
								log.info("解析ESB返回报文error:"+e.getMessage());
								selector.close();
								client.close();
								return null;
							}	
						} while (receivebuffer.hasRemaining()); // consume all
						selector.close();
						client.close();
						return receiveText;
                    } else {
                    	throw new BizException(1, 0, "1003", "读取esb信息长度异常!");
                    }
				} else if (selectionKey.isWritable()) { //发送信息到esb
					sendbuffer.clear();
					client = (SocketChannel) selectionKey.channel();
					String publicTitlStr = createPublicTitle(txnCd, sendMsg);
					StringBuffer msg = new StringBuffer();
					msg.append("POST /" + txnCd + " HTTP/1.1\r\n");
					msg.append("Accept: */*\r\n");
					msg.append("Accept-Language: zh-cn\r\n");
					msg.append("User-Agent: ocrm-hrbank (V1.0)\r\n");
					msg.append("Host: localhost\r\n");
//					String checkSum = EndecryptUtils.encrypt(sendMsg);
//					msg.append("Content-MD5: " + checkSum + "\r\n");
					msg.append("Connection: Keep-Alive\r\n");
					msg.append("Content-type: application/xml; charset=utf-8\r\n");
					msg.append("Content-Length: " + publicTitlStr.getBytes().length + "\r\n");
					msg.append("\r\n");
					msg.append(publicTitlStr);
					sendbuffer.put(msg.toString().getBytes());
					//将缓冲区各标志复位,因为向里面put了数据标志被改变要想从中读取数据发向服务器,就要复位
					sendbuffer.flip();
					len = client.write(sendbuffer);
					sendtime = System.currentTimeMillis();
					log.info(">>>[SEND] Client[" + client.socket().getRemoteSocketAddress() + "][" + len + "]");
					log.info(msg.toString());
					client.register(selector, SelectionKey.OP_READ);
				}
			}
			selectionKeys.clear();
		}
	}
	
	/**
	 * 组装公共报文信息
	 * @param txdCode
	 * @param esbMsg
	 * @return
	 */
	public String createPublicTitle(String txdCode, String esbMsg) {
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		sb.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"www.cqrcb.com.cn\">");
		sb.append("<soapenv:Header/>");
		sb.append("<soapenv:Body>");
		sb.append("<ns:"+txdCode+">");
		sb.append("<RequestHeader>");
		sb.append("<VerNo></VerNo>");//版本号
		sb.append("<ReqSysCd>");
		sb.append(CrmConstants.REQSYSCD);//请求方系统代码
		sb.append("</ReqSysCd>");
		sb.append("<ReqSecCd></ReqSecCd>");//请求方安全节点号
		sb.append("<TxnTyp></TxnTyp>");//交易类型
		sb.append("<TxnMod></TxnMod>");//交易模式
		sb.append("<TxnCd></TxnCd>");//交易码
		sb.append("<TxnNme></TxnNme>");//公共交易名称
		sb.append("<ReqDt></ReqDt>");//请求方交易日期
		sb.append("<ReqTm></ReqTm>");//请求方交易时间戳
		sb.append("<ReqSeqNo></ReqSeqNo>");//请求方流水号
		sb.append("<ChnlNo></ChnlNo>");//渠道号
		sb.append("<BrchNo></BrchNo>");//机构号
		sb.append("<BrchNme></BrchNme>");//公共交易机构名称
		sb.append("<TrmNo></TrmNo>");//终端号
		sb.append("<TrmIP></TrmIP>");//终端IP地址
		sb.append("<TlrNo></TlrNo>");//柜员号
		sb.append("<TlrNme></TlrNme>");//柜员名称
		sb.append("<TlrLvl></TlrLvl>");//操作员级别
		sb.append("<TlrTyp></TlrTyp>");//操作员种类
		sb.append("<TlrPwd></TlrPwd>");//柜员密码
		sb.append("<AuthTlr></AuthTlr>");//授权柜员
		sb.append("<AuthPwd></AuthPwd>");//授权柜员密码
		sb.append("<AuthCd></AuthCd>");//授权码
		sb.append("<AuthFlg></AuthFlg>");//授权标志
		sb.append("<AuthDisc></AuthDisc>");//授权信息
		sb.append("<AuthWk></AuthWk>");//授权执行动作
		sb.append("<SndFileNme></SndFileNme>");//发送文件名
		sb.append("<BgnRec></BgnRec>");//开始记录数
		sb.append("<MaxRec></MaxRec>");//一次查询最大记录数
		sb.append("<FileHMac></FileHMac>");//文件MAC值
		sb.append("<HMac></HMac>");//报文MAC值
		sb.append("</RequestHeader>");
		sb.append("<RequestBody>");
		sb.append(esbMsg);
		sb.append("</RequestBody>");
		sb.append("</ns:"+txdCode+">");
		sb.append("</soapenv:Body>");
		sb.append("</soapenv:Envelope>");
		return sb.toString();
	}

	public static void main(String[] args) throws Exception {
		try {
			StringBuffer sb = new StringBuffer();
//			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
//			sb.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"www.cqrcb.com.cn\">");
//			sb.append("<soapenv:Header/>");
//			sb.append("<soapenv:Body>");
//			sb.append("<cqr:S002001010990002>");
//			sb.append("<RequestHeader>");
//			sb.append("<VerNo>20121016ESB</VerNo>");//版本号
//			sb.append("<ReqSysCd>026001</ReqSysCd>");//请求方系统代码
//			sb.append("<ReqSecCd>026001</ReqSecCd>");//请求方安全节点号
//			sb.append("<TxnTyp>RQ</TxnTyp>");//交易类型
//			sb.append("<TxnMod></TxnMod>");//交易模式
//			sb.append("<TxnCd></TxnCd>");//交易码
//			sb.append("<TxnNme></TxnNme>");//公共交易名称
//			sb.append("<ReqDt></ReqDt>");//请求方交易日期
//			sb.append("<ReqTm></ReqTm>");//请求方交易时间戳
//			sb.append("<ReqSeqNo></ReqSeqNo>");//请求方流水号
//			sb.append("<ChnlNo>W0</ChnlNo>");//渠道号
//			sb.append("<BrchNo>0100</BrchNo>");//机构号
//			sb.append("<BrchNme></BrchNme>");//公共交易机构名称
//			sb.append("<TrmNo></TrmNo>");//终端号
//			sb.append("<TrmIP></TrmIP>");//终端IP地址
//			sb.append("<TlrNo>5142</TlrNo>");//柜员号
//			sb.append("<TlrNme></TlrNme>");//柜员名称
//			sb.append("<TlrLvl></TlrLvl>");//操作员级别
//			sb.append("<TlrTyp></TlrTyp>");//操作员种类
//			sb.append("<TlrPwd></TlrPwd>");//柜员密码
//			sb.append("<AuthTlr></AuthTlr>");//授权柜员
//			sb.append("<AuthPwd></AuthPwd>");//授权柜员密码
//			sb.append("<AuthCd></AuthCd>");//授权码
//			sb.append("<AuthFlg></AuthFlg>");//授权标志
//			sb.append("<AuthDisc></AuthDisc>");//授权信息
//			sb.append("<AuthWk></AuthWk>");//授权执行动作
//			sb.append("<SndFileNme></SndFileNme>");//发送文件名
//			sb.append("<BgnRec></BgnRec>");//开始记录数
//			sb.append("<MaxRec></MaxRec>");//一次查询最大记录数
//			sb.append("<FileHMac></FileHMac>");//文件MAC值
//			sb.append("<HMac></HMac>");//报文MAC值
//			sb.append("</RequestHeader>");
//			sb.append("<RequestBody>");
//			sb.append("<AcctNo>62242501003333999</AcctNo>");
//			sb.append("<ChkMagFlg>0</ChkMagFlg>");
//			sb.append("<ExtndStat>1111100000000000</ExtndStat>");
//			sb.append("<SecTrk/>");
//			sb.append("<ThdTrk/>");
//			sb.append("</RequestBody>");
//			sb.append("</cqr:S002001010990002>");
//			sb.append("</soapenv:Body>");
//			sb.append("</soapenv:Envelope>");
			NIOClient ser = EsbUtil.getEsbService();
//			NIOClient client = new NIOClient("130.1.9.182",12117);
        	sb.append("<txCode>");
        	sb.append("OO100220001");
        	sb.append("</txCode>");
        	sb.append("<txName></txName>");
        	sb.append("<authType></authType>");
        	sb.append("<authCode></authCode>");
        	sb.append("<identifier>");
        	sb.append("<identCustName>");
        	sb.append("测试计划i");
        	sb.append("</identCustName>");
        	sb.append("<identType>");
        	sb.append("020401");
        	sb.append("</identType>");
        	sb.append("<identNo>");
        	sb.append("L18957591");
        	sb.append("</identNo>");
        	sb.append("</identifier>");
        	sb.append("<nametitle>");
        	sb.append("<name>");
        	sb.append("测试计划i");
        	sb.append("</name>");
        	sb.append("</nametitle>");
			String receiveText = ser.interactive(sb.toString(), "S007001990OO1002");
			Map map = EsbUtil.parseStringToMapForUnThrowException(receiveText);
			System.out.println(map);
			System.out.println(receiveText);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
