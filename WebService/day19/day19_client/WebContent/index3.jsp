<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script type="text/javascript" src="jquery-1.7.2.js"></script>
<script type="text/javascript">


//使用HttpUrlConnect请求

$(function(){
	
$("#btn").click(function(){
	
	var name = document.getElementById("name").value;
	$.ajax({
		type : "post",
		url : "HttpUrlConnectServlet",
		data : {
			"name":name
		},
		success : function(msg){
			 
			alert(msg);
		},
		error : function(msg) {
			//alert("-----"+msg);
		},
		dataType : "text"
	});
});	
	

});
 

</script>
</head>
<body>
	用户名:
	<input id="name" name="username" value="" />
	<br>
	<button id="btn">HttpUrlConnect请求webservice</button>
</body>
</html>