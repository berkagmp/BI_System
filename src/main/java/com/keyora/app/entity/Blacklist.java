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
@Entity(name = "blacklist")
@Table(name = "blacklist")
public class Blacklist {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "product_id", nullable = false)
	private String productId;

	@Column(name = "user_id", nullable = false)
	private String userId;

	@Column(name = "keyword_id", nullable = false)
	private Integer keywordId;

	@Column(name = "listed_date", insertable = false, updatable = false)
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date listedDate;

	public Blacklist() {
	}

	public Blacklist(String productId, String userId, Integer keywordId, Date listedDate) {
		this.productId = productId;
		this.userId = userId;
		this.keywordId = keywordId;
		this.listedDate = listedDate;
	}

}
