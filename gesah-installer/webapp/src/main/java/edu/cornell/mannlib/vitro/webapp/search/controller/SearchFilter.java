package edu.cornell.mannlib.vitro.webapp.search.controller;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.jena.rdf.model.RDFNode;



public class SearchFilter {

    private static final Log log = LogFactory.getLog(SearchFilter.class);

	private String id;
	private String name = "";
	private int order;
	private long step = 100;

	private String field= "";
	private String endField= "";
	private String inputText= "";
	private boolean localizationRequired = false;
	private boolean multivalued = false;
	private boolean selected = false;
	private boolean input = false;
	private Map<String,FilterValue> values = new LinkedHashMap<>();

	private boolean inputRegex = false;

	private boolean facetsRequired;

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
	
	public String getEndField() {
		return endField;
	}
	
	public void setEndField(String endField) {
		this.endField = endField;
	}
	
	public void addValue(FilterValue value) {
		values.put(value.getId(),value);
	}
	
	public FilterValue getValue(String name) {
		return values.get(name);
	}
	
	public Map<String, FilterValue> getValues() {
		return values;
	}

	public void sortValues() {
		List<Entry<String, FilterValue>> list = new LinkedList<>(values.entrySet());
		list.sort(new FilterValueComparator());
		values = list.stream()
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue, (a, b) -> b, LinkedHashMap::new));
	}
	
	public class FilterValueComparator implements Comparator<Map.Entry<String, FilterValue>>{
	    public int compare(Entry<String, FilterValue> obj1, Entry<String, FilterValue> obj2) {
	        FilterValue filter1 = obj1.getValue();
	        FilterValue filter2 = obj2.getValue();
	        int result = filter1.getOrder().compareTo(filter2.getOrder());
	        if (result == 0) {
	            // order are equal, sort by name
	            return filter1.getName().toLowerCase().compareTo(filter2.getName().toLowerCase());
	        }
	        else {
	            return result;
	        }
	    }
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

	public String getId() {
		return id;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean isSelected) {
		this.selected = isSelected;
	}


	public boolean isMultivalued() {
		return multivalued;
	}


	public void setMultivalued(boolean multivalued) {
		this.multivalued = multivalued;
	}

	public boolean isInput() {
		return input;
	}

	public void setInput(boolean input) {
		this.input = input;
	}

	public String getInputText() {
		return inputText;
	}

	public void setInputText(String inputText) {
		this.inputText = inputText;
	}

	public void setInputRegex(boolean regex) {
		this.inputRegex  = regex;
	}
	
	public boolean isInputRegex() {
		return inputRegex;
	}

	public void setFacetsRequired(boolean facetsRequired) {
		this.facetsRequired = facetsRequired;
	}

	public boolean isFacetsRequired() {
		return facetsRequired;
	}

	public long getStep() {
		return step;
	}

	public void setStep(long step) {
		this.step = step;
	}
	
}
