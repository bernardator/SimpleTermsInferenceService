package org.aike.waspssdrools.droolschance.cts2.test.denotes;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.aike.waspssdrools.droolschance.cts2.SimpleTermsInferenceService;
import org.aike.waspssdrools.droolschance.cts2.test.denotes.model.Microorganism;
import org.aike.waspssdrools.droolschance.cts2.test.denotes.util.ConceptDescriptorFactory;
import org.aike.waspssdrools.droolschance.cts2.test.denotes.util.MicroorganismConceptDescriptorGenerator;
import org.aike.waspssdrools.droolschance.cts2.test.denotes.vocabulary.VocabularyMicro;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.kie.api.conf.EqualityBehaviorOption;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.conf.EvaluatorOption;
import org.kie.internal.utils.KieHelper;
import org.drools.shapes.terms.TermsInferenceServiceFactory;
import org.drools.shapes.terms.evaluator.DenotesEvaluatorDefinition;

import cts2.mayo.edu.terms_metamodel.terms.ConceptDescriptor;

/**
 * Testing the use of the 'denotes' operator with the SimpleTermsInferenceService
 * @author bernardo
 *
 */
public class TestDenotesWithSimpleTIS {
	static List<String> detectedRelations = new ArrayList<String>();
	static List<String> expectedRelations = Arrays.asList(
		"360-"+ VocabularyMicro.DEFAULT_IRI + "/Nodo562",  // Escherichia Coli (individual) with Escherichia Coli (Class)
		"360-"+ VocabularyMicro.DEFAULT_IRI + "/Nodo543",  // Escherichia Coli (individual) with Enterobacteriaceae (Class)
		"331-"+ VocabularyMicro.DEFAULT_IRI + "/Nodo543",  // Enterobacter aerogenes (individual) with Enterobacteriaceae (Class)
		"360-"+ VocabularyMicro.DEFAULT_IRI + "/Nodo1224"  // Escherichia Coli (individual) with Proteobacteria (Class)
	);
	
	
	@Test
	public final void doDenotesTest() {
		// Register cd generator for Microorganism
		ConceptDescriptorFactory.register(Microorganism.class, new MicroorganismConceptDescriptorGenerator());

		KieSession kSession = null;
		
		try {
			SimpleTermsInferenceService termsService = new SimpleTermsInferenceService();

			// The namespace must be the same that the value in the CodeSystem field in the vocabulary CHECK
			termsService.addOntologyFromOntologyDocument(VocabularyMicro.DEFAULT_IRI, new File("src/test/resources/ontologies/microSmall.owl"));
			TermsInferenceServiceFactory.instance().setDefaultValueSetProcessor(SimpleTermsInferenceService.KIND, termsService);
			
			kSession = new KieHelper(EvaluatorOption.get( "denotes", new DenotesEvaluatorDefinition() ))
					.addContent( generateDefinitions()+System.lineSeparator()+generateDenotesCDFactoryRules(), ResourceType.DRL) 
					.build(EqualityBehaviorOption.EQUALITY)
					.newKieSession();

			if (kSession != null) {
				insertFacts(kSession);
				System.out.println("Facts loaded = " + kSession.getFactCount());				
				System.out.println("Executing rules...");
				Logger.getLogger(this.getClass()).info("Facts loaded = " + kSession.getFactCount());				
				Logger.getLogger(this.getClass()).info("Executing rules...");

				detectedRelations.clear();
				kSession.fireAllRules();
				
				System.out.println("Detected relations:");
				for (String s : detectedRelations) System.out.println("\t"+s);

				if (expectedRelations.containsAll(detectedRelations) && detectedRelations.containsAll(expectedRelations)) {
					System.out.println("Ok");
					assertTrue(true);
				} else {
					System.out.println("FAIL!!!!");
					System.out.println("Expected relations:");
					for (String s : expectedRelations) System.out.println("\t"+s);
					assertTrue(false);
				}		
				
			} else {
				Logger.getLogger(this.getClass()).info("Something went wrong. No KieSession created!!" );
				assertTrue(false);
			}
			
		} catch (Throwable t) {
			Logger.getLogger(this.getClass()).error(t.getMessage(), t);
			t.printStackTrace();
		} 
	}
	
	
	
	public static final String generateRelationString(Microorganism micro, ConceptDescriptor cd) {
		return micro.getId()+"-"+cd.getUri().toString();
	}

	public static void detectedRelation(Microorganism micro, ConceptDescriptor cd) {
		detectedRelations.add(generateRelationString(micro,cd));
	}

