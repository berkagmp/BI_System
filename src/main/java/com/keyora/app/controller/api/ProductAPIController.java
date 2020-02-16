package com.keyora.app.controller.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.keyora.app.entity.Brand;
import com.keyora.app.entity.Product;
import com.keyora.app.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductAPIController {
	private Logger logger = LoggerFactory.getLogger(getClass().getName());
	
	@Autowired
	ProductService productService;

	@PostMapping("/")
	public ResponseEntity<?> save(@Validated @RequestBody Product product) {
		Integer result = productService.save(product);
		return ResponseEntity.ok().body("New Keyword has been saved with ID:" + result);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable(value = "id") Integer id) {
		Product product = productService.get(id);
		try {
			return ResponseEntity.ok().body(product);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Something's wrong");
		}
	}

	@GetMapping("/list")
	public ResponseEntity<List<Product>> list(@RequestParam(value="useyn", required=false, defaultValue="false") Boolean useyn) {
		List<Product> resultList = productService.listByUseynAndBrandId(useyn, 0);
		return ResponseEntity.ok().body(resultList);
	}

	@GetMapping("/list/{brandId}")
	public ResponseEntity<List<Product>> list(@PathVariable(value = "brandId") Integer brandId
											, @RequestParam(value = "useyn", required=false, defaultValue="false") Boolean useyn) {
		List<Product> resultList = productService.listByUseynAndBrandId(useyn, brandId);
		return ResponseEntity.ok().body(resultList);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable(value = "id") Integer productId,
			@Validated @RequestBody Product productDetail) {
		try {
			Integer result = productService.update(productId, productDetail);
			return ResponseEntity.ok().body("Product has been updated successfully : " + result);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Something's wrong");
		}
	}
	
	@PutMapping("/raw/{id}")
	public ResponseEntity<?> updateRaw(@PathVariable(value = "id") Integer productId,
			@Validated @RequestBody Product productDetail) {
		try {
			Product product = productService.get(productId);
			product.setRaw(productDetail.getRaw());
			
			Integer result = productService.update(productId, product);
			return ResponseEntity.ok().body("Product has been updated successfully : " + result);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Something's wrong");
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Integer productId) {
		try {
			productService.delete(productId);
			return ResponseEntity.status(HttpStatus.OK).body("Product has been deleted successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Something's wrong");
		}
	}
}
