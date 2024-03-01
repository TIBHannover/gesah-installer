/* $This file is distributed under the terms of the license in LICENSE$ */
package edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import edu.cornell.mannlib.vitro.webapp.controller.VitroRequest;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.EditConfigurationVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.fields.FieldVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.fields.IndividualsViaVClassOptions;
import edu.cornell.mannlib.vitro.webapp.utils.FrontEndEditingUtils.EditMode;
import edu.cornell.mannlib.vitro.webapp.utils.generators.EditModeUtils;
import org.apache.jena.vocabulary.XSD;

public class MarkDesignationGenerator extends AbstractCulturalObjectGenerator implements EditConfigurationGenerator {

	private static final String GESAH_MARK_DESIGNATION_OF = LT + GESAH + "mark_designation_of" + GT;
	private static final String MARK_DESIGNATION = "mark_designation";
	private static final String GESAH_HAS_MARK_DESIGNATION = LT + GESAH + "has_mark_designation" + GT;
	private static final String MARK_DESIGNATION_FTL = "markDesignation.ftl";
	private static final String GESAH_MARK_DESIGNATION_CLASS = LT + GESAH + "Mark_Designation" + GT;
	private static final String MARK_LOCATION = "markLocation";

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
	// private static final String MARK_DESIGNATION_LABEL = "markDesignationLabel";
	private static final String EXISTING_MARK_DESIGNATION_LABEL = "existingMarkDesignationLabel";
	private static final String EXISTING_MARK_LABEL = "existingMarkLabel";
	private static final String COLLECTORS_MARK_LABEL = "collectorsMarkLabel";

	@Override
	protected void configureForm(VitroRequest vreq, HttpSession session, EditConfigurationVTwo conf) throws Exception {
		conf.setTemplate(MARK_DESIGNATION_FTL);

		conf.setVarNameForSubject(CULT_OBJECT);
		conf.setVarNameForPredicate(PREDICATE);
		conf.setVarNameForObject(MARK_DESIGNATION);

		conf.setN3Required(Arrays.asList(n3ForNewMarkDesignation));
		conf.addN3Optional(Arrays.asList(n3SelectedMark));

		conf.addNewResource(MARK_DESIGNATION, DEFAULT_NS_FOR_NEW_RESOURCE);

		addNewCollectorsMark(conf);
		addExistingCollectorsMark(conf);
		addExistingMarkLabel(conf);
		addMarkId(conf);
		addMarkUrl(conf);
		addMarkTitle(conf);
		// addMarkDesignationLabel(conf);
		addMarkLocation(conf);
		addComment(conf);
	}

	private void addMarkUrl(EditConfigurationVTwo conf) {
		conf.addN3Optional(Arrays.asList(n3ForNewMarkUrl));
		conf.addLiteralsOnForm(Arrays.asList(MARK_URL));
		conf.addField(new FieldVTwo().setName(MARK_URL).setRangeDatatypeUri(XSD.xstring.toString()));
	}

	/*
	 * private void addMarkDesignationLabel(EditConfigurationVTwo conf) {
	 * conf.addN3Optional(Arrays.asList(n3ForNewMarkDesignationLabel));
	 * conf.addLiteralsOnForm( Arrays.asList(MARK_DESIGNATION_LABEL));
	 * conf.addSparqlForExistingLiteral(MARK_DESIGNATION_LABEL,
	 * sparqlForExistingMarkDesignationLabel); conf.addField( new FieldVTwo().
	 * setName(MARK_DESIGNATION_LABEL). setRangeDatatypeUri(XSD.xstring.toString()
	 * )); }
	 * 
	 * final static String n3ForNewMarkDesignationLabel = "" + VAR +
	 * MARK_DESIGNATION + LT + LABEL + GT + VAR + MARK_DESIGNATION_LABEL + LINE_END;
	 */

	private void addMarkLocation(EditConfigurationVTwo conf) {
		conf.addN3Optional(Arrays.asList(n3ForNewMarkLocation));
		conf.addLiteralsOnForm(Arrays.asList(MARK_LOCATION));
		conf.addSparqlForExistingLiteral(MARK_LOCATION, sparqlForExistingMarkLocationValue);

		conf.addField(new FieldVTwo().
				setName(MARK_LOCATION).
				setRangeDatatypeUri(XSD.xstring.toString()).
        setValidators(list(DATATYPE + XSD.xstring.toString())));
	}

	final static String sparqlForExistingMarkLocationValue = "" 
			+ SELECT + VAR + MARK_LOCATION 
			+ WHERE + VAR + MARK_DESIGNATION + GESAH_MARK_LOCATION + VAR + MARK_LOCATION + LINE_END 
			+ "}";
	
	protected void addComment(EditConfigurationVTwo conf) {
		conf.addN3Optional(Arrays.asList(n3ForNewMarkComment));
		conf.addLiteralsOnForm(Arrays.asList(COMMENT));
		conf.addField( new FieldVTwo().
        setName(COMMENT).
        setRangeDatatypeUri( org.apache.jena.vocabulary.RDFS.Literal.getURI() ).
        setValidators(list(DATATYPE + XSD.xstring.toString())));
	}

	private void addMarkTitle(EditConfigurationVTwo conf) {
		conf.addLiteralsOnForm(Arrays.asList(MARK_TITLE));
		conf.addField(new FieldVTwo().setName(MARK_TITLE).setRangeDatatypeUri(XSD.xstring.toString()));
	}

