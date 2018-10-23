package com.yuchengtech.bcrm.customer.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the OCRM_F_CI_REVIEW_CONTENT database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_REVIEW_CONTENT")
public class OcrmFCiReviewContent implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_REVIEW_CONTENT_ID_GENERATOR", sequenceName="ID_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_REVIEW_CONTENT_ID_GENERATOR")
	private Long id;

	private String commentcontent;

	private Timestamp commenttime;

	@Column(name="TASK_NUMBER")
	private String taskNumber;

	private String username;

    public OcrmFCiReviewContent() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCommentcontent() {
		return this.commentcontent;
	}

	public void setCommentcontent(String commentcontent) {
		this.commentcontent = commentcontent;
	}

	public Timestamp getCommenttime() {
		return this.commenttime;
	}

	public void setCommenttime(Timestamp commenttime) {
		this.commenttime = commenttime;
	}

	public String getTaskNumber() {
		return this.taskNumber;
	}

	public void setTaskNumber(String taskNumber) {
		this.taskNumber = taskNumber;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}