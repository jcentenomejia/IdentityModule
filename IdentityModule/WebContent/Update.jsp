<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Identity Manager</title>
</head>
<body>
	<center>
		<h2>Update Identity</h2>
		<c:if test="${messages != null}">
		<c:forEach items="${messages}" var="messages">
		        <p style="color:red;">${messages}</p>
		    </c:forEach>
		</c:if>
		
		<form method="post" action="${pageContext.request.contextPath}/Update">
		<table cellpadding="2">
			<tr>
				<td>User ID:</td>
				<td><label>${uid }</label></td>
			</tr>
			<tr>
				<td>User name:</td>
				<td><input type="text" name="displayname" value="${displayname }"></input></td>
			</tr>
			<tr>
				<td>Email:</td>
				<td><input type="text" name="email" value="${email }"></input></td>
			</tr>
			<tr>
				<td>Birth date:</td>
				<td><input type="text" name="birthdate" value="${birthdate }"></input></td>
			</tr>
			
			<tr>
				<td></td>
				<td><input type="submit" value="Submit"/></td>
			</tr>
		</table>
		</form>
		
	</center>
</body>
</html>