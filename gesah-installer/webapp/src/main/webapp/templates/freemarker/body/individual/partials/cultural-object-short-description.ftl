<#import "lib-gesah-view.ftl" as lgv>

<#if creators?has_content || producers?has_content || editors?has_content >
	<div class="co-short-description">
		<@lgv.printParticipations />
	</div>
</#if>