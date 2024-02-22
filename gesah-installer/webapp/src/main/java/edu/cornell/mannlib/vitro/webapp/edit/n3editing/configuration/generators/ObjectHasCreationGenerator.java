/* $This file is distributed under the terms of the license in LICENSE$ */
package edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import edu.cornell.mannlib.vitro.webapp.controller.VitroRequest;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.EditConfigurationVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.validators.AntiXssValidation;
import edu.cornell.mannlib.vitro.webapp.utils.FrontEndEditingUtils.EditMode;
import edu.cornell.mannlib.vitro.webapp.utils.generators.EditModeUtils;

public class ObjectHasCreationGenerator extends AbstractCulturalObjectGenerator implements EditConfigurationGenerator {
	private static final String GESAH_OUTPUT_OF_CREATION = GESAH + "output_of_creation";
	private static final String GESAH_CREATION = GESAH + "Creation";
	private static final String OUTPUT_OF_CREATION = "output_of_creation";
	private static final String CREATION_HAS_OUTPUT = "creationHasOutput";
	private static final String OBJECT_HAS_CREATION_FTL = "objectHasCreation.ftl";

	@Override
	protected void configureForm(VitroRequest vreq, HttpSession session, EditConfigurationVTwo conf) throws Exception {
        conf.setTemplate(OBJECT_HAS_CREATION_FTL);

        conf.setVarNameForSubject(CULT_OBJECT);
        conf.setVarNameForPredicate(PREDICATE);
        conf.setVarNameForObject(ACTIVITY_OBJ);
        
        addLitDateAppeal(conf);
        addExistingPlace(conf);
        addPlace(conf);
        addComment(conf);
        addAttributeType(conf);
        addExistingRoleType(conf);
        addNewActorRole(conf);		
        addNewActor(conf);	
        addExistingActor(conf);	
        addStartEndInterval(conf);
        addTechnique(conf);
        addMaterial(conf);
        
        conf.addN3Required( Arrays.asList(n3ForNewObCreation) );
        conf.addNewResource(ACTIVITY_OBJ, DEFAULT_NS_FOR_NEW_RESOURCE);
        conf.addSparqlForAdditionalUrisInScope(CREATION_HAS_OUTPUT, creationHasOutputQuery);
        conf.addValidator(new AntiXssValidation());
    }

	/* N3 assertions for creation of a cultural object */

    private final static String n3ForNewObCreation =
        VAR + CULT_OBJECT + " " + "<" + GESAH_OUTPUT_OF_CREATION + "> " + VAR + ACTIVITY_OBJ + " .\n" +
        VAR + ACTIVITY_OBJ + " a <" + GESAH_CREATION + "> . \n" +
        VAR + ACTIVITY_OBJ + " <" + GESAH + "has_creation_output> ?cultObject .";
		
    //Query for inverse property
    private final static String creationHasOutputQuery  =
    	  "PREFIX owl:  <http://www.w3.org/2002/07/owl#>" +
    	  " SELECT ?creationHasOutput " +
    	  " WHERE { ?creationHasOutput owl:inverseOf " + "<" + GESAH + OUTPUT_OF_CREATION + "> . } ";

	@Override
	protected EditMode getEditMode(EditConfigurationVTwo editConf, VitroRequest vreq) {
		List<String> predicates = new ArrayList<String>();
		predicates.add(GESAH + OUTPUT_OF_CREATION);
		return EditModeUtils.getEditMode(vreq, predicates);
	}
}
