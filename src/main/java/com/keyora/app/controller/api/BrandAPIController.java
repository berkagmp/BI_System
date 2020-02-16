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
import com.keyora.app.service.BrandService;

@RestController
@RequestMapping("/api/brand")
public class BrandAPIController {

	private Logger logger = LoggerFactory.getLogger(getClass().getName());

	@Autowired
	BrandService brandService;

	@PostMapping("/")
	public ResponseEntity<?> save(@Validated @RequestBody Brand brand) {
		Integer result = brandService.save(brand);
		return ResponseEntity.ok().body("New brand has been saved with ID:" + result);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable(value = "id") Integer id) {
		Brand brand = brandService.get(id);
		try {
			return ResponseEntity.ok().body(brand);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Something's wrong");
		}
	}

	@GetMapping("/list")
	public ResponseEntity<List<Brand>> list(@RequestParam(value="useyn", required=false, defaultValue="false") Boolean useyn) {
		List<Brand> resultList = brandService.listByUseyn(useyn);
		return ResponseEntity.ok().body(resultList);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable(value = "id") Integer brandId,
			@Validated @RequestBody Brand brandDetail) {
		try {
			Integer result = brandService.update(brandId, brandDetail);
			return ResponseEntity.ok().body("Brand has been updated successfully : " + result);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Something's wrong");
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Integer brandId) {
		try {
			brandService.delete(brandId);
			return ResponseEntity.status(HttpStatus.OK).body("Brand has been deleted successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Something's wrong");
		}
	}
}
