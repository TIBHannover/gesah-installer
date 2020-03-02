<#-- Renders the html for the research section on the home page. -->
<#-- Works in conjunction with the homePageUtils.js file -->
<#macro researchClasses classGroups=vClassGroups>
    <#assign foundClassGroup = false />
    <section id="home-research" class="home-sections">
    <h4>${i18n().research_capitalized}</h4>
    <div id="home-sections-list" class="list-group">
    <#list classGroups as group>
        <#if (group.individualCount > 0) && group.uri?contains("publications") >
            <#assign foundClassGroup = true />
            <#list group.classes as class>
                <#if (class.individualCount > 0) && (
                        class.uri?contains("/AcademicArticle") ||
                        class.uri?contains("/Book") ||
                        class.uri?contains("/Chapter") ||
                        class.uri?contains("#ConferencePaper") ||
                        class.uri?contains("#Dataset") ||
                        class.uri?contains("#Grant") ||
                        class.uri?contains("/Report")) >
                    <a class="list-group-item" href='${urls.base}/research#${class.uri!}'>
                    <#if class.name?substring(class.name?length-1) == "s">
                        ${class.name}
                    <#else>
                        ${class.name}
                    </#if>
                <#-- Add bootstrap badge class to individual counts -->
                &nbsp;
                    <span class="badge">${class.individualCount!}</span>
                    </a>
                </#if>
            </#list>
            <a href="${urls.base}/research"   style="color: AF1414;" alt="${i18n().view_all_research}">${i18n().view_all}</a>
        </#if>
    </#list>
    <#if !foundClassGroup>
        <p style="padding-left:1.2em">${i18n().no_research_content_found}</p>
    </#if>
    </div>
    </section>
</#macro>
