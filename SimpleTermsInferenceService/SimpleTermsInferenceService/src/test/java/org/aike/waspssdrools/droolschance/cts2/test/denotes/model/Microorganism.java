package org.aike.waspssdrools.droolschance.cts2.test.denotes.model;

public class Microorganism {
	int id;
	String name;
	
	
	public Microorganism(int id, String name) {
		super();
		this.id = id;
		this.name = name;
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
	
}
