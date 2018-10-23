/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.service.component.biz.identification
 * @�ļ�����GetObjectByBusinessKey.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:58:43
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.service.component.biz.identification;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ytec.fubonecif.domain.MCiIdentifier;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.GetKeyNameUtil;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.ReflectionUtils;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.server.common.BusinessCfg;
import com.ytec.mdm.service.bo.ObjectReturnMessage;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�GetObjectByBusinessKey
 * @����������ҵ�������ͼ����������Ҽ�¼
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:58:44
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:58:44
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ��
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 */
@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class GetObjectByBusinessKey {
	private JPABaseDAO baseDAO;
	private static Logger log = LoggerFactory.getLogger(GetObjectByBusinessKey.class);

	public ObjectReturnMessage bizGetObject(Object object, boolean businessKey, boolean isUpdate, String srcSysCd) {
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");

		ObjectReturnMessage message = new ObjectReturnMessage();

		if (isUpdate == true && (MdmConstants.MODEL_ORGIDENTIFIER.equals(object.getClass().getSimpleName()) || MdmConstants.MODEL_ORGIDENTIFIER.equals(object.getClass().getSimpleName()))) {
			MCiIdentifier ident = (MCiIdentifier) object;
			StringBuffer jql = new StringBuffer();
			jql.append("FROM " + object.getClass().getSimpleName() + " I");

			jql.append(" WHERE I.identType=? AND I.identNo=? AND I.identCustName=?");// AND C.custType=?");
			List result = baseDAO.findWithIndexParam(jql.toString(), ident.getIdentType(), ident.getIdentNo(), ident.getIdentCustName());
			if (result != null && result.size()!=0) {
				if (result.size() > 1) {
					message.setSuccessFlag(false);
					message.setError(ErrorCode.ERR_ALL);
					String msg = String.format("���ݴ��󣬴��ڶ���֤����Ϣ������ϵIT��������");
					message.setError(new com.ytec.mdm.base.bo.Error(ErrorCode.ERR_ALL.getCode(), null, msg));
					return message;
				} else {
					MCiIdentifier find =(MCiIdentifier) result.get(0);
					if(!ident.getCustId().equals(find.getCustId())){
						message.setSuccessFlag(false);
						message.setError(ErrorCode.ERR_ALL);
						String msg = String.format("�Ѵ��ڵĿͻ�(%s)֤����Ϣ��������ͻ���(%s)��һ��", find.getCustId(),ident.getCustId());
						message.setError(new com.ytec.mdm.base.bo.Error(ErrorCode.ERR_ALL.getCode(), null, msg));
						return message;
					}
				}
			}
			if (true) {

			}
		}
		// ����
		String simpleName = object.getClass().getSimpleName();
		String keyName = null;
		Object keyValue = null;
		if (!businessKey) {
			// ���������
			keyName = GetKeyNameUtil.getInstance().getKeyName(object);
			if (keyName != null) {
				// �������ֵ
				keyValue = ReflectionUtils.getFieldValue(object, keyName);
			}
		}
		boolean isCheck = false;
		String check[] = BusinessCfg.getStringArray(simpleName + "BusinessKey");
		if (check != null && check.length != 0) {
			isCheck = true;
		}

		// ���У�����Ϊ������ֵΪ��,���ؿ�ֵ
		if ((keyValue == null || keyValue.equals(0L) || "".equals(keyValue)) && !isCheck) {
			log.info("ͨ����������({}={})��ѯ��¼", keyName, keyValue);
			if (isUpdate) {
				try {
					if (srcSysCd != null && !srcSysCd.isEmpty()) {
						StringBuffer jqlStr = new StringBuffer();
						jqlStr.append("FROM " + simpleName + " a");
						jqlStr.append(" WHERE a." + MdmConstants.CUSTID + "=?");
						jqlStr.append(" AND a.lastUpdateSys=?");
						List result = baseDAO.findWithIndexParam(jqlStr.toString(), ReflectionUtils.getFieldValue(object, MdmConstants.CUSTID), srcSysCd);
						if (result != null && result.size() > 0) {
							message.setSuccessFlag(true);
							message.setObject(result.get(0));
							return message;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			message.setSuccessFlag(false);
			message.setError(ErrorCode.WRN_NONE_FOUND);
			// String msg = String.format("%s: %s(%s)",ErrorCode.WRN_NONE_FOUND.getChDesc(),simpleName);
			String msg = String.format("%s: %s", ErrorCode.WRN_NONE_FOUND.getChDesc(), simpleName);
			log.warn(msg);
			return message;
		}
		// ��ñ���
		String tableName = simpleName;
		// ƴװJQL��ѯ���
		StringBuffer jql = new StringBuffer();
		Map paramMap = new HashMap();

		// ��ѯ��
		jql.append("FROM " + tableName + " a");

		// ��ѯ����
		jql.append(" WHERE 1=1");

		// ����������
		if (keyValue != null && !keyValue.equals(0L)) {
			jql.append(" AND a." + keyName + " =:" + keyName);
			paramMap.put(keyName, keyValue);
			// TODO
		} else if (isCheck) {// ��У������ѯ
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
