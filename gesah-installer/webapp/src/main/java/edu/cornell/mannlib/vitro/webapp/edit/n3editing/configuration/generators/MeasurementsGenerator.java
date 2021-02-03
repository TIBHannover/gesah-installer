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
 * Subject   - Cultural Object (http://ontology.tib.eu/gesah/Cultural_Object)
 * Predicate - has_measurements
 * Object    - Measurements (http://ontology.tib.eu/gesah/Measurement)
 */
public class MeasurementsGenerator extends GesahEditConfigurationGenerator {
    private final static String measurementsSpecificationClass = "http://ontology.tib.eu/gesah/Measurement_Specification";
    private final static String hasMeasurementsSpecPred = "http://ontology.tib.eu/gesah/has_measurement_specification";

    private final static String measurementClass = "http://ontology.tib.eu/gesah/Measurement";

    private final static String hasMeasurementsPred = "http://ontology.tib.eu/gesah/has_measurements";
    private final static String titlePred = "http://ontology.tib.eu/gesah/title";

    private final static String depthPred = "http://ontology.tib.eu/gesah/measurement_depth";
    private final static String diameterPred = "http://ontology.tib.eu/gesah/measurement_diameter";
    private final static String heightPred = "http://ontology.tib.eu/gesah/measurement_height";
    private final static String widthPred = "http://ontology.tib.eu/gesah/measurement_width";
//    private final static String descriptionPred = "http://ontology.tib.eu/gesah/description";


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
        conf.setTemplate("MeasurementsForm.ftl");

        conf.addNewResource("object", DEFAULT_NS_FOR_NEW_RESOURCE);

        // Add options data
        // Depth
        literalsOnForm.add("depth");
        n3required.add(n3ForNewDepth);
        conf.addSparqlForExistingLiteral("depth", depthQuery);          // Retrieve current literal
        conf.addField( new FieldVTwo().
                setName("depth")
                .setRangeDatatypeUri( XSD.xstring.toString() ).
                        setValidators( list("nonempty") ) );            // Require not empty

        // Height
        literalsOnForm.add("height");
        n3required.add(n3ForNewHeight);
        conf.addSparqlForExistingLiteral("height", heightQuery);          // Retrieve current literal
        conf.addField( new FieldVTwo().
                setName("height")
                .setRangeDatatypeUri( XSD.xstring.toString() ).
                        setValidators( list("nonempty") ) );            // Require not empty


        // Width
        literalsOnForm.add("width");
        n3required.add(n3ForNewWidth);
        conf.addSparqlForExistingLiteral("width", widthQuery);          // Retrieve current literal
        conf.addField( new FieldVTwo().
                setName("width")
                .setRangeDatatypeUri( XSD.xstring.toString() ).
                        setValidators( list("nonempty") ) );            // Require not empty

        // Diameter
        literalsOnForm.add("diameter");
        n3optional.add(n3ForNewDiameter);
        conf.addSparqlForExistingLiteral("diameter", diameterQuery);          // Retrieve current literal
        conf.addField( new FieldVTwo().
                setName("diameter")
                .setRangeDatatypeUri( XSD.xstring.toString() ) );

        // Measurement Specifications - should this be classes? Or an autocomplete select?
        urisOnForm.add("measurementsSpecification");
        n3optional.add(n3ForNewMeasurementsSpecification);
        conf.addSparqlForExistingUris("measurementsSpecification", measurementsSpecificationQuery);     // Retrieve selected URI
        conf.addField( new FieldVTwo().
                setName("measurementsSpecification").
                setOptions(new IndividualsViaVClassOptions(measurementsSpecificationClass)));   // Populate options with individuals

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

    private final static String n3ForNewDepth =
        "?subject <" + hasMeasurementsPred + "> ?object . \n" +
        "?object a <" + measurementClass + "> . \n" +
        "?object <" + depthPred + "> ?depth ; ";

    private final static String n3ForNewDiameter =
        "?subject <" + hasMeasurementsPred + "> ?object . \n" +
        "?object a <" + measurementClass + "> . \n" +
        "?object <" + diameterPred + "> ?diameter ; ";

    private final static String n3ForNewHeight =
        "?subject <" + hasMeasurementsPred + "> ?object . \n" +
        "?object a <" + measurementClass + "> . \n" +
        "?object <" + heightPred + "> ?height ; ";

    private final static String n3ForNewWidth =
        "?subject <" + hasMeasurementsPred + "> ?object . \n" +
        "?object a <" + measurementClass + "> . \n" +
        "?object <" + widthPred + "> ?width ; ";

    private final static String n3ForNewMeasurementsSpecification =
        "?object <" + hasMeasurementsSpecPred + "> ?measurementsSpecification ; ";

    private final static String depthQuery =
        "SELECT ?existingDepth WHERE { \n" +
        "  ?object <" + depthPred + "> ?existingDepth . \n" +
        "}";

    private final static String diameterQuery =
        "SELECT ?existingDiameter WHERE { \n" +
        "  ?object <" + diameterPred + "> ?existingDiameter . \n" +
        "}";

    private final static String heightQuery =
        "SELECT ?existingHeight WHERE { \n" +
        "  ?object <" + heightPred + "> ?existingHeight . \n" +
        "}";

    private final static String widthQuery =
        "SELECT ?existingWidth WHERE { \n" +
        "  ?object <" + widthPred + "> ?existingWidth . \n" +
        "}";

    private final static String measurementsSpecificationQuery =
        "SELECT ?existingMeasurementsSpecification WHERE { \n" +
        "  ?object <" + hasMeasurementsSpecPred + "> ?existingMeasurementsSpecification . \n" +
        "}";

// http://ontology.tib.eu/gesah/has_Title
// <http://ontology.tib.eu/gesah/has_title_type>                    | <http://ontology.tib.eu/gesah/Descriptive_Title>
// <http://ontology.tib.eu/gesah/title>                             | "Detailstudien von Säulenordnungen"^^<http://www.w3.org/2
// <http://www.w3.org/1999/02/22-rdf-syntax-ns#type>                | <http://erlangen-crm.org/170309/E35_Title>
}

