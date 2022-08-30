package edu.cornell.mannlib.vitro.webapp.search.controller;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.jena.rdf.model.RDFNode;



public class SearchFilter {

    private static final String FILTER = "Filter";
	private static final String RANGE_FILTER = "RangeFilter";

	private static final Log log = LogFactory.getLog(SearchFilter.class);

	private String id;
	private String name = "";
	private String from = "";
	private String to = "";
	private String fromYear = "";
	private String toYear = "";
	
	private String min = "0";
	private String max = "2000";
	private int order;
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

	private String type = FILTER;
	private String rangeText = "";

	public String getRangeText() {
		return rangeText;
	}

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
	
	public boolean isRange() {
		return RANGE_FILTER.equals(type);
	}

	public void setInput(boolean input) {
		this.input = input;
	}

	public String getInputText() {
		return inputText;
	}

	public void setInputText(String inputText) {
		if (StringUtils.isBlank(inputText)) {
			return;
		}
		this.inputText = inputText;
		selected = true;
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

	public void setType(RDFNode rdfNode) {
		String typeOntClass = rdfNode.toString();
		if (typeOntClass.contains(RANGE_FILTER)) {
			type = RANGE_FILTER;
		}
	}

	public String getType() {
		return type;
	}
	
	public String getFrom() {
		return from;
	}

	public void setFrom(String fromValue) {
		this.from = fromValue;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String toValue) {
		this.to = toValue;
	}

	public void setRangeValues(String filterRangeText) {
		if (StringUtils.isBlank(filterRangeText)) { return; }
		String[] range = filterRangeText.trim().split(" ");
		if (range.length != 2) {
			return;
		}
		setFrom(range[0]);
		setFromYear(range[0]);
		setTo(to = range[1]);
		setToYear(range[1]);
		rangeText  = "[" + from.trim() + " TO " + to.trim() + "]";
		selected = true;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}
	
	private String prettyTime(String timeString) {
		Instant time = Instant.parse ( timeString );
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.systemDefault()) ;
		String formatted = formatter.format(time);
		return formatted;
	}
	
	private String getYear(String timeString) {
		Instant time = Instant.parse ( timeString );
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy").withZone(ZoneId.systemDefault()) ;
		String formatted = formatter.format(time);
		return formatted;
	}
	
	public String getFromYear() {
		return fromYear;
	}

	public void setFromYear(String fromYear) {
		this.fromYear = getYear(fromYear);
	}

	public String getToYear() {
		return toYear;
	}

	public void setToYear(String toYear) {
		this.toYear = getYear(toYear);
	}
	
}
