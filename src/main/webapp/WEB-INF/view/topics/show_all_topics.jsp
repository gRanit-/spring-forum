<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<script src="<c:url value="/resources/static/js/topics/topics.js"/>"></script>
</head>
<body>
	<button class="btn" id="addTopic">Add Topic</button>
	<c:if test="${empty topics}">
	<h1>Currently there are no topics!</h1>
	</c:if>
	<table class="table table-bordered">
		<c:forEach var="topic" items="${topics}">
		<tr>
		<td>${topic.title }</td>
		<td>${topic.date }</td>
		<td><a href="<c:url value="/showTopic/${topic.id}" />">View</a></td>
		<security:authorize access="hasRole('ROLE_ADMIN')">
		<td><a href="<c:url value="/deleteTopic/${topic.id}" />">Delete</a></td>
		</security:authorize>
		</tr>
		</tr>
		</c:forEach>
	</table>
	<br>
	<div id="topic-modal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div id="inner-topic-modal" class="modal-body"></div>
			</div>
		</div>
	</div>
</body>
</html>