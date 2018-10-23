/**
 * 
 */
package com.yuchengtech.emp.ecif.base.nio;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * Title:程序的中文名称
 * Description: 程序功能的描述
 * </pre>
 * 
 * @author guanyb guanyb@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class HttpClient {
	private static Logger log = LoggerFactory.getLogger(HttpClient.class);
	private String ip;
	private int port;
	/**
	 * 
	 * @param ip
	 * @param port
	 */
	public HttpClient(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	public String interactive(String sendmsg, String Url) throws IOException {
		String charset = Charset.defaultCharset().name();
		byte[] bb = sendmsg.getBytes(charset);
		// 请求地址
		URL url = new URL("http://" + ip + ":" + port+"/"+Url);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setConnectTimeout(30000);// 设置超时的时间
		conn.setDoInput(true);
		conn.setDoOutput(true);// 如果通过post提交数据，必须设置允许对外输出数据
		conn.setRequestProperty("Content-Type", "text/xml; charset=" + charset);
		conn.setRequestProperty("Content-Length", String.valueOf(bb.length));
		conn.connect();
		DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		out.write(bb); // 写入请求的字符串
		out.flush();
		out.close();
		// 请求返回的状态
		String a = null;
		if (conn.getResponseCode() == 200) {
			// 请求返回的数据
			InputStream in = conn.getInputStream();
			try {
				byte[] data1 = new byte[in.available()];
				in.read(data1);
				// 转成字符串
				a=new String(data1, charset);
			} catch (Exception e) {
				log.error("请求返回的数据",e);
			}
		} else {
			a= conn.getResponseMessage();
		}
		conn.disconnect();
		return a;
	}
}
