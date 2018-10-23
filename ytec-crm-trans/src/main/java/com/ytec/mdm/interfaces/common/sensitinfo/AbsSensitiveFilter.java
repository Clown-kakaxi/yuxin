/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.common.sensitinfo
 * @文件名：AbsSensitiveFilter.java
 * @版本信息：1.0.0
 * @日期：2014-6-26-下午2:17:32
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.interfaces.common.sensitinfo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.NameUtil;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.base.util.StringUtil;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：AbsSensitiveFilter
 * @类描述：
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-6-26 下午2:17:32   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-6-26 下午2:17:32
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public abstract class AbsSensitiveFilter{
	/**
	 * The sensit infor map.
	 * 
	 * @属性描述:基础敏感信息
	 */
	protected Set sensitInforMap=new HashSet();
	
	/**
	 * @函数名称:init
	 * @函数描述:初始化报文敏感信息
	 * @参数与返回说明:
	 * 		@param arg
	 * @算法描述:
	 */
	public void initXmlSensitive(Map arg)  throws Exception{
		sensitInforMap.clear();
		String sensitiveInfors = (String) arg.get("sensitiveXmlInfor");
		if (sensitiveInfors != null) {
			String[] sensitiveInfor = sensitiveInfors.split("\\,");
			JPABaseDAO baseDAO = (JPABaseDAO) SpringContextUtils
					.getBean("baseDAO");
			int length = sensitiveInfor.length;
			String sensit = null;
			int p = 0;
			String sql = "select distinct attr_code from tx_msg_node_attr t where";
			StringBuffer buf = null;
			List<String> result=null;
			Map<String,String> values=new HashMap<String,String>();
			for (int index = 0; index < length; index++) {
				sensit = sensitiveInfor[index];
				if (!StringUtil.isEmpty(sensit)) {
					if (StringUtil.isChinese(sensit)) {
						if (p == 0) {
							buf = new StringBuffer(sql);
							values.clear();
							buf.append(" attr_name like :P_").append(p);
						} else {
							buf.append(" or attr_name like :P_").append(p);
						}
						values.put("P_"+p,"%"+sensit+"%");
						p++;
					}else{
						if(!sensitInforMap.contains(sensit)){
							sensitInforMap.add(sensit);
						}
					}
				}
				
				if(p!=0&&(p==5 || index==length-1)){
					result = baseDAO.findByNativeSQLWithNameParam(buf.toString(),values);
					if(result!=null && result.size()>0){
						for(String sensitCode:result){
							if(!sensitInforMap.contains(sensitCode)){
								sensitInforMap.add(sensitCode);
							}
						}
					}
					p=0;
				}
			}
		}
	}
	
	/**
	 * @函数名称:initDbSensitive
	 * @函数描述:初始化数据库对应的敏感信息字段
	 * @参数与返回说明:
	 * 		@param arg
	 * 		@throws Exception
	 * @算法描述:
	 */
	public void initDbSensitive(Map arg)  throws Exception{
		sensitInforMap.clear();
		String sensitiveInfors = (String) arg.get("sensitiveDbInfor");
		if (sensitiveInfors != null) {
			String[] sensitiveInfor = sensitiveInfors.split("\\,");
			JPABaseDAO baseDAO = (JPABaseDAO) SpringContextUtils
					.getBean("baseDAO");
			int length = sensitiveInfor.length;
			String sensit = null;
			int p = 0;
			String sql = "select distinct COL_NAME from tx_col_def t where";
			StringBuffer buf = null;
			List<String> result=null;
			Map<String,String> values=new HashMap<String,String>();
			for (int index = 0; index < length; index++) {
				sensit = sensitiveInfor[index];
				if (!StringUtil.isEmpty(sensit)) {
					if (StringUtil.isChinese(sensit)) {
						if (p == 0) {
							buf = new StringBuffer(sql);
							values.clear();
							buf.append(" t.col_ch_name like :P_").append(p);
						} else {
							buf.append(" or t.col_ch_name like :P_").append(p);
						}
						values.put("P_"+p,"%"+sensit+"%");
						p++;
					}else{
						if(!sensitInforMap.contains(sensit)){
							sensitInforMap.add(sensit);
						}
					}
				}
				
				if(p!=0&&(p==5 || index==length-1)){
					result = baseDAO.findByNativeSQLWithNameParam(buf.toString(),values);
					if(result!=null && result.size()>0){
						for(String sensitCode:result){
							sensitCode=NameUtil.getColumToJava(sensitCode);
							if(!sensitInforMap.contains(sensitCode)){
								sensitInforMap.add(sensitCode);
							}
						}
					}
					p=0;
				}
			}
		}
	}

}
