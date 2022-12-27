package com.cs.eventlogs.batch.job;

import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;

/*
 * This class contains methods related to Custom Skip Policy.
 */
public class CustomSkipPolicy implements SkipPolicy {

	/*
	 * This method is used to configure custom skip policy to ignore error record 
	 * @param t Throwable object 
	 * @param skipCount skip count
	 * @return boolean always true for next record to be processed.
	 */
	@Override
	public boolean shouldSkip(Throwable t, int skipCount) throws SkipLimitExceededException {
		return true;
	}

}
