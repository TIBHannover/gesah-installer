/* $This file is distributed under the terms of the license in LICENSE$ */

package edu.cornell.mannlib.vitro.webapp.search.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.QuerySolutionMap;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.shared.Lock;
import org.apache.jena.sparql.function.library.context;

import com.sun.mail.imap.protocol.BODY;

import edu.cornell.mannlib.vitro.webapp.application.ApplicationUtils;
import edu.cornell.mannlib.vitro.webapp.beans.ApplicationBean;
import edu.cornell.mannlib.vitro.webapp.beans.Individual;
import edu.cornell.mannlib.vitro.webapp.beans.VClass;
import edu.cornell.mannlib.vitro.webapp.beans.VClassGroup;
import edu.cornell.mannlib.vitro.webapp.controller.VitroRequest;
import edu.cornell.mannlib.vitro.webapp.controller.freemarker.FreemarkerHttpServlet;
import edu.cornell.mannlib.vitro.webapp.controller.freemarker.UrlBuilder;
import edu.cornell.mannlib.vitro.webapp.controller.freemarker.UrlBuilder.ParamMap;
import edu.cornell.mannlib.vitro.webapp.controller.freemarker.responsevalues.ExceptionResponseValues;
import edu.cornell.mannlib.vitro.webapp.controller.freemarker.responsevalues.ResponseValues;
import edu.cornell.mannlib.vitro.webapp.controller.freemarker.responsevalues.TemplateResponseValues;
import edu.cornell.mannlib.vitro.webapp.dao.IndividualDao;
import edu.cornell.mannlib.vitro.webapp.dao.VClassDao;
import edu.cornell.mannlib.vitro.webapp.dao.VClassGroupDao;
import edu.cornell.mannlib.vitro.webapp.dao.VClassGroupsForRequest;
import edu.cornell.mannlib.vitro.webapp.dao.VitroVocabulary;
import edu.cornell.mannlib.vitro.webapp.dao.jena.VClassGroupCache;
import edu.cornell.mannlib.vitro.webapp.i18n.I18n;
import edu.cornell.mannlib.vitro.webapp.modelaccess.ModelAccess;
import edu.cornell.mannlib.vitro.webapp.modules.searchEngine.SearchEngine;
import edu.cornell.mannlib.vitro.webapp.modules.searchEngine.SearchFacetField;
import edu.cornell.mannlib.vitro.webapp.modules.searchEngine.SearchFacetField.Count;
import edu.cornell.mannlib.vitro.webapp.modules.searchEngine.SearchQuery;
import edu.cornell.mannlib.vitro.webapp.modules.searchEngine.SearchQuery.Order;
import edu.cornell.mannlib.vitro.webapp.modules.searchEngine.SearchResponse;
import edu.cornell.mannlib.vitro.webapp.modules.searchEngine.SearchResultDocument;
import edu.cornell.mannlib.vitro.webapp.modules.searchEngine.SearchResultDocumentList;
import edu.cornell.mannlib.vitro.webapp.search.VitroSearchTermNames;
import edu.cornell.mannlib.vitro.webapp.web.templatemodels.LinkTemplateModel;
import edu.cornell.mannlib.vitro.webapp.web.templatemodels.searchresult.IndividualSearchResult;

/**
 * Paged search controller that uses the search engine
 */

@WebServlet(name = "ExtendedSearchController", urlPatterns = {"/extendedsearch","/extendedsearch.jsp","/extendedfedsearch","/extendedsearchcontroller"} )
public class ExtendedSearchController extends FreemarkerHttpServlet {

    private static final String FACETS = "facets";
	private static final long serialVersionUID = 1L;
    private static final Log log = LogFactory.getLog(ExtendedSearchController.class);

    protected static final int DEFAULT_HITS_PER_PAGE = 25;
    protected static final int DEFAULT_MAX_HIT_COUNT = 1000;

    private static final String PARAM_XML_REQUEST = "xml";
    private static final String PARAM_CSV_REQUEST = "csv";
    private static final String PARAM_START_INDEX = "startIndex";
    private static final String PARAM_HITS_PER_PAGE = "hitsPerPage";
    private static final String PARAM_CLASSGROUP = "classgroup";
    private static final String PARAM_RDFTYPE = "type";
    private static final String PARAM_QUERY_TEXT = "querytext";
    private static final String PARAM_QUERY_SORT_BY = "sortBy";
    private static final String PARAM_QUERY_SORT_ORDER = "sortOrder";
    
