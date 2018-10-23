/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.unit
 * @文件名：WsClient.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:50:54
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
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
 * @项目名称：ytec-mdm-ecif
 * @类名称：WsClient
 * @类描述：WEB SERVICE 客户端
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:50:55
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:50:55
 * @修改备注：
 * @修改日期 修改人员 修改原因 -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
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
			// requestFile="D:/公司SVN/产品/ytececif-doc/01-过程库/06-测试/02-集成测试/02-测试用例/交易服务/测试请求报文/复杂业务流程_01.xml";
			// requestFile="D:/公司SVN/产品/ytececif-doc/01-过程库/06-测试/02-集成测试/02-测试用例/交易服务/测试请求报文/联机批量结果查询_01.xml";
			// requestFile="D:/公司SVN/产品/ytececif-doc/01-过程库/06-测试/02-集成测试/02-测试用例/交易服务/测试请求报文/修改客户信息类交易_01.xml";
			// requestFile="D:/公司SVN/产品/ytececif-doc/01-过程库/06-测试/02-集成测试/02-测试用例/交易服务/测试请求报文/客户合并_01.xml";
			// requestFile="D:/公司SVN/产品/ytececif-doc/01-过程库/06-测试/01-单元测试/交易/batch.xml";
			// requestFile="D:/公司SVN/产品/ytececif-doc/01-过程库/06-测试/02-集成测试/02-测试用例/交易服务/测试请求报文/多表子查询交易_05.xml";
			requestFile = "D:/公司SVN/产品/ytececif-doc/01-过程库/06-测试/02-集成测试/02-测试用例/交易服务/测试请求报文/个人开客户户交易_01.xml";
			// requestFile="D:/公司SVN/产品/ytececif-doc/01-过程库/06-测试/02-集成测试/02-测试用例/交易服务/测试请求报文/外部报文.xml";
			// requestFile="D:/公司SVN/产品/ytececif-doc/01-过程库/06-测试/02-集成测试/02-测试用例/交易服务/测试请求报文/个人开客户户交易_02.xml";
			serverIp = "192.168.1.97";
			serverPort = "9000";
		} else {
			System.out.println("Usage :服务器IP 服务器端口    请求报文");
			return;
		}
		if (StringUtil.isEmpty(serverIp) || StringUtil.isEmpty(serverPort)
				|| StringUtil.isEmpty(requestFile)) {
			System.out.println("Usage :服务器IP 服务器端口    请求报文");
			return;
		}

		Map map = new HashMap();
		map.put("url", "http://"+serverIp+":"+serverPort+"/txDealWebService");

		File file = new File(requestFile);
		if (!file.exists()) {
			System.out.println("文件不存在");
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
