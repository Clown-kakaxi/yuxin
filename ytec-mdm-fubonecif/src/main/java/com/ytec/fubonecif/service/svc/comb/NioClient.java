package com.ytec.fubonecif.service.svc.comb;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class NioClient {
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
//						System.out.println("客户端接受服务器端数据<---------------------");

						if (receiveText.length() > 0) {
//							System.out.println("<--" + receiveText);
							flag = -1;
							break;
						} else {
							client.register(selector, SelectionKey.OP_WRITE);
						}
					}
				} else if (selectionKey.isWritable()) {
					sendbuffer.clear();
					client = (SocketChannel) selectionKey.channel();
					sendbuffer.put(msg.getBytes("GBK"));
					// sendbuffer.put((new String(msg.getBytes("UTF-8"),"GBK")).getBytes());
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
	
//	public static void main(String[] args) {
//		String ip = "10.20.35.193";
//		int port = 12027;
//		NioClient cl = new NioClient(ip,port);
//		
//		try {
//			String resp = cl.SocketCommunication(cl.getData());
//			
//			System.err.println("===========================");
//			System.err.println(resp.trim());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
