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
import org.springframework.web.bind.annotation.RestController;

import com.keyora.app.entity.Blacklist;
import com.keyora.app.service.BlacklistService;

@RestController
@RequestMapping("/api/blacklist")
public class BlacklistAPIController {
	private Logger logger = LoggerFactory.getLogger(getClass().getName());

	@Autowired
	BlacklistService blacklistService;

	@PostMapping("/")
	public ResponseEntity<?> save(@Validated @RequestBody Blacklist blacklist) {
		Integer result = blacklistService.save(blacklist);
		return ResponseEntity.ok().body("New blacklist has been saved with ID:" + result);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable(value = "id") Integer id) {
		Blacklist blacklist = blacklistService.get(id);
		try {
			return ResponseEntity.ok().body(blacklist);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Something's wrong");
		}
	}

	@GetMapping("/list")
	public ResponseEntity<List<Blacklist>> list() {
		List<Blacklist> resultList = blacklistService.list();
		return ResponseEntity.ok().body(resultList);
	}

	@GetMapping("/listWithItem")
	public ResponseEntity<?> listWithItem() {
		List<Map<String, Object>> resultList = blacklistService.listWithItem();
		return ResponseEntity.ok().body(resultList);
	}

	@GetMapping("/listWithItemToMap")
	public ResponseEntity<List<Map<String, String>>> listWithItemToMap() {
		List<Map<String, String>> resultList = blacklistService.listWithItemToMap();
		return ResponseEntity.ok().body(resultList);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable(value = "id") Integer blacklistId,
			@Validated @RequestBody Blacklist blacklistDetail) {
		try {
			Integer result = blacklistService.update(blacklistId, blacklistDetail);
			return ResponseEntity.ok().body("Blacklist has been updated successfully : " + result);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Something's wrong");
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Integer blacklistId) {
		try {
			blacklistService.delete(blacklistId);
			return ResponseEntity.status(HttpStatus.OK).body("Blacklist has been deleted successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Something's wrong");
		}
	}
}
