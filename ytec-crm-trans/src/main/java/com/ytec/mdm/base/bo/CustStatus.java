/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.base.bo
 * @文件名：CustStatus.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-9:46:35
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */

package com.ytec.mdm.base.bo;

/**
 * The Class CustStatus.
 * 
 * @项目名称：ytec-mdm-ecif
 * @类名称： CustStatus
 * @类描述： 客户状态模型
 * @功能描述:
 * @创建人： wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 9:46:35
 * @修改人： wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 9:46:35
 * @修改备注：
 * @修改日期    修改人员        修改原因
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
public class CustStatus {
	
	/**
	 * The normal.
	 * 
	 * @属性描述:客户状态是否正常
	 */
	private boolean normal;
	
	/**
	 * The cust status.
	 * 
	 * @属性描述:客户状态
	 */
	private String  custStatus;
	
	/**
	 * The cust status desc.
	 * 
	 * @属性描述:客户状态描述
	 */
	private String  custStatusDesc;
	
	/**
	 * The re open.
	 * 
	 * @属性描述:可开户
	 */
	private boolean reOpen;
	
	/**
	 * The update.
	 * 
	 * @属性描述:可维护
	 */
	private boolean update;
	
	/**
	 * The select.
	 * 
	 * @属性描述:可查询
	 */
	private boolean select;
	
	
	
	/**
	 *@构造函数 
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
	 * @函数名称:isNormal
	 * @函数描述:
	 * @参数与返回说明:
	 * 		@return  boolean
	 * @算法描述:
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
	 * @函数名称:boolean isReOpen()
	 * @函数描述:
	 * @参数与返回说明: boolean isReOpen()
	 * @算法描述:
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
	 * @函数名称:boolean isUpdate()
	 * @函数描述:
	 * @参数与返回说明: boolean isUpdate()
	 * @算法描述:
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
	 * @函数名称:boolean isSelect()
	 * @函数描述:
	 * @参数与返回说明: boolean isSelect()
	 * @算法描述:
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
