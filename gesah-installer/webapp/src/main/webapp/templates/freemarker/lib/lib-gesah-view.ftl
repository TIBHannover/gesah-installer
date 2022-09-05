
<#assign iiifUrl="https://osl.tib.eu/gesah-iiif" />
<#assign iiifSlash="^" /> 
<#assign height = "200" />
<#assign maxWidth = "220" />


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
  <#if individual.mostSpecificTypes?has_content && individual.mostSpecificTypes[0]?has_content>
	<#return "(" + individual.mostSpecificTypes[0] + ")" >
  <#else>
	<#return "">
  </#if>
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
