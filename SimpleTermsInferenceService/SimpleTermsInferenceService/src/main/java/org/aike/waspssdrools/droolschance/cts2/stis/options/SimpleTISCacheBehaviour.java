package org.aike.waspssdrools.droolschance.cts2.stis.options;

public enum SimpleTISCacheBehaviour implements SimpleTermsInferenceServiceOption {
	/** All the classes and hierarchical relations in the ontology are cached during the initialization phase. Big memory consumption, fast execution. Good for small ontologies */
	EAGER,
	/** The classes and hierarchical relations in the ontology are cached during the execution phase. Middle-range memory consumption, very slow execution. Good for big ontologies */
	LAZY,
	/** No cache at all. The lowest memory consumption, the worst execution time. Only useful for extreme big ontologies and very few rules */
	NONE;
}
