<#-- $This file is distributed under the terms of the license in LICENSE$ -->

<#if individual?has_content>
	<@dataGetter uri = "http://gesah-short-view#mostSpecificType" parameters = {"object": individual.uri} />
	<#if mostSpecificTypes?has_content>
		<#assign typeLabel = individual.mostSpecificTypes?first!'cultural object' />
    	<title>${i18n().meta_co_title(typeLabel, (title?html)!siteName!)}</title>
	<#else>
		<title>${(title?html)!siteName!}</title>		
	</#if>
<#else>
	<title>${(title?html)!siteName!}</title>
</#if>