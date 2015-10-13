package org.aike.waspssdrools.droolschance.cts2.test.denotes.util;

public class Utils {
	
	/**
	 * Changes an string to make a name to be used as java/drools identifier
	 * 
	 * Like_this_one
	 * 
	 * @param name
	 * @return
	 */
	public static String generateDroolsName(String name) {
		if (!name.isEmpty()) {
			name = name.
					replace(",", "").
					replace(".", "").
					replace("(-)", "n").
					replace(".-", "n").
					replace("(", "").
					replace(")", "").
					replace("[", "").
					replace("]", "").
					replace("'", "").
					replace("-", "").
					replace("+", "p").
					replace("á", "a").
					replace("é", "e").
					replace("í", "i").
					replace("ó", "o").
					replace("ú", "u").
					replace("ñ", "n").
					replace("Ã©","e").// Some odd Latin-1 artifact for é
					replace("Ã³","o").// Some odd Latin-1 artifact for ó 
					replace("/", " ").trim().
					replace(" ", "_").toLowerCase();
			name = name.substring(0,1).toUpperCase()+name.substring(1);
		} 
		return name;
	}

}
