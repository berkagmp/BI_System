package com.keyora.app.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keyora.app.dao.ErrDao;
import com.keyora.app.entity.Err;

@Service
@Transactional(readOnly = true)
public class ErrService {
	private Logger logger = LoggerFactory.getLogger(getClass().getName());

	@Autowired
	ErrDao errDao;

	@Transactional
	public Integer save(Err err) {
		return errDao.save(err);
	}

	public Err get(Integer errId) {
		return errDao.get(errId);
	}

	public List<Err> list() {
		return errDao.list();
	}

	@Transactional
	public void delete(Integer errId) {
		errDao.delete(errId);
	}
}
