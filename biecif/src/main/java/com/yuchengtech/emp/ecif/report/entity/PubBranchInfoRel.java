package com.yuchengtech.emp.ecif.report.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * <pre>
 * Title:程序的中文名称
 * Description: 程序功能的描述 
 * </pre>
 * @author guanyb  guanyb@yuchengtech.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
@Entity
@Table(name="PUBBRANCHINFOREL")
public class PubBranchInfoRel implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	BRCCODE	VARCHAR	9
//	BRCNAME	VARCHAR	40
//	PARENTBRCCODE	VARCHAR	9
	@Id
	@Column(name="BRCCODE", unique=true, nullable=false)
	private String brcCode;
	@Column(name="BRCNAME")
	private String brcName;
	@Column(name="PARENTBRCCODE")
	private String parentBrcCode;
	
	public String getBrcCode() {
		return brcCode;
	}
	public void setBrcCode(String brcCode) {
		this.brcCode = brcCode;
	}
	public String getBrcName() {
		return brcName;
	}
	public void setBrcName(String brcName) {
		this.brcName = brcName;
	}
	public String getParentBrcCode() {
		return parentBrcCode;
	}
	public void setParentBrcCode(String parentBrcCode) {
		this.parentBrcCode = parentBrcCode;
	}
}
