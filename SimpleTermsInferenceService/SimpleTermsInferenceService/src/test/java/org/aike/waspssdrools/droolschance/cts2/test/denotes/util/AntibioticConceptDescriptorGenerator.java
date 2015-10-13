package org.aike.waspssdrools.droolschance.cts2.test.denotes.util;

import java.net.URI;

import org.aike.waspssdrools.droolschance.cts2.test.denotes.model.Antibiotic;
import org.aike.waspssdrools.droolschance.cts2.test.denotes.vocabulary.VocabularyAtc;


public class AntibioticConceptDescriptorGenerator implements ConceptDescriptorGenerator<Antibiotic> {

	@Override
	public URI getCodeURI(Antibiotic concept) {
		return URI.create( VocabularyAtc.DEFAULT_IRI + "#" +concept.getCodAtc()+"_"+Utils.generateDroolsName(concept.getCodAtc()));
	}
	@Override
	public String getCode(Antibiotic concept) {
		return concept.getCodAtc()+"_"+Utils.generateDroolsName(concept.getCodAtc());
	}
	@Override
	public String getDisplayName(Antibiotic concept) {
		return concept.getCodAtc();
	}
	@Override
	public String getCodeSystem(Antibiotic concept) {
		return VocabularyAtc.DEFAULT_IRI;
	}
	@Override
	public String getCodeSystemName(Antibiotic concept) {
		return "";
	}			
	
}
