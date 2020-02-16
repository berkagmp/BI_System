package com.keyora.app.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.keyora.app.entity.Err;
import com.keyora.app.service.ErrService;

@RestController
@RequestMapping("/api/err")
public class ErrAPIController {
	@Autowired
	ErrService errService;

	@GetMapping("/list")
	public List<Err> list() {
		return errService.list();
	}
}
