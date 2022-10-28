package com.cybage.model;

public class EventOrganizer {
	private int id;
	private String name;
	private String email;
	private String venue;
	private String price;
	
	public EventOrganizer(int id, String name, String email, String venue, String price) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.venue = venue;
		this.price = price;
	}
	
	public EventOrganizer(String name, String email, String venue, String price) {
		super();
		this.name = name;
		this.email = email;
		this.venue = venue;
		this.price = price;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getVenue() {
		return venue;
	}
	public void setVenue(String venue) {
		this.venue = venue;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
}
