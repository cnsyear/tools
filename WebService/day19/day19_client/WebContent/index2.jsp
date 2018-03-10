<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script type="text/javascript" src="jquery-1.7.2.js"></script>
<script type="text/javascript">
/**
 * 测试Jquery请求WebService
 */
$(function(){
	
	 $("#btn").click(function(){  
			var name = document.getElementById("name").value;
			var data = '<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"><soap:Body><ns2:say xmlns:ns2="http://server.cnsyear.com/"><arg0>'+name+'</arg0></ns2:say></soap:Body></soap:Envelope>';
			
			
			$.ajax({
				type : "post",
				url : "http://localhost:8080/day19/helloWs",
				data : data,
				success : function(msg){
					alert("------");
					var $Result = $(msg);
					var value = $Result.find("return").text();
					alert(value);
				},
				error : function(msg) {
					//alert("-----"+msg);
				},
				dataType : "xml"
			});
	});
	
});
 

</script>
</head>
<body>
	用户名:
	<input id="name" name="username" value="" />
	<br>
	<button id="btn">Jquery请求webservice</button>
</body>
</html>