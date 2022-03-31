/* $This file is distributed under the terms of the license in LICENSE$ */
package edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.jena.vocabulary.XSD;

import edu.cornell.mannlib.vitro.webapp.controller.VitroRequest;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.EditConfigurationVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.fields.FieldVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.fields.IndividualsViaVClassOptions;
import edu.cornell.mannlib.vitro.webapp.utils.FrontEndEditingUtils.EditMode;
import edu.cornell.mannlib.vitro.webapp.utils.generators.EditModeUtils;

public class WorkIndexEntryGenerator extends AbstractCulturalObjectGenerator implements EditConfigurationGenerator {

	private static final String WORK_INDEX = "workIndex";
	private static final String WORK_INDEX_LABEL = "workIndexLabel";
	private static final String WORK_INDEX_TITLE = "workIndexTitle";
	private static final String WORK_INDEX_URL = "workIndexUrl";
	
	private static final String INDEX_URL = "index_URL";
	private static final String GESAH_INDEX_URL = GESAH + INDEX_URL;
	private static final String GESAH_INDEX_URL_L = LT + GESAH_INDEX_URL + GT;
	
	private static final String LONG_TITLE = "long_title";
	private static final String GESAH_LONG_TITLE = GESAH + LONG_TITLE;
	private static final String GESAH_LONG_TITLE_L =  LT + GESAH_LONG_TITLE + GT;
	
	private static final String NEW_WORK_INDEX = "newWorkIndex";
	private static final String GESAH_WORK_INDEX = GESAH + "Work_Index";
	private static final String GESAH_WORK_INDEX_L = LT + GESAH_WORK_INDEX + GT;
	
	private static final String WORK_INDEX_ENTRY = "workIndexEntry";
	private static final String WORK_INDEX_ENTRY_LABEL = "workIndexEntryLabel";

	private static final String GESAH_WORK_INDEX_ENTRY = GESAH + "Work_Index_Entry";
	private static final String GESAH_WORK_INDEX_ENTRY_L = LT + GESAH_WORK_INDEX_ENTRY + GT;
	
	private static final String INDEX_NUMBER = "indexNumber";
	private static final String GESAH_INDEX_NUMBER = GESAH + "index_number";
	private static final String GESAH_INDEX_NUMBER_L = LT + GESAH_INDEX_NUMBER + GT;
	
	private static final String IS_LISTED_IN = "is_listed_in";
	private static final String GESAH_IS_LISTED_IN = GESAH + IS_LISTED_IN;
	private static final String GESAH_IS_LISTED_IN_L = LT + GESAH_IS_LISTED_IN + GT;
	
	private static final String HAS_ENTRY = "has_entry";
	private static final String GESAH_HAS_ENTRY = GESAH + HAS_ENTRY;
	private static final String GESAH_HAS_ENTRY_L = LT + GESAH_HAS_ENTRY + GT;
	
	private static final String IS_WORK_INDEX_ENTRY_OF = "is_Work_Index_Entry_of";
	private static final String GESAH_IS_WORK_INDEX_ENTRY_OF = GESAH + IS_WORK_INDEX_ENTRY_OF;
	private static final String GESAH_IS_WORK_INDEX_ENTRY_OF_L = LT + GESAH_IS_WORK_INDEX_ENTRY_OF + GT;
	private static final String HAS_WORK_INDEX_ENTRY = "has_Work_Index_Entry";
	private static final String GESAH_HAS_WORK_INDEX_ENTRY = GESAH + HAS_WORK_INDEX_ENTRY;
	private static final String GESAH_HAS_WORK_INDEX_ENTRY_L = LT + GESAH_HAS_WORK_INDEX_ENTRY + GT;

	private static final String MARK_DESIGNATION_FTL = "workIndexEntry.ftl";

