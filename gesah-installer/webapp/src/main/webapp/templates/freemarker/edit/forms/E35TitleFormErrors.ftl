<#--Display error messages if any-->
<#if submissionErrors?has_content>
    <section id="error-alert" role="alert">
        <img src="${urls.images}/iconAlert.png" width="24" height="24" alert="${i18n().error_alert_icon}" />
        <p>
            <#--Checking if title field is empty-->
            <#if lvf.submissionErrorExists(editSubmission, "title")>
                ${i18n().enter_title_value}<br />
            </#if>
        </p>
    </section>
</#if>