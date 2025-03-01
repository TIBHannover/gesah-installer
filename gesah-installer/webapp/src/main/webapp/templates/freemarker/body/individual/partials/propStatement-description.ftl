<#-- $This file is distributed under the terms of the license in LICENSE$ -->

<#-- VIVO-specific default data property statement template.

     This template must be self-contained and not rely on other variables set for the individual page, because it
     is also used to generate the property statement during a deletion.
 -->
<#import "lib-datetime.ftl" as dt>
<#import "lib-meta-tags.ftl" as lmt>
<#if property.rangeDatatypeURI?? && property.rangeDatatypeURI?contains("#")>
	<#assign datatype = property.rangeDatatypeURI?substring(property.rangeDatatypeURI?last_index_of("#")+1) />
<#else>
	<#assign datatype = "none" />
</#if>
<@showStatement statement property datatype />
<#macro showStatement statement property datatype>
    <#assign theValue = statement.value />

    <#if datatype == "anyURI" && theValue?starts_with("http")>
	<#assign theValue = "<a href=\"" + statement.value + "\" target=\"_blank\">" + statement.value + "</a>" />
    </#if>

    <#if theValue?contains("<ul>") >
        <#assign theValue = theValue?replace("<ul>","<ul class='tinyMCEDisc'>") />
    </#if>
    <#if theValue?contains("<ol>") >
        <#assign theValue = theValue?replace("<ol>","<ol class='tinyMCENumeric'>") />
    </#if>
    <#if theValue?contains("<p>") >
        <#assign theValue = theValue?replace("<p>","<p style='margin-bottom:.6em'>") />
    </#if>
	<#if theValue?matches("([0-9]{4})-([0-9]{1,2})-([0-9]{1,2})") >
		<#assign theValue = theValue + "T00:00:00" />
		${dt.formatXsdDateTimeLong(theValue, "yearMonthDayPrecision")}
	<#elseif theValue?matches("^([0-9]{4})-((0[1-9])|(1[0-2]))-((0[1-9])|([1-2][0-9])|(3[0-1]))(T|\\s)(([0-1][0-9])|(2[0-3])):([0-5][0-9]):([0-5][0-9])")>
		<#if theValue?contains("T00:00:00") >
			${dt.formatXsdDateTimeLong(theValue, "yearMonthDayPrecision")}
		<#else>
			${dt.formatXsdDateTimeLong(theValue, "yearMonthDayTimePrecision")}
		</#if>
	<#elseif theValue?matches("^([0-9]{4})-(0[1-9]|1[012])")>
		<#assign theValue = theValue + "-01T00:00:00" />
		${dt.formatXsdDateTimeLong(theValue, "yearMonthPrecision")}
	<#elseif theValue?matches("^--(0[1-9]|1[012])")>
		<#assign theValue = "2000" + theValue?substring(1) + "-01T00:00:00" />
		${dt.formatXsdDateTimeLong(theValue, "monthPrecision")}
	<#else>
    	${theValue} <#if !datatype?contains("none")> <@validateFormat theValue datatype/> </#if>
	</#if>
	<@lmt.addCitationMetaTag uri=(property.uri!) content=(theValue!) />
	<#assign isEdit = individual?has_content && individual.showAdminPanel />
	<#if isEdit>
	  <div class="partObjectCreation" style="display:none;" >
	    <#assign uuid = statics["java.util.UUID"].randomUUID() />
	    <input form="part-creation" type="checkbox" name="description-${uuid}" value="${theValue?html}">
	    <label style="display:inline;" for="description-${uuid}">Use description</label>
	  </div>
	</#if>
</#macro>
<#macro validateFormat value datatype >
	<#if datatype?? >
		<#switch datatype>
			<#case "date">
				<img class="invalidFormatImg" src="${urls.base}/images/iconAlert.png" width="18" alt="${i18n().invalid_format}" title=" ${i18n().invalid_format}"> <#-- validated above -->
		     	<span class="invalidFormatText">invalid format</span>
				<#break>
		    <#case "dateTime">
				<img class="invalidFormatImg" src="${urls.base}/images/iconAlert.png" width="18" alt=" ${i18n().invalid_format}" title=" ${i18n().invalid_format}"> <#-- validated above -->
		     	<span class="invalidFormatText">invalid format</span>
				<#break>
		  	<#case "time">
		     	<#if !value?matches("(([0-1][0-9])|(2[0-3])):([0-5][0-9]):([0-5][0-9])") >
					<img class="invalidFormatImg" src="${urls.base}/images/iconAlert.png" width="18" alt=" ${i18n().invalid_format}" title=" ${i18n().invalid_format}">
					<span class="invalidFormatText">invalid format</span>
			 	</#if>
		     	<#break>
		  	<#case "gYear">
		     	<#if !value?matches("^\\d{4}") >
					<img class="invalidFormatImg" src="${urls.base}/images/iconAlert.png" width="18" alt=" ${i18n().invalid_format}" title=" ${i18n().invalid_format}">
					<span class="invalidFormatText">invalid format</span>
			 	</#if>
		     	<#break>
		    <#case "gMonth">
		     	<img class="invalidFormatImg" src="${urls.base}/images/iconAlert.png" width="18" alt=" ${i18n().invalid_format}" title=" ${i18n().invalid_format}"> <#-- validated above -->
		     	<span class="invalidFormatText">invalid format</span>
				<#break>
		    <#case "gYearMonth">
		     	<img class="invalidFormatImg" src="${urls.base}/images/iconAlert.png" width="18" alt=" ${i18n().invalid_format}" title=" ${i18n().invalid_format}"> <#-- validated above -->
		     	<span class="invalidFormatText">invalid format</span>
				<#break>
		  	<#case "float">
				<#if !value?matches("^[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?") >
		     		<img class="invalidFormatImg" src="${urls.base}/images/iconAlert.png" width="18" alt=" ${i18n().invalid_format}" title=" ${i18n().invalid_format}">
			     	<span class="invalidFormatText">invalid format</span>
				</#if>
				<#break>
		  	<#case "integer">
				<#if !value?matches("^-?\\d+$") >
		     		<img class="invalidFormatImg" src="${urls.base}/images/iconAlert.png" width="18" alt=" ${i18n().invalid_format}" title=" ${i18n().invalid_format}">
			     	<span class="invalidFormatText">invalid format</span>
				</#if>
				<#break>
		  	<#case "int">
				<#if !value?matches("^-?\\d+$") >
	     			<img class="invalidFormatImg" src="${urls.base}/images/iconAlert.png" width="18" alt=" ${i18n().invalid_format}" title=" ${i18n().invalid_format}">
			     	<span class="invalidFormatText">invalid format</span>
				</#if>
				<#break>
		  	<#case "boolean">
				<#if !value?matches("false") && !value?matches("true") >
		     		<img class="invalidFormatImg" src="${urls.base}/images/iconAlert.png" width="18" alt=" ${i18n().invalid_format}" title=" ${i18n().invalid_format}">
			     	<span class="invalidFormatText">invalid format</span>
				</#if>
				<#break>
		  	<#default>
		</#switch>
	</#if>
</#macro>

