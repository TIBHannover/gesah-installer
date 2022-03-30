/* $This file is distributed under the terms of the license in LICENSE$ */
package edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.jena.vocabulary.XSD;

import edu.cornell.mannlib.vitro.webapp.controller.VitroRequest;
import edu.cornell.mannlib.vitro.webapp.dao.VitroVocabulary;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.DateTimeIntervalValidationVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.DateTimeWithPrecisionVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.EditConfigurationVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.fields.ChildVClassesOptions;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.fields.ChildVClassesWithParent;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.fields.FieldVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.fields.IndividualsViaVClassOptions;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.validators.AntiXssValidation;
import edu.cornell.mannlib.vitro.webapp.utils.FrontEndEditingUtils.EditMode;
import edu.cornell.mannlib.vitro.webapp.utils.generators.EditModeUtils;


public class MarkDesignationGenerator extends AbstractCulturalObjectGenerator implements EditConfigurationGenerator {
	private static final String WHERE = " WHERE { \n ";
	private static final String SELECT = "SELECT ";
	private static final String GT = "> ";
	private static final String LT = " <";
	private static final String A = " a ";
	private static final String GESAH_MARK_DESIGNATION_OF = LT + GESAH + "mark_designation_of" + GT;
	private static final String MARK_DESIGNATION = "mark_designation";
	private static final String GESAH_HAS_MARK_DESIGNATION = LT  + GESAH + "has_mark_designation" + GT; 
	private static final String LINE_END = " . \n ";
	private static final String MARK_DESIGNATION_FTL = "markDesignation.ftl";
	private static final String GESAH_MARK_DESIGNATION_CLASS = LT + GESAH + "Mark_Designation" + GT;
	private static final String MARK_LOCATION = "markLocation";
	private static final String MARK_EXISTING_LOCATION = "markExistingLocation";
	private static final String EXISTING_MARK_URI = "existingMark";
	private static final String COLLECTORS_MARK = "collectorsMark";
	private static final String NEW_COLLECTORS_MARK = "newCollectorsMark";

	
	private static final String GESAH_MARK_LOCATION = LT + GESAH + "mark_location" + GT;

	private static final String EXISTING_COLLECTORS_MARK = "existingCollectorsMark";

	private static final String GESAH_USES_MARK = LT + GESAH + "uses_mark" + GT;
	private static final String GESAH_MARK_USED_IN = LT + GESAH + "mark_used_in" + GT;

	private static final String GESAH_IS_MARK_USED_IN = "<" + GESAH + "mark_used_in" + GT;
	private static final String MARK_TITLE = "markTitle";
	private static final String GESAH_MARK_ID = LT + GESAH + "mark_ID" + GT;
	private static final String MARK_ID = "markId";
	private static final String GESAH_MARK_URL = LT + GESAH + "mark_URL" + GT;
	private static final String MARK_URL = "markUrl";
	private static final String GESAH_MARK_CLASS = LT + GESAH + "Mark" + GT;
	private static final String MARK_DESIGNATION_LABEL = "markDesignationLabel";
	private static final String EXISTING_MARK_DESIGNATION_LABEL = "existingMarkDesignationLabel";
	private static final String EXISTING_MARK_LABEL = "existingMarkLabel";
	private static final String COLLECTORS_MARK_LABEL = "collectorsMarkLabel";




	@Override
	protected void configureForm(VitroRequest vreq, HttpSession session, EditConfigurationVTwo conf) throws Exception {
    conf.setTemplate(MARK_DESIGNATION_FTL);
    
		conf.setVarNameForSubject(CULT_OBJECT);
    conf.setVarNameForPredicate(PREDICATE);
    conf.setVarNameForObject(MARK_DESIGNATION);
    
    conf.setN3Required( Arrays.asList(n3ForNewMarkDesignation) );
		conf.setN3Optional(Arrays.asList(n3ForNewMarkDesignationLabel, n3ForNewMarkLocation, n3SelectedMark,
				n3ForNew_Mark_with_title_id_url));

    conf.addNewResource(MARK_DESIGNATION, DEFAULT_NS_FOR_NEW_RESOURCE);
    conf.addNewResource(NEW_COLLECTORS_MARK, DEFAULT_NS_FOR_NEW_RESOURCE);

    conf.setLiteralsOnForm( Arrays.asList(MARK_DESIGNATION_LABEL, MARK_LOCATION, COLLECTORS_MARK_LABEL, MARK_ID, MARK_URL, MARK_TITLE));
    
    conf.setUrisOnform( Arrays.asList( COLLECTORS_MARK, NEW_COLLECTORS_MARK )); 
    
    conf.addSparqlForExistingLiteral(MARK_DESIGNATION_LABEL, sparqlForExistingMarkDesignationLabel);
    conf.addSparqlForExistingLiteral(MARK_LOCATION, sparqlForExistingMarkLocation);
    conf.addSparqlForExistingLiteral(COLLECTORS_MARK_LABEL, existingForMarkLabelQuery);

    conf.addSparqlForExistingUris(COLLECTORS_MARK, existingMarkQuery);
    
    conf.addField( new FieldVTwo().
        setName(COLLECTORS_MARK).
        setOptions( new IndividualsViaVClassOptions(
        		GESAH + "Mark")));	
    
    conf.addField( new FieldVTwo().
        setName(COLLECTORS_MARK_LABEL).
        setRangeDatatypeUri(XSD.xstring.toString() ));
    
    conf.addField( new FieldVTwo().
        setName(MARK_ID).
        setRangeDatatypeUri(XSD.xstring.toString() ));
    
    conf.addField( new FieldVTwo().
        setName(MARK_URL).
        setRangeDatatypeUri(XSD.xstring.toString() ));
    
    conf.addField( new FieldVTwo().
        setName(MARK_TITLE).
        setRangeDatatypeUri(XSD.xstring.toString() ));
    
    conf.addField( new FieldVTwo().
        setName(MARK_DESIGNATION_LABEL).
        setRangeDatatypeUri(XSD.xstring.toString() ));
    
    conf.addField( new FieldVTwo().
        setName(MARK_LOCATION).
        setRangeDatatypeUri(XSD.xstring.toString() ));
    
	}
	
