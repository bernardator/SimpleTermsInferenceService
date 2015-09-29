package org.aike.waspssdrools.droolschance.cts2.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Set;

import org.aike.waspssdrools.droolschance.cts2.SimpleTermsInferenceService;
import org.junit.Test;

import edu.mayo.cts2.framework.model.command.ResolvedFilter;
import edu.mayo.cts2.framework.model.command.ResolvedReadContext;
import edu.mayo.cts2.framework.model.service.core.EntityNameOrURI;
import edu.mayo.cts2.framework.model.service.core.Query;
import edu.mayo.cts2.framework.model.util.ModelUtils;
import edu.mayo.cts2.framework.service.command.restriction.EntityDescriptionQueryServiceRestrictions;
import edu.mayo.cts2.framework.service.profile.entitydescription.EntitiesFromAssociationsQuery;
import edu.mayo.cts2.framework.service.profile.entitydescription.EntityDescriptionQuery;

public class TestSimpleTermsInferenceService {

    private static final String MICRO_ONT       = "src/test/resources/ontologies/microSmall.owl";
    private static final String ATC_ONT         = "src/test/resources/ontologies/atcSmall.owl";
	private static final String MICRO_ONT_IRI 	= "http://www.um.es/waspss/testOntologies/2015/1/microorganisms";
	private static final String ATC_ONT_IRI     = "http://www.um.es/waspss/testOntologies/atcs";
    private static final String MICRO_NAMESPACE = "microorganisms";
    private static final String ATC_NAMESPACE   = "atcs";
    
	@Test
	public void createService() {
		SimpleTermsInferenceService htis = new SimpleTermsInferenceService();
		try {
			htis.addOntologyFromOntologyDocument(MICRO_NAMESPACE,new File(MICRO_ONT));
			htis.addOntologyFromOntologyDocument(ATC_NAMESPACE,new File(ATC_ONT));
			
			// Microorganisms - Individuals
			EntityNameOrURI escherichiaColi = new EntityNameOrURI();
			escherichiaColi.setEntityName( ModelUtils.createScopedEntityName( "Escherichia Coli", MICRO_NAMESPACE ) );
			escherichiaColi.setUri(MICRO_ONT_IRI+"#360-Escherichia_coli");
			
			EntityNameOrURI pseudomonasAeruginosa = new EntityNameOrURI();
			pseudomonasAeruginosa.setEntityName( ModelUtils.createScopedEntityName( "Pseudomonas Aeruginosa", MICRO_NAMESPACE ) );
			pseudomonasAeruginosa .setUri(MICRO_ONT_IRI+"#713-Pseudomonas_aeruginosa");

			// Microorganisms - Classes
			EntityNameOrURI enterobacteriaceae = new EntityNameOrURI();
			enterobacteriaceae.setEntityName( ModelUtils.createScopedEntityName( "Enterobacteriaceae", MICRO_NAMESPACE ) );
			enterobacteriaceae .setUri(MICRO_ONT_IRI+"/Nodo543");

			// Atcs - Individuals
			EntityNameOrURI fusidicAcid = new EntityNameOrURI();
			fusidicAcid.setEntityName( ModelUtils.createScopedEntityName( "Fusidic Acid", ATC_NAMESPACE ) );
			fusidicAcid .setUri(ATC_ONT_IRI+"#Ácido_fusídico_J01XC01");

			EntityNameOrURI streptomycin = new EntityNameOrURI();
			streptomycin.setEntityName( ModelUtils.createScopedEntityName( "Streptomycin", ATC_NAMESPACE ) );
			streptomycin .setUri(ATC_ONT_IRI+"#Estreptomicina_J01GA01");

			// Atcs - Classes
			EntityNameOrURI aminoglycosides = new EntityNameOrURI();
			aminoglycosides .setEntityName( ModelUtils.createScopedEntityName( "Aminoglycosides", ATC_NAMESPACE ) );
			aminoglycosides  .setUri("http://www.ebi.ac.uk/Rebholz-srv/atc/J01G");

			// Tests
			assertTrue(htis.entityDescriptionQuery().isEntityInSet(escherichiaColi, new HierarchyEntityDescriptionQueryImpl(enterobacteriaceae), null));
			assertFalse(htis.entityDescriptionQuery().isEntityInSet(pseudomonasAeruginosa, new HierarchyEntityDescriptionQueryImpl(enterobacteriaceae), null));
			assertTrue(htis.entityDescriptionQuery().isEntityInSet(streptomycin, new HierarchyEntityDescriptionQueryImpl(aminoglycosides), null));
			assertFalse(htis.entityDescriptionQuery().isEntityInSet(fusidicAcid, new HierarchyEntityDescriptionQueryImpl(aminoglycosides), null));
			assertFalse(htis.entityDescriptionQuery().isEntityInSet(fusidicAcid, new HierarchyEntityDescriptionQueryImpl(enterobacteriaceae), null));

			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Class extracted from Drools Chance project (DenotesEvaluatorImpl.java)
	 * 
	 * https://github.com/droolsjbpm/drools-chance/blob/0.6.x/drools-shapes/drools-shapes-terms/drools-shapes-terms-impl/src/main/java/org/drools/shapes/terms/evaluator/DenotesEvaluatorImpl.java
	 * 
	 * (extracted over Sept/2015)
	 * 
	 *
	 */
    private static class HierarchyEntityDescriptionQueryImpl implements EntityDescriptionQuery {

        private EntityNameOrURI entity;

        private HierarchyEntityDescriptionQueryImpl(EntityNameOrURI entity) {
            super();
            this.entity = entity;
        }

        @Override
        public EntitiesFromAssociationsQuery getEntitiesFromAssociationsQuery() {
            return null;
        }

        @Override
        public EntityDescriptionQueryServiceRestrictions getRestrictions() {
            EntityDescriptionQueryServiceRestrictions restrictions = new EntityDescriptionQueryServiceRestrictions();

            EntityDescriptionQueryServiceRestrictions.HierarchyRestriction hierarchyRestriction = new EntityDescriptionQueryServiceRestrictions.HierarchyRestriction();
            hierarchyRestriction.setHierarchyType(EntityDescriptionQueryServiceRestrictions.HierarchyRestriction.HierarchyType.DESCENDANTS);

            hierarchyRestriction.setEntity(this.entity);

            restrictions.setHierarchyRestriction(hierarchyRestriction);

            return restrictions;
        }

        @Override
        public Query getQuery() {
            return null;
        }

        @Override
        public Set<ResolvedFilter> getFilterComponent() {
            return null;
        }

        @Override
        public ResolvedReadContext getReadContext() {
            return null;
        }
    }

}
