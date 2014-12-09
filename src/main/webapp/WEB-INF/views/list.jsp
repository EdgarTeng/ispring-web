<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<html>
<head>
<title>list reservations</title>
</head>
<body>
	<h2>Example JSP Page</h2>

	<strong>Reservations:</strong>
	<br />
	<table border="1">
		<tr>
			<td><b>player</b></td>
			<td><b>sportType</b></td>
			<td><b>courtName</b></td>
			<td><b>date</b></td>
			<td><b>during</b></td>
			<td><b>phone</b></td>
		</tr>
		<c:forEach var="bean" items="${list}">
			<tr>
				<td>${bean.player.name }</td>
				<td>${bean.sportType.name }</td>
				<td>${bean.courtName }</td>
				<td>${bean.date }</td>
				<td>${bean.hour }</td>
				<td>${bean.player.phone }</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>