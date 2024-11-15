package edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators;

import java.util.Arrays;

import org.apache.jena.vocabulary.XSD;

import edu.cornell.mannlib.vitro.webapp.dao.VitroVocabulary;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.AutocompleteRequiredInputValidator;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.DateTimeIntervalValidationVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.DateTimeWithPrecisionVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.EditConfigurationVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.fields.ChildVClassesOptions;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.fields.FieldVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.fields.IndividualsViaVClassOptions;

public abstract class AbstractCulturalObjectGenerator extends GesahEditConfigurationGenerator {
    public static final String SPACE = " ";
    public static final String AGENT = "Agent";
    public final static String ACTOR_CLASS = FOAF + AGENT;
    protected static final String ACTOR_LABEL = "agentLabel";
    protected static final String ACTOR_LABEL_DISPLAY = "agentLabelDisplay";
    protected static final String ACTOR_TYPE = "agentType";
    protected static final String ATTR_TYPE_LABEL = "attrTypeLabel";
    public final static String ATTRIBUTION_TYPE_CLASS = GESAH + "Attribution_Type";

    public static final String COMMENT = "comment";
    public final static String COMMENT_PRED = GESAH + COMMENT;

    public static final String CULT_OBJECT = "cultObject";

    public static final String ACTIVITY_OBJ = "activityVar";

    public static final String DATATYPE = "datatype:";

    private static final String END_FIELD = "endField";
    private static final String END_FIELD_PRECISION = "endField-precision";
    private static final String END_FIELD_VALUE = "endField-value";

    private static final String END_NODE = "endNode";
    public static final String EXISTING_ACTOR = "existingAgent";
    public static final String EXISTING_AGENT_LABEL = "existingAgentLabel";
    protected static final String EXISTING_ATTR_TYPE = "existingAttrType";
    protected static final String EXISTING_ATTR_TYPE_LABEL = "existingAttrTypeLabel";
    public static final String EXISTING_COMMENT = "existingComment";
    private static final String EXISTING_COMMENT_VALUE = "existingCommentValue";

    public static final String EXISTING_DATE_START = "existingDateStart";
    public static final String EXISTING_END_DATE = "existingEndDate";
    public static final String EXISTING_END_NODE = "existingEndNode";

    public static final String EXISTING_END_PRECISION = "existingEndPrecision";
    public static final String EXISTING_INTERVAL_NODE = "existingIntervalNode";
    private static final String EXISTING_MATERIAL = "existingMaterial";
    private static final String EXISTING_PLACE = "existingPlace";

    public static final String EXISTING_PLACE_LABEL = "existingPlaceLabel";
    protected static final String EXISTING_ROLE = "existingRole";
    protected static final String EXISTING_ROLE_TYPE = "existingRoleType";
    public static final String EXISTING_ROLE_TYPE_LABEL = "existingRoleTypeLabel";
    public static final String EXISTING_START_NODE = "existingStartNode";
    public static final String EXISTING_START_PRECISION = "existingStartPrecision";
    private static final String EXISTING_TECHNIQUE = "existingTechnique";

    public static final String EXISTING_TECHNIQUE_LABEL = "existingTechniqueLabel";
    public static final String EXISTINGLIT_DATE_APPEL = "existinglitDateAppel";


    public static final String ATTRIBUTION_TYPE = GESAH + "Attribution_Type";
    public static final String GESAH_COMMENT = GESAH + "comment";

    public static final String GESAH_EDITION = GESAH + "Edition";
    public static final String GESAH_HAS_EDITION_OBJECT = GESAH + "has_edition_object";
    public static final String GESAH_HAS_MATERIAL = GESAH + "has_material";
    public static final String GESAH_HAS_PLACE = GESAH + "has_place";
    public static final String HAS_ROLE = GESAH + "has_role";
    public static final String HAS_ROLE_TYPE = GESAH + "has_role_type";
    public static final String HAS_TYPE_OF_ATTRIBUTION = GESAH + "has_type_of_attribution";
    public static final String GESAH_INCORPORATED_IN = GESAH + "incorporated_in";
    public static final String IS_ATTRIBUTION_TYPE_OF = GESAH + "is_attribution_type_of";
    public static final String GESAH_IS_PLACE_OF = GESAH + "is_place_of";
    public static final String IS_ROLE_OF = GESAH + "is_role_of";
    public static final String GESAH_LITERAL_DATE_APPELATION = GESAH + "literal_date_appellation";
    public static final String GESAH_MATERIAL = GESAH + "Material";
    public static final String GESAH_OBJECT_OF_PUBLICATION = GESAH + "object_of_publication";
    public static final String REALIZED_IN = GESAH + "realized_in";
    public static final String REALIZES = GESAH + "realizes";
    public static final String ROLE_TYPE = GESAH + "Role_Type";
    public static final String GESAH_TECHNIQUE = GESAH + "Technique";
    public static final String GESAH_USED_IN = GESAH + "used_in";
    public static final String GESAH_USES_TECHNIQUE = GESAH + "uses_technique";
    private static final String INTERVAL_NODE = "intervalNode";
    private static final String LIT_DATE_APPEL = "litDateAppel";
    public final static String LITERAL_DATE_APPEAL_PRED = GESAH + "literal_date_appellation";
    private static final String MATERIAL_LABEL = "materialLabel";
    public final static String MATERIAL_TYPE_CLASS = GESAH + "Material";
    public static final String NEW_ACTOR = "newAgent";
    protected static final String NEW_ATTR_TYPE = "newAttrType";
    private static final String NEW_MATERIAL = "newMaterial";
    private static final String NEW_PLACE = "newPlace";
    public static final String NEW_ROLE = "newRole";
    public static final String NEW_ACTIVITY_ROLE_TYPE = "newRoleType";
    protected static final String NEW_ROLE_TYPE_LABEL = "newRoleTypeLabel";
    private static final String NEW_TECHNIQUE = "newTechnique";

