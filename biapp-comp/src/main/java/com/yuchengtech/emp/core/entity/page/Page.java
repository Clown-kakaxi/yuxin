package com.yuchengtech.emp.core.entity.page;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 功能描述: 分页对象
 * 
 * Copyright: Copyright (c) 2011 Company: 北京宇信易诚科技有限公司
 * 
 * @author TanDong
 * @version 1.0 2011-6-1下午04:08:32
 * @param <T>
 * @see HISTORY 2011-6-1下午04:08:32 创建文件
 ************************************************* 
 */
public class Page<T> {
	
	/**
	 * 分页信息
	 */
	private PageInfo pageInfo = new PageInfo();

	/**
	 * 数据集
	 */
	private List<T> data = new ArrayList<T>();

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
}