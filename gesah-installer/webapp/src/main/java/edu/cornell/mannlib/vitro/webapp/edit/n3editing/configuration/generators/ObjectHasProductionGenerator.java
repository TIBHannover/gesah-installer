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
public class ObjectHasProductionGenerator extends GesahEditConfigurationGenerator implements EditConfigurationGenerator{
	private final static String agentClass = foaf + "Agent";
	private final static String roleClass =obo + "BFO_0000023" ;

	final static String attributionTypeClass =gesah+"Attribution_Type" ;
	private final static String materialTypeClass =gesah+"Material" ;
	private final static String placeTypeClass = vivoCore+"GeographicLocation" ;
	private final static String roleTypeClass =gesah +"Role_Type";
	private final static String techniqueTypeClass =gesah+"Technique" ;

	private final static String commentPred =gesah+"comment" ;
	private final static String literalDateAppelPred =gesah+"literal_date_appellation" ;

	@Override
	protected void configureForm(VitroRequest vreq, HttpSession session, EditConfigurationVTwo conf) throws Exception {
        conf.setTemplate("objectHasProduction.ftl");

        conf.setVarNameForSubject("cultObject");
        conf.setVarNameForPredicate("predicate");
        conf.setVarNameForObject("obProduction");

        conf.setN3Required( Arrays.asList(n3ForNewObProduction) );
        conf.setN3Optional(Arrays.asList( commentAssertion,  n3ForNewAttrType, n3ForExistingAttrType, n3ForNewAgent, n3ForExistingAgent, n3ForNewRole,
                n3ForNewTechnique, n3ForExistingTechnique, n3ForNewMaterial,  n3ForExistingMaterial,  n3ForNewRoleType, n3ForExistingRoleType, n3ForNewPlace, n3ForExistingPlace, litDateAppelAssertion, n3ForStart, n3ForEnd ));

        conf.addNewResource("obProduction", DEFAULT_NS_FOR_NEW_RESOURCE);
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

        conf.setUrisOnform( Arrays.asList( "existingAgent", "agentType", "existingMaterial","existingTechnique", "existingAttrType", "existingRoleType", "existingPlace"));
        conf.setLiteralsOnForm( Arrays.asList("agentLabel", "techniqueLabel", "materialLabel", "roleTypeLabel", "agentLabelDisplay", "existingAttrTypeLabel", "placeLabel", "placeLabelDisplay","comment", "litDateAppel"));

        conf.addSparqlForExistingLiteral("agentLabel", agentLabelQuery);
		conf.addSparqlForExistingLiteral("techniqueLabel", existingTechniqueLabelQuery);
		conf.addSparqlForExistingLiteral("materialLabel", existingMaterialLabelQuery);
		conf.addSparqlForExistingLiteral("placeLabel", existingPlaceLabelQuery);
		conf.addSparqlForExistingLiteral("attrTypeLabel", existingAttrTypeLabelQuery);
		conf.addSparqlForExistingLiteral("roleTypeLabel", existingRoleTypeLabelQuery);
        conf.addSparqlForExistingLiteral("comment", commentQuery);
        conf.addSparqlForExistingLiteral("litDateAppel", litDateAppelQuery);
        conf.addSparqlForExistingLiteral("startField-value", existingStartDateQuery);
        conf.addSparqlForExistingLiteral("endField-value", existingEndDateQuery);


        conf.addSparqlForExistingUris("existingMaterial", existingMaterialQuery);
		conf.addSparqlForExistingUris("existingTechnique", existingTechniqueQuery);
        conf.addSparqlForExistingUris("existingAgent", existingAgentQuery);
        conf.addSparqlForExistingUris("existingAttrType", existingAttrTypeQuery);
        conf.addSparqlForExistingUris("existingRoleType", existingRoleTypeQuery);
		conf.addSparqlForExistingUris("newRole", existingRoleQuery);
		conf.addSparqlForExistingUris("agentType", agentTypeQuery);
        conf.addSparqlForExistingUris("existingPlace", existingPlaceQuery);
        conf.addSparqlForExistingUris("intervalNode",existingIntervalNodeQuery);
        conf.addSparqlForExistingUris("startNode", existingStartNodeQuery);
        conf.addSparqlForExistingUris("endNode", existingEndNodeQuery);
        conf.addSparqlForExistingUris("startField-precision", existingStartPrecisionQuery);
        conf.addSparqlForExistingUris("endField-precision", existingEndPrecisionQuery);
        //Add sparql to include inverse property as well
        conf.addSparqlForAdditionalUrisInScope("productionHasOutput", productionHasOutputQuery);
			
					

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
                setName("comment").
                setRangeDatatypeUri( XSD.xstring.toString() ).
                setValidators(list("datatype:" + XSD.xstring.toString())));
				
