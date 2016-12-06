<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Get Simple Greeting</title>
</head>
<body style="width: 980px; margin-left: 200px; margin-top: 50px">
	<form:form action="/simple-greet/updateUser.do" method="post"
		modelAttribute="userForm">
		<table border="0">
			<tr>
				<td colspan="4"><a
					href='<c:url value="/jsp/index.jsp"></c:url>'>Go to Home</a></td>
			</tr>
			<tr>
				<td>Current user is:</td>
				<td>
					<c:choose>
						<c:when test="${sessionScope.USER == null}">
							No user in session.
						</c:when>
						<c:otherwise>
							<c:out value="${sessionScope.USER.name}" />
						</c:otherwise>
					</c:choose>
				</td>
				<td>And address is:</td>
				<td>
					<c:choose>
						<c:when test="${sessionScope.USER == null}">
							No user in session.
						</c:when>
						<c:otherwise>
							<c:out value="${sessionScope.USER.address}" />
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td>Set user:</td>
				<td><form:input path="name" /></td>
				<td>And address:</td>
				<td><form:input path="address" /></td>
			</tr>
			<tr>
				<td colspan="4" align="center"><input type="submit"
					value="Update user"></td>
			</tr>
		</table>
	</form:form>
</body>
</html>
