/* $This file is distributed under the terms of the license in LICENSE$ */
package edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.jena.vocabulary.XSD;

import edu.cornell.mannlib.vitro.webapp.controller.VitroRequest;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.EditConfigurationVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.fields.ChildVClassesOptions;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.fields.FieldVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.fields.IndividualsViaVClassOptions;
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
public class ObjectHasInscriptionGenerator extends AbstractCulturalObjectGenerator implements EditConfigurationGenerator{
	private static final String EXISTING_INSC_TYPE_LABEL = "existingInscTypeLabel";
	private static final String INSCRIPTION_SPECIFICATION = "inscriptionSpecification";
	private static final String INSCRIPTION_OUTPUT_TYPE = "inscriptionOutputType";
	private static final String OBJECT_OF_INSCRIPTION = "object_of_inscription";
	private static final String INSCRIPTION_OUTPUT = "inscriptionOutput";
	private static final String OBJECT_HAS_INSCRIPTION_FTL = "objectHasInscription.ftl";
	private final static String INSCRIPTION_OUTPUT_CLASS = GESAH + "Inscription";
	private final static String INSCRIPTION_TYPE_CLASS = GESAH + "Inscription_Type";
	private final static String TRANSCRIPTION_PRED = GESAH + "transcription";

	@Override
	protected void configureForm(VitroRequest vreq, HttpSession session, EditConfigurationVTwo conf) throws Exception {
        conf.setTemplate(OBJECT_HAS_INSCRIPTION_FTL);

        conf.setVarNameForSubject(CULT_OBJECT);
        conf.setVarNameForPredicate(PREDICATE);
        conf.setVarNameForObject(ACTIVITY_OBJ);
        conf.addNewResource(ACTIVITY_OBJ, DEFAULT_NS_FOR_NEW_RESOURCE);
        conf.addUrisOnForm( Arrays.asList(INSCRIPTION_OUTPUT));
        conf.addNewResource(INSCRIPTION_OUTPUT, DEFAULT_NS_FOR_NEW_RESOURCE);
        conf.addSparqlForExistingUris(INSCRIPTION_OUTPUT, inscriptionOutputQuery);

        conf.setN3Required(Arrays.asList(n3ForInscription));

        conf.addN3Optional(Arrays.asList(n3ForInscriptionOutputType));
        conf.addSparqlForAdditionalUrisInScope("hasInscriptionObject", hasInscriptionObjectQuery);

		addNewActor(conf);
		addNewActorRole(conf);
        addExistingActor(conf);
        addComment(conf);
        addExistingRoleType(conf);
        addTranscription(conf);
        addInscriptionSpecification(conf);
        addInscriptionOutputType(conf);
    }

    private void addInscriptionOutputType(EditConfigurationVTwo conf) throws Exception {
        conf.addUrisOnForm( Arrays.asList(INSCRIPTION_OUTPUT_TYPE));

        conf.addLiteralsOnForm( Arrays.asList(EXISTING_INSC_TYPE_LABEL));
        //conf.addSparqlForExistingLiteral("inscTypeLabel", existingInscTypeLabelQuery);
        conf.addSparqlForExistingUris(INSCRIPTION_OUTPUT_TYPE, inscriptionOutputTypeQuery);
        conf.addField(new FieldVTwo().
                setName(INSCRIPTION_OUTPUT_TYPE).
                setValidators( list("nonempty")).
                setOptions(new ChildVClassesOptions(INSCRIPTION_OUTPUT_CLASS)));	
        //conf.addField(new FieldVTwo().
        //        setName(INSCRIPTION_OUTPUT).
        //        setOptions(new IndividualsViaVClassOptions(INSCRIPTION_OUTPUT_CLASS)));		
	}

	private void addInscriptionSpecification(EditConfigurationVTwo conf) throws Exception {
        conf.addN3Optional(Arrays.asList(n3ForInscriptionSpecification));
        conf.addSparqlForExistingUris(INSCRIPTION_SPECIFICATION, existingInsciptionSpecificationQuery);
        conf.addUrisOnForm( Arrays.asList(INSCRIPTION_SPECIFICATION));
        conf.addField(new FieldVTwo().
                setName(INSCRIPTION_SPECIFICATION).
                setOptions(new IndividualsViaVClassOptions(INSCRIPTION_TYPE_CLASS)));
        conf.addField(new FieldVTwo().
                setName(EXISTING_INSC_TYPE_LABEL).
                setRangeDatatypeUri(XSD.xstring.toString() ).
                setValidators( list("datatype:" + XSD.xstring.toString())));
	}

	private void addTranscription(EditConfigurationVTwo conf) {
        conf.addN3Optional(Arrays.asList(transcriptionN3));
        conf.addLiteralsOnForm( Arrays.asList("transcription"));
        conf.addSparqlForExistingLiteral("existingTranscription", transcriptionValueQuery);
        conf.addSparqlForExistingLiteral("transcription", transcriptionQuery);
        conf.addField(new FieldVTwo().
            setName("transcription").
            setRangeDatatypeUri( org.apache.jena.vocabulary.RDFS.Literal.getURI() ).
            setValidators(list("datatype:" + XSD.xstring.toString())));		
	}

