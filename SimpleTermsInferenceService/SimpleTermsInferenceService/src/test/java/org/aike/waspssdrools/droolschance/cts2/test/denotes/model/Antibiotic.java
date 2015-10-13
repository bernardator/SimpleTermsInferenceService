package org.aike.waspssdrools.droolschance.cts2.test.denotes.model;

public class Antibiotic {
	String codAtc;
	
	String name;

	public Antibiotic(String codAtc, String name) {
		super();
		this.codAtc = codAtc;
		this.name = name;
	}

	public String getCodAtc() {
		return codAtc;
	}

	public void setCodAtc(String codAtc) {
		this.codAtc = codAtc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
