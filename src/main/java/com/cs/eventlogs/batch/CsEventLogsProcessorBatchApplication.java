package com.cs.eventlogs.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * This class is used to bootstrap and launch Event logs processor batch from main method 
 */
@SpringBootApplication
public class CsEventLogsProcessorBatchApplication {

	/*
	 * This is the main method to launch the application and automatically creates the ApplicationContext,
	 * scan the configuration classes. 
	 * @param args main method arguments
	 * 
	 */
	public static void main(String[] args) {
		System.exit(SpringApplication.exit(SpringApplication.run(CsEventLogsProcessorBatchApplication.class, args)));
	}

}
