/* $This file is distributed under the terms of the license in LICENSE$ */
package edu.cornell.mannlib.vitro.webapp.edit.n3editing;

import java.util.HashMap;
import java.util.Map;

public class N3TransitionToV2Mapping extends HashMap<String, String>{
    public N3TransitionToV2Mapping(){
        Map<String,String> map = this;

        map.put("defaultAddMissingIndividualForm.jsp",
                edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators.DefaultAddMissingIndividualFormGenerator.class.getName());

        // gesah-vitro forms:

        
        map.put("objectHasCreation.jsp",
                edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators.ObjectHasCreation.class.getName());
        

//        map.put("terminologyAnnotation.jsp",
//                edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators.TerminologyAnnotationGenerator.class.getName());
//
//        map.put("redirectToPublication.jsp",
//                edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators.RedirectToPublicationGenerator.class.getName());
//        map.put("unsupportedBrowserMessage.jsp",
//                edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators.UnsupportedBrowserMessage.class.getName());
//
        

        
    }
}
