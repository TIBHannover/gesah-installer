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
    foaf:Person - existing individual
    foaf:Organization - new or existing individual - has to be implemented
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
public class ObjectHasCreation  extends GesahBaseGenerator implements EditConfigurationGenerator{

    //TODO: can we get rid of the session and get it form the vreq?
    public EditConfigurationVTwo getEditConfiguration(VitroRequest vreq, HttpSession session) throws Exception {

        EditConfigurationVTwo conf = new EditConfigurationVTwo();

        initBasics(conf, vreq);
        initPropertyParameters(vreq, session, conf);
        initObjectPropForm(conf, vreq);

        conf.setTemplate("objectHasCreation.ftl");

        conf.setVarNameForSubject("cultObject");
        conf.setVarNameForPredicate("predicate");
        conf.setVarNameForObject("obCreation");

        conf.setN3Required( Arrays.asList(n3ForNewObCreation) );
        conf.setN3Optional(Arrays.asList( descriptionAssertion,  n3ForNewAttrType, n3ForExistingAttrType, n3ForNewAgent, n3ForExistingAgent,
                n3ForNewTechnique, n3ForExistingTechnique, n3ForNewMaterial, n3ForExistingMaterial, n3ForNewRole, n3ForExistingRole, n3ForNewRoleType, n3ForExistingRoleType, n3ForNewPlace, n3ForExistingPlace, litDateAppelAssertion, n3ForStart, n3ForEnd ));

        conf.addNewResource("obCreation", DEFAULT_NS_FOR_NEW_RESOURCE);
        conf.addNewResource("newRole",DEFAULT_NS_FOR_NEW_RESOURCE);
		conf.addNewResource("newRoleType",DEFAULT_NS_FOR_NEW_RESOURCE);
		conf.addNewResource("newAttrType",DEFAULT_NS_FOR_NEW_RESOURCE);
		conf.addNewResource("newTechnique",DEFAULT_NS_FOR_NEW_RESOURCE);
		conf.addNewResource("newMaterial",DEFAULT_NS_FOR_NEW_RESOURCE);
        conf.addNewResource("newAgent",DEFAULT_NS_FOR_NEW_RESOURCE);
		conf.addNewResource("newPlace",DEFAULT_NS_FOR_NEW_RESOURCE);
        conf.addNewResource("intervalNode",DEFAULT_NS_FOR_NEW_RESOURCE);
        conf.addNewResource("startNode",DEFAULT_NS_FOR_NEW_RESOURCE);
        conf.addNewResource("endNode",DEFAULT_NS_FOR_NEW_RESOURCE);

        //uris in scope: none
        //literals in scope: none

        conf.setUrisOnform( Arrays.asList( "existingAgent", "agentType", "existingMaterial","existingTechnique","existingAttrType", "existingRoleType", "existingRole", "existingPlace"));
        conf.setLiteralsOnForm( Arrays.asList("agentLabel", "techniqueLabel", "materialLabel", "roleTypeLabel", "agentLabelDisplay", "existingAttrTypeLabel", "placeLabel", "placeLabelDisplay","description", "litDateAppel"));

        conf.addSparqlForExistingLiteral("agentLabel", persLabelQuery);
		conf.addSparqlForExistingLiteral("techniqueLabel", existingTechniqueLabelQuery);
		conf.addSparqlForExistingLiteral("materialLabel", existingMaterialLabelQuery);
		conf.addSparqlForExistingLiteral("placeLabel", existingPlaceLabelQuery);
		conf.addSparqlForExistingLiteral("attrTypeLabel", existingAttrTypeLabelQuery);
		conf.addSparqlForExistingLiteral("roleTypeLabel", existingRoleTypeLabelQuery);
        conf.addSparqlForExistingLiteral("description", descriptionQuery);
        conf.addSparqlForExistingLiteral("litDateAppel", litDateAppelQuery);
        conf.addSparqlForExistingLiteral("startField-value", existingStartDateQuery);
        conf.addSparqlForExistingLiteral("endField-value", existingEndDateQuery);


        conf.addSparqlForExistingUris("existingMaterial", existingMaterialQuery);
		conf.addSparqlForExistingUris("existingTechnique", existingTechniqueQuery);
        conf.addSparqlForExistingUris("existingAgent", existingPersQuery);
        conf.addSparqlForExistingUris("existingAttrType", existingAttrTypeQuery);
        conf.addSparqlForExistingUris("agentType", agentTypeQuery);
        conf.addSparqlForExistingUris("existingRoleType", existingRoleTypeQuery);
		conf.addSparqlForExistingUris("existingRole", existingRoleQuery);
        conf.addSparqlForExistingUris("existingPlace", existingPlaceQuery);
        conf.addSparqlForExistingUris("intervalNode",existingIntervalNodeQuery);
        conf.addSparqlForExistingUris("startNode", existingStartNodeQuery);
        conf.addSparqlForExistingUris("endNode", existingEndNodeQuery);
        conf.addSparqlForExistingUris("startField-precision", existingStartPrecisionQuery);
        conf.addSparqlForExistingUris("endField-precision", existingEndPrecisionQuery);
        //Add sparql to include inverse property as well
        conf.addSparqlForAdditionalUrisInScope("creationHasOutput", creationHasOutputQuery);
			
					

		conf.addField( new FieldVTwo().
                setName("existingAgent").
                setOptions( new IndividualsViaVClassOptions(
                        agentClass)));	
						
		conf.addField( new FieldVTwo().
                setName("newAgent").
                setOptions( new IndividualsViaVClassOptions(
                        agentClass)));	

        conf.addField( new FieldVTwo().
                setName("agentType").
				setValidators( list("nonempty")).
                setOptions( new ChildVClassesOptions(
                        agentClass)));		

		conf.addField( new FieldVTwo().
                setName("existingPlace").
                setOptions( new IndividualsViaVClassOptions(
                        placeTypeClass)));		

        conf.addField( new FieldVTwo().
                setName("newPlace").
                setOptions( new IndividualsViaVClassOptions(
                        placeTypeClass)));                			

        conf.addField( new FieldVTwo().
                setName("description").
                setRangeDatatypeUri( XSD.xstring.toString() ).
                setValidators(list("datatype:" + XSD.xstring.toString())));
				
		conf.addField( new FieldVTwo().
                setName("litDateAppel").
                setRangeDatatypeUri( XSD.xstring.toString() ).
                setValidators(list("datatype:" + XSD.xstring.toString())));		


        conf.addField( new FieldVTwo().
                setName("existingAttrType").
                setValidators( list("nonempty")).
                setOptions( new IndividualsViaVClassOptions(
                        attributionTypeClass)));
                

        conf.addField( new FieldVTwo().
                setName("agentLabel").
                setRangeDatatypeUri(XSD.xstring.toString() ).
                setValidators( list("datatype:" + XSD.xstring.toString())));

        conf.addField( new FieldVTwo().
                setName("techniqueLabel").
                setRangeDatatypeUri(XSD.xstring.toString() ).
                setValidators( list("datatype:" + XSD.xstring.toString())));

        conf.addField( new FieldVTwo().
                setName("existingAttrTypeLabel").
                setRangeDatatypeUri(XSD.xstring.toString() ).
                setValidators( list("datatype:" + XSD.xstring.toString())));
		
		conf.addField( new FieldVTwo().
                setName("materialLabel").
                setRangeDatatypeUri(XSD.xstring.toString() ).
                setValidators( list("datatype:" + XSD.xstring.toString())));
				
		conf.addField( new FieldVTwo().
                setName("roleTypeLabel").
                setRangeDatatypeUri(XSD.xstring.toString() ).
                setValidators( list("datatype:" + XSD.xstring.toString())));		

        conf.addField( new FieldVTwo().
                setName("agentLabelDisplay").
                setRangeDatatypeUri(XSD.xstring.toString() ));

        conf.addField( new FieldVTwo().
                setName("placeLabelDisplay").
                setRangeDatatypeUri(XSD.xstring.toString() ));        

        conf.addField( new FieldVTwo().
                setName("existingRoleType").
                setOptions( new IndividualsViaVClassOptions(
                        roleTypeClass)));
						
		conf.addField( new FieldVTwo().
                setName("existingMaterial").
                setValidators( list("nonempty")).
                setOptions( new IndividualsViaVClassOptions(
                        materialTypeClass)));;	

		conf.addField( new FieldVTwo().
                setName("existingTechnique").
                setValidators( list("nonempty")).
                setOptions( new IndividualsViaVClassOptions(
                        techniqueTypeClass)));	
 
						
		conf.addField( new FieldVTwo().
                setName("newRole").
                setOptions( new IndividualsViaVClassOptions(
                        roleClass)));		
    

        FieldVTwo startField = new FieldVTwo().
        						setName("startField");
        conf.addField(startField.
            setEditElement(
                    new DateTimeWithPrecisionVTwo(startField,
                    VitroVocabulary.Precision.YEAR.uri(),
                    VitroVocabulary.Precision.NONE.uri())));

        FieldVTwo endField = new FieldVTwo().
								setName("endField");
        conf.addField( endField.
                setEditElement(
                        new DateTimeWithPrecisionVTwo(endField,
                        VitroVocabulary.Precision.YEAR.uri(),
                        VitroVocabulary.Precision.NONE.uri())));
        //Add validator
        conf.addValidator(new DateTimeIntervalValidationVTwo("startField","endField"));
        conf.addValidator(new AntiXssValidation());

        //Adding additional data, specifically edit mode
        //addFormSpecificData(conf, vreq);
        prepare(vreq, conf);
        return conf;
    }

