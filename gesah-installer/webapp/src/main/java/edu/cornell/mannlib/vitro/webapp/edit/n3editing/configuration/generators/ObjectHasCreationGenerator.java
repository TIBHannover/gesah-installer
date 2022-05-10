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

/**
    Form for adding an educational attainment to an individual

    Classes:
    gesah:Creation - primary new individual being created
    foaf:Agent -  new or existing individual of type foaf:Person, foaf:Organization etc.  
    gesah:Cultural_Object - existing individual
	obo:BFO_0000023 - new individual or existing while being edited
	gesah:Role_Type - new or existing individual while being edited
	gesah:Attribution_Type - new or existing individual 
	gesah:Material - new or existing individual 
	gesah:Technique - new or existing individual 
    core:GeographicLocation - new or existing individual



    There are 4 modes that this form can be in:
     1.  Add, there is a subject and a predicate but no position and nothing else.

     2. normal edit where everything should already be filled out.  There is a subject, a object and an individual on
        the other end of the object's relationship.

     3. Repair a bad role node.  There is a subject, predicate and object but there is no individual on the
        other end of the object's  relationship.  This should be similar to an add but the form should be expanded.

     4. Really bad node. multiple statements on the other end of the object's  relationship.

 *
 *
 */
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
        addExistingActivityRole(conf);
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

    final static String n3ForNewObCreation =
        VAR + CULT_OBJECT + " " + "<" + GESAH_OUTPUT_OF_CREATION + "> " + VAR + ACTIVITY_OBJ + " .\n" +
        VAR + ACTIVITY_OBJ + " a <" + GESAH_CREATION + "> ; \n" +
        "	<" + GESAH_REALIZES + "> " + VAR + NEW_ACTOR_ROLE + " . \n" +
        VAR + NEW_ACTOR_ROLE + " <" + GESAH + "realized_in> " + VAR + ACTIVITY_OBJ + " . \n" +
        VAR + ACTIVITY_OBJ + " <" + GESAH + "has_creation_output> ?cultObject .";
		
    //Query for inverse property
    final static String creationHasOutputQuery  =
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
