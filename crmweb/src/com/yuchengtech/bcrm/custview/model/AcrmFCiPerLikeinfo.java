package com.yuchengtech.bcrm.custview.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the ACRM_F_CI_PER_LIKEINFO database table.
 * 个人喜好表
 */
@Entity
@Table(name="ACRM_F_CI_PER_LIKEINFO")
public class AcrmFCiPerLikeinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CUST_ID")
	private String custId;

	private String avoid;

	@Column(name="CONTACT_TYPE")
	private String contactType;

	@Column(name="FINA_NEWS")
	private String finaNews;

	@Column(name="FINA_SERV")
	private String finaServ;

	private String interests;

	@Column(name="LAST_UPDATE_SYS")
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM")
	private Timestamp lastUpdateTm;

	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;

	@Column(name="LIKE_BRAND_TEXT")
	private String likeBrandText;

	@Column(name="LIKE_BRAND_TYPE")
	private String likeBrandType;

	@Column(name="LIKE_COLLECTION_TYPE")
	private String likeCollectionType;

	@Column(name="LIKE_ECHANL_TYPE")
	private String likeEchanlType;
	
//	@Column(name="LIKE_BUSI_TYPE")//金融业务类型
//	private String likeBusiType;
	
	@Column(name="CUST_TABOO")//特别需求
    private String custTaboo;
	
//	@Column(name="CUST_RELIGION")//宗教信仰
//    private String custReligion;
	
//	@Column(name="LIKE_CONTACT_TIME")//希望联系的时间
//	private String likeContactTime;
	
	@Column(name="LIKE_FILM_TYPE")
	private String likeFilmType;

	@Column(name="LIKE_INVEST_TYPE")
	private String likeInvestType;

	@Column(name="LIKE_LEISURE_TYPE")
	private String likeLeisureType;

	@Column(name="LIKE_MAGAZINE_TYPE")
	private String likeMagazineType;

	@Column(name="LIKE_MEDIA_TYPE")
	private String likeMediaType;

	@Column(name="LIKE_PET_TYPE")
	private String likePetType;

	@Column(name="LIKE_SPORT_TYPE")
	private String likeSportType;

	private String other;

	private String salon;

	@Column(name="TX_SEQ_NO")
	private String txSeqNo;

//    public String getLikeContactTime() {
//		return likeContactTime;
//	}
//
//	public void setLikeContactTime(String likeContactTime) {
//		this.likeContactTime = likeContactTime;
//	}

//	public String getLikeBusiType() {
//		return likeBusiType;
//	}
//
//	public void setLikeBusiType(String likeBusiType) {
//		this.likeBusiType = likeBusiType;
//	}

	public String getCustTaboo() {
		return custTaboo;
	}

	public void setCustTaboo(String custTaboo) {
		this.custTaboo = custTaboo;
	}

//	public String getCustReligion() {
//		return custReligion;
//	}
//
//	public void setCustReligion(String custReligion) {
//		this.custReligion = custReligion;
//	}

	public AcrmFCiPerLikeinfo() {
    }

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getAvoid() {
		return this.avoid;
	}

	public void setAvoid(String avoid) {
		this.avoid = avoid;
	}

	public String getContactType() {
		return this.contactType;
	}

	public void setContactType(String contactType) {
		this.contactType = contactType;
	}

	public String getFinaNews() {
		return this.finaNews;
	}

	public void setFinaNews(String finaNews) {
		this.finaNews = finaNews;
	}

	public String getFinaServ() {
		return this.finaServ;
	}

	public void setFinaServ(String finaServ) {
		this.finaServ = finaServ;
	}

	public String getInterests() {
		return this.interests;
	}

	public void setInterests(String interests) {
		this.interests = interests;
	}

	public String getLastUpdateSys() {
		return this.lastUpdateSys;
	}

	public void setLastUpdateSys(String lastUpdateSys) {
		this.lastUpdateSys = lastUpdateSys;
	}

	public Timestamp getLastUpdateTm() {
		return this.lastUpdateTm;
	}

	public void setLastUpdateTm(Timestamp lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public String getLikeBrandText() {
		return this.likeBrandText;
	}

	public void setLikeBrandText(String likeBrandText) {
		this.likeBrandText = likeBrandText;
	}

	public String getLikeBrandType() {
		return this.likeBrandType;
	}

	public void setLikeBrandType(String likeBrandType) {
		this.likeBrandType = likeBrandType;
	}

	public String getLikeCollectionType() {
		return this.likeCollectionType;
	}

	public void setLikeCollectionType(String likeCollectionType) {
		this.likeCollectionType = likeCollectionType;
	}

	public String getLikeEchanlType() {
		return this.likeEchanlType;
	}

	public void setLikeEchanlType(String likeEchanlType) {
		this.likeEchanlType = likeEchanlType;
	}

	public String getLikeFilmType() {
		return this.likeFilmType;
	}

	public void setLikeFilmType(String likeFilmType) {
		this.likeFilmType = likeFilmType;
	}

	public String getLikeInvestType() {
		return this.likeInvestType;
	}

	public void setLikeInvestType(String likeInvestType) {
		this.likeInvestType = likeInvestType;
	}

	public String getLikeLeisureType() {
		return this.likeLeisureType;
	}

	public void setLikeLeisureType(String likeLeisureType) {
		this.likeLeisureType = likeLeisureType;
	}

	public String getLikeMagazineType() {
		return this.likeMagazineType;
	}

	public void setLikeMagazineType(String likeMagazineType) {
		this.likeMagazineType = likeMagazineType;
	}

	public String getLikeMediaType() {
		return this.likeMediaType;
	}

	public void setLikeMediaType(String likeMediaType) {
		this.likeMediaType = likeMediaType;
	}

	public String getLikePetType() {
		return this.likePetType;
	}

	public void setLikePetType(String likePetType) {
		this.likePetType = likePetType;
	}

	public String getLikeSportType() {
		return this.likeSportType;
	}

	public void setLikeSportType(String likeSportType) {
		this.likeSportType = likeSportType;
	}

	public String getOther() {
		return this.other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getSalon() {
		return this.salon;
	}

	public void setSalon(String salon) {
		this.salon = salon;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

}