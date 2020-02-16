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
@Entity(name = "product")
@Table(name = "product")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "useyn", nullable = false)
	private Boolean useyn = true;

	@Column(name = "raw", nullable = false)
	private Float raw;

	@Column(name = "keyword", nullable = false)
	private String keyword;

	@Column(name = "last_update_date", insertable = false, updatable = false)
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date lastUpdateDate;

	@Column(name = "brand_id", nullable = false)
	private Integer brandId;

	public Product() {}

	public Product(String name, Boolean useyn, Float raw, String keyword, Integer brandId) {
		this.name = name;
		this.useyn = useyn;
		this.raw = raw;
		this.keyword = keyword;
		this.brandId = brandId;
	}
}
