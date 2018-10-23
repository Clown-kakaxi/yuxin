package com.yuchengtech.bcrm.custview.model;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ACRM_F_SOS_SERVICE database table.
 * 
 */
@Entity
@Table(name="ACRM_F_SOS_SERVICE")
public class AcrmFSosService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACRM_A_CARD_USED_ID_GENERATOR", sequenceName="ID_SEQUENCE",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACRM_A_CARD_USED_ID_GENERATOR")
	@Column(name="ID")
	private Long id;
	
	@Column(name="CUST_CORE_ID")
	private String custCoreId;

	@Column(name="CUST_NAME")
	private String custName;

    @Temporal( TemporalType.DATE)
	@Column(name="SERVICE_DAY")
	private Date serviceDay;

    @Temporal( TemporalType.DATE)
	@Column(name="SERVICE_ENDTIME")
	private Date serviceEndtime;

	@Column(name="SERVICE_REMNANT")
	private BigDecimal serviceRemnant;

    @Temporal( TemporalType.DATE)
	@Column(name="SERVICE_STARTTIME")
	private Date serviceStarttime;

	@Column(name="SERVICE_TIMES")
	private BigDecimal serviceTimes;

    public AcrmFSosService() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustCoreId() {
		return this.custCoreId;
	}

	public void setCustCoreId(String custCoreId) {
		this.custCoreId = custCoreId;
	}

	public String getCustName() {
		return this.custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public Date getServiceDay() {
		return this.serviceDay;
	}

	public void setServiceDay(Date serviceDay) {
		this.serviceDay = serviceDay;
	}

	public Date getServiceEndtime() {
		return this.serviceEndtime;
	}

	public void setServiceEndtime(Date serviceEndtime) {
		this.serviceEndtime = serviceEndtime;
	}

	public BigDecimal getServiceRemnant() {
		return this.serviceRemnant;
	}

	public void setServiceRemnant(BigDecimal serviceRemnant) {
		this.serviceRemnant = serviceRemnant;
	}

	public Date getServiceStarttime() {
		return this.serviceStarttime;
	}

	public void setServiceStarttime(Date serviceStarttime) {
		this.serviceStarttime = serviceStarttime;
	}

	public BigDecimal getServiceTimes() {
		return this.serviceTimes;
	}

	public void setServiceTimes(BigDecimal serviceTimes) {
		this.serviceTimes = serviceTimes;
	}

}