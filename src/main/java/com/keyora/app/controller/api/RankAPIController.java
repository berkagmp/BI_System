package com.keyora.app.controller.api;

import java.util.List;
import java.util.Map;

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

import com.keyora.app.entity.Rank;
import com.keyora.app.service.RankService;

@RestController
@RequestMapping("/api/rank")
public class RankAPIController {
	private Logger logger = LoggerFactory.getLogger(getClass().getName());

	@Autowired
	RankService rankService;

	@PostMapping("/")
	public ResponseEntity<?> save(@Validated @RequestBody Rank rank) {
		Integer result = rankService.save(rank);
		return ResponseEntity.ok().body("New rank has been saved with ID:" + result);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable(value = "id") Integer id) {
		Rank rank = rankService.get(id);
		try {
			return ResponseEntity.ok().body(rank);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Something's wrong");
		}
	}

	@GetMapping("/list")
	public ResponseEntity<List<Rank>> list() {
		List<Rank> resultList = rankService.list();
		return ResponseEntity.ok().body(resultList);
	}

	@GetMapping("/collect")
	public ResponseEntity<Integer> collect() {
		int i = rankService.collect();
		return ResponseEntity.ok().body(i);
	}

	@GetMapping("/listByDate")
	public ResponseEntity<?> list(@RequestParam(value = "date") String date) {
		List<Map<String, Object>> resultList = rankService.listByDate(date);
		return ResponseEntity.ok().body(resultList);
	}

	@GetMapping("/getStats/{keyword}")
	public ResponseEntity<?> getStats(@PathVariable(value = "keyword") String keyword) {
		List<Map<String, Object>> resultList = rankService.getStats(keyword);
		return ResponseEntity.ok().body(resultList);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable(value = "id") Integer rankId,
			@Validated @RequestBody Rank rankDetail) {
		try {
			Integer result = rankService.update(rankId, rankDetail);
			return ResponseEntity.ok().body("Rank has been updated successfully : " + result);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Something's wrong");
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Integer rankId) {
		try {
			rankService.delete(rankId);
			return ResponseEntity.status(HttpStatus.OK).body("Rank has been deleted successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Something's wrong");
		}
	}
}
