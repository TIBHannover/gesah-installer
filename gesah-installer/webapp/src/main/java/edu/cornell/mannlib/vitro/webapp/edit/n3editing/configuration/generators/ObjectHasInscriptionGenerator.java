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
    gesah:Inscription_Activity - primary new individual being created
    foaf:Person - new or existing individual
    foaf:Organization - new or existing individual - has to be implemented
    gesah:Cultural_Object - existing individual
	obo:BFO_0000023 - new individual or existing while being edited
	gesah:Role_Type - existing individual while being edited
	gesah:Inscription - new individual or existing while being edited
	gesah:Inscription_Type - existing individual 
	



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
public class ObjectHasInscriptionGenerator extends GesahEditConfigurationGenerator implements EditConfigurationGenerator{
	private final static String agentClass = foaf + "Agent";
	private final static String inscriptionOutputClass = gesah + "Inscription";
	private final static String roleClass =obo + "BFO_0000023" ;

	private final static String inscriptionTypeClass =gesah+"Inscription_Type" ;
	private final static String roleTypeClass =gesah +"Role_Type";

	private final static String transcriptionPred =gesah+"transcription" ;
	private final static String commentPred =gesah+"comment" ;

	@Override
	protected void configureForm(VitroRequest vreq, HttpSession session, EditConfigurationVTwo conf) throws Exception {
        conf.setTemplate("objectHasInscription.ftl");

        conf.setVarNameForSubject("cultObject");
        conf.setVarNameForPredicate("predicate");
        conf.setVarNameForObject("obInscription");

        conf.setN3Required(Arrays.asList(n3ForNewObInscription) );
        conf.setN3Optional(Arrays.asList( transcriptionAssertion,  n3ForNewInscType, n3ForExistingInscType, n3ForNewAgent, n3ForExistingAgent, n3ForNewRole, n3ForNewInscriptionOutput,
                n3ForNewRoleType, n3ForExistingRoleType, transcriptionAssertion,  commentAssertion ));

        conf.addNewResource("obInscription", DEFAULT_NS_FOR_NEW_RESOURCE);
        conf.addNewResource("newRole",DEFAULT_NS_FOR_NEW_RESOURCE);
		conf.addNewResource("newRoleType",DEFAULT_NS_FOR_NEW_RESOURCE);
		conf.addNewResource("newInscriptionOutput",DEFAULT_NS_FOR_NEW_RESOURCE);
		conf.addNewResource("newInscType",DEFAULT_NS_FOR_NEW_RESOURCE);
        conf.addNewResource("newAgent",DEFAULT_NS_FOR_NEW_RESOURCE);
		

       // uris in scope: none
       // literals in scope: none

        conf.setUrisOnform( Arrays.asList( "inscriptionOutput", "inscriptionOutputType", "existingAgent", "agentType", "existingInscType", "existingRoleType" ));
        conf.setLiteralsOnForm( Arrays.asList("agentLabel", "roleTypeLabel", "agentLabelDisplay", "existingInscTypeLabel", "transcription", "comment"));

        conf.addSparqlForExistingLiteral("agentLabel", agentLabelQuery);
		conf.addSparqlForExistingLiteral("inscTypeLabel", existingInscTypeLabelQuery);
		conf.addSparqlForExistingLiteral("roleTypeLabel", existingRoleTypeLabelQuery);
        conf.addSparqlForExistingLiteral("transcription", transcriptionQuery);
        conf.addSparqlForExistingLiteral("comment", commentQuery);
        


        
        conf.addSparqlForExistingUris("existingAgent", existingAgentQuery);
        conf.addSparqlForExistingUris("existingInscType", existingInscTypeQuery);
        conf.addSparqlForExistingUris("existingRoleType", existingRoleTypeQuery);
		conf.addSparqlForExistingUris("newRole", existingRoleQuery);
		conf.addSparqlForExistingUris("agentType", agentTypeQuery);
        conf.addSparqlForExistingUris("inscriptionOutputType", inscriptionOutputTypeQuery);
		//conf.addSparqlForExistingUris("inscriptionOutput", inscriptionOutputQuery);
        
        //Add sparql to include inverse property as well
        conf.addSparqlForAdditionalUrisInScope("hasInscriptionObject", hasInscriptionObjectQuery);
			
					

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
                setName("agentLabel").
                setRangeDatatypeUri(XSD.xstring.toString() ).
                setValidators( list("datatype:" + XSD.xstring.toString())));				

		conf.addField( new FieldVTwo().
                setName("inscriptionOutputType").
				setValidators( list("nonempty")).
                setOptions( new ChildVClassesOptions(
                        inscriptionOutputClass)));					

