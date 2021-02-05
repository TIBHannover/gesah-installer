<#-- $This file is distributed under the terms of the license in LICENSE$ -->

<#-- Custom object property statement view for http://vivoweb.org/ontology/core#dateTimeValue.

     This template must be self-contained and not rely on other variables set for the individual page, because it
     is also used to generate the property statement during a deletion.
 -->

<#import "lib-meta-tags.ftl" as lmt>

<#if ! statement.measurementsSpecification??>
    <a href="${profileUrl(statement.uri("measurementsObj"))}">Missing Spec</a>;
<#else>
    <a href="${profileUrl(statement.uri("measurementsObj"))}">${statement.measurementsSpecification}</a>;
</#if>
<#if statement.height??>
    ${i18n().height}: ${statement.height}
</#if>
<#if statement.width??>
    , ${i18n().width}: ${statement.width}
</#if>
<#if statement.depth??>
    , ${i18n().depth}: ${statement.depth}
</#if>
<#if statement.diameter??>
    , ${i18n().diameter}: ${statement.diameter}
</#if>
