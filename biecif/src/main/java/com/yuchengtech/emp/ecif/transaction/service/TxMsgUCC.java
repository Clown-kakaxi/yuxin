package com.yuchengtech.emp.ecif.transaction.service;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.xml.namespace.QName;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.configuration.ConfigurationException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spc.webos.data.Message;
import spc.webos.endpoint.ESB2;
import spc.webos.endpoint.Executable;
import spc.webos.util.SystemUtil;

import com.yuchengtech.emp.ecif.base.nio.HttpClient;
import com.yuchengtech.emp.ecif.base.nio.SocketClient;
import com.yuchengtech.emp.ecif.transaction.server.TxDealWebService;
import com.yuchengtech.emp.ecif.transaction.server.TxDealWebService_Service;
import com.yuchengtech.emp.utils.StringUtils;

@Service
@Transactional(readOnly = true)
public class TxMsgUCC {

	public String getUrl(Map map) throws ConfigurationException {

		/*
		 * Properties p = new Properties(); InputStream i = new
		 * BufferedInputStream(new FileInputStream(
		 * "./conf/src/mqSeverConfig.properties")); p.load(i); String url =
		 * p.getProperty("url"); return url;
		 */

		String url = (String) map.get("URL");
		return url;
	}

	public String getActiveMqRcvQueueName(Map map) throws ConfigurationException {

		String activeMqRcvQueueName = (String) map.get("activeMqSndQueueName");
		return activeMqRcvQueueName;
	}

	public String getActiveMqSndQueueName(Map map) throws ConfigurationException {

		String activeMqSndQueueName = (String) map.get("activeMqRcvQueueName");
		return activeMqSndQueueName;
	}

	/**
	 * 发送HTTP请求
	 * 
	 * @param ip
	 * @param port
	 * @param sendBody
	 * @return
	 * @throws Exception
	 */
	public String httpMessage(String ip, int port, String sendBody) throws Exception {

		Map map = new HashMap();

		HttpClient client = new HttpClient(ip, port);
		return format(client.interactive(sendBody, "ECIF"));
	}

	/**
	 * 发送socket请求
	 * 
	 * @param ip
	 * @param port
	 * @param sendBody
	 * @return
	 * @throws Exception
	 */
	public String socketMessage(String ip, int port, String sendBody) throws Exception {

		Map map = new HashMap();
		map.put("ip", ip);
		map.put("port", (new Integer(port)).toString());

		SocketClient client = new SocketClient();

		client.init(map);
		try {
//			String msg = client.sendMsg(StringUtils.formate("%08d", sendBody.getBytes().length) + sendBody);
			String msg = client.sendMsg(sendBody);
			msg = format(msg);

			return msg;
		} catch (java.net.ConnectException e) {
			return e.getMessage();
		} catch (Exception e) {
			return e.getMessage();
		}

	}

