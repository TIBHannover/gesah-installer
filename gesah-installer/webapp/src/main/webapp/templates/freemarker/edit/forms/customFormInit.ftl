<#-- $This file is distributed under the terms of the license in LICENSE$ -->

<#import "lib-vitro-form.ftl" as lvf>

<#--Retrieve certain edit configuration information-->
<#-- assign htmlForElements = editConfiguration.pageData.htmlForElements / -->
<#assign editMode = editConfiguration.pageData.editMode />

<#assign blankSentinel = "" />
<#if editConfigurationConstants?has_content && editConfigurationConstants?keys?seq_contains("BLANK_SENTINEL")>
    <#assign blankSentinel = editConfigurationConstants["BLANK_SENTINEL"] />
</#if>

<#--This flag is for clearing the label field on submission for an existing object being selected from autocomplete.
Set this flag on the input acUriReceiver where you would like this behavior to occur. -->
<#assign flagClearLabelForExisting = "flagClearLabelForExisting" />

<#-- Endure the browser is supported -->
<@lvf.unsupportedBrowser urls.base />

<#-- Determine if in create or edit mode -->
<#if editMode == "edit">
    <#assign formAction="${i18n().edit_capitalized}">
    <#assign submitButtonText="${i18n().save_changes}">
    <#assign disabledVal="disabled">
<#else>
    <#assign formAction="${i18n().create_capitalized}">
    <#assign submitButtonText="${i18n().create_entry}">
    <#assign disabledVal="">
</#if>

<#-- Common HTML for form hints -->
<#assign requiredHint="<span class='requiredHint'> *</span>"/>
<#assign yearHint     = "<span class='hint'>(${i18n().year_hint_format})</span>" />

${stylesheets.add('<link rel="stylesheet" href="${urls.base}/js/jquery-ui/css/smoothness/jquery-ui-1.12.1.css" />')}
${stylesheets.add('<link rel="stylesheet" href="${urls.base}/templates/freemarker/edit/forms/css/customForm.css" />')}
${stylesheets.add('<link rel="stylesheet" href="${urls.base}/templates/freemarker/edit/forms/css/customFormWithAutocomplete.css" />')}

${scripts.add('<script type="text/javascript" src="${urls.base}/js/jquery-ui/js/jquery-ui-1.12.1.min.js"></script>',
'<script type="text/javascript" src="${urls.base}/js/customFormUtils.js"></script>',
'<script type="text/javascript" src="${urls.base}/js/extensions/String.js"></script>',
'<script type="text/javascript" src="${urls.base}/js/browserUtils.js"></script>',
'<script type="text/javascript" src="${urls.base}/js/jquery_plugins/jquery.bgiframe.pack.js"></script>',
'<script type="text/javascript" src="${urls.base}/templates/freemarker/edit/forms/js/customFormWithAutocomplete.js"></script>')}

<#macro formControls>
    <input type="hidden" name = "editKey" value="${editKey}" role="input"/>
    <p class="submit">
        <#if editMode == "edit">
            <input type="submit" id="submit" name="submit-${formAction}" value="${submitButtonText}" class="submit" />
        <#else>
            <input type="submit" id="submit" name="submit-${formAction}" value="${submitButtonText}" class="submit" />
        </#if>

        <span class="or"> ${i18n().or} </span><a class="cancel" href="${editConfiguration.cancelUrl}" title="${i18n().cancel_title}">${i18n().cancel_link}</a>
    </p>
    <p class="requiredHint"  id="requiredLegend" >* ${i18n().required_fields}</p>
</#macro>
