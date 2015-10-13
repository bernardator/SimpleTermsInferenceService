package org.aike.waspssdrools.droolschance.cts2.test.denotes.util;

import java.net.URI;

public interface ConceptDescriptorGenerator<T> {

	/** URI unique for each concept */
	public URI getCodeURI(T concept);
	
	public String getCode(T concept);
	
	public String getDisplayName(T concept);
	
	public String getCodeSystem(T concept);
	
	public String getCodeSystemName(T concept);
	
}
