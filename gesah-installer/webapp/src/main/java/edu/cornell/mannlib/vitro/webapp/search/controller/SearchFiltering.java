package edu.cornell.mannlib.vitro.webapp.search.controller;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.QuerySolutionMap;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.shared.Lock;

import edu.cornell.mannlib.vitro.webapp.application.ApplicationUtils;
import edu.cornell.mannlib.vitro.webapp.controller.VitroRequest;
import edu.cornell.mannlib.vitro.webapp.controller.freemarker.UrlBuilder.ParamMap;
import edu.cornell.mannlib.vitro.webapp.modelaccess.ModelAccess;
import edu.cornell.mannlib.vitro.webapp.modules.searchEngine.SearchEngine;
import edu.cornell.mannlib.vitro.webapp.modules.searchEngine.SearchFacetField;
import edu.cornell.mannlib.vitro.webapp.modules.searchEngine.SearchFacetField.Count;
import edu.cornell.mannlib.vitro.webapp.modules.searchEngine.SearchQuery;
import edu.cornell.mannlib.vitro.webapp.modules.searchEngine.SearchResponse;

public class SearchFiltering {

	private static final String FILTER_RANGE = "filter_range_";
	private static final String FILTER_INPUT_PREFIX = "filter_input_";
	private static final String FILTERS = "filters";

	private static final String FILTER_QUERY = "       PREFIX vitro: <http://vitro.mannlib.cornell.edu/ns/vitro/public#>\n"
			+ "     PREFIX search: <https://osl.tib.eu/vitro-searchOntology#>\n"
			+ "     PREFIX gesah:    <http://ontology.tib.eu/gesah/>\n"
			+ "     PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
			+ "     PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
			+ "SELECT ?filter_id ?filter_type ?filter_label ?value_label ?value_id  ?field_name ?filter_order ?value_order "
			+ " (STR(?isUriReq) as ?isUri ) ?multivalued ?input ?regex ?facet ?min ?max \n"
			+ " 	WHERE {\n" + " 	    ?filter rdf:type search:Filter .\n"
			+ "        ?filter rdfs:label ?filter_label .\n" + "        ?filter search:id ?filter_id .\n"
			+ "        ?filter <http://vitro.mannlib.cornell.edu/ns/vitro/0.7#mostSpecificType> ?filter_type .\n"
			+ "        ?filter search:filterField ?field .\n" + "   		?field search:indexField ?field_name .\n"
			+ "         OPTIONAL {?filter search:hasKnownValue ?value . \n"
			+ "         	?value rdfs:label ?value_label .\n" + "         	?value search:id ?value_id .\n"
			+ "            OPTIONAL {?value search:order ?value_order}\n" + "  		 }\n"
			+ "  		 OPTIONAL {?field search:multivalued ?multivalued}\n"
			+ "  		 OPTIONAL {?filter search:isUriValues ?isUriReq }\n"
			+ "  		 OPTIONAL {?filter search:userInput ?input }\n"
			+ "  		 OPTIONAL {?filter search:userInputRegex ?regex }\n"
			+ "  		 OPTIONAL {?filter search:facetResults ?facet }\n"
			+ "  		 OPTIONAL {?filter search:from ?min }\n" + "  		 OPTIONAL {?filter search:to ?max }\n"
			+ "        OPTIONAL {?filter search:order ?filter_order }\n"
			+ " 	} ORDER BY ?filter_id ?filter_order ?value_order";

	static final String LABEL_QUERY = "     PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
			+ "    SELECT ?label\n" + " 	WHERE {\n" + "		?uri rdfs:label ?label .\n" + " 	} LIMIT 1";

