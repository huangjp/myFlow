/**
 * 页面初始化对象，该对象不可NEW，避免内存消耗
 */
var _manager = (function() {
	
	var Manager = function() {
		
		//初始化
		this.init = function() {
			_manager.aw(_manager.Url().getMenus(), _manager.Data("all"), "POST", _manager.Url().getMenus);
		};
		
		//表单的全部请求路径及请求回调函数的管理
		this.Url = (function(){
			var Url = function(data) {
				//根请求
				var _url = "";
				//获取全部菜单列表
				var getMenus = _url + "getMenus.do";
				
				this.getMenus = function(data) {
					if(data) {
						for(var key in data) {
							if("all" == key) {
								$(".flow_left_dialog>script").tmpl(data[key]).insertBefore(".flow_left_dialog>script");
							} else if("parent" == key) {
								$(".flow_left_dialog>script").tmpl(data[key]).insertBefore(".flow_left_dialog>script");
							} else {
								$(".flow_menu_tree_dialog>div.dialog-content").remove();
								$(".flow_menu_tree_dialog>script").tmpl(data[key]).insertBefore(".flow_menu_tree_dialog>script");
							}
						}
					}
					return getMenus;
				};
			};
			
			return function(){
				return new Url();	
			};
		})();
		
		//表单的全部属性的检验及取值、设置值的管理
		this.Data = (function($){
			var Data = function(type){
				this.type = type;
			};
			
			return function(type) {
				return new Data(type);
			};
		})($);
		
		this.clickMenu = function(obj) {
			var data = $(obj).attr("data");
			_manager.aw(_manager.Url().getMenus(), _manager.Data(data), "POST", _manager.Url().getMenus);
		}
		
		this.clickSubMenu = function(path) {
			var html = "<iframe src="+path+"></iframe>";
			$(".flow_content_dialog>div.flow_content").html(html);
		}
		
//		function createXMLHttpRequest() {
//			var xmlHttp;
//			if (window.XMLHttpRequest) {
//				xmlHttp = new XMLHttpRequest();
//			}
//			if (window.ActiveXObject) {
//				xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
//			}
//			return xmlHttp;
//		}
		
		//通用ajax请求方法
		this.aw = function(url, data, type, foo) {
			
//			var req = createXMLHttpRequest();
//			req.open("POST", url, true);
//			req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
//			req.send("type=all");
//			req.responseType = "json";
//			req.onreadystatechange = function() {
//				if (req.readyState == 4 && req.status == 200) {
//					foo(req.response);
//				}
//			};
			
			
			
			$.ajax({
				url: url,
				type:type,
				data:data,
				async: false,
				success: foo
			});
		};
	}
	return new Manager();
})();