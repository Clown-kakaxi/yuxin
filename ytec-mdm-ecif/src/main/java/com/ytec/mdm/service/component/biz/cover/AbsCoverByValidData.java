/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.service.component.biz.cover
 * @�ļ�����AbsCoverByValidData.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:57:10
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.service.component.biz.cover;

import java.lang.reflect.Field;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ytec.mdm.base.bo.DataChangeModel;
import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.util.GetKeyNameUtil;
import com.ytec.mdm.base.util.ReflectionUtils;
import com.ytec.mdm.server.common.BusinessCfg;
import com.ytec.mdm.service.facade.ICoveringRule;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�AbsCoverByValidData
 * @��������������Ч�Ը���
 * @��������:�����ޱ�׼����ֶ���ֵ����NULL�����ڱ�׼����ֵ����****000000��****999999��****999998
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:57:10
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:57:10
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ�� -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
@SuppressWarnings("rawtypes")
public abstract class AbsCoverByValidData implements ICoveringRule {
	private static Logger log = LoggerFactory
			.getLogger(AbsCoverByValidData.class);

	// (wangtb@yuchengtech.com)
	public Object cover(EcifData ecifData, Object oldObject, Object newObject)
			throws Exception {
		Class clazz = newObject.getClass();
		
		log.info("������������[{}]�����ݸ���ԭ�򣬸�����������", clazz.getSimpleName());
		boolean doChangeLog = BusinessCfg.getBoolean("doChangeLog");
		boolean obChange = false;// ʵ���Ƿ���
		boolean fvChange = false;// �����Ƿ���
		boolean useRule = true; // �Ƿ�ʹ�ø��ǹ���
		DataChangeModel dataChangeModel = null;
		String tableEntityName = clazz.getSimpleName();
		if (doChangeLog) {
			dataChangeModel = getDataChangeModel(ecifData, oldObject);
		}
		// ��ø����͵����з���
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			// ���get����������
			String name = field.getName();
			field.setAccessible(true);
			// ���ҪSET��ֵ
			Object oldValue = field.get(oldObject);
			Object newValue = field.get(newObject);

			/***
			 * ���������Ƿ�ǿն��Դ��������Ϊ׼����ʹ�ø��ǹ���
			 */
			useRule = useCoveringRule(ecifData, tableEntityName, name);
			fvChange = false;
			if (newValue != null) {// �ǿ�
				if (oldValue == null) {
					fvChange = true;
				} else if (!newValue.equals(oldValue)) {
					if (useRule) {
						/*** �Զ��帲�ǹ���, �����Զ������ **/
						fvChange = exeCoveringRule(ecifData, tableEntityName,
								name, newValue);
					} else {
						fvChange = true;
						log.info("����[{}],ϵͳ[{}]����[{}]����ֶ�[{}]ʱ,�������ǹ���",
								ecifData.getTxCode(), ecifData.getOpChnlNo(),
								tableEntityName, name);
					}
				} else {
					fvChange = false;
				}
			} else {

				if (oldValue != null && !useRule) {
					if(name.equals(GetKeyNameUtil.getInstance().getKeyName(oldObject))){
						/***
						 * ��������Ϊ��
						 */
						fvChange = false;
					}else{
						/**
						 * �����ݷǿգ����ǲ��ø��ǹ���ǿ��ʹ�ÿ�ֵ
						 */
						fvChange = true;
						log.info("����[{}],ϵͳ[{}]����[{}]����ֶ�[{}]ʱ,�������ǹ���,ǿ��ʹ�ÿ�ֵ",
							ecifData.getTxCode(), ecifData.getOpChnlNo(),
							tableEntityName, name);
					}
				} else {
					fvChange = false;
				}
			}

			obChange = obChange || fvChange;
			// Ϊ���صĶ���ֵ
			if (!fvChange) {
				if (oldValue != null) {
					field.set(newObject, oldValue);
				}
			} else {
				// ��ӡ�����¼
				if (dataChangeModel != null) {
					dataChangeModel.addChangeField(field.getName(), oldValue,
							newValue, field.getType());
				}
			}
		}
		if (obChange) {
			if (dataChangeModel != null) {
				if (ecifData.getDataSynchro() == null) {
					ecifData.setDataSynchro(new ArrayList());
				}
				if(dataChangeModel.isNewChange()){
					ecifData.getDataSynchro().add(dataChangeModel);
				}
			}
			return newObject;
		} else {
			return null;
		}
	}

	/**
	 * @��������:getDataChangeModel
	 * @��������:��ȡ��Ϣ�䶯���󣬼�¼ģ�������ĸ��������Է����䶯
	 * @�����뷵��˵��:
	 * @param ecifData
	 * @param oldObject
	 * @return
	 * @�㷨����:
	 */
	private DataChangeModel getDataChangeModel(EcifData ecifData,
			Object oldObject) {
		// ���������
		String keyName = GetKeyNameUtil.getInstance().getKeyName(oldObject);
		String key = null;
		if (keyName != null) {
			// �������ֵ
			Object keyValue = ReflectionUtils.getFieldValue(oldObject, keyName);
			if (keyValue != null) {
				key = keyValue.toString();
			}
		}
		DataChangeModel o = new DataChangeModel(oldObject.getClass()
				.getSimpleName(), key);
		o.setKeyName(keyName);
		o.setChangeType(0);
		if (ecifData.getDataSynchro() != null) {
			int i = ecifData.getDataSynchro().indexOf(o);
			if (i >= 0) {
				o=(DataChangeModel)ecifData.getDataSynchro().get(i);
				o.setNewChange(false);
				return o;
			}
		}
		o.setNewChange(true);
		return o;

	}

	/**
	 * @��������:useCoveringRule
	 * @��������:�жϸý����д���������������ֶ��Ƿ�ʹ�ø��ǹ���
	 * @�����뷵��˵��:
	 * @param ecifData
	 * @param tableEntityName
	 *            �������
	 * @param fieldName
	 *            �ֶ���������
	 * @return true-ʹ�� false-��ʹ��
	 * @�㷨����:
	 */
	protected abstract boolean useCoveringRule(EcifData ecifData,
			String tableEntityName, String fieldName);

	/**
	 * @��������:exeCoveringRule
	 * @��������:ִ���������ǹ���
	 * @�����뷵��˵��:
	 * @param ecifData
	 * @param tableEntityName
	 *            �������
	 * @param fieldName
	 *            �ֶ���������
	 * @param newValue
	 *            ������
	 * @return
	 * @�㷨����: true-ʹ�������� false-ʹ��������
	 */
	protected abstract boolean exeCoveringRule(EcifData ecifData,
			String tableEntityName, String fieldName, Object newValue);

}
