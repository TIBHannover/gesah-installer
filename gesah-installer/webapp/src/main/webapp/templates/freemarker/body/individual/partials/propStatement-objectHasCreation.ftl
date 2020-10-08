<#-- $This file is distributed under the terms of the license in LICENSE$ -->

<#-- Custom object property statement view for faux property "education and training". See the PropertyConfig.n3 file for details.

     This template must be self-contained and not rely on other variables set for the individual page, because it
     is also used to generate the property statement during a deletion.
 -->

<#-- import "lib-sequence.ftl" as s -->
<#-- import "lib-datetime.ftl" as dt -->
<#import "lib-meta-tags.ftl" as lmt>
<@showObCreation statement />
<#-- Use a macro to keep variable assignments local; otherwise the values carry over to the
     next statement -->
	 

    
<#macro showobCreation statement>	
		
		<#if statement.technique??>
			<a href="${profileUrl(statement.uri("technique"))}" title="${i18n().creation_technique}">${statement.techniqueLabel}</a>
        <@lmt.addCitationMetaTag uri="http://ontology.tib.eu/gesah/Creation" content=statement.techniqueLabel />
		<#else>
			<#-- This shouldn't happen, but we must provide for it -->
			<a href="${profileUrl(statement.uri("obCreation"))}" title="${i18n().unknown_creation_technique}">${i18n().unknown_creation_technique}</a>
		</#if>	
		
</#macro>		
		

    

	    


