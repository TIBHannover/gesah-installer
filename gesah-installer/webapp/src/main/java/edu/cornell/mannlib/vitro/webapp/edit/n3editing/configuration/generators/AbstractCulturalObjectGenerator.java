package edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators;


public abstract class AbstractCulturalObjectGenerator extends GesahEditConfigurationGenerator {
	public static final String SPACE = " ";
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
	public static final String EXISTING_COMMENT_VALUE = "existingCommentValue";


	
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
	public static final String EXISTINGLIT_DATE_APPEL_VALUE = "existinglitDateAppelValue";

	
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
	public static final String LINE_END = " . \n ";
	public static final String GT = "> ";
	public static final String LT = " <";
	public static final String WHERE = " WHERE { \n ";
	public static final String SELECT = "SELECT ";

	public static final String A = " a ";

	
  final static String commentQuery  =
      "SELECT" + SPACE + VAR + EXISTING_COMMENT + " WHERE {" + "\n" +
      VAR + OB_CULTURAL_OBJECT + SPACE + "<" + COMMENT_PRED + ">" + SPACE + VAR + EXISTING_COMMENT + " . }";
  
  final static String commentValueQuery  =
      "SELECT" + SPACE + " (STR(?existCommentColumn) as " + VAR + EXISTING_COMMENT_VALUE + " ) WHERE {" + "\n" +
      VAR + OB_CULTURAL_OBJECT + SPACE + "<" + COMMENT_PRED + ">" + SPACE + VAR + "existCommentColumn" + " . }";
  
  
  final static String litDateAppelAssertion  =
      VAR + OB_CULTURAL_OBJECT + SPACE + "<" + GESAH_LITERAL_DATE_APPELATION + "> " + VAR + LIT_DATE_APPEL + " .";	
  
  final static String n3ForExistingAttrType  =
      "@prefix gesah:" + SPACE + "<" + GESAH + ">" + " ." + "\n" +
      VAR + OB_CULTURAL_OBJECT + SPACE + "<" + GESAH_HAS_TYPE_OF_ATTRIBUTION + ">" + SPACE + VAR + EXISTING_ATTR_TYPE + " . " + "\n" +
      VAR + EXISTING_ATTR_TYPE + SPACE + "<" + GESAH_IS_ATTRIBUTION_TYPE_OF + ">" + SPACE + VAR + OB_CULTURAL_OBJECT + " . " + "\n" +
      VAR + EXISTING_ATTR_TYPE + " a" + SPACE + "<" + GESAH_ATTRIBUTION_TYPE + "> .";	
	
  final static String n3ForNewAttrType  =
      "@prefix gesah:" + SPACE + "<" + GESAH + ">" + " ." + "\n" +
      VAR + OB_CULTURAL_OBJECT + SPACE + "<" + GESAH_HAS_TYPE_OF_ATTRIBUTION + ">" + SPACE + VAR + NEW_ATTR_TYPE + " . " + "\n" +
      VAR + NEW_ATTR_TYPE + SPACE + "<" + GESAH_IS_ATTRIBUTION_TYPE_OF + ">" + SPACE + VAR + OB_CULTURAL_OBJECT + " . " + "\n" +
      VAR + NEW_ATTR_TYPE + SPACE + "<" + LABEL + ">" + SPACE + VAR + ATTR_TYPE_LABEL + " . " + "\n" +
      VAR + NEW_ATTR_TYPE + " a " + "<" + GESAH_ATTRIBUTION_TYPE + "> .";
	
  final static String n3ForExistingRoleType  =
			VAR + OB_CULTURAL_OBJECT + SPACE + "<" + GESAH_REALIZES + ">" + SPACE + VAR + NEW_ROLE + " . " + "\n" +
			VAR + NEW_ROLE + SPACE + "<" + GESAH_REALIZED_IN + ">" + SPACE + VAR + OB_CULTURAL_OBJECT + " . " + "\n" +
			VAR + NEW_ROLE + SPACE + "<" + GESAH_HAS_ROLE_TYPE + ">" + SPACE + VAR + EXISTING_ROLE_TYPE + " . " ;	
	