    /* N3 assertions for creation of a cultural object */

    final static String n3ForNewObCreation =
        "?cultObject <http://ontology.tib.eu/gesah/output_of_creation>  ?obCreation .\n" +
        "?obCreation  a <http://ontology.tib.eu/gesah/Creation> ; \n" +
		"			 <http://ontology.tib.eu/gesah/realizes> ?newRole . \n" +
		"?newRole <http://ontology.tib.eu/gesah/realized_in> ?obCreation . \n" +
        "?obCreation <http://ontology.tib.eu/gesah/has_creation_output> ?cultObject .";
		
 final static String n3ForNewAgent  =
		"@prefix rdfs: <"+ rdfs +">   .\n"+
        "?obCreation <http://ontology.tib.eu/gesah/has_participant> ?newAgent . \n" +
        "?newAgent <http://ontology.tib.eu/gesah/participates_in> ?obCreation . \n" +
		"?obCreation <http://ontology.tib.eu/gesah/realizes> ?newRole . \n" +
		"?newAgent <http://ontology.tib.eu/gesah/has_role> ?newRole . \n" +
		"?newRole <http://ontology.tib.eu/gesah/is_role_of> ?newAgent . \n" +
		"?newRole <http://ontology.tib.eu/gesah/realized_in> ?obCreation . \n" +
        "?newAgent a ?agentType . \n" +
		"?agentType rdfs:subClassOf <http://xmlns.com/foaf/0.1/Agent> .\n" +
        "?newAgent rdfs:label ?agentLabel . ";

