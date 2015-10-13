package org.aike.waspssdrools.droolschance.cts2.test.denotes.util;


import java.util.HashMap;
import java.util.Map;

import org.drools.shapes.terms.ConceptCoding;

import cts2.mayo.edu.terms_metamodel.terms.ConceptDescriptor;

public class ConceptDescriptorFactory {
	private static Map<Class<?>, ConceptDescriptorGenerator<?>> generators = new HashMap<Class<?>,ConceptDescriptorGenerator<?>>();
	
	public static <T> void register(Class<T> classType, ConceptDescriptorGenerator<T> cdGenerator) {
		generators.put(classType,cdGenerator);
	}
	
	public static  <T> void unregister(Class<T> classType) {
		generators.remove(classType);
	}
	
	public static <T> ConceptDescriptor getCD(T concept) {

		ConceptDescriptorGenerator<T> generator;
		generator = (ConceptDescriptorGenerator<T>) generators.get(concept.getClass());
		if (generator==null) return null; // TODO THROW AN EXCEPTION OR SOMETHING		
		return new ConceptCoding( 
					generator.getCodeURI(concept),
					generator.getCode(concept),
					generator.getDisplayName(concept),
					generator.getCodeSystem(concept),
					generator.getCodeSystemName(concept));
	
	}

}