  final static String n3ForExistingRole  =
			"@prefix gesah:" + SPACE + "<" + GESAH + ">" + " ." + "\n" +
			VAR + OB_CULTURAL_OBJECT + SPACE + "<" + GESAH_REALIZES + ">" + SPACE + VAR + EXISTING_ROLE + " . " + "\n";
	
  final static String n3ForNewRoleType  =
			"@prefix rdfs:" + SPACE + "<" + RDFS + ">" + "  . " + "\n" +
      VAR + OB_CULTURAL_OBJECT + SPACE + "<" + GESAH_REALIZES + ">" + SPACE + VAR + NEW_ROLE + " . " + "\n" +
      VAR + NEW_ROLE + SPACE + "<" + GESAH_REALIZED_IN + ">" + SPACE + VAR + OB_CULTURAL_OBJECT + " . " + "\n" +
      VAR + NEW_ROLE + SPACE + "<" + GESAH_HAS_ROLE_TYPE + ">" + SPACE + VAR + NEW_ROLE_TYPE + " . " + "\n" +
      VAR + NEW_ROLE_TYPE + SPACE + "<" + LABEL + ">" + SPACE + VAR + NEW_ROLE_TYPE_LABEL + " . " + "\n" +
      VAR + NEW_ROLE_TYPE + " a " + "<" + GESAH_ROLE_TYPE + "> . " ;
	
  final static String n3ForNewRole  =
      VAR + OB_CULTURAL_OBJECT + SPACE + "<" + GESAH_REALIZES + ">" + SPACE + VAR + NEW_ROLE + " . " + "\n" +
      VAR + NEW_ROLE + SPACE + "<" + GESAH_REALIZED_IN + ">" + SPACE + VAR + OB_CULTURAL_OBJECT + " . " + "\n" +
	    VAR + NEW_ROLE + " a " + "<" + OBO + "BFO_0000023> . " ;
  
  //Should work only if participant wasn't selected
  final static String n3ForNewAgent  =
  		"@prefix rdfs:" + SPACE + "<" + RDFS + ">" + " ." + "\n" +
      VAR + OB_CULTURAL_OBJECT + SPACE + "<" + GESAH_HAS_PARTICIPANT + ">" + SPACE + VAR + NEW_AGENT + " . " + "\n" +
      VAR + NEW_AGENT + SPACE + "<" + GESAH_PARTICIPATES_IN + ">" + SPACE + VAR + OB_CULTURAL_OBJECT + " . " + "\n" +
  		VAR + OB_CULTURAL_OBJECT + SPACE + "<" + GESAH_REALIZES + ">" + SPACE + VAR + NEW_ROLE + " . " + "\n" +
  		VAR + NEW_AGENT + SPACE + "<" + GESAH_HAS_ROLE + ">" + SPACE + VAR + NEW_ROLE + " . " + "\n" +
  		VAR + NEW_ROLE + SPACE + "<" + GESAH_IS_ROLE_OF + ">" + SPACE + VAR + NEW_AGENT + " . " + "\n" +
  		VAR + NEW_ROLE + SPACE + "<" + GESAH_REALIZED_IN + ">" + SPACE + VAR + OB_CULTURAL_OBJECT + " . " + "\n" +
      VAR + NEW_AGENT + " a " + VAR + AGENT_TYPE + " . " + "\n" +
  		VAR + AGENT_TYPE + " rdfs:subClassOf <" + AGENT_CLASS + "> ." + "\n" +
      VAR + NEW_AGENT + " rdfs:label " + VAR + AGENT_LABEL + " . ";
  