    private static final String FILTER_QUERY = 
    		  " PREFIX search: <https://osl.tib.eu/vitro-searchOntology#>\n"
    		  + "     PREFIX gesah:    <http://ontology.tib.eu/gesah/>\n"
    		  + "     PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
    		  + "     PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
    		  + "SELECT ?filter_id ?filter_label ?value_label ?value_id  ?field_name  ?filter_order ?value_order (STR(?isUriReq) as ?isUri )\n"
    		  + " 	WHERE {\n"
    		  + " 	    ?filter rdf:type search:Filter .\n"
    		  + "        ?filter rdfs:label ?filter_label .\n"
    		  + "        ?filter search:id ?filter_id .\n"
    		  + "        ?filter search:filterField ?field .\n"
    		  + "   		?field search:indexField ?field_name .\n"
    		  + "         OPTIONAL {?filter search:hasKnownValue ?value . \n"
    		  + "         	?value rdfs:label ?value_label .\n"
    		  + "         	?value search:id ?value_id .\n"
    		  + "  		 }\n"
    		  + "  		 OPTIONAL {?filter search:isUriValues ?isUriReq }\n"
    		  + "         OPTIONAL { ?filter search:order ?filter_order }\n"
    		  + "         OPTIONAL { ?value search:order ?value_order}\n"
    		  + " 	} ORDER BY ?filter_id ?filter_order ?value_order";

	private static final String LABEL_QUERY = 
			"     PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
			+ "    SELECT ?label\n"
			+ " 	WHERE {\n"
			+ "		?uri rdfs:label ?label .\n"
			+ " 	} LIMIT 1";
	

    protected static final Map<Format,Map<Result,String>> templateTable;
	private static final String FILTERS = "filters";


    protected enum Format {
        HTML, XML, CSV;
    }

    protected enum Result {
        PAGED, ERROR, BAD_QUERY
    }

    static{
        templateTable = setupTemplateTable();
    }

    /**
     * Overriding doGet from FreemarkerHttpController to do a page template (as
     * opposed to body template) style output for XML requests.
     *
     * This follows the pattern in AutocompleteController.java.
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        VitroRequest vreq = new VitroRequest(request);
        boolean wasXmlRequested = isRequestedFormatXml(vreq);
        boolean wasCSVRequested = isRequestedFormatCSV(vreq);
        if( !wasXmlRequested && !wasCSVRequested){
            super.doGet(vreq,response);
        }else if (wasXmlRequested){
            try {
                ResponseValues rvalues = processRequest(vreq);

                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/xml;charset=UTF-8");
                response.setHeader("Content-Disposition", "attachment; filename=search.xml");
                writeTemplate(rvalues.getTemplateName(), rvalues.getMap(), request, response);
            } catch (Exception e) {
                log.error(e, e);
            }
        }else if (wasCSVRequested){
        	try {
                ResponseValues rvalues = processRequest(vreq);

                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/csv;charset=UTF-8");
                response.setHeader("Content-Disposition", "attachment; filename=search.csv");
                writeTemplate(rvalues.getTemplateName(), rvalues.getMap(), request, response);
            } catch (Exception e) {
                log.error(e, e);
            }
        }
    }

    @Override
    protected ResponseValues processRequest(VitroRequest vreq) {

        //There may be other non-html formats in the future
        Format format = getFormat(vreq);
        boolean wasXmlRequested = Format.XML == format;
        boolean wasCSVRequested = Format.CSV == format;
        log.debug("Requested format was " + (wasXmlRequested ? "xml" : "html"));
        boolean wasHtmlRequested = ! (wasXmlRequested || wasCSVRequested);

        try {

            //make sure an IndividualDao is available
            if( vreq.getWebappDaoFactory() == null
                    || vreq.getWebappDaoFactory().getIndividualDao() == null ){
                log.error("Could not get webappDaoFactory or IndividualDao");
                throw new Exception("Could not access model.");
            }
            IndividualDao iDao = vreq.getWebappDaoFactory().getIndividualDao();
            VClassGroupDao grpDao = vreq.getWebappDaoFactory().getVClassGroupDao();
            VClassDao vclassDao = vreq.getWebappDaoFactory().getVClassDao();

            ApplicationBean appBean = vreq.getAppBean();

            log.debug("IndividualDao is " + iDao.toString() + " Public classes in the classgroup are " + grpDao.getPublicGroupsWithVClasses().toString());
            log.debug("VClassDao is "+ vclassDao.toString() );

            int startIndex = getStartIndex(vreq);
            int hitsPerPage = getHitsPerPage( vreq );

            String queryText = getQueryText(vreq);
            log.debug("Query text is \""+ queryText + "\"");
            
            Map<String, SearchFilter> filtersByField = getFilterConfiguration(vreq);
            
            //String badQueryMsg = badQueryText( queryText, vreq );
            //if( ! badQueryMsg.isEmpty() ){
            //    return doFailedSearch(badQueryMsg, queryText, format, vreq);
            //}

            SearchQuery query = getQuery(queryText, hitsPerPage, startIndex, vreq, filtersByField);
            SearchEngine search = ApplicationUtils.instance().getSearchEngine();
            SearchResponse response = null;

            try {
                response = search.query(query);
            } catch (Exception ex) {
                String msg = makeBadSearchMessage(queryText, ex.getMessage(), vreq);
                log.error("could not run search query",ex);
                return doFailedSearch(msg, queryText, format, vreq);
            }

            if (response == null) {
                log.error("Search response was null");
                return doFailedSearch(I18n.text(vreq, "error_in_search_request"), queryText, format, vreq);
            }
            addFacetCountersFromUserRequest(response, filtersByField, vreq);

            SearchResultDocumentList docs = response.getResults();
            if (docs == null) {
                log.error("Document list for a search was null");
                return doFailedSearch(I18n.text(vreq, "error_in_search_request"), queryText,format, vreq);
            }

            long hitCount = docs.getNumFound();
            log.debug("Number of hits = " + hitCount);
            if ( hitCount < 1 ) {
                return doNoHits(queryText,format, vreq);
            }

            List<Individual> individuals = new ArrayList<Individual>(docs.size());
            for (SearchResultDocument doc : docs) {
                try {
                    String uri = doc.getStringValue(VitroSearchTermNames.URI);
                    Individual ind = iDao.getIndividualByURI(uri);
                    if (ind != null) {
                        ind.setSearchSnippet(getSnippet(doc, response));
                        individuals.add(ind);
                    }
                } catch (Exception e) {
                    log.error("Problem getting usable individuals from search hits. ", e);
                }
            }

            ParamMap pagingLinkParams = new ParamMap();
            pagingLinkParams.put(PARAM_QUERY_TEXT, queryText);
            pagingLinkParams.put(PARAM_HITS_PER_PAGE, String.valueOf(hitsPerPage));
            String[] filters = vreq.getParameterValues(FILTERS);
            if (filters != null) {
            	for (String filter : filters) {
            		pagingLinkParams.put(FILTERS, filter);	
            	}
            }

            if( wasXmlRequested ){
                pagingLinkParams.put(PARAM_XML_REQUEST,"1");
            }

            /* Compile the data for the templates */

