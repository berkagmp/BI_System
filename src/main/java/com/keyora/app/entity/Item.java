package com.keyora.app.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.util.StringUtils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity(name = "item")
@Table(name = "item")
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "product_name", nullable = false)
	private String title;

	@Column(nullable = false)
	private String link;

	@Transient
	private String image;

	@Column(name = "price", nullable = false)
	private Integer lprice;

	@Transient
	private String hprice;

	@Column(name = "delivery_fee", nullable = true)
	private String deliveryFee;

	@Column(name = "sum", nullable = true)
	private Integer sum;

	@Column(name = "seller", nullable = false)
	private String mallName;

	@Column(name = "product_id", nullable = false)
	private String productId;

	@Column(name = "product_type", nullable = false)
	private String productType;

	@Column(name = "insert_date", insertable = false, updatable = false)
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date insertDate;
	
	@Column(name = "useyn", nullable = false)
	private Boolean useyn = true;

	@Column(name = "keyword_id", nullable = false)
	private Integer keywordId;

	@PrePersist
	void preInsert() {
		if (StringUtils.isEmpty(this.deliveryFee))
			this.deliveryFee = "0";
	}
	
}
