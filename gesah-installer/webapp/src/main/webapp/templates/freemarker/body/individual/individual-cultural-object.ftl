<#include "individual-setup.ftl">
<#import "lib-gesah-view.ftl" as lgv>
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

<#global digitalReps = digitalRepresentations>
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
       	<div onclick="exportLIDO()" style="float: right; margin-right: 5px;"><img alt="LIDO" width="30"></div>
       	<div onclick="exportRDF()" style="float: right; margin-right: 5px;"><img alt="RDF/XML" width="30"></div>
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
            <div class="col-md-4">
	            <div class="co-short-description">
		            <#assign shortDescription >
						<@lgv.printParticipations individual />
					</#assign>
					${shortDescription}
					<@dataGetter uri = "http://gesah-short-view#mostSpecificType" parameters = {"object": individual.uri} />
					<#if mostSpecificTypes?has_content>
						<#assign participantsDescription = shortDescription?replace('<[^>]+>','','r')?replace('\\r?\\n',' ','r')?replace('\\ +',' ','r')?trim />
						<#assign coType = (mostSpecificTypes?first).type!'http://ontology.tib.eu/gesah/Cultural_Object' />
						<#assign typeLabel = individual.mostSpecificTypes?first!'cultural object' />
						<#if participantsDescription?has_content>
							<#if title?contains('"') || title?contains("'")>
								<#assign descriptionTitle = title?html />
							<#else>
	            				<#assign descriptionTitle = "&quot;" + title?html + "&quot;" />
	    					</#if>
							<#if "http://ontology.tib.eu/gesah/Composite_Volume" == coType || "http://ontology.tib.eu/gesah/Photomechanical_Print" == coType >
								${metaTags.add("<meta tag=\"description\" content=\"" + i18n().meta_description_co_text_participants_2(typeLabel?html, descriptionTitle, participantsDescription?html) + "\" />")}
							<#elseif "http://ontology.tib.eu/gesah/Seal" == coType >
								${metaTags.add("<meta tag=\"description\" content=\"" + i18n().meta_description_co_text_participants_3(typeLabel?html, descriptionTitle, participantsDescription?html) + "\" />")}
							<#else>
								${metaTags.add("<meta tag=\"description\" content=\"" + i18n().meta_description_co_text_participants_1(typeLabel?html, descriptionTitle, participantsDescription?html) + "\" />")}
							</#if>
						</#if>
					</#if>
				</div>
				    <#if user.loggedIn>
				        <button type="button" id="create-co-button" onclick="showPartObjectForm()">Open creation form</button>
				        <script>
					    	function showPartObjectForm(){
					    		$(".partObjectCreation").show();
					    		$("#create-co-button").hide();
					    	}
					    	function hidePartObjectForm(){
					    		$(".partObjectCreation").hide();
					    		$("#create-co-button").show();
					    	}
					    	function createObjects(){
					    		let formData = $('#part-creation').serializeArray();
					    		let type = formData.filter(obj => { return obj.name === "coType" });
					    		if (!type ||!type[0] || !type[0].value){
					    			alert("Please select cultural object type.");
					    			return;
					    		}
					    		let images = formData.filter(obj => { return obj.name.startsWith('image-')&& obj.value });
					    		if (images.length === 0){
					    			alert("Please assign at least one image.");
					    			return;
					    		}
					    		let jsonData = {};
					    		jsonData.type = type[0].value;
					    		jsonData.parent = '${individual.uri?js_string}';
					    		
					    		let activities = [];
					    		let formActivities = formData.filter(obj => { return obj.name.startsWith('activity-') });
					    		formActivities.forEach((element) => activities.push(element.value));
					    		jsonData.activities = activities;
					    		
					    		let genericTerms = [];
					    		let formGenericTerms = formData.filter(obj => { return obj.name.startsWith('generic_term-') });
					    		formGenericTerms.forEach((element) => genericTerms.push(element.value));
					    		jsonData.genericTerms = genericTerms;
					    		
					    		let narrowerTerms = [];
					    		let formNarrowerTerms = formData.filter(obj => { return obj.name.startsWith('narrower_term-') });
					    		formNarrowerTerms.forEach((element) => narrowerTerms.push(element.value));
					    		jsonData.narrowerTerms = narrowerTerms;
					    		
					    		let geographicAssignments = [];
					    		let formGeorgraphicAssignments = formData.filter(obj => { return obj.name.startsWith('geographic_assignment-') });
					    		formGeorgraphicAssignments.forEach((element) => geographicAssignments.push(element.value));
					    		jsonData.geographicAssignments = geographicAssignments;
					    		
					    		let stylisticAssignments = [];
					    		let formStylisticAssignments = formData.filter(obj => { return obj.name.startsWith('stylistic-') });
					    		formStylisticAssignments.forEach((element) => stylisticAssignments.push(element.value));
					    		jsonData.stylisticAssignments = stylisticAssignments;
					    		
					    		
					    		let depictionTypes = [];
					    		let formDepictionTypes = formData.filter(obj => { return obj.name.startsWith('depiction-type-') });
					    		formDepictionTypes.forEach((element) => depictionTypes.push(element.value));
					    		jsonData.depictionTypes = depictionTypes;
					    		
					    		let markDesignations = [];
					    		let formMarkDesignations = formData.filter(obj => { return obj.name.startsWith('mark-designation-') });
					    		formMarkDesignations.forEach((element) => markDesignations.push(element.value));
					    		jsonData.markDesignations = markDesignations;
					    		
					    		let workIndexEntries = [];
					    		let formWorkIndexEntries = formData.filter(obj => { return obj.name.startsWith('work_index_entry-') });
					    		formWorkIndexEntries.forEach((element) => workIndexEntries.push(element.value));
					    		jsonData.workIndexEntries = workIndexEntries;
					    		
					    		let watermarks = [];
					    		let formWatermarks = formData.filter(obj => { return obj.name.startsWith('watermark-') });
					    		formWatermarks.forEach((element) => watermarks.push(element.value));
					    		jsonData.watermarks = watermarks;
					    		
					    		let descriptions = [];
					    		let formDescriptions = formData.filter(obj => { return obj.name.startsWith('description') });
					    		formDescriptions.forEach((element) => descriptions.push(element.value));
					    		jsonData.descriptions = descriptions;
					    		
					    		let authorInitials = [];
					    		let formAuthorInitials = formData.filter(obj => { return obj.name.startsWith('record-author-initials') });
					    		formAuthorInitials.forEach((element) => authorInitials.push(element.value));
					    		jsonData.authorInitials = authorInitials;
					    		
					    		let secondaryLiterature = [];
					    		let formSecondaryLiterature = formData.filter(obj => { return obj.name.startsWith('secondary-literature') });
					    		formSecondaryLiterature.forEach((element) => secondaryLiterature.push(element.value));
					    		jsonData.secondaryLiterature = secondaryLiterature;
					    		
					    		let culturalObjects = [];
					    		images.forEach((element) => 
					    			{
					    				let id = element.value;
					    			    let cos = culturalObjects.filter(obj => { return obj.id === id });
					    				let co = {};
					    				let co_images = [];
					    				if (cos.length > 0){
					    					co = cos[0];
					    					co_images = co.images;
					    				}
					    				let image_uri = element.name.replace('image-', '');
					    				co_images.push(image_uri);
					    				co.images = co_images;
					    				let mainImageEntries = formData.filter(obj => { return obj.name.startsWith("main-image-" + image_uri) });
					    				if (mainImageEntries.length > 0){
					    					co.mainImage = image_uri;
					    				}
					    				if (cos.length === 0){
					    					co.id = id;
					    					culturalObjects.push(co);
					    				}
					    			});
								jsonData.culturalObjects = culturalObjects;

					    		var body = JSON.stringify( jsonData );
					    		fetch("/gesah/api/rest/1/cultural_object/copy",
								{
								    method: "POST",
									headers: {
										'Accept': '*/*',
										'Content-Type': 'application/json;charset=UTF-8'
									},
								    body: body
								}).then(function(res){ return res.json(); })
					            .then(function(data){ 
					                let objects = data.objects;
					                for (let i = 0; i < objects.length; i++) {
					                  let objectUri = objects[i].value ;
					                  window.open("${urls.base}/entity?uri=" + encodeURIComponent(objectUri), '_blank');
					                } 
					            })
					    	}
					    </script>
		                <div class="partObjectCreation" style="display:none;">
		                    <@dataGetter uri = "http://gesah#coTypes" />
		            		<form id="part-creation" name="part-creation" action="${urls.base}/search">
		            		    <label for="coType">Select CO type</label>
				        		<select name="coType" form="part-creation">
						            <option selected value="">not selected</option>
						            <#list coTypes as coType>
							          <option value="${coType.co_type}">${coType.label}</option>
							        </#list>              
						        </select>
						        <#-- <button type="button" id="create-co-abort" onclick="hidePartObjectForm()">Close form</button> -->
						        <button type="button" id="create-co-submit" onclick="createObjects()">Create objects</button>
		            		</form>
		            	</div>
	                </#if>
	            <div style="overflow: auto; overflow-x: hidden; height: 65vh">
	                <#include "individual-property-group-notabs.ftl">
            	</div>
            </div>
            
            <div class="col-md-8">
                <div id="viewer" style="height: 65vh"></div>
		<div class="license-statement">
		  ${digitalRepresentations?first.rightlabel!""}
		</div>
            </div>
        </div>
    </div>
