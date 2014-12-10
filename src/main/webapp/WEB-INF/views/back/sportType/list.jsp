<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<html>
<head>
<title>SportType List</title>
</head>
<body>
	<table>
		<tr>
			<td><a href="../create">Add a sportType</a></td>
		</tr>
	</table>
	<table border="1">
		<tr>
			<td><b>name</b></td>
			<td><b>options</b></td>
		</tr>
		<c:forEach var="sportType" items="${sportTypes}">
			<tr>
				<td>${sportType.name }</td>
				<td><a href="../delete/${sportType.id }">Delete</a> | <a
					href="../edit/${sportType.id }">Edit</a></td>
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