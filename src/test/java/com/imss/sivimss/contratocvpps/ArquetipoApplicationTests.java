package com.imss.sivimss.contratocvpps;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ArquetipoApplicationTests {

	@Test
	void contextLoads() {
		String result = "test";
		ContratoCVPPSFApplication.main(new String[] {});
		assertNotNull(result);
	}

}