		conf.addField( new FieldVTwo().
                setName("litDateAppel").
                setRangeDatatypeUri( XSD.xstring.toString() ).
                setValidators(list("datatype:" + XSD.xstring.toString())));		


        conf.addField( new FieldVTwo().
                setName("existingAttrType").
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
                setName("placeLabel").
                setRangeDatatypeUri(XSD.xstring.toString() ));

        conf.addField( new FieldVTwo().
                setName("existingRoleType").
                setOptions( new IndividualsViaVClassOptions(
                        roleTypeClass)));
						
		conf.addField( new FieldVTwo().
                setName("existingMaterial").
                //setValidators( list("nonempty")).
                setOptions( new IndividualsViaVClassOptions(
                        materialTypeClass)));	

		conf.addField( new FieldVTwo().
                setName("existingTechnique").
                //setValidators( list("nonempty")).
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
    }

    /* N3 assertions for production of a cultural object */

    final static String n3ForNewObProduction =
        "?cultObject <http://ontology.tib.eu/gesah/output_of_production>  ?obProduction .\n" +
        "?obProduction  a <http://ontology.tib.eu/gesah/Production> ; \n" +
		"	 <http://ontology.tib.eu/gesah/realizes> ?newRole . \n" +
		"?newRole <http://ontology.tib.eu/gesah/realized_in> ?obProduction . \n" +
        "?obProduction <http://ontology.tib.eu/gesah/has_production_output> ?cultObject .";
		
 final static String n3ForNewAgent  =
		"@prefix rdfs: <"+ rdfs +">   .\n"+
        "?obProduction <http://ontology.tib.eu/gesah/has_participant> ?newAgent . \n" +
        "?newAgent <http://ontology.tib.eu/gesah/participates_in> ?obProduction . \n" +
		"?obProduction <http://ontology.tib.eu/gesah/realizes> ?newRole . \n" +
		"?newAgent <http://ontology.tib.eu/gesah/has_role> ?newRole . \n" +
		"?newRole <http://ontology.tib.eu/gesah/is_role_of> ?newAgent . \n" +
		"?newRole <http://ontology.tib.eu/gesah/realized_in> ?obProduction . \n" +
        "?newAgent a ?agentType . \n" +
		"?agentType rdfs:subClassOf <http://xmlns.com/foaf/0.1/Agent> .\n" +
        "?newAgent rdfs:label ?agentLabel . ";

    final static String n3ForExistingAgent  =
		"@prefix rdfs: <"+ rdfs +">  . \n"+
        "?obProduction <http://ontology.tib.eu/gesah/has_participant> ?existingAgent . \n" +
		"?obProduction <http://ontology.tib.eu/gesah/realizes> ?newRole . \n" +
        "?existingAgent <http://ontology.tib.eu/gesah/participates_in> ?obProduction . \n" +
		"?existingAgent <http://ontology.tib.eu/gesah/has_role> ?newRole . \n" +
		"?newRole <http://ontology.tib.eu/gesah/realized_in> ?obProduction . \n" +
		"?newRole <http://ontology.tib.eu/gesah/is_role_of> ?existingAgent . " ;

