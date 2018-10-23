/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.unit
 * @�ļ�����WsClient.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:50:54
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.unit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.interfaces.common.ClientResponse;
import com.ytec.mdm.interfaces.common.IClient;
import com.ytec.mdm.interfaces.ws.client.WsClient;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�WsClient
 * @��������WEB SERVICE �ͻ���
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:50:55
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:50:55
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ�� -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
@Component
public class TestWsClient {

	public static void main(String args[]) {
		String requestFile = null;
		String serverIp = null;
		String serverPort = null;
		if (args.length == 3) {
			serverIp = args[0];
			serverPort = args[1];
			requestFile = args[2];
		} else if (args.length == 0) {
			// requestFile="D:/��˾SVN/��Ʒ/ytececif-doc/01-���̿�/06-����/02-���ɲ���/02-��������/���׷���/����������/����ҵ������_01.xml";
			// requestFile="D:/��˾SVN/��Ʒ/ytececif-doc/01-���̿�/06-����/02-���ɲ���/02-��������/���׷���/����������/�������������ѯ_01.xml";
			// requestFile="D:/��˾SVN/��Ʒ/ytececif-doc/01-���̿�/06-����/02-���ɲ���/02-��������/���׷���/����������/�޸Ŀͻ���Ϣ�ཻ��_01.xml";
			// requestFile="D:/��˾SVN/��Ʒ/ytececif-doc/01-���̿�/06-����/02-���ɲ���/02-��������/���׷���/����������/�ͻ��ϲ�_01.xml";
			// requestFile="D:/��˾SVN/��Ʒ/ytececif-doc/01-���̿�/06-����/01-��Ԫ����/����/batch.xml";
			// requestFile="D:/��˾SVN/��Ʒ/ytececif-doc/01-���̿�/06-����/02-���ɲ���/02-��������/���׷���/����������/����Ӳ�ѯ����_05.xml";
			requestFile = "D:/��˾SVN/��Ʒ/ytececif-doc/01-���̿�/06-����/02-���ɲ���/02-��������/���׷���/����������/���˿��ͻ�������_01.xml";
			// requestFile="D:/��˾SVN/��Ʒ/ytececif-doc/01-���̿�/06-����/02-���ɲ���/02-��������/���׷���/����������/�ⲿ����.xml";
			// requestFile="D:/��˾SVN/��Ʒ/ytececif-doc/01-���̿�/06-����/02-���ɲ���/02-��������/���׷���/����������/���˿��ͻ�������_02.xml";
			serverIp = "192.168.1.97";
			serverPort = "9000";
		} else {
			System.out.println("Usage :������IP �������˿�    ������");
			return;
		}
		if (StringUtil.isEmpty(serverIp) || StringUtil.isEmpty(serverPort)
				|| StringUtil.isEmpty(requestFile)) {
			System.out.println("Usage :������IP �������˿�    ������");
			return;
		}

		Map map = new HashMap();
		map.put("url", "http://"+serverIp+":"+serverPort+"/txDealWebService");

		File file = new File(requestFile);
		if (!file.exists()) {
			System.out.println("�ļ�������");
			return;
		}
		StringBuffer sb = new StringBuffer();
		BufferedReader reader = null;
		try {
			String fileCharSet = "GBK";
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), fileCharSet));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				sb.append(tempString);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		IClient cc = new WsClient();
		try {
			cc.init(map);
			ClientResponse r = cc.sendMsg(sb.toString());
			System.out.println(r.getResponseMsg());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
