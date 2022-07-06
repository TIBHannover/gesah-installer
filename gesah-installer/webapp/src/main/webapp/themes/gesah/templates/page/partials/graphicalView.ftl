<#if categories?has_content>
<div class="col-md-12">
	<div>
		<#list categories as category>
			<@printCategory category />
		</#list>
	</div>
</div>
<#else>
	error: categories not found!
</#if>

<#macro printCategory category>
	<#if category?has_content>
		<a href="${category.uri}">
			<div>
			${category.label} ${category.count}
			</div>
		</a>
	</#if>
</#macro>