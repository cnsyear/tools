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
 * 测试Ajax请求WebService
 */
function reqWebService(){
	var name = document.getElementById("name").value;
	var data = '<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"><soap:Body><ns2:say xmlns:ns2="http://server.cnsyear.com/"><arg0>'+name+'</arg0></ns2:say></soap:Body></soap:Envelope>';
	//XMLHttpRequest对象
	var request = getRequest();
	request.onreadystatechange = function(){
		if(request.readyState==4 && request.status==200) {
			var result = request.responseXML;
			alert(result);
			var returnEle = result.getElementsByTagName("return")[0];
			var value = returnEle.firstChild.data;
			alert(value);
		}
	};
	
	request.open("POST", "http://localhost:8080/day19/helloWs");
	
	request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	
	request.send(data);
}

function getRequest() {
	var xmlHttp = null;
	try {
		// Firefox, Opera 8.0+, Safari  chrome
		xmlHttp = new XMLHttpRequest();
	} catch (e) {
		// Internet Explorer
		try {
			xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	}
	return xmlHttp;
} 


</script>
</head>
<body>
	用户名:
	<input id="name" name="username" value="" />
	<br>
	<button onclick="reqWebService()">测试Ajax请求WebService</button> 
	
</body>
</html>