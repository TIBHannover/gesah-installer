<#include "individual-setup.ftl">
<#-- $This file is distributed under the terms of the license in LICENSE$ -->
<#import "lib-microformats.ftl" as mf>
<#assign iiifSlash="%5E" /> 

<#--Number of labels present-->
<#if !labelCount??>
    <#assign labelCount = 0 >
</#if>
<#--Number of available locales-->
<#if !localesCount??>
    <#assign localesCount = 1>
</#if>
<#--Number of distinct languages represented, with no language tag counting as a language, across labels-->
<#if !languageCount??>
    <#assign languageCount = 1>
</#if>

<#-- Default individual profile page template -->
<#--@dumpAll /-->
<#include "individual-adminPanel.ftl">
<@dataGetter uri = "http://gesah#digitalRepresentations" parameters = {"individualURI": individual.uri} />
<section id="individual-intro" class="vcard" role="region" <@mf.sectionSchema individual/>>
    <!-- start section individual-info -->
    <section id="individual-info" ${infoClass!} role="region">

        <#if individualProductExtensionPreHeader??>
            ${individualProductExtensionPreHeader}
        </#if>
       
        <div style="float: right;"><a href="${urls.base}/contact"><img src="${urls.images}/icons/mail.svg" title="${i18n().feedback_icon}" width="30"></a></div>
        <#if digitalRepresentations?has_content>
            <!-- Download button -->
            <div onclick="downloadImage()" style="float: right; margin-right: 5px;"><img title="${i18n().download_image}" src="${urls.images}/icons/down-arrow.svg?v=2" width="30"></div>
        </#if>
        <header style="float: left">
            <#if relatedSubject??>
                <h2>${relatedSubject.relatingPredicateDomainPublic} for ${relatedSubject.name}</h2>
                <p><a href="${relatedSubject.url}" title="${i18n().return_to(relatedSubject.name)}">&larr; ${i18n().return_to(relatedSubject.name)}</a></p>
            <#else>
                <h1 class="fn" itemprop="name">
                    <#-- Label -->
                    <@p.label individual editable labelCount localesCount languageCount/>

                    <#--  Most-specific types -->
                    <@p.mostSpecificTypes individual />
                    <span id="iconControlsVitro"><img id="uriIcon" title="${individual.uri}" class="middle" src="${urls.images}/individual/uriIcon.gif" alt="uri icon"/></span>
                </h1>
            </#if>
        </header>

        <#if individualProductExtension??>
        ${individualProductExtension}
        <#else>
    </section> <!-- individual-info -->
</section> <!-- individual-intro -->
</#if>

<#assign nameForOtherGroup = "${i18n().other}">

<!-- Property group menu or tabs -->
<#if digitalRepresentations?has_content>
    <div id="property-and-image">
        <div class="row">
            <div class="col-md-4" style="overflow: auto; overflow-x: hidden; height: 65vh">
                <#include "individual-property-group-notabs.ftl">
            </div>
            <div class="col-md-8">
                <div id="viewer" style="height: 65vh"></div>
		<div class="license-statement">
                   ${digitalRepresentations?first.rightlabel!""}
		</div>
            </div>
        </div>
    </div>

    <script type="text/javascript" async>
        
        function loadScript(src) {
            return new Promise(function (resolve, reject) {
                var el;
                el = document.createElement('script');
                el.src = src;
                el.onload = resolve;
                el.onerror = reject;
                document.head.appendChild(el);
            });
        }
        
        loadScript("${urls.base}/seadragon/openseadragon.js").then(successOpenSeaDragonLoad);
        
        var viewer;
 
        function successOpenSeaDragonLoad(result) {
			viewer = OpenSeadragon({
			    id: "viewer",
			    collectionMode:       true,
			    collectionRows:       3,
			    collectionTileSize:   1024,
			    collectionTileMargin: 56,
			    prefixUrl: "${urls.base}/seadragon/images/",
			    tileSources: [
			        <#list digitalRepresentations as digRep>"${urls.iiif}/iiif/2/${digRep["barcode"]}${iiifSlash}content${iiifSlash}streams${iiifSlash}${digRep["fileNum"]}/info.json"<#sep>, </#list>
			    ]
       		});
        }
		var imageDownloadUrls = [
            <#list digitalRepresentations as digRep>"${urls.iiif}/iiif/2/${digRep["barcode"]}${iiifSlash}content${iiifSlash}streams${iiifSlash}${digRep["fileNum"]}/full/full/0/default.tif"<#sep>, </#list>
        ];
        
        function downloadImage() {
            window.location = imageDownloadUrls[viewer.currentPage()];
        }
    </script>
<#else>
    <#include "individual-property-group-tabs.ftl">
</#if>

<#assign rdfUrl = individual.rdfUrl>

<#if rdfUrl??>
    <script>
        var individualRdfUrl = '${rdfUrl}';
    </script>
</#if>
<script>
    var i18nStringsUriRdf = {
        shareProfileUri: '${i18n().share_profile_uri}',
        viewRDFProfile: '${i18n().view_profile_in_rdf}',
        closeString: '${i18n().close}'
    };
    var i18nStrings = {
        displayLess: '${i18n().display_less}',
        displayMoreEllipsis: '${i18n().display_more_ellipsis}',
        showMoreContent: '${i18n().show_more_content}',
    };

</script>

${stylesheets.add('<link rel="stylesheet" href="${urls.base}/css/individual/individual.css" />',
'<link rel="stylesheet" type="text/css" href="${urls.base}/css/jquery_plugins/qtip/jquery.qtip.min.css" />')}

${headScripts.add('<script type="text/javascript" src="${urls.base}/js/jquery_plugins/qtip/jquery.qtip.min.js"></script>',
'<script type="text/javascript" src="${urls.base}/js/tiny_mce/tiny_mce.js"></script>')}

${scripts.add('<script type="text/javascript" src="${urls.base}/js/imageUpload/imageUploadUtils.js"></script>',
'<script type="text/javascript" src="${urls.base}/js/individual/moreLessController.js"></script>',
'<script type="text/javascript" src="${urls.base}/js/individual/individualUriRdf.js"></script>')}

<script type="text/javascript">
    i18n_confirmDelete = "${i18n().confirm_delete}";
</script>