    public static final String NONEMPTY = "nonempty";
    private static final String PLACE_LABEL = "placeLabel";
    private static final String PLACE_LABEL_DISPLAY = "placeLabelDisplay";
    public final static String PLACE_TYPE_CLASS = VIVO_CORE + "GeographicLocation";
    public static final String PREDICATE = "predicate";
    public final static String ROLE_CLASS = OBO + "BFO_0000023";
    public final static String ROLE_TYPE_CLASS = GESAH + "Role_Type";
    protected static final String ACTIVITY_ROLE_TYPE_LABEL = "roleTypeLabel";
    private static final String START_FIELD = "startField";
    private static final String START_FIELD_PRECISION = "startField-precision";
    private static final String START_FIELD_VALUE = "startField-value";
    private static final String START_NODE = "startNode";
    private static final String TECHNIQUE_LABEL = "techniqueLabel";
    public final static String TECHNIQUE_TYPE_CLASS = GESAH + "Technique";
    public static final String VAR = "?";
    public static final String LINE_END = " . \n ";
    public static final String GT = "> ";
    public static final String LT = " <";
    public static final String WHERE = " WHERE { \n ";
    public static final String SELECT = "SELECT ";

    public static final String A = " a ";


    protected void addLitDateAppeal(EditConfigurationVTwo conf) {
        conf.addLiteralsOnForm( Arrays.asList(LIT_DATE_APPEL));
        conf.addSparqlForExistingLiteral(LIT_DATE_APPEL, litDateAppelQuery);
        conf.addN3Optional(Arrays.asList(litDateAppelAssertion));
        conf.addField( new FieldVTwo().
            setName(LIT_DATE_APPEL).
            setRangeDatatypeUri( XSD.xstring.toString() ).
            setValidators(list(DATATYPE + XSD.xstring.toString())));
    }

    private final static String litDateAppelQuery  =
      "SELECT" + SPACE + VAR + EXISTINGLIT_DATE_APPEL + " WHERE {" + "\n" +
      VAR + ACTIVITY_OBJ + SPACE + "<" + LITERAL_DATE_APPEAL_PRED + ">" + SPACE + VAR + EXISTINGLIT_DATE_APPEL + 
      " . }";

    private final static String litDateAppelAssertion  =
      VAR + ACTIVITY_OBJ + SPACE + "<" + GESAH_LITERAL_DATE_APPELATION + "> " + VAR + LIT_DATE_APPEL + " .";


    protected void addExistingPlace(EditConfigurationVTwo conf) throws Exception {
        conf.addUrisOnForm( Arrays.asList( EXISTING_PLACE ));
        conf.addN3Optional(Arrays.asList(n3ForExistingPlace));
        conf.addSparqlForExistingUris(EXISTING_PLACE, existingPlaceQuery);
        conf.addField( new FieldVTwo().
                setName(EXISTING_PLACE).
                setOptions( new IndividualsViaVClassOptions(
                        PLACE_TYPE_CLASS)));
    }

    private final static String n3ForExistingPlace  =
      "@prefix gesah:" + SPACE + "<" + GESAH + ">" + " ." + "\n" +
      VAR + ACTIVITY_OBJ + SPACE + "<" + GESAH_HAS_PLACE + ">" + SPACE + VAR + EXISTING_PLACE + " . " + "\n" +
      VAR + EXISTING_PLACE + SPACE + "<" + GESAH_IS_PLACE_OF + ">" + SPACE + VAR + ACTIVITY_OBJ + " . " ;

    private final static String existingPlaceQuery =
      "SELECT " + SPACE + VAR + EXISTING_PLACE + SPACE +
      " WHERE {" + "\n" +
          VAR + ACTIVITY_OBJ + SPACE + "<" + GESAH_HAS_PLACE + ">" + SPACE + VAR + EXISTING_PLACE + " ." + 
      "}";

