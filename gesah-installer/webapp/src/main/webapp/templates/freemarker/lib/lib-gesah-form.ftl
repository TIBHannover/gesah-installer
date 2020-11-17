<#-- $This file is distributed under the terms of the license in LICENSE$ -->

<#-- Macros and functions for form controls -->

<#-- After selecting an individual via autocomplete, display highlighted and with verify link -->
<#macro acSelection urlsBase inputName inputId acGroupName inputValue labelValue="">
<div class="acSelection" acGroupName="${acGroupName}">
    <p class="inline">
        <label>${labelValue}</label>
        <span class="acSelectionInfo"></span>
        <a href="${urlsBase}/individual?uri=" class="verifyMatch" title="${i18n().verify_this_match_title}">(${i18n().verify_match_capitalized}</a> ${i18n().or}
        <a href="#" class="changeSelection" id="changeSelection">${i18n().change_selection})</a>
        </p>
        <input class="acUriReceiver" type="hidden" id="${inputId}" name="${inputName}" value="${inputValue}" />
        <!-- Field value populated by JavaScript -->
</div>
</#macro>
