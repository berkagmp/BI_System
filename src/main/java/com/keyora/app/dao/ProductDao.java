package com.keyora.app.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.keyora.app.entity.Brand;
import com.keyora.app.entity.Product;

@Repository
public class ProductDao {
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	public Integer save(Product product) {
		sessionFactory.getCurrentSession().save(product);
		return product.getId();
	}

	public Product get(Integer id) {
		return sessionFactory.getCurrentSession().get(Product.class, id);
	}
	
	public Integer getMaxId() {
		String sql = "select max(id) from product"; 
		Integer result = jdbcTemplate.queryForObject(sql, Integer.class);

		return result;
	}

	public List<Product> list() {
		Session session = sessionFactory.getCurrentSession();

		CriteriaBuilder cb = session.getCriteriaBuilder();

		CriteriaQuery<Product> cq = cb.createQuery(Product.class);
		Root<Product> root = cq.from(Product.class);

		cq.select(root);

		Query<Product> query = session.createQuery(cq);

		return query.getResultList();
	}

	public List<Product> listByUseynAndBrandId(Boolean useyn, Integer brandId) {
		Session session = sessionFactory.getCurrentSession();

		StringBuilder str = new StringBuilder("from product p where 1=1 ");
		if (useyn)
			str.append(" and p.useyn = :useyn");
		if (brandId > 0)
			str.append(" and p.brandId = :brandId");

		Query<Product> query = session.createQuery(str.toString(), Product.class);
		if (useyn)
			query.setParameter("useyn", useyn);
		if (brandId > 0)
			query.setParameter("brandId", brandId);

		return query.getResultList();
	}

	public List<Product> listByBrand(Integer brandId) {
		Session session = sessionFactory.getCurrentSession();

		CriteriaBuilder cb = session.getCriteriaBuilder();

		CriteriaQuery<Product> cq = cb.createQuery(Product.class);
		Metamodel m = session.getMetamodel();
		EntityType<Product> Product_ = m.entity(Product.class);
		Root<Product> root = cq.from(Product.class);
		cq.where(cb.equal(root.get(Product_.getSingularAttribute("brandId")), brandId));

		cq.select(root);

		Query<Product> query = session.createQuery(cq);

		return query.getResultList();
	}

	public Integer update(Integer id, Product product) {
		/*
		 * session.byId(T).load(id) setter() session.flush()
		 */
		Session session = sessionFactory.getCurrentSession();

		Product tempProduct = session.byId(Product.class).load(id);
		tempProduct.setName(product.getName());
		tempProduct.setUseyn(product.getUseyn());
		tempProduct.setRaw(product.getRaw());
		tempProduct.setKeyword(product.getKeyword());

		// flush: Sync between session state and DB
		session.flush();

		return tempProduct.getId();
	}

	public void updateUseyn(Integer brandId, Brand brandDetail) {
		Session session = sessionFactory.getCurrentSession();

		String hql = "update product p set useyn = :useyn where p.brandId = :brandId";
		Query query = session.createQuery(hql);

		query.setParameter("useyn", brandDetail.getUseyn());
		query.setParameter("brandId", brandId);
		query.executeUpdate();

	}

	public void delete(Integer id) {
		// session.delete(session.byId(T).load(id))
		Session session = sessionFactory.getCurrentSession();
		session.delete(session.byId(Product.class).load(id));
	}

	public int deleteByBrandId(Integer brandId) {
		Session session = sessionFactory.getCurrentSession();

		String hql = "delete product p where p.brandId = :brandId";
		int deletedEntities = session.createQuery(hql).setParameter("brandId", brandId).executeUpdate();

		return deletedEntities;
	}
}
