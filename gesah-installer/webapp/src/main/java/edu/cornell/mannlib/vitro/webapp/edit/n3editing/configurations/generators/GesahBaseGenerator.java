/* $This file is distributed under the terms of the license in LICENSE$ */
package edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators;

import edu.cornell.mannlib.vitro.webapp.dao.VitroVocabulary;

/**
 * Adds static Strings that may be useful for forms that are part of VIVO.
 *
 * @author bdc34
 *
 */
public abstract class GesahBaseGenerator extends BaseEditConfigurationGenerator implements EditConfigurationGenerator {

    final static String vivoCore ="http://vivoweb.org/ontology/core#" ;
    final static String rdfs =VitroVocabulary.RDFS ;
    final static String foaf = "http://xmlns.com/foaf/0.1/";
    final static String type =VitroVocabulary.RDF_TYPE ;
    final static String label =rdfs+"label" ;
   	final static String gesah = "http://ontology.tib.eu/gesah/";
	final static String obo = "http://purl.obolibrary.org/obo/";

    
	final static String creatActivityClass =gesah+"Creation" ;
   	final static String attributionTypeClass =gesah+"Attribution_Type" ;
   	final static String techniqueTypeClass =gesah+"Technique" ;
    final static String materialTypeClass =gesah+"Material" ;
	final static String placeTypeClass = vivoCore+"GeographicLocation" ;
	final static String roleTypeClass =gesah +"Role_Type";
    final static String literalDateAppelPred =gesah+"literal_date_appellation" ;
	final static String desciptionPred =gesah+"description" ;
    

    final static String dateTimeValue =vivoCore+"dateTime";
    final static String dateTimeValueType =vivoCore+"DateTimeValue";
    final static String dateTimePrecision =vivoCore+"dateTimePrecision";

    final static String toInterval =vivoCore+"dateTimeInterval";
    final static String intervalType =vivoCore+"DateTimeInterval";
    final static String intervalToStart =vivoCore+"start";
    final static String intervalToEnd =vivoCore+"end";

    final static String roleClass =obo + "BFO_0000023" ;
    final static String personClass = foaf + "Person";
	

}
