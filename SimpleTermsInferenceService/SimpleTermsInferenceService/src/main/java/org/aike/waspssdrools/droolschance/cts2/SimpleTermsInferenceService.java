package org.aike.waspssdrools.droolschance.cts2;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.aike.waspssdrools.droolschance.cts2.services.SimpleConceptDomainReadService;
import org.aike.waspssdrools.droolschance.cts2.services.SimpleEntityDescriptionQueryService;
import org.aike.waspssdrools.droolschance.cts2.services.SimpleResolvedValueSetResolutionService;
import org.aike.waspssdrools.droolschance.cts2.services.SimpleValueSetDefinitionResolutionService;
import org.aike.waspssdrools.droolschance.cts2.stis.options.SimpleTermsInferenceServiceOption;
import org.drools.shapes.terms.operations.internal.TermsInferenceService;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import edu.mayo.cts2.framework.service.profile.BaseService;
import edu.mayo.cts2.framework.service.profile.Cts2Profile;
import edu.mayo.cts2.framework.service.profile.conceptdomain.ConceptDomainReadService;
import edu.mayo.cts2.framework.service.profile.conceptdomainbinding.ConceptDomainBindingReadService;
import edu.mayo.cts2.framework.service.profile.entitydescription.EntityDescriptionQueryService;
import edu.mayo.cts2.framework.service.profile.mapentry.MapEntryReadService;
import edu.mayo.cts2.framework.service.profile.resolvedvalueset.ResolvedValueSetResolutionService;
import edu.mayo.cts2.framework.service.profile.valueset.ValueSetReadService;
import edu.mayo.cts2.framework.service.profile.valuesetdefinition.ValueSetDefinitionReadService;
import edu.mayo.cts2.framework.service.profile.valuesetdefinition.ValueSetDefinitionResolutionService;

public class SimpleTermsInferenceService implements TermsInferenceService {

	public static final String KIND = "simple";
	
	SimpleTermsInferenceServiceImpl ontoManager;
	Map<Class<? extends Cts2Profile>,BaseService> services;
    
	
	public SimpleTermsInferenceService() {
		ontoManager = new SimpleTermsInferenceServiceImpl();
		
		services = new HashMap<Class<? extends Cts2Profile>,BaseService>();

		services.put(ConceptDomainReadService.class           , new SimpleConceptDomainReadService(ontoManager));
		// TODO PROBLEM!!! BASESERVICE IS AVAILABLE IN TWO DIFFERENT PACKAGES. CHECK IT! AND REMOVE THE CAST OR IT WILL CRASH!!
		services.put(EntityDescriptionQueryService.class      , (BaseService) new SimpleEntityDescriptionQueryService(ontoManager));
		services.put(ResolvedValueSetResolutionService.class  , new SimpleResolvedValueSetResolutionService());
		services.put(ValueSetDefinitionResolutionService.class, new SimpleValueSetDefinitionResolutionService());
	}

	public SimpleTermsInferenceService(SimpleTermsInferenceServiceOption... options) {
		this();
		setOptions(options);
	}

	
	public void setOptions(SimpleTermsInferenceServiceOption... options) {
		for (SimpleTermsInferenceServiceOption opt : options) {
			ontoManager.setOption(opt);
		}
	}
    
	///////////////////////////////////////////////////////////////////
	// 'UNIQUE' METHODS
	///////////////////////////////////////////////////////////////////
    
    public void addOntologyFromOntologyDocument(String namespace,File doc) throws OWLOntologyCreationException{
    	ontoManager.addOntology(namespace, doc);
    }

	///////////////////////////////////////////////////////////////////
	//                       IMPLEMENTED SERVICES                    //
	///////////////////////////////////////////////////////////////////
	
	
	@Override
	public ConceptDomainReadService conceptDomainCatalogRead() {		
		return (ConceptDomainReadService) services.get(ConceptDomainReadService.class);
	}

	@Override
	public EntityDescriptionQueryService entityDescriptionQuery() {
		return (EntityDescriptionQueryService) services.get(EntityDescriptionQueryService.class);
	}

	@Override
	public ResolvedValueSetResolutionService resolvedValueSetResolution() {
		return (ResolvedValueSetResolutionService) services.get(ResolvedValueSetResolutionService.class);
	}
	
	@Override
	public ValueSetDefinitionResolutionService valueSetDefinitionResolution() {
		return (ValueSetDefinitionResolutionService ) services.get(ValueSetDefinitionResolutionService .class);
	}

	///////////////////////////////////////////////////////////////////
	//                     NOT IMPLEMENTED SERVICES                  //
	///////////////////////////////////////////////////////////////////
	
	@Override
	public ConceptDomainBindingReadService conceptDomainBindingRead() {
		return null;
	}

	@Override
	public MapEntryReadService mapEntryRead() {
		return null;
	}

	@Override
	public ValueSetReadService valueSetCatalogRead() {
		return null;
	}

	@Override
	public ValueSetDefinitionReadService valueSetDefinitionRead() {
		return null;
	}
}
