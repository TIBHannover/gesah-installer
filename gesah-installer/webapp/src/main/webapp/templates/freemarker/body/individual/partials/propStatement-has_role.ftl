<#-- $This file is distributed under the terms of the license in LICENSE$ -->

<#-- Custom object property statement view for http://vivoweb.org/ontology/core#dateTimeValue.

     This template must be self-contained and not rely on other variables set for the individual page, because it
     is also used to generate the property statement during a deletion.
 -->

<#import "lib-meta-tags.ftl" as lmt>
<#assign iiifUrl="https://osl.tib.eu/gesah-iiif" />
<#assign iiifSlash="^" /> 
<#assign height = "150" />

<a href="${profileUrl(statement.uri("cultObject"))}" title="${statement.cultObjectLabel}">${statement.cultObjectLabel} <@mostSpecificType statement /></a>
<@showMarkDesignation statement/>
<@showDepictedIn statement />


<#macro showMarkDesignation statement>
	<#if individual?has_content && individual.editable?has_content && individual.editable>
		<#if statement.label?has_content>
			<#assign title = statement.label />
		<#else>
			<#assign title = statement.uri("object") />
		</#if>
		<br/><a href="${profileUrl(statement.uri("object"))}" title="${title}">Role</a>
	</#if>
</#macro>

<#-- Use a macro to keep variable assignments local; otherwise the values carry over to the next statement -->

<#macro mostSpecificType statement>
  <#if statement.typeLabel?has_content >
    (${statement.typeLabel})
  </#if>
</#macro>

<#macro showDepictedIn statement>
	<#if statement.roles?has_content>
		<p>Roles: ${statement.roles}</p>
	</#if>
	<#if statement.curNumber?has_content>
		<@printCurInventoryNumber statement.curNumber />
	</#if>
    <div class="imageThumbnails">
	    <#if statement.fileNum?has_content && statement.barcode?has_content>
	      <@createImageThumbnail statement.barcode statement.fileNum profileUrl(statement.uri("cultObject")) statement.cultObjectLabel />
	    </#if>
	</div>
</#macro>

<#macro printCurInventoryNumber curNumber>
    <#if curNumber?has_content>
      <p>${i18n().gesah_current_inventory_number} ${curNumber}</p>
    </#if>
</#macro>

<#macro createImageThumbnail barcode fileName profileUrl title>
  <div class="imageThumbnail">
     <a href="${profileUrl}" title="${title}">
       <img src="${iiifUrl}/iiif/2/${barcode}${iiifSlash}content${iiifSlash}streams${iiifSlash}${fileName}/full/,${height}/0/default.jpg" />
     </a>
  </div>
</#macro>
