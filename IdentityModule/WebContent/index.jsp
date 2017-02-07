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
	<form method="post" action="${pageContext.request.contextPath}/Authentication">
            <center>
			<h2>User Login</h2>
			
	        <p style="color:red;">${messages}</p>
		    
			<table width="30%" cellpadding="2">
				<tr>
					<td>User Name</td>
					<td><input type="text" name="username" value="" width="80" /></td>
				</tr>
				<tr>
					<td>Password</td>
					<td><input type="password" name="password" value="" width="80" /></td>
				</tr>
				<tr>
					<td></td>
					<td><input type="submit" value="Sign in" /></td>
				</tr>
			</table>
        </center>
    </form>
</body>
</html>