	final static String n3ForNewRole  =
		"?obProduction <http://ontology.tib.eu/gesah/realizes> ?newRole . \n" +
		"?newRole <http://ontology.tib.eu/gesah/realized_in> ?obProduction . \n" +
		"?newRole a <http://purl.obolibrary.org/obo/BFO_0000023> . " ;
		

	final static String n3ForNewRoleType  =
		"@prefix rdfs: <"+ rdfs +">  . \n"+
        "?obProduction <http://ontology.tib.eu/gesah/realizes> ?newRole . \n" +
        "?newRole <http://ontology.tib.eu/gesah/realized_in> ?obProduction . \n" +
		"?newRole <http://ontology.tib.eu/gesah/has_role_type> ?newRoleType . \n" +
        "?newRoleType <"+ label +"> ?newRoleTypeLabel . \n" +
		"?newRoleType a  <http://ontology.tib.eu/gesah/Role_Type> . " ;
			
	final static String n3ForExistingRoleType  =
		"?obProduction <http://ontology.tib.eu/gesah/realizes> ?newRole . \n" +
        "?newRole <http://ontology.tib.eu/gesah/realized_in> ?obProduction . \n" +
        "?newRole <http://ontology.tib.eu/gesah/has_role_type> ?existingRoleType . " ;		


    final static String n3ForNewAttrType  =
        "@prefix gesah: <"+ gesah +"> .\n"+
        "?obProduction <http://ontology.tib.eu/gesah/has_type_of_attribution> ?newAttrType . \n" +
        "?newAttrType <http://ontology.tib.eu/gesah/is_attribution_type_of> ?obProduction . \n" +
        "?newAttrType <"+ label +"> ?attrTypeLabel . \n" +
        "?newAttrType a <http://ontology.tib.eu/gesah/Attribution_Type> .";
		
	final static String n3ForExistingAttrType  =
        "@prefix gesah: <"+ gesah +"> .\n"+
        "?obProduction <http://ontology.tib.eu/gesah/has_type_of_attribution> ?existingAttrType . \n" +
        "?existingAttrType <http://ontology.tib.eu/gesah/is_attribution_type_of> ?obProduction . \n" +
        "?existingAttrType a <http://ontology.tib.eu/gesah/Attribution_Type> .";	

   
    final static String n3ForNewTechnique  =
        "@prefix gesah: <"+ gesah +"> .\n"+
        "?obProduction <http://ontology.tib.eu/gesah/uses_technique> ?newTechnique . \n" +
        "?newTechnique <http://ontology.tib.eu/gesah/used_in> ?obProduction . \n" +
        "?newTechnique <"+ label +"> ?techniqueLabel . \n" +
        "?newTechnique a <http://ontology.tib.eu/gesah/Technique> .";
		
		
	final static String n3ForExistingTechnique  =
        "@prefix gesah: <"+ gesah +"> .\n"+
        "?obProduction <http://ontology.tib.eu/gesah/uses_technique> ?existingTechnique . \n" +
        "?existingTechnique <http://ontology.tib.eu/gesah/used_in> ?obProduction . \n" +
        "?existingTechnique a <http://ontology.tib.eu/gesah/Technique> .";	
				
		
	final static String n3ForNewMaterial  =
        "@prefix gesah: <"+ gesah +"> .\n"+
        "?obProduction <http://ontology.tib.eu/gesah/has_material> ?newMaterial . \n" +
        "?newMaterial <http://ontology.tib.eu/gesah/incorporated_in> ?obProduction . \n" +
        "?newMaterial <"+ label +"> ?materialLabel . \n" +
        "?newMaterial a <http://ontology.tib.eu/gesah/Material> .";
		
		
	final static String n3ForExistingMaterial  =
        "@prefix gesah: <"+ gesah +"> .\n"+
        "?obProduction <http://ontology.tib.eu/gesah/has_material> ?existingMaterial . \n" +
        "?existingMaterial <http://ontology.tib.eu/gesah/incorporated_in> ?obProduction . \n" +
        "?existingMaterial a <http://ontology.tib.eu/gesah/Material> .";
		
		
	final static String n3ForNewPlace  =
        "@prefix gesah: <"+ gesah +"> .\n"+
        "?obProduction <http://ontology.tib.eu/gesah/has_place> ?newPlace . \n" +
        "?newPlace <http://ontology.tib.eu/gesah/is_place_of> ?obProduction . \n" +
        "?newPlace <"+ label +"> ?placeLabel . \n" +
        "?newPlace a <http://vivoweb.org/ontology/core#GeographicLocation> .";
		
