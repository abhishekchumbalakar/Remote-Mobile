package com.example.remotemobile;

public class set {
	private  String make;
	private int iconid;
	private String condition;
	
	
	public set(String make, int iconid, String condition) {
		super();
		this.make = make;
		this.iconid = iconid;
		this.condition = condition;
	}
	/**
	 * @return the make
	 */
	public String getMake() {
		return make;
	}
	/**
	 * @return the iconid
	 */
	public int getIconid() {
		return iconid;
	}
	/**
	 * @return the condition
	 */
	public String getCondition() {
		return condition;
	}
	

}
