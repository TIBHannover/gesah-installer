<#include "customFormInit.ftl">
<#include "../../lib/lib-gesah-form.ftl">	

<#assign subjectName=""/>
<#assign roleActivityUri="mysteryRoleActivityURI"/>

<#--This flag is for clearing the label field on submission for an existing object being selected from autocomplete.
Set this flag on the input acUriReceiver where you would like this behavior to occur. -->
<#assign flagClearLabelForExisting = "flagClearLabelForExisting" />

<h2>${titleVerb}&nbsp;${i18n().object_of_event} ${editConfiguration.subjectName}</h2>

<section id="objectOfProvenance" role="region">
	<form id="objectOfProvenance" class="customForm noIE67" action="${submitUrl}"  role="add/edit provenance">
		<#assign existingEvent = lvf.getFormFieldValue(editSubmission, editConfiguration, "cultObjectVarName") />
		<#if !existingEvent?has_content>
			<@print_event />
		<#else>
			<@print_organizer />
			<@print_place />
			<@print_comment />
			<@print_dates />
			<#list 1..3 as num>
			  <@print_publication num />
			</#list>
		</#if>
		<@formControls />
	</form>

</section>

<script type="text/javascript">
var customFormData  = {
    acUrl: '${urls.base}/autocomplete?tokenize=true&stem=true',
    editMode: '${editMode}',
    acTypes: {organizer: 'http://xmlns.com/foaf/0.1/Agent' , place: 'http://vivoweb.org/ontology/core#GeographicLocation', event: 'http://purl.org/NET/c4dm/event.owl#Event'},
    multipleTypeNames: { organizer: 'organizer' , place: 'place', event: 'event' },
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

<#macro print_event>
	<#assign eventType = lvf.getFormFieldValue(editSubmission, editConfiguration, "eventType") />
    <#assign eventTypes = editConfiguration.pageData.eventType />
    <#assign eventLabelDisplay = lvf.getFormFieldValue(editSubmission, editConfiguration, "eventLabelDisplay") />

	<p class="inline">
        <label for="eventType">${i18n().event_type} ${requiredHint}</label>
        <select id="typeSelector" name="eventType" acGroupName="event">
            <option value="" selected="selected">${i18n().select_one}</option>
            <#list eventTypes?keys as key>
            	<#if eventType = key>
					<option value="${key}" selected>${eventTypes[key]}</option>
                <#else>
					<option value="${key}">${eventTypes[key]}</option>
                </#if>
            </#list>
        </select>
    </p>
	<p>
        <label for="relatedEventLabel">${i18n().event_capitalized} ${requiredHint}</label>
        <input class="acSelector" size="50"  type="text" id="relatedEventLabel" name="eventLabel" acGroupName="event" value="${eventLabelDisplay}"  />
        <input class="display" type="hidden" id="eventDisplay" acGroupName="event" name="eventLabelDisplay" value="">
    </p>

    <div class="acSelection" acGroupName="event">
        <p class="inline">
            <label>${i18n().selected_event}:</label>
            <span class="acSelectionInfo"></span>
            <a href="" class="verifyMatch"  title="${i18n().verify_match_capitalized}">(${i18n().verify_match_capitalized}</a> ${i18n().or}
            <a href="#" class="changeSelection" id="changeSelection">${i18n().change_selection})</a>
        </p>
        <input class="acUriReceiver" type="hidden" id="eventUri" name="existingEvent" value="${existingEvent}" ${flagClearLabelForExisting}="true" />
    </div>
</#macro>

<#macro print_organizer>
		<#assign organizerType = lvf.getFormFieldValue(editSubmission, editConfiguration, "organizerType")/>
		<#assign organizerLabel = lvf.getFormFieldValue(editSubmission, editConfiguration, "organizerLabel") />
		<#assign organizerLabelDisplay = lvf.getFormFieldValue(editSubmission, editConfiguration, "organizerLabelDisplay") />
		<#assign existingOrganizer = lvf.getFormFieldValue(editSubmission, editConfiguration, "existingOrganizer") />
        <#assign organizerTypeOpts = editConfiguration.pageData.organizerType />

		<p class="inline">
            <label for="organizerType">${i18n().organizer_type_capitalized}  ${requiredHint}</label>
            <select id="typeSelector" name="organizerType" acGroupName="organizer">
                <option value="" selected="selected">${i18n().select_one}</option>
                <#list organizerTypeOpts?keys as key>
                    <#if organizerType = key>
                        <option value="${key}" selected>${organizerTypeOpts[key]}</option>
                    <#else>
                        <option value="${key}">${organizerTypeOpts[key]}</option>
                    </#if>
                </#list>
            </select>
        </p>
        <p>
            <label for="relatedOrgLabel">${i18n().organizer_capitalized} ${i18n().name_capitalized} ${requiredHint}</label>
            <input class="acSelector" size="50"  type="text" id="relatedOrgLabel" name="organizerLabel" acGroupName="organizer" value="${organizerLabel}"  />
            <input class="display" type="hidden" id="organizerDisplay" acGroupName="organizer" name="organizerLabelDisplay" value="${organizerLabelDisplay}">
        </p>
        <div class="acSelection" acGroupName="organizer">
            <p class="inline">
                <label>${i18n().selected_organizer}:</label>
                <span class="acSelectionInfo"></span>
                <a href="" class="verifyMatch"  title="${i18n().verify_match_capitalized}">(${i18n().verify_match_capitalized}</a> ${i18n().or}
                <a href="#" class="changeSelection" id="changeSelection">${i18n().change_selection})</a>
            </p>
            <input class="acUriReceiver" type="hidden" id="organizerUri" name="existingOrganizer" value="${existingOrganizer}" ${flagClearLabelForExisting}="true" />
        </div>
</#macro>

