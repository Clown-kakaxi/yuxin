package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * HMCiPerLikeinfoId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class HMCiPerLikeinfoId implements java.io.Serializable {

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
	private String hisOperSys;
	private String hisOperType;
	private Timestamp hisOperTime;
	private String hisDataDate;

	// Constructors

	/** default constructor */
	public HMCiPerLikeinfoId() {
	}

	/** minimal constructor */
	public HMCiPerLikeinfoId(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	/** full constructor */
	public HMCiPerLikeinfoId(String custId, String likeEchanlType,
			String likeLeisureType, String likeMediaType, String likeSportType,
			String likeMagazineType, String likeFilmType, String likePetType,
			String likeCollectionType, String likeInvestType,
			String likeBrandType, String likeBrandText, String finaServ,
			String contactType, String finaNews, String salon,
			String interests, String avoid, String custTaboo, String other,
			String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo, String hisOperSys,
			String hisOperType, Timestamp hisOperTime, String hisDataDate) {
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
		this.hisOperSys = hisOperSys;
		this.hisOperType = hisOperType;
		this.hisOperTime = hisOperTime;
		this.hisDataDate = hisDataDate;
	}

	// Property accessors

	@Column(name = "CUST_ID", length = 20)
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

	@Column(name = "HIS_OPER_SYS", length = 20)
	public String getHisOperSys() {
		return this.hisOperSys;
	}

	public void setHisOperSys(String hisOperSys) {
		this.hisOperSys = hisOperSys;
	}

	@Column(name = "HIS_OPER_TYPE", length = 2)
	public String getHisOperType() {
		return this.hisOperType;
	}

	public void setHisOperType(String hisOperType) {
		this.hisOperType = hisOperType;
	}

	@Column(name = "HIS_OPER_TIME", nullable = false, length = 11)
	public Timestamp getHisOperTime() {
		return this.hisOperTime;
	}

	public void setHisOperTime(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	@Column(name = "HIS_DATA_DATE", length = 10)
	public String getHisDataDate() {
		return this.hisDataDate;
	}

	public void setHisDataDate(String hisDataDate) {
		this.hisDataDate = hisDataDate;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof HMCiPerLikeinfoId))
			return false;
		HMCiPerLikeinfoId castOther = (HMCiPerLikeinfoId) other;

		return ((this.getCustId() == castOther.getCustId()) || (this
				.getCustId() != null
				&& castOther.getCustId() != null && this.getCustId().equals(
				castOther.getCustId())))
				&& ((this.getLikeEchanlType() == castOther.getLikeEchanlType()) || (this
						.getLikeEchanlType() != null
						&& castOther.getLikeEchanlType() != null && this
						.getLikeEchanlType().equals(
								castOther.getLikeEchanlType())))
				&& ((this.getLikeLeisureType() == castOther
						.getLikeLeisureType()) || (this.getLikeLeisureType() != null
						&& castOther.getLikeLeisureType() != null && this
						.getLikeLeisureType().equals(
								castOther.getLikeLeisureType())))
				&& ((this.getLikeMediaType() == castOther.getLikeMediaType()) || (this
						.getLikeMediaType() != null
						&& castOther.getLikeMediaType() != null && this
						.getLikeMediaType()
						.equals(castOther.getLikeMediaType())))
				&& ((this.getLikeSportType() == castOther.getLikeSportType()) || (this
						.getLikeSportType() != null
						&& castOther.getLikeSportType() != null && this
						.getLikeSportType()
						.equals(castOther.getLikeSportType())))
				&& ((this.getLikeMagazineType() == castOther
						.getLikeMagazineType()) || (this.getLikeMagazineType() != null
						&& castOther.getLikeMagazineType() != null && this
						.getLikeMagazineType().equals(
								castOther.getLikeMagazineType())))
				&& ((this.getLikeFilmType() == castOther.getLikeFilmType()) || (this
						.getLikeFilmType() != null
						&& castOther.getLikeFilmType() != null && this
						.getLikeFilmType().equals(castOther.getLikeFilmType())))
				&& ((this.getLikePetType() == castOther.getLikePetType()) || (this
						.getLikePetType() != null
						&& castOther.getLikePetType() != null && this
						.getLikePetType().equals(castOther.getLikePetType())))
				&& ((this.getLikeCollectionType() == castOther
						.getLikeCollectionType()) || (this
						.getLikeCollectionType() != null
						&& castOther.getLikeCollectionType() != null && this
						.getLikeCollectionType().equals(
								castOther.getLikeCollectionType())))
				&& ((this.getLikeInvestType() == castOther.getLikeInvestType()) || (this
						.getLikeInvestType() != null
						&& castOther.getLikeInvestType() != null && this
						.getLikeInvestType().equals(
								castOther.getLikeInvestType())))
				&& ((this.getLikeBrandType() == castOther.getLikeBrandType()) || (this
						.getLikeBrandType() != null
						&& castOther.getLikeBrandType() != null && this
						.getLikeBrandType()
						.equals(castOther.getLikeBrandType())))
				&& ((this.getLikeBrandText() == castOther.getLikeBrandText()) || (this
						.getLikeBrandText() != null
						&& castOther.getLikeBrandText() != null && this
						.getLikeBrandText()
						.equals(castOther.getLikeBrandText())))
				&& ((this.getFinaServ() == castOther.getFinaServ()) || (this
						.getFinaServ() != null
						&& castOther.getFinaServ() != null && this
						.getFinaServ().equals(castOther.getFinaServ())))
				&& ((this.getContactType() == castOther.getContactType()) || (this
						.getContactType() != null
						&& castOther.getContactType() != null && this
						.getContactType().equals(castOther.getContactType())))
				&& ((this.getFinaNews() == castOther.getFinaNews()) || (this
						.getFinaNews() != null
						&& castOther.getFinaNews() != null && this
						.getFinaNews().equals(castOther.getFinaNews())))
				&& ((this.getSalon() == castOther.getSalon()) || (this
						.getSalon() != null
						&& castOther.getSalon() != null && this.getSalon()
						.equals(castOther.getSalon())))
				&& ((this.getInterests() == castOther.getInterests()) || (this
						.getInterests() != null
						&& castOther.getInterests() != null && this
						.getInterests().equals(castOther.getInterests())))
				&& ((this.getAvoid() == castOther.getAvoid()) || (this
						.getAvoid() != null
						&& castOther.getAvoid() != null && this.getAvoid()
						.equals(castOther.getAvoid())))
				&& ((this.getCustTaboo() == castOther.getCustTaboo()) || (this
						.getCustTaboo() != null
						&& castOther.getCustTaboo() != null && this
						.getCustTaboo().equals(castOther.getCustTaboo())))
				&& ((this.getOther() == castOther.getOther()) || (this
						.getOther() != null
						&& castOther.getOther() != null && this.getOther()
						.equals(castOther.getOther())))
				&& ((this.getLastUpdateSys() == castOther.getLastUpdateSys()) || (this
						.getLastUpdateSys() != null
						&& castOther.getLastUpdateSys() != null && this
						.getLastUpdateSys()
						.equals(castOther.getLastUpdateSys())))
				&& ((this.getLastUpdateUser() == castOther.getLastUpdateUser()) || (this
						.getLastUpdateUser() != null
						&& castOther.getLastUpdateUser() != null && this
						.getLastUpdateUser().equals(
								castOther.getLastUpdateUser())))
				&& ((this.getLastUpdateTm() == castOther.getLastUpdateTm()) || (this
						.getLastUpdateTm() != null
						&& castOther.getLastUpdateTm() != null && this
						.getLastUpdateTm().equals(castOther.getLastUpdateTm())))
				&& ((this.getTxSeqNo() == castOther.getTxSeqNo()) || (this
						.getTxSeqNo() != null
						&& castOther.getTxSeqNo() != null && this.getTxSeqNo()
						.equals(castOther.getTxSeqNo())))
				&& ((this.getHisOperSys() == castOther.getHisOperSys()) || (this
						.getHisOperSys() != null
						&& castOther.getHisOperSys() != null && this
						.getHisOperSys().equals(castOther.getHisOperSys())))
				&& ((this.getHisOperType() == castOther.getHisOperType()) || (this
						.getHisOperType() != null
						&& castOther.getHisOperType() != null && this
						.getHisOperType().equals(castOther.getHisOperType())))
				&& ((this.getHisOperTime() == castOther.getHisOperTime()) || (this
						.getHisOperTime() != null
						&& castOther.getHisOperTime() != null && this
						.getHisOperTime().equals(castOther.getHisOperTime())))
				&& ((this.getHisDataDate() == castOther.getHisDataDate()) || (this
						.getHisDataDate() != null
						&& castOther.getHisDataDate() != null && this
						.getHisDataDate().equals(castOther.getHisDataDate())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getCustId() == null ? 0 : this.getCustId().hashCode());
		result = 37
				* result
				+ (getLikeEchanlType() == null ? 0 : this.getLikeEchanlType()
						.hashCode());
		result = 37
				* result
				+ (getLikeLeisureType() == null ? 0 : this.getLikeLeisureType()
						.hashCode());
		result = 37
				* result
				+ (getLikeMediaType() == null ? 0 : this.getLikeMediaType()
						.hashCode());
		result = 37
				* result
				+ (getLikeSportType() == null ? 0 : this.getLikeSportType()
						.hashCode());
		result = 37
				* result
				+ (getLikeMagazineType() == null ? 0 : this
						.getLikeMagazineType().hashCode());
		result = 37
				* result
				+ (getLikeFilmType() == null ? 0 : this.getLikeFilmType()
						.hashCode());
		result = 37
				* result
				+ (getLikePetType() == null ? 0 : this.getLikePetType()
						.hashCode());
		result = 37
				* result
				+ (getLikeCollectionType() == null ? 0 : this
						.getLikeCollectionType().hashCode());
		result = 37
				* result
				+ (getLikeInvestType() == null ? 0 : this.getLikeInvestType()
						.hashCode());
		result = 37
				* result
				+ (getLikeBrandType() == null ? 0 : this.getLikeBrandType()
						.hashCode());
		result = 37
				* result
				+ (getLikeBrandText() == null ? 0 : this.getLikeBrandText()
						.hashCode());
		result = 37 * result
				+ (getFinaServ() == null ? 0 : this.getFinaServ().hashCode());
		result = 37
				* result
				+ (getContactType() == null ? 0 : this.getContactType()
						.hashCode());
		result = 37 * result
				+ (getFinaNews() == null ? 0 : this.getFinaNews().hashCode());
		result = 37 * result
				+ (getSalon() == null ? 0 : this.getSalon().hashCode());
		result = 37 * result
				+ (getInterests() == null ? 0 : this.getInterests().hashCode());
		result = 37 * result
				+ (getAvoid() == null ? 0 : this.getAvoid().hashCode());
		result = 37 * result
				+ (getCustTaboo() == null ? 0 : this.getCustTaboo().hashCode());
		result = 37 * result
				+ (getOther() == null ? 0 : this.getOther().hashCode());
		result = 37
				* result
				+ (getLastUpdateSys() == null ? 0 : this.getLastUpdateSys()
						.hashCode());
		result = 37
				* result
				+ (getLastUpdateUser() == null ? 0 : this.getLastUpdateUser()
						.hashCode());
		result = 37
				* result
				+ (getLastUpdateTm() == null ? 0 : this.getLastUpdateTm()
						.hashCode());
		result = 37 * result
				+ (getTxSeqNo() == null ? 0 : this.getTxSeqNo().hashCode());
		result = 37
				* result
				+ (getHisOperSys() == null ? 0 : this.getHisOperSys()
						.hashCode());
		result = 37
				* result
				+ (getHisOperType() == null ? 0 : this.getHisOperType()
						.hashCode());
		result = 37
				* result
				+ (getHisOperTime() == null ? 0 : this.getHisOperTime()
						.hashCode());
		result = 37
				* result
				+ (getHisDataDate() == null ? 0 : this.getHisDataDate()
						.hashCode());
		return result;
	}

}