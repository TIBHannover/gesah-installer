<#-- $This file is distributed under the terms of the license in LICENSE$ -->

<#-- Template for displaying paged search results -->
<#-- <@dump var ="filters" />  

-->

<@printFilters filters />

<h2 class="searchResultsHeader">
<#escape x as x?html>
    ${hitCount} ${i18n().results_found} 
    <#if classGroupName?has_content>${i18n().limited_to_type} '${classGroupName}'</#if>
    <#if typeName?has_content>${i18n().limited_to_type} '${typeName}'</#if>
</#escape>
<script type="text/javascript">
	var url = window.location.toString();
	if (url.indexOf("?") == -1){
		var queryText = 'querytext=${querytext}';
	} else {
		var urlArray = url.split("?");
		var queryText = urlArray[1];
	}

	var urlsBase = '${urls.base}';
	
	$("input:radio").on("click",function (e) {
	    var input=$(this);
	    if (input.is(".selected-input")) { 
	        input.prop("checked",false);
	        input.removeClass("selected-input");
	        return;
	    }
	    $("input:radio[name='"+input.prop("name")+"'].selected-input").removeClass("selected-input");
	    input.addClass("selected-input");
	});
</script>

	<img id="downloadIcon" src="images/download-icon.png" alt="${i18n().download_results}" title="${i18n().download_results}" />
<#-- <span id="downloadResults" style="float:left"></span>  -->
</h2>

<span id="searchHelp"><a href="${urls.base}/searchHelp" title="${i18n().search_help}">${i18n().not_expected_results}</a></span>
<div class="contentsBrowseGroup">

    <#-- Search results -->
    <ul class="searchhits">
        <#list individuals as individual>
            <li>
            	<@shortView uri=individual.uri viewContext="search" />
            </li>
        </#list>
    </ul>


    <#-- Paging controls -->
    <#if (pagingLinks?size > 0)>
        <div class="searchpages">
            ${i18n().pages}:
            <#if prevPage??><a class="prev" href="${prevPage}" title="${i18n().previous}">${i18n().previous}</a></#if>
            <#list pagingLinks as link>
                <#if link.url??>
                    <a href="${link.url}" title="${i18n().page_link}">${link.text}</a>
                <#else>
                    <span>${link.text}</span> <#-- no link if current page -->
                </#if>
            </#list>
            <#if nextPage??><a class="next" href="${nextPage}" title="${i18n().next_capitalized}">${i18n().next_capitalized}</a></#if>
        </div>
    </#if>
    <br />

<#macro printFilters filters>
	<#assign selectedFilter = "">

	<form autocomplete="off" method="get" action="${urls.base}/extendedsearch">
		<div id="search-filter-container">
			<ul class="nav nav-tabs">
				<#list filters?values as f>
					<@printFilterMenu f />  
				</#list>
			</ul>
		</div>
		<div class="tab-content">
			<#list filters?values as f>
				<@printFilterValues f />  
			</#list>
		</div>
	<div id="selected-filters">
		<input type="submit" class="Submit" value="Search" />
		<@printSelectedFilterValueLabels filters />
	</div>  
	</form>
</#macro>

<#macro printSelectedFilterValueLabels filters>
	<#list filters?values as filter>
		<#assign valueNumber = 1>
		<#list filter.values?values as v>
			<#if v.selected>
				${getInput(filter, v, getValueID(filter.id, valueNumber), valueNumber)}
				${getLabel(valueNumber, v, filter)}
			</#if>
			<#assign valueNumber = valueNumber + 1>
		</#list>
	</#list>
</#macro>


<#macro printFilterMenu filter>
	<li>
		<a data-toggle="tab" href="#${filter.id}">${filter.name}</a>
	</li>
</#macro>

<#macro printFilterValues filter>
	<div id="${filter.id}" class="tab-pane fade">
		<#if filter.input>
			<div class="user-filter-search-input">
				<@createUserInput filter />
			</div>
		</#if>
		<#assign valueNumber = 1>
		<#list filter.values?values as v>
			<#if !v.selected>
				${getInput(filter, v, getValueID(filter.id, valueNumber), valueNumber)}
				${getLabel(valueNumber, v, filter)}
			</#if>
			<#assign valueNumber = valueNumber + 1>
		</#list>
	</div>
</#macro>

<#function getLabel valueID value filter >
	<#assign label = value.name >
	<#if !filter.localizationRequired>
		<#assign label = value.id >
	</#if>
	<#return "<label for=\"" + getValueID(filter.id, valueNumber) + "\">" + getValueLabel(label, value.count) + "</label>" />
</#function>

<#macro createUserInput filter>
	<input class="search-vivo" type="text" name="filter_input_${filter.id}" value="${filter.inputText}" autocapitalize="none" />
</#macro>

<#function getInput filter filterValue valueID valueNumber>
	<#assign checked = "" >
	<#assign class = "" >
	<#if filterValue.selected>
		<#assign checked = " checked=\"checked\" " >
		<#assign class = "selected-input" >
	</#if>
	<#assign type = "checkbox" >
	<#if !filter.multivalued>
		<#assign type = "radio" >
	</#if>
	<#assign filterName = filter.id >
	<#if filter.multivalued>
		<#assign filterName = filterName + "_" + valueNumber >
	</#if>

	<#return "<input type=\"" + type + "\" id=\"" + valueID + "\"  value=\"" + filter.id + ":" + filterValue.id 
		+ "\" name=\"filters_" + filterName + "\" style=\"display:none;\" " + checked + "\" class=\"" + class + "\" >" />
</#function>

<#function getValueID id number>
	<#return id + "__" + number /> 
</#function>



<#function getValueLabel label count >
	<#assign result = label >
	${label}
	<#if count!=0>
		<#assign result = result + " (" + count + ")" >
	</#if>
	<#return result />
</#function>

</div> <!-- end contentsBrowseGroup -->

${stylesheets.add('<link rel="stylesheet" href="//code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />',
  				  '<link rel="stylesheet" href="${urls.base}/css/search.css" />',
                  '<link rel="stylesheet" type="text/css" href="${urls.base}/css/jquery_plugins/qtip/jquery.qtip.min.css" />')}

${headScripts.add('<script src="//code.jquery.com/ui/1.10.3/jquery-ui.js"></script>',
				  '<script type="text/javascript" src="${urls.base}/js/jquery_plugins/qtip/jquery.qtip.min.js"></script>',
                  '<script type="text/javascript" src="${urls.base}/js/tiny_mce/tiny_mce.js"></script>'
                  )}

${scripts.add('<script type="text/javascript" src="${urls.base}/js/searchDownload.js"></script>')}
