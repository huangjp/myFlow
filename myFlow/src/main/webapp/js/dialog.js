Dialog = (function(){
	
	var main = {
			flowLeft : "flow_left_dialog",
			flowLeftSub : "flow_menu_tree_dialog",
			menuContent : "flow_content_dialog",
			message : "flow_message_dialog"
	}
	
	var isDialogTitle=false;
	
	var thisDialog = null;
	
    var down = function(e){
    	var name = e.target.className;
        if(name.indexOf('dialog-title')!=-1){
            isDialogTitle=true;
            var parent = $(e.target).parent().attr("class");
            for(var i in main) {
            	if(parent.indexOf(main[i])!=-1){
            		thisDialog = $("." + main[i])[0];
            	}
        	}
        }
    }
    
    var move = function(e){
        if(isDialogTitle){//只有点击Dialog Title的时候才能拖动
        	if(thisDialog) {
        		$(thisDialog).offset({top : e.clientY, left : e.clientX});
        	}
        }
    }
    
    function up(e){
        isDialogTitle=false;
        thisDialog = null;
    }
    
    document.addEventListener('mousedown',down);
    document.addEventListener('mousemove',move);
    document.addEventListener('mouseup',up);
})();