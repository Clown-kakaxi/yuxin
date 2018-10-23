/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.service.component.biz.cover
 * @文件名：AbsCoverByValidData.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:57:10
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
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
 * @项目名称：ytec-mdm-ecif
 * @类名称：AbsCoverByValidData
 * @类描述：数据有效性覆盖
 * @功能描述:对于无标准码的字段有值覆盖NULL，对于标准码有值覆盖****000000、****999999、****999998
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:57:10
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:57:10
 * @修改备注：
 * @修改日期 修改人员 修改原因 -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
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
		
		log.info("存在已有数据[{}]，根据覆盖原则，更新已有数据", clazz.getSimpleName());
		boolean doChangeLog = BusinessCfg.getBoolean("doChangeLog");
		boolean obChange = false;// 实体是否变更
		boolean fvChange = false;// 数据是否变更
		boolean useRule = true; // 是否使用覆盖规则
		DataChangeModel dataChangeModel = null;
		String tableEntityName = clazz.getSimpleName();
		if (doChangeLog) {
			dataChangeModel = getDataChangeModel(ecifData, oldObject);
		}
		// 获得该类型的所有方法
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			// 获得get方法的名字
			String name = field.getName();
			field.setAccessible(true);
			// 获得要SET的值
			Object oldValue = field.get(oldObject);
			Object newValue = field.get(newObject);

			/***
			 * 无论数据是否非空都以传入的数据为准还是使用覆盖规则
			 */
			useRule = useCoveringRule(ecifData, tableEntityName, name);
			fvChange = false;
			if (newValue != null) {// 非空
				if (oldValue == null) {
					fvChange = true;
				} else if (!newValue.equals(oldValue)) {
					if (useRule) {
						/*** 自定义覆盖规则, 其他自定义规则 **/
						fvChange = exeCoveringRule(ecifData, tableEntityName,
								name, newValue);
					} else {
						fvChange = true;
						log.info("交易[{}],系统[{}]处理[{}]表的字段[{}]时,跳过覆盖规则",
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
						 * 主键不能为空
						 */
						fvChange = false;
					}else{
						/**
						 * 老数据非空，但是不用覆盖规则，强制使用空值
						 */
						fvChange = true;
						log.info("交易[{}],系统[{}]处理[{}]表的字段[{}]时,跳过覆盖规则,强制使用空值",
							ecifData.getTxCode(), ecifData.getOpChnlNo(),
							tableEntityName, name);
					}
				} else {
					fvChange = false;
				}
			}

			obChange = obChange || fvChange;
			// 为返回的对象赋值
			if (!fvChange) {
				if (oldValue != null) {
					field.set(newObject, oldValue);
				}
			} else {
				// 打印变更记录
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
	 * @函数名称:getDataChangeModel
	 * @函数描述:获取信息变动对象，记录模型数据哪个数据属性发生变动
	 * @参数与返回说明:
	 * @param ecifData
	 * @param oldObject
	 * @return
	 * @算法描述:
	 */
	private DataChangeModel getDataChangeModel(EcifData ecifData,
			Object oldObject) {
		// 获得主键名
		String keyName = GetKeyNameUtil.getInstance().getKeyName(oldObject);
		String key = null;
		if (keyName != null) {
			// 获得主键值
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
	 * @函数名称:useCoveringRule
	 * @函数描述:判断该交易中处理的这个表的数据字段是否使用覆盖规则
	 * @参数与返回说明:
	 * @param ecifData
	 * @param tableEntityName
	 *            表对象名
	 * @param fieldName
	 *            字段属性名称
	 * @return true-使用 false-不使用
	 * @算法描述:
	 */
	protected abstract boolean useCoveringRule(EcifData ecifData,
			String tableEntityName, String fieldName);

	/**
	 * @函数名称:exeCoveringRule
	 * @函数描述:执行其他覆盖规则
	 * @参数与返回说明:
	 * @param ecifData
	 * @param tableEntityName
	 *            表对象名
	 * @param fieldName
	 *            字段属性名称
	 * @param newValue
	 *            新数据
	 * @return
	 * @算法描述: true-使用新数据 false-使用老数据
	 */
	protected abstract boolean exeCoveringRule(EcifData ecifData,
			String tableEntityName, String fieldName, Object newValue);

}
