<#-- $This file is distributed under the terms of the license in /doc/license.txt$ -->
<footer role="contentinfo">
    <div class="row">
        <div class="col-md-12">
            <div class="container">
                <div class="row">
                    <div class="col-md-4">
                        <p class="footer-heading"><a target="_blank" href="contact">${i18n().contact_link_text}</a></p>
                        <p><a href="/about_page" title="${i18n().menu_about}">${i18n().menu_about}</a></p>
                        <p><a rel="me" href="https://baudigital.social/@sah">${i18n().follow_us_on_mastodon}</a></p>
                    </div>
                    <div class="col-md-4">
						<p class="footer-heading">${i18n().footer_legal_notices}</p>
   						<p><a href="${urls.base}/images_and_metadata" title="${i18n().menu_images_and_metadata}">${i18n().menu_images_and_metadata}</a></p>
   						<p><a href="${urls.base}/impressum" title="${i18n().menu_imprint}">${i18n().menu_imprint}</a></p>
						<p><a href="${urls.base}/dataprotection" title="${i18n().menu_data_protection}">${i18n().menu_data_protection}</a></p>
		                    <#if copyright??>
		                        <small>&copy;${copyright.year?c}
		                            <#if copyright.url??>
		                                <a href="${copyright.url}" title="${i18n().menu_copyright}">${copyright.text}</a>
		                            <#else>
		                            ${copyright.text}
		                            </#if>
		                           | <a class="terms" href="${urls.termsOfUse}" title="${i18n().menu_termuse}">${i18n().menu_termuse}</a></small> |
		                    </#if>
                    </div>
                    <div class="col-md-4">
   						<p class="footer-heading">${i18n().footer_technical_information}</p>
   						<p>
	                        <a href="http://vivoweb.org" target="_blank" title="${i18n().menu_powered} Vitro">${i18n().menu_powered} <strong>Vitro</strong></a>
	                        <#if user.hasRevisionInfoAccess>
	                            | ${i18n().menu_version} <a href="${version.moreInfoUrl}" title="${i18n().menu_version}">${version.label}</a>
	                        </#if>
                        </p>
                    </div>
                </div>
                <div class="row">
                    <div class="bottom-logos">
					    <div class="col-md-4">
			              <a href="https://tib.eu/" target="_blank"><img src="${urls.base}/themes/gesah/images/tib-logo.svg" alt="TIB – Leibniz-Informationszentrum Technik und Naturwissenschaften Universitätsbibliothek"></a>
			            </div>
			            <div class="col-md-4">
			              <a href="https://www.uni-hannover.de" target="_blank"> <img src="${urls.base}/themes/gesah/images/luh_logo.svg" alt="Leibniz Universität Hannover"></a>
			            </div>
			            <div class="col-md-4">
			              <a href="https://www.dfg.de" target="_blank"><img src="${urls.base}/themes/gesah/images/dfg_logo_schriftzug_weiss.svg" alt="Deutsche Forschungsgemeinschaft"></a>
					    </div>
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
    ${scripts.add('<script async type="text/javascript" src="${urls.base}/themes/gesah/js/cookieConsent.js"></script>')}
</footer>

<#include "scripts.ftl">
