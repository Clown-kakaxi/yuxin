package com.ytec.mdm.service.component.biz.identification;

/**
 * @项目名：ytec-mdm-trans
 * @包名：com.ytec.mdm.service.component.biz.identification
 * @文件名：GetObjectByBusinessKey.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:58:43
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 *
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.GetKeyNameUtil;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.ReflectionUtils;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.integration.transaction.bs.TxConfigBS4CRM;
import com.ytec.mdm.server.common.BusinessCfg;
import com.ytec.mdm.service.bo.ObjectReturnMessage;

/**
 * @项目名称：ytec-mdm-trans(CRM)
 * @类名称：GetObjectByBusinessKey
 * @类描述：按业务主键和技术主键查找记录
 * @功能描述:
 * @创建人：wangtb@yuchengtech.com
 *                             -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class GetObjectByBusinessKey {
	private JPABaseDAO baseDAO;
	private static Logger log = LoggerFactory.getLogger(GetObjectByBusinessKey.class);

	public ObjectReturnMessage bizGetObject(Object object, boolean businessKey) {
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");

		ObjectReturnMessage message = new ObjectReturnMessage();

		// 类名
		String simpleName = object.getClass().getSimpleName();
		String keyName = null;
		Object keyValue = null;
		if (!businessKey) {
			// 获得主键名
			keyName = GetKeyNameUtil.getInstance().getKeyName(object);
			if (keyName != null) {
				// 获得主键值
				keyValue = ReflectionUtils.getFieldValue(object, keyName);
			}
		}
		boolean isCheck = false;
		String check[] = TxConfigBS4CRM.getBizKey(simpleName + "BusinessKey");
		if (check != null && check.length != 0) {
			isCheck = true;
		}

		// 如果校验规则为否，主键值为空,返回空值
		if ((keyValue == null || keyValue.equals(0L) || "".equals(keyValue)) && !isCheck) {
			log.info("通过技术主键({}={})查询记录", keyName, keyValue);
			message.setSuccessFlag(false);
			message.setError(ErrorCode.WRN_NONE_FOUND);
			// String msg = String.format("%s: %s(%s)",ErrorCode.WRN_NONE_FOUND.getChDesc(),simpleName);
			String msg = String.format("%s: %s", ErrorCode.WRN_NONE_FOUND.getChDesc(), simpleName);
			log.warn(msg);
			return message;
		}
		// 获得表名
		String tableName = simpleName;
		// 拼装JQL查询语句
		StringBuffer jql = new StringBuffer();
		Map paramMap = new HashMap();

		// 查询表
		jql.append("FROM " + tableName + " a");

		// 查询条件
		jql.append(" WHERE 1=1");

		// 按主键查找
		if (keyValue != null && !keyValue.equals(0L)) {
			jql.append(" AND a." + keyName + " =:" + keyName);
			paramMap.put(keyName, keyValue);
			// TODO
		} else if (isCheck) {// 按校验规则查询
			for (String column : check) {
				jql.append(" AND a." + column + " =:" + column);
				Object invokeRtn = ReflectionUtils.getFieldValue(object, column);
				if (invokeRtn == null || "".equals(invokeRtn)) {
					message.setSuccessFlag(false);
					message.setError(ErrorCode.ERR_ECIF_NOT_EXIST_BUSINESSKEY);
					String msg = String.format("%s: %s(%s)", ErrorCode.ERR_ECIF_NOT_EXIST_BUSINESSKEY.getChDesc(), simpleName, column);
					log.warn(msg);
					return message;
				}
				paramMap.put(column, invokeRtn);
			}
		}
		List result = null;
		result = baseDAO.findWithNameParm(jql.toString(), paramMap);
		if (result != null && result.size() > 0) {
			message.setSuccessFlag(true);
			message.setObject(result.get(0));
		} else {
			if (keyValue != null && !keyValue.equals(0L) && !keyName.equals(MdmConstants.CUSTID)) {
				message.setSuccessFlag(false);
				message.setError(ErrorCode.ERR_ECIF_NOT_EXIST_TECHNICALKEY);
			} else {
				message.setSuccessFlag(false);
				message.setError(ErrorCode.WRN_NONE_FOUND);
			}
		}
		return message;
	}
}
