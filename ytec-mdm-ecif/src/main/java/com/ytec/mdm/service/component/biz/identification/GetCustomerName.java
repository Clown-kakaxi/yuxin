/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.service.component.biz.identification
 * @文件名：GetCustomerName.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:58:15
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.service.component.biz.identification;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.SpringContextUtils;

/**
 * @项目名称：ytec-mdm-ecif
 * @类名称：GetCustomerName
 * @类描述：获取客户的户名
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:58:16
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:58:16
 * @修改备注：
 * @修改日期 修改人员 修改原因
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
@Service
@SuppressWarnings("rawtypes")
public class GetCustomerName {
	private JPABaseDAO baseDAO;

	/**
	 * @param custId 客户标识
	 * @return String 户名
	 */
	public String bizGetCustName(Object custId) {
		String custName = null;
		if (custId != null) {
			baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
			/**
			 * FBECIF将NameTitle表删除
			 * List result = baseDAO.findByNativeSQLWithIndexParam("SELECT CUST_NAME FROM M_CI_NAMETITLE WHERE CUST_ID=?",custId);
			 */
			List result = baseDAO.findByNativeSQLWithIndexParam("SELECT CUST_NAME FROM M_CI_CUSTOMER WHERE CUST_ID=?",
					custId);
			// 成功返回客户标识，失败返回数据不存在
			if (result != null && !result.isEmpty()) {
				custName = result.get(0) == null ? null : result.get(0).toString();
			}
		}
		// 返回户名
		return custName;
	}
}
