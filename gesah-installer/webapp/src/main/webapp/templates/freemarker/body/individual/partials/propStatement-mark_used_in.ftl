<#-- $This file is distributed under the terms of the license in LICENSE$ -->

<#-- Custom object property statement view for http://vivoweb.org/ontology/core#dateTimeValue.

     This template must be self-contained and not rely on other variables set for the individual page, because it
     is also used to generate the property statement during a deletion.
 -->

<#include "lib-gesah-view.ftl">

<#assign aLabel = labelWithLink(profileUrl(statement.uri("cultObject")), statement.cultObjectLabel, typeFromStatement(statement)) />
<#assign supplementaryPage = getSupplementaryPageLink(statement, "Mark designation") />
<@printElementContainer statement statement.uri("cultObject") statement.cultObjectLabel aLabel supplementaryPage statement.hideFromPublic!"" />