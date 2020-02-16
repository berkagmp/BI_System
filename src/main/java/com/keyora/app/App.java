package com.keyora.app;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.keyora.app.config.AppConfig;
import com.keyora.app.config.MyWebAppInitializer;
import com.keyora.app.config.WebConfig;
import com.keyora.app.service.ItemService;
import com.keyora.app.service.RankService;

public class App {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(AppConfig.class, WebConfig.class);
		ctx.refresh();
		
		RankService rankService = ctx.getBean(RankService.class);

		System.out.println(rankService.collect());

		ctx.close();
	}

}
