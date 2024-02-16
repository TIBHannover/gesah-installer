
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
       <img src="${urls.iiif}/iiif/2/${barcode}${iiifSlash}content${iiifSlash}streams${iiifSlash}${fileName}/full/${maxWidth},/0/default.jpg" />
     </a>
  </div>
</#macro>

<#--  start new marco -->
<#macro printParticipations individual>
	<@dataGetter uri = "http://gesah-short-view#Participants" var = "parts" parameters = {"cultural_object": individual.uri} />
	<#if parts?has_content>
		<#list parts as participant>
			<p><a href="${profileUrl(participant.uri)}">${participant.label}</a>
				<@dataGetter uri = "http://gesah-short-view#Attributions" parameters = {"cultural_object": individual.uri, "participant": participant.uri} />
				<#if attributions?has_content>
					<#list attributions as attribution>
					    <@dataGetter uri = "http://gesah-short-view#Roles" parameters = {"cultural_object": individual.uri, "participant": participant.uri , "attribution": attribution.uri} />
					    <br>
					    <@printRoles roles />
						<span class="titleTypeListItemInv">${attribution.label}</span>
						<@dataGetter uri = "http://gesah-short-view#PlacesDates" parameters = {"cultural_object": individual.uri, "participant": participant.uri , "attribution": attribution.uri} />
						<#if place_dates?has_content>
							<#list place_dates as place_date>
								<@printPlaceAndDate place_date />
							</#list>
						</#if>
					</#list>
				</#if>
				<@dataGetter uri = "http://gesah-short-view#RolesNoAttribution" parameters = { "cultural_object": individual.uri, "participant": participant.uri} />
			    <@printRoles roles />
				<@dataGetter uri = "http://gesah-short-view#PlacesDatesNoAttribution" parameters = { "cultural_object": individual.uri, "participant": participant.uri} />
				<#if place_dates?has_content>
					<#list place_dates as place_date>
						<@printPlaceAndDate place_date />
					</#list>
				</#if>
			</p>
		</#list>
		<@dataGetter uri = "http://gesah-short-view#TechniquesMaterials" parameters = {"cultural_object": individual.uri} />
		<#if techniques_materials?has_content>
		    <#assign previousParticipation = "" />
		    <#assign previousMaterial = "" />
			<#list techniques_materials as technique_on_material>
				<#if previousParticipation != technique_on_material.participation || previousMaterial != technique_on_material.material_label>
					<p> <@printTechniques technique_on_material.participation technique_on_material.material_label techniques_materials /> ${i18n().gesah_made_on} ${technique_on_material.material_label}</p>
					<#assign previousParticipation = technique_on_material.participation />
					<#assign previousMaterial = technique_on_material.material_label />
				</#if>
			</#list>
		</#if>
	</#if>
</#macro>

<#macro printTechniques participation material techniques_materials>
    <#assign techniquesStarted = false />
	<#list techniques_materials as technique_on_material>
		<#if participation == technique_on_material.participation && material == technique_on_material.material_label>
			<#if techniquesStarted>
		        ,<#lt>
			</#if>
			<#assign techniquesStarted = true />
			${technique_on_material.technique_label}<#rt>
		</#if>
	</#list>
</#macro>

<#macro printRoles roles>
	<#if roles?has_content>
	    <#assign rolesStarted = false />
	    (<#rt>
	    <#list roles as role>
	    	<#if rolesStarted>
				,<#lt>
		    <#else>
		    	<#assign rolesStarted = true />
		    </#if>
	    	<#lt>${role.label}<#rt>
	    </#list>
	    )<#lt>
	</#if>
</#macro>

<#macro printPlaceAndDate place_date>
	<#assign place_printed = false />
	<#if place_date.place?has_content && place_date.place_label?has_content>
	    <br><a href="${profileUrl(place_date.place)}">${place_date.place_label}</a><#rt>
		<#assign place_printed = true />
	</#if>
	<#if place_date.literalDate?has_content>
		<#if place_printed>
	        ,<#lt>
        <#else>
			<br>
		</#if>
	    ${place_date.literalDate}
	<#elseif place_date.yearEnd?has_content>
		<#if place_date.yearStart?has_content && ( place_date.yearStart != place_date.yearEnd ) >
			<#if place_printed>
	            ,<#lt>
            <#else>
				<br>
			</#if>
	        ${place_date.yearStart} - ${place_date.yearEnd}
		<#else>
			<#if place_printed>
	        	,<#lt>
            <#else>
				<br>
			</#if>
	        ${place_date.yearEnd}
		</#if>
	</#if>
</#macro>

<#macro showActivityDescription statement>
	<div class="listViewCard">
		<@dataGetter uri = "http://gesah-short-view#Participants" var = "participants" parameters = {"participation": statement.activity } />
		<#if participants?has_content>
			<#list participants as participant>
				<p><a href="${profileUrl(participant.uri)}">${participant.label}</a>
					<@dataGetter uri = "http://gesah-short-view#Attributions" parameters = {"participation": statement.activity, "participant": participant.uri} />
					<#if attributions?has_content>
						<#list attributions as attribution>
						    <@dataGetter uri = "http://gesah-short-view#Roles" parameters = {"participation": statement.activity, "participant": participant.uri , "attribution": attribution.uri} />
						    <br>
						    <@printRoles roles />
							<span class="titleTypeListItem">${attribution.label}</span>
							<@dataGetter uri = "http://gesah-short-view#PlacesDates" parameters = {"participation": statement.activity, "participant": participant.uri , "attribution": attribution.uri} />
							<#if place_dates?has_content>
								<#list place_dates as place_date>
									<@printPlaceAndDate place_date />
								</#list>
							</#if>
						</#list>
					</#if>
					<@dataGetter uri = "http://gesah-short-view#RolesNoAttribution" parameters = {"participation": statement.activity, "participant": participant.uri} />
				    <@printRoles roles />
					<@dataGetter uri = "http://gesah-short-view#PlacesDatesNoAttribution" parameters = {"participation": statement.activity, "participant": participant.uri} />
					<#if place_dates?has_content>
						<#list place_dates as place_date>
							<@printPlaceAndDate place_date />
						</#list>
					</#if>
				</p>
			</#list>
			<@dataGetter uri = "http://gesah-short-view#TechniquesMaterials" parameters = {"_participation": statement.activity} />
			<#if techniques_materials?has_content>
			    <#assign previousParticipation = "" />
			    <#assign previousMaterial = "" />
				<#list techniques_materials as technique_on_material>
					<#if previousParticipation != technique_on_material.participation || previousMaterial != technique_on_material.material_label>
						<p> <@printTechniques technique_on_material.participation technique_on_material.material_label techniques_materials /> ${i18n().gesah_made_on} ${technique_on_material.material_label}</p>
						<#assign previousParticipation = technique_on_material.participation />
						<#assign previousMaterial = technique_on_material.material_label />
					</#if>
				</#list>
			</#if>
		</#if>
		<@dataGetter uri = "http://gesah-short-view#Comments" parameters = {"participation": statement.activity} />
		<#if comments?has_content>
			${i18n().comment_capitalized}:
			<#assign commentStarted = false />
			<#list comments as comment>
				<#if commentStarted>
			        ,<#lt>
				</#if>
				${comment.comment}<#rt>
				<#assign commentStarted = true />
			</#list>
			<br />
		</#if>
		<#-- If user can edit individual, show a link to the context object -->
		<#if individual?has_content && individual.showAdminPanel>
			<div class="contextLink"><a href="${profileUrl(statement.activity)}">${statement.activity?keep_after_last("/")}</a></div>
		</#if>
	</div>
</#macro>