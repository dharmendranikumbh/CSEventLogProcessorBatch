package com.cs.eventlogs.batch.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.cs.eventlogs.batch.model.Event;
import com.cs.eventlogs.batch.repository.CSRepository;

/*
 * Writer component to write event in database Event table
 */
public class EventWriter implements ItemWriter<Event> {

	/*
	 * JPA repository
	 */
	@Autowired
	private CSRepository cSRepository;

	/*
	 * Write the events in database Event table and if record exist update the records
	 * @param items events received from processor component
	 * @throws Exception exception
	 */
	@Override
	public void write(List<? extends Event> items) throws Exception {
		items.forEach(e -> {
			Event modifiedEvent = processEvent(cSRepository.findById(e.getId()), e);
			cSRepository.save(modifiedEvent);
		});

	}

	/*
	 * This method is used to prepare event to store in database.
	 * In case event already present then based on state update the events to store
	 * @param e Event stored in database
	 * @param item event available in log file
	 * @return Event modified event to store in database
	 */
	private Event processEvent(Event e, Event item) {
		if (e == null) {
			e = item;
		}
		String state = item.getState();
		if (state.equals("STARTED")) {
			e.setStartTimestamp(item.getTimestamp());
		} else {
			e.setEndTimestamp(item.getTimestamp());
		}

		if (e.getStartTimestamp() != null && e.getEndTimestamp() != null) {
			Long difference = e.getEndTimestamp() - e.getStartTimestamp();
			e.setDifferenceTimestamp(difference);
			if (difference > 4) {
				e.setAlert(true);
			} else {
				e.setAlert(false);
			}
		}
		return e;
	}

}
