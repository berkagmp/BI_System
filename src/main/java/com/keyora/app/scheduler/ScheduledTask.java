package com.keyora.app.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.keyora.app.service.ItemService;
import com.keyora.app.service.ProductService;
import com.keyora.app.service.RankService;

@Component
public class ScheduledTask {
	private Logger logger = LoggerFactory.getLogger(getClass().getName());

	@Autowired
	private ItemService itemService;

	@Autowired
	private RankService rankService;

	@Autowired
	private ProductService productService;

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	// CRON: <second> <minute> <hour> <day-of-month> <month> <day-of-week> <year>

	@Scheduled(cron = "0 0 6 * * * ")
	public void batchCron() {
		logger.info("The start time is {}", dateFormat.format(new Date()));

		int productSize = productService.getMaxId();

		for (int i = 1; i < productSize + 1; i++) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			itemService.collectOneData(i);
		}

		logger.info("The end time is {}", dateFormat.format(new Date()));
	}

	@Scheduled(cron = "0 10 7 * * * ")
	public void collectKeywords() {
		logger.info("The time is now {}", dateFormat.format(new Date()));
		logger.info(String.valueOf(rankService.collect()));
	}

}