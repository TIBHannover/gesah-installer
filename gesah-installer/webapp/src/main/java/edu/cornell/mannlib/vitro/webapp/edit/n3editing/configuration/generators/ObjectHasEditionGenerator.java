/* $This file is distributed under the terms of the license in LICENSE$ */
package edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import edu.cornell.mannlib.vitro.webapp.controller.VitroRequest;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.EditConfigurationUtils;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.EditConfigurationVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.validators.AntiXssValidation;
import edu.cornell.mannlib.vitro.webapp.utils.FrontEndEditingUtils.EditMode;
import edu.cornell.mannlib.vitro.webapp.utils.generators.EditModeUtils;
import org.apache.commons.lang3.StringUtils;

public class ObjectHasEditionGenerator extends AbstractCulturalObjectGenerator implements EditConfigurationGenerator{

	private static final String OBJECT_OF_PUBLICATION = "object_of_publication";
	public static final String EDITION_HAS_OUTPUT = "editionHasOutput";
	private static final String OBJECT_HAS_EDITION_FTL = "objectHasEdition.ftl";


	@Override
	protected void configureForm(VitroRequest vreq, HttpSession session, EditConfigurationVTwo conf) throws Exception {
        conf.setTemplate(OBJECT_HAS_EDITION_FTL);

        conf.setVarNameForSubject(CULT_OBJECT);
        conf.setVarNameForPredicate(PREDICATE);
        conf.setVarNameForObject(ACTIVITY_OBJ);
        
        addLitDateAppeal(conf);		
        addExistingPlace(conf);
        addPlace(conf);
        addComment(conf);
        String objectUri = EditConfigurationUtils.getObjectUri(vreq);
        if (StringUtils.isBlank(objectUri)) {
            addAttributeType(conf);
            addExistingRoleType(conf);
            addNewActorRole(conf);		
            addNewActor(conf);	
            addExistingActor(conf);	
        }
        addStartEndInterval(conf);
        if (StringUtils.isBlank(objectUri)) {
            addTechnique(conf);
        }
        addMaterial(conf);	

        conf.addN3Required( Arrays.asList(n3ForNewObEdition) );
        conf.addNewResource(ACTIVITY_OBJ, DEFAULT_NS_FOR_NEW_RESOURCE);
        conf.addSparqlForAdditionalUrisInScope(EDITION_HAS_OUTPUT, editionHasOutputQuery);
        conf.addValidator(new AntiXssValidation());
    }

	/* N3 assertions for production of a cultural object */

    private final static String n3ForNewObEdition =
		VAR + CULT_OBJECT + " " + "<" + GESAH_OBJECT_OF_PUBLICATION + "> " + " " + VAR + ACTIVITY_OBJ + " ." + "\n" +
        VAR + ACTIVITY_OBJ + "  a" + " " + "<" + GESAH_EDITION + "> ." + "\n" +
        VAR + ACTIVITY_OBJ + " " + "<" + GESAH_HAS_EDITION_OBJECT + "> " + VAR + CULT_OBJECT + " .";
	
    //Query for inverse property
    private final static String editionHasOutputQuery  =
    	  "PREFIX owl:  <http://www.w3.org/2002/07/owl#" + ">" +
    	  " SELECT" + " " + VAR + EDITION_HAS_OUTPUT + " " +
			  " WHERE {" + " " + VAR + EDITION_HAS_OUTPUT + " owl:inverseOf " + "<" + GESAH + OBJECT_OF_PUBLICATION + ">" + ". } ";

    @Override
		protected EditMode getEditMode(EditConfigurationVTwo editConf, VitroRequest vreq) {
    	List<String> predicates = new ArrayList<String>();
    	predicates.add(GESAH + OBJECT_OF_PUBLICATION);
    	return EditModeUtils.getEditMode(vreq, predicates);
    }
}