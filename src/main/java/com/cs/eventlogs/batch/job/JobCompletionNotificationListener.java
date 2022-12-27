package com.cs.eventlogs.batch.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cs.eventlogs.batch.repository.CSRepository;

/*
 * This class contains methods to invoke post job execution
 */
@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport{

	/*
	 * CSRepository instance
	 */
	@Autowired
	private CSRepository cSRepository;

	/*
	 * Logger instance
	 */
	private static final Logger log= LoggerFactory.getLogger(JobCompletionNotificationListener.class);

	/*
	 * This method is used to configure post job execution to fetch the stored events log
	 * @param jobExecution Job Execution
	 */
	@Override
	public void afterJob(JobExecution jobExecution) {
		
		if (jobExecution.getExitStatus().equals(ExitStatus.COMPLETED)) {
			log.info("Job Completion Notification Listener");
			log.info("All Stored Events ");
			cSRepository.findAll().forEach(e->log.info(" EVENT id:{} | type:{} | Start Time:{} | End Time:{} | Time Difference:{} | Alert:{}",
					e.getId(),e.getType(),e.getStartTimestamp(),e.getEndTimestamp(),e.getDifferenceTimestamp(),e.getAlert()));
			
			log.info("Events with True Alert ");
			cSRepository.findByAlert(true).forEach(e->log.info(" EVENT id:{} | type:{} | Start Time:{} | End Time:{} | Time Difference:{} | Alert:{}",
					e.getId(),e.getType(),e.getStartTimestamp(),e.getEndTimestamp(),e.getDifferenceTimestamp(),e.getAlert()));
		}
		
	}
	
	
}
