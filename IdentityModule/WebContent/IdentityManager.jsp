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
		 body{
	        width:80%;
	        margin-left:auto;
	        margin-right:auto;
	    }
	</style>
</head>
<body>

<div align="right">User: <font color="blue"><%=session.getAttribute("loggedUser") %></font>, <a href="${pageContext.request.contextPath}/Logout">logout</a></div>
<div align="center">
	<h2>Welcome to the Identity Manager site</h2>
	<p style="color:green;" >${messages}</p>
	<p style="color:red;" >${errors}</p>
	<p>These are the identities, if you want to create a new one 
		<a href="${pageContext.request.contextPath}/AddIdentity">click here</a>
	</p>
	<table>
		<thead>
			<tr>
				<th>Id</th>
				<th>Display name</th>
				<th>Email</th>
				<th>Birth date</th>
				<th>User type</th>
				<th colspan="2">Action</th>
		</tr>
		</thead>
		<c:forEach items="${identities}" var="identities">
		    <tr>
		        <td>${identities.getUid()}</td>
		        <td>${identities.getDisplayname()}</td>
		        <td>${identities.getEmail()}</td>
		        <td>${identities.getBirthDate()}</td>
		        <td>${identities.getUserType()}</td>
		        <td><a href="${pageContext.request.contextPath}/Update?uid=${identities.getUid()}">update</a></td>
		        <td><a href="${pageContext.request.contextPath}/Delete?uid=${identities.getUid()}">delete</a></td>
		    </tr>
		</c:forEach>
	</table>
</div>
</body>
</html>