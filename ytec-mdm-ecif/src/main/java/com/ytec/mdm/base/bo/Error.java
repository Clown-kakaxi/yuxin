/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.base.bo
 * @文件名：Error.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-9:54:25
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */

package com.ytec.mdm.base.bo;

/**
 * The Class Error.
 * 
 * @项目名称：ytec-mdm-ecif
 * @类名称： Error
 * @类描述：错误返回码模型
 * @功能描述:
 * @创建人： wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 9:54:25
 * @修改人： wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 9:54:25
 * @修改备注：
 * @修改日期 修改人员 修改原因
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
public class Error {
	
	/**
	 * The code.
	 * 
	 * @属性描述:错误码
	 */
	private String code;
	
	/**
	 * The en desc.
	 * 
	 * @属性描述:英文描述
	 */
	private String enDesc;
	
	/**
	 * The ch desc.
	 * 
	 * @属性描述:中文描述
	 */
	private String chDesc;

	/**
	 * The Constructor.
	 * 
	 * @构造函数 error
	 */
	public Error() {
	}

	
	/**
	 *@构造函数 
	 * @param value
	 */
	public Error(String value) {
		String value0[] = value.split("\\|");
		this.code = value0[0];
		this.enDesc = value0[1];
		this.chDesc = value0[2];
	}

	
	/**
	 *@构造函数 
	 * @param code
	 * @param enDesc
	 * @param chDesc
	 */
	public Error(String code, String enDesc, String chDesc) {
		this.code = code;
		this.enDesc = enDesc;
		this.chDesc = chDesc;

	}

	/**
	 * Gets the code.
	 * 
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the code.
	 * 
	 * @param code
	 *            the new code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Gets the en desc.
	 * 
	 * @return the en desc
	 */
	public String getEnDesc() {
		return enDesc;
	}

	/**
	 * Sets the en desc.
	 * 
	 * @param enDesc
	 *            the new en desc
	 */
	public void setEnDesc(String enDesc) {
		this.enDesc = enDesc;
	}

	/**
	 * Gets the ch desc.
	 * 
	 * @return the ch desc
	 */
	public String getChDesc() {
		return chDesc;
	}

	/**
	 * Sets the ch desc.
	 * 
	 * @param chDesc
	 *            the new ch desc
	 */
	public void setChDesc(String chDesc) {
		this.chDesc = chDesc;
	}

}
