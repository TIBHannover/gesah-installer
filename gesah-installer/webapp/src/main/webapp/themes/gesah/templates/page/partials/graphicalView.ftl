<#if categories?has_content>
<div class="col-md-12">
	<div class="container">
	   <div id="searchFacets">
		 <#list categories as category>
			<@printCategory category />
		 </#list>
	   </div>
	</div>
</div>
<#else>
	error: categories not found!
</#if>

<#macro printCategory category>
	<#if category?has_content>
		<a href="${category.uri}">
			<div class="searchFacet">
			  <img alt="${category.label}">
			</div>
		</a>
	</#if>
</#macro>