	private static final String inscriptionOutputQuery = "PREFIX rdfs: <"+ RDFS +">   \n"+
	        "SELECT ?" + INSCRIPTION_OUTPUT + " WHERE {\n"+
	        "?" + ACTIVITY_OBJ + " <http://ontology.tib.eu/gesah/has_inscription_output> ?" + INSCRIPTION_OUTPUT + " . \n" +
	        "?" + INSCRIPTION_OUTPUT + " <http://ontology.tib.eu/gesah/output_of_inscription> ?" + ACTIVITY_OBJ + " . \n }";
	
    final static String n3ForInscription =
    	"@prefix rdfs: <"+ RDFS +">   .\n"+
        "?" + CULT_OBJECT + " <http://ontology.tib.eu/gesah/object_of_inscription>  ?" + ACTIVITY_OBJ + " .\n" +
        "?" + ACTIVITY_OBJ + " <http://ontology.tib.eu/gesah/has_inscription_object> ?" + CULT_OBJECT + " ." +
        "?" + ACTIVITY_OBJ + " a <http://ontology.tib.eu/gesah/Inscription_Activity> . \n" +
        "?" + ACTIVITY_OBJ + " <http://ontology.tib.eu/gesah/realizes> ?" + NEW_ACTOR_ROLE + " . \n" +
        "?" + ACTIVITY_OBJ + " <http://ontology.tib.eu/gesah/has_inscription_output> ?" + INSCRIPTION_OUTPUT + " . \n" +
        "?" + INSCRIPTION_OUTPUT + " <http://ontology.tib.eu/gesah/output_of_inscription> ?" + ACTIVITY_OBJ + " . \n" +
        "?" + NEW_ACTOR_ROLE + " " + "<" + GESAH_REALIZED_IN + ">" + " ?" + ACTIVITY_OBJ + " . \n" ;
    
    final static String n3ForInscriptionSpecification  =
        "?" + INSCRIPTION_OUTPUT + " <http://ontology.tib.eu/gesah/has_inscription_type> ?" + INSCRIPTION_SPECIFICATION + " . \n" +
        "?" + INSCRIPTION_SPECIFICATION + " <http://ontology.tib.eu/gesah/inscription_type_of> ?" + INSCRIPTION_OUTPUT + " . \n";	
		
    final static String transcriptionN3  =
        "?" + INSCRIPTION_OUTPUT + " <http://ontology.tib.eu/gesah/transcription> ?transcription .";
    
    final static String transcriptionQuery  =
            "SELECT ?transcription WHERE {\n" +
            "?" + ACTIVITY_OBJ + " <http://ontology.tib.eu/gesah/has_inscription_output> ?" + INSCRIPTION_OUTPUT + " .\n" +
            "?" + INSCRIPTION_OUTPUT + " <"+ TRANSCRIPTION_PRED +"> ?transcription . }";
    
    final static String transcriptionValueQuery  =
        "SELECT (STR(?existingTranscriptionLit) as ?existingTranscription) WHERE {\n" +
        "?" + ACTIVITY_OBJ + " <http://ontology.tib.eu/gesah/has_inscription_output> ?" + INSCRIPTION_OUTPUT + " .\n" +
        "?" + INSCRIPTION_OUTPUT + " <"+ TRANSCRIPTION_PRED +"> ?existingTranscriptionLit . }";
		
    final static String existingInsciptionSpecificationQuery =
        "SELECT ?" + INSCRIPTION_SPECIFICATION + " WHERE {\n"+
        "?" + ACTIVITY_OBJ + " <http://ontology.tib.eu/gesah/has_inscription_output> ?" + INSCRIPTION_OUTPUT + " . \n" +
        "?" + INSCRIPTION_OUTPUT + " <http://ontology.tib.eu/gesah/has_inscription_type> ?" + INSCRIPTION_SPECIFICATION + " . }";

    final static String inscriptionOutputTypeQuery  =
        "PREFIX rdfs: <"+ RDFS +">   \n"+
        "SELECT ?" + INSCRIPTION_OUTPUT_TYPE + " WHERE {\n"+
        "?" + ACTIVITY_OBJ + " <http://ontology.tib.eu/gesah/has_inscription_output> ?" + INSCRIPTION_OUTPUT + " . \n" +
        "?" + INSCRIPTION_OUTPUT + " <http://ontology.tib.eu/gesah/output_of_inscription> ?" + ACTIVITY_OBJ + " . \n" +
        "?" + INSCRIPTION_OUTPUT + " a ?" + INSCRIPTION_OUTPUT_TYPE + "  . \n" +
        "?" + INSCRIPTION_OUTPUT_TYPE + " <http://www.w3.org/2000/01/rdf-schema#subClassOf> <http://ontology.tib.eu/gesah/Inscription> . }";		
	
    final static String n3ForInscriptionOutputType  =
    	"?" + INSCRIPTION_OUTPUT + " a ?" + INSCRIPTION_OUTPUT_TYPE + "  . \n" ;

    //Query for inverse property
    final static String hasInscriptionObjectQuery  =
    	"PREFIX owl:  <http://www.w3.org/2002/07/owl#>" +
		" SELECT ?hasInscriptionObject " +
		" WHERE { ?hasInscriptionObject owl:inverseOf " + "<" + GESAH + OBJECT_OF_INSCRIPTION + "> . } ";

	@Override
	protected EditMode getEditMode(EditConfigurationVTwo editConf, VitroRequest vreq) {
		List<String> predicates = new ArrayList<String>();
		predicates.add(GESAH + OBJECT_OF_INSCRIPTION);
		return EditModeUtils.getEditMode(vreq, predicates);
	}
	
}
