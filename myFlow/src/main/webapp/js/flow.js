(function($,w) {
	//表单的全部请求路径及请求回调函数的管理
	$.Url = (function(){
		var Url = function(data) {
			//根请求
			var _url = "";
			//获取全部菜单列表
			var getMenus = _url + "getMenus.do";
			
			this.getMenus = function(data) {
				if(data) {
					for(var key in data) {
						if("all" == key) {
							
						} else if("parent" == key) {
							$(".flow_left script").tmpl(data[key]).appendTo(".flow_left");
							$(".flow_left script").remove();
						} else {
							$(".flow_menu_tree div").remove();
							$(".flow_menu_tree script").tmpl(data[key]).appendTo(".flow_menu_tree");
						}
					}
				}
				return getMenus;
			};
		};
		
		return function(u){
			return new Url(u);	
		};
	})();
	
	//表单的全部属性的检验及取值、设置值的管理
	$.Data = (function($){
		var Data = function(type){
			this.type = type;
		};
		
		return function(type) {
			return new Data(type);
		};
	})($);
	
	//通用ajax请求方法
	(function($) {
		var _url = "";
		var _data = {};
		var _type = "";
		
		$.aw = function(url, data, type, foo) {
			_url = url;
			_data = data;
			_type = type;
			_request(foo);
		};
		
		//请求
		var _request = function(foo) {
			$.ajax({
				url: _url,
				type:_type,
				data:_data,
				async: false,
				success: foo
			});
		};
	})($);
})($,window)