  final static String n3ForExistingAgent  =
  		"@prefix rdfs:" + SPACE + "<" + RDFS +">" + SPACE + " . \n" +
      VAR + OB_CULTURAL_OBJECT + SPACE + "<" + GESAH_HAS_PARTICIPANT + ">" + SPACE + VAR + EXISTING_AGENT + " . \n" +
      VAR + OB_CULTURAL_OBJECT + SPACE + "<" + GESAH_REALIZES + ">" + SPACE + VAR + NEW_ROLE + " . \n" +
      VAR + EXISTING_AGENT +SPACE + "<" + GESAH_PARTICIPATES_IN + ">" + SPACE + VAR + OB_CULTURAL_OBJECT + " . \n" +
      VAR + EXISTING_AGENT +SPACE + "<" + GESAH_HAS_ROLE + ">" + SPACE + VAR + NEW_ROLE + " . \n" +
      VAR + NEW_ROLE +SPACE + "<" + GESAH_REALIZED_IN + ">" + SPACE + VAR + OB_CULTURAL_OBJECT + " . \n" +
      VAR + NEW_ROLE +SPACE + "<" + GESAH_IS_ROLE_OF + ">" + SPACE + VAR + EXISTING_AGENT + " . " ;
  
  final static String n3ForStart =
      VAR + OB_CULTURAL_OBJECT + SPACE + "<" + TO_INTERVAL + ">" + SPACE + VAR + INTERVAL_NODE + " ." + "\n" +
      VAR + INTERVAL_NODE + SPACE + SPACE + "<" + TYPE + ">" + SPACE + "<" + INTERVAL_TYPE + ">" + " ." + "\n" +
      VAR + INTERVAL_NODE + SPACE + "<" + INTERVAL_TO_START + ">" + SPACE + VAR + START_NODE + " ." + "\n" +
      VAR + START_NODE + SPACE + SPACE + "<" + TYPE + ">" + SPACE + "<" + DATE_TIME_VALUE_TYPE + ">" + " ." + "\n" +
      VAR + START_NODE + SPACE + SPACE + "<" + DATE_TIME_VALUE + ">" + SPACE + VAR + START_FIELD_VALUE + "." + "\n" +
      VAR + START_NODE + SPACE + SPACE + "<" + DATE_TIME_PRECISION + ">" + SPACE + VAR + START_FIELD_PRECISION + " .";

  final static String n3ForEnd =
      VAR + OB_CULTURAL_OBJECT + SPACE + SPACE + "<" + TO_INTERVAL + ">" + SPACE + VAR + INTERVAL_NODE + " . " + "\n" +
      VAR + INTERVAL_NODE + SPACE + SPACE + "<" + TYPE + ">" + SPACE + "<" + INTERVAL_TYPE + ">" + " ." + "\n" +
      VAR + INTERVAL_NODE + SPACE + "<" + INTERVAL_TO_END + ">" + SPACE + VAR + END_NODE + " ." + "\n" +
      VAR + END_NODE + SPACE + SPACE + "<" + TYPE + ">" + SPACE + "<" + DATE_TIME_VALUE_TYPE + ">" + " ." + "\n" +
      VAR + END_NODE + SPACE + SPACE + "<" + DATE_TIME_VALUE + ">" + SPACE + VAR + END_FIELD_VALUE + " ." + "\n" +
      VAR + END_NODE + SPACE + SPACE + "<" + DATE_TIME_PRECISION + ">" + SPACE + VAR + END_FIELD_PRECISION + " .";
  
  final static String commentAssertion  =
      VAR + OB_CULTURAL_OBJECT + SPACE + "<" + GESAH_COMMENT + ">" + SPACE + VAR + COMMENT + " .";
  
	final static String existingRoleQuery =
			"SELECT" + SPACE + VAR + EXISTING_ROLE + " WHERE {" + "\n" +
			VAR + OB_CULTURAL_OBJECT + SPACE + "<" + GESAH_REALIZES + ">" + SPACE + VAR + EXISTING_ROLE + "  . }";
	
  final static String existingRoleTypeQuery =
      "SELECT" + SPACE + VAR + EXISTING_ROLE_TYPE + " WHERE {" + "\n" +
      VAR + OB_CULTURAL_OBJECT + SPACE + "<" + GESAH_REALIZES + ">" + SPACE + VAR + EXISTING_ROLE + " . " + "\n" +
      VAR + EXISTING_ROLE + SPACE + "<" + GESAH_REALIZED_IN + ">" + SPACE + VAR + OB_CULTURAL_OBJECT + " . " + "\n" +
      VAR + EXISTING_ROLE + SPACE + "<" + GESAH_HAS_ROLE_TYPE + ">" + SPACE + VAR + EXISTING_ROLE_TYPE + " . }";

