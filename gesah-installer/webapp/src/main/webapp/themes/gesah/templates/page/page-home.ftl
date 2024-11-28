<#-- $This file is distributed under the terms of the license in /doc/license.txt$  -->

<@widget name="login" include="assets" />

${metaTags.add("<meta tag=\"description\" content=\"Digital collection of prints and drawings collected by architect and building historian Albrecht Haupt.\" />")}
<#-- 
        With release 1.6, the home page no longer uses the "browse by" class group/classes display. 
        If you prefer to use the "browse by" display, replace the import statement below with the
        following include statement:
        
            <#include "browse-classgroups.ftl">
            
        Also ensure that the homePage.geoFocusMaps flag in the runtime.properties file is commented
        out.
-->
<#import "lib-home-page.ftl" as lh>
<!DOCTYPE html>
<html lang="en">
    <head>
        <#include "head.ftl">
        <#if geoFocusMapsEnabled >
            <#include "geoFocusMapScripts.ftl">
        </#if>
        <style>
            #nav.affix-top section#search {
                display: none;
            }
        </style>

    </head>
    
    <body class="${bodyClasses!}" onload="${bodyOnload!}">
        <#-- supplies the faculty count to the js function that generates a random row number for the search query -->
        <header id="branding" role="banner">
            <#include "identity.ftl">
        </header>
        <#include "developer.ftl">
        <#include "menu.ftl">
        <#include "message.ftl">
        <div class="row hero">
            <div class="theme-showcase">
                <div class="col-md-12">
                    <div class="container" role="main">
                           <div class="home-page-intro">
                               <h1 class="home-page-heading">${i18n().home_page_intro_header}</h1>
                               <p>${i18n().home_page_intro_text}</p>
                           </div>
                                  
                        <div class="home-page-heading2">${i18n().search_collection_text}</div>
                            <form id="search-homepage" action="${urls.base}/search" name="search-home" role="search" method="GET" class="form-horizontal">
                                <fieldset>
                                    <div class="form-group">
                                         <div id="search-line-container">
                                             <div class="input-group home-page-input-group">
                                                 <input type="text" name="querytext" class="form-control" value="" placeholder="${i18n().search_field_placeholder}" autocapitalize="off" />
                                                 <input type="radio" value="limit-to:cultural-object" name="filters_limit-to" style="display:none;" checked="checked">
                                                 <span class="input-group-btn">
                                                     <button class="btn btn-default" type="submit">
                                                         <span class="icon-search">${i18n().search_button}</span>
                                                     </button>
                                                 </span>
                                             </div>
                                            <#assign typeFilter = "?filters_limit-to=limit-to%3Acultural-object" />
                                              <a class="home-page-help-icon" href="${urls.base}/search${typeFilter}"><img class="search-icon search-icon-settings" src="images/icons/settings.svg"></a>
                                              <div class="home-page-help-text">${i18n().search_filters_help_text}</div>
                                             <a class="home-page-help-icon"><img class="search-icon search-icon-question"  src="images/icons/question.svg"></a>
                                             <div class="home-page-help-text">${i18n().search_help_text}</div>
                                         </div>
                                     </div>
                                 </fieldset>
                             </form>
                         </div>
                     </div>
            <div class="col-md-12">
                <div class="container">
                    <div class="home-page-heading2">${i18n().browse_collections_text}</div>
                    <div class="more-objects-image-container jumbotron">
                        <div class="category_facet">
                            <a class="facet_link" target="_blank" href="${urls.base}/search?filters_limit-to=limit-to%3Acultural-object&filters_part-of-collection__2=part-of-collection%3Ahttps%3A%2F%2Fsah.tib.eu%2Findividual%2Fn34774">
                                <img src="${urls.iiif}/iiif/3/89140529713%5Econtent%5Estreams%5Emaster_89140529713_00000002.tif/pct:37,7,60,93/,320/0/default.jpg">
                                <div class="category_facet_label">${i18n().single_sheets_img_text}</div>
                                <div class="category_facet_description">${i18n().single_sheets_collection_descripton}</div>
                            </a>
                        </div>
                        <div class="category_facet">
                            <a class="facet_link" target="_blank" href="${urls.base}/search?filters_limit-to=limit-to%3Acultural-object&filters_part-of-collection__1=part-of-collection%3Ahttps%3A%2F%2Fsah.tib.eu%2Findividual%2Fn34147">
                                <img src="${urls.iiif}/iiif/3/antivaaui_1853079340^content^streams^master_00000001.tif/pct:20.,5,70,90/,320/0/default.jpg">
                                <div class="category_facet_label">${i18n().composite_volumes_img_text}</div>
                                <div class="category_facet_description">${i18n().composite_volumes_description}</div>
                            </a>
                        </div>
                        <div class="category_facet">
                            <a class="facet_link" target="_blank" href="${urls.base}/search?filters_limit-to=limit-to%3Acultural-object&filters_part-of-collection__3=part-of-collection%3Ahttps%3A%2F%2Fsah.tib.eu%2Findividual%2Fn48780">
                                <img src="${urls.iiif}/iiif/2/travel_sketches%5Econtent%5Estreams%5EHaupt-12Re-53.pdf-001-000.tif/pct:40,2,55,35/,300/0/default.jpg">
                                <div class="category_facet_label">${i18n().travel_sketches_img_text}</div>
                                <div class="category_facet_description">${i18n().travel_sketches_description}</div>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-12">
                <div class="container">
                  <div class="home-page-heading2">${i18n().explore_collection_text}</div>
                    <div id="searchFacets">
                      <div class="category_facet">
                        <a class="facet_link" href="/gesah/search?filters=main_filter:drawing&amp;filters_limit-to=limit-to%3Acultural-object">
                          <img alt="Drawing" src="${urls.iiif}/iiif/2/89140499261%5Econtent%5Estreams%5Emaster_89140499261_00000001.tif/pct:22,20,47,40/,310/0/default.jpg">
                          <div class="category_facet_label">${i18n().drawing_img_text}</div>
                          <div class="category_facet_description">${i18n().drawing_img_description}</div>
                        </a>
                      </div>
                      <div class="category_facet">
                        <a class="facet_link" href="/gesah/search?filters=main_filter:print&amp;filters_limit-to=limit-to%3Acultural-object">
                          <img alt="Print" src="${urls.iiif}/iiif/2/89140497781%5Econtent%5Estreams%5Emaster_89140497781_00000002.tif/pct:15,5,70,85/,320/0/default.jpg">
                          <div class="category_facet_label">${i18n().print_img_text}</div>
                          <div class="category_facet_description">${i18n().print_img_description}</div>
                        </a>
                      </div>
                      <div class="category_facet">
                        <a class="facet_link" href="/gesah/search?filters=main_filter:architecture&amp;filters_limit-to=limit-to%3Acultural-object">
                          <img alt="Architecture" src="${urls.iiif}/iiif/2/89140495568%5Econtent%5Estreams%5Emaster_89140495568_00000002.tif/pct:25,25,50,50/480,/0/default.jpg">
                          <div class="category_facet_label">${i18n().architecture_img_text}</div>
                          <div class="category_facet_description">${i18n().architecture_img_description}</div>
                        </a>
                      </div>
                      <div class="category_facet">
                        <a class="facet_link" href="/gesah/search?filters=main_filter:ornament&amp;filters_limit-to=limit-to%3Acultural-object">
                          <img alt="Ornament" src="${urls.iiif}/iiif/2/89140519300%5Econtent%5Estreams%5Emaster_00000002.tif/pct:25,25,50,50/,300/0/default.jpg">
                          <div class="category_facet_label">${i18n().ornament_img_text}</div>
                          <div class="category_facet_description">${i18n().ornament_img_description}</div>
                        </a>
                      </div>
                      <div class="category_facet">
                        <a class="facet_link" href="/gesah/search?filters=main_filter:garden&amp;filters_limit-to=limit-to%3Acultural-object">
                          <img alt="Garden" src="${urls.iiif}/iiif/2/89140510370%5Econtent%5Estreams%5Emaster_00000002.tif/pct:30,25,40,50/,300/0/default.jpg">
                          <div class="category_facet_label">${i18n().garden_img_text}</div>
                          <div class="category_facet_description">${i18n().garden_img_description}</div>
                        </a>
                      </div>
                      <div class="category_facet">
                        <a class="facet_link" href="/gesah/search?filters=main_filter:scenography&amp;filters_limit-to=limit-to%3Acultural-object">
                          <img alt="Scenography" src="${urls.iiif}/iiif/2/89140531114%5Econtent%5Estreams%5Emaster_89140531114_00000002.tif/pct:15,15,70,70/,300/0/default.jpg">
                          <div class="category_facet_label">${i18n().scenography_img_text}</div>
                          <div class="category_facet_description">${i18n().scenography_img_description}</div>
                        </a>
                      </div>
                   </div>
                </div>
            </div>
            <div class="col-md-12">
                <div class="container">
                    <div class="home-page-heading2">${i18n().more_objects_in_albrecht_haupt_collection}</div>
                    <div class="more-objects-image-container jumbotron">
                        <div class="external-link-container">
                            <a class="external-link" target="_blank" href="https://www.tib.eu/de/suchen?tx_tibsearch_search[action]=search&tx_tibsearch_search[cnt]=20&tx_tibsearch_search[controller]=Search&tx_tibsearch_search[pg]=1&tx_tibsearch_search[query]=prefix%3Atibkat shelfmark%3A*Haupt\ * -shelfmark%3A*Haupt\ 16%3F%3F* -shelfmark%3A*Haupt\ 17%3F%3F* -shelfmark%3A*Haupt\ 18%3F%3F* -shelfmark%3A*Haupt\ Hs* -shelfmark%3AArchiv\ *">
                                <div>${i18n().haupt_library_img_text}<img class="external-link-img" src="/gesah/images/icons/external-link.svg"></div>
                            </a>
                        </div>
                        <div class="external-link-container">
                            <a class="external-link" target="_blank" href="https://goobi.tib.eu/viewer/cms/19/">
                                <div>${i18n().haupt_special_collection_img_text}<img class="external-link-img" src="/gesah/images/icons/external-link.svg"></div>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
         </div>
     </div>
        <#if geoFocusMapsEnabled >
            <div class="row geo-focus">
                <div class="container">
                    <div class="col-md-12">
                        <!-- Map display of researchers' areas of geographic focus. Must be enabled in runtime.properties -->
                        <@lh.geographicFocusHtml />
                    </div>
                </div>
            </div>
        </#if>

        <#include "footer.ftl">
    <script>
        var i18nStrings = {
            researcherString: '${i18n().researcher}',
            researchersString: '${i18n().researchers}',
            currentlyNoResearchers: '${i18n().currently_no_researchers}',
            countriesAndRegions: '${i18n().countries_and_regions}',
            countriesString: '${i18n().countries}',
            regionsString: '${i18n().regions}',
            statesString: '${i18n().map_states_string}',
            stateString: '${i18n().map_state_string}',
            statewideLocations: '${i18n().statewide_locations}',
            researchersInString: '${i18n().researchers_in}',
            inString: '${i18n().in}',
            noFacultyFound: '${i18n().no_faculty_found}',
            placeholderImage: '${i18n().placeholder_image}',
            viewAllFaculty: '${i18n().view_all_faculty}',
            viewAllString: '${i18n().view_all}',
            viewAllDepartments: '${i18n().view_all_departments}',
            noDepartmentsFound: '${i18n().no_departments_found}'
        };
        // set the 'limmit search' text and alignment
        if  ( $('input.search-homepage').css('text-align') == "right" ) {       
             $('input.search-homepage').attr("value","${i18n().limit_search} \u2192");
        }  
    </script>
    </body>
</html>
