package com.keyora.app.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.keyora.app.dao.ErrDao;
import com.keyora.app.dao.ItemDao;
import com.keyora.app.entity.Blacklist;
import com.keyora.app.entity.Err;
import com.keyora.app.entity.Item;
import com.keyora.app.entity.ItemView;
import com.keyora.app.entity.Product;
import com.keyora.app.entity.Result;

@Service
@PropertySource("classpath:server.properties")
@Transactional(readOnly = true)
public class ItemService {

	private Logger logger = LoggerFactory.getLogger(getClass().getName());

	private final String pagingSize30 = "30";
	private final String pagingSize60 = "60";

	private final int dataCount10 = 10;
	private final int dataCount30 = 30;

	private boolean evergreen = false;
	private boolean manualSearch = false;

	@Value("${naver.clientId}")
	private String clientId;

	@Value("${naver.clientSecret}")
	private String clientSecret;

	@Value("${item.apiURL}")
	private String _apiURL;

	@Value("${item.url}")
	private String _url;

	@Autowired
	ItemDao itemDao;

	@Autowired
	ErrDao errDao;

	@Autowired
	JobService jobService;

	@Autowired
	BlacklistService blacklistService;

	@Autowired
	ProductService productService;

	public List<Map<String, Object>> getStatisticsByProductId(String pId, String startDate, String endDate) {
		return itemDao.getStatisticsByProductId(pId, startDate, endDate);
	}

	public List<Map<String, Object>> getStatisticsByProductIdByMonth(String pId) {
		return itemDao.getStatisticsByProductIdByMonth(pId);
	}

	public List<Map<String, Object>> getStatisticsByProductIdByWeek(String pId) {
		return itemDao.getStatisticsByProductIdByWeek(pId);
	}

	public List<String> listDateByProductId(Integer productId) {
		return itemDao.listDateByProductId(productId);
	}

	public Result listByProductIdAndDate(Integer productId, String date) {
		List<String> dateList = itemDao.listDateByProductId(productId);

		if (StringUtils.isEmpty(date)) {
			if (dateList != null && dateList.size() > 0) {
				date = dateList.get(0);
			}
		}

		List<ItemView> resultList = itemDao.listByProductIdAndDate(productId, date);

		Result result = new Result();

		result.setLastBuildDate(date);
		result.setKeyword(productService.get(productId).getKeyword());

		result.setItemViews(resultList);
		result.setDate(dateList);

		return result;
	}

	public List<Map<String, String>> getStatistics() {
		return itemDao.getStatistics();
	}

	public Elements webcrawler(String keyword) throws Exception {
		Document doc;
		String crawling_url = _url + keyword;

//		logger.info(crawling_url);

		// Web crawling
		doc = Jsoup.connect(crawling_url).get();
		Elements items = doc.select("._itemSection");

		return items;
	}

	public String match(Elements elements, String naverProductId) throws Exception {
		StringBuilder result = new StringBuilder();

		Elements ULs = null;
		Elements EMs = null;

		for (Element element : elements) {
			if (element.attr("data-expose-id").equals(naverProductId)) {
				ULs = element.select(".mall_option");

				for (Element UL : ULs) {
					EMs = UL.getElementsByTag("em");
					for (Element EM : EMs) {
						if (EM.text().startsWith("배송")) {
							result.append(EM.text().replaceAll("배송비", "").replaceAll(",", "").replaceAll("원", "")
									.replaceAll("무료", "").replaceAll(" ", "").trim());
						}
					}
					EMs.clear();
				}

				ULs.clear();
//				logger.info(element.attr("data-expose-id") + " | " + naverProductId + "|" + result.toString());
			}
		}

		if (StringUtils.isEmpty(result.toString())) {
			result.append("0");
		}

		return result.toString();
	}