    protected void addPlace(EditConfigurationVTwo conf) throws Exception {
        conf.addNewResource(NEW_PLACE,DEFAULT_NS_FOR_NEW_RESOURCE);
        conf.addN3Optional(Arrays.asList(n3ForNewPlace));
        conf.addField( new FieldVTwo().
                setName(NEW_PLACE).
                setOptions( new IndividualsViaVClassOptions(
                        PLACE_TYPE_CLASS)));

        conf.addLiteralsOnForm( Arrays.asList(PLACE_LABEL));
        conf.addLiteralsOnForm( Arrays.asList(PLACE_LABEL_DISPLAY));
        conf.addSparqlForExistingLiteral(PLACE_LABEL, existingPlaceLabelQuery);
        conf.addField( new FieldVTwo().
            setName(PLACE_LABEL_DISPLAY).
            setRangeDatatypeUri(XSD.xstring.toString() ));
        conf.addField( new FieldVTwo().
                setName(PLACE_LABEL).
                setRangeDatatypeUri(XSD.xstring.toString() ));
    }

    private final static String existingPlaceLabelQuery =
      "SELECT Distinct" + SPACE + VAR + EXISTING_PLACE_LABEL + " WHERE {" + "\n" +
      VAR + ACTIVITY_OBJ + SPACE + "<" + GESAH_HAS_PLACE + ">" + SPACE + VAR + EXISTING_PLACE + " . " + "\n" +
      VAR + EXISTING_PLACE + SPACE + "<" + LABEL + ">" + SPACE + VAR + EXISTING_PLACE_LABEL + " .}";

    private final static String n3ForNewPlace  =
      "@prefix gesah:" + SPACE + "<" + GESAH + ">" + " ." + "\n" +
      VAR + ACTIVITY_OBJ + SPACE + "<" + GESAH_HAS_PLACE + ">" + SPACE + VAR + NEW_PLACE + " .\n" +
      VAR + NEW_PLACE + SPACE + "<" + GESAH_IS_PLACE_OF + ">" + SPACE + VAR + ACTIVITY_OBJ + " .\n" +
      VAR + NEW_PLACE + SPACE + "<" + LABEL + ">" + SPACE + VAR + PLACE_LABEL + " .\n" +
      VAR + NEW_PLACE + " a <http://vivoweb.org/ontology/core#GeographicLocation> .";


    protected void addComment(EditConfigurationVTwo conf) {
        conf.addLiteralsOnForm(Arrays.asList(COMMENT));
        conf.addN3Optional(Arrays.asList(commentAssertion));
        conf.addSparqlForExistingLiteral(COMMENT, commentQuery);
        conf.addSparqlForExistingLiteral(EXISTING_COMMENT_VALUE, commentValueQuery);
        conf.addField(
                new FieldVTwo().setName(COMMENT).setRangeDatatypeUri(org.apache.jena.vocabulary.RDFS.Literal.getURI())
                        .setValidators(list(DATATYPE + XSD.xstring.toString())));
    }

    private final static String commentAssertion  =
      VAR + ACTIVITY_OBJ + SPACE + "<" + GESAH_COMMENT + ">" + SPACE + VAR + COMMENT + " .";

    private final static String commentQuery  =
      "SELECT" + SPACE + VAR + EXISTING_COMMENT + " WHERE {" + "\n" +
      VAR + ACTIVITY_OBJ + SPACE + "<" + COMMENT_PRED + ">" + SPACE + VAR + EXISTING_COMMENT + " . }";

    private final static String commentValueQuery  =
      "SELECT" + SPACE + " (STR(?existCommentColumn) as " + VAR + EXISTING_COMMENT_VALUE + " ) WHERE {" + "\n" +
      VAR + ACTIVITY_OBJ + SPACE + "<" + COMMENT_PRED + ">" + SPACE + VAR + "existCommentColumn" + " . }";


    protected void addAttributeType(EditConfigurationVTwo conf) throws Exception{
        conf.addN3Optional(Arrays.asList(n3ForNewAttrTypeNewActorRole));
        conf.addNewResource(NEW_ATTR_TYPE,DEFAULT_NS_FOR_NEW_RESOURCE);
        conf.addLiteralsOnForm( Arrays.asList(EXISTING_ATTR_TYPE_LABEL));
        conf.addField( new FieldVTwo().
        setName(EXISTING_ATTR_TYPE_LABEL).
        setRangeDatatypeUri(XSD.xstring.toString() ).
        setValidators( list(DATATYPE + XSD.xstring.toString())));
        conf.addN3Optional(Arrays.asList(n3ForNewActorRoleExistingAttrType));
        conf.addUrisOnForm(Arrays.asList(EXISTING_ATTR_TYPE));

        conf.addField( new FieldVTwo().
                setName(EXISTING_ATTR_TYPE).
                setOptions( new IndividualsViaVClassOptions(
                        ATTRIBUTION_TYPE_CLASS)));
    }

    private final static String n3ForNewActorRoleExistingAttrType =
      "@prefix gesah:" + SPACE + "<" + GESAH + ">" + " ." + "\n" +
      VAR + NEW_ROLE + SPACE + "<" + HAS_TYPE_OF_ATTRIBUTION + ">" + SPACE + VAR + EXISTING_ATTR_TYPE + " . " + "\n" +
      VAR + NEW_ROLE + SPACE + " a " + SPACE + "<" + ROLE_CLASS + "> .\n" +
      VAR + EXISTING_ATTR_TYPE + SPACE + "<" + IS_ATTRIBUTION_TYPE_OF + ">" + SPACE + VAR + NEW_ROLE + " . ";