    final static String n3ForExistingAgent  =
		"@prefix rdfs: <"+ rdfs +">  . \n"+
        "?obCreation <http://ontology.tib.eu/gesah/has_participant> ?existingAgent . \n" +
		"?obCreation <http://ontology.tib.eu/gesah/realizes> ?newRole . \n" +
        "?existingAgent <http://ontology.tib.eu/gesah/participates_in> ?obCreation . \n" +
		"?existingAgent <http://ontology.tib.eu/gesah/has_role> ?newRole . \n" +
		"?newRole <http://ontology.tib.eu/gesah/realized_in> ?obCreation . \n" +
		"?newRole <http://ontology.tib.eu/gesah/is_role_of> ?existingAgent . " ;

	final static String n3ForNewRole  =
        "?obCreation <http://ontology.tib.eu/gesah/realizes> ?newRole . \n" +
        "?newRole <http://ontology.tib.eu/gesah/realized_in> ?obCreation . \n" +
		"?newRole a <http://purl.obolibrary.org/obo/BFO_0000023> . " ;
			

	final static String n3ForNewRoleType  =
		"@prefix rdfs: <"+ rdfs +">  . \n"+
        "?obCreation <http://ontology.tib.eu/gesah/realizes> ?newRole . \n" +
        "?newRole <http://ontology.tib.eu/gesah/realized_in> ?obCreation . \n" +
		"?newRole <http://ontology.tib.eu/gesah/has_role_type> ?newRoleType . \n" +
        "?newRoleType <"+ label +"> ?newRoleTypeLabel . \n" +
		"?newRoleType a  <http://ontology.tib.eu/gesah/Role_Type> . " ;
			
