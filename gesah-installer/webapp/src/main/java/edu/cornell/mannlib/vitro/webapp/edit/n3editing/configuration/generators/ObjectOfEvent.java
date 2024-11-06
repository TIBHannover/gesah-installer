/* $This file is distributed under the terms of the license in LICENSE$ */
package edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators;


import static edu.cornell.mannlib.vitro.webapp.dao.VitroVocabulary.MOST_SPECIFIC_TYPE;

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
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.validators.AntiXssValidation;
import edu.cornell.mannlib.vitro.webapp.utils.FrontEndEditingUtils.EditMode;
import edu.cornell.mannlib.vitro.webapp.utils.generators.EditModeUtils;

public class ObjectOfEvent extends AbstractCulturalObjectGenerator implements EditConfigurationGenerator {
	private static final String EVENT = "Event";
	private static final String EXISTING_EVENT = "existingEvent";
	private static final String ACTIVITY_LABEL = "eventLabel";
	private static final String GESAH_HAS_EVENT_OBJECT = GESAH + "has_event_object";
	private static final String OBJECT_OF_EVENT = "object_of_event";
	private static final String GESAH_OBJECT_OF_EVENT = GESAH + OBJECT_OF_EVENT;
	private static final String EVENT_EVENT = "http://purl.org/NET/c4dm/event.owl#" + EVENT;
	private static final String OBJECT_OF_EVENT_FTL = "objectOfEvent.ftl";
	private static final String EVENT_TYPE = "eventType";
	private static final String EVENT_CURRENT_TYPE = "eventCurrentType";
	private static final String EVENT_LABEL_DISPLAY = "eventLabelDisplay";
	private static final String PUBLICATION = "publication";
	private static final String REMOVE_PUBLICATION = "removePublication";
	private static final String EXISTING_PUBLICATION = "existingPublication";
	private static final String GESAH_RELATED_PUBLICATION = GESAH + "related_publication";
	
	private final static String ORGANIZER_CLASS = FOAF + AGENT;
	private static final String ORGANIZER_TYPE = "organizerType";
	private static final String NEW_ORGANIZER = "newAgent";
	private static final String ORGANIZER_LABEL = "organizerLabel";
	private static final String ORGANIZER_LABEL_DISPLAY = "organizerLabelDisplay";
	private static final String EXISTING_ORGANIZER = "existingOrganizer";
	private static final String GESAH_HAS_ORGANIZER = GESAH + "has_organizer";
	private static final String GESAH_ORGANIZER_OF = GESAH + "organizer_of";
	private static final String REMOVE_ORGANIZER = "removeOrganizer";
	


	@Override
	protected void configureForm(VitroRequest vreq, HttpSession session, EditConfigurationVTwo conf) throws Exception {
        conf.setTemplate(OBJECT_OF_EVENT_FTL);

        conf.setVarNameForSubject(CULT_OBJECT);
        conf.setVarNameForPredicate(PREDICATE);
        conf.setVarNameForObject(ACTIVITY_OBJ);
        
        conf.addN3Optional( Arrays.asList(n3ForNewEventCreation) );
        conf.addLiteralsOnForm(Arrays.asList(ACTIVITY_LABEL));
        conf.addField( new FieldVTwo().
            setName(ACTIVITY_LABEL).
            setRangeDatatypeUri(XSD.xstring.toString() ).
            setValidators( list(DATATYPE + XSD.xstring.toString())));
        
        conf.addNewResource(ACTIVITY_OBJ, DEFAULT_NS_FOR_NEW_RESOURCE);
        conf.addValidator(new AntiXssValidation());
        
        addEvent(conf);
        addStartEndInterval(conf);
        addComment(conf);
        addExistingPlace(conf);
        addPlace(conf);
        addPublications(conf);
        addOrganizers(conf);
        conf.addN3OptionalAssertions(conf.getN3Optional());
        conf.setN3Optional(new ArrayList<>());
    }
	
	private void addOrganizers(EditConfigurationVTwo conf) throws Exception {
		addNewActor(conf);
	}
			
