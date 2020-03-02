<#import "tib-lib-home-page.ftl" as tlh>
<#-- Key Research areas -->
<div class="row explore">
    <#-- This section abandons the randomizer for faculty/depts in favour of a static approach so that institutions have better control over which ones they showcase. A script could still be deployed to explout the id's to change the departments with page load -->
    <div class="container">
        <#--The title row extends the full width -->
        <div class="col-md-12">
            <h2 class="h1">${i18n().explorer_key_areas}</h2>
            <p>${i18n().explorer_intro}</p>
        </div>

        <#-- The second row is split in two columns of 1:2 -->
        <div class="col-md-4">
            <a href="${urls.base}/search?querytext=${i18n().explorer_area_preservation}&classgroup=http%3A%2F%2Fvivoweb.org%2Fontology%23vitroClassGrouppublications" class="explore-panel" id="ph1">
                <span>${i18n().explorer_area_preservation}</span>
            </a>
        </div>
        <div class="col-md-8">
            <a href="${urls.base}/search?querytext=${i18n().explorer_area_visual_analytics}&classgroup=http%3A%2F%2Fvivoweb.org%2Fontology%23vitroClassGrouppublications" class="explore-panel" id="ph2">
                <span>${i18n().explorer_area_visual_analytics}</span>
            </a>
        </div>

        <#-- The third row is split in two columns of 1:1  -->
        <div class="col-md-6">
            <a href="${urls.base}/search?querytext=${i18n().explorer_area_open_science}&classgroup=http%3A%2F%2Fvivoweb.org%2Fontology%23vitroClassGrouppublications" class="explore-panel" id="ph3">
                <span>${i18n().explorer_area_open_science}</span>
            </a>
        </div>
        <div class="col-md-6">
            <a href="${urls.base}/search?querytext=${i18n().explorer_area_open_access}&classgroup=http%3A%2F%2Fvivoweb.org%2Fontology%23vitroClassGrouppublications" class="explore-panel" id="ph4">
                <span>${i18n().explorer_area_open_access}</span>
            </a>
        </div>
        <div class="col-md-12">
            <a class="pull-right" href="${urls.base}/individuallist?vclassId=http%3A%2F%2Fwww.w3.org%2F2004%2F02%2Fskos%2Fcore%23Concept">
                ${i18n().explorer_area_view_all}...
            </a>
        </div>
    </div>
</div>

<#-- About and list of classes-->
<!-- List of research classes: e.g., articles, books, collections, conference papers -->
<div class="row research-count">
    <div class="container">
        <div class="col-md-6">
            <h2 class="h1">${i18n().explorer_about_title}</h2>
            <div class="gap20"></div>
            <p>${i18n().explorer_about}</p>
            <div class="gap30"></div>
            <#--<button type="button" class="btn btn-default">
            <a href="${urls.base}/individual?uri=http://d-nb.info/gnd/1080328793">${i18n().sym9}</a>

            </button>
            -->
            <a class="" href="${urls.base}/about">	${i18n().explorer_about_more}</a>
        </div>
        <div class="col-md-6" id="research-classes">
            <@tlh.researchClasses />
        </div>
    </div>
</div>
