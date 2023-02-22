<#-- $This file is distributed under the terms of the license in /doc/license.txt$ -->

<#--Breaking this out so this can be utilized by other pages such as the jsp advanced tools pages-->

<section id="search" role="region">
    <fieldset>
        <legend>${i18n().search_form}</legend>

            <div id="search-field">
                <input form="extended-search-form" id="filter_input_querytext" type="text" name="querytext" class="search-vivo" placeholder="${i18n().search_field_placeholder}"  value="${querytext!}" autocapitalize="off" />
                <input form="extended-search-form" type="submit" value="${i18n().search_button}" class="search">
            </div>
			<input form="extended-search-form" type="checkbox" id="type__17" value="type:http://ontology.tib.eu/gesah/Cultural_Object" name="filters_type_cultural_object" style="display:none;" checked="checked" class="selected-input">
    </fieldset>
</section>
