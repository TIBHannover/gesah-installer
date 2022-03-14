
<a href="${individual.profileUrl}" title="individual profile">${individual.name}</a>
<div class=imageThumbnails>
  <#list imageInfo as image>
    <@createImageThumbnail image.barcode image.fileNum individual.profileUrl />
  </#list> 
</div>

<#macro createImageThumbnail barcode fileName profileUrl>
  <div class="imageThumbnail">
     <#-- <img src="${barcode}-${filename}-${iiifUrl}"> -->
     <#assign iiifUrl="https://osl.tib.eu/gesah-iiif" />
     <#assign iiifSlash="^" />
     <a href="${profileUrl}" title="individual profile">
       <img src="${iiifUrl}/iiif/2/${barcode}${iiifSlash}content${iiifSlash}streams${iiifSlash}${fileName}/full/,100/0/default.jpg" />
     </a>
  </div>
</#macro>
