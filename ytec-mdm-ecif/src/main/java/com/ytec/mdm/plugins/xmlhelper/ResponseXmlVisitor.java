/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.plugins.xmlhelper
 * @文件名：ResponseXmlVisitor.java
 * @版本信息：1.0.0
 * @日期：2013-12-18-下午1:39:43
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
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
 * @项目名称：ytec-mdm-ecif 
 * @类名称：ResponseXmlVisitor
 * @类描述：响应报文数据设置
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-18 下午1:39:43   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-18 下午1:39:43
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
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
	 *@构造函数 
	 */
	public ResponseXmlVisitor() {
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 *@构造函数 
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
			 	case '#':/**请求报文的值*/
			 		parseText=elementText.substring(2, elementText.length()-1);
			 		if(!StringUtil.isEmpty(parseText)){
			 			element.setText(getValueFromReq(parseText));
			 		}else{
			 			log.warn("响应报文模板属性{},值{}定义错误",element.getName(),elementText);
			 			element.setText("");
			 		}
			 		break;
			 	case '$':/**函数*/
			 		parseText=elementText.substring(2, elementText.length()-1);
			 		if(!StringUtil.isEmpty(parseText)){
			 			String funName;
			 			Object[] arg=null; 
			 			if(parseText.endsWith(")")){//带参数
			 				int f=parseText.indexOf('(');
			 				if(f<0){
			 					log.warn("响应报文模板属性{},值{}定义错误",element.getName(),elementText);
			 					element.setText("");
			 					break;
			 				}else{
			 					funName=parseText.substring(0, f);
			 					String args=parseText.substring(f+1, parseText.length()-1);
			 					if(!StringUtil.isEmpty(args)){
			 						String[] argtr=args.split("\\,");
			 						arg=new Object[argtr.length];
			 						for(int i=0;i<argtr.length;i++){
			 							if(argtr[i].toString().startsWith("#")){/**请求报文的值*/
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
			 			}else{//无参数
			 				funName=parseText;
			 			}
			 			IResponseXmlFun ff=ecifXmlHelper.getResponseXmlFun(funName);
			 			if(ff!=null){
			 				element.setText(ff.getValueByFun(arg));
			 			}else{
			 				log.warn("响应报文模板属性{},值{}定义函数不存在",element.getName(),elementText);
		 					element.setText("");
			 			}
			 			arg=null; 
			 		}else{
			 			log.warn("响应报文模板属性{},值{}定义错误",element.getName(),elementText);
			 			element.setText("");
			 		}
			 		break;
			 	case '@':/**ecifData对象中的属性*/
			 		parseText=elementText.substring(2, elementText.length()-1);
			 		if(!StringUtil.isEmpty(parseText)){
			 			element.setText(getValueFromEcifData(parseText).toString());
			 		}else{
			 			log.warn("响应报文模板属性{},值{}定义错误",element.getName(),elementText);
			 			element.setText("");
			 		}
			 		break;
			 	default:
			 		break;
			 }
		}
	}
	
	private String getValueFromReq(String parseText_){
		if(parseText_.startsWith("head:")){//请求报文头
			parseText_=parseText_.substring(5);
			point=ecifData.getRequestHeader();
		}else if(parseText.startsWith("body:")){//请求报文体
			parseText_=parseText_.substring(5);
			point=ecifData.getBodyNode();
		}else{
			point=ecifData.getPrimalDoc().getRootElement();
		}
		Element reqEle=(Element) point.selectSingleNode(parseText_);
		if(reqEle!=null){
			return reqEle.getText();
		}else{
			log.warn("从请求报文中获取不到{}的属性",parseText_);
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
 			log.warn("响应报文模板属性值{}定义错误",parseText_);
 			log.warn("错误信息",e);
 			return "";
 		}
		
	}

}
