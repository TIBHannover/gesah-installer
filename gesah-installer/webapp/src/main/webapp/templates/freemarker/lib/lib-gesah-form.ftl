<#-- $This file is distributed under the terms of the license in LICENSE$ -->

<#-- Macros and functions for form controls -->

<#-- After selecting an individual via autocomplete, display highlighted and with verify link -->
<#macro acSelection urlsBase inputName inputId acGroupName inputValue labelValue="">
<div class="acSelection" acGroupName="${acGroupName}">
    <p class="inline">
        <label>${labelValue}</label>
        <span class="acSelectionInfo"></span>
        <a href="${urlsBase}/individual?uri=" class="verifyMatch" title="${i18n().verify_this_match_title}">(${i18n().verify_match_capitalized}</a> ${i18n().or}
        <a href="#" class="changeSelection" id="changeSelection">${i18n().change_selection})</a>
        </p>
        <input class="acUriReceiver" type="hidden" id="${inputId}" name="${inputName}" value="${inputValue}" />
        <!-- Field value populated by JavaScript -->
</div>
</#macro>

<#macro print_technique>
	<#assign existingTechniqueValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "existingTechnique") />

	<div class="entry">
        <label for="techniqueUri">${i18n().technique} </label>

        <#assign existingTechniqueOpts = editConfiguration.pageData.existingTechnique />
        <select name="existingTechnique" id="techniqueUri" >
            <option value="" <#if existingTechniqueValue = "">selected</#if>>${i18n().select_one}</option>
            <#list existingTechniqueOpts?keys as key>
                <option value="${key}" <#if existingTechniqueValue = key>selected</#if>>${existingTechniqueOpts[key]}</option>
            </#list>
        </select>
        <input type="hidden" id="techniqueLabel" name="techniqueLabel" value=""/>
    </div>
</#macro>

<#macro print_material>
	<#assign existingMaterialValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "existingMaterial") />

    <div class="entry">
        <label for="material">${i18n().material} </label>
        <#assign existingMaterialOpts = editConfiguration.pageData.existingMaterial />
        <select name="existingMaterial" style="margin-top:-2px" >
            <option value="" <#if existingMaterialValue == "">selected</#if>>${i18n().select_one}</option>
            <#list existingMaterialOpts?keys as key>
                <option value="${key}"  <#if existingMaterialValue == key>selected</#if>><#if existingMaterialOpts[key] == "Other">${i18n().or_other}<#else>${existingMaterialOpts[key]}</#if></option>
            </#list>
        </select>
		<input type="hidden" id="materialLabel" name="materialLabel" value=""/>
	</div>
</#macro>	

<#macro print_attribution_type>
	<#assign existingAttrTypeValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "existingAttrType") />

    <div class="entry">
        <label for="attributionType">${i18n().attribution_type} </label>
        <#assign existingAttrTypeOpts = editConfiguration.pageData.existingAttrType />
        <select name="existingAttrType" style="margin-top:-2px" >
            <option value="" <#if existingAttrTypeValue == "">selected</#if>>${i18n().select_one}</option>
            <#list existingAttrTypeOpts?keys as key>
                <option value="${key}"  <#if existingAttrTypeValue == key>selected</#if>><#if existingAttrTypeOpts[key] == "Other">${i18n().or_other}<#else>${existingAttrTypeOpts[key]}</#if></option>
            </#list>
        </select>
		<input type="hidden" id="attributionTypeLabel" name="attributionTypeLabel" value=""/>
	</div>
</#macro>

<#macro print_role_type>
	<#assign existingRoleTypeValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "existingRoleType") />
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
</#macro>



<#macro print_publication num>
		<#assign existingPublication = lvf.getFormFieldValue(editSubmission, editConfiguration, "existingPublication" + num) />
        <p class="print_publication">
            <label for="publication${num}">${i18n().related_publication} ${num}</label>
            <textarea  size="60"  type="text" id="publication${num}" name="publication${num}" >${existingPublication}</textarea>
            <textarea  style="display:none;" size="60"  type="text" id="removePublication${num}" name="removePublication${num}">${existingPublication}</textarea>
        </p>
</#macro>

<#macro print_provenance_type>
	<#assign provenanceCurrentType = lvf.getFormFieldValue(editSubmission, editConfiguration, "provenanceCurrentType") />
    <#assign provenanceTypes = editConfiguration.pageData.provenanceType />

	<p class="inline">
        <label for="provenanceType">${i18n().provenance_type} ${requiredHint}</label>
        <select name="provenanceType">
            <option value="" selected="selected">${i18n().select_one}</option>
            <#list provenanceTypes?keys as key>
            	<#if provenanceCurrentType = key>
					<option value="${key}" selected>${provenanceTypes[key]}</option>
                <#else>
					<option value="${key}">${provenanceTypes[key]}</option>
                </#if>
            </#list>
        </select>
    </p>
