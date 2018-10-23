/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.plugins.synchelper
 * @�ļ�����SyncXmlHelper.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-����3:36:14
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
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
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�SyncXmlHelper
 * @��������ECIF���İ�����
 * @��������:�Բ�ͬ�Ľ��뱨�ģ�Ϊ�ӿ��ṩ�����Զ�������ȡ��Ϣ��
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����3:36:14   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����3:36:14
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class SyncXmlHelper {
	private final String defautCfg = "syncXmlConfig.xml";
	private static SyncXmlHelper syncXmlHelper=new SyncXmlHelper();
	private Logger log = LoggerFactory.getLogger(SyncXmlHelper.class);
	/**
	 * @��������:funcMap
	 * @��������:ͬ�����������ò�������
	 * @since 1.0.0
	 */
	private Map<String,ISyncXmlFun> funcMap=new HashMap<String,ISyncXmlFun>();
	/**
	 * @��������:responseXmlTemp
	 * @��������:ͬ��������ģ��
	 * @since 1.0.0
	 */
	private Map<String,String> syncXmlTempMap = new HashMap<String,String>();
	

	/**
	 *@���캯�� 
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
	 * @��������:parseSyncXml
	 * @��������:����ת��ͬ��������
	 * @�����뷵��˵��:
	 * 		@param ecifData
	 * 		@param syncXmlTempName
	 * 		@return
	 * 		@throws Exception
	 * @�㷨����:
	 */
	public Document parseSyncXml(EcifData ecifData,String syncXmlTempName) throws Exception{
		String syncXmlTemp= syncXmlTempMap.get(syncXmlTempName);
		if(syncXmlTemp==null){
			log.error("ͬ���ӿ�ģ��Ϊ��",syncXmlTempName);
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
				log.error("ͬ�����Ĵ�����{}������",syncXmlObject.getiSyncXmlFunName());
			}else{
				ff.getValueByFun(new Object[]{syncXmlDoc,syncXmlObject});
			}
		}
		return syncXmlDoc;
	}
	
	/**
	 * @��������:init
	 * @��������:��ʼ��
	 * @�����뷵��˵��:
	 * 		@throws Exception
	 * @�㷨����:
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
					log.error("����ͬ���ӿ�ģ�������ļ�ʧ��:", e);
					throw new Exception("����ͬ���ӿ�ģ�������ļ�ʧ��");
				}
			}else{
				log.error("�����ļ�{}{}������", cfgPath, defautCfg);
				throw new Exception("�����ļ�������");
			}
		}else{
			try {
				syncXmlCfg = saxReader.read(cfgFile);
			} catch (DocumentException e) {
				log.error("����ͬ���ӿ�ģ�������ļ�ʧ��:", e);
				throw new Exception("����ͬ���ӿ�ģ�������ļ�ʧ��");
			}
		}
		syncXmlHelper.clear();
		/**��������*/
		List<Element> syncFunList =syncXmlCfg.selectNodes("//messageAnalysis/syncFuns/syncFun");
		if (syncFunList != null && !syncFunList.isEmpty()) {
			for (Element syncFun : syncFunList) {
				String name = syncFun.attributeValue("name");
				String funClass = syncFun.attributeValue("class");
				if(!StringUtil.isEmpty(name)&&!StringUtil.isEmpty(funClass)){
					syncXmlHelper.addXmlFun(name, (ISyncXmlFun)Class.forName(funClass).newInstance());
				}else{
					log.warn("�����ļ���syncFun�п�����");
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
									log.error("����ͬ���ӿ�ģ��ʧ��:", e);
									throw new Exception("����ͬ���ӿ�ģ��ʧ��");
								}
							}else{
								log.error("ͬ���ӿ�ģ���ļ�{}/{}������", cfgPath, syncXml);
								throw new Exception("ͬ���ӿ�ģ�岻����");
							}
						}else{
							try {
								syncXmlHelper.addResponseXmlTemp(syncXmlTempName,saxReader.read(syncXmlFile).asXML());
							} catch (DocumentException e) {
								log.error("����ͬ���ӿ�ģ��ʧ��:", e);
								throw new Exception("����ͬ���ӿ�ģ��ʧ��");
							}
						}
					}else{
						log.warn("�����ļ���syncXml��û������ģ��");
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
