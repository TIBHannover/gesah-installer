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
    gesah:Production - primary new individual being created
    foaf:Person - new or existing individual
    foaf:Organization - new or existing individual - has to be implemented
    gesah:Cultural_Object - existing individual
	obo:BFO_0000023 - new individual or existing while being edited
	gesah:Role_Type - existing individual while being edited
	gesah:Attribution_Type - existing individual 
	gesah:Material - existing individual 
	gesah:Technique - existing individual 
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
        conf.setVarNameForObject(OB_CULTURAL_OBJECT);
        
        addLitDateAppeal(conf);		
        addExistingPlace(conf);
        addPlace(conf);
        addComment(conf);
        addAttributeType(conf);
        addExistingRoleType(conf);
        addExistingRole(conf);
        addRole(conf);		
        addAgent(conf);	
        addExistingAgent(conf);	
        addStartEndInterval(conf);
        addTechnique(conf);
        addMaterial(conf);	

        conf.addN3Required( Arrays.asList(n3ForNewObProduction) );
        conf.addNewResource(OB_CULTURAL_OBJECT, DEFAULT_NS_FOR_NEW_RESOURCE);
        conf.addSparqlForAdditionalUrisInScope(PRODUCTION_HAS_OUTPUT, productionHasOutputQuery);
        conf.addValidator(new AntiXssValidation());
    }

    /* N3 assertions for production of a cultural object */

    final static String n3ForNewObProduction =
        "?cultObject" + " " + "<" + GESAH + "output_of_production> " + " " + VAR + OB_CULTURAL_OBJECT + " .\n" +
        VAR + OB_CULTURAL_OBJECT + "  a" + " " + "<" + GESAH + "Production> ; \n" +
        "	<" + GESAH_REALIZES + ">" + " " + VAR + NEW_ROLE + " . \n" +
        VAR + NEW_ROLE +" " + "<" + GESAH + "realized_in" + ">" + " " + VAR + OB_CULTURAL_OBJECT + " . \n" +
        VAR + OB_CULTURAL_OBJECT +" " + "<" + GESAH + "has_production_output> ?cultObject .";

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
