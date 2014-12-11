<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<html>
<head>
<title>Court Form</title>
</head>
<body>
	<form:form method="post" action="save" modelAttribute="bean">
		<table>
			<form:hidden path="id" />
			<tr>
				<td><b>name:</b></td>
				<td><form:input path="name" /></td>
			</tr>
			<tr>
				<td><b>sportType:</b></td>
				<td><form:select path="sportType.id" items="${sportTypeList }"
						itemLabel="name" itemValue="id"></form:select></td>
			</tr>
			<tr>
				<td><b>status:</b></td>
				<td><form:select path="courtStatus.id" items="${courtStatusList }"
						itemLabel="name" itemValue="id"></form:select></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="submit"></td>
			</tr>
		</table>
	</form:form>
</body>
</html>