	public Result search(String keyword, Integer productId, Integer numForSearch, List<Blacklist> blacklists,
			boolean webscrap) {

		logger.info(keyword + " / " + String.valueOf(productId) + " / " + String.valueOf(numForSearch));

		if (productId == 0) {
			manualSearch = true;
		} else if (productId > 167 & productId < 189) {
			evergreen = true;
		} else {
			manualSearch = false;
			evergreen = false;
		}

		Gson gson = new Gson();

		// Invocation Naver API
		BufferedReader br = null;
		boolean success = false;
		Result result = null;

		try {
			String text = URLEncoder.encode(keyword, "UTF-8");
			String apiURL;

			if (manualSearch) {
				apiURL = _apiURL + (numForSearch <= 80 ? numForSearch + 20 : numForSearch) + "&sort=asc&query=" + text;
			} else {
				if (evergreen) {
					apiURL = _apiURL + pagingSize60 + "&sort=asc&query=" + text;
				} else {
					apiURL = _apiURL + pagingSize30 + "&sort=asc&query=" + text;
				}
			}
//			logger.info(apiURL);

			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setRequestMethod("GET");
			con.setRequestProperty("X-Naver-Client-Id", clientId);
			con.setRequestProperty("X-Naver-Client-Secret", clientSecret);

			int responseCode = con.getResponseCode();

			if (responseCode == 200) { // Success
				logger.info("Success");
				success = true;
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else { // Errors
				logger.info("Errors");
				success = false;
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}

			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			// logger.info(response.toString());

			if (success) {
				result = gson.fromJson(response.toString(), Result.class);
				result.setKeyword(keyword);

				// Delete meaningless items
				result.getItems().removeIf(item -> !item.getHprice().trim().equals("0"));

				// Excluding blacklist items
				if (blacklists != null) {
					result.getItems().removeIf(item -> blacklists.stream().anyMatch(blacklist -> {
						if (blacklist.getProductId().equals(item.getProductId())
								&& blacklist.getKeywordId().equals(productId)) {
						}

						return blacklist.getProductId().equals(item.getProductId())
								&& blacklist.getKeywordId().equals(productId);
					}));
				}

				// Extract items by amount
				if (manualSearch) {
					if (result.getItems().size() > numForSearch) {
						result.getItems().subList(numForSearch, result.getItems().size()).clear();
					}
				} else {
					if (evergreen) {
						if (result.getItems().size() > dataCount30) {
							result.getItems().subList(dataCount30, result.getItems().size()).clear();
						}
					} else {
						if (result.getItems().size() > dataCount10) {
							result.getItems().subList(dataCount10, result.getItems().size()).clear();
						}
					}
				}

				Elements elements = webcrawler(keyword);

				// Getting delivery fees
				result.getItems().forEach(item -> {
					item.setKeywordId(productId);

					if (webscrap) {
						try {
							item.setDeliveryFee(match(elements, item.getProductId()));
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						item.setDeliveryFee("0");
					}

					item.setSum(item.getLprice() + Integer.valueOf(item.getDeliveryFee()));
					item.setTitle(Jsoup.parse(item.getTitle()).text());
					logger.info(item.toString());
				});
			} else {
				result = null;

				// Error handling
				if (productId != 0)
					errDao.save(new Err(keyword, productId, response.toString()));
			}
		} catch (Exception e) {
			logger.info(e.toString());
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	@Transactional()
	public void save(Item item) {
		itemDao.save(item);
	}

	@Transactional()
	public void batch(boolean blacklist, boolean webscrap) {
//			Optional<List<Keyword>> result = Optional.ofNullable(keywordService.listByUseynAndProductId(true, 0));
		Optional<List<Product>> result = Optional.ofNullable(productService.listByUseynAndBrandId(true, 0));
		final List<Blacklist> blacklists;

		if (blacklist) {
			blacklists = blacklistService.list();
		} else {
			blacklists = null;
		}

		result.ifPresent(list -> {
			list.forEach(product -> {
				logger.info(product.getKeyword() + "/" + product.getId());
				Optional.ofNullable(search(product.getKeyword(), product.getId(), 0, blacklists, webscrap))
						.ifPresent(obj -> {
							obj.getItems().forEach(item -> {
								// logger.info(item.toString());
								itemDao.save(item);
							});
						});
			});
		});
	}

	@Transactional
	public void reBatchForErrorData() {
		Optional<List<Err>> result = Optional.ofNullable(errDao.list());
		List<Blacklist> blacklists = blacklistService.list();

		try {
			result.ifPresent(list -> {
				list.forEach(err -> {
					logger.info(err.getKeywordId() + "/" + err.getErrMsg());
					Optional.ofNullable(search(err.getKeyword(), err.getKeywordId(), 0, blacklists, true))
							.ifPresent(obj -> {
								obj.getItems().forEach(item -> {
									itemDao.save(item);
								});
							});

					errDao.delete(err.getId());
				});
			});
		} finally {
			jobService.save("U");
		}
	}

	@Transactional
	public void collectOneData(Integer productId) {

		Product product = productService.get(productId);
		List<Blacklist> blacklists = blacklistService.list();

		if (product != null) {
			Optional.ofNullable(search(product.getKeyword(), product.getId(), 0, blacklists, true)).ifPresent(obj -> {
				obj.getItems().forEach(item -> {
					itemDao.save(item);
				});
			});
		}
	}

	@Transactional
	public Long update(Long itemId, Item itemDetail) {
		return itemDao.update(itemId, itemDetail);
	}
}