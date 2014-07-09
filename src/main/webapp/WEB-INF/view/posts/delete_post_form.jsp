<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="<c:url value="/resources/static/js/posts/deletePost.js"/>"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />


</head>
<div class="modal-body">

	<p><b>Are you sure you wish to delete post written on ${post.creationDate} in topic ${post.topic.title}?</b></p>

</div>

<div class="modal-footer">
	<button class="btn btn-danger" id="deletePost" value="${post.id}">Delete</button>
	<button class="btn btn-danger" id="cancel" value="${post.id}">Cancel</button>
</div>


</html>
