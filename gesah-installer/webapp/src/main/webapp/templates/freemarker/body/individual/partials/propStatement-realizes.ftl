<#-- $This file is distributed under the terms of the license in LICENSE$ -->

<#-- Custom object property statement view for http://vivoweb.org/ontology/core#dateTimeValue.

     This template must be self-contained and not rely on other variables set for the individual page, because it
     is also used to generate the property statement during a deletion.
 -->

<@showRole statement />

<#macro showRole statement>
    <#if statement.agent_label?has_content>
    	<p><a href="${profileUrl(statement.agent)}">${statement.agent_label}</a></p>
    </#if>
    <#if statement.role_type_label?has_content>
    	(${statement.role_type_label})
    </#if>
    <#if statement.attribution_label?has_content>
    	<span class="titleTypeListItem">${statement.attribution_label}</span>
    </#if>
    <#if individual?has_content && individual.showAdminPanel>
		<div class="contextLink"><a href="${profileUrl(statement.uri("object"))}">${statement.object?keep_after_last("/")}</a></div>
	</#if>
</#macro>
