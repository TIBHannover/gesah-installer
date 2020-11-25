<#include "customFormInit.ftl">

<h2>${titleVerb}&nbsp;${i18n().title_for} ${editConfiguration.subjectName}</h2>

<#include "E35TitleFormErrors.ftl">

<section id="E35TitleForm" role="region">
    <form id="E35TitleForm" class="customForm noIE67" action="${submitUrl}"  role="add/edit title">
        <#-- Title Entry -->
        <#assign titleValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "title")/>
        <p>
            <label for="title">${i18n().title} ${requiredHint}</label>
            <input  size="30"  type="text" id="title" name="title" value="${titleValue}" role="input" />
        </p>

        <#-- Select Title Type -->
        <#assign titleTypeValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "titleType")/>
        <p class="inline">
            <label for="titleType">${i18n().title_type_capitalized}</label>
            <select id="typeSelector" name="titleType">
                <option value="" selected="selected">${i18n().select_one}</option>
                <#assign titleTypeOpts = editConfiguration.pageData.titleType />
                <#list titleTypeOpts?keys as key>
                    <option value="${key}" <#if titleTypeValue = key>selected</#if>>${titleTypeOpts[key]}</option>
                </#list>
            </select>
        </p>

        <@formControls />
    </form>
</section>

