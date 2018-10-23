package com.yuchengtech.bcrm.custview.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 客户事件信息
 */
@Entity
@Table(name = "ACRM_F_CI_EVENT")
public class CustomerEvent implements Serializable {

    private static final long serialVersionUID = 2020000344145516058L;

    @Id
    @SequenceGenerator(name="ACRM_F_CI_EVENT_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACRM_F_CI_EVENT_ID_GENERATOR")
	@Column(name="EVENT_ID",unique=true, nullable=false)
    private Long EVENT_ID;

    @Temporal(TemporalType.DATE)
    @Column(name = "CRM_DT")
    private Date CRM_DT;// 平台日期

    @Column(name = "CUST_ID", length = 32)
    private String CUST_ID;// 客户编号

    @Column(name = "CUST_NAME", length = 200)
    private String CUST_NAME;// 客户名称

    @Column(name = "EVENT_DESC", length = 800)
    private String EVENT_DESC;// 事件信息

//    @Column(name = "EVENT_ID")
//    private Long EVENT_ID;// 事件编号

    @Column(name = "EVENT_NAME", length = 200)
    private String EVENT_NAME;// 事件名称

    @Column(name = "EVENT_TYP", length = 100)
    private String EVENT_TYP;// 事件类型

    @Temporal(TemporalType.DATE)
    @Column(name = "FS_DT")
    private Date FS_DT;// 发生日期

    @Column(name = "WARN_FLG", length = 13)
    private String WARN_FLG;// 是否提醒

    @Temporal(TemporalType.DATE)
    @Column(name = "WHDT")
    private Date WHDT;// 维护日期

    @Column(name = "WHRY", length = 200)
    private String WHRY;// 维护人员

//    public Long getID() {
//        return ID;
//    }
//
//    public void setID(Long iD) {
//        ID = iD;
//    }

    public Date getCRM_DT() {
        return CRM_DT;
    }

    public void setCRM_DT(Date cRM_DT) {
        CRM_DT = cRM_DT;
    }

    public String getCUST_ID() {
        return CUST_ID;
    }

    public void setCUST_ID(String cUST_ID) {
        CUST_ID = cUST_ID;
    }

    public String getCUST_NAME() {
        return CUST_NAME;
    }

    public void setCUST_NAME(String cUST_NAME) {
        CUST_NAME = cUST_NAME;
    }

    public String getEVENT_DESC() {
        return EVENT_DESC;
    }

    public void setEVENT_DESC(String eVENT_DESC) {
        EVENT_DESC = eVENT_DESC;
    }

   

   

	public Long getEVENT_ID() {
		return EVENT_ID;
	}

	public void setEVENT_ID(Long eVENTID) {
		EVENT_ID = eVENTID;
	}

	public String getEVENT_NAME() {
        return EVENT_NAME;
    }

    public void setEVENT_NAME(String eVENT_NAME) {
        EVENT_NAME = eVENT_NAME;
    }

    public String getEVENT_TYP() {
        return EVENT_TYP;
    }

    public void setEVENT_TYP(String eVENT_TYP) {
        EVENT_TYP = eVENT_TYP;
    }

    public Date getFS_DT() {
        return FS_DT;
    }

    public void setFS_DT(Date fS_DT) {
        FS_DT = fS_DT;
    }

    public String getWARN_FLG() {
        return WARN_FLG;
    }

    public void setWARN_FLG(String wARN_FLG) {
        WARN_FLG = wARN_FLG;
    }

    public Date getWHDT() {
        return WHDT;
    }

    public void setWHDT(Date wHDT) {
        WHDT = wHDT;
    }

    public String getWHRY() {
        return WHRY;
    }

    public void setWHRY(String wHRY) {
        WHRY = wHRY;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}