<#-- $This file is distributed under the terms of the license in /doc/license.txt$ -->

<#--Breaking this out so this can be utilized by other pages such as the jsp advanced tools pages-->

<section id="search" role="region">
    <fieldset>
        <legend>${i18n().search_form}</legend>

        <form id="search-form" action="${urls.base}/search" name="search" role="search" accept-charset="UTF-8" method="GET"> 
            <div id="search-field">
                <input type="text" name="querytext" class="search-vivo" placeholder="${i18n().search_field_placeholder}"  value="${querytext!?html}" autocapitalize="off" />
                <input type="submit" value="${i18n().search_button}" class="search">
                <input type="radio" value="limit-to:cultural-object" name="filters_limit-to" form="search-form" style="display:none;" checked="checked">
            </div>
        </form>
    </fieldset>
</section>
        
