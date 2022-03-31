<#include "customFormInit.ftl">


<script type="text/javascript" src="/gesah/js/tiny_mce/tiny_mce.js?version=f10c"></script>
<script type="text/javascript" src="/gesah/js/tiny_mce/jquery.tinymce.js?version=f10c"></script>
<script type="text/javascript" src="/gesah/js/edit/initTinyMce.js?version=f10c"></script>

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
<#assign newCollectorsMarkLabelValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "markTitle") />

<#assign markId = lvf.getFormFieldValue(editSubmission, editConfiguration, "markId") />
<#assign markUrl = lvf.getFormFieldValue(editSubmission, editConfiguration, "markUrl") />
<#assign commentValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "comment") />


<h2>${titleVerb}&nbsp;mark designation for ${editConfiguration.subjectName}</h2>

<section id="markDesignation" role="region">
	<form id="markDesignation" class="customForm noIE67" action="${submitUrl}"  role="add/edit markDesignation" >
		<label for="markDesignationLabel">${i18n().mark_designation_label}</label>
		<input type="text" id="markDesignationLabel" name="markDesignationLabel" autocomplete="off" value="${markDesignationLabel}" />
		<label for="markLocation">${i18n().mark_designation_location}</label>
		<textarea id="markLocation" name="markLocation" class="useTinyMce" role="textarea" autocomplete="off">${markLocation}</textarea>
		
		<p>
            <label for="markTitle">${i18n().mark_designation_collectors_mark} ${requiredHint}</label>
            <input class="acSelector" size="50" type="text" id="markTitle" name="markTitle" acGroupName="collectors_mark" value="${oldCollectorsMarkLabel}"  />
            <input class="display" type="hidden" id="collectors_mark" acGroupName="collectors_mark" name="newMarkLabelDisplay" value="${oldCollectorsMark}">
        
        	<label for="markID">${i18n().mark_id}</label>
			<textarea type="text" id="markID" name="markId" autocomplete="off" class="useTinyMce">${markId}</textarea>
			
			<label for="markUrl">${i18n().mark_url}</label>
			<input type="text" id="markUrl" name="markUrl" autocomplete="off" value="${markUrl}" />
			
			<label for="comment">${i18n().comment}
                <span class="hint">&nbsp;${i18n().supplemental_information_hint_origin}</span>
            </label>
            <textarea  size="60"  type="text" id="comment" name="comment" class="useTinyMce" >${commentValue}</textarea>
        </p>
        <div class="acSelection" id="typeSelector" acGroupName="collectors_mark">
            <p class="inline">
                <label>${i18n().mark_designation_select_collectors_mark}</label>
                <span class="acSelectionInfo"></span>
                <a href="" class="verifyMatch" title="${i18n().verify_match_capitalized}">(${i18n().verify_match_capitalized}</a> ${i18n().or}
                <a href="#" class="changeSelection" id="changeSelection">${i18n().change_selection})</a>
            </p>
            <input class="acUriReceiver" type="hidden" id="collectorsMarkUri" name="collectorsMark" value="${oldCollectorsMark}" ${flagClearLabelForExisting}="true" />
        </div>
	<@formControls />
    </form>
</section>

<script type="text/javascript">
	var customFormData  = {
	    acUrl: '${urls.base}/autocomplete?tokenize=true&stem=true',
	    editMode: '${editMode}',
	    acTypes: {collectors_mark: 'http://ontology.tib.eu/gesah/Mark'},
	    defaultTypeName: 'collector\'s mark',
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
<#--  <#include "customFormInitTinyMCE.ftl">  -->

