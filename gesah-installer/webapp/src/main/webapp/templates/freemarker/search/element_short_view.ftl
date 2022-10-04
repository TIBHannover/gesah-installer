
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


<#macro openName name >
  <p>${name}
</#macro>

<#macro closeName >
  </p>
</#macro>

<#macro openCreators>
  <p>${i18n().gesah_search_element_created}
  <@printDate creators />
  <@byPeople />
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

<#macro byPeople>
    ${i18n().gesah_search_created_by} 
</#macro>

<#macro openEditors>
  <p>${i18n().gesah_search_element_edited}
  <@printDate editors />
  <@byPeople />
</#macro>

<#macro openProducers>
  <p>${i18n().gesah_search_element_produced}
  <@printDate producers />
  <@byPeople />
</#macro>

<#macro closeType>
  </p>
</#macro>

<#macro printParticipations>
	     <#if creators?has_content>
	       <@openCreators />
	 	   <@printTypeInfo creators />
	 	   <@closeType />
	     </#if>
	     <#if producers?has_content>
	       <@openProducers />
	 	   <@printTypeInfo producers />
	 	   <@closeType />
	     </#if>
	     <#if editors?has_content>
	       <@openEditors />
	 	   <@printTypeInfo editors />
	 	   <@closeType />
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

