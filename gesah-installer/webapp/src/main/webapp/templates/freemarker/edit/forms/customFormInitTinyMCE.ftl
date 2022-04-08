    
<script type="text/javascript" src="/gesah/js/tiny_mce/tiny_mce.js?version=f10c"></script>
<script type="text/javascript" src="/gesah/js/tiny_mce/jquery.tinymce.js?version=f10c"></script>
 
<script type="text/javascript">
    var customFormConfig = {
    	tinyMCEData : {
                theme : "advanced",
                mode : "textareas",
                theme_advanced_buttons1 : "bold,italic,underline,separator,link,bullist,numlist,separator,sub,sup,charmap,separator,undo,redo,separator,code",
                theme_advanced_buttons2 : "",
                theme_advanced_buttons3 : "",
                theme_advanced_toolbar_location : "top",
                theme_advanced_toolbar_align : "left",
                theme_advanced_statusbar_location : "bottom",
                theme_advanced_path : false,
                theme_advanced_resizing : true,
                height : "200",
                width  : "75%",
                valid_elements : "a[href|name|title],br,p,i,em,cite,strong/b,u,sub,sup,ul,ol,li",
                fix_list_elements : true,
                fix_nesting : true,
                cleanup_on_startup : true,
                gecko_spellcheck : true,
                forced_root_block: false,
                plugins : "paste, autosave",
                paste_use_dialog : false,
                paste_auto_cleanup_on_paste : true,
                paste_convert_headers_to_strong : true,
                paste_strip_class_attributes : "all",
                paste_remove_spans : true,
                paste_remove_styles : true,
                paste_retain_style_properties : ""
        }
    };

    var initTinyMCE = {
    // Initial page setup
    onLoad: function() {
        this.mergeFromTemplate();
        this.initObjects();
        this.initEditor();
       
    },
    
    // Add variables from menupage template
    mergeFromTemplate: function() {
        $.extend(this, customFormConfig);
    },
    initObjects: function() {
    	this.wsywigFields = $(".useTinyMce");
    },
    // Create references to frequently used elements for convenience
    initEditor: function() {
    	initTinyMCE.wsywigFields.tinymce(initTinyMCE.tinyMCEData);
    	
    }
};
  $(document).ready(function() {
    initTinyMCE.onLoad();
	});
  
 
</script>
