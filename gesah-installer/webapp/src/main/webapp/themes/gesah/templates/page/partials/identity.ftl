<#-- $This file is distributed under the terms of the license in /doc/license.txt$ -->

<div class="container">
    <div class="row">
        <div>

<nav role="navigation">
    <ul id="header-nav" role="list">
        <#include "languageSelector.ftl">
        <#if user.loggedIn>
                <li role="listitem"><a href="${urls.index}" title="${i18n().identity_index}">${i18n().identity_index}</a></li>
            <#-- COMMENTING OUT THE EDIT PAGE LINK FOR RELEASE 1.5. WE NEED TO IMPLEMENT THIS IN A MORE 
                 USER FRIENDLY WAY. PERHAPS INCLUDE A LINK ON THE PAGES THEMSELVES AND DISPLAY IF THE
                 USER IS A SITE ADMIN. tlw72
                 
                 <#if (page??) && (page?is_hash || page?is_hash_ex) && (page.URLToEditPage??) >
                    <li role="listitem"><a href="${page.URLToEditPage}" title="${i18n().identity_edit}">${i18n().identity_edit}</a></li>
                 </#if>
            -->
            <#if user.hasSiteAdminAccess>
                <li role="listitem"><a href="${urls.siteAdmin}" title="${i18n().identity_admin}">${i18n().identity_admin}</a></li>
            </#if>
                <li>
                    <ul class="dropdown">
                        <li id="user-menu"><a href="#" title="${i18n().identity_user}">${user.loginName}</a>
                            <ul class="sub_menu">
                                 <#if user.hasProfile>
                                     <li role="listitem"><a href="${user.profileUrl}" title="${i18n().identity_myprofile}">${i18n().identity_myprofile}</a></li>
                                 </#if>
                                 <#if urls.myAccount??>
                                     <li role="listitem"><a href="${urls.myAccount}" title="${i18n().identity_myaccount}">${i18n().identity_myaccount}</a></li>
                                 </#if>
                                 <li role="listitem"><a href="${urls.logout}" title="${i18n().menu_logout}">${i18n().menu_logout}</a></li>
                            </ul>
                        </li>
                     </ul>
                 </li>
                 

            
            ${scripts.add('<script type="text/javascript" src="${urls.base}/js/userMenu/userMenuUtils.js"></script>')}
            
        <#else>
            <li role="listitem"><a class="log-out" title="${i18n().menu_loginfull}" href="${urls.login}">${i18n().menu_login}</a></li>
        </#if>
    </ul>
</nav>
<p class="vivo-logo" style="background:url(${urls.base}/images/${i18n().header_logo_file_name}) 0 0 no-repeat;"><a title="${i18n().identity_title}" href="${urls.home}"><span class="displace">${siteName}</span></a></p>
        </div>
    </div>
</div>
