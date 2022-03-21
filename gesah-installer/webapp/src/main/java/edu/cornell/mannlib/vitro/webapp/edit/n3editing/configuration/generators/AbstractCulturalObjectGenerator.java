package edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators;


public abstract class AbstractCulturalObjectGenerator extends GesahEditConfigurationGenerator {
	public static final String AGENT = "Agent";
	public final static String AGENT_CLASS = FOAF + AGENT;
	public static final String AGENT_LABEL = "agentLabel";

	public static final String AGENT_LABEL_DISPLAY = "agentLabelDisplay";
	public static final String AGENT_TYPE = "agentType";
	public static final String ATTR_TYPE_LABEL = "attrTypeLabel";
	public final static String ATTRIBUTION_TYPE_CLASS = GESAH + "Attribution_Type";

	public static final String COMMENT = "comment";
	public final static String COMMENT_PRED = GESAH + COMMENT;

	public static final String CULT_OBJECT = "cultObject";
	
	public static final String OB_CULTURAL_OBJECT = "cultObjectVarName";

	
	public static final String DATATYPE = "datatype:";
			
	public static final String END_FIELD = "endField";
	public static final String END_FIELD_PRECISION = "endField-precision";
	public static final String END_FIELD_VALUE = "endField-value";
	
	public static final String END_NODE = "endNode";
	public static final String EXISTING_AGENT = "existingAgent";
	public static final String EXISTING_AGENT_LABEL = "existingAgentLabel";
	public static final String EXISTING_ATTR_TYPE = "existingAttrType";
	public static final String EXISTING_ATTR_TYPE_LABEL = "existingAttrTypeLabel";
	public static final String EXISTING_COMMENT = "existingComment";

	
	public static final String EXISTING_DATE_START = "existingDateStart";
	public static final String EXISTING_END_DATE = "existingEndDate";
	
	public static final String EXISTING_END_NODE = "existingEndNode";

	
	public static final String EXISTING_END_PRECISION = "existingEndPrecision";
	public static final String EXISTING_INTERVAL_NODE = "existingIntervalNode";
	public static final String EXISTING_MATERIAL = "existingMaterial";
	public static final String EXISTING_PLACE = "existingPlace";



	public static final String EXISTING_PLACE_LABEL = "existingPlaceLabel";
	public static final String EXISTING_ROLE = "existingRole";
	public static final String EXISTING_ROLE_TYPE = "existingRoleType";
	public static final String EXISTING_ROLE_TYPE_LABEL = "existingRoleTypeLabel";
	public static final String EXISTING_START_NODE = "existingStartNode";
	public static final String EXISTING_START_PRECISION = "existingStartPrecision";
	public static final String EXISTING_TECHNIQUE = "existingTechnique";

	public static final String EXISTING_TECHNIQUE_LABEL = "existingTechniqueLabel";
	public static final String EXISTINGLIT_DATE_APPEL = "existinglitDateAppel";
	
	public static final String GESAH_ATTRIBUTION_TYPE = GESAH + "Attribution_Type";
	public static final String GESAH_COMMENT = GESAH + "comment";
	
	public static final String GESAH_EDITION = GESAH + "Edition";
	public static final String GESAH_HAS_EDITION_OBJECT = GESAH + "has_edition_object";
	public static final String GESAH_HAS_MATERIAL = GESAH + "has_material";
	public static final String GESAH_HAS_PARTICIPANT = GESAH + "has_participant";
	public static final String GESAH_HAS_PLACE = GESAH + "has_place";
	public static final String GESAH_HAS_ROLE = GESAH + "has_role";
	public static final String GESAH_HAS_ROLE_TYPE = GESAH + "has_role_type";
	public static final String GESAH_HAS_TYPE_OF_ATTRIBUTION = GESAH + "has_type_of_attribution";
	public static final String GESAH_INCORPORATED_IN = GESAH + "incorporated_in";
	public static final String GESAH_IS_ATTRIBUTION_TYPE_OF = GESAH + "is_attribution_type_of";
	public static final String GESAH_IS_PLACE_OF = GESAH + "is_place_of";
	public static final String GESAH_IS_ROLE_OF = GESAH + "is_role_of";
	public static final String GESAH_LITERAL_DATE_APPELATION = GESAH + "literal_date_appellation";
	public static final String GESAH_MATERIAL = GESAH + "Material";
	public static final String GESAH_OBJECT_OF_PUBLICATION = GESAH + "object_of_publication";
	public static final String GESAH_PARTICIPATES_IN = GESAH + "participates_in";
	public static final String GESAH_REALIZED_IN = GESAH + "realized_in";
	public static final String GESAH_REALIZES = GESAH + "realizes";
	public static final String GESAH_ROLE_TYPE = GESAH + "Role_Type";
	public static final String GESAH_TECHNIQUE = GESAH + "Technique";
	public static final String GESAH_USED_IN = GESAH + "used_in";
	public static final String GESAH_USES_TECHNIQUE = GESAH + "uses_technique";
	public static final String INTERVAL_NODE = "intervalNode";
	public static final String LIT_DATE_APPEL = "litDateAppel";
	public final static String LITERAL_DATE_APPEAL_PRED = GESAH + "literal_date_appellation";
	public static final String MATERIAL_LABEL = "materialLabel";
	public final static String MATERIAL_TYPE_CLASS = GESAH + "Material";
	public static final String NEW_AGENT = "newAgent";
	public static final String NEW_ATTR_TYPE = "newAttrType";
	public static final String NEW_MATERIAL = "newMaterial";
	public static final String NEW_PLACE = "newPlace";
	public static final String NEW_ROLE = "newRole";
	public static final String NEW_ROLE_TYPE = "newRoleType";
	public static final String NEW_ROLE_TYPE_LABEL = "newRoleTypeLabel";
	public static final String NEW_TECHNIQUE = "newTechnique";
	
