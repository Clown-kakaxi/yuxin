/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.plugins.synchelper
 * @文件名：SyncXmlHelper.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-下午3:36:14
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.plugins.synchelper;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.util.StringUtil;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：SyncXmlHelper
 * @类描述：ECIF报文帮助类
 * @功能描述:对不同的接入报文，为接口提供报文自动解析提取信息，
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 下午3:36:14   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 下午3:36:14
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class SyncXmlHelper {
	private final String defautCfg = "syncXmlConfig.xml";
	private static SyncXmlHelper syncXmlHelper=new SyncXmlHelper();
	private Logger log = LoggerFactory.getLogger(SyncXmlHelper.class);
	/**
	 * @属性名称:funcMap
	 * @属性描述:同步请求报文设置操作函数
	 * @since 1.0.0
	 */
	private Map<String,ISyncXmlFun> funcMap=new HashMap<String,ISyncXmlFun>();
	/**
	 * @属性名称:responseXmlTemp
	 * @属性描述:同步请求报文模板
	 * @since 1.0.0
	 */
	private Map<String,String> syncXmlTempMap = new HashMap<String,String>();
	

	/**
	 *@构造函数 
	 */
	public SyncXmlHelper() {
		// TODO Auto-generated constructor stub
	}
	
	public static SyncXmlHelper getInstance(){
		return syncXmlHelper;
	}
	private void clearXmlFun(){
		this.funcMap.clear();
	}
	public void addXmlFun(String name,ISyncXmlFun fun){
		this.funcMap.put(name, fun);
	}
	
	public void addResponseXmlTemp(String syncXmlTempName,String responseXmlTemp) {
		syncXmlTempMap.put(syncXmlTempName, responseXmlTemp);
	}
	
	public void clear(){
		clearXmlFun();
		clearSyncXmlTempMap();
	}
	public void clearSyncXmlTempMap(){
		syncXmlTempMap.clear();
	}

	public ISyncXmlFun getResponseXmlFun(String name){
		return funcMap.get(name);
	}

	/**
	 * @函数名称:parseSyncXml
	 * @函数描述:解析转换同步请求报文
	 * @参数与返回说明:
	 * 		@param ecifData
	 * 		@param syncXmlTempName
	 * 		@return
	 * 		@throws Exception
	 * @算法描述:
	 */
	public Document parseSyncXml(EcifData ecifData,String syncXmlTempName) throws Exception{
		String syncXmlTemp= syncXmlTempMap.get(syncXmlTempName);
		if(syncXmlTemp==null){
			log.error("同步接口模板为空",syncXmlTempName);
			return null;
		}
		Document syncXmlDoc = DocumentHelper.parseText(syncXmlTemp);
		SyncXmlVisitor syncXmlVisitor=new SyncXmlVisitor(ecifData);
		syncXmlVisitor.differentialEntity();
		syncXmlDoc.accept(syncXmlVisitor);
		List<SyncXmlObject> syncXmlObjectList=syncXmlVisitor.getSyncXmlObjectList();
		for(SyncXmlObject syncXmlObject:syncXmlObjectList){
			ISyncXmlFun ff=getResponseXmlFun(syncXmlObject.getiSyncXmlFunName());
			if(ff==null){
				log.error("同步报文处理函数{}不存在",syncXmlObject.getiSyncXmlFunName());
			}else{
				ff.getValueByFun(new Object[]{syncXmlDoc,syncXmlObject});
			}
		}
		return syncXmlDoc;
	}
	
	/**
	 * @函数名称:init
	 * @函数描述:初始化
	 * @参数与返回说明:
	 * 		@throws Exception
	 * @算法描述:
	 */
	public void init() throws Exception{
		Document syncXmlCfg = null;
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
					syncXmlCfg=saxReader.read(is);
				} catch (DocumentException e) {
					log.error("解析同步接口模板配置文件失败:", e);
					throw new Exception("解析同步接口模板配置文件失败");
				}
			}else{
				log.error("配置文件{}{}不存在", cfgPath, defautCfg);
				throw new Exception("配置文件不存在");
			}
		}else{
			try {
				syncXmlCfg = saxReader.read(cfgFile);
			} catch (DocumentException e) {
				log.error("解析同步接口模板配置文件失败:", e);
				throw new Exception("解析同步接口模板配置文件失败");
			}
		}
		syncXmlHelper.clear();
		/**操作函数*/
		List<Element> syncFunList =syncXmlCfg.selectNodes("//messageAnalysis/syncFuns/syncFun");
		if (syncFunList != null && !syncFunList.isEmpty()) {
			for (Element syncFun : syncFunList) {
				String name = syncFun.attributeValue("name");
				String funClass = syncFun.attributeValue("class");
				if(!StringUtil.isEmpty(name)&&!StringUtil.isEmpty(funClass)){
					syncXmlHelper.addXmlFun(name, (ISyncXmlFun)Class.forName(funClass).newInstance());
				}else{
					log.warn("配置文件中syncFun有空配置");
				}
			}
		}
		
		List<Element> messageTempletList =syncXmlCfg.selectNodes("//messageAnalysis/messageTemplet");
		if(messageTempletList!=null&&!messageTempletList.isEmpty()){
			for (Element messageTemplet : messageTempletList) {
				String syncXmlTempName=messageTemplet.attributeValue("name");
				Element syncXmlEle=messageTemplet.element("syncXml");
				if(syncXmlEle!=null){
						String syncXml=syncXmlEle.getTextTrim();
						File syncXmlFile=new File(cfgPath+syncXml);
						if (!syncXmlFile.exists()) {
							InputStream is=Thread.currentThread().getContextClassLoader().getResourceAsStream(syncXml);
							if(is!=null){
								try {
									syncXmlHelper.addResponseXmlTemp(syncXmlTempName,saxReader.read(is).asXML());
								} catch (DocumentException e) {
									log.error("解析同步接口模板失败:", e);
									throw new Exception("解析同步接口模板失败");
								}
							}else{
								log.error("同步接口模板文件{}/{}不存在", cfgPath, syncXml);
								throw new Exception("同步接口模板不存在");
							}
						}else{
							try {
								syncXmlHelper.addResponseXmlTemp(syncXmlTempName,saxReader.read(syncXmlFile).asXML());
							} catch (DocumentException e) {
								log.error("解析同步接口模板失败:", e);
								throw new Exception("解析同步接口模板失败");
							}
						}
					}else{
						log.warn("配置文件中syncXml中没有配置模板");
					}
				}
		}
		syncXmlCfg = null;
	}
	
	
	public static void main(String[] arg) throws Exception{
//		SyncXmlHelper test=SyncXmlHelper.getInstance();
//		test.init();
//		EcifData ecifData=new EcifData();
//		MCiOrgIdentifier oo=new MCiOrgIdentifier();
//		oo.setIdentNo("12343ewdfsrw3");
//		oo.setCustId(234342323L);
//		List as=new LinkedList();
//		SynchroEntityHandler ss=new SynchroEntityHandler();
//		ss.setOpType("A");
//		ss.copyEntity(oo, null);
//		as.add(ss);
//		ecifData.setDataSynchro(as);
//		SAXReader saxReader = new SAXReader();
//		InputStream is=Thread.currentThread().getContextClassLoader().getResourceAsStream("test.xml");
//		ecifData.setRepNode((Element)saxReader.read(is).getRootElement());
//		String dsd=test.parseSyncXml(ecifData, "temp1").asXML();
//		System.out.println(dsd);
		
	}
	
}
