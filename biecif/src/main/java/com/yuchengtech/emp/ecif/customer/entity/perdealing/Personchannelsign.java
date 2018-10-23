package com.yuchengtech.emp.ecif.customer.entity.perdealing;
import java.io.Serializable;
import javax.persistence.*;
import java.math.*;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;
@Entity(name="com.yuchengtech.emp.ecif.customer.entity.perdealing.Personchannelsign")
@Table(name="M_CI_BELONG_CHANNEL")
public class Personchannelsign implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="BELONG_CHANNEL_ID", unique=true, nullable=false)
	private Long belongChannelId;
	@Column(name="CUST_ID")
	private Long custId;
	@Column(name="BELONG_CHANNEL")
	private String belongChannel;
	@Column(name="LAST_UPDATE_SYS")
	private String lastUpdateSys;
	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;
	@Column(name="LAST_UPDATE_TM")
	private String lastUpdateTm;
	@Column(name="TX_SEQ_NO")
	private String txSeqNo;
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getBelongChannelId() {
 return this.belongChannelId;
 }
 public void setBelongChannelId(Long belongChannelId) {
  this.belongChannelId=belongChannelId;
 }
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getCustId() {
 return this.custId;
 }
 public void setCustId(Long custId) {
  this.custId=custId;
 }
 public String getBelongChannel() {
 return this.belongChannel;
 }
 public void setBelongChannel(String belongChannel) {
  this.belongChannel=belongChannel;
 }
 public String getLastUpdateSys() {
 return this.lastUpdateSys;
 }
 public void setLastUpdateSys(String lastUpdateSys) {
  this.lastUpdateSys=lastUpdateSys;
 }
 public String getLastUpdateUser() {
 return this.lastUpdateUser;
 }
 public void setLastUpdateUser(String lastUpdateUser) {
  this.lastUpdateUser=lastUpdateUser;
 }
 public String getLastUpdateTm() {
 return this.lastUpdateTm;
 }
 public void setLastUpdateTm(String lastUpdateTm) {
  this.lastUpdateTm=lastUpdateTm;
 }
 public String getTxSeqNo() {
 return this.txSeqNo;
 }
 public void setTxSeqNo(String txSeqNo) {
  this.txSeqNo=txSeqNo;
 }
 }