    private final static String n3ForNewAttrTypeNewActorRole  =
      "@prefix gesah:" + SPACE + "<" + GESAH + ">" + " ." + "\n" +
      VAR + NEW_ROLE + SPACE + "<" + HAS_TYPE_OF_ATTRIBUTION + ">" + SPACE + VAR + NEW_ATTR_TYPE + " . " + "\n" +
      VAR + NEW_ATTR_TYPE + SPACE + "<" + IS_ATTRIBUTION_TYPE_OF + ">" + SPACE + VAR + NEW_ROLE + " . " + "\n" +
      VAR + NEW_ROLE + SPACE + " a " + SPACE + "<" + ROLE_CLASS + "> .\n" +
      VAR + NEW_ATTR_TYPE + SPACE + "<" + LABEL + ">" + SPACE + VAR + ATTR_TYPE_LABEL + " . " + "\n" +
      VAR + NEW_ATTR_TYPE + " a " + "<" + ATTRIBUTION_TYPE + "> .";

    protected void addExistingRoleType(EditConfigurationVTwo conf) throws Exception {
        addOptionalExistingRoleType(conf, false);
    }

    protected void addOptionalExistingRoleType(EditConfigurationVTwo conf, boolean addValidators) throws Exception {
        conf.addN3Optional(Arrays.asList(n3ForExistingRoleType));
        conf.addUrisOnForm(Arrays.asList(EXISTING_ROLE_TYPE));
        FieldVTwo existingRoleTypeField = new FieldVTwo().setName(EXISTING_ROLE_TYPE)
                .setOptions(new IndividualsViaVClassOptions(ROLE_TYPE_CLASS));
        conf.addField(existingRoleTypeField);
        if (addValidators) {
            existingRoleTypeField.setValidators(list(DATATYPE + XSD.xstring.toString(), NONEMPTY));
        }
    }

    private final static String n3ForExistingRoleType =
        VAR + ACTIVITY_OBJ + SPACE + "<" + REALIZES + ">" + SPACE + VAR + NEW_ROLE + " . " + "\n" +
        VAR + NEW_ROLE + SPACE + " a " + SPACE + "<" + ROLE_CLASS + "> .\n" +
        VAR + NEW_ROLE + SPACE + "<" + REALIZED_IN + ">" + SPACE + VAR + ACTIVITY_OBJ + " . " + "\n" +
        VAR + NEW_ROLE + SPACE + "<" + HAS_ROLE_TYPE + ">" + SPACE + VAR + EXISTING_ROLE_TYPE + " . ";

    protected void addNewActorRole(EditConfigurationVTwo conf) throws Exception {
        addNewOptionalActorRole(conf, false);
    }

    protected void addNewOptionalActorRole(EditConfigurationVTwo conf, boolean addValidators) throws Exception {
        conf.addNewResource(NEW_ROLE, DEFAULT_NS_FOR_NEW_RESOURCE);
        conf.addNewResource(NEW_ACTIVITY_ROLE_TYPE, DEFAULT_NS_FOR_NEW_RESOURCE);
        conf.addN3Optional(Arrays.asList(n3ForNewActivityRoleType));
        conf.addField(new FieldVTwo().setName(NEW_ROLE).setOptions(new IndividualsViaVClassOptions(ROLE_CLASS)));
        conf.addLiteralsOnForm(Arrays.asList(ACTIVITY_ROLE_TYPE_LABEL));
        FieldVTwo activityRoleTypeField = new FieldVTwo().setName(ACTIVITY_ROLE_TYPE_LABEL).setRangeDatatypeUri(XSD.xstring.toString());
        conf.addField(activityRoleTypeField);
        if (addValidators) {
            activityRoleTypeField.setValidators(list(DATATYPE + XSD.xstring.toString()));
        }
    }

    private final static String n3ForNewActivityRoleType  =
            "@prefix rdfs:" + SPACE + "<" + RDFS + ">" + "  . " + "\n" +
      VAR + ACTIVITY_OBJ + SPACE + "<" + REALIZES + ">" + SPACE + VAR + NEW_ROLE + " . " + "\n" +
      VAR + NEW_ROLE + SPACE + "<" + REALIZED_IN + ">" + SPACE + VAR + ACTIVITY_OBJ + " . " + "\n" +
      VAR + NEW_ROLE + SPACE + "<" + HAS_ROLE_TYPE + ">" + SPACE + VAR + NEW_ACTIVITY_ROLE_TYPE + " . " + "\n" +
      VAR + NEW_ACTIVITY_ROLE_TYPE + SPACE + "<" + LABEL + ">" + SPACE + VAR + NEW_ROLE_TYPE_LABEL + " . " + "\n" +
      VAR + NEW_ACTIVITY_ROLE_TYPE + " a " + "<" + ROLE_TYPE + "> . " ;

    protected void addNewActor(EditConfigurationVTwo conf) throws Exception {
        addNewOptionalActor(conf, true);
    }

