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
            <#--Checking if Person field is empty-->
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
            <#--Checking if Role Type field is empty-->
            <#if lvf.submissionErrorExists(editSubmission, "existingRoleType")>
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
