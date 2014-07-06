<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="<c:url value="/resources/static/js/posts/posts.js"/>"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<security:authentication var="user" property="principal" />


<c:forEach var="post" items="${posts}">

				<tr>
					<td>${post.author.email}</td>
					<td>${post.creationDate}</td>
					
					
					<c:set var="admin" value="${false}"/>
					<security:authorize access="hasRole('ROLE_ADMIN')">
   					 	<c:set var="admin" value="${true}"/>
					</security:authorize>
					<c:if test="${user.username == post.author.email || admin}">
						<td>
						<button class="btn btn-default" value="${post.id}" name="deletePost">Delete</button>
						<button class="btn btn-default" value="${post.id}" name="editPost">Edit</button>
						
						</td>
					</c:if>
					
				</tr>
				<tr rowspan="2">
					<td colspan="3"
						style="white-space: -moz-pre-wrap !important; white-space: -pre-wrap; white-space: -o-pre-wrap; white-space: pre-wrap; word-wrap: break-word; word-break: break-all; white-space: normal;">${post.text}</td>


			</tr>
</c:forEach>
