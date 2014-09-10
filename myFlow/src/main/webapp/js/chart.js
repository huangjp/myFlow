(function($,window) {
	
	var pages = 3;//最初先取5页信息
	var rows = 10;//每页取12行
	var startRow = 0;
	var data = {
			startRow : startRow,
			rows : pages * rows	
	};

	var orgPage = null;//静态分页对象，数据会累加
	var userPage = null;//静态分页对象，数据会累加
	
	//初始化报表数据
	var init = function(year) {
		$.aw(Url().mscolumn(), Data(year,"","chartCallback",""), "POST", Url().mscolumn);
	};
	
	window.chartInit = init;

	//子报表函数
	var callback = function(year,month,type) {
		$.aw(Url().callback(), Data(year,month,"",type), "POST", Url().callback);
	};
	
	window.chartCallback = callback;
	
	//公司列表函数
	var orgList = function(d) {
		var myData = {};
		if(d) {
			myData = d;
		} else {
			myData = data;
		}
		$.aw(Url().orgList(), myData, "POST", Url().orgList);
	};
	
	window.reportOrgList = orgList;
	
	//列表列表函数
	var userList = function(d) {
		var myData = {};
		if(d) {
			myData = d;
		} else {
			myData = data;
		}
		$.aw(Url().userList(), myData, "POST", Url().userList);
	};
	
	window.reportUserList = userList;

	//表单的全部请求路径及请求回调函数的管理
	var Url = (function($,win){
		var Url = function(data) {
			//根请求
			var _url = "statis/chart";
			//mscolumn格式数据
			var mscolumn = _url + "/registChartsByMscolumn.json";
			//column格式中的回调函数
			var callback = _url + "/registCallback.json";
			//注册公司信息列表
			var orgList = _url + "/getOrganizations.json";
			//注册用户信息列表
			var userList = _url + "/getUsers.json";
			
			this.mscolumn = function(data) {
				if(data) {
					FusionCharts.ready(function(){
						var revenueChart = new FusionCharts(data.fusionCharts);
						revenueChart.render("chart_ec_container");
					}); 
				}
				return mscolumn;
			};
			
			this.callback = function(data) {
				if(data) {
					FusionCharts.ready(function(){
						var revenueChart = new FusionCharts(data.fusionCharts);
						revenueChart.render("chart_ec_call_container");
					}); 
				}
				return callback;
			};
			
			this.orgList = function(data) {
				if(data) {
					var columns = ['姓名','用户数','联系人','联系电话','注册时间'];
					if(orgPage == null) {
						orgPage = $.page(data.count,rows,data.list,reportOrgList,pages);
					} else {
						for(var i = 0; i < data.list.length; i++) {
							orgPage.list.push(data.list[i]);
						}
					}
					$.report(orgPage,$("#chart_ec_organization_container"),columns);
				}
				return orgList;
			};
			
			this.userList = function(data) {
				if(data) {
					var columns = ['姓名','联系电话','移动电话','邮箱','所属公司','注册时间','是否管理员'];
					if(userPage == null) {
						userPage = $.page(data.count,rows,data.list,reportUserList,pages);
					} else {
						for(var i = 0; i < data.list.length; i++) {
							userPage.list.push(data.list[i]);
						}
					}
					$.report(userPage,$("#chart_ec_users_container"),columns);
				}
				return userList;
			};
		};
		
		return function(u){
			return new Url(u);	
		};
	})($,window);

	//表单的全部属性的检验及取值、设置值的管理
	var Data = (function($){
		var Data = function(year, month, callback, type){
			this.year = year;
			this.month = month;
			this.callback = callback;
			this.type = type;
		};
		
		return function(year, month, callback, type) {
			return new Data(year, month, callback, type);
		};
	})($);

	//添加请求方法
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
})($,window);