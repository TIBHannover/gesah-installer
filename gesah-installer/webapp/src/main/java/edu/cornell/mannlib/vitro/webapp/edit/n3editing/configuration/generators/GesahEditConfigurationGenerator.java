package edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import edu.cornell.mannlib.vitro.webapp.controller.VitroRequest;
import edu.cornell.mannlib.vitro.webapp.dao.VitroVocabulary;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.EditConfigurationUtils;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.EditConfigurationVTwo;
import edu.cornell.mannlib.vitro.webapp.utils.FrontEndEditingUtils;

public abstract class GesahEditConfigurationGenerator extends BaseEditConfigurationGenerator implements EditConfigurationGenerator  {
  	protected final static String VIVO_CORE = "http://vivoweb.org/ontology/core#";
  	protected final static String RDFS = VitroVocabulary.RDFS;
  	protected final static String FOAF = "http://xmlns.com/foaf/0.1/";
  	protected final static String TYPE = VitroVocabulary.RDF_TYPE;
  	protected final static String LABEL = RDFS + "label";
  	protected final static String GESAH = "http://ontology.tib.eu/gesah/";
  	protected final static String OBO = "http://purl.obolibrary.org/obo/";

		protected final static String DATE_TIME_VALUE = VIVO_CORE + "dateTime";
		protected final static String DATE_TIME_VALUE_TYPE = VIVO_CORE + "DateTimeValue";
		protected final static String DATE_TIME_PRECISION = VIVO_CORE + "dateTimePrecision";

		protected final static String TO_INTERVAL = VIVO_CORE + "dateTimeInterval";
		protected final static String INTERVAL_TYPE = VIVO_CORE + "DateTimeInterval";
		protected final static String INTERVAL_TO_START = VIVO_CORE + "start";
		protected final static String INTERVAL_TO_END = VIVO_CORE + "end";

    public EditConfigurationVTwo getEditConfiguration(VitroRequest vreq, HttpSession session) throws Exception {
        EditConfigurationVTwo conf = getDefaultConfiguration(vreq);

        configureForm(vreq, session, conf);

        // Populate existing values, etc.
        prepare(vreq, conf);
        initFormSpecificData(conf, vreq);
        return conf;
    }

    protected abstract void configureForm(VitroRequest vreq, HttpSession session, EditConfigurationVTwo conf) throws Exception;

    protected EditConfigurationVTwo getDefaultConfiguration(VitroRequest vreq) {
        EditConfigurationVTwo conf = new EditConfigurationVTwo();
        initBasics(conf, vreq);
        initObjectPropForm(conf, vreq);
        initPropertyParameters(conf, vreq);

        conf.setVarNameForSubject("subject");
        conf.setVarNameForPredicate("predicate");
        conf.setVarNameForObject("object");

        return conf;
    }

    /**
     * Sets up the things that should be done for just about every form.
     */
    protected void initBasics(EditConfigurationVTwo editConf, VitroRequest vreq){
        String editKey = EditConfigurationUtils.getEditKey(vreq);
        editConf.setEditKey(editKey);

        String formUrl = EditConfigurationUtils.getFormUrlWithoutContext(vreq);
        editConf.setFormUrl(formUrl);
    }

    /**
     * Method that setups up a form for basic object or data property editing.
     */
    protected void  initPropertyParameters(EditConfigurationVTwo editConfiguration, VitroRequest vreq) {
        //set up the subject URI based on request
        String subjectUri = EditConfigurationUtils.getSubjectUri(vreq);
        editConfiguration.setSubjectUri(subjectUri);

        //set up predicate URI based on request
        String predicateUri = EditConfigurationUtils.getPredicateUri(vreq);
        editConfiguration.setPredicateUri(predicateUri);

        editConfiguration.setUrlPatternToReturnTo("/individual");
        editConfiguration.setEntityToReturnTo(subjectUri);
    }

    protected void initObjectPropForm(EditConfigurationVTwo editConfiguration,VitroRequest vreq) {
        editConfiguration.setObject( EditConfigurationUtils.getObjectUri(vreq) );
    }

    protected void initFormSpecificData(EditConfigurationVTwo editConf, VitroRequest vreq) {
        HashMap<String, Object> formSpecificData = new HashMap<String, Object>();

        // Edit mode must be lowercase
        formSpecificData.put("editMode", getEditMode(editConf, vreq).name().toLowerCase());

        editConf.setFormSpecificData(formSpecificData);
    }

    protected abstract FrontEndEditingUtils.EditMode getEditMode(EditConfigurationVTwo editConf, VitroRequest vreq);
}
