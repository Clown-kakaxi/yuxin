/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.base.bo
 * @文件名：InfoGrantCtrl.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-9:56:11
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */

package com.ytec.mdm.base.bo;

import java.util.Date;

/**
 * The Class InfoGrantCtrl.
 * 
 * @项目名称：ytec-mdm-ecif
 * @类名称： InfoGrantCtrl
 * @类描述：信息查询分级配置模型
 * @功能描述:
 * @创建人： wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 9:56:11
 * @修改人： wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 9:56:11
 * @修改备注：
 * @修改日期 修改人员 修改原因
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
public class InfoGrantCtrl {
	
	/**
	 * The info item id.
	 * 
	 * @属性描述:信息项标识
	 */
	private Long   infoItemId;
	
	/**
	 * The ctrl desc.
	 * 
	 * @属性描述:授权描述
	 */
	private String ctrlDesc;
	
	/**
	 * The ctrl stat.
	 * 
	 * @属性描述:授权状态
	 */
	private String ctrlStat;
	
	/**
	 * The start date.
	 * 
	 * @属性描述:开始时间
	 */
	private Date   startDate;
	
	/**
	 * The end date.
	 * 
	 * @属性描述:结束时间
	 */
	private Date   endDate;
	
	/**
	 * The Constructor.
	 * 
	 * @构造函数 info grant ctrl
	 */
	public InfoGrantCtrl() {
	}
	
	/**
	 * Gets the info item id.
	 * 
	 * @return the info item id
	 */
	public Long getInfoItemId() {
		return infoItemId;
	}
	
	/**
	 * Sets the info item id.
	 * 
	 * @param infoItemId
	 *            the new info item id
	 */
	public void setInfoItemId(Long infoItemId) {
		this.infoItemId = infoItemId;
	}
	
	/**
	 * Gets the ctrl desc.
	 * 
	 * @return the ctrl desc
	 */
	public String getCtrlDesc() {
		return ctrlDesc;
	}
	
	/**
	 * Sets the ctrl desc.
	 * 
	 * @param ctrlDesc
	 *            the new ctrl desc
	 */
	public void setCtrlDesc(String ctrlDesc) {
		this.ctrlDesc = ctrlDesc;
	}
	
	/**
	 * Gets the ctrl stat.
	 * 
	 * @return the ctrl stat
	 */
	public String getCtrlStat() {
		return ctrlStat;
	}
	
	/**
	 * Sets the ctrl stat.
	 * 
	 * @param ctrlStat
	 *            the new ctrl stat
	 */
	public void setCtrlStat(String ctrlStat) {
		this.ctrlStat = ctrlStat;
	}
	
	/**
	 * Gets the start date.
	 * 
	 * @return the start date
	 */
	public Date getStartDate() {
		return startDate;
	}
	
	/**
	 * Sets the start date.
	 * 
	 * @param startDate
	 *            the new start date
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	/**
	 * Gets the end date.
	 * 
	 * @return the end date
	 */
	public Date getEndDate() {
		return endDate;
	}
	
	/**
	 * Sets the end date.
	 * 
	 * @param endDate
	 *            the new end date
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "InfoGrantCtrl [infoItemId=" + infoItemId + ", ctrlDesc="
				+ ctrlDesc + ", ctrlStat=" + ctrlStat + ", startDate="
				+ startDate + ", endDate=" + endDate + "]";
	}
	
	
	

}
