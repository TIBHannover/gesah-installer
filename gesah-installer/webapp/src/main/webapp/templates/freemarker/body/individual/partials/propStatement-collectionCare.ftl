<#-- $This file is distributed under the terms of the license in LICENSE$ -->

<#-- Custom object property statement view for http://vivoweb.org/ontology/core#dateTimeValue.

     This template must be self-contained and not rely on other variables set for the individual page, because it
     is also used to generate the property statement during a deletion.
 -->

<#import "lib-meta-tags.ftl" as lmt>
<#import "lib-gesah-view.ftl" as lgv>

<@showCollectionCare statement />
<#-- Use a macro to keep variable assignments local; otherwise the values carry over to the next statement -->

<#macro showCollectionCare statement>
    <#if statement.preservationLabel??>
        <b>${statement.preservationLabel}</b><br />
    </#if>
   	<#assign isEdit = individual?has_content && individual.showAdminPanel />
    <@lgv.activityRoles statement.preservationObj isEdit />
    <#if statement.treatmentLabels??>
        <b>${i18n().treatment_capitalized}:</b> ${statement.treatmentLabels}<br />
    </#if>
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
    <#if individual?has_content && individual.showAdminPanel>
        <div class="contextLink"><a href="${profileUrl(statement.uri("preservationObj"))}">${statement.preservationObj?keep_after_last("/")}</a></div>
    </#if>
	<#if isEdit>
	  <div class="partObjectCreation" style="display:none;" >
	    <input form="part-creation" type="checkbox" autocomplete="off" name="activity-${statement.uri("preservationObj")}" value="${statement.uri("preservationObj")}">
	    <label style="display:inline;" for="activity-${statement.uri("preservationObj")}">Copy collection care</label>
	  </div>
	</#if>
</#macro>
