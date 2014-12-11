<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<html>
<head>
<title>Court List</title>
</head>
<body>

	<!--  search bar -->
	<form action="search" method="post">
		<table>
			<tr>
				<td><input type="text" name="condition"></td>
				<td><input type="submit" value="search"></td>
			</tr>
		</table>
	</form>

	<!-- add a new sportType -->
	<table>
		<tr>
			<td><a href="create">Add a court</a></td>
		</tr>
	</table>

	<!-- list sportTypes -->
	<table border="1">
		<tr>
			<td><b>name</b></td>
			<td><b>sportType</b></td>
			<td><b>reservered</b></td>
			<td><b>options</b></td>
		</tr>
		<c:forEach var="bean" items="${pagedList.content}">
			<tr>
				<td>${bean.name }</td>
				<td>${bean.sportType.name }</td>
				<td>${bean.reservered }</td>
				<td><a href="delete?id=${bean.id }">Delete</a> | <a
					href="edit?id=${bean.id }">Edit</a></td>
			</tr>
		</c:forEach>
	</table>

	<!-- paging -->
	<table>
		<tr>
			<c:forEach var="index" begin="1" end="${pagedList.totalPages }">
				<td><a href="list?pageIndex=${index-1 }">${index}</a></td>
			</c:forEach>
		</tr>
	</table>
</body>
</html>