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
        conf.setVarNameForObject(OB_CULTURAL_OBJECT);

        conf.setN3Required( Arrays.asList(n3ForNewObCreation) );
        conf.setN3Optional(Arrays.asList( commentAssertion,  n3ForNewAttrType, n3ForExistingAttrType, n3ForNewAgent, 
        		n3ForExistingAgent, n3ForNewTechnique, n3ForExistingTechnique, n3ForNewMaterial, n3ForExistingMaterial, 
        		n3ForNewRole, n3ForExistingRole, n3ForNewRoleType, n3ForExistingRoleType, n3ForNewPlace, n3ForExistingPlace, 
        		litDateAppelAssertion, n3ForStart, n3ForEnd ));

        conf.addNewResource(OB_CULTURAL_OBJECT, DEFAULT_NS_FOR_NEW_RESOURCE);
        conf.addNewResource(NEW_ROLE,DEFAULT_NS_FOR_NEW_RESOURCE);
        conf.addNewResource(NEW_ROLE_TYPE,DEFAULT_NS_FOR_NEW_RESOURCE);
        conf.addNewResource(NEW_ATTR_TYPE,DEFAULT_NS_FOR_NEW_RESOURCE);
        conf.addNewResource(NEW_TECHNIQUE,DEFAULT_NS_FOR_NEW_RESOURCE);
        conf.addNewResource(NEW_MATERIAL,DEFAULT_NS_FOR_NEW_RESOURCE);
        conf.addNewResource(NEW_AGENT,DEFAULT_NS_FOR_NEW_RESOURCE);
        conf.addNewResource(NEW_PLACE,DEFAULT_NS_FOR_NEW_RESOURCE);
        conf.addNewResource(INTERVAL_NODE,DEFAULT_NS_FOR_NEW_RESOURCE);
        conf.addNewResource(START_NODE,DEFAULT_NS_FOR_NEW_RESOURCE);
        conf.addNewResource(END_NODE,DEFAULT_NS_FOR_NEW_RESOURCE);

        //uris in scope: none
        //literals in scope: none

        conf.setUrisOnform( Arrays.asList( EXISTING_AGENT, AGENT_TYPE, EXISTING_MATERIAL,EXISTING_TECHNIQUE,EXISTING_ATTR_TYPE, 
        		EXISTING_ROLE_TYPE, EXISTING_ROLE, EXISTING_PLACE));
        conf.setLiteralsOnForm( Arrays.asList(AGENT_LABEL, TECHNIQUE_LABEL, MATERIAL_LABEL, ROLE_TYPE_LABEL, AGENT_LABEL_DISPLAY,
        		EXISTING_ATTR_TYPE_LABEL, PLACE_LABEL, PLACE_LABEL_DISPLAY,COMMENT, LIT_DATE_APPEL));

        conf.addSparqlForExistingLiteral(AGENT_LABEL, agentLabelQuery);
        conf.addSparqlForExistingLiteral(TECHNIQUE_LABEL, existingTechniqueLabelQuery);
        conf.addSparqlForExistingLiteral(MATERIAL_LABEL, existingMaterialLabelQuery);
        conf.addSparqlForExistingLiteral(PLACE_LABEL, existingPlaceLabelQuery);
        conf.addSparqlForExistingLiteral(ATTR_TYPE_LABEL, existingAttrTypeLabelQuery);
        conf.addSparqlForExistingLiteral(ROLE_TYPE_LABEL, existingRoleTypeLabelQuery);
        conf.addSparqlForExistingLiteral(COMMENT, commentQuery);
        conf.addSparqlForExistingLiteral(LIT_DATE_APPEL, litDateAppelQuery);
        conf.addSparqlForExistingLiteral(START_FIELD_VALUE, existingStartDateQuery);
        conf.addSparqlForExistingLiteral(END_FIELD_VALUE, existingEndDateQuery);


        conf.addSparqlForExistingUris(EXISTING_MATERIAL, existingMaterialQuery);
        conf.addSparqlForExistingUris(EXISTING_TECHNIQUE, existingTechniqueQuery);
        conf.addSparqlForExistingUris(EXISTING_AGENT, existingAgentQuery);
        conf.addSparqlForExistingUris(EXISTING_ATTR_TYPE, existingAttrTypeQuery);
        conf.addSparqlForExistingUris(AGENT_TYPE, agentTypeQuery);
        conf.addSparqlForExistingUris(EXISTING_ROLE_TYPE, existingRoleTypeQuery);
        conf.addSparqlForExistingUris(NEW_ROLE, existingRoleQuery);
        conf.addSparqlForExistingUris(EXISTING_ROLE, existingRoleQuery);
        conf.addSparqlForExistingUris(EXISTING_PLACE, existingPlaceQuery);
        conf.addSparqlForExistingUris(INTERVAL_NODE,existingIntervalNodeQuery);
        conf.addSparqlForExistingUris(START_NODE, existingStartNodeQuery);
        conf.addSparqlForExistingUris(END_NODE, existingEndNodeQuery);
        conf.addSparqlForExistingUris(START_FIELD_PRECISION, existingStartPrecisionQuery);
        conf.addSparqlForExistingUris(END_FIELD_PRECISION, existingEndPrecisionQuery);
        //Add sparql to include inverse property as well
        conf.addSparqlForAdditionalUrisInScope(CREATION_HAS_OUTPUT, creationHasOutputQuery);
			
        conf.addField( new FieldVTwo().
                setName(EXISTING_AGENT).
                setOptions( new IndividualsViaVClassOptions(
                        AGENT_CLASS)));	
						
        conf.addField( new FieldVTwo().
                setName(NEW_AGENT).
                setOptions( new IndividualsViaVClassOptions(
                        AGENT_CLASS)));	

        conf.addField( new FieldVTwo().
                setName(AGENT_TYPE).
                setValidators( list(NONEMPTY)).
                setOptions( new ChildVClassesOptions(
                        AGENT_CLASS)));		

        conf.addField( new FieldVTwo().
                setName(EXISTING_PLACE).
                setOptions( new IndividualsViaVClassOptions(
                        PLACE_TYPE_CLASS)));		

        conf.addField( new FieldVTwo().
                setName(NEW_PLACE).
                setOptions( new IndividualsViaVClassOptions(
                        PLACE_TYPE_CLASS)));                			

        conf.addField( new FieldVTwo().
                setName(COMMENT).
                setRangeDatatypeUri( XSD.xstring.toString() ).
                setValidators(list(DATATYPE + XSD.xstring.toString())));
				
        conf.addField( new FieldVTwo().
                setName(LIT_DATE_APPEL).
                setRangeDatatypeUri( XSD.xstring.toString() ).
                setValidators(list(DATATYPE + XSD.xstring.toString())));		

        conf.addField( new FieldVTwo().
                setName(EXISTING_ATTR_TYPE).
                //setValidators( list("nonempty")).
                setOptions( new IndividualsViaVClassOptions(
                        ATTRIBUTION_TYPE_CLASS)));

        conf.addField( new FieldVTwo().
                setName(AGENT_LABEL).
                setRangeDatatypeUri(XSD.xstring.toString() ).
                setValidators( list(DATATYPE + XSD.xstring.toString())));

        conf.addField( new FieldVTwo().
                setName(TECHNIQUE_LABEL).
                setRangeDatatypeUri(XSD.xstring.toString() ).
                setValidators( list(DATATYPE + XSD.xstring.toString())));

        conf.addField( new FieldVTwo().
                setName(EXISTING_ATTR_TYPE_LABEL).
                setRangeDatatypeUri(XSD.xstring.toString() ).
                setValidators( list(DATATYPE + XSD.xstring.toString())));
		
        conf.addField( new FieldVTwo().
                setName(MATERIAL_LABEL).
                setRangeDatatypeUri(XSD.xstring.toString() ).
                setValidators( list(DATATYPE + XSD.xstring.toString())));
				
        conf.addField( new FieldVTwo().
                setName(ROLE_TYPE_LABEL).
                setRangeDatatypeUri(XSD.xstring.toString() ).
                setValidators( list(DATATYPE + XSD.xstring.toString())));		

        conf.addField( new FieldVTwo().
                setName(AGENT_LABEL_DISPLAY).
                setRangeDatatypeUri(XSD.xstring.toString() ));

        conf.addField( new FieldVTwo().
                setName(PLACE_LABEL_DISPLAY).
                setRangeDatatypeUri(XSD.xstring.toString() ));        

        conf.addField( new FieldVTwo().
            setName(PLACE_LABEL).
            setRangeDatatypeUri(XSD.xstring.toString() ));
        
        conf.addField( new FieldVTwo().
                setName(EXISTING_ROLE_TYPE).
                setOptions( new IndividualsViaVClassOptions(
                        ROLE_TYPE_CLASS)));
    						
    		conf.addField( new FieldVTwo().
                setName(EXISTING_MATERIAL).
                //setValidators( list("nonempty")).
                setOptions( new IndividualsViaVClassOptions(
                        MATERIAL_TYPE_CLASS)));;	

        conf.addField( new FieldVTwo().
                setName(EXISTING_TECHNIQUE).
                //setValidators( list("nonempty")).
                setOptions( new IndividualsViaVClassOptions(
                        TECHNIQUE_TYPE_CLASS)));	
 
        conf.addField( new FieldVTwo().
                setName(NEW_ROLE).
                setOptions( new IndividualsViaVClassOptions(
                        ROLE_CLASS)));		

        FieldVTwo startField = new FieldVTwo().
        						setName(START_FIELD);
        conf.addField(startField.
            		setEditElement(
                    new DateTimeWithPrecisionVTwo(startField,
                    VitroVocabulary.Precision.YEAR.uri(),
                    VitroVocabulary.Precision.NONE.uri())));

        FieldVTwo endField = new FieldVTwo().
								setName(END_FIELD);
        conf.addField( endField.
                setEditElement(
                        new DateTimeWithPrecisionVTwo(endField,
                        VitroVocabulary.Precision.YEAR.uri(),
                        VitroVocabulary.Precision.NONE.uri())));
        //Add validator
        conf.addValidator(new DateTimeIntervalValidationVTwo(START_FIELD,END_FIELD));
        conf.addValidator(new AntiXssValidation());
    }

	/* N3 assertions for creation of a cultural object */

    final static String n3ForNewObCreation =
        VAR + CULT_OBJECT + " " + "<" + GESAH_OUTPUT_OF_CREATION + "> " + VAR + OB_CULTURAL_OBJECT + " .\n" +
        VAR + OB_CULTURAL_OBJECT + " a <" + GESAH_CREATION + "> ; \n" +
        "	<" + GESAH_REALIZES + "> " + VAR + NEW_ROLE + " . \n" +
        VAR + NEW_ROLE + " <" + GESAH + "realized_in> " + VAR + OB_CULTURAL_OBJECT + " . \n" +
        VAR + OB_CULTURAL_OBJECT + " <" + GESAH + "has_creation_output> ?cultObject .";
		
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
