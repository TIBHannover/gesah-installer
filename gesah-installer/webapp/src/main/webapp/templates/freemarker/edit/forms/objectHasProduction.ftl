<#-- $This file is distributed under the terms of the license in LICENSE$ -->

<#-- this is in request.subject.name -->

<#-- leaving this edit/add mode code in for reference in case we decide we need it -->

<#import "lib-vivo-form.ftl" as lvf>

<#assign subjectName=""/>
<#assign roleActivityUri="mysteryRoleActivityURI"/>
<#assign agentLabel="mysteryAgentLabel"/>

<#--Retrieve certain edit configuration information-->
<#-- assign editMode = editConfiguration.pageData.editMode /-->

<#--Retrieve certain edit configuration information-->
<#if editConfiguration.objectUri?has_content>
    <#assign editMode = "edit">
<#else>
    <#assign editMode = "add">
</#if>

<#assign htmlForElements = editConfiguration.pageData.htmlForElements />

<#--The blank sentinel indicates what value should be put in a URI when no autocomplete result has been selected.
If the blank value is non-null or non-empty, n3 editing for an existing object will remove the original relationship
if nothing is selected for that object-->
<#assign blankSentinel = "" />
<#if editConfigurationConstants?has_content && editConfigurationConstants?keys?seq_contains("BLANK_SENTINEL")>
	<#assign blankSentinel = editConfigurationConstants["BLANK_SENTINEL"] />
</#if>

<#--This flag is for clearing the label field on submission for an existing object being selected from autocomplete.
Set this flag on the input acUriReceiver where you would like this behavior to occur. -->
<#assign flagClearLabelForExisting = "flagClearLabelForExisting" />

<#--Retrieve variables needed-->
<#assign newAgentValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "newAgent")/>
<#assign agentLabelValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "agentLabel") />
<#assign agentLabelDisplayValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "agentLabelDisplay") />
<#assign agentTypeValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "agentType")/>
<#assign litDateAppelValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "litDateAppel") />
<#assign descriptionValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "description") />
<#assign newRoleTypeValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "newRoleType") />
<#assign newRoleTypeLabelValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "newRoleTypeLabel") />
<#assign newAttrTypeValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "newAttrType") />
<#assign newAttrTypeLabelValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "newAttrTypeLabel") />
<#assign newTechniqueValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "newTechnique") />
<#assign newTechniqueLabelValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "newTechniqueLabel") />
<#assign newMaterialValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "newMaterial") />
<#assign newMaterialLabelValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "newMaterialLabel") />
<#assign newPlaceValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "newPlace") />
<#assign placeLabelValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "placeLabel") />
<#assign existingAgentValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "existingAgent") />
<#assign existingMaterialValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "existingMaterial") />
<#assign existingMaterialLabelValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "existingMaterialLabel") />
<#assign existingTechniqueValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "existingTechnique") />
<#assign existingTechniqueLabelValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "existingTechniqueLabel") />
<#assign existingAttrTypeValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "existingAttrType") />
<#assign existingAttrTypeLabelValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "existingAttrTypeLabel") />
<#assign existingRoleTypeValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "existingRoleType") />
<#assign existingRoleTypeLabelValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "existingRoleTypeLabel") />
<#assign existingPlaceValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "existingPlace") />
<#assign placeLabelDisplayValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "placeDisplay") />

<#-- If edit submission exists, then retrieve validation errors if they exist-->
<#if editSubmission?has_content && editSubmission.submissionExists = true && editSubmission.validationErrors?has_content>
	<#assign submissionErrors = editSubmission.validationErrors/>
</#if>

<#if editMode == "edit" || editMode == "repair">
        <#assign titleVerb="${i18n().edit_capitalized}">
        <#assign submitButtonText="${i18n().save_changes}">
        <#assign disabledVal="disabled">
<#else>
        <#assign titleVerb="${i18n().create_capitalized}">
        <#assign submitButtonText="${i18n().create_entry}">
        <#assign disabledVal=""/>
</#if>

<#assign requiredHint = "<span class='requiredHint'> *</span>" />
<#assign yearHint     = "<span class='hint'>(${i18n().year_hint_format})</span>" />


<h2>${titleVerb}&nbsp;${i18n().production_for} ${editConfiguration.subjectName}</h2>

