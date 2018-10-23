package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MCiPerLikeinfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_PER_LIKEINFO")
public class MCiPerLikeinfo implements java.io.Serializable {

	// Fields

	private String custId;
	private String likeEchanlType;
	private String likeLeisureType;
	private String likeMediaType;
	private String likeSportType;
	private String likeMagazineType;
	private String likeFilmType;
	private String likePetType;
	private String likeCollectionType;
	private String likeInvestType;
	private String likeBrandType;
	private String likeBrandText;
	private String finaServ;
	private String contactType;
	private String finaNews;
	private String salon;
	private String interests;
	private String avoid;
	private String custTaboo;
	private String other;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;

	// Constructors

	/** default constructor */
	public MCiPerLikeinfo() {
	}

	/** minimal constructor */
	public MCiPerLikeinfo(String custId) {
		this.custId = custId;
	}

	/** full constructor */
	public MCiPerLikeinfo(String custId, String likeEchanlType,
			String likeLeisureType, String likeMediaType, String likeSportType,
			String likeMagazineType, String likeFilmType, String likePetType,
			String likeCollectionType, String likeInvestType,
			String likeBrandType, String likeBrandText, String finaServ,
			String contactType, String finaNews, String salon,
			String interests, String avoid, String custTaboo, String other,
			String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo) {
		this.custId = custId;
		this.likeEchanlType = likeEchanlType;
		this.likeLeisureType = likeLeisureType;
		this.likeMediaType = likeMediaType;
		this.likeSportType = likeSportType;
		this.likeMagazineType = likeMagazineType;
		this.likeFilmType = likeFilmType;
		this.likePetType = likePetType;
		this.likeCollectionType = likeCollectionType;
		this.likeInvestType = likeInvestType;
		this.likeBrandType = likeBrandType;
		this.likeBrandText = likeBrandText;
		this.finaServ = finaServ;
		this.contactType = contactType;
		this.finaNews = finaNews;
		this.salon = salon;
		this.interests = interests;
		this.avoid = avoid;
		this.custTaboo = custTaboo;
		this.other = other;
		this.lastUpdateSys = lastUpdateSys;
		this.lastUpdateUser = lastUpdateUser;
		this.lastUpdateTm = lastUpdateTm;
		this.txSeqNo = txSeqNo;
	}

	// Property accessors
	@Id
	@Column(name = "CUST_ID", unique = true, nullable = false, length = 20)
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Column(name = "LIKE_ECHANL_TYPE", length = 200)
	public String getLikeEchanlType() {
		return this.likeEchanlType;
	}

	public void setLikeEchanlType(String likeEchanlType) {
		this.likeEchanlType = likeEchanlType;
	}

	@Column(name = "LIKE_LEISURE_TYPE", length = 200)
	public String getLikeLeisureType() {
		return this.likeLeisureType;
	}

	public void setLikeLeisureType(String likeLeisureType) {
		this.likeLeisureType = likeLeisureType;
	}

	@Column(name = "LIKE_MEDIA_TYPE", length = 200)
	public String getLikeMediaType() {
		return this.likeMediaType;
	}

	public void setLikeMediaType(String likeMediaType) {
		this.likeMediaType = likeMediaType;
	}

	@Column(name = "LIKE_SPORT_TYPE", length = 200)
	public String getLikeSportType() {
		return this.likeSportType;
	}

	public void setLikeSportType(String likeSportType) {
		this.likeSportType = likeSportType;
	}

	@Column(name = "LIKE_MAGAZINE_TYPE", length = 200)
	public String getLikeMagazineType() {
		return this.likeMagazineType;
	}

	public void setLikeMagazineType(String likeMagazineType) {
		this.likeMagazineType = likeMagazineType;
	}

	@Column(name = "LIKE_FILM_TYPE", length = 200)
	public String getLikeFilmType() {
		return this.likeFilmType;
	}

	public void setLikeFilmType(String likeFilmType) {
		this.likeFilmType = likeFilmType;
	}

	@Column(name = "LIKE_PET_TYPE", length = 200)
	public String getLikePetType() {
		return this.likePetType;
	}

	public void setLikePetType(String likePetType) {
		this.likePetType = likePetType;
	}

	@Column(name = "LIKE_COLLECTION_TYPE", length = 200)
	public String getLikeCollectionType() {
		return this.likeCollectionType;
	}

	public void setLikeCollectionType(String likeCollectionType) {
		this.likeCollectionType = likeCollectionType;
	}

	@Column(name = "LIKE_INVEST_TYPE", length = 200)
	public String getLikeInvestType() {
		return this.likeInvestType;
	}

	public void setLikeInvestType(String likeInvestType) {
		this.likeInvestType = likeInvestType;
	}

	@Column(name = "LIKE_BRAND_TYPE", length = 200)
	public String getLikeBrandType() {
		return this.likeBrandType;
	}

	public void setLikeBrandType(String likeBrandType) {
		this.likeBrandType = likeBrandType;
	}

	@Column(name = "LIKE_BRAND_TEXT", length = 200)
	public String getLikeBrandText() {
		return this.likeBrandText;
	}

	public void setLikeBrandText(String likeBrandText) {
		this.likeBrandText = likeBrandText;
	}

	@Column(name = "FINA_SERV", length = 200)
	public String getFinaServ() {
		return this.finaServ;
	}

	public void setFinaServ(String finaServ) {
		this.finaServ = finaServ;
	}

	@Column(name = "CONTACT_TYPE", length = 200)
	public String getContactType() {
		return this.contactType;
	}

	public void setContactType(String contactType) {
		this.contactType = contactType;
	}

	@Column(name = "FINA_NEWS", length = 200)
	public String getFinaNews() {
		return this.finaNews;
	}

	public void setFinaNews(String finaNews) {
		this.finaNews = finaNews;
	}

	@Column(name = "SALON", length = 200)
	public String getSalon() {
		return this.salon;
	}

	public void setSalon(String salon) {
		this.salon = salon;
	}

	@Column(name = "INTERESTS", length = 200)
	public String getInterests() {
		return this.interests;
	}

	public void setInterests(String interests) {
		this.interests = interests;
	}

	@Column(name = "AVOID", length = 200)
	public String getAvoid() {
		return this.avoid;
	}

	public void setAvoid(String avoid) {
		this.avoid = avoid;
	}

	@Column(name = "CUST_TABOO", length = 200)
	public String getCustTaboo() {
		return this.custTaboo;
	}

	public void setCustTaboo(String custTaboo) {
		this.custTaboo = custTaboo;
	}

	@Column(name = "OTHER", length = 200)
	public String getOther() {
		return this.other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	@Column(name = "LAST_UPDATE_SYS", length = 20)
	public String getLastUpdateSys() {
		return this.lastUpdateSys;
	}

	public void setLastUpdateSys(String lastUpdateSys) {
		this.lastUpdateSys = lastUpdateSys;
	}

	@Column(name = "LAST_UPDATE_USER", length = 20)
	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	@Column(name = "LAST_UPDATE_TM", length = 11)
	public Timestamp getLastUpdateTm() {
		return this.lastUpdateTm;
	}

	public void setLastUpdateTm(Timestamp lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	@Column(name = "TX_SEQ_NO", length = 32)
	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

}