	final static String n3ForExistingPlace  =
        "@prefix gesah: <"+ gesah +"> .\n"+
        "?obProduction <http://ontology.tib.eu/gesah/has_place> ?existingPlace . \n" +
        "?existingPlace <http://ontology.tib.eu/gesah/is_place_of> ?obProduction . \n" +
        "?existingPlace a <http://vivoweb.org/ontology/core#GeographicLocation> .";
		
    final static String commentAssertion  =
        "?obProduction <http://ontology.tib.eu/gesah/comment> ?comment .";
		
	final static String litDateAppelAssertion  =
        "?obProduction <http://ontology.tib.eu/gesah/literal_date_appellation> ?litDateAppel .";	

    final static String n3ForStart =
        "?obProduction      <"+ toInterval +"> ?intervalNode .\n"+
        "?intervalNode  <"+ type +"> <"+ intervalType +"> .\n"+
        "?intervalNode <"+ intervalToStart +"> ?startNode .\n"+
        "?startNode  <"+ type +"> <"+ dateTimeValueType +"> .\n"+
        "?startNode  <"+ dateTimeValue +"> ?startField-value .\n"+
        "?startNode  <"+ dateTimePrecision +"> ?startField-precision .";

    final static String n3ForEnd =
        "?obProduction     <"+ toInterval +"> ?intervalNode . \n"+
        "?intervalNode  <"+ type +"> <"+ intervalType +"> .\n"+
        "?intervalNode <"+ intervalToEnd +"> ?endNode .\n"+
        "?endNode  <"+ type +"> <"+ dateTimeValueType +"> .\n"+
        "?endNode  <"+ dateTimeValue +"> ?endField-value .\n"+
        "?endNode  <"+ dateTimePrecision +"> ?endField-precision .";



 
  //Queries for editing an existing production entry
  
	 
    final static String existingAttrTypeQuery =
        "SELECT ?existingAttrType WHERE {\n"+
        "?obProduction <http://ontology.tib.eu/gesah/has_type_of_attribution> ?existingAttrType . }";

    final static String existingAttrTypeLabelQuery =
        "SELECT Distinct ?existingAttrTypeLabel WHERE {\n"+
        "?obProduction <http://ontology.tib.eu/gesah/has_type_of_attribution> ?existingAttrType . \n" +
        "?existingAttrType <"+ label +"> ?existingAttrTypeLabel . }";

	final static String existingTechniqueQuery =
        "SELECT ?existingTechnique WHERE {\n"+
        "?obProduction <http://ontology.tib.eu/gesah/uses_technique> ?existingTechnique . }";

    final static String existingTechniqueLabelQuery =
        "SELECT Distinct ?existingTechniqueLabel WHERE {\n"+
        "?obProduction <http://ontology.tib.eu/gesah/uses_technique> ?existingTechnique . \n" +
        "?existingTechnique <"+ label +"> ?existingTechniqueLabel }";
		

	final static String existingMaterialQuery =
        "SELECT ?existingMaterial WHERE {\n"+
        "?obProduction <http://ontology.tib.eu/gesah/has_material> ?existingMaterial  . }";

    final static String existingMaterialLabelQuery =
        "SELECT Distinct ?existingMaterialLabel WHERE {\n"+
        "?obProduction <http://ontology.tib.eu/gesah/has_material> ?existingMaterial . \n" +
        "?existingMaterial <"+ label +"> ?existingMaterialLabel }";			
		
