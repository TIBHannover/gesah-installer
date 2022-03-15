
<#assign iiifUrl="https://osl.tib.eu/gesah-iiif" />
<#assign iiifSlash="^" />

<a href="${individual.profileUrl}" title="individual profile">${individual.name}</a>
		
<div class="imageThumbnails">
  <#list imageInfo as image>
    <#if image.fileNum?has_content && image.barcode?has_content>
      <@createImageThumbnail image.barcode image.fileNum individual.profileUrl />
    </#if>
  </#list> 
  <@printParticipations participations />
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

<#macro openType type>
  <p>
  <#if type == "http://ontology.tib.eu/gesah/Edition">
    ${i18n().gesah_search_element_edited_by}
  <#elseif type == "http://ontology.tib.eu/gesah/Production">
    ${i18n().gesah_search_element_producted_by}
  <#elseif type == "http://ontology.tib.eu/gesah/Creation">
    ${i18n().gesah_search_element_created_by}
  </#if>
</#macro>

<#macro closeType>
  </p>
</#macro>

<#macro printCreationDate creationDate>
    <#if creationDate[0].date?has_content>
      <p>${i18n().gesah_search_element_creation_date} ${creationDate[0].date}</p>
    </#if>
</#macro>

<#macro printParticipations participations>
	<#if participations?has_content>
	  <div class="search-element-information">
	    <@printCreationDate creationDate />
	    <#assign type = "" />
	    <#assign name = "" />
	    <#assign role = "" />
	    <#assign rolesIsStarted = false />
	    <#assign nameIsStarted = false />
	    <#assign typeIsStarted = false />
	
	    <#list participations as participation>
	    
	    	<#--  participation type has changed? -->
	    	<#if type != participation.type >
	    	  <#--  previous type description is started? -->
	    	  <#if typeIsStarted >
	    	    <#-- close roles list if roles list is started -->
	    	    <#if rolesIsStarted>
	    	      <@closeRoles />
	    	      <#assign rolesIsStarted = false />
	    	    </#if>
	    	    <#-- close participant naming if it is started -->
	    	    <#if nameIsStarted>
	    	      <@closeName />
	    	      <#assign nameIsStarted = false />
	    	    </#if>
	    	    <@closeType />
	    	    <#assign typeIsStarted = false />
	    	  </#if>
	    	  <#-- all previous types, roles and participant naming are closed -->
	    	  <#assign type = participation.type />
	    	  <@openType type />
	    	  <#assign typeIsStarted = true />
	        </#if>
	        
	    	<#if name != participation.name >
	    	  <#if rolesIsStarted>
	    	    <@closeRoles />
	    	    <#assign rolesIsStarted = false />
	    	  </#if>
	    	  <#if nameIsStarted>
	    	    <@closeName />
	    	    <#assign nameIsStarted = false />
	    	  </#if>
	    	  <#assign name = participation.name />
	    	  <@openName name/>
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
	    <#if typeIsStarted>
	      <@closeType />
	    </#if>
	  </div>
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
