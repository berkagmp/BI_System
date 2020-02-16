package com.keyora.app.dao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.keyora.app.entity.Job;

@Repository
public class JobDao {

	@Autowired
	private SessionFactory sessionFactory;

//	public void update() {
//		Session session = sessionFactory.getCurrentSession();
//
//		Job tempJob = session.byId(Job.class).load(1);
//		tempJob.setJobType("U");
//		tempJob.setJobDate(LocalDateTime.now()
//				.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//
//		session.flush();
//	}

	public void save(String jobType) {
		sessionFactory.getCurrentSession()
				.save(new Job(jobType, LocalDateTime.now()
						.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
	}
}
