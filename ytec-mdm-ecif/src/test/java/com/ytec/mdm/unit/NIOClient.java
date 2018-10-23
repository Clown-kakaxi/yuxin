/**
 * 
 */
package com.ytec.mdm.unit;
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
 * Title:�������������
 * Description: �����ܵ�����
 * </pre>
 * 
 * @author guanyb guanyb@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * �޸ļ�¼
 *    �޸ĺ�汾:     �޸��ˣ�  �޸�����:     �޸�����:
 * </pre>
 */
public class NIOClient {
	private static Logger log = LoggerFactory.getLogger(NIOClient.class);
	private String ip;
	private int port;
	/**
	 * 
	 * @param ip
	 * @param port
	 */
	public NIOClient(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	public String interactive(String sendmsg, String Url) throws IOException {
		String charset = Charset.defaultCharset().name();
		byte[] bb = sendmsg.getBytes(charset);
		// �����ַ
		URL url = new URL("http://" + ip + ":" + port);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setConnectTimeout(30000);// ���ó�ʱ��ʱ��
		conn.setDoInput(true);
		conn.setDoOutput(true);// ���ͨ��post�ύ���ݣ�����������������������
		conn.setRequestProperty("Content-Type", "text/xml; charset=" + charset);
		conn.setRequestProperty("Content-Length", String.valueOf(bb.length));
		conn.connect();
		DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		out.write(bb); // д��������ַ���
		out.flush();
		out.close();
		// ���󷵻ص�״̬
		String a = null;
		if (conn.getResponseCode() == 200) {
			// ���󷵻ص�����
			InputStream in = conn.getInputStream();
			try {
				byte[] data1 = new byte[in.available()];
				in.read(data1);
				// ת���ַ���
				a=new String(data1, charset);
			} catch (Exception e) {
				log.error("���󷵻ص�����",e);
			}
		} else {
			a= conn.getResponseMessage();
		}
		conn.disconnect();
		return a;
	}
}
