package com.keyora.app.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keyora.app.dao.BlacklistDao;
import com.keyora.app.dao.ItemDao;
import com.keyora.app.entity.Blacklist;

@Service
@Transactional(readOnly = true)
public class BlacklistService {
	private Logger logger = LoggerFactory.getLogger(getClass().getName());

	@Autowired
	BlacklistDao blacklistDao;
	
	@Autowired
	ItemDao itemDao;

	@Transactional
	public Integer save(Blacklist blacklist) {
		return blacklistDao.save(blacklist); 
	}

	public Blacklist get(Integer blacklistId) {
		return blacklistDao.get(blacklistId);
	}

	public List<Blacklist> list() {
		return blacklistDao.list();
	}
	
	public List<Map<String,Object>> listWithItem() {
		return blacklistDao.listWithItem();
	}
	
	public List<Map<String, String>> listWithItemToMap() {
		return blacklistDao.listWithItemToMap();
	}

	@Transactional
	public Integer update(Integer blacklistId, Blacklist blacklistDetail) {
		return blacklistDao.update(blacklistId, blacklistDetail);
	}

	@Transactional
	public void delete(Integer blacklistId) {
		logger.info("service delete");
		blacklistDao.delete(blacklistId);
	}
}
