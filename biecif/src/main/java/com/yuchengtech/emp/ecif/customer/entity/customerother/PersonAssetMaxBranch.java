package com.yuchengtech.emp.ecif.customer.entity.customerother;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;

/**
 * 
 * 
 * <pre>
 * Title:PersonAssetMaxBranch的实体类
 * Description: 个人资产最大行信息
 * </pre>
 * 
 * @author zhengyukun zhengyk3@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Entity
@Table(name = "PERSONASSETMAXBRANCH")
public class PersonAssetMaxBranch implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CUST_ID", unique = true, nullable = false)
	private Long custId;
	
	@Column(name = "ASSET_MAX_ORG",length = 9)
	private String assetMaxOrg;
	
	@Column(name = "ASSET_MAX_BAL")
	private Double assetMaxBal;
	
	@Column(name = "ASSET_MAX_BRANCH",length = 9)
	private String assetMaxBranch;
	
	@Column(name = "ASSET_MAX_SUBBRANCH",length = 9)
	private String assetMaxSubbranch;

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getAssetMaxOrg() {
		return assetMaxOrg;
	}

	public void setAssetMaxOrg(String assetMaxOrg) {
		this.assetMaxOrg = assetMaxOrg;
	}

	public Double getAssetMaxBal() {
		return assetMaxBal;
	}

	public void setAssetMaxBal(Double assetMaxBal) {
		this.assetMaxBal = assetMaxBal;
	}

	public String getAssetMaxBranch() {
		return assetMaxBranch;
	}

	public void setAssetMaxBranch(String assetMaxBranch) {
		this.assetMaxBranch = assetMaxBranch;
	}

	public String getAssetMaxSubbranch() {
		return assetMaxSubbranch;
	}

	public void setAssetMaxSubbranch(String assetMaxSubbranch) {
		this.assetMaxSubbranch = assetMaxSubbranch;
	}
	
}

