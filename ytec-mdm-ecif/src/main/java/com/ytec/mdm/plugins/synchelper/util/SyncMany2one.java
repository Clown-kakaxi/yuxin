/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.plugins.synchelper.util
 * @文件名：SyncMany2one.java
 * @版本信息：1.0.0
 * @日期：2014-2-24-下午2:37:23
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.plugins.synchelper.util;

import java.util.List;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.plugins.synchelper.ISyncXmlFun;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：SyncMany2one
 * @类描述：多对一映射
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-2-24 下午2:37:23   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-2-24 下午2:37:23
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public class SyncMany2one implements ISyncXmlFun {
	private Logger log = LoggerFactory.getLogger(SyncMany2one.class);
	/**
	 * @函数名称:多对一报文映射转换
	 * @函数描述:将多条信息结点转换成单条信息结点
	 * @参数与返回说明:
	 * 		@param Object[] arg arg[0] List<Element>,  arg[1] 类型结点名称,  arg[2] 类型结点数据,arg[3] 需要的数据结点名称
	 * 		@return Object
	 * @算法描述:
	 */
	@Override
	public Object getValueByFun(Object[] arg) {
		// TODO Auto-generated method stub
		if(arg!=null && arg.length==4){
			List<Element> pointList=(List<Element>)arg[0];
			if(pointList!=null&&!pointList.isEmpty()){
				for(Element point:pointList){
					if(arg[2].toString().equals(point.elementText(arg[1].toString()))){
						return point.elementText(arg[3].toString());
					}
				}
			}
		}else{
			log.warn("多对一映射函数参数错误");
			return "";
		}
		return "";
	}

}