	final static String n3ForExistingRoleType  =
		"?obCreation <http://ontology.tib.eu/gesah/realizes> ?newRole . \n" +
        "?newRole <http://ontology.tib.eu/gesah/realized_in> ?obCreation . \n" +
        "?newRole <http://ontology.tib.eu/gesah/has_role_type> ?existingRoleType . " ;		


    final static String n3ForNewAttrType  =
        "@prefix gesah: <"+ gesah +"> .\n"+
        "?obCreation <http://ontology.tib.eu/gesah/has_type_of_attribution> ?newAttrType . \n" +
        "?newAttrType <http://ontology.tib.eu/gesah/is_attribution_type_of> ?obCreation . \n" +
        "?newAttrType <"+ label +"> ?attrTypeLabel . \n" +
        "?newAttrType a <http://ontology.tib.eu/gesah/Attribution_Type> .";
		
	final static String n3ForExistingAttrType  =
        "@prefix gesah: <"+ gesah +"> .\n"+
        "?obCreation <http://ontology.tib.eu/gesah/has_type_of_attribution> ?existingAttrType . \n" +
        "?existingAttrType <http://ontology.tib.eu/gesah/is_attribution_type_of> ?obCreation . \n" +
        "?existingAttrType a <http://ontology.tib.eu/gesah/Attribution_Type> .";	

   
    final static String n3ForNewTechnique  =
        "@prefix gesah: <"+ gesah +"> .\n"+
        "?obCreation <http://ontology.tib.eu/gesah/uses_technique> ?newTechnique . \n" +
        "?newTechnique <http://ontology.tib.eu/gesah/used_in> ?obCreation . \n" +
        "?newTechnique <"+ label +"> ?techniqueLabel . \n" +
        "?newTechnique a <http://ontology.tib.eu/gesah/Technique> .";
		
	final static String n3ForExistingTechnique  =
        "@prefix gesah: <"+ gesah +"> .\n"+
        "?obCreation <http://ontology.tib.eu/gesah/uses_technique> ?existingTechnique . \n" +
        "?existingTechnique <http://ontology.tib.eu/gesah/used_in> ?obCreation . \n" +
        "?existingTechnique a <http://ontology.tib.eu/gesah/Technique> .";	
		
	final static String n3ForNewMaterial  =
        "@prefix gesah: <"+ gesah +"> .\n"+
        "?obCreation <http://ontology.tib.eu/gesah/has_material> ?newMaterial . \n" +
        "?newMaterial <http://ontology.tib.eu/gesah/incorporated_in> ?obCreation . \n" +
        "?newMaterial <"+ label +"> ?materialLabel . \n" +
        "?newMaterial a <http://ontology.tib.eu/gesah/Material> .";
		
	final static String n3ForExistingMaterial  =
        "@prefix gesah: <"+ gesah +"> .\n"+
        "?obCreation <http://ontology.tib.eu/gesah/has_material> ?existingMaterial . \n" +
        "?existingMaterial <http://ontology.tib.eu/gesah/incorporated_in> ?obCreation . \n" +
        "?existingMaterial a <http://ontology.tib.eu/gesah/Material> .";
		
