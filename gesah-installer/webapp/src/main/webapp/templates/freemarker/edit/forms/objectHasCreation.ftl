<#-- $This file is distributed under the terms of the license in LICENSE$ -->

<#-- this is in request.subject.name -->

<#-- leaving this edit/add mode code in for reference in case we decide we need it -->

<#import "lib-gesah-form.ftl" as lgf>

<#assign subjectName=""/>
<#assign roleActivityUri="mysteryRoleActivityURI"/>
<#assign personLabel="mysteryPersLabel"/>

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
<#assign newPersonValue = lgf.getFormFieldValue(editSubmission, editConfiguration, "newPerson")/>
<#assign persLabelValue = lgf.getFormFieldValue(editSubmission, editConfiguration, "persLabel") />
<#assign persLabelDisplayValue = lgf.getFormFieldValue(editSubmission, editConfiguration, "persLabelDisplay") />
<#assign litDateAppelValue = lgf.getFormFieldValue(editSubmission, editConfiguration, "litDateAppel") />
<#assign descriptionValue = lgf.getFormFieldValue(editSubmission, editConfiguration, "description") />
<#assign newRoleValue = lgf.getFormFieldValue(editSubmission, editConfiguration, "newRole") />
<#assign newRoleTypeValue = lgf.getFormFieldValue(editSubmission, editConfiguration, "newRoleType") />
<#assign newRoleTypeLabelValue = lgf.getFormFieldValue(editSubmission, editConfiguration, "newRoleTypeLabel") />
<#assign newAttrTypeValue = lgf.getFormFieldValue(editSubmission, editConfiguration, "newAttrType") />
<#assign newAttrTypeLabelValue = lgf.getFormFieldValue(editSubmission, editConfiguration, "newAttrTypeLabel") />
<#assign newTechniqueValue = lgf.getFormFieldValue(editSubmission, editConfiguration, "newTechnique") />
<#assign newTechniqueLabelValue = lgf.getFormFieldValue(editSubmission, editConfiguration, "newTechniqueLabel") />
<#assign newMaterialValue = lgf.getFormFieldValue(editSubmission, editConfiguration, "newMaterial") />
<#assign newMaterialLabelValue = lgf.getFormFieldValue(editSubmission, editConfiguration, "newMaterialLabel") />
<#assign newPlaceValue = lgf.getFormFieldValue(editSubmission, editConfiguration, "newPlace") />
<#assign newPlaceLabelValue = lgf.getFormFieldValue(editSubmission, editConfiguration, "newPlaceLabel") />
<#assign existingPersValue = lgf.getFormFieldValue(editSubmission, editConfiguration, "existingPers") />
<#assign existingMaterialValue = lgf.getFormFieldValue(editSubmission, editConfiguration, "existingMaterial") />
<#assign existingMaterialLabelValue = lgf.getFormFieldValue(editSubmission, editConfiguration, "existingMaterialLabel") />
<#assign existingTechniqueValue = lgf.getFormFieldValue(editSubmission, editConfiguration, "existingTechnique") />
<#assign existingTechniqueLabelValue = lgf.getFormFieldValue(editSubmission, editConfiguration, "existingTechniqueLabel") />
<#assign existingAttrTypeValue = lgf.getFormFieldValue(editSubmission, editConfiguration, "existingAttrType") />
<#assign existingAttrTypeLabelValue = lgf.getFormFieldValue(editSubmission, editConfiguration, "existingAttrTypeLabel") />
<#assign existingRoleTypeValue = lgf.getFormFieldValue(editSubmission, editConfiguration, "existingRoleType") />
<#assign existingRoleTypeLabelValue = lgf.getFormFieldValue(editSubmission, editConfiguration, "existingRoleTypeLabel") />
<#assign existingPlaceValue = lgf.getFormFieldValue(editSubmission, editConfiguration, "existingPlace") />
<#assign existingPlaceLabelValue = lgf.getFormFieldValue(editSubmission, editConfiguration, "existingPlaceLabel") />
<#--
    <#assign existingTechniqueLabelValue = lgf.getFormFieldValue(editSubmission, editConfiguration, "techniqueLabel") />
-->
<#--
    <#assign existingMaterialLabelValue = lgf.getFormFieldValue(editSubmission, editConfiguration, "materialLabel") />
-->

<#--

