<#-- $This file is distributed under the terms of the license in LICENSE$ -->

<#-- Custom object property statement view for http://vivoweb.org/ontology/core#dateTimeValue.

     This template must be self-contained and not rely on other variables set for the individual page, because it
     is also used to generate the property statement during a deletion.
 -->

<#import "lib-meta-tags.ftl" as lmt>

<@showMarkDesignation statement />
<#-- Use a macro to keep variable assignments local; otherwise the values carry over to the next statement -->

<#macro showMarkDesignation statement>
    <#if statement.markLabel??>
        <#if statement.markObj??>
            <a href="${profileUrl(statement.uri("markObj"))}">${statement.markLabel}</a>
        <#else>
            ${statement.markLabel}
        </#if>
        <br />
    </#if>
    <#if statement.markLocation??>
        (${statement.markLocation})<br />
    </#if>
    <#if statement.markID??>
        <#if statement.markObj?? && statement.markURL?has_content && statement.markID?has_content>
            <a href="${statement.markURL}">${statement.markID}</a>
        <#else>
            ${statement.markID}
        </#if>
        <br />
    </#if>
    <#if statement.markComment??>
        ${statement.markComment}<br />
    </#if>
    <#-- If user can edit individual, show a link to the context object -->
    <#if individual?has_content && individual.showAdminPanel>
        <div class="contextLink"><a href="${profileUrl(statement.uri("markDesignationObj"))}">${statement.markDesignationObj?keep_after_last("/")}</a></div>
    </#if>
</#macro>