	final static String n3ForNewPlace  =
        "@prefix gesah: <"+ gesah +"> .\n"+
        "?obCreation <http://ontology.tib.eu/gesah/has_place> ?newPlace . \n" +
        "?newPlace <http://ontology.tib.eu/gesah/is_place_of> ?obCreation . \n" +
        "?newPlace <"+ label +"> ?placeLabel . \n" +
        "?newPlace a <http://vivoweb.org/ontology/core#GeographicLocation> .";
		
	final static String n3ForExistingPlace  =
        "@prefix gesah: <"+ gesah +"> .\n"+
        "?obCreation <http://ontology.tib.eu/gesah/has_place> ?existingPlace . \n" +
        "?existingPlace <http://ontology.tib.eu/gesah/is_place_of> ?obCreation . \n" +
        "?existingPlace a <http://vivoweb.org/ontology/core#GeographicLocation> .";
		
    final static String descriptionAssertion  =
        "?obCreation <http://ontology.tib.eu/gesah/description> ?description .";
		
	final static String litDateAppelAssertion  =
        "?obCreation <http://ontology.tib.eu/gesah/literal_date_appellation> ?litDateAppel .";	

    final static String n3ForStart =
        "?obCreation      <"+ toInterval +"> ?intervalNode .\n"+
        "?intervalNode  <"+ type +"> <"+ intervalType +"> .\n"+
        "?intervalNode <"+ intervalToStart +"> ?startNode .\n"+
        "?startNode  <"+ type +"> <"+ dateTimeValueType +"> .\n"+
        "?startNode  <"+ dateTimeValue +"> ?startField-value .\n"+
        "?startNode  <"+ dateTimePrecision +"> ?startField-precision .";

    final static String n3ForEnd =
        "?obCreation     <"+ toInterval +"> ?intervalNode . \n"+
        "?intervalNode  <"+ type +"> <"+ intervalType +"> .\n"+
        "?intervalNode <"+ intervalToEnd +"> ?endNode .\n"+
        "?endNode  <"+ type +"> <"+ dateTimeValueType +"> .\n"+
        "?endNode  <"+ dateTimeValue +"> ?endField-value .\n"+
        "?endNode  <"+ dateTimePrecision +"> ?endField-precision .";



 
  //Queries for editing an existing creation entry

    final static String existingAttrTypeQuery =
        "SELECT ?existingAttrType WHERE {\n"+
        "?obCreation <http://ontology.tib.eu/gesah/has_type_of_attribution> ?existingAttrType . }";

    final static String existingAttrTypeLabelQuery =
        "SELECT Distinct ?existingAttrTypeLabel WHERE {\n"+
        "?obCreation <http://ontology.tib.eu/gesah/has_type_of_attribution> ?existingAttrType . \n" +
        "?existingAttrType <"+ label +"> ?existingAttrTypeLabel .}";

	final static String existingTechniqueQuery =
        "SELECT ?existingTechnique WHERE {\n"+
        "?obCreation <http://ontology.tib.eu/gesah/uses_technique> ?existingTechnique . }";

    final static String existingTechniqueLabelQuery =
        "SELECT Distinct ?existingTechniqueLabel WHERE {\n"+
        "?obCreation <http://ontology.tib.eu/gesah/uses_technique> ?existingTechnique . \n" +
        "?existingTechnique <"+ label +"> ?existingTechniqueLabel }";

	final static String existingMaterialQuery =
        "SELECT ?existingMaterial WHERE {\n"+
        "?obCreation <http://ontology.tib.eu/gesah/has_material> ?existingMaterial  . }";

    final static String existingMaterialLabelQuery =
        "SELECT Distinct ?existingMaterialLabel WHERE {\n"+
        "?obCreation <http://ontology.tib.eu/gesah/has_material> ?existingMaterial . \n" +
        "?existingMaterial <"+ label +"> ?existingMaterialLabel .}";	
		
	final static String existingRoleTypeQuery =
        "SELECT ?existingRoleType WHERE {\n"+
        "?obCreation <http://ontology.tib.eu/gesah/realizes> ?existingRole . \n" +
        "?existingRole <http://ontology.tib.eu/gesah/realized_in> ?obCreation . \n" +
		"?existingRole <http://ontology.tib.eu/gesah/has_role_type> ?existingRoleType . }";	
		
