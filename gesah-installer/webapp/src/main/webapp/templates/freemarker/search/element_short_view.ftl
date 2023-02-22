<#include "lib-gesah-view.ftl">

<#assign aLabel = labelWithLink(individual.profileUrl, individual.name, typeFromIndividual(individual)) />
		
<div class="listElementContainer">
  <#list imageInfo as image>
    <#if image.fileNum?has_content && image.barcode?has_content>
      <@createImageThumbnail image.barcode image.fileNum individual.profileUrl individual.name />
    </#if>
  </#list> 
  <div class="listElementInformation">
    ${aLabel}
    <#if elementInfo?has_content && elementInfo[0].curNumber?has_content>
		<@printCurInventoryNumber elementInfo[0].curNumber />
	<#elseif elementInfo?has_content && elementInfo[0].formNumber?has_content>
		<@printFormInventoryNumber elementInfo[0].formNumber />
	</#if>
	<@printParticipations />
  </div>

</div>

<#macro closeParticipationRoleList participation type name >
 <#if name?has_content && type?has_content && ( participation.name != name || participation.type != type )>
   <@roleCloseList />
 </#if>
</#macro>

<#macro printRole role>
  ${role}
</#macro>

<#macro openRoles>
  (
</#macro>

<#macro closeRoles>
  )
</#macro>

<#macro addRoleSeparator>
,
</#macro>

<#macro addCommaSeparator>
, <#lt>
</#macro>

<#macro openName name >
  <p>${name}
</#macro>

<#macro closeName >
  </p>
</#macro>

<#macro printDate yearInfo>
  <#if yearInfo?has_content && yearInfo[0].literalDate?has_content>
    ${yearInfo[0].literalDate}
  <#elseif yearInfo?has_content && yearInfo[0].year?has_content >
    <#if yearInfo[0].yearStart?has_content && ( yearInfo[0].yearStart != yearInfo[0].year ) >
      ${i18n().gesah_search_in_year} ${yearInfo[0].yearStart}-${yearInfo[0].year}
    <#else>
      ${i18n().gesah_search_in_year} ${yearInfo[0].year}
    </#if>
  </#if>
</#macro>

<#macro printPlaceAndDate placeAndDateInfo>
	<#if placeAndDateInfo?has_content>
		<#assign place_printed = false />
		<#if placeAndDateInfo[0].place?has_content>
			${placeAndDateInfo[0].place}<#rt>
			<#assign place_printed = true />
		</#if>
		<#if placeAndDateInfo[0].literalDate?has_content>
			<#if place_printed>
				<@addCommaSeparator/>
			</#if>
			${placeAndDateInfo[0].literalDate}
		<#elseif placeAndDateInfo[0].year?has_content >
			<#if placeAndDateInfo[0].yearStart?has_content && ( placeAndDateInfo[0].yearStart != placeAndDateInfo[0].year ) >
				<#if place_printed>
					<@addCommaSeparator/>
				</#if>
				${i18n().gesah_search_in_year} ${placeAndDateInfo[0].yearStart}-${placeAndDateInfo[0].year}
			<#else>
				<#if place_printed>
					<@addCommaSeparator/>
				</#if>
				${i18n().gesah_search_in_year} ${placeAndDateInfo[0].year}
			</#if>
		</#if>
	</#if>
</#macro>

<#macro byPeople>
    ${i18n().gesah_search_created_by} 
</#macro>

<#macro printParticipations>
	     <#if creators?has_content>
	       <p>
	 	   <@printTypeInfo creators />
           <@printPlaceAndDate creators />
	 	   </p>
	     </#if>
	     <#if producers?has_content>
	       <p>
	 	   <@printTypeInfo producers />
           <@printPlaceAndDate producers />
	 	   </p>
	     </#if>
	     <#if editors?has_content>
	       <p>
	 	   <@printTypeInfo editors />
           <@printPlaceAndDate editors />
	 	   </p>
	     </#if>
</#macro>

<#macro printTypeInfo people>
	    <#assign name = "" />
	    <#assign role = "" />
	    <#assign rolesIsStarted = false />
	    <#assign nameIsStarted = false />
	 	
	    <#list people as participation>
	    	        
	    	<#if name != participation.name >
	    	  <#if rolesIsStarted>
	    	    <@closeRoles />
	    	    <#assign rolesIsStarted = false />
	    	    <#assign role = "" />
	    	  </#if>
	    	  <#if nameIsStarted>
	    	    <@closeName />
	    	    <#assign nameIsStarted = false />
	    	  </#if>
	    	  <#assign name = participation.name />
	    	  <@openName name />
	    	  <#assign nameIsStarted = true />
	        </#if>
	        
	        <#if role != participation.role >
	    	  <#if rolesIsStarted>
	    	    <@addRoleSeparator />
	    	  <#else>
	    	    <@openRoles />
	    	  </#if>
	    	  <#assign rolesIsStarted = true />
	    	  <#assign role = participation.role />
			  <@printRole role/>
	        </#if>
	    </#list>
	    <#if rolesIsStarted>
	      <@closeRoles />
	    </#if>
	    <#if nameIsStarted>
	      <@closeName />
	    </#if>
</#macro>

