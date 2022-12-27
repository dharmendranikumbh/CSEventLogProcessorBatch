package com.cs.eventlogs.batch;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;

import com.cs.eventlogs.batch.job.CustomSkipPolicy;
import com.cs.eventlogs.batch.job.JobCompletionNotificationListener;
import com.cs.eventlogs.batch.mapper.EventMapper;
import com.cs.eventlogs.batch.model.Event;
import com.cs.eventlogs.batch.processor.EventProcessor;
import com.cs.eventlogs.batch.writer.EventWriter;

/*
 * This class contains methods related to spring batch component to process event logger file.
 */
@Configuration
@EnableBatchProcessing
public class BatchJobConfiguration {

	/*
	 * Entity Manager
	 */
	@PersistenceContext
	private EntityManager entityManager;

	/*
	 * Step Builder factory to build step instance
	 */
	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	/*
	 * Job Builder factory to build job instance
	 */
	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	/*
	 * Logger Instance
	 */
	private static final Logger log= LoggerFactory.getLogger(BatchJobConfiguration.class);

	/*
	 * This method read the events log file and map to the java object using the mapper.
	 * Accepts the log files from the command line argument.
	 * @param env contains the log file including path
	 */
	@Bean
	@StepScope
	public FlatFileItemReader<Event> reader(@Autowired Environment env) {
		String logFileName=env.getProperty("LOG_FILE_NAME");
		log.info("Event Logger File Name :{}",logFileName);
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer("\n");
        String[] tokens = { "eventLine" };
        tokenizer.setNames(tokens);
        
		return new FlatFileItemReaderBuilder<Event>()
				.name("eventReader")
				.resource(new FileSystemResource(logFileName))
				.lineTokenizer(tokenizer)
				.fieldSetMapper(new EventMapper())
				.build();
	}

	/*
	 * Processor method to define custom processor for the events fetched from reader component.
	 * @return EventProcessor Event Processor instance
	 */
	@Bean
	@StepScope
	public EventProcessor processor() {
		return new EventProcessor();
	}

	/*
	 * Writer method to define custom writer for the events processed by processor component
	 * @return ItemWriter Item Writer
	 */
	@Bean
	@StepScope
	public ItemWriter<Event> writer() {
		return new EventWriter();
	}

	/*
	 * Step component to register the reader, processor and writer with additional configuration.
	 * @param writer writer instance
	 * @return Step Step instance
	 */
	@Bean
	public Step eventStep(ItemWriter<Event> writer) {
		
		return stepBuilderFactory.get("eventStep")
					.<Event,Event>chunk(1)
					.reader(reader(null))
					.processor(processor())
					.writer(writer)
					.faultTolerant()
					.skipPolicy(skipErrorRecord())
					.throttleLimit(6)
					.build();
	}
	
	/*
	 * Custom skip policy to ignore error record and proceed to next record
	 * @return SkipPolicy Skip Policy Instance
	 */
	@Bean
	public SkipPolicy skipErrorRecord() {
		return new CustomSkipPolicy();
	}

	/*
	 * This method is used to configure Job 
	 * @param listener Job Completion Notification Listener 
	 * @param step Step Configuration
	 * @return Job instance
	 */
	@Bean
	public Job eventJob(JobCompletionNotificationListener listener , Step step) {
		return jobBuilderFactory.get("eventJob")
				.incrementer(new RunIdIncrementer())
				.preventRestart()
				.listener(listener)
				.flow(step)
				.end()
				.build();
	}
}
