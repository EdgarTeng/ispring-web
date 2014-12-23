<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<html>
<head>
<title>Register</title>
</head>
<body>
	<form:form method="post" action="do_register" modelAttribute="bean">
		<table>
			<form:hidden path="id" />
			<tr>
				<td><b>userName:</b></td>
				<td><form:input path="userName" /></td>
			</tr>
			<tr>
				<td><b>passWord:</b></td>
				<td><form:input path="passWord" /></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="submit"></td>
			</tr>
		</table>
	</form:form>
</body>
</html>