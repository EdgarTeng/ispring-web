<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<html>
<head>
<title>CourtStatus List</title>
</head>
<body>

	<!-- add a new CourtStatus -->
	<table>
		<tr>
			<td><a href="create">Add a court</a></td>
		</tr>
	</table>

	<!-- list CourtStatus -->
	<table border="1">
		<tr>
			<td><b>name</b></td>
			<td><b>options</b></td>
		</tr>
		<c:forEach var="bean" items="${pagedList.content}">
			<tr>
				<td>${bean.name }</td>
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