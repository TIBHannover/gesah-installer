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

<#macro printImageInfo statement >
  <a href="${profileUrl(statement.digRep)}" title="${title}">${statement.strlabel}</a>
  <#if user.loggedIn>
    <div class="partObjectCreation" style="display:none;" >
	    <div>
	      <a href="${profileUrl(statement.digRep)}" title="${title}">
	        <img src="${urls.iiif}/iiif/2/${statement.barcode}${iiifSlash}content${iiifSlash}streams${iiifSlash}${statement.fileNum}/full/${maxWidth},/0/default.jpg" />
	      </a>
	    </div>
	    <div class="listElementInformation">
	      <p>Already assigned to ${statement.co_count} cultural objects</p>
          <select name="image-${statement.digRep}" autocomplete="off" form="part-creation">
            <option selected value="">not assigned</option>
            <#assign valueNumber = 1>
            <#list digitalReps as rep>
	          <option value="${valueNumber}">${valueNumber}</option>
	          <#assign valueNumber += 1>
	        </#list>              
          </select>
          <input form="part-creation" type="checkbox" autocomplete="off" name="main-image-${statement.digRep}" value="true">
		  <label style="display:inline;" for="main-image-${statement.digRep}">Is main image</label>
	    </div>
    </div>
  </#if>
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

<#macro printTechniques participation material techniques_materials isEdit>
    <#assign techniquesStarted = false />
    <#list techniques_materials as technique_on_material>
        <#if participation == technique_on_material.participation && material == technique_on_material.material_label>
            <#if techniquesStarted>
                ,<#lt>
            </#if>
            <#assign techniquesStarted = true />
            ${technique_on_material.technique_label}<#rt>
            <#if isEdit>
                <#assign technique_prop_uri = 'http://ontology.tib.eu/gesah/uses_technique' />
                <@p.showEditLink 'technique' '' getEditUrl(participation, technique_prop_uri, technique_on_material.technique) />
                <@p.showDeleteLink 'technique' '' getDeletePropertyUrl(participation, technique_prop_uri, technique_on_material.technique technique_on_material.technique_label) />
                <br>
            </#if>
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

<#function getAddUrl subjectUri predicateUri>
    <#return 
    urls.base + 
    "/editRequestDispatch?subjectUri=" + subjectUri + 
    "&predicateUri=" + predicateUri + 
    "&returnURL=" + urls.requestUrl /> 
</#function>

<#function getDeleteIndividualUrl objectUri individualName>
    <#return 
        urls.base + 
        "/editRequestDispatch" + 
        "?objectUri=" + objectUri + 
        "&individualName=" + individualName +
        "&cmd=delete&objectKey=object" + 
        "&returnURL=" + urls.requestUrl
     /> 
</#function>

<#function getDeletePropertyUrl subjectUri predicateUri objectUri label=''>
	<#return 
	urls.base + 
	"/editRequestDispatch" + 
	"?subjectUri=" + subjectUri + 
	"&predicateUri=" + predicateUri +
	"&objectUri=" + objectUri + 
    "&returnURL=" + urls.requestUrl + 
	"&cmd=delete&objectKey=object" +
	"&statement_label=" + label
	 /> 
</#function>

<#function getEditUrl subjectUri predicateUri objectUri>
    <#return 
    urls.base + 
    "/editRequestDispatch" + 
    "?subjectUri=" + subjectUri + 
    "&predicateUri=" + predicateUri +
    "&objectUri=" + objectUri + 
    "&returnURL=" + urls.requestUrl /> 
</#function>

<#macro showActivityDescription statement>
    <#if individual?has_content>
        <@activityDescription statement.activity />
    </#if>
</#macro>

<#macro activityRoles activityUri isEdit>
    <#assign prop_uri = 'http://ontology.tib.eu/gesah/realizes' />
    <@dataGetter uri = "http://gesah-short-view#ActivityRoles" var = "roles" parameters = {"activity": activityUri } />
    <#if isEdit>
        Add role <@p.showAddLink 'role' 'Role' getAddUrl(activityUri, prop_uri) '' ''/>
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
                    <@p.showEditLink 'role' '' getEditUrl(activityUri, prop_uri, role.role) />
                    <@p.showDeleteLink 'role' '' getDeleteIndividualUrl(role.role, "Role of " + role.agent_label) />
                </#if>
            </p>
        </#list>
    </#if>
</#macro>

<#macro activityDescription activityUri>
    <#assign isEdit = individual?has_content && individual.showAdminPanel />
    <div class="listViewCard">
        <@activityRoles activityUri isEdit />
        <@placesAndDates activityUri/>
        <@dataGetter uri = "http://gesah-short-view#TechniquesMaterials" parameters = {"_participation": activityUri} />
        <#if isEdit>
            <#assign technique_prop_uri = 'http://ontology.tib.eu/gesah/uses_technique' />
            <br>Add technique <@p.showAddLink 'technique' 'Technique' getAddUrl(activityUri, technique_prop_uri) '' ''/><br>
        </#if>
        <#if techniques_materials?has_content>
            <#assign previousParticipation = "" />
            <#assign previousMaterial = "" />
            <#list techniques_materials as technique_on_material>
                <#if previousParticipation != technique_on_material.participation || previousMaterial != technique_on_material.material_label>
                    <p> <@printTechniques technique_on_material.participation technique_on_material.material_label techniques_materials isEdit /> ${i18n().gesah_made_on} ${technique_on_material.material_label}</p>
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
		<#if isEdit>
		  <div class="partObjectCreation" style="display:none;" >
		    <input form="part-creation" type="checkbox" autocomplete="off" name="activity-${activityUri}" value="${activityUri}">
		    <label style="display:inline;" for="activity-${activityUri}">Copy activity</label>
		  </div>
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
    <@dataGetter uri = "http://gesah-short-view#CulturalObjectRoles" parameters = {"cultural_object": co} />
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
                    <p> <@printTechniques technique_on_material.participation technique_on_material.material_label techniques_materials false /> ${i18n().gesah_made_on} ${technique_on_material.material_label}</p>
                    <#assign previousParticipation = technique_on_material.participation />
                    <#assign previousMaterial = technique_on_material.material_label />
                </#if>
            </#list>
        </#if>
    </#if>
</#macro>
