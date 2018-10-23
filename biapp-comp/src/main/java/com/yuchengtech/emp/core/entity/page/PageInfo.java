package com.yuchengtech.emp.core.entity.page;

/**
 * 
 * 功能描述: 分页信息对象
 * 
 * Copyright: Copyright (c) 2011 Company: 北京宇信易诚科技有限公司
 * 
 * @author TanDong
 * @version 1.0 2011-6-1下午04:08:32
 * @see HISTORY 2011-6-1下午04:08:32 创建文件
 * ************************************************ 
 */
public class PageInfo {

	public PageInfo() {
	}

	public PageInfo(String pageNum, String pageLength) {
		if (null != pageNum && pageNum.length() > 0) {
			this.currentPageNum = Integer.parseInt(pageNum);
		} else {
			this.currentPageNum = 1;
		}
		if (null != pageLength && pageLength.length() > 0) {
			this.rowsOfPage = Integer.parseInt(pageLength);
		} else {
			this.rowsOfPage = 10;
		}
	}

	private int totalPageCount;
	private int totalRowCount;
	private int currentPageNum = 1;
	private int rowsOfPage = 10;
	private boolean countOfEverytime = true;

	public boolean isCountOfEverytime() {
		return countOfEverytime;
	}

	public void setCountOfEverytime(boolean countOfEverytime) {
		this.countOfEverytime = countOfEverytime;
	}

	public int getCurrentPageNum() {
		return currentPageNum <= 0 ? 1 : currentPageNum;// 1
	}

	public void setCurrentPageNum(int currentPageNum) {
		this.currentPageNum = currentPageNum;
	}

	public int getRowsOfPage() {
		return rowsOfPage <= 0 ? 10 : rowsOfPage;// 10
	}

	public void setRowsOfPage(int rowsOfPage) {
		this.rowsOfPage = rowsOfPage;
	}

	public int getTotalPageCount() {
		return totalPageCount;
	}

	public void setTotalPageCount(int totalPageNum) {
		totalPageCount = totalPageNum;
	}

	public int getTotalRowCount() {
		return totalRowCount;
	}

	public void setTotalRowCount(int totalRowCount) {
		this.totalRowCount = totalRowCount;
	}

	public int getStartIndex() {
		return (getCurrentPageNum() - 1) * getRowsOfPage();
	}

}