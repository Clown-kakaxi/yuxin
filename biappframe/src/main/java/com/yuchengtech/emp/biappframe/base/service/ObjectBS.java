package com.yuchengtech.emp.biappframe.base.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@SuppressWarnings("unchecked")
public class ObjectBS extends BaseBS<Object> {

	/**
	 * 指增加数据
	 * 
	 * @param list
	 */
	@Transactional(readOnly = false)
	public void updateDataWithImport(List<?> list) {
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				this.baseDAO.merge(list.get(i));
			}
		}
	}
	
	/**
	 * 更新对象
	 * 
	 * @param entity
	 * @return
	 */
	@Transactional(readOnly = false)
	public <X> X updateObject(X entity) {
		return (X) this.baseDAO.merge(entity);
	}
}
