<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<html>
<head>
<link rel="stylesheet" type="text/css" media="screen"
	href="resources/css/style.css" />
<title>Register</title>
</head>

<body>
	<form:form method="post" action="do_register" modelAttribute="bean">
		<table>
			<form:hidden path="id" />
			<tr>
				<td><b>username:</b></td>
				<td><form:input path="username" /></td>
			</tr>
			<tr>
				<td><b>password:</b></td>
				<td><form:input path="password" /></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="submit"></td>
			</tr>
		</table>
	</form:form>
</body>
</html>