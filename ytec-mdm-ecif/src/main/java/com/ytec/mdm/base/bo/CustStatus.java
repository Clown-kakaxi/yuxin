/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.base.bo
 * @�ļ�����CustStatus.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-9:46:35
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */

package com.ytec.mdm.base.bo;

/**
 * The Class CustStatus.
 * 
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ� CustStatus
 * @�������� �ͻ�״̬ģ��
 * @��������:
 * @�����ˣ� wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 9:46:35
 * @�޸��ˣ� wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 9:46:35
 * @�޸ı�ע��
 * @�޸�����    �޸���Ա        �޸�ԭ��
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 */
public class CustStatus {
	
	/**
	 * The normal.
	 * 
	 * @��������:�ͻ�״̬�Ƿ�����
	 */
	private boolean normal;
	
	/**
	 * The cust status.
	 * 
	 * @��������:�ͻ�״̬
	 */
	private String  custStatus;
	
	/**
	 * The cust status desc.
	 * 
	 * @��������:�ͻ�״̬����
	 */
	private String  custStatusDesc;
	
	/**
	 * The re open.
	 * 
	 * @��������:�ɿ���
	 */
	private boolean reOpen;
	
	/**
	 * The update.
	 * 
	 * @��������:��ά��
	 */
	private boolean update;
	
	/**
	 * The select.
	 * 
	 * @��������:�ɲ�ѯ
	 */
	private boolean select;
	
	
	
	/**
	 *@���캯�� 
	 * @param custStatus
	 * @param custStatusDesc
	 * @param normal
	 * @param grant
	 */
	public CustStatus(String custStatus, String custStatusDesc,boolean normal, int grant) {
		this.normal = normal;
		this.custStatus = custStatus;
		this.custStatusDesc = custStatusDesc;
		if((grant & 0x1)!=0){
			select=true;
		}
		if((grant & 0x2)!=0){
			update=true;
		}
		if((grant & 0x4)!=0){
			reOpen=true;
		}
		
	}
	
	
	/**
	 * @��������:isNormal
	 * @��������:
	 * @�����뷵��˵��:
	 * 		@return  boolean
	 * @�㷨����:
	 */
	public boolean isNormal() {
		return normal;
	}
	
	/**
	 * Sets the normal.
	 * 
	 * @param normal
	 *            the new normal
	 */
	public void setNormal(boolean normal) {
		this.normal = normal;
	}
	
	/**
	 * Gets the cust status.
	 * 
	 * @return the cust status
	 */
	public String getCustStatus() {
		return custStatus;
	}
	
	/**
	 * Sets the cust status.
	 * 
	 * @param custStatus
	 *            the new cust status
	 */
	public void setCustStatus(String custStatus) {
		this.custStatus = custStatus;
	}
	
	/**
	 * Gets the cust status desc.
	 * 
	 * @return the cust status desc
	 */
	public String getCustStatusDesc() {
		return custStatusDesc;
	}
	
	/**
	 * Sets the cust status desc.
	 * 
	 * @param custStatusDesc
	 *            the new cust status desc
	 */
	public void setCustStatusDesc(String custStatusDesc) {
		this.custStatusDesc = custStatusDesc;
	}
	
	/**
	 * Checks if is re open.
	 * 
	 * @return true, if checks if is re open
	 * @��������:boolean isReOpen()
	 * @��������:
	 * @�����뷵��˵��: boolean isReOpen()
	 * @�㷨����:
	 */
	public boolean isReOpen() {
		return reOpen;
	}
	
	/**
	 * Sets the re open.
	 * 
	 * @param reOpen
	 *            the new re open
	 */
	public void setReOpen(boolean reOpen) {
		this.reOpen = reOpen;
	}
	
	/**
	 * Checks if is update.
	 * 
	 * @return true, if checks if is update
	 * @��������:boolean isUpdate()
	 * @��������:
	 * @�����뷵��˵��: boolean isUpdate()
	 * @�㷨����:
	 */
	public boolean isUpdate() {
		return update;
	}
	
	/**
	 * Sets the update.
	 * 
	 * @param update
	 *            the new update
	 */
	public void setUpdate(boolean update) {
		this.update = update;
	}
	
	/**
	 * Checks if is select.
	 * 
	 * @return true, if checks if is select
	 * @��������:boolean isSelect()
	 * @��������:
	 * @�����뷵��˵��: boolean isSelect()
	 * @�㷨����:
	 */
	public boolean isSelect() {
		return select;
	}
	
	/**
	 * Sets the select.
	 * 
	 * @param select
	 *            the new select
	 */
	public void setSelect(boolean select) {
		this.select = select;
	}

}
