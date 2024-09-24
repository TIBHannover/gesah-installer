<#-- $This file is distributed under the terms of the license in LICENSE$ -->

<#-- Custom object property statement view for http://vivoweb.org/ontology/core#dateTimeValue.

     This template must be self-contained and not rely on other variables set for the individual page, because it
     is also used to generate the property statement during a deletion.
 -->

<@showGoobiViewerLink statement />

<#macro showGoobiViewerLink statement>
    <a href="https://goobi.tib.eu/viewer/image/${statement.value?trim?keep_after("10.14463/KXP:")}/" target="_blank">Goobi Viewer</a>
</#macro>
