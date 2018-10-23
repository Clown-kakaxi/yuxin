package com.yuchengtech.emp.ecif.customer.entity.image;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "IMAGEINFO")
public class ImageInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="IMAGE_INFO_ID", unique=true, nullable=false)
	private String imageInfoId;
	@Column(name = "CUST_ID")
	private Long custId;
	@Column(name = "IMAGE_INFO_TYPE", length = 20)
	private String imageInfoType;
	@Column(name = "IMAGE_INFO_INDEX", length = 255)
	private String imageInfoIndex;
	@Column(name = "LAST_UPDATE_SYS", length = 20)
	private String lastUpdateSys;
	@Column(name = "LAST_UPDATE_USER", length = 20)
	private String lastUpdateUser;
	@Column(name = "LAST_UPDATE_TM")
	private Timestamp lastUpdateTm;
	@Column(name = "TX_SEQ_NO", length = 32)
	private String txSeqNo;
	public String getImageInfoId() {
		return imageInfoId;
	}
	public void setImageInfoId(String imageInfoId) {
		this.imageInfoId = imageInfoId;
	}
	public Long getCustId() {
		return custId;
	}
	public void setCustId(Long custId) {
		this.custId = custId;
	}
	public String getImageInfoType() {
		return imageInfoType;
	}
	public void setImageInfoType(String imageInfoType) {
		this.imageInfoType = imageInfoType;
	}
	public String getImageInfoIndex() {
		return imageInfoIndex;
	}
	public void setImageInfoIndex(String imageInfoIndex) {
		this.imageInfoIndex = imageInfoIndex;
	}
	public String getLastUpdateSys() {
		return lastUpdateSys;
	}
	public void setLastUpdateSys(String lastUpdateSys) {
		this.lastUpdateSys = lastUpdateSys;
	}
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
	public Timestamp getLastUpdateTm() {
		return lastUpdateTm;
	}
	public void setLastUpdateTm(Timestamp lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}
	public String getTxSeqNo() {
		return txSeqNo;
	}
	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}
	
	

}
