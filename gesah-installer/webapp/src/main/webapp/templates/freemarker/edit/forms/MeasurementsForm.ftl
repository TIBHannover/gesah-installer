<#include "customFormInit.ftl">

<h2>${titleVerb}&nbsp;${i18n().measurements_for} ${editConfiguration.subjectName}</h2>

<#include "MeasurementsFormErrors.ftl">

<section id="MeasurementsForm" role="region">
    <form id="MeasurementsForm" class="customForm noIE67" action="${submitUrl}"  role="add/edit title">
        <#-- Height Entry -->
        <#assign heightValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "height")/>
        <p>
            <label for="height">${i18n().height}</label>
            <input  size="30"  type="text" id="height" name="height" value="${heightValue}" role="input" />
        </p>

        <#-- Width Entry -->
        <#assign widthValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "width")/>
        <p>
            <label for="width">${i18n().width}</label>
            <input  size="30"  type="text" id="width" name="width" value="${widthValue}" role="input" />
        </p>

        <#-- Depth Entry -->
        <#assign depthValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "depth")/>
        <p>
            <label for="depth">${i18n().depth}</label>
            <input  size="30"  type="text" id="depth" name="depth" value="${depthValue}" role="input" />
        </p>

        <#-- Diameter Entry -->
        <#assign diameterValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "diameter")/>
        <p>
            <label for="diameter">${i18n().diameter}</label>
            <input  size="30"  type="text" id="diameter" name="diameter" value="${diameterValue}" role="input" />
        </p>

        <#-- Select Measurement Specification -->
        <#assign measurementsSpecificationValue = lvf.getFormFieldValue(editSubmission, editConfiguration, "measurementsSpecification")/>
        <p class="inline">
            <label for="measurementsSpecification">${i18n().measurement_specification_capitalized}</label>
            <select name="measurementsSpecification">
                <option value="" selected="selected">${i18n().select_one}</option>
                <#assign measurementsSpecificationOpts = editConfiguration.pageData.measurementsSpecification />
                <#list measurementsSpecificationOpts?keys as key>
                    <option value="${key}" <#if measurementsSpecificationValue = key>selected</#if>>${measurementsSpecificationOpts[key]}</option>
                </#list>
            </select>
        </p>

        <@formControls />
    </form>
</section>

<script type="text/javascript">
	var customFormData  = {
	    acUrl: '${urls.base}/autocomplete?tokenize=true&stem=true',
	    editMode: '${editMode}',
	    acTypes: {},
		multipleTypeNames: {},
	    baseHref: '${urls.base}/individual?uri=',
	    blankSentinel: '${blankSentinel}',
	    flagClearLabelForExisting: '${flagClearLabelForExisting}',
	    subjectName: '${editConfiguration.subjectName}'
	};
</script>

