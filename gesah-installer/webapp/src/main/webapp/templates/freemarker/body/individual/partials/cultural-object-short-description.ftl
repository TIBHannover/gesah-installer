<div class="co-short-description">
	<@dataGetter uri = "http://gesah-short-view#Participants" var = "parts" parameters = {"cultural_object": individual.uri} />
	<#if parts?has_content>
		<#list parts as participant>
			<p>${participant.label}
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
</div>

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
	<#if place_date.place?has_content>
	    <br>${place_date.place}<#rt>
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