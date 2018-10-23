/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.plugins.xmlhelper
 * @�ļ�����ResponseXmlVisitor.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-18-����1:39:43
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.plugins.xmlhelper;

import org.dom4j.Element;
import org.dom4j.VisitorSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.util.ReflectionUtils;
import com.ytec.mdm.base.util.StringUtil;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�ResponseXmlVisitor
 * @����������Ӧ������������
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
public class ResponseXmlVisitor extends VisitorSupport {
	private static Logger log = LoggerFactory.getLogger(ResponseXmlVisitor.class);
	private EcifData ecifData;
	private String elementText;
	private char firstChar;
	private String parseText;
	private Element point=null;
	private EcifXmlHelper ecifXmlHelper=EcifXmlHelper.getInstance();

	/**
	 *@���캯�� 
	 */
	public ResponseXmlVisitor() {
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 *@���캯�� 
	 * @param ecifData
	 */
	public ResponseXmlVisitor(EcifData ecifData) {
		// TODO Auto-generated constructor stub
		this.ecifData=ecifData;
	}
	
	public void visit(Element element) {
		if((elementText=element.getTextTrim())!=null&&!elementText.isEmpty()){
			firstChar=elementText.charAt(0);
			 switch(firstChar){
			 	case '#':/**�����ĵ�ֵ*/
			 		parseText=elementText.substring(2, elementText.length()-1);
			 		if(!StringUtil.isEmpty(parseText)){
			 			element.setText(getValueFromReq(parseText));
			 		}else{
			 			log.warn("��Ӧ����ģ������{},ֵ{}�������",element.getName(),elementText);
			 			element.setText("");
			 		}
			 		break;
			 	case '$':/**����*/
			 		parseText=elementText.substring(2, elementText.length()-1);
			 		if(!StringUtil.isEmpty(parseText)){
			 			String funName;
			 			Object[] arg=null; 
			 			if(parseText.endsWith(")")){//������
			 				int f=parseText.indexOf('(');
			 				if(f<0){
			 					log.warn("��Ӧ����ģ������{},ֵ{}�������",element.getName(),elementText);
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
			 								arg[i]=getValueFromReq(argtr[i].toString());
			 							}else if(argtr[i].toString().startsWith("@")){
			 								argtr[i]=argtr[i].toString().substring(2, argtr[i].toString().length()-1);
			 								arg[i]=getValueFromEcifData(argtr[i].toString());
			 							}else{
			 								arg[i]=argtr[i];
			 							}
			 						}
			 					}
			 				}
			 			}else{//�޲���
			 				funName=parseText;
			 			}
			 			IResponseXmlFun ff=ecifXmlHelper.getResponseXmlFun(funName);
			 			if(ff!=null){
			 				element.setText(ff.getValueByFun(arg));
			 			}else{
			 				log.warn("��Ӧ����ģ������{},ֵ{}���庯��������",element.getName(),elementText);
		 					element.setText("");
			 			}
			 			arg=null; 
			 		}else{
			 			log.warn("��Ӧ����ģ������{},ֵ{}�������",element.getName(),elementText);
			 			element.setText("");
			 		}
			 		break;
			 	case '@':/**ecifData�����е�����*/
			 		parseText=elementText.substring(2, elementText.length()-1);
			 		if(!StringUtil.isEmpty(parseText)){
			 			element.setText(getValueFromEcifData(parseText).toString());
			 		}else{
			 			log.warn("��Ӧ����ģ������{},ֵ{}�������",element.getName(),elementText);
			 			element.setText("");
			 		}
			 		break;
			 	default:
			 		break;
			 }
		}
	}
	
	private String getValueFromReq(String parseText_){
		if(parseText_.startsWith("head:")){//������ͷ
			parseText_=parseText_.substring(5);
			point=ecifData.getRequestHeader();
		}else if(parseText.startsWith("body:")){//��������
			parseText_=parseText_.substring(5);
			point=ecifData.getBodyNode();
		}else{
			point=ecifData.getPrimalDoc().getRootElement();
		}
		Element reqEle=(Element) point.selectSingleNode(parseText_);
		if(reqEle!=null){
			return reqEle.getText();
		}else{
			log.warn("���������л�ȡ����{}������",parseText_);
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
 			log.warn("��Ӧ����ģ������ֵ{}�������",parseText_);
 			log.warn("������Ϣ",e);
 			return "";
 		}
		
	}

}
