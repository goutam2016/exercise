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
			<td><a href='<c:url value="/jsp/index.jsp"></c:url>'>Go to Home</a></td>
		</tr>
		<tr>
			<td>
				<c:choose>
					<c:when test="${sessionScope.USER == null}">
						No user in session. Please set user first to get your greeting.
					</c:when>
					<c:otherwise>
						Welcome to this sample application, 
							<c:out value="${sessionScope.USER.name}"/> from <c:out value="${sessionScope.USER.address}"/> !
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
	</table>
</body>
</html>
