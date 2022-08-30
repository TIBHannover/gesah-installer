<#-- $This file is distributed under the terms of the license in LICENSE$ -->

<#-- Template for displaying paged search results -->
<#-- <@dump var ="filters" />  

-->
<@searchForm filters emptySearch />

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
	
	function clearInput(elementId) {
  		let inputEl = document.getElementById(elementId);
  		inputEl.value = "";
  		let srcButton = document.getElementById("button_" + elementId);
  		srcButton.classList.add("unchecked-selected-search-input-label");
	}
	
	function createSliders(){
		sliders = document.getElementsByClassName('range-slider-container');
		for (let sliderElement of sliders) {
			createSlider(sliderElement);
		}
	};
		
	function createSlider(sliderContainer){
		rangeSlider = sliderContainer.querySelector('.range-slider');
		
		noUiSlider.create(rangeSlider, {
		    range: {
		        min: Number(sliderContainer.getAttribute('min')),
		        max: Number(sliderContainer.getAttribute('max'))
		    },
		
		    step: 1,
		    start: [Number(sliderContainer.querySelector('.range-slider-start').textContent), 
		  			Number(sliderContainer.querySelector('.range-slider-end').textContent)],
		
		    format: wNumb({
		        decimals: 0
		    })
		});
		
		var dateValues = [
		     sliderContainer.querySelector('.range-slider-start'),
		     sliderContainer.querySelector('.range-slider-end')
		];
		
		var input = sliderContainer.querySelector('.range-slider-input');
		var first = true;
		
		rangeSlider.noUiSlider.on('update', function (values, handle) {
		    dateValues[handle].innerHTML = values[handle];
		    var active = input.getAttribute('active');
		    if (active === null){
		    	input.setAttribute('active', "false");
		    } else if (active !== "true"){
		        input.setAttribute('active', "true");
		    } else {
		    	var startDate = new Date(+values[0],1,1);
		    	var endDate = new Date(+values[1],1,1);
		    	input.value = startDate.toISOString() + " " + endDate.toISOString();
		    }
		});
	}
	
	window.onload = (event) => {
  		createSliders();
	};
	
	$('#extended-search-form').submit(function () {
    $(this)
        .find('input')
        .filter(function () {
            return !this.value;
        })
        .prop('name', '');
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

<#macro searchForm filters isEmptySearch>

	<form id="extended-search-form" autocomplete="off" method="get" action="${urls.base}/extendedsearch">
		<div id="search-filter-container">
			<ul class="nav nav-tabs">
				<#assign assignedActive = false>
				<#list filters?values as f>
					<@searchFormTab f assignedActive isEmptySearch/>  
					<#if !assignedActive && (f.selected || isEmptySearch )>
						<#assign assignedActive = true>
					</#if>
				</#list>
			</ul>
		</div>
		<div class="tab-content">
			<#assign assignedActive = false>
			<#list filters?values as f>
				<@printFilterValues f assignedActive isEmptySearch/>  
				<#if !assignedActive && ( f.selected || isEmptySearch )>
					<#assign assignedActive = true>
				</#if>
			</#list>
		</div>
	<div id="selected-filters">
		<input type="submit" class="Submit" value="${i18n().search_button}" />
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
		<@userSelectedInput filter />
	</#list>
</#macro>


<#macro searchFormTab filter assignedActive isEmptySearch>
	<#if !assignedActive && ( filter.selected || isEmptySearch )>
	 	<li class="active">
	<#else>
		<li>
	</#if>
			<a data-toggle="tab" href="#${filter.id}">${filter.name}</a>
		</li>
</#macro>

<#macro printFilterValues filter assignedActive isEmptySearch>
	<#if !assignedActive && ( filter.selected || isEmptySearch )>
		<div id="${filter.id}" class="tab-pane fade in active filter-area">
	<#else>
		<div id="${filter.id}" class="tab-pane fade filter-area">
	</#if>
			<#if filter.type == "RangeFilter">
				<@rangeFilter filter/>
			<#else>
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
			</#if>
		</div>
</#macro>

<#macro rangeFilter filter>
	<#assign min = filter.min >
	<#assign max = filter.max >
	<#assign from = filter.fromYear >
	<#assign to = filter.toYear >
	<#if from?has_content && to?has_content >
		<#assign range = from + " " + to >
	</#if>

	<div class="range-filter" id="${filter.id}" class="tab-pane fade filter-area">
		<div class="range-slider-container" min="${filter.min}" max="${filter.max}">
			<div class="range-slider"></div>
			<#if from?has_content>
				<div class="range-slider-start">${from}</div>
			<#else>
				<div class="range-slider-start">${min}</div>
			</#if>
			<#if to?has_content>
				<div class="range-slider-end">${to}</div>
			<#else>
				<div class="range-slider-end">${max}</div>
			</#if>
			<input id="filter_range_${filter.id}" style="display:none;" class="range-slider-input" name="filter_range_${filter.id}" value="${range!""}"/>
		</div>
	</div>
</#macro>

<#function getLabel valueID value filter >
	<#assign label = value.name >
	<#if !filter.localizationRequired>
		<#assign label = value.id >
	</#if>
	<#return "<label for=\"" + getValueID(filter.id, valueNumber) + "\">" + getValueLabel(label, value.count) + "</label>" />
</#function>


<#macro userSelectedInput filter>
	<#if filter.inputText?has_content>
		<button type="button" id="button_filter_input_${filter.id}" onclick="clearInput('filter_input_${filter.id}')" class="checked-search-input-label">${filter.name} : ${filter.inputText}</button>
	</#if>
	<#assign from = filter.fromYear >
	<#assign to = filter.toYear >
	<#if from?has_content && to?has_content >
		<#assign range = i18n().from + " " + from + " " + i18n().to + " " + to >
		<button type="button" id="button_filter_range_${filter.id}" onclick="clearInput('filter_range_${filter.id}')" class="checked-search-input-label">${filter.name} : ${range}</button>
	</#if>
</#macro>

<#macro createUserInput filter>
	<input id="filter_input_${filter.id}" class="search-vivo" type="text" name="filter_input_${filter.id}" value="${filter.inputText}" autocapitalize="none" />
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
