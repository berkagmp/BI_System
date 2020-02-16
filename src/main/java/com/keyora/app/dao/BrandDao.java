package com.keyora.app.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.keyora.app.entity.Brand;

@Repository
public class BrandDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Integer save(Brand brand) {
		sessionFactory.getCurrentSession().save(brand);
		return brand.getId();
	}

	public Brand get(Integer id) {
		return sessionFactory.getCurrentSession().get(Brand.class, id);
	}
	
	public List<Brand> list() {
		Session session = sessionFactory.getCurrentSession();

		CriteriaBuilder cb = session.getCriteriaBuilder();

		CriteriaQuery<Brand> cq = cb.createQuery(Brand.class);
		Root<Brand> root = cq.from(Brand.class);

		cq.select(root);

		Query<Brand> query = session.createQuery(cq);

		return query.list();
	}
	
	public List<Brand> listByUseyn(Boolean useyn) {
		Session session = sessionFactory.getCurrentSession();

		StringBuilder str = new StringBuilder("from brand b ");
		if(useyn) str.append("where b.useyn = :useyn");
		
		Query<Brand> query = session.createQuery(str.toString(), Brand.class);
		if(useyn) query.setParameter("useyn", useyn);

		return query.getResultList();
	}

	public Integer update(Integer id, Brand brand) {
		/*
		 * session.byId(T).load(id) setter() session.flush()
		 */
		Session session = sessionFactory.getCurrentSession();

		Brand tempBrand = session.byId(Brand.class).load(id);
		tempBrand.setName(brand.getName());
		tempBrand.setUseyn(brand.getUseyn());

		// flush: Sync between session state and DB
		session.flush();

		return tempBrand.getId();
	}

	public void delete(Integer id) {
		// session.delete(session.byId(T).load(id))
		Session session = sessionFactory.getCurrentSession();
		session.delete(session.byId(Brand.class).load(id));
	}
}
