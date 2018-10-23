package com.yuchengtech.emp.core.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.yuchengtech.emp.utils.JsonMapper;

/*
 * 软件著作权：宇信易诚
 *
 * 项目名称：核心基础库
 * 创建日期: 2012-08-30
 */

@SuppressWarnings("serial")
@MappedSuperclass
public abstract class AbstractEntity implements Entity {

	@Id
	@Column(name = "id", unique = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	// 注意，数据库id是必须自增类型
	private long id = 0L;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String toXml() {

		return this.toJSON();

	}

	public String toJSON() {
		return JsonMapper.nonEmptyMapper().toJson(this);
	}

	public String toString() {
		// 可能存在性能和依赖包问题
		return toXml();
	}

	// public Object clone() {
	// return JsonMapper.nonEmptyMapper().
	//
	// }
	// chenluning's 2012-12-7 注释，考虑 未 保持 entity对象 put MAP,回抛出异常.
	public final int hashCode() {
		if (this.getId() == -1)
			return super.hashCode();
		return (int) this.getId();
	}

	public final boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (obj.getClass().equals(this.getClass())) {
			if (obj.hashCode() == hashCode())
				return true;
			return false;
		}
		return false;
	}
}
