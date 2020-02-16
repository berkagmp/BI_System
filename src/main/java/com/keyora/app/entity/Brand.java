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
@Entity(name = "brand")
@Table(name = "brand")
public class Brand {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "useyn", nullable = false)
	private Boolean useyn = true;

	@Column(name = "last_update_date", insertable = false, updatable = false)
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date lastUpdateDate;

	public Brand() {}

	public Brand(String name, Boolean useyn) {
		this.name = name;
		this.useyn = useyn;
	}

}
