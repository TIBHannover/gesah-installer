<#include "customFormInit.ftl">
<#include "../../lib/lib-gesah-form.ftl">
<#include "../../lib/lib-gesah-view.ftl">


<#assign subjectName=""/>
<#assign roleActivityUri="mysteryRoleActivityURI"/>
<#assign agentLabel="mysteryAgentLabel"/>

<#--This flag is for clearing the label field on submission for an existing object being selected from autocomplete.
Set this flag on the input acUriReceiver where you would like this behavior to occur. -->
<#assign flagClearLabelForExisting = "flagClearLabelForExisting" />

<h2>${titleVerb}&nbsp;${i18n().creation_for} ${editConfiguration.subjectName}</h2>

<#include "objectHasCreationErrors.ftl" />

<section id="objectHasCreation" role="region">
    <form id="objectHasCreation" class="customForm noIE67" action="${submitUrl}"  role="add/edit creation">
        <#if !editConfiguration.objectUri?has_content>
        	<@print_actor />
            <@print_role_type />
            <@print_attribution_type />
        </#if>
        <@print_material />
        <@print_technique />
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