	@Override
	protected void configureForm(VitroRequest vreq, HttpSession session, EditConfigurationVTwo conf) throws Exception {
    conf.setTemplate(MARK_DESIGNATION_FTL);
    
		conf.setVarNameForSubject(CULT_OBJECT);
    conf.setVarNameForPredicate(PREDICATE);
    conf.setVarNameForObject(WORK_INDEX_ENTRY);
    
    conf.addNewResource(WORK_INDEX_ENTRY, DEFAULT_NS_FOR_NEW_RESOURCE);
		conf.addN3Required( Arrays.asList(n3forNewWorkIndexEntry) );

    addIndexNumber(conf);
	  addWorkIndexEntryLabel(conf);
	  addNewWorkIndex(conf);
	  addExistingWorkIndex(conf);
	  addWorkIndexLabel(conf);
	  addWorkIndexTitle(conf);
	  addWorkIndexUrl(conf);
	}
	
	private void addWorkIndexUrl(EditConfigurationVTwo conf) {
    conf.addLiteralsOnForm( Arrays.asList(WORK_INDEX_URL));
    conf.addN3Optional(Arrays.asList(n3ForNewWokIndexUrl));
    conf.addField( new FieldVTwo().
        setName(WORK_INDEX_URL).
        setRangeDatatypeUri(XSD.xstring.toString() ));
	}
	
	private static final String n3ForNewWokIndexUrl = ""
		+ VAR + NEW_WORK_INDEX + SPACE + GESAH_INDEX_URL_L + SPACE + VAR + WORK_INDEX_URL + LINE_END;
	

	private void addWorkIndexTitle(EditConfigurationVTwo conf) {
    conf.addLiteralsOnForm( Arrays.asList(WORK_INDEX_TITLE));
    conf.addN3Optional(Arrays.asList(n3ForNewWokIndexTitle));
    conf.addField( new FieldVTwo().
        setName(WORK_INDEX_TITLE).
        setRangeDatatypeUri( XSD.xstring.toString() ).
        setValidators(list(DATATYPE + XSD.xstring.toString())));
	}
	
	private static final String n3ForNewWokIndexTitle = ""
		+ VAR + NEW_WORK_INDEX + SPACE + GESAH_LONG_TITLE_L + SPACE + VAR + WORK_INDEX_TITLE + LINE_END;
	
	private void addWorkIndexLabel(EditConfigurationVTwo conf) {
    conf.addSparqlForExistingLiteral(WORK_INDEX_LABEL, existingWorkIndexLabelQuery);
    conf.addLiteralsOnForm( Arrays.asList(WORK_INDEX_LABEL));
		conf.addField( new FieldVTwo().
        setName(WORK_INDEX_LABEL).
        setRangeDatatypeUri(XSD.xstring.toString() ));
	}
	
	private String existingWorkIndexLabelQuery = ""
			+ SELECT + VAR + WORK_INDEX_LABEL + WHERE
			+ VAR + WORK_INDEX_ENTRY + GESAH_IS_LISTED_IN_L  + VAR + WORK_INDEX + LINE_END
			+ VAR + WORK_INDEX + GESAH_HAS_ENTRY_L + VAR + WORK_INDEX_ENTRY + LINE_END 
			+ VAR + WORK_INDEX + A + GESAH_WORK_INDEX_L + LINE_END 
			+ VAR + WORK_INDEX + LT + LABEL + GT + VAR + WORK_INDEX_LABEL + LINE_END
			+ "}";
	
	private void addNewWorkIndex(EditConfigurationVTwo conf) throws Exception {
    conf.addNewResource(NEW_WORK_INDEX, DEFAULT_NS_FOR_NEW_RESOURCE);
    conf.addUrisOnForm(Arrays.asList( NEW_WORK_INDEX ));
    conf.addN3Optional(Arrays.asList(n3ForNewMarkWorkIndexWithLabel));
    conf.addField( new FieldVTwo().
        setName(WORK_INDEX).
        setOptions( new IndividualsViaVClassOptions(
        		GESAH_WORK_INDEX)));		
	}
	
	private static final String n3ForNewMarkWorkIndexWithLabel = ""
  		+ VAR + WORK_INDEX_ENTRY + SPACE + GESAH_IS_LISTED_IN_L + SPACE + VAR + NEW_WORK_INDEX + LINE_END
  		+ VAR + NEW_WORK_INDEX + SPACE + GESAH_HAS_ENTRY_L + SPACE + VAR + WORK_INDEX_ENTRY + LINE_END
  		+ VAR + NEW_WORK_INDEX + A + GESAH_WORK_INDEX_L + LINE_END
  		+ VAR + NEW_WORK_INDEX + LT + LABEL + GT + VAR + WORK_INDEX_TITLE + LINE_END;
	