	final static String existingMarkQuery = ""
			+ SELECT + VAR + EXISTING_MARK_URI + WHERE
			+ VAR + MARK_DESIGNATION + GESAH_USES_MARK  + VAR + EXISTING_MARK_URI + LINE_END
			+ VAR + EXISTING_MARK_URI + GESAH_MARK_USED_IN + VAR + MARK_DESIGNATION + LINE_END 
			+ VAR + EXISTING_MARK_URI + A + GESAH_MARK_CLASS + LINE_END 
			+ "}";
	
	final static String existingForMarkLabelQuery = ""
			+ SELECT + VAR + EXISTING_MARK_LABEL + WHERE
			+ VAR + MARK_DESIGNATION + GESAH_USES_MARK  + VAR + EXISTING_MARK_URI + LINE_END
			+ VAR + EXISTING_MARK_URI + GESAH_MARK_USED_IN + VAR + MARK_DESIGNATION + LINE_END 
			+ VAR + EXISTING_MARK_URI + A + GESAH_MARK_CLASS + LINE_END 
			+ VAR + EXISTING_MARK_URI + LT +LABEL + GT + VAR + EXISTING_MARK_LABEL + LINE_END
			+ "}";
	
	final static String sparqlForExistingMarkDesignationLabel = ""
			+ SELECT + VAR + EXISTING_MARK_DESIGNATION_LABEL + WHERE
			+ VAR + MARK_DESIGNATION + LT + LABEL + GT + VAR + EXISTING_MARK_DESIGNATION_LABEL + LINE_END
			+ "}";
	
	final static String sparqlForExistingMarkLocation = ""
			+ SELECT + VAR + MARK_EXISTING_LOCATION + WHERE
			+ VAR + MARK_DESIGNATION + GESAH_MARK_LOCATION + VAR + MARK_EXISTING_LOCATION + LINE_END
			+ "}";
	
	
  final static String n3ForNewMarkDesignation = ""
  		+ VAR + CULT_OBJECT + SPACE + GESAH_HAS_MARK_DESIGNATION + SPACE + VAR + MARK_DESIGNATION + LINE_END
  		+ VAR + MARK_DESIGNATION + SPACE + GESAH_MARK_DESIGNATION_OF + SPACE + VAR + CULT_OBJECT + LINE_END
  		+ VAR + MARK_DESIGNATION + A + GESAH_MARK_DESIGNATION_CLASS + LINE_END;
  
  final static String n3ForNewMarkDesignationLabel = ""
  		+ VAR + MARK_DESIGNATION + LT + LABEL + GT + VAR + MARK_DESIGNATION_LABEL + LINE_END;
  
  final static String n3ForNewMarkLocation = ""
  		+ VAR + MARK_DESIGNATION + GESAH_MARK_LOCATION + VAR + MARK_LOCATION + LINE_END;
  
  final static String n3ForNewMarkDesignationLocation = ""
  		+ VAR + MARK_DESIGNATION + SPACE + GESAH_MARK_LOCATION + SPACE + VAR + MARK_LOCATION + LINE_END;

  final static String n3SelectedMark = ""
  		+ VAR + MARK_DESIGNATION + SPACE + GESAH_USES_MARK + SPACE + VAR + COLLECTORS_MARK + LINE_END
  		+ VAR + COLLECTORS_MARK + SPACE + GESAH_IS_MARK_USED_IN + SPACE + VAR + MARK_DESIGNATION + LINE_END;
  
  final static String n3ForNew_Mark_with_title_id_url = ""
  		+ VAR + MARK_DESIGNATION + SPACE + GESAH_USES_MARK + SPACE + VAR + NEW_COLLECTORS_MARK + LINE_END
  		+ VAR + NEW_COLLECTORS_MARK + SPACE + GESAH_IS_MARK_USED_IN + SPACE + VAR + MARK_DESIGNATION + LINE_END
  		+ VAR + NEW_COLLECTORS_MARK + A + GESAH_MARK_CLASS + LINE_END
  		+ VAR + NEW_COLLECTORS_MARK + LT + LABEL + GT + VAR + MARK_TITLE + LINE_END
  		+ VAR + NEW_COLLECTORS_MARK + SPACE + GESAH_MARK_ID + SPACE + VAR + MARK_ID + LINE_END
  		+ VAR + NEW_COLLECTORS_MARK + SPACE + GESAH_MARK_URL + SPACE + VAR + MARK_URL + LINE_END;

	@Override
	protected EditMode getEditMode(EditConfigurationVTwo editConf, VitroRequest vreq) {
		List<String> predicates = new ArrayList<String>();
		predicates.add(GESAH + "has_mark_designation");
		return EditModeUtils.getEditMode(vreq, predicates);
	}
}
