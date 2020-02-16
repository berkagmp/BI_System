package com.keyora.app.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.keyora.app.entity.Item;
import com.keyora.app.entity.ItemView;

@Repository
public class ItemDao {

	private Logger logger = LoggerFactory.getLogger(getClass().getName());

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	JdbcTemplate jdbcTemplate;

	public Long save(Item item) {
		sessionFactory.getCurrentSession().save(item);
		return item.getId();
	}

	public List<Map<String, Object>> getStatisticsByProductId(String pId, String startDate, String endDate) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT  ");
		sql.append("    DATE_FORMAT(insert_date, '%Y-%m-%d') AS date, ");
		sql.append("    MIN(sum) AS value, ");
		sql.append("    ANY_VALUE(price) AS price, ");
		sql.append("    ANY_VALUE(delivery_fee) AS delivery_fee, ");
		sql.append("    ANY_VALUE(seller) AS seller, ");
		sql.append("    ANY_VALUE(link) AS link, ");
		sql.append("    ANY_VALUE(product_name) AS roduct_name ");
		sql.append("FROM item_view ");
		sql.append("WHERE p_id = ? ");
		sql.append("  AND DATE_FORMAT(insert_date, '%Y-%m-%d') BETWEEN ? AND ? ");
		sql.append("GROUP BY DATE_FORMAT(insert_date, '%Y-%m-%d') ");
		sql.append("ORDER BY DATE_FORMAT(insert_date, '%Y-%m-%d') ");        

		List<Map<String, Object>> result = jdbcTemplate.queryForList(sql.toString(),
				new Object[] { pId, startDate, endDate });

		return result;
	}
	
	public List<Map<String, Object>> getStatisticsByProductIdByWeek(String pId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DATE_FORMAT(DATE_SUB(insert_date, INTERVAL (DAYOFWEEK(insert_date)-1) DAY), '%m.%d') as start, "); 
		sql.append("       DATE_FORMAT(DATE_SUB(insert_date, INTERVAL (DAYOFWEEK(insert_date)-7) DAY), '%m.%d') as end, "); 
		sql.append("       DATE_FORMAT(insert_date, '%Y-%U') AS date, ");  
		sql.append("       round(avg(sum)) as value "); 
		sql.append("  FROM item_view "); 
		sql.append("  where p_id = ? "); 
		sql.append(" GROUP BY insert_date ");

		List<Map<String, Object>> result = jdbcTemplate.queryForList(sql.toString(),
				new Object[] { pId });

		return result;
	}
	
	public List<Map<String, Object>> getStatisticsByProductIdByMonth(String pId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select date_format(insert_date, '%Y-%m') as date, round(avg(sum)) as value ");
		sql.append("  from item_view where p_id = ?");
		sql.append(" group by date_format(insert_date, '%Y-%m') ");

		List<Map<String, Object>> result = jdbcTemplate.queryForList(sql.toString(),
				new Object[] { pId });

		return result;
	}
	
	public List<Map<String, String>> getStatistics() {
		Session session = sessionFactory.getCurrentSession();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DATE_FORMAT(insert_date, '%Y-%m-%d'), COUNT(1) ");
		sql.append("  FROM item ");
		sql.append("GROUP BY DATE_FORMAT(insert_date, '%Y-%m-%d') ");
		sql.append("ORDER BY insert_date desc");

		List<Map<String, String>> usersList = session.createQuery(sql.toString()).list();

		return usersList;
	}

	public List<String> listDateByProductId(Integer productId) {
		Session session = sessionFactory.getCurrentSession();

		StringBuilder sql = new StringBuilder();

		sql.append(
				"select DATE_FORMAT(i.insertDate, '%Y-%m-%d') from item i where i.keywordId = :productId ");
		sql.append("group by DATE_FORMAT(i.insertDate, '%Y-%m-%d') ");
		sql.append("order by 1 desc ");

		Query<String> query = session.createQuery(sql.toString(), String.class);
		query.setParameter("productId", productId);
		query.setMaxResults(10);

		return query.getResultList();
	}

	public List<ItemView> listByProductIdAndDate(Integer productId, String date) {
		Session session = sessionFactory.getCurrentSession();

		StringBuilder sql = new StringBuilder();
		sql.append("from item_view iv where iv.pId = :productId ");

		if (StringUtils.isEmpty(date)) {
			sql.append(" and DATE_FORMAT(iv.insertDate, '%Y-%m-%d') = (select DATE_FORMAT(max(j.jobDate), '%Y-%m-%d') from job j)");
		} else {
			sql.append(" and DATE_FORMAT(iv.insertDate, '%Y-%m-%d') = :date");
		}

		Query<ItemView> query = session.createQuery(sql.toString(), ItemView.class);

		query.setParameter("productId", productId);

		if (!StringUtils.isEmpty(date)) {
			query.setParameter("date", date);
		}

		return query.getResultList();
	}

	public List<Item> list() {
		Session session = sessionFactory.getCurrentSession();
		Query<Item> query = session.createQuery("from item", Item.class);
		return query.getResultList();
	}

	public void delete(Long id) {
		// session.delete(session.byId(T).load(id))
		Session session = sessionFactory.getCurrentSession();
		session.delete(session.byId(Item.class).load(id));
	}

	public Long update(Long id, Item item) {
		/*
		 * session.byId(T).load(id) setter() session.flush()
		 */
		Session session = sessionFactory.getCurrentSession();

		Item tempItem = session.byId(Item.class).load(id);
		tempItem.setUseyn(item.getUseyn());

		// flush: Sync between session state and DB
		session.flush();

		return tempItem.getId();
	}
}
