<#-- $This file is distributed under the terms of the license in LICENSE$ -->

<#-- Custom object property statement view for http://vivoweb.org/ontology/core#dateTimeValue.

     This template must be self-contained and not rely on other variables set for the individual page, because it
     is also used to generate the property statement during a deletion.
 -->

<#import "lib-meta-tags.ftl" as lmt>

<@showCurrentHoldingInstitution statement />
<#-- Use a macro to keep variable assignments local; otherwise the values carry over to the next statement -->

<#macro showCurrentHoldingInstitution statement>
    <#if statement.holdingObj??>
        <a href="${profileUrl(statement.uri("holdingObj"))}">${statement.holdingLabel}</a>
        <#if statement.placeObj??>
            , <a href="${profileUrl(statement.uri("placeObj"))}">${statement.placeLabel}</a>
        </#if>
        <br />
    </#if>
</#macro>