	public static final String generateDenotesCDFactoryRules() {
		StringBuilder denotesRules = new StringBuilder();


//		denotesRules.append ("rule 'Detect anything'"												+System.lineSeparator());
//		denotesRules.append ("when"																							+System.lineSeparator());
//		denotesRules.append ("\t $micro : Microorganism () "	+System.lineSeparator());
//		denotesRules.append ("then"																							+System.lineSeparator());
//		denotesRules.append ("\t System.out.println(ConceptDescriptorFactory.getCD($micro));"	+System.lineSeparator());
//		denotesRules.append ("end"																							+System.lineSeparator());

		
		denotesRules.append ("rule 'Detect any Escherichia coli (Results: 1)'"												+System.lineSeparator());
		denotesRules.append ("when"																							+System.lineSeparator());
		denotesRules.append ("\t $micro : Microorganism (ConceptDescriptorFactory.getCD(this) denotes VocabularyMicro.EscherichiaColi) "	+System.lineSeparator());
		denotesRules.append ("then"																							+System.lineSeparator());
		denotesRules.append ("\t TestDenotesWithSimpleTIS.detectedRelation($micro,VocabularyMicro.EscherichiaColi);"	+System.lineSeparator());
		denotesRules.append ("\t System.out.println(\"Detected EColi\");"	+System.lineSeparator());

		denotesRules.append ("end"																							+System.lineSeparator());

		denotesRules.append ("rule 'Detect any Enterobacteriaceae (Results: 2)'"											+System.lineSeparator());
		denotesRules.append ("when"																							+System.lineSeparator());
		denotesRules.append ("\t $micro : Microorganism (ConceptDescriptorFactory.getCD(this)  denotes VocabularyMicro.Enterobacteriaceae) "	+System.lineSeparator());
		denotesRules.append ("then"																							+System.lineSeparator());
		denotesRules.append ("\t TestDenotesWithSimpleTIS.detectedRelation($micro,VocabularyMicro.Enterobacteriaceae);"	+System.lineSeparator());
		denotesRules.append ("end"																							+System.lineSeparator());

		denotesRules.append ("rule 'Check if exists any Escherichia Coli that is also a Proteobacteria (Results: 1)'"		+System.lineSeparator());
		denotesRules.append ("when"																							+System.lineSeparator());
		denotesRules.append ("\t $micro : Microorganism ("
				+ "								ConceptDescriptorFactory.getCD(this) denotes VocabularyMicro.EscherichiaColi, "
				+ "								ConceptDescriptorFactory.getCD(this) denotes VocabularyMicro.Proteobacteria) "			+System.lineSeparator());
		denotesRules.append ("then"																							+System.lineSeparator());
		denotesRules.append ("\t TestDenotesWithSimpleTIS.detectedRelation($micro,VocabularyMicro.Proteobacteria);"	+System.lineSeparator());
		denotesRules.append ("end"																							+System.lineSeparator());

		denotesRules.append ("rule 'Check if exists any Escherichia Coli that is also a Firmicutes (Results: 0)'"					+System.lineSeparator());
		denotesRules.append ("when"																							+System.lineSeparator());
		denotesRules.append ("\t $micro : Microorganism ("
				+ "								ConceptDescriptorFactory.getCD(this) denotes VocabularyMicro.EscherichiaColi, "
				+ "								ConceptDescriptorFactory.getCD(this) denotes VocabularyMicro.Firmicutes) "				+System.lineSeparator());
		denotesRules.append ("then"																							+System.lineSeparator());
		denotesRules.append ("\t TestDenotesWithSimpleTIS.detectedRelation($micro,VocabularyMicro.Firmicutes);"	+System.lineSeparator());
		denotesRules.append ("end"																							+System.lineSeparator());

		
		return denotesRules.toString();
	}
	

	
	public final String generateDefinitions() {
		StringBuilder header = new StringBuilder();
		header.append("package com.aike.waspssdrools.droolschancetests.ontologies;"+System.lineSeparator());
		header.append("import "+Microorganism.class.getName()                      +";"+System.lineSeparator());
		header.append("import "+VocabularyMicro.class.getName()                    +";"+System.lineSeparator());
		header.append("import "+TestDenotesWithSimpleTIS.class.getName()           +";"+System.lineSeparator());
		header.append("import "+ConceptDescriptorFactory.class.getName()           +";"+System.lineSeparator());
		header.append("import "+ConceptDescriptor.class.getName()                  +";"+System.lineSeparator());
		
		header.append("import java.util.ArrayList;"+System.lineSeparator());
		return header.toString();
	}
	
	public final void insertFacts(KieSession kSession) {
		kSession.insert(new Microorganism(360,"Escherichia Coli"));
		kSession.insert(new Microorganism(713,"Pseudomonas Aeruginosa"));
		kSession.insert(new Microorganism(348,"Enterococcus Faecalis"));
		kSession.insert(new Microorganism(331,"Enterobacter Aerogenes"));	

	}


}
