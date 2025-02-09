<#assign iiifSlash="%5E" /> 
<#assign height = "230" />



<#if categories?has_content>
<div class="col-md-12">
	<div class="container">
      <div class="home-page-heading2">${i18n().explore_collection_text}</div>
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
	  <#assign typeFilter = "&filters_limit-to=limit-to%3Acultural-object">
	  <div class="category_facet">
		<a class="facet_link" href="${urls.base}/search?filters=${category.filter_id}:${category.id}${typeFilter}">
		  <img alt="${category.label}" src="${iiifImage(category.barcode category.fileName category.crop_bottom category.crop_top category.crop_left category.crop_right)}" />
		  <div class="category_facet_label">${category.label}</div>
		  <div class="category_facet_description">${category.label}</div>
		</a>
      </div>
	</#if>
</#macro>

<#function iiifImage barcode fileName crop_bottom="15" crop_top="15" crop_left="15" crop_right="15" >
	<#assign crop_width_n = 100 - crop_left?number - crop_right?number />
	<#assign crop_height_n = 100 - crop_top?number - crop_bottom?number />
	<#assign crop_left_n = crop_left?number />
	<#assign crop_top_n = crop_top?number />
	
	<#if ( crop_left_n >= 0 ) && ( crop_left_n <= 99 ) >
		<#assign crop_left_n = crop_left?number />
	<#else>
		<#assign crop_left_n = 15 />
	</#if>
	
	<#if ( crop_top_n >= 0 ) && ( crop_top_n <= 99 ) >
		<#assign crop_top_n = crop_top?number />
	<#else>
		<#assign crop_top_n = 15 />
	</#if>
	
	<#if ( crop_width_n <= 0 ) || ( crop_width_n > 100 ) >
		<#assign crop_width_n = 70 />
	</#if>
	
	<#if ( crop_height_n <= 0 ) || ( crop_height_n > 100 ) >
		<#assign crop_height_n = 70 />
	</#if>
	
				
	
	<#return urls.iiif + "/iiif/2/" + barcode + iiifSlash + "content" + iiifSlash + "streams" + iiifSlash + fileName + 
	"/pct:" + crop_left_n?c + "," + crop_top_n?c + "," + crop_width_n?c + "," + crop_height_n?c + "/," + height + "/0/default.jpg" />
</#function>
