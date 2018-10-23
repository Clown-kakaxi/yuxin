package com.yuchengtech.emp.ecif.customer.entity.customerdealings;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the CHANNELINFO database table.
 * 
 */
@Entity
@Table(name="CHANNELINFO")
public class Channelinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CHANNEL_ID", unique=true, nullable=false)
	private Long channelId;

	@Column(name="CHANNEL_NAME", length=30)
	private String channelName;

	@Column(name="CHANNEL_NO", length=2)
	private String channelNo;

    public Channelinfo() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getChannelId() {
		return this.channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public String getChannelName() {
		return this.channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getChannelNo() {
		return this.channelNo;
	}

	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}

}