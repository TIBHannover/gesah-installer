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
	<#elseif elementInfo?has_content && elementInfo[0].formNumber?has_content>
		<@printFormInventoryNumber elementInfo[0].formNumber />
	</#if>
	<@printParticipations />
  </div>

</div>

<#macro closeParticipationRoleList participation type name >
 <#if name?has_content && type?has_content && ( participation.name != name || participation.type != type )>
   <@roleCloseList />
 </#if>
</#macro>

