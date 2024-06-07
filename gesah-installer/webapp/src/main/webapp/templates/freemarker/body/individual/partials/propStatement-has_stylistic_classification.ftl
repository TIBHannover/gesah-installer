<#-- $This file is distributed under the terms of the license in LICENSE$ -->

<#-- Default object property statement template.

     This template must be self-contained and not rely on other variables set for the individual page, because it
     is also used to generate the property statement during a deletion.
 -->

<a href="${profileUrl(statement.uri("object"))}" title="${i18n().name}">${statement.label!statement.localName!}</a>
<#assign isEdit = individual?has_content && individual.showAdminPanel />
<#if isEdit>
  <div class="partObjectCreation" style="display:none;" >
    <input form="part-creation" type="checkbox" name="stylistic-${statement.uri("object")}" value="${statement.uri("object")}" checked>
    <label style="display:inline;" for="stylistic-${statement.uri("object")}">Use stylistic classification</label>
  </div>
</#if>