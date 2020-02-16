package com.keyora.app.controller.api;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.keyora.app.entity.Item;
import com.keyora.app.entity.Result;
import com.keyora.app.service.ItemService;
import com.keyora.app.service.ProductService;

@RestController
@RequestMapping("/api/item")
public class ItemAPIController {

	private Logger logger = LoggerFactory.getLogger(getClass().getName());

	@Autowired
	ItemService itemService;

	@GetMapping("/getStatisticsByProductId")
	public ResponseEntity<?> getStatisticsByProductId(@RequestParam(value = "pId") String pId,
			@RequestParam(value = "startDate") String startDate, @RequestParam(value = "endDate") String endDate) {
		List<Map<String, Object>> resultList = itemService.getStatisticsByProductId(pId, startDate, endDate);
		return ResponseEntity.ok().body(resultList);
	}

	@GetMapping("/getStatisticsByProductIdByMonth")
	public ResponseEntity<?> getStatisticsByProductIdByMonth(@RequestParam(value = "pId") String pId) {
		List<Map<String, Object>> resultList = itemService.getStatisticsByProductIdByMonth(pId);
		return ResponseEntity.ok().body(resultList);
	}

	@GetMapping("/getStatisticsByProductIdByWeek")
	public ResponseEntity<?> getStatisticsByProductIdByWeek(@RequestParam(value = "pId") String pId) {
		List<Map<String, Object>> resultList = itemService.getStatisticsByProductIdByWeek(pId);
		return ResponseEntity.ok().body(resultList);
	}

	@GetMapping("/getStatistics")
	public ResponseEntity<?> getStatistics() {
		List<Map<String, String>> result = itemService.getStatistics();
		return ResponseEntity.ok().body(result);
	}

	@GetMapping("/listByProductIdAndDate/{productId}")
	public ResponseEntity<?> listByProductIdAndDate(@PathVariable(value = "productId") Integer productId,
			@RequestParam(value = "date", required = false) String date) {
		Result result = itemService.listByProductIdAndDate(productId, date);
		return ResponseEntity.ok().body(result);
	}

	@GetMapping("/search")
	public ResponseEntity<?> listByProductIdAndDate(@RequestParam(value = "keyword") String keyword,
			@RequestParam(value = "num") Integer num) {
		Result result = itemService.search(keyword, 0, num, null, true);
		return ResponseEntity.ok().body(result);
	}

	@GetMapping("/batch/{id}")
	public ResponseEntity<?> get(@PathVariable(value = "id") Integer id) {
		itemService.collectOneData(id);
		return ResponseEntity.ok().body("The job has been finished.");
	}

	@GetMapping("/batch")
	public ResponseEntity<?> batch() {
		itemService.batch(true, true);
		return ResponseEntity.ok().body("The job has been finished.");
	}

	@GetMapping("/batchAll")
	public ResponseEntity<?> batchAll() {
		itemService.batch(false, true);
		return ResponseEntity.ok().body("The job has been finished.");
	}

	@GetMapping("/batchWithoutDevliveryFee")
	public ResponseEntity<?> batchWithoutDevliveryFee() {
		itemService.batch(true, false);
		return ResponseEntity.ok().body("The job has been finished.");
	}

	@GetMapping("/batchAllWithoutDevliveryFee")
	public ResponseEntity<?> batchAllWithoutDevliveryFee() {
		itemService.batch(false, false);
		return ResponseEntity.ok().body("The job has been finished.");
	}

	@GetMapping("/rebatch")
	public ResponseEntity<?> reBatchForErrorData() {
		itemService.reBatchForErrorData();
		return ResponseEntity.ok().body("The job has been finished.");
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable(value = "id") Long itemId, @Validated @RequestBody Item itemDetail) {
		try {
			Long result = itemService.update(itemId, itemDetail);
			return ResponseEntity.ok().body("Brand has been updated successfully : " + result);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Something's wrong");
		}
	}
}
