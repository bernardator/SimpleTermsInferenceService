package org.aike.waspssdrools.droolschance.cts2;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.log4j.Logger;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.EntityType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import edu.mayo.cts2.framework.model.service.core.EntityNameOrURI;


/**
 * Stores and centralizes access to the ontologies served
 * by the TermsInferenceService
 */
public class SimpleTermsInferenceServiceImpl {

	
	Map<String,OWLOntology> ontologies  = new HashMap<String,OWLOntology>();
	Map<String,OWLReasoner> reasoners   = new HashMap<String,OWLReasoner>();

	OWLOntologyManager      manager     = OWLManager.createOWLOntologyManager(); 
    OWLDataFactory          factory     = manager.getOWLDataFactory(); 

    Map<IRI,Set<IRI>> parents = new ConcurrentHashMap<IRI,Set<IRI>>();
    
    
    public SimpleTermsInferenceServiceImpl() {}
    
    
    public void addOntology(String namespace, File doc) throws OWLOntologyCreationException {
    	OWLOntology ontology = manager.loadOntologyFromOntologyDocument(doc); 
    	OWLReasoner reasoner = new org.semanticweb.HermiT.Reasoner(ontology);
    	
    	ontologies.put(namespace, ontology);
    	reasoners.put(namespace,  reasoner);
    }
    
    public OWLOntologyManager getManager() { return manager;  }
    
    public Map<String,OWLOntology> getOntologies() { return ontologies; }
	
    /**
     * Return true if 'descendant' is in the set of the descendants of 'ancestor'
     * 
     * TODO Right now, the method check for classes and individuals. 
     * TODO If 'descendant' is forced to be an individual and 'ancestor' a class, all these checks can be avoided 
     * In the ontology, 'descendant' can be an individual or a class.
     * If 'descendant' is an individual, the method will return true if 'ancestor' if one of the types of the individual.
     * If 'descendant' is a class, the method will return true if 'ancestor' is
     * 
     * 
     * @param descendant
     * @param ancestor
     * @return
     */
    public boolean isDescendantOf(EntityNameOrURI descendant, EntityNameOrURI ancestor) {

    	
    	if (descendant.getEntityName().getNamespace().equals(ancestor.getEntityName().getNamespace())){
    		String namespace = descendant.getEntityName().getNamespace();
    		OWLOntology onto = ontologies.get(namespace);
    		OWLReasoner reasoner = reasoners.get(namespace);

    		if (onto==null) {
    			Logger.getLogger(SimpleTermsInferenceServiceImpl.class).error("Ontology with namespace '"+descendant.getEntityName().getNamespace()+"' not found. 'denotes' operator could not work properly!!!");
    			return false;    			
    		}
    		
    		IRI aIri = IRI.create(ancestor.getUri());
    		IRI dIri = IRI.create(descendant.getUri());

    		// Lets try if it is cached
    		Set<IRI> parentSet = parents.get(dIri);
    		if (parentSet!=null) {
    			// Yuhuuu!!
    			return parentSet.contains(aIri);
    		}
    		
    		
    		OWLClass ac = null;
    		OWLClass dc = null;
    		OWLNamedIndividual dInd = null;
    		
    		// We obtain the ancestor class from the ontology, checking all the possible mistakes
    		Set<OWLEntity> aEntities = onto.getEntitiesInSignature(aIri);
    		if (aEntities.size()==1) {
    			for (OWLEntity e : aEntities) {
    				if (e.getEntityType().equals(EntityType.CLASS)) {
    					ac = e.asOWLClass();
    				} else {
    	    			Logger.getLogger(SimpleTermsInferenceServiceImpl.class).error("Ontology "+namespace+": IRI: "+aIri+" should be a class, and is a "+e.getEntityType()+". 'denotes' operator could not work properly!!!");
    	    			return false; 
    				}
    			}
    		} else {
    			if (aEntities.size()>1) {
    				Logger.getLogger(SimpleTermsInferenceServiceImpl.class).error("Ontology "+namespace+" with multiple IRIs: "+aIri+" . 'denotes' operator could not work properly!!!");
    			} else {
    				Logger.getLogger(SimpleTermsInferenceServiceImpl.class).error("Ontology "+namespace+" not contains IRI: "+aIri+" . 'denotes' operator could not work properly!!!");    				
    			}
    			return false;    			    			
    		}
    		
    		// We obtain the descendant class/individual from the ontology, checking all the possible mistakes
			Set<OWLEntity> entities = onto.getEntitiesInSignature(dIri); 
			if (entities.size()==1) {
    			for (OWLEntity e : entities) { // Should be executed only once
    				NodeSet<OWLClass> superClasses = null;
    				if (e.getEntityType().equals(EntityType.CLASS)) {
    					dc   = e.asOWLClass();
    					superClasses = reasoner.getSuperClasses(dc, false);
    				} else if (e.getEntityType().equals(EntityType.NAMED_INDIVIDUAL)) {
    					dInd = e.asOWLNamedIndividual();
    					superClasses = reasoner.getTypes(dInd, false);    					
    				} else {
    	    			Logger.getLogger(SimpleTermsInferenceServiceImpl.class).error("Ontology "+namespace+": IRI: "+aIri+" should be a class or an individual, and is a "+e.getEntityType()+". 'denotes' operator could not work properly!!!");
    	    			return false; 
    				}

					Set<IRI> currentNodeParents = new HashSet<IRI>();
					boolean found = false;
					for (Node<OWLClass> c : superClasses) {
						IRI parentIri = c.getRepresentativeElement().getIRI();
						currentNodeParents.add(parentIri);
						if (parentIri.equals(aIri)) found = true;
					}
					parents.put(dIri, currentNodeParents);
					
					return found;
    			}
    			// It never should reach here. 
    			Logger.getLogger(SimpleTermsInferenceServiceImpl.class).error("Problem found when comparing "+ancestor.getUri()+" and "+descendant.getUri()+". 'denotes' operator could not work properly!!!");
    			return false; 
    			
			} else {
    			if (aEntities.size()>1) {
    				// Two entities with the same IRI ?? Not allowed
    				Logger.getLogger(SimpleTermsInferenceServiceImpl.class).error("Ontology "+namespace+" with multiple IRIs: "+dIri+" . 'denotes' operator could not work properly!!!");
    			} else {
    				Logger.getLogger(SimpleTermsInferenceServiceImpl.class).error("Ontology "+namespace+" not contains IRI: "+dIri+" . 'denotes' operator could not work properly!!!");    				
    			}
    			return false;
			}
    	} else {
    		// Different namespaces. No mappings considered 
    		return false;
    	}
    	
    }
    
}
