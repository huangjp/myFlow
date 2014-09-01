<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>流程操作过程</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="css/jquery.treeview.css" />
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script src="js/jquery.treeview.js" type="text/javascript"></script>
	<script type="text/javascript">
	
	$(function() {
		$(".showFlowList_id").click(function() {
			var id = $("#userId").val();
			var url = $(this).attr("action");
			$.ajax({
				type:"post",
				url:url,
				data:"id="+id,
				success:function(data) {
					showList(data);
				}
			});
		});
	})
	
	function showList(data) {
		$("#list").html("");
		data = JSON.parse(data);
		var html = "<table>";
		for(var i = 0; i < data.length; i++) {
			var map = data[i];
			var id = map.id;
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
				html += "<td><input type='button' onclick='showFlow(this);' data_task_id='"+map.flowInstanceId+"' data_id='"+id+"' value='显示'/></td>";
				html += "</tr>";
			}
		}
		html += "</table>";
		$("#list").append(html);
	}
	
	function showFlow(object) {
		var instanceId = $(object).attr("data_task_id");
		var id = $(object).attr("data_id");
		if(id == undefined) return alert("没有taskId");
		$.ajax({
			type:"post",
			url:"showFlow.do",
			data:"flowInstanceId=" + instanceId,
			success:function(data) {
				var html = "";
				html += data+"</br>";
				html += "同意 或者 驳回  或者 取消  或者 条件表达式:<input type='text' id='applyDesc_id' value=''/>"
				html += "<input type='button' onclick='apply(this)' processId = "+id+" value='确定'/>";
				$("#flows").html(html);
			}
		});
	}
	function apply(object) {
		var url = $("#flow_request").val();
		var desc = $("#applyDesc_id").val();
		var id = $(object).attr("processId");
		var userId = $("#userId").val();
		
		$.ajax({
			type:"post",
			url:"driveFlow.do",
			data:"processId="+id+"&userId="+userId+"&operatorDesc="+desc
		})
	}
	
	function userList() {
		$.ajax({
			type:"post",
			url:"getBusinesss.do",
			success:function(data) {
				data = JSON.parse(data);
				var _html = "<ul id='navigation'>";
				for(var key in data) {
					var _array = data[key];
					_html += recursion(_array, "", null);
				}
				_html += "</ul>";
				$("#userList").html(_html);
				$("#navigation").treeview({
					persist: "location",
					collapsed: true,
					unique: true
				});
			}
		})
	}
	
	function recursion(data, html, type) {
		for(var i = 0; i < data.length; i++) {
			var map = data[i];
			html += "<li>"+map.name+"<input type='hidden' value='"+map.id+"'/>";
			for(var key in map) {
				if(key == "groups" && map.groups != null && map.groups.length != 0) {
					var groupsHtml = "<ul>";
					groupsHtml += recursion(map[key], html, "groups");
					groupsHtml += "</ul>";
					html = groupsHtml;
				} 
				if(key == "posts" && map.posts != null && map.posts.length != 0) {
					var postsHtml = "<ul>";
					postsHtml += recursion(map[key], html, "posts");
					postsHtml += "</ul>";
					html = postsHtml;
				}
				if(key == "users" && map.users != null && map.users.length != 0) {
					var usersHtml = "<ul>";
					usersHtml += recursion(map[key], html, "users");
					usersHtml += "</ul>";
					html = usersHtml;
				}
			}
			html += "</li>";
		}
		return html;
	}
	function userHasGroupList() {
		$("#userList").html("");
		$.ajax({
			type:"post",
			url:"getGroupHasUser.do",
			success:function(data) {
				data = JSON.parse(data);
				var html = "<table>";
				for(var i = 0; i < data.length; i++) {
					var map = data[i];
					var json_map = JSON.stringify(map);
					if(i == 0) {
						html += "<tr>";
						for(var key in map) {
							html += "<td>"+key+"</td>";
						}
						html += "</tr>";
						html += "<tr onclick='showUser(this);' my_data="+json_map+">";
						for(var key in map) {
							if(key == "users") {
								html += "<td>";
								for(var j = 0; j < map[key].length; j++) {
									html += "[";
									var userMap = map[key][j];
									for(var userKey in userMap) {
										html += userMap[userKey] + ",";
									}
									html += "]";
								}
								html += "</td>";
							} else {
								html += "<td>"+map[key]+"</td>";
							}
						}
						html += "</tr>";
					} else {
						html += "<tr onclick='showUser(this);' my_data="+json_map+">";
						for(var key in map) {
							if(key == "users") {
								html += "<td>";
								for(var j = 0; j < map[key].length; j++) {
									html += "[";
									var userMap = map[key][j];
									for(var userKey in userMap) {
										html += userMap[userKey] + ",";
									}
									html += "]";
								}
								html += "</td>";
							} else {
								html += "<td>"+map[key]+"</td>";
							}
						}
						html += "</tr>";
					}
				}
				html += "</table>";
				$("#userList").html(html);
			}
		})
	}
	function userHasPostList() {
		$("#userList").html("");
		$.ajax({
			type:"post",
			url:"getPostHasUser.do",
			success:function(data) {
				data = JSON.parse(data);
				var html = "<table>";
				for(var i = 0; i < data.length; i++) {
					var map = data[i];
					var json_map = JSON.stringify(map);
					if(i == 0) {
						html += "<tr>";
						for(var key in map) {
							html += "<td>"+key+"</td>";
						}
						html += "</tr>";
						html += "<tr onclick='showUser(this);' my_data="+json_map+">";
						for(var key in map) {
							if(key == "users") {
								html += "<td>";
								for(var j = 0; j < map[key].length; j++) {
									html += "[";
									var userMap = map[key][j];
									for(var userKey in userMap) {
										html += userMap[userKey] + ",";
									}
									html += "]";
								}
								html += "</td>";
							} else {
								html += "<td>"+map[key]+"</td>";
							}
						}
						html += "</tr>";
					} else {
						html += "<tr onclick='showUser(this);' my_data="+json_map+">";
						for(var key in map) {
							if(key == "users") {
								html += "<td>";
								for(var j = 0; j < map[key].length; j++) {
									html += "[";
									var userMap = map[key][j];
									for(var userKey in userMap) {
										html += userMap[userKey] + ",";
									}
									html += "]";
								}
								html += "</td>";
							} else {
								html += "<td>"+map[key]+"</td>";
							}
						}
						html += "</tr>";
					}
				}
				html += "</table>";
				$("#userList").html(html);
			}
		});
	}
	
	function showUser(object) {
		$("#userSonList").html("");
		var json_data = $(object).attr("my_data");
		var data = JSON.parse(json_data);
		var html = "<table>";
		for(var i = 0; i < data.length; i++) {
			var map = data[i];
			if(i == 0) {
				html += "<tr>";
				for(var key in map) {
					html += "<td>"+key+"</td>";
				}
				html += "</tr>";
				html += "<tr>";
				for(var key in map) {
					html += "<td>"+map[key]+"</td>";
				}
				html += "</tr>";
			} else {
				html += "<tr>";
				for(var key in map) {
					html += "<td>"+map[key]+"</td>";
				}
				html += "</tr>";
			}
		}
		html += "</table>";
		$("#userSonList").html(html);
	}
	</script>
  </head>
  
  <body>
  	输入当前用户ID<input type="text" id="userId" value="1"/></br>
  	<input type=button value="显示用户树列表" onclick="userList();">
  	<input type=button value="用户-群组" onclick="userHasGroupList();">
  	<input type=button value="用户-岗位" onclick="userHasPostList();">
  	<div id="userList"></div>
  	<div id="userSonList"></div>
	<input type="button" class="showFlowList_id" action='myToDo.do' value="显示待办列表"/>
	<input type="button" class="showFlowList_id" action='iCreate.do' value="显示我创建的列表"/>
	<input type="button" class="showFlowList_id" action='passMe.do' value="显示流过我的列表"/>
	<input type="button" class="showFlowList_id" action='ccMe.do' value="抄送我的"/>
  	<div id="list"></div>
  	<div id="flows"></div>
  </body>
</html>
