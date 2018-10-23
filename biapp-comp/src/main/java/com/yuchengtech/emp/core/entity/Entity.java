package com.yuchengtech.emp.core.entity;

/*
 * 软件著作权：
 *
 * 项目名称：核心基础库
 * 创建日期: 2011-03-30
 */

import java.io.Serializable;

public interface Entity extends Serializable, Cloneable {

	public long getId();

	public void setId(long object);

	public boolean equals(Object object);

	public int hashCode();

	public String toString();

	public String toXml();

	public String toJSON();

	// public Object clone();
}
