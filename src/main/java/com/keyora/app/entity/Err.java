package com.keyora.app.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity(name = "error")
@Table(name = "error")
public class Err {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "keyword_id", nullable = false)
	private Integer keywordId;

	@Column(name = "keyword", nullable = false)
	private String keyword;

	@Column(name = "err_msg", nullable = true)
	private String errMsg;

	@Column(name = "error_date", insertable = false, updatable = false)
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date errorDate;

	public Err() {
	}

	public Err(String keyword, Integer keywordId, String errMsg) {
		this.keyword = keyword;
		this.keywordId = keywordId;
		this.errMsg = errMsg;
	}

}
