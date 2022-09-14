<#-- $This file is distributed under the terms of the license in LICENSE$ -->

<#-- Custom object property statement view for http://vivoweb.org/ontology/core#dateTimeValue.

     This template must be self-contained and not rely on other variables set for the individual page, because it
     is also used to generate the property statement during a deletion.
 -->

<#import "lib-meta-tags.ftl" as lmt>

<@showWorkIndexEntry statement />
<#-- Use a macro to keep variable assignments local; otherwise the values carry over to the next statement -->

<#macro showWorkIndexEntry statement>
    <#if statement.indexLabel?has_content && statement.index?has_content>
      <a href="${profileUrl(statement.uri("index"))}">${statement.indexLabel}</a>&nbsp;
    </#if>
    <#if statement.indexNo?has_content>
        <#if statement.indexNoUrl?has_content>
            <a href="${statement.indexNoUrl}" target="_blank">${statement.indexNo}</a>
        </#if>
        <br />
    <#else>
    	<a href="${profileUrl(statement.uri("object"))}">${statement.object?keep_after_last("/")}</a>
    </#if>
</#macro>
