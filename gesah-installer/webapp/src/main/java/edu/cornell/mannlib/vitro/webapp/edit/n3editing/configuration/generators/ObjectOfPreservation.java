package edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators;

import static edu.cornell.mannlib.vitro.webapp.dao.VitroVocabulary.MOST_SPECIFIC_TYPE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import edu.cornell.mannlib.vitro.webapp.controller.VitroRequest;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.EditConfigurationVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.fields.ChildVClassesOptions;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.fields.FieldVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.fields.IndividualsViaVClassOptions;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.validators.AntiXssValidation;
import edu.cornell.mannlib.vitro.webapp.utils.FrontEndEditingUtils.EditMode;
import edu.cornell.mannlib.vitro.webapp.utils.generators.EditModeUtils;

public class ObjectOfPreservation extends AbstractCulturalObjectGenerator implements EditConfigurationGenerator {
	private static final String GESAH_IS_SPECIFICS_OF_MEASURE = GESAH + "is_specifics_of_measure";
	private static final String GESAH_HAS_MEASURE_SPECIFIC = GESAH + "has_measure_specifics";
	private static final String GESAH_PRESERVATION_MEASURE_SPECIFICS = GESAH + "Preservation_Measure_Specifics";
	private static final String GESAH_HAS_PRESERVATION_OBJECT = GESAH + "has_preservation_object";
	private static final String OBJECT_OF_PRESERVATION = "object_of_preservation";
	private static final String GESAH_OBJECT_OF_PRESERVATION = GESAH + OBJECT_OF_PRESERVATION;
	private static final String GESAH_PRESERVATION = GESAH + "Preservation_Activity";
	private static final String OBJECT_OF_PRESERVATION_FTL = "objectOfPreservation.ftl";
	private static final String PRESERVATION_TYPE = "preservationType";
	private static final String PRESERVATION_CURRENT_TYPE = "preservationCurrentType";
	private static final String TREATMENT_SPECIFICS = "treatmentSpecifics";
//	private static final String TREATMENT_SPECIFICS_CURRENT = "treatmentSpecificsCurrent";

	@Override
	protected void configureForm(VitroRequest vreq, HttpSession session, EditConfigurationVTwo conf) throws Exception {
        conf.setTemplate(OBJECT_OF_PRESERVATION_FTL);

        conf.setVarNameForSubject(CULT_OBJECT);
        conf.setVarNameForPredicate(PREDICATE);
        conf.setVarNameForObject(ACTIVITY_OBJ);
        
        conf.addN3Required( Arrays.asList(n3ForNewPreservationCreation) );
        
        conf.addNewResource(ACTIVITY_OBJ, DEFAULT_NS_FOR_NEW_RESOURCE);
        conf.addValidator(new AntiXssValidation());
        
        addPreservationType(conf);
        addTreatmentSpecifics(conf);
        addExistingActor(conf);	
        addNewActor(conf);	
        addExistingRoleType(conf);
        addNewActorRole(conf);

        addStartEndInterval(conf);
        addLitDateAppeal(conf);
        addComment(conf);
        addExistingPlace(conf);
        addPlace(conf);
    }

	private void addPreservationType(EditConfigurationVTwo conf) {
    conf.addUrisOnForm( Arrays.asList( PRESERVATION_TYPE ));
    
    conf.addSparqlForExistingUris(PRESERVATION_TYPE, preservationTypesQuery);
    conf.addSparqlForExistingUris(PRESERVATION_CURRENT_TYPE, existingPreservationTypeQuery);
    conf.addField( new FieldVTwo().
        setName(PRESERVATION_TYPE).
        setValidators( list(NONEMPTY)).
        setOptions( new ChildVClassesOptions(
        		GESAH_PRESERVATION)));			
	}
	
	private final static String preservationTypesQuery = 
			"SELECT DISTINCT ?preservationTypes "
		+ "WHERE {"
		+ "  ?preservationTypes <http://www.w3.org/2000/01/rdf-schema#subClassOf>* " + "<" + GESAH_PRESERVATION + "> ."
		+ "}";

  private final static String existingPreservationTypeQuery = 
  		  SELECT + SPACE + VAR + PRESERVATION_TYPE + " WHERE {" + SPACE   
  		+ VAR + CULT_OBJECT + " " + "<" + GESAH_OBJECT_OF_PRESERVATION + "> " + VAR + ACTIVITY_OBJ + " ."
  		+ VAR + ACTIVITY_OBJ + SPACE + "<" + MOST_SPECIFIC_TYPE + ">" + SPACE + VAR + PRESERVATION_TYPE + " . }"
  		+ "LIMIT 1";
  
  private final static String n3ForNewPreservationCreation =
        VAR + CULT_OBJECT + " " + "<" + GESAH_OBJECT_OF_PRESERVATION + "> " + VAR + ACTIVITY_OBJ + " ." +
        VAR + ACTIVITY_OBJ + " a " + VAR + PRESERVATION_TYPE + " . " +
        VAR + ACTIVITY_OBJ + " <" + GESAH_HAS_PRESERVATION_OBJECT + "> " +  VAR + CULT_OBJECT + " .";

	
	private void addTreatmentSpecifics(EditConfigurationVTwo conf) throws Exception {
    conf.addUrisOnForm( Arrays.asList( TREATMENT_SPECIFICS ));
    
    conf.addSparqlForExistingUris(TREATMENT_SPECIFICS, currentTreatmentSpecificQuery);
    conf.addN3Optional(n3ForTreatmentSpecifics);
    conf.addField( new FieldVTwo().
        setName(TREATMENT_SPECIFICS ).
        setValidators( list(NONEMPTY)).
        setOptions( new IndividualsViaVClassOptions(
        		GESAH_PRESERVATION_MEASURE_SPECIFICS)));			
	}
	
  private final static String n3ForTreatmentSpecifics = 
  		VAR + ACTIVITY_OBJ + SPACE + "<" + GESAH_HAS_MEASURE_SPECIFIC + ">" + SPACE + VAR + TREATMENT_SPECIFICS + " ." +
  		VAR + TREATMENT_SPECIFICS + SPACE + "<" + GESAH_IS_SPECIFICS_OF_MEASURE + ">" + SPACE + VAR + ACTIVITY_OBJ;

	private final static String currentTreatmentSpecificQuery = 
		  SELECT + SPACE + VAR + TREATMENT_SPECIFICS + " WHERE {" + SPACE   
		+ VAR + CULT_OBJECT + " " + "<" + GESAH_OBJECT_OF_PRESERVATION + "> " + VAR + ACTIVITY_OBJ + " ."
		+ VAR + ACTIVITY_OBJ + SPACE + "<" + GESAH_HAS_MEASURE_SPECIFIC + ">" + SPACE + VAR + TREATMENT_SPECIFICS + " . }"
		+ "LIMIT 1";
		
    @Override
	protected EditMode getEditMode(EditConfigurationVTwo editConf, VitroRequest vreq) {
		List<String> predicates = new ArrayList<String>();
		predicates.add(GESAH + OBJECT_OF_PRESERVATION);
		return EditModeUtils.getEditMode(vreq, predicates);
	}
}
