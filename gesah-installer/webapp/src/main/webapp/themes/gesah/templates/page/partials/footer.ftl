<#-- $This file is distributed under the terms of the license in /doc/license.txt$ -->
<footer role="contentinfo">
    <div class="row">
        <div class="col-md-12">
            <div class="container">
                <div class="row">
                    <div class="col-md-8">
                        <p class="copyright">
                        <#if copyright??>
                            <small>&copy;${copyright.year?c}
                                <#if copyright.url??>
                                    <a href="${copyright.url}" title="${i18n().menu_copyright}">${copyright.text}</a>
                                <#else>
                                ${copyright.text}
                                </#if>
                               | <a class="terms" href="${urls.termsOfUse}" title="${i18n().menu_termuse}">${i18n().menu_termuse}</a></small> |
                        </#if>

                             <a class="terms" href="${i18n().menu_data_protection_url}" title="${i18n().menu_data_protection}">${i18n().menu_data_protection}</a></small> |
                            <a class="terms" href="${i18n().menu_imprimnt_url}" title="${i18n().menu_imprint}">${i18n().menu_imprint}</a></small> |


                        ${i18n().menu_powered} <a class="powered-by-vivo" href="http://vivoweb.org" target="_blank" title="${i18n().menu_powered} Vitro and TIB ">  <strong>Vitro</strong> and <strong>TIB</strong></a>
                        <#if user.hasRevisionInfoAccess>
                            | ${i18n().menu_version} <a href="${version.moreInfoUrl}" title="${i18n().menu_version}">${version.label}</a>
                        </#if>
                        </p>
                    </div>
                    <div class="col-md-4">
                        <nav role="navigation">
                            <ul id="footer-nav" role="list">
                                <li role="listitem"><a href="${urls.about}" title="${i18n().menu_about}">${i18n().menu_about}</a></li>
                                <li role="listitem"><a href="http://www.vivoweb.org/support" target="blank" title="${i18n().menu_support}">${i18n().menu_support}</a></li>
                            </ul>
                        </nav>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        window.cookieconsent_options = {
            learnMore: '${i18n().cookie_data_protection}',
            dismiss: '${i18n().cookie_button}',
            message: '${i18n().cookie_message}',
            link: '${i18n().menu_data_protection_url}'
        };
    </script>
    ${scripts.add('<script async type="text/javascript" src="${urls.base}/themes/tib/js/cookieConsent.js"></script>')}
</footer>

<#include "scripts.ftl">