	public static final String NONEMPTY = "nonempty";
	public static final String PLACE_LABEL = "placeLabel";
	public static final String PLACE_LABEL_DISPLAY = "placeLabelDisplay";
	public final static String PLACE_TYPE_CLASS = VIVO_CORE + "GeographicLocation";
	public static final String PREDICATE = "predicate";
	public final static String ROLE_CLASS = OBO + "BFO_0000023";
	public final static String ROLE_TYPE_CLASS = GESAH + "Role_Type";
	public static final String ROLE_TYPE_LABEL = "roleTypeLabel";
	public static final String START_FIELD = "startField";
	public static final String START_FIELD_PRECISION = "startField-precision";
	public static final String START_FIELD_VALUE = "startField-value";
	public static final String START_NODE = "startNode";
	public static final String TECHNIQUE_LABEL = "techniqueLabel";
	public final static String TECHNIQUE_TYPE_CLASS = GESAH + "Technique";
	public static final String VAR = "?";
	
  final static String commentQuery  =
      "SELECT" + " " + VAR + EXISTING_COMMENT + " WHERE {" + "\n" +
      VAR + OB_CULTURAL_OBJECT + " " + "<" + COMMENT_PRED + ">" + " " + VAR + EXISTING_COMMENT + " . }";
  
  final static String litDateAppelAssertion  =
      VAR + OB_CULTURAL_OBJECT + " " + "<" + GESAH_LITERAL_DATE_APPELATION + "> " + VAR + LIT_DATE_APPEL + " .";	
  
  final static String n3ForExistingAttrType  =
      "@prefix gesah:" + " " + "<" + GESAH + ">" + " ." + "\n" +
      VAR + OB_CULTURAL_OBJECT + " " + "<" + GESAH_HAS_TYPE_OF_ATTRIBUTION + ">" + " " + VAR + EXISTING_ATTR_TYPE + " . " + "\n" +
      VAR + EXISTING_ATTR_TYPE + " " + "<" + GESAH_IS_ATTRIBUTION_TYPE_OF + ">" + " " + VAR + OB_CULTURAL_OBJECT + " . " + "\n" +
      VAR + EXISTING_ATTR_TYPE + " a" + " " + "<" + GESAH_ATTRIBUTION_TYPE + "> .";	
	
  final static String n3ForNewAttrType  =
      "@prefix gesah:" + " " + "<" + GESAH + ">" + " ." + "\n" +
      VAR + OB_CULTURAL_OBJECT + " " + "<" + GESAH_HAS_TYPE_OF_ATTRIBUTION + ">" + " " + VAR + NEW_ATTR_TYPE + " . " + "\n" +
      VAR + NEW_ATTR_TYPE + " " + "<" + GESAH_IS_ATTRIBUTION_TYPE_OF + ">" + " " + VAR + OB_CULTURAL_OBJECT + " . " + "\n" +
      VAR + NEW_ATTR_TYPE + " " + "<" + LABEL + ">" + " " + VAR + ATTR_TYPE_LABEL + " . " + "\n" +
      VAR + NEW_ATTR_TYPE + " a " + "<" + GESAH_ATTRIBUTION_TYPE + "> .";
	
