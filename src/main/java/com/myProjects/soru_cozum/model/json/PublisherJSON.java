package com.myProjects.soru_cozum.model.json;

public class PublisherJSON {
	private long publisherId;
	private String publisherName;
	private long publisYear;
	public PublisherJSON(long publisherId, String publisherName, long publisYear) {
		super();
		this.publisherId = publisherId;
		this.publisherName = publisherName;
		this.publisYear = publisYear;
	}
	public long getPublisherId() {
		return publisherId;
	}
	public void setPublisherId(long publisherId) {
		this.publisherId = publisherId;
	}
	public String getPublisherName() {
		return publisherName;
	}
	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}
	public long getPublisYear() {
		return publisYear;
	}
	public void setPublisYear(long publisYear) {
		this.publisYear = publisYear;
	}
	
	
	
}
