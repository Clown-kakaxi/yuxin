/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.plugins.xmlhelper
 * @文件名：XmlHelperConfiger.java
 * @版本信息：1.0.0
 * @日期：2013-12-18-下午3:09:56
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
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
 * @项目名称：ytec-mdm-ecif 
 * @类名称：XmlHelperConfiger
 * @类描述：XML帮助工具配置类
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-18 下午3:09:56   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-18 下午3:09:56
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class XmlHelperConfiger {
	private Logger log = LoggerFactory.getLogger(XmlHelperConfiger.class);
	private final String defautCfg = "ecifXmlConfig.xml";

	/**
	 *@构造函数 
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
					log.error("解析配置文件失败:", e);
					throw new Exception("解析配置文件失败");
				}
			}else{
				log.error("配置文件{}/{}不存在", cfgPath, defautCfg);
				throw new Exception("配置文件不存在");
			}
		}else{
			try {
				ecifXmlCfg = saxReader.read(cfgFile);
			} catch (DocumentException e) {
				log.error("解析配置文件失败:", e);
				throw new Exception("解析配置文件失败");
			}
		}
		EcifXmlHelper ecifXmlHelper=EcifXmlHelper.getInstance();
		ecifXmlHelper.clear();
		/**操作函数*/
		List<Element> responseFunList =ecifXmlCfg.selectNodes("//messageAnalysis/responseFuns/responseFun");
		if (responseFunList != null && !responseFunList.isEmpty()) {
			for (Element responseFun : responseFunList) {
				String name = responseFun.attributeValue("name");
				String funClass = responseFun.attributeValue("class");
				if(!StringUtil.isEmpty(name)&&!StringUtil.isEmpty(funClass)){
					ecifXmlHelper.addXmlFun(name, (IResponseXmlFun)Class.forName(funClass).newInstance());
				}else{
					log.warn("配置文件中responseFun有空配置");
				}
			}
		}
		
		List<Element> messageTempletList =ecifXmlCfg.selectNodes("//messageAnalysis/messageTemplet");
		if(messageTempletList!=null&&!messageTempletList.isEmpty()){
			for (Element messageTemplet : messageTempletList) {
				/**请求报文**/
				Element requestTemplet=messageTemplet.element("requestTemplet");
				if(requestTemplet==null){
					log.warn("配置文件中requestTemplet有空配置");
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
								log.warn("配置文件中messageAnalysis/messageTemplet/property有空配置");
								continue;
							}
						}
					}
				}
				
				/**响应报文**/
				Element responseTemplet=messageTemplet.element("responseTemplet");
				if(responseTemplet!=null){
					Element point=null;
					point=responseTemplet.element("responseXml");/**响应模板**/
					if(point!=null){
						String responseXml=point.getTextTrim();
						File responseXmlFile=new File(cfgPath+responseXml);
						if (!responseXmlFile.exists()) {
							InputStream is=Thread.currentThread().getContextClassLoader().getResourceAsStream(responseXml);
							if(is!=null){
								try {
									ecifXmlHelper.setResponseXmlTemp(saxReader.read(is).asXML());
								} catch (DocumentException e) {
									log.error("解析响应模板失败:", e);
									throw new Exception("解析响应模板失败");
								}
							}else{
								log.error("响应模板文件{}/{}不存在", cfgPath, responseXml);
								throw new Exception("响应模板不存在");
							}
						}else{
							try {
								ecifXmlHelper.setResponseXmlTemp(saxReader.read(responseXmlFile).asXML());
							} catch (DocumentException e) {
								log.error("响应模板文件失败:", e);
								throw new Exception("响应模板文件失败");
							}
						}
						
						
						point=responseTemplet.element("responseHead");/**响应头路径**/
						if(point==null){
							throw new Exception("响应头路径配置错误");
						}
						ecifXmlHelper.setResponseHead(point.getTextTrim());
						point=responseTemplet.element("responseBodyParent");/**响应体父节点**/
						if(point==null){
							throw new Exception("*响应体父节点配置错误");
						}
						ecifXmlHelper.setResponseBodyParent(point.getTextTrim());
						point=responseTemplet.element("responseStatus");/**响应返回码结点**/
						if(point==null){
							throw new Exception("*响应返回码结点配置错误");
						}
						ecifXmlHelper.setResponseStatus(point.getTextTrim());
						
						point=responseTemplet.element("responseBodyName");/**响应体名称**/
						if(point==null){
							throw new Exception("*响应体名称配置错误");
						}
						ecifXmlHelper.setResponseBodyName(point.getTextTrim());
					}else{
						log.warn("配置文件中responseTemplet中没有配置响应模板");
					}
				}else{
					log.warn("配置文件中responseTemplet有空配置");
					continue;
				}
			}
		}
		ecifXmlCfg = null;
		ecifXmlHelper=null;
	}

	/**
	 * @函数名称:main
	 * @函数描述:
	 * @参数与返回说明:
	 * 		@param args
	 * @throws Exception 
	 * @算法描述:
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		XmlHelperConfiger test =new XmlHelperConfiger();
		test.initXmlHelper();

	}

}
