package com.yuchengtech.bob.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity implementation class for Entity: CustomerContractInfo
 * 
 */
@Entity
@Table(name = "ocrm_f_ci_contract_info")
public class CustomerSignInfo implements Serializable {

    private static final long serialVersionUID = -6770928563212664334L;

    @Id
    @GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
    private Long ID;

    /** 签约日期 */
    @Temporal(TemporalType.DATE)
    private Date SIGN_DATE;

    /** 签约到期日期 */
    @Temporal(TemporalType.DATE)
    private Date SIGN_END_DATE;

    /** 签约名称 */
    @Column(length = 100)
    private String SIGN_NAME;

    /** 签约机构 */
    @Column(length = 100)
    private String SIGN_ORG;

    /** 主办机构名称 */
    @Column(length = 100)
    private String OGR_NAME;

    /** 经办人 */
    @Column(length = 100)
    private String ATTN;

    /** 签约状态 */
    @Column(length = 100)
    private String SIGN_STS;

    /** 附件 */
    @Column(length = 100)
    private String ANNEX;
    
    @Column(length = 20)
    private String CUST_ID;
    
    public String getCUST_ID() {
        return CUST_ID;
    }

    public void setCUST_ID(String cUST_ID) {
        CUST_ID = cUST_ID;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long iD) {
        ID = iD;
    }

    public Date getSIGN_DATE() {
        return SIGN_DATE;
    }

    public void setSIGN_DATE(Date sIGN_DATE) {
        SIGN_DATE = sIGN_DATE;
    }

    public Date getSIGN_END_DATE() {
        return SIGN_END_DATE;
    }

    public void setSIGN_END_DATE(Date sIGN_END_DATE) {
        SIGN_END_DATE = sIGN_END_DATE;
    }

    public String getSIGN_NAME() {
        return SIGN_NAME;
    }

    public void setSIGN_NAME(String sIGN_NAME) {
        SIGN_NAME = sIGN_NAME;
    }

    public String getSIGN_ORG() {
        return SIGN_ORG;
    }

    public void setSIGN_ORG(String sIGN_ORG) {
        SIGN_ORG = sIGN_ORG;
    }

    public String getOGR_NAME() {
        return OGR_NAME;
    }

    public void setOGR_NAME(String oGR_NAME) {
        OGR_NAME = oGR_NAME;
    }

    public String getATTN() {
        return ATTN;
    }

    public void setATTN(String aTTN) {
        ATTN = aTTN;
    }

    public String getSIGN_STS() {
        return SIGN_STS;
    }

    public void setSIGN_STS(String sIGN_STS) {
        SIGN_STS = sIGN_STS;
    }

    public String getANNEX() {
        return ANNEX;
    }

    public void setANNEX(String aNNEX) {
        ANNEX = aNNEX;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
