<#--Display error messages if any-->
<#if submissionErrors?has_content>
    <#if agentLabelDisplayValue?has_content >
        <#assign agentLabelValue = agentLabelDisplayValue />
    </#if>
    
    <section id="error-alert" role="alert">
        <img src="${urls.images}/iconAlert.png" width="24" height="24" alert="${i18n().error_alert_icon}" />
        <p>
            <#--below shows examples of both printing out all error messages and checking the error message for a specific field-->
            
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
			<#--Checking if Inscription Output Type field is empty-->
            <#if lvf.submissionErrorExists(editSubmission, "inscriptionOutputType")>
                ${i18n().select_inscription_output_type}
            </#if>
            <#--Checking if Inscription Type field is empty-->
            <#if lvf.submissionErrorExists(editSubmission, "newInscType")>
                ${i18n().select_inscription_type_value}<br />
            </#if>
            <#--Checking if Role Type field is empty-->
            <#if lvf.submissionErrorExists(editSubmission, "newRoleType")>
                ${i18n().select_role_type_value}<br />
            </#if>
            

        </p>
    </section>
</#if>
