package service;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.annotations.common.reflection.java.generics.TypeEnvironmentFactory;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.keyora.app.config.AppConfig;
import com.keyora.app.service.ItemService;
import com.keyora.app.service.RankService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { AppConfig.class }, loader = AnnotationConfigWebContextLoader.class)
@WebAppConfiguration
public class ServiceTest {
	@Autowired
	RankService rankService;

	@Autowired
	ItemService itemService;

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@Test
	public void getLastestRank() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime yesterday = LocalDateTime.now().minus(1, ChronoUnit.DAYS);
		
		System.out.println(formatter.format(yesterday));
		assertEquals(20, rankService.listByDate(formatter.format(yesterday)).size());
	}

	@Test
	public void getStatisticsForItems() {
		List<Map<String, String>> result = itemService.getStatistics();

		assertThat(result.size(), greaterThan(0));
	}

}
