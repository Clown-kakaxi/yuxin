/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.base.bo
 * @�ļ�����InfoGrantCtrl.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-9:56:11
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */

package com.ytec.mdm.base.bo;

import java.util.Date;

/**
 * The Class InfoGrantCtrl.
 * 
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ� InfoGrantCtrl
 * @����������Ϣ��ѯ�ּ�����ģ��
 * @��������:
 * @�����ˣ� wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 9:56:11
 * @�޸��ˣ� wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 9:56:11
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ��
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 */
public class InfoGrantCtrl {
	
	/**
	 * The info item id.
	 * 
	 * @��������:��Ϣ���ʶ
	 */
	private Long   infoItemId;
	
	/**
	 * The ctrl desc.
	 * 
	 * @��������:��Ȩ����
	 */
	private String ctrlDesc;
	
	/**
	 * The ctrl stat.
	 * 
	 * @��������:��Ȩ״̬
	 */
	private String ctrlStat;
	
	/**
	 * The start date.
	 * 
	 * @��������:��ʼʱ��
	 */
	private Date   startDate;
	
	/**
	 * The end date.
	 * 
	 * @��������:����ʱ��
	 */
	private Date   endDate;
	
	/**
	 * The Constructor.
	 * 
	 * @���캯�� info grant ctrl
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