  final static String existingRoleTypeLabelQuery =
      "SELECT Distinct" + SPACE + VAR + EXISTING_ROLE_TYPE_LABEL + " WHERE {" + "\n" +
      VAR + OB_CULTURAL_OBJECT + SPACE + "<" + GESAH_REALIZES + ">" + SPACE + VAR + EXISTING_ROLE + " . " + "\n" +
      VAR + EXISTING_ROLE + SPACE + "<" + GESAH_REALIZED_IN + ">" + SPACE + VAR + OB_CULTURAL_OBJECT + " . " + "\n" +
      VAR + EXISTING_ROLE + SPACE + "<" + GESAH_HAS_ROLE_TYPE + ">" + SPACE + VAR + EXISTING_ROLE_TYPE + " . " + "\n" +
      VAR + EXISTING_ROLE_TYPE + SPACE + "<" + LABEL + ">" + SPACE + VAR + EXISTING_ROLE_TYPE_LABEL + " . }";		
  
  final static String existingAgentQuery  =
      "PREFIX rdfs:" + SPACE + "<" + RDFS + ">" + "   " + "\n" +
      "SELECT" + SPACE + VAR + EXISTING_AGENT + " WHERE {" + "\n" +
      VAR + OB_CULTURAL_OBJECT + SPACE + "<" + GESAH_HAS_PARTICIPANT + ">" + SPACE + VAR + EXISTING_AGENT + " . " + "\n" +
      VAR + EXISTING_AGENT + SPACE + "<" + GESAH_PARTICIPATES_IN + ">" + SPACE + VAR + OB_CULTURAL_OBJECT + " . " + "\n" +
      VAR + EXISTING_AGENT + SPACE + "<" + GESAH_HAS_ROLE + ">" + SPACE + VAR + EXISTING_ROLE + " ." + "\n" +
      VAR + EXISTING_ROLE + SPACE + "<" + GESAH_IS_ROLE_OF + ">" + SPACE + VAR + EXISTING_AGENT + " ." + "\n" +
      VAR + EXISTING_AGENT + " a" + SPACE + VAR + AGENT_TYPE + " . \n " +
      VAR + AGENT_TYPE + " rdfs:subClassOf <" + AGENT_CLASS + "> . }" ;
	
  final static String agentLabelQuery  =
      "PREFIX rdfs:" + SPACE + "<" + RDFS + ">" + "   " + "\n" +
      "SELECT Distinct" + SPACE + VAR + EXISTING_AGENT_LABEL + " WHERE {" + "\n" +
      VAR + OB_CULTURAL_OBJECT + SPACE + "<" + GESAH_HAS_PARTICIPANT + ">" + SPACE + VAR + EXISTING_AGENT + " . " + "\n" +
      VAR + EXISTING_AGENT + SPACE + "<" + GESAH_PARTICIPATES_IN + ">" + SPACE + VAR + OB_CULTURAL_OBJECT + " . " + "\n" +
      VAR + EXISTING_AGENT + SPACE + "<" + LABEL + ">" + SPACE + VAR + EXISTING_AGENT_LABEL + " ." + "\n" +
      VAR + EXISTING_AGENT + " a" + SPACE + VAR + AGENT_TYPE + " . \n " +
      VAR + AGENT_TYPE + " rdfs:subClassOf" + SPACE + "<" + AGENT_CLASS + ">" + " . }" ;
  
