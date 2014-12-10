<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<html>
<head>
<title>Player Form</title>
</head>
<body>
	<form:form method="post" action="${oprt=='create'?'save':'../save'}"
		modelAttribute="player">
		<table>
			<form:hidden path="id" />
			<tr>
				<td><b>name:</b></td>
				<td><form:input path="name" /></td>
			</tr>
			<tr>
				<td><b>phone:</b></td>
				<td><b><form:input path="phone" /></b></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="submit"></td>
			</tr>
		</table>
	</form:form>
</body>
</html>