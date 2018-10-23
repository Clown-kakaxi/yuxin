package com.yuchengtech.emp.biappframe.logicsys.entity;


import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the BIONE_EXPORT_ENTITY database table.
 * 
 */
@Entity
@Table(name="BIONE_EXPORT_ENTITY")
public class BioneExportEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ENTITY_ID", unique=true, nullable=false, length=32)
	private String entityId;

	@Column(name="ENTITY_NAME", length=100)
	private String entityName;
	
	@Column(name="REMARK", length=500)
	private String remark;

    public BioneExportEntity() {
    }

	public String getEntityId() {
		return this.entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getEntityName() {
		return this.entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "BioneExportEntity [entityId=" + entityId + ", entityName=" + entityName + ", remark=" + remark + "]";
	}

}