	protected void addNewActor(EditConfigurationVTwo conf) throws Exception {
		conf.addN3Optional(Arrays.asList(n3ForNewOrganizer));
		conf.addNewResource(NEW_ORGANIZER,DEFAULT_NS_FOR_NEW_RESOURCE);
		conf.addField( new FieldVTwo().
		        setName(NEW_ORGANIZER).
		        setOptions( new IndividualsViaVClassOptions(ORGANIZER_CLASS)));
    
    conf.addLiteralsOnForm( Arrays.asList(ORGANIZER_LABEL ));
    conf.addSparqlForExistingLiteral(ORGANIZER_LABEL, existingOrganizerLabelQuery);
    conf.addField( new FieldVTwo().
        setName(ORGANIZER_LABEL).
        setRangeDatatypeUri(XSD.xstring.toString() ).
        setValidators( list(DATATYPE + XSD.xstring.toString())));
    
    conf.addLiteralsOnForm( Arrays.asList(ORGANIZER_LABEL_DISPLAY));
    conf.addField( new FieldVTwo().
        setName(ORGANIZER_LABEL_DISPLAY).
        setRangeDatatypeUri(XSD.xstring.toString() ));
    
    conf.addUrisOnForm( Arrays.asList( ORGANIZER_TYPE ));
    conf.addSparqlForExistingUris(ORGANIZER_TYPE, existingOrganizerTypeQuery);
    conf.addField( new FieldVTwo().
        setName(ORGANIZER_TYPE).
        setValidators( list(NONEMPTY)).
        setOptions( new ChildVClassesOptions(ORGANIZER_CLASS)));			
    
    conf.addN3Optional(Arrays.asList(n3ForExistingActor));
		conf.addUrisOnForm(Arrays.asList(EXISTING_ORGANIZER));
		conf.addSparqlForExistingUris(EXISTING_ORGANIZER, existingOrganizerQuery);
		conf.addField( new FieldVTwo().
		        setName(EXISTING_ORGANIZER).
		        setOptions( new IndividualsViaVClassOptions(ORGANIZER_CLASS)));
	}
	
  private static final String existingOrganizerTypeQuery  =
      "PREFIX rdfs:" + SPACE + "<" + RDFS + ">" + "   " + "\n" +
      "SELECT" + SPACE + VAR + ORGANIZER_TYPE + " WHERE {" + "\n" +
      VAR + ACTIVITY_OBJ + SPACE + "<" + GESAH_HAS_ORGANIZER + ">" + SPACE + VAR + EXISTING_ORGANIZER + " . " + "\n" +
      VAR + EXISTING_ORGANIZER + SPACE + "<" + GESAH_ORGANIZER_OF + ">" + SPACE + VAR + ACTIVITY_OBJ + " . " + "\n" +
      VAR + EXISTING_ORGANIZER + " a" + SPACE + VAR + ORGANIZER_TYPE + " ." + "\n" +
      VAR + ORGANIZER_TYPE + " rdfs:subClassOf <" + ORGANIZER_CLASS + "> .}";		
  
	
  private static final String existingOrganizerLabelQuery  =
      "PREFIX rdfs:" + SPACE + "<" + RDFS + ">" + "   " + "\n" +
      "SELECT Distinct" + SPACE + VAR + EXISTING_AGENT_LABEL + " WHERE {" + "\n" +
      VAR + ACTIVITY_OBJ + SPACE + "<" + GESAH_HAS_ORGANIZER + ">" + SPACE + VAR + EXISTING_ORGANIZER + " . " + "\n" +
      VAR + EXISTING_ORGANIZER + SPACE + "<" + GESAH_ORGANIZER_OF + ">" + SPACE + VAR + ACTIVITY_OBJ + " . " + "\n" +
      VAR + EXISTING_ORGANIZER + SPACE + "<" + LABEL + ">" + SPACE + VAR + EXISTING_AGENT_LABEL + " ." + "\n" +
      VAR + EXISTING_ORGANIZER + " a" + SPACE + VAR + ORGANIZER_TYPE + " . \n " +
      VAR + ORGANIZER_TYPE + " rdfs:subClassOf" + SPACE + "<" + ORGANIZER_CLASS + ">" + " . }" ;
  
