package org.aike.waspssdrools.droolschance.cts2.test.denotes.util;

import java.net.URI;

import org.aike.waspssdrools.droolschance.cts2.test.denotes.model.Microorganism;
import org.aike.waspssdrools.droolschance.cts2.test.denotes.vocabulary.VocabularyMicro;


public class MicroorganismConceptDescriptorGenerator implements ConceptDescriptorGenerator<Microorganism> {

	@Override
	public URI getCodeURI(Microorganism concept) {
		return URI.create( VocabularyMicro.DEFAULT_IRI + "#" +concept.getId()+"-"+Utils.generateDroolsName(concept.getName()));
	}
	@Override
	public String getCode(Microorganism concept) {
		return concept.getId()+"-"+Utils.generateDroolsName(concept.getName());
	}
	@Override
	public String getDisplayName(Microorganism concept) {
		return concept.getName();
	}
	@Override
	public String getCodeSystem(Microorganism concept) {
		return VocabularyMicro.DEFAULT_IRI;
	}
	@Override
	public String getCodeSystemName(Microorganism concept) {
		return "";
	}			
	
}
