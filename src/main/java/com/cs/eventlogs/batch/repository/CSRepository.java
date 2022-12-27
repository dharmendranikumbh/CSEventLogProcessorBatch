package com.cs.eventlogs.batch.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cs.eventlogs.batch.model.Event;

/*
 * JPA repository to perform database operation using JPA
 */
public interface CSRepository extends CrudRepository<Event, Long>  {

	/*
	 * Searches stored event by id
	 * @param id
	 * @return Event
	 */
	public Event findById(String id);

	/*
	 * Searches stored events by alert
	 * @param alert true or false
	 * @return List<Event> list of events
	 */
	public List<Event> findByAlert(Boolean alert);
	
}
