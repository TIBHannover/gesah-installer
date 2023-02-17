<#-- $This file is distributed under the terms of the license in /doc/license.txt$ -->

<div id="nav-wrapper">
    <div id="nav">
        <div class="container">
            <nav class="navbar navbar-default">
                <#-- Ugly check to see if we are on the homepage -->
                <#if geoFocusMapsEnabled??>
                    <div class="navbar-header pull-right">
                    <ul class="nav pull-left">
                    <li>
                    <#include "search.ftl">
                    </li>
                    </ul>
                    </div>
                <#elseif currentServlet != "extendedsearch">
                    <div class="navbar-header pull-right">
                        <ul class="nav pull-left">
                            <li>
                        	    <#include "search.ftl">
                            </li>
                        </ul>
                    </div>
                <#else>
                	<div class="navbar-header pull-right">
                        <ul class="nav pull-left">
                            <li>
                    	        <#include "remote-search.ftl">
                            </li>
                        </ul>
                    </div>
                </#if>

                <button class="navbar-toggle pull-left" type="button" data-toggle="collapse" data-target="#navbarContent" aria-controls="navbarContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="button-label">${i18n().collapsed_menu_name}</span>
                    <div class="button-bars">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </div>
                </button>

                <div class="collapse navbar-collapse navbar-left" id="navbarContent">
                    <ul id="main-nav" role="list" class="nav navbar-nav mr-auto mt-2 mt-md-0">
                    <#list menu.items as item>
                        <li class="nav-item" role="listitem" <#if item.active> class="active" </#if>><a href="${item.url}" title="${item.linkText} ${i18n().menu_item}" class="nav-link">${item.linkText}</a></li>
                    </#list>
		    <#if user.loggedIn>	
			<li class="nav-item" role="listitem"><a href="/gesah/personen" title="Personen menu item" class="nav-link">Personen</a></li>
			<li class="nav-item" role="listitem"><a href="/gesah/orte" title="Orte menu item" class="nav-link">Orte</a></li>
			<li class="nav-item" role="listitem"><a href="/gesah/thema" title="Thema menu item" class="nav-link">Thema</a></li>
			<li class="nav-item" role="listitem"><a href="/gesah/digitalisate" title="Digitalisate menu item" class="nav-link">Digitalisate</a></li>
			<li class="nav-item" role="listitem"><a href="/gesah/werke" title="Werke menu item" class="nav-link">Werke</a></li>
			<li class="nav-item" role="listitem"><a href="/gesah/imagesToLink" title="Images to link menu item" class="nav-link">Images to link</a></li>
			<li class="nav-item" role="listitem"><a href="/gesah/search-settings" title="Search settings" class="nav-link">Search settings</a></li>
		    </#if>
                    </ul>
                </div>
            </nav>
        </div>
    </div>
</div>
