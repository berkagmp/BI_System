package com.keyora.app.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keyora.app.dao.BrandDao;
import com.keyora.app.dao.ProductDao;
import com.keyora.app.entity.Brand;

@Service
@Transactional(readOnly = true)
public class BrandService {

	private Logger logger = LoggerFactory.getLogger(getClass().getName());

	@Autowired
	BrandDao brandDao;

	@Autowired
	ProductDao productDao;

	@Transactional
	public Integer save(Brand brand) {
		return brandDao.save(brand);
	}

	public Brand get(Integer brandId) {
		return brandDao.get(brandId);
	}

	public List<Brand> list() {
		return brandDao.list();
	}

	public List<Brand> listByUseyn(Boolean useyn) {
		return brandDao.listByUseyn(useyn);
	}

	@Transactional
	public Integer update(Integer brandId, Brand brandDetail) {
		productDao.updateUseyn(brandId, brandDetail);
		return brandDao.update(brandId, brandDetail);
	}

	@Transactional
	public void delete(Integer brandId) {
		productDao.deleteByBrandId(brandId);
		brandDao.delete(brandId);
	}
}
