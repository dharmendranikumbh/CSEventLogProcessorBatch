package com.cs.eventlogs.batch;

import java.io.File;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CsEventLogsProcessorBatchApplicationTests {

	/*
	 * Logger instance
	 */
	private static final Logger log= LoggerFactory.getLogger(CsEventLogsProcessorBatchApplicationTests.class);

	/*
	 * Set the test event log file in environment
	 */
	@BeforeAll
    static void initAll() {
		log.info("inside init All");
		final ClassLoader classLoader = CsEventLogsProcessorBatchApplicationTests.class.getClassLoader();
		final String resourceName = "logfile.txt";
		final File file = new File(classLoader.getResource(resourceName).getFile());
		final String absolutePath = file.getAbsolutePath();
        System.setProperty("LOG_FILE_NAME", absolutePath);
    }

	/*
	 * Run the integration test
	 */
	@Test
	void contextLoads() {
	
	}

}
