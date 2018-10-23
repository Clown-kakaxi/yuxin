package com.yuchengtech.emp.biappframe.logicsys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.biappframe.logicsys.entity.BioneExportEntity;

/**
 * 
 * <pre>
 * Title: 逻辑系统管理员操作类
 * Description: 添加、修改 逻辑系统管理员
 * </pre>
 * 
 * @author xugy xugy@yuchengtech.com
 * @version 1.00.00
 * 
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  	修改日期:     修改内容:
 * </pre>
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ExportEntityBS extends BaseBS<BioneExportEntity> {

	/**
	 * 获取要导出的全部实体名称
	 */
	public List<String> getExportEntitiesAll() {
		List<BioneExportEntity> entitiesList = this.getAllEntityList();
		List<String> entitiesName = Lists.newArrayList();
		for(int i = 0; i < entitiesList.size(); i++) {
			entitiesName.add(entitiesList.get(i).getEntityName());
		}
		return entitiesName;
	}
}