  //Should work only if participant wasn't selected
  // Requires ACTIVITY_OBJ NEW_ACTOR ACTOR_TYPE ACTOR_LABEL
  private final static String n3ForNewOrganizer  =
  		"@prefix rdfs:" + SPACE + "<" + RDFS + ">" + " ." + "\n" +
      VAR + ACTIVITY_OBJ + SPACE + "<" + GESAH_HAS_ORGANIZER + ">" + SPACE + VAR + NEW_ORGANIZER + " . " + "\n" +
      VAR + NEW_ORGANIZER + SPACE + "<" + GESAH_ORGANIZER_OF + ">" + SPACE + VAR + ACTIVITY_OBJ + " . " + "\n" +
      VAR + NEW_ORGANIZER + " a " + VAR + ORGANIZER_TYPE + " . " + "\n" +
      VAR + NEW_ORGANIZER + " rdfs:label " + VAR + ORGANIZER_LABEL + " . ";
  
	private static final String existingOrganizerQuery  =
      "PREFIX rdfs:" + SPACE + "<" + RDFS + ">" + "   " + "\n" +
      "SELECT" + SPACE + VAR + EXISTING_ORGANIZER + " WHERE {" + "\n" +
      VAR + ACTIVITY_OBJ + SPACE + "<" + GESAH_HAS_ORGANIZER + ">" + SPACE + VAR + EXISTING_ORGANIZER + " . " + "\n" +
      VAR + EXISTING_ORGANIZER + SPACE + "<" + GESAH_ORGANIZER_OF + ">" + SPACE + VAR + ACTIVITY_OBJ + " . " + "\n" +
      VAR + EXISTING_ORGANIZER + " a" + SPACE + VAR + ORGANIZER_TYPE + " . \n " +
      VAR + ORGANIZER_TYPE + " rdfs:subClassOf <" + ORGANIZER_CLASS + "> . }" ;
	
  private static final String n3ForExistingActor  =
  		"@prefix rdfs:" + SPACE + "<" + RDFS +">" + SPACE + " . \n" +
      VAR + ACTIVITY_OBJ + SPACE + "<" + GESAH_HAS_ORGANIZER + ">" + SPACE + VAR + EXISTING_ORGANIZER + " . \n" +
      VAR + EXISTING_ORGANIZER +SPACE + "<" + GESAH_ORGANIZER_OF + ">" + SPACE + VAR + ACTIVITY_OBJ + " . \n" ;

	private void addPublications(EditConfigurationVTwo conf) {
		for (int i = 1; i < 4; i++) {
			addPublication(conf, i);
		}
	}

	private void addPublication(EditConfigurationVTwo conf, int i) {
		conf.addLiteralsOnForm( Arrays.asList(PUBLICATION + i));
    conf.addN3Optional(Arrays.asList( n3forPublication(i)));
		conf.addSparqlForExistingLiteral(PUBLICATION + i, publicationQuery(i));
    conf.addSparqlForExistingLiteral(EXISTING_PUBLICATION + i, publicationValueQuery(i));
		conf.addField( new FieldVTwo().
		    setName(PUBLICATION + i).
		    setRangeDatatypeUri( org.apache.jena.vocabulary.RDFS.Literal.getURI() ).
		    setValidators(list(DATATYPE + XSD.xstring.toString())));
		
    conf.addLiteralsOnForm( Arrays.asList(REMOVE_PUBLICATION + i));
    conf.addN3OptionalRetracts(Arrays.asList( n3toRemovePublication(i)));
		conf.addSparqlForExistingLiteral(REMOVE_PUBLICATION + i, publicationQuery(i));

		conf.addField( new FieldVTwo().
		    setName(REMOVE_PUBLICATION + i).
		    setRangeDatatypeUri( org.apache.jena.vocabulary.RDFS.Literal.getURI() ).
		    setValidators(list(DATATYPE + XSD.xstring.toString())));
	}

  private String n3forPublication(int i) {
      return VAR + ACTIVITY_OBJ + SPACE + "<" + GESAH_RELATED_PUBLICATION + ">" + SPACE + VAR + PUBLICATION + i + " .";
  }
  
  private String n3toRemovePublication(int i) {
    return VAR + ACTIVITY_OBJ + SPACE + "<" + GESAH_RELATED_PUBLICATION + ">" + SPACE + VAR + REMOVE_PUBLICATION + i + " .";
}
  
  private String publicationQuery(int i) {
      String query = "SELECT" + SPACE + VAR + PUBLICATION + i + " WHERE {" + "\n" +
      VAR + ACTIVITY_OBJ + SPACE + "<" + GESAH_RELATED_PUBLICATION + ">" + SPACE + VAR + PUBLICATION + " . }" +
      "ORDER BY " + VAR + PUBLICATION + " LIMIT 1" + SPACE + "OFFSET " + --i;
      return query;
  }
  
  private String publicationValueQuery(int i) {
  	String query = "SELECT" + SPACE + " (STR(?existPubColumn) as " + VAR + EXISTING_PUBLICATION + i + " ) WHERE {" + "\n" +
    VAR + ACTIVITY_OBJ + SPACE + "<" + GESAH_RELATED_PUBLICATION + ">" + SPACE + VAR + "existPubColumn" + " . }" +
    "ORDER BY " + VAR + "existPubColumn" + " LIMIT 1" + SPACE + "OFFSET " + --i ;
  	return query;
  } 

	private void addEvent(EditConfigurationVTwo conf) throws Exception {
		conf.addSparqlForExistingLiteral(EVENT_LABEL_DISPLAY, existingEventLabelQuery);

		conf.addField( new FieldVTwo().
        setName(EXISTING_EVENT).
        setOptions( new IndividualsViaVClassOptions(EVENT_EVENT)));
		conf.addUrisOnForm(Arrays.asList(EXISTING_EVENT));
    conf.addN3Optional(Arrays.asList(n3ForExistingEvent));
    
    conf.addSparqlForExistingUris(EVENT_TYPE, existingEventTypeQuery);
    conf.addUrisOnForm( Arrays.asList( EVENT_TYPE ));
    conf.addField( new FieldVTwo().
        setName(EVENT_TYPE).
        setValidators( list(NONEMPTY)).
        setOptions( new ChildVClassesOptions(
        		EVENT_EVENT)));			
	}
	
	private final static String existingEventLabelQuery = "SELECT " + VAR + EVENT_LABEL_DISPLAY + " WHERE { "
			+ VAR + CULT_OBJECT + " " + "<" + GESAH_OBJECT_OF_EVENT + "> " + VAR + ACTIVITY_OBJ + " ."
			+ VAR + ACTIVITY_OBJ  + SPACE + LT + LABEL + GT + SPACE + VAR + EVENT_LABEL_DISPLAY + " ."
			+ "}";
	
	private final static String existingEventTypeQuery = 
			  SELECT + SPACE + VAR + EVENT_CURRENT_TYPE + " WHERE {" + SPACE   
			+ VAR + CULT_OBJECT + " " + "<" + GESAH_OBJECT_OF_EVENT + "> " + VAR + ACTIVITY_OBJ + " ."
			+ VAR + ACTIVITY_OBJ + SPACE + "<" + MOST_SPECIFIC_TYPE + ">" + SPACE + VAR + EVENT_CURRENT_TYPE + " . }"
			+ "LIMIT 1";
	
	private final static String n3ForExistingEvent = ""
			+ VAR + CULT_OBJECT + SPACE + LT + GESAH_OBJECT_OF_EVENT + GT + SPACE + VAR + EXISTING_EVENT + " ."
			+ VAR + EXISTING_EVENT + SPACE + LT + GESAH_HAS_EVENT_OBJECT + GT + SPACE + VAR + CULT_OBJECT + " .";

	//EVENT TYPE CULT_OBJECT ACTIVITY_OBJ ACTIVITY_LABEL
  private final static String n3ForNewEventCreation =
    		"@prefix rdfs:" + SPACE + LT + RDFS + GT + " ." + "\n" +
    		VAR + CULT_OBJECT + SPACE + LT + GESAH_OBJECT_OF_EVENT + GT + SPACE + VAR + ACTIVITY_OBJ + " .\n" +
    		VAR + ACTIVITY_OBJ + SPACE + LT + GESAH_HAS_EVENT_OBJECT +  GT + SPACE + VAR + CULT_OBJECT + " .\n" +
    		VAR + ACTIVITY_OBJ + " a " + VAR + EVENT_TYPE + " . \n" +
    		VAR + ACTIVITY_OBJ + SPACE + LT + LABEL + GT + SPACE + VAR + ACTIVITY_LABEL + " .\n";
		  
    @Override
	protected EditMode getEditMode(EditConfigurationVTwo editConf, VitroRequest vreq) {
		List<String> predicates = new ArrayList<String>();
		predicates.add(GESAH + OBJECT_OF_EVENT);
		return EditModeUtils.getEditMode(vreq, predicates);
	}
}
