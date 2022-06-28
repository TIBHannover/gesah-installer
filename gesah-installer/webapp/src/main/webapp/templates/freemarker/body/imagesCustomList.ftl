

<#import "lib-meta-tags.ftl" as lmt>
<#assign iiifUrl="https://osl.tib.eu/gesah-iiif" />
<#assign iiifSlash="^" /> 
<#assign height = "150" />

<#if images?has_content>
	<#list images as image>
 	 <#if image.digRep?has_content && image.label?has_content && image.barcode?has_content && image.fileName?has_content>
	  <div>
		<a href="${profileUrl(image.digRep)}" title="${image.label}">${image.label}</a>
		<br/>
		<@createImageThumbnail image.barcode image.fileName image.digRep />
	  </div>
	  </#if>
	</#list>
</#if>

<#-- Use a macro to keep variable assignments local; otherwise the values carry over to the next statement -->

<#macro createImageThumbnail barcode fileName uri>
  <div class="imageThumbnail">
     <a href="${profileUrl(uri)}">
       <img src="${iiifUrl}/iiif/2/${barcode}${iiifSlash}content${iiifSlash}streams${iiifSlash}${fileName}/full/,${height}/0/default.jpg" />
     </a>
  </div>
</#macro>