	final static String existingRoleTypeLabelQuery =
        "SELECT Distinct ?existingRoleTypeLabel WHERE {\n"+
        "?obCreation <http://ontology.tib.eu/gesah/realizes> ?existingRole . \n" +
        "?existingRole <http://ontology.tib.eu/gesah/realized_in> ?obCreation . \n" +
		"?existingRole <http://ontology.tib.eu/gesah/has_role_type> ?existingRoleType . \n" +
        "?existingRoleType <"+ label +"> ?existingRoleTypeLabel . }";
	
		
	final static String existingPlaceQuery =
        "SELECT  ?existingPlace WHERE {\n"+
        "?obCreation <http://ontology.tib.eu/gesah/has_place> ?existingPlace  . }";

    final static String existingPlaceLabelQuery =
        "SELECT Distinct ?existingPlaceLabel WHERE {\n"+
        "?obCreation <http://ontology.tib.eu/gesah/has_place> ?existingPlace . \n" +
        "?existingPlace <"+ label +"> ?existingPlaceLabel }";	
	
    final static String existingAgentQuery  =
        "PREFIX rdfs: <"+ rdfs +">   \n"+
        "SELECT ?existingAgent WHERE {\n"+
        "?obCreation <http://ontology.tib.eu/gesah/has_participant> ?existingAgent . \n" +
		"?existingAgent <http://ontology.tib.eu/gesah/participates_in> ?obCreation . \n" +
		"?existingAgent <http://ontology.tib.eu/gesah/has_role> ?existingRole .\n" +
		"?existingRole <http://ontology.tib.eu/gesah/is_role_of> ?existingAgent .\n" +
		"?existingAgent a ?agentType . \n " +
        "?agentType rdfs:subClassOf <http://xmlns.com/foaf/0.1/Agent> . }" ;

    final static String agentLabelQuery  =
        "PREFIX rdfs: <"+ rdfs +">   \n"+
        "SELECT Distinct ?existingAgentLabel WHERE {\n"+
        "?obCreation <http://ontology.tib.eu/gesah/has_participant> ?existingAgent . \n" +
		"?existingAgent <http://ontology.tib.eu/gesah/participates_in> ?obCreation . \n" +
        "?existingAgent <"+ label +"> ?existingAgentLabel .\n"+
        "?existingAgent a ?agentType . \n " +
        "?agentType rdfs:subClassOf <http://xmlns.com/foaf/0.1/Agent> . }" ;

    /* Limit type to subclasses of foaf:Agent. Otherwise, sometimes owl:Thing or another
    type is returned and we don't get a match to the select element options. */
	
    final static String agentTypeQuery  =
        "PREFIX rdfs: <"+ rdfs +">   \n"+
        "SELECT ?agentType WHERE {\n"+
        "?obCreation <http://ontology.tib.eu/gesah/has_participant> ?existingAgent . \n" +
		"?existingAgent <http://ontology.tib.eu/gesah/participates_in> ?obCreation . \n" +
		"?existingAgent <http://ontology.tib.eu/gesah/has_role> ?existingRole .\n" +
		"?existingRole <http://ontology.tib.eu/gesah/is_role_of> ?existingAgent .\n" +
        "?existingAgent a ?agentType .\n"+
        "?agentType rdfs:subClassOf <http://xmlns.com/foaf/0.1/Agent> .}";

    final static String descriptionQuery  =
        "SELECT ?existingDescription WHERE {\n"+
        "?obCreation <"+ desciptionPred +"> ?existingDescription . }";

    final static String litDateAppelQuery  =
        "SELECT ?existinglitDateAppel WHERE {\n"+
        "?obCreation <"+ literalDateAppelPred +"> ?existinglitDateAppel . }";

    final static String existingIntervalNodeQuery  =
        "SELECT ?existingIntervalNode WHERE {\n"+
        "?obCreation <"+ toInterval +"> ?existingIntervalNode .\n"+
        "?existingIntervalNode <"+ type +"> <"+ intervalType +"> . }";

