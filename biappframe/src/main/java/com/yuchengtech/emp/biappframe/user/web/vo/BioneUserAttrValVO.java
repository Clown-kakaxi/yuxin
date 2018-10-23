/**
 * 
 */
package com.yuchengtech.emp.biappframe.user.web.vo;

import java.io.Serializable;

/**
 * <pre>
 * Title:用户信息扩展VO
 * Description: 用户信息扩展VO
 * </pre>
 * 
 * @author caiqy caiqy@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class BioneUserAttrValVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userId;

	private String attrId;

	private String fieldName;

	private String attrValue;

	/**
	 * @return 返回 userId。
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            设置 userId。
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return 返回 attrId。
	 */
	public String getAttrId() {
		return attrId;
	}

	/**
	 * @param attrId
	 *            设置 attrId。
	 */
	public void setAttrId(String attrId) {
		this.attrId = attrId;
	}

	/**
	 * @return 返回 fieldName。
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * @param fieldName
	 *            设置 fieldName。
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * @return 返回 attrValue。
	 */
	public String getAttrValue() {
		return attrValue;
	}

	/**
	 * @param attrValue
	 *            设置 attrValue。
	 */
	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}

	public BioneUserAttrValVO() {
		super();
	}

}