  final static String agentTypeQuery  =
      "PREFIX rdfs:" + SPACE + "<" + RDFS + ">" + "   " + "\n" +
      "SELECT" + SPACE + VAR + AGENT_TYPE + " WHERE {" + "\n" +
      VAR + OB_CULTURAL_OBJECT + SPACE + "<" + GESAH_HAS_PARTICIPANT + ">" + SPACE + VAR + EXISTING_AGENT + " . " + "\n" +
      VAR + EXISTING_AGENT + SPACE + "<" + GESAH_PARTICIPATES_IN + ">" + SPACE + VAR + OB_CULTURAL_OBJECT + " . " + "\n" +
      VAR + EXISTING_AGENT + SPACE + "<" + GESAH_HAS_ROLE + ">" + SPACE + VAR + EXISTING_ROLE + " ." + "\n" +
      VAR + EXISTING_ROLE + SPACE + "<" + GESAH_IS_ROLE_OF + ">" + SPACE + VAR + EXISTING_AGENT + " ." + "\n" +
      VAR + EXISTING_AGENT + " a" + SPACE + VAR + AGENT_TYPE + " ." + "\n" +
      VAR + AGENT_TYPE + " rdfs:subClassOf <" + AGENT_CLASS + "> .}";		
  
  final static String litDateAppelQuery  =
      "SELECT" + SPACE + VAR + EXISTINGLIT_DATE_APPEL + " WHERE {" + "\n" +
      VAR + OB_CULTURAL_OBJECT + SPACE + "<" + LITERAL_DATE_APPEAL_PRED + ">" + SPACE + VAR + EXISTINGLIT_DATE_APPEL + " . }";
  
  final static String litDateAppelQueryValue  =
      "SELECT" + SPACE + " (STR(?existLocColumn) as " + VAR + EXISTINGLIT_DATE_APPEL_VALUE + " ) " + " WHERE {" + "\n" +
      VAR + OB_CULTURAL_OBJECT + SPACE + "<" + LITERAL_DATE_APPEAL_PRED + ">" + SPACE + VAR + "existLocColumn" + " . }";

  final static String existingIntervalNodeQuery  =
      "SELECT" + SPACE + VAR + EXISTING_INTERVAL_NODE + " WHERE {" + "\n" +
      VAR + OB_CULTURAL_OBJECT + SPACE + "<" + TO_INTERVAL + ">" + SPACE + VAR + EXISTING_INTERVAL_NODE + " ." + "\n" +
      VAR + EXISTING_INTERVAL_NODE + SPACE + "<" + TYPE + ">" + SPACE + "<" + INTERVAL_TYPE + ">" + " . }";

  final static String existingStartNodeQuery  =
      "SELECT" + SPACE + VAR + EXISTING_START_NODE + " WHERE {" + "\n" +
      VAR + OB_CULTURAL_OBJECT + SPACE + "<" + TO_INTERVAL + ">" + SPACE + VAR + INTERVAL_NODE + " ." + "\n" +
      VAR + INTERVAL_NODE + SPACE + "<" + TYPE + ">" + SPACE + "<" + INTERVAL_TYPE + ">" + " ." + "\n" +
      VAR + INTERVAL_NODE + SPACE + "<" + INTERVAL_TO_START + ">" + SPACE + VAR + EXISTING_START_NODE + " . " + "\n" +
      VAR + EXISTING_START_NODE + SPACE + "<" + TYPE + ">" + SPACE + "<" + DATE_TIME_VALUE_TYPE + ">" + " .}";

  final static String existingStartDateQuery  =
      "SELECT" + SPACE + VAR + EXISTING_DATE_START + " WHERE {" + "\n" +
      VAR + OB_CULTURAL_OBJECT + SPACE + "<" + TO_INTERVAL + ">" + SPACE + VAR + INTERVAL_NODE + " ." + "\n" +
      VAR + INTERVAL_NODE + SPACE + "<" + TYPE + ">" + SPACE + "<" + INTERVAL_TYPE + ">" + " ." + "\n" +
      VAR + INTERVAL_NODE + SPACE + "<" + INTERVAL_TO_START + ">" + SPACE + VAR + START_NODE + " ." + "\n" +
      VAR + START_NODE + SPACE + "<" + TYPE + ">" + SPACE + "<" + DATE_TIME_VALUE_TYPE + ">" + " ." + "\n" +
      VAR + START_NODE + SPACE + "<" + DATE_TIME_VALUE + ">" + SPACE + VAR + EXISTING_DATE_START + " . }";

