package dao;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.keyora.app.config.AppConfig;
import com.keyora.app.dao.ItemDao;
import com.keyora.app.dao.RankDao;
import com.keyora.app.entity.Rank;
import com.keyora.app.service.ProductService;
import com.keyora.app.service.RankService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { AppConfig.class }, loader = AnnotationConfigWebContextLoader.class)
@WebAppConfiguration
public class DaoTest {

	private Logger logger = Logger.getLogger(getClass().getName());

	@Autowired
	RankDao rankDao;

	@Autowired
	RankService rankService;

	@Autowired
	ItemDao itemDao;

	@Autowired
	ProductService productService;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Test
	public void getRankStats() {
		List<Map<String, Object>> list = rankService.getStats("노니");
		list.forEach(System.out::println);
		assertNotNull(list);
	}
	
	@Test
	@Transactional
	public void test() {
		int i = rankDao.save(new Rank(1, "123", "456"));
		assertThat(i, greaterThan(0));
	}

	@Test
	@Transactional
	public void collect() {
		int i = rankService.collect();
		assertThat(i, equalTo(20));
	}

	@Test
	@Transactional
	public void rankCRUD() {
		int i = rankService.save(new Rank(1, "123", "456"));
		assertThat(i, greaterThan(20));
		Rank r = rankService.get(i);
		Assert.assertNotNull(r);
		rankService.delete(i);
		assertNull(rankService.get(i));
	}
	
	@Test
	public void listByDate() {
		List<Map<String, Object>> list = rankService.listByDate("20181005");
		assertNotNull(list);
	}
	
	@Test
	@Transactional
	public void itemstatic() {
		List<Map<String, Object>> list = itemDao.getStatisticsByProductId("5", "2018-01-01", "2018-10-10");
		list.forEach(map -> logger.info(map.toString()));
		assertNotNull(list);

	}
	
	@Test
	public void jdbcTest() throws ClassNotFoundException, SQLException {
		String startDate = "20181001";
		String endDate = "20181007";

		StringBuilder sql = new StringBuilder();
		sql.append("select date_format(insert_date, '%Y%m%d') as insert_date, keyword, rank, fluctuation, id");
		sql.append("  from rank r");
		sql.append(" where date_format(insert_date, '%Y%m%d') between ? and ?");
		sql.append(" order by insert_date desc ");

		List<java.util.Map<String, Object>> name = jdbcTemplate.queryForList(sql.toString(),
				new Object[] { startDate, endDate });
		logger.info(String.valueOf(name.size()));

		name.forEach(m -> logger.info(m.toString()));

		assertNotNull(name);
	}
}
