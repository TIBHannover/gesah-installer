<#include "customFormInit.ftl">
<#include "../../lib/lib-gesah-form.ftl">	

<#assign subjectName=""/>
<#assign roleActivityUri="mysteryRoleActivityURI"/>
<#assign agentLabel="mysteryAgentLabel"/>

<#--This flag is for clearing the label field on submission for an existing object being selected from autocomplete.
Set this flag on the input acUriReceiver where you would like this behavior to occur. -->
<#assign flagClearLabelForExisting = "flagClearLabelForExisting" />

<h2>${titleVerb}&nbsp;${i18n().object_of_provenance} ${editConfiguration.subjectName}</h2>

<section id="objectOfProvenance" role="region">
	<form id="objectOfProvenance" class="customForm noIE67" action="${submitUrl}"  role="add/edit provenance">
		<@print_provenance_type />
		<#if !editConfiguration.objectUri?has_content>
			<@print_actor />
			<@print_role_type />
		</#if>
		<@print_place />
		<@print_lit_date_appeal />
		<@print_comment />
		<@print_dates />
    	<@formControls />
	</form>

</section>

<script type="text/javascript">
var customFormData  = {
    acUrl: '${urls.base}/autocomplete?tokenize=true&stem=true',
    editMode: '${editMode}',
    acTypes: {agent: 'http://xmlns.com/foaf/0.1/Agent' , place: 'http://vivoweb.org/ontology/core#GeographicLocation'},
    multipleTypeNames: { agent: 'participant' , place: 'place' },
    baseHref: '${urls.base}/individual?uri=',
    blankSentinel: '${blankSentinel}',
    flagClearLabelForExisting: '${flagClearLabelForExisting}',
    subjectName: '${editConfiguration.subjectName?js_string}'
};
var i18nStrings = {
    selectAnExisting: '${i18n().select_an_existing}',
    selectAnExistingOrCreateNewOne: '${i18n().select_an_existing_or_create_a_new_one?js_string}',
    selectedString: '${i18n().selected}'
};

</script>