	private void addMarkId(EditConfigurationVTwo conf) {
		conf.addLiteralsOnForm(Arrays.asList(MARK_ID));
		conf.addN3Optional(Arrays.asList(n3ForNewMarkId));
		conf.addField(new FieldVTwo().
				setName(MARK_ID).
				setRangeDatatypeUri(XSD.xstring.toString()).
        setValidators(list(DATATYPE + XSD.xstring.toString())));
	}

	private void addExistingMarkLabel(EditConfigurationVTwo conf) {
		conf.addSparqlForExistingLiteral(COLLECTORS_MARK_LABEL, existingForMarkLabelQuery);
		conf.addLiteralsOnForm(Arrays.asList(COLLECTORS_MARK_LABEL));
		conf.addField(new FieldVTwo().setName(COLLECTORS_MARK_LABEL).setRangeDatatypeUri(XSD.xstring.toString()));
	}

	final static String existingForMarkLabelQuery = "" + SELECT + VAR + EXISTING_MARK_LABEL + WHERE + VAR
			+ MARK_DESIGNATION + GESAH_USES_MARK + VAR + EXISTING_MARK_URI + LINE_END + VAR + EXISTING_MARK_URI
			+ GESAH_MARK_USED_IN + VAR + MARK_DESIGNATION + LINE_END + VAR + EXISTING_MARK_URI + A + GESAH_MARK_CLASS
			+ LINE_END + VAR + EXISTING_MARK_URI + LT + LABEL + GT + VAR + EXISTING_MARK_LABEL + LINE_END + "}";

	private void addNewCollectorsMark(EditConfigurationVTwo conf) throws Exception {
		conf.addN3Optional(Arrays.asList(n3ForNewMarkWithTitle));
		conf.addNewResource(NEW_COLLECTORS_MARK, DEFAULT_NS_FOR_NEW_RESOURCE);
		conf.addUrisOnForm(Arrays.asList(NEW_COLLECTORS_MARK));
	}

	final static String n3ForNewMarkWithTitle = "" + VAR + MARK_DESIGNATION + SPACE + GESAH_USES_MARK + SPACE + VAR
			+ NEW_COLLECTORS_MARK + LINE_END + VAR + NEW_COLLECTORS_MARK + SPACE + GESAH_IS_MARK_USED_IN + SPACE + VAR
			+ MARK_DESIGNATION + LINE_END + VAR + NEW_COLLECTORS_MARK + A + GESAH_MARK_CLASS + LINE_END + VAR
			+ NEW_COLLECTORS_MARK + LT + LABEL + GT + VAR + MARK_TITLE + LINE_END;

	private void addExistingCollectorsMark(EditConfigurationVTwo conf) throws Exception {
		conf.addSparqlForExistingUris(COLLECTORS_MARK, existingMarkQuery);
		conf.addUrisOnForm(Arrays.asList(COLLECTORS_MARK));
		conf.addField(new FieldVTwo().setName(COLLECTORS_MARK).setOptions(new IndividualsViaVClassOptions(GESAH + "Mark")));
	}

	final static String n3SelectedMark = "" + VAR + MARK_DESIGNATION + SPACE + GESAH_USES_MARK + SPACE + VAR
			+ COLLECTORS_MARK + LINE_END + VAR + COLLECTORS_MARK + SPACE + GESAH_IS_MARK_USED_IN + SPACE + VAR
			+ MARK_DESIGNATION + LINE_END;

	final static String existingMarkQuery = "" + SELECT + VAR + EXISTING_MARK_URI + WHERE + VAR + MARK_DESIGNATION
			+ GESAH_USES_MARK + VAR + EXISTING_MARK_URI + LINE_END + VAR + EXISTING_MARK_URI + GESAH_MARK_USED_IN + VAR
			+ MARK_DESIGNATION + LINE_END + VAR + EXISTING_MARK_URI + A + GESAH_MARK_CLASS + LINE_END + "}";

	final static String sparqlForExistingMarkDesignationLabel = "" + SELECT + VAR + EXISTING_MARK_DESIGNATION_LABEL
			+ WHERE + VAR + MARK_DESIGNATION + LT + LABEL + GT + VAR + EXISTING_MARK_DESIGNATION_LABEL + LINE_END + "}";


	final static String n3ForNewMarkDesignation = "" + VAR + CULT_OBJECT + SPACE + GESAH_HAS_MARK_DESIGNATION + SPACE
			+ VAR + MARK_DESIGNATION + LINE_END + VAR + MARK_DESIGNATION + SPACE + GESAH_MARK_DESIGNATION_OF + SPACE + VAR
			+ CULT_OBJECT + LINE_END + VAR + MARK_DESIGNATION + A + GESAH_MARK_DESIGNATION_CLASS + LINE_END;

	final static String n3ForNewMarkLocation = VAR + MARK_DESIGNATION + GESAH_MARK_LOCATION + VAR + MARK_LOCATION
			+ LINE_END;

	final static String n3ForNewMarkId = "" + VAR + NEW_COLLECTORS_MARK + SPACE + GESAH_MARK_ID + SPACE + VAR + MARK_ID
			+ LINE_END;

	final static String n3ForNewMarkUrl = "" + VAR + NEW_COLLECTORS_MARK + SPACE + GESAH_MARK_URL + SPACE + VAR + MARK_URL
			+ LINE_END;

	final static String n3ForNewMarkComment = "" + VAR + NEW_COLLECTORS_MARK + SPACE + LT + GESAH_COMMENT + GT + SPACE
			+ VAR + COMMENT + LINE_END;

	@Override
	protected EditMode getEditMode(EditConfigurationVTwo editConf, VitroRequest vreq) {
		List<String> predicates = new ArrayList<String>();
		predicates.add(GESAH + "has_mark_designation");
		return EditModeUtils.getEditMode(vreq, predicates);
	}
}
