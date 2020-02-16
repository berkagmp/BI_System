package com.keyora.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keyora.app.dao.JobDao;

@Service
public class JobService {
	@Autowired
	private JobDao jobDao;

//	@Transactional
//	public void update() {
//		jobDao.update();
//	}
	
	@Transactional
	public void save(String jobType) {
		jobDao.save(jobType);
	}
}