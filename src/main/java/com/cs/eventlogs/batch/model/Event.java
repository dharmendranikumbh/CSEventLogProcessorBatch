package com.cs.eventlogs.batch.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;

/*
 * This is the entity class for the Event table
 */
@Entity
@Table(name = "Event")
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "EVENT_ID")
	private Long eventId;

	@Column(name = "ID")
	private String id;

	@Column(name = "STATE")
	private String state;
	
	@Column(name = "START_TIME")
	private Long startTimestamp;
	
	@Column(name = "TYPE")
	private String type;
	
	@Column(name = "HOST")
	private String host;
	
	@Column(name = "END_TIME")
	private Long endTimestamp;
	
	@Column(name = "TIME_DIFFERENCE")
	private Long differenceTimestamp;
	
	@Column(name = "ALERT")
	private Boolean alert;
	
	@JsonInclude
	@Transient
	private Long timestamp;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Long getStartTimestamp() {
		return startTimestamp;
	}

	public void setStartTimestamp(Long startTimestamp) {
		this.startTimestamp = startTimestamp;
	}

	public Long getEndTimestamp() {
		return endTimestamp;
	}

	public void setEndTimestamp(Long endTimestamp) {
		this.endTimestamp = endTimestamp;
	}

	public Long getDifferenceTimestamp() {
		return differenceTimestamp;
	}

	public void setDifferenceTimestamp(Long differenceTimestamp) {
		this.differenceTimestamp = differenceTimestamp;
	}

	public Boolean getAlert() {
		return alert;
	}

	public void setAlert(Boolean alert) {
		this.alert = alert;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

}
