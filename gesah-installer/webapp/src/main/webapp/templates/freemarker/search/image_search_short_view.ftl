
<#assign iiifUrl="https://osl.tib.eu/gesah-iiif" />
<#assign iiifSlash="^" />

<a href="${individual.profileUrl}" title="individual profile">${individual.name}</a>

<#if personInfo?has_content>
  <div class="elementSearchDescription">
    <#list personInfo as person>
      <p>${person.label}</p>
    </#list>
  </div>
</#if>

<div class="imageThumbnails">
  <#list imageInfo as image>
    <#if image.fileNum?has_content && image.barcode?has_content>
      <@createImageThumbnail image.barcode image.fileNum individual.profileUrl />
    </#if>
  </#list> 
</div>

<#macro createImageThumbnail barcode fileName profileUrl>
  <div class="imageThumbnail">
     <a href="${profileUrl}" title="individual profile">
       <img src="${iiifUrl}/iiif/2/${barcode}${iiifSlash}content${iiifSlash}streams${iiifSlash}${fileName}/full/,100/0/default.jpg" />
     </a>
  </div>
</#macro>
