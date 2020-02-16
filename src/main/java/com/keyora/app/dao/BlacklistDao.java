package com.keyora.app.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.keyora.app.entity.Blacklist;

@Repository
public class BlacklistDao {
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	JdbcTemplate  jdbcTemplate;

	public Integer save(Blacklist blacklist) {
		sessionFactory.getCurrentSession().save(blacklist);
		return blacklist.getId();
	}

	public Blacklist get(Integer id) {
		return sessionFactory.getCurrentSession().get(Blacklist.class, id);
	}

	public List<Blacklist> list() {
		Session session = sessionFactory.getCurrentSession();

		CriteriaBuilder cb = session.getCriteriaBuilder();

		CriteriaQuery<Blacklist> cq = cb.createQuery(Blacklist.class);
		Root<Blacklist> root = cq.from(Blacklist.class);

		cq.select(root);

		Query<Blacklist> query = session.createQuery(cq);

		return query.getResultList();
	}
	
	public List<Map<String,Object>> listWithItem() {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT ");
		sql.append("    b.id, ");
		sql.append("    ANY_VALUE(i.product_name) as product_name, ");
		sql.append("    ANY_VALUE(i.seller) as seller, ");
		sql.append("    ANY_VALUE(i.link) as link, ");
		sql.append("    b.product_id, ");
		sql.append("    ANY_VALUE(b.keyword_id) as keyword_id, ");
		sql.append("    ANY_VALUE(b.listed_date) as listed_date, ");
		sql.append("    ANY_VALUE(bb.name) as b_name, ");
		sql.append("    ANY_VALUE(p.name) as p_name ");
		sql.append(" FROM ");
		sql.append("    item i ");
		sql.append("        INNER JOIN ");
		sql.append("    blacklist b ON b.product_id = i.product_id ");
		sql.append("        INNER JOIN ");
		sql.append("    product p ON p.id = i.keyword_id ");
		sql.append("        INNER JOIN ");
		sql.append("    brand bb ON p.brand_id = bb.id ");
		sql.append("GROUP BY i.product_id ");
		
		List<Map<String,Object>> result = jdbcTemplate.queryForList(sql.toString());
		
		return result;
	}
	
	public List<Map<String, String>> listWithItemToMap() {
		Session session = sessionFactory.getCurrentSession();

		StringBuilder sql = new StringBuilder();
		sql.append("   select new map(b.id as id, i.title as title, i.mallName as seller, b.productId as productId) ");
		sql.append("     from blacklist b, item i where b.productId = i.productId ");
		sql.append(" group by i.productId");
		
		return session.createQuery(sql.toString()).list();
	}

	public Integer update(Integer id, Blacklist blacklist) {
		/*
		 * session.byId(T).load(id) setter() session.flush()
		 */
		Session session = sessionFactory.getCurrentSession();

		Blacklist tempBlacklist = session.byId(Blacklist.class).load(id);
		tempBlacklist.setProductId(blacklist.getProductId());
		tempBlacklist.setUserId(blacklist.getUserId());

		// flush: Sync between session state and DB
		session.flush();

		return tempBlacklist.getId();
	}

	public void delete(Integer blacklistId) {
		System.out.println("DAO delete");
		Session session = sessionFactory.getCurrentSession();
        session.delete((Blacklist)session.get(Blacklist.class, blacklistId));
	}
}
