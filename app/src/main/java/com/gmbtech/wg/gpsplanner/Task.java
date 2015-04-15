package com.gmbtech.wg.gpsplanner;

import android.location.Location;


public class Task {
	private String title;
	private Location location;

	public Task(String title, Location location) {
		this.title = title;
		this.location = location;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public Location getLocation() {
		return location;
	}
}
