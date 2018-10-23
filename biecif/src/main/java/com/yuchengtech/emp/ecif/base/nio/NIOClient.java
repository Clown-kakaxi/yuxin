/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.socket
 * @文件名：NioClient.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:47:12
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.yuchengtech.emp.ecif.base.nio;


import java.io.EOFException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：NioClient
 * @类描述：NIO客户端
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:47:08   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:47:08
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public abstract class NIOClient {
	private static Logger log = LoggerFactory.getLogger(NIOClient.class);
	protected ByteBuffer sendbuffer = ByteBuffer.allocate(1 * 1024 * 1024);
	protected ByteBuffer receivebuffer = ByteBuffer.allocate(1 * 1024 * 1024);
	protected String ip;
	protected int port;
	protected Long SELECTTIMEOUT = 3000L;
	protected Long TIMEOUT = 60000L;
	protected int sleepTime = 50; // 网络不稳定时，解析器等待时间
	protected int readMaxNum = 100; // 网络不稳定时，一次没有解析完，读取多少次
	protected String charset ;
	protected String responseMsg;
	protected SocketChannel client;
	
	public NIOClient() {
	}

	public void init(Map arg) throws Exception{
		// TODO Auto-generated method stub
		this.ip = (String) arg.get("ip");
		this.port = Integer.parseInt((String) arg.get("port"));
		this.charset = "GBK";
		if(this.charset==null){
			charset = "GB18030";
		}
		this.TIMEOUT = Long.valueOf(("60000") );
		this.SELECTTIMEOUT = Long.valueOf("1000");
	}
	
	public String sendMsg(String sendmsg)  throws Exception{
		Selector selector=null;
		SocketChannel socketChannel=null;
		String String=new String();
		try {
			Set<SelectionKey> selectionKeys;
			Iterator<SelectionKey> iterator;
			SelectionKey selectionKey;
			long len = 0;
			Long sendtime = 0L;
			log.info("NIO 客户端请求开始");
			socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(false);
			selector = Selector.open();
			InetSocketAddress SERVER_ADDRESS = new InetSocketAddress(this.ip,this.port);
			socketChannel.connect(SERVER_ADDRESS);
			socketChannel.register(selector, SelectionKey.OP_CONNECT);
			while (true) {
				if (selector.select(SELECTTIMEOUT) <= 0) {
					if (sendtime > 0 && System.currentTimeMillis() - sendtime > TIMEOUT) {
						return String;
					}
					continue;
				}
				selectionKeys = selector.selectedKeys();
				iterator = selectionKeys.iterator();
				while (iterator.hasNext()) {
					selectionKey = iterator.next();
					iterator.remove();
					client = (SocketChannel) selectionKey.channel();
					if (selectionKey.isConnectable()) {
						if (client.isConnectionPending()) {
							client.finishConnect();
						}
						client.register(selector, SelectionKey.OP_WRITE);
					} else if (selectionKey.isReadable()) {
						receivebuffer.clear();
						len = client.read(receivebuffer);
						if (len > 0) {
							receivebuffer.flip();
							int rcount = 0;
							do {
									if (decode()) {
										if (responseMsg != null) {
											log.info(">>>[RECE] Client[{}]",client.socket().getRemoteSocketAddress());
										}
										return responseMsg;
									} else {
										if (rcount > readMaxNum) {
											throw new Exception("Nio报文解析超时");
										}
										rcount++;
										log.warn("读取数据" + len+ "B,需要读取多次。。。");
										this.receivebuffer.clear(); // 再读
										len = client.read(receivebuffer);
										receivebuffer.flip(); // flip for read
										if (len < 0) {
											log.info("!>exit");
											return String;
										} else if (len == 0) {
											log.warn("读取0Byte数据");
										}
									}

							} while (receivebuffer.hasRemaining()); // consume
						} else if (len == -1) {
							log.warn("服务端断开连接,请求失败");
							return String;
						}
					} else if (selectionKey.isWritable()) {
						sendbuffer.clear();
						client = (SocketChannel) selectionKey.channel();
						String msg=packing(sendmsg);
						sendbuffer.put(msg.getBytes(charset));
						sendbuffer.flip();
						len =sendChannel(sendbuffer); //client.write(sendbuffer);
						sendtime = System.currentTimeMillis();
						log.info(">>>[SEND] Client[{}]",client.socket().getRemoteSocketAddress());
						client.register(selector, SelectionKey.OP_READ);
					}
				}
			}
		} catch (Exception e) {
			log.error("NIO客户端错误",e);
			throw e;
			//return String;
		}finally{
			try{
				if(client!=null){
					client.close();
				}
				if(socketChannel!=null){
					socketChannel.close();
				}
				if(selector!=null){
					selector.close();
				}
			}catch(Exception ie){
				log.error("关闭端口异常",ie);
			}
			log.info("NIO 客户端请求结束");
		}
	}
	
	
	/****
	 * 发送消息      用于网络状态不好的情况
	 * @param bb
	 * @param writeTimeout
	 * @return
	 * @throws IOException
	 */
	private long sendChannel(ByteBuffer buf) throws IOException
	{
	    SelectionKey key = null;
	    Selector writeSelector = null;
	    int attempts = 0;
	    long bytesProduced = 0;
	    long len;
	    try {
	        while (buf.hasRemaining()) {
	            len = this.client.write(buf);
	            attempts++;
	            if (len < 0){
	                throw new EOFException();
	            }
	            bytesProduced += len;
	            if (len == 0) {
	            	log.warn("网络状态不好，需要发送多次。。。");
	                if (writeSelector == null){
	                    writeSelector = Selector.open();
	                    if (writeSelector == null){
	                        continue;
	                    }
	                }
	                key = this.client.register(writeSelector, key.OP_WRITE);
	                if (writeSelector.select(this.SELECTTIMEOUT) == 0) {
	                    if (attempts > 2)
	                        throw new IOException("Client disconnected");
	                } else {
	                    attempts--;
	                }
	            } else {
	                attempts = 0;
	            }
	        }
	    } finally {
	        if (key != null) {
	            key.cancel();
	            key = null;
	        }
	        if (writeSelector != null) {
	            writeSelector.close();
	        }
	    }
	    return bytesProduced;
	} 
	
	protected abstract String packing(String sendmsg) throws Exception;
	
	protected abstract boolean decode() throws Exception;


}