<#--Display error messages if any-->
<#if submissionErrors?has_content>
    <#if agentLabelDisplayValue?has_content >
        <#assign agentLabelValue = agentLabelDisplayValue />
    </#if>
	<#if placeLabelDisplayValue?has_content >
        <#assign placeLabelValue = placeLabelDisplayValue />
    </#if>
    <section id="error-alert" role="alert">
        <img src="${urls.images}/iconAlert.png" width="24" height="24" alert="${i18n().error_alert_icon}" />
        <p>
        <#--below shows examples of both printing out all error messages and checking the error message for a specific field-->
        <#list submissionErrors?keys as errorFieldName>
        	<#if errorFieldName == "startField">
        	    <#if submissionErrors[errorFieldName]?contains("before")>
        	        ${i18n().start_year_must_precede_end}
        	    <#else>
        	        ${submissionErrors[errorFieldName]}
        	    </#if>
        	    <br />
        	<#elseif errorFieldName == "endField">
    	        <#if submissionErrors[errorFieldName]?contains("after")>
    	            ${i18n().end_year_must_be_later}
    	        <#else>
    	            ${submissionErrors[errorFieldName]}
    	        </#if>
	        </#if>
        </#list>
        <#--Checking if Agent field is empty-->
         <#if lvf.submissionErrorExists(editSubmission, "newAgent")>
 	        ${i18n().select_a_participant}
        </#if>
        <#--Checking if Agent Name field is empty-->
         <#if lvf.submissionErrorExists(editSubmission, "agentLabel")>
 	        ${i18n().select_a_participant}
        </#if>
		<#--Checking if Agent Type field is empty-->
         <#if lvf.submissionErrorExists(editSubmission, "agentType")>
 	        ${i18n().select_participant_type}
        </#if>
        <#--Checking if Attribution Type field is empty-->
         <#if lvf.submissionErrorExists(editSubmission, "newAttrType")>
 	        ${i18n().select_attribution_type_value}<br />
        </#if>
		<#--Checking if Technique field is empty-->
         <#if lvf.submissionErrorExists(editSubmission, "newTechnique")>
 	        ${i18n().select_technique_value}<br />
        </#if>
		<#--Checking if Material field is empty-->
         <#if lvf.submissionErrorExists(editSubmission, "newMaterial")>
 	        ${i18n().select_material_value}<br />
        </#if>
		<#--Checking if Role Type field is empty-->
         <#if lvf.submissionErrorExists(editSubmission, "newRoleType")>
 	        ${i18n().select_role_type_value}<br />
        </#if>
		<#--Checking if Place field is empty-->
         <#if lvf.submissionErrorExists(editSubmission, "newPlace")>
 	        ${i18n().select_a_place}
        </#if>
        <#--Checking if Place Name field is empty-->
         <#if lvf.submissionErrorExists(editSubmission, "placeLabel")>
 	        ${i18n().select_a_place}
        </#if>

        </p>
    </section>
</#if>

<@lvf.unsupportedBrowser urls.base />

<section id="objectHasProduction" role="region">

    <form id="objectHasProduction" class="customForm noIE67" action="${submitUrl}"  role="add/edit production">
	
	<p class="inline">
        <label for="agentType">${i18n().agent_type_capitalized} ${requiredHint}</label>
        <#assign agentTypeOpts = editConfiguration.pageData.agentType />
        <select id="typeSelector" name="agentType" acGroupName="agentType">
            <option value="" selected="selected">${i18n().select_one}</option>
            <#list agentTypeOpts?keys as key>
                <#if agentTypeValue = key>
                    <option value="${key}"  selected >${agentTypeOpts[key]}</option>
                <#else>
                    <option value="${key}">${agentTypeOpts[key]}</option>
                </#if>
            </#list>
        </select>
    </p>

    <p>
        <label for="relatedIndLabel">${i18n().agent_capitalized} ${i18n().name_capitalized} ${requiredHint}</label>
        <input class="acSelector" size="50"  type="text" id="relatedIndLabel" name="agentLabel" acGroupName="agent" value="${agentLabelValue}"  />
        <input class="display" type="hidden" id="agentDisplay" acGroupName="agent" name="agentLabelDisplay" value="${agentLabelDisplayValue}">
    </p>

    <div class="acSelection" acGroupName="agent">
        <p class="inline">
            <label>${i18n().selected_agent}:</label>
            <span class="acSelectionInfo"></span>
            <a href="" class="verifyMatch"  title="${i18n().verify_match_capitalized}">(${i18n().verify_match_capitalized}</a> ${i18n().or}
            <a href="#" class="changeSelection" id="changeSelection">${i18n().change_selection})</a>
        </p>
        <input class="acUriReceiver" type="hidden" id="agentUri" name="existingAgent" value="${existingAgentValue}" ${flagClearLabelForExisting}="true" />
    </div>


	<div class="entry">
		<label for="roleType">${i18n().role_type} </label>
		<#assign existingRoleTypeOpts = editConfiguration.pageData.existingRoleType />
		<select name="existingRoleType" style="margin-top:-2px" >
			<option value="" <#if existingRoleTypeValue == "">selected</#if>>${i18n().select_one}</option>
			<#list existingRoleTypeOpts?keys as key>
				<option value="${key}"  <#if existingRoleTypeValue == key>selected</#if>><#if existingRoleTypeOpts[key] == "Other">${i18n().or_other}<#else>${existingRoleTypeOpts[key]}</#if></option>
			</#list>
		</select>
