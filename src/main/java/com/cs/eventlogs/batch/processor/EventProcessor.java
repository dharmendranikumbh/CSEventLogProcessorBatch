package com.cs.eventlogs.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.cs.eventlogs.batch.model.Event;

/*
 * EventProcessor is Processor component of Spring batch
 */
public class EventProcessor implements ItemProcessor<Event, Event> {

	/*
	 * This method is default implementation of processor component
	 * @param item from reader component
	 * @return Event send event to writer component
	 */
	@Override
	public Event process(Event item) throws Exception {
		return item;
	}
}
