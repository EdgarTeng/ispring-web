<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<html>
<head>
<title>Player List</title>
</head>
<body>
	<table>
		<tr>
			<td><a href="../create">Add a player</a></td>
		</tr>
	</table>
	<table border="1">
		<tr>
			<td><b>name</b></td>
			<td><b>phone</b></td>
			<td><b>options</b></td>
		</tr>
		<c:forEach var="player" items="${players}">
			<tr>
				<td>${player.name }</td>
				<td>${player.phone }</td>
				<td><a href="../delete/${player.id }">Delete</a> | <a
					href="../edit/${player.id }">Edit</a></td>
			</tr>
		</c:forEach>
	</table>
	<table>
		<tr>
			<c:forEach var="index" begin="1" end="${totalPages }">
				<td><a href="${index-1 }">${index}</a></td>
			</c:forEach>
		</tr>
	</table>
</body>
</html>