<#-- $This file is distributed under the terms of the license in /doc/license.txt$ -->

<#-- Template for property listing on individual profile page -->

<#import "lib-properties.ftl" as p>
<#assign subjectUri = individual.controlPanelUrl()?split("=") >
<#assign tabCount = 1 >
<#assign sectionCount = 1 >

<div id="property-group-notabs">
    <div class="row">
        <div class="col-md-12 tab-content">
            <#list propertyGroups.all as group>
                <#if (group.properties?size > 0)>
                    <#assign groupName = group.getName(nameForOtherGroup)>
                    <#assign groupNameHtmlId = p.createPropertyGroupHtmlId(groupName) >
                    <#assign verbose = (verbosePropertySwitch.currentValue)!false>

                    <div id="${groupNameHtmlId?replace("/","-")}Group"
                         class="tab-pane active"
                         role="tabpanel">
                        <#-- Display the group heading -->
                        <#if groupName?has_content>
                        <#--the function replaces spaces in the name with underscores, also called for the property group menu-->
                            <#assign groupNameHtmlId = p.createPropertyGroupHtmlId(groupName) >
                            <h2 id="${groupNameHtmlId?replace("/","-")}" pgroup="tabs" class="hidden">${groupName?capitalize}</h2>
                        <#else>
                            <h2 id="properties" pgroup="tabs" class="hidden">${i18n().properties_capitalized}</h2>
                        </#if>
                        <#-- List the properties in the group   -->
                        <#include "individual-properties.ftl">
                    </div>
                    <#assign sectionCount = 2 >
                </#if>
            </#list>
        </div>
    </div>
</div>
<script>
    var individualLocalName = "${individual.localName}";
</script>

${stylesheets.add('<link rel="stylesheet" href="${urls.base}/css/individual/individual-property-groups.css" />')}
${headScripts.add('<script type="text/javascript" src="${urls.base}/js/amplify/amplify.store.min.js"></script>')}
