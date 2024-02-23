<#--Display error messages if any-->
<#if submissionErrors?has_content>
    <section id="error-alert" role="alert">
        <img src="${urls.images}/iconAlert.png" width="24" height="24" alert="${i18n().error_alert_icon}" />
        <p>
            <#if lvf.submissionErrorExists(editSubmission, "depth")>
                ${i18n().enter_depth_value}<br>
            </#if>

            <#if lvf.submissionErrorExists(editSubmission, "height")>
                ${i18n().enter_height_value}<br>
            </#if>

            <#if lvf.submissionErrorExists(editSubmission, "width")>
                ${i18n().enter_width_value}<br>
            </#if>

            <#if lvf.submissionErrorExists(editSubmission, "measurementsSpecification")>
                ${i18n().enter_specification_value}<br>
            </#if>
        </p>
    </section>
</#if>