    final static String existingStartNodeQuery  =
        "SELECT ?existingStartNode WHERE {\n"+
        "?obCreation <"+ toInterval +"> ?intervalNode .\n"+
        "?intervalNode <"+ type +"> <"+ intervalType +"> .\n"+
        "?intervalNode <"+ intervalToStart +"> ?existingStartNode . \n"+
        "?existingStartNode <"+ type +"> <"+ dateTimeValueType +"> .}";

    final static String existingStartDateQuery  =
        "SELECT ?existingDateStart WHERE {\n"+
        "?obCreation <"+ toInterval +"> ?intervalNode .\n"+
        "?intervalNode <"+ type +"> <"+ intervalType +"> .\n"+
        "?intervalNode <"+ intervalToStart +"> ?startNode .\n"+
        "?startNode <"+ type +"> <"+ dateTimeValueType +"> .\n"+
        "?startNode <"+ dateTimeValue +"> ?existingDateStart . }";

    final static String existingStartPrecisionQuery  =
        "SELECT ?existingStartPrecision WHERE {\n"+
        "?obCreation <"+ toInterval +"> ?intervalNode .\n"+
        "?intervalNode <"+ type +"> <"+ intervalType +"> .\n"+
        "?intervalNode <"+ intervalToStart +"> ?startNode .\n"+
        "?startNode <"+ type +"> <"+ dateTimeValueType +"> . \n"+
        "?startNode <"+ dateTimePrecision +"> ?existingStartPrecision . }";

    final static String existingEndNodeQuery  =
        "SELECT ?existingEndNode WHERE { \n"+
        "?obCreation <"+ toInterval +"> ?intervalNode .\n"+
        "?intervalNode <"+ type +"> <"+ intervalType +"> .\n"+
        "?intervalNode <"+ intervalToEnd +"> ?existingEndNode . \n"+
        "?existingEndNode <"+ type +"> <"+ dateTimeValueType +"> .}";

    final static String existingEndDateQuery  =
        "SELECT ?existingEndDate WHERE {\n"+
        "?obCreation <"+ toInterval +"> ?intervalNode .\n"+
        "?intervalNode <"+ type +"> <"+ intervalType +"> .\n"+
        "?intervalNode <"+ intervalToEnd +"> ?endNode .\n"+
        "?endNode <"+ type +"> <"+ dateTimeValueType +"> .\n"+
        "?endNode <"+ dateTimeValue +"> ?existingEndDate . }";

    final static String existingEndPrecisionQuery  =
        "SELECT ?existingEndPrecision WHERE {\n"+
        "?obCreation <"+ toInterval +"> ?intervalNode .\n"+
        "?intervalNode <"+ type +"> <"+ intervalType +"> .\n"+
        "?intervalNode <"+ intervalToEnd +"> ?endNode .\n"+
        "?endNode <"+ type +"> <"+ dateTimeValueType +"> .\n"+
        "?endNode <"+ dateTimePrecision +"> ?existingEndPrecision . }";

    //Query for inverse property
    final static String creationHasOutputQuery  =
    	  "PREFIX owl:  <http://www.w3.org/2002/07/owl#>"
			+ " SELECT ?creationHasOutput "
			+ "    WHERE { ?creationHasOutput owl:inverseOf <http://ontology.tib.eu/gesah/output_of_creation> . } ";


  //Adding form specific data such as edit mode
	public void addFormSpecificData(EditConfigurationVTwo editConfiguration, VitroRequest vreq) {
		HashMap<String, Object> formSpecificData = new HashMap<String, Object>();
		formSpecificData.put("editMode", getEditMode(vreq).name().toLowerCase());
		editConfiguration.setFormSpecificData(formSpecificData);
	}

	public EditMode getEditMode(VitroRequest vreq) {
		List<String> predicates = new ArrayList<String>();
		predicates.add("http://ontology.tib.eu/gesah/output_of_creation");
		return EditModeUtils.getEditMode(vreq, predicates);
	}
	
}
