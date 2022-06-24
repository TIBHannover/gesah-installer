<#-- $This file is distributed under the terms of the license in LICENSE$ -->

<#-- Custom object property statement view for http://vivoweb.org/ontology/core#dateTimeValue.

     This template must be self-contained and not rely on other variables set for the individual page, because it
     is also used to generate the property statement during a deletion.
 -->

<#import "lib-meta-tags.ftl" as lmt>
<#assign iiifUrl="https://osl.tib.eu/gesah-iiif" />
<#assign iiifSlash="^" /> 
<#assign height = "150" />

<a href="${profileUrl(statement.uri("object"))}" title="${statement.cultObjectLabel}">${statement.cultObjectLabel} <@mostSpecificType statement /></a>
<@showDepictedIn statement />
<#-- Use a macro to keep variable assignments local; otherwise the values carry over to the next statement -->

<#macro mostSpecificType statement>
  <#if statement.typeLabel?has_content >
    (${statement.typeLabel})
  </#if>
</#macro>

<#macro printCurInventoryNumber curNumber>
    <#if curNumber?has_content>
      <p>${i18n().gesah_current_inventory_number} ${curNumber}</p>
    </#if>
</#macro>

<#macro showDepictedIn statement>
	<#if statement.curNumber?has_content>
		<@printCurInventoryNumber statement.curNumber />
	</#if>
    <div class="imageThumbnails">
	    <#if statement.fileNum?has_content && statement.barcode?has_content>
	      <@createImageThumbnail statement.barcode statement.fileNum profileUrl(statement.uri("object")) />
	    </#if>
	</div>
</#macro>

<#macro createImageThumbnail barcode fileName profileUrl>
  <div class="imageThumbnail">
     <a href="${profileUrl}" title="individual profile">
       <img src="${iiifUrl}/iiif/2/${barcode}${iiifSlash}content${iiifSlash}streams${iiifSlash}${fileName}/full/,${height}/0/default.jpg" />
     </a>
  </div>
</#macro>