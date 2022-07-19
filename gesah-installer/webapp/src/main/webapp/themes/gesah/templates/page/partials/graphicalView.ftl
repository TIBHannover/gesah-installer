<#assign iiifUrl="https://osl.tib.eu/gesah-iiif" />
<#assign iiifSlash="^" /> 
<#assign height = "230" />

<#if categories?has_content>
<div class="col-md-12">
	<div class="container">
      <div id="exploreTheCollection">${i18n().explore_collection_text}</div>
	   <div id="searchFacets">
		 <#list categories as category>
			<@printCategory category />
		 </#list>
	   </div>
	</div>
</div>
</#if>

<#macro printCategory category>
	<#if category?has_content>
	  <div class="category_facet" style="height:${height}px;">
		<a href="${urls.base}/extendedsearch?querytext=ALLTEXT:*&filters=${category.fieldName}:${category.id}">
		  <img alt="${category.label}" src="${iiifImage(category.barcode category.fileName)}" />
		  <div class="category_facet_label">${category.label}</div>
		</a>
      </div>
	</#if>
</#macro>

<#function iiifImage barcode fileName>
	<#return iiifUrl + "/iiif/2/" + barcode + iiifSlash + "content" + iiifSlash + "streams" + iiifSlash + fileName + "/pct:15,15,70,70/," + height + "/0/default.jpg" />
</#function>
