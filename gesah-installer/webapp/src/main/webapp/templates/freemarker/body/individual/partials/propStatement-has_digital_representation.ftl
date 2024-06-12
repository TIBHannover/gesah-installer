<#-- $This file is distributed under the terms of the license in LICENSE$ -->

<#-- Custom object property statement view for http://vivoweb.org/ontology/core#dateTimeValue.

     This template must be self-contained and not rely on other variables set for the individual page, because it
     is also used to generate the property statement during a deletion.
 -->

<#include "lib-gesah-view.ftl">

<@digitalRepresentation statement />

<#macro digitalRepresentation statement >
  <div class="coLabelWithImage">
  	<a href="${profileUrl(statement.digRep)}" title="${title}">${statement.strlabel}</a>
  </div>
  <#if digitalReps?has_content>
    <div>
        <a href="${profileUrl(statement.digRep)}" title="${title}">
	      <img src="${urls.iiif}/iiif/2/${statement.barcode}${iiifSlash}content${iiifSlash}streams${iiifSlash}${statement.fileNum}/full/!335,700/0/default.jpg" />
	    </a>
        <#if user.loggedIn >
		    <div class="listElementInformation partObjectCreation" style="display:none;">
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
        </#if>
    </div>
  </#if>
</#macro>