            Map<String, Object> body = new HashMap<String, Object>();

            String classGroupParam = vreq.getParameter(PARAM_CLASSGROUP);
            log.debug("ClassGroupParam is \""+ classGroupParam + "\"");
            boolean classGroupFilterRequested = false;
            if (!StringUtils.isBlank(classGroupParam)) {
                VClassGroup grp = grpDao.getGroupByURI(classGroupParam);
                classGroupFilterRequested = true;
                if (grp != null && grp.getPublicName() != null)
                    body.put("classGroupName", grp.getPublicName());
            }

            String typeParam = vreq.getParameter(PARAM_RDFTYPE);
            boolean typeFilterRequested = false;
            if (!StringUtils.isBlank(typeParam)) {

                VClass type = vclassDao.getVClassByURI(typeParam);
                typeFilterRequested = true;
                if (type != null && type.getName() != null)
                    body.put("typeName", type.getName());
            }

            /* Add ClassGroup and type refinement links to body */
            if( wasHtmlRequested ){
            	body.put("filters", filtersByField);
                if ( !classGroupFilterRequested && !typeFilterRequested ) {
                    // Search request includes no ClassGroup and no type, so add ClassGroup search refinement links.
                    body.put("classGroupLinks", getClassGroupsLinks(vreq, grpDao, docs, response, queryText));
                } else if ( classGroupFilterRequested && !typeFilterRequested ) {
                    // Search request is for a ClassGroup, so add rdf:type search refinement links
                    // but try to filter out classes that are subclasses
                    body.put("classLinks", getVClassLinks(vclassDao, docs, response, queryText));
                    body.put("classGroupLinks", getClassGroupsLinks(vreq, grpDao, docs, response, queryText));

                    pagingLinkParams.put(PARAM_CLASSGROUP, classGroupParam);

                } else {
                    //search request is for a class so there are no more refinements
                    pagingLinkParams.put(PARAM_RDFTYPE, typeParam);
                    pagingLinkParams.put(PARAM_CLASSGROUP, classGroupParam);
                    body.put("classLinks", getVClassLinks(vclassDao, docs, response, queryText));
                    body.put("classGroupLinks", getClassGroupsLinks(vreq, grpDao, docs, response, queryText));

                }
            }

            body.put("individuals", IndividualSearchResult
                    .getIndividualTemplateModels(individuals, vreq));

            body.put("querytext", queryText);
            body.put("title", new StringBuilder().append(appBean.getApplicationName()).append(" - ").
                    append(I18n.text(vreq, "search_results_for")).append(" '").append(queryText).append("'").toString());

            body.put("hitCount", hitCount);
            body.put("startIndex", startIndex);
            body.put(PARAM_HITS_PER_PAGE, hitsPerPage);

