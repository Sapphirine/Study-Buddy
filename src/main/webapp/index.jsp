 
 <!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">

	
	function createRequest() {
	  var result = null;
	  if (window.XMLHttpRequest) {
	    // FireFox, Safari, etc.
	    result = new XMLHttpRequest();
	    
	  }
	  else if (window.ActiveXObject) {
	    // MSIE
	    result = new ActiveXObject("Microsoft.XMLHTTP");
	  } 
	  else {
	    // No known mechanism -- consider aborting the application
	  }
	  return result;
	}
	
	


	
	//var decode = JSON.parse(json)
	//document.write(decode.userName);
	function httpPost(theUrl, jsonObject)
	{
		var jsonString = JSON.stringify(jsonObject);
	    var self = this;
	    var req = createRequest(); // defined above
	    
	    
	    req.open("POST",theUrl,true);
	    req.setRequestHeader("Content-type", "application/json");
	    //req.setRequestHeader("Content-length", jsonString.length);
	    //req.setRequestHeader("Connection", "close");
	    req.send(jsonString);
	    return req.responseText;
	}
</script>

<html>
	<head>
		<meta charset="utf-8">
		<title>Welcome</title>
		<p>welcome</p >
		<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
		<script>
			$(document).ready(function(){
			  $("button").click(function(){
			    $.post("http://localhost:8080/ERP/user/create",
			    {
			      userName:"Donald Duck",
			      pwd:"Duckburg"
			    },
			    function(data,status){
			      alert("Data: " + data + "\nStatus: " + status);
			    });
			  });
			});
		</script>
	</head> 
	
	<body>
	<!-- 
		<script>
			var user_psw = {};
			user_psw.userName = "HuanGao";
			user_psw.pwd = "7211"
			
			var tmp = httpPost("http://localhost:8080/ERP/user/create", user_psw);
			document.write("post-");
			document.write(tmp);
			document.write(user_psw);
		</script>
		<p>test</p>
		 -->
		 
		 <button>Sign Up</button>
		

	</body>
</html>