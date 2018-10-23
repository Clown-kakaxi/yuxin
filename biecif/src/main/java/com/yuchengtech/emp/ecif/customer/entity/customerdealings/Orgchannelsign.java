package com.yuchengtech.emp.ecif.customer.entity.customerdealings;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the ORGCHANNELSIGN database table.
 * 
 */
@Entity
@Table(name="ORGCHANNELSIGN")
public class Orgchannelsign implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CUST_CHANNEL_SIGN_ID", unique=true, nullable=false)
	private String custChannelSignId;

	@Column(name="CHANNEL_ID", length=2)
	private String channelId;

	@Column(name="CUST_ID")
	private Long custId;

	@Column(name="SIGN_TYPE", length=20)
	private String signType;

    public Orgchannelsign() {
    }

	public String getCustChannelSignId() {
		return this.custChannelSignId;
	}

	public void setCustChannelSignId(String custChannelSignId) {
		this.custChannelSignId = custChannelSignId;
	}

	public String getChannelId() {
		return this.channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getSignType() {
		return this.signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

}