  final static String existingStartPrecisionQuery  =
      "SELECT" + SPACE + VAR + EXISTING_START_PRECISION + " WHERE {" + "\n" +
      VAR + OB_CULTURAL_OBJECT + SPACE + "<" + TO_INTERVAL + ">" + SPACE + VAR + INTERVAL_NODE + " ." + "\n" +
      VAR + INTERVAL_NODE + SPACE + "<" + TYPE + ">" + SPACE + "<" + INTERVAL_TYPE + ">" + " ." + "\n" +
      VAR + INTERVAL_NODE + SPACE + "<" + INTERVAL_TO_START + ">" + SPACE + VAR + START_NODE + " ." + "\n" +
      VAR + START_NODE + SPACE + "<" + TYPE + ">" + SPACE + "<" + DATE_TIME_VALUE_TYPE + ">" + " . " + "\n" +
      VAR + START_NODE + SPACE + "<" + DATE_TIME_PRECISION + ">" + SPACE + VAR + EXISTING_START_PRECISION + " . }";

  final static String existingEndNodeQuery  =
      "SELECT" + SPACE + VAR + EXISTING_END_NODE + " WHERE { " + "\n" +
      VAR + OB_CULTURAL_OBJECT + SPACE + "<" + TO_INTERVAL + ">" + SPACE + VAR + INTERVAL_NODE + " ." + "\n" +
      VAR + INTERVAL_NODE + SPACE + "<" + TYPE + ">" + SPACE + "<" + INTERVAL_TYPE + ">" + " ." + "\n" +
      VAR + INTERVAL_NODE + SPACE + "<" + INTERVAL_TO_END + ">" + SPACE + VAR + EXISTING_END_NODE + " . " + "\n" +
      VAR + EXISTING_END_NODE + SPACE + "<" + TYPE + ">" + SPACE + "<" + DATE_TIME_VALUE_TYPE + ">" + " .}";

  final static String existingEndDateQuery  =
      "SELECT" + SPACE + VAR + EXISTING_END_DATE + " WHERE {" + "\n" +
      VAR + OB_CULTURAL_OBJECT + SPACE + "<" + TO_INTERVAL + ">" + SPACE + VAR + INTERVAL_NODE + " ." + "\n" +
      VAR + INTERVAL_NODE + SPACE + "<" + TYPE + ">" + SPACE + "<" + INTERVAL_TYPE + ">" + " ." + "\n" +
      VAR + INTERVAL_NODE + SPACE + "<" + INTERVAL_TO_END + ">" + SPACE + VAR + END_NODE + " ." + "\n" +
      VAR + END_NODE + SPACE + "<" + TYPE + ">" + SPACE + "<" + DATE_TIME_VALUE_TYPE + ">" + " ." + "\n" +
      VAR + END_NODE + SPACE + "<" + DATE_TIME_VALUE + ">" + SPACE + VAR + EXISTING_END_DATE + " . }";

  final static String existingEndPrecisionQuery  =
      "SELECT" + SPACE + VAR + EXISTING_END_PRECISION + " WHERE {" + "\n" +
      VAR + OB_CULTURAL_OBJECT + SPACE + "<" + TO_INTERVAL + ">" + SPACE + VAR + INTERVAL_NODE + " ." + "\n" +
      VAR + INTERVAL_NODE + SPACE + "<" + TYPE + ">" + SPACE + "<" + INTERVAL_TYPE + ">" + " ." + "\n" +
      VAR + INTERVAL_NODE + SPACE + "<" + INTERVAL_TO_END + ">" + SPACE + VAR + END_NODE + " ." + "\n" +
      VAR + END_NODE + SPACE + "<" + TYPE + ">" + SPACE + "<" + DATE_TIME_VALUE_TYPE + ">" + " ." + "\n" +
      VAR + END_NODE + SPACE + "<" + DATE_TIME_PRECISION + ">" + SPACE + VAR + EXISTING_END_PRECISION + " . }";
  
