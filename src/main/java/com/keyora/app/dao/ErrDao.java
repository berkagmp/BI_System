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

import com.keyora.app.entity.Err;

@Repository
public class ErrDao {
	@Autowired
	private SessionFactory sessionFactory;

	public Integer save(Err err) {
		sessionFactory.getCurrentSession().save(err);
		return err.getId();
	}

	public Err get(Integer id) {
		return sessionFactory.getCurrentSession().get(Err.class, id);
	}

	public List<Err> list() {
		Session session = sessionFactory.getCurrentSession();

		CriteriaBuilder cb = session.getCriteriaBuilder();

		CriteriaQuery<Err> cq = cb.createQuery(Err.class);
		Root<Err> root = cq.from(Err.class);

		cq.select(root);

		Query<Err> query = session.createQuery(cq);

		return query.getResultList();
	}

	public void delete(Integer id) {
		// session.delete(session.byId(T).load(id))
		Session session = sessionFactory.getCurrentSession();
		session.delete(session.byId(Err.class).load(id));
	}
}