    protected void addNewOptionalActor(EditConfigurationVTwo conf, boolean addValidators) throws Exception {
        conf.addN3Optional(Arrays.asList(n3ForNewActor));
        conf.addNewResource(NEW_ACTOR, DEFAULT_NS_FOR_NEW_RESOURCE);
        conf.addField(new FieldVTwo().setName(NEW_ACTOR).setOptions(new IndividualsViaVClassOptions(ACTOR_CLASS)));

        conf.addUrisOnForm(Arrays.asList(ACTOR_TYPE));
        conf.addLiteralsOnForm(Arrays.asList(ACTOR_LABEL));
        conf.addLiteralsOnForm(Arrays.asList(ACTOR_LABEL_DISPLAY));
        
        FieldVTwo actorTypeField = new FieldVTwo().setName(ACTOR_TYPE);
        actorTypeField.setOptions(new ChildVClassesOptions(ACTOR_CLASS));
        conf.addField(actorTypeField);
        
        FieldVTwo actorLabelField = new FieldVTwo().setName(ACTOR_LABEL).setRangeDatatypeUri(XSD.xstring.toString());
        conf.addField(actorLabelField);
        conf.addField(new FieldVTwo().setName(ACTOR_LABEL_DISPLAY).setRangeDatatypeUri(XSD.xstring.toString()));
        
        if (addValidators) {
            actorTypeField.setValidators(list(DATATYPE + XSD.xstring.toString(),NONEMPTY));
            actorLabelField.setValidators(list(DATATYPE + XSD.xstring.toString()));
            conf.addValidator(new AutocompleteRequiredInputValidator(EXISTING_ACTOR, ACTOR_LABEL));    
        }
    }

  //Should work only if participant wasn't selected
    private final static String n3ForNewActor  =
      "@prefix rdfs:" + SPACE + "<" + RDFS + ">" + " ." + "\n" +
      VAR + ACTIVITY_OBJ + SPACE + "<" + REALIZES + ">" + SPACE + VAR + NEW_ROLE + " . " + "\n" +
      VAR + NEW_ACTOR + SPACE + "<" + HAS_ROLE + ">" + SPACE + VAR + NEW_ROLE + " . " + "\n" +
      VAR + NEW_ROLE + SPACE + "<" + IS_ROLE_OF + ">" + SPACE + VAR + NEW_ACTOR + " . " + "\n" +
      VAR + NEW_ROLE + SPACE + " a " + SPACE + "<" + ROLE_CLASS + "> .\n" +
      VAR + NEW_ROLE + SPACE + "<" + REALIZED_IN + ">" + SPACE + VAR + ACTIVITY_OBJ + " . " + "\n" +
      VAR + NEW_ACTOR + " a " + VAR + ACTOR_TYPE + " . " + "\n" +
      VAR + ACTOR_TYPE + " rdfs:subClassOf <" + ACTOR_CLASS + "> ." + "\n" +
      VAR + NEW_ACTOR + " rdfs:label " + VAR + ACTOR_LABEL + " . ";

    protected void addExistingActor(EditConfigurationVTwo conf) throws Exception {
        conf.addN3Optional(Arrays.asList(n3ForExistingActor));
        conf.addUrisOnForm(Arrays.asList(EXISTING_ACTOR));
        conf.addField( new FieldVTwo().
                setName(EXISTING_ACTOR).
                setOptions( new IndividualsViaVClassOptions(
                        ACTOR_CLASS)));
    }

    private static final String n3ForExistingActor  =
          "@prefix rdfs:" + SPACE + "<" + RDFS +">" + SPACE + " . \n" +
      VAR + ACTIVITY_OBJ + SPACE + "<" + REALIZES + ">" + SPACE + VAR + NEW_ROLE + " . \n" +
      VAR + EXISTING_ACTOR +SPACE + "<" + HAS_ROLE + ">" + SPACE + VAR + NEW_ROLE + " . \n" +
      VAR + NEW_ROLE +SPACE + "<" + REALIZED_IN + ">" + SPACE + VAR + ACTIVITY_OBJ + " . \n" +
      VAR + NEW_ROLE + SPACE + " a " + SPACE + "<" + ROLE_CLASS + "> .\n" +
      VAR + NEW_ROLE +SPACE + "<" + IS_ROLE_OF + ">" + SPACE + VAR + EXISTING_ACTOR + " . " ;


