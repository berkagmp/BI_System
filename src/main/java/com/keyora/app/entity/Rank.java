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
@Entity(name = "rank")
@Table(name = "rank")
public class Rank {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "rank", nullable = false)
	private Integer rank;

	@Column(name = "keyword", nullable = false)
	private String keyword;

	@Column(name = "fluctuation", nullable = false)
	private String fluctuation;

	@Column(name = "insert_date", insertable = false, updatable = false)
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date insertDate;

	public Rank() {
	}

	public Rank(Integer rank, String keyword, String fluctuation) {
		this.rank = rank;
		this.keyword = keyword;
		this.fluctuation = fluctuation;
	}

}
