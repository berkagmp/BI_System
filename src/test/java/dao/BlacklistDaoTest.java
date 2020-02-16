package dao;

import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.keyora.app.config.AppConfig;
import com.keyora.app.dao.BlacklistDao;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { AppConfig.class }, loader = AnnotationConfigWebContextLoader.class)
@WebAppConfiguration
public class BlacklistDaoTest {
	@Autowired
	BlacklistDao blacklistDao;

	@Test
	@Transactional
	public void listByDate() {
		List<Map<String, Object>> list = blacklistDao.listWithItem();
		System.out.println(list.size());

		List<Map<String, String>> list1 = blacklistDao.listWithItemToMap();
		System.out.println(list1.size());
		list1.forEach(m -> System.out.println(m.toString()));

		assertNotNull(list);
	}
}
