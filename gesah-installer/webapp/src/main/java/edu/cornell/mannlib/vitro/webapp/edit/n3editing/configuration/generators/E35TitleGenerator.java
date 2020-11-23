package edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators;

import edu.cornell.mannlib.vitro.webapp.controller.VitroRequest;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.EditConfigurationVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.fields.FieldVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.fields.IndividualsViaVClassOptions;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.validators.AntiXssValidation;
import edu.cornell.mannlib.vitro.webapp.utils.FrontEndEditingUtils;
import org.apache.jena.vocabulary.XSD;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Subject   - Cultural work
 * Predicate - has_Title
 * Object    - Title
 */
public class E35TitleGenerator extends GesahEditConfigurationGenerator {
    private final static String titleTypeClass = "http://ontology.tib.eu/gesah/Title_Type";

    private final static String titleClass = "http://erlangen-crm.org/170309/E35_Title";

    private final static String hasTitlePred = "http://ontology.tib.eu/gesah/has_Title";
    private final static String hasTitleTypePred = "http://ontology.tib.eu/gesah/has_title_type";
    private final static String titlePred = "http://ontology.tib.eu/gesah/title";

    @Override
//    public EditConfigurationVTwo getEditConfiguration(VitroRequest vreq, HttpSession session) throws Exception {
    protected void configureForm(VitroRequest vreq, HttpSession session, EditConfigurationVTwo conf) throws Exception {
        List<String> urisOnForm = new ArrayList<>();
        List<String> literalsOnForm = new ArrayList<>();
        List<String> n3required = new ArrayList<>();
        List<String> n3optional = new ArrayList<>();

//        conf.setVarNameForSubject("work");
//        conf.setVarNameForPredicate("predicate");
//        conf.setVarNameForObject("title");

        // Set Template
        conf.setTemplate("E35TitleForm.ftl");

        conf.addNewResource("object", DEFAULT_NS_FOR_NEW_RESOURCE);

        // Add options data
        // Title
        literalsOnForm.add("title");
        n3required.add(n3ForNewTitle);
        conf.addSparqlForExistingLiteral("title", titleQuery);          // Retrieve current literal
        conf.addField( new FieldVTwo().
                setName("title")
                .setRangeDatatypeUri( XSD.xstring.toString() ).
                        setValidators( list("nonempty") ) );            // Require not empty

        // Title Type - should this be classes? Or an autocomplete select?
        urisOnForm.add("titleType");
        n3optional.add(n3ForNewTitleType);
        conf.addSparqlForExistingUris("titleType", titleTypeQuery);     // Retrieve selected URI
        conf.addField( new FieldVTwo().
                setName("titleType").
                setOptions(new IndividualsViaVClassOptions(titleTypeClass)));   // Populate options with individuals

        conf.setUrisOnform(urisOnForm);
        conf.setLiteralsOnForm(literalsOnForm);
        conf.setN3Required(n3required);
        conf.setN3Optional(n3optional);

        conf.addValidator(new AntiXssValidation());
    }

    @Override
    protected FrontEndEditingUtils.EditMode getEditMode(EditConfigurationVTwo editConf, VitroRequest vreq) {
        if (editConf.getObject() != null) {
            return FrontEndEditingUtils.EditMode.EDIT;

            // In theory, if the title is missing but the object is present, we should treat it as a REPAIR
            // However, this still doesn't stop the retraction updates from failing, so we'll just leave it
//            if (editConf.getLiteralsInScope().get("title") == null) {
//                return FrontEndEditingUtils.EditMode.REPAIR;
//            }
        }

        return FrontEndEditingUtils.EditMode.ADD;
    }

    private final static String n3ForNewTitle =
        "?subject <" + hasTitlePred + "> ?object . \n" +
        "?object a <" + titleClass + "> . \n" +
        "?object <" + titlePred + "> ?title ; ";

    private final static String n3ForNewTitleType =
        "?object <" + hasTitleTypePred + "> ?titleType ; ";

    private final static String titleQuery =
        "SELECT ?existingTitle WHERE { \n" +
        "  ?object <" + titlePred + "> ?existingTitle . \n" +
        "}";

    private final static String titleTypeQuery =
        "SELECT ?existingTitleType WHERE { \n" +
        "  ?object <" + hasTitleTypePred + "> ?existingTitleType . \n" +
        "}";

// http://ontology.tib.eu/gesah/has_Title
// <http://ontology.tib.eu/gesah/has_title_type>                    | <http://ontology.tib.eu/gesah/Descriptive_Title>
// <http://ontology.tib.eu/gesah/title>                             | "Detailstudien von Säulenordnungen"^^<http://www.w3.org/2
// <http://www.w3.org/1999/02/22-rdf-syntax-ns#type>                | <http://erlangen-crm.org/170309/E35_Title>
}