    protected void addStartEndInterval(EditConfigurationVTwo conf) {
        conf.addN3Optional(Arrays.asList(n3ForStart));
        conf.addN3Optional(Arrays.asList(n3ForEnd));
        conf.addSparqlForExistingUris(INTERVAL_NODE,existingIntervalNodeQuery);
        conf.addSparqlForExistingUris(START_NODE, existingStartNodeQuery);
        conf.addSparqlForExistingUris(END_NODE, existingEndNodeQuery);
        conf.addNewResource(INTERVAL_NODE,DEFAULT_NS_FOR_NEW_RESOURCE);
        conf.addNewResource(START_NODE,DEFAULT_NS_FOR_NEW_RESOURCE);
        conf.addNewResource(END_NODE,DEFAULT_NS_FOR_NEW_RESOURCE);
        conf.addSparqlForExistingLiteral(START_FIELD_VALUE, existingStartDateQuery);
        conf.addSparqlForExistingLiteral(END_FIELD_VALUE, existingEndDateQuery);
        conf.addSparqlForExistingUris(START_FIELD_PRECISION, existingStartPrecisionQuery);
        conf.addSparqlForExistingUris(END_FIELD_PRECISION, existingEndPrecisionQuery);
        FieldVTwo startField = new FieldVTwo().setName(START_FIELD);
        conf.addField(startField.
                    setEditElement(
                  new DateTimeWithPrecisionVTwo(startField,
                    VitroVocabulary.Precision.YEAR.uri(),
                    VitroVocabulary.Precision.NONE.uri())));

        FieldVTwo endField = new FieldVTwo().setName(END_FIELD);
        conf.addField( endField.
                     setEditElement(
                         new DateTimeWithPrecisionVTwo(endField,
                    VitroVocabulary.Precision.YEAR.uri(),
                    VitroVocabulary.Precision.NONE.uri())));
        //Add validator
        conf.addValidator(new DateTimeIntervalValidationVTwo(START_FIELD,END_FIELD));
    }

    private final static String existingStartPrecisionQuery  =
      "SELECT" + SPACE + VAR + EXISTING_START_PRECISION + " WHERE {" + "\n" +
      VAR + ACTIVITY_OBJ + SPACE + "<" + TO_INTERVAL + ">" + SPACE + VAR + INTERVAL_NODE + " ." + "\n" +
      VAR + INTERVAL_NODE + SPACE + "<" + TYPE + ">" + SPACE + "<" + INTERVAL_TYPE + ">" + " ." + "\n" +
      VAR + INTERVAL_NODE + SPACE + "<" + INTERVAL_TO_START + ">" + SPACE + VAR + START_NODE + " ." + "\n" +
      VAR + START_NODE + SPACE + "<" + TYPE + ">" + SPACE + "<" + DATE_TIME_VALUE_TYPE + ">" + " . " + "\n" +
      VAR + START_NODE + SPACE + "<" + DATE_TIME_PRECISION + ">" + SPACE + VAR + EXISTING_START_PRECISION + " . }";

    private final static String existingEndNodeQuery  =
      "SELECT" + SPACE + VAR + EXISTING_END_NODE + " WHERE { " + "\n" +
      VAR + ACTIVITY_OBJ + SPACE + "<" + TO_INTERVAL + ">" + SPACE + VAR + INTERVAL_NODE + " ." + "\n" +
      VAR + INTERVAL_NODE + SPACE + "<" + TYPE + ">" + SPACE + "<" + INTERVAL_TYPE + ">" + " ." + "\n" +
      VAR + INTERVAL_NODE + SPACE + "<" + INTERVAL_TO_END + ">" + SPACE + VAR + EXISTING_END_NODE + " . " + "\n" +
      VAR + EXISTING_END_NODE + SPACE + "<" + TYPE + ">" + SPACE + "<" + DATE_TIME_VALUE_TYPE + ">" + " .}";

    private final static String existingEndDateQuery  =
      "SELECT" + SPACE + VAR + EXISTING_END_DATE + " WHERE {" + "\n" +
      VAR + ACTIVITY_OBJ + SPACE + "<" + TO_INTERVAL + ">" + SPACE + VAR + INTERVAL_NODE + " ." + "\n" +
      VAR + INTERVAL_NODE + SPACE + "<" + TYPE + ">" + SPACE + "<" + INTERVAL_TYPE + ">" + " ." + "\n" +
      VAR + INTERVAL_NODE + SPACE + "<" + INTERVAL_TO_END + ">" + SPACE + VAR + END_NODE + " ." + "\n" +
      VAR + END_NODE + SPACE + "<" + TYPE + ">" + SPACE + "<" + DATE_TIME_VALUE_TYPE + ">" + " ." + "\n" +
      VAR + END_NODE + SPACE + "<" + DATE_TIME_VALUE + ">" + SPACE + VAR + EXISTING_END_DATE + " . }";

    private final static String existingEndPrecisionQuery  =
      "SELECT" + SPACE + VAR + EXISTING_END_PRECISION + " WHERE {" + "\n" +
      VAR + ACTIVITY_OBJ + SPACE + "<" + TO_INTERVAL + ">" + SPACE + VAR + INTERVAL_NODE + " ." + "\n" +
      VAR + INTERVAL_NODE + SPACE + "<" + TYPE + ">" + SPACE + "<" + INTERVAL_TYPE + ">" + " ." + "\n" +
      VAR + INTERVAL_NODE + SPACE + "<" + INTERVAL_TO_END + ">" + SPACE + VAR + END_NODE + " ." + "\n" +
      VAR + END_NODE + SPACE + "<" + TYPE + ">" + SPACE + "<" + DATE_TIME_VALUE_TYPE + ">" + " ." + "\n" +
      VAR + END_NODE + SPACE + "<" + DATE_TIME_PRECISION + ">" + SPACE + VAR + EXISTING_END_PRECISION + " . }";

