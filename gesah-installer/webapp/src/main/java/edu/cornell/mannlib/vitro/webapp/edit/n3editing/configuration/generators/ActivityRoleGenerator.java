/* $This file is distributed under the terms of the license in LICENSE$ */
package edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import edu.cornell.mannlib.vitro.webapp.controller.VitroRequest;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.AutocompleteRequiredInputValidator;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.EditConfigurationVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.fields.ChildVClassesOptions;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.fields.FieldVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.fields.IndividualsViaVClassOptions;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.validators.AntiXssValidation;
import edu.cornell.mannlib.vitro.webapp.utils.FrontEndEditingUtils.EditMode;
import edu.cornell.mannlib.vitro.webapp.utils.generators.EditModeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.vocabulary.XSD;

public class ActivityRoleGenerator extends AbstractCulturalObjectGenerator implements EditConfigurationGenerator{

	private static final String TEMPLATE = "activityHasRole.ftl";

	@Override
	protected void configureForm(VitroRequest vreq, HttpSession session, EditConfigurationVTwo conf) throws Exception {
        conf.setTemplate(TEMPLATE);

        conf.setVarNameForSubject(ACTIVITY_OBJ);
        conf.setVarNameForPredicate(PREDICATE);
        conf.setVarNameForObject(EXISTING_ROLE);

        conf.addValidator(new AntiXssValidation());

        conf.addValidator(new AutocompleteRequiredInputValidator(EXISTING_ACTOR, ACTOR_LABEL));
        
        conf.addSparqlForAdditionalLiteralsInScope("isAttribution", isAttributionQuery);

        String returnUrl = vreq.getParameter("returnURL");
        if (!StringUtils.isBlank(returnUrl)) {
            conf.setUrlToReturnTo(returnUrl);
        }
        
        addActivityAttributeType(conf);
        addActivityNewActor(conf);
        addActivityExistingActor(conf);
        addActivityNewActorRole(conf);
        addActivityExistingRoleType(conf);
    }
	
    private final static String isAttributionQuery =
            "SELECT ?isAttribution \n" +
            "WHERE {\n" +
            "    BIND( \n" +
            "      IF( exists {?" + ACTIVITY_OBJ + " a <http://ontology.tib.eu/gesah/Creation> } , true, \n" +
            "      IF( exists {?" + ACTIVITY_OBJ + " a <http://ontology.tib.eu/gesah/Production> } , true, \n" +
            "      IF( exists {?" + ACTIVITY_OBJ + " a <http://ontology.tib.eu/gesah/Edition> } , true, \n" +
            "      false)\n" +
            "      )) as ?isAttribution )" + 
            "}";
	
    private void addActivityNewActorRole(EditConfigurationVTwo conf) throws Exception {
        conf.addSparqlForExistingUris(NEW_ROLE, existingRoleQuery); 

        conf.addSparqlForExistingLiteral(ACTIVITY_ROLE_TYPE_LABEL, existingActivityRoleTypeLabelQuery);

        conf.addN3Optional(Arrays.asList(n3ForNewActivityRoleType));
        conf.addN3Optional(Arrays.asList(n3ForNewRole));

        conf.addNewResource(NEW_ROLE, DEFAULT_NS_FOR_NEW_RESOURCE);
        conf.addNewResource(NEW_ACTIVITY_ROLE_TYPE, DEFAULT_NS_FOR_NEW_RESOURCE);

        conf.addLiteralsOnForm(Arrays.asList(ACTIVITY_ROLE_TYPE_LABEL));

        conf.addField(
                new FieldVTwo()
                .setName(NEW_ROLE)
                .setOptions(new IndividualsViaVClassOptions(ROLE_CLASS)));

        conf.addField(
                new FieldVTwo()
                .setName(ACTIVITY_ROLE_TYPE_LABEL)
                .setRangeDatatypeUri(XSD.xstring.toString())
                .setValidators(list(DATATYPE + XSD.xstring.toString())));
    }
	
	
	private void addActivityAttributeType(EditConfigurationVTwo conf) throws Exception{
	    conf.addSparqlForExistingUris(EXISTING_ATTR_TYPE, existingAttrTypeQuery);
	
	    conf.addSparqlForExistingLiteral(EXISTING_ATTR_TYPE_LABEL, existingAttrTypeLabelQuery);
	
	    conf.addN3Optional(Arrays.asList(n3ForNewAttrTypeNewActorRole));
	    conf.addN3Optional(Arrays.asList(n3ForExistingActorRoleExistingAttrType, n3ForNewActorRoleExistingAttrType));
	
	    conf.addNewResource(NEW_ATTR_TYPE,DEFAULT_NS_FOR_NEW_RESOURCE);

        conf.addLiteralsOnForm(Arrays.asList(EXISTING_ATTR_TYPE_LABEL));

        conf.addUrisOnForm(Arrays.asList(EXISTING_ATTR_TYPE));

        conf.addField(
                new FieldVTwo()
                .setName(EXISTING_ATTR_TYPE_LABEL)
                .setRangeDatatypeUri(XSD.xstring.toString() )
                .setValidators(list(DATATYPE + XSD.xstring.toString())));

        conf.addField(
                new FieldVTwo()
                .setName(EXISTING_ATTR_TYPE)
                .setOptions(new IndividualsViaVClassOptions(ATTRIBUTION_TYPE_CLASS)));
    }
	
