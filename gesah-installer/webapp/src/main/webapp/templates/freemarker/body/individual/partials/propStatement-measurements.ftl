<#-- $This file is distributed under the terms of the license in LICENSE$ -->

<#-- Custom object property statement view for http://vivoweb.org/ontology/core#dateTimeValue.

     This template must be self-contained and not rely on other variables set for the individual page, because it
     is also used to generate the property statement during a deletion.
 -->

<#import "lib-meta-tags.ftl" as lmt>

<@showMeasures statement />
<#-- Use a macro to keep variable assignments local; otherwise the values carry over to the next statement -->

<#macro showMeasures statement>
    <#local measurements=""/>
    <#local separator=""/>
    <#if statement.height??>
        <#local measurements>
            ${measurements}${separator}${statement.height}
        </#local>
        <#local separator=" x "/>
    </#if>
    <#if statement.width??>
        <#local measurements>
            ${measurements}${separator}${statement.width}
        </#local>
        <#local separator=" x "/>
    </#if>
    <#if statement.depth??>
        <#local measurements>
            ${measurements}${separator}${statement.depth}
        </#local>
        <#local separator=" x "/>
    </#if>
    <#if statement.diameter??>
        <#local measurements>
            ${measurements}${separator}${statement.diameter}
        </#local>
        <#local separator=" x "/>
    </#if>
    <#if measurements??>
        <#if statement.measurementsSpecification??>
            ${statement.measurementsSpecification}: ${measurements}
        <#else>
            ${measurements}
        </#if>
    </#if>
    <#-- If user can edit individual, show a link to the context object -->
    <#if individual.showAdminPanel>
        <div class="contextLink"><a href="${profileUrl(statement.uri("measurementsObj"))}">${statement.measurementsObj?keep_after_last("/")}</a></div>
    </#if>
</#macro>