<#-- $This file is distributed under the terms of the license in LICENSE$ -->

<#-- Custom object property statement view for http://vivoweb.org/ontology/core#dateTimeValue.

     This template must be self-contained and not rely on other variables set for the individual page, because it
     is also used to generate the property statement during a deletion.
 -->

<#import "lib-meta-tags.ftl" as lmt>

<@showProvenance statement />
<#-- Use a macro to keep variable assignments local; otherwise the values carry over to the next statement -->

<#macro showProvenance statement>
    <#if statement.provenanceLabel??>
        <b>${statement.provenanceLabel}</b><br />
    </#if>
    <#if statement.personName??>
        <a href="${profileUrl(statement.uri("person"))}">${statement.personName}</a>
        <#if statement.roleTypeLabels??>
            (${statement.roleTypeLabels})
        </#if>
        <br />
    </#if>
    <#if statement.placeObj??>
        <b>${i18n().place_capitalized}:</b> <a href="${profileUrl(statement.uri("placeObj"))}">${statement.placeLabel}</a><br />
    </#if>
    <#if statement.litteralDtAppel??>
        <b>${i18n().date_capitalized}:</b> ${statement.litteralDtAppel}<br />
    <#elseif statement.dateTimeStart??>
        <b>${i18n().date_capitalized}:</b>
        <#if statement.dateTimeEnd??>
            <@dt.yearIntervalSpan "${statement.dateTimeStart!}" "${statement.dateTimeEnd!}" />
        <#else>
            <@dt.yearSpan "${statement.dateTimeStart!}" />
        </#if>
        <br />
    <#elseif statement.dateTimeEnd??>
        <b>${i18n().date_capitalized}:</b>
        <@dt.yearSpan "${statement.dateTimeEnd!}" />
        <br />
    </#if>
    <#if statement.comments??>
        <b>${i18n().comment_capitalized}:</b> ${statement.comments}
        <br />
    </#if>
    <#-- If user can edit individual, show a link to the context object -->
    <#if individual?has_content &&  individual.showAdminPanel>
        <div class="contextLink"><a href="${profileUrl(statement.uri("provenanceObj"))}">${statement.provenanceObj?keep_after_last("/")}</a></div>
    </#if>
</#macro>
