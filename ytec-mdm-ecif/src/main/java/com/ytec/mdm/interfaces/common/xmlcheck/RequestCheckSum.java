/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.common.xmlcheck
 * @文件名：RequestCheckSum.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:34:01
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.interfaces.common.xmlcheck;

import com.ytec.mdm.base.util.crypt.EncryptUtils;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：RequestCheckSum
 * @类描述：请求报文一致性校验
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:34:02   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:34:02
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class RequestCheckSum {
	/****
	 * 请求报文的一致性检查
	 * rCheckSum:校验信息
	 * xml:请求体
	 * @return boolean 
	 */
	public static boolean CheckRequestSum(String rCheckSum,String xml){
		if(rCheckSum==null|| rCheckSum.isEmpty() ||xml==null ||xml.isEmpty()){
			return true;
		}else{
			if(rCheckSum.equals(EncryptUtils.encrypt(xml,"MD5")))
				return true;
			else
				return false;
		}
	}
	
	/****
	 * 响应报文的一致性校验值
	 * xml:响应体
	 * @return String 校验信息
	 */
	public static String  CheckReponseSum(String xml){
		if(xml==null|| xml.isEmpty()){
			return "";
		}else{
			/***
			 * 获取MD5的加密工具
			 * 进行MD5加密
			 */
			return EncryptUtils.encrypt(xml,"MD5");
		}
	}

}