<#--
      <#if editMode == "edit" || editMode == "repair">
            <input type="hidden" id="newRoleTypeLabel" name="roleTypeLabel" value=""/>
            <input type="hidden" id="roleTypeLabel" name="existingRoleTypeLabel" value="${existingRoleTypeLabelValue!}"/>
      <#else>
            <input type="hidden" id="roleTypeLabel" name="roleTypeLabel" value=""/>
      </#if>
-->
            <input type="hidden" id="roleTypeLabel" name="roleTypeLabel" value=""/>
    </div>		

	<div class="entry">
		<label for="attributionType">${i18n().attribution_type} </label>
		<#assign existingAttrTypeOpts = editConfiguration.pageData.existingAttrType />
		<select name="existingAttrType" style="margin-top:-2px" >
			<option value="" <#if existingAttrTypeValue == "">selected</#if>>${i18n().select_one}</option>
			<#list existingAttrTypeOpts?keys as key>
				<option value="${key}"  <#if existingAttrTypeValue == key>selected</#if>><#if existingAttrTypeOpts[key] == "Other">${i18n().or_other}<#else>${existingAttrTypeOpts[key]}</#if></option>
			</#list>
		</select>
		
<#--
		<#if editMode == "edit" || editMode == "repair">
				<input type="hidden" id="newAttrTypeLabel" name="attributionTypeLabel" value=""/>
				<input type="hidden" id="attributionTypeLabel" name="existingAttrTypeLabel" value="${existingAttrTypeLabel!}"/>
		<#else>
				<input type="hidden" id="attributionTypeLabel" name="attributionTypeLabel" value=""/>
		</#if>
-->
				<input type="hidden" id="attributionTypeLabel" name="attributionTypeLabel" value=""/>
	
	</div>			
	
	<div class="entry">	
		<label for="material">${i18n().material} ${requiredHint}</label>
		<#assign existingMaterialOpts = editConfiguration.pageData.existingMaterial />
		<select name="existingMaterial" style="margin-top:-2px" >
			<option value="" <#if existingMaterialValue == "">selected</#if>>${i18n().select_one}</option>
			<#list existingMaterialOpts?keys as key>
				<option value="${key}"  <#if existingMaterialValue == key>selected</#if>><#if existingMaterialOpts[key] == "Other">${i18n().or_other}<#else>${existingMaterialOpts[key]}</#if></option>
			</#list>
		</select>
	
<#--
		<#if editMode == "edit" || editMode == "repair">
				<input type="hidden" id="newMaterialLabel" name="materialLabel" value=""/>
				<input type="hidden" id="materialLabel" name="existingMaterialLabel" value="${existingMaterialLabelValue!}"/>
		<#else>
				<input type="hidden" id="materialLabel" name="materialLabel" value=""/>
		</#if>
-->
				<input type="hidden" id="materialLabel" name="materialLabel" value=""/>
	
	</div>	
	
	
	
	<div class="entry">
      <label for="techniqueUri">${i18n().technique} ${requiredHint}</label>

      <#assign existingTechniqueOpts = editConfiguration.pageData.existingTechnique />
      <select name="existingTechnique" id="techniqueUri" >
        <option value="" <#if existingTechniqueValue = "">selected</#if>>${i18n().select_one}</option>
               <#list existingTechniqueOpts?keys as key>
        <option value="${key}" <#if existingTechniqueValue = key>selected</#if>>${existingTechniqueOpts[key]}</option>
        </#list>
      </select>
