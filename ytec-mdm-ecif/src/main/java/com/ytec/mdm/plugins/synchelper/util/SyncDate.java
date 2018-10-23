/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.plugins.synchelper.util
 * @文件名：SyncDate.java
 * @版本信息：1.0.0
 * @日期：2013-12-18-下午2:59:17
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.plugins.synchelper.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.plugins.synchelper.ISyncXmlFun;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：SyncDate
 * @类描述：报文日期时间加工
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-18 下午2:59:17   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-18 下午2:59:17
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class SyncDate implements ISyncXmlFun {
	private Logger log = LoggerFactory.getLogger(SyncDate.class);

	/**
	 *@构造函数 
	 */
	public SyncDate() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @函数名称:报文日期时间加工
	 * @函数描述:取当前日期时间，将其格式化为参数中的格式
	 * @参数与返回说明:
	 * 		@param Object[] arg arg[0]格式化字符串
	 * 		@return Object 时间字符串
	 * @算法描述:
	 */
	@Override
	public Object getValueByFun(Object[] arg) {
		// TODO Auto-generated method stub
		if(arg!=null && arg.length==1){
			try{
				SimpleDateFormat df = new SimpleDateFormat(arg[0].toString());
				return df.format(new Date());
			}catch(Exception e){
				log.error("日期时间函数异常",e);
			}
			return "";
		}else{
			log.warn("日期时间函数参数为空");
			return "";
		}
	}

}
