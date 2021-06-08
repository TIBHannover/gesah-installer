<#-- $This file is distributed under the terms of the license in LICENSE$ -->

<#-- Custom object property statement view for faux property "education and training". See the PropertyConfig.n3 file for details.

     This template must be self-contained and not rely on other variables set for the individual page, because it
     is also used to generate the property statement during a deletion.
 -->

<#-- import "lib-sequence.ftl" as s -->
<#import "lib-datetime.ftl" as dt>
<#import "lib-meta-tags.ftl" as lmt>

<@showObjProduction statement />
<#-- Use a macro to keep variable assignments local; otherwise the values carry over to the next statement -->

<#macro showObjProduction statement>
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
		<#if statement.place??>
			<b>${i18n().place_capitalized}:</b> <a href="${profileUrl(statement.uri("place"))}">${statement.placeLabel}</a>
			<br />
		</#if>
		<#if statement.litteralDtAppel??>
			<b>${i18n().date_capitalized}:</b> ${statement.litteralDtAppel}<br />
		<#elseif statement.dateTimeStart??>
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
		<#if statement.techniqueLabels??>
			<b>${i18n().technique_capitalized}:</b> ${statement.techniqueLabels}
			<br />
		</#if>
		<#if statement.materialLabels??>
			<b>${i18n().material_capitalized}:</b> ${statement.materialLabels}
			<br />
		</#if>
		<#if statement.comments??>
			<b>${i18n().comment_capitalized}:</b> ${statement.comments}
			<br />
		</#if>
		<#-- If user can edit individual, show a link to the context object -->
		<#if individual.showAdminPanel>
			<div class="contextLink"><a href="${profileUrl(statement.uri("objProduction"))}">${statement.objProduction?keep_after_last("/")}</a></div>
		</#if>
	</div>
</#macro>
