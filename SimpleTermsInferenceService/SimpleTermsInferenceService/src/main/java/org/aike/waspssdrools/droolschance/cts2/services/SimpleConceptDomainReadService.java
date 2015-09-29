package org.aike.waspssdrools.droolschance.cts2.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.aike.waspssdrools.droolschance.cts2.SimpleTermsInferenceServiceImpl;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;

import edu.mayo.cts2.framework.model.command.ResolvedReadContext;
import edu.mayo.cts2.framework.model.conceptdomain.ConceptDomainCatalogEntry;
import edu.mayo.cts2.framework.model.core.OpaqueData;
import edu.mayo.cts2.framework.model.core.SourceReference;
import edu.mayo.cts2.framework.model.service.core.DocumentedNamespaceReference;
import edu.mayo.cts2.framework.model.service.core.EntityNameOrURI;
import edu.mayo.cts2.framework.model.service.core.NameOrURI;
import edu.mayo.cts2.framework.service.profile.conceptdomain.ConceptDomainReadService;

public class SimpleConceptDomainReadService implements ConceptDomainReadService {

	///////////////////////////////////////////////////
	// Internal attributes with their default values //
    ///////////////////////////////////////////////////

	String serviceName     = "";
	String serviceProvider = "";
	OpaqueData description = null;
	
	SimpleTermsInferenceServiceImpl ontoManager;
	
	public SimpleConceptDomainReadService(SimpleTermsInferenceServiceImpl ontoManager) {
		this.ontoManager = ontoManager;
	}
	
	/**
	 *  Look for the identifier URI (interpreted as IRI) in the included ontologies. 
	 *  
	 *  @param identifier identifier of the element to be read. Only URI will be used
	 *  @param readContext Ignored.
	 */
	@Override
	public ConceptDomainCatalogEntry read(NameOrURI identifier, ResolvedReadContext readContext) {
		return read(identifier.getUri());
	}

	@Override
	public boolean exists(NameOrURI identifier, ResolvedReadContext readContext) {
		return exists(identifier.getUri());
	}

	/**
	 * In the end, IRI as strings will be searched into the ontologies
	 * @param iriStr
	 * @return
	 */
	private boolean exists(String iriStr) {
		IRI iri;  

		iri = IRI.create(iriStr);
		for (OWLOntology o : ontoManager.getOntologies().values()) {
			if (!o.getEntitiesInSignature(iri).isEmpty())
				return true;
		}
		return false;
		
	}
	
	@Override
	public String getServiceName()                   { return serviceName;             }
	public void   setServiceName(String serviceName) { this.serviceName = serviceName; }
	
	@Override
	public OpaqueData getServiceDescription() {		return description;	}
	public void setServiceDescription(OpaqueData description) { this.description = description; }

	@Override
	public String getServiceVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SourceReference getServiceProvider() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DocumentedNamespaceReference> getKnownNamespaceList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConceptDomainCatalogEntry readByDefiningEntity(
			EntityNameOrURI entity, ResolvedReadContext resolvedReadContext) {
		return read(entity.getUri());
	}
	
	@Override
	public boolean existsDefiningEntity(EntityNameOrURI entity,
			ResolvedReadContext resolvedReadContext) {
		// TODO Auto-generated method stub
		return false;
	}

	private ConceptDomainCatalogEntry read (String iriStr) {
        IRI iri;
        Set<OWLEntity> entities = new HashSet<OWLEntity>();
        
        iri = IRI.create(iriStr);
        
		for (OWLOntology o : ontoManager.getOntologies().values()) {
        	entities.addAll(o.getEntitiesInSignature(iri));
        }
		/*
		if (entities)
		
		ConceptDomain
		OWLOntology ontology;
		OWLReasoner reasoner = new Reasoner.ReasonerFactory().createReasoner(ontology);
		*/
		// TODO Auto-generated method stub
		return null;

	}
}
