<#include "customFormInit.ftl">

<#assign subjectName=""/>
<#assign roleActivityUri="mysteryRoleActivityURI"/>
<#assign agentLabel="mysteryAgentLabel"/>

<#--This flag is for clearing the label field on submission for an existing object being selected from autocomplete.
Set this flag on the input acUriReceiver where you would like this behavior to occur. -->
<#assign flagClearLabelForExisting = "flagClearLabelForExisting" />

<#assign markDesignationLabel = lvf.getFormFieldValue(editSubmission, editConfiguration, "markDesignationLabel") />
<#assign markLocation = lvf.getFormFieldValue(editSubmission, editConfiguration, "markLocation") />

<#assign oldCollectorsMark = lvf.getFormFieldValue(editSubmission, editConfiguration, "collectorsMark") />
<#assign oldCollectorsMarkLabel = lvf.getFormFieldValue(editSubmission, editConfiguration, "collectorsMarkLabel") />
<#assign newCollectorsMarkLabelValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "newCollectorsMarkLabel") />


<h2>${titleVerb}&nbsp;mark designation for ${editConfiguration.subjectName}</h2>

<section id="markDesignation" role="region">
	<form id="markDesignation" class="customForm noIE67" action="${submitUrl}"  role="add/edit markDesignation" >
		<label for="markDesignationLabel">${i18n.mark_designation_label}</label>
		<input type="text" id="markDesignationLabel" name="markDesignationLabel" autocomplete="off" value="${markDesignationLabel}" />
		<label for="markLocation">${i18n.mark_designation_location}</label>
		<input type="text" id="markLocation" name="markLocation" autocomplete="off" value="${markLocation}" />
		<p>
            <label for="newCollectorsMarkLabel">${i18n().mark_designation_collectors_mark}</label>
            <input type="text" id="collectors_mark" name="newCollectorsMarkLabel" acGroupName="collectors_mark" size="50" class="acSelector" value="${newCollectorsMarkLabelValue}"  />
            <input class="display" type="hidden" id="newCollectorsMarkLabel" acGroupName="collectors_mark" name="placeLabelDisplay" value="${oldCollectorsMarkLabel}">
        </p>
        <div class="acSelection" id="typeSelector" acGroupName="collectors_mark">
            <p class="inline">
                <label>${i18n.mark_designation_select_collectors_mark}</label>
                <span class="acSelectionInfo"></span>
                <a href="" class="verifyMatch" title="${i18n().verify_match_capitalized}">(${i18n().verify_match_capitalized}</a> ${i18n().or}
                <a href="#" class="changeSelection" id="changeSelection">${i18n().change_selection})</a>
            </p>
            <input class="acUriReceiver" type="hidden" id="collectorsMarkUri" name="existingPlace" value="${oldCollectorsMark}" ${flagClearLabelForExisting}="true" />
        </div>
	<@formControls />
    </form>
</section>

<script type="text/javascript">
var customFormData  = {
    acUrl: '${urls.base}/autocomplete?tokenize=true&stem=true',
    editMode: '${editMode}',
    acTypes: {collectors_mark: 'http://ontology.tib.eu/gesah/Mark'},
    multipleTypeNames: { collectors_mark: 'collectors_mark' },
    defaultTypeName: 'collectors_mark',
    baseHref: '${urls.base}/individual?uri=',
    blankSentinel: '${blankSentinel}',
    flagClearLabelForExisting: '${flagClearLabelForExisting}',
    subjectName: '${editConfiguration.subjectName}'
};
var i18nStrings = {
    selectAnExisting: '${i18n().select_an_existing}',
    orCreateNewOne: '${i18n().or_create_new_one}',
    selectedString: '${i18n().selected}'
};
</script>  
