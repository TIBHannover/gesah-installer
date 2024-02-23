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

public class ObjectHasProductionGenerator extends AbstractCulturalObjectGenerator implements EditConfigurationGenerator{

	private static final String EXISTING_MATERIAL_LABEL = "existingMaterialLabel";
	private static final String PRODUCTION_HAS_OUTPUT = "productionHasOutput";
	private static final String OUTPUT_OF_PRODUCTION = "output_of_production";
	private static final String OBJECT_HAS_PRODUCTION_FTL = "objectHasProduction.ftl";

	@Override
	protected void configureForm(VitroRequest vreq, HttpSession session, EditConfigurationVTwo conf) throws Exception {
        conf.setTemplate(OBJECT_HAS_PRODUCTION_FTL);

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
        addTechnique(conf);
        addMaterial(conf);	

        conf.addN3Required( Arrays.asList(n3ForNewObProduction) );
        conf.addNewResource(ACTIVITY_OBJ, DEFAULT_NS_FOR_NEW_RESOURCE);
        conf.addSparqlForAdditionalUrisInScope(PRODUCTION_HAS_OUTPUT, productionHasOutputQuery);
        conf.addValidator(new AntiXssValidation());
    }

    /* N3 assertions for production of a cultural object */

	private final static String n3ForNewObProduction =
        "?cultObject" + " " + "<" + GESAH + "output_of_production> " + " " + VAR + ACTIVITY_OBJ + " .\n" +
        VAR + ACTIVITY_OBJ + "  a " + "<" + GESAH + "Production> . \n" +
        VAR + ACTIVITY_OBJ +" " + "<" + GESAH + "has_production_output> ?cultObject .";

    //Query for inverse property
    final static String productionHasOutputQuery  =
    	  "PREFIX owl:  <http://www.w3.org/2002/07/owl#" + ">" +
			  " SELECT" + " " + VAR + PRODUCTION_HAS_OUTPUT + " " +
			  " WHERE {" + " " + VAR + PRODUCTION_HAS_OUTPUT + " owl:inverseOf " + "<" + GESAH + OUTPUT_OF_PRODUCTION +">" + " " + ". } ";


		@Override
		protected EditMode getEditMode(EditConfigurationVTwo editConf, VitroRequest vreq) {
			List<String> predicates = new ArrayList<String>();
			predicates.add(GESAH + OUTPUT_OF_PRODUCTION);
			return EditModeUtils.getEditMode(vreq, predicates);
		}
	
}
