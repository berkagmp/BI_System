package com.keyora.app.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keyora.app.dao.ProductDao;
import com.keyora.app.entity.Product;

@Service
@Transactional(readOnly = true)
public class ProductService {
	private Logger logger = LoggerFactory.getLogger(getClass().getName());

	@Autowired
	ProductDao productDao;

	@Transactional
	public Integer save(Product product) {
		return productDao.save(product);
	}

	public Product get(Integer productId) {
		return productDao.get(productId);
	}
	
	public Integer getMaxId() {
		return productDao.getMaxId();
	}

	public List<Product> list() {
		return productDao.list();
	}

	public List<Product> listByBrand(Integer brandId) {
		return productDao.listByBrand(brandId);
	}

	public List<Product> listByUseynAndBrandId(Boolean useyn, Integer brandId) {
		return productDao.listByUseynAndBrandId(useyn, brandId);
	}

	@Transactional
	public Integer update(Integer productId, Product productDetail) {
		return productDao.update(productId, productDetail);
	}

	@Transactional
	public Integer updateRaw(Integer productId, Float raw) {
		Product product = productDao.get(productId);
		product.setRaw(raw);
		return productDao.update(productId, product);
	}

	@Transactional
	public void delete(Integer productId) {
		productDao.delete(productId);
	}
}
