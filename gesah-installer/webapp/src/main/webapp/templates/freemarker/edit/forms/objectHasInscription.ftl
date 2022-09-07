<#include "customFormInit.ftl">

<#assign subjectName=""/>
<#assign roleActivityUri="mysteryRoleActivityURI"/>
<#assign agentLabel="mysteryAgentLabel"/>

<#--This flag is for clearing the label field on submission for an existing object being selected from autocomplete.
Set this flag on the input acUriReceiver where you would like this behavior to occur. -->
<#assign flagClearLabelForExisting = "flagClearLabelForExisting" />

<#--Retrieve variables needed-->
<#assign newAgentValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "newAgent")/>
<#assign agentLabelValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "agentLabel") />
<#assign agentLabelDisplayValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "agentLabelDisplay") />
<#assign agentTypeValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "agentType")/>
<#assign inscriptionOutputTypeValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "inscriptionOutputType")/>
<#assign commentValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "comment") />
<#assign transcriptionValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "transcription") />
<#assign newRoleTypeValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "newRoleType") />
<#assign newRoleTypeLabelValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "newRoleTypeLabel") />
<#assign newInscTypeValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "newAttrType") />
<#assign newInscTypeLabelValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "newAttrTypeLabel") />
<#assign existingAgentValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "existingAgent") />
<#assign existingInscType = lvf.getFormFieldValue(editSubmission, editConfiguration, "existingInscType") />
<#assign inscTypeLabel = lvf.getFormFieldValue(editSubmission, editConfiguration, "inscTypeLabel") />
<#assign existingRoleTypeValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "existingRoleType") />
<#assign existingRoleTypeLabelValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "existingRoleTypeLabel") />


<h2>${titleVerb}&nbsp;${i18n().inscription_for} ${editConfiguration.subjectName}</h2>

<#include "objectHasInscriptionErrors.ftl" />

<section id="objectHasInscription" role="region">
    <form id="objectHasInscription" class="customForm noIE67" action="${submitUrl}"  role="add/edit inscription">
       
	   <div class="entry">
            <label for="inscriptionOutputType">${i18n().inscription_output_type_capitalized}${requiredHint} </label>
            <#assign inscriptionOutputTypeOpts = editConfiguration.pageData.inscriptionOutputType />
            <select name="inscriptionOutputType" style="margin-top:-2px" >
                <option value="" <#if inscriptionOutputTypeValue == "">selected</#if>>${i18n().select_one}</option>
                <#list inscriptionOutputTypeOpts?keys as key>
                    <option value="${key}"  <#if inscriptionOutputTypeValue == key>selected</#if>><#if inscriptionOutputTypeOpts[key] == "Other">${i18n().or_other}<#else>${inscriptionOutputTypeOpts[key]}</#if></option>
                </#list>
            </select>
            <#-- input type="hidden" id="roleTypeLabel" name="roleTypeLabel" value=""/-->
        </div>

        <div class="entry">
            <label for="inscType">${i18n().inscription_type}${requiredHint} </label>
            <#assign existingInscTypeOpts = editConfiguration.pageData.existingInscType />
            <select name="existingInscType" style="margin-top:-2px" >
                <option value="" <#if existingInscType == "">selected</#if>>${i18n().select_one}</option>
                <#list existingInscTypeOpts?keys as key>
                    <option value="${key}"  <#if existingInscType == key>selected</#if>><#if existingInscTypeOpts[key] == "Other">${i18n().or_other}<#else>${existingInscTypeOpts[key]}</#if></option>
                </#list>
            </select>
			<input type="hidden" id="existingInscTypeLabel" name="existingInscTypeLabel" value=""/>
    	</div>
	
		<p>
            <label for="transcription">${i18n().transcription}</label>
            <textarea  size="60"  type="text" id="transcription" name="transcription">${transcriptionValue}</textarea>
        </p>
		
		<p class="inline">
            <label for="agentType">${i18n().agent_type_capitalized} </label>
            <#assign agentTypeOpts = editConfiguration.pageData.agentType />
            <select id="typeSelector" name="agentType" acGroupName="agent">
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
            <label for="relatedIndLabel">${i18n().agent_capitalized} ${i18n().name_capitalized} </label>
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
            <input type="hidden" id="roleTypeLabel" name="roleTypeLabel" value=""/>
        </div>
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
		        

        <p>
            <label for="comment">${i18n().comment}
			<span class="hint">&nbsp;${i18n().supplemental_information_hint_inscription}</span>
			</label>
            <input type="text" id="comment" name="comment" size="30" value="${commentValue}"/>
        </p>

        
        

        

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
    blankSentinel: '${blankSentinel}',
    flagClearLabelForExisting: '${flagClearLabelForExisting}',
    subjectName: '${editConfiguration.subjectName?js_string}'
};
var i18nStrings = {
    selectAnExisting: '${i18n().select_an_existing}',
    orCreateNewOne: '${i18n().or_create_new_one}',
    selectedString: '${i18n().selected}'
};
</script>
