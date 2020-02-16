package com.keyora.app.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.keyora.app.entity.Brand;
import com.keyora.app.service.BrandService;
import com.keyora.app.service.ErrService;
import com.keyora.app.service.ItemService;

@Controller
public class HomeController {

	private Logger logger = LoggerFactory.getLogger(getClass().getName());

	@Autowired
	private ItemService itemService;

	@Autowired
	private ErrService errService;
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@GetMapping("/")
	public String home(Model theModel) {
		theModel.addAttribute("err", errService.list());
		theModel.addAttribute("from", itemService.getStatistics().get(itemService.getStatistics().size() - 1));
		theModel.addAttribute("to", itemService.getStatistics().get(0));
		
		logger.info("The time is now {}", dateFormat.format(new Date()));
		
		return "home";
	}

	@GetMapping("/data")
	public String table(Model model) {
		return "data";
	}

	@GetMapping("/search")
	public String search() {
		return "search";
	}
	
	@GetMapping("/rank")
	public String rank() {
		return "rank";
	}
	
	@GetMapping("/blacklist")
	public String blacklist() {
		return "blacklist";
	}

	@GetMapping("/setting")
	public String setting(Model model) {
		return "setting";
	}

	@GetMapping("/login")
	public String showMyLoginPage() {
		return "login";
	}

	// add request mapping for /access-denied

	@GetMapping("/access-denied")
	public String showAccessDenied() {
		return "access-denied";
	}

	@GetMapping("/rebatch")
	public String reBatchForErrorData() {
		itemService.reBatchForErrorData();
		return "redirect:/";
	}
}