  final static String n3ForNewTechnique  =
      "@prefix gesah:" + SPACE + "<" + GESAH + ">" + " ." + "\n" +
      VAR + OB_CULTURAL_OBJECT + SPACE + "<" + GESAH_USES_TECHNIQUE + ">" + SPACE + VAR + NEW_TECHNIQUE + " . " + "\n" +
      VAR + NEW_TECHNIQUE + SPACE + "<" + GESAH_USED_IN + ">" + SPACE + VAR + OB_CULTURAL_OBJECT + " . " + "\n" +
      VAR + NEW_TECHNIQUE + SPACE + "<" + LABEL + ">" + SPACE + VAR + TECHNIQUE_LABEL + " . " + "\n" +
      VAR + NEW_TECHNIQUE + " a" + SPACE + "<" + GESAH_TECHNIQUE + "> .";
	
  final static String n3ForExistingTechnique  =
      "@prefix gesah:" + SPACE + "<" + GESAH + ">" + " ." + "\n" +
      VAR + OB_CULTURAL_OBJECT + SPACE + "<" + GESAH_USES_TECHNIQUE + ">" + SPACE + VAR + EXISTING_TECHNIQUE + " . " + "\n" +
      VAR + EXISTING_TECHNIQUE + SPACE + "<" + GESAH_USED_IN + ">" + SPACE + VAR + OB_CULTURAL_OBJECT + " . " + "\n" +
      VAR + EXISTING_TECHNIQUE + " a" + SPACE + "<" + GESAH_TECHNIQUE + "> .";	
	
  final static String n3ForNewMaterial  =
      "@prefix gesah:" + SPACE + "<" + GESAH + ">" + " ." + "\n" +
      VAR + OB_CULTURAL_OBJECT + SPACE + "<" + GESAH_HAS_MATERIAL + ">" + SPACE + VAR + NEW_MATERIAL + " . " + "\n" +
      VAR + NEW_MATERIAL + SPACE + "<" + GESAH_INCORPORATED_IN + ">" + SPACE + VAR + OB_CULTURAL_OBJECT + " . " + "\n" +
      VAR + NEW_MATERIAL + SPACE + "<" + LABEL + ">" + SPACE + VAR + MATERIAL_LABEL + " . " + "\n" +
      VAR + NEW_MATERIAL + " a" + SPACE + "<" + GESAH_MATERIAL + "> .";
	
  final static String n3ForExistingMaterial  =
      "@prefix gesah:" + SPACE + "<" + GESAH + ">" + " ." + "\n" +
      VAR + OB_CULTURAL_OBJECT + SPACE + "<" + GESAH_HAS_MATERIAL + ">" + SPACE + VAR + EXISTING_MATERIAL + " . " + "\n" +
      VAR + EXISTING_MATERIAL + SPACE + "<" + GESAH_INCORPORATED_IN + ">" + SPACE + VAR + OB_CULTURAL_OBJECT + " . " + "\n" +
      VAR + EXISTING_MATERIAL + " a" + SPACE + "<" + GESAH_MATERIAL + "> .";

  final static String n3ForNewPlace  =
      "@prefix gesah:" + SPACE + "<" + GESAH + ">" + " ." + "\n" +
      VAR + OB_CULTURAL_OBJECT + SPACE + "<" + GESAH_HAS_PLACE + ">" + SPACE + VAR + NEW_PLACE + " . " + "\n" +
      VAR + NEW_PLACE + SPACE + "<" + GESAH_IS_PLACE_OF + ">" + SPACE + VAR + OB_CULTURAL_OBJECT + " . " + "\n" +
      VAR + NEW_PLACE + SPACE + "<" + LABEL + ">" + SPACE + VAR + PLACE_LABEL + " . " + "\n" +
      VAR + NEW_PLACE + " a <http://vivoweb.org/ontology/core#GeographicLocation> .";
	
  final static String n3ForExistingPlace  =
      "@prefix gesah:" + SPACE + "<" + GESAH + ">" + " ." + "\n" +
      VAR + OB_CULTURAL_OBJECT + SPACE + "<" + GESAH_HAS_PLACE + ">" + SPACE + VAR + EXISTING_PLACE + " . " + "\n" +
      VAR + EXISTING_PLACE + SPACE + "<" + GESAH_IS_PLACE_OF + ">" + SPACE + VAR + OB_CULTURAL_OBJECT + " . " + "\n" +
      VAR + EXISTING_PLACE + " a <http://vivoweb.org/ontology/core#GeographicLocation> .";
  
