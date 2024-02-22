<#-- $This file is distributed under the terms of the license in LICENSE$ -->

<#-- Custom object property statement view for http://vivoweb.org/ontology/core#dateTimeValue.

     This template must be self-contained and not rely on other variables set for the individual page, because it
     is also used to generate the property statement during a deletion.
 -->

<#import "lib-meta-tags.ftl" as lmt>
<#import "lib-gesah-view.ftl" as lgv>

<@showProvenance statement />
<#-- Use a macro to keep variable assignments local; otherwise the values carry over to the next statement -->

<#macro showProvenance statement>
    <#if statement.provenanceLabel??>
        <b>${statement.provenanceLabel}</b><br />
    </#if>
   	<#assign isEdit = individual?has_content && individual.showAdminPanel />
    <@lgv.activityRoles statement.provenanceObj profileUrl(individual.getUri()) isEdit />
    
    <#assign place_printed = false />
	<#if statement.placeObj??>
		<a href="${profileUrl(statement.uri("placeObj"))}">${statement.placeLabel}</a><#rt>
		<#assign place_printed = true />
	</#if>
	<#if statement.litteralDtAppel??>
		<#if place_printed>
			<@lgv.addCommaSeparator />
		</#if>
		${statement.litteralDtAppel}<br />
	<#elseif statement.dateTimeStart??>
		<#if place_printed>
			<@lgv.addCommaSeparator />
		</#if>
		<#if statement.dateTimeEnd??>
			<@dt.yearIntervalSpan "${statement.dateTimeStart!}" "${statement.dateTimeEnd!}" />
		<#else>
			<@dt.yearSpan "${statement.dateTimeStart!}" />
		</#if>
		<br />
	<#elseif statement.dateTimeEnd??>
		<#if place_printed>
			<@lgv.addCommaSeparator />
		</#if>
		<@dt.yearSpan "${statement.dateTimeEnd!}" />
		<br />
	<#elseif place_printed>
		<br />
	</#if>
        
    <#if statement.comments??>
        ${i18n().comment_capitalized}: ${statement.comments}
        <br />
    </#if>
    <#-- If user can edit individual, show a link to the context object -->
    <#if individual?has_content &&  individual.showAdminPanel>
        <div class="contextLink"><a href="${profileUrl(statement.uri("provenanceObj"))}">${statement.provenanceObj?keep_after_last("/")}</a></div>
    </#if>
</#macro>
