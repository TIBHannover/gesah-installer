<#-- $This file is distributed under the terms of the license in LICENSE$ -->

<#-- Custom object property statement view for http://vivoweb.org/ontology/core#dateTimeValue.

     This template must be self-contained and not rely on other variables set for the individual page, because it
     is also used to generate the property statement during a deletion.
 -->

<@showWikidataLink statement />

<#macro showWikidataLink statement>
    <a href="https://www.wikidata.org/wiki/${statement.value?trim}" target="_blank" >${statement.value?trim}</a>
</#macro>
