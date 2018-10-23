package com.yuchengtech.emp.ecif.transaction.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the TX_MSG_NODE_TAB_MAP database table.
 * 
 */
@Entity
@Table(name="TX_MSG_NODE_TAB_MAP")
public class TxMsgNodeTabMap implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "TX_MSG_NODE_TAB_MAP_NODETABMAPID_GENERATOR")
//	@GenericGenerator(name = "TX_MSG_NODE_TAB_MAP_NODETABMAPID_GENERATOR", strategy = "sequence", parameters = { @org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_TX_MSG_NODE_TAB_MAP") })
	@GenericGenerator(name = "TX_MSG_NODE_TAB_MAP_NODETABMAPID_GENERATOR", strategy = "com.yuchengtech.emp.ecif.base.util.IncrementGenerator")
	@Column(name="NODE_TAB_MAP_ID", unique=true, nullable=false)
	private Long nodeTabMapId;

	@Column(name="CREATE_TM")
	private Timestamp createTm;

	@Column(name="CREATE_USER", length=20)
	private String createUser;

	@Column(name="NODE_ID", nullable=false)
	private Long nodeId;

	@Column(name="STATE", length=1)
	private String state;

	@Column(name="TAB_ID", nullable=false)
	private Integer tabId;

	@Column(name="TAB_ROLE_TP", length=20)
	private String tabRoleTp;

	@Column(name="UPDATE_TM")
	private Timestamp updateTm;

	@Column(name="UPDATE_USER", length=20)
	private String updateUser;

    public TxMsgNodeTabMap() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getNodeTabMapId() {
		return this.nodeTabMapId;
	}

	public void setNodeTabMapId(Long nodeTabMapId) {
		this.nodeTabMapId = nodeTabMapId;
	}

	public Timestamp getCreateTm() {
		return this.createTm;
	}

	public void setCreateTm(Timestamp createTm) {
		this.createTm = createTm;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	@JsonSerialize(using=BioneLongSerializer.class)
	public Long getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getTabId() {
		return this.tabId;
	}

	public void setTabId(Integer tabId) {
		this.tabId = tabId;
	}

	public String getTabRoleTp() {
		return this.tabRoleTp;
	}

	public void setTabRoleTp(String tabRoleTp) {
		this.tabRoleTp = tabRoleTp;
	}

	public Timestamp getUpdateTm() {
		return this.updateTm;
	}

	public void setUpdateTm(Timestamp updateTm) {
		this.updateTm = updateTm;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

}