  final static String n3ForExistingRoleType  =
			VAR + OB_CULTURAL_OBJECT + " " + "<" + GESAH_REALIZES + ">" + " " + VAR + NEW_ROLE + " . " + "\n" +
			VAR + NEW_ROLE + " " + "<" + GESAH_REALIZED_IN + ">" + " " + VAR + OB_CULTURAL_OBJECT + " . " + "\n" +
			VAR + NEW_ROLE + " " + "<" + GESAH_HAS_ROLE_TYPE + ">" + " " + VAR + EXISTING_ROLE_TYPE + " . " ;	
	
  final static String n3ForExistingRole  =
			"@prefix gesah:" + " " + "<" + GESAH + ">" + " ." + "\n" +
			VAR + OB_CULTURAL_OBJECT + " " + "<" + GESAH_REALIZES + ">" + " " + VAR + EXISTING_ROLE + " . " + "\n";
	
  final static String n3ForNewRoleType  =
			"@prefix rdfs:" + " " + "<" + RDFS + ">" + "  . " + "\n" +
      VAR + OB_CULTURAL_OBJECT + " " + "<" + GESAH_REALIZES + ">" + " " + VAR + NEW_ROLE + " . " + "\n" +
      VAR + NEW_ROLE + " " + "<" + GESAH_REALIZED_IN + ">" + " " + VAR + OB_CULTURAL_OBJECT + " . " + "\n" +
      VAR + NEW_ROLE + " " + "<" + GESAH_HAS_ROLE_TYPE + ">" + " " + VAR + NEW_ROLE_TYPE + " . " + "\n" +
      VAR + NEW_ROLE_TYPE + " " + "<" + LABEL + ">" + " " + VAR + NEW_ROLE_TYPE_LABEL + " . " + "\n" +
      VAR + NEW_ROLE_TYPE + " a " + "<" + GESAH_ROLE_TYPE + "> . " ;
	
  final static String n3ForNewRole  =
      VAR + OB_CULTURAL_OBJECT + " " + "<" + GESAH_REALIZES + ">" + " " + VAR + NEW_ROLE + " . " + "\n" +
      VAR + NEW_ROLE + " " + "<" + GESAH_REALIZED_IN + ">" + " " + VAR + OB_CULTURAL_OBJECT + " . " + "\n" +
	    VAR + NEW_ROLE + " a " + "<" + OBO + "BFO_0000023> . " ;
  
  //Should work only if participant wasn't selected
  final static String n3ForNewAgent  =
  		"@prefix rdfs:" + " " + "<" + RDFS + ">" + " ." + "\n" +
      VAR + OB_CULTURAL_OBJECT + " " + "<" + GESAH_HAS_PARTICIPANT + ">" + " " + VAR + NEW_AGENT + " . " + "\n" +
      VAR + NEW_AGENT + " " + "<" + GESAH_PARTICIPATES_IN + ">" + " " + VAR + OB_CULTURAL_OBJECT + " . " + "\n" +
  		VAR + OB_CULTURAL_OBJECT + " " + "<" + GESAH_REALIZES + ">" + " " + VAR + NEW_ROLE + " . " + "\n" +
  		VAR + NEW_AGENT + " " + "<" + GESAH_HAS_ROLE + ">" + " " + VAR + NEW_ROLE + " . " + "\n" +
  		VAR + NEW_ROLE + " " + "<" + GESAH_IS_ROLE_OF + ">" + " " + VAR + NEW_AGENT + " . " + "\n" +
  		VAR + NEW_ROLE + " " + "<" + GESAH_REALIZED_IN + ">" + " " + VAR + OB_CULTURAL_OBJECT + " . " + "\n" +
      VAR + NEW_AGENT + " a " + VAR + AGENT_TYPE + " . " + "\n" +
  		VAR + AGENT_TYPE + " rdfs:subClassOf <" + AGENT_CLASS + "> ." + "\n" +
      VAR + NEW_AGENT + " rdfs:label " + VAR + AGENT_LABEL + " . ";
  
