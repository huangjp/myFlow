<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>myDebug</title>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript">
	$(function() {
		$("#search_id").click(function() {
			var val = $(this).val();
			var object = $(this).parent().parent().find(val);
			alert(object);
		});
		$("#post_id").click(function() {
			var url = $(this).prev().val();
			if (url == "")
				return alert("请求路径为空");
			$.ajax({
				type : "post",
				contentType : "application/x-www-form-urlencoded; charset=utf-8",
				url : url,
				success : function(data) {
					$("#data_servlet_id").text(data);
				},
				error : function() {
					alert("请求异常");
				}
			})
		});
		$("#test_id").click(function() {
			var entity = $(this).prev("input").val();
			window.open("test.do?entityName="+entity);
		});
	});
</script>
</head>
<body>
	<div>
		<input type="text" value="" /> <input type="button" id="search_id"
			value="搜索" />
	</div>


	<div>
		<div style="background: orange;">
			<h1>debug</h1>
		</div>
		<div>${debug}</div>
		<div>
			<h3>
				<input type="text" value="Flow">
				<a href="" id="test_id">测试</a>
				<a href="flow.jsp">测试流程主页</a>
			</h3>
		</div>
		<div>
			<h3>
				<a href="flow/management/active.do?flowId=1">流程部属</a>
			</h3>
		</div>
		<div>
			模拟Post请求:
			<input type="text" value="" style="width: 800px; border: solid 1px gray; height: 30px; line-height: 30px" />
			<input id="post_id" type="button" value="请求" />
			<div id="data_servlet_id"></div>
		</div>
	</div>

	<div>
		<div style="background: orange;">
			<h1>flow模块基本数据的请求</h1>
		</div>
		<div>${flow}</div>
	</div>
	
	<div>
		<div style="background: orange;">
			<h1>flow模块基本数据的请求</h1>
		</div>
		<div>${business}</div>
	</div>
	
	<div>
		<div style="background: orange;">各种实体类和对应的属性字段字段注释</div>
		<c:forEach items="${entity}" var="map">
			<div style="background: teal;">
				实体类名：${map.key}
				<div style="background: white;">${map.value}</div>
			</div>
		</c:forEach>
	</div>
</body>
</html>