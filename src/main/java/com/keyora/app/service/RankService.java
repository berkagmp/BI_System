package com.keyora.app.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keyora.app.dao.RankDao;
import com.keyora.app.entity.Rank;

@Service
@Transactional(readOnly = true)
public class RankService {
	private Logger logger = LoggerFactory.getLogger(getClass().getName());

	@Autowired
	RankDao rankDao;

	@Transactional
	public Integer save(Rank rank) {
		return rankDao.save(rank);
	}

	public Rank get(Integer rankId) {
		return rankDao.get(rankId);
	}

	public List<Rank> list() {
		return rankDao.list();
	}
	
	public List<Map<String,Object>> listByDate(String date) {
		return rankDao.listByDate(date);
	}
	
	public List<Map<String,Object>> getStats(String keyword) {
		return rankDao.getStats(keyword);
	}

	@Transactional
	public Integer update(Integer rankId, Rank rankDetail) {
		return rankDao.update(rankId, rankDetail);
	}

	@Transactional
	public void delete(Integer rankId) {
		rankDao.delete(rankId);
	}

	@Transactional
	public int collect() {
		String url = "https://search.shopping.naver.com/best100v2/detail/kwd.nhn?catId=50000023&kwdType=KWD";
		Document doc;
		List<Rank> list = new ArrayList<>();
		final AtomicInteger count = new AtomicInteger();

		try {
			doc = Jsoup.connect(url).get();

			Elements items = doc.select("ul#popular_srch_lst li");

			for (Element element : items) {
				list.add(new Rank(Integer.parseInt(element.select("em").text().replaceAll("위", "").trim()),
						element.select(".txt").text(), element.select(".vary").text()));
			}

			list.forEach(r -> {
				rankDao.save(r);
				count.incrementAndGet();
			});

		} catch (IOException e) {
			e.printStackTrace();
		}

		return count.get();
	}
}
