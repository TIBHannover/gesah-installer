<#-- $This file is distributed under the terms of the license in LICENSE$ -->

<#-- Custom object property statement view for faux property "education and training". See the PropertyConfig.n3 file for details.

     This template must be self-contained and not rely on other variables set for the individual page, because it
     is also used to generate the property statement during a deletion.
 -->

<#-- import "lib-sequence.ftl" as s -->
<#import "lib-datetime.ftl" as dt>
<#import "lib-meta-tags.ftl" as lmt>
<#import "lib-gesah-view.ftl" as lgv>

<@showObCreation statement />
<#-- Use a macro to keep variable assignments local; otherwise the values carry over to the next statement -->

<#macro showObCreation statement>
	<div class="listViewCard">
		<#if statement.person??>
			<a href="${profileUrl(statement.uri("person"))}">${statement.personName}</a>
			<#if statement.roleTypeLabels??>
				(${statement.roleTypeLabels})
			</#if>
			<br />
		</#if>
		<#if statement.attrTypeLabels??>
			${statement.attrTypeLabels}<br />
		</#if>
		<#assign place_printed = false />
		<#if statement.place??>
			<a href="${profileUrl(statement.uri("place"))}">${statement.placeLabel}</a>
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
		<#if statement.techniqueLabels??>
			${statement.techniqueLabels}
			${i18n().gesah_made_on}
			<#if statement.materialLabels??>
				${statement.materialLabels}
			</#if>
			<br />
		</#if>
		<#if statement.comments??>
			${i18n().comment_capitalized}: ${statement.comments}
			<br />
		</#if>
		<#-- If user can edit individual, show a link to the context object -->
		<#if individual?has_content && individual.showAdminPanel>
			<div class="contextLink"><a href="${profileUrl(statement.uri("obCreation"))}">${statement.obCreation?keep_after_last("/")}</a></div>
		</#if>
	</div>
</#macro>
