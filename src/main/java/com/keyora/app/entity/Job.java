package com.keyora.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity(name = "job")
@Table(name = "job")
public class Job {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "job_type")
	private String jobType;

	@Column(name = "job_date")
	private String jobDate;

	public Job() {
	}

	public Job(String jobType, String jobDate) {
		this.jobType = jobType;
		this.jobDate = jobDate;
	}

}