	/**
	 * 发送报文
	 * 
	 * @param String
	 *        url 服务器地址
	 * @param String
	 *        expectedBody 发送内容
	 */
	public void sendMessage(Map paramMap, String expectedBody) throws JMSException {
		Connection connection = null;
		try {
			// String queueName = this.getActiveMqSndQueueName();
			String queueName = (String) paramMap.get("activeMqRcvQueueName");

			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(this.getUrl(paramMap));
			connection = connectionFactory.createConnection();

			connection.start();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue(queueName);
			MessageProducer producer = session.createProducer(destination);
			TextMessage message = session.createTextMessage(expectedBody);
			message.setStringProperty("headname", "remoteB");
			producer.send(message);
		} catch (JMSException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null)
				connection.close();
		}

	}

	/**
	 * 接收报文
	 * 
	 * @param String
	 *        url 接收报文的服务器地址
	 */
	public String receiveMessage(Map paramMap) {

		Connection connection = null;
		String queueName = null;
		try {
			try {
				// queueName = this.getActiveMqRcvQueueName();
				queueName = (String) paramMap.get("activeMqSndQueueName");
				ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(this.getUrl(paramMap));
				connection = connectionFactory.createConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
			connection.start();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue(queueName);
			MessageConsumer consumer = session.createConsumer(destination);
			return consumeMessagesAndClose(connection, session, consumer);
		} catch (Exception e) {

		}
		return "无内容";
	}

	protected String consumeMessagesAndClose(Connection connection, Session session, MessageConsumer consumer)
			throws JMSException {
		String msg = "无内容";
		for (int i = 0; i < 1;) {
			javax.jms.Message message = consumer.receive(1000);
			if (message != null) {
				i++;
				msg = onMessage(message);
			}
		}
		System.out.println("Closing connection");
		consumer.close();
		session.close();
		connection.close();
		return msg;
	}

	public String onMessage(javax.jms.Message message) throws JMSException {

		if (message instanceof TextMessage) {
			TextMessage txtMsg = (TextMessage) message;
			String msg = txtMsg.getText();
			// System.out.println("Received: " + msg);
			return msg;
		}
		return "无内容";
	}

	private static final QName SERVICE_NAME = new QName("http://server.ws.interfaces.mdm.ytec.com/", "txDealWebService");

	public String webServiceMessage(String ip, int port, String message) {
		// URL wsdlURL = TxDealWebService_Service.WSDL_LOCATION;
		URL wsdlURL = null;
		try {
			// Configuration config = new PropertiesConfiguration(
			// "mqSeverConfig.properties");
			//
			// String ip = paramMap.get("ip");
			// String port = paramMap.get("port");

			wsdlURL = new URL("http://" + ip + ":" + port + "/txDealWebService?wsdl");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TxDealWebService_Service ss = new TxDealWebService_Service(wsdlURL, SERVICE_NAME);
		TxDealWebService portservice = ss.getTxDealWebServiceImplPort();
		return portservice.execute(message);

	}

	// public String esbMessage(String message) throws Exception{
	// Configuration config = new PropertiesConfiguration(
	// "mqSeverConfig.properties");
	// Hashtable props = new Hashtable(); // MQ队列管理器配置
	// props.put("channel", paramMap.get("ycEsbChannel")); // MQ通道配置, 固定
	// props.put("qmName", paramMap.get("ycEsbQmName")); // MQ队列管理器配置，固定
	// props.put(MQC.CCSID_PROPERTY, config.getInt("ycEsbQmCcsid")); // MQ CCSID，固定
	// props.put(MQC.PORT_PROPERTY, config.getInt("ycEsbQmPort")); // MQ端口，固定
	// props.put("hostname", paramMap.get("ycEsbHostname")); // ESB MQ服务的地址，此值依环境动态配置
	// ESB esb = ESB.getInstance();
	// // 客户端对ESB连接是长连接，此为连接数，类似数据库连接数
	// // 测试环境建议配置2，
	// // 生产环境依赖交易量大小决定
	// // 建议每10TPS交易规模增加5个连接，原则上一个客户端不能超过30，否则需// 要向ESB项目组协商,
	// esb.setMaxCnnNum(2);
	// esb.setCnnHoldTime(300); // 长连接有效时间，单位为秒，一般为3到5分钟，-1为连接用不失效，指导队列管理器报告连接错误
	// esb.setProps(props); // 设置MQ访问属性
	// esb.setAppCd(paramMap.get("ycEsbAppCd")); // ESB 为每个接入系统分配的系统编号
	// esb.init(); // 初始化对象，建立和ESB的通讯连接
	// esb = ESB.getInstance();
	// String str = message;
	// spc.webos.data.Message reqmsg;
	// reqmsg = new spc.webos.data.Message(str.getBytes());
	// IMessage repmsg = esb.execute(reqmsg);//执行一次ESB请求，返回为应答报文
	// ESB.getInstance().destory();
	// return repmsg.toString();
	// }
	//

	public String esb2Message(Map paramMap, String message) throws Exception {
		// Configuration config = new PropertiesConfiguration(
		// "mqSeverConfig.properties");
		Hashtable arg = new Hashtable(); // MQ队列管理器配置
		arg.put("ycEsbHostname_main", paramMap.get("ycEsbHostname_main")); // ESB地址(主)
		arg.put("ycEsbQmName_main", paramMap.get("ycEsbQmName_main")); // MQ队列管理器(主)
		arg.put("ycEsbQmPort_main", paramMap.get("ycEsbQmPort_main")); // MQ端口(主)
		arg.put("ycEsbQmCcsid_main", paramMap.get("ycEsbQmCcsid_main")); // MQCCSID，固定
		arg.put("ycEsbChannel_main", paramMap.get("ycEsbChannel_main")); // MQ通道(主)
		arg.put("ycEsbSndQueueName_main", paramMap.get("ycEsbSndQueueName_main")); // 请求队列(主)
		arg.put("ycEsbRcvQueueName_main", paramMap.get("ycEsbRcvQueueName_main")); // 响应队列(主)
		arg.put("ycEsbCnnPoolMax_main", paramMap.get("ycEsbCnnPoolMax_main")); // ESB 连接数(主)
		arg.put("ycEsbHostname_sub", paramMap.get("ycEsbHostname_sub")); // ESB地址(辅)
		arg.put("ycEsbQmName_sub", paramMap.get("ycEsbQmName_sub")); // MQ队列管理器(辅)
		arg.put("ycEsbQmPort_sub", paramMap.get("ycEsbQmPort_sub")); // MQ端口(辅)
		arg.put("ycEsbQmCcsid_sub", paramMap.get("ycEsbQmCcsid_sub")); // MQCCSID，固定(辅)
		arg.put("ycEsbChannel_sub", paramMap.get("ycEsbChannel_sub")); // MQ通道 (辅)
		arg.put("ycEsbRcvQueueName_sub", paramMap.get("ycEsbRcvQueueName_sub")); // 响应队列(主)
		arg.put("ycEsbCnnPoolMax_sub", paramMap.get("ycEsbCnnPoolMax_sub")); // ESB 连接数(辅)
		arg.put("charset", paramMap.get("charset")); // 报文字符集
		arg.put("timeout", paramMap.get("timeout")); // 超时时间(秒)

		ESB2 esb2 = ESB2.getInstance();
		// esb2.setAccess("[{name:'"+arg.get("ycEsbQmName_main")+"',maxCnnNum:"+arg.get("ycEsbCnnPoolMax_main")+",channel:{hostname:'"+arg.get("ycEsbHostname_main")+"',port:"+arg.get("ycEsbQmPort_main")+",channel:'"+arg.get("ycEsbChannel_main")+"'}},"
		// +
		// "{name:'"+arg.get("ycEsbQmName_sub")+"',maxCnnNum:"+arg.get("ycEsbCnnPoolMax_sub")+",channel:{hostname:'"+arg.get("ycEsbHostname_sub")+"',port:"+arg.get("ycEsbQmPort_sub")+",channel:'"+arg.get("ycEsbChannel_sub")+"'}}]");
		// esb2.setSynResponsePools("[{props:{hostname:'"+arg.get("ycEsbHostname_main")+"',port:"+arg.get("ycEsbQmPort_main")+",channel:'"+arg.get("ycEsbChannel_main")+"'}},{props:{hostname:'"+arg.get("ycEsbHostname_sub")+"',port:"+arg.get("ycEsbQmPort_sub")+",channel:'"+arg.get("ycEsbChannel_sub")+"'}}]");
		// esb2.setReplyToQ((String)arg.get("ycEsbRcvQueueName_main"));
		// esb2.setMatchMsgId(true);
		esb2.setAccess("[{name:'" + arg.get("ycEsbQmName_main") + "',maxCnnNum:" + arg.get("ycEsbCnnPoolMax_main")
				+ ",channel:{hostname:'" + arg.get("ycEsbHostname_main") + "',port:" + arg.get("ycEsbQmPort_main")
				+ ",channel:'" + arg.get("ycEsbChannel_main") + "'}}]");
		esb2.setJvm("A1");
		esb2.init();
		String seqNb = esb2.getSeqNb(); // 报文必须由ESB2对象生成的流水号。
		String sndDt = SystemUtil.getInstance().getCurrentDate(SystemUtil.DF_APP8);
		// String sndTm = SystemUtil.getInstance().getCurrentDate(SystemUtil.DF_HMS9);
		String corId = sndDt + "-" + seqNb;
		byte[] request = message.getBytes();
		Message msg = new Message(request);
		msg.setSeqNb(seqNb);
		msg.setSndDt(sndDt);
		// msg.setSndTm(sndTm);
		Executable exe = new Executable();
		exe.request = msg.toByteArray(false);
		exe.timeout = Integer.valueOf((String) arg.get("timeout"));
		exe.correlationID = corId.getBytes();
		esb2.execute(exe);
		esb2.destory();
		return new String(exe.response, (String) arg.get("charset"));
	}

	public static String format(String str) throws Exception {

		StringReader in = null;
		StringWriter out = null;
		try {
			SAXReader reader = new SAXReader();
			// 创建一个串的字符输入流
			in = new StringReader(str);
			Document doc = reader.read(in);
			// 创建输出格式
			OutputFormat formate = OutputFormat.createPrettyPrint();
			// 创建输出
			out = new StringWriter();
			// 创建输出流
			XMLWriter writer = new XMLWriter(out, formate);
			// 输出格式化的串到目标中,格式化后的串保存在out中。
			writer.write(doc);
		} catch (IOException ioe) {
			throw new Exception("对xml字符串进行格式化时产生IOException异常", ioe);
		} catch (DocumentException de) {
			return str;
			// throw new Exception("对xml字符串进行格式化时产生DocumentException异常",de);
		} finally {
			// 关闭流
			quietClose(in);
			quietClose(out);
		}
		return out.toString();
	}

	public static void quietClose(Reader reader) {
		try {
			if (reader != null) {
				reader.close();
			}
		} catch (IOException ioe) {
		}

	}

	public static void quietClose(Writer writer) {
		try {
			if (writer != null) {
				writer.close();
			}
		} catch (IOException ioe) {
		}
	}

}