  final static String existingPlaceQuery =
      "SELECT " + SPACE + VAR + EXISTING_PLACE + " WHERE {" + "\n" +
      VAR + OB_CULTURAL_OBJECT + SPACE + "<" + GESAH_HAS_PLACE + ">" + SPACE + VAR + EXISTING_PLACE + "  . }";

  final static String existingPlaceLabelQuery =
      "SELECT Distinct" + SPACE + VAR + EXISTING_PLACE_LABEL + " WHERE {" + "\n" +
      VAR + OB_CULTURAL_OBJECT + SPACE + "<" + GESAH_HAS_PLACE + ">" + SPACE + VAR + EXISTING_PLACE + " . " + "\n" +
      VAR + EXISTING_PLACE + SPACE + "<" + LABEL + ">" + SPACE + VAR + EXISTING_PLACE_LABEL + " .}";	
  
  final static String existingTechniqueQuery =
      "SELECT" + SPACE + VAR + EXISTING_TECHNIQUE + " WHERE {" + "\n" +
      VAR + OB_CULTURAL_OBJECT + SPACE + "<" + GESAH_USES_TECHNIQUE + ">" + SPACE + VAR + EXISTING_TECHNIQUE + " . }";

	final static String existingTechniqueLabelQuery =
      "SELECT Distinct" + SPACE + VAR + EXISTING_TECHNIQUE_LABEL + " WHERE {" + "\n" +
      VAR + OB_CULTURAL_OBJECT + SPACE + "<" + GESAH_USES_TECHNIQUE + ">" + SPACE + VAR + EXISTING_TECHNIQUE + " . " + "\n" +
      VAR + EXISTING_TECHNIQUE + SPACE + "<" + LABEL + ">" + SPACE + VAR + EXISTING_TECHNIQUE_LABEL + " .}";

	final static String existingMaterialQuery =
      "SELECT" + SPACE + VAR + EXISTING_MATERIAL + " WHERE {" + "\n" +
      VAR + OB_CULTURAL_OBJECT + SPACE + "<" + GESAH_HAS_MATERIAL + ">" + SPACE + VAR + EXISTING_MATERIAL + "  . }";
	
  final static String existingMaterialLabelQuery =
      "SELECT Distinct" + SPACE + VAR + EXISTING_MATERIAL + "Label" + " WHERE {" + "\n" +
      VAR + OB_CULTURAL_OBJECT + SPACE + "<" + GESAH_HAS_MATERIAL + ">" + SPACE + VAR + EXISTING_MATERIAL + " . " + "\n" +
      VAR + EXISTING_MATERIAL + SPACE + "<" + LABEL + ">" + SPACE + VAR + EXISTING_MATERIAL + "Label" + " . }";	

  final static String existingAttrTypeQuery =
      "SELECT" + SPACE + VAR + EXISTING_ATTR_TYPE + " WHERE {" + "\n" +
      VAR + OB_CULTURAL_OBJECT + SPACE + "<" + GESAH_HAS_TYPE_OF_ATTRIBUTION + ">" + SPACE + VAR + EXISTING_ATTR_TYPE + " . }" + "\n";

  final static String existingAttrTypeLabelQuery =
      "SELECT Distinct" + SPACE + VAR + EXISTING_ATTR_TYPE_LABEL + " WHERE {" + "\n" +
      VAR + OB_CULTURAL_OBJECT + SPACE + "<" + GESAH_HAS_TYPE_OF_ATTRIBUTION + ">" + SPACE + VAR + EXISTING_ATTR_TYPE + " . " + "\n" +
      VAR + EXISTING_ATTR_TYPE + SPACE + "<" + LABEL + ">" + SPACE + VAR + EXISTING_ATTR_TYPE_LABEL + " .}";
}
