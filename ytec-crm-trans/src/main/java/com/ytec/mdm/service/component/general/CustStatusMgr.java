/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.service.component.general
 * @文件名：CustStatusMgr.java
 * @版本信息：1.0.0
 * @日期：2014-6-9-下午2:32:03
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.service.component.general;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.ytec.mdm.base.bo.CustStatus;
import com.ytec.mdm.base.util.StringUtil;

/**
 * @项目名称：ytec-mdm-ecif
 * @类名称：CustStatusMgr
 * @类描述：客户状态判定管理
 * @功能描述:定义客户状态的查插删改的权限，为客户信息操作作判定依据
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-6-9 下午2:32:03
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-6-9 下午2:32:03
 * @修改备注：
 * @修改日期 修改人员 修改原因 -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public class CustStatusMgr {
	/**
	 * @属性名称:custStatusMgr
	 * @属性描述:客户状态判定管理
	 * @since 1.0.0
	 */
	private static CustStatusMgr custStatusMgr = new CustStatusMgr();
	/**
	 * @属性名称:custStatusMap
	 * @属性描述:客户状态对象
	 * @since 1.0.0
	 */
	private Map<String, CustStatus> custStatusMap = new HashMap<String, CustStatus>();

	/**
	 * @构造函数
	 */
	public CustStatusMgr() {
	}

	/**
	 * @函数名称:getInstance
	 * @函数描述:单例获取对象
	 * @参数与返回说明:
	 * @return
	 * @算法描述:
	 */
	public static CustStatusMgr getInstance() {
		return custStatusMgr;
	}

	/**
	 * @函数名称:init
	 * @函数描述:初始化函数
	 * @参数与返回说明:
	 * @param arg
	 * @throws Exception
	 * @算法描述:
	 */
	public void init(Map arg) throws Exception {
		/** 客户状态加载 ***/
		custStatusMap.clear();
		/** 获取客户状态配置 */
		Collection<String> c = arg.values();
		Iterator<String> it = c.iterator();
		while (it.hasNext()) {
			String value_i[] = it.next().split("\\:");
			if (value_i != null && value_i.length == 4) {
				// 装换成对象
				// 客户状态权限
				// custStatus=状态码:描述:是否正常(true正常,false异常):权限(客户重新启用，维护，查询)
				custStatusMap.put(
						value_i[0],
						new CustStatus(value_i[0], value_i[1], Boolean
								.parseBoolean(value_i[2]), Integer
								.parseInt(value_i[3])));
			}
		}
	}

	/***
	 * 客户状态验证
	 * 
	 * @param custStat
	 *            客户状态数据
	 * @return
	 */
	public CustStatus getCustStatus(String custStat) {
		if (StringUtil.isEmpty(custStat) || custStatusMap.isEmpty()) {
			return null;
		}
		CustStatus custStatObj = custStatusMap.get(custStat);
		if (custStatObj == null) {
			custStatObj = new CustStatus(custStat, "异常", false, 0);
		}
		return custStatObj;
	}
}
