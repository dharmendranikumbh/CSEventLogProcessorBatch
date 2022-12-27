package com.cs.eventlogs.batch.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.cs.eventlogs.batch.model.Event;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * This class contains methods to map fetched events in JSON from log file to java object
 */
public class EventMapper implements FieldSetMapper<Event> {

	/*
	 * Logger Instance
	 */
	private static final Logger log= LoggerFactory.getLogger(EventMapper.class);

	/*
	 * ObjectMapper instance to convert JSON String to Java object
	 */
	private ObjectMapper jsonMapper=new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	
	/*
	 * This method is used to convert JSON String fetched from log file to Java object
	 * @param fieldSet Fetched JSON string
	 * @return Event Java object of each event
	 */
	@Override
	public Event mapFieldSet(FieldSet fieldSet) throws BindException{
		try {
			return jsonMapper.readValue(fieldSet.readString("eventLine"), Event.class);
		} catch (JsonMappingException e) {
			log.error(e.getMessage());
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
		}
		return null;
	}

}