<#assign existingAttrTypeLabelValue = lgf.getFormFieldValue(editSubmission, editConfiguration, "existingAttrTypeLabel") />

-->

<#--
    <#assign existingRoleTypeLabelValue = lgf.getFormFieldValue(editSubmission, editConfiguration, "roleTypeLabel") />
-->
<#--
    <#assign existingPlaceLabelValue = lgf.getFormFieldValue(editSubmission, editConfiguration, "placeLabel") />
-->
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


<h2>${titleVerb}&nbsp;${i18n().creation_for} ${editConfiguration.subjectName}</h2>

<#--Display error messages if any-->
<#if submissionErrors?has_content>
    <#if persLabelDisplayValue?has_content >
        <#assign persLabelValue = persLabelDisplayValue />
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
        <#--Checking if Person field is empty-->
         <#if lgf.submissionErrorExists(editSubmission, "newPerson")>
 	        ${i18n().select_a_person}
        </#if>
        <#--Checking if Person Name field is empty-->
         <#if lgf.submissionErrorExists(editSubmission, "persLabel")>
 	        ${i18n().select_a_person}
        </#if>
        <#--Checking if Attribution Type field is empty-->
         <#if lgf.submissionErrorExists(editSubmission, "newAttrType")>
 	        ${i18n().select_attribution_type_value}<br />
        </#if>
		<#--Checking if Technique field is empty-->
         <#if lgf.submissionErrorExists(editSubmission, "newTechnique")>
 	        ${i18n().select_technique_value}<br />
        </#if>
		<#--Checking if Material field is empty-->
         <#if lgf.submissionErrorExists(editSubmission, "newMaterial")>
 	        ${i18n().select_material_value}<br />
        </#if>
		<#--Checking if Role Type field is empty-->
         <#if lgf.submissionErrorExists(editSubmission, "newRoleType")>
 	        ${i18n().select_role_type_value}<br />
        </#if>

        </p>
    </section>
</#if>

<@lgf.unsupportedBrowser urls.base />

<section id="objectHasCreation" role="region">

    <form id="objectHasCreation" class="customForm noIE67" action="${submitUrl}"  role="add/edit creation">

    <p>
        <label for="relatedIndLabel">${i18n().person_capitalized} ${i18n().name_capitalized} ${requiredHint}</label>
        <input class="acSelector" size="50"  type="text" id="relatedIndLabel" name="persLabel" acGroupName="person" value="${persLabelValue}"  />
        <input class="display" type="hidden" id="persDisplay" acGroupName="person" name="persLabelDisplay" value="${persLabelDisplayValue}">
    </p>

    <div class="acSelection" acGroupName="person">
        <p class="inline">
            <label>${i18n().selected_person}:</label>
            <span class="acSelectionInfo"></span>
            <a href="" class="verifyMatch"  title="${i18n().verify_match_capitalized}">(${i18n().verify_match_capitalized}</a> ${i18n().or}
            <a href="#" class="changeSelection" id="changeSelection">${i18n().change_selection})</a>
        </p>
        <input class="acUriReceiver" type="hidden" id="persUri" name="existingPers" value="${existingPersValue}" ${flagClearLabelForExisting}="true" />
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
		<label for="attributionType">${i18n().attribution_type} ${requiredHint}</label>
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
	
	

    <div class="entry">
      <label for="placeUri">${i18n().place}</label>

      <#assign existingPlaceOpts = editConfiguration.pageData.existingPlace />
      <select name="existingPlace" id="placeUri" >
        <option value="" <#if existingPlaceValue = "">selected</#if>>${i18n().select_one}</option>
               <#list existingPlaceOpts?keys as key>
        <option value="${key}" <#if existingPlaceValue = key>selected</#if>>${existingPlaceOpts[key]}</option>
        </#list>
      </select>
<#--
      <#if editMode == "edit" || editMode == "repair">
            <input type="hidden" id="newPlaceLabel" name="placeLabel" value=""/>
            <input type="hidden" id="placeLabel" name="existingPlaceLabel" value="${existingPlaceLabelValue!}"/>
      <#else>
            <input type="hidden" id="placeLabel" name="placeLabel" value=""/>
      </#if>
-->
            <input type="hidden" id="placeLabel" name="placeLabel" value=""/>
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
    acTypes: {person: 'http://xmlns.com/foaf/0.1/Person'},
    defaultTypeName: 'person',
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
    objectHasCreationUtils.onLoad();
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