    private final static String existingIntervalNodeQuery  =
      "SELECT" + SPACE + VAR + EXISTING_INTERVAL_NODE + " WHERE {" + "\n" +
      VAR + ACTIVITY_OBJ + SPACE + "<" + TO_INTERVAL + ">" + SPACE + VAR + EXISTING_INTERVAL_NODE + " ." + "\n" +
      VAR + EXISTING_INTERVAL_NODE + SPACE + "<" + TYPE + ">" + SPACE + "<" + INTERVAL_TYPE + ">" + " . }";

    private final static String existingStartNodeQuery  =
      "SELECT" + SPACE + VAR + EXISTING_START_NODE + " WHERE {" + "\n" +
      VAR + ACTIVITY_OBJ + SPACE + "<" + TO_INTERVAL + ">" + SPACE + VAR + INTERVAL_NODE + " ." + "\n" +
      VAR + INTERVAL_NODE + SPACE + "<" + TYPE + ">" + SPACE + "<" + INTERVAL_TYPE + ">" + " ." + "\n" +
      VAR + INTERVAL_NODE + SPACE + "<" + INTERVAL_TO_START + ">" + SPACE + VAR + EXISTING_START_NODE + " . " + "\n" +
      VAR + EXISTING_START_NODE + SPACE + "<" + TYPE + ">" + SPACE + "<" + DATE_TIME_VALUE_TYPE + ">" + " .}";

    private final static String n3ForStart =
      VAR + ACTIVITY_OBJ + SPACE + "<" + TO_INTERVAL + ">" + SPACE + VAR + INTERVAL_NODE + " ." + "\n" +
      VAR + INTERVAL_NODE + SPACE + SPACE + "<" + TYPE + ">" + SPACE + "<" + INTERVAL_TYPE + ">" + " ." + "\n" +
      VAR + INTERVAL_NODE + SPACE + "<" + INTERVAL_TO_START + ">" + SPACE + VAR + START_NODE + " ." + "\n" +
      VAR + START_NODE + SPACE + SPACE + "<" + TYPE + ">" + SPACE + "<" + DATE_TIME_VALUE_TYPE + ">" + " ." + "\n" +
      VAR + START_NODE + SPACE + SPACE + "<" + DATE_TIME_VALUE + ">" + SPACE + VAR + START_FIELD_VALUE + "." + "\n" +
      VAR + START_NODE + SPACE + SPACE + "<" + DATE_TIME_PRECISION + ">" + SPACE + VAR + START_FIELD_PRECISION + " .";

    private final static String n3ForEnd =
      VAR + ACTIVITY_OBJ + SPACE + SPACE + "<" + TO_INTERVAL + ">" + SPACE + VAR + INTERVAL_NODE + " . " + "\n" +
      VAR + INTERVAL_NODE + SPACE + SPACE + "<" + TYPE + ">" + SPACE + "<" + INTERVAL_TYPE + ">" + " ." + "\n" +
      VAR + INTERVAL_NODE + SPACE + "<" + INTERVAL_TO_END + ">" + SPACE + VAR + END_NODE + " ." + "\n" +
      VAR + END_NODE + SPACE + SPACE + "<" + TYPE + ">" + SPACE + "<" + DATE_TIME_VALUE_TYPE + ">" + " ." + "\n" +
      VAR + END_NODE + SPACE + SPACE + "<" + DATE_TIME_VALUE + ">" + SPACE + VAR + END_FIELD_VALUE + " ." + "\n" +
      VAR + END_NODE + SPACE + SPACE + "<" + DATE_TIME_PRECISION + ">" + SPACE + VAR + END_FIELD_PRECISION + " .";

  final static String existingStartDateQuery  =
      "SELECT" + SPACE + VAR + EXISTING_DATE_START + " WHERE {" + "\n" +
      VAR + ACTIVITY_OBJ + SPACE + "<" + TO_INTERVAL + ">" + SPACE + VAR + INTERVAL_NODE + " ." + "\n" +
      VAR + INTERVAL_NODE + SPACE + "<" + TYPE + ">" + SPACE + "<" + INTERVAL_TYPE + ">" + " ." + "\n" +
      VAR + INTERVAL_NODE + SPACE + "<" + INTERVAL_TO_START + ">" + SPACE + VAR + START_NODE + " ." + "\n" +
      VAR + START_NODE + SPACE + "<" + TYPE + ">" + SPACE + "<" + DATE_TIME_VALUE_TYPE + ">" + " ." + "\n" +
      VAR + START_NODE + SPACE + "<" + DATE_TIME_VALUE + ">" + SPACE + VAR + EXISTING_DATE_START + " . }";

    protected void addTechnique(EditConfigurationVTwo conf) throws Exception {
        conf.addN3Optional(Arrays.asList(n3ForNewTechnique));
        conf.addNewResource(NEW_TECHNIQUE,DEFAULT_NS_FOR_NEW_RESOURCE);
        conf.addLiteralsOnForm(Arrays.asList(TECHNIQUE_LABEL));
        conf.addField( new FieldVTwo().
            setName(TECHNIQUE_LABEL).
            setRangeDatatypeUri(XSD.xstring.toString() ).
            setValidators( list(DATATYPE + XSD.xstring.toString())));
        conf.addN3Optional(Arrays.asList(n3ForExistingTechnique));
        conf.addUrisOnForm(Arrays.asList(EXISTING_TECHNIQUE));
        conf.addField( new FieldVTwo().
            setName(EXISTING_TECHNIQUE).
            setOptions( new IndividualsViaVClassOptions(
                    TECHNIQUE_TYPE_CLASS)));
    }
    