	final static String existingRoleTypeQuery =
        "SELECT ?existingRoleType WHERE {\n"+
        "?obProduction <http://ontology.tib.eu/gesah/realizes> ?existingRole . \n" +
        "?existingRole <http://ontology.tib.eu/gesah/realized_in> ?obProduction . \n" +
		"?existingRole <http://ontology.tib.eu/gesah/has_role_type> ?existingRoleType . }";
		

    final static String existingRoleTypeLabelQuery =
        "SELECT Distinct ?existingRoleTypeLabel WHERE {\n"+
        "?obProduction <http://ontology.tib.eu/gesah/realizes> ?existingRole . \n" +
        "?existingRole <http://ontology.tib.eu/gesah/realized_in> ?obProduction . \n" +
		"?existingRole <http://ontology.tib.eu/gesah/has_role_type> ?existingRoleType . \n" +
        "?existingRoleType <"+ label +"> ?existingRoleTypeLabel . }";		
		
	final static String existingPlaceQuery =
        "SELECT  ?existingPlace WHERE {\n"+
        "?obProduction <http://ontology.tib.eu/gesah/has_place> ?existingPlace  . }";

    final static String existingPlaceLabelQuery =
        "SELECT Distinct ?existingPlaceLabel WHERE {\n"+
        "?obProduction <http://ontology.tib.eu/gesah/has_place> ?existingPlace . \n" +
        "?existingPlace <"+ label +"> ?existingPlaceLabel .}";	
	
    final static String existingAgentQuery  =
        "PREFIX rdfs: <"+ rdfs +">   \n"+
        "SELECT ?existingAgent WHERE {\n"+
        "?obProduction <http://ontology.tib.eu/gesah/has_participant> ?existingAgent . \n" +
		"?existingAgent <http://ontology.tib.eu/gesah/participates_in> ?obProduction . \n" +
		"?existingAgent <http://ontology.tib.eu/gesah/has_role> ?existingRole .\n" +
		"?existingRole <http://ontology.tib.eu/gesah/is_role_of> ?existingAgent .\n" +
		"?existingAgent a ?agentType . \n " +
        "?agentType rdfs:subClassOf <http://xmlns.com/foaf/0.1/Agent> . }" ;

    final static String agentLabelQuery  =
        "PREFIX rdfs: <"+ rdfs +">   \n"+
        "SELECT Distinct ?existingAgentLabel WHERE {\n"+
        "?obProduction <http://ontology.tib.eu/gesah/has_participant> ?existingAgent . \n" +
		"?existingAgent <http://ontology.tib.eu/gesah/participates_in> ?obProduction . \n" +
        "?existingAgent <"+ label +"> ?existingAgentLabel .\n"+
        "?existingAgent a ?agentType . \n " +
        "?agentType rdfs:subClassOf <http://xmlns.com/foaf/0.1/Agent> . }" ;


	/* Limit type to subclasses of foaf:Agent. Otherwise, sometimes owl:Thing or another
    type is returned and we don't get a match to the select element options. */
	
    final static String agentTypeQuery  =
        "PREFIX rdfs: <"+ rdfs +">   \n"+
        "SELECT ?agentType WHERE {\n"+
        "?obProduction <http://ontology.tib.eu/gesah/has_participant> ?existingAgent . \n" +
		"?existingAgent <http://ontology.tib.eu/gesah/participates_in> ?obProduction . \n" +
		"?existingAgent <http://ontology.tib.eu/gesah/has_role> ?existingRole .\n" +
		"?existingRole <http://ontology.tib.eu/gesah/is_role_of> ?existingAgent .\n" +
        "?existingAgent a ?agentType .\n"+
        "?agentType rdfs:subClassOf <http://xmlns.com/foaf/0.1/Agent> .}";		
	
    final static String commentQuery  =
        "SELECT ?existingComment WHERE {\n"+
        "?obProduction <"+ commentPred +"> ?existingComment . }";

    final static String litDateAppelQuery  =
        "SELECT ?existinglitDateAppel WHERE {\n"+
        "?obProduction <"+ literalDateAppelPred +"> ?existinglitDateAppel . }";

