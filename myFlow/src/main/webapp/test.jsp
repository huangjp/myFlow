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
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>增、删、改、查等</title>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript">
	$(function() {
		$("#main_id").click(function() {
			var url = $("#main_value").val();
			main(url,this);
		});
		$("#showList_id").click(function() {
			var url = $("#showList_value").val();
			$.ajax({
				type:"post",
				contentType:"application/x-www-form-urlencoded; charset=utf-8", 
				url:url,
				data:"currentPage=" + 1 + "&pageSize=" + 10,
				success:function(data) {
					showList(data);
				}
			});
		});
		$("#showFlowList_id").click(function() {
			var mbrId = $("#member_id").val();
			var url = $(this).attr("action");
			$.ajax({
				type:"post",
				url:url,
				data:"mebId="+mbrId+"&currentPage=" + 1 + "&pageSize=" + 10,
				success:function(data) {
					showList(data);
				}
			});
		});
		generateFormFields(JSON.parse('${fields}'));
	});
	function main(url,object) {
		var data = "";
		var inputList = $(object).parent().find("input[name]");
		for(var i = 0; i < inputList.length; i++) {
			var o = inputList[i];
			var name = $(o).attr("name");
			var val = $(o).val();if(val != "") {
				data += ("&" + name + "=" + val);
			}
		}
		data = data.substring(data.indexOf("&") + 1, data.length);
		$.ajax({
			type:"post",
			url:url,
			data:data + "&flowId=3",
			success:function(data) {
				alert("增删改查成功，返回值为："+data);
				giveValue(data);
			},
			error:function() {
				alert("增删改查失败");
			}
		})
	}
	function giveValue(data) {
		if(data == null) return false;
		data = JSON.parse(data);
		if(data.length < 2) return false;
		for(var key in data) {
			var val = data[key];
			if(val != "" || val != null) {
				var o = $("form").find("input[name='"+key+"']");
				if(o != undefined && o != null) {o.val(val);}
			}
		}
	}
	function generateFormFields(data) {
		var fields = data;
		var html = "";
		for(var key in fields) {
			if(fields != "" && "serialVersionUID" != key) {
				html += fields[key] + ": <input type='text' name='"+key+"' value=''/></br>";
			}
		}
		$("#myForm_id").append(html);
	}
	function showList(data) {
		$("#list").html("");
		data = JSON.parse(data);
		var html = "<table>";
		for(var i = 0; i < data.length; i++) {
			var map = data[i];
			var id = map.processId;
			if(id == undefined) id = map.id;
			if(i == 0) {
				html += "<tr>";
				for(var key in map) {
					html += "<td>"+key+"</td>";
				}
				html += "</tr>";
			}
			if(i >= 0) {
				html += "<tr>";
				for(var key in map) {
					html += "<td>"+map[key]+"</td>";
				}
				html += "<td><input type='button' onclick='showFlow(this);' data_task_id='"+map.taskId+"' data_id='"+id+"' value='显示'/></td>";
				html += "</tr>";
			}
		}
		html += "</table>";
		$("#list").append(html);
	}
	var a = "";
	var b = "";
	var c = "";
	var d = "";
	function showFlow(object) {
		var id = $(object).attr("data_task_id");
		if(id == undefined) return alert("没有taskId");
		$.ajax({
			type:"post",
			url:"flow/findFlow.do",
			data:"taskId=" + id,
			success:function(data) {
				var object = data;
				data = JSON.parse(data);
				var html = "";
				for(var key in data) {
					if("processStatusEntities" == key) {
						var o = data[key][0];
						a = o.instanceId;
						b = id;
						c = o.curNodeName;
						d = o.proposer;
					}
				}
				html += object+"</br>";
				html += "同意 或者 驳回 或者 条件表达式:<input type='text' id='applyDesc_id' value=''/>"
				html += "<input type='button' onclick='apply(this)' value='确定'/>";
				$("#flows").html(html);
			}
		});
	}
	function apply(object) {
		var url = $("#flow_request").val();
		var desc = $("#applyDesc_id").val();
		$.ajax({
			type:"post",
			url:"flow/leave/driveProcess.do",
			data:"instanceId="+a+"&taskId="+b+"&curNodeName="+c+"&proposer="+d+"&operateDesc="+desc
		})
	}
</script>
</head>
<body>
	用户ID：<input type="text" id="member_id" value="1"/>
	<form action="" method="post">
		<div id="myForm_id">form表单控件：</br></div>
		输入请求路径：<input type="text" id="main_value" style="width:400px;height:25px;" value=""/>
		<input type="button" id="main_id" value="增删改查（需要在form控件中填和相应的值，如删除需要填ID的值）"/></br>
	</form>
	输入请求路径：<input type="text" style="width:400px;height:25px;" id="showList_value" value=""/>
	<input type="button" id="showList_id" value="显示列表"/></br>
	<input type="button" id="showFlowList_id" action='flow/findMyToDo.do' value="显示待办列表"/>
	<input type="button" id="showFlowList_id" action='flow/findMyCreateFlow.do' value="显示我创建的列表"/>
	<input type="button" id="showFlowList_id" action='' value="显示流过我的列表"/>
	<input type="button" id="showFlowList_id" action='' value="抄送我的"/>
	<input type="button" id="showFlowList_id" action='' value="草稿箱"/></br>
	
	<div id="list"></div>
	
	<div id="flows"></div>
	
	<div>${control}</div>
</body>
</html>