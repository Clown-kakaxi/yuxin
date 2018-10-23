/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.common.sensitinfo
 * @文件名：SensitInforXmlFilter.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-10:27:17
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.interfaces.common.sensitinfo;

import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：SensitInforXmlFilter
 * @类描述：敏感信息过滤
 * @功能描述:基于XML的敏感信息过滤
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午10:27:36   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午10:27:36
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class SensitInforXmlFilter extends AbsSensitiveFilter implements SensitiveFilter {
	/**
	 * The log.
	 * 
	 * @属性描述:日志
	 */
	private Logger log = LoggerFactory
			.getLogger(SensitInforXmlFilter.class);
	
	/**
	 * @函数名称:doInforFilter
	 * @函数描述:敏感信息过滤
	 * @参数与返回说明:
	 * 		@param xmlStr  信息
	 * 		@param sensitInforSet 敏感信息定义
	 * 		@return
	 * @算法描述:
	 */
	public String doInforFilter(String xmlStr,Set sensitInforSet) {
		try {
			if(sensitInforSet==null||sensitInforSet.isEmpty()){
				sensitInforSet=sensitInforMap;
			}else{
				sensitInforSet.addAll(sensitInforMap);
			}
			return parser(xmlStr.getBytes(),sensitInforSet);
		} catch (Exception e) {
			log.error("客户敏感信息过滤失败", e);
		}
		return xmlStr;
	}
	
	private String parser(byte[] bytes,Set sensitInforSet){
		int state;   //0:不读数据,1:标签 ,2数据    
		byte[] bb=new byte[512];
		int length=bytes.length;
		boolean skip=false;
		state=0;
		byte b;
		byte c;
		int j;
		int blength=0;
		for(int index=0;index<length;index++){
			b=bytes[index];
			switch (b) {
				case '<':
					j=index;
					c=bytes[j+1];
					if(c=='/'){//结束标签
//						if(state==2&& blength!=0 && skip){
//							
//						}
						state=0;
					}else if(c=='!'){//注释
						if(bytes[j+2]=='-'&&bytes[j+3]=='-'){
							state=0;
						}else{
							state=1;
						}
					}else if(c=='?'){//协议头
						if(bytes[j+2]=='x'&&bytes[j+3]=='m'&&bytes[j+4]=='l'){
							state=0;
						}else{
							state=1;
						}
					}else{//标签开始
						state=1;
					}
					blength=0;
					break;
				case '>': 
					j=index;
					c=bytes[j-1];
					if(c=='/'){//起始标签结束,无信息
						state=0;
					}else if(c=='?'){//   />
						state=0;
					}else if(c=='-'){//   ->
						if(bytes[j-2]=='-'){//注释结束
							state=0;
						}else{
							if(state==1){
								String nodeName=new String(bb,0,blength);
								if(sensitInforSet.contains(nodeName)){
									skip=true;
								}else{
									skip=false;
								}
								state=2;
							}else{
								state=0;
							}
						}
					}else{ //>
						if(state==1){
							String nodeName=new String(bb,0,blength);
							if(sensitInforSet.contains(nodeName)){
								skip=true;
							}else{
								skip=false;
							}
							state=2;
						}else{
							state=0;
						}
					}
					blength=0;
					break;
				default:
					if(state==1){
						bb[blength++]=b;
					}else if(state==2 && skip&&!Character.isWhitespace((int)bytes[index])){
						bytes[index]='*';
					}
			}
		}
		return new String(bytes);
	}

	/* (non-Javadoc)
	 * @see com.ytec.mdm.interfaces.common.sensitinfo.SensitiveFilter#init(java.util.Map)
	 */
	@Override
	public void init(Map arg) throws Exception {
		// TODO Auto-generated method stub
		super.initXmlSensitive(arg);
	}

	/* (non-Javadoc)
	 * @see com.ytec.mdm.interfaces.common.sensitinfo.SensitiveFilter#isSensitiveInfo(java.lang.String)
	 */
	@Override
	public boolean isSensitiveInfo(String info) {
		// TODO Auto-generated method stub
		return this.sensitInforMap.contains(info);
	}
}
