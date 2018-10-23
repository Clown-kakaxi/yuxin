/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.plugins.synchelper
 * @�ļ�����SyncXmlVisitor.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-18-����1:39:43
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.plugins.synchelper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Clob;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
import org.dom4j.VisitorSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.util.ReflectionUtils;
import com.ytec.mdm.base.util.StringUtil;
/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�SyncXmlVisitor
 * @��������ͬ��������������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-18 ����1:39:43   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-18 ����1:39:43
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class SyncXmlVisitor extends VisitorSupport {
	private static Logger log = LoggerFactory.getLogger(SyncXmlVisitor.class);
	private EcifData ecifData;
	private String elementText;
	private char firstChar;
	private String parseText;
	private Element point=null;
	private SyncXmlHelper syncXmlHelper=SyncXmlHelper.getInstance();
	private Object opTextObj=null;
	private List<SyncXmlObject> syncXmlObjectList=new ArrayList<SyncXmlObject>();
	private Map<String,List<SynchroEntity>> synchroEntityMap;

	/**
	 *@���캯�� 
	 */
	public SyncXmlVisitor() {
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 *@���캯�� 
	 * @param ecifData
	 */
	public SyncXmlVisitor(EcifData ecifData) {
		// TODO Auto-generated constructor stub
		this.ecifData=ecifData;
	}
	
	public void visit(Element element) {
		if((elementText=element.getTextTrim())!=null&&!elementText.isEmpty()){
			firstChar=elementText.charAt(0);
			 switch(firstChar){
			 	case '#':/**�޸Ľ��׵�ԭ������,����ͬ����ѯ���ױ������ȡ*/
			 		parseText=elementText.substring(2, elementText.length()-1);
			 		if(!StringUtil.isEmpty(parseText)){
			 			opTextObj=getValueFromXmlObject(parseText);
			 			if(opTextObj!=null){
			 				if(opTextObj instanceof String){
			 					element.setText(opTextObj.toString());
			 				}else{
			 					log.warn("ͬ��������ģ������{},ֵ{}�������,ֵת����Ϊ����",element.getName(),elementText);
			 					element.setText("");
			 				}
			 			}else{
			 				element.setText("");
			 			}
			 		}else{
			 			log.warn("ͬ��������ģ������{},ֵ{}�������",element.getName(),elementText);
			 			element.setText("");
			 		}
			 		break;
			 	case '$':/**�����ӹ�*/
			 		parseText=elementText.substring(2, elementText.length()-1);
			 		if(!StringUtil.isEmpty(parseText)){
			 			String funName;
			 			Object[] arg=null; 
			 			if(parseText.endsWith(")")){//������
			 				int f=parseText.indexOf('(');
			 				if(f<0){
			 					log.warn("ͬ��������ģ������{},ֵ{}�������",element.getName(),elementText);
			 					element.setText("");
			 					break;
			 				}else{
			 					funName=parseText.substring(0, f);
			 					String args=parseText.substring(f+1, parseText.length()-1);
			 					if(!StringUtil.isEmpty(args)){
			 						String[] argtr=args.split("\\,");
			 						arg=new Object[argtr.length];
			 						for(int i=0;i<argtr.length;i++){
			 							if(argtr[i].toString().startsWith("#")){/**�����ĵ�ֵ*/
			 								argtr[i]=argtr[i].toString().substring(2, argtr[i].toString().length()-1);
			 								arg[i]=getValueFromXmlObject(argtr[i].toString());
			 							}else if(argtr[i].toString().startsWith("@")){
			 								argtr[i]=argtr[i].toString().substring(2, argtr[i].toString().length()-1);
			 								arg[i]=getValueFromEcifData(argtr[i].toString());
			 							}else{
			 								if("this".equals(argtr[i])){
			 									arg[i]=element;
			 								}else{
			 									arg[i]=argtr[i];
			 								}
			 							}
			 						}
			 					}
			 				}
			 			}else{//�޲���
			 				funName=parseText;
			 			}
			 			ISyncXmlFun ff=syncXmlHelper.getResponseXmlFun(funName);
			 			if(ff!=null){
			 				opTextObj=ff.getValueByFun(arg);
			 				if(opTextObj instanceof String){
			 					element.setText(opTextObj.toString());
			 				}else if(opTextObj instanceof SyncXmlObject ){
			 					syncXmlObjectList.add((SyncXmlObject)opTextObj);
			 				}else{
			 					log.warn("ͬ��������ģ������{},ֵ{}���庯�����ز�֧��",element.getName(),elementText);
			 					element.setText("");
			 				}
			 			}else{
			 				log.warn("ͬ��������ģ������{},ֵ{}���庯��������",element.getName(),elementText);
		 					element.setText("");
			 			}
			 			arg=null; 
			 		}else{
			 			log.warn("ͬ��������ģ������{},ֵ{}�������",element.getName(),elementText);
			 			element.setText("");
			 		}
			 		break;
			 	case '@':/**ecifData�����е�����,�������еĶ���*/
			 		parseText=elementText.substring(2, elementText.length()-1);
			 		if(!StringUtil.isEmpty(parseText)){
			 			element.setText(getValueFromEcifData(parseText).toString());
			 		}else{
			 			log.warn("ͬ��������ģ������{},ֵ{}�������",element.getName(),elementText);
			 			element.setText("");
			 		}
			 		break;
			 	default:
			 		break;
			 }
		}
	}
	
	private Object getValueFromXmlObject(String parseText_){
		if(parseText_.startsWith("entityTxt:")){//ͬ��ʵ����������ֵ
			parseText_=parseText_.substring(10);
			return getValueFromEntity(parseText_);
		}else if(parseText_.startsWith("entityObj:")){//ͬ��ʵ�����
			parseText_=parseText_.substring(10);
			return synchroEntityMap.get(parseText_);
		}else if(parseText_.startsWith("selectBody:")){//ͬ����ѯ��
			parseText_=parseText_.substring(11);
			point=ecifData.getRepNode();
		}else if(parseText_.startsWith("selectBodyObj:")){//ͬ����ѯ���ݶ���
			parseText_=parseText_.substring(14);
			point=ecifData.getRepNode();
			return point.selectNodes(parseText_);
		}else if(parseText_.startsWith("head:")){//������ͷ
			parseText_=parseText_.substring(5);
			point=ecifData.getRequestHeader();
		}else if(parseText_.startsWith("body:")){//��������
			parseText_=parseText_.substring(5);
			point=ecifData.getBodyNode();
		}else{
			log.warn("�ӱ����л�ȡ����{}������",parseText_);
			return "";
		}
		Element reqEle=(Element) point.selectSingleNode(parseText_);
		if(reqEle!=null){
			return reqEle.getText();
		}else{
			log.warn("�ӱ����л�ȡ����{}������",parseText_);
			return "";
		}
	}
	
	private Object getValueFromEcifData(String parseText_){
		try{
 			Object v=ReflectionUtils.getFieldValue(ecifData, parseText_);
 			if(v!=null){
 				return v;
 			}else{
 				return "";
 			}
 		}catch(Exception e){
 			log.warn("ͬ��������ģ������ֵ{}�������",parseText_);
 			log.warn("������Ϣ",e);
 			return "";
 		}
		
	}
	
	private Object getValueFromEntity(String parseText_){
		try{
			String[] argtr=parseText_.split("\\/");
			if(argtr.length!=2){
				log.warn("ͬ��������ģ������ֵ{}�������",parseText_);
				return "";
			}
			List<SynchroEntity> temp=synchroEntityMap.get(argtr[0]);
 			Object v=ReflectionUtils.getFieldValue(temp.get(0).getIncrEntity(), argtr[1]);
 			if(v!=null){
 				return convertObjectToString(v);
 			}else{
 				return "";
 			}
 		}catch(Exception e){
 			log.warn("ͬ��������ģ������ֵ{}�������",parseText_);
 			log.warn("������Ϣ",e);
 			return "";
 		}
		
	}


	public List<SyncXmlObject> getSyncXmlObjectList() {
		return syncXmlObjectList;
	}
	
	/**
	 * @��������:differentialEntity
	 * @��������:��ֱȶ�
	 * @�����뷵��˵��:
	 * @�㷨����:
	 */
	public void differentialEntity(){
		List<SynchroEntity> synchroEntityList=ecifData.getDataSynchro();
		if(synchroEntityList!=null&&!synchroEntityList.isEmpty()){
			synchroEntityMap=new HashMap<String, List<SynchroEntity>>();
			boolean change;
			List temp;
			for(SynchroEntity synchroEntity:synchroEntityList){
				change=synchroEntity.differentialEntity();
				if(change){
					temp=synchroEntityMap.get(synchroEntity.getEntityName());
					if(temp!=null){
						temp.add(synchroEntity);
					}else{
						temp=new LinkedList();
						temp.add(synchroEntity);
						synchroEntityMap.put(synchroEntity.getEntityName(), temp);
					}
				}
			}
		}
	}
	
	private String convertObjectToString(Object value){
		Class dataType=value.getClass();
		String newValue = null;
		if (String.class.equals(dataType)||Character.class.equals(dataType)) {// �ַ���
			newValue = value.toString();
		}else if(char.class.equals(dataType)){
			newValue=String.valueOf(value);
		}else if(Clob.class.equals(dataType)){
			newValue=StringUtil.readClob((Clob)value);
		}else {// ���ַ���
			DecimalFormat decimalFormat = null;
			SimpleDateFormat dateformat = null;
			if (Long.class.equals(dataType)||BigInteger.class.equals(dataType)||Integer.class.equals(dataType)||
					Short.class.equals(dataType)||long.class.equals(dataType)||int.class.equals(dataType)||short.class.equals(dataType)) {// ����
				newValue = value.toString();
			} else if (Double.class.equals(dataType)||BigDecimal.class.equals(dataType)||Float.class.equals(dataType)||double.class.equals(dataType)||float.class.equals(dataType)) {// ������
				decimalFormat = new DecimalFormat("#.##");
			} else if (Date.class.equals(dataType)) {// ����
				dateformat = new SimpleDateFormat("yyyy-MM-dd");
			} else if (Time.class.equals(dataType)) {// ʱ��
				dateformat = new SimpleDateFormat("HH:mm:ss");
			} else if (Timestamp.class.equals(dataType)) {// ʱ���
				dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			} else {
				log.warn("�ֶ���������{}��֧��",dataType.getName());
			}
			try {
				if (decimalFormat != null) {
					newValue = decimalFormat.format(value);
				} else if (dateformat != null) {
					newValue = dateformat.format(value);
				}
			} catch (Exception e) {
				log.error("��ʽ��ʧ��",e);
				return null;
			}
		}
		return newValue;
	}

}
