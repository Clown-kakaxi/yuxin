package com.yuchengtech.emp.ecif.customer.entity.customerdealings;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the PERSONCHANNELSIGN database table.
 * 
 */
public class PersonchannelsignVO {

	private String custChannelSignId;

	private String channelId;

	private String channelName;
	
	private Long custId;

	private String signType;

    public PersonchannelsignVO() {
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

    public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
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