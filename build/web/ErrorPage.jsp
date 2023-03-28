<%-- 
    Document   : newjsp
    Created on : Feb 9, 2023, 5:08:18 AM
    Author     : TNA
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>test</title>
	</head>
	<body>
		<h3 align="center">Status of E-Mail : </h3>
		<h1 align="center">${requestScope.message}</h1>
	</body>
</html>
