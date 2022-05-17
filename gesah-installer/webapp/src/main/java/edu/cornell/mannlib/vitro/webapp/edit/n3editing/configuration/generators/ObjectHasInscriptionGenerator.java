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
	private static final String OBJECT_OF_INSCRIPTION = "object_of_inscription";
	private static final String NEW_AGENT = "newAgent";
	private static final String NEW_INSC_TYPE = "newInscType";
	private static final String NEW_INSCRIPTION_OUTPUT = "newInscriptionOutput";
	private static final String NEW_ROLE_TYPE = "newRoleType";
	private static final String NEW_ROLE = "newRole";
	private static final String OB_INSCRIPTION = "obInscription";
	private static final String PREDICATE = "predicate";
	private static final String CULT_OBJECT = "cultObject";
	private static final String OBJECT_HAS_INSCRIPTION_FTL = "objectHasInscription.ftl";
	private static final String EXISTING_AGENT = "existingAgent";
	private final static String AGENT_CLASS = FOAF + "Agent";
	private final static String INSCRIPTION_OUTPUT_CLASS = GESAH + "Inscription";
	private final static String ROLE_CLASS = OBO + "BFO_0000023";

	private final static String INSCRIPTION_TYPE_CLASS = GESAH + "Inscription_Type";
	private final static String ROLE_TYPE_CLASS = GESAH + "Role_Type";

	private final static String TRANSCRIPTION_PRED = GESAH + "transcription";
	private final static String COMMENT_PRED = GESAH + "comment";

	@Override
	protected void configureForm(VitroRequest vreq, HttpSession session, EditConfigurationVTwo conf) throws Exception {
        conf.setTemplate(OBJECT_HAS_INSCRIPTION_FTL);

        conf.setVarNameForSubject(CULT_OBJECT);
        conf.setVarNameForPredicate(PREDICATE);
        conf.setVarNameForObject(OB_INSCRIPTION);

        conf.setN3Required(Arrays.asList(n3ForNewObInscription) );
        conf.addN3Optional(Arrays.asList(n3ForNewInscType));
        conf.addN3Optional(Arrays.asList(n3ForExistingInscType));
        conf.addN3Optional(Arrays.asList(n3ForExistingAgent));
        conf.addN3Optional(Arrays.asList(n3ForNewInscriptionOutput));
        conf.addN3Optional(Arrays.asList(n3ForNewRole));
        conf.addN3Optional(Arrays.asList(n3ForNewRoleType));
        conf.addN3Optional(Arrays.asList(n3ForExistingRoleType));
        conf.addN3Optional(Arrays.asList(commentAssertion));
        
        conf.addNewResource(OB_INSCRIPTION, DEFAULT_NS_FOR_NEW_RESOURCE);
        conf.addNewResource(NEW_ROLE,DEFAULT_NS_FOR_NEW_RESOURCE);
        conf.addNewResource(NEW_ROLE_TYPE,DEFAULT_NS_FOR_NEW_RESOURCE);
        conf.addNewResource(NEW_INSCRIPTION_OUTPUT,DEFAULT_NS_FOR_NEW_RESOURCE);
        conf.addNewResource(NEW_INSC_TYPE,DEFAULT_NS_FOR_NEW_RESOURCE);
        
        conf.addN3Optional(Arrays.asList(n3ForNewAgent));
        conf.addNewResource(NEW_AGENT,DEFAULT_NS_FOR_NEW_RESOURCE);
        conf.addField( new FieldVTwo().
            setName(NEW_AGENT).
            setOptions( new IndividualsViaVClassOptions(
                    AGENT_CLASS)));
        
        
        conf.addUrisOnForm( Arrays.asList(EXISTING_AGENT));
        conf.addSparqlForExistingUris(EXISTING_AGENT, existingAgentQuery);
        conf.addField( new FieldVTwo().
            setName(EXISTING_AGENT).
            setOptions( new IndividualsViaVClassOptions(
                    AGENT_CLASS)));	  
        
        conf.addLiteralsOnForm( Arrays.asList("agentLabel"));
        conf.addSparqlForExistingLiteral("agentLabel", agentLabelQuery);
        conf.addField( new FieldVTwo().
            setName("agentLabel").
            setRangeDatatypeUri(XSD.xstring.toString() ).
            setValidators( list("datatype:" + XSD.xstring.toString())));	
        
        conf.addLiteralsOnForm( Arrays.asList("agentLabelDisplay"));
        conf.addField( new FieldVTwo().
            setName("agentLabelDisplay").
            setRangeDatatypeUri(XSD.xstring.toString() ));
        
        
        conf.addUrisOnForm( Arrays.asList("agentType"));
        conf.addSparqlForExistingUris("agentType", agentTypeQuery);
        conf.addField( new FieldVTwo().
            setName("agentType").
            setValidators( list("nonempty")).
            setOptions( new ChildVClassesOptions(
                    AGENT_CLASS)));		



       // uris in scope: none
       // literals in scope: none

        conf.addUrisOnForm( Arrays.asList("inscriptionOutput"));
        conf.addUrisOnForm( Arrays.asList("inscriptionOutputType"));
        conf.addUrisOnForm( Arrays.asList("existingInscType"));
        conf.addUrisOnForm( Arrays.asList("existingRoleType"));
        conf.addLiteralsOnForm( Arrays.asList("roleTypeLabel"));
        conf.addLiteralsOnForm( Arrays.asList("existingInscTypeLabel"));
        
        
        conf.addN3Optional(Arrays.asList(transcriptionAssertion));
        conf.addLiteralsOnForm( Arrays.asList("transcription"));
        conf.addSparqlForExistingLiteral("transcription", transcriptionQuery);
        conf.addField( new FieldVTwo().
            setName("transcription").
            setRangeDatatypeUri( org.apache.jena.vocabulary.RDFS.Literal.getURI() ).
            setValidators(list("datatype:" + XSD.xstring.toString())));

        
        conf.addLiteralsOnForm( Arrays.asList("comment"));

        conf.addSparqlForExistingLiteral("inscTypeLabel", existingInscTypeLabelQuery);
        conf.addSparqlForExistingLiteral("roleTypeLabel", existingRoleTypeLabelQuery);
        conf.addSparqlForExistingLiteral("comment", commentQuery);
        conf.addSparqlForExistingUris("existingInscType", existingInscTypeQuery);
        conf.addSparqlForExistingUris("existingRoleType", existingRoleTypeQuery);
        conf.addSparqlForExistingUris(NEW_ROLE, existingRoleQuery);
        conf.addSparqlForExistingUris("inscriptionOutputType", inscriptionOutputTypeQuery);
		//conf.addSparqlForExistingUris("inscriptionOutput", inscriptionOutputQuery);
        
        //Add sparql to include inverse property as well
        conf.addSparqlForAdditionalUrisInScope("hasInscriptionObject", hasInscriptionObjectQuery);
			
        conf.addField( new FieldVTwo().
                setName("inscriptionOutputType").
                setValidators( list("nonempty")).
                setOptions( new ChildVClassesOptions(
                        INSCRIPTION_OUTPUT_CLASS)));					

        conf.addField( new FieldVTwo().
                setName("comment").
                setRangeDatatypeUri( XSD.xstring.toString() ).
                setValidators(list("datatype:" + XSD.xstring.toString())));		


        conf.addField( new FieldVTwo().
                setName("existingInscType").
                setOptions( new IndividualsViaVClassOptions(
                        INSCRIPTION_TYPE_CLASS)));
                
        conf.addField( new FieldVTwo().
                setName("existingInscTypeLabel").
                setRangeDatatypeUri(XSD.xstring.toString() ).
                setValidators( list("datatype:" + XSD.xstring.toString())));
		
        conf.addField( new FieldVTwo().
                setName("roleTypeLabel").
                setRangeDatatypeUri(XSD.xstring.toString() ).
                setValidators( list("datatype:" + XSD.xstring.toString())));		


				
        conf.addField( new FieldVTwo().
                setName("existingRoleType").
                setOptions( new IndividualsViaVClassOptions(
                        ROLE_TYPE_CLASS)));
						
        conf.addField( new FieldVTwo().
                setName(NEW_ROLE).
                setOptions( new IndividualsViaVClassOptions(
                        ROLE_CLASS)));	

        conf.addField( new FieldVTwo().
                setName(NEW_INSCRIPTION_OUTPUT).
                setOptions( new IndividualsViaVClassOptions(
                        INSCRIPTION_OUTPUT_CLASS)));					
    }

    /* N3 assertions for production of a cultural object */

    final static String n3ForNewObInscription =
    		"@prefix rdfs: <"+ RDFS +">   .\n"+
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
				"@prefix rdfs: <"+ RDFS +">   .\n"+
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
    		"@prefix rdfs: <"+ RDFS +">  . \n"+
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
    		"@prefix rdfs: <"+ RDFS +">  . \n"+
        "?obInscription <http://ontology.tib.eu/gesah/realizes> ?newRole . \n" +
        "?newRole <http://ontology.tib.eu/gesah/realized_in> ?obInscription . \n" +
        "?newRole <http://ontology.tib.eu/gesah/has_role_type> ?newRoleType . \n" +
        "?newRoleType <"+ LABEL +"> ?newRoleTypeLabel . \n" +
        "?newRoleType a  <http://ontology.tib.eu/gesah/Role_Type> . " ;
			
    final static String n3ForExistingRoleType  =
				"?obInscription <http://ontology.tib.eu/gesah/realizes> ?newRole . \n" +
        "?newRole <http://ontology.tib.eu/gesah/realized_in> ?obInscription . \n" +
        "?newRole <http://ontology.tib.eu/gesah/has_role_type> ?existingRoleType . " ;		

    final static String n3ForNewInscType  =
        //"@prefix gesah: <"+ gesah +"> .\n"+
    		"@prefix rdfs: <"+ RDFS +">   .\n"+
    		"?obInscription <http://ontology.tib.eu/gesah/has_inscription_output> ?newInscriptionOutput . \n" +
        "?newInscriptionOutput <http://ontology.tib.eu/gesah/has_inscription_type> ?newInscType . \n" +
        "?newInscType <http://ontology.tib.eu/gesah/inscription_type_of> ?newInscriptionOutput . \n" +
        "?newInscType <"+ LABEL +"> ?inscTypeLabel . \n" +
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
    		"PREFIX rdfs: <"+ RDFS +">   \n"+
        "SELECT Distinct ?existingInscTypeLabel WHERE {\n"+
        "?obInscription <http://ontology.tib.eu/gesah/has_inscription_output> ?newInscriptionOutput . \n" +
        "?newInscriptionOutput <http://ontology.tib.eu/gesah/has_inscription_type> ?existingInscType . \n" +
        "?existingInscType <"+ LABEL +"> ?existingInscTypeLabel . }";

    final static String existingRoleTypeQuery =
        "SELECT ?existingRoleType WHERE {\n"+
        "?obInscription <http://ontology.tib.eu/gesah/realizes> ?existingRole . \n" +
        "?existingRole <http://ontology.tib.eu/gesah/realized_in> ?obInscription . \n" +
        "?existingRole <http://ontology.tib.eu/gesah/has_role_type> ?existingRoleType . }";

    final static String existingRoleTypeLabelQuery =
    		"PREFIX rdfs: <"+ RDFS +">   \n"+
        "SELECT Distinct ?existingRoleTypeLabel WHERE {\n"+
        "?obInscription <http://ontology.tib.eu/gesah/realizes> ?existingRole . \n" +
        "?existingRole <http://ontology.tib.eu/gesah/realized_in> ?obInscription . \n" +
        "?existingRole <http://ontology.tib.eu/gesah/has_role_type> ?existingRoleType . \n" +
        "?existingRoleType <"+ LABEL +"> ?existingRoleTypeLabel . }";		
	
    final static String existingAgentQuery  =
        "PREFIX rdfs: <"+ RDFS +">   \n"+
        "SELECT ?existingAgent WHERE {\n"+
        "?obInscription <http://ontology.tib.eu/gesah/has_participant> ?existingAgent . \n" +
        "?existingAgent <http://ontology.tib.eu/gesah/participates_in> ?obInscription . \n" +
        "?existingAgent <http://ontology.tib.eu/gesah/has_role> ?existingRole .\n" +
        "?existingRole <http://ontology.tib.eu/gesah/is_role_of> ?existingAgent .\n" +
        "?existingAgent a ?agentType . \n " +
        "?agentType rdfs:subClassOf <http://xmlns.com/foaf/0.1/Agent> . }" ;

    final static String agentLabelQuery  =
        "PREFIX rdfs: <"+ RDFS +">   \n"+
        "SELECT Distinct ?existingAgentLabel WHERE {\n"+
        "?obInscription <http://ontology.tib.eu/gesah/has_participant> ?existingAgent . \n" +
		"?existingAgent <http://ontology.tib.eu/gesah/participates_in> ?obInscription . \n" +
        "?existingAgent <"+ LABEL +"> ?existingAgentLabel .\n"+
        "?existingAgent a ?agentType . \n " +
        "?agentType rdfs:subClassOf <http://xmlns.com/foaf/0.1/Agent> . }" ;


	/* Limit type to subclasses of foaf:Agent. Otherwise, sometimes owl:Thing or another
    type is returned and we don't get a match to the select element options. */
	
    final static String agentTypeQuery  =
        "PREFIX rdfs: <"+ RDFS +">   \n"+
        "SELECT ?agentType WHERE {\n"+
        "?obInscription <http://ontology.tib.eu/gesah/has_participant> ?existingAgent . \n" +
        "?existingAgent <http://ontology.tib.eu/gesah/participates_in> ?obInscription . \n" +
        "?existingAgent <http://ontology.tib.eu/gesah/has_role> ?existingRole .\n" +
        "?existingRole <http://ontology.tib.eu/gesah/is_role_of> ?existingAgent .\n" +
        "?existingAgent a ?agentType .\n"+
        "?agentType rdfs:subClassOf <http://xmlns.com/foaf/0.1/Agent> .}";		
		
    final static String inscriptionOutputTypeQuery  =
        "PREFIX rdfs: <"+ RDFS +">   \n"+
        "SELECT ?inscriptionOutputType WHERE {\n"+
        "?obInscription <http://ontology.tib.eu/gesah/has_inscription_output> ?newInscriptionOutput . \n" +
        "?newInscriptionOutput <http://ontology.tib.eu/gesah/output_of_inscription> ?obInscription . \n" +
        "?newInscriptionOutput a ?inscriptionOutputType  . \n" +
        "?inscriptionOutputType rdfs:subClassOf <http://ontology.tib.eu/gesah/Inscription> . }";		
	
    final static String transcriptionQuery  =
        "SELECT (STR(?existingTranscriptionLit) as ?existingTranscription) WHERE {\n"+
        "?newInscriptionOutput <"+ TRANSCRIPTION_PRED +"> ?existingTranscriptionLit . }";

    final static String commentQuery  =
        "SELECT ?existingComment WHERE {\n"+
        "?obInscription <"+ COMMENT_PRED +"> ?existingComment . }";

    //Query for inverse property
    final static String hasInscriptionObjectQuery  =
    	  "PREFIX owl:  <http://www.w3.org/2002/07/owl#>" +
			  " SELECT ?hasInscriptionObject " +
			  " WHERE { ?hasInscriptionObject owl:inverseOf " + "<" + GESAH + OBJECT_OF_INSCRIPTION + "> . } ";

    final static String existingRoleQuery =
    		"SELECT ?existingRole WHERE {\n"+
    		"?obCreation <" + GESAH + "realizes" + "> ?existingRole  . }";
    
		@Override
		protected EditMode getEditMode(EditConfigurationVTwo editConf, VitroRequest vreq) {
			List<String> predicates = new ArrayList<String>();
			predicates.add(GESAH + OBJECT_OF_INSCRIPTION);
			return EditModeUtils.getEditMode(vreq, predicates);
		}
	
}