<!--
    collectionMode:       true,
    collectionRows:       3,
    collectionTileSize:   1024,
    collectionTileMargin: 56,
-->

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
        
        loadScript("${urls.base}/seadragon/openseadragon.min.js").then(successOpenSeaDragonLoad);
        
        var viewer;
 
        function successOpenSeaDragonLoad(result) {
          viewer = OpenSeadragon({
              id: "viewer",
              prefixUrl: "${urls.base}/seadragon/images/",
              sequenceMode:         true,
              showReferenceStrip:   true,
              showRotationControl: true,
              // Enable touch rotation on tactile devices
              gestureSettingsTouch: {
                  pinchRotate: true
              },
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

        function exportLIDO() {
			var payload = {
			    resource_id: "${individual.uri}"
			};
			var body = JSON.stringify( payload );
			fetch("${urls.base}/api/rest/1/cultural_object/lido_export",
			{
			    method: "POST",
				headers: {
					'Accept': '*/*',
					'Content-Type': 'application/json;charset=UTF-8'
				},
			    body: body
			})
			.then(function(res){ return res.json(); })
            .then(function(data){ var dialog = document.createElement("dialog");
                document.body.appendChild(dialog)
                var text = document.createTextNode(data.lido);
                dialog.style.setProperty('white-space', 'pre');
                dialog.appendChild(text);
                dialog.showModal();
            })

        }

        function exportRDF() {
			var payload = {
			    resource_id: "${individual.uri}"
			};
			var body = JSON.stringify( payload );
			fetch("${urls.base}/api/rest/1/cultural_object/graph_export",
			{
			    method: "POST",
				headers: {
					'Accept': '*/*',
					'Content-Type': 'application/json;charset=UTF-8'
				},
			    body: body
			})
			.then(function(res){ return res.json(); })
            .then(function(data){ var dialog = document.createElement("dialog");
                document.body.appendChild(dialog)
                var text = document.createTextNode(data.cultural_object_graph);
                dialog.style.setProperty('white-space', 'pre');
                dialog.appendChild(text);
                dialog.showModal();
            })

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

<@lgv.addImageMetadata digitalRepresentations />

${stylesheets.add('<link rel="stylesheet" href="${urls.base}/css/individual/individual.css" />',
'<link rel="stylesheet" href="${urls.base}/css/jquery_plugins/qtip/jquery.qtip.min.css" media="print" onload="this.media=\'all\'; this.onload=null;"/>')}

${headScripts.add('<script type="text/javascript" src="${urls.base}/js/jquery_plugins/qtip/jquery.qtip.min.js"></script>')}
<#if user.loggedIn>
  ${headScripts.add('<script type="text/javascript" src="${urls.base}/js/tiny_mce/tiny_mce.js"></script>')}
</#if>

${scripts.add('<script type="text/javascript" src="${urls.base}/js/imageUpload/imageUploadUtils.js" async></script>',
'<script type="text/javascript" src="${urls.base}/js/individual/moreLessController.js" async></script>',
'<script type="text/javascript" src="${urls.base}/js/individual/individualUriRdf.js" async></script>')}

<script type="text/javascript">
    i18n_confirmDelete = "${i18n().confirm_delete}";
</script>