            body.put("pagingLinks",
                    getPagingLinks(startIndex, hitsPerPage, hitCount,
                                   vreq.getServletPath(),
                                   pagingLinkParams, vreq));

            if (startIndex != 0) {
                body.put("prevPage", getPreviousPageLink(startIndex,
                        hitsPerPage, vreq.getServletPath(), pagingLinkParams));
            }
            if (startIndex < (hitCount - hitsPerPage)) {
                body.put("nextPage", getNextPageLink(startIndex, hitsPerPage,
                        vreq.getServletPath(), pagingLinkParams));
            }

	        String template = templateTable.get(format).get(Result.PAGED);

            return new TemplateResponseValues(template, body);
        } catch (Throwable e) {
            return doSearchError(e,format);
        }
    }

	private void addFacetCountersFromUserRequest(SearchResponse response, Map<String, SearchFilter> filtersByField, VitroRequest vreq) {
		List<SearchFacetField> resultfacetFields = response.getFacetFields();
		Map<String, String> requestFiltersById = getRequestFilters(vreq);
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
         		if (requestFiltersById.containsKey(searchFilter.getId())) {
					String requestedValue = requestFiltersById.get(searchFilter.getId());
					if (name.equals(requestedValue)) {
						filterValue.setSelected(true);
					}
				}
         		if(searchFilter.isLocalizationRequired() && StringUtils.isBlank(filterValue.getName())) {
        			String label = getUriLabel(value.getName(), vreq);
        			if (!StringUtils.isBlank(label)) {
        				filterValue.setName(label);
        			}
        		}
         		//COUNT should be from the real results of the query
         		filterValue.setCount(value.getCount());
         	}
         }
	}

	private String getQueryText(VitroRequest vreq) {
		String query = vreq.getParameter(PARAM_QUERY_TEXT);
		if (StringUtils.isBlank(query)) {
			return "";
		}
		return query;
	}
    
    private Map<String, SearchFilter> getFilterConfiguration(VitroRequest vreq) {
		Map<String, String> requestFilters = getRequestFilters(vreq);
		Map<String,SearchFilter> filtersByField  = new HashMap<>();
    	Model model = ModelAccess.on(vreq).getOntModelSelector().getABoxModel();
    	model.enterCriticalSection(Lock.READ);
		try {
			Query facetQuery = QueryFactory.create(FILTER_QUERY);
			QueryExecution qexec = QueryExecutionFactory.create(facetQuery, model);
			ResultSet results = qexec.execSelect();
			while (results.hasNext()) {
				QuerySolution solution = results.nextSolution();
				if (solution.get("filter_id") == null ||
					solution.get("field_name") == null ) {
					continue;
				}
				String resultFilterId = solution.get("filter_id").toString();
				String resultFieldName = solution.get("field_name").toString();
	
				SearchFilter filter = null;
				if (filtersByField.containsKey(resultFieldName)) {
					filter = filtersByField.get(resultFieldName);
				} else {
					filter = new SearchFilter(resultFilterId);
					filtersByField.put(resultFieldName, filter);
					filter.setName(solution.get("filter_label"));
					filter.setOrder(solution.get("filter_order"));
					if (solution.get("isUri") != null && 
						"true".equals(solution.get("isUri").toString())) {
						filter.setLocalizationRequired(true);
					}
					filter.setField(resultFieldName);
				}
				if (solution.get("value_id") == null ) {
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
        querySolrFilterInfo(filtersByField, vreq,requestFilters);
		return filtersByField;
    }

	private Map<String, SearchFilter> getFiltersById(Map<String, SearchFilter> filtersByField) {
		Map<String,SearchFilter> filtersById = filtersByField
        		.values()
        		.stream()
        		.collect(Collectors.toMap(SearchFilter::getId, Function.identity()));
		return filtersById;
	}


    private void querySolrFilterInfo(Map<String, SearchFilter> filtersByField, VitroRequest vreq, Map<String, String> requestFiltersById) {
    	SearchQuery query = ApplicationUtils.instance().getSearchEngine().createQuery("*:*");
    	query.setRows(0);
    	query.setFacetLimit(200);
    	addFacetFieldsToQuery(filtersByField, query);
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
					
					if (requestFiltersById.containsKey(searchFilter.getId())) {
						String requestedValue = requestFiltersById.get(searchFilter.getId());
						if (name.equals(requestedValue)) {
							filterValue.setSelected(true);
						}
					}
            		//COUNT should be from the real results of the query
            		//filterValue.setCount(value.getCount());
            		//filter.setSelected(true);
            		if(searchFilter.isLocalizationRequired()) {
            			String label = getUriLabel(value.getName(), vreq);
            			if (!StringUtils.isBlank(label)) {
            				filterValue.setName(label);
            			}
            		}
            	}
            	searchFilter.sortValues();
            }
        } catch (Exception e) {
        	log.error(e,e);
        }
	}

	private String getUriLabel(String uri, VitroRequest vreq) {
		String result = "";
		Model model = ModelAccess.on(vreq).getOntModelSelector().getABoxModel();
		model.enterCriticalSection(Lock.READ);
		try {
			QuerySolutionMap initialBindings = new QuerySolutionMap();
			initialBindings.add("uri", ResourceFactory.createResource(uri));
			Query facetQuery = QueryFactory.create(LABEL_QUERY);
			QueryExecution qexec = QueryExecutionFactory.create(facetQuery, model, initialBindings);
			ResultSet results = qexec.execSelect();
			if(results.hasNext()) {
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

	private int getHitsPerPage(VitroRequest vreq) {
        int hitsPerPage = DEFAULT_HITS_PER_PAGE;
        try{
            hitsPerPage = Integer.parseInt(vreq.getParameter(PARAM_HITS_PER_PAGE));
        } catch (Throwable e) {
            hitsPerPage = DEFAULT_HITS_PER_PAGE;
        }
        log.debug("hitsPerPage is " + hitsPerPage);
        return hitsPerPage;
    }

    private int getStartIndex(VitroRequest vreq) {
        int startIndex = 0;
        try{
            startIndex = Integer.parseInt(vreq.getParameter(PARAM_START_INDEX));
        }catch (Throwable e) {
            startIndex = 0;
        }
        log.debug("startIndex is " + startIndex);
        return startIndex;
    }

    /**
     * Get the class groups represented for the individuals in the documents.
     */
    private List<VClassGroupSearchLink> getClassGroupsLinks(VitroRequest vreq, VClassGroupDao grpDao, SearchResultDocumentList docs, SearchResponse rsp, String qtxt) {
        Map<String,Long> cgURItoCount = new HashMap<String,Long>();

        List<VClassGroup> classgroups = new ArrayList<VClassGroup>( );
        List<SearchFacetField> ffs = rsp.getFacetFields();
        for(SearchFacetField ff : ffs){
            if(VitroSearchTermNames.CLASSGROUP_URI.equals(ff.getName())){
                List<Count> counts = ff.getValues();
                for( Count ct: counts){
                    VClassGroup vcg = grpDao.getGroupByURI( ct.getName() );
                    if( vcg == null ){
                        log.debug("could not get classgroup for URI " + ct.getName());
                    }else{
                        classgroups.add(vcg);
                        cgURItoCount.put(vcg.getURI(),  ct.getCount());
                    }
                }
            }
        }

        grpDao.sortGroupList(classgroups);

        VClassGroupsForRequest vcgfr = VClassGroupCache.getVClassGroups(vreq);
        List<VClassGroupSearchLink> classGroupLinks = new ArrayList<VClassGroupSearchLink>(classgroups.size());
        for (VClassGroup vcg : classgroups) {
        	String groupURI = vcg.getURI();
			VClassGroup localizedVcg = vcgfr.getGroup(groupURI);
            long count = cgURItoCount.get( groupURI );
            if (localizedVcg.getPublicName() != null && count > 0 )  {
                classGroupLinks.add(new VClassGroupSearchLink(qtxt, localizedVcg, count));
            }
        }
        return classGroupLinks;
    }

    private List<VClassSearchLink> getVClassLinks(VClassDao vclassDao, SearchResultDocumentList docs, SearchResponse rsp, String qtxt){
        HashSet<String> typesInHits = getVClassUrisForHits(docs);
        List<VClass> classes = new ArrayList<VClass>(typesInHits.size());
        Map<String,Long> typeURItoCount = new HashMap<String,Long>();

        List<SearchFacetField> ffs = rsp.getFacetFields();
        for(SearchFacetField ff : ffs){
            if(VitroSearchTermNames.RDFTYPE.equals(ff.getName())){
                List<Count> counts = ff.getValues();
                for( Count ct: counts){
                    String typeUri = ct.getName();
                    long count = ct.getCount();
                    try{
                        if( VitroVocabulary.OWL_THING.equals(typeUri) ||
                            count == 0 )
                            continue;
                        VClass type = vclassDao.getVClassByURI(typeUri);
                        if( type != null &&
                            ! type.isAnonymous() &&
                              type.getName() != null && !"".equals(type.getName()) &&
                              type.getGroupURI() != null ){ //don't display classes that aren't in classgroups
                            typeURItoCount.put(typeUri,count);
                            classes.add(type);
                        }
                    }catch(Exception ex){
                        if( log.isDebugEnabled() )
                            log.debug("could not add type " + typeUri, ex);
                    }
                }
            }
        }


        classes.sort(new Comparator<VClass>() {
            public int compare(VClass o1, VClass o2) {
                return o1.compareTo(o2);
            }
        });

        List<VClassSearchLink> vClassLinks = new ArrayList<VClassSearchLink>(classes.size());
        for (VClass vc : classes) {
            long count = typeURItoCount.get(vc.getURI());
            vClassLinks.add(new VClassSearchLink(qtxt, vc, count ));
        }

        return vClassLinks;
    }

    private HashSet<String> getVClassUrisForHits(SearchResultDocumentList docs){
        HashSet<String> typesInHits = new HashSet<String>();
        for (SearchResultDocument doc : docs) {
            try {
                Collection<Object> types = doc.getFieldValues(VitroSearchTermNames.RDFTYPE);
                if (types != null) {
                    for (Object o : types) {
                        String typeUri = o.toString();
                        typesInHits.add(typeUri);
                    }
                }
            } catch (Exception e) {
                log.error("problems getting rdf:type for search hits",e);
            }
        }
        return typesInHits;
    }

    private String getSnippet(SearchResultDocument doc, SearchResponse response) {
        String docId = doc.getStringValue(VitroSearchTermNames.DOCID);
        StringBuilder text = new StringBuilder();
        Map<String, Map<String, List<String>>> highlights = response.getHighlighting();
        if (highlights != null && highlights.get(docId) != null) {
            List<String> snippets = highlights.get(docId).get(VitroSearchTermNames.ALLTEXT);
            if (snippets != null && snippets.size() > 0) {
                text.append("... ").append(snippets.get(0)).append(" ...");
            }
        }
        return text.toString();
    }

    private SearchQuery getQuery(String queryText, int hitsPerPage, int startIndex, VitroRequest vreq, Map<String, SearchFilter> filtersByField) {
        // Lowercase the search term to support wildcard searches: The search engine applies no text
        // processing to a wildcard search term.
    	if (StringUtils.isBlank(queryText)) {
    		queryText  = "*:*";
    	}
        SearchQuery query = ApplicationUtils.instance().getSearchEngine().createQuery(queryText);

        query.setStart( startIndex )
             .setRows(hitsPerPage);
        
        addSortRules(vreq, query);

        addDefaultVitroFacets(vreq, query);
        
        addFacetFieldsToQuery(filtersByField, query);

        Map<String, SearchFilter> filtersById = getFiltersById(filtersByField);
        
        addFiltersToQuery(vreq, query, filtersById);
        
        // ClassGroup filtering param
        String classgroupParam = vreq.getParameter(PARAM_CLASSGROUP);

        // rdf:type filtering param
        String typeParam = vreq.getParameter(PARAM_RDFTYPE);

        if ( ! StringUtils.isBlank(classgroupParam) ) {
            // ClassGroup filtering
            log.debug("Firing classgroup query ");
            log.debug("request.getParameter(classgroup) is "+ classgroupParam);
            query.addFilterQuery(VitroSearchTermNames.CLASSGROUP_URI + ":\"" + classgroupParam + "\"");

            //with ClassGroup filtering we want type facets
            query.addFacetFields(VitroSearchTermNames.RDFTYPE).setFacetLimit(-1);

        }else if ( ! StringUtils.isBlank(typeParam) ) {
            // rdf:type filtering
            log.debug("Firing type query ");
            log.debug("request.getParameter(type) is "+ typeParam);
            query.addFilterQuery(VitroSearchTermNames.RDFTYPE + ":\"" + typeParam + "\"");
            //with type filtering we don't have facets.
        }else{
            //When no filtering is set, we want ClassGroup facets
        	query.addFacetFields(VitroSearchTermNames.CLASSGROUP_URI).setFacetLimit(-1);
        }

        log.debug("Query = " + query.toString());
        return query;
    }

	private void addFacetFieldsToQuery(Map<String, SearchFilter> filters, SearchQuery query) {
		for (String fieldId : filters.keySet()) {
        	query.addFacetFields(fieldId);
    	}
	}
    
	private void addDefaultVitroFacets(VitroRequest vreq, SearchQuery query) {
		String[] facets = vreq.getParameterValues(FACETS);
        if (facets != null && facets.length > 0) {
        	query.addFacetFields(facets);	
        }
	}
	
	private void addFiltersToQuery(VitroRequest vreq, SearchQuery query, Map<String, SearchFilter> filterById) {
		Enumeration<String> paramNames = vreq.getParameterNames();
		while ( paramNames.hasMoreElements()) {
			String paramFilterName = paramNames.nextElement();
			if (!StringUtils.isBlank(paramFilterName) &&
				paramFilterName.startsWith(FILTERS)) {
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
	}
	private Map<String,String> getRequestFilters(VitroRequest vreq) {
		Map<String,String> requestFilters = new HashMap<>();
		Enumeration<String> paramNames = vreq.getParameterNames();
		while ( paramNames.hasMoreElements()) {
			String paramFilterName = paramNames.nextElement();
			if (!StringUtils.isBlank(paramFilterName) &&
				paramFilterName.startsWith(FILTERS)) {
				String[] filters = vreq.getParameterValues(paramFilterName);
		        if (filters != null && filters.length > 0) {
		        	for (String filter : filters) {
		        		String[] pair = filter.split(":", 2);
		        		if (pair.length == 2) {
		        			String name = pair[0].replace("\"", "");
		        			String value = pair[1].replace("\"", "");
		        			requestFilters.put(name, value);
		        		}
		        	}
		        }	
			}
		}
		
        return requestFilters;
	}

	private void addSortRules(VitroRequest vreq, SearchQuery query) {
		String sortField = getSortField(vreq);
        if (!StringUtils.isBlank(sortField)) {
        	query.addSortField(sortField, getSortOrder(vreq));	
        }
	}

	private String getSortField(VitroRequest vreq) {
		return vreq.getParameter(PARAM_QUERY_SORT_BY);
	}

	private Order getSortOrder(VitroRequest vreq) {
		String sortOrder = vreq.getParameter(PARAM_QUERY_SORT_ORDER);
		if ("ASC".equals(sortOrder)) {
			return Order.ASC;
		}
		return Order.DESC;
	}

	public static class VClassGroupSearchLink extends LinkTemplateModel {
		private static final String EXTENDEDSEARCH = "/extendedsearch";
		long count = 0;
        VClassGroupSearchLink(String querytext, VClassGroup classgroup, long count) {
            super(classgroup.getPublicName(), EXTENDEDSEARCH, PARAM_QUERY_TEXT, querytext, PARAM_CLASSGROUP, classgroup.getURI());
            this.count = count;
        }

        public String getCount() { return Long.toString(count); }
    }

    public static class VClassSearchLink extends LinkTemplateModel {
        long count = 0;
        VClassSearchLink(String querytext, VClass type, long count) {
            super(type.getName(), "/extendedsearch", PARAM_QUERY_TEXT, querytext, PARAM_RDFTYPE, type.getURI());
            this.count = count;
        }

        public String getCount() { return Long.toString(count); }
    }

    protected static List<PagingLink> getPagingLinks(int startIndex, int hitsPerPage, long hitCount, String baseUrl, ParamMap params, VitroRequest vreq) {

        List<PagingLink> pagingLinks = new ArrayList<PagingLink>();

        // No paging links if only one page of results
        if (hitCount <= hitsPerPage) {
            return pagingLinks;
        }

        int maxHitCount = DEFAULT_MAX_HIT_COUNT ;
        if( startIndex >= DEFAULT_MAX_HIT_COUNT  - hitsPerPage )
            maxHitCount = startIndex + DEFAULT_MAX_HIT_COUNT ;

        for (int i = 0; i < hitCount; i += hitsPerPage) {
            params.put(PARAM_START_INDEX, String.valueOf(i));
            if ( i < maxHitCount - hitsPerPage) {
                int pageNumber = i/hitsPerPage + 1;
                boolean iIsCurrentPage = (i >= startIndex && i < (startIndex + hitsPerPage));
                if ( iIsCurrentPage ) {
                    pagingLinks.add(new PagingLink(pageNumber));
                } else {
                    pagingLinks.add(new PagingLink(pageNumber, baseUrl, params));
                }
            } else {
            	pagingLinks.add(new PagingLink(I18n.text(vreq, "paging_link_more"), baseUrl, params));
                break;
            }
        }

        return pagingLinks;
    }

    private String getPreviousPageLink(int startIndex, int hitsPerPage, String baseUrl, ParamMap params) {
        params.put(PARAM_START_INDEX, String.valueOf(startIndex-hitsPerPage));
        return UrlBuilder.getUrl(baseUrl, params);
    }

    private String getNextPageLink(int startIndex, int hitsPerPage, String baseUrl, ParamMap params) {
        params.put(PARAM_START_INDEX, String.valueOf(startIndex+hitsPerPage));
        return UrlBuilder.getUrl(baseUrl, params);
    }

    protected static class PagingLink extends LinkTemplateModel {

        PagingLink(int pageNumber, String baseUrl, ParamMap params) {
            super(String.valueOf(pageNumber), baseUrl, params);
        }

        // Constructor for current page item: not a link, so no url value.
        PagingLink(int pageNumber) {
            setText(String.valueOf(pageNumber));
        }

        // Constructor for "more..." item
        PagingLink(String text, String baseUrl, ParamMap params) {
            super(text, baseUrl, params);
        }
    }

    private ExceptionResponseValues doSearchError(Throwable e, Format f) {
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("message", "Search failed: " + e.getMessage());
        return new ExceptionResponseValues(getTemplate(f,Result.ERROR), body, e);
    }

    private TemplateResponseValues doFailedSearch(String message, String querytext, Format f, VitroRequest vreq) {
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("title", I18n.text(vreq, "search_for", querytext));
        if ( StringUtils.isEmpty(message) ) {
        	message = I18n.text(vreq, "search_failed");
        }
        body.put("message", message);
        return new TemplateResponseValues(getTemplate(f,Result.ERROR), body);
    }

    private TemplateResponseValues doNoHits(String querytext, Format f, VitroRequest vreq) {
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("title", I18n.text(vreq, "search_for", querytext));
        body.put("message", I18n.text(vreq, "no_matching_results"));
        return new TemplateResponseValues(getTemplate(f,Result.ERROR), body);
    }

    /**
     * Makes a message to display to user for a bad search term.
     */
    private String makeBadSearchMessage(String querytext, String exceptionMsg, VitroRequest vreq){
        String rv = "";
        try{
            //try to get the column in the search term that is causing the problems
            int coli = exceptionMsg.indexOf("column");
            if( coli == -1) return "";
            int numi = exceptionMsg.indexOf(".", coli+7);
            if( numi == -1 ) return "";
            String part = exceptionMsg.substring(coli+7,numi );
            int i = Integer.parseInt(part) - 1;

            // figure out where to cut preview and post-view
            int errorWindow = 5;
            int pre = i - errorWindow;
            if (pre < 0)
                pre = 0;
            int post = i + errorWindow;
            if (post > querytext.length())
                post = querytext.length();
            // log.warn("pre: " + pre + " post: " + post + " term len:
            // " + term.length());

            // get part of the search term before the error and after
            String before = querytext.substring(pre, i);
            String after = "";
            if (post > i)
                after = querytext.substring(i + 1, post);

            rv = I18n.text(vreq, "search_term_error_near") +
            		" <span class='searchQuote'>"
                + before + "<span class='searchError'>" + querytext.charAt(i)
                + "</span>" + after + "</span>";
        } catch (Throwable ex) {
            return "";
        }
        return rv;
    }

    public static final int MAX_QUERY_LENGTH = 500;

    protected boolean isRequestedFormatXml(VitroRequest req){
        if( req != null ){
            String param = req.getParameter(PARAM_XML_REQUEST);
            return param != null && "1".equals(param);
        }else{
            return false;
        }
    }

    protected boolean isRequestedFormatCSV(VitroRequest req){
        if( req != null ){
            String param = req.getParameter(PARAM_CSV_REQUEST);
            return param != null && "1".equals(param);
        }else{
            return false;
        }
    }

    protected Format getFormat(VitroRequest req){
        if( req != null && req.getParameter("xml") != null && "1".equals(req.getParameter("xml")))
            return Format.XML;
        else if ( req != null && req.getParameter("csv") != null && "1".equals(req.getParameter("csv")))
        	return Format.CSV;
        else
            return Format.HTML;
    }

    protected static String getTemplate(Format format, Result result){
        if( format != null && result != null)
            return templateTable.get(format).get(result);
        else{
            log.error("getTemplate() must not have a null format or result.");
            return templateTable.get(Format.HTML).get(Result.ERROR);
        }
    }

    protected static Map<Format,Map<Result,String>> setupTemplateTable(){
        Map<Format,Map<Result,String>> table = new HashMap<>();

        HashMap<Result,String> resultsToTemplates = new HashMap<Result,String>();

        // set up HTML format
        resultsToTemplates.put(Result.PAGED, "extended-search-pagedResults.ftl");
        resultsToTemplates.put(Result.ERROR, "extended-search-error.ftl");
        // resultsToTemplates.put(Result.BAD_QUERY, "search-badQuery.ftl");
        table.put(Format.HTML, Collections.unmodifiableMap(resultsToTemplates));

        // set up XML format
        resultsToTemplates = new HashMap<Result,String>();
        resultsToTemplates.put(Result.PAGED, "extended-search-xmlResults.ftl");
        resultsToTemplates.put(Result.ERROR, "search-xmlError.ftl");

        // resultsToTemplates.put(Result.BAD_QUERY, "search-xmlBadQuery.ftl");
        table.put(Format.XML, Collections.unmodifiableMap(resultsToTemplates));


        // set up CSV format
        resultsToTemplates = new HashMap<Result,String>();
        resultsToTemplates.put(Result.PAGED, "extended-search-csvResults.ftl");
        resultsToTemplates.put(Result.ERROR, "search-csvError.ftl");

        // resultsToTemplates.put(Result.BAD_QUERY, "search-xmlBadQuery.ftl");
        table.put(Format.CSV, Collections.unmodifiableMap(resultsToTemplates));


        return Collections.unmodifiableMap(table);
    }
}