        conf.addField( new FieldVTwo().
                setName("transcription").
                setRangeDatatypeUri( XSD.xstring.toString() ).
                setValidators(list("datatype:" + XSD.xstring.toString())));
				
		conf.addField( new FieldVTwo().
                setName("comment").
                setRangeDatatypeUri( XSD.xstring.toString() ).
                setValidators(list("datatype:" + XSD.xstring.toString())));		


        conf.addField( new FieldVTwo().
                setName("existingInscType").
                setOptions( new IndividualsViaVClassOptions(
                        inscriptionTypeClass)));
                


        conf.addField( new FieldVTwo().
                setName("existingInscTypeLabel").
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
                setName("existingRoleType").
                setOptions( new IndividualsViaVClassOptions(
                        roleTypeClass)));
						
		
 				
		conf.addField( new FieldVTwo().
                setName("newRole").
                setOptions( new IndividualsViaVClassOptions(
                        roleClass)));	

		conf.addField( new FieldVTwo().
                setName("newInscriptionOutput").
                setOptions( new IndividualsViaVClassOptions(
                        inscriptionOutputClass)));					
    

        
    }

    /* N3 assertions for production of a cultural object */

    final static String n3ForNewObInscription =
		"@prefix rdfs: <"+ rdfs +">   .\n"+
        "?cultObject <http://ontology.tib.eu/gesah/object_of_inscription>  ?obInscription .\n" +
        "?obInscription  a <http://ontology.tib.eu/gesah/Inscription_Activity> . \n" +
		"?obInscription	 <http://ontology.tib.eu/gesah/realizes> ?newRole . \n" +
		"?obInscription	 <http://ontology.tib.eu/gesah/has_inscription_output> ?newInscriptionOutput . \n" +
		"?newInscriptionOutput a ?inscriptionOutputType . \n" +
		"?inscriptionOutputType rdfs:subClassOf <http://ontology.tib.eu/gesah/Inscription> . \n" +
		"?newInscriptionOutput <http://ontology.tib.eu/gesah/output_of_inscription> ?obInscription . \n" +
		"?newRole <http://ontology.tib.eu/gesah/realized_in> ?obInscription . \n" +
		//"?newRole a <http://purl.obolibrary.org/obo/BFO_0000023> . \n" +
        "?obInscription <http://ontology.tib.eu/gesah/has_inscription_object> ?cultObject .";
		
	final static String n3ForNewInscriptionOutput  =
		"?obInscription <http://ontology.tib.eu/gesah/has_inscription_output> ?newInscriptionOutput . \n" +
		"?newInscriptionOutput <http://ontology.tib.eu/gesah/output_of_inscription> ?obInscription . \n" +
		"?newInscriptionOutput a ?inscriptionOutputType  . \n" +
		"?inscriptionOutputType rdfs:subClassOf <http://ontology.tib.eu/gesah/Inscription> ." ;
		
	final static String n3ForNewAgent  =
		"@prefix rdfs: <"+ rdfs +">   .\n"+
        "?obInscription <http://ontology.tib.eu/gesah/has_participant> ?newAgent . \n" +
        "?newAgent <http://ontology.tib.eu/gesah/participates_in> ?obInscription . \n" +
		"?obInscription <http://ontology.tib.eu/gesah/realizes> ?newRole . \n" +
		"?newAgent <http://ontology.tib.eu/gesah/has_role> ?newRole . \n" +
		"?newRole <http://ontology.tib.eu/gesah/is_role_of> ?newAgent . \n" +
		"?newRole <http://ontology.tib.eu/gesah/realized_in> ?obInscription . \n" +
        "?newAgent a ?agentType . \n" +
		"?agentType rdfs:subClassOf <http://xmlns.com/foaf/0.1/Agent> .\n" +
        "?newAgent rdfs:label ?agentLabel . ";

    final static String n3ForExistingAgent  =
		"@prefix rdfs: <"+ rdfs +">  . \n"+
        "?obInscription <http://ontology.tib.eu/gesah/has_participant> ?existingAgent . \n" +
		"?obInscription <http://ontology.tib.eu/gesah/realizes> ?newRole . \n" +
        "?existingAgent <http://ontology.tib.eu/gesah/participates_in> ?obInscription . \n" +
		"?existingAgent <http://ontology.tib.eu/gesah/has_role> ?newRole . \n" +
		"?newRole <http://ontology.tib.eu/gesah/realized_in> ?obInscription . \n" +
		"?newRole <http://ontology.tib.eu/gesah/is_role_of> ?existingAgent . " ;

	final static String n3ForNewRole  =
		"?obInscription <http://ontology.tib.eu/gesah/realizes> ?newRole . \n" +
		"?newRole <http://ontology.tib.eu/gesah/realized_in> ?obInscription . \n" +
		"?newRole a <http://purl.obolibrary.org/obo/BFO_0000023> . " ;
		

	final static String n3ForNewRoleType  =
		"@prefix rdfs: <"+ rdfs +">  . \n"+
        "?obInscription <http://ontology.tib.eu/gesah/realizes> ?newRole . \n" +
        "?newRole <http://ontology.tib.eu/gesah/realized_in> ?obInscription . \n" +
		"?newRole <http://ontology.tib.eu/gesah/has_role_type> ?newRoleType . \n" +
        "?newRoleType <"+ label +"> ?newRoleTypeLabel . \n" +
		"?newRoleType a  <http://ontology.tib.eu/gesah/Role_Type> . " ;
			
	final static String n3ForExistingRoleType  =
		"?obInscription <http://ontology.tib.eu/gesah/realizes> ?newRole . \n" +
        "?newRole <http://ontology.tib.eu/gesah/realized_in> ?obInscription . \n" +
        "?newRole <http://ontology.tib.eu/gesah/has_role_type> ?existingRoleType . " ;		


    final static String n3ForNewInscType  =
        //"@prefix gesah: <"+ gesah +"> .\n"+
		"@prefix rdfs: <"+ rdfs +">   .\n"+
		"?obInscription <http://ontology.tib.eu/gesah/has_inscription_output> ?newInscriptionOutput . \n" +
        "?newInscriptionOutput <http://ontology.tib.eu/gesah/has_inscription_type> ?newInscType . \n" +
        "?newInscType <http://ontology.tib.eu/gesah/inscription_type_of> ?newInscriptionOutput . \n" +
        "?newInscType <"+ label +"> ?inscTypeLabel . \n" +
        "?newInscType a <http://ontology.tib.eu/gesah/Inscription_Type> .";
		
	final static String n3ForExistingInscType  =
        //"@prefix gesah: <"+ gesah +"> .\n"+
		"?obInscription <http://ontology.tib.eu/gesah/has_inscription_output> ?newInscriptionOutput . \n" +
        "?newInscriptionOutput <http://ontology.tib.eu/gesah/has_inscription_type> ?existingInscType . \n" +
        "?existingInscType <http://ontology.tib.eu/gesah/inscription_type_of> ?newInscriptionOutput . \n" +
        "?existingInscType a <http://ontology.tib.eu/gesah/Inscription_Type> .";	
	

		
    final static String transcriptionAssertion  =
        "?newInscriptionOutput <http://ontology.tib.eu/gesah/transcription> ?transcription .";
		
	final static String commentAssertion  =
        "?obInscription <http://ontology.tib.eu/gesah/comment> ?comment .";	

    
 
  //Queries for editing an existing production entry
  
	 
    final static String existingInscTypeQuery =
        "SELECT ?existingInscType WHERE {\n"+
        "?obInscription <http://ontology.tib.eu/gesah/has_inscription_output> ?newInscriptionOutput . \n" +
        "?newInscriptionOutput <http://ontology.tib.eu/gesah/has_inscription_type> ?existingInscType . }";

    final static String existingInscTypeLabelQuery =
		"PREFIX rdfs: <"+ rdfs +">   \n"+
        "SELECT Distinct ?existingInscTypeLabel WHERE {\n"+
        "?obInscription <http://ontology.tib.eu/gesah/has_inscription_output> ?newInscriptionOutput . \n" +
        "?newInscriptionOutput <http://ontology.tib.eu/gesah/has_inscription_type> ?existingInscType . \n" +
        "?existingInscType <"+ label +"> ?existingInscTypeLabel . }";

	
	final static String existingRoleTypeQuery =
        "SELECT ?existingRoleType WHERE {\n"+
        "?obInscription <http://ontology.tib.eu/gesah/realizes> ?existingRole . \n" +
        "?existingRole <http://ontology.tib.eu/gesah/realized_in> ?obInscription . \n" +
		"?existingRole <http://ontology.tib.eu/gesah/has_role_type> ?existingRoleType . }";
		

    final static String existingRoleTypeLabelQuery =
		"PREFIX rdfs: <"+ rdfs +">   \n"+
        "SELECT Distinct ?existingRoleTypeLabel WHERE {\n"+
        "?obInscription <http://ontology.tib.eu/gesah/realizes> ?existingRole . \n" +
        "?existingRole <http://ontology.tib.eu/gesah/realized_in> ?obInscription . \n" +
		"?existingRole <http://ontology.tib.eu/gesah/has_role_type> ?existingRoleType . \n" +
        "?existingRoleType <"+ label +"> ?existingRoleTypeLabel . }";		
			
	
    final static String existingAgentQuery  =
        "PREFIX rdfs: <"+ rdfs +">   \n"+
        "SELECT ?existingAgent WHERE {\n"+
        "?obInscription <http://ontology.tib.eu/gesah/has_participant> ?existingAgent . \n" +
		"?existingAgent <http://ontology.tib.eu/gesah/participates_in> ?obInscription . \n" +
		"?existingAgent <http://ontology.tib.eu/gesah/has_role> ?existingRole .\n" +
		"?existingRole <http://ontology.tib.eu/gesah/is_role_of> ?existingAgent .\n" +
		"?existingAgent a ?agentType . \n " +
        "?agentType rdfs:subClassOf <http://xmlns.com/foaf/0.1/Agent> . }" ;

    final static String agentLabelQuery  =
        "PREFIX rdfs: <"+ rdfs +">   \n"+
        "SELECT Distinct ?existingAgentLabel WHERE {\n"+
        "?obInscription <http://ontology.tib.eu/gesah/has_participant> ?existingAgent . \n" +
		"?existingAgent <http://ontology.tib.eu/gesah/participates_in> ?obInscription . \n" +
        "?existingAgent <"+ label +"> ?existingAgentLabel .\n"+
        "?existingAgent a ?agentType . \n " +
        "?agentType rdfs:subClassOf <http://xmlns.com/foaf/0.1/Agent> . }" ;


	/* Limit type to subclasses of foaf:Agent. Otherwise, sometimes owl:Thing or another
    type is returned and we don't get a match to the select element options. */
	
    final static String agentTypeQuery  =
        "PREFIX rdfs: <"+ rdfs +">   \n"+
        "SELECT ?agentType WHERE {\n"+
        "?obInscription <http://ontology.tib.eu/gesah/has_participant> ?existingAgent . \n" +
		"?existingAgent <http://ontology.tib.eu/gesah/participates_in> ?obInscription . \n" +
		"?existingAgent <http://ontology.tib.eu/gesah/has_role> ?existingRole .\n" +
		"?existingRole <http://ontology.tib.eu/gesah/is_role_of> ?existingAgent .\n" +
        "?existingAgent a ?agentType .\n"+
        "?agentType rdfs:subClassOf <http://xmlns.com/foaf/0.1/Agent> .}";		
		
	final static String inscriptionOutputTypeQuery  =
        "PREFIX rdfs: <"+ rdfs +">   \n"+
        "SELECT ?inscriptionOutputType WHERE {\n"+
        "?obInscription <http://ontology.tib.eu/gesah/has_inscription_output> ?newInscriptionOutput . \n" +
		"?newInscriptionOutput <http://ontology.tib.eu/gesah/output_of_inscription> ?obInscription . \n" +
		"?newInscriptionOutput a ?inscriptionOutputType  . \n" +
		"?inscriptionOutputType rdfs:subClassOf <http://ontology.tib.eu/gesah/Inscription> . }";		
	
    final static String transcriptionQuery  =
        "SELECT ?existingTranscription WHERE {\n"+
        "?newInscriptionOutput <"+ transcriptionPred +"> ?existingTranscription . }";

    final static String commentQuery  =
        "SELECT ?existingComment WHERE {\n"+
        "?obInscription <"+ commentPred +"> ?existingComment . }";


    //Query for inverse property
    final static String hasInscriptionObjectQuery  =
    	  "PREFIX owl:  <http://www.w3.org/2002/07/owl#>"
			+ " SELECT ?hasInscriptionObject "
			+ "    WHERE { ?hasInscriptionObject owl:inverseOf <http://ontology.tib.eu/gesah/object_of_inscription> . } ";

    final static String existingRoleQuery =
			"SELECT ?existingRole WHERE {\n"+
			"?obCreation <http://ontology.tib.eu/gesah/realizes> ?existingRole  . }";
    
	@Override
	protected EditMode getEditMode(EditConfigurationVTwo editConf, VitroRequest vreq) {
	List<String> predicates = new ArrayList<String>();
		predicates.add("http://ontology.tib.eu/gesah/object_of_inscription");
		return EditModeUtils.getEditMode(vreq, predicates);
	}
	
}
