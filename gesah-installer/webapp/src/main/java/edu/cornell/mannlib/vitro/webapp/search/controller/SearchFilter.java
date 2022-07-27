package edu.cornell.mannlib.vitro.webapp.search.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.jena.rdf.model.RDFNode;


public class SearchFilter {

    private static final Log log = LogFactory.getLog(SearchFilter.class);

	private String id;
	private String name = "";
	private int order;
	private String field= "";
	private boolean localizationRequired = false;
	private Map<String,FilterValue> values = new HashMap<>();

	public SearchFilter(String id){
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(RDFNode rdfNode) {
		if (rdfNode != null) {
			name = rdfNode.asLiteral().getLexicalForm();
		}
	}

	public void setOrder(RDFNode rdfNode) {
		if (rdfNode != null) {
			order = rdfNode.asLiteral().getInt();
		}
	}

	public int getOrder() {
		return order;
	}

	public String getField() {
		return field;
	}
	
	public void addValue(FilterValue value) {
		values.put(value.getId(),value);
	}
	
	public FilterValue getValue(String name) {
		return values.get(name);
	}


	public void setField(String fieldName) {
		field = fieldName;
	}

	public boolean contains(String valueId) {
		if (values.containsKey(valueId)) {
			return true;
		}
		return false;
	}

	public boolean isLocalizationRequired() {
		return localizationRequired;
	}

	public void setLocalizationRequired(boolean localizationRequired) {
		this.localizationRequired = localizationRequired;
	}
}
