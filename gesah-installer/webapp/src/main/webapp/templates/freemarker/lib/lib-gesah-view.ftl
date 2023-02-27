
<#assign iiifUrl="https://osl.tib.eu/gesah-iiif" />
<#assign iiifSlash="%5E" /> 
<#assign height = "200" />
<#assign maxWidth = "220" />

<#macro addCommaSeparator>
, <#lt>
</#macro>

<#macro printElementContainer statement uri title aLabel pageLink>
    <div class="listElementContainer">
	    <#if statement.fileNum?has_content && statement.barcode?has_content>
	      <@createImageThumbnail statement.barcode statement.fileNum profileUrl(uri) title />
	    </#if>
        <div class="listElementInformation">
			${aLabel}
			${pageLink}
			<#if statement.roles?has_content>
				<p>Roles: ${statement.roles}</p>
			</#if>
			<#if statement.curNumber?has_content>
				<@printCurInventoryNumber statement.curNumber />
			<#elseif statement.formNumber?has_content>
				<@printFormInventoryNumber statement.formNumber />
			</#if>
  		</div>
	</div>
</#macro>

<#function labelWithLink url title type >
	<#return "<a class=\"listElementIndividualLink\"  href=\"" + url + "\" title=\"" + title + "\">" + title + " " + type + "</a>" />
</#function>

<#function typeFromStatement statement>
  <#if statement.typeLabel?has_content >
    <#return "(" + statement.typeLabel + ")" >
  <#else>
   <#return "">
  </#if>
</#function>

<#function typeFromIndividual individual>
  <#if individual.mostSpecificTypes?has_content>
    <#if individual.mostSpecificTypes?is_hash >
      <#return "" >
    </#if>
    <#if individual.mostSpecificTypes[0]?has_content >
       <#return "(" + individual.mostSpecificTypes[0] + ")" >
    </#if>
  </#if>
  <#return "">
</#function>

<#function getSupplementaryPageLink statement pageName>
	<#if individual?has_content && individual.editable?has_content && individual.editable>
		<#if statement.label?has_content>
			<#assign title = statement.label />
		<#else>
			<#assign title = statement.uri("object") />
		</#if>
		<#return "<br/><a href=\"" + profileUrl(statement.uri("object")) + "\" title=\"" + title + "\">" + pageName + "</a>" />
	<#else>
		<#return "" />
	</#if>
</#function>

<#macro printFormInventoryNumber number>
    <#if number?has_content>
      <p>${i18n().gesah_former_inventory_number} ${number}</p>
    </#if>
</#macro>

<#macro printCurInventoryNumber curNumber>
    <#if curNumber?has_content>
      <p>${i18n().gesah_current_inventory_number} ${curNumber}</p>
    </#if>
</#macro>

<#macro createImageThumbnail barcode fileName profileUrl title>
  <div class="imageThumbnail">
     <a href="${profileUrl}" title="${title}">
       <img src="${iiifUrl}/iiif/2/${barcode}${iiifSlash}content${iiifSlash}streams${iiifSlash}${fileName}/full/${maxWidth},/0/default.jpg" />
     </a>
  </div>
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
				${placeAndDateInfo[0].yearStart}-${placeAndDateInfo[0].year}
			<#else>
				<#if place_printed>
					<@addCommaSeparator/>
				</#if>
				${placeAndDateInfo[0].year}
			</#if>
		</#if>
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