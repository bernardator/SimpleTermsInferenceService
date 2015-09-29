package org.aike.waspssdrools.droolschance.cts2.services;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.aike.waspssdrools.droolschance.cts2.SimpleTermsInferenceServiceImpl;

import edu.mayo.cts2.framework.model.command.Page;
import edu.mayo.cts2.framework.model.command.ResolvedReadContext;
import edu.mayo.cts2.framework.model.core.ComponentReference;
import edu.mayo.cts2.framework.model.core.EntityReferenceList;
import edu.mayo.cts2.framework.model.core.MatchAlgorithmReference;
import edu.mayo.cts2.framework.model.core.OpaqueData;
import edu.mayo.cts2.framework.model.core.PredicateReference;
import edu.mayo.cts2.framework.model.core.SortCriteria;
import edu.mayo.cts2.framework.model.core.SourceReference;
import edu.mayo.cts2.framework.model.core.VersionTagReference;
import edu.mayo.cts2.framework.model.directory.DirectoryResult;
import edu.mayo.cts2.framework.model.entity.EntityDirectoryEntry;
import edu.mayo.cts2.framework.model.entity.EntityListEntry;
import edu.mayo.cts2.framework.model.service.core.DocumentedNamespaceReference;
import edu.mayo.cts2.framework.model.service.core.EntityNameOrURI;
import edu.mayo.cts2.framework.model.service.core.EntityNameOrURIList;
import edu.mayo.cts2.framework.service.command.restriction.EntityDescriptionQueryServiceRestrictions;
import edu.mayo.cts2.framework.service.profile.entitydescription.EntityDescriptionQuery;
import edu.mayo.cts2.framework.service.profile.entitydescription.EntityDescriptionQueryService;


public class SimpleEntityDescriptionQueryService implements EntityDescriptionQueryService {
	
	
	SimpleTermsInferenceServiceImpl ontoManager;
	
	public SimpleEntityDescriptionQueryService(SimpleTermsInferenceServiceImpl ontoManager) {
		this.ontoManager = ontoManager;
	}
	
	@Override
	public DirectoryResult<EntityDirectoryEntry> getResourceSummaries(
			EntityDescriptionQuery query, SortCriteria sortCriteria, Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DirectoryResult<EntityListEntry> getResourceList(
			EntityDescriptionQuery query, SortCriteria sortCriteria, Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int count(EntityDescriptionQuery query) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Set<? extends MatchAlgorithmReference> getSupportedMatchAlgorithms() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<? extends ComponentReference> getSupportedSearchReferences() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<? extends ComponentReference> getSupportedSortReferences() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<PredicateReference> getKnownProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServiceName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OpaqueData getServiceDescription() {
		// TODO Auto-generated method stub
		return null;
	}

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

	/**
	 * Return true if 'entity' is in the set generated applying 'restrictions'
	 * 
	 * Only HierarchyRestrictions.DESCEDANT is checked. Any other kind of restriction
	 * will generate a log warning message
	 * 
	 */
	@Override
	public boolean isEntityInSet(EntityNameOrURI entity, EntityDescriptionQuery restrictions, ResolvedReadContext readContext) {
		if (restrictions != null)  {
			EntityDescriptionQueryServiceRestrictions realRestrictions;
			realRestrictions = restrictions.getRestrictions();
			if (realRestrictions != null) {
				EntityDescriptionQueryServiceRestrictions.HierarchyRestriction hierarchyRestriction;
				hierarchyRestriction = realRestrictions.getHierarchyRestriction();
				if ( (hierarchyRestriction                    != null) && 
				     (hierarchyRestriction.getEntity()        != null) &&
				     (hierarchyRestriction.getHierarchyType() != null) ) {
					if (hierarchyRestriction.getHierarchyType().equals(EntityDescriptionQueryServiceRestrictions.HierarchyRestriction.HierarchyType.DESCENDANTS)) {
						return ontoManager.isDescendantOf(entity, hierarchyRestriction.getEntity());
					}
				}
			}
		}

		// If we reach here, something has gone wrong or no we have no valid parameters. Report. 
		Logger.getLogger(SimpleEntityDescriptionQueryService.class.getName()).warning("Restrictions not supported by the CTS2 service. Returning false");
		return false;
	}

	@Override
	public EntityReferenceList resolveAsEntityReferenceList(
			EntityDescriptionQuery restrictions, ResolvedReadContext readContext) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntityNameOrURIList intersectEntityList(
			Set<EntityNameOrURI> entities, EntityDescriptionQuery restrictions,
			ResolvedReadContext readContext) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<? extends VersionTagReference> getSupportedTags() {
		// TODO Auto-generated method stub
		return null;
	}
}
