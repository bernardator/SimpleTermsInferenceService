package org.aike.waspssdrools.droolschance.cts2.services;

import java.util.List;
import java.util.Set;

import edu.mayo.cts2.framework.model.command.Page;
import edu.mayo.cts2.framework.model.command.ResolvedReadContext;
import edu.mayo.cts2.framework.model.core.ComponentReference;
import edu.mayo.cts2.framework.model.core.EntityReferenceList;
import edu.mayo.cts2.framework.model.core.MatchAlgorithmReference;
import edu.mayo.cts2.framework.model.core.OpaqueData;
import edu.mayo.cts2.framework.model.core.PredicateReference;
import edu.mayo.cts2.framework.model.core.SortCriteria;
import edu.mayo.cts2.framework.model.core.SourceReference;
import edu.mayo.cts2.framework.model.core.URIAndEntityName;
import edu.mayo.cts2.framework.model.entity.EntityDirectoryEntry;
import edu.mayo.cts2.framework.model.service.core.DocumentedNamespaceReference;
import edu.mayo.cts2.framework.model.service.core.EntityNameOrURI;
import edu.mayo.cts2.framework.model.service.core.NameOrURI;
import edu.mayo.cts2.framework.model.valuesetdefinition.ResolvedValueSet;
import edu.mayo.cts2.framework.service.profile.valuesetdefinition.ResolvedValueSetResolutionEntityQuery;
import edu.mayo.cts2.framework.service.profile.valuesetdefinition.ResolvedValueSetResult;
import edu.mayo.cts2.framework.service.profile.valuesetdefinition.ValueSetDefinitionResolutionService;
import edu.mayo.cts2.framework.service.profile.valuesetdefinition.name.ValueSetDefinitionReadId;

public class SimpleValueSetDefinitionResolutionService implements ValueSetDefinitionResolutionService {

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

	@Override
	public ResolvedValueSetResult<URIAndEntityName> resolveDefinition(
			ValueSetDefinitionReadId definitionId,
			Set<NameOrURI> codeSystemVersions, NameOrURI tag,
			SortCriteria sortCriteria, ResolvedReadContext readContext,
			Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResolvedValueSetResult<EntityDirectoryEntry> resolveDefinitionAsEntityDirectory(
			ValueSetDefinitionReadId definitionId,
			Set<NameOrURI> codeSystemVersions, NameOrURI tag,
			ResolvedValueSetResolutionEntityQuery query,
			SortCriteria sortCriteria, ResolvedReadContext readContext,
			Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResolvedValueSet resolveDefinitionAsCompleteSet(
			ValueSetDefinitionReadId definitionId,
			Set<NameOrURI> codeSystemVersions, NameOrURI tag,
			SortCriteria sortCriteria, ResolvedReadContext readContext) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntityReferenceList contains(ValueSetDefinitionReadId definitionId,
			Set<EntityNameOrURI> entities) {
		// TODO Auto-generated method stub
		return null;
	}

}
