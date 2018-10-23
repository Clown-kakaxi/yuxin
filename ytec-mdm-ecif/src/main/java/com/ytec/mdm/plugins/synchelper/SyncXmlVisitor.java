/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.plugins.synchelper
 * @文件名：SyncXmlVisitor.java
 * @版本信息：1.0.0
 * @日期：2013-12-18-下午1:39:43
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
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
 * @项目名称：ytec-mdm-ecif 
 * @类名称：SyncXmlVisitor
 * @类描述：同步报文数据设置
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
	 *@构造函数 
	 */
	public SyncXmlVisitor() {
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 *@构造函数 
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
			 	case '#':/**修改交易的原请求报文,或者同步查询交易报文体获取*/
			 		parseText=elementText.substring(2, elementText.length()-1);
			 		if(!StringUtil.isEmpty(parseText)){
			 			opTextObj=getValueFromXmlObject(parseText);
			 			if(opTextObj!=null){
			 				if(opTextObj instanceof String){
			 					element.setText(opTextObj.toString());
			 				}else{
			 					log.warn("同步请求报文模板属性{},值{}定义错误,值转换后为对象",element.getName(),elementText);
			 					element.setText("");
			 				}
			 			}else{
			 				element.setText("");
			 			}
			 		}else{
			 			log.warn("同步请求报文模板属性{},值{}定义错误",element.getName(),elementText);
			 			element.setText("");
			 		}
			 		break;
			 	case '$':/**函数加工*/
			 		parseText=elementText.substring(2, elementText.length()-1);
			 		if(!StringUtil.isEmpty(parseText)){
			 			String funName;
			 			Object[] arg=null; 
			 			if(parseText.endsWith(")")){//带参数
			 				int f=parseText.indexOf('(');
			 				if(f<0){
			 					log.warn("同步请求报文模板属性{},值{}定义错误",element.getName(),elementText);
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
			 			}else{//无参数
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
			 					log.warn("同步请求报文模板属性{},值{}定义函数返回不支持",element.getName(),elementText);
			 					element.setText("");
			 				}
			 			}else{
			 				log.warn("同步请求报文模板属性{},值{}定义函数不存在",element.getName(),elementText);
		 					element.setText("");
			 			}
			 			arg=null; 
			 		}else{
			 			log.warn("同步请求报文模板属性{},值{}定义错误",element.getName(),elementText);
			 			element.setText("");
			 		}
			 		break;
			 	case '@':/**ecifData对象中的属性,请求交易中的对象*/
			 		parseText=elementText.substring(2, elementText.length()-1);
			 		if(!StringUtil.isEmpty(parseText)){
			 			element.setText(getValueFromEcifData(parseText).toString());
			 		}else{
			 			log.warn("同步请求报文模板属性{},值{}定义错误",element.getName(),elementText);
			 			element.setText("");
			 		}
			 		break;
			 	default:
			 		break;
			 }
		}
	}
	
	private Object getValueFromXmlObject(String parseText_){
		if(parseText_.startsWith("entityTxt:")){//同步实体对象的属性值
			parseText_=parseText_.substring(10);
			return getValueFromEntity(parseText_);
		}else if(parseText_.startsWith("entityObj:")){//同步实体对象
			parseText_=parseText_.substring(10);
			return synchroEntityMap.get(parseText_);
		}else if(parseText_.startsWith("selectBody:")){//同步查询体
			parseText_=parseText_.substring(11);
			point=ecifData.getRepNode();
		}else if(parseText_.startsWith("selectBodyObj:")){//同步查询数据对象
			parseText_=parseText_.substring(14);
			point=ecifData.getRepNode();
			return point.selectNodes(parseText_);
		}else if(parseText_.startsWith("head:")){//请求报文头
			parseText_=parseText_.substring(5);
			point=ecifData.getRequestHeader();
		}else if(parseText_.startsWith("body:")){//请求报文体
			parseText_=parseText_.substring(5);
			point=ecifData.getBodyNode();
		}else{
			log.warn("从报文中获取不到{}的属性",parseText_);
			return "";
		}
		Element reqEle=(Element) point.selectSingleNode(parseText_);
		if(reqEle!=null){
			return reqEle.getText();
		}else{
			log.warn("从报文中获取不到{}的属性",parseText_);
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
 			log.warn("同步请求报文模板属性值{}定义错误",parseText_);
 			log.warn("错误信息",e);
 			return "";
 		}
		
	}
	
	private Object getValueFromEntity(String parseText_){
		try{
			String[] argtr=parseText_.split("\\/");
			if(argtr.length!=2){
				log.warn("同步请求报文模板属性值{}定义错误",parseText_);
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
 			log.warn("同步请求报文模板属性值{}定义错误",parseText_);
 			log.warn("错误信息",e);
 			return "";
 		}
		
	}


	public List<SyncXmlObject> getSyncXmlObjectList() {
		return syncXmlObjectList;
	}
	
	/**
	 * @函数名称:differentialEntity
	 * @函数描述:差分比对
	 * @参数与返回说明:
	 * @算法描述:
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
		if (String.class.equals(dataType)||Character.class.equals(dataType)) {// 字符型
			newValue = value.toString();
		}else if(char.class.equals(dataType)){
			newValue=String.valueOf(value);
		}else if(Clob.class.equals(dataType)){
			newValue=StringUtil.readClob((Clob)value);
		}else {// 非字符串
			DecimalFormat decimalFormat = null;
			SimpleDateFormat dateformat = null;
			if (Long.class.equals(dataType)||BigInteger.class.equals(dataType)||Integer.class.equals(dataType)||
					Short.class.equals(dataType)||long.class.equals(dataType)||int.class.equals(dataType)||short.class.equals(dataType)) {// 整型
				newValue = value.toString();
			} else if (Double.class.equals(dataType)||BigDecimal.class.equals(dataType)||Float.class.equals(dataType)||double.class.equals(dataType)||float.class.equals(dataType)) {// 浮点型
				decimalFormat = new DecimalFormat("#.##");
			} else if (Date.class.equals(dataType)) {// 日期
				dateformat = new SimpleDateFormat("yyyy-MM-dd");
			} else if (Time.class.equals(dataType)) {// 时间
				dateformat = new SimpleDateFormat("HH:mm:ss");
			} else if (Timestamp.class.equals(dataType)) {// 时间戳
				dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			} else {
				log.warn("字段数据类型{}不支持",dataType.getName());
			}
			try {
				if (decimalFormat != null) {
					newValue = decimalFormat.format(value);
				} else if (dateformat != null) {
					newValue = dateformat.format(value);
				}
			} catch (Exception e) {
				log.error("格式化失败",e);
				return null;
			}
		}
		return newValue;
	}

}
