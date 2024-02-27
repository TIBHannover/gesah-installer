<#-- $This file is distributed under the terms of the license in LICENSE$ -->

<#-- Custom object property statement view for faux property "education and training". See the PropertyConfig.n3 file for details.

     This template must be self-contained and not rely on other variables set for the individual page, because it
     is also used to generate the property statement during a deletion.
 -->

<#-- import "lib-sequence.ftl" as s -->
<#import "lib-datetime.ftl" as dt>
<#import "lib-meta-tags.ftl" as lmt>

<@showInscription statement />
<#-- Use a macro to keep variable assignments local; otherwise the values carry over to the next statement -->

<#macro showInscription statement>
	<div class="listViewCard">
		<#if statement.inscriptionLabel??>
			<b>${statement.inscriptionLabel}</b>
			<br />
		</#if>
		<#if statement.transcriptions??>
			<b>${i18n().transcription_capitalized}:</b> ${statement.transcriptions}
			<br />
		</#if>
		<#if statement.inscriptionTypes??>
			(${statement.inscriptionTypes})
			<br />
		</#if>
		<#if statement.comments??>
			${i18n().comment_capitalized}: ${statement.comments}
			<br />
		</#if>
		<#assign isEdit = individual?has_content && individual.showAdminPanel />
    	<@lgv.activityRoles statement.objInscriptionActivity isEdit />
		
		<#-- If user can edit individual, show a link to the context object -->
		<#if individual?has_content && individual.showAdminPanel>
			<div class="contextLink"><a href="${profileUrl(statement.uri("objInscriptionActivity"))}">${statement.objInscriptionActivity?keep_after_last("/")}</a></div>
		</#if>
	</div>
</#macro>
