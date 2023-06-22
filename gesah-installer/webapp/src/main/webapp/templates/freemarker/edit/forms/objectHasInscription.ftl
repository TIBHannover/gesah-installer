<#include "customFormInit.ftl">
<#include "../../lib/lib-gesah-form.ftl">

<#assign subjectName=""/>
<#assign roleActivityUri="mysteryRoleActivityURI"/>
<#assign agentLabel="mysteryAgentLabel"/>
<#--This flag is for clearing the label field on submission for an existing object being selected from autocomplete.
Set this flag on the input acUriReceiver where you would like this behavior to occur. -->
<#assign flagClearLabelForExisting = "flagClearLabelForExisting" />

<#--Retrieve variables needed-->

<h2>${titleVerb}&nbsp;${i18n().inscription_for} ${editConfiguration.subjectName}</h2>

<#include "objectHasInscriptionErrors.ftl" />

<section id="objectHasInscription" role="region">
    <form id="objectHasInscription" class="customForm noIE67" action="${submitUrl}"  role="add/edit inscription">
	   
	   <#assign inscriptionOutputTypeValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "inscriptionOutputType")/>
	   <div class="entry">
            <label for="inscriptionOutputType">${i18n().inscription_output_type_capitalized}${requiredHint} </label>
            <#assign inscriptionOutputTypeOpts = editConfiguration.pageData.inscriptionOutputType />
            <select name="inscriptionOutputType" style="margin-top:-2px" >
                <option value="" <#if inscriptionOutputTypeValue == "">selected</#if>>${i18n().select_one}</option>
                <#list inscriptionOutputTypeOpts?keys as key>
                    <option value="${key}"  <#if inscriptionOutputTypeValue == key>selected</#if>>${inscriptionOutputTypeOpts[key]}</option>
                </#list>
            </select>
        </div>
        
        <#assign inscriptionSpecification = lvf.getFormFieldValue(editSubmission, editConfiguration, "inscriptionSpecification") />
        <div class="entry">
            <label for="inscType">${i18n().inscription_type}${requiredHint} </label>
            <#assign existingInscTypeOpts = editConfiguration.pageData.inscriptionSpecification />
            <select name="inscriptionSpecification" style="margin-top:-2px" >
                <option value="" <#if inscriptionSpecification == "">selected</#if>>${i18n().select_one}</option>
                <#list existingInscTypeOpts?keys as key>
                    <option value="${key}"  <#if inscriptionSpecification == key>selected</#if>><#if existingInscTypeOpts[key] == "Other">${i18n().or_other}<#else>${existingInscTypeOpts[key]}</#if></option>
                </#list>
            </select>
			<input type="hidden" id="existingInscTypeLabel" name="existingInscTypeLabel" value=""/>
    	</div>
    	
    	<#assign transcriptionValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "existingTranscription") />
		<p>
            <label for="transcription">${i18n().transcription}</label>
            <textarea  size="60"  type="text" id="transcription" name="transcription">${transcriptionValue}</textarea>
        </p>
		<@print_actor />
		<@print_role_type />
		<@print_comment />
	 <#--
		<p class="inline">
            <label for="inscriptionOutputType">${i18n().inscription_output_type_capitalized} ${requiredHint}</label>
            <#assign inscriptionOutputTypeOpts = editConfiguration.pageData.inscriptionOutputType />
            <select id="typeSelector" name="inscriptionOutputType">
                <option value="" selected="selected">${i18n().select_one}</option>
                <#list inscriptionOutputTypeOpts?keys as key>
                    <#if inscriptionOutputTypeValue = key>
                        <option value="${key}"  selected >${inscriptionOutputTypeOpts[key]}</option>
                    <#else>
                        <option value="${key}">${inscriptionOutputTypeOpts[key]}</option>
                    </#if>
                </#list>
            </select>
        </p>
     -->   
        <@formControls />
    </form>
</section>
<script type="text/javascript">
var customFormData  = {
    acUrl: '${urls.base}/autocomplete?tokenize=true&stem=true',
    editMode: '${editMode}',
    acTypes: {agent: 'http://xmlns.com/foaf/0.1/Agent' },
    multipleTypeNames: { agent: 'participant'  },
    baseHref: '${urls.base}/individual?uri=',
    blankSentinel: '${blankSentinel?js_string}',
    flagClearLabelForExisting: '${flagClearLabelForExisting?js_string}',
    subjectName: '${editConfiguration.subjectName?js_string}'
};
var i18nStrings = {
    selectAnExisting: '${i18n().select_an_existing?js_string}',
    selectAnExistingOrCreateNewOne: '${i18n().select_an_existing_or_create_a_new_one?js_string}',
    selectedString: '${i18n().selected?js_string}'
};
</script>
