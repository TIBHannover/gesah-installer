<#include "customFormInit.ftl">
<#include "../../lib/lib-gesah-form.ftl">

<#assign subjectName=""/>
<#assign roleActivityUri="mysteryRoleActivityURI"/>
<#assign agentLabel="mysteryAgentLabel"/>

<#--This flag is for clearing the label field on submission for an existing object being selected from autocomplete.
Set this flag on the input acUriReceiver where you would like this behavior to occur. -->
<#assign flagClearLabelForExisting = "flagClearLabelForExisting" />

<h2>${i18n().role_for} ${editConfiguration.subjectName}</h2>

<#include "objectHasCreationErrors.ftl" />

<section id="objectHasCreation" role="region">
    <form id="objectHasCreation" class="customForm noIE67" action="${submitUrl}" role="add/edit role">
        <@print_actor />
        <@print_role_type />
        
		<#assign isAttribution = lvf.getFormFieldValue(editSubmission, editConfiguration, "isAttribution") />
		<#if isAttribution?has_content && isAttribution = "true" >
			<@print_attribution_type />
		</#if>
        <@formControls />
    </form>
</section>
<script type="text/javascript">
	var customFormData  = {
	    acUrl: '${urls.base}/autocomplete?tokenize=true&stem=true',
	    editMode: '${editMode}',
	    acTypes: {agent: 'http://xmlns.com/foaf/0.1/Agent'},
	    multipleTypeNames: { agent: 'participant'},
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
