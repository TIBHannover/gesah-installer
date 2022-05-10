/* $This file is distributed under the terms of the license in LICENSE$ */
package edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.jena.vocabulary.XSD;

import edu.cornell.mannlib.vitro.webapp.controller.VitroRequest;
import edu.cornell.mannlib.vitro.webapp.dao.VitroVocabulary;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.DateTimeIntervalValidationVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.DateTimeWithPrecisionVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.EditConfigurationVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.fields.ChildVClassesOptions;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.fields.ChildVClassesWithParent;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.fields.FieldVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.fields.IndividualsViaVClassOptions;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.validators.AntiXssValidation;
import edu.cornell.mannlib.vitro.webapp.utils.FrontEndEditingUtils.EditMode;
import edu.cornell.mannlib.vitro.webapp.utils.generators.EditModeUtils;
import static edu.cornell.mannlib.vitro.webapp.dao.VitroVocabulary.MOST_SPECIFIC_TYPE;

public class ObjectOfProvenance extends AbstractCulturalObjectGenerator implements EditConfigurationGenerator {
	private static final String GESAH_HAS_PROVENANCE_OBJECT = GESAH + "has_provenance_object";
	private static final String GESAH_OBJECT_OF_PROVENANCE = GESAH + "object_of_provenance";
	private static final String GESAH_PROVENANCE = GESAH + "Provenance";
	private static final String OUTPUT_OF_CREATION = "output_of_creation";
	private static final String PROVENCANCE_OUTPUT_OBJECT = "provenanceOutputObject";
	private static final String OBJECT_OF_PROVENANCE_FTL = "objectOfProvenance.ftl";
	private static final String PROVENANCE_TYPE = "provenanceType";
	private static final String PROVENANCE_CURRENT_TYPE = "provenanceCurrentType";

	@Override
	protected void configureForm(VitroRequest vreq, HttpSession session, EditConfigurationVTwo conf) throws Exception {
        conf.setTemplate(OBJECT_OF_PROVENANCE_FTL);

        conf.setVarNameForSubject(CULT_OBJECT);
        conf.setVarNameForPredicate(PREDICATE);
        conf.setVarNameForObject(ACTIVITY_OBJ);
        
        conf.addN3Required( Arrays.asList(n3ForNewProvenanceCreation) );
        
        conf.addNewResource(ACTIVITY_OBJ, DEFAULT_NS_FOR_NEW_RESOURCE);
        conf.addValidator(new AntiXssValidation());
        
        addProvinenceType(conf);
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

	private void addProvinenceType(EditConfigurationVTwo conf) {
    conf.addUrisOnForm( Arrays.asList( PROVENANCE_TYPE ));
    conf.addSparqlForExistingUris(PROVENANCE_TYPE, provenanceTypesQuery);
    conf.addSparqlForExistingUris(PROVENANCE_CURRENT_TYPE, existingProvenanceTypeQuery);

    conf.addField( new FieldVTwo().
        setName(PROVENANCE_TYPE).
        setValidators( list(NONEMPTY)).
        setOptions( new ChildVClassesOptions(
        		GESAH_PROVENANCE)));			
	}
	private final static String provenanceTypesQuery = 
				"SELECT DISTINCT ?provinenceTypes \n"
			+ "WHERE {\n"
			+ "  ?provinenceTypes <http://www.w3.org/2000/01/rdf-schema#subClassOf>* " + "<" + GESAH_PROVENANCE + "> .\n"
			+ "}";
	
	private final static String existingProvenanceTypeQuery = 
			  SELECT + SPACE + VAR + PROVENANCE_TYPE + " WHERE {" + SPACE   
			+ VAR + CULT_OBJECT + " " + "<" + GESAH_OBJECT_OF_PROVENANCE + "> " + VAR + ACTIVITY_OBJ + " ."
			+ VAR + ACTIVITY_OBJ + SPACE + "<" + MOST_SPECIFIC_TYPE + ">" + SPACE + VAR + PROVENANCE_TYPE + " . }"
			+ "LIMIT 1";

	/* N3 assertions for creation of a provenance object
	 * Requires NEW_ACTIVITY_ROLE, CULT_OBJECT and ACTIVITY_OBJ
	 *  */

    final static String n3ForNewProvenanceCreation =
        VAR + CULT_OBJECT + " " + "<" + GESAH_OBJECT_OF_PROVENANCE + "> " + VAR + ACTIVITY_OBJ + " .\n" +
        VAR + ACTIVITY_OBJ + " a " + VAR + PROVENANCE_TYPE + " . \n" +
        VAR + ACTIVITY_OBJ + " <" + GESAH_HAS_PROVENANCE_OBJECT + "> " +  VAR + CULT_OBJECT + " .";
		
    //VAR + ACTIVITY_OBJ + " <" + GESAH_REALIZES + "> " + VAR + NEW_ACTOR_ROLE + " . \n" +
    //VAR + NEW_ACTOR_ROLE + " <" + GESAH_REALIZED_IN + "> " + VAR + ACTIVITY_OBJ + " . \n" +
  
    @Override
	protected EditMode getEditMode(EditConfigurationVTwo editConf, VitroRequest vreq) {
		List<String> predicates = new ArrayList<String>();
		predicates.add(GESAH + OUTPUT_OF_CREATION);
		return EditModeUtils.getEditMode(vreq, predicates);
	}
}