	static void addFiltersToQuery(VitroRequest vreq, SearchQuery query, Map<String, SearchFilter> filterById) {
		Enumeration<String> paramNames = vreq.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramFilterName = paramNames.nextElement();
			if (!StringUtils.isBlank(paramFilterName) && paramFilterName.startsWith(SearchFiltering.FILTERS)) {
				String[] filters = vreq.getParameterValues(paramFilterName);
				if (filters != null && filters.length > 0) {
					for (String filter : filters) {
						String[] pair = filter.split(":", 2);
						if (pair.length == 2) {
							String name = pair[0].replace("\"", "");
							String value = pair[1].replace("\"", "");
							SearchFilter searchFilter = filterById.get(name);
							if (searchFilter != null && searchFilter.getField() != null) {
								query.addFilterQuery(searchFilter.getField() + ":\"" + value + "\"");
							}

						}
					}
				}

			}
		}
		for (String filterId : filterById.keySet()) {
			SearchFilter searchFilter = filterById.get(filterId);
			if (searchFilter.isInput()) {
				SearchFiltering.addInputFilter(query, searchFilter);
			} else if (searchFilter.isRange()) {
				SearchFiltering.addRangeFilter(query, searchFilter);
			}
		}
	}

	private static void addRangeFilter(SearchQuery query, SearchFilter searchFilter) {
		String rangeText = searchFilter.getRangeText();
		if (StringUtils.isBlank(rangeText)) {
			return;
		}
		query.addFilterQuery(searchFilter.getField() + ":\"" + rangeText + "\"");
	}

	private static void addInputFilter(SearchQuery query, SearchFilter searchFilter) {
		if (StringUtils.isBlank(searchFilter.getInputText())) {
			return;
		}
		String searchText = searchFilter.getInputText();
		if (searchFilter.isInputRegex()) {
			searchText = searchText.replaceAll("([ )(:])", "\\\\$1") + "*";
			query.addFilterQuery(searchFilter.getField() + ":" + searchText);
		} else {
			query.addFilterQuery(searchFilter.getField() + ":\"" + searchText + "\"");
		}
	}

	static Map<String, List<String>> getRequestFilters(VitroRequest vreq) {
		Map<String, List<String>> requestFilters = new HashMap<>();
		Enumeration<String> paramNames = vreq.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramFilterName = paramNames.nextElement();
			if (!StringUtils.isBlank(paramFilterName) && paramFilterName.startsWith(SearchFiltering.FILTERS)) {
				String[] filters = vreq.getParameterValues(paramFilterName);
				if (filters != null && filters.length > 0) {
					for (String filter : filters) {
						String[] pair = filter.split(":", 2);
						if (pair.length == 2) {
							String name = pair[0].replace("\"", "");
							String value = pair[1].replace("\"", "");
							if (requestFilters.containsKey(name)) {
								List<String> list = requestFilters.get(name);
								list.add(value);
							} else {
								requestFilters.put(name, new LinkedList<String>(Arrays.asList(value)));
							}
						}
					}
				}
			}
		}

		return requestFilters;
	}

	private static String getFilterInputText(VitroRequest vreq, String name) {
		String[] values = vreq.getParameterValues(SearchFiltering.FILTER_INPUT_PREFIX + name);
		if (values != null && values.length > 0) {
			return values[0];
		}
		return "";
	}

	private static String getFilterRangeText(VitroRequest vreq, String name) {
		String[] values = vreq.getParameterValues(SearchFiltering.FILTER_RANGE + name);
		if (values != null && values.length > 0) {
			return values[0];
		}
		return "";
	}

	static void setSelectedFilters(Map<String, SearchFilter> filtersByField, Map<String, List<String>> requestFilters) {
		for (SearchFilter filter : filtersByField.values()) {
			if (requestFilters.containsKey(filter.getId())) {
				List<String> requestValues = requestFilters.get(filter.getId());
				if (!SearchFiltering.isEmptyValues(requestValues)) {
					filter.setSelected(true);
					for (String requestValue : requestValues) {
						if (filter.getValues().containsKey(requestValue)) {
							FilterValue value = filter.getValue(requestValue);
							value.setSelected(true);
						}
					}
				}
			}
		}
	}

	static Map<String, SearchFilter> readFilterConfigurations(VitroRequest vreq) {
		Map<String, SearchFilter> filtersByField = new LinkedHashMap<>();
		Model model = ModelAccess.on(vreq).getOntModelSelector().getABoxModel();
		model.enterCriticalSection(Lock.READ);
		try {
			Query facetQuery = QueryFactory.create(SearchFiltering.FILTER_QUERY);
			QueryExecution qexec = QueryExecutionFactory.create(facetQuery, model);
			ResultSet results = qexec.execSelect();
			while (results.hasNext()) {
				QuerySolution solution = results.nextSolution();
				if (solution.get("filter_id") == null || solution.get("field_name") == null
						|| solution.get("filter_type") == null) {
					continue;
				}
				String resultFilterId = solution.get("filter_id").toString();
				String resultFieldName = solution.get("field_name").toString();

				SearchFilter filter = null;
				if (filtersByField.containsKey(resultFieldName)) {
					filter = filtersByField.get(resultFieldName);
				} else {
					filter = createSearchFilter(vreq, filtersByField, solution, resultFilterId, resultFieldName);
				}
				if (solution.get("value_id") == null) {
					continue;
				}
				String valueId = solution.get("value_id").toString();
				if (!filter.contains(valueId)) {
					FilterValue value = new FilterValue(valueId);
					value.setName(solution.get("value_label"));
					value.setOrder(solution.get("value_order"));
					filter.addValue(value);
				}
			}
		} finally {
			model.leaveCriticalSection();
		}
		Map<String, List<String>> requestFilters = getRequestFilters(vreq);
		setSelectedFilters(filtersByField, requestFilters);
		SearchFiltering.querySolrFilterInfo(filtersByField, vreq, requestFilters);
		return filtersByField;
	}

	private static SearchFilter createSearchFilter(VitroRequest vreq, Map<String, SearchFilter> filtersByField,
			QuerySolution solution, String resultFilterId, String resultFieldName) {
		SearchFilter filter;
		filter = new SearchFilter(resultFilterId);
		filtersByField.put(resultFieldName, filter);
		filter.setName(solution.get("filter_label"));
		filter.setOrder(solution.get("filter_order"));
		filter.setType(solution.get("filter_type"));
		if (solution.get("isUri") != null && "true".equals(solution.get("isUri").toString())) {
			filter.setLocalizationRequired(true);
		}
		RDFNode min = solution.get("min");
		if (min != null) {
			filter.setMin(min.asLiteral().toString());
		}
		RDFNode max = solution.get("max");
		if (max != null) {
			filter.setMax(max.asLiteral().toString());
		}
		filter.setField(resultFieldName);
		RDFNode multivalued = solution.get("multivalued");
		if (multivalued != null) {
			filter.setMultivalued(multivalued.asLiteral().getBoolean());
		}
		RDFNode input = solution.get("input");
		if (input != null) {
			filter.setInput(input.asLiteral().getBoolean());
		}
		RDFNode inputRegex = solution.get("regex");
		if (inputRegex != null) {
			filter.setInputRegex(inputRegex.asLiteral().getBoolean());
		}
		RDFNode facet = solution.get("facet");
		if (facet != null) {
			filter.setFacetsRequired(facet.asLiteral().getBoolean());
		}
		
		filter.setInputText(getFilterInputText(vreq, resultFilterId));
		filter.setRangeValues(getFilterRangeText(vreq, resultFilterId));

		// String filterTo = getFilterTo(vreq, resultFilterId);
		// if (!StringUtils.isBlank(filterTo)) {
		// filter.setToValue(filterTo);
		// filter.setSelected(true);
		// }
		// String filterFrom = getFilterFrom(vreq, resultFilterId);
		// if (!StringUtils.isBlank(filterFrom)) {
		// filter.setFromValue(filterFrom);
		// filter.setSelected(true);
		// }
		return filter;
	}

	static void querySolrFilterInfo(Map<String, SearchFilter> filtersByField, VitroRequest vreq,
			Map<String, List<String>> requestFilters) {
		SearchQuery query = ApplicationUtils.instance().getSearchEngine().createQuery("*:*");
		query.setRows(0);
		query.setFacetLimit(1000);
		SearchFiltering.addFacetFieldsToQuery(filtersByField, query);
		SearchEngine search = ApplicationUtils.instance().getSearchEngine();
		SearchResponse response = null;
		try {
			response = search.query(query);
			List<SearchFacetField> resultfacetFields = response.getFacetFields();
			for (SearchFacetField resultField : resultfacetFields) {
				SearchFilter searchFilter = filtersByField.get(resultField.getName());
				if (searchFilter == null) {
					continue;
				}
				List<Count> values = resultField.getValues();
				for (Count value : values) {
					String name = value.getName();
					FilterValue filterValue = searchFilter.getValue(name);
					if (filterValue == null) {
						filterValue = new FilterValue(name);
						searchFilter.addValue(filterValue);
					}

					if (requestFilters.containsKey(searchFilter.getId())) {
						List<String> requestedValues = requestFilters.get(searchFilter.getId());
						if (requestedValues.contains(name)) {
							filterValue.setSelected(true);
							searchFilter.setSelected(true);

						}
					}
					if (searchFilter.isLocalizationRequired()) {
						String label = SearchFiltering.getUriLabel(value.getName(), vreq);
						if (!StringUtils.isBlank(label)) {
							filterValue.setName(label);
						}
					}
				}
			}
		} catch (Exception e) {
			ExtendedSearchController.log.error(e, e);
		}
	}

	static boolean isEmptyValues(List<String> requestedValues) {
		if (requestedValues.isEmpty()) {
			return true;
		}
		for (String value : requestedValues) {
			if (!StringUtils.isBlank(value)) {
				return false;
			}
		}
		return true;
	}

	static String getUriLabel(String uri, VitroRequest vreq) {
		String result = "";
		Model model = ModelAccess.on(vreq).getOntModelSelector().getFullModel();
		model.enterCriticalSection(Lock.READ);
		try {
			QuerySolutionMap initialBindings = new QuerySolutionMap();
			initialBindings.add("uri", ResourceFactory.createResource(uri));
			Query facetQuery = QueryFactory.create(SearchFiltering.LABEL_QUERY);
			QueryExecution qexec = QueryExecutionFactory.create(facetQuery, model, initialBindings);
			ResultSet results = qexec.execSelect();
			if (results.hasNext()) {
				QuerySolution solution = results.nextSolution();
				RDFNode rdfNode = solution.get("label");
				Literal literal = rdfNode.asLiteral();
				result = literal.getLexicalForm();
			}
		} finally {
			model.leaveCriticalSection();
		}
		return result;
	}

	static void addFacetFieldsToQuery(Map<String, SearchFilter> filters, SearchQuery query) {
		for (String fieldId : filters.keySet()) {
			SearchFilter filter = filters.get(fieldId);
			if (filter.isFacetsRequired()) {
				query.addFacetFields(fieldId);
			}
		}
	}

	static Map<String, SearchFilter> getFiltersById(Map<String, SearchFilter> filtersByField) {
		Map<String, SearchFilter> filtersById = filtersByField.values().stream()
				.collect(Collectors.toMap(SearchFilter::getId, Function.identity()));
		return filtersById;
	}

	static void addFiltersToPageLinks(VitroRequest vreq, ParamMap pagingLinkParams, Enumeration<String> paramNames) {
		while (paramNames.hasMoreElements()) {
			String paramFilterName = paramNames.nextElement();
			if (!StringUtils.isBlank(paramFilterName) && (paramFilterName.startsWith(FILTERS)
					|| paramFilterName.startsWith(FILTER_RANGE) || paramFilterName.startsWith(FILTER_INPUT_PREFIX))) {
				String[] values = vreq.getParameterValues(paramFilterName);
				if (values.length > 0) {
					pagingLinkParams.put(paramFilterName, values[0]);
				}
			}
		}
	}

}