    private final static String n3ForNewTechnique  =
      "@prefix gesah:" + SPACE + "<" + GESAH + ">" + " ." + "\n" +
      VAR + ACTIVITY_OBJ + SPACE + "<" + GESAH_USES_TECHNIQUE + ">" + SPACE + VAR + NEW_TECHNIQUE + " . " + "\n" +
      VAR + NEW_TECHNIQUE + SPACE + "<" + GESAH_USED_IN + ">" + SPACE + VAR + ACTIVITY_OBJ + " . " + "\n" +
      VAR + NEW_TECHNIQUE + SPACE + "<" + LABEL + ">" + SPACE + VAR + TECHNIQUE_LABEL + " . " + "\n" +
      VAR + NEW_TECHNIQUE + " a" + SPACE + "<" + GESAH_TECHNIQUE + "> .";

    private final static String n3ForExistingTechnique  =
      "@prefix gesah:" + SPACE + "<" + GESAH + ">" + " ." + "\n" +
      VAR + ACTIVITY_OBJ + SPACE + "<" + GESAH_USES_TECHNIQUE + ">" + SPACE + VAR + EXISTING_TECHNIQUE + " . " + "\n" +
      VAR + EXISTING_TECHNIQUE + SPACE + "<" + GESAH_USED_IN + ">" + SPACE + VAR + ACTIVITY_OBJ + " . " + "\n" ;

    protected void addMaterial(EditConfigurationVTwo conf) throws Exception {
        conf.addN3Optional(Arrays.asList(n3ForNewMaterial));
        conf.addN3Optional(Arrays.asList(n3ForExistingMaterial));
        conf.addNewResource(NEW_MATERIAL,DEFAULT_NS_FOR_NEW_RESOURCE);
        conf.addUrisOnForm(Arrays.asList(EXISTING_MATERIAL));
        conf.addSparqlForExistingUris(EXISTING_MATERIAL, existingMaterialQuery);
        conf.addSparqlForExistingLiteral(MATERIAL_LABEL, existingMaterialLabelQuery);
        conf.addField( new FieldVTwo().
            setName(EXISTING_MATERIAL).
            //setValidators( list(NONEMPTY)).
            setOptions( new IndividualsViaVClassOptions(
                    MATERIAL_TYPE_CLASS)));
        conf.addLiteralsOnForm( Arrays.asList(MATERIAL_LABEL));
        conf.addField( new FieldVTwo().
            setName(MATERIAL_LABEL).
            setRangeDatatypeUri(XSD.xstring.toString() ).
            setValidators( list(DATATYPE + XSD.xstring.toString())));
    }

    final static String existingMaterialQuery =
        "SELECT" + SPACE + VAR + EXISTING_MATERIAL + " WHERE {" + "\n" +
        VAR + ACTIVITY_OBJ + SPACE + "<" + GESAH_HAS_MATERIAL + ">" + SPACE + VAR + EXISTING_MATERIAL + "  . }";


    private final static String n3ForNewMaterial  =
        "@prefix gesah:" + SPACE + "<" + GESAH + ">" + " ." + "\n" +
        VAR + ACTIVITY_OBJ + SPACE + "<" + GESAH_HAS_MATERIAL + ">" + SPACE + VAR + NEW_MATERIAL + " . " + "\n" +
        VAR + NEW_MATERIAL + SPACE + "<" + GESAH_INCORPORATED_IN + ">" + SPACE + VAR + ACTIVITY_OBJ + " . " + "\n" +
        VAR + NEW_MATERIAL + SPACE + "<" + LABEL + ">" + SPACE + VAR + MATERIAL_LABEL + " . " + "\n" +
        VAR + NEW_MATERIAL + " a" + SPACE + "<" + GESAH_MATERIAL + "> .";

    private final static String n3ForExistingMaterial  =
        "@prefix gesah:" + SPACE + "<" + GESAH + ">" + " ." + "\n" +
        VAR + ACTIVITY_OBJ + SPACE + "<" + GESAH_HAS_MATERIAL + ">" + SPACE + VAR + EXISTING_MATERIAL + " . " + "\n" +
        VAR + EXISTING_MATERIAL + SPACE + "<" + GESAH_INCORPORATED_IN + ">" + SPACE + VAR + ACTIVITY_OBJ + " . " + "\n" ;

    private final static String existingMaterialLabelQuery =
        "SELECT Distinct" + SPACE + VAR + EXISTING_MATERIAL + "Label" + " WHERE {" + "\n" +
        VAR + ACTIVITY_OBJ + SPACE + "<" + GESAH_HAS_MATERIAL + ">" + SPACE + VAR + EXISTING_MATERIAL + " . " + "\n" +
        VAR + EXISTING_MATERIAL + SPACE + "<" + LABEL + ">" + SPACE + VAR + EXISTING_MATERIAL + "Label" + " . }";

}
