/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.unit
 * @�ļ�����MainTest.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-3-5-����2:02:01
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.unit;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.persistence.Parameter;
import javax.persistence.Query;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.collections.MultiHashMap;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import com.ytec.mdm.base.dao.JPAAnnotationMetadataUtil;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.SQLUtils;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.base.util.crypt.EncryptUtils;
import com.ytec.mdm.domain.txp.AppCustMergeRecord;
import com.ytec.mdm.integration.check.bs.ChangeIdCord;
import com.ytec.mdm.integration.transaction.xml.XMLUtils;
import com.ytec.mdm.server.EcifServerLauncher;
import com.ytec.mdm.server.EcifSynchroLauncher;
import com.ytec.mdm.server.EcifTradingLauncher;
import com.ytec.mdm.server.common.AbsServerLauncher;
import com.yuchenglicense.b;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�MainTest
 * @��������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-3-5 ����2:02:01
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-3-5 ����2:02:01
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ�� -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class MainTest {
	public static String replaceAll(String src, String from, String to) {
		StringBuffer sb = new StringBuffer(src);

		int len = from.length();
		int fromIndex = 0;
		int i1;
		while ((i1 = src.indexOf(from, fromIndex)) != -1) {
			int i2 = i1 + len;
			int tail = src.length() - i2;
			sb = sb.replace(i1, i2, to);
			src = new String(sb);
			fromIndex = src.length() - tail;
		}

		return new String(sb);
	}

	public static String replaceAll(String src, String[] from, String[] to) {
		String rt = src;
		int len = Math.min(from.length, to.length);
		for (int i = 0; i < len; i++)
			rt = replaceAll(rt, from[i], to[i]);
		return rt;
	}

	public static String xml2str(String v) {
		v = replaceAll(v, "&lt;", "<");
		v = replaceAll(v, "&gt;", ">");
		v = replaceAll(v, "&apos;", "'");
		v = replaceAll(v, "&quot;", "\"");
		v = replaceAll(v, "&amp;", "&");
		return v;
	}

	public static String str2xml(String v, boolean cdata) {
		if ((cdata)
				&& ((v.indexOf('<') >= 0) || (v.indexOf('>') >= 0) || (v
						.indexOf('&') >= 0)))
			return "<![CDATA[" + v + "]]>";
		if (v.indexOf('&') >= 0)
			v = replaceAll(v, "&", "&amp;");
		if (v.indexOf('<') >= 0)
			v = replaceAll(v, "<", "&lt;");
		if (v.indexOf('>') >= 0)
			v = replaceAll(v, ">", "&gt;");
		return v;
	}

	public static void main(String[] args) throws Exception {
		// String
		// d="license_code123456product_code0000000015product_name_zh-cn12product_name_en-us12version_major2version_sub2version_publish2complie_date2014-03-05license_type4customer_name1contract_code1212121license_start_time2014-03-06license_end_time2015-06-06expend_infow:w";
		// System.out.println(new b().a(d));
		// System.out.println(EncryptUtils.encrypt(d,"MD5"));
//		ScriptEngineManager manager = new ScriptEngineManager();
//		ScriptEngine engine = manager.getEngineByName("javascript");
//		String name="wangzhanyuan";
//		if (engine instanceof Invocable) {
//			try {
//				engine.eval("function reverse(name) {" 
//						+ " var output = '';"
//						+ " for (i = 0; i <= name.length; i++) {"
//						+ " 	output = name.charAt(i) + output"
//						+ " } " 
//						+ " return output;" 
//						+ " }");
//				Invocable invokeEngine = (Invocable) engine;
//				Object o = invokeEngine.invokeFunction("reverse",name);
//				System.out.printf("��ת����ַ�����%s", o);
//			} catch (NoSuchMethodException e) {
//				System.err.println(e);
//			} catch (ScriptException e) {
//				System.err.println(e);
//			}
//		} else {
//			System.err.println("����ű����治֧�ֶ�̬����");
//		}
//		
//		String requestFile="D:/��˾SVN/��Ʒ/ytececif-doc/01-���̿�/06-����/02-���ɲ���/02-��������/���׷���/����������/ǩԼ��Ϣ��ѯ.xml";
//		File file = new File(requestFile);
//		if(!file.exists()){
//			System.out.println("�ļ�������");
//			return ;
//		}
//		 StringBuffer sb=new StringBuffer();
//		 BufferedReader reader = null;
//	        try {
//	        	String fileCharSet="GBK";
//	            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),fileCharSet));
//	            String tempString = null;
//	            while ((tempString = reader.readLine()) != null) {
//	                sb.append(tempString);
//	            }
//	            reader.close();
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	        } finally {
//	            if (reader != null) {
//	                try {
//	                    reader.close();
//	                } catch (IOException e1) {
//	                }
//	            }
//	        }
////	    int m=2;
//	    String str=sb.toString();
//	    SAXReader saxReader = new SAXReader();
//	   Document  doc = saxReader.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("serverConfig.xml"));
//	   System.out.println(doc.asXML());
//	   System.out.println(doc.getXMLEncoding());
		String sql="select * from v x,(select distinct c from cc) a where a.c=x.c ";
		System.out.println(SQLUtils.buildCountSQL(sql));
	}
}
