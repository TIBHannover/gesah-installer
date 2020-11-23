<#-- $This file is distributed under the terms of the license in LICENSE$ -->

<#-- Custom object property statement view for http://vivoweb.org/ontology/core#dateTimeValue.

     This template must be self-contained and not rely on other variables set for the individual page, because it
     is also used to generate the property statement during a deletion.
 -->

<#import "lib-meta-tags.ftl" as lmt>

<#if ! statement.title??>
    <a href="${profileUrl(statement.uri("titleObj"))}">missing label</a>
<#else>
    <a href="${profileUrl(statement.uri("titleObj"))}">${statement.title}</a>
    <#if statement.titleType??>
        <span class="titleTypeListItem">${statement.titleType}</span>
    </#if>
</#if>
