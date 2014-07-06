<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<script src="<c:url value="/resources/static/js/posts/deletePost.js"/>"></script>
</head>
<div class="modal-body">

	<p><b>Are you sure you wish to delete post written on ${post.creationDate} in topic ${post.topic.title}?</b></p>

</div>

<div class="modal-footer">
	<button class="btn btn-danger" id="deletePost">Delete</button>
	<button class="btn btn-danger" id="cancel">Cancel</button>
</div>


</html>