    private void addActivityNewActor(EditConfigurationVTwo conf) throws Exception {
        conf.addSparqlForExistingLiteral(ACTOR_LABEL, actorLabelQuery);
        conf.addSparqlForExistingUris(ACTOR_TYPE, actorTypeQuery);

        conf.addN3Optional(Arrays.asList(n3ForNewActor));

        conf.addNewResource(NEW_ACTOR, DEFAULT_NS_FOR_NEW_RESOURCE);

        conf.addUrisOnForm(Arrays.asList(ACTOR_TYPE));

        conf.addLiteralsOnForm(Arrays.asList(ACTOR_LABEL));
        conf.addLiteralsOnForm(Arrays.asList(ACTOR_LABEL_DISPLAY));

        conf.addField(
                new FieldVTwo()
                .setName(NEW_ACTOR)
                .setOptions(new IndividualsViaVClassOptions(ACTOR_CLASS)));

        conf.addField(
                new FieldVTwo()
                .setName(ACTOR_TYPE)
                .setValidators(list(DATATYPE + XSD.xstring.toString(), "nonempty"))
                .setOptions(new ChildVClassesOptions(ACTOR_CLASS)));
        conf.addField(
                new FieldVTwo()
                .setName(ACTOR_LABEL)
                .setRangeDatatypeUri(XSD.xstring.toString())
                .setValidators(list(DATATYPE + XSD.xstring.toString())));
        conf.addField(
                new FieldVTwo()
                .setName(ACTOR_LABEL_DISPLAY)
                .setRangeDatatypeUri(XSD.xstring.toString()));
    }

    private void addActivityExistingActor(EditConfigurationVTwo conf) throws Exception {
        conf.addSparqlForExistingUris(EXISTING_ACTOR, existingActorQuery);

        conf.addN3Optional(Arrays.asList(n3ForExistingActor));

        conf.addUrisOnForm(Arrays.asList(EXISTING_ACTOR));

        conf.addField(new FieldVTwo()
                .setName(EXISTING_ACTOR)
                .setOptions(new IndividualsViaVClassOptions(ACTOR_CLASS)));
    }
    
    protected void addActivityExistingRoleType(EditConfigurationVTwo conf) throws Exception {
        conf.addSparqlForExistingUris(EXISTING_ROLE_TYPE, existingActorRoleTypeQuery);

        conf.addN3Optional(Arrays.asList(n3ForExistingRoleType));

        conf.addUrisOnForm(Arrays.asList(EXISTING_ROLE_TYPE));
        
        conf.addField(new FieldVTwo()
                .setName(EXISTING_ROLE_TYPE)
                .setValidators(list("nonempty"))
                .setOptions(new IndividualsViaVClassOptions(
                        ROLE_TYPE_CLASS)));
    }
    
    private static final String existingActorRoleTypeQuery =
            "SELECT" + SPACE + VAR + EXISTING_ROLE_TYPE + SPACE + 
            "WHERE {\n" +
                VAR + ACTIVITY_OBJ + SPACE + "<" + REALIZES + ">" + SPACE + VAR + EXISTING_ROLE + " .\n" +
                VAR + EXISTING_ROLE + SPACE + "<" + REALIZED_IN + ">" + SPACE + VAR + ACTIVITY_OBJ + " .\n" +
                VAR + EXISTING_ROLE + SPACE + "<" + HAS_ROLE_TYPE + ">" + SPACE + VAR + EXISTING_ROLE_TYPE + " .\n" + 
            "}";

    private final static String n3ForExistingRoleType =
            VAR + ACTIVITY_OBJ + SPACE + "<" + REALIZES + ">" + SPACE + VAR + NEW_ROLE + " .\n" +
            VAR + NEW_ROLE + SPACE + "<" + REALIZED_IN + ">" + SPACE + VAR + ACTIVITY_OBJ + " .\n" +
            VAR + NEW_ROLE + SPACE + "<" + HAS_ROLE_TYPE + ">" + SPACE + VAR + EXISTING_ROLE_TYPE + " .";

    private static final String existingActorQuery =
            "PREFIX rdfs:" + SPACE + "<" + RDFS + ">\n" +
            "SELECT" + SPACE + VAR + EXISTING_ACTOR + SPACE +
            "WHERE {" + "\n" +
                VAR + ACTIVITY_OBJ + SPACE + "<" + REALIZES + ">" + SPACE + VAR + EXISTING_ROLE + " .\n" +
                VAR + EXISTING_ROLE + SPACE + "<" + IS_ROLE_OF + ">" + SPACE + VAR + EXISTING_ACTOR + " .\n" +
                VAR + EXISTING_ACTOR + SPACE + "<" + HAS_ROLE + ">" + SPACE + VAR + EXISTING_ROLE + " .\n" +
                VAR + EXISTING_ROLE + SPACE + "<" + IS_ROLE_OF + ">" + SPACE + VAR + EXISTING_ACTOR + " .\n" +
                VAR + EXISTING_ACTOR + " a" + SPACE + VAR + ACTOR_TYPE + " . \n " +
                VAR + ACTOR_TYPE + " rdfs:subClassOf <" + ACTOR_CLASS + "> .\n" +
            "}" ;
	
    private static final String actorTypeQuery =
            "PREFIX rdfs:" + SPACE + "<" + RDFS + "> \n" +
            "SELECT" + SPACE + VAR + ACTOR_TYPE + SPACE +
            "WHERE {" + "\n" +
                VAR + ACTIVITY_OBJ + SPACE + "<" + REALIZES + ">" + SPACE + VAR + EXISTING_ROLE + " .\n" +
                VAR + EXISTING_ROLE + SPACE + "<" + IS_ROLE_OF + ">" + SPACE + VAR + EXISTING_ACTOR + " .\n" +
                VAR + EXISTING_ACTOR + SPACE + "<" + HAS_ROLE + ">" + SPACE + VAR + EXISTING_ROLE + " .\n" +
                VAR + EXISTING_ROLE + SPACE + "<" + IS_ROLE_OF + ">" + SPACE + VAR + EXISTING_ACTOR + " .\n" +
                VAR + EXISTING_ACTOR + " a" + SPACE + VAR + ACTOR_TYPE + " .\n" +
                VAR + ACTOR_TYPE + " rdfs:subClassOf <" + ACTOR_CLASS + "> .\n" +
            "}";

    private static final String actorLabelQuery =
            "PREFIX rdfs:" + SPACE + "<" + RDFS + "> \n" +
            "SELECT DISTINCT" + SPACE + VAR + EXISTING_AGENT_LABEL + SPACE +
            "WHERE {\n" +
                VAR + ACTIVITY_OBJ + SPACE + "<" + REALIZES + ">" + SPACE + VAR + EXISTING_ROLE + " .\n" +
                VAR + EXISTING_ROLE + SPACE + "<" + IS_ROLE_OF + ">" + SPACE + VAR + EXISTING_ACTOR + " .\n" +
                VAR + EXISTING_ACTOR + SPACE + "<" + LABEL + ">" + SPACE + VAR + EXISTING_AGENT_LABEL + " .\n" +
                VAR + EXISTING_ACTOR + " a" + SPACE + VAR + ACTOR_TYPE + " . \n " +
                VAR + ACTOR_TYPE + " rdfs:subClassOf" + SPACE + "<" + ACTOR_CLASS + ">" + " .\n" +
            "}" ;

	private final static String existingAttrTypeQuery =
            "SELECT" + SPACE + VAR + EXISTING_ATTR_TYPE + SPACE +
            "WHERE {" + "\n" +
                VAR + ACTIVITY_OBJ + SPACE + "<" + REALIZES + ">" + SPACE + VAR + EXISTING_ROLE + " .\n" +
                VAR + EXISTING_ROLE + SPACE + "<" + HAS_TYPE_OF_ATTRIBUTION + ">" + SPACE + VAR + EXISTING_ATTR_TYPE + " .\n" +
            "}";

    private final static String existingAttrTypeLabelQuery =
            "SELECT DISTINCT" + SPACE + VAR + EXISTING_ATTR_TYPE_LABEL + SPACE +
            "WHERE {\n" +
                VAR + ACTIVITY_OBJ + SPACE + "<" + REALIZES + ">" + SPACE + VAR + EXISTING_ROLE + " .\n" +
                VAR + EXISTING_ROLE + SPACE + "<" + HAS_TYPE_OF_ATTRIBUTION + ">" + SPACE + VAR + EXISTING_ATTR_TYPE + " .\n" +
                VAR + EXISTING_ATTR_TYPE + SPACE + "<" + LABEL + ">" + SPACE + VAR + EXISTING_ATTR_TYPE_LABEL + " .\n"+
            "}";

    private static final String existingActivityRoleTypeLabelQuery =
            "SELECT DISTINCT" + SPACE + VAR + EXISTING_ROLE_TYPE_LABEL + SPACE +
            "WHERE {\n" +
                VAR + ACTIVITY_OBJ + SPACE + "<" + REALIZES + ">" + SPACE + VAR + EXISTING_ROLE + " .\n" +
                VAR + EXISTING_ROLE + SPACE + "<" + REALIZED_IN + ">" + SPACE + VAR + ACTIVITY_OBJ + " .\n" +
                VAR + EXISTING_ROLE + SPACE + "<" + HAS_ROLE_TYPE + ">" + SPACE + VAR + EXISTING_ROLE_TYPE + " .\n" +
                VAR + EXISTING_ROLE_TYPE + SPACE + "<" + LABEL + ">" + SPACE + VAR + EXISTING_ROLE_TYPE_LABEL + " .\n" +
            "}";

    private static final String existingRoleQuery = 
            "SELECT" + SPACE + "?role" + SPACE + 
            "WHERE {\n" + 
                "BIND (?existingRole as ?role) .\n" + 
            "}";

    private static final String n3ForExistingActor =
            "@prefix rdfs:" + SPACE + "<" + RDFS +">" + SPACE + " .\n" +
            VAR + ACTIVITY_OBJ + SPACE + "<" + REALIZES + ">" + SPACE + VAR + NEW_ROLE + " .\n" +
            VAR + EXISTING_ACTOR + SPACE + "<" + HAS_ROLE + ">" + SPACE + VAR + NEW_ROLE + " .\n" +
            VAR + NEW_ROLE +SPACE + "<" + REALIZED_IN + ">" + SPACE + VAR + ACTIVITY_OBJ + " .\n" +
            VAR + NEW_ROLE +SPACE + "<" + IS_ROLE_OF + ">" + SPACE + VAR + EXISTING_ACTOR + " ." ;

    //Should work only if participant wasn't selected
    private final static String n3ForNewActor =
            "@prefix rdfs:" + SPACE + "<" + RDFS + ">" + " .\n" +
            VAR + ACTIVITY_OBJ + SPACE + "<" + REALIZES + ">" + SPACE + VAR + NEW_ROLE + " .\n" +
            VAR + NEW_ACTOR + SPACE + "<" + HAS_ROLE + ">" + SPACE + VAR + NEW_ROLE + " .\n" +
            VAR + NEW_ROLE + SPACE + "<" + IS_ROLE_OF + ">" + SPACE + VAR + NEW_ACTOR + " .\n" +
            VAR + NEW_ROLE + SPACE + "<" + REALIZED_IN + ">" + SPACE + VAR + ACTIVITY_OBJ + " .\n" +
            VAR + NEW_ACTOR + " a " + VAR + ACTOR_TYPE + " .\n" +
            VAR + ACTOR_TYPE + " rdfs:subClassOf <" + ACTOR_CLASS + "> .\n" +
            VAR + NEW_ACTOR + SPACE + "<" + LABEL + ">" + SPACE + VAR + ACTOR_LABEL + " .";

    private final static String n3ForNewActorRoleExistingAttrType =
            VAR + NEW_ROLE + SPACE + "<" + HAS_TYPE_OF_ATTRIBUTION + ">" + SPACE + VAR + EXISTING_ATTR_TYPE + " .\n" +
            VAR + EXISTING_ATTR_TYPE + SPACE + "<" + IS_ATTRIBUTION_TYPE_OF + ">" + SPACE + VAR + NEW_ROLE + " .";
	
    private final static String n3ForExistingActorRoleExistingAttrType  =
            VAR + EXISTING_ROLE + SPACE + "<" + HAS_TYPE_OF_ATTRIBUTION + ">" + SPACE + VAR + EXISTING_ATTR_TYPE + " .\n" +
            VAR + EXISTING_ATTR_TYPE + SPACE + "<" + IS_ATTRIBUTION_TYPE_OF + ">" + SPACE + VAR + EXISTING_ROLE + " .";

    private final static String n3ForNewAttrTypeNewActorRole  =
            VAR + NEW_ROLE + SPACE + "<" + HAS_TYPE_OF_ATTRIBUTION + ">" + SPACE + VAR + NEW_ATTR_TYPE + " .\n" +
            VAR + NEW_ATTR_TYPE + SPACE + "<" + IS_ATTRIBUTION_TYPE_OF + ">" + SPACE + VAR + NEW_ROLE + " .\n" +
            VAR + NEW_ATTR_TYPE + SPACE + "<" + LABEL + ">" + SPACE + VAR + ATTR_TYPE_LABEL + " .\n" +
            VAR + NEW_ATTR_TYPE + " a " + "<" + ATTRIBUTION_TYPE + "> .";
	
    private final static String n3ForNewActivityRoleType =
            VAR + NEW_ROLE + SPACE + "<" + HAS_ROLE_TYPE + ">" + SPACE + VAR + NEW_ACTIVITY_ROLE_TYPE + " . " + "\n" +
            VAR + NEW_ACTIVITY_ROLE_TYPE + SPACE + "<" + LABEL + ">" + SPACE + VAR + NEW_ROLE_TYPE_LABEL + " . " + "\n" +
            VAR + NEW_ACTIVITY_ROLE_TYPE + " a " + "<" + ROLE_TYPE + "> . " ;

    final static String n3ForNewRole =
            VAR + ACTIVITY_OBJ + " <" + REALIZES + "> " + VAR + NEW_ROLE + " . \n" +
            VAR + NEW_ROLE +" <" + REALIZED_IN + "> " + VAR + ACTIVITY_OBJ + " . \n" +
            VAR + NEW_ROLE + " a " + "<" + OBO + "BFO_0000023> . " ;

	@Override
	protected EditMode getEditMode(EditConfigurationVTwo editConf, VitroRequest vreq) {
		List<String> predicates = new ArrayList<String>();
		predicates.add(REALIZES);
		return EditModeUtils.getEditMode(vreq, predicates);
	}
	
}