    final static String existingIntervalNodeQuery  =
        "SELECT ?existingIntervalNode WHERE {\n"+
        "?obProduction <"+ toInterval +"> ?existingIntervalNode .\n"+
        "?existingIntervalNode <"+ type +"> <"+ intervalType +"> . }";

    final static String existingStartNodeQuery  =
        "SELECT ?existingStartNode WHERE {\n"+
        "?obProduction <"+ toInterval +"> ?intervalNode .\n"+
        "?intervalNode <"+ type +"> <"+ intervalType +"> .\n"+
        "?intervalNode <"+ intervalToStart +"> ?existingStartNode . \n"+
        "?existingStartNode <"+ type +"> <"+ dateTimeValueType +"> .}";

    final static String existingStartDateQuery  =
        "SELECT ?existingDateStart WHERE {\n"+
        "?obProduction <"+ toInterval +"> ?intervalNode .\n"+
        "?intervalNode <"+ type +"> <"+ intervalType +"> .\n"+
        "?intervalNode <"+ intervalToStart +"> ?startNode .\n"+
        "?startNode <"+ type +"> <"+ dateTimeValueType +"> .\n"+
        "?startNode <"+ dateTimeValue +"> ?existingDateStart . }";

    final static String existingStartPrecisionQuery  =
        "SELECT ?existingStartPrecision WHERE {\n"+
        "?obProduction <"+ toInterval +"> ?intervalNode .\n"+
        "?intervalNode <"+ type +"> <"+ intervalType +"> .\n"+
        "?intervalNode <"+ intervalToStart +"> ?startNode .\n"+
        "?startNode <"+ type +"> <"+ dateTimeValueType +"> . \n"+
        "?startNode <"+ dateTimePrecision +"> ?existingStartPrecision . }";

    final static String existingEndNodeQuery  =
        "SELECT ?existingEndNode WHERE { \n"+
        "?obProduction <"+ toInterval +"> ?intervalNode .\n"+
        "?intervalNode <"+ type +"> <"+ intervalType +"> .\n"+
        "?intervalNode <"+ intervalToEnd +"> ?existingEndNode . \n"+
        "?existingEndNode <"+ type +"> <"+ dateTimeValueType +"> .}";

    final static String existingEndDateQuery  =
        "SELECT ?existingEndDate WHERE {\n"+
        "?obProduction <"+ toInterval +"> ?intervalNode .\n"+
        "?intervalNode <"+ type +"> <"+ intervalType +"> .\n"+
        "?intervalNode <"+ intervalToEnd +"> ?endNode .\n"+
        "?endNode <"+ type +"> <"+ dateTimeValueType +"> .\n"+
        "?endNode <"+ dateTimeValue +"> ?existingEndDate . }";

    final static String existingEndPrecisionQuery  =
        "SELECT ?existingEndPrecision WHERE {\n"+
        "?obProduction <"+ toInterval +"> ?intervalNode .\n"+
        "?intervalNode <"+ type +"> <"+ intervalType +"> .\n"+
        "?intervalNode <"+ intervalToEnd +"> ?endNode .\n"+
        "?endNode <"+ type +"> <"+ dateTimeValueType +"> .\n"+
        "?endNode <"+ dateTimePrecision +"> ?existingEndPrecision . }";

    //Query for inverse property
    final static String productionHasOutputQuery  =
    	  "PREFIX owl:  <http://www.w3.org/2002/07/owl#>"
			+ " SELECT ?productionHasOutput "
			+ "    WHERE { ?productionHasOutput owl:inverseOf <http://ontology.tib.eu/gesah/output_of_production> . } ";


	final static String existingRoleQuery =
			"SELECT ?existingRole WHERE {\n"+
			"?obCreation <http://ontology.tib.eu/gesah/realizes> ?existingRole  . }";

	@Override
	protected EditMode getEditMode(EditConfigurationVTwo editConf, VitroRequest vreq) {
	List<String> predicates = new ArrayList<String>();
		predicates.add("http://ontology.tib.eu/gesah/output_of_production");
		return EditModeUtils.getEditMode(vreq, predicates);
	}
	
}
