<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<html>
<head>
<title>list player</title>
</head>
<body>
	<strong>Players:</strong>
	<br />
	<table border="1">
		<tr>
			<td><b>name</b></td>
			<td><b>phone</b></td>
		</tr>
		<c:forEach var="bean" items="${list}">
			<tr>
				<td>${bean.name }</td>
				<td>${bean.phone }</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>