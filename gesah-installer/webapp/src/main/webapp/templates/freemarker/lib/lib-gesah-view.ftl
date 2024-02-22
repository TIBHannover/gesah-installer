<#import "lib-properties.ftl" as p>

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

<#macro printPlaceAndDate place_date>
	<#assign place_printed = false />
	<#if place_date.place?has_content && place_date.place_label?has_content>
	    <a href="${profileUrl(place_date.place)}">${place_date.place_label}</a><#rt>
		<#assign place_printed = true />
	</#if>
	<#if place_date.literalDate?has_content>
		<#if place_printed>
	        ,<#lt>
		</#if>
	    ${place_date.literalDate}
	<#elseif place_date.yearEnd?has_content>
		<#if place_date.yearStart?has_content && ( place_date.yearStart != place_date.yearEnd ) >
			<#if place_printed>
	            ,<#lt>
			</#if>
	        ${place_date.yearStart} - ${place_date.yearEnd}
		<#else>
			<#if place_printed>
	        	,<#lt>
			</#if>
	        ${place_date.yearEnd}
		</#if>
	</#if>
</#macro>

<#function getAddUrl subjectUri predicateUri returnURL='' >
	<#return 
	urls.base + 
	"/editRequestDispatch?subjectUri=" + subjectUri + 
	"&predicateUri=" + predicateUri + 
	"&returnURL=" + returnURL /> 
</#function>

<#function getDeletePropertyUrl subjectUri predicateUri objectUri redirectUrl=''>
	<#return 
	urls.base + 
	"/editRequestDispatch" + 
	"?subjectUri=" + subjectUri + 
	"?predicateUri=" + predicateUri +
	"?objectUri=" + objectUri + 
	"&cmd=delete&objectKey=object" /> 
</#function>

<#function getDeleteIndividualUrl objectUri individualName redirectUrl=''>
	<#return 
		urls.base + 
		"/editRequestDispatch" + 
		"?objectUri=" + objectUri + 
		"&individualName=" + individualName +
		"&cmd=delete&objectKey=object" + 
		"&redirectUrl=" + redirectUrl?keep_after("/")?keep_after("/")
	 /> 
</#function>

<#function getEditUrl subjectUri predicateUri objectUri returnURL=''>
	<#return 
	urls.base + 
	"/editRequestDispatch" + 
	"?subjectUri=" + subjectUri + 
	"&predicateUri=" + predicateUri +
	"&objectUri=" + objectUri + 
	"&returnURL=" + returnURL /> 
</#function>

<#macro showActivityDescription statement>
	<@activityDescription statement.activity profileUrl(individual.getUri()) />
</#macro>

<#macro activityRoles activityUri pageUri isEdit>
	<@dataGetter uri = "http://gesah-short-view#Roles" var = "roles" parameters = {"activity": activityUri } />
	<#if isEdit>
    	Add role <@p.showAddLink 'role' 'Role' getAddUrl(activityUri, prop_uri, pageUri) '' ''/>
    </#if>
	<#if roles?has_content>
		<#list roles as role>
			<p>
				<a href="${profileUrl(role.agent)}">${role.agent_label}</a><br>
			    (${role.role_type_label!'no role'}) 
			    <#if role.attribution_label?has_content>
			    	<span class="titleTypeListItem">${role.attribution_label}</span>
			    </#if>
			    <#if isEdit>
				    <@p.showEditLink 'role' '' getEditUrl(activityUri, prop_uri, role.role, pageUri) />
	                <@p.showDeleteLink 'role' '' getDeleteIndividualUrl(role.role, "Role of " + role.agent_label, pageUri) />
                </#if>
			</p>
		</#list>
	</#if>
</#macro>

<#macro activityDescription activityUri pageUri>
	<#assign prop_uri = 'http://ontology.tib.eu/gesah/realizes' />
	<#assign isEdit = individual?has_content && individual.showAdminPanel />
	<div class="listViewCard">
		<@activityRoles activityUri pageUri isEdit />
		<@placesAndDates activityUri/>
		<@dataGetter uri = "http://gesah-short-view#TechniquesMaterials" parameters = {"_participation": activityUri} />
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
		<@dataGetter uri = "http://gesah-short-view#Comments" parameters = {"participation": activityUri} />
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
		<#if isEdit>
			<div class="contextLink"><a href="${profileUrl(activityUri)}">${activityUri?keep_after_last("/")}</a></div>
		</#if>
	</div>
</#macro>

<#macro placesAndDates activityUri>
	<@dataGetter uri = "http://gesah-short-view#PlacesDates" parameters = {"participation": activityUri} />
	<#if place_dates?has_content>
		<#list place_dates as place_date>
			<@printPlaceAndDate place_date />
		</#list>
	</#if>
</#macro>

<#macro printParticipations individual>
    <@shortDescription individual.uri />
</#macro>

<#macro shortDescription co >
	<@dataGetter uri = "http://gesah-short-view#Roles" parameters = {"cultural_object": co} />
	<#if roles?has_content>
		<#assign activity = '' /> 
		<#list roles as role>
    		<#if activity?has_content && activity != role._activity >
    			<@placesAndDates activity/>
			</#if>
			<p>
				<a href="${profileUrl(role.agent)}">${role.agent_label}</a><br>
				(${role.role_type_label!'no role'})
			    <#if role.attribution_label?has_content>
			    	 <span class="titleTypeListItemInv">${role.attribution_label}</span>
			    </#if>
			    
			</p>
			<#assign activity = role._activity />
		</#list>
		<#if activity?has_content>
			<@placesAndDates activity/>
		</#if>
		
		<@dataGetter uri = "http://gesah-short-view#TechniquesMaterials" parameters = {"cultural_object": co} />
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
