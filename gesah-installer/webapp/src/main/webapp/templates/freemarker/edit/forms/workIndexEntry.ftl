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

<#assign workIndexEntryLabel = lvf.getFormFieldValue(editSubmission, editConfiguration, "workIndexEntryLabel") />
<#assign indexNumber = lvf.getFormFieldValue(editSubmission, editConfiguration, "indexNumber") />
<#assign indexNumberUrl = lvf.getFormFieldValue(editSubmission, editConfiguration, "indexNumberUrl") />

<#assign oldWorkIndex = lvf.getFormFieldValue(editSubmission, editConfiguration, "workIndex") />
<#assign oldWorkIndexLabel = lvf.getFormFieldValue(editSubmission, editConfiguration, "workIndexLabel") />

<#assign workIndexTitle = lvf.getFormFieldValue(editSubmission, editConfiguration, "workIndexTitle") />
<#assign workIndexUrl = lvf.getFormFieldValue(editSubmission, editConfiguration, "workIndexUrl") />



<h2>${titleVerb}&nbsp;mark designation for ${editConfiguration.subjectName}</h2>

<section id="markDesignation" role="region">
	<form id="markDesignation" class="customForm noIE67" action="${submitUrl}"  role="add/edit markDesignation" >
		<#-- 
		<label for="workIndexEntryLabel">${i18n().work_index_entry_label}</label>
		<input type="text" id="workIndexEntryLabel" name="workIndexEntryLabel" autocomplete="off" value="${workIndexEntryLabel}" />
		 -->
	
		<label for="indexNumber">${i18n().work_index_entry_index_number} ${requiredHint}</label>
		<input type="text" id="indexNumber" name="indexNumber" autocomplete="off" value="${indexNumber}" />
		
		<label for="indexNumberUrl">${i18n().work_index_entry_index_number_url}</label>
		<input type="text" id="indexNumberUrl" name="indexNumberUrl" autocomplete="off" value="${indexNumberUrl}" />
		
		<p>
            <label for="workIndexLabel">${i18n().work_index_label} ${requiredHint}</label>
            <input class="acSelector" size="50" type="text" id="workIndexLabel" name="workIndexLabel" acGroupName="work_index" value="${oldWorkIndexLabel}"  />
            <input class="display" type="hidden" id="work_index" acGroupName="work_index" value="${oldWorkIndex}">
        
        	<label for="workIndexTitle">${i18n().work_index_title}</label>
			<textarea type="text" id="workIndexTitle" name="workIndexTitle" autocomplete="off" class="useTinyMce">${workIndexTitle}</textarea>
			
			<label for="workIndexUrl">${i18n().work_index_url}</label>
			<input type="text" id="workIndexUrl" name="workIndexUrl" autocomplete="off" value="${workIndexUrl}" />
        </p>
        <div class="acSelection" id="typeSelector" acGroupName="work_index">
            <p class="inline">
                <label>${i18n().work_index_entry_select_work_index}</label>
                <span class="acSelectionInfo"></span>
                <a href="" class="verifyMatch" title="${i18n().verify_match_capitalized}">(${i18n().verify_match_capitalized}</a> ${i18n().or}
                <a href="#" class="changeSelection" id="changeSelection">${i18n().change_selection})</a>
            </p>
            <input class="acUriReceiver" type="hidden" id="workIndexUri" name="workIndex" value="${oldWorkIndex}" ${flagClearLabelForExisting}="true" />
        </div>
	<@formControls />
    </form>
</section>

<script type="text/javascript">
	var customFormData  = {
	    acUrl: '${urls.base}/autocomplete?tokenize=true&stem=true',
	    editMode: '${editMode}',
	    acTypes: {work_index: 'http://ontology.tib.eu/gesah/Work_Index'},
	    defaultTypeName: 'work index',
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

