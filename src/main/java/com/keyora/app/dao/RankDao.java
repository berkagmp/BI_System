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

import com.keyora.app.entity.Rank;

@Repository
public class RankDao {
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	JdbcTemplate  jdbcTemplate;

	public Integer save(Rank rank) {
		sessionFactory.getCurrentSession().save(rank);
		return rank.getId();
	}

	public Rank get(Integer id) {
		return sessionFactory.getCurrentSession().get(Rank.class, id);
	}

	public List<Rank> list() {
		Session session = sessionFactory.getCurrentSession();

		CriteriaBuilder cb = session.getCriteriaBuilder();

		CriteriaQuery<Rank> cq = cb.createQuery(Rank.class);
		Root<Rank> root = cq.from(Rank.class);

		cq.select(root);

		Query<Rank> query = session.createQuery(cq);

		return query.getResultList();
	}
	
//	public List<Map<String,Object>> listByDate(String startDate, String endDate) {
//		StringBuilder sql = new StringBuilder();
//		sql.append("select date_format(insert_date, '%Y%m%d') as insert_date, keyword, rank, id");
//		sql.append("  from rank r");
//		sql.append(" where date_format(insert_date, '%Y%m%d') between ? and ?");
//		sql.append(" order by insert_date desc ");
//		
//		List<Map<String,Object>> result = jdbcTemplate.queryForList(sql.toString(), new Object[]{startDate, endDate});
//		
//		return result;
//	}
	
	public List<Map<String,Object>> listByDate(String date) {
		StringBuilder sql = new StringBuilder();
		sql.append("select date_format(insert_date, '%Y-%m-%d') as insert_date, keyword, rank, fluctuation, id");
		sql.append("  from rank r");
		sql.append(" where date_format(insert_date, '%Y-%m-%d') = ?");
		sql.append(" order by insert_date desc ");
		
		List<Map<String,Object>> result = jdbcTemplate.queryForList(sql.toString(), new Object[]{date});
		
		return result;
	}
	
	public List<Map<String,Object>> getStats(String keyword) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select date_format(insert_date, '%Y-%m-%d') as date, rank, keyword, fluctuation from rank ");
		sql.append(" where keyword = ? ");
		sql.append(" order by id desc");
		
		List<Map<String,Object>> result = jdbcTemplate.queryForList(sql.toString(), new Object[]{keyword});
		
		return result;
	}

	public Integer update(Integer id, Rank rank) {
		/*
		 * session.byId(T).load(id) setter() session.flush()
		 */
		Session session = sessionFactory.getCurrentSession();

		Rank tempRank = session.byId(Rank.class).load(id);
		tempRank.setRank(rank.getRank());
		tempRank.setKeyword(rank.getKeyword());
		tempRank.setFluctuation(rank.getFluctuation());

		// flush: Sync between session state and DB
		session.flush();

		return tempRank.getId();
	}

	public void delete(Integer id) {
		// session.delete(session.byId(T).load(id))
		Session session = sessionFactory.getCurrentSession();
		session.delete(session.byId(Rank.class).load(id));
	}
}