	private void addExistingWorkIndex(EditConfigurationVTwo conf) throws Exception {
		conf.addSparqlForExistingUris(WORK_INDEX, existingWorkIndexQuery);
    conf.addUrisOnForm(Arrays.asList( WORK_INDEX ));

	}
	
	private String existingWorkIndexQuery = ""
	+ SELECT + VAR + WORK_INDEX + WHERE
	+ VAR + WORK_INDEX_ENTRY + GESAH_IS_LISTED_IN_L  + VAR + WORK_INDEX + LINE_END
	+ VAR + WORK_INDEX + GESAH_HAS_ENTRY_L + VAR + WORK_INDEX_ENTRY + LINE_END 
	+ VAR + WORK_INDEX + A + GESAH_WORK_INDEX_L + LINE_END 
	+ "}";

	private void addIndexNumber(EditConfigurationVTwo conf) {
		conf.addLiteralsOnForm( Arrays.asList(INDEX_NUMBER));
    conf.addSparqlForExistingLiteral(INDEX_NUMBER, sparqlForExistingIndexNumber);
		conf.addField( new FieldVTwo().
        setName(INDEX_NUMBER).
        setRangeDatatypeUri( XSD.xstring.toString() ).
        setValidators(list(DATATYPE + XSD.xstring.toString())));
	}
	
	private static final String sparqlForExistingIndexNumber = ""
			+ SELECT + "(STR(?tmpVar) as " + VAR + INDEX_NUMBER + " ) " + WHERE 
			+ VAR + WORK_INDEX_ENTRY + GESAH_INDEX_NUMBER_L + " ?tmpVar " + LINE_END
			+ "}";

	private void addWorkIndexEntryLabel(EditConfigurationVTwo conf) {
		conf.addLiteralsOnForm( Arrays.asList(WORK_INDEX_ENTRY_LABEL));
    conf.addN3Optional(Arrays.asList(n3ForNewWokIndexEntryLabel));
		conf.addSparqlForExistingLiteral(WORK_INDEX_ENTRY_LABEL, sparqlForExistingWorkIndexEntryLabel);
    conf.addField( new FieldVTwo().
        setName(WORK_INDEX_ENTRY_LABEL).
        setRangeDatatypeUri(XSD.xstring.toString() ));
	}
	
	private static final String n3ForNewWokIndexEntryLabel = ""
			+ VAR + WORK_INDEX_ENTRY + LT + LABEL + GT + VAR + WORK_INDEX_ENTRY_LABEL + LINE_END;
	
	private static final String sparqlForExistingWorkIndexEntryLabel = ""
			+ SELECT + VAR + WORK_INDEX_ENTRY_LABEL + WHERE 
			+ VAR + WORK_INDEX_ENTRY + LT + LABEL + GT + VAR + WORK_INDEX_ENTRY_LABEL + LINE_END
			+ "}";
	
	private static final String n3forNewWorkIndexEntry = ""
			+ VAR + CULT_OBJECT + SPACE + GESAH_HAS_WORK_INDEX_ENTRY_L + SPACE + VAR + WORK_INDEX_ENTRY + LINE_END
  		+ VAR + WORK_INDEX_ENTRY + SPACE + GESAH_IS_WORK_INDEX_ENTRY_OF_L + SPACE + VAR + CULT_OBJECT + LINE_END
  		+ VAR + WORK_INDEX_ENTRY + A + GESAH_WORK_INDEX_ENTRY_L + LINE_END
  		+ VAR + WORK_INDEX_ENTRY + GESAH_INDEX_NUMBER_L + VAR + INDEX_NUMBER + LINE_END;

	@Override
	protected EditMode getEditMode(EditConfigurationVTwo editConf, VitroRequest vreq) {
		List<String> predicates = new ArrayList<String>();
		predicates.add(GESAH_HAS_WORK_INDEX_ENTRY);
		return EditModeUtils.getEditMode(vreq, predicates);
	}
}
