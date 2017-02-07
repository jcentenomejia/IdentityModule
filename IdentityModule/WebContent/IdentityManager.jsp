<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Identity Manager</title>
	<style>
		table, th, td {
		    border: 1px solid black;
		    border-collapse: collapse;
		}
		th, td {
		    padding: 5px;  
		}
	</style>
</head>
<body>
<center>
	<h2> ${user}, welcome to the Identity Manager site</h2>
	
	<p style="color:green;" >${messages}</p>
	
	<p>These are the identities, if you want to create a new one 
		<a href="${pageContext.request.contextPath}/NewIdentity.jsp">click here</a>
	</p>
	<table>
		<thead>
			<tr>
				<th>Id</th>
				<th>Display name</th>
				<th>Email</th>
				<th>Birth date</th>
				<th colspan="2">Action</th>
		</tr>
		</thead>
		<c:forEach items="${identities}" var="identities">
		    <tr>
		        <td>${identities.getUid()}</td>
		        <td>${identities.getDisplayname()}</td>
		        <td>${identities.getEmail()}</td>
		        <td>${identities.getBirthDate()}</td>
		        <td><a href="${pageContext.request.contextPath}/Update?uid=${identities.getUid()}">update</a></td>
		        <td><a href="${pageContext.request.contextPath}/Delete?uid=${identities.getUid()}">delete</a></td>
		    </tr>
		</c:forEach>
	</table>
</center>
</body>
</html>