  final static String n3ForExistingAgent  =
  		"@prefix rdfs:" + " " + "<" + RDFS +">" + " " + " . \n" +
      VAR + OB_CULTURAL_OBJECT + " " + "<" + GESAH_HAS_PARTICIPANT + ">" + " " + VAR + EXISTING_AGENT + " . \n" +
      VAR + OB_CULTURAL_OBJECT + " " + "<" + GESAH_REALIZES + ">" + " " + VAR + NEW_ROLE + " . \n" +
      VAR + EXISTING_AGENT +" " + "<" + GESAH_PARTICIPATES_IN + ">" + " " + VAR + OB_CULTURAL_OBJECT + " . \n" +
      VAR + EXISTING_AGENT +" " + "<" + GESAH_HAS_ROLE + ">" + " " + VAR + NEW_ROLE + " . \n" +
      VAR + NEW_ROLE +" " + "<" + GESAH_REALIZED_IN + ">" + " " + VAR + OB_CULTURAL_OBJECT + " . \n" +
      VAR + NEW_ROLE +" " + "<" + GESAH_IS_ROLE_OF + ">" + " " + VAR + EXISTING_AGENT + " . " ;
  
  final static String n3ForStart =
      VAR + OB_CULTURAL_OBJECT + " " + "<" + TO_INTERVAL + ">" + " " + VAR + INTERVAL_NODE + " ." + "\n" +
      VAR + INTERVAL_NODE + " " + " " + "<" + TYPE + ">" + " " + "<" + INTERVAL_TYPE + ">" + " ." + "\n" +
      VAR + INTERVAL_NODE + " " + "<" + INTERVAL_TO_START + ">" + " " + VAR + START_NODE + " ." + "\n" +
      VAR + START_NODE + " " + " " + "<" + TYPE + ">" + " " + "<" + DATE_TIME_VALUE_TYPE + ">" + " ." + "\n" +
      VAR + START_NODE + " " + " " + "<" + DATE_TIME_VALUE + ">" + " " + VAR + START_FIELD_VALUE + "." + "\n" +
      VAR + START_NODE + " " + " " + "<" + DATE_TIME_PRECISION + ">" + " " + VAR + START_FIELD_PRECISION + " .";

  final static String n3ForEnd =
      VAR + OB_CULTURAL_OBJECT + " " + " " + "<" + TO_INTERVAL + ">" + " " + VAR + INTERVAL_NODE + " . " + "\n" +
      VAR + INTERVAL_NODE + " " + " " + "<" + TYPE + ">" + " " + "<" + INTERVAL_TYPE + ">" + " ." + "\n" +
      VAR + INTERVAL_NODE + " " + "<" + INTERVAL_TO_END + ">" + " " + VAR + END_NODE + " ." + "\n" +
      VAR + END_NODE + " " + " " + "<" + TYPE + ">" + " " + "<" + DATE_TIME_VALUE_TYPE + ">" + " ." + "\n" +
      VAR + END_NODE + " " + " " + "<" + DATE_TIME_VALUE + ">" + " " + VAR + END_FIELD_VALUE + " ." + "\n" +
      VAR + END_NODE + " " + " " + "<" + DATE_TIME_PRECISION + ">" + " " + VAR + END_FIELD_PRECISION + " .";
  
  final static String commentAssertion  =
      VAR + OB_CULTURAL_OBJECT + " " + "<" + GESAH_COMMENT + ">" + " " + VAR + COMMENT + " .";
  
	final static String existingRoleQuery =
			"SELECT" + " " + VAR + EXISTING_ROLE + " WHERE {" + "\n" +
			VAR + OB_CULTURAL_OBJECT + " " + "<" + GESAH_REALIZES + ">" + " " + VAR + EXISTING_ROLE + "  . }";
	
  final static String existingRoleTypeQuery =
      "SELECT" + " " + VAR + EXISTING_ROLE_TYPE + " WHERE {" + "\n" +
      VAR + OB_CULTURAL_OBJECT + " " + "<" + GESAH_REALIZES + ">" + " " + VAR + EXISTING_ROLE + " . " + "\n" +
      VAR + EXISTING_ROLE + " " + "<" + GESAH_REALIZED_IN + ">" + " " + VAR + OB_CULTURAL_OBJECT + " . " + "\n" +
      VAR + EXISTING_ROLE + " " + "<" + GESAH_HAS_ROLE_TYPE + ">" + " " + VAR + EXISTING_ROLE_TYPE + " . }";

  final static String existingRoleTypeLabelQuery =
      "SELECT Distinct" + " " + VAR + EXISTING_ROLE_TYPE_LABEL + " WHERE {" + "\n" +
      VAR + OB_CULTURAL_OBJECT + " " + "<" + GESAH_REALIZES + ">" + " " + VAR + EXISTING_ROLE + " . " + "\n" +
      VAR + EXISTING_ROLE + " " + "<" + GESAH_REALIZED_IN + ">" + " " + VAR + OB_CULTURAL_OBJECT + " . " + "\n" +
      VAR + EXISTING_ROLE + " " + "<" + GESAH_HAS_ROLE_TYPE + ">" + " " + VAR + EXISTING_ROLE_TYPE + " . " + "\n" +
      VAR + EXISTING_ROLE_TYPE + " " + "<" + LABEL + ">" + " " + VAR + EXISTING_ROLE_TYPE_LABEL + " . }";		
  
  final static String existingAgentQuery  =
      "PREFIX rdfs:" + " " + "<" + RDFS + ">" + "   " + "\n" +
      "SELECT" + " " + VAR + EXISTING_AGENT + " WHERE {" + "\n" +
      VAR + OB_CULTURAL_OBJECT + " " + "<" + GESAH_HAS_PARTICIPANT + ">" + " " + VAR + EXISTING_AGENT + " . " + "\n" +
      VAR + EXISTING_AGENT + " " + "<" + GESAH_PARTICIPATES_IN + ">" + " " + VAR + OB_CULTURAL_OBJECT + " . " + "\n" +
      VAR + EXISTING_AGENT + " " + "<" + GESAH_HAS_ROLE + ">" + " " + VAR + EXISTING_ROLE + " ." + "\n" +
      VAR + EXISTING_ROLE + " " + "<" + GESAH_IS_ROLE_OF + ">" + " " + VAR + EXISTING_AGENT + " ." + "\n" +
      VAR + EXISTING_AGENT + " a" + " " + VAR + AGENT_TYPE + " . \n " +
      VAR + AGENT_TYPE + " rdfs:subClassOf <" + AGENT_CLASS + "> . }" ;
	
  final static String agentLabelQuery  =
      "PREFIX rdfs:" + " " + "<" + RDFS + ">" + "   " + "\n" +
      "SELECT Distinct" + " " + VAR + EXISTING_AGENT_LABEL + " WHERE {" + "\n" +
      VAR + OB_CULTURAL_OBJECT + " " + "<" + GESAH_HAS_PARTICIPANT + ">" + " " + VAR + EXISTING_AGENT + " . " + "\n" +
      VAR + EXISTING_AGENT + " " + "<" + GESAH_PARTICIPATES_IN + ">" + " " + VAR + OB_CULTURAL_OBJECT + " . " + "\n" +
      VAR + EXISTING_AGENT + " " + "<" + LABEL + ">" + " " + VAR + EXISTING_AGENT_LABEL + " ." + "\n" +
      VAR + EXISTING_AGENT + " a" + " " + VAR + AGENT_TYPE + " . \n " +
      VAR + AGENT_TYPE + " rdfs:subClassOf" + " " + "<" + AGENT_CLASS + ">" + " . }" ;
  
  final static String agentTypeQuery  =
      "PREFIX rdfs:" + " " + "<" + RDFS + ">" + "   " + "\n" +
      "SELECT" + " " + VAR + AGENT_TYPE + " WHERE {" + "\n" +
      VAR + OB_CULTURAL_OBJECT + " " + "<" + GESAH_HAS_PARTICIPANT + ">" + " " + VAR + EXISTING_AGENT + " . " + "\n" +
      VAR + EXISTING_AGENT + " " + "<" + GESAH_PARTICIPATES_IN + ">" + " " + VAR + OB_CULTURAL_OBJECT + " . " + "\n" +
      VAR + EXISTING_AGENT + " " + "<" + GESAH_HAS_ROLE + ">" + " " + VAR + EXISTING_ROLE + " ." + "\n" +
      VAR + EXISTING_ROLE + " " + "<" + GESAH_IS_ROLE_OF + ">" + " " + VAR + EXISTING_AGENT + " ." + "\n" +
      VAR + EXISTING_AGENT + " a" + " " + VAR + AGENT_TYPE + " ." + "\n" +
      VAR + AGENT_TYPE + " rdfs:subClassOf <" + AGENT_CLASS + "> .}";		
  
  
  final static String litDateAppelQuery  =
      "SELECT" + " " + VAR + EXISTINGLIT_DATE_APPEL + " WHERE {" + "\n" +
      VAR + OB_CULTURAL_OBJECT + " " + "<" + LITERAL_DATE_APPEAL_PRED + ">" + " " + VAR + EXISTINGLIT_DATE_APPEL + " . }";

  final static String existingIntervalNodeQuery  =
      "SELECT" + " " + VAR + EXISTING_INTERVAL_NODE + " WHERE {" + "\n" +
      VAR + OB_CULTURAL_OBJECT + " " + "<" + TO_INTERVAL + ">" + " " + VAR + EXISTING_INTERVAL_NODE + " ." + "\n" +
      VAR + EXISTING_INTERVAL_NODE + " " + "<" + TYPE + ">" + " " + "<" + INTERVAL_TYPE + ">" + " . }";

  final static String existingStartNodeQuery  =
      "SELECT" + " " + VAR + EXISTING_START_NODE + " WHERE {" + "\n" +
      VAR + OB_CULTURAL_OBJECT + " " + "<" + TO_INTERVAL + ">" + " " + VAR + INTERVAL_NODE + " ." + "\n" +
      VAR + INTERVAL_NODE + " " + "<" + TYPE + ">" + " " + "<" + INTERVAL_TYPE + ">" + " ." + "\n" +
      VAR + INTERVAL_NODE + " " + "<" + INTERVAL_TO_START + ">" + " " + VAR + EXISTING_START_NODE + " . " + "\n" +
      VAR + EXISTING_START_NODE + " " + "<" + TYPE + ">" + " " + "<" + DATE_TIME_VALUE_TYPE + ">" + " .}";

  final static String existingStartDateQuery  =
      "SELECT" + " " + VAR + EXISTING_DATE_START + " WHERE {" + "\n" +
      VAR + OB_CULTURAL_OBJECT + " " + "<" + TO_INTERVAL + ">" + " " + VAR + INTERVAL_NODE + " ." + "\n" +
      VAR + INTERVAL_NODE + " " + "<" + TYPE + ">" + " " + "<" + INTERVAL_TYPE + ">" + " ." + "\n" +
      VAR + INTERVAL_NODE + " " + "<" + INTERVAL_TO_START + ">" + " " + VAR + START_NODE + " ." + "\n" +
      VAR + START_NODE + " " + "<" + TYPE + ">" + " " + "<" + DATE_TIME_VALUE_TYPE + ">" + " ." + "\n" +
      VAR + START_NODE + " " + "<" + DATE_TIME_VALUE + ">" + " " + VAR + EXISTING_DATE_START + " . }";

  final static String existingStartPrecisionQuery  =
      "SELECT" + " " + VAR + EXISTING_START_PRECISION + " WHERE {" + "\n" +
      VAR + OB_CULTURAL_OBJECT + " " + "<" + TO_INTERVAL + ">" + " " + VAR + INTERVAL_NODE + " ." + "\n" +
      VAR + INTERVAL_NODE + " " + "<" + TYPE + ">" + " " + "<" + INTERVAL_TYPE + ">" + " ." + "\n" +
      VAR + INTERVAL_NODE + " " + "<" + INTERVAL_TO_START + ">" + " " + VAR + START_NODE + " ." + "\n" +
      VAR + START_NODE + " " + "<" + TYPE + ">" + " " + "<" + DATE_TIME_VALUE_TYPE + ">" + " . " + "\n" +
      VAR + START_NODE + " " + "<" + DATE_TIME_PRECISION + ">" + " " + VAR + EXISTING_START_PRECISION + " . }";

  final static String existingEndNodeQuery  =
      "SELECT" + " " + VAR + EXISTING_END_NODE + " WHERE { " + "\n" +
      VAR + OB_CULTURAL_OBJECT + " " + "<" + TO_INTERVAL + ">" + " " + VAR + INTERVAL_NODE + " ." + "\n" +
      VAR + INTERVAL_NODE + " " + "<" + TYPE + ">" + " " + "<" + INTERVAL_TYPE + ">" + " ." + "\n" +
      VAR + INTERVAL_NODE + " " + "<" + INTERVAL_TO_END + ">" + " " + VAR + EXISTING_END_NODE + " . " + "\n" +
      VAR + EXISTING_END_NODE + " " + "<" + TYPE + ">" + " " + "<" + DATE_TIME_VALUE_TYPE + ">" + " .}";

  final static String existingEndDateQuery  =
      "SELECT" + " " + VAR + EXISTING_END_DATE + " WHERE {" + "\n" +
      VAR + OB_CULTURAL_OBJECT + " " + "<" + TO_INTERVAL + ">" + " " + VAR + INTERVAL_NODE + " ." + "\n" +
      VAR + INTERVAL_NODE + " " + "<" + TYPE + ">" + " " + "<" + INTERVAL_TYPE + ">" + " ." + "\n" +
      VAR + INTERVAL_NODE + " " + "<" + INTERVAL_TO_END + ">" + " " + VAR + END_NODE + " ." + "\n" +
      VAR + END_NODE + " " + "<" + TYPE + ">" + " " + "<" + DATE_TIME_VALUE_TYPE + ">" + " ." + "\n" +
      VAR + END_NODE + " " + "<" + DATE_TIME_VALUE + ">" + " " + VAR + EXISTING_END_DATE + " . }";

  final static String existingEndPrecisionQuery  =
      "SELECT" + " " + VAR + EXISTING_END_PRECISION + " WHERE {" + "\n" +
      VAR + OB_CULTURAL_OBJECT + " " + "<" + TO_INTERVAL + ">" + " " + VAR + INTERVAL_NODE + " ." + "\n" +
      VAR + INTERVAL_NODE + " " + "<" + TYPE + ">" + " " + "<" + INTERVAL_TYPE + ">" + " ." + "\n" +
      VAR + INTERVAL_NODE + " " + "<" + INTERVAL_TO_END + ">" + " " + VAR + END_NODE + " ." + "\n" +
      VAR + END_NODE + " " + "<" + TYPE + ">" + " " + "<" + DATE_TIME_VALUE_TYPE + ">" + " ." + "\n" +
      VAR + END_NODE + " " + "<" + DATE_TIME_PRECISION + ">" + " " + VAR + EXISTING_END_PRECISION + " . }";
  
  final static String n3ForNewTechnique  =
      "@prefix gesah:" + " " + "<" + GESAH + ">" + " ." + "\n" +
      VAR + OB_CULTURAL_OBJECT + " " + "<" + GESAH_USES_TECHNIQUE + ">" + " " + VAR + NEW_TECHNIQUE + " . " + "\n" +
      VAR + NEW_TECHNIQUE + " " + "<" + GESAH_USED_IN + ">" + " " + VAR + OB_CULTURAL_OBJECT + " . " + "\n" +
      VAR + NEW_TECHNIQUE + " " + "<" + LABEL + ">" + " " + VAR + TECHNIQUE_LABEL + " . " + "\n" +
      VAR + NEW_TECHNIQUE + " a" + " " + "<" + GESAH_TECHNIQUE + "> .";
	
  final static String n3ForExistingTechnique  =
      "@prefix gesah:" + " " + "<" + GESAH + ">" + " ." + "\n" +
      VAR + OB_CULTURAL_OBJECT + " " + "<" + GESAH_USES_TECHNIQUE + ">" + " " + VAR + EXISTING_TECHNIQUE + " . " + "\n" +
      VAR + EXISTING_TECHNIQUE + " " + "<" + GESAH_USED_IN + ">" + " " + VAR + OB_CULTURAL_OBJECT + " . " + "\n" +
      VAR + EXISTING_TECHNIQUE + " a" + " " + "<" + GESAH_TECHNIQUE + "> .";	
	
  final static String n3ForNewMaterial  =
      "@prefix gesah:" + " " + "<" + GESAH + ">" + " ." + "\n" +
      VAR + OB_CULTURAL_OBJECT + " " + "<" + GESAH_HAS_MATERIAL + ">" + " " + VAR + NEW_MATERIAL + " . " + "\n" +
      VAR + NEW_MATERIAL + " " + "<" + GESAH_INCORPORATED_IN + ">" + " " + VAR + OB_CULTURAL_OBJECT + " . " + "\n" +
      VAR + NEW_MATERIAL + " " + "<" + LABEL + ">" + " " + VAR + MATERIAL_LABEL + " . " + "\n" +
      VAR + NEW_MATERIAL + " a" + " " + "<" + GESAH_MATERIAL + "> .";
	
  final static String n3ForExistingMaterial  =
      "@prefix gesah:" + " " + "<" + GESAH + ">" + " ." + "\n" +
      VAR + OB_CULTURAL_OBJECT + " " + "<" + GESAH_HAS_MATERIAL + ">" + " " + VAR + EXISTING_MATERIAL + " . " + "\n" +
      VAR + EXISTING_MATERIAL + " " + "<" + GESAH_INCORPORATED_IN + ">" + " " + VAR + OB_CULTURAL_OBJECT + " . " + "\n" +
      VAR + EXISTING_MATERIAL + " a" + " " + "<" + GESAH_MATERIAL + "> .";

  final static String n3ForNewPlace  =
      "@prefix gesah:" + " " + "<" + GESAH + ">" + " ." + "\n" +
      VAR + OB_CULTURAL_OBJECT + " " + "<" + GESAH_HAS_PLACE + ">" + " " + VAR + NEW_PLACE + " . " + "\n" +
      VAR + NEW_PLACE + " " + "<" + GESAH_IS_PLACE_OF + ">" + " " + VAR + OB_CULTURAL_OBJECT + " . " + "\n" +
      VAR + NEW_PLACE + " " + "<" + LABEL + ">" + " " + VAR + PLACE_LABEL + " . " + "\n" +
      VAR + NEW_PLACE + " a <http://vivoweb.org/ontology/core#GeographicLocation> .";
	
