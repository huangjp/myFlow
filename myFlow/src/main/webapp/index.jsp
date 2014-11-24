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
<title>就是这工作流，没错</title>
<link rel="stylesheet" type="text/css" href="css/flow.css"/>
<script src="js/jquery-2.1.1.js"></script>
<script src="js/jquery.tmpl.js"></script>
<script src="js/flow.js"></script>
<script src="js/dialog.js"></script>
</head>
<body onload="_manager.init();">
	<!-- 头部信息容器 -->
	<div onselectstart="return false;" class="flow_head">头部信息容器</div>
	<!-- 中部内容容器 -->
	<div class="flow_center dialog_center">
		<!-- 左侧菜单容器 draggable="true"  -->
		<div onselectstart="return false;" class="flow_left_dialog">
			<div class="dialog-title">左侧菜单容器</div>
			<script type="text/html">
			<div class="dialog-content">
<!-- 				<a href="#">{{= name}}</a> -->
				<img src="{{= icon}}" onclick="_manager.clickMenu(this);" data={{= id}} onerror="$(this).attr('onerror','').attr('src','');"/>
			</div>
 			</script>
			<div class="dialog-content">
				<img hegiht="120px;" onclick="alert('2354');" src="images/menu/add.png"/>
			</div>
		</div>
		<!-- 子菜单树列表容器 -->
		<div onselectstart="return false;" class="flow_menu_tree_dialog">
			<div class="dialog-title">子菜单树列表容器</div>
			<script type="text/html">
			<div class="dialog-content">
 				<a onclick="_manager.clickSubMenu('{{= path}}');">{{= name}}</a> 
<!--				<img src="{{= icon}}" onerror="$(this).attr('onerror','').attr('src','');"/> -->
			</div>
			</script>
		</div>
		<!-- 菜单内容视图容器 -->
		<div onselectstart="return false;" class="flow_content_dialog">
			<div class="dialog-title">菜单内容视图容器</div>
			<div class="flow_content"></div>
		</div>
		<!-- 右侧提示容器 -->
		<div onselectstart="return false;" class="flow_message_dialog">
			<div class="dialog-title">右侧提示容器</div>
		</div>
	</div>
	<!-- 底部状态显示栏 -->
	<div class="flow_bottom">底部状态显示栏</div>
</body>
</html>