/**
 * 
 */
package com.yuchengtech.emp.biappframe.base.common;

import java.io.Serializable;
import java.util.Map;

/**
 * <pre>
 * Title:Form表单中field项对象
 * Description: 程序功能的描述
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
@SuppressWarnings("rawtypes")
public class CommonFormField implements Serializable {

	private static final long serialVersionUID = 1L;
	private String type;
	private String name;
	private String display;
	private String width;
	private String align;
	private String labelWidth;
	private String label;
	private String labelAlign;
	private String space;
	private Boolean newline;
	private String group;
	private String groupicon;
	private String comboboxName;
	// 扩展属性，如textarea的resize: none;
	private Map attr;
	// 校验属性
	private Map validate;
	// 下拉属性
	private Map options;

	public CommonFormField() {
		super();
	}

	/**
	 * @return 返回 type。
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            设置 type。
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return 返回 name。
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            设置 name。
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return 返回 display。
	 */
	public String getDisplay() {
		return display;
	}

	/**
	 * @param display
	 *            设置 display。
	 */
	public void setDisplay(String display) {
		this.display = display;
	}

	/**
	 * @return 返回 width。
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * @param width
	 *            设置 width。
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	/**
	 * @return 返回 align。
	 */
	public String getAlign() {
		return align;
	}

	/**
	 * @param align
	 *            设置 align。
	 */
	public void setAlign(String align) {
		this.align = align;
	}

	/**
	 * @return 返回 labelWidth。
	 */
	public String getLabelWidth() {
		return labelWidth;
	}

	/**
	 * @param labelWidth
	 *            设置 labelWidth。
	 */
	public void setLabelWidth(String labelWidth) {
		this.labelWidth = labelWidth;
	}

	/**
	 * @return 返回 label。
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label
	 *            设置 label。
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return 返回 labelAlign。
	 */
	public String getLabelAlign() {
		return labelAlign;
	}

	/**
	 * @param labelAlign
	 *            设置 labelAlign。
	 */
	public void setLabelAlign(String labelAlign) {
		this.labelAlign = labelAlign;
	}

	/**
	 * @return 返回 space。
	 */
	public String getSpace() {
		return space;
	}

	/**
	 * @param space
	 *            设置 space。
	 */
	public void setSpace(String space) {
		this.space = space;
	}

	/**
	 * @return 返回 newline。
	 */
	public Boolean getNewline() {
		return newline;
	}

	/**
	 * @param newline
	 *            设置 newline。
	 */
	public void setNewline(Boolean newline) {
		this.newline = newline;
	}

	/**
	 * @return 返回 group。
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * @param group
	 *            设置 group。
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 * @return 返回 groupicon。
	 */
	public String getGroupicon() {
		return groupicon;
	}

	/**
	 * @param groupicon
	 *            设置 groupicon。
	 */
	public void setGroupicon(String groupicon) {
		this.groupicon = groupicon;
	}

	/**
	 * @return 返回 comboboxName。
	 */
	public String getComboboxName() {
		return comboboxName;
	}

	/**
	 * @param comboboxName
	 *            设置 comboboxName。
	 */
	public void setComboboxName(String comboboxName) {
		this.comboboxName = comboboxName;
	}

	/**
	 * @return 返回 validate。
	 */
	public Map getValidate() {
		return validate;
	}

	/**
	 * @param validate
	 *            设置 validate。
	 */
	public void setValidate(Map validate) {
		this.validate = validate;
	}

	/**
	 * @return 返回 options。
	 */
	public Map getOptions() {
		return options;
	}

	/**
	 * @param options
	 *            设置 options。
	 */
	public void setOptions(Map options) {
		this.options = options;
	}

	/**
	 * @return 返回 attr。
	 */
	public Map getAttr() {
		return attr;
	}

	/**
	 * @param attr
	 *            设置 attr。
	 */
	public void setAttr(Map attr) {
		this.attr = attr;
	}

}
