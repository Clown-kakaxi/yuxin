package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiPerLikeinfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_PER_LIKEINFO")
public class HMCiPerLikeinfo implements java.io.Serializable {

	// Fields

	private HMCiPerLikeinfoId id;

	// Constructors

	/** default constructor */
	public HMCiPerLikeinfo() {
	}

	/** full constructor */
	public HMCiPerLikeinfo(HMCiPerLikeinfoId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "custId", column = @Column(name = "CUST_ID", length = 20)),
			@AttributeOverride(name = "likeEchanlType", column = @Column(name = "LIKE_ECHANL_TYPE", length = 200)),
			@AttributeOverride(name = "likeLeisureType", column = @Column(name = "LIKE_LEISURE_TYPE", length = 200)),
			@AttributeOverride(name = "likeMediaType", column = @Column(name = "LIKE_MEDIA_TYPE", length = 200)),
			@AttributeOverride(name = "likeSportType", column = @Column(name = "LIKE_SPORT_TYPE", length = 200)),
			@AttributeOverride(name = "likeMagazineType", column = @Column(name = "LIKE_MAGAZINE_TYPE", length = 200)),
			@AttributeOverride(name = "likeFilmType", column = @Column(name = "LIKE_FILM_TYPE", length = 200)),
			@AttributeOverride(name = "likePetType", column = @Column(name = "LIKE_PET_TYPE", length = 200)),
			@AttributeOverride(name = "likeCollectionType", column = @Column(name = "LIKE_COLLECTION_TYPE", length = 200)),
			@AttributeOverride(name = "likeInvestType", column = @Column(name = "LIKE_INVEST_TYPE", length = 200)),
			@AttributeOverride(name = "likeBrandType", column = @Column(name = "LIKE_BRAND_TYPE", length = 200)),
			@AttributeOverride(name = "likeBrandText", column = @Column(name = "LIKE_BRAND_TEXT", length = 200)),
			@AttributeOverride(name = "finaServ", column = @Column(name = "FINA_SERV", length = 200)),
			@AttributeOverride(name = "contactType", column = @Column(name = "CONTACT_TYPE", length = 200)),
			@AttributeOverride(name = "finaNews", column = @Column(name = "FINA_NEWS", length = 200)),
			@AttributeOverride(name = "salon", column = @Column(name = "SALON", length = 200)),
			@AttributeOverride(name = "interests", column = @Column(name = "INTERESTS", length = 200)),
			@AttributeOverride(name = "avoid", column = @Column(name = "AVOID", length = 200)),
			@AttributeOverride(name = "custTaboo", column = @Column(name = "CUST_TABOO", length = 200)),
			@AttributeOverride(name = "other", column = @Column(name = "OTHER", length = 200)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM", length = 11)),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false, length = 11)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMCiPerLikeinfoId getId() {
		return this.id;
	}

	public void setId(HMCiPerLikeinfoId id) {
		this.id = id;
	}

}