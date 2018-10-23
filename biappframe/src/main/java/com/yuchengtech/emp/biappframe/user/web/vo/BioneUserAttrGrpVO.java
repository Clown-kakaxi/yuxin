/**
 * 
 */
package com.yuchengtech.emp.biappframe.user.web.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.yuchengtech.emp.biappframe.user.entity.BioneUserAttr;

/**
 * <pre>
 * Title:用户信息属性分组VO
 * Description: 用户信息属性分组VO 
 * </pre>
 * @author caiqy  caiqy@yuchengtech.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class BioneUserAttrGrpVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String grpId;

	private String grpIcon;

	private String grpName;
	
	private BigDecimal orderNo;
	
	private List<BioneUserAttr> attrs = new ArrayList<BioneUserAttr>();

	/**
	 * @return 返回 grpId。
	 */
	public String getGrpId() {
		return grpId;
	}

	/**
	 * @param grpId 设置 grpId。
	 */
	public void setGrpId(String grpId) {
		this.grpId = grpId;
	}

	/**
	 * @return 返回 grpIcon。
	 */
	public String getGrpIcon() {
		return grpIcon;
	}

	/**
	 * @param grpIcon 设置 grpIcon。
	 */
	public void setGrpIcon(String grpIcon) {
		this.grpIcon = grpIcon;
	}

	/**
	 * @return 返回 grpName。
	 */
	public String getGrpName() {
		return grpName;
	}

	/**
	 * @param grpName 设置 grpName。
	 */
	public void setGrpName(String grpName) {
		this.grpName = grpName;
	}

	/**
	 * @return 返回 orderNo。
	 */
	public BigDecimal getOrderNo() {
		return orderNo;
	}

	/**
	 * @param orderNo 设置 orderNo。
	 */
	public void setOrderNo(BigDecimal orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * @return 返回 attrs。
	 */
	public List<BioneUserAttr> getAttrs() {
		return attrs;
	}

	/**
	 * @param attrs 设置 attrs。
	 */
	public void setAttrs(List<BioneUserAttr> attrs) {
		this.attrs = attrs;
	}

	public BioneUserAttrGrpVO() {
		super();
	}
	
}
