package com.lexisnexis.assessment.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class MusicDatabaseUtilsTest {

	@Test
	public void getOrderTest() {

		String sortColumn = "name";

		Order expectedAscOrder = Sort.Order.asc(sortColumn).ignoreCase();
		Order expctedDescOrder = Sort.Order.desc(sortColumn).ignoreCase();

		assertEquals(expectedAscOrder, MusicDatabaseUtils.getOrder(sortColumn, "asc"));
		assertEquals(expctedDescOrder, MusicDatabaseUtils.getOrder(sortColumn, "desc"));
		assertEquals(null, MusicDatabaseUtils.getOrder(sortColumn, "randomString"));
	}

	@Test
	public void getQueryStringTest() {

		String expectedQueryString = "justin-bieber-my-world";

		assertEquals(expectedQueryString, MusicDatabaseUtils.getQueryString("justin bieber", "my world"));

	}

	@Test
	public void buildURLTest() {

		String expectedURL = "www.google.com?query=a&release_title=b&key=c&secret=d";

		assertEquals(expectedURL, MusicDatabaseUtils.buildURL("www.google.com?", "a", "b", "c", "d"));

	}
}
