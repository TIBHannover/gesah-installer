
<#assign iiifUrl="https://osl.tib.eu/gesah-iiif" />
<#assign iiifSlash="^" /> 

<a href="${individual.profileUrl}" title="individual profile">${individual.name} <@mostSpecificType individual/></a>

<@printCurInventoryNumber />
				
<div class="imageThumbnails">
  <#list imageInfo as image>
    <#if image.fileNum?has_content && image.barcode?has_content>
      <@createImageThumbnail image.barcode image.fileNum individual.profileUrl />
    </#if>
  </#list> 
  <@printParticipations />
</div>

<#macro closeParticipationRoleList participation type name >
 <#if name?has_content && type?has_content && ( participation.name != name || participation.type != type )>
   <@roleCloseList />
 </#if>
</#macro>


<#macro mostSpecificType individual>
  <#if individual.mostSpecificTypes?has_content && individual.mostSpecificTypes[0]?has_content>
    (${individual.mostSpecificTypes[0]})
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
    ${i18n().gesah_search_in_year} ${yearInfo[0].year}
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

<#macro printCurInventoryNumber >
    <#if elementInfo?has_content && elementInfo[0].curNumber?has_content>
      <p>${i18n().gesah_current_inventory_number} ${elementInfo[0].curNumber}</p>
    </#if>
</#macro>

<#macro printParticipations>
	  <div class="search-element-information">
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
	  </div>
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

<#macro createImageThumbnail barcode fileName profileUrl>
  <#assign height = "150" />
  <div class="imageThumbnail">
     <a href="${profileUrl}" title="individual profile">
       <img src="${iiifUrl}/iiif/2/${barcode}${iiifSlash}content${iiifSlash}streams${iiifSlash}${fileName}/full/,${height}/0/default.jpg" />
     </a>
  </div>
</#macro>
