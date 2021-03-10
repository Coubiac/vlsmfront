package com.begr.vlsmfront;

import com.begr.vlsmfront.controller.HomeController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class VlsmfrontApplicationTests {

	@Autowired
	private HomeController homeController;

	@Test
	void contextLoads() {
		assertThat(homeController).isNotNull();
	}

}
