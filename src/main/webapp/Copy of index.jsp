<!-- 
<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
	<head>
		<meta charset="utf-8">
		<title>Welcome</title>
	</head> 
	<body>
		<c:url value="/showMessage" var="messageUrl" />
		<a href="${messageUrl}">Click to enter</a>
	</body>
</html>
 -->

 
 <!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script>

	var user_psw = { };
	user_psw.name = "HuanGao";
	user_psw.password = 7211




	var json = JSON.stringify(user_psw);
	var decode = JSON.parse(json)
	document.write(decode.k.user_id);
	function httpGet(theUrl)
	{
    	var xmlHttp = null;
    	xmlHttp = new XMLHttpRequest();
    	xmlHttp.open( "POST", theUrl, false );
    	xmlHttp.send( decode );
    	return xmlHttp.responseText;
	}
</script>

<html>
	<head>
		<meta charset="utf-8">
		<title>Welcome</title>
		<p>welcome</p >
	</head> 
	
	<body>
		<!--<c:url value="/showMessage" var="messageUrl" />
		<c:url value="/showName" var="nameUrl" />-->
		

		<!--<a href=" ">string</a >
		<a href="${nameUrl}">name</a >-->
	    <!--<c:url value="/showName" var="nameUrl" />
		<c:url value="/typeSearchString" var="searchStringUrl" />
		
		
		<a href="${nameUrl}">name</a >		
		<a href="${searchStringUrl}">search</a >	 -->
		
		httpGet("http://localhost:8080/ERP/user/create");
		

	</body>
</html>