<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Get Simple Greeting</title>
</head>
<body style="width: 980px; margin-left: 200px; margin-top: 50px">
	<table border="0">
		<tr>
			<td colspan="4"><a href='<c:url value="/jsp/index.jsp"></c:url>'>Go
					to Home</a></td>
		</tr>
		<tr>
			<td>Current user is:</td>
			<td><c:out value="${userForm.name}" /></td>
			<td>And address is:</td>
			<td><c:out value="${userForm.address}" /></td>
		</tr>
	</table>
</body>
</html>
