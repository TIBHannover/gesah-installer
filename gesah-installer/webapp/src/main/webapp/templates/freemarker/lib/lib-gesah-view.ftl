
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
	 	   </p>
	     </#if>
	     <#if producers?has_content>
	       <p>
	 	   <@printTypeInfo producers />
	 	   </p>
	     </#if>
	     <#if editors?has_content>
	       <p>
	 	   <@printTypeInfo editors />
	 	   </p>
	     </#if>
</#macro>

<#macro printTypeInfo people>
	    <#assign name = "" />
	    <#assign nameIsStarted = false />
	    
	    <#assign role = "" />
	    <#assign rolesIsStarted = false />
	    
		<#assign technique_and_material = "" />
		<#assign attribution = "" />
        <#assign placeAndDate = "" />
	    	    
	    <#list people as participation>
	    	<#if participation.name?? & name != participation.name >
	    	  <#assign name = participation.name />
	    	  <#if rolesIsStarted>
	    	    <@closeRoles />
	    	    <#assign rolesIsStarted = false />
	    	    <#assign role = "" />
	    	    ${attribution}
   	    		<#assign attribution = "" />
	    	  </#if>
	    	  <#if nameIsStarted>
	    	    <@closeName />
	    	    ${technique_and_material}
	      		<#assign technique_and_material = "" />
	    	    <#assign nameIsStarted = false />
	    	    ${placeAndDate}
	      		<#assign placeAndDate = "" />
	    	  </#if>
	    	  <#assign technique_and_material = getTechniqueAndMaterial(participation) />
  	    	  <#assign placeAndDate = getPlaceAndDate(participation) />
	    	  <p>${name}
	    	  <#if participation.attributionType?has_content>
    	  		<#assign attribution = "<span class=\"titleTypeListItemInv\">" + participation.attributionType + "</span>" />
	    	  </#if>
	    	  <#assign nameIsStarted = true />
	        </#if>
	        <#if participation.role?has_content && role != participation.role >
	    	  <#if rolesIsStarted>
	    	    ,<#lt>
	    	  <#else>
	    	    <@openRoles />
	    	  </#if>
	    	  <#assign rolesIsStarted = true />
	    	  <#assign role = participation.role />
			  ${role}<#t>
	        </#if>
	    </#list>
	    <#if rolesIsStarted>
	      <@closeRoles />
	      ${attribution}
   	      <#assign attribution = "" />
	    </#if>
	    <#if nameIsStarted>
	      <@closeName />
	      ${technique_and_material}
	      <#assign technique_and_material = "" />
	      ${placeAndDate}
	      <#assign placeAndDate = "" />
	    </#if>
</#macro>

<#function getTechniqueAndMaterial participation>
  <#if participation.technique?has_content && participation.material?has_content >
	<#return "<p> ${participation.technique} ${i18n().gesah_made_on} ${participation.material} </p>" >
  <#else>
   <#return "">
  </#if>
</#function>

<#function getPlaceAndDate participation>
  <#assign result = "" />
	<#assign place_printed = false />
	<#if participation.place?has_content>
	    <#assign result = result + participation.place/>
		<#assign place_printed = true />
	</#if>
	<#if participation.literalDate?has_content>
		<#if place_printed>
	        <#assign result = result + ", "/>
		</#if>
	    <#assign result = result + participation.literalDate />
	<#elseif participation.year?has_content >
		<#if participation.yearStart?has_content && ( participation.yearStart != participation.year ) >
			<#if place_printed>
	            <#assign result = result + ", "/>
			</#if>
	        <#assign result = result + participation.yearStart + "-" + participation.year />
		<#else>
			<#if place_printed>
	            <#assign result = result + ", "/>
			</#if>
	        <#assign result = result + participation.year />
		</#if>
	</#if>

  <#return result>
</#function>

<#macro openRoles>
  (<#rt>
</#macro>

<#macro closeRoles>
  )<#lt>
</#macro>

<#macro closeName >
  </p>
</#macro>