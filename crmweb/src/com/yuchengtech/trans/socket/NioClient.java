package com.yuchengtech.trans.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuchengtech.trans.bo.TxData;

public class NioClient {
	private static Logger log = LoggerFactory
			.getLogger(NioClient.class);
	private SimpleDateFormat sdf16 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// 缓冲区大小
	private static int BLOCK = 14096;
	// 发送数据大小
	private static ByteBuffer sendbuffer = ByteBuffer.allocate(BLOCK);
	// 接收数据大小
	private static ByteBuffer receiverbuffer = ByteBuffer.allocate(BLOCK);

	private String ip = null;
	private int port;
	private Map para;

	private static InetSocketAddress SERVER_ADDRESS;

	public NioClient(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	public NioClient(String ip, int port, Object... para) {
		this.ip = ip;
		this.port = port;
	}

//	private String getData() {
//		StringBuffer sb = new StringBuffer();
//		sb.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
//		sb.append("<TransBody>");
//		sb.append("	<RequestHeader>");
//		sb.append("		<ReqSysCd>CRM</ReqSysCd>");
//		sb.append("		<ReqSeqNo>20140906122659</ReqSeqNo>");
//		sb.append("		<ReqDt>20140906</ReqDt>");
//		sb.append("		<ReqTm>122659</ReqTm>");
//		sb.append("		<DestSysCd>CB</DestSysCd>");
//		sb.append("		<ChnlNo>82</ChnlNo>");
//		sb.append("		<BrchNo>503</BrchNo>");
//		sb.append("		<BizLine>209</BizLine>");
//		sb.append("		<TrmNo>TRM10010</TrmNo>");
//		sb.append("		<TrmIP>10.18.250.71</TrmIP>");
//		sb.append("		<TlrNo>6101</TlrNo>");
//		sb.append("	</RequestHeader>");
//		sb.append("	<RequestBody>");
//		sb.append("		<txCode>CMSD</txCode>");
//		sb.append("		<custCd>503000018213</custCd>");
//		sb.append("	</RequestBody>");
//		sb.append("</TransBody>");
//		return String.format("%08d", sb.toString().getBytes().length)+sb.toString();
//	}

	public static String SocketCommunication(String sysName, Object... para) throws Exception {
		return null;
	}

	/**
	 * @Title: SocketCommunication
	 * @Description: 与服务器通讯
	 * @param @param msg 报文内容
	 * @param		 requestCharSet   请求字符串编码方式，默认GBK（可为Null）
	 * @param @throws IOException
	 * @throws
	 */
	public String SocketCommunication(String msg) throws Exception {
		
		// 开启通道
		SocketChannel sc = SocketChannel.open();
		sc.configureBlocking(false);

		// 开启选择器
		Selector selector = Selector.open();
		SERVER_ADDRESS = new InetSocketAddress(ip, port);

		// 注册连接到服务器
		sc.register(selector, SelectionKey.OP_CONNECT);

		// 连接
		sc.connect(SERVER_ADDRESS);

		// 分配缓冲区
		Set<SelectionKey> selectionKeys;
		Iterator<SelectionKey> iterator;
		SelectionKey selectionKey;
		SocketChannel client = null;
		String receiveText = null; // getdata from server core
		int count = 0;
		int flag = 1;
		while (flag > 0) {
			selector.select();
			selectionKeys = selector.selectedKeys();
			iterator = selectionKeys.iterator();
			while (iterator.hasNext()) {
				selectionKey = iterator.next();
				client = (SocketChannel) selectionKey.channel();
				if (selectionKey.isConnectable()) {
//					System.out.println("client connect");

					// 判断此通道上是否正在进行连接操作,完成套接字通道的连接过程。
					if (client.isConnectionPending()) {
						client.finishConnect();
//						System.out.println("完成连接!");
					}
					client.register(selector, SelectionKey.OP_WRITE);
				} else if (selectionKey.isReadable()) {
					client = (SocketChannel) selectionKey.channel();
					receiverbuffer.clear();
					count = client.read(receiverbuffer);
					if (count > 0) {
						receiverbuffer.flip();
						// 字符集
						receiveText = new String(receiverbuffer.array(), 0, count, "GBK");
//							System.out.println("客户端接受服务器端数据<---------------------");

						if (receiveText.length() > 0) {
//								System.out.println("<--" + receiveText);
							flag = -1;
							//break;
						} else {
							client.register(selector, SelectionKey.OP_WRITE);
						}
					}
					if (receiveText.length() > 0) {
//						System.out.println("<--" + receiveText);
						flag = -1;
						break;
					} else {
						client.register(selector, SelectionKey.OP_WRITE);
					}if (receiveText.length() > 0) {
//						System.out.println("<--" + receiveText);
						flag = -1;
						//break;
					} else {
						client.register(selector, SelectionKey.OP_WRITE);
					}
				} else if (selectionKey.isWritable()) {
					sendbuffer.clear();
					client = (SocketChannel) selectionKey.channel();
					sendbuffer.put(msg.getBytes("GBK"));
					 //sendbuffer.put((new String(msg.getBytes("UTF-8"),"GBK")).getBytes());
					sendbuffer.flip();
					client.write(sendbuffer);
//					System.out.println("客户端向服务器端发送数据---------------------------->");
//					System.err.println(msg);
					client.register(selector, SelectionKey.OP_READ);
				}
			}
		}
		if (client != null) {
			client.close();
		}
		if (sc != null) {
			sc.close();
		}
		if (selector != null) {
			selector.close();
		}
		return receiveText;
	}
	
	
	/**
	 * @Title: SocketCommunication
	 * @Description: 与服务器通讯
	 * @param @param msg 报文内容
	 * @param		 requestCharSet   请求字符串编码方式，默认GBK（可为Null）
	 * @param @throws IOException
	 * @throws
	 */
	public String SocketCommunication(String sysNm, TxData txData, String reqCharset, String resCharset) throws Exception {
		String msg = txData.getReqMsg();
		// 开启通道
		SocketChannel sc = SocketChannel.open();
		sc.configureBlocking(false);

		// 开启选择器
		Selector selector = Selector.open();
		SERVER_ADDRESS = new InetSocketAddress(ip, port);

		// 注册连接到服务器
		sc.register(selector, SelectionKey.OP_CONNECT);

		// 连接
		sc.connect(SERVER_ADDRESS);

		// 分配缓冲区
		Set<SelectionKey> selectionKeys;
		Iterator<SelectionKey> iterator;
		SelectionKey selectionKey;
		SocketChannel client = null;
		String receiveText = null; // getdata from server core
		int count = 0;
		int flag = 1;
		while (flag > 0) {
			selector.select();
			selectionKeys = selector.selectedKeys();
			iterator = selectionKeys.iterator();
			while (iterator.hasNext()) {
				selectionKey = iterator.next();
				client = (SocketChannel) selectionKey.channel();
				if (selectionKey.isConnectable()) {
//					System.out.println("client connect");

					// 判断此通道上是否正在进行连接操作,完成套接字通道的连接过程。
					if (client.isConnectionPending()) {
						client.finishConnect();
//						System.out.println("完成连接!");
					}
					client.register(selector, SelectionKey.OP_WRITE);
				} else if (selectionKey.isReadable()) {
					txData.setTxResTm(new Timestamp(System.currentTimeMillis()));//添加响应时间
					log.info("开始读取响应报文----------------------------------------------------------");
					log.info("当前响应报文系统：["+sysNm+"]");
					log.info("当前系统时间：" + sdf16.format(new Date()));
					client = (SocketChannel) selectionKey.channel();
					receiverbuffer.clear();
					count = client.read(receiverbuffer);
					if (count > 0) {
						receiverbuffer.flip();
						// 字符集
						if(StringUtils.isEmpty(resCharset)){
							reqCharset = "GBK";
						}
						receiveText = new String(receiverbuffer.array(), 0, count, reqCharset);
//							System.out.println("客户端接受服务器端数据<---------------------");

						log.info("响应报文内容：["+receiveText+"]");
						log.info("响应报文结束----------------------------------------------------------");
						if (receiveText.length() > 0) {
//								System.out.println("<--" + receiveText);
							flag = -1;
							//break;
						} else {
							client.register(selector, SelectionKey.OP_WRITE);
						}
					}
					if (receiveText.length() > 0) {
//						System.out.println("<--" + receiveText);
						flag = -1;
						break;
					} else {
						client.register(selector, SelectionKey.OP_WRITE);
					}if (receiveText.length() > 0) {
//						System.out.println("<--" + receiveText);
						flag = -1;
						//break;
					} else {
						client.register(selector, SelectionKey.OP_WRITE);
					}
				} else if (selectionKey.isWritable()) {
					txData.setTxReqTm(new Timestamp(System.currentTimeMillis()));//添加请求时间
					log.info("开始发送请求报文----------------------------------------------------------");
					log.info("当前系统时间：" + sdf16.format(new Date()));
					log.info("当前请求报文系统：["+sysNm+"]");
					log.info("当前请求地址：["+ip+"]--端口：["+port+"]");
					log.info("请求报文内容：["+msg+"]");
					sendbuffer.clear();
					client = (SocketChannel) selectionKey.channel();
					//字符集
					if(StringUtils.isEmpty(reqCharset)){
						reqCharset = "GBK";
					}
					sendbuffer.put(msg.getBytes(reqCharset));
					 //sendbuffer.put((new String(msg.getBytes("UTF-8"),"GBK")).getBytes());
					sendbuffer.flip();
					client.write(sendbuffer);
//					System.out.println("客户端向服务器端发送数据---------------------------->");
//					System.err.println(msg);
					client.register(selector, SelectionKey.OP_READ);
					log.info("请求报文发送结束----------------------------------------------------------");
				}
			}
		}
		if (client != null) {
			client.close();
		}
		if (sc != null) {
			sc.close();
		}
		if (selector != null) {
			selector.close();
		}
		return receiveText;
	}
	
	
	
	/**
	 * @Title: SocketCommunication
	 * @Description: 与服务器通讯
	 * @param @param msg 报文内容
	 * @param		 requestCharSet   请求字符串编码方式，默认GBK（可为Null）
	 * @param @throws IOException
	 * @throws
	 */
	public String SocketCommunication(String msg, String requestCharSet, String responCharSet, String sysInfo) throws Exception {
		
		// 开启通道
		SocketChannel sc = SocketChannel.open();
		sc.configureBlocking(false);

		// 开启选择器
		Selector selector = Selector.open();
		SERVER_ADDRESS = new InetSocketAddress(ip, port);

		// 注册连接到服务器
		sc.register(selector, SelectionKey.OP_CONNECT);

		// 连接
		sc.connect(SERVER_ADDRESS);

		// 分配缓冲区
		Set<SelectionKey> selectionKeys;
		Iterator<SelectionKey> iterator;
		SelectionKey selectionKey;
		SocketChannel client = null;
		String receiveText = null; // getdata from server core
		int count = 0;
		int flag = 1;
		while (flag > 0) {
			selector.select();
			selectionKeys = selector.selectedKeys();
			iterator = selectionKeys.iterator();
			while (iterator.hasNext()) {
				selectionKey = iterator.next();
				client = (SocketChannel) selectionKey.channel();
				if (selectionKey.isConnectable()) {
//					System.out.println("client connect");

					// 判断此通道上是否正在进行连接操作,完成套接字通道的连接过程。
					if (client.isConnectionPending()) {
						client.finishConnect();
//						System.out.println("完成连接!");
					}
					client.register(selector, SelectionKey.OP_WRITE);
				} else if (selectionKey.isReadable()) {
//					System.out.println("开始读取响应报文----------------------------------------------------------");
//					System.out.println("当前响应报文系统：["+sysInfo+"]");
//					System.out.println("当前系统时间：" + sdf16.format(new Date()));
					log.info("开始读取响应报文----------------------------------------------------------");
					log.info("当前响应报文系统：["+sysInfo+"]");
					log.info("当前系统时间：" + sdf16.format(new Date()));
					client = (SocketChannel) selectionKey.channel();
					receiverbuffer.clear();
					try {
						count = client.read(receiverbuffer);
					} catch (IOException e) {
						e.printStackTrace();
						selectionKey.cancel();
						client.socket().close();
						client.close();
						return receiveText;
					}
					if (count > 0) {
						receiverbuffer.flip();
						// 字符集
						if(StringUtils.isEmpty(responCharSet)){
							responCharSet = "GBK";
						}
						receiveText = new String(receiverbuffer.array(), 0, count, responCharSet);
						log.info("响应报文内容：["+receiveText+"]");
						log.info("响应报文结束----------------------------------------------------------");
//						System.out.println("响应报文内容：["+receiveText+"]");
//						System.out.println("响应报文结束----------------------------------------------------------");
//						
						//receiveText = new String(receiverbuffer.array(), 0, count, "GBK");
//							System.out.println("客户端接受服务器端数据<---------------------");

						if (receiveText.length() > 0) {
//								System.out.println("<--" + receiveText);
							flag = -1;
							//break;
						} else {
							client.register(selector, SelectionKey.OP_WRITE);
						}
					}
					if (receiveText.length() > 0) {
//						System.out.println("<--" + receiveText);
						flag = -1;
						break;
					} else {
						client.register(selector, SelectionKey.OP_WRITE);
					}if (receiveText.length() > 0) {
//						System.out.println("<--" + receiveText);
						flag = -1;
						//break;
					} else {
						client.register(selector, SelectionKey.OP_WRITE);
					}

					if (client != null) {
						client.close();
					}
					if (sc != null) {
						sc.close();
					}
					if (selector != null) {
						selector.close();
					}
				} else if (selectionKey.isWritable()) {
					log.info("开始发送请求报文----------------------------------------------------------");
					log.info("当前系统时间：" + sdf16.format(new Date()));
					log.info("当前请求报文系统：["+sysInfo+"]");
					log.info("请求报文内容：["+msg+"]");
//					System.out.println("开始发送请求报文----------------------------------------------------------");
//					System.out.println("当前系统时间：" + sdf16.format(new Date()));
//					System.out.println("当前请求报文系统：["+sysInfo+"]");
//					System.out.println("请求报文内容：["+msg+"]");
					sendbuffer.clear();
					client = (SocketChannel) selectionKey.channel();
					//sendbuffer.put(msg.getBytes("GBK"));
					if(requestCharSet == null || requestCharSet.equals("")){
						requestCharSet = "GBK";
					}
					sendbuffer.put(msg.getBytes(requestCharSet));
					 //sendbuffer.put((new String(msg.getBytes("UTF-8"),"GBK")).getBytes());
					sendbuffer.flip();
					client.write(sendbuffer);
//					System.out.println("客户端向服务器端发送数据---------------------------->");
//					System.err.println(msg);
					client.register(selector, SelectionKey.OP_READ);
					log.info("请求报文发送结束----------------------------------------------------------");
//					System.out.println("请求报文发送结束----------------------------------------------------------");
				}
			}
		}
		return receiveText;
	}
	
	
	/**
	 * @Title: SocketCommunication
	 * @Description: 与服务器通讯
	 * @param		 txData 交易数据内容
	 * @param		 requestCharSet   请求字符串编码方式，默认GBK（可为Null）
	 * @param @throws IOException
	 * @throws
	 */
	public String SocketCommunication(TxData txData, String requestCharSet, String sysInfo){
		String msg = txData.getReqMsg();
		String receiveText = null; // getdata from server core
		try {
			// 开启通道
			SocketChannel sc = SocketChannel.open();
			sc.configureBlocking(false);

			// 开启选择器
			Selector selector = Selector.open();
			SERVER_ADDRESS = new InetSocketAddress(ip, port);
			//添加请求地址端口
			txData.setTxSvrIp(ip);
			// 注册连接到服务器
			sc.register(selector, SelectionKey.OP_CONNECT);

			// 连接
			sc.connect(SERVER_ADDRESS);

			// 分配缓冲区
			Set<SelectionKey> selectionKeys;
			Iterator<SelectionKey> iterator;
			SelectionKey selectionKey;
			SocketChannel client = null;
			int count = 0;
			int flag = 1;
			while (flag > 0) {
				selector.select();
				selectionKeys = selector.selectedKeys();
				iterator = selectionKeys.iterator();
				while (iterator.hasNext()) {
					selectionKey = iterator.next();
					client = (SocketChannel) selectionKey.channel();
					if (selectionKey.isConnectable()) {
//						System.out.println("client connect");

						// 判断此通道上是否正在进行连接操作,完成套接字通道的连接过程。
						if (client.isConnectionPending()) {
							client.finishConnect();
//							System.out.println("完成连接!");
						}
						client.register(selector, SelectionKey.OP_WRITE);
					} else if (selectionKey.isReadable()) {
//						System.out.println("开始读取响应报文----------------------------------------------------------");
//						System.out.println("当前响应报文系统：["+sysInfo+"]");
//						System.out.println("当前系统时间：" + sdf16.format(new Date()));
						txData.setTxResTm(new Timestamp(System.currentTimeMillis()));//添加响应时间
						log.info("开始读取响应报文----------------------------------------------------------");
						log.info("当前响应报文系统：["+sysInfo+"]");
						log.info("当前系统时间：" + sdf16.format(new Date()));
						client = (SocketChannel) selectionKey.channel();
						receiverbuffer.clear();
						try {
							count = client.read(receiverbuffer);
						} catch (IOException e) {
							e.printStackTrace();
							selectionKey.cancel();
							client.socket().close();
							client.close();
							return receiveText;
						}
						if (count > 0) {
							receiverbuffer.flip();
							// 字符集
							receiveText = new String(receiverbuffer.array(), 0, count, requestCharSet);
//							String receiveText2 = new String(receiverbuffer.array(), 0, count, "UTF-8");
//							log.info("响应报文内容2：["+receiveText2+"]");
//							log.info("响应报文内容2：["+new String(receiveText2.getBytes("UTF-8"))+"]");
//							log.info("响应报文内容GBK：["+new String(receiveText.getBytes("UTF-8"),"GBK")+"]");
							log.info("响应报文内容：["+receiveText+"]");
							log.info("响应报文结束----------------------------------------------------------");
//							System.out.println("响应报文内容：["+receiveText+"]");
//							System.out.println("响应报文结束----------------------------------------------------------");
//							
							//receiveText = new String(receiverbuffer.array(), 0, count, "GBK");
//								System.out.println("客户端接受服务器端数据<---------------------");

							if (StringUtils.isNotBlank(receiveText)) {
//									System.out.println("<--" + receiveText);
								flag = -1;
								//break;
							} else {
								client.register(selector, SelectionKey.OP_WRITE);
							}
						}
						if (count==-1||StringUtils.isNotBlank(receiveText)) {
//							System.out.println("<--" + receiveText);
							flag = -1;
							//break;
						} else {
							client.register(selector, SelectionKey.OP_WRITE);
						}
						/*if (receiveText.length() > 0) {
//							System.out.println("<--" + receiveText);
							flag = -1;
							//break;
						} else {
							client.register(selector, SelectionKey.OP_WRITE);
						}*/

						if (client != null) {
							client.close();
						}
						if (sc != null) {
							sc.close();
						}
						if (selector != null) {
							selector.close();
						}
					} else if (selectionKey.isWritable()) {
						txData.setTxReqTm(new Timestamp(System.currentTimeMillis()));//添加请求时间
						log.info("开始发送请求报文----------------------------------------------------------");
						log.info("当前系统时间：" + sdf16.format(new Date()));
						log.info("当前请求报文系统：["+sysInfo+"]");
						log.info("当前请求地址：["+ip+"]--端口：["+port+"]");
						log.info("请求报文内容：["+msg+"]");
//						System.out.println("开始发送请求报文----------------------------------------------------------");
//						System.out.println("当前系统时间：" + sdf16.format(new Date()));
//						System.out.println("当前请求报文系统：["+sysInfo+"]");
//						System.out.println("请求报文内容：["+msg+"]");
						sendbuffer.clear();
						client = (SocketChannel) selectionKey.channel();
						//sendbuffer.put(msg.getBytes("GBK"));
						if(requestCharSet == null || requestCharSet.equals("")){
							requestCharSet = "GBK";
						}
						sendbuffer.put(msg.getBytes(requestCharSet));
						 //sendbuffer.put((new String(msg.getBytes("UTF-8"),"GBK")).getBytes());
						sendbuffer.flip();
						client.write(sendbuffer);
//						System.out.println("客户端向服务器端发送数据---------------------------->");
//						System.err.println(msg);
						client.register(selector, SelectionKey.OP_READ);
						log.info("请求报文发送结束----------------------------------------------------------");
//						System.out.println("请求报文发送结束----------------------------------------------------------");
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			String logMsg = "通讯异常，请联系管理员";
			log.error(logMsg);
			txData.setTxResult("2");
			txData.setTxRtnCd("");
			txData.setTxRtnMsg(logMsg);
			txData.setStatus("error");
			txData.setMsg(logMsg);
			txData.setAttribute("status", "error");
			txData.setAttribute("msg", logMsg);
		}
		
		return receiveText;
	}
	
	
	/**
	 * @Title: SocketCommunication
	 * @Description: 与服务器通讯
	 * @param @param msg 报文内容
	 * @param		 requestCharSet   请求字符串编码方式，默认GBK（可为Null）
	 * @param @throws IOException
	 * @throws
	 */
	public String SocketCommunication(String msg, String requestCharSet, String sysInfo) throws Exception {
		
		// 开启通道
		SocketChannel sc = SocketChannel.open();
		sc.configureBlocking(false);

		// 开启选择器
		Selector selector = Selector.open();
		SERVER_ADDRESS = new InetSocketAddress(ip, port);

		// 注册连接到服务器
		sc.register(selector, SelectionKey.OP_CONNECT);

		// 连接
		sc.connect(SERVER_ADDRESS);

		// 分配缓冲区
		Set<SelectionKey> selectionKeys;
		Iterator<SelectionKey> iterator;
		SelectionKey selectionKey;
		SocketChannel client = null;
		String receiveText = null; // getdata from server core
		int count = 0;
		int flag = 1;
		while (flag > 0) {
			selector.select();
			selectionKeys = selector.selectedKeys();
			iterator = selectionKeys.iterator();
			while (iterator.hasNext()) {
				selectionKey = iterator.next();
				client = (SocketChannel) selectionKey.channel();
				if (selectionKey.isConnectable()) {
//					System.out.println("client connect");

					// 判断此通道上是否正在进行连接操作,完成套接字通道的连接过程。
					if (client.isConnectionPending()) {
						client.finishConnect();
//						System.out.println("完成连接!");
					}
					client.register(selector, SelectionKey.OP_WRITE);
				} else if (selectionKey.isReadable()) {
//					System.out.println("开始读取响应报文----------------------------------------------------------");
//					System.out.println("当前响应报文系统：["+sysInfo+"]");
//					System.out.println("当前系统时间：" + sdf16.format(new Date()));
					log.info("开始读取响应报文----------------------------------------------------------");
					log.info("当前响应报文系统：["+sysInfo+"]");
					log.info("当前系统时间：" + sdf16.format(new Date()));
					client = (SocketChannel) selectionKey.channel();
					receiverbuffer.clear();
					try {
						count = client.read(receiverbuffer);
					} catch (IOException e) {
						e.printStackTrace();
						selectionKey.cancel();
						client.socket().close();
						client.close();
						return receiveText;
					}
					if (count > 0) {
						receiverbuffer.flip();
						// 字符集
						receiveText = new String(receiverbuffer.array(), 0, count, requestCharSet);
//						String receiveText2 = new String(receiverbuffer.array(), 0, count, "UTF-8");
//						log.info("响应报文内容2：["+receiveText2+"]");
//						log.info("响应报文内容2：["+new String(receiveText2.getBytes("UTF-8"))+"]");
//						log.info("响应报文内容GBK：["+new String(receiveText.getBytes("UTF-8"),"GBK")+"]");
						log.info("响应报文内容：["+receiveText+"]");
						log.info("响应报文结束----------------------------------------------------------");
//						System.out.println("响应报文内容：["+receiveText+"]");
//						System.out.println("响应报文结束----------------------------------------------------------");
//						
						//receiveText = new String(receiverbuffer.array(), 0, count, "GBK");
//							System.out.println("客户端接受服务器端数据<---------------------");

						if (receiveText.length() > 0) {
//								System.out.println("<--" + receiveText);
							flag = -1;
							//break;
						} else {
							client.register(selector, SelectionKey.OP_WRITE);
						}
					}
					if (receiveText.length() > 0) {
//						System.out.println("<--" + receiveText);
						flag = -1;
						break;
					} else {
						client.register(selector, SelectionKey.OP_WRITE);
					}if (receiveText.length() > 0) {
//						System.out.println("<--" + receiveText);
						flag = -1;
						//break;
					} else {
						client.register(selector, SelectionKey.OP_WRITE);
					}

					if (client != null) {
						client.close();
					}
					if (sc != null) {
						sc.close();
					}
					if (selector != null) {
						selector.close();
					}
				} else if (selectionKey.isWritable()) {
					log.info("开始发送请求报文----------------------------------------------------------");
					log.info("当前系统时间：" + sdf16.format(new Date()));
					log.info("当前请求报文系统：["+sysInfo+"]");
					log.info("当前请求地址：["+ip+"]--端口：["+port+"]");
					log.info("请求报文内容：["+msg+"]");
//					System.out.println("开始发送请求报文----------------------------------------------------------");
//					System.out.println("当前系统时间：" + sdf16.format(new Date()));
//					System.out.println("当前请求报文系统：["+sysInfo+"]");
//					System.out.println("请求报文内容：["+msg+"]");
					sendbuffer.clear();
					client = (SocketChannel) selectionKey.channel();
					//sendbuffer.put(msg.getBytes("GBK"));
					if(requestCharSet == null || requestCharSet.equals("")){
						requestCharSet = "GBK";
					}
					sendbuffer.put(msg.getBytes(requestCharSet));
					 //sendbuffer.put((new String(msg.getBytes("UTF-8"),"GBK")).getBytes());
					sendbuffer.flip();
					client.write(sendbuffer);
//					System.out.println("客户端向服务器端发送数据---------------------------->");
//					System.err.println(msg);
					client.register(selector, SelectionKey.OP_READ);
					log.info("请求报文发送结束----------------------------------------------------------");
//					System.out.println("请求报文发送结束----------------------------------------------------------");
				}
			}
		}
		return receiveText;
	}

	/**
	 * @Title: SocketCommunication
	 * @Description: 与服务器通讯
	 * @param @param msg 报文内容
	 * @param		 requestCharSet   请求字符串编码方式，默认GBK（可为Null）
	 * @param @throws IOException
	 * @throws
	 */
	public String SocketCommunication2(String msg, String requestCharSet, String sysInfo) throws Exception {
		
		// 开启通道
		SocketChannel sc = SocketChannel.open();
		sc.configureBlocking(false);

		// 开启选择器
		Selector selector = Selector.open();
		SERVER_ADDRESS = new InetSocketAddress(ip, port);

		// 注册连接到服务器
		sc.register(selector, SelectionKey.OP_CONNECT);

		// 连接
		sc.connect(SERVER_ADDRESS);

		// 分配缓冲区
		Set<SelectionKey> selectionKeys;
		Iterator<SelectionKey> iterator;
		SelectionKey selectionKey;
		SocketChannel client = null;
		String receiveText = null; // getdata from server core
		int count = 0;
		int flag = 1;
		while (flag > 0) {
			selector.select();
			selectionKeys = selector.selectedKeys();
			iterator = selectionKeys.iterator();
			while (iterator.hasNext()) {
				selectionKey = iterator.next();
				client = (SocketChannel) selectionKey.channel();
				if (selectionKey.isConnectable()) {
//					System.out.println("client connect");

					// 判断此通道上是否正在进行连接操作,完成套接字通道的连接过程。
					if (client.isConnectionPending()) {
						client.finishConnect();
//						System.out.println("完成连接!");
					}
					client.register(selector, SelectionKey.OP_WRITE);
				} else if (selectionKey.isReadable()) {
					log.info("开始读取响应报文----------------------------------------------------------");
					log.info("当前响应报文系统：["+sysInfo+"]");
					log.info("当前系统时间：" + sdf16.format(new Date()));
					client = (SocketChannel) selectionKey.channel();
					receiverbuffer.clear();
					//count = client.read(receiverbuffer);
					int countLength = 0;
					while((count = client.read(receiverbuffer)) != -1){
						countLength += count;
					}
					receiveText = new String(receiverbuffer.array(), 0, countLength, requestCharSet);//
					log.info("响应报文内容：["+receiveText+"]");
					log.info("响应报文结束----------------------------------------------------------");
					receiverbuffer.flip();
					receiverbuffer.clear();
					if (receiveText.length() > 0) {
//						System.out.println("<--" + receiveText);
						flag = -1;
						break;
					} else {
						client.register(selector, SelectionKey.OP_WRITE);
					}if (receiveText.length() > 0) {
//						System.out.println("<--" + receiveText);
						flag = -1;
						//break;
					} else {
						client.register(selector, SelectionKey.OP_WRITE);
					}
				} else if (selectionKey.isWritable()) {
					log.info("开始发送请求报文----------------------------------------------------------");
					log.info("当前请求报文系统：["+sysInfo+"]");
					log.info("当前系统时间：" + sdf16.format(new Date()));
					log.info("请求报文内容：["+msg+"]");
					sendbuffer.clear();
					client = (SocketChannel) selectionKey.channel();
					//sendbuffer.put(msg.getBytes("GBK"));
					if(requestCharSet == null || requestCharSet.equals("")){
						requestCharSet = "GBK";
					}
					sendbuffer.put(msg.getBytes(requestCharSet));
					 //sendbuffer.put((new String(msg.getBytes("UTF-8"),"GBK")).getBytes());
					sendbuffer.flip();
					client.write(sendbuffer);
//					System.out.println("客户端向服务器端发送数据---------------------------->");
//					System.err.println(msg);
					client.register(selector, SelectionKey.OP_READ);
					log.info("请求报文发送结束----------------------------------------------------------");
				}
			}
		}
		if (client != null) {
			client.close();
		}
		if (sc != null) {
			sc.close();
		}
		if (selector != null) {
			selector.close();
		}
		return receiveText;
	}
	
	
	
	/**
	 * 发送报文
	 * @param param				报文内容
	 * @param requestCharSet	请求内容编码
	 * @param hostIp			IP地址
	 * @param port				端口号
	 * @param sysInfo			请求系统信息
	 * @return
	 */
	public static String sendRequest(TxData txData, String requestCharSet,
			String hostIp, int port, String sysInfo) {
		NioClient cl = new NioClient(hostIp, port);

		String resp = null;
		if (requestCharSet == null || requestCharSet.equals("")) {
			requestCharSet = "GBK";
		}
		resp = cl.SocketCommunication(txData, requestCharSet, sysInfo);
		return resp;
	}
}
