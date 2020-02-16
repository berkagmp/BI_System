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
import javax.persistence.Transient;

import org.hibernate.annotations.Immutable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity(name = "item_view")
@Table(name = "item_view")
@Immutable
public class ItemView {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "product_name")
	private String title;

	@Column(nullable = false)
	private String link;

	@Transient
	private String image;

	@Column(name = "price")
	private Integer lprice;

	@Transient
	private String hprice;

	@Column(name = "delivery_fee")
	private String deliveryFee;

	@Column(name = "sum")
	private Integer sum;

	@Column(name = "raw")
	private Float raw;

	@Column(name = "seller")
	private String mallName;

	@Column(name = "product_id")
	private String productId;

	@Column(name = "product_type")
	private String productType;

	@Column(name = "insert_date")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date insertDate;

	@Column(name = "keyword_id")
	private Integer keywordId;

	@Column(name = "p_id")
	private Integer pId;
}