</#macro>

<#macro print_treatment_specifics>
	<#assign treatmentSpecificsCurrent = lvf.getFormFieldValue(editSubmission, editConfiguration, "treatmentSpecifics") />
    <#assign treatmentSpecifics = editConfiguration.pageData.treatmentSpecifics />

	<p class="inline">
        <label for="treatmentSpecifics">${i18n().treatment_specifics} ${requiredHint}</label>
        <select name="treatmentSpecifics">
            <option value="" selected="selected">${i18n().select_one}</option>
            <#list treatmentSpecifics?keys as key>
            	<#if treatmentSpecificsCurrent = key>
					<option value="${key}" selected>${treatmentSpecifics[key]}</option>
                <#else>
					<option value="${key}">${treatmentSpecifics[key]}</option>
                </#if>
            </#list>
        </select>
    </p>
</#macro>

<#macro print_preservation_type>
	<#assign preservationCurrentType = lvf.getFormFieldValue(editSubmission, editConfiguration, "preservationCurrentType") />
    <#assign preservationTypes = editConfiguration.pageData.preservationType />

	<p class="inline">
        <label for="preservationCurrentType">${i18n().preservation_type} ${requiredHint}</label>
        <select name="preservationCurrentType">
            <option value="" selected="selected">${i18n().select_one}</option>
            <#list preservationTypes?keys as key>
            	<#if preservationCurrentType = key>
					<option value="${key}" selected>${preservationTypes[key]}</option>
                <#else>
					<option value="${key}">${preservationTypes[key]}</option>
                </#if>
            </#list>
        </select>
    </p>
</#macro>

<#macro print_actor>
		<#assign agentTypeValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "agentType")/>
		<#assign agentLabelValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "agentLabel") />
		<#assign agentLabelDisplayValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "agentLabelDisplay") />
		<#assign existingAgentValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "existingAgent") />

		<p class="inline">
            <label for="agentType">${i18n().agent_type_capitalized}</label>
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
            <label for="relatedIndLabel">${i18n().agent_capitalized} ${i18n().name_capitalized}</label>
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
</#macro>

<#macro print_place>
		<#assign placeLabelValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "placeLabel") />
		<#assign placeLabelDisplayValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "placeLabelDisplay") />
		<#assign existingPlaceValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "existingPlace") />
		<p>
            <label for="placeLabel">${i18n().place_name_capitalized} </label>
            <input type="text" id="place" name="placeLabel" acGroupName="place" size="50" class="acSelector" value="${placeLabelValue}"  />
            <input class="display" type="hidden" id="placeLabel" acGroupName="place" name="placeLabelDisplay" value="${placeLabelDisplayValue}">
        </p>

        <div class="acSelection" id="typeSelector" acGroupName="place">
            <p class="inline">
                <label>${i18n().selected_place}:</label>
                <span class="acSelectionInfo"></span>
                <a href="" class="verifyMatch"  title="${i18n().verify_match_capitalized}">(${i18n().verify_match_capitalized}</a> ${i18n().or}
                <a href="#" class="changeSelection" id="changeSelection">${i18n().change_selection})</a>
            </p>
            <input class="acUriReceiver" type="hidden" id="placeUri" name="existingPlace" value="${existingPlaceValue}" ${flagClearLabelForExisting}="true" />
        </div>
</#macro>

<#macro print_lit_date_appeal>
		<#assign litDateAppelValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "litDateAppel") />
 		<p>
	        <label for="litDateAppel">${i18n().literal_date_appelatlion}</label>
            <input type="text" id="litDateAppel" name="litDateAppel" size="30" value="${litDateAppelValue}"/>
	    </p>

</#macro>

<#macro print_comment>
		<#assign commentValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "existingCommentValue") />
        <p>
            <label for="comment">${i18n().comment}
                <span class="hint">&nbsp;${i18n().supplemental_information_hint_origin}</span>
            </label>
            <textarea  size="60"  type="text" id="comment" name="comment" >${commentValue}</textarea>
        </p>
</#macro>

<#macro print_dates>
        <#if htmlForElements?keys?seq_contains("startField")>
               <label class="dateTime" for="startField">${i18n().start_capitalized}</label>
               ${htmlForElements["startField"]} ${yearHint}
        </#if>
        <p></p>
        <#if htmlForElements?keys?seq_contains("endField")>
               <label class="dateTime" for="endField">${i18n().end_capitalized}</label>
               ${htmlForElements["endField"]} ${yearHint}
        </#if>
</#macro>