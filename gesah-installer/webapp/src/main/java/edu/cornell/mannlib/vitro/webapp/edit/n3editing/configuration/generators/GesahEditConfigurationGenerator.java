package edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators;

import edu.cornell.mannlib.vitro.webapp.controller.VitroRequest;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.EditConfigurationUtils;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.EditConfigurationVTwo;
import edu.cornell.mannlib.vitro.webapp.utils.FrontEndEditingUtils;
import edu.cornell.mannlib.vitro.webapp.utils.generators.EditModeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class GesahEditConfigurationGenerator extends BaseEditConfigurationGenerator implements EditConfigurationGenerator  {
    protected EditConfigurationVTwo getDefaultConfiguration(VitroRequest vreq) {
        EditConfigurationVTwo conf = new EditConfigurationVTwo();
        initBasics(conf, vreq);
        initObjectPropForm(conf, vreq);
        initPropertyParameters(conf, vreq);
        initFormSpecificData(conf, vreq);
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
        formSpecificData.put("editMode", getEditMode(vreq).name().toLowerCase());
        editConf.setFormSpecificData(formSpecificData);
    }

    protected FrontEndEditingUtils.EditMode getEditMode(VitroRequest vreq) {
        List<String> predicates = new ArrayList<String>();
        predicates.add("http://vivoweb.org/ontology/core#relates");
        return EditModeUtils.getEditMode(vreq, predicates);
    }
}
