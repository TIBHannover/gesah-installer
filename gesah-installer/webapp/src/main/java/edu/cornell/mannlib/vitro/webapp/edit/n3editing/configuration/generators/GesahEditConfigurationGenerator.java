package edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators;

import edu.cornell.mannlib.vitro.webapp.controller.VitroRequest;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.EditConfigurationUtils;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.EditConfigurationVTwo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class GesahEditConfigurationGenerator extends BaseEditConfigurationGenerator implements EditConfigurationGenerator  {
    protected EditConfigurationVTwo getDefaultConfiguration(VitroRequest vreq) {
        EditConfigurationVTwo conf = new EditConfigurationVTwo();
        initBasics(conf, vreq);
        initObjectPropForm(conf, vreq);
        initPropertyParameters(conf, vreq);
        initFormSpecificData(conf, vreq);

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
        getFormSpecificData(editConf, vreq, formSpecificData);
        editConf.setFormSpecificData(formSpecificData);
    }

    protected abstract void getFormSpecificData(EditConfigurationVTwo editConf, VitroRequest vreq, Map<String, Object> formSpecificData);
}
