package com.demo.androidontheway.camera;

public class DriveVideo {

	private int id;
	private String name;
	private int lock;
	private int resolution;
	
	public DriveVideo() {
		// TODO Auto-generated constructor stub
	}
	
	public DriveVideo(int id, String name, int lock, int resolution) {
		this.id = id;
		this.name = name;
		this.lock = lock;
		this.resolution = resolution;
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

	public int getLock() {
		return lock;
	}

	public void setLock(int lock) {
		this.lock = lock;
	}

	public int getResolution() {
		return resolution;
	}

	public void setResolution(int resolution) {
		this.resolution = resolution;
	}
	
	
}
