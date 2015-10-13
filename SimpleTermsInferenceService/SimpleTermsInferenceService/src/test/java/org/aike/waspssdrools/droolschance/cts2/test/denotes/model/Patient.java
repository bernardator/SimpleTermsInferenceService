package org.aike.waspssdrools.droolschance.cts2.test.denotes.model;

public class Patient {
	int id;
	Microorganism culture;
	Antibiotic treatment;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Microorganism getCulture() {
		return culture;
	}
	public void setCulture(Microorganism culture) {
		this.culture = culture;
	}
	public Antibiotic getTreatment() {
		return treatment;
	}
	public void setTreatment(Antibiotic treatment) {
		this.treatment = treatment;
	}
	
	
}
