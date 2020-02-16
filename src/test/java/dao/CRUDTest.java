package dao;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.keyora.app.config.AppConfig;
import com.keyora.app.dao.ProductDao;
import com.keyora.app.entity.Brand;
import com.keyora.app.entity.Product;
import com.keyora.app.service.BrandService;
import com.keyora.app.service.ProductService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { AppConfig.class }, loader = AnnotationConfigWebContextLoader.class)
@WebAppConfiguration
public class CRUDTest {

	@Autowired
	BrandService brandService;

	@Autowired
	ProductService productService;

	@Autowired
	ProductDao productDao;

	@Test
	@Transactional
	public void brandTest() {
		Brand b = new Brand("test", true);
		int before = brandService.list().size();
		Integer id = brandService.save(b);

		int after = brandService.list().size();
		assertThat(after, greaterThan(before));

		brandService.delete(id);

		after = brandService.list().size();
		assertThat(before, equalTo(after));
	}

	@Test
	@Transactional
	public void productTest() {
		Float f = 10f;
		Product p = new Product("test", false, f, "keyword", 1);
		int before = productService.list().size();
		Integer id = productService.save(p);

		int after = productService.list().size();
		assertThat(after, greaterThan(before));

		p.setRaw(100f);
		productService.update(id, p);
		assertThat(f, not(productService.get(id).getRaw()));

		productService.delete(id);

		after = productService.list().size();
		assertThat(before, equalTo(after));
	}

	@Test
	@Transactional
	public void productUpdateRawTest() {
		Integer productId = 1;

		Float beforeRaw = productService.get(productId).getRaw();
		productService.updateRaw(1, 13.02f);
		assertThat(beforeRaw, not(productService.get(productId).getRaw()));
	}

	@Test
	@Transactional
	public void deleteByBrandID() {
		int cnt = productDao.deleteByBrandId(1);
		System.out.println("cnt : " + cnt);
		assertThat(cnt, greaterThan(0));
	}

}