<#--
      <#if editMode == "edit" || editMode == "repair">
            <input type="hidden" id="newTechniqueLabel" name="techniqueLabel" value=""/>
            <input type="hidden" id="techniqueLabel" name="existingTechniqueLabel" value="${existingTechniqueLabelValue!}"/>
      <#else>
            <input type="hidden" id="techniqueLabel" name="techniqueLabel" value=""/>
      </#if>
-->
            <input type="hidden" id="techniqueLabel" name="techniqueLabel" value=""/>
    </div>
	
	
	
    <p>
        <label for="placeLabel">${i18n().place_name_capitalized} </label>
        <input type="text" id="placeLabel" name="placeLabel" acGroupName="place" size="50" class="acSelector" value="${placeLabelValue}"  />
        <input class="display" type="hidden" id="placeLabel" acGroupName="place" name="placeLabelDisplay" value="${placeLabelDisplayValue}">
    </p>

    <div class="acSelection" acGroupName="place">
        <p class="inline">
            <label>${i18n().selected_place}:</label>
            <span class="acSelectionInfo"></span>
            <a href="" class="verifyMatch"  title="${i18n().verify_match_capitalized}">(${i18n().verify_match_capitalized}</a> ${i18n().or}
            <a href="#" class="changeSelection" id="changeSelection">${i18n().change_selection})</a>
        </p>
        <input class="acUriReceiver" type="hidden" id="placeUri" name="existingPlace" value="${existingPlaceValue}" ${flagClearLabelForExisting}="true" />
    </div>	
	
    <p>
        <label for="litDateAppel">${i18n().literal_date_appelatlion}</label>
        <input type="text" id="litDateAppel" name="litDateAppel" size="30" value="${litDateAppelValue}"/>
    </p>

    <p>
        <label for="description">${i18n().description}
            <span class="hint">&nbsp;${i18n().supplemental_information_hint_origin}</span>
        </label>
        <input  size="60"  type="text" id="description" name="description" value="${descriptionValue}" />

    </p>
    <p></p>
    <#--Need to draw edit elements for dates here-->
     <#if htmlForElements?keys?seq_contains("startField")>
			<label class="dateTime" for="startField">${i18n().start_capitalized}</label>
			${htmlForElements["startField"]} ${yearHint}
     </#if>
     <p></p>
     <#if htmlForElements?keys?seq_contains("endField")>
			<label class="dateTime" for="endField">${i18n().end_capitalized}</label>
		 	${htmlForElements["endField"]} ${yearHint}
     </#if>

  	<#--End draw elements-->
    <input type="hidden" id="editKey" name="editKey" value="${editKey}"/>
    <p class="submit">
         <input type="submit" id="submit" value="${submitButtonText}"/><span class="or"> ${i18n().or} </span>
         <a class="cancel" href="${cancelUrl}" title="${i18n().cancel_title}">${i18n().cancel_link}</a>
     </p>

    <p id="requiredLegend" class="requiredHint">* ${i18n().required_fields}</p>

</form>


<script type="text/javascript">
var customFormData  = {
    acUrl: '${urls.base}/autocomplete?tokenize=true&stem=true',
    editMode: '${editMode}',
    acTypes: {agent: 'http://xmlns.com/foaf/0.1/Agent' , place: 'http://vivoweb.org/ontology/core#GeographicLocation'},
	multipleTypeNames: { agent: 'participant' , place: 'place' },
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

<#--$(document).ready(function() {
    objectHasProductionUtils.onLoad();
});
-->

</script>

</section>

${stylesheets.add('<link rel="stylesheet" href="${urls.base}/js/jquery-ui/css/smoothness/jquery-ui-1.12.1.css" />')}
${stylesheets.add('<link rel="stylesheet" href="${urls.base}/templates/freemarker/edit/forms/css/customForm.css" />')}
${stylesheets.add('<link rel="stylesheet" href="${urls.base}/templates/freemarker/edit/forms/css/customFormWithAutocomplete.css" />')}


${scripts.add('<script type="text/javascript" src="${urls.base}/js/jquery-ui/js/jquery-ui-1.12.1.min.js"></script>',
             '<script type="text/javascript" src="${urls.base}/js/customFormUtils.js"></script>',
             '<script type="text/javascript" src="${urls.base}/js/extensions/String.js"></script>',
             '<script type="text/javascript" src="${urls.base}/js/browserUtils.js"></script>',
             '<script type="text/javascript" src="${urls.base}/js/jquery_plugins/jquery.bgiframe.pack.js"></script>',
             '<script type="text/javascript" src="${urls.base}/templates/freemarker/edit/forms/js/customFormWithAutocomplete.js"></script>')}


