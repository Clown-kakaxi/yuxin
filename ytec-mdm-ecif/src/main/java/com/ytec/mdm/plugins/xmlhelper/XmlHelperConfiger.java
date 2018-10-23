/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.plugins.xmlhelper
 * @�ļ�����XmlHelperConfiger.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-18-����3:09:56
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.plugins.xmlhelper;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ytec.mdm.base.util.StringUtil;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�XmlHelperConfiger
 * @��������XML��������������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-18 ����3:09:56   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-18 ����3:09:56
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class XmlHelperConfiger {
	private Logger log = LoggerFactory.getLogger(XmlHelperConfiger.class);
	private final String defautCfg = "ecifXmlConfig.xml";

	/**
	 *@���캯�� 
	 */
	public XmlHelperConfiger() {
		// TODO Auto-generated constructor stub
	}
	
	public void initXmlHelper() throws Exception{
		Document ecifXmlCfg = null;
		SAXReader saxReader = new SAXReader();
		String cfgPath=Thread.currentThread().getContextClassLoader().getResource("").getPath();
		if (!cfgPath.endsWith("/")) {
			cfgPath = cfgPath + "/";
		}
		File cfgFile = new File(cfgPath + defautCfg);
		if (!cfgFile.exists()) {
			InputStream is=Thread.currentThread().getContextClassLoader().getResourceAsStream(defautCfg);
			if(is!=null){
				try {
					ecifXmlCfg=saxReader.read(is);
				} catch (DocumentException e) {
					log.error("���������ļ�ʧ��:", e);
					throw new Exception("���������ļ�ʧ��");
				}
			}else{
				log.error("�����ļ�{}/{}������", cfgPath, defautCfg);
				throw new Exception("�����ļ�������");
			}
		}else{
			try {
				ecifXmlCfg = saxReader.read(cfgFile);
			} catch (DocumentException e) {
				log.error("���������ļ�ʧ��:", e);
				throw new Exception("���������ļ�ʧ��");
			}
		}
		EcifXmlHelper ecifXmlHelper=EcifXmlHelper.getInstance();
		ecifXmlHelper.clear();
		/**��������*/
		List<Element> responseFunList =ecifXmlCfg.selectNodes("//messageAnalysis/responseFuns/responseFun");
		if (responseFunList != null && !responseFunList.isEmpty()) {
			for (Element responseFun : responseFunList) {
				String name = responseFun.attributeValue("name");
				String funClass = responseFun.attributeValue("class");
				if(!StringUtil.isEmpty(name)&&!StringUtil.isEmpty(funClass)){
					ecifXmlHelper.addXmlFun(name, (IResponseXmlFun)Class.forName(funClass).newInstance());
				}else{
					log.warn("�����ļ���responseFun�п�����");
				}
			}
		}
		
		List<Element> messageTempletList =ecifXmlCfg.selectNodes("//messageAnalysis/messageTemplet");
		if(messageTempletList!=null&&!messageTempletList.isEmpty()){
			for (Element messageTemplet : messageTempletList) {
				/**������**/
				Element requestTemplet=messageTemplet.element("requestTemplet");
				if(requestTemplet==null){
					log.warn("�����ļ���requestTemplet�п�����");
					continue;
				}else{
					List<Element> propertyList=requestTemplet.elements("property");
					if(propertyList!=null&&!propertyList.isEmpty()){
						for(Element property:propertyList){
							String name=property.attributeValue("name");
							String xpath=property.getTextTrim();
							if(!StringUtil.isEmpty(xpath)&&!StringUtil.isEmpty(name)){
								ecifXmlHelper.addRequestProperty(new RequestProperty(name,xpath));
							}else{
								log.warn("�����ļ���messageAnalysis/messageTemplet/property�п�����");
								continue;
							}
						}
					}
				}
				
				/**��Ӧ����**/
				Element responseTemplet=messageTemplet.element("responseTemplet");
				if(responseTemplet!=null){
					Element point=null;
					point=responseTemplet.element("responseXml");/**��Ӧģ��**/
					if(point!=null){
						String responseXml=point.getTextTrim();
						File responseXmlFile=new File(cfgPath+responseXml);
						if (!responseXmlFile.exists()) {
							InputStream is=Thread.currentThread().getContextClassLoader().getResourceAsStream(responseXml);
							if(is!=null){
								try {
									ecifXmlHelper.setResponseXmlTemp(saxReader.read(is).asXML());
								} catch (DocumentException e) {
									log.error("������Ӧģ��ʧ��:", e);
									throw new Exception("������Ӧģ��ʧ��");
								}
							}else{
								log.error("��Ӧģ���ļ�{}/{}������", cfgPath, responseXml);
								throw new Exception("��Ӧģ�岻����");
							}
						}else{
							try {
								ecifXmlHelper.setResponseXmlTemp(saxReader.read(responseXmlFile).asXML());
							} catch (DocumentException e) {
								log.error("��Ӧģ���ļ�ʧ��:", e);
								throw new Exception("��Ӧģ���ļ�ʧ��");
							}
						}
						
						
						point=responseTemplet.element("responseHead");/**��Ӧͷ·��**/
						if(point==null){
							throw new Exception("��Ӧͷ·�����ô���");
						}
						ecifXmlHelper.setResponseHead(point.getTextTrim());
						point=responseTemplet.element("responseBodyParent");/**��Ӧ�常�ڵ�**/
						if(point==null){
							throw new Exception("*��Ӧ�常�ڵ����ô���");
						}
						ecifXmlHelper.setResponseBodyParent(point.getTextTrim());
						point=responseTemplet.element("responseStatus");/**��Ӧ��������**/
						if(point==null){
							throw new Exception("*��Ӧ�����������ô���");
						}
						ecifXmlHelper.setResponseStatus(point.getTextTrim());
						
						point=responseTemplet.element("responseBodyName");/**��Ӧ������**/
						if(point==null){
							throw new Exception("*��Ӧ���������ô���");
						}
						ecifXmlHelper.setResponseBodyName(point.getTextTrim());
					}else{
						log.warn("�����ļ���responseTemplet��û��������Ӧģ��");
					}
				}else{
					log.warn("�����ļ���responseTemplet�п�����");
					continue;
				}
			}
		}
		ecifXmlCfg = null;
		ecifXmlHelper=null;
	}

	/**
	 * @��������:main
	 * @��������:
	 * @�����뷵��˵��:
	 * 		@param args
	 * @throws Exception 
	 * @�㷨����:
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		XmlHelperConfiger test =new XmlHelperConfiger();
		test.initXmlHelper();

	}

}
