<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%= basePath%>"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>用户、角色、公司、部门配置</title>
<link rel="stylesheet" type="text/css" href="css/flow.css"/>
<script src="js/jquery-2.1.1.js"></script>
<script src="js/jquery.tmpl.js"></script>
<script src="js/flow.js"></script>
<script type="text/javascript">
	$(function() {
		$.aw($.Url().getMenus(), $.Data("parent"), "POST", $.Url().getMenus);
	});
	function clickSubMenu(obj) {
		var data = $(obj).attr("data");
		$.aw($.Url().getMenus(), $.Data(data), "POST", $.Url().getMenus);
	}
</script>
</head>
<body>
	<!-- 头部信息容器 -->
	<div class="flow_head">头部信息容器</div>
	<!-- 中部内容容器 -->
	<div class="flow_center">
		<!-- 左侧菜单容器 -->
		<div class="flow_left">
			<script type="text/html">
			<div>
<!-- 				<a href="#">{{= name}}</a> -->
				<img src="{{= icon}}" onclick="clickSubMenu(this);" data={{= id}} onerror="$(this).attr('onerror','').attr('src','');"/>
			</div>
			</script>
		</div>
		<!-- 子菜单树列表容器 -->
		<div class="flow_menu_tree">
			<script type="text/html">
			<div>
 				<a href="{{= path}}">{{= name}}</a> 
<!--				<img src="{{= icon}}" onerror="$(this).attr('onerror','').attr('src','');"/> -->
			</div>
			</script>
		</div>
		<!-- 菜单内容视图容器 -->
		<div class="flow_content">菜单内容视图容器</div>
		<!-- 右侧提示容器 -->
		<div class="flow_message">右侧提示容器</div>
	</div>
	<!-- 底部状态显示栏 -->
	<div class="flow_bottom">底部状态显示栏</div>
</body>
</html>