<#-- $This file is distributed under the terms of the license in LICENSE$ -->

<#-- Custom object property statement view for faux property "education and training". See the PropertyConfig.n3 file for details.

     This template must be self-contained and not rely on other variables set for the individual page, because it
     is also used to generate the property statement during a deletion.
 -->

<#-- import "lib-sequence.ftl" as s -->
<#import "lib-datetime.ftl" as dt>
<#import "lib-meta-tags.ftl" as lmt>

<@showEvent statement />
<#-- Use a macro to keep variable assignments local; otherwise the values carry over to the next statement -->

<#macro showEvent statement>
	<div class="listViewCard">
		<#if statement.typeLabel?has_content>
			<b>${statement.typeLabel}</b>
			<br />
		</#if>
		<#if statement.label?has_content>
			<a href="${profileUrl(statement.uri("object"))}">${statement.label}</a>
			<br />
		</#if>
		<#if statement.place?has_content>
			<b>${i18n().place_capitalized}:</b> <a href="${profileUrl(statement.uri("place"))}">${statement.placeLabel}</a>
			<br />
		</#if>
		<#-- <#if statement.publication?has_content>
			<p>${statement.publication}</p>
		</#if>
		
		<#if statement.organizer?has_content>
			<a href="${profileUrl(statement.uri("organizerUri"))}">${statement.organizer}</a>
			<br />
		</#if> -->
		<#if statement.dateTimeStart??>
			<b>${i18n().date_capitalized}:</b>
			<#if statement.dateTimeEnd??>
				<@dt.yearIntervalSpan "${statement.dateTimeStart!}" "${statement.dateTimeEnd!}" />
			<#else>
				<@dt.yearSpan "${statement.dateTimeStart!}" />
			</#if>
			<br />
		<#elseif statement.dateTimeEnd??>
			<b>${i18n().date_capitalized}:</b>
			<@dt.yearSpan "${statement.dateTimeEnd!}" />
			<br />
		</#if>
		<#-- If user can edit individual, show a link to the context object -->
		<#if individual?has_content && individual.showAdminPanel>
			<div class="contextLink"><a href="${profileUrl(statement.uri("object"))}">${statement.object?keep_after_last("/")}</a></div>
		</#if>
	</div>
</#macro>