  final static String n3ForExistingPlace  =
      "@prefix gesah:" + " " + "<" + GESAH + ">" + " ." + "\n" +
      VAR + OB_CULTURAL_OBJECT + " " + "<" + GESAH_HAS_PLACE + ">" + " " + VAR + EXISTING_PLACE + " . " + "\n" +
      VAR + EXISTING_PLACE + " " + "<" + GESAH_IS_PLACE_OF + ">" + " " + VAR + OB_CULTURAL_OBJECT + " . " + "\n" +
      VAR + EXISTING_PLACE + " a <http://vivoweb.org/ontology/core#GeographicLocation> .";
  
  final static String existingPlaceQuery =
      "SELECT " + " " + VAR + EXISTING_PLACE + " WHERE {" + "\n" +
      VAR + OB_CULTURAL_OBJECT + " " + "<" + GESAH_HAS_PLACE + ">" + " " + VAR + EXISTING_PLACE + "  . }";

  final static String existingPlaceLabelQuery =
      "SELECT Distinct" + " " + VAR + EXISTING_PLACE_LABEL + " WHERE {" + "\n" +
      VAR + OB_CULTURAL_OBJECT + " " + "<" + GESAH_HAS_PLACE + ">" + " " + VAR + EXISTING_PLACE + " . " + "\n" +
      VAR + EXISTING_PLACE + " " + "<" + LABEL + ">" + " " + VAR + EXISTING_PLACE_LABEL + " .}";	
  
  final static String existingTechniqueQuery =
      "SELECT" + " " + VAR + EXISTING_TECHNIQUE + " WHERE {" + "\n" +
      VAR + OB_CULTURAL_OBJECT + " " + "<" + GESAH_USES_TECHNIQUE + ">" + " " + VAR + EXISTING_TECHNIQUE + " . }";

	final static String existingTechniqueLabelQuery =
      "SELECT Distinct" + " " + VAR + EXISTING_TECHNIQUE_LABEL + " WHERE {" + "\n" +
      VAR + OB_CULTURAL_OBJECT + " " + "<" + GESAH_USES_TECHNIQUE + ">" + " " + VAR + EXISTING_TECHNIQUE + " . " + "\n" +
      VAR + EXISTING_TECHNIQUE + " " + "<" + LABEL + ">" + " " + VAR + EXISTING_TECHNIQUE_LABEL + " .}";

	final static String existingMaterialQuery =
      "SELECT" + " " + VAR + EXISTING_MATERIAL + " WHERE {" + "\n" +
      VAR + OB_CULTURAL_OBJECT + " " + "<" + GESAH_HAS_MATERIAL + ">" + " " + VAR + EXISTING_MATERIAL + "  . }";
	
  final static String existingMaterialLabelQuery =
      "SELECT Distinct" + " " + VAR + EXISTING_MATERIAL + "Label" + " WHERE {" + "\n" +
      VAR + OB_CULTURAL_OBJECT + " " + "<" + GESAH_HAS_MATERIAL + ">" + " " + VAR + EXISTING_MATERIAL + " . " + "\n" +
      VAR + EXISTING_MATERIAL + " " + "<" + LABEL + ">" + " " + VAR + EXISTING_MATERIAL + "Label" + " . }";	

  final static String existingAttrTypeQuery =
      "SELECT" + " " + VAR + EXISTING_ATTR_TYPE + " WHERE {" + "\n" +
      VAR + OB_CULTURAL_OBJECT + " " + "<" + GESAH_HAS_TYPE_OF_ATTRIBUTION + ">" + " " + VAR + EXISTING_ATTR_TYPE + " . }" + "\n";

  final static String existingAttrTypeLabelQuery =
      "SELECT Distinct" + " " + VAR + EXISTING_ATTR_TYPE_LABEL + " WHERE {" + "\n" +
      VAR + OB_CULTURAL_OBJECT + " " + "<" + GESAH_HAS_TYPE_OF_ATTRIBUTION + ">" + " " + VAR + EXISTING_ATTR_TYPE + " . " + "\n" +
      VAR + EXISTING_ATTR_TYPE + " " + "<" + LABEL + ">" + " " + VAR + EXISTING_ATTR_TYPE_LABEL + " .}";
}
