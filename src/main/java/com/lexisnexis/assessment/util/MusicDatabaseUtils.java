package com.lexisnexis.assessment.util;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author Sudhakaran Vasudevan
 * 
 * This is utility class to provide helper methods to the application
 *
 */
@Component
public class MusicDatabaseUtils {

	/**
	 * @param sortColumn
	 * @param sortOrder
	 * @return Order 
	 * 
	 * this method will return Order object based on given sortOrder and colum
	 * 
	 */
	public static Order getOrder(String sortColumn, String sortOrder) {

		Order order = null;

		if (!StringUtils.isEmpty(sortOrder)) {
			if (sortOrder.equalsIgnoreCase("asc")) {
				order = Sort.Order.asc(sortColumn).ignoreCase();
			} else if (sortOrder.equalsIgnoreCase("desc")) {
				order = Sort.Order.desc(sortColumn).ignoreCase();
			}
		}

		return order;
	}

	/**
	 * @param artistName
	 * @param albumName
	 * @return queryString
	 * 
	 * This method accepts artistName and albumName as input and produce
	 * QueryString value for Discogs DB search
	 * 
	 */
	public static String getQueryString(String artistName, String albumName) {
		StringBuilder queryString = new StringBuilder();
		queryString.append(artistName).append(" ");
		queryString.append(albumName);

		return queryString.toString().replaceAll(" ", "-");
	}

	/**
	 * @param baseURL
	 * @param queryString
	 * @param albumName
	 * @param key
	 * @param secret
	 * @return URL
	 * 
	 * This method is to build Discogs search URL
	 */
	public static String buildURL(String baseURL, String queryString, String albumName, String key, String secret) {
		String amp = "&";
		StringBuilder urlBuilder = new StringBuilder(baseURL);

		urlBuilder.append("query=").append(queryString).append(amp).append("release_title=").append(albumName)
				.append(amp).append("key=").append(key).append(amp).append("secret=").append(secret);

		return